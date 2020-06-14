package com.example.foodflix.data;

import com.google.firebase.database.IgnoreExtraProperties;

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

    public DietPref() {
    }

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

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public String getDietDef() {
        return dietDef;
    }

    public void setDietDef(String dietDef) {
        this.dietDef = dietDef;
    }

    public String getResPeanut() {
        return resPeanut;
    }

    public void setResPeanut(String resPeanut) {
        this.resPeanut = resPeanut;
    }

    public String getResMilk() {
        return resMilk;
    }

    public void setResMilk(String resMilk) {
        this.resMilk = resMilk;
    }

    public String getResWheat() {
        return resWheat;
    }

    public void setResWheat(String resWheat) {
        this.resWheat = resWheat;
    }

    public String getResSoy() {
        return resSoy;
    }

    public void setResSoy(String resSoy) {
        this.resSoy = resSoy;
    }

    public String getResShellFish() {
        return resShellFish;
    }

    public void setResShellFish(String resShellFish) {
        this.resShellFish = resShellFish;
    }

    public String getResEgg() {
        return resEgg;
    }

    public void setResEgg(String resEgg) {
        this.resEgg = resEgg;
    }

    public String getResFish() {
        return resFish;
    }

    public void setResFish(String resFish) {
        this.resFish = resFish;
    }

    public String getResPork() {
        return resPork;
    }

    public void setResPork(String resPork) {
        this.resPork = resPork;
    }

    public String getResAlcohol() {
        return resAlcohol;
    }

    public void setResAlcohol(String resAlcohol) {
        this.resAlcohol = resAlcohol;
    }

    public String getResPoultry() {
        return resPoultry;
    }

    public void setResPoultry(String resPoultry) {
        this.resPoultry = resPoultry;
    }

    public String getResBeef() {
        return resBeef;
    }

    public void setResBeef(String resBeef) {
        this.resBeef = resBeef;
    }
}
