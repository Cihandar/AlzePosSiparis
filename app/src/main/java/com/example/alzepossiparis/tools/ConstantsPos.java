package com.example.alzepossiparis.tools;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.alzepossiparis.R;
import com.example.alzepossiparis.TablesPos;
import com.example.alzepossiparis.applications.ApiSettingCrud;
import com.example.alzepossiparis.applications.VolleyCrud;
import com.example.alzepossiparis.models.MasaGrup;
import com.example.alzepossiparis.models.Masalar;
import com.example.alzepossiparis.models.RequestModel;
import com.example.alzepossiparis.models.Stok;
import com.example.alzepossiparis.models.StokGrup;
import com.example.alzepossiparis.models.VolleyCallback;
import com.example.alzepossiparis.sqliteModels.Groups;
import com.example.alzepossiparis.sqliteModels.Product;
import com.example.alzepossiparis.sqliteModels.ProductGroup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.acl.Group;
import java.util.List;

public class ConstantsPos {
    Context _context;
    ToolProgressBar _tools;
    ApiSettingCrud _apiSettingCrud;
    VolleyCrud _voleyCrud;
    RequestQueue mQueue;
    ObjectMapper mapper;
    CreateRequestModel _createRequestModel;

    public ConstantsPos(Context context) {
        _context = context;
        _tools = new ToolProgressBar(context);
        _createRequestModel = new CreateRequestModel();
        _apiSettingCrud = new ApiSettingCrud();
        _voleyCrud = new VolleyCrud(context);
        mQueue  = Volley.newRequestQueue(context);
        mapper = new ObjectMapper();
    }

    public void pullGroups()
    {
        ProgressDialog progressDialog = _tools.showProgress("Lütfen Bekleyin", "Masa Grupları Getiriliyor..", R.style.AppTheme_Dark_Dialog);
        progressDialog.show();
        RequestModel requestModel = _createRequestModel.Create("SELECT DISTINCT GRUP FROM MASALAR WHERE GRUP IS NOT NULL");
        if(requestModel!=null)
        {
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(requestModel);
                mQueue.add(_voleyCrud.sendRequest(requestModel.apiUrl, jsonString, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            List<MasaGrup> groups = mapper.readValue(result, new TypeReference<List<MasaGrup>>() {
                            });
                            progressDialog.dismiss();
                            if(groups.size()>0)
                            {
                                try {
                                    Groups.deleteAll(Groups.class);
                                }catch (Exception e) {}

                                for(MasaGrup grp:groups)
                                {
                                    Groups newGroups = new Groups(grp.grup);
                                    newGroups.save();
                                }
                            }else
                            {

                            }
                        }catch (Exception e)
                        {
                            System.out.println("Hata->"+e.getMessage());
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(String result) {
                        System.out.println("Hata->"+result);
                        progressDialog.dismiss();
                    }
                }));

            }catch (Exception e)
            {
                System.out.println("Hata->"+e.getMessage());
                progressDialog.dismiss();
            }
        }


    }

    public void pullProductGroup()
    {
        ProgressDialog progressDialog = _tools.showProgress("Lütfen Bekleyin", "Ürün Grupları Getiriliyor..", R.style.AppTheme_Dark_Dialog);
        progressDialog.show();
        RequestModel requestModel = _createRequestModel.Create("select STOKGRUPID,KODU,ADI from STOKGRUP WHERE TURU='URUN'");
        if(requestModel!=null)
        {
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(requestModel);
                mQueue.add(_voleyCrud.sendRequest(requestModel.apiUrl, jsonString, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            List<StokGrup> groups = mapper.readValue(result, new TypeReference<List<StokGrup>>() {
                            });
                            progressDialog.dismiss();
                            if(groups.size()>0)
                            {
                                try {
                                    ProductGroup.deleteAll(ProductGroup.class);
                                }catch (Exception e) {}

                                for(StokGrup grp:groups)
                                {
                                    ProductGroup newGroups = new ProductGroup(grp.sTOKGRUPID,grp.kODU,grp.aDI);
                                    newGroups.save();
                                }
                            }else
                            {

                            }
                        }catch (Exception e)
                        {
                            System.out.println("Hata->"+e.getMessage());
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(String result) {
                        System.out.println("Hata->"+result);
                        progressDialog.dismiss();
                    }
                }));

            }catch (Exception e)
            {
                System.out.println("Hata->"+e.getMessage());
                progressDialog.dismiss();
            }
        }
    }

    public void pullProducts()
    {
        // public Product(int productId,String groupCode,String productName,double productPrice,String productCurrent,String productUnit,String productGGRUP,String productHYERI)
        ProgressDialog progressDialog = _tools.showProgress("Lütfen Bekleyin", "Ürünler Getiriliyor..", R.style.AppTheme_Dark_Dialog);
        progressDialog.show();
        RequestModel requestModel = _createRequestModel.Create("select STOKID,GRUP,ADI,SFIYAT1,SDOVIZ,BIRIM,GGRUP,HYERI from STOK WHERE TURU IN ('MAMUL','YARIMAMUL','Mamul','YarıMamul','Yarımamul')");
        if(requestModel!=null)
        {
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(requestModel);
                mQueue.add(_voleyCrud.sendRequest(requestModel.apiUrl, jsonString, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            List<Stok> stoks = mapper.readValue(result, new TypeReference<List<Stok>>() {
                            });
                            progressDialog.dismiss();
                            if(stoks.size()>0)
                            {
                                try {
                                    Product.deleteAll(Product.class);
                                }catch (Exception e) {}

                                for(Stok stk:stoks)
                                {
                                    Product newProduct = new Product(stk.sTOKID,stk.gRUP,stk.aDI,stk.sFIYAT1,stk.sDOVIZ,stk.bIRIM,stk.gGRUP, stk.hYERI);
                                    newProduct.save();
                                }
                            }else
                            {

                            }
                        }catch (Exception e)
                        {
                            System.out.println("Hata->"+e.getMessage());
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(String result) {
                        System.out.println("Hata->"+result);
                        progressDialog.dismiss();
                    }
                }));

            }catch (Exception e)
            {
                System.out.println("Hata->"+e.getMessage());
                progressDialog.dismiss();
            }
        }
    }


}
