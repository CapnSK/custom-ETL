package com.dwm.a2.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.dwm.a2.Enums.FilterType;
import com.dwm.a2.Model.Article;
import com.dwm.a2.Utils.Constants;

public class Transform {
    private static LogService logger = LogService.getLogger(Transform.class);
    public List<Article> clean(Boolean filesQueued){
        List<Article> cleanedArticles = new ArrayList<>();
        if(filesQueued){
            List<Article> rawArticles = new Queue().getArticles();
            

            cleanedArticles = rawArticles.stream().filter(Objects::nonNull).map(rawArticle->{
                Article cleanedArticle = new Article();
                cleanedArticle.setTopic(rawArticle.getTopic());
                cleanedArticle.setTitle(cleanUsingFilters(rawArticle.getTitle(), null));
                cleanedArticle.setContent(cleanUsingFilters(rawArticle.getContent(), null));
                return cleanedArticle;
            }).collect(Collectors.toList());
            
        }
        return cleanedArticles;
    }

    private String cleanUsingFilters(String input, List<FilterType> filters){
        String output = null;
        if(input != null){
            List<Pattern> patterns = new ArrayList<>();
            //if filters not specified then use all
            if(filters == null){
                Pattern UrlPattern = Pattern.compile(Constants.URL_REGEX);
                Pattern SpecialCharPattern = Pattern.compile(Constants.SPECIAL_CHARACTER_REGEX);
                Pattern EmoticonsPattern = Pattern.compile(Constants.EMOTICONS_REGEX);
    
                patterns.add(UrlPattern);
                patterns.add(SpecialCharPattern);
                patterns.add(EmoticonsPattern);
            }
            
            AtomicReference<String> op = new AtomicReference<>(input);
            patterns.stream().forEach(pattern->{
                Matcher matcher = pattern.matcher(op.get());
                op.set(matcher.replaceAll(""));
            });

            output = op.get();
        }
        return output;
    }
}
