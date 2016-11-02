package com.jackpan.taiwamrain.drawer;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jackpan.findchurch.R;


/**
 * Created by HYXEN20141227 on 2015/7/8.
 */
public abstract class DrawerActivity extends AppCompatActivity {

    int requestCount = 0;
    ProgressDialog loadingDialog;
    protected URTitleViewCtrl titleViewCtrl;
    protected URDrawerController drawer;

    /**
     * @return null if don't want drawer, return type for this activity.
     */
    public abstract URMenuType onCurrentMenuType();


    @Override
    protected void onResume() {
        super.onResume();

        if (drawer != null) drawer.onResume();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        configActionBar();

        URMenuType type = onCurrentMenuType();
        if (type != null) drawer = new URDrawerController(this, type);
        else
        {
            DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (dl != null) dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void configActionBar()
    {
        titleViewCtrl = new URTitleViewCtrl(this);

        View titleView = titleViewCtrl.getTitleView();

        ActionBar mToolbar = getSupportActionBar();
        mToolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bk_orange)));

        mToolbar.setDisplayShowTitleEnabled(false);
        mToolbar.setDisplayShowHomeEnabled(false);
        mToolbar.setDisplayHomeAsUpEnabled(false);
        mToolbar.setHomeButtonEnabled(false);

        mToolbar.setDisplayShowCustomEnabled(true);
        mToolbar.setCustomView(titleView);

        Toolbar parent = (Toolbar) titleView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

    public void setBackBtn(boolean show, View.OnClickListener listener)
    {
        titleViewCtrl.setLeftNaviBtn(show, R.drawable.ur_s_btn_back, listener != null ? listener : new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    public void setMenuBtn(boolean show, View.OnClickListener listener) {
        titleViewCtrl.setLeftNaviBtn(show, R.drawable.ur_s_btn_mainlist, listener != null ? listener : new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.toggleDrawer();
            }
        });
    }
    //首頁搜尋功能
    public  void  setSerchBtn(boolean show , View.OnClickListener listener){
//            titleViewCtrl.setRightNaviBtn();




    }

    public void setTitleName(String title){

        titleViewCtrl.setTitle(title);
    }

    public void setImageTitle(int imgRes)
    {
        titleViewCtrl.setImageTitle(imgRes);
    }

    public void addOnRightNaviBar(View view) {

        titleViewCtrl.addViewOnRight(view);
    }

    private View.OnClickListener onBackBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawer != null) drawer.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (drawer != null) drawer.onPostCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (drawer != null) drawer.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (drawer != null) drawer.close();
    }

    //region    =   Loading Dialog  =

//    public void showLoading()
//    {
//        requestCount++;
//        if (loadingDialog == null)  loadingDialog = ProgressDialog.show(this, null, getString(R.string.ur_gen_alert_loading));
//        else                        loadingDialog.show();
//    }

    public void dismissLoading()
    {
        if (--requestCount <= 0 && loadingDialog != null)
        {
            loadingDialog.dismiss();
            requestCount = 0;
        }
    }

    //endregion
}
