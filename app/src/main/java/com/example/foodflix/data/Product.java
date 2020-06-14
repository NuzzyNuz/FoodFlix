package com.example.foodflix.data;

public class Product {
    private String productCode;
    private String productName;
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

    public Product() {

    }

    public Product(String productCode, String productName, String peanut, String milk, String wheat, String soy, String shellFish, String egg, String fish, String pork, String alcohol, String poultry, String beef) {
        this.productCode = productCode;
        this.productName = productName;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPeanut() {
        return peanut;
    }

    public void setPeanut(String peanut) {
        this.peanut = peanut;
    }

    public String getMilk() {
        return milk;
    }

    public void setMilk(String milk) {
        this.milk = milk;
    }

    public String getWheat() {
        return wheat;
    }

    public void setWheat(String wheat) {
        this.wheat = wheat;
    }

    public String getSoy() {
        return soy;
    }

    public void setSoy(String soy) {
        this.soy = soy;
    }

    public String getShellFish() {
        return shellFish;
    }

    public void setShellFish(String shellFish) {
        this.shellFish = shellFish;
    }

    public String getEgg() {
        return egg;
    }

    public void setEgg(String egg) {
        this.egg = egg;
    }

    public String getFish() {
        return fish;
    }

    public void setFish(String fish) {
        this.fish = fish;
    }

    public String getPork() {
        return pork;
    }

    public void setPork(String pork) {
        this.pork = pork;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getPoultry() {
        return poultry;
    }

    public void setPoultry(String poultry) {
        this.poultry = poultry;
    }

    public String getBeef() {
        return beef;
    }

    public void setBeef(String beef) {
        this.beef = beef;
    }
}
