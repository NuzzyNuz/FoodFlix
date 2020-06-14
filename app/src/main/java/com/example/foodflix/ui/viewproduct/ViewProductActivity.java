package com.example.foodflix.ui.viewproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodflix.R;
import com.example.foodflix.data.Product;
import com.example.foodflix.ui.addproduct.ScanProductActivity;
import com.example.foodflix.ui.home.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity {


    List<Product> products;

    DatabaseReference databaseProducts;

    ListView listViewProducts;

    ProgressBar progressBar;

    private TextView textViewReturn, textViewReturnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        //getting the reference of products node
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");

        textViewReturn = findViewById(R.id.text_view_return);
        textViewReturn = findViewById(R.id.text_view_returnHome);

        listViewProducts = findViewById(R.id.listViewProducts);

        progressBar = findViewById(R.id.progressbarImg);

        products = new ArrayList<>();

        textViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProductActivity.this, ScanProductActivity.class);
                startActivity(intent);
            }
        });

        textViewReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProductActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = products.get(i);
                showUpdateDeleteDialog(product.getProductCode(), product.getProductName(), product.getPeanut(), product.getMilk(), product.getWheat(), product.getSoy(), product.getShellFish(), product.getEgg(), product.getFish(), product.getPork(), product.getAlcohol(), product.getPoultry(), product.getBeef());
                return true;
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    products.add(product);

                }

                ProductListActivity productAdapter = new ProductListActivity(ViewProductActivity.this, products);

                listViewProducts.setAdapter(productAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog(String productCode, String productName, String peanut, String milk, String wheat, String soy, String shellFish, String egg, String fish, String pork, String alcohol, String poultry, String beef) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_delete_product, null);
        dialogBuilder.setView(dialogView);

        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteProduct);
//        final EditText editTextProductBarcode = dialogView.findViewById(R.id.editTextProductBarcodeU);
        final EditText editTextProductName = dialogView.findViewById(R.id.editTextProductNameU);
        final CheckBox checkBoxPeanut = dialogView.findViewById(R.id.checkBoxPeanutPU);
        final CheckBox checkBoxMilk = dialogView.findViewById(R.id.checkBoxMilkPU);
        final CheckBox checkBoxWheat = dialogView.findViewById(R.id.checkBoxWheatPU);
        final CheckBox checkBoxSoy = dialogView.findViewById(R.id.checkBoxSoyPU);
        final CheckBox checkBoxShellFish = dialogView.findViewById(R.id.checkBoxShellFishPU);
        final CheckBox checkBoxEgg = dialogView.findViewById(R.id.checkBoxEggPU);
        final CheckBox checkBoxFish = dialogView.findViewById(R.id.checkBoxFishPU);
        final CheckBox checkBoxPork = dialogView.findViewById(R.id.checkBoxPorkPU);
        final CheckBox checkBoxAlcohol = dialogView.findViewById(R.id.checkBoxAlcoholPU);
        final CheckBox checkBoxPoultry = dialogView.findViewById(R.id.checkBoxPoultryPU);
        final CheckBox checkBoxBeef = dialogView.findViewById(R.id.checkBoxBeefPU);
        final ProgressBar progressBar = dialogView.findViewById(R.id.progressbarImgU);

//        editTextProductBarcode.setText(productCode);

        editTextProductName.setText(productName);
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
        if (shellFish.equals("true")) {
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

        dialogBuilder.setTitle("Product Barcode: " + productCode);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
//                String productCode = editTextProductBarcode.getText().toString().trim();
                String productName = editTextProductName.getText().toString().trim();

                String peanut = "false";
                String milk = "false";
                String wheat = "false";
                String soy = "false";
                String shellFish = "false";
                String egg = "false";
                String fish = "false";
                String pork = "false";
                String alcohol = "false";
                String poultry = "false";
                String beef = "false";

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
                    shellFish = "true";
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

                if (!TextUtils.isEmpty(productName)) {
                    updateProduct(productCode, productName, peanut, milk, wheat, soy, shellFish, egg, fish, pork, alcohol, poultry, beef);
                    progressBar.setVisibility(View.INVISIBLE);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                databaseProducts.child(productCode).removeValue();
                Toast.makeText(getApplicationContext(), "Product Deleted", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                b.dismiss();
            }
        });


    }

    private boolean updateProduct(String productCode, String productName, String peanut, String milk, String wheat, String soy, String shellFish, String egg, String fish, String pork, String alcohol, String poultry, String beef) {
        DatabaseReference DB = databaseProducts.child(productCode);

        //updating product
        Product product = new Product(productCode, productName, peanut, milk, wheat, soy, shellFish, egg, fish, pork, alcohol, poultry, beef);
        DB.setValue(product);

        Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_LONG).show();
        return true;
    }


}

