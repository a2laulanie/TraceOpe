package com.traceope.app.slider;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traceope.app.R;

/**
 * Created by ale on 04/11/14.
 */
public class SlideFragment_01 extends Fragment {

    public static final String ARG_PAGE = "page";
    private int mPageNumber;


    public static SlideFragment_01 create(int pageNumber) {
        SlideFragment_01 fragment = new SlideFragment_01();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SlideFragment_01() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_01, container, false);

        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
