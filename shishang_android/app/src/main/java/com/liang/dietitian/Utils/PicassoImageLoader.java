package com.liang.dietitian.Utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.liang.dietitian.R;
import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, Uri aUri, ImageView imageView, int width, int height) {
        Picasso.get()//
                .load(aUri)//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .resize(width, height)//
                .centerInside()//
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}
