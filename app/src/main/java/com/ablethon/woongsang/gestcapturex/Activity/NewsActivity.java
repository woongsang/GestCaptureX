package com.ablethon.woongsang.gestcapturex.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ablethon.woongsang.gestcapturex.API.CommonLibrary;
import com.ablethon.woongsang.gestcapturex.API.DownloadTask;
import com.ablethon.woongsang.gestcapturex.API.ProcessXMLTask;
import com.ablethon.woongsang.gestcapturex.API.RssHandler;
import com.ablethon.woongsang.gestcapturex.API.TouchInterface;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessCallGesture;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessNewsGesture;
import com.ablethon.woongsang.gestcapturex.R;
import com.ablethon.woongsang.gestcapturex.VO.Article;
import com.ablethon.woongsang.gestcapturex.VO.RssFeed;
import com.ablethon.woongsang.gestcapturex.VO.RssItem;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by SangHeon on 2017-10-12.
 */

public class NewsActivity  extends Activity implements TextToSpeech.OnInitListener {

    public static TextToSpeech myTTS;
    static DownloadTask task = null;

    public static ArrayList<String> mDatas= new ArrayList<String>();
    static ListView listview;
    public static int itemSelector = -1;
    Context context=this;
    int callChecker = 0;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listview= (ListView) findViewById(R.id.NewsListView);


            ProcessXMLTask xmlTask = new ProcessXMLTask();
            xmlTask.execute("http://myhome.chosun.com/rss/www_section_rss.xml");

        itemSelector = -1;

        callChecker=0;
        listview.setOnTouchListener(scrollChecker);
    }
    public void setListView(){
        myTTS = new TextToSpeech(this, this);
        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, mDatas);
        listview.setAdapter(adapter);
    }


    AdapterView.OnTouchListener scrollChecker = new  AdapterView.OnTouchListener() {


        ProcessNewsGesture pg= new ProcessNewsGesture();                               //to prcessing gesture
        TouchInterface TI = new TouchInterface((Activity) context,context,pg);       //to prcessing gesture

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onTouch(View v, MotionEvent event) {


            if(TI.gestureInterface(event)){    //to prcessing gesture
                return true;                //to prcessing gesture
            }else{                          //to prcessing gesture
                return false;            //to prcessing gesture
            }
        }
    };

    public static void setMdatas(){

            for(int i=0;i<CommonLibrary.ARTICLE_LIST.size();i++){
                NewsActivity.mDatas.add( CommonLibrary.ARTICLE_LIST.get(i).getTitle() );


        }
    }
    /*
    *  다음 인덱스를 구하는 메소드
    *  operator이 2이면 위로이동 1이면 아래로 이동
    * */
    public static String getNextContent(int operator){
        if(operator==1){
            if(itemSelector < mDatas.size()-1 ) {
                itemSelector++;
            }else {
                itemSelector = 0;
            }
        }else{
            if(itemSelector > 0 ) {
                itemSelector--;
            }else {
                if(itemSelector ==-1){
                    itemSelector =0;
                }else {
                    itemSelector = mDatas.size() - 1;
                }
            }
        }
        return mDatas.get(itemSelector);
    }

    public void onInit(int status) {
        String myText1 = "뉴스기사를 선택하려면 위 아래로 드래그해주세요";
        String myText2 = "해당 뉴스를 청취하시려면 좌 우로 드래그해주세요.";
        myTTS.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
        myTTS.speak(myText2, TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    class ProcessXMLTask extends AsyncTask<String, Void, Void> {
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
            setListView();
            super.onPostExecute(result);
        }
    }

}
