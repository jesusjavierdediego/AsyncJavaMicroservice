package com.ms.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class JSONPlaceholderItem {
    private final String userId;
    private final String id;
    private final String title;
    private final String body;
    

    @JsonCreator
    public JSONPlaceholderItem(
                        @JsonProperty("userId") String userId,
                        @JsonProperty("id") String id,
                        @JsonProperty("title") String title,
                        @JsonProperty("body") String body){
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    
    public String getBody() {
        return body;
    }
    
}
