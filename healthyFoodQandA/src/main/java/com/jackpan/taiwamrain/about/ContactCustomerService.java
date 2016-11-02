package com.jackpan.taiwamrain.about;

import android.os.Bundle;

import com.jackpan.findchurch.R;
import com.jackpan.taiwamrain.drawer.DrawerActivity;
import com.jackpan.taiwamrain.drawer.URMenuType;


public class ContactCustomerService extends DrawerActivity {


    @Override
    public URMenuType onCurrentMenuType() {
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_contact_customer_service);
        initLayout();
    }

    private void initLayout() {
        setTitleName(getResources().getString(R.string.index_contactcustomerservice));
        setBackBtn(true,null);
    }


}
