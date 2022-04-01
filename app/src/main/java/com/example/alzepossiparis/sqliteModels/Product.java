package com.example.alzepossiparis.sqliteModels;

import com.orm.SugarRecord;

//select STOKID,GRUP,ADI,SFIYAT1,SDOVIZ,BIRIM,GGRUP,HYERI from STOK WHERE TURU IN ('MAMUL','YARIMAMUL','Mamul','YarıMamul','Yarımamul')
public class Product extends SugarRecord<Product> {

    int productId;
    String groupCode;
    String productName;
    double productPrice;
    String productCurrent;
    String productUnit;
    String productGGRUP;
    String productHYERI;

    public Product() {}

    public Product(int productId,String groupCode,String productName,double productPrice,String productCurrent,String productUnit,String productGGRUP,String productHYERI) {
        this.productId = productId;
        this.productName =productName;
        this.groupCode = groupCode;
        this.productPrice =productPrice;
        this.productCurrent = productCurrent;
        this.productUnit =productName;
        this.productGGRUP = productGGRUP;
        this.productHYERI =productHYERI;
    }

    public int getProductId() {
        return productId;
    }
    public String getProductName() { return productName;}
    public String getGroupCode() { return groupCode;}
    public double getProductPrice() { return productPrice;}
    public String getProductCurrent() { return productCurrent;}
    public String getProductUnit() { return productUnit;}
    public String getProductGGRUP() { return productGGRUP;}
    public String getProductHYERI() { return productHYERI;}

    public void setProductId(int productId) {
        this.productId = productId;
    }
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public void setProductCurrent(String productCurrent) {
        this.productCurrent = productCurrent;
    }
    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }
    public void setProductGGRUP(String productGGRUP) {
        this.productGGRUP = productGGRUP;
    }
    public void setProductHYERI(String productHYERI) {
        this.productHYERI = productHYERI;
    }

}
