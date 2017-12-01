package com.layer.messenger.tenor;

import com.tenor.android.core.constant.AspectRatioRange;
import com.tenor.android.core.constant.MediaFilter;
import com.tenor.android.core.constant.StringConstant;
import com.tenor.android.core.network.ApiClient;
import com.tenor.android.core.presenter.impl.BasePresenter;
import com.tenor.android.core.response.impl.GifsResponse;
import com.tenor.android.core.response.impl.TrendingGifResponse;

import retrofit2.Call;

public class GifPresenter extends BasePresenter<IGifRecyclerView> implements IGifRecyclerView.Presenter {


    public GifPresenter(IGifRecyclerView view) {
        super(view);
    }

    @Override
    public Call<GifsResponse> search(String query, String locale, int limit, String pos, String type, final boolean isAppend) {

        final String qry = StringConstant.getOrEmpty(query);

        Call<GifsResponse> call = ApiClient.getInstance(getView().getContext()).search(
                ApiClient.getServiceIds(getView().getContext()),
                qry, limit, StringConstant.getOrEmpty(pos), MediaFilter.BASIC, AspectRatioRange.ALL);

        call.enqueue(new OnSearchCallEnqueuedCallback<>(getView(), isAppend));
        return call;
    }

    @Override
    public Call<TrendingGifResponse> getTrending(int limit, String pos, String type, final boolean isAppend) {
        Call<TrendingGifResponse> call = ApiClient.getInstance(getView().getContext()).getTrending(
                ApiClient.getServiceIds(getView().getContext()),
                limit, StringConstant.getOrEmpty(pos), MediaFilter.BASIC, AspectRatioRange.ALL);

        call.enqueue(new OnTrendingCallEnqueuedCallback<>(getView(), isAppend));
        return call;
    }
}
