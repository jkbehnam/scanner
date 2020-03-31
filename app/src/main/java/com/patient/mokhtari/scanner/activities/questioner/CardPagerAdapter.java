package com.patient.mokhtari.scanner.activities.questioner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Objects.ReqQuestions;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {
    CardPagerAdapter.OnCardClickListner onCardClickListner;
    Boolean YNQ=false;
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position, ReqQuestions reqQuestions);
    }

    public void setOnCardClickListner(CardPagerAdapter.OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.buttonContainer);
        final ExpandableRelativeLayout expandableLayout
                = (ExpandableRelativeLayout) view.findViewById(R.id.what_is_ezterab_expandableLayout1);
        expandableLayout.setInterpolator(new LinearInterpolator());
        expandableLayout.collapse();
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        CardView cardYes = (CardView) view.findViewById(R.id.btnyes);
        CardView cardNo = (CardView) view.findViewById(R.id.btnno);
        CardView cardNext = (CardView) view.findViewById(R.id.btn_next);
        EditText et_more_desc = (EditText) view.findViewById(R.id.et_more_desc);


        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YNQ=false;
                //  onCardClickListner.OnCardClicked(v, position);
                expandableLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });
        cardYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YNQ=true;
                //  onCardClickListner.OnCardClicked(v, position);
                expandableLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });
        cardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClickListner.OnCardClicked(view, position, new ReqQuestions(position, YNQ, et_more_desc.getText().toString()));
            }
        });

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {

        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        contentTextView.setText(item.getText());
    }

}
