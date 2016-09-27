package com.example.dpat.myapplication3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Devang on 2016-09-25.
 */
public class ReadRss extends AsyncTask<Void, Void, Void> {

    Context context;
    String address = "http://www.cbc.ca/cmlink/rss-topstories";
    URL url;
    ProgressDialog progressDialog;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;

    public ReadRss(Context context, RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute(){
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        ListAdapter adapter = new ListAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Void doInBackground(Void... params) {

        ProcessXml(getData());
        return null;
    }

    private void ProcessXml(Document data) {
        if(data != null){
            feedItems = new ArrayList<>();
         //   Log.d("Root",data.getDocumentElement().getNodeName());
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for(int i = 0; i < items.getLength(); i++){
                Node currentChild = items.item(i);
                if(currentChild.getNodeName().equalsIgnoreCase("item")){
                    FeedItem item = new FeedItem();
                    NodeList itemChilds = currentChild.getChildNodes();
                    for(int j = 0; j < itemChilds.getLength(); j++){
                        Node current = itemChilds.item(j);
                    //    Log.d("textcontent", current.getTextContent());
                        if(current.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(current.getTextContent());
                        }
                        else if(current.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(current.getTextContent());
                        }
                        else if(current.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(current.getTextContent());
                        }
                        else if(current.getNodeName().equalsIgnoreCase("author")){
                            item.setAuthor(current.getTextContent());
                        }
/*
                        else if(current.getNodeName().equalsIgnoreCase("img")){
                            String url = current.getAttributes().item(0).getTextContent();
                            item.setThumbnailUrl(url);
                        }
*/
                    }
                    feedItems.add(item);
                    Log.d("itemTitle", item.getTitle());
                    Log.d("itemPubDate", item.getPubDate());
                    Log.d("itemLink", item.getLink());
                    Log.d("itemAuthor", item.getAuthor());
                   // Log.d("itemUrl", item.getThumbnailUrl());
                }
            }
        }

    }

    public Document getData(){
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
