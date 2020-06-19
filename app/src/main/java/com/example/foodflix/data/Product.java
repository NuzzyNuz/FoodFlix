/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.data;

/**
 * The type Product.
 */
public class Product {
    private String productCode;
    private String productName;
    private String productCategory;
    private String peanut;
    private String milk;
    private String wheat;
    private String soy;
    private String shellFish;
    private String egg;
    private String fish;
    private String pork;
    private String alcohol;
    private String poultry;
    private String beef;

    /**
     * Instantiates a new Product.
     */
    public Product() {

    }

    /**
     * Instantiates a new Product.
     *
     * @param productCode     the product code
     * @param productName     the product name
     * @param productCategory the product category
     * @param peanut          the peanut
     * @param milk            the milk
     * @param wheat           the wheat
     * @param soy             the soy
     * @param shellFish       the shell fish
     * @param egg             the egg
     * @param fish            the fish
     * @param pork            the pork
     * @param alcohol         the alcohol
     * @param poultry         the poultry
     * @param beef            the beef
     */
    public Product(String productCode, String productName, String productCategory, String peanut, String milk, String wheat, String soy, String shellFish, String egg, String fish, String pork, String alcohol, String poultry, String beef) {
        this.productCode = productCode;
        this.productName = productName;
        this.productCategory = productCategory;
        this.peanut = peanut;
        this.milk = milk;
        this.wheat = wheat;
        this.soy = soy;
        this.shellFish = shellFish;
        this.egg = egg;
        this.fish = fish;
        this.pork = pork;
        this.alcohol = alcohol;
        this.poultry = poultry;
        this.beef = beef;
    }

    /**
     * Gets product code.
     *
     * @return the product code
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets product code.
     *
     * @param productCode the product code
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets product name.
     *
     * @param productName the product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets product category.
     *
     * @return the product category
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * Sets product category.
     *
     * @param productCategory the product category
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * Gets peanut.
     *
     * @return the peanut
     */
    public String getPeanut() {
        return peanut;
    }

    /**
     * Sets peanut.
     *
     * @param peanut the peanut
     */
    public void setPeanut(String peanut) {
        this.peanut = peanut;
    }

    /**
     * Gets milk.
     *
     * @return the milk
     */
    public String getMilk() {
        return milk;
    }

    /**
     * Sets milk.
     *
     * @param milk the milk
     */
    public void setMilk(String milk) {
        this.milk = milk;
    }

    /**
     * Gets wheat.
     *
     * @return the wheat
     */
    public String getWheat() {
        return wheat;
    }

    /**
     * Sets wheat.
     *
     * @param wheat the wheat
     */
    public void setWheat(String wheat) {
        this.wheat = wheat;
    }

    /**
     * Gets soy.
     *
     * @return the soy
     */
    public String getSoy() {
        return soy;
    }

    /**
     * Sets soy.
     *
     * @param soy the soy
     */
    public void setSoy(String soy) {
        this.soy = soy;
    }

    /**
     * Gets shell fish.
     *
     * @return the shell fish
     */
    public String getShellFish() {
        return shellFish;
    }

    /**
     * Sets shell fish.
     *
     * @param shellFish the shell fish
     */
    public void setShellFish(String shellFish) {
        this.shellFish = shellFish;
    }

    /**
     * Gets egg.
     *
     * @return the egg
     */
    public String getEgg() {
        return egg;
    }

    /**
     * Sets egg.
     *
     * @param egg the egg
     */
    public void setEgg(String egg) {
        this.egg = egg;
    }

    /**
     * Gets fish.
     *
     * @return the fish
     */
    public String getFish() {
        return fish;
    }

    /**
     * Sets fish.
     *
     * @param fish the fish
     */
    public void setFish(String fish) {
        this.fish = fish;
    }

    /**
     * Gets pork.
     *
     * @return the pork
     */
    public String getPork() {
        return pork;
    }

    /**
     * Sets pork.
     *
     * @param pork the pork
     */
    public void setPork(String pork) {
        this.pork = pork;
    }

    /**
     * Gets alcohol.
     *
     * @return the alcohol
     */
    public String getAlcohol() {
        return alcohol;
    }

    /**
     * Sets alcohol.
     *
     * @param alcohol the alcohol
     */
    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    /**
     * Gets poultry.
     *
     * @return the poultry
     */
    public String getPoultry() {
        return poultry;
    }

    /**
     * Sets poultry.
     *
     * @param poultry the poultry
     */
    public void setPoultry(String poultry) {
        this.poultry = poultry;
    }

    /**
     * Gets beef.
     *
     * @return the beef
     */
    public String getBeef() {
        return beef;
    }

    /**
     * Sets beef.
     *
     * @param beef the beef
     */
    public void setBeef(String beef) {
        this.beef = beef;
    }
}
