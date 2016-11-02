package com.jackpan.taiwamrain.about;

import android.os.Bundle;
import android.widget.TextView;


import com.jackpan.findchurch.R;
import com.jackpan.taiwamrain.drawer.DrawerActivity;
import com.jackpan.taiwamrain.drawer.URMenuType;


public class PrivacyActivity extends DrawerActivity {
    private TextView mPrivacytextvivew;

    public URMenuType onCurrentMenuType() {
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        initLayout();

    }

    private void initLayout() {
//        setTitleName(getResources().getString(R.string.index_privacy));
        setBackBtn(true,null);
        mPrivacytextvivew = (TextView) findViewById(R.id.privacytextvivew);
    }




}
