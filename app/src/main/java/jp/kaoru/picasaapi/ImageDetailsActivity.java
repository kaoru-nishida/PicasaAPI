package jp.kaoru.picasaapi;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class ImageDetailsActivity extends AppCompatActivity {

    private String TAG = "ImageDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        if(this.getIntent() == null){
            finish();
        }else {
            ImageView imageView = findViewById(R.id.imageDetailsView);
            Glide.with(this).load(this.getIntent().getStringExtra("Content"))
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_error_black_48dp) // エラー画像
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem sync = menu.add("更新");
        MenuItem details = menu.add("詳細");
        MenuItem share = menu.add("共有");
        MenuItem save = menu.add("保存");
        MenuItem copy_link = menu.add("共有");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.i("onOptionsItemSelected", item.getTitle().toString());
        return super.onOptionsItemSelected(item);
    }

}
