/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.scanresult;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.foodflix.R;
import com.example.foodflix.databinding.ActivityScanResultBinding;
import com.example.foodflix.helpers.constant.IntentKey;
import com.example.foodflix.helpers.constant.PreferenceKey;
import com.example.foodflix.helpers.model.Code;
import com.example.foodflix.helpers.util.SharedPrefUtil;
import com.example.foodflix.helpers.util.TimeUtil;
import com.example.foodflix.helpers.util.database.DatabaseUtil;
import com.example.foodflix.ui.alternativeproducts.AlternativeProductsActivity;
import com.example.foodflix.ui.settings.SettingsActivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Scan result activity.
 */
public class ScanResultActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The constant PRODUCT_CODE.
     */
    public static final String PRODUCT_CODE = "com.example.foodflix.productCode";

    /**
     * The M auth.
     */
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    /**
     * The M auth user.
     */
    FirebaseUser mAuthUser = mAuth.getCurrentUser();
    /**
     * The Database diet preference.
     */
    DatabaseReference databaseDietPreference, /**
     * The Database products.
     */
    databaseProducts;
    private CompositeDisposable mCompositeDisposable;
    private ActivityScanResultBinding mBinding;
    private Menu mToolbarMenu;
    private Code mCurrentCode;
    private boolean mIsHistory, mIsPickedFromGallery;
    /**
     * The Tv alternatives.
     */
    TextView TVAlternatives;
    private ProgressBar progressBar;

    /**
     * Gets composite disposable.
     *
     * @return the composite disposable
     */
    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    /**
     * Sets composite disposable.
     *
     * @param compositeDisposable the composite disposable
     */
    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        mCompositeDisposable = compositeDisposable;
    }

    /**
     * Gets current code.
     *
     * @return the current code
     */
    public Code getCurrentCode() {
        return mCurrentCode;
    }

    /**
     * Sets current code.
     *
     * @param currentCode the current code
     */
    public void setCurrentCode(Code currentCode) {
        mCurrentCode = currentCode;
    }

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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_result);


        //getting the reference of products node
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");
        databaseProducts.keepSynced(true);
        //getting the reference of diet preference node
        databaseDietPreference = FirebaseDatabase.getInstance().getReference("dietPreferences");
        databaseDietPreference.keepSynced(true);

        progressBar = findViewById(R.id.progressbarImg);

        TVAlternatives = findViewById(R.id.text_view_alternatives);

        TVAlternatives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlternativeProductsActivity.class);

                intent.putExtra(PRODUCT_CODE, getCurrentCode().getContent());

                startActivity(intent);

            }
        });

        setCompositeDisposable(new CompositeDisposable());
        getWindow().setBackgroundDrawable(null);
        initializeToolbar();
        loadQRCode();
        setListeners();
        checkInternetConnection();
        loadMatchResult();
        try {
            loadProductResult();

        } catch (Exception e) {
            TextView ErrorMsg = findViewById(R.id.textViewProductNameR);
            TextView HideMatch = findViewById(R.id.text_view_match_result);
            HideMatch.setVisibility(View.GONE);
            TVAlternatives.setVisibility(View.GONE);
            ErrorMsg.setText("Unable to detect a product code. Please scan a valid product code");
            ErrorMsg.setTextColor(Color.RED);
            ErrorMsg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            Toast.makeText(this,"Unable to detect a product code!", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadProductResult() {
        String productCode = null;
        if (getCurrentCode().getContent() != null) {
            productCode = getCurrentCode().getContent();
        }

        databaseProducts.child(productCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String productName = dataSnapshot.child("productName").getValue().toString();
                    String productCategory = dataSnapshot.child("productCategory").getValue().toString();
                    String peanutProduct = dataSnapshot.child("peanut").getValue().toString();
                    String milkProduct = dataSnapshot.child("milk").getValue().toString();
                    String wheatProduct = dataSnapshot.child("wheat").getValue().toString();
                    String soyProduct = dataSnapshot.child("soy").getValue().toString();
                    String shellfishProduct = dataSnapshot.child("shellFish").getValue().toString();
                    String eggProduct = dataSnapshot.child("egg").getValue().toString();
                    String fishProduct = dataSnapshot.child("fish").getValue().toString();
                    String porkProduct = dataSnapshot.child("pork").getValue().toString();
                    String alcoholProduct = dataSnapshot.child("alcohol").getValue().toString();
                    String poultryProduct = dataSnapshot.child("poultry").getValue().toString();
                    String beefProduct = dataSnapshot.child("beef").getValue().toString();

                    TextView textViewProductName = findViewById(R.id.textViewProductNameR);
                    TextView textViewProductCategory = findViewById(R.id.textViewProductCatR);
                    Chip chipPeanut = findViewById(R.id.chipPeanutR);
                    Chip chipMilk = findViewById(R.id.chipMilkR);
                    Chip chipWheat = findViewById(R.id.chipWheatR);
                    Chip chipsoy = findViewById(R.id.chipsoyR);
                    Chip chipshellFish = findViewById(R.id.chipshellFishR);
                    Chip chipegg = findViewById(R.id.chipeggR);
                    Chip chipfish = findViewById(R.id.chipfishR);
                    Chip chippork = findViewById(R.id.chipporkR);
                    Chip chippoultry = findViewById(R.id.chippoultryR);
                    Chip chipbeef = findViewById(R.id.chipbeefR);
                    Chip chipalcohol = findViewById(R.id.chipalcoholR);

                    textViewProductName.setText("PRODUCT NAME: " + productName);
                    textViewProductCategory.setText("PRODUCT CATEGORY: " + productCategory);
                    if (peanutProduct.equals("true")) {
                        chipPeanut.setText("peanut");
                        chipPeanut.setVisibility(View.VISIBLE);
                    }

                    if (milkProduct.equals("true")) {
                        chipMilk.setText("milk");
                        chipMilk.setVisibility(View.VISIBLE);
                    }

                    if (wheatProduct.equals("true")) {
                        chipWheat.setText("wheat");
                        chipWheat.setVisibility(View.VISIBLE);
                    }

                    if (soyProduct.equals("true")) {
                        chipsoy.setText("soy");
                        chipsoy.setVisibility(View.VISIBLE);
                    }

                    if (shellfishProduct.equals("true")) {
                        chipshellFish.setText("shell fish");
                        chipshellFish.setVisibility(View.VISIBLE);
                    }

                    if (eggProduct.equals("true")) {
                        chipegg.setText("egg");
                        chipegg.setVisibility(View.VISIBLE);
                    }

                    if (fishProduct.equals("true")) {
                        chipfish.setText("fish");
                        chipfish.setVisibility(View.VISIBLE);
                    }

                    if (porkProduct.equals("true")) {
                        chippork.setText("pork");
                        chippork.setVisibility(View.VISIBLE);
                    }

                    if (poultryProduct.equals("true")) {
                        chippoultry.setText("poultry");
                        chippoultry.setVisibility(View.VISIBLE);
                    }

                    if (beefProduct.equals("true")) {
                        chipbeef.setText("beef");
                        chipbeef.setVisibility(View.VISIBLE);
                    }

                    if (alcoholProduct.equals("true")) {
                        chipalcohol.setText("alcohol");
                        chipalcohol.setVisibility(View.VISIBLE);
                    }

                } else {
                    ((TextView) findViewById(R.id.text_view_match_result)).setText("Sorry! This product barcode has not yet been added to our database");
                    ((TextView) findViewById(R.id.text_view_match_result)).setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadMatchResult() {
        if (mAuthUser != null) {
            progressBar.setVisibility(View.VISIBLE);
            databaseDietPreference.child(mAuthUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String dietaryPref = dataSnapshot.child("dietDef").getValue().toString();
                        String peanut = dataSnapshot.child("resPeanut").getValue().toString();
                        String milk = dataSnapshot.child("resMilk").getValue().toString();
                        String wheat = dataSnapshot.child("resWheat").getValue().toString();
                        String soy = dataSnapshot.child("resSoy").getValue().toString();
                        String shellfish = dataSnapshot.child("resShellFish").getValue().toString();
                        String egg = dataSnapshot.child("resEgg").getValue().toString();
                        String fish = dataSnapshot.child("resFish").getValue().toString();
                        String pork = dataSnapshot.child("resPork").getValue().toString();
                        String alcohol = dataSnapshot.child("resAlcohol").getValue().toString();
                        String poultry = dataSnapshot.child("resPoultry").getValue().toString();
                        String beef = dataSnapshot.child("resBeef").getValue().toString();

                        if (dietaryPref.equals("Vegan")) {
                            milk = "true";
                            shellfish = "true";
                            egg = "true";
                            fish = "true";
                            pork = "true";
                            poultry = "true";
                            beef = "true";
                        }

                        if (dietaryPref.equals("Ovo-Vegetarian")) {
                            milk = "true";
                            shellfish = "true";
                            fish = "true";
                            pork = "true";
                            poultry = "true";
                            beef = "true";
                        }

                        if (dietaryPref.equals("Lacto-Vegetarian")) {
                            shellfish = "true";
                            egg = "true";
                            fish = "true";
                            pork = "true";
                            poultry = "true";
                            beef = "true";
                        }

                        if (dietaryPref.equals("Lacto-Ovo Vegetarian")) {
                            shellfish = "true";
                            fish = "true";
                            pork = "true";
                            poultry = "true";
                            beef = "true";
                        }

                        if (dietaryPref.equals("Pescetarians")) {
                            milk = "true";
                            shellfish = "true";
                            egg = "true";
                            pork = "true";
                            poultry = "true";
                            beef = "true";
                        }

                        if (dietaryPref.equals("Pollotarian")) {
                            milk = "true";
                            shellfish = "true";
                            egg = "true";
                            fish = "true";
                            pork = "true";
                            beef = "true";
                        }

                        if (dietaryPref.equals("Pollo-Pescetarian")) {
                            milk = "true";
                            egg = "true";
                            pork = "true";
                            beef = "true";
                        }

                        String productCode = null;
                        if (getCurrentCode().getContent() != null) {
                            productCode = getCurrentCode().getContent();
                        }

                        String finalShellfish = shellfish;
                        String finalMilk = milk;
                        String finalEgg = egg;
                        String finalFish = fish;
                        String finalPork = pork;
                        String finalPoultry = poultry;
                        String finalBeef = beef;

                        databaseProducts.child(productCode).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String productName = dataSnapshot.child("productName").getValue().toString();
                                    String productCategory = dataSnapshot.child("productCategory").getValue().toString();
                                    String peanutProduct = dataSnapshot.child("peanut").getValue().toString();
                                    String milkProduct = dataSnapshot.child("milk").getValue().toString();
                                    String wheatProduct = dataSnapshot.child("wheat").getValue().toString();
                                    String soyProduct = dataSnapshot.child("soy").getValue().toString();
                                    String shellfishProduct = dataSnapshot.child("shellFish").getValue().toString();
                                    String eggProduct = dataSnapshot.child("egg").getValue().toString();
                                    String fishProduct = dataSnapshot.child("fish").getValue().toString();
                                    String porkProduct = dataSnapshot.child("pork").getValue().toString();
                                    String alcoholProduct = dataSnapshot.child("alcohol").getValue().toString();
                                    String poultryProduct = dataSnapshot.child("poultry").getValue().toString();
                                    String beefProduct = dataSnapshot.child("beef").getValue().toString();


                                    boolean peanutMatch = peanut.equals("true") && peanutProduct.equals("true");
                                    boolean milkMatch = finalMilk.equals("true") && milkProduct.equals("true");
                                    boolean wheatMatch = wheat.equals("true") && wheatProduct.equals("true");
                                    boolean soyMatch = soy.equals("true") && soyProduct.equals("true");
                                    boolean shellFishMatch = finalShellfish.equals("true") && shellfishProduct.equals("true");
                                    boolean eggMatch = finalEgg.equals("true") && eggProduct.equals("true");
                                    boolean fishMatch = finalFish.equals("true") && fishProduct.equals("true");
                                    boolean porkMatch = finalPork.equals("true") && porkProduct.equals("true");
                                    boolean alcoholMatch = alcohol.equals("true") && alcoholProduct.equals("true");
                                    boolean poultryMatch = finalPoultry.equals("true") && poultryProduct.equals("true");
                                    boolean beefMatch = finalBeef.equals("true") && beefProduct.equals("true");


                                    if (peanutMatch || milkMatch || wheatMatch || soyMatch || shellFishMatch || eggMatch || fishMatch || porkMatch || alcoholMatch || poultryMatch || beefMatch) {
                                        ((TextView) findViewById(R.id.text_view_match_result)).setText("This product does not suit your dietary preference");
                                        ((TextView) findViewById(R.id.text_view_match_result)).setTextColor(Color.RED);
                                        progressBar.setVisibility(View.INVISIBLE);
                                    } else {
                                        ((TextView) findViewById(R.id.text_view_match_result)).setText("This product suits your dietary preference");
                                        ((TextView) findViewById(R.id.text_view_match_result)).setTextColor(Color.GREEN);
                                        progressBar.setVisibility(View.INVISIBLE);

                                    }

                                } else {
//                                    ((TextView) findViewById(R.id.text_view_match_result)).setText("Sorry! This product barcode has not yet been added to our database");
//                                    ((TextView) findViewById(R.id.text_view_match_result)).setTextColor(Color.RED);
                                    progressBar.setVisibility(View.INVISIBLE);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    } else {
                        ((TextView) findViewById(R.id.text_view_match_result)).setText("Your Dietary Preferences have not been set. Please set them in 'Settings > Dietary Preferences'");
                        ((TextView) findViewById(R.id.text_view_match_result)).setTextColor(Color.RED);
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


//            if (peanut == peanutProduct){
//                ((TextView)findViewById(R.id.text_view_match_result)).setText("This product does not suit your dietary preference");
//                ((TextView)findViewById(R.id.text_view_match_result)).setTextColor(Color.RED);
//            }else {
//                ((TextView)findViewById(R.id.text_view_match_result)).setText("This product suits your dietary preference");
//                ((TextView)findViewById(R.id.text_view_match_result)).setTextColor(Color.GREEN);
//            }

        }
    }

    private void checkInternetConnection() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(ReactiveNetwork
                .observeNetworkConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    if (connectivity.state() == NetworkInfo.State.CONNECTED) {
//                        mBinding.adView.setVisibility(View.VISIBLE);
                    } else {
//                        mBinding.adView.setVisibility(View.GONE);
                    }

                }, throwable -> {
                    Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                }));
    }

    private void setListeners() {
//        mBinding.textViewOpenInBrowser.setOnClickListener(this);
        mBinding.imageViewShare.setOnClickListener(this);
    }

    private void loadQRCode() {
        Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();

            if (bundle != null && bundle.containsKey(IntentKey.MODEL)) {
                setCurrentCode(bundle.getParcelable(IntentKey.MODEL));
            }

            if (bundle != null && bundle.containsKey(IntentKey.IS_HISTORY)) {
                mIsHistory = bundle.getBoolean(IntentKey.IS_HISTORY);
            }

            if (bundle != null && bundle.containsKey(IntentKey.IS_PICKED_FROM_GALLERY)) {
                mIsPickedFromGallery = bundle.getBoolean(IntentKey.IS_PICKED_FROM_GALLERY);
            }
        }

        if (getCurrentCode() != null) {
            mBinding.textViewContent.setText(String.format(Locale.ENGLISH,
                    getString(R.string.content), getCurrentCode().getContent()));

            mBinding.textViewType.setText(String.format(Locale.ENGLISH, getString(R.string.code_type),
                    getResources().getStringArray(R.array.code_types)[getCurrentCode().getType()]));

            mBinding.textViewTime.setText(String.format(Locale.ENGLISH, getString(R.string.created_time),
                    TimeUtil.getFormattedDateString(getCurrentCode().getTimeStamp())));

//            mBinding.textViewOpenInBrowser.setEnabled(URLUtil.isValidUrl(getCurrentCode().getContent()));

            if (!TextUtils.isEmpty(getCurrentCode().getCodeImagePath())) {
                Glide.with(this)
                        .asBitmap()
                        .load(getCurrentCode().getCodeImagePath())
                        .into(mBinding.imageViewScannedCode);
            }

            if (SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.COPY_TO_CLIPBOARD)
                    && !mIsHistory) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                if (clipboard != null) {
                    ClipData clip = ClipData.newPlainText(
                            getString(R.string.scanned_qr_code_content),
                            getCurrentCode().getContent());
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(this, getString(R.string.copied_to_clipboard),
                            Toast.LENGTH_SHORT).show();
                }
            }

            if (SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.SAVE_HISTORY) && !mIsHistory) {
                getCompositeDisposable().add(DatabaseUtil.on().insertCode(getCurrentCode())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));
            }
        }
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

            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        setToolbarMenu(menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.text_view_open_in_browser:
//                if (getCurrentCode() != null
//                        && URLUtil.isValidUrl(getCurrentCode().getContent())) {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//                    browserIntent.setData(Uri.parse(getCurrentCode().getContent()));
//                    startActivity(browserIntent);
//                }
//                break;

            case R.id.image_view_share:
                if (getCurrentCode() != null) {
                    shareCode(new File(getCurrentCode().getCodeImagePath()));
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getCompositeDisposable().dispose();

        if (getCurrentCode() != null
                && !SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.SAVE_HISTORY)
                && !mIsHistory && !mIsPickedFromGallery) {
            new File(getCurrentCode().getCodeImagePath()).delete();
        }
    }

    private void shareCode(File codeImageFile) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this,
                    getString(R.string.file_provider_authority), codeImageFile));
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(codeImageFile));
        }

        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_code_using)));
    }
}
