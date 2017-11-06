package jp.kaoru.picasaapi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

/**
 * 17/11/06にかおるが作成しました
 */

public class EntryAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Entry> EntryList;

    public EntryAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setEntryList(ArrayList<Entry> tweetList) {
        this.EntryList = tweetList;
    }

    @Override
    public int getCount() {
        return EntryList.size();
    }

    @Override
    public Object getItem(int position) {
        return EntryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return EntryList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.entry_item,parent,false);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.ThumbnailView);
        Glide.with(context).load(EntryList.get(position).getThumbnail())
                .thumbnail((float) 0.8)
                // リスナー（エラーハンドリングをする）
                .listener(
                        new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }
                ).into(imageView);

        ((TextView)convertView.findViewById(R.id.name)).setText(EntryList.get(position).getTitle());
        ((TextView)convertView.findViewById(R.id.DateTextView)).setText(EntryList.get(position).getPublished());

        return convertView;
    }
}
