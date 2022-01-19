package com.andrew.codingtest.data;

/**
 * Created by andrew.liu on 2022/1/18.
 */
public class Product {
    private ProductType productType = ProductType.DAY_PASS;
    private PassType passType = null;
    private boolean purchaseStatus = false;
    private String passActivatedTime;
    private String passExpiredTime;

    public Product(ProductType productType, PassType passType) {
        this.productType = productType;
        this.passType = passType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public PassType getPassType() {
        return passType;
    }

    public void setPassType(PassType passType) {
        this.passType = passType;
    }

    public boolean isPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(boolean purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public String getPassActivatedTime() {
        return passActivatedTime;
    }

    public void setPassActivatedTime(String passActivatedTime) {
        this.passActivatedTime = passActivatedTime;
    }

    public String getPassExpiredTime() {
        return passExpiredTime;
    }

    public void setPassExpiredTime(String passExpiredTime) {
        this.passExpiredTime = passExpiredTime;
    }

    public enum ProductType {
        DAY_PASS,
        HOUR_PASS,
    }

    public static class PassType {

        String passName;
        int passPrice;
        int passRange;

        public PassType(String name, int range, int price) {
            passName = name;
            passPrice = price;
            passRange = range;
        }

        public String getPassName() {
            return passName;
        }

        public int getPassPrice() {
            return passPrice;
        }

        public int getPassRange() {
            return passRange;
        }
    }
}
