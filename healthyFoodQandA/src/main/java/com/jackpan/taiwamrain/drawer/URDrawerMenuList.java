package com.jackpan.taiwamrain.drawer;

import java.util.ArrayList;

/**
 * Created by redjack on 15/11/12.
 */
public class URDrawerMenuList extends ArrayList<URMenuType> {

    public static URDrawerMenuList listWithout(URMenuType type)
    {
        URDrawerMenuList list = new URDrawerMenuList();
        if (type != null) list.remove(type);

        return list;
    }

    public URDrawerMenuList() {

        for (URMenuType menu : URMenuType.values())
        {
            if (menu.isOnMenuList()) add(menu);
        }
    }
}
