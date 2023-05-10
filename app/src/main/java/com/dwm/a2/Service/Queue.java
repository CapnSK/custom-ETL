package com.dwm.a2.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.dwm.a2.Model.Article;
import com.dwm.a2.Utils.Constants;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

public class Queue {
    private static LogService logger = LogService.getLogger(Queue.class);
    public Boolean persistArticles(Map<String, ArticleResponse> phase1Output){
        Boolean filesSaved = false;
        if(phase1Output != null){
            Integer totalCount = phase1Output.values().stream()
                                .filter(Objects::nonNull)
                                .filter(response->response.getArticles() != null)
                                .map(response->response.getArticles().size()).reduce(0, (prev, curr)->prev+curr);
            Integer fileCount = Integer.divideUnsigned(totalCount, Constants.PAGE_SIZE);
            if(totalCount % Constants.PAGE_SIZE != 0){
                fileCount++;
            }


            AtomicInteger currArticleInd = new AtomicInteger(0);
            List<String> keywordList = phase1Output.entrySet().stream().map(Entry::getKey).collect(Collectors.toList());
            logger.log("keywordList: "+keywordList.toString());
            AtomicInteger keywordListIndex = new AtomicInteger(0);
            AtomicReference<String> currentKeyword = new AtomicReference<>(keywordList.get(keywordListIndex.get()));

            filesSaved = IntStream.range(0, fileCount).boxed().map(fileNo ->{
                List<Article> contentsToSave = new ArrayList<>();
                Integer countTillNow = 0;
                while(countTillNow < 5){
                    Article article = new Article();
                    if(currArticleInd.get() >= phase1Output.get(currentKeyword.get()).getArticles().size()){
                        currArticleInd.set(0);
                        if(keywordListIndex.incrementAndGet() < keywordList.size()){
                            currentKeyword.set(keywordList.get(keywordListIndex.get()));
                        }
                        else{
                            break;
                        }
                        continue;
                    }
                    logger.log("Current keyword is: "+currentKeyword.get());
                    article.setTopic(currentKeyword.get());
                    article.setTitle(phase1Output.get(currentKeyword.get()).getArticles().get(currArticleInd.get()).getTitle());
                    if(phase1Output.get(currentKeyword.get()).getArticles().get(currArticleInd.get()).getContent() != null){
                        article.setContent(phase1Output.get(currentKeyword.get()).getArticles().get(currArticleInd.get()).getContent());
                    }
                    else{
                        article.setContent(phase1Output.get(currentKeyword.get()).getArticles().get(currArticleInd.get()).getDescription());
                    }
                    contentsToSave.add(article);
                    currArticleInd.getAndIncrement();
                    countTillNow++;
                }
                logger.log("Saving for file no : "+ fileNo);
                return PersistenceService.save(fileNo, contentsToSave);
            }).allMatch(success -> success);
        }
        return filesSaved;
    }


    public List<Article> getArticles(){
        List<Article> articles = null;
        //[[topic1, title1, content1]...]
        List<List<String>> savedArticlesRaw = PersistenceService.getSaved();
        if(savedArticlesRaw != null){
            articles = savedArticlesRaw.stream().filter(Objects::nonNull).filter(rawArticle->rawArticle.size() == 3)
            .map(rawArticle->{
                String topic = rawArticle.get(0);
                String title = rawArticle.get(1);
                String content = rawArticle.get(2);

                return new Article(topic, title, content);
            }).collect(Collectors.toList());

        }
        return articles;
    }
}
