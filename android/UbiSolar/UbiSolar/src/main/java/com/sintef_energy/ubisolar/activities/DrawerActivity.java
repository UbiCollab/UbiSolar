package com.sintef_energy.ubisolar.activities;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sintef_energy.ubisolar.IView.IPresenterCallback;

import com.sintef_energy.ubisolar.database.energy.DeviceModel;
import com.sintef_energy.ubisolar.fragments.DeviceFragment;
import com.sintef_energy.ubisolar.fragments.HomeFragment;
import com.sintef_energy.ubisolar.fragments.PowerSavingFragment;
import com.sintef_energy.ubisolar.fragments.ProfileFragment;
import com.sintef_energy.ubisolar.fragments.SocialFragment;
import com.sintef_energy.ubisolar.presenter.DevicePresenter;
import com.sintef_energy.ubisolar.presenter.TipPresenter;
import com.sintef_energy.ubisolar.presenter.TotalEnergyPresenter;
import com.sintef_energy.ubisolar.utils.Global;
import com.sintef_energy.ubisolar.R;
import com.sintef_energy.ubisolar.fragments.NavigationDrawerFragment;
import com.sintef_energy.ubisolar.fragments.UsageFragment;

import java.util.Calendar;

public class DrawerActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        IPresenterCallback{

    private static final String LOG = DrawerActivity.class.getName();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private String[] titleNames;

    /**
     * Presenter
     */
    private TotalEnergyPresenter mTotalEnergyPresenter;
    private DevicePresenter devicePresenter;
    private RequestQueue requestQueue;
    private TipPresenter tipPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Check if user is logged in
        //Switch to login screen or continue.
//        if(!Global.loggedIn) {
//            Intent loginIntent = new Intent(this, LoginActivity.class);
//            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(loginIntent);
//        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestQueue = Volley.newRequestQueue(this);
        tipPresenter = new TipPresenter(requestQueue);
        /* Set up the presenters */

        /*UsagePresenter*/
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 8);
        mTotalEnergyPresenter = new TotalEnergyPresenter();
        //mTotalEnergyPresenter.loadEnergyData(getContentResolver(),
        //        0,
        //        calendar.getTimeInMillis());


        titleNames = getResources().getStringArray(R.array.nav_drawer_items);
        setContentView(R.layout.activity_usage);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        /* DevicePresenter */
        devicePresenter = new DevicePresenter();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        /* Update UX on backstack change *//*
        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        // Update your UI here.
                        getFragmentManager().
                    }
                });*/
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        //FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = null;

        boolean logout = false;

        switch (position){
            case 0:
                fragment = HomeFragment.newInstance(position);
                break;
            case 1:
                fragment = UsageFragment.newInstance(position);
                break;
            case 2:
                fragment = PowerSavingFragment.newInstance(position);
                break;
            case 3:
                fragment = DeviceFragment.newInstance(position);
                break;
            case 4:
                fragment = SocialFragment.newInstance(position);
                break;
            case 5:
                fragment = ProfileFragment.newInstance(position);
                break;
            case 6:
                logout = true;
                break;
        }

        if(fragment != null) //todo: Add to backstack? Or add null?
            addFragment(fragment, false, true, titleNames[position]);
            //fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        else if(logout){
            Global.loggedIn = false;
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
        }
        else
            Log.e(LOG, "Error creating fragment from navigation drawer.");
    }

    /**
     * Helper method to add fragments to the view.
     */
    public void addFragment(Fragment fragment, boolean animate, boolean addToBackStack, String tag) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (animate) {/*
            ft.setCustomAnimations(android.R.anim.fragment_from_right,
                    R.anim.fragment_from_left, R.anim.fragment_from_right,
                    R.anim.fragment_from_left);*/
        }
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        //ft.add(R.id.container, fragment);
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    public void onSectionAttached(int number) {
        if(number < titleNames.length)
            mTitle = titleNames[number];
        else
            Log.e(LOG, "Attaching to section number that does not exist: " + number);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.usage, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public TotalEnergyPresenter getmTotalEnergyPresenter() {
        return mTotalEnergyPresenter;
    }

    @Override
    public DevicePresenter getDevicePresenter() { return devicePresenter; }

    public TipPresenter getTipPresenter() {
        return tipPresenter;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
