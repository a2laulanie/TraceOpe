package com.traceope.app.slider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traceope.app.R;

/**
 * Created by ale on 06/11/14.
 */
public class SlideFragment_02 extends Fragment {

    public static final String ARG_PAGE = "page";
    private int mPageNumber;


    public static SlideFragment_02 create(int pageNumber) {
        SlideFragment_02 fragment = new SlideFragment_02();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SlideFragment_02() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_02, container, false);

        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
