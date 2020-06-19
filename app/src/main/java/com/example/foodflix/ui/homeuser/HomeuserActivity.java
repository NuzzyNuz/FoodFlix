/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.homeuser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodflix.R;
import com.example.foodflix.databinding.ActivityHomeuserBinding;
import com.example.foodflix.helpers.util.PermissionUtil;
import com.example.foodflix.ui.history.HistoryFragment;
import com.example.foodflix.ui.scan.ScanFragment;
import com.example.foodflix.ui.settings.SettingsActivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Homeuser activity.
 */
public class HomeuserActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityHomeuserBinding mBinding;
    private Menu mToolbarMenu;

    /**
     * Gets toolbar menu.
     *
     * @return the toolbar menu
     */
    public Menu getToolbarMenu() {
        return mToolbarMenu;
    }

    /**
     * Sets toolbar menu.
     *
     * @param toolbarMenu the toolbar menu
     */
    public void setToolbarMenu(Menu toolbarMenu) {
        mToolbarMenu = toolbarMenu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_homeuser);

        getWindow().setBackgroundDrawable(null);

        setListeners();
        initializeToolbar();
        initializeBottomBar();
        checkInternetConnection();
        playAd();
    }

    private void checkInternetConnection() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(ReactiveNetwork
                .observeNetworkConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    if (connectivity.state() == NetworkInfo.State.CONNECTED) {
                        mBinding.adViewU.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.adViewU.setVisibility(View.GONE);
                    }

                }, throwable -> {
                    Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                }));
    }

    private void playAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mBinding.adViewU.loadAd(adRequest);
        mBinding.adViewU.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mBinding.adViewU.setVisibility(View.GONE);
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
            }
        });
    }

    private void initializeToolbar() {
        setSupportActionBar(mBinding.toolbarU);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        setToolbarMenu(menu);
        return true;
    }

    /**
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {
        mBinding.textViewScanU.setOnClickListener(this);
        mBinding.textViewHistoryU.setOnClickListener(this);

        mBinding.imageViewScanU.setOnClickListener(this);
        mBinding.imageViewHistoryU.setOnClickListener(this);

        mBinding.constraintLayoutScanContainerU.setOnClickListener(this);
        mBinding.constraintLayoutHistoryContainerU.setOnClickListener(this);
    }

    private void initializeBottomBar() {
        clickOnScan();
    }


    private void clickOnScan() {
        if (PermissionUtil.on().requestPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {

            mBinding.textViewScanU.setTextColor(
                    ContextCompat.getColor(this, R.color.bottom_bar_selected));

            mBinding.textViewHistoryU.setTextColor(
                    ContextCompat.getColor(this, R.color.bottom_bar_normal));

            mBinding.imageViewScanU.setVisibility(View.INVISIBLE);
            mBinding.imageViewScanActiveU.setVisibility(View.VISIBLE);

            mBinding.imageViewHistoryU.setVisibility(View.VISIBLE);
            mBinding.imageViewHistoryActiveU.setVisibility(View.INVISIBLE);

            setToolbarTitle(getString(R.string.toolbar_title_scan));

        /*IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setBeepEnabled(SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.PLAY_SOUND));
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan a barcode");
        integrator.initiateScan();

        *//*
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
         */
            showFragment(ScanFragment.newInstance());
        }
    }

    private void clickOnHistory() {

        mBinding.textViewScanU.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_normal));

        mBinding.textViewHistoryU.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_selected));

        mBinding.imageViewScanU.setVisibility(View.VISIBLE);
        mBinding.imageViewScanActiveU.setVisibility(View.INVISIBLE);

        mBinding.imageViewHistoryU.setVisibility(View.INVISIBLE);
        mBinding.imageViewHistoryActiveU.setVisibility(View.VISIBLE);

        setToolbarTitle(getString(R.string.toolbar_title_history));
        showFragment(HistoryFragment.newInstance());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_scanU:
            case R.id.text_view_scanU:
            case R.id.constraint_layout_scan_containerU:
                clickOnScan();
                break;

            case R.id.image_view_historyU:
            case R.id.text_view_historyU:
            case R.id.constraint_layout_history_containerU:
                clickOnHistory();
                break;
        }
    }

    private void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.coordinator_layout_fragment_containerU, fragment,
                fragment.getClass().getSimpleName());
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtil.REQUEST_CODE_PERMISSION_DEFAULT) {
            boolean isAllowed = true;

            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isAllowed = false;
                }
            }

            if (isAllowed) {
                clickOnScan();
            }
        }
    }

  /*  public void hideAdMob()
    {
        if (mBinding.adView.isShown())
            mBinding.adView.setVisibility(View.GONE);
    }

    public void showAdmob()
    {
        if (!mBinding.adView.isShown())
            mBinding.adView.setVisibility(View.VISIBLE);
    }*/
}
