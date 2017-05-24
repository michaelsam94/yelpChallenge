package com.operr.yelpchallenge.view.activity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.operr.yelpchallenge.R;
import com.operr.yelpchallenge.common.helper.Utilities;


public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    //instance of the current activity
    public static BaseActivity activity;
    //app bar
    Toolbar toolbar;
    //virtual app bar
    //to make the page comes behind or below the app bar




    private RelativeLayout rlContainer;


    // to allow sliding menu or not
    private boolean mShowToolbar = true;

    // inner layout to be inflated
    private int mActivityLayout;
    //side menu
    private DrawerLayout mDrawerLayout;
    private FrameLayout mSideMenuContainer;
    private ActionBarDrawerToggle mDrawerToggle;





    //Constructor
    public BaseActivity(int activityLayout, boolean showToolbar) {
        this.mActivityLayout = activityLayout;
        this.mShowToolbar = showToolbar;

    }


    // abstract method to be called in activities that extend this one instead of oncreate().
    protected abstract void doOnCreate(Bundle bundle);

    /**
     * ========================================= activity life cycle =========================================
     */
    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setCartViewCount();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        rlContainer = (RelativeLayout) findViewById(R.id.rl_base);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutMainDialer);
        mSideMenuContainer = (FrameLayout) mDrawerLayout.findViewById(R.id.fragment_side_menu);




        activity = BaseActivity.this;

        if (mShowToolbar) {
            toolbar.setVisibility(View.VISIBLE);
            if (toolbar != null) {
                setupToolbar(toolbar);
            }
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toolbar.setVisibility(View.GONE);
        }


        /*initialize content */
        ViewGroup contentLayout = (ViewGroup) findViewById(R.id.ll_content);
        if (mActivityLayout != -1)
            LayoutInflater.from(this).inflate(mActivityLayout, contentLayout, true);


        doOnCreate(savedInstanceState);

    }

    /**
     * ========================================= app bar  =========================================
     */

    public void setupToolbar(Toolbar toolbar) {


        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setTitle("");



    }

    public void setToolbarForSearchActivity() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setTitle("");
    }

    public void enableBackButtonToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().getDisplayOptions();
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            /*mAmvDrawerAndBack.setVisibility(View.GONE);*/
        }
    }

    //change the background of the header
    public void setHeaderBackgroundColor(int colorResId) {
        //get resource color for lollipop+ & pre-lollipop
        int bgColor = ContextCompat.getColor(this, colorResId);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(bgColor));
        }
    }

    //change the first line title of the header
    public void setHeaderTitleText(String titleText) {
        if (getSupportActionBar() != null) {
            if (!Utilities.isNullString(titleText)) {
                TextView tvTitle = (TextView) toolbar.findViewById(R.id.tv_title_toolbar);


                tvTitle.setText(titleText);
                tvTitle.setVisibility(View.VISIBLE);
                setSupportActionBar(toolbar);

            } else
                getSupportActionBar().setTitle(null);
        }
    }


    //change the second line title of the header
    public void setHeaderSubTitleText(String titleText) {
        if (getSupportActionBar() != null) {
            if (!Utilities.isNullString(titleText)) {
                getSupportActionBar().setSubtitle(titleText);
            } else {
                getSupportActionBar().setSubtitle(null);
            }
        }

    }


    /**
     * ========================================= Status Bar ======================================
     */
    public void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


}
