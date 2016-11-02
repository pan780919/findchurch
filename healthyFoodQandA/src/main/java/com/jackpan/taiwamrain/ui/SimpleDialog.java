package com.jackpan.taiwamrain.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;


/**
 * Created by redjack on 15/6/2.
 */
public class SimpleDialog extends DialogFragment {

    static public int BUTTON_INDEX_OK = -1;

    static public String KEY_ARG_TITLE              = "title";
    static public String KEY_ARG_MESSAGE            = "message";
    static public String KEY_ARG_BTN_TITLE_OK       = "okBtnTitle";
    static public String KEY_ARG_BTN_TITLE_CANCEL   = "cancelBtnTitle";


    public OnClickListener okBtnAction;
    public OnClickListener cancelBtnAction;
    public Object userInfo;

    boolean cancelable = false;
    View customView;

    static public void showAlert(FragmentManager fragmentManager, String message)
    {
        SimpleDialog.showAlert(fragmentManager, null, message, null, null, null, null);
    }

    static public void showAlert(FragmentManager fragmentManager, String message, OnClickListener okBtnAction)
    {
        SimpleDialog.showAlert(fragmentManager, null, message, null, null, okBtnAction, null);
    }
    static public void showAlertWitchOk(FragmentManager fragmentManager, String message, String okBtnTitle,OnClickListener okBtnAction)
    {
        SimpleDialog.showAlert(fragmentManager, null, message, okBtnTitle, null, okBtnAction, null);
    }
    static public void showAlert(FragmentManager fragmentManager, String title, String message)
    {
        SimpleDialog.showAlert(fragmentManager, title, message, null, null, null, null);
    }

    static public void showAlert(FragmentManager fragmentManager, String title, String message, OnClickListener okBtnAction)
    {
        SimpleDialog.showAlert(fragmentManager, title, message, null, null, okBtnAction, null);
    }

    static public void showAlertWithCancelBtn(FragmentManager fragmentManager, String message, OnClickListener btnAction)
    {
        SimpleDialog.showAlert(fragmentManager, null, message, null, "", btnAction, btnAction);
    }


    static public void showAlert(FragmentManager fragmentManager,
                                 String title,
                                 String message,
                                 String okBtnTitle,
                                 String cancelBtnTitle,
                                 OnClickListener okBtnAction,
                                 OnClickListener cancelBtnAction)
    {
        if (fragmentManager == null) return;

        SimpleDialog dialog = new SimpleDialog();
        dialog.okBtnAction = okBtnAction;
        dialog.cancelBtnAction = cancelBtnAction;
        dialog.setCancelable(false);

        Bundle data = new Bundle();
        data.putString(KEY_ARG_TITLE, title);
        data.putString(KEY_ARG_MESSAGE, message);
        data.putString(KEY_ARG_BTN_TITLE_OK, okBtnTitle);
        data.putString(KEY_ARG_BTN_TITLE_CANCEL, cancelBtnTitle);

        dialog.setArguments(data);

        fragmentManager.beginTransaction()
                .add(dialog, dialog.getClass().toString())
                .commitAllowingStateLoss();
    }

    /**
     * @param customView If null, will crash.
     */
    static public SimpleDialog showCustomView(FragmentManager fragmentManager, View customView, boolean cancelable)
    {
        if (fragmentManager == null) return null;

        SimpleDialog dialog = new SimpleDialog();
        dialog.cancelable = cancelable;
        dialog.customView = customView;

        fragmentManager.beginTransaction()
                .add(dialog, customView.getClass().toString())
                .commitAllowingStateLoss();

        return dialog;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle data = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (data != null)
        {
            builder.setTitle(data.getString(KEY_ARG_TITLE))
                    .setMessage(data.getString(KEY_ARG_MESSAGE))
                    .setPositiveButton(data.getString(KEY_ARG_BTN_TITLE_OK, getString(android.R.string.ok)), okBtnAction);

            String cancelTitle = data.getString(KEY_ARG_BTN_TITLE_CANCEL);
            if (cancelTitle != null) builder.setNegativeButton("".equals(cancelTitle) ? getString(android.R.string.cancel) : cancelTitle, cancelBtnAction);
        }

        builder.setCancelable(!cancelable);

        if (customView != null) builder.setView(customView);

        return builder.create();
    }
}
