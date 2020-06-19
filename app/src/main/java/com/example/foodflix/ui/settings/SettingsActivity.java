/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.foodflix.R;
import com.example.foodflix.databinding.ActivitySettingsBinding;
import com.example.foodflix.helpers.constant.PreferenceKey;
import com.example.foodflix.helpers.util.SharedPrefUtil;
import com.example.foodflix.ui.about_us.AboutUsActivity;
import com.example.foodflix.ui.adddiet.AddDietPrefActivity;
import com.example.foodflix.ui.login.LoginActivity;
import com.example.foodflix.ui.map.activity.MapActivity;
import com.example.foodflix.ui.privacy_policy.PrivayPolicyActivity;
import com.example.foodflix.ui.profile.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The type Settings activity.
 */
public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ActivitySettingsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        initializeToolbar();
        loadSettings();
        setListeners();
    }

    private void loadSettings() {
        mBinding.switchCompatPlaySound.setChecked(SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.PLAY_SOUND));
        mBinding.switchCompatVibrate.setChecked(SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.VIBRATE));
        mBinding.switchCompatSaveHistory.setChecked(SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.SAVE_HISTORY));
        mBinding.switchCompatCopyToClipboard.setChecked(SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.COPY_TO_CLIPBOARD));
    }

    private void setListeners() {
        mBinding.switchCompatPlaySound.setOnCheckedChangeListener(this);
        mBinding.switchCompatVibrate.setOnCheckedChangeListener(this);
        mBinding.switchCompatSaveHistory.setOnCheckedChangeListener(this);
        mBinding.switchCompatCopyToClipboard.setOnCheckedChangeListener(this);

        mBinding.textViewPlaySound.setOnClickListener(this);
        mBinding.textViewVibrate.setOnClickListener(this);
        mBinding.textViewSaveHistory.setOnClickListener(this);
        mBinding.textViewCopyToClipboard.setOnClickListener(this);
    }

    private void initializeToolbar() {
        setSupportActionBar(mBinding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_compat_play_sound:
                SharedPrefUtil.write(PreferenceKey.PLAY_SOUND, isChecked);
                break;

            case R.id.switch_compat_vibrate:
                SharedPrefUtil.write(PreferenceKey.VIBRATE, isChecked);
                break;

            case R.id.switch_compat_save_history:
                SharedPrefUtil.write(PreferenceKey.SAVE_HISTORY, isChecked);
                break;

            case R.id.switch_compat_copy_to_clipboard:
                SharedPrefUtil.write(PreferenceKey.COPY_TO_CLIPBOARD, isChecked);
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_play_sound:
                mBinding.switchCompatPlaySound.setChecked(!mBinding.switchCompatPlaySound.isChecked());
                break;

            case R.id.text_view_vibrate:
                mBinding.switchCompatVibrate.setChecked(!mBinding.switchCompatVibrate.isChecked());
                break;

            case R.id.text_view_save_history:
                mBinding.switchCompatSaveHistory.setChecked(!mBinding.switchCompatSaveHistory.isChecked());
                break;

            case R.id.text_view_copy_to_clipboard:
                mBinding.switchCompatCopyToClipboard.setChecked(!mBinding.switchCompatCopyToClipboard.isChecked());
                break;

            default:
                break;
        }
    }

    /**
     * Start about us activity.
     *
     * @param view the view
     */
    public void startAboutUsActivity(View view) {

        startActivity(new Intent(this, AboutUsActivity.class));
    }

    /**
     * Start privacy policy activity.
     *
     * @param view the view
     */
    public void startPrivacyPolicyActivity(View view) {
        startActivity(new Intent(this, PrivayPolicyActivity.class));
    }

    /**
     * Start account settings activity.
     *
     * @param view the view
     */
    public void startAccountSettingsActivity(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    /**
     * Start logout activity.
     *
     * @param view the view
     */
    public void startLogoutActivity(View view) {
//        startActivity(new Intent(this, LogoutActivity.class));
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));


    }

    /**
     * Start diet preferences activity.
     *
     * @param view the view
     */
    public void startDietPreferencesActivity(View view) {
        startActivity(new Intent(this, AddDietPrefActivity.class));
    }

    /**
     * Start map activity.
     *
     * @param view the view
     */
    public void startMapActivity(View view) {
        startActivity(new Intent(this, MapActivity.class));
    }
}
