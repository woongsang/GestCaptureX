package com.ablethon.woongsang.gestcapturex.VO;

import java.util.List;
import java.util.Vector;

/**
 * Created by SangHeon on 2017-10-13.
 */

public class RssFeed {
    private String title = null;
    private String description = null;
    private String link = null;
    private String pubdate = null;
    private List<RssItem> itemList;

    public RssFeed() {
        itemList = new Vector<RssItem>(0);
    }



    public void addItem(RssItem item) {
        itemList.add(item);
    }

    RssItem getItem(int location) {
        return itemList.get(location);
    }

    public List<RssItem> getList() {
        return itemList;
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public  String getPubdate() {
        return pubdate;
    }
}

