package com.ablethon.woongsang.gestcapturex.VO;

/**
 * Created by SangHeon on 2017-10-13.
 */

public class RssItem {

    private String title = null;
    private String description = null;
    private String link = null;
    private String pubdate = null;

    public RssItem() {
    }

    public void setTitle(String value) {
        title = value;
    }

    public void setDescription(String value) {
        description = value;
    }

    public void setLink(String value) {
        link = value;
    }

    public void setPubdate(String value) {
        pubdate = value;
    }

    public  String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getPubdate() {
        return pubdate;
    }

    @Override
    public String toString() {
        return title;
    }
}

