package com.traceope.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.traceope.app.R;
import com.traceope.app.slider.*;


public class SlideActivity extends FragmentActivity {

    /**
     * Type de slide à afficher
     */

    private String slideType;
    public static final String FILE_SELECTED = "FILE_SELECTED";
    public static final String SLIDE_TUTORIEL = "SLIDE_TUTORIEL";


    /**
     * The number of pages
     */
    int NUM_PAGES = 0;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //recup du slide_type a afficher)
        Bundle bundle = getIntent().getExtras();
        slideType = bundle.getString("slideType");
         setContentView(R.layout.activity_slide);


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);

        if (slideType.equals(SLIDE_TUTORIEL)) {
            NUM_PAGES = 9;
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        } else {
            NUM_PAGES = 5;
            setTitle(R.string.title_activity_slide_color);

            mPagerAdapter = new ScreenSlidePagerColorAdapter(getSupportFragmentManager());
        }


        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active.
                invalidateOptionsMenu();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    /**
     * A simple pager adapter
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }


        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SlideFragment.create(position);
                case 1:
                    return SlideFragment_01.create(position);
                case 2:
                    return SlideFragment_02.create(position);
                case 3:
                    return SlideFragment_03.create(position);
                case 4:
                    return SlideFragment_04.create(position);
                case 5:
                    return SlideFragment_05.create(position);
                case 6:
                    return SlideFragment_07.create(position);
                case 7:
                    return SlideFragment_08.create(position);
                case 8:
                    return SlideFragment_09.create(position);

                default:
                    return SlideFragment.create(position);
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    /**
     * A simple pager adapter
     */
    private class ScreenSlidePagerColorAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerColorAdapter(FragmentManager fm) {
            super(fm);
        }


        public Fragment getItem(int position) {


            return SlideFragmentColor.create(position);


        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}


