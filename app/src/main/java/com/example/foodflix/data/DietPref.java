/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.data;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * The type Diet pref.
 */
@IgnoreExtraProperties
public class DietPref {
    private String dietId;
    private String dietDef;
    private String resPeanut;
    private String resMilk;
    private String resWheat;
    private String resSoy;
    private String resShellFish;
    private String resEgg;
    private String resFish;
    private String resPork;
    private String resAlcohol;
    private String resPoultry;
    private String resBeef;

    /**
     * Instantiates a new Diet pref.
     */
    public DietPref() {
    }

    /**
     * Instantiates a new Diet pref.
     *
     * @param dietId       the diet id
     * @param dietDef      the diet def
     * @param resPeanut    the res peanut
     * @param resMilk      the res milk
     * @param resWheat     the res wheat
     * @param resSoy       the res soy
     * @param resShellFish the res shell fish
     * @param resEgg       the res egg
     * @param resFish      the res fish
     * @param resPork      the res pork
     * @param resAlcohol   the res alcohol
     * @param resPoultry   the res poultry
     * @param resBeef      the res beef
     */
    public DietPref(String dietId, String dietDef, String resPeanut, String resMilk, String resWheat, String resSoy, String resShellFish, String resEgg, String resFish, String resPork, String resAlcohol, String resPoultry, String resBeef) {
        this.dietId = dietId;
        this.dietDef = dietDef;
        this.resPeanut = resPeanut;
        this.resMilk = resMilk;
        this.resWheat = resWheat;
        this.resSoy = resSoy;
        this.resShellFish = resShellFish;
        this.resEgg = resEgg;
        this.resFish = resFish;
        this.resPork = resPork;
        this.resAlcohol = resAlcohol;
        this.resPoultry = resPoultry;
        this.resBeef = resBeef;
    }

    /**
     * Gets diet id.
     *
     * @return the diet id
     */
    public String getDietId() {
        return dietId;
    }

    /**
     * Sets diet id.
     *
     * @param dietId the diet id
     */
    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    /**
     * Gets diet def.
     *
     * @return the diet def
     */
    public String getDietDef() {
        return dietDef;
    }

    /**
     * Sets diet def.
     *
     * @param dietDef the diet def
     */
    public void setDietDef(String dietDef) {
        this.dietDef = dietDef;
    }

    /**
     * Gets res peanut.
     *
     * @return the res peanut
     */
    public String getResPeanut() {
        return resPeanut;
    }

    /**
     * Sets res peanut.
     *
     * @param resPeanut the res peanut
     */
    public void setResPeanut(String resPeanut) {
        this.resPeanut = resPeanut;
    }

    /**
     * Gets res milk.
     *
     * @return the res milk
     */
    public String getResMilk() {
        return resMilk;
    }

    /**
     * Sets res milk.
     *
     * @param resMilk the res milk
     */
    public void setResMilk(String resMilk) {
        this.resMilk = resMilk;
    }

    /**
     * Gets res wheat.
     *
     * @return the res wheat
     */
    public String getResWheat() {
        return resWheat;
    }

    /**
     * Sets res wheat.
     *
     * @param resWheat the res wheat
     */
    public void setResWheat(String resWheat) {
        this.resWheat = resWheat;
    }

    /**
     * Gets res soy.
     *
     * @return the res soy
     */
    public String getResSoy() {
        return resSoy;
    }

    /**
     * Sets res soy.
     *
     * @param resSoy the res soy
     */
    public void setResSoy(String resSoy) {
        this.resSoy = resSoy;
    }

    /**
     * Gets res shell fish.
     *
     * @return the res shell fish
     */
    public String getResShellFish() {
        return resShellFish;
    }

    /**
     * Sets res shell fish.
     *
     * @param resShellFish the res shell fish
     */
    public void setResShellFish(String resShellFish) {
        this.resShellFish = resShellFish;
    }

    /**
     * Gets res egg.
     *
     * @return the res egg
     */
    public String getResEgg() {
        return resEgg;
    }

    /**
     * Sets res egg.
     *
     * @param resEgg the res egg
     */
    public void setResEgg(String resEgg) {
        this.resEgg = resEgg;
    }

    /**
     * Gets res fish.
     *
     * @return the res fish
     */
    public String getResFish() {
        return resFish;
    }

    /**
     * Sets res fish.
     *
     * @param resFish the res fish
     */
    public void setResFish(String resFish) {
        this.resFish = resFish;
    }

    /**
     * Gets res pork.
     *
     * @return the res pork
     */
    public String getResPork() {
        return resPork;
    }

    /**
     * Sets res pork.
     *
     * @param resPork the res pork
     */
    public void setResPork(String resPork) {
        this.resPork = resPork;
    }

    /**
     * Gets res alcohol.
     *
     * @return the res alcohol
     */
    public String getResAlcohol() {
        return resAlcohol;
    }

    /**
     * Sets res alcohol.
     *
     * @param resAlcohol the res alcohol
     */
    public void setResAlcohol(String resAlcohol) {
        this.resAlcohol = resAlcohol;
    }

    /**
     * Gets res poultry.
     *
     * @return the res poultry
     */
    public String getResPoultry() {
        return resPoultry;
    }

    /**
     * Sets res poultry.
     *
     * @param resPoultry the res poultry
     */
    public void setResPoultry(String resPoultry) {
        this.resPoultry = resPoultry;
    }

    /**
     * Gets res beef.
     *
     * @return the res beef
     */
    public String getResBeef() {
        return resBeef;
    }

    /**
     * Sets res beef.
     *
     * @param resBeef the res beef
     */
    public void setResBeef(String resBeef) {
        this.resBeef = resBeef;
    }
}
