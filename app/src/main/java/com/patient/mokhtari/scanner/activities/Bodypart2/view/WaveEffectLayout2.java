package com.patient.mokhtari.scanner.activities.Bodypart2.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.BodyPart.view.BodyPointMain;
import com.patient.mokhtari.scanner.activities.Bodypart2.UIUtil;
import com.patient.mokhtari.scanner.activities.Bodypart2.region.Region;
import com.patient.mokhtari.scanner.activities.Bodypart2.region.RegionParam;
import com.patient.mokhtari.scanner.activities.Bodypart2.region.RegionPathView;
import com.patient.mokhtari.scanner.activities.Bodypart2.region.RegionView;

import java.util.ArrayList;
import java.util.Map;

import static com.patient.mokhtari.scanner.activities.BodyPart.view.WaveEffectLayout.location_copy3;
import static com.patient.mokhtari.scanner.activities.Bodypart2.view.HumanBodyWidget.body3;
import static com.patient.mokhtari.scanner.activities.Frag_request_details.reqBodyPoints2;

/**
 * Created by angelo on 2015/2/15.
 */
public class WaveEffectLayout2 extends FrameLayout implements Runnable {

    private static final String TAG = "DxWaveEffectLayout";
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mTargetWidth;

    private static final int mMaxRevealRadius = 24; //40
    private static final int mRevealRadiusGap = 3; //5
    private int mRevealRadius = 0;
    private final int[] mLocationInScreen = new int[2];

    private boolean mShouldDoAnimation = false;
    private boolean mIsPressed = false;
    private String mTag;

    private View mTouchTarget;
    private final DispatchUpTouchEventRunnable mDispatchUpTouchEventRunnable = new DispatchUpTouchEventRunnable();

    ArrayList<BodyPoint> bodyPoints=new ArrayList<>();

    private RegionView regionView;
    private RegionPathView regionPathView;
    private ImageView bodyImageView;
    private int regionType = -1;

    private static int mHeadY, mHandX1, mHandX2, mChestY, mWaistY, mBackHeadY, mUpperPartY, mMiddlePartY;

    private static int bodyImageViewHeight = 0;

    public WaveEffectLayout2(Context context) {
        super(context);
        init();
    }

    public WaveEffectLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public WaveEffectLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint.setColor(getResources().getColor(R.color.red));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.getLocationOnScreen(mLocationInScreen);

        int regionW = (int) getResources().getDimension(R.dimen.region_width);
        RegionParam.OFFSET_Y =  regionW + UIUtil.dip2px(RegionParam.STANDARD_OFFSET_Y) ;
        RegionParam.LEFT_REGION_X = UIUtil.dip2px(RegionParam.REGION_WIDTH) / 2 + this.getPaddingLeft();
        RegionParam.RIGHT_REGION_X = this.getWidth() - RegionParam.LEFT_REGION_X - UIUtil.dip2px(20f); // besure 10f

        regionPathView = new RegionPathView(this);

    }

    private void initParametersForChild(MotionEvent event, View view) {
        float mCenterX = event.getX();
        float mCenterY = event.getY();
        mTargetWidth = view.getMeasuredWidth();
        int mTargetHeight = view.getMeasuredHeight();
        mRevealRadius = 0;
        mShouldDoAnimation = true;
        mIsPressed = true;
        mTag = (String) view.getTag();

    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mShouldDoAnimation || mTargetWidth <= 0 || mTouchTarget == null || !"root".equals(mTag)) {
            ImageView imageView = body3;
            if (imageView != null) {


                // bodyImageView = getBodyImageView();
                mRevealRadius = 15;
                this.getLocationOnScreen(mLocationInScreen);

                int[] location2 = new int[2];
                imageView.getLocationOnScreen(location2);
                if (location2[1] < location2[0]) {
                    location2=location_copy3;
                } else {
                    location_copy3 = location2;
                }


                if (location2[0] != 0 && location2[1] != 0) {
                    int left = location2[0] - mLocationInScreen[0];
                    int top = location2[1] - mLocationInScreen[1];
                    int right = left + imageView.getMeasuredWidth();
                    int bottom = top + imageView.getMeasuredHeight();


                    for (BodyPointMain f : reqBodyPoints2
                    ) {
                        if (HumanBodyWidget.mShowingBack == f.mShowingBack)
                            canvas.drawCircle(left + (right - left) * f.fx, top + (bottom - top) * f.fy, mRevealRadius, mPaint);
                    }
                }

            }
            return;
        }
        bodyImageView = getBodyImageView();

        refresh(regionType);
        mRevealRadius = 15;
        this.getLocationOnScreen(mLocationInScreen);

        int[] location2 = new int[2];
        bodyImageView.getLocationOnScreen(location2);
        int left = location2[0] - mLocationInScreen[0];
        int top = location2[1] - mLocationInScreen[1];
        int right = left + bodyImageView.getMeasuredWidth();
        int bottom = top + bodyImageView.getMeasuredHeight();
        int width = this.getWidth();
        int height = this.getHeight();

        for (BodyPointMain f : reqBodyPoints2
        ) {
            if (HumanBodyWidget.mShowingBack == f.mShowingBack)
            canvas.drawCircle(left+(right-left)*f.fx, top+(bottom-top)*f.fy, mRevealRadius, mPaint);
        }


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        int INVALIDATE_DURATION = 40;
        if (action == MotionEvent.ACTION_DOWN) {

            View touchTarget = getTouchTarget(this, x, y);

            String tag = (String) touchTarget.getTag();
            if(!"root".equals(tag)) { //"region".equals(tag)
           //     touchTarget.performClick();
         //       return super.dispatchTouchEvent(event);
            }
          //  bodyImageView = getBodyImageView();


          //  refresh(regionType);

            if (touchTarget != null && touchTarget.isClickable() && touchTarget.isEnabled()) {
                mTouchTarget = touchTarget;
                initParametersForChild(event, touchTarget);
                postInvalidateDelayed(INVALIDATE_DURATION);
            }
        } else if (action == MotionEvent.ACTION_UP) {
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
            mDispatchUpTouchEventRunnable.event = event;
            postDelayed(mDispatchUpTouchEventRunnable, 40);
            return false;
        } else if (action == MotionEvent.ACTION_CANCEL) {
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
        }

        return super.dispatchTouchEvent(event);
    }

    private View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> touchableViews = view.getTouchables();

        touchableViews.remove(view);
        if(touchableViews.size() > 2){
            for (View child : touchableViews) {
                if(!"root".equals(child.getTag())) {
                    if (isTouchPointInView(child, x, y)) {
                        target = child;
                        break;
                    }
                }
            }
        }

        if(target == null){
            for (View child : touchableViews) {
                if (isTouchPointInView(child, x, y)) {
                    target = child;
                    break;
                }
            }
        }
        if(target == null)
            target = view;

        return target;
    }

    private boolean isTouchPointInView(View view, int x, int y) {


        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return view.isClickable() && y >= top && y <= bottom
                && x >= left && x <= right;
    }

    @Override
    public boolean performClick() {
        postDelayed(this, 400);
        return true;
    }

    @Override
    public void run() {
        super.performClick();
    }

    private class DispatchUpTouchEventRunnable implements Runnable {
        public MotionEvent event;

        @Override
        public void run() {
            if (mTouchTarget == null || !mTouchTarget.isEnabled()) {
                return;
            }

            if (isTouchPointInView(mTouchTarget, (int)event.getRawX(), (int)event.getRawY())) {
                mTouchTarget.performClick();
            }
        }
    }

    private void initParametersForRegion() {

        if(bodyImageView == null)
            return;

        if(bodyImageViewHeight != bodyImageView.getHeight()) {

            bodyImageViewHeight = bodyImageView.getHeight();
            int paddingTop = this.getPaddingTop();

            mHeadY = (219 + RegionParam.standardOffsetY) * bodyImageViewHeight / RegionParam.standardHeight + mLocationInScreen[1] + paddingTop;
            mHandX1 = 200 * bodyImageView.getWidth() / RegionParam.standardWidth + bodyImageView.getLeft() + mLocationInScreen[0];
            mHandX2 = (RegionParam.standardWidth - 180) * bodyImageView.getWidth() / RegionParam.standardWidth  + bodyImageView.getLeft() + mLocationInScreen[0];
            mChestY = (219 + RegionParam.standardOffsetY + 232) * bodyImageViewHeight / RegionParam.standardHeight + mLocationInScreen[1] + paddingTop;
            mWaistY = (219 + RegionParam.standardOffsetY + 232 + 247) * bodyImageViewHeight / RegionParam.standardHeight + mLocationInScreen[1] + paddingTop;

            mBackHeadY = (221 + RegionParam.standardOffsetY) * bodyImageViewHeight / RegionParam.standardHeight + mLocationInScreen[1] + paddingTop;
            mUpperPartY = (221 + RegionParam.standardOffsetY + 364) * bodyImageViewHeight / RegionParam.standardHeight + mLocationInScreen[1] + paddingTop;
            mMiddlePartY = (221 + RegionParam.standardOffsetY + 364 + 187) * bodyImageViewHeight / RegionParam.standardHeight + mLocationInScreen[1] + paddingTop;

            initParametersForRegionLocation();

        }
    }

    private void initParametersForRegionLocation(){
        int middleAlignmentX = this.getWidth() / 2 - this.getPaddingLeft(); //+ mLocationInScreen[0]

        for(Map.Entry<Integer, Region[]> item : RegionParam.regionItems.entrySet()){
            Region[] regions = item.getValue();
            for (Region region : regions) {
                initLocationForRegion(region, middleAlignmentX);
            }
        }

        initLocationForRegion(Region.SKIN, middleAlignmentX);

    }

    private void initLocationForRegion(Region region, int middleAlignmentX){
        if(Region.LayoutSide.LEFT == region.getLayoutSide())
            region.setStartX(middleAlignmentX - (region.getOffsetSX() * bodyImageViewHeight / RegionParam.standardHeight));
        else
            region.setStartX(middleAlignmentX + (region.getOffsetSX() * bodyImageViewHeight / RegionParam.standardHeight));

        region.setStartY(region.getOffsetSY() * bodyImageViewHeight / RegionParam.standardHeight + RegionParam.standardOffsetY);
        region.setDestinationY( region.getOffsetDY() * bodyImageViewHeight / RegionParam.standardHeight + (RegionParam.OFFSET_Y * region.getOffSetNum()) );

    }

    private int touchPointInRegion(int x, int y){
        initParametersForRegion();

        if(HumanBodyWidget.mShowingBack){
            if(x < mHandX1 || x > mHandX2)
                return RegionParam.REGION_BACK_UPPER_PART;
            else if (y < mBackHeadY)
                return RegionParam.REGION_BACK_HEAD;
            else if(y < mUpperPartY)
                return RegionParam.REGION_BACK_UPPER_PART;
            else if(y < mMiddlePartY)
                return RegionParam.REGION_BACK_MIDDLE_PART;
            else
                return RegionParam.REGION_BACK_LOWER_PART;

        }else {
            if(x < mHandX1 || x > mHandX2)
                return RegionParam.REGION_FRONT_HAND;
            else if (y < mHeadY)
                return RegionParam.REGION_FRONT_HEAD;
            else if(y < mChestY)
                return RegionParam.REGION_FRONT_CHEST;
            else if(y < mWaistY)
                return RegionParam.REGION_FRONT_WAIST;
            else
                return RegionParam.REGION_FRONT_LEG;
        }

    }

    private boolean isTouchPointInTransparent(int x, int y){
        if(bodyImageView == null) {
            return true;
        }
        int rootLeft = mLocationInScreen[0];
        int rootTop = mLocationInScreen[1];
        int imageLeft = bodyImageView.getLeft();
        int imageTop = bodyImageView.getTop();
        int imageHeight = bodyImageView.getHeight();
        int imageWidth = bodyImageView.getWidth();

        if (imageWidth == 0 || imageHeight == 0) {
            return true;
        }

        Drawable drawable = bodyImageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();

        int locationInBitmapX = (x - rootLeft - imageLeft - this.getPaddingLeft()) * intrinsicWidth / imageWidth;
        int locationInBitmapY = (y - rootTop - imageTop - this.getPaddingTop()) * intrinsicHeight / imageHeight;

        try {
            int pixel = bitmap.getPixel(locationInBitmapX, locationInBitmapY);
            if (Color.TRANSPARENT == pixel)
                return true;
        }catch (IllegalArgumentException e){
            return true;
        }
        return false;
    }

    private ImageView getBodyImageView(){
        ImageView imageView;
        if (HumanBodyWidget.mShowingBack)
            imageView = this.findViewById(R.id.body_back);
        else
            imageView = this.findViewById(R.id.body_front);

        return imageView;
    }

    private void refresh(int regionType){
        regionPathView.setAdapter(regionType);
        regionView.setAdapter(regionType);
    }

    public void setRegionView(RegionView regionView) {
        this.regionView = regionView;
    }

    public void setRegionType(int regionType) {
        this.regionType = regionType;
    }
}
