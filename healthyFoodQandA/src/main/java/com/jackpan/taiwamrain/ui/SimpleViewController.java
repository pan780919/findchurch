package com.jackpan.taiwamrain.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by redjack on 15/6/15.
 */
public abstract class SimpleViewController<D> {

    Context mContext;
    View mMainView;


    //region    =   To Implement    =

    /**
     * Will call when controller is created.
     * @param context
     * @return  View for this controller, often return item template layout, this layout will be used.
     *          You can use easy function 'inflate' at this class to inflate a layout.
     */
    public abstract View onCreateView(Context context);

    /**
     * Will call when adaptor reuse this controller and send new data to initial layout for controller.
     * @param data
     */
    public abstract void onInitial(D data);

    //endregion



    /**
     * Must implement.
     */
    public SimpleViewController(Context mContext) {

        this.mContext = mContext;
        mMainView = onCreateView(mContext);
    }

    public View inflate(int viewId)
    {
        return LayoutInflater.from(mContext).inflate(viewId, null);
    }

    public Context getContext() {

        return mContext;
    }

    public View getView() {

        return mMainView;
    }
}
