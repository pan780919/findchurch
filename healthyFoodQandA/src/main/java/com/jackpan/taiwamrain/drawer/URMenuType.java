package com.jackpan.taiwamrain.drawer;


import com.jackpan.findchurch.R;

/**
 * Created by redjack on 15/11/12.
 */
public enum URMenuType {

    MEMBER,
    HOME,

    NEWS,

    BONUS_REDEEM,
    BONUS_HISTORY,
    ABOUT,
    TASK;

    static final int[] R_MENU_ICON = {

            R.drawable.sidemenu_member,
            R.drawable.sidemenu_home,

            R.drawable.sidemenu_news,

            R.drawable.sidemenu_bonus,
            R.drawable.sidemenu_record,
            R.drawable.sidemenu_about,
            0
    };

    static final int[] R_MENU_TITLE = {

            R.string.ur_menu_member,
            R.string.ur_menu_home,

            R.string.ur_menu_news,

            R.string.ur_menu_bonus,
            R.string.ur_menu_record,
            R.string.ur_menu_about,
            0
    };



    public int ricon() {
        return R_MENU_ICON[this.ordinal()];
    }

    public int rtitle() {
        return R_MENU_TITLE[this.ordinal()];
    }

    public boolean isOnMenuList() {
        return ricon() != 0;
    }
}
