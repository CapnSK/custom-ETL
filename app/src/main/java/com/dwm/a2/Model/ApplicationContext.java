package com.dwm.a2.Model;

import java.util.List;
import java.util.Map;

import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

public class ApplicationContext {
    private Map<String, ArticleResponse> phase1Output; //EXTRACT
    private Boolean phase2Output; //QUEUE
    private List<Article> phase3Output; //TRANSFORM
    private String phase4Outout; //LOAD
    
   
    public Map<String, ArticleResponse> getPhase1Output() {
        return phase1Output;
    }
    public void setPhase1Output(Map<String, ArticleResponse> phase1Output) {
        this.phase1Output = phase1Output;
    }
    public Boolean getPhase2Output() {
        return phase2Output;
    }
    public void setPhase2Output(Boolean phase2Output) {
        this.phase2Output = phase2Output;
    }
    public List<Article> getPhase3Output() {
        return phase3Output;
    }
    public void setPhase3Output(List<Article> phase3Output) {
        this.phase3Output = phase3Output;
    }
    public String getPhase4Outout() {
        return phase4Outout;
    }
    public void setPhase4Outout(String phase4Outout) {
        this.phase4Outout = phase4Outout;
    }
}
