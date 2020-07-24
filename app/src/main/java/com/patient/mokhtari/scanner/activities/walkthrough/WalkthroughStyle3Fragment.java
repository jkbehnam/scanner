package com.patient.mokhtari.scanner.activities.walkthrough;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.patient.mokhtari.scanner.R;


/**
 * Created by wahyu on 15/11/16.
 */

@SuppressLint("ValidFragment")
public class WalkthroughStyle3Fragment extends Fragment {
    final int wizard_page_position;

    public WalkthroughStyle3Fragment(int position) {
        this.wizard_page_position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout_id = R.layout.walkthrough3_fragment;
        View view = inflater.inflate(layout_id, container, false);
        ImageView img = view.findViewById(R.id.imagePage1);
        int img_id=0;
      //  String url = BuildConfig.IMAGE_URL + "walkthrough/style-3/Welcome-3-circle.png";
switch (wizard_page_position){

    case 0:

        img_id= getImage("skin_desise1");
        break;
    case 1:
        img_id=  getImage("skin_");
        break;
    case 2:
        img_id=  getImage("skin_desise1");
        break;
    case 3:
        img_id=  getImage("skin_desise1");
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

        return getActivity().getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
    }
}
