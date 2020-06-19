/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.map.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodflix.R;
import com.example.foodflix.ui.map.model.IndividualLocation;

import java.util.List;

/**
 * RecyclerView adapter to display a list of location cards on top of the map
 */
public class LocationRecyclerViewAdapter extends
        RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private List<IndividualLocation> listOfLocations;
    private Context context;
    private int selectedTheme;
    private Drawable emojiForCircle = null;
    private Drawable backgroundCircle = null;
    private int upperCardSectionColor = 0;

    private int locationNameColor = 0;
    private int locationAddressColor = 0;
    private int locationPhoneNumColor = 0;
    private int locationPhoneHeaderColor = 0;
    private int locationHoursColor = 0;
    private int locationHoursHeaderColor = 0;
    private int locationDistanceNumColor = 0;
    private int milesAbbreviationColor = 0;

    /**
     * Instantiates a new Location recycler view adapter.
     *
     * @param styles            the styles
     * @param context           the context
     * @param cardClickListener the card click listener
     * @param selectedTheme     the selected theme
     */
    public LocationRecyclerViewAdapter(List<IndividualLocation> styles,
                                       Context context, ClickListener cardClickListener, int selectedTheme) {
        this.context = context;
        this.listOfLocations = styles;
        this.selectedTheme = selectedTheme;
        clickListener = cardClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int singleRvCardToUse = R.layout.single_location_map_view_rv_card;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(singleRvCardToUse, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return listOfLocations.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder card, int position) {

        IndividualLocation locationCard = listOfLocations.get(position);

        card.nameTextView.setText(locationCard.getName());
        card.addressTextView.setText(locationCard.getAddress());
        card.phoneNumTextView.setText(locationCard.getPhoneNum());
        card.hoursTextView.setText(locationCard.getHours());
        card.distanceNumberTextView.setText(locationCard.getDistance());

        switch (selectedTheme) {
            case R.style.AppTheme_Blue:
                emojiForCircle = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pin, null);
                backgroundCircle = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_circle, null);
                setColors(R.color.colorPrimary_blue, R.color.white, R.color.white, R.color.cardHourAndPhoneTextColor_blue,
                        R.color.cardHourAndPhoneHeaderTextColor_blue, R.color.cardHourAndPhoneTextColor_blue,
                        R.color.cardHourAndPhoneHeaderTextColor_blue, R.color.white, R.color.white);
                setAlphas(card, .41f, .48f, 100f, .48f,
                        100f,
                        .41f);
                break;
        }

        card.emojiImageView.setImageDrawable(emojiForCircle);
        card.constraintUpperColorSection.setBackgroundColor(upperCardSectionColor);
        card.backgroundCircleImageView.setImageDrawable(backgroundCircle);
        card.nameTextView.setTextColor(locationNameColor);
        card.phoneNumTextView.setTextColor(locationPhoneNumColor);
        card.hoursTextView.setTextColor(locationHoursColor);
        card.hoursHeaderTextView.setTextColor(locationHoursHeaderColor);
        card.distanceNumberTextView.setTextColor(locationDistanceNumColor);
        card.milesAbbreviationTextView.setTextColor(milesAbbreviationColor);
        card.addressTextView.setTextColor(locationAddressColor);
        card.phoneHeaderTextView.setTextColor(locationPhoneHeaderColor);
    }

    private void setColors(int colorForUpperCard, int colorForName, int colorForAddress,
                           int colorForHours, int colorForHoursHeader, int colorForPhoneNum,
                           int colorForPhoneHeader, int colorForDistanceNum, int colorForMilesAbbreviation) {
        upperCardSectionColor = ResourcesCompat.getColor(context.getResources(), colorForUpperCard, null);
        locationNameColor = ResourcesCompat.getColor(context.getResources(), colorForName, null);
        locationAddressColor = ResourcesCompat.getColor(context.getResources(), colorForAddress, null);
        locationHoursColor = ResourcesCompat.getColor(context.getResources(), colorForHours, null);
        locationHoursHeaderColor = ResourcesCompat.getColor(context.getResources(), colorForHoursHeader, null);
        locationPhoneNumColor = ResourcesCompat.getColor(context.getResources(), colorForPhoneNum, null);
        locationPhoneHeaderColor = ResourcesCompat.getColor(context.getResources(), colorForPhoneHeader, null);
        locationDistanceNumColor = ResourcesCompat.getColor(context.getResources(), colorForDistanceNum, null);
        milesAbbreviationColor = ResourcesCompat.getColor(context.getResources(), colorForMilesAbbreviation, null);
    }

    private void setAlphas(ViewHolder card, float addressAlpha, float hoursHeaderAlpha, float hoursNumAlpha,
                           float phoneHeaderAlpha, float phoneNumAlpha, float milesAbbreviationAlpha) {
        card.addressTextView.setAlpha(addressAlpha);
        card.hoursHeaderTextView.setAlpha(hoursHeaderAlpha);
        card.hoursTextView.setAlpha(hoursNumAlpha);
        card.phoneHeaderTextView.setAlpha(phoneHeaderAlpha);
        card.phoneNumTextView.setAlpha(phoneNumAlpha);
        card.milesAbbreviationTextView.setAlpha(milesAbbreviationAlpha);
    }

    /**
     * The interface Click listener.
     */
    public interface ClickListener {
        /**
         * On item click.
         *
         * @param position the position
         */
        void onItemClick(int position);
    }

    /**
     * The type View holder.
     */
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * The Name text view.
         */
        TextView nameTextView;
        /**
         * The Address text view.
         */
        TextView addressTextView;
        /**
         * The Phone num text view.
         */
        TextView phoneNumTextView;
        /**
         * The Hours text view.
         */
        TextView hoursTextView;
        /**
         * The Distance number text view.
         */
        TextView distanceNumberTextView;
        /**
         * The Hours header text view.
         */
        TextView hoursHeaderTextView;
        /**
         * The Miles abbreviation text view.
         */
        TextView milesAbbreviationTextView;
        /**
         * The Phone header text view.
         */
        TextView phoneHeaderTextView;
        /**
         * The Constraint upper color section.
         */
        ConstraintLayout constraintUpperColorSection;
        /**
         * The Card view.
         */
        CardView cardView;
        /**
         * The Background circle image view.
         */
        ImageView backgroundCircleImageView;
        /**
         * The Emoji image view.
         */
        ImageView emojiImageView;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.location_name_tv);
            addressTextView = itemView.findViewById(R.id.location_description_tv);
            phoneNumTextView = itemView.findViewById(R.id.location_phone_num_tv);
            phoneHeaderTextView = itemView.findViewById(R.id.phone_header_tv);
            hoursTextView = itemView.findViewById(R.id.location_hours_tv);
            backgroundCircleImageView = itemView.findViewById(R.id.background_circle);
            emojiImageView = itemView.findViewById(R.id.emoji);
            constraintUpperColorSection = itemView.findViewById(R.id.constraint_upper_color);
            distanceNumberTextView = itemView.findViewById(R.id.distance_num_tv);
            hoursHeaderTextView = itemView.findViewById(R.id.hours_header_tv);
            milesAbbreviationTextView = itemView.findViewById(R.id.miles_mi_tv);
            cardView = itemView.findViewById(R.id.map_view_location_card);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(getLayoutPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {
        }
    }
}
