package com.stn.pojo;

public class Topic {

    private int topicId;
    private String name;
    private int groupId;
    private int authorId;
    private int authorClass;
    private String authorName;
    private int totalposts;

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setAuthorClass(int authorClass) {
        this.authorClass = authorClass;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setTotalposts(int totalposts) {
        this.totalposts = totalposts;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getName() {
        return name;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getAuthorClass() {
        return authorClass;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getTotalposts() {
        return totalposts;
    }
}
