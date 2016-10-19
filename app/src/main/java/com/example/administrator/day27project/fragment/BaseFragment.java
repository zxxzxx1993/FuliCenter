package com.example.administrator.day27project.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;

/**
 * A simple {@link Fragment} subclass.
 */
abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        initData(I.ACTION_DOWNLOAD);
        setListener();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void setListener();

    protected abstract void initData(int action);

    protected abstract void initView();
}
