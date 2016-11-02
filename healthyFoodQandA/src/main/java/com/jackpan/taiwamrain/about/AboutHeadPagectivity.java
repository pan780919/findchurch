package com.jackpan.taiwamrain.about;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jackpan.findchurch.R;
import com.jackpan.taiwamrain.drawer.DrawerActivity;
import com.jackpan.taiwamrain.drawer.URMenuType;


public class AboutHeadPagectivity extends DrawerActivity implements View.OnClickListener {
    private LinearLayout mContactcustomerservice_layout,privacyLayout,eulaLayout;

    @Override
    public URMenuType onCurrentMenuType() {
        return URMenuType.HOME;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_head_pagectivity);
        intitLayout();

    }




    private void intitLayout() {
//        setTitleName(getResources().getString(R.string.index_about));
//        setBackBtn(true,null);
        setMenuBtn(true,null);
        mContactcustomerservice_layout = (LinearLayout) findViewById(R.id.contactcustomerservice_layout);
        findViewById(R.id.contactcustomerservice_layout).setOnClickListener(this);
        privacyLayout = (LinearLayout) findViewById(R.id.privacyLayout);
        findViewById(R.id.privacyLayout).setOnClickListener(this);
       eulaLayout = (LinearLayout) findViewById(R.id.eulaLayout);
        findViewById(R.id.eulaLayout ).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.contactcustomerservice_layout :
                Intent i = new Intent();
                i.setClass(this,ContactCustomerService.class);
                startActivity(i);
            break;
            case R.id.privacyLayout:
                Intent privacy = new Intent();
                privacy.setClass(this,PrivacyActivity.class);
                startActivity(privacy);
                break;
            case  R.id.eulaLayout:
                Intent eula = new Intent();
                eula.setClass(this,EulaActivity.class);
                startActivity(eula);
                break;

        }
    }
}
