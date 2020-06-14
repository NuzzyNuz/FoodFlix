package com.example.foodflix.ui.addproduct;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.example.foodflix.R;
import com.example.foodflix.data.Product;
import com.example.foodflix.databinding.ActivityScanProductBinding;
import com.example.foodflix.helpers.constant.IntentKey;
import com.example.foodflix.helpers.constant.PreferenceKey;
import com.example.foodflix.helpers.model.Code;
import com.example.foodflix.helpers.util.SharedPrefUtil;
import com.example.foodflix.helpers.util.TimeUtil;
import com.example.foodflix.helpers.util.database.DatabaseUtil;
import com.example.foodflix.ui.settings.SettingsActivity;
import com.example.foodflix.ui.viewproduct.ViewProductActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class ScanProductActivity extends AppCompatActivity {

    CheckBox checkBoxPeanut, checkBoxMilk, checkBoxWheat,
            checkBoxSoy, checkBoxShellFish, checkBoxEgg, checkBoxFish,
            checkBoxPork, checkBoxAlcohol, checkBoxPoultry, checkBoxBeef;
    //a list to store all the dietPref in firebase database
    List<Product> products;
    //database reference object
    DatabaseReference databaseProducts;
    ProgressBar progressBar;
    private CompositeDisposable mCompositeDisposable;
    private ActivityScanProductBinding mBinding;
    private Menu mToolbarMenu;
    private Code mCurrentCode;
    private boolean mIsHistory, mIsPickedFromGallery;
    private EditText editTextProductBarcode, editTextProductName;
    private TextView addProductBTN;

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        mCompositeDisposable = compositeDisposable;
    }

    public Code getCurrentCode() {
        return mCurrentCode;
    }

    public void setCurrentCode(Code currentCode) {
        mCurrentCode = currentCode;
    }

    public Menu getToolbarMenu() {
        return mToolbarMenu;
    }

    public void setToolbarMenu(Menu toolbarMenu) {
        mToolbarMenu = toolbarMenu;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mBinding.textViewContent.getText().toString().isEmpty()) {
            mBinding.textViewContent.setVisibility(View.GONE);
            mBinding.textViewTime.setVisibility(View.GONE);

        } else {
            editTextProductBarcode.setVisibility(View.GONE);
            findViewById(R.id.TVbarcode).setVisibility(View.GONE);
        }

        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (products != null) {
                    products.clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_product);
        setCompositeDisposable(new CompositeDisposable());
        getWindow().setBackgroundDrawable(null);
        initializeToolbar();
        loadQRCode();
//        setListeners();

        //getting the reference of products node
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");

        editTextProductBarcode = findViewById(R.id.editTextProductBarcode);
        editTextProductName = findViewById(R.id.editTextProductName);
        checkBoxPeanut = findViewById(R.id.checkBoxPeanutP);
        checkBoxMilk = findViewById(R.id.checkBoxMilkP);
        checkBoxWheat = findViewById(R.id.checkBoxWheatP);
        checkBoxSoy = findViewById(R.id.checkBoxSoyP);
        checkBoxShellFish = findViewById(R.id.checkBoxShellFishP);
        checkBoxEgg = findViewById(R.id.checkBoxEggP);
        checkBoxFish = findViewById(R.id.checkBoxFishP);
        checkBoxPork = findViewById(R.id.checkBoxPorkP);
        checkBoxAlcohol = findViewById(R.id.checkBoxAlcoholP);
        checkBoxPoultry = findViewById(R.id.checkBoxPoultryP);
        checkBoxBeef = findViewById(R.id.checkBoxBeefP);

        addProductBTN = findViewById(R.id.buttonAddProduct);

        products = new ArrayList<>();

        progressBar = findViewById(R.id.progressbarImgS);


        addProductBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                addProduct();
            }
        });

    }

    private void addProduct() {
        String productCode = null;
        String scanCode = null;
        String typeCode = editTextProductBarcode.getText().toString().trim();

        if (mBinding.textViewContent.getText().toString().isEmpty()) {
            productCode = typeCode;
        } else {
            scanCode = getCurrentCode().getContent().trim();
            productCode = scanCode;
        }

        String productName = editTextProductName.getText().toString().trim();

        String peanut = "false";
        String milk = "false";
        String wheat = "false";
        String soy = "false";
        String shellfish = "false";
        String egg = "false";
        String fish = "false";
        String pork = "false";
        String alcohol = "false";
        String poultry = "false";
        String beef = "false";

        if (!TextUtils.isEmpty(productCode)) {
            if (!TextUtils.isEmpty(productName)) {
                if (checkBoxPeanut.isChecked()) {
                    peanut = "true";
                }
                if (checkBoxMilk.isChecked()) {
                    milk = "true";
                }
                if (checkBoxWheat.isChecked()) {
                    wheat = "true";
                }
                if (checkBoxSoy.isChecked()) {
                    soy = "true";
                }
                if (checkBoxShellFish.isChecked()) {
                    shellfish = "true";
                }
                if (checkBoxEgg.isChecked()) {
                    egg = "true";
                }
                if (checkBoxFish.isChecked()) {
                    fish = "true";
                }
                if (checkBoxPork.isChecked()) {
                    pork = "true";
                }
                if (checkBoxAlcohol.isChecked()) {
                    alcohol = "true";
                }
                if (checkBoxPoultry.isChecked()) {
                    poultry = "true";
                }
                if (checkBoxBeef.isChecked()) {
                    beef = "true";
                }

                String finalProductCode = productCode;
                String finalPeanut = peanut;
                String finalMilk = milk;
                String finalWheat = wheat;
                String finalSoy = soy;
                String finalShellfish = shellfish;
                String finalEgg = egg;
                String finalFish = fish;
                String finalPork = pork;
                String finalAlcohol = alcohol;
                String finalPoultry = poultry;
                String finalBeef = beef;
                databaseProducts.child(productCode).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(ScanProductActivity.this, "Product Already Exists", Toast.LENGTH_LONG).show();

                            finish();
                            Intent intent = new Intent(ScanProductActivity.this, ViewProductActivity.class);
                            startActivity(intent);
                        } else {
                            Product product = new Product(finalProductCode, productName, finalPeanut, finalMilk, finalWheat, finalSoy, finalShellfish, finalEgg, finalFish, finalPork, finalAlcohol, finalPoultry, finalBeef);

                            databaseProducts.child(finalProductCode).setValue(product);

                            progressBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(ScanProductActivity.this, "Product Added", Toast.LENGTH_LONG).show();

                            finish();
                            Intent intent = new Intent(ScanProductActivity.this, ViewProductActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            } else {
                Toast.makeText(this, "Please enter a product name", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter a barcode", Toast.LENGTH_LONG).show();
        }


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

//            mBinding.textViewType.setText(String.format(Locale.ENGLISH, getString(R.string.code_type),
//                    getResources().getStringArray(R.array.code_types)[getCurrentCode().getType()]));

            mBinding.textViewTime.setText(String.format(Locale.ENGLISH, getString(R.string.created_time),
                    TimeUtil.getFormattedDateString(getCurrentCode().getTimeStamp())));


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
