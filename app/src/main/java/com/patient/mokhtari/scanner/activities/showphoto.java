package com.patient.mokhtari.scanner.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.patient.mokhtari.scanner.R;

import butterknife.ButterKnife;


public class showphoto {
    Context context;
    Dialog dialog;
    private PhotoView mPhotoView;
    static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %% ID: %d";
    static final String SCALE_TOAST_STRING = "Scaled to: %.2ff";
    static final String FLING_LOG_STRING = "Fling velocityX: %.2f, velocityY: %.2f";
    private Toast mCurrentToast;

    private Matrix mCurrentDisplayMatrix = null;

    @SuppressLint("ResourceAsColor")
    public Dialog init_dialog(Context context, String url) {
        this.context = context;
        dialog = new Dialog(context, android.R.style.Theme_Holo_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_simple_sample);
       // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, dialog);

        //  et_answer = dialog.findViewById(R.id.txt_pin_entry);
        Toolbar toolbar = dialog.findViewById(R.id.toolbar);
        toolbar.setTitle("تصویر عارضه");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
            }
        });
        mPhotoView = dialog.findViewById(R.id.iv_photo);

/*
//        Drawable bitmap = ContextCompat.getDrawable(context, R.drawable.doc_face);
//        mPhotoView.setImageDrawable(bitmap);
        mPhotoView.setImageURI(Uri.parse(url));
        // Lets attach some listeners, not required though!
        mPhotoView.setMaximumScale(500);
        mPhotoView.setOnPhotoTapListener(new PhotoTapListener());
        mPhotoView.setOnSingleFlingListener(new SingleFlingListener());
*/


        Glide.with(context)
                .asBitmap().load(url)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                  //Toast.makeText(context,getResources().getString(R.string.unexpected_error_occurred_try_again),Toast.LENGTH_SHORT).show();
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {

                                  //  Drawable bitmap = ContextCompat.getDrawable(context, R.drawable.doc_face);
                                  mPhotoView.setImageBitmap(bitmap);
                                  //    mPhotoView.setImageURI(Uri.parse(url));
                                  // Lets attach some listeners, not required though!
                                  mPhotoView.setMaximumScale(500);
                                  mPhotoView.setOnPhotoTapListener(new PhotoTapListener());
                                  mPhotoView.setOnSingleFlingListener(new SingleFlingListener());
                                  return false;
                              }
                          }
                ).submit();


        return dialog;
    }

    public void show() {
        dialog.show();
    }

    private void showToast(CharSequence text) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }

        mCurrentToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    private class SingleFlingListener implements OnSingleFlingListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("PhotoView", String.format(FLING_LOG_STRING, velocityX, velocityY));
            return true;
        }
    }

    private class PhotoTapListener implements OnPhotoTapListener {

        @Override
        public void onPhotoTap(ImageView view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;

            showToast(String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage, view == null ? 0 : view.getId()));
        }
    }
}
