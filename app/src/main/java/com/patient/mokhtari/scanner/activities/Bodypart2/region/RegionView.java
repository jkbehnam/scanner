package com.patient.mokhtari.scanner.activities.Bodypart2.region;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Bodypart2.view.WaveEffectLayout2;
import com.patient.mokhtari.scanner.activities.Frag_Body_part;
import com.patient.mokhtari.scanner.activities.Frag_duration_list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo on 2015/2/26.
 */
public class RegionView {

    public static final String REGION_TYPE = "regionType";
    public static final String REGION_ID = "regionID";
    private final WaveEffectLayout2 container;
    private final Context mContext;
    private AbsoluteLayout leftRegionLayout, rightRegionLayout;
    private LayoutInflater layoutInflater;
    private Region[] regions;
    private List<View> regionViews = new ArrayList<>();

    public RegionView(WaveEffectLayout2 container, Context context) {
        this.container = container;
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        leftRegionLayout = (AbsoluteLayout) container.findViewById(R.id.left_region_layout);
        rightRegionLayout = (AbsoluteLayout) container.findViewById(R.id.right_region_layout);

    }

    public void setAdapter(int regionType) {
        regionViews.clear();
        if (-1 == regionType) {
            if (leftRegionLayout != null) {
                leftRegionLayout.removeAllViews();
            }
            if (rightRegionLayout != null) {
                rightRegionLayout.removeAllViews();
            }
            return;
        }

        regions = RegionParam.regionItems.get(regionType);
        for (Region region : regions) {
            regionViews.add(getItem(region));
        }

        regionViews.add(getItem(Region.SKIN));
        refresh();
    }

    private View getItem(final Region region) {

        View itemView = layoutInflater.inflate(R.layout.region_item, null);
        final TextView textView = (TextView) itemView.findViewById(R.id.text_view);
        textView.setText(region.getName());
        itemView.setTag(String.valueOf(region.getValue()));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"انتخاب شما: "+ region.getName(), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //    android.support.v4.app.FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                        // android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        //  fragmentTransaction.remove(fragment);
                        //  fragmentTransaction.commit();

                      //  Intent i=new Intent(mContext, Mainskin.class);
                      //  mContext.startActivity(i);

                        FragmentTransaction transaction = (Frag_Body_part.fragment_body_part).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, Frag_duration_list.newInstance());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }, 800);
              //  Toast.makeText(mContext, "You click " + region.getName(), Toast.LENGTH_SHORT);
            }
        });
        return itemView;
    }

    public void refresh() {

        if (leftRegionLayout == null || rightRegionLayout == null)
            return;

        leftRegionLayout.removeAllViews();
        rightRegionLayout.removeAllViews();
        int size = regionViews.size() - 1;
        if (size > 0) {
            int columnSize = size / 2 + size % 2;

            for (int i = 0; i < columnSize; i++) {

                leftRegionLayout.addView(regionViews.get(i), new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0, regions[i].getDestinationY()));
            }

            for (int i = columnSize; i < size; i++) {

                rightRegionLayout.addView(regionViews.get(i), new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0, regions[i].getDestinationY()));
            }

            // add skin region
        //    rightRegionLayout.addView(getItem(Region.SKIN), new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
           //         ViewGroup.LayoutParams.WRAP_CONTENT, 0, regions[size - 1].getDestinationY() + Region.SKIN.getDestinationY()));//RegionParam.OFFSET_Y
        }
    }

}

