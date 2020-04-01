package com.patient.mokhtari.scanner.activities.Bodypart2;

import android.app.Fragment;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.patient.mokhtari.scanner.activities.Frag_Body_part;


/**
 * Created by angelo on 2015/2/15.
 */
public abstract class BodyFragment extends Fragment implements Frag_Body_part.MyTouchListener {
    public ImageView bodyImageView;
    private boolean isMan = true;


    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        (Frag_Body_part.newInstance()).registerTouchListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        (Frag_Body_part.newInstance()).registerTouchListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setShowImage(isMan);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        Toast.makeText(getActivity(), "clickkk", Toast.LENGTH_SHORT).show();
        if(event.getAction() == MotionEvent.ACTION_UP){

        }
    }

    public void setMan(boolean isMan) {
        this.isMan = isMan;
    }

    public abstract void setShowImage(Boolean isMan);

}
