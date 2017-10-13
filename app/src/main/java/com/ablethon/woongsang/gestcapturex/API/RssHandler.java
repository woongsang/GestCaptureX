package com.ablethon.woongsang.gestcapturex.API;

import android.util.Log;

import com.ablethon.woongsang.gestcapturex.VO.RssFeed;
import com.ablethon.woongsang.gestcapturex.VO.RssItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by SangHeon on 2017-10-13.
 */

public class RssHandler extends DefaultHandler {

    final int STATE_UNKNOW = 0;
    final int STATE_TITLE = 1;
    final int STATE_DESCRIPTION = 2;
    final int STATE_LINK = 3;
    final int STATE_PUBDATE = 4;
    int currentState = STATE_UNKNOW;

    public RssFeed feed;
    public RssItem item;

    boolean itemFound = false;

    public RssHandler() {
    }

    public RssFeed getFeed() {
        return feed;
    }


    @Override
    public void startDocument() throws SAXException {
        feed = new RssFeed();
        item = new RssItem();
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (localName.equalsIgnoreCase("item")) {
            itemFound = true;
            item = new RssItem();
            currentState = STATE_UNKNOW;
        } else if (localName.equalsIgnoreCase("title")) {
            currentState = STATE_TITLE;
        } else if (localName.equalsIgnoreCase("description")) {
            currentState = STATE_DESCRIPTION;
        } else if (localName.equalsIgnoreCase("link")) {
            currentState = STATE_LINK;
        } else if (localName.equalsIgnoreCase("pubdate")) {
            currentState = STATE_PUBDATE;
        } else {
            currentState = STATE_UNKNOW;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equalsIgnoreCase("item")) {
            feed.addItem(item);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        String strCharacters = new String(ch, start, length);

        if (itemFound == true) {
            // "item" tag found, it's item's parameter
            switch (currentState) {
                case STATE_TITLE:
                    item.setTitle(strCharacters);
                    break;
                case STATE_DESCRIPTION:
                    item.setDescription(strCharacters);
                    break;
                case STATE_LINK:
                    item.setLink(strCharacters);
                    break;
                case STATE_PUBDATE:
                    item.setPubdate(strCharacters);
                    break;
                default:
                    break;
            }
        } else {
            // not "item" tag found, it's feed's parameter
            switch (currentState) {
                case STATE_TITLE:
                    feed.setTitle(strCharacters);
                    break;
                case STATE_DESCRIPTION:
                    feed.setDescription(strCharacters);
                    break;
                case STATE_LINK:
                    feed.setLink(strCharacters);
                    break;
                case STATE_PUBDATE:
                    feed.setPubdate(strCharacters);
                    break;
                default:
                    break;
            }
        }
        currentState = STATE_UNKNOW;
    }
}
