package com.example.alzepossiparis.sqliteModels;

import com.orm.SugarRecord;

//select STOKGRUPID,KODU,ADI from STOKGRUP WHERE TURU='URUN'
public class ProductGroup extends SugarRecord<ProductGroup> {
    int productGroupId;
    String productCode;
    String productName;

    public ProductGroup(){}

    public ProductGroup(int productGroupId,String productCode,String productName) {
        this.productGroupId = productGroupId;
        this.productCode=productCode;
        this.productName =productName;
    }

    public int getProductGroupId() {
        return productGroupId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductGroupId(int productGroupId) {
        this.productGroupId = productGroupId;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
