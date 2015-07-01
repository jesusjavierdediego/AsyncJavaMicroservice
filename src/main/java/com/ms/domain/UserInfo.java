package com.ms.domain;


public class UserInfo {
    private Identity gitHubUser;
    private JSONPlaceholderItem jSONplaceholderItem;
    private Long time;

    public UserInfo() {
    }
       
    public UserInfo(JSONPlaceholderItem jSONplaceholderItem, Identity gitHubUser) {
        this.gitHubUser = gitHubUser;
        this.jSONplaceholderItem = jSONplaceholderItem;
//        this.time = time;
    }

    public Identity getGitHubUser() {
        return gitHubUser;
    }

    public JSONPlaceholderItem getJSONplaceholderItem() {
        return jSONplaceholderItem;
    }
    
    public void setJSONplaceholderItem(final JSONPlaceholderItem jSONplaceholderItem) {
        this.jSONplaceholderItem = jSONplaceholderItem;
    }
    
    public void setGitHubUser(final Identity gitHubUser) {
        this.gitHubUser = gitHubUser;
    }
    
//    public Long getTime(){
//        return time;
//    }
//    
//    public void setTime(long time){
//        this.time = time;
//    }
    
    
}
