/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.adddiet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.foodflix.R;
import com.example.foodflix.data.DietPref;
import com.example.foodflix.ui.homeuser.HomeuserActivity;
import com.example.foodflix.ui.login.LoginActivity;
import com.example.foodflix.ui.register.RegisterActivity;
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
 * The type Add diet pref activity.
 */
public class AddDietPrefActivity extends AppCompatActivity {

    /**
     * The Spinner diet def.
     */
//view objects
    Spinner spinnerDietDef;
    /**
     * The Button add diet pref.
     */
    TextView buttonAddDietPref, textViewSkip;

    /**
     * The Check box peanut.
     */
    CheckBox checkBoxPeanut,
    /**
     * The Check box milk.
     */
    checkBoxMilk,
    /**
     * The Check box wheat.
     */
    checkBoxWheat,
    /**
     * The Check box soy.
     */
    checkBoxSoy,
    /**
     * The Check box shell fish.
     */
    checkBoxShellFish,
    /**
     * The Check box egg.
     */
    checkBoxEgg,
    /**
     * The Check box fish.
     */
    checkBoxFish,
    /**
     * The Check box pork.
     */
    checkBoxPork,
    /**
     * The Check box alcohol.
     */
    checkBoxAlcohol,
    /**
     * The Check box poultry.
     */
    checkBoxPoultry,
    /**
     * The Check box beef.
     */
    checkBoxBeef;

    /**
     * The Progress bar.
     */
    ProgressBar progressBar;

    /**
     * The Diet prefs.
     */
//a list to store all the dietPref in firebase database
    List<DietPref> dietPrefs;

    /**
     * The Database diet preference.
     */
//database reference object
    DatabaseReference databaseDietPreference;

    /**
     * The M auth.
     */
//Firebase User
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    /**
     * The M auth user.
     */
    FirebaseUser mAuthUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet_pref);

        Intent intent = getIntent();
        String RegisterIntent = intent.getStringExtra(RegisterActivity.FROM_REGISTER);

        initializeToolbar();

        //getting the reference of diet preferences node
        databaseDietPreference = FirebaseDatabase.getInstance().getReference("dietPreferences");
        databaseDietPreference.keepSynced(true);
        //getting views
        spinnerDietDef = findViewById(R.id.spinnerDietaryDef);

        buttonAddDietPref = findViewById(R.id.buttonAddDietPref);

        checkBoxPeanut = findViewById(R.id.checkBoxPeanut);
        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxWheat = findViewById(R.id.checkBoxWheat);
        checkBoxSoy = findViewById(R.id.checkBoxSoy);
        checkBoxShellFish = findViewById(R.id.checkBoxShellFish);
        checkBoxEgg = findViewById(R.id.checkBoxEgg);
        checkBoxFish = findViewById(R.id.checkBoxFish);
        checkBoxPork = findViewById(R.id.checkBoxPork);
        checkBoxAlcohol = findViewById(R.id.checkBoxAlcohol);
        checkBoxPoultry = findViewById(R.id.checkBoxPoultry);
        checkBoxBeef = findViewById(R.id.checkBoxBeef);

        progressBar = findViewById(R.id.progressbarImg);

        textViewSkip = findViewById(R.id.textViewSkip);


        if (RegisterIntent != null) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            textViewSkip.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
            textViewSkip.setOnClickListener(view1 -> {
                startActivity(new Intent(this, HomeuserActivity.class));
            });
        }

        loadDietPrefInformation();


        //list to store diet preferences
        dietPrefs = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddDietPref.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            addDietPref();
        });
    }

    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadDietPrefInformation() {
        if (mAuthUser != null) {
            progressBar.setVisibility(View.VISIBLE);

            databaseDietPreference.child(mAuthUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

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

                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddDietPrefActivity.this, R.array.dietary_definition, android.R.layout.simple_spinner_item);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerDietDef.setAdapter(adapter);
                                int spinnerPosition = adapter.getPosition(dietaryPref);
                                spinnerDietDef.setSelection(spinnerPosition);

                                if (peanut.equals("true")) {
                                    checkBoxPeanut.setChecked(true);
                                }
                                if (milk.equals("true")) {
                                    checkBoxMilk.setChecked(true);
                                }
                                if (wheat.equals("true")) {
                                    checkBoxWheat.setChecked(true);
                                }
                                if (soy.equals("true")) {
                                    checkBoxSoy.setChecked(true);
                                }
                                if (shellfish.equals("true")) {
                                    checkBoxShellFish.setChecked(true);
                                }
                                if (egg.equals("true")) {
                                    checkBoxEgg.setChecked(true);
                                }
                                if (fish.equals("true")) {
                                    checkBoxFish.setChecked(true);
                                }
                                if (pork.equals("true")) {
                                    checkBoxPork.setChecked(true);
                                }
                                if (alcohol.equals("true")) {
                                    checkBoxAlcohol.setChecked(true);
                                }
                                if (poultry.equals("true")) {
                                    checkBoxPoultry.setChecked(true);
                                }
                                if (beef.equals("true")) {
                                    checkBoxBeef.setChecked(true);
                                }

                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


        }
    }

    private void addDietPref() {

        databaseDietPreference.child(mAuthUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //getting the values to save
                String dietaryDef = spinnerDietDef.getSelectedItem().toString();

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

                String id = mAuthUser.getUid();

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

                if (dataSnapshot.exists()) {

                    databaseDietPreference.child(mAuthUser.getUid()).removeValue();

                    //creating an Artist Object
                    DietPref dietPref = new DietPref(id, dietaryDef, peanut, milk, wheat, soy, shellfish, egg, fish, pork, alcohol, poultry, beef);

                    //Saving the Artist
                    databaseDietPreference.child(mAuthUser.getUid()).setValue(dietPref);

                    progressBar.setVisibility(View.INVISIBLE);

                    //displaying a success toast
                    Toast.makeText(AddDietPrefActivity.this, "Diet Preference Updated", Toast.LENGTH_LONG).show();
                } else {

                    //creating an Artist Object
                    DietPref dietPref = new DietPref(id, dietaryDef, peanut, milk, wheat, soy, shellfish, egg, fish, pork, alcohol, poultry, beef);

                    //Saving the Artist
                    databaseDietPreference.child(mAuthUser.getUid()).setValue(dietPref);

                    progressBar.setVisibility(View.INVISIBLE);

                    //displaying a success toast
                    Toast.makeText(AddDietPrefActivity.this, "Diet Preference Added", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}