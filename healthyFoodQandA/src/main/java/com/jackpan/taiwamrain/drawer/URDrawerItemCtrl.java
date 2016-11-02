package com.jackpan.taiwamrain.drawer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackpan.findchurch.R;
import com.jackpan.taiwamrain.ui.SimpleViewController;


/**
 * Created by redjack on 15/11/11.
 */
public class URDrawerItemCtrl extends SimpleViewController<URMenuType> {

    TextView titleText;
    ImageView iconImage;


    public URDrawerItemCtrl(Context mContext) {
        super(mContext);
    }

    @Override
    public View onCreateView(Context context) {

        View mainView = inflate(R.layout.ur_i_drawer_menu);
        titleText = (TextView) mainView.findViewById(R.id.ur_i_drawer_menu_text_title);
        iconImage = (ImageView) mainView.findViewById(R.id.ur_i_drawer_menu_img_icon);

        return mainView;
    }

    @Override
    public void onInitial(URMenuType data) {

        titleText.setText(getContext().getText(data.rtitle()));
        iconImage.setImageResource(data.ricon());
    }
}
