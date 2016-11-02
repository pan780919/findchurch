package com.jackpan.taiwamrain.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackpan.findchurch.R;


/**
 * Created by redjack on 15/11/13.
 */
public class URTitleViewCtrl {

    Context context;

    ViewGroup mainView;

    ViewGroup centerContainer;
    ViewGroup rightContainer;
    ViewGroup titleView;

    TextView titleText;
    TextView subtitleText;

    ImageButton leftNaviBtn;


    public URTitleViewCtrl(Context context)
    {
        this.context = context;

        mainView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.ur_title_view, null);

        centerContainer = (ViewGroup) mainView.findViewById(R.id.ur_title_layout_center);
        rightContainer = (ViewGroup) mainView.findViewById(R.id.ur_title_layout_right);

        titleView = (ViewGroup) mainView.findViewById(R.id.ur_title_layout_title);
        titleText = (TextView) mainView.findViewById(R.id.ur_title_layout_text_title);
        subtitleText = (TextView) mainView.findViewById(R.id.ur_title_layout_text_subtitle);

        leftNaviBtn = (ImageButton) mainView.findViewById(R.id.ur_title_view_btn_left_navi);
        leftNaviBtn.setVisibility(View.INVISIBLE);
    }

    public void setLeftNaviBtn(boolean show, int imgRes, View.OnClickListener listener)
    {
        leftNaviBtn.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        leftNaviBtn.setOnClickListener(listener);

        if (show) leftNaviBtn.setImageResource(imgRes);
    }
    public void setRightNaviBtn(boolean show, int imgRes, View.OnClickListener listener)
    {
        leftNaviBtn.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        leftNaviBtn.setOnClickListener(listener);

        if (show) leftNaviBtn.setImageResource(imgRes);
    }
    public void addViewOnRight(View view)
    {
        if (view != null) rightContainer.addView(view);
    }

    public void removeViewOnRight(View view)
    {
        rightContainer.removeView(view);
    }

    public void setTitle(String title){

        titleText.setText(title);
    }

    public void setSubtitle(String subtitle) {

        subtitleText.setText(subtitle);
    }

    public void setImageTitle(int imgRes)
    {
        ImageView img = new ImageView(context);
        img.setImageResource(imgRes);

        setHeadView(img);
    }

    public void setHeadView(View headView)
    {
        titleView.setVisibility(View.GONE);
        centerContainer.addView(headView);
    }

    public ViewGroup getTitleView() {
        return mainView;
    }
}
