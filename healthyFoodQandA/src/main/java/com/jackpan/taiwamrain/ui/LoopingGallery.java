package com.jackpan.taiwamrain.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by redjack on 15/9/16.
 *
 * If has 4 items, will create 4 + 2 page:
 *
 * Index:       0   1   2   3   4   5
 * Item index:  3   0   1   2   3   0
 *
 * first page is to show the last item, last page is to show first item,
 * so that when user scroll to first page, will has enough time to switch the page last item belong that is page 4.
 *
 */
public class LoopingGallery extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private Context mContext;
    private ViewPager mViewPager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ArrayList<Bitmap> mImages = new ArrayList<>(0);
    private int mPosition = 1;

    private long mLoopingInterval = 7000;

    public LoopingGallery(Context context, ViewPager viewPager)
    {
        this.mContext = context;
        this.mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);

        viewPager.setCurrentItem(mPosition);
    }

    public LoopingGallery(Context mContext, ViewPager viewPager, ArrayList<Bitmap> images) {

        this(mContext, viewPager);
        this.mImages = images;
    }

    //region    =   Control =

    public void setmImages(ArrayList<Bitmap> mImages)
    {
        this.mImages = mImages;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                mViewPager.setCurrentItem(1);
            }
        });
    }

    /**
     * If has images, then will start lopping, if not won't.
     * @param second Minimum is 1 second
     */
    public void startLooping(int second)
    {
        if (mImages.size() == 0) return;
        if (second > 1) mLoopingInterval = second * 1000;

        mHandler.postDelayed(loopingTask, mLoopingInterval);
    }

    public void stopLooping()
    {
        mHandler.removeCallbacks(loopingTask);
    }

    private Runnable loopingTask = new Runnable() {
        @Override
        public void run() {

            if (mHandler != null)
            {
                mViewPager.setCurrentItem(mPosition + 1);
                mHandler.postDelayed(loopingTask, mLoopingInterval);
            }
        }
    };

    //endregion

    //region    =   On Page Change  =
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

        this.mPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if (state != ViewPager.SCROLL_STATE_IDLE) return;

        int count = mImages.size();
        if      (mPosition == 0)         mViewPager.setCurrentItem(count, false);
        else if (mPosition == count + 1) mViewPager.setCurrentItem(1, false);
    }
    //endregion


    //region    =   Adapter =
    @Override
    public int getCount() {
        return mImages.size() > 0 ? mImages.size() + 2 : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Bitmap img = getImage(position);
        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(img);

        container.addView(imageView);

        return imageView;
    }

    public Bitmap getImage(int position)
    {
        int count = mImages.size();

        if      (position == 0)         return mImages.get(count - 1);
        else if (position == count + 1) return mImages.get(0);
        else                            return mImages.get(position - 1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((ImageView) object);
    }
    //endregion
}
