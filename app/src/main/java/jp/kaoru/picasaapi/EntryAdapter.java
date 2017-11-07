package jp.kaoru.picasaapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

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

    //内部ストレージから、画像ファイルを読み込む(Android 用)
    private static Bitmap loadBitmapLocalStorage(String fileName, Context context)
            throws IOException, FileNotFoundException {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(context.openFileInput(fileName));
            return BitmapFactory.decodeStream(bis);
        } finally {
            try {
                bis.close();
            } catch (Exception e) {
                //IOException, NullPointerException
            }
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.entry_item,parent,false);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.ThumbnailView);
        Glide.with(context).load(EntryList.get(position).getThumbnail())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_error_black_48dp) // エラー画像
                        .override(300, 300) // リサイズ（縦横の最大サイズを指定して、収める）
                        .placeholder(R.drawable.ic_cached_black_48dp) // ローディング画像
                        .dontAnimate() // placeholderを設定した場合に必要 これを書かないとplaceholder画像と同じ大きさにリサイズされる
                        .timeout(5000) //タイムアウト
                )
                // リスナー（ハンドリング）
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
