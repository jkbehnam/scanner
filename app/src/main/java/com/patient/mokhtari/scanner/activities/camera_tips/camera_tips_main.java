package com.patient.mokhtari.scanner.activities.camera_tips;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class camera_tips_main extends myFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private ViewPager viewPager;
    ViewPagerAdapter vp;
    private View indicator1;
    private View indicator2;
    private View indicator3;
    private View indicator4;
    @BindView(R.id.btntips)
    CardView btntips;

    // TODO: Rename and change types and number of parameters
    public static camera_tips_main newInstance() {
        camera_tips_main fragment = new camera_tips_main();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_tips_layout, container, false);
        ButterKnife.bind(this, rootView);


        //setToolbar_notmain(rootView, "نکات عکس برداری");

        indicator1 = rootView.findViewById(R.id.indicator1);
        indicator2 = rootView.findViewById(R.id.indicator2);
        indicator3 = rootView.findViewById(R.id.indicator3);
        indicator4 = rootView.findViewById(R.id.indicator4);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        vp = new ViewPagerAdapter(((AppCompatActivity) getActivity()).getSupportFragmentManager());
        viewPager.setAdapter(vp);
        viewPager.addOnPageChangeListener(new camera_tips_main.WizardPageChangeListener());

        updateIndicators(0);
        btntips.setOnClickListener(this);


        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView, "نکات عکس برداری");

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getActivity(), "Backarrow pressed", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private class WizardPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int position) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            updateIndicators(position);
        }
    }

    public void updateIndicators(int position) {
        switch (position) {
            case 0:
                indicator1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot));
                indicator2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                indicator3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                indicator4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                break;
            case 1:
                indicator1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                indicator2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot));
                indicator3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                indicator4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                break;
            case 2:
                indicator1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                indicator2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                indicator3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot));
                indicator4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                break;
            case 3:
                indicator1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                indicator2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                indicator3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_grey));
                indicator4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot));
                break;
        }
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private int WIZARD_PAGES_COUNT = 4;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new camera_tips_fragment(position);
        }

        @Override
        public int getCount() {
            return WIZARD_PAGES_COUNT;
        }

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
        //  if (context instanceof OnFragmentInteractionListener) {
        //      mListener = (OnFragmentInteractionListener) context;
        //   } else {
        //     throw new RuntimeException(context.toString()
        //              + " must implement OnFragmentInteractionListener");
        //   }
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
        switch (view.getId()) {
            case R.id.btntips:

                int x = viewPager.getCurrentItem();
                if (x == vp.getCount() - 1) {
                    ((AppCompatActivity) getActivity()).onBackPressed();

                } else {
                    viewPager.setCurrentItem(++x);
                }

                break;
        }
    }
}
