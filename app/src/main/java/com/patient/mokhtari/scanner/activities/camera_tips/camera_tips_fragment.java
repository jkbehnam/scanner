package com.patient.mokhtari.scanner.activities.camera_tips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.patient.mokhtari.scanner.R;


/**
 * Created by wahyu on 15/11/16.
 */

@SuppressLint("ValidFragment")
public class camera_tips_fragment extends Fragment {
    int wizard_page_position;

    public camera_tips_fragment(int position) {
        this.wizard_page_position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout_id = R.layout.camera_tips_fragment;
        View view = inflater.inflate(layout_id, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.imagePage1);
        TextView tv = (TextView) view.findViewById(R.id.textPage1);
        int img_id=0;
      //  String url = BuildConfig.IMAGE_URL + "walkthrough/style-3/Welcome-3-circle.png";
switch (wizard_page_position){
/*
    case 0:
        tv.setText("عکس برداری از زاویه بالا");
        img_id= getImage("assets_images_img_tip_angle");
        break;
    case 1:
        tv.setText("پس زمینه ساده باشد");
        img_id=  getImage("assets_images_img_tip_background");
        break;
    case 2:
        tv.setText("رعایت فاصله مناسب");
        img_id=  getImage("assets_images_img_tip_distance");
        break;
    case 3:
        tv.setText("لبه ی عارضه مشخص باشد");
        img_id=  getImage("assets_images_img_tip_edge");
        break;
        */
    case 0:
        tv.setText("تصویر تار نباشد");
        img_id= getImage("tar");
        break;
    case 1:
        tv.setText("رعایت فاصله مناسب");
        img_id=  getImage("fasele");
        break;
    case 2:
        tv.setText("");
        img_id=  getImage("");
        break;
    case 3:
        tv.setText("");
        img_id=  getImage("");
        break;
}
        Glide.with(getActivity()).load(img_id).into(img);
    //    loadImageRequest(img, "https://moviemag.ir/cache/d4f76de61b5f614c3502dcc2f6763d75_w250_h250_cp.jpg");

        return view;
    }

    private void loadImageRequest(ImageView img, String url) {


        Glide.with(getActivity()).load(url).into(img);
    }
    public int getImage(String imageName) {

        int drawableResourceId = getActivity().getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());

        return drawableResourceId;
    }
}
