/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.viewproduct;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodflix.R;
import com.example.foodflix.data.Product;
import com.google.android.material.chip.Chip;

import java.util.List;


/**
 * The type Product list activity.
 */
public class ProductListActivity extends ArrayAdapter<Product> {
    /**
     * The Products.
     */
    List<Product> products;
    private Activity context;

    /**
     * Instantiates a new Product list activity.
     *
     * @param context  the context
     * @param products the products
     */
    public ProductListActivity(Activity context, List<Product> products) {
        super(context, R.layout.activity_product_list, products);
        this.context = context;
        this.products = products;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_product_list, null, true);

        TextView textViewProductName = listViewItem.findViewById(R.id.textViewProductName);
        TextView textViewProductBarcode = listViewItem.findViewById(R.id.textViewProductBarcode);
        TextView textViewProductCategory = listViewItem.findViewById(R.id.textViewProductCat);
        Chip chipPeanut = listViewItem.findViewById(R.id.chipPeanut);
        Chip chipMilk = listViewItem.findViewById(R.id.chipMilk);
        Chip chipWheat = listViewItem.findViewById(R.id.chipWheat);
        Chip chipsoy = listViewItem.findViewById(R.id.chipsoy);
        Chip chipshellFish = listViewItem.findViewById(R.id.chipshellFish);
        Chip chipegg = listViewItem.findViewById(R.id.chipegg);
        Chip chipfish = listViewItem.findViewById(R.id.chipfish);
        Chip chippork = listViewItem.findViewById(R.id.chippork);
        Chip chippoultry = listViewItem.findViewById(R.id.chippoultry);
        Chip chipbeef = listViewItem.findViewById(R.id.chipbeef);
        Chip chipalcohol = listViewItem.findViewById(R.id.chipalcohol);

        Product product = products.get(position);
        textViewProductName.setText(product.getProductName());
        textViewProductBarcode.setText(product.getProductCode());
        textViewProductCategory.setText(product.getProductCategory());

        if (product.getPeanut().equals("true")) {
            chipPeanut.setText("peanut");
            chipPeanut.setVisibility(View.VISIBLE);
        }

        if (product.getMilk().equals("true")) {
            chipMilk.setText("milk");
            chipMilk.setVisibility(View.VISIBLE);
        }

        if (product.getWheat().equals("true")) {
            chipWheat.setText("wheat");
            chipWheat.setVisibility(View.VISIBLE);
        }

        if (product.getSoy().equals("true")) {
            chipsoy.setText("soy");
            chipsoy.setVisibility(View.VISIBLE);
        }

        if (product.getShellFish().equals("true")) {
            chipshellFish.setText("shell fish");
            chipshellFish.setVisibility(View.VISIBLE);
        }

        if (product.getEgg().equals("true")) {
            chipegg.setText("egg");
            chipegg.setVisibility(View.VISIBLE);
        }

        if (product.getFish().equals("true")) {
            chipfish.setText("fish");
            chipfish.setVisibility(View.VISIBLE);
        }

        if (product.getPork().equals("true")) {
            chippork.setText("pork");
            chippork.setVisibility(View.VISIBLE);
        }

        if (product.getPoultry().equals("true")) {
            chippoultry.setText("poultry");
            chippoultry.setVisibility(View.VISIBLE);
        }

        if (product.getBeef().equals("true")) {
            chipbeef.setText("beef");
            chipbeef.setVisibility(View.VISIBLE);
        }

        if (product.getAlcohol().equals("true")) {
            chipalcohol.setText("alcohol");
            chipalcohol.setVisibility(View.VISIBLE);
        }


        return listViewItem;
    }
}