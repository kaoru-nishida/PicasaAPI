package jp.kaoru.picasaapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener{

    private String TAG = "MainActivity";

    private Handler handler= new Handler();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Entry> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);

        // 色設定
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue,
                R.color.green, R.color.red,
                R.color.yellow);
        // Listenerをセット
        mSwipeRefreshLayout.setOnRefreshListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL("http://picasaweb.google.com/data/feed/base/user/kaoru.nish?alt=json&max-results=20");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    int statusCode = urlConnection.getResponseCode();
                    String responseData = "";
                    InputStream stream = urlConnection.getInputStream();
                    StringBuffer sb = new StringBuffer();
                    String line = "";
                    BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    try {
                        stream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    responseData = sb.toString();
                    Log.i(TAG, "すていたすこーど："+String.valueOf(statusCode));
                    if(statusCode == 200) {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            final String Title = json.getJSONObject("feed").getJSONObject("title").getString("$t");
                            JSONArray entries = json.getJSONObject("feed").getJSONArray("entry");
                            final ArrayList<Entry> list = new ArrayList<>();
                            for (int i = 0; i < entries.length(); i++) {
                                JSONObject data = entries.getJSONObject(i);
                                Entry entry = new Entry();
                                entry.setId(i);
                                entry.setContent(data.getJSONObject("media$group").getJSONArray("media$content").getJSONObject(0).getString("url"));
                                entry.setPublished(data.getJSONObject("published").getString("$t"));
                                entry.setThumbnail(data.getJSONObject("media$group").getJSONArray("media$thumbnail").getJSONObject(0).getString("url"));
                                entry.setTitle(data.getJSONObject("title").getString("$t"));
                                entry.setUpdated(data.getJSONObject("updated").getString("$t"));
                                list.add(entry);
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    setTitle(Title);
                                    ListView listView = findViewById(R.id.ListView);
                                    EntryAdapter adapter = new EntryAdapter(MainActivity.this);
                                    adapter.setEntryList(list);
                                    listView.setAdapter(adapter);

                                    ListView listview = findViewById(R.id.ListView);
                                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            try {
                                                com.nispok.snackbar.Snackbar.with(MainActivity.this) // コンテキスト
                                                        .text(list.get(i).getContent()) // 表示するテキスト
                                                        .show(MainActivity.this); // どこに表示するか
                                                Intent intent = new Intent(MainActivity.this, ImageDetailsActivity.class);
                                                intent.putExtra("Content",list.get(i).getContent());
                                                intent.putExtra("Id",list.get(i).getId());
                                                intent.putExtra("Published",list.get(i).getPublished());
                                                intent.putExtra("Thumbnail",list.get(i).getThumbnail());
                                                intent.putExtra("Title",list.get(i).getTitle());
                                                intent.putExtra("Updated",list.get(i).getUpdated());
                                                startActivity(intent);
                                            }catch(Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                // マルチスレッドにしたい処理 ここまで
            }
        }).start();

    }

    @Override
    public void onRefresh() {
        // 更新処理を実装する
        // ここでは単純に2秒後にインジケータ非表示
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL url;
                        HttpURLConnection urlConnection = null;
                        try {
                            url = new URL("http://picasaweb.google.com/data/feed/base/user/kaoru.nish?alt=json&max-results=20");
                            urlConnection = (HttpURLConnection) url.openConnection();
                            InputStream in = urlConnection.getInputStream();
                            int statusCode = urlConnection.getResponseCode();
                            String responseData = "";
                            InputStream stream = urlConnection.getInputStream();
                            StringBuffer sb = new StringBuffer();
                            String line = "";
                            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                            }
                            try {
                                stream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            responseData = sb.toString();
                            Log.i(TAG, "すていたすこーど：" + String.valueOf(statusCode));
                            if (statusCode == 200) {
                                try {
                                    JSONObject json = new JSONObject(responseData);
                                    final String Title = json.getJSONObject("feed").getJSONObject("title").getString("$t");
                                    JSONArray entries = json.getJSONObject("feed").getJSONArray("entry");
                                    final ArrayList<Entry> list = new ArrayList<>();
                                    for (int i = 0; i < entries.length(); i++) {
                                        JSONObject data = entries.getJSONObject(i);
                                        Entry entry = new Entry();
                                        entry.setId(i);
                                        entry.setContent(data.getJSONObject("media$group").getJSONArray("media$content").getJSONObject(0).getString("url"));
                                        entry.setPublished(data.getJSONObject("published").getString("$t"));
                                        entry.setThumbnail(data.getJSONObject("media$group").getJSONArray("media$thumbnail").getJSONObject(0).getString("url"));
                                        entry.setTitle(data.getJSONObject("title").getString("$t"));
                                        entry.setUpdated(data.getJSONObject("updated").getString("$t"));
                                        list.add(entry);
                                    }

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ListView listView = findViewById(R.id.ListView);
                                            EntryAdapter adapter = new EntryAdapter(MainActivity.this);
                                            adapter.setEntryList(list);
                                            adapter.notifyDataSetChanged();
                                            listView.setAdapter(adapter);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (urlConnection != null) {
                                urlConnection.disconnect();
                            }
                        }

                        // マルチスレッドにしたい処理 ここまで
                    }
                }).start();
                // 更新が終了したらインジケータ非表示
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
