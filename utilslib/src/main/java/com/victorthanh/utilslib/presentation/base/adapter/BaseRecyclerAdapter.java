package com.victorthanh.utilslib.presentation.base.adapter;


import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.victorthanh.utilslib.presentation.base.event.OnAdapterListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {

    //region Properties
    private List<T> objectList = new ArrayList<>();
    private Activity activity;
    private OnAdapterListener listener;
    public int prePosChoose = -1;

    public T getPreItemSelect() {
        if (prePosChoose >= 0 && prePosChoose < objectList.size())
            return objectList.get(prePosChoose);
        return null;
    }

    //endregion

    //region Constructor
    public BaseRecyclerAdapter(Activity activity, OnAdapterListener listener) {
        super();
        this.activity = activity;
        this.listener = listener;
    }
    //endregion

    //region Getter - Setter
    public void setOnAdapterListener(OnAdapterListener listener) {
        this.listener = listener;
    }

    public OnAdapterListener getListener() {
        return listener;
    }
    //endregion

    //region Abstract methods
    protected abstract BaseViewHolder createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(T item, int position, BaseViewHolder baseViewHolder);

    public BaseRecyclerAdapter(Activity activity) {
        this(activity, null);
    }
    //endregion

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return createView(activity, viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        bindView(getItem(position), position, holder);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public T getItem(int index) {
        return ((objectList != null && index >= 0 && index < objectList.size()) ? objectList.get(index) : null);
    }

    public Context getActivity() {
        return activity;
    }

    public void setList(List<T> list) {
        objectList = list;
    }

    public List<T> getList() {
        return objectList;
    }

    //region BaseViewHolder
    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mMapView;

        public BaseViewHolder(View view) {
            super(view);
            mMapView = new SparseArray<>();
            mMapView.put(0, view);
        }

        public void initViewList(int[] idList) {
            for (int id : idList)
                initViewById(id);
        }

        public void initViewById(int id) {
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                mMapView.put(id, view);
        }

        public View getView() {
            return getView(0);
        }

        public View getView(int id) {
            if (mMapView.indexOfKey(id) < 0)
                return mMapView.get(id);
            else
                initViewById(id);

            return mMapView.get(id);
        }
    }
    //endregion
}
