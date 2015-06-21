package com.ms.domain;


public class UserInfo {
    private GitHubUser gitHubUser;
    private JSONPlaceholderItem jSONplaceholderItem;

    public UserInfo() {
    }
       
    public UserInfo(JSONPlaceholderItem jSONplaceholderItem, GitHubUser gitHubUser) {
        this.gitHubUser = gitHubUser;
        this.jSONplaceholderItem = jSONplaceholderItem;
    }

    public GitHubUser getGitHubUser() {
        return gitHubUser;
    }

    public JSONPlaceholderItem getJSONplaceholderItem() {
        return jSONplaceholderItem;
    }
    
    public void setJSONplaceholderItem(final JSONPlaceholderItem jSONplaceholderItem) {
        this.jSONplaceholderItem = jSONplaceholderItem;
    }
    
    public void setGitHubUser(final GitHubUser gitHubUser) {
        this.gitHubUser = gitHubUser;
    }
}
