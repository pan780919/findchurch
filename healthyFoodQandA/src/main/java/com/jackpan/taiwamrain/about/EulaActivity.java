package com.jackpan.taiwamrain.about;

import android.os.Bundle;
import android.widget.TextView;

import com.jackpan.findchurch.R;
import com.jackpan.taiwamrain.drawer.DrawerActivity;
import com.jackpan.taiwamrain.drawer.URMenuType;


public class EulaActivity extends DrawerActivity {
    private TextView mEulatextvivew;


    @Override
    public URMenuType onCurrentMenuType() {
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eula);

        initLayout();

    }

    private void initLayout() {
        setTitleName(getResources().getString(R.string.index_eula));
        setBackBtn(true,null);
        mEulatextvivew = (TextView) findViewById(R.id.eulatextvivew);
    }




}
