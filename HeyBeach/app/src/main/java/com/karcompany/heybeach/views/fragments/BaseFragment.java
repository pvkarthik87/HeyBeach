package com.karcompany.heybeach.views.fragments;

/**
 * Created by pvkarthik on 2017-02-20.
 *
 * BaseFragment from which other fragments should extend.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment {

    protected abstract void setUpPresenter();

    protected abstract void bindViews(View view);

    protected abstract void unBindViews();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpPresenter();
        bindViews(view);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindViews();
    }
}
