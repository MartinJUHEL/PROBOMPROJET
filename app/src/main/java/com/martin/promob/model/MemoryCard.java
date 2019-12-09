package com.martin.promob.model;

public class MemoryCard {
    private String name;
    private int imageUrl;
    private boolean discover;
    private boolean find;

    public MemoryCard(String name,int imageUrl){
        this.imageUrl=imageUrl;
        this.name=name;
        this.discover=false;
        this.find=false;
    }

    public boolean isDiscover() {
        return discover;
    }

    public void setDiscover(boolean discover) {
        this.discover = discover;
    }

    public String getName() {
        return name;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setFind(boolean find) {
        this.find = find;
    }

    public boolean isFind() {
        return find;
    }
}
