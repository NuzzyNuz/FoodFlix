/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.alternativeproducts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodflix.R;
import com.example.foodflix.data.Product;
import com.example.foodflix.ui.home.HomeActivity;
import com.example.foodflix.ui.homeuser.HomeuserActivity;
import com.example.foodflix.ui.scanresult.ScanResultActivity;
import com.example.foodflix.ui.viewproduct.ProductListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Alternative products activity.
 */
public class AlternativeProductsActivity extends AppCompatActivity {

    /**
     * The Products.
     */
    List<Product> products;

    /**
     * The Database products.
     */
    DatabaseReference databaseProducts, /**
     * The Database diet preference.
     */
    databaseDietPreference;

    /**
     * The List view products.
     */
    ListView listViewProducts;

    /**
     * The Progress bar.
     */
    ProgressBar progressBar;

    /**
     * The Text view return home.
     */
    TextView textViewReturnHome;

    /**
     * The M auth.
     */
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    /**
     * The M auth user.
     */
    FirebaseUser mAuthUser = mAuth.getCurrentUser();

    /**
     * The Product cat from intent.
     */
//    Intent intent;
//    String productCodeFromIntent;
    String productCatFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative_products);

        Intent intent = getIntent();
        String productCodeFromIntent = intent.getStringExtra(ScanResultActivity.PRODUCT_CODE);

        TextView sample = findViewById(R.id.sample);

        databaseProducts = FirebaseDatabase.getInstance().getReference("products");
        databaseProducts.keepSynced(true);

        databaseDietPreference = FirebaseDatabase.getInstance().getReference("dietPreferences");
        databaseDietPreference.keepSynced(true);

        textViewReturnHome = findViewById(R.id.text_view_returnHome);

        listViewProducts = findViewById(R.id.listViewProducts);

        progressBar = findViewById(R.id.progressbarImg);

        products = new ArrayList<>();

        databaseDietPreference.child(mAuthUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
//                            String milk = "true";
                            String dietaryPref = Objects.requireNonNull(dataSnapshot.child("dietDef").getValue()).toString();
                            String peanut = Objects.requireNonNull(dataSnapshot.child("resPeanut").getValue()).toString();
                            String milk = Objects.requireNonNull(dataSnapshot.child("resMilk").getValue()).toString();
                            String wheat = Objects.requireNonNull(dataSnapshot.child("resWheat").getValue()).toString();
                            String soy = Objects.requireNonNull(dataSnapshot.child("resSoy").getValue()).toString();
                            String shellfish = Objects.requireNonNull(dataSnapshot.child("resShellFish").getValue()).toString();
                            String egg = Objects.requireNonNull(dataSnapshot.child("resEgg").getValue()).toString();
                            String fish = Objects.requireNonNull(dataSnapshot.child("resFish").getValue()).toString();
                            String pork = Objects.requireNonNull(dataSnapshot.child("resPork").getValue()).toString();
                            String alcohol = Objects.requireNonNull(dataSnapshot.child("resAlcohol").getValue()).toString();
                            String poultry = Objects.requireNonNull(dataSnapshot.child("resPoultry").getValue()).toString();
                            String beef = Objects.requireNonNull(dataSnapshot.child("resBeef").getValue()).toString();

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


                            String finalShellfish = shellfish;
                            String finalMilk = milk;
                            String finalEgg = egg;
                            String finalFish = fish;
                            String finalPork = pork;
                            String finalPoultry = poultry;
                            String finalBeef = beef;


                            databaseProducts.child(productCodeFromIntent).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        productCatFromIntent = Objects.requireNonNull(dataSnapshot.child("productCategory").getValue()).toString();

                                        progressBar.setVisibility(View.VISIBLE);
                                        databaseProducts.orderByChild("productCategory").equalTo(productCatFromIntent).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                products.clear();

                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                                    Product product = postSnapshot.getValue(Product.class);


//                                                    if ( !peanutMatch || !milkMatch || !wheatMatch || !soyMatch || !shellFishMatch || !eggMatch || !fishMatch || !porkMatch || !alcoholMatch || !poultryMatch || !beefMatch){
//                                                        products.add(product);
//                                                    }

//                                                    if (!product.getPeanut().equals("true")){
//                                                        products.add(product);
//                                                    }products.add(product);

                                                    if (!(product.getPeanut().equals("true") && peanut.equals("true"))) {
                                                        if (!(product.getMilk().equals("true") && finalMilk.equals("true"))) {
                                                            if (!(product.getWheat().equals("true") && wheat.equals("true"))) {
                                                                if (!(product.getSoy().equals("true") && soy.equals("true"))) {
                                                                    if (!(product.getShellFish().equals("true") && finalShellfish.equals("true"))) {
                                                                        if (!(product.getEgg().equals("true") && finalEgg.equals("true"))) {
                                                                            if (!(product.getFish().equals("true") && finalFish.equals("true"))) {
                                                                                if (!(product.getPork().equals("true") && finalPork.equals("true"))) {
                                                                                    if (!(product.getAlcohol().equals("true") && alcohol.equals("true"))) {
                                                                                        if (!(product.getPoultry().equals("true") && finalPoultry.equals("true"))) {
                                                                                            if (!(product.getBeef().equals("true") && finalBeef.equals("true"))) {

                                                                                                products.add(product);

                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }

                                                    }

                                                }


                                                ProductListActivity productAdapter = new ProductListActivity(AlternativeProductsActivity.this, products);
//                                                Toast.makeText(AlternativeProductsActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                                                listViewProducts.setAdapter(productAdapter);
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    } else {
                                        Toast.makeText(AlternativeProductsActivity.this, "No available alternatives for your dietary preference", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            Toast.makeText(AlternativeProductsActivity.this, "Please setup your dietary preferences for personalized alternatives. ", Toast.LENGTH_SHORT).show();
                            databaseProducts.child(productCodeFromIntent).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        productCatFromIntent = Objects.requireNonNull(dataSnapshot.child("productCategory").getValue()).toString();

                                        progressBar.setVisibility(View.VISIBLE);
                                        databaseProducts.orderByChild("productCategory").equalTo(productCatFromIntent).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                products.clear();

                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                                    Product product = postSnapshot.getValue(Product.class);
                                                    products.add(product);
                                                }


                                                ProductListActivity productAdapter = new ProductListActivity(AlternativeProductsActivity.this, products);
//                                                Toast.makeText(AlternativeProductsActivity.this, "ERROR2!", Toast.LENGTH_SHORT).show();

                                                listViewProducts.setAdapter(productAdapter);
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        sample.setText(intent.getStringExtra(ScanResultActivity.PRODUCT_CODE));


        textViewReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuthUser != null) {
                    String UID = mAuthUser.getUid();
                    if (UID.equals("IJwCUxpVhKQDym0nJNPw9f9T5043")) {
                        startActivity(new Intent(AlternativeProductsActivity.this, HomeActivity.class));
                        finish();
                    } else if (!UID.equals("IJwCUxpVhKQDym0nJNPw9f9T5043")) {
                        startActivity(new Intent(AlternativeProductsActivity.this, HomeuserActivity.class));
                        finish();
                    }
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}