package com.layer.messenger.tenor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tenor.android.core.response.BaseError;
import com.tenor.android.core.response.WeakRefCallback;
import com.tenor.android.core.response.impl.TrendingGifResponse;

import java.lang.ref.WeakReference;

public class OnTrendingCallEnqueuedCallback<CTX extends IGifRecyclerView>
        extends WeakRefCallback<CTX, TrendingGifResponse> {

    private final boolean mAppend;

    public OnTrendingCallEnqueuedCallback(@NonNull CTX ctx, boolean append) {
        this(new WeakReference<>(ctx), append);
    }

    public OnTrendingCallEnqueuedCallback(@NonNull WeakReference<CTX> weakRef, boolean append) {
        super(weakRef);
        mAppend = append;
    }

    @Override
    public void success(@NonNull IGifRecyclerView view, @NonNull TrendingGifResponse response) {
        view.onReceiveTrendingSucceeded(response.getResults(), response.getNext(), mAppend);
    }

    @Override
    public void failure(@NonNull IGifRecyclerView view, @Nullable BaseError error) {
        view.onReceiveTrendingFailed(error);
    }
}
