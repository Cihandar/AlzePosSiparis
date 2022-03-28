package com.example.alzepossiparis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.RestrictionEntry;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alzepossiparis.applications.ApiSettingCrud;
import com.example.alzepossiparis.models.ApiResult;
import com.example.alzepossiparis.sqliteModels.ApiSetting;
import com.example.alzepossiparis.sqliteModels.Groups;
import com.example.alzepossiparis.tools.ConstantsPos;
import com.example.alzepossiparis.tools.ToolProgressBar;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class OptionsPos extends AppCompatActivity {

    EditText txtApiUrl;
    EditText txtApiUsername;
    EditText txtApiPassword;
    AppCompatButton btnTest,btnSave,btnGetConstants;
    RequestQueue mQueue;
    Context context =this;
    ProgressDialog progressDialog;
    ToolProgressBar toolProgressBar;
    ObjectMapper mapper;
    ApiSettingCrud _apiSettingCrud;
    ConstantsPos _constantsPos;
    void init()
    {
        txtApiUrl = findViewById(R.id.txtApiUrl);
        txtApiUsername = findViewById(R.id.txtApiUsername);
        txtApiPassword = findViewById(R.id.txtApiPassword);

        btnTest = findViewById(R.id.btnApiTest);
        btnTest.setOnClickListener(view -> { testApi(); });

        btnSave = findViewById(R.id.btnApiSave);
        btnSave.setOnClickListener(view -> {saveApi();});

        btnGetConstants =  findViewById(R.id.btnGetConstants);
        btnGetConstants.setOnClickListener(view->{getConstants();});


        mQueue = Volley.newRequestQueue(OptionsPos.this);
        toolProgressBar = new ToolProgressBar(OptionsPos.this);
        mapper = new ObjectMapper();
        _apiSettingCrud = new ApiSettingCrud();
        _constantsPos = new ConstantsPos(OptionsPos.this);
        getApiData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_pos);

        init();

    }

    void getConstants()
    {
        try {
            Groups.deleteAll(Groups.class);
        }catch (Exception e) {}

        _constantsPos.pullGroups();
    }


    void getApiData()
    {
        List<ApiSetting> getApiSetting = _apiSettingCrud.getAll();
            for (ApiSetting ob:getApiSetting) {
                txtApiUrl.setText(ob.getApiUrl());
                txtApiUsername.setText(ob.getApiUsername());
                txtApiPassword.setText(ob.getApiPassword());
            }
    }

    void saveApi()
    {
        progressDialog = toolProgressBar.showProgress("Lütfen Bekleyin", "Api Ayarları Kayıt Ediliyor..", R.style.AppTheme_Dark_Dialog);
        progressDialog.show();
        try {
            ApiSetting.deleteAll(ApiSetting.class);
            ApiSetting apiSetting = new ApiSetting(txtApiUrl.getText().toString(),txtApiUsername.getText().toString(),txtApiPassword.getText().toString());
            apiSetting.save();
            toolProgressBar.showToast("Kayıt Başarılı");
            progressDialog.dismiss();
        }catch (Exception e)
        {
            progressDialog.dismiss();
            AlertDialog.Builder dialog = toolProgressBar.showAlertDialog("Hata",e.getMessage());
            dialog.show();

        }
    }
    void testApi()
    {
        progressDialog = toolProgressBar.showProgress("Lütfen Bekleyin", "İstek Gönderiliyor..", R.style.AppTheme_Dark_Dialog);
        progressDialog.show();
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("query","select * from otel");
            object.put("userName",txtApiUsername.getText().toString());
            object.put("password",txtApiPassword.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(txtApiUsername.getText().toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, txtApiUrl.getText().toString(), object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            ApiResult apiResult = mapper.readValue(response.toString(),ApiResult.class);
                            if(apiResult.status)
                            {
                                toolProgressBar.showToast("Bağlantı Başarılı");

                            }else{
                                AlertDialog.Builder dialog = toolProgressBar.showAlertDialog("Hata",apiResult.message);
                                dialog.show();
                            }
                        } catch (JsonProcessingException e) {
                            System.out.println(e.getMessage());
                        }
                        progressDialog.dismiss();
                      //  resultTextView.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder dialog = toolProgressBar.showAlertDialog("Hata",error.getMessage());
                dialog.show();
               // System.out.println(error.getMessage());
               // resultTextView.setText("Error getting response");
            }
        });
        mQueue.add(jsonObjectRequest);
    }
}