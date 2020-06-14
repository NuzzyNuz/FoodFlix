package com.example.foodflix.ui.privacy_policy;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.foodflix.R;
import com.example.foodflix.databinding.ActivityPrivayPolicyBinding;

public class PrivayPolicyActivity extends AppCompatActivity {

    ActivityPrivayPolicyBinding activity_privay_policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_privay_policy = DataBindingUtil.setContentView(this, R.layout.activity_privay_policy);
        initializeToolbar();

        Spanned policy;
        if (Build.VERSION.SDK_INT >= 24) {
            policy = Html.fromHtml(getString(R.string.privacy_policy3), Html.FROM_HTML_MODE_LEGACY);
        } else {
            policy = Html.fromHtml(getString(R.string.privacy_policy3));
        }
        TextView policyLink = findViewById(R.id.linkspolicy);
        policyLink.setText(policy);
        policyLink.setMovementMethod(LinkMovementMethod.getInstance());

        Spanned policy2;
        if (Build.VERSION.SDK_INT >= 24) {
            policy2 = Html.fromHtml(getString(R.string.privacy_policyText4), Html.FROM_HTML_MODE_LEGACY);
        } else {
            policy2 = Html.fromHtml(getString(R.string.privacy_policyText4));
        }
        TextView policyLink2 = findViewById(R.id.linkspolicy2);
        policyLink2.setText(policy2);
        policyLink2.setMovementMethod(LinkMovementMethod.getInstance());
    }


    private void initializeToolbar() {
        setSupportActionBar(activity_privay_policy.toolbar);

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
}
