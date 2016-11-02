package com.jackpan.taiwamrain.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Will notice through listen when needs to load next page data.
 *
 * Created by redjack on 15/7/13.
 */
public class ListPager<E> extends SimpleItemAdapter {

    static final public int INITIAL_PAGE = 1;

    int initialPage = INITIAL_PAGE;
    int page = 0;
    int reloadCount = 20;
    int nextReloadIndex = 0;

    public boolean isReloadWhenShowTwoThird = false;

    boolean isLoadedFirst = false;
    boolean isLoadAll = false;
    boolean hasSentLoadNotice = false;
    ListPagerListener pageListener;

    public ListPager(Context context, Class itemClass, ListPagerListener pageListener) {
        super(context, itemClass, new ArrayList<E>());

        this.pageListener = pageListener;
        this.initialPage = INITIAL_PAGE;
        this.page = this.initialPage;
    }

    public ListPager(Context context, Class itemClass, int reloadCount, ListPagerListener pageListener) {
        super(context, itemClass, new ArrayList<E>());

        this.initialPage = INITIAL_PAGE;
        this.page = this.initialPage;
        this.reloadCount = reloadCount;
        this.pageListener = pageListener;
    }

    public ListPager(Context context, Class itemClass, int initialPage, int reloadCount, ListPagerListener pageListener) {
        super(context, itemClass, new ArrayList<E>());

        this.initialPage = initialPage;
        this.page = this.initialPage;
        this.reloadCount = reloadCount;
        this.pageListener = pageListener;
    }

    /**
     * Call this method when view is ready, if view is not finished onCreateView, list view won't load.
     */
    public void loadFirstPage()
    {
        if (isLoadedFirst || pageListener == null) return;

        isLoadedFirst = false;
        pageListener.needToLoadNextPage(new ListPagerTask(true, page, reloadCount));
    }

    public void updateList(ArrayList items, boolean needToReload) {

        updateList(items);

        page = initialPage;

        setCheckPoint(items != null ? items.size() : 0, isReloadWhenShowTwoThird);

        if (needToReload) loadFirstPage();
    }

    public void updateList(Object[] items, boolean needToReload) {

        updateList(items);

        page = initialPage;

        setCheckPoint(items != null ? items.length : 0, isReloadWhenShowTwoThird);

        if (needToReload) loadFirstPage();
    }

    private void setCheckPoint(int totalCount, boolean isReloadWhenShowTwoThird)
    {
        if (totalCount <= 0)
        {
            nextReloadIndex = 0;
            return;
        }

        nextReloadIndex = isReloadWhenShowTwoThird ? (int) (totalCount * 0.6) : totalCount - 1;

        if (nextReloadIndex <= 0) nextReloadIndex = 0;
    }

    public boolean isLoadAll(E[] data)
    {
        return isLoadAll(new ArrayList<E>(Arrays.asList(data)));
    }

    public boolean isLoadAll(ArrayList<E> data)
    {
        isLoadAll = data.size() < reloadCount;
        return isLoadAll;
    }

    public boolean isNeedToLoadAtPosition(int position)
    {
        return !isLoadAll && !hasSentLoadNotice && position >= nextReloadIndex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = super.getView(position, convertView, parent);

        if (pageListener != null && isNeedToLoadAtPosition(position))
        {
            pageListener.needToLoadNextPage(new ListPagerTask(false, page + 1, reloadCount));
            hasSentLoadNotice = true;
        }

        return item;
    }

    @Override
    public E getItem(int position) {
        return (E) super.getItem(position);
    }

    public void appendNewData(E[] newItems)
    {
        appendNewData(new ArrayList<E>(Arrays.asList(newItems)));
    }

    public void appendNewData(ArrayList<E> newItems)
    {
        items.addAll(newItems);
        isLoadAll(newItems);
        setCheckPoint(items.size(), isReloadWhenShowTwoThird);

        updateList(items);
    }

    /**
     * Default is 20;
     */
    public void setReloadCount(int reloadCount) {
        this.reloadCount = reloadCount;
    }

    public void setPagerListener(ListPagerListener pagerListener) {
        this.pageListener = pagerListener;
    }

    public void setPagerListener(int reloadCount, ListPagerListener pagerListener) {
        setReloadCount(reloadCount);
        setPagerListener(pagerListener);
    }

    public void clearAll()
    {
        isLoadedFirst = false;
        items.clear();
        updateList(items, false);
    }

    public void clearAllAndReload()
    {
        isLoadedFirst = false;
        items.clear();
        updateList(items, true);
    }

    public ArrayList<E> getAll(){
        return items;
    }






    /**
     * Execute appendNewData if receive new data.
     */
    public class ListPagerTask
    {
        boolean isAskFirstLoad = false;
        int needLoadAtPage;
        int reloadCount;

        public ListPagerTask(boolean isAskFirstLoad, int needLoadAtPage, int reloadCount)
        {
            this.isAskFirstLoad = isAskFirstLoad;
            this.needLoadAtPage = needLoadAtPage;
            this.reloadCount = reloadCount;
        }

        public void newDataArrive(ArrayList<E> data)
        {
            appendNewData(data);

            page = this.needLoadAtPage;
            hasSentLoadNotice = false;

            if (isAskFirstLoad) isLoadedFirst = true;
        }

        public void newDataArrive(E[] data)
        {
            appendNewData(data);

            page = this.needLoadAtPage;
            hasSentLoadNotice = false;
        }

        public void loadNextPage()
        {
            page = this.needLoadAtPage;
            this.needLoadAtPage ++;
            pageListener.needToLoadNextPage(this);
        }

        public ArrayList<E> getAll(){
            return ListPager.this.getAll();
        }

        public void clearAndUpdateNewData(ArrayList<E> data)
        {
            updateList(data, true);
        }

        public void clearAndUpdateNewData(E[] data)
        {
            updateList(data, true);
        }

        public int getPage() {
            return needLoadAtPage;
        }

        public int getReloadCount() {
            return reloadCount;
        }
    }

    public interface ListPagerListener {

        public void needToLoadNextPage(ListPager.ListPagerTask task);
    }

}
