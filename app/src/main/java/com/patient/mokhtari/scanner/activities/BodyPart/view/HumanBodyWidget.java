package com.patient.mokhtari.scanner.activities.BodyPart.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.patient.mokhtari.scanner.activities.BodyPart.BodyFragment;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.BodyPart.region.RegionPathView;


/**
 * Created by angelo on 2015/2/13.
 */

public class HumanBodyWidget {

    static public final boolean isAPI11 = true;
    public static boolean mShowingBack = false;
    public static boolean isMan = true;
public static ImageView body2;
    private final BodyFrontFragment bodyFrontFragment = new BodyFrontFragment();
    private final BodyBackFragment bodyBackFragment = new BodyBackFragment();
//    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener;

    private AppCompatActivity activity;
    private WaveEffectLayout container;

    private ImageView frontView, backView;
    private AbsoluteLayout leftRegionLayout, rightRegionLayout;

    public HumanBodyWidget(Context context, FrameLayout container, Bundle savedInstanceState) {
        init(context, container, savedInstanceState);
    }

    private void init(Context context, FrameLayout container, Bundle savedInstanceState) {
        this.activity = (AppCompatActivity) context;
        this.container = (WaveEffectLayout) container;
        mShowingBack = false;
        isMan = true;

        initViews();

        if (isAPI11) {
            initFragment(savedInstanceState);

        } else {
            // api < 11
            LayoutInflater inflater = LayoutInflater.from(context);
            frontView = (ImageView) inflater.inflate(R.layout.fragment_body_front, null);
            backView = (ImageView) inflater.inflate(R.layout.fragment_body_back, null);
            this.container.addView(frontView);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            activity.getFragmentManager()
                    .beginTransaction()
                    .add(R.id.body_container, bodyFrontFragment)//container.getId()
                    .commit();

        } else {
            mShowingBack = (activity.getFragmentManager().getBackStackEntryCount() > 0);
        }
        activity.getFragmentManager().addOnBackStackChangedListener(() -> mShowingBack = (activity.getFragmentManager().getBackStackEntryCount() > 0));
    }

    private void initViews() {
        leftRegionLayout = container.findViewById(R.id.left_region_layout);
        rightRegionLayout = container.findViewById(R.id.right_region_layout);
    }


    public boolean flipBody(boolean isShowingBack) {

        if (mShowingBack == isShowingBack)
            return false;

        clearRegionView();
        if (isAPI11) {

            performFragmentFlipAnimation();

        } else {

            container.removeAllViews();
            if (mShowingBack) {
                container.addView(frontView);
            } else {
                container.addView(backView);
            }
            mShowingBack = !mShowingBack;
        }

        return true;

    }

    @SuppressLint("ResourceType")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void performFragmentFlipAnimation() {
        if (mShowingBack) {
            activity.getFragmentManager().popBackStack();
            return;
        }

        activity.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.body_flip_right_in, R.anim.body_flip_right_out,
                        R.anim.body_flip_left_in, R.anim.body_flip_left_out)
                .replace(R.id.body_container, bodyBackFragment)
                .addToBackStack(null)
                .commit();

    }

//    @Override
//    public void onBackStackChanged() {
//        mShowingBack = (activity.getFragmentManager().getBackStackEntryCount() > 0);
//
//    }


    public Boolean toggleBodyGenderImage(Boolean isMan) {
        if (HumanBodyWidget.isMan == isMan)
            return false;

        clearRegionView();
        HumanBodyWidget.isMan = isMan;
        if (isAPI11) {
            if (mShowingBack) {
                bodyFrontFragment.setMan(isMan);
                bodyBackFragment.setShowImage(isMan);
            } else {
                bodyFrontFragment.setShowImage(isMan);
                bodyBackFragment.setMan(isMan);
            }

        } else {
            if (isMan) {
                frontView.setImageResource(R.mipmap.man_front);
                backView.setImageResource(R.mipmap.man_back);
            } else {
                frontView.setImageResource(R.mipmap.woman_front);
                backView.setImageResource(R.mipmap.woman_back);
            }
        }

        return true;
    }

    public static class BodyFrontFragment extends BodyFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_body_front, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            bodyImageView = view.findViewById(R.id.body_front);
            body2=bodyImageView;
        }

        @Override
        public void setShowImage(Boolean isMan) {
            if (bodyImageView == null)
                return;

            setMan(isMan);
            if (isMan) {
                bodyImageView.setImageResource(R.mipmap.man_front);
            } else {
                bodyImageView.setImageResource(R.mipmap.woman_front);
            }
        }
    }

    public static class BodyBackFragment extends BodyFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_body_back, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            bodyImageView = view.findViewById(R.id.body_back);
            body2=bodyImageView;

        }

        @Override
        public void setShowImage(Boolean isMan) {
            if (bodyImageView == null)
                return;

            setMan(isMan);
            if (isMan) {
                bodyImageView.setImageResource(R.mipmap.man_back);
            } else {
                bodyImageView.setImageResource(R.mipmap.woman_back);
            }
        }
    }

    private void clearRegionView() {
        container.setRegionType(-1);
        RegionPathView.pathView.setToClear(true);
        RegionPathView.pathView.invalidate();
        leftRegionLayout.removeAllViews();
        rightRegionLayout.removeAllViews();
    }


}
