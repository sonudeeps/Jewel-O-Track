package com.jewel.admin.jewel_new.Adapter;

/**
 * Created by admin on 02-01-2018.
 */

public class Jewel {
    public Jewel(){

    }
    public Jewel(String jt, String ij, String p){
       this.jeweltype=jt;
       this.IJSC=ij;
       this.Price=p;

    }
    public String Certified;
    public String Color;
    public String IJSC;

    public String getCertified() {
        return Certified;
    }

    public void setCertified(String certified) {
        Certified = certified;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getIJSC() {
        return IJSC;
    }

    public void setIJSC(String IJSC) {
        this.IJSC = IJSC;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getJeweltype() {
        return jeweltype;
    }

    public void setJeweltype(String jeweltype) {
        this.jeweltype = jeweltype;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String Price;
    public String Weight;
    public String jeweltype;
    public String size;




}
