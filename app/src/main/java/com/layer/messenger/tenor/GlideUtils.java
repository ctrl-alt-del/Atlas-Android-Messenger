package com.layer.messenger.tenor;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;

public class GlideUtils {

    public static void resumeRequests(@Nullable final Context context) {
        if (context != null) {
            Glide.with(context).resumeRequests();
        }
    }

    public static void pauseRequests(@Nullable final Context context) {
        if (context != null) {
            Glide.with(context).pauseRequests();
        }
    }
}
