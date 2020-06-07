package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.BodyPart.region.RegionView;
import com.patient.mokhtari.scanner.activities.BodyPart.view.HumanBodyWidget;
import com.patient.mokhtari.scanner.activities.BodyPart.view.WaveEffectLayout;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.patient.mokhtari.scanner.activities.Frag_new_request.reqBodyPoints;

public class Frag_Body_part extends myFragment {

    private OnFragmentInteractionListener mListener;
    View rootView;
    @BindView(R.id.t2)
    SegmentTabLayout segmentTabLayout;
@BindView(R.id.btn_clear)
    Button btn_clear;
    public static FragmentActivity fragment_body_part;
    private WaveEffectLayout container;
    private HumanBodyWidget bodyWidget;
    private ImageView manIv, womanIv;
    private TextView manTv, womanTv, flipFrontTv, flipBackTv;
    private String[] mTitles = {"جلو","پشت" };
    private ArrayList<MyTouchListener> mTouchListeners = new ArrayList<>();

    // TODO: Rename and change types and number of parameters
    public static Frag_Body_part newInstance() {
        Frag_Body_part fragment = new Frag_Body_part();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView= inflater.inflate(R.layout.activity_bodypart, container, false);

        super.onCreate(savedInstanceState);
        setFragmentActivity(getActivity());
        fragment_body_part=getActivity();
       getActivity(). setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        View myLayout = rootView.findViewById(R.id.toolbar); // root View id from that link
        ImageView myView = (ImageView) myLayout.findViewById(R.id.imageView3);
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(Frag_duration_list.newInstance());
            }
        });

        initViews(savedInstanceState);


        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    //do something
                    for(MyTouchListener listener : mTouchListeners){
                        listener.onTouchEvent(event);
                    }
                }
                return true;
            }
        });




        return rootView;
    }
    public void initViews(Bundle savedInstanceState){
        ButterKnife.bind(this, rootView);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reqBodyPoints.clear();
               getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, Frag_Body_part.newInstance()).commit();
            }
        });
        container = (WaveEffectLayout) rootView.findViewById(R.id.container);
        manIv = (ImageView) rootView.findViewById(R.id.man_icon);
        manTv = (TextView) rootView.findViewById(R.id.man_text);
        womanIv = (ImageView)rootView. findViewById(R.id.woman_icon);
        womanTv = (TextView) rootView.findViewById(R.id.woman_text);
        flipFrontTv = (TextView)rootView.findViewById(R.id.flipFront);
        flipBackTv = (TextView)rootView. findViewById(R.id.flipBack);
        segmentTabLayout.setTabData(mTitles);
        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position){
                    case 0:
                        if(bodyWidget.flipBody(false)) {
                            flipFrontTv.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                            flipBackTv.setBackgroundColor(Color.TRANSPARENT);
                            flipFrontTv.setTextColor(Color.WHITE);
                            flipBackTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                        }
                        break;
                    case 1:
                        if(bodyWidget.flipBody(true)) {
                            flipFrontTv.setBackgroundColor(Color.TRANSPARENT);
                            flipBackTv.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                            flipFrontTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                            flipBackTv.setTextColor(Color.WHITE);
                        }
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        bodyWidget = new HumanBodyWidget(getActivity(), container, savedInstanceState);
        container.setRegionView(new RegionView(container, getActivity()));
        Toolbar toolbar;
        toolbar=(Toolbar)rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)rootView.findViewById(R.id.txttoolbar);
        txttoolbar.setText("انتخاب محل ضایعه");
        Typeface typeface3 = Typeface.createFromAsset(getActivity().getAssets(), "font/vazirbold.ttf");
        txttoolbar.setTypeface(typeface3, Typeface.BOLD);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).onBackPressed();
            }
        });
        ((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long downTime = SystemClock.uptimeMillis();
                long eventTime = SystemClock.uptimeMillis() + 100;
                float x = 0.50f;
                float y = 0.50f;
// List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
                int metaState = 0;
                MotionEvent motionEvent = MotionEvent.obtain(
                        downTime,
                        eventTime,
                        MotionEvent.ACTION_UP,
                        x,
                        y,
                        metaState
                );

// Dispatch touch event to view
                motionEvent.setAction(0);
                container.dispatchTouchEvent(motionEvent);

                 downTime = SystemClock.uptimeMillis();
                 eventTime = SystemClock.uptimeMillis() + 100;
                 x = 0.50f;
                 y = 0.50f;
// List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
                 metaState = 0;
                 motionEvent = MotionEvent.obtain(
                        downTime,
                        eventTime,
                        MotionEvent.ACTION_MOVE,
                        x,
                        y,
                        metaState
                );

// Dispatch touch event to view
                motionEvent.setAction(0);
                container.dispatchTouchEvent(motionEvent);

                downTime = SystemClock.uptimeMillis();
                eventTime = SystemClock.uptimeMillis() + 100;
                x = 0.50f;
                y = 0.50f;
// List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
                metaState = 0;
                motionEvent = MotionEvent.obtain(
                        downTime,
                        eventTime,
                        MotionEvent.ACTION_UP,
                        x,
                        y,
                        metaState
                );
       motionEvent.setAction(0);
// Dispatch touch event to view
                container.dispatchTouchEvent(motionEvent);
            }
        }, 1000);



    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            reqBodyPoints.clear();
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void registerTouchListener(MyTouchListener listener){
        mTouchListeners.add(listener);
    }

    public void unRegisterTouchListener(MyTouchListener listener){
        mTouchListeners.remove(listener);
    }
    public interface MyTouchListener
    {
        void onTouchEvent(MotionEvent event);
    }
    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
