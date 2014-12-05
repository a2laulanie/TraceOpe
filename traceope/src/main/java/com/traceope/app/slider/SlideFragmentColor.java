package com.traceope.app.slider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.traceope.app.R;


public class SlideFragmentColor extends Fragment {

    public static final String ARG_PAGE = "page";
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static SlideFragmentColor create(int pageNumber) {
        SlideFragmentColor fragment = new SlideFragmentColor();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SlideFragmentColor() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_color, container, false);

        ImageView iv = (ImageView) rootView.findViewById(R.id.imageViewColor);


        if (mPageNumber == 0) {
            iv.setImageResource(R.drawable.color_help1);
        } else if (mPageNumber == 1) {
            iv.setImageResource(R.drawable.color_help2);
        } else if (mPageNumber == 2) {
            iv.setImageResource(R.drawable.color_help3);
        } else if (mPageNumber == 3) {
            iv.setImageResource(R.drawable.color_help4);
        } else if (mPageNumber == 4) {
            iv.setImageResource(R.drawable.color_help5);
        }

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }


}
