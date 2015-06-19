/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class CombinedEntity {
    
    private FacebookUser facebookUser;
    private GitHubUser gitHubUser;
    private long processingTime;
    
    public CombinedEntity() {
    }


    @JsonCreator
    public CombinedEntity(
            @JsonProperty("FacebookUser") FacebookUser facebookUser, 
            @JsonProperty("GitHubUser") GitHubUser gitHubUser,
            @JsonProperty("ProcessingTime") long processingTime
    ) {
        this.facebookUser = facebookUser;
        this.gitHubUser = gitHubUser;
        this.processingTime = processingTime;
    }
    
    public FacebookUser getFacebookUser() {
        return facebookUser;
    }

    public void setFacebookUser(final FacebookUser facebookUser) {
        this.facebookUser = facebookUser;
    }

    public void setGitHubUser(final GitHubUser gitHubUser) {
        this.gitHubUser = gitHubUser;
    }

    public GitHubUser getGitHubUser() {
        return gitHubUser;
    }
    
    public void setProcessingTime(final long processingTime) {
        this.processingTime = processingTime;
    }

}
