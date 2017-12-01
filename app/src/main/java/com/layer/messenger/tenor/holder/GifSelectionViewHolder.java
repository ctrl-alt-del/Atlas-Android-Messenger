package com.layer.messenger.tenor.holder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.layer.atlas.R;
import com.layer.atlas.tenor.adapter.OnDismissPopupWindowListener;
import com.layer.atlas.tenor.messagetype.threepartgif.GifSender;
import com.layer.atlas.tenor.messagetype.threepartgif.ThreePartGifUtils;
import com.layer.atlas.tenor.model.IMinimalResult;
import com.tenor.android.core.constant.MediaCollectionFormat;
import com.tenor.android.core.loader.GlideTaskParams;
import com.tenor.android.core.loader.gif.GifLoader;
import com.tenor.android.core.model.impl.Media;
import com.tenor.android.core.model.impl.MediaCollection;
import com.tenor.android.core.model.impl.Result;
import com.tenor.android.core.util.AbstractListUtils;
import com.tenor.android.core.view.IBaseView;
import com.tenor.android.core.widget.viewholder.StaggeredGridLayoutItemViewHolder;

import java.util.List;

public class GifSelectionViewHolder<CTX extends IBaseView> extends StaggeredGridLayoutItemViewHolder<CTX> {

    private final ImageView mImageView;

    private MinimalResult mMinimalResult;
    private GifSender mGifSender;
    private OnDismissPopupWindowListener mListener;

    public GifSelectionViewHolder(View itemView, CTX context,
                                  @Nullable final GifSender gifSender,
                                  @Nullable final OnDismissPopupWindowListener listener) {
        super(itemView, context);
        mGifSender = gifSender;
        mListener = listener;
        mImageView = (ImageView) itemView.findViewById(R.id.tgi_iv_item);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGifSender == null || !hasRef()) {
                    return;
                }
                mGifSender.send(mMinimalResult);

                if (mListener != null) {
                    mListener.dismiss();
                }
            }
        });
    }

    public void setImage(@Nullable Result result) {
        if (result == null) {
            return;
        }

        final int placeholderColor = Color.parseColor(result.getPlaceholderColorHex());
        mMinimalResult = new MinimalResult(result);
        // normal load to display
        List<MediaCollection> mediaCollections = result.getMedias();
        if (AbstractListUtils.isEmpty(mediaCollections)) {
            return;
        }

//            final float density = AbstractUIUtils.getScreenDensity(getActivity());
//            payload.setWidth(Math.round(media.getWidth() * density));
//            payload.setHeight(Math.round(media.getHeight() * density));
        final GlideTaskParams<ImageView> payload = new GlideTaskParams<>(mImageView, mMinimalResult.getPreviewUrl());
        payload.setPlaceholder(placeholderColor);
        GifLoader.loadGif(getContext(), payload);
    }

    private static class MinimalResult implements IMinimalResult {

        private final Result mResult;

        public MinimalResult(Result result) {
            mResult = result;
        }

        @NonNull
        @Override
        public String getId() {
            return mResult.getId();
        }

        @NonNull
        @Override
        public String getUrl() {
            return mResult.getUrl();
        }

        @NonNull
        @Override
        public String getPreviewUrl() {
            return mResult.getMedias().get(0).get(MediaCollectionFormat.GIF_TINY).getUrl();
        }

        @Override
        public int getWidth() {
            return mResult.getMedias().get(0).get(MediaCollectionFormat.GIF_TINY).getWidth();
        }

        @Override
        public int getHeight() {
            return mResult.getMedias().get(0).get(MediaCollectionFormat.GIF_TINY).getHeight();
        }
    }
}
