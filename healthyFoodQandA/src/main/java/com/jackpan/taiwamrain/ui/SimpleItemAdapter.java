package com.jackpan.taiwamrain.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Have inconvenient place is that the class passed inside must add to pro-guard list, so that adapter can instance right class.
 *
 * Created by redjack on 15/6/5.
 *
 * D = Data type.
 * C = Controller type.
 */
public class SimpleItemAdapter<D, C extends SimpleViewController> extends BaseAdapter {

    Context context;
    Handler updateHandler = new Handler(Looper.getMainLooper());
    protected ArrayList<C> controllers = new ArrayList<>();
    protected ArrayList<D> items;
    Class<C> itemClass;

    public SimpleItemAdapter(Context context, Class<C> itemClass) {

        this(context, itemClass, new ArrayList<D>());
    }

    public SimpleItemAdapter(Context context, Class<C> itemClass, D[] items) {

        this(context, itemClass, items != null ? new ArrayList<>(Arrays.asList(items)) : new ArrayList<D>(0));
    }

    public SimpleItemAdapter(Context context, Class<C> itemClass, ArrayList<D> items) {

        this.context = context;
        this.items = items;
        this.itemClass = itemClass;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public D getItem(int position) {
        return items != null ? items.get(position) : null;
    }

    public C getController(int position) {
        return controllers != null ? controllers.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        C ctrl = null;

        if (convertView == null)
        {
            ctrl = (C) objectFromClass(itemClass, context);
            controllers.add(ctrl);
        }
        else {
            ctrl = getController(position % controllers.size());  //reuse
        }

        D item = getItem(position);
        ctrl.onInitial(item);

        return ctrl.getView();
    }


    public void updateList(D[] items)
    {
        updateList(new ArrayList<D>(Arrays.asList(items)));
    }

    public void updateList(ArrayList<D> items)
    {
        this.items = items;

        updateHandler.post(new Runnable() {
            @Override
            public void run() {

                notifyDataSetChanged();
            }
        });
    }

    public void removeItem(D item)
    {
        items.remove(item);
        updateList(items);
    }

    public ArrayList<D> getItems() {

        return items;
    }

    static public Object objectFromClass(Class cls, Context context)
    {
        if (cls == null) return null;

        Object obj = null;

        try
        {
            Constructor<Object> constructor = cls.getConstructor(Context.class);
            obj = constructor.newInstance(context);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
