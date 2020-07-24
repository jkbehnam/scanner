package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.patient.mokhtari.scanner.R;

import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Objects.ReqQuestions;
import com.patient.mokhtari.scanner.activities.helper.PrefManager;
import com.patient.mokhtari.scanner.activities.questioner.CardItem;
import com.patient.mokhtari.scanner.activities.questioner.CardPagerAdapter;
import com.patient.mokhtari.scanner.activities.questioner.ShadowTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.patient.mokhtari.scanner.activities.Frag_new_request.reqQuestionsArrayList;


public class Frag_Question_list extends myFragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private OnFragmentInteractionListener mListener;


    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;
    @BindView(R.id.tv_queston_title)
    TextView tv_queston_title;
    @BindView(R.id.tv_qnumber)
    TextView tv_qnumber;

public  static final String[] ql={"آیا محل ضایعه بیمار همراه با درد است؟ ",
        "آیا محل ضایعه بیمار همراه با خارش است؟ ",
        "آیا بیمار تب دارد؟ ",
        "آیا بیمار در حال حاضر داروی خاصی مصرف می کند؟ در صورت استفاده از دارو نام آن را وارد کنید.",
        "آیا بیماری خاصی مانند( ایدز- هپاتیت - دیابت - سل) دارید. در صورت داشتن بیماری، نام بیماری خود را وارد کنید ",
        "آیا بیمار حامله است؟ ",
        "آیا در خانواده بیمار کسی شرایط مشابه بیمار را داشته است؟",
        "اولین منطقه ای که ضایعه ظهور پیدا کرد کدام قسمت بدن بود؟ ",
        "آیا ضایعه قابل لمس (برجستگی، خشکی یا زبری) است؟ ",
        "در این قسمت اگر تاریخچه پزشکی خاصی (دوره درمانی قبلی، بیماری های مشابه) برای بیمار وجود دارد ذکر کنید. "
        };

    // TODO: Rename and change types and number of parameters
    public static Frag_Question_list newInstance() {

        return new Frag_Question_list();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_question, container, false);
        ButterKnife.bind(this, rootView);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView, "پاسخ به سوالات");
Fragment fragment=this;
        mViewPager = rootView.findViewById(R.id.viewPager);

        Typeface typeface3 = Typeface.createFromAsset(getActivity().getAssets(), "font/iran_sans.ttf");
        tv_queston_title.setTypeface(typeface3, Typeface.BOLD);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1,0,true));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_2,1,true));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_3,2,true));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_4,3,true));
        mCardAdapter.addCardItem(new CardItem(R.string.title_5, R.string.text_5,4,true));

        PrefManager pm = new PrefManager(this.getActivity());

        String gender = pm.getUserDetails().get("gender");
       if( !gender.equals("مرد"))
        mCardAdapter.addCardItem(new CardItem(R.string.title_6, R.string.text_6,5,true));

        mCardAdapter.addCardItem(new CardItem(R.string.title_7, R.string.text_7,6,true));
        mCardAdapter.addCardItem(new CardItem(R.string.title_8, R.string.text_8,7,false));
        mCardAdapter.addCardItem(new CardItem(R.string.title_9, R.string.text_9,8,true));
        mCardAdapter.addCardItem(new CardItem(R.string.title_10, R.string.text_10,9,true));


        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        //     mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_qnumber.setText((position + 1) +"/"+ mCardAdapter.getCount());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mCardAdapter.setOnCardClickListner((view, position, reqQuestions) -> {
            mViewPager.setCurrentItem(position + 1, true);

            boolean dup=false;
            for (ReqQuestions req : reqQuestionsArrayList
            ) {
                if (req.getQ_id() == reqQuestions.getQ_id()) {
                    dup=true;
                    reqQuestionsArrayList.set(reqQuestionsArrayList.indexOf(req),reqQuestions);
                    Log.d("behnamq", reqQuestionsArrayList.toString());
                }

            }
            if(!dup){
                reqQuestionsArrayList.add(reqQuestions);
                Log.d("behnamq", reqQuestionsArrayList.toString());
            }
            if(position==mCardAdapter.getCount()-1){
                getActivity().onBackPressed();                }
            mViewPager.setCurrentItem(position + 1, true);
        });


        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
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

    @Override
    public void onClick(View view) {
        if (!mShowingFragments) {

            //   mViewPager.setAdapter(mFragmentCardAdapter);
            mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        } else {

            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
        }

        mShowingFragments = !mShowingFragments;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }
}
