package com.example.alzepossiparis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.alzepossiparis.applications.ApiSettingCrud;
import com.example.alzepossiparis.applications.VolleyCrud;
import com.example.alzepossiparis.models.ApiResult;
import com.example.alzepossiparis.models.Garson;
import com.example.alzepossiparis.models.RequestModel;
import com.example.alzepossiparis.models.VolleyCallback;
import com.example.alzepossiparis.sqliteModels.ApiSetting;
import com.example.alzepossiparis.tools.CreateRequestModel;
import com.example.alzepossiparis.tools.ToolProgressBar;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.util.List;

public class Login extends AppCompatActivity {
    EditText txtWaiterPassword;
    AppCompatButton btnLogin;
    ImageView btnOptions;
    ApiSettingCrud _apiSettingCrud;
    VolleyCrud _voleyCrud;
    ToolProgressBar _tools;
    CreateRequestModel _createRequestModel;
    RequestQueue mQueue;
    ObjectMapper mapper;
    ProgressDialog progressDialog;

    void init ()
    {
        txtWaiterPassword = findViewById(R.id.txtWaiterPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {loginWaiters();});
        btnOptions = findViewById(R.id.btnOptions);
        btnOptions.setOnClickListener(view -> {openOptionsPos();});
        _apiSettingCrud = new ApiSettingCrud();
        _voleyCrud = new VolleyCrud(Login.this);
        mQueue  = Volley.newRequestQueue(this);
        mapper = new ObjectMapper();
        _tools =new ToolProgressBar(Login.this);
        _createRequestModel = new CreateRequestModel();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    void openOptionsPos()
    {
        Intent optpos = new Intent(Login.this,OptionsPos.class);
        startActivity(optpos);
    }

    void loginWaiters()
    {
        progressDialog = _tools.showProgress("Lütfen Bekleyin", "Giriş Yapılıyor..", R.style.AppTheme_Dark_Dialog);
        progressDialog.show();

        RequestModel requestModel = _createRequestModel.Create("select GKODU,GADI,DEPKODU from GARSON WHERE GSIFRE='"+ txtWaiterPassword.getText().toString() + "'");
        if(requestModel!=null)
        {
            String jsonString="";
            try {
                jsonString = mapper.writeValueAsString(requestModel);
                mQueue.add(_voleyCrud.sendRequest(requestModel.apiUrl, jsonString, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            List<Garson> garson = mapper.readValue(result, new TypeReference<List<Garson>>() {
                            });
                            progressDialog.dismiss();
                            if(garson.size()>0)
                            {
                                _tools.showToast("Hoşgeldin, "+garson.get(0).gAdi);
                                Intent selectdpt = new Intent(Login.this,SelectDepartment.class);
                                startActivity(selectdpt);

                            }else
                            {
                                AlertDialog.Builder dialog = _tools.showAlertDialog("Hata","Şifre Yanlış.");
                                dialog.show();
                            }
                        }catch (Exception e)
                        {
                            System.out.println("Hata->"+e.getMessage());
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(String result) {

                    }
                }));

            }catch (Exception e)
            {

            }






        }

    }

}