package com.dwm.a2.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bson.Document;

import com.dwm.a2.Model.Article;
import com.dwm.a2.Utils.Constants;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;


public class Load {
    private static LogService logger = LogService.getLogger(Load.class);
    
    public Boolean saveToRemote(List<Article> phase3Output){
        Boolean savedSuccessfully = false;
        try(MongoClient mongoClient = MongoClients.create(Constants.MONGO_CONNECTION_STRING)){
            MongoDatabase DB = mongoClient.getDatabase(Constants.DB_NAME);
            if(phase3Output != null){
                savedSuccessfully = phase3Output.stream().filter(Objects::nonNull)
                .map(article->storeArticle(DB, article)).allMatch(success->success);
            }
        }
        return savedSuccessfully;
    }

    private Boolean storeArticle(MongoDatabase DB, Article article){
        Boolean saved = false;
        if(DB != null && article != null){
            MongoCollection<Document> collection = DB.getCollection(article.getTopic());
            Document document = new Document();
            document.append("Title", article.getTitle());
            document.append("Content", article.getContent());

            InsertOneResult result = collection.insertOne(document);
            saved = result.wasAcknowledged();
        }
        return saved;
    }
}
