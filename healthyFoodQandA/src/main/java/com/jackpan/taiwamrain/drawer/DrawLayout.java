package com.jackpan.taiwamrain.drawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackpan.findchurch.R;


/**
 * Created by HYXEN20141227 on 2015/7/8.
 */
public class DrawLayout  extends LinearLayout implements View.OnClickListener{
private    ImageView loginimg ;
private TextView loginNameText;


    public DrawLayout(Context context) {
        super(context);
        init(context);
    }

    public DrawLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public DrawLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(Context context){
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.lt_draw_layout, this, true);
        findViewById(R.id.drawer_btn_login).setOnClickListener(this);
        findViewById(R.id.drawer_btn_list).setOnClickListener(this);
        findViewById(R.id.drawer_btn_home).setOnClickListener(this);
        findViewById(R.id.drawer_btn_whos).setOnClickListener(this);
        findViewById(R.id.drawer_btn_oil).setOnClickListener(this);
        findViewById(R.id.drawer_btn_task).setOnClickListener(this);
        findViewById(R.id.drawer_btn_group).setOnClickListener(this);
        findViewById(R.id.drawer_btn_record).setOnClickListener(this);
        findViewById(R.id.drawer_btn_about).setOnClickListener(this);
        loginimg = (ImageView) findViewById( R.id.imag_icon);
        loginNameText = (TextView) findViewById(R.id.logintex);

        checkLogout();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.drawer_btn_login:
//                gotoActivity(MemberWebActivity.class);
                break;
            case R.id.drawer_btn_list:




                break;
            case R.id.drawer_btn_home:


                break;
            case R.id.drawer_btn_whos:




                break;
            case R.id.drawer_btn_oil:




                break;
            case R.id.drawer_btn_task:



                break;
            case R.id.drawer_btn_group:




                break;
            case R.id.drawer_btn_record:


                break;
            case R.id.drawer_btn_about:

                break;

        }
    }


    private void gotoActivity(Class<?> className) {
        Intent intent = new Intent(getContext(), className);
        getContext().startActivity(intent);
        ((Activity)getContext()).finish();
    }



    public void checkLogout() {

//        if(TaskSharedPreferences.isLogin(getContext())) {
//            String base64 = ProfileType.getPhotoBase64(getContext());
//            String name =  ProfileType.getReceiveName(getContext());
//            Log.e("name",name);
//            if (!base64.equals("")) {
//                Bitmap b = GtApi.base64ToBitmap(base64);
//                loginimg.setImageBitmap(b);
//
//            }
//            if(!name.equals(""))
//            {
//             loginNameText.setText(name);
//
//            }
//        }

    }

    public void logout() {
//        TaskSharedPreferences.takeAPIToken(getContext(), "");
//        TaskSharedPreferences.saveFbToken(getContext(), "");
//        checkFacebookState();
//        checkSmartIdState();
        checkLogout();

    }
public void exit(){
    Activity activity = ((Activity)getContext());
    activity.finish();

}





}
