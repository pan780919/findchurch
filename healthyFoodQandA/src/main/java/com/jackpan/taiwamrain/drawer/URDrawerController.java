package com.jackpan.taiwamrain.drawer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jackpan.findchurch.R;
import com.jackpan.taiwamrain.MainActivity;
import com.jackpan.taiwamrain.TwoActivity;
import com.jackpan.taiwamrain.about.AboutHeadPagectivity;
import com.jackpan.taiwamrain.ui.SimpleItemAdapter;


/**
 * Created by redjack on 15/11/11.
 */
public class URDrawerController implements AdapterView.OnItemClickListener {

    private Activity activity;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    private SimpleItemAdapter<URMenuType, URDrawerItemCtrl> menuAdapter;

    private DrawerMenuEventListener eventListener;

    private URMenuType currentMenuType;

    public static final int R_DRAWER_LAYOUT = R.id.drawer_layout;
    public static final int R_DRAWER_LISTVIEW = R.id.drawer_listview;



    public URDrawerController(Activity activity, URMenuType currentMenuType)
    {
        this.activity = activity;
        this.currentMenuType = currentMenuType;
        View mainView = activity.findViewById(android.R.id.content);

        drawerLayout = (DrawerLayout) mainView.findViewById(R_DRAWER_LAYOUT);
        drawerList = (ListView) mainView.findViewById(R_DRAWER_LISTVIEW);

        URDrawerMenuList menuList = new URDrawerMenuList();
//        menuList.remove(URMenuType.MEMBER);
//        menuList.remove(URMenuType.NEWS);
//        menuList.remove(URMenuType.ABOUT);

        menuAdapter = new SimpleItemAdapter(activity, URDrawerItemCtrl.class, menuList);
        drawerList.setAdapter(menuAdapter);
        drawerList.setOnItemClickListener(this);
        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, 0, 0);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        URMenuType type = menuAdapter.getItem(position);

        if (eventListener != null) eventListener.onMenuClick(type);

        if (currentMenuType == type)
        {
            close();
            return;
        }

        switch (type)
        {
            case  MEMBER:       activity.startActivity(new Intent(activity, TwoActivity.class));break;
            case HOME:
                Intent i = new Intent(activity, AboutHeadPagectivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(i);
                break;

            case NEWS:          activity.startActivity(new Intent(activity, MainActivity.class)); break;

            case BONUS_REDEEM:  activity.startActivity(new Intent(activity, MainActivity.class)); break;
            case BONUS_HISTORY: activity.startActivity(new Intent(activity, MainActivity.class)); break;
            case ABOUT:activity.startActivity(new Intent(activity, AboutHeadPagectivity.class));break;
        }

        if (currentMenuType != URMenuType.HOME) activity.finish();
    }

    //region    =   Control =

    public void open()
    {
        drawerLayout.openDrawer(drawerList);
        drawerList.setFocusable(false);
    }

    public void close()
    {
        drawerLayout.closeDrawers();

    }

    public void toggleDrawer()
    {
        if (drawerLayout.isDrawerOpen(drawerList)) close();
        else                                       open();
    }

    public void withoutMenu(URMenuType type)
    {
        menuAdapter.removeItem(type);
    }

    public void setSwipeGuesture(boolean isOn)
    {
        drawerLayout.setDrawerLockMode(isOn ? DrawerLayout.LOCK_MODE_LOCKED_OPEN : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


    //endregion


    //region    =   Event Config  =

    public void onResume()
    {
        drawerLayout.closeDrawers();
    }

    public void onPostCreate()
    {
        drawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {

        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        return drawerToggle.onOptionsItemSelected(item);
    }

    //endregions


    public void setEventListener(DrawerMenuEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public interface DrawerMenuEventListener {
        void onMenuClick(URMenuType menuType);
    }
}
