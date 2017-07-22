package sber.tech.habr;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sber.tech.habr.adapters.HabrItemsAdapter;
import sber.tech.habr.managers.HabrItemsManager;
import sber.tech.habr.models.HabrItem;
import sber.tech.habr.parsers.HabrParser;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvHabrItems;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!initialized) {
            RecyclerView rvHabrItems = (RecyclerView) findViewById(R.id.rvHabrItems);
            mLayoutManager = new LinearLayoutManager(this);
            rvHabrItems.setLayoutManager(mLayoutManager);

            mAdapter = new HabrItemsAdapter(HabrItemsManager.ITEMS);
            rvHabrItems.setAdapter(mAdapter);
            doRefresh();
            initialized = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRefresh:
                doRefresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doRefresh() {
        String URL = "https://habrahabr.ru/rss/hubs/all/";
        new DownloadXmlTask().execute(URL);
    }

    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                return getResources().getString(R.string.connection_error);
            } catch (XmlPullParserException e) {
                return getResources().getString(R.string.xml_error);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            ((HabrItemsAdapter)mAdapter).updateDataset(HabrItemsManager.ITEMS);
            mAdapter.notifyDataSetChanged();
        }
    }

    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;

        try {
            stream = downloadUrl(urlString);
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlPullParserFactory.newPullParser();
            parser.setInput(stream, "UTF-8");
            HabrItemsManager.ITEMS.clear();
            HabrParser.parseRSS(parser);

        } finally {
            if (stream != null) {
                stream.close();
            }
       }

       return urlString;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }

    public void stubUpdate() {
        ((HabrItemsAdapter)mAdapter).updateDataset(HabrItemsManager.ITEMS);
        HabrParser.parseRSS(getResources().getXml(R.xml.stub));
        mAdapter.notifyDataSetChanged();
    }
}


