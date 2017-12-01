package com.layer.messenger.tenor;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.layer.atlas.R;
import com.layer.atlas.tenor.adapter.OnDismissPopupWindowListener;
import com.layer.atlas.tenor.messagetype.threepartgif.GifSender;
import com.layer.messenger.tenor.holder.GifSelectionViewHolder;
import com.layer.messenger.tenor.rvitem.ResultRVItem;
import com.tenor.android.core.model.impl.Result;
import com.tenor.android.core.util.AbstractListUtils;
import com.tenor.android.core.view.IBaseView;
import com.tenor.android.core.widget.adapter.AbstractRVItem;
import com.tenor.android.core.widget.adapter.ListRVAdapter;
import com.tenor.android.core.widget.viewholder.StaggeredGridLayoutItemViewHolder;

import java.util.List;
import java.util.Map;

public class GifAdapter<CTX extends IBaseView> extends ListRVAdapter<CTX, AbstractRVItem, StaggeredGridLayoutItemViewHolder<CTX>> {

    private static int ITEM_HEIGHT;
    public final static int TYPE_LOADING = -1;
    public final static int TYPE_GIF = 0;
    private Map<String, Integer> mWidths;
    private GifSender mGifSender;
    private OnDismissPopupWindowListener mListener;

    private static final AbstractRVItem ITEM_LOADING = new AbstractRVItem(TYPE_LOADING){};

    public GifAdapter(CTX context) {
        super(context);
        mWidths = new ArrayMap<>();
        if (hasContext()) {
            ITEM_HEIGHT = (int) getContext().getResources().getDimension(R.dimen.tenor_gif_adapter_height);
        }
    }

    @Nullable
    public OnDismissPopupWindowListener getDismissPopupWindowListener() {
        return mListener;
    }

    public void setDismissPopupWindowListener(@Nullable OnDismissPopupWindowListener listener) {
        mListener = listener;
    }

    @Override
    public StaggeredGridLayoutItemViewHolder<CTX> onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            default:
                view = inflater.inflate(R.layout.tenor_item_gif, null);
                return new GifSelectionViewHolder<>(view, getRef(), mGifSender, mListener);
        }
    }

    public void setGifSender(GifSender gifSender) {
        mGifSender = gifSender;
    }

    @Override
    public void onBindViewHolder(final StaggeredGridLayoutItemViewHolder<CTX> viewHolder, int position) {

        if (viewHolder instanceof GifSelectionViewHolder) {
            final GifSelectionViewHolder holder = (GifSelectionViewHolder) viewHolder;

            if (getList().get(position).getType() != TYPE_GIF) {
                return;
            }

            final ResultRVItem resultRVItem = (ResultRVItem) getList().get(position);

            holder.setImage(resultRVItem.getResult());
            if (mWidths.containsKey(resultRVItem.getId())) {
                holder.setParams(mWidths.get(resultRVItem.getId()), ITEM_HEIGHT);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getList().get(position).getType();
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    @Override
    public void insert(@Nullable List<AbstractRVItem> list, boolean isAppend) {
        if (AbstractListUtils.isEmpty(list)) {
            notifyDataSetChanged();
            return;
        }

        if (!isAppend) {
            getList().clear();
            mWidths.clear();
        }

        getList().addAll(list);
        for (AbstractRVItem item : list) {
            cacheItemWidth(item);
        }

        if (!isAppend) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(getItemCount(), list.size());
        }
    }

    protected boolean cacheItemWidth(AbstractRVItem item) {
        if (item == null || item.getType() != TYPE_GIF) {
            return false;
        }

        final Result result = ((ResultRVItem) item).getResult();
        if (!mWidths.containsKey(result.getId())) {
            mWidths.put(result.getId(), (int) (ITEM_HEIGHT * result.getAspectRatio()));
        }
        return false;
    }

    public void notifyOnGifLoading() {
        clearList();
        getList().add(ITEM_LOADING);
        notifyDataSetChanged();
    }
}
