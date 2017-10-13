package com.ablethon.woongsang.gestcapturex.API;

import android.os.AsyncTask;
import android.util.Log;

import com.ablethon.woongsang.gestcapturex.Activity.NewsActivity;
import com.ablethon.woongsang.gestcapturex.Activity.WeatherActivity;
import com.ablethon.woongsang.gestcapturex.VO.Article;
import com.ablethon.woongsang.gestcapturex.VO.RssFeed;
import com.ablethon.woongsang.gestcapturex.VO.RssItem;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by SangHeon on 2017-10-13.
 */

public class ProcessXMLTask extends AsyncTask<String, Void, Void> {
    private RssFeed mRssFeed = null;

    protected Void doInBackground(String... urls) {
        try {

            URL rssUrl = new URL(urls[0]);
            SAXParserFactory mySAXParserFactory = SAXParserFactory.newInstance();
            SAXParser mySAXParser = mySAXParserFactory.newSAXParser();
            XMLReader myXMLReader = mySAXParser.getXMLReader();
            RssHandler myRSSHandler = new RssHandler();
            myXMLReader.setContentHandler(myRSSHandler);
            InputSource myInputSource = new InputSource(rssUrl.openStream());
            myXMLReader.parse(myInputSource);

            mRssFeed=myRSSHandler.getFeed();
            List<RssItem> list = mRssFeed.getList();

            for(int i=0;i<list.size();i++){
                CommonLibrary.insertArticle(list.get(i).getTitle(),list.get(i).getDescription());
            }
            NewsActivity.setMdatas();
          new NewsActivity().setListView();

        } catch (MalformedURLException e) {
            e.printStackTrace();
         //   mResult.setText("Cannot connect RSS!");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
         //   mResult.setText("Cannot connect RSS!");
        } catch (SAXException e) {
            e.printStackTrace();
           // mResult.setText("Cannot connect RSS!");
        } catch (IOException e) {
            e.printStackTrace();
           // mResult.setText("Cannot connect RSS!");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);
    }
}

