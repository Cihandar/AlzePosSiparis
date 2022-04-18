package com.example.alzepossiparis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.alzepossiparis.applications.ApiSettingCrud;
import com.example.alzepossiparis.applications.VolleyCrud;
import com.example.alzepossiparis.models.Depart;
import com.example.alzepossiparis.models.Garson;
import com.example.alzepossiparis.models.RequestModel;
import com.example.alzepossiparis.models.VolleyCallback;
import com.example.alzepossiparis.tools.CreateRequestModel;
import com.example.alzepossiparis.tools.SpTools;
import com.example.alzepossiparis.tools.ToolProgressBar;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class SelectDepartment extends AppCompatActivity {

    GridLayout gridDepartment;
    ApiSettingCrud _apiSettingCrud;
    VolleyCrud _voleyCrud;
    ToolProgressBar _tools;
    RequestQueue mQueue;
    ObjectMapper mapper;
    ProgressDialog progressDialog;
    CreateRequestModel _createRequestModel;
    SpTools spTools;
    void init()
    {
        gridDepartment = findViewById(R.id.gridDepartment);
        _apiSettingCrud = new ApiSettingCrud();
        _voleyCrud = new VolleyCrud(SelectDepartment.this);
        mQueue  = Volley.newRequestQueue(this);
        mapper = new ObjectMapper();
        _tools =new ToolProgressBar(SelectDepartment.this);
        _createRequestModel = new CreateRequestModel();
        gridDepartment.removeAllViews();
        spTools = new SpTools(this);
        getDepartment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_department);
        init();
    }

    void getDepartment()
    {
        progressDialog = _tools.showProgress("Lütfen Bekleyin", "Departmanlar Getiriliyor..", R.style.AppTheme_Dark_Dialog);
        progressDialog.show();

        RequestModel requestModel = _createRequestModel.Create("select KODU,ADI from DEPART WHERE TUR='B'");
        if(requestModel!=null)
        {
            String jsonString="";
            try {
                jsonString = mapper.writeValueAsString(requestModel);
                mQueue.add(_voleyCrud.sendRequest(requestModel.apiUrl, jsonString, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            List<Depart> depart = mapper.readValue(result, new TypeReference<List<Depart>>() {
                            });
                            progressDialog.dismiss();
                            if(depart.size()>0)
                            {

                                    createCardView(depart);

                            }else
                            {
                                AlertDialog.Builder dialog = _tools.showAlertDialog("Bulamadım","Gösterilecek Departman Yok");
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

    void createCardView(List<Depart> depart)
    {
        gridDepartment.removeAllViews();
        CardView cardView;
        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;
        int countId=300000;
        for (Depart dp: depart) {
            countId++;
            cardView = new CardView(this);
            linearLayout = new LinearLayout(this);
            imageView = new ImageView(this);
            textView = new TextView(this);

            //Cardview Style
            GridLayout.LayoutParams gridLytprms = new GridLayout.LayoutParams();
            gridLytprms.height = GridLayout.LayoutParams.WRAP_CONTENT;
            gridLytprms.width = GridLayout.LayoutParams.MATCH_PARENT;
            gridLytprms.setMargins(_tools.setDpInt(12),_tools.setDpInt(6),_tools.setDpInt(12),_tools.setDpInt(6));
            cardView.setLayoutParams(gridLytprms);
            cardView.setElevation(_tools.setDpInt(6));
            cardView.setRadius(_tools.setDpInt(12));
            cardView.setTransitionName(dp.kodu);
            cardView.setId(countId);
            cardView.setOnClickListener(new ClickHandler());

            //LinearLayout Style
            ViewGroup.LayoutParams  crdlytprm = new GridLayout.LayoutParams();
            crdlytprm.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            crdlytprm.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setPadding(_tools.setDpInt(16),_tools.setDpInt(16),_tools.setDpInt(16),_tools.setDpInt(16));

            //ImageView Style
            LinearLayout.LayoutParams lnrlytprm = new LinearLayout.LayoutParams(_tools.setDpInt(60),_tools.setDpInt(60));
            imageView.setLayoutParams(lnrlytprm);
            imageView.setImageResource(R.drawable.restaurant);

            //TextView Style
            lnrlytprm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lnrlytprm.leftMargin = _tools.setDpInt(20);
            textView.setLayoutParams(lnrlytprm);
            textView.setTextColor(getColor(R.color.departTextColor));
            textView.setText(dp.adi);
            textView.setTextSize(_tools.setDpInt(12));


            linearLayout.addView(imageView);
            linearLayout.addView(textView);

            cardView.addView(linearLayout);

            gridDepartment.addView(cardView);

        } //foreach
    } //createCardView

    public class ClickHandler implements View.OnClickListener {

        public void onClick(View v) {
            final CardView cardView = findViewById(v.getId());
            Intent tables = new Intent(SelectDepartment.this,TablesPos.class);
            tables.putExtra("DeptCode",cardView.getTransitionName());
            spTools.PutData("Departman",cardView.getTransitionName());
            startActivity(tables);
            _tools.showToast("Seçili Departman Kodu : "+cardView.getTransitionName());

        }
    }
}