package com.dwm.a2.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;



public class FetchRemote {
    private static LogService logger = LogService.getLogger(FetchRemote.class);
    HttpClient restClient = HttpClient.getInstance();
    NewsApiClient client = NewsApiClientWrapper.getInstance();

    public Map<String,ArticleResponse> getApiResponse(List<String> keywords){
        Map<String,ArticleResponse> responses = new HashMap<>();
        keywords.stream().forEach(keyword->{
            responses.put(keyword, getApiResponse(keyword));
        });
        return responses;
    }

    public ArticleResponse getApiResponse(String keyword){
        CompletableFuture<ArticleResponse> future = fetchAsync(keyword);
        ArticleResponse response = null;
        try {
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.log(Arrays.toString(e.getStackTrace()));
            Thread.currentThread().interrupt();
        }
        return response;
    }

    private CompletableFuture<ArticleResponse> fetchAsync(String keyword){
        CompletableFuture<ArticleResponse> future = new CompletableFuture<>();
        client.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                                        .q(keyword)
                                        .build()
        ,new NewsApiClient.ArticlesResponseCallback() {
            @Override
            public void onSuccess(ArticleResponse response) {
                future.complete(response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                future.completeExceptionally(throwable);
            }
        });
        return future;
    }
}
