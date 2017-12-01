package com.layer.messenger.tenor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tenor.android.core.response.BaseError;
import com.tenor.android.core.response.WeakRefCallback;
import com.tenor.android.core.response.impl.GifsResponse;

import java.lang.ref.WeakReference;

public class OnSearchCallEnqueuedCallback<CTX extends IGifRecyclerView>
        extends WeakRefCallback<CTX, GifsResponse> {

    private final boolean mAppend;

    public OnSearchCallEnqueuedCallback(@NonNull CTX ctx, boolean append) {
        this(new WeakReference<>(ctx), append);
    }

    public OnSearchCallEnqueuedCallback(@NonNull WeakReference<CTX> weakRef, boolean append) {
        super(weakRef);
        mAppend = append;
    }

    @Override
    public void success(@NonNull IGifRecyclerView view, @NonNull GifsResponse response) {
        view.onReceiveSearchResultsSucceed(response, mAppend);
    }

    @Override
    public void failure(@NonNull IGifRecyclerView view, @Nullable BaseError error) {
        view.onReceiveSearchResultsFailed(error);
    }
}
