package com.jackpan.taiwamrain.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.Calendar;

/**
 * Created by redjack on 15/6/18.
 */
public class SimplePicker extends DialogFragment implements AlertDialog.OnClickListener {

    PickerType type;

    TimePickerDialog.OnTimeSetListener timeSetListener;
    DatePickerDialog.OnDateSetListener dateSetListener;
    DialogInterface.OnClickListener onClickListener;

    CustomViewReactListener reactListener;

    String title;
    String[] options;

    View customView;


    static public void showTimePicker(FragmentManager fragmentManager, String title, TimePickerDialog.OnTimeSetListener timeSetListener)
    {
        SimplePicker picker = new SimplePicker();
        picker.title = title;
        picker.type = PickerType.Time;
        picker.timeSetListener = timeSetListener;

        picker.show(fragmentManager, SimplePicker.class.toString());
    }

    static public void showDatePicker(FragmentManager fragmentManager, String title, DatePickerDialog.OnDateSetListener dateSetListener)
    {
        SimplePicker picker = new SimplePicker();
        picker.title = title;
        picker.type = PickerType.Date;
        picker.dateSetListener = dateSetListener;

        picker.show(fragmentManager, SimplePicker.class.toString());
    }

    static public void showOptionPicker(FragmentManager fragmentManager, String title, String[] options, DialogInterface.OnClickListener onClickListener)
    {
        SimplePicker picker = new SimplePicker();
        picker.title = title;
        picker.options = options;
        picker.type = PickerType.Options;
        picker.onClickListener = onClickListener;

        picker.show(fragmentManager, SimplePicker.class.toString());
    }

    /**
     *  Will have cancel and confirm button.
     */
    static public void showCustomPicker(FragmentManager fragmentManager, String title, View customView, CustomViewReactListener listener)
    {
        SimplePicker picker = new SimplePicker();
        picker.title = title;
        picker.type = PickerType.Custom;
        picker.customView = customView;
        picker.reactListener = listener;

        picker.show(fragmentManager, SimplePicker.class.toString());
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = null;

        switch (type)
        {
            case Date:      dialog = datePicker(title, dateSetListener); break;
            case Time:      dialog = timePicker(title, timeSetListener); break;
            case Options:   dialog = optionDialog(title, options, onClickListener); break;
            case Custom:    dialog = customDialog(title, customView);
        }

        return dialog;
    }

    public DatePickerDialog datePicker(String title, DatePickerDialog.OnDateSetListener dateSetListener)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog picker = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        picker.setTitle(title);

        return picker;
    }

    public TimePickerDialog timePicker(String title, TimePickerDialog.OnTimeSetListener timeSetListener)
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog picker = new TimePickerDialog(getActivity(), timeSetListener, hour, minute, true);
        picker.setTitle(title);

        return picker;
    }

    public AlertDialog optionDialog(String title, String[] options, DialogInterface.OnClickListener resultListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(options, resultListener);
        builder.setTitle(title);

        return builder.create();
    }

    public AlertDialog customDialog(String title, View customView)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(customView);
        builder.setTitle(title);
        builder.setPositiveButton(getString(android.R.string.ok), this);
        builder.setNegativeButton(getString(android.R.string.cancel), this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (reactListener == null) return;

        if (which == -2)        reactListener.onDialogCancel(customView);
        else if (which == -1)   reactListener.onDialogConfirm(customView);
    }

    public interface CustomViewReactListener
    {
        public void onDialogCancel(View customView);
        public void onDialogConfirm(View customView);
    }


    public enum PickerType
    {
        Time, Date, Options, Custom;
    }
}
