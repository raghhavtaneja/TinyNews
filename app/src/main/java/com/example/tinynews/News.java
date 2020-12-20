package com.example.tinynews;

public class News {
    private String title;
    private String source;
    private String imageUrl;
    private String url;

    public News(String title, String source, String imageUrl,String url) {
        this.title = title;
        this.source = source;
        this.imageUrl = imageUrl;
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String author) {
        this.source = source;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public String getUrl() { return url;}

}
