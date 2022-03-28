package com.example.alzepossiparis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.alzepossiparis.applications.ApiSettingCrud;
import com.example.alzepossiparis.applications.VolleyCrud;
import com.example.alzepossiparis.models.Depart;
import com.example.alzepossiparis.models.Masalar;
import com.example.alzepossiparis.models.RequestModel;
import com.example.alzepossiparis.models.VolleyCallback;
import com.example.alzepossiparis.sqliteModels.Groups;
import com.example.alzepossiparis.tools.CreateRequestModel;
import com.example.alzepossiparis.tools.ToolProgressBar;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class TablesPos extends AppCompatActivity {

    GridLayout gridTables;
    LinearLayout gridGroups;
    Spinner spPlace;
    EditText txtCreateTable;
    Button btnCreate;
    ApiSettingCrud _apiSettingCrud;
    VolleyCrud _voleyCrud;
    ToolProgressBar _tools;
    RequestQueue mQueue;
    ObjectMapper mapper;
    ProgressDialog progressDialog;
    CreateRequestModel _createRequestModel;
    Bundle ext;

    void init()
    {
        gridTables = findViewById(R.id.gridTables);
        gridGroups = findViewById(R.id.gridGroups);
        //  spPlace = findViewById(R.id.spPlace);
        txtCreateTable = findViewById(R.id.txtCreateTable);
        btnCreate = findViewById(R.id.btnCreate);
        _apiSettingCrud = new ApiSettingCrud();
        _voleyCrud = new VolleyCrud(TablesPos.this);
        mQueue  = Volley.newRequestQueue(this);
        mapper = new ObjectMapper();
        _tools =new ToolProgressBar(TablesPos.this);
        _createRequestModel = new CreateRequestModel();
        ext =  getIntent().getExtras();
        try {
            getGroups();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        getTables("Hepsi");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables_pos);
        init();
    }

    void getGroups()
    {
        gridGroups.removeAllViews();
        Button btn = new Button(this);
        LinearLayout.LayoutParams gridLytprms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,_tools.setDpInt(50));
        btn.setText("Hepsi");
        btn.setLayoutParams(gridLytprms);
        btn.setId(90000+1);
        btn.setAllCaps(false);
        btn.setOnClickListener( new ClickHandlerGroups());
        gridGroups.addView(btn);
        int i=0;
        for(Groups grp : Groups.listAll(Groups.class))
        {
            i++;

            btn = new Button(this);
            btn.setText(grp.getGroupName());
            btn.setLayoutParams(gridLytprms);
            btn.setId(90001+i);
            btn.setOnClickListener( new ClickHandlerGroups());
            gridGroups.addView(btn);

        }
    }

    public class ClickHandlerGroups implements View.OnClickListener {

        public void onClick(View v) {
            final Button btn = findViewById(v.getId());
            getTables(btn.getText().toString());

        }
    }

    void getTables(String groupName)
    {
        progressDialog = _tools.showProgress("Lütfen Bekleyin", "Masalar Getiriliyor..", R.style.AppTheme_Dark_Dialog);
        progressDialog.show();
        String deptCode=ext.getString("DeptCode");
        String where ="";
        if(groupName!="Hepsi") where = " WHERE GRUP='"+groupName+"' ";
        String Query= "SELECT * FROM (SELECT ISNULL(A.MASANO,M.MASANO) AS MASANO,M.ACIKLAMA,ASAATI,XPOS,YPOS,KISIS, CASE WHEN ISNULL(A.SEZLONG,0)=1 THEN 9 ELSE ISNULL(STATU,CASE WHEN (SELECT COUNT(*) FROM ADSSATIR AS S WHERE S.FISCOUNTER=A.FISCOUNTER AND ISNULL(S.YAZ,0)=0)=0 AND A.YAZCOUNT>0 THEN 2 ELSE 1 END) END AS STATU, KISI AS ADISYONKISI,ANO,TOPLAM,GARSON_KODU AS GARSON_ADI ,GRUP FROM (SELECT FISCOUNTER,ASAATI,MASANO,KISI,YAZCOUNT,ROUND(TOPLAM,2) AS TOPLAM,GARSON.GADI AS GARSON_KODU, ADISYONNO+' '+GARSONKODU AS ANO,SEZLONG FROM ADISYON  WITH (NOLOCK)  LEFT JOIN GARSON ON GARSON.GKODU = ADISYON.GARSONKODU WHERE ADISYON.DEPKODU='"+deptCode+"' AND MASANO NOT LIKE 'Z%') A FULL OUTER JOIN (SELECT MASANO,KISIS,XPOS,YPOS,STATU,ACIKLAMA,GRUP  FROM MASALAR  WITH (NOLOCK) WHERE DEPART='"+deptCode+"') M ON A.MASANO=M.MASANO) TBL "+where+"  ORDER BY MASANO";
       // System.out.println(Query);
        RequestModel requestModel = _createRequestModel.Create(Query);
        if(requestModel!=null)
        {
            String jsonString="";
            try {
                jsonString = mapper.writeValueAsString(requestModel);
                mQueue.add(_voleyCrud.sendRequest(requestModel.apiUrl, jsonString, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            List<Masalar> tables = mapper.readValue(result, new TypeReference<List<Masalar>>() {
                            });
                            progressDialog.dismiss();
                            if(tables.size()>0)
                            {
                            createTables(tables);
                            }else
                            {
                                AlertDialog.Builder dialog = _tools.showAlertDialog("Bulamadım","Gösterilecek Masa Yok");
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

    void createTables(List<Masalar> tables)
    {
        gridTables.removeAllViews();
        LinearLayout firstLayout;
        LinearLayout secondLayout;
        ImageView imageView;
        TextView txtTablesNo;
        TextView txtTableExp;

        for (Masalar tb: tables) {
            firstLayout = new LinearLayout(this);
            secondLayout = new LinearLayout(this);
            imageView = new ImageView(this);
            txtTablesNo = new TextView(this);
            txtTableExp = new TextView(this);


            //First Layout
            GridLayout.LayoutParams gridLytprms = new GridLayout.LayoutParams();
            gridLytprms.height = GridLayout.LayoutParams.WRAP_CONTENT;
            gridLytprms.width = GridLayout.LayoutParams.WRAP_CONTENT;
            gridLytprms.setMargins(_tools.setDpInt(5),_tools.setDpInt(5),_tools.setDpInt(5),_tools.setDpInt(5));
            gridLytprms.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            firstLayout.setLayoutParams(gridLytprms);
            firstLayout.setPadding(_tools.setDpInt(5),_tools.setDpInt(5),_tools.setDpInt(5),_tools.setDpInt(5));
            firstLayout.setOrientation(LinearLayout.VERTICAL);
            try {
                if(tb.gARSON_ADI!=null) firstLayout.setBackgroundResource(R.drawable.fulltable); else firstLayout.setBackgroundResource(R.drawable.empty_table);

            }catch (Exception ex)
            {
                System.out.println("Hata->" + ex.getMessage());
            }

            //Second Layout
            secondLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            secondLayout.setOrientation(LinearLayout.VERTICAL);
            secondLayout.setPadding(_tools.setDpInt(1),_tools.setDpInt(1),_tools.setDpInt(1),_tools.setDpInt(1));

            //ImageView
            LinearLayout.LayoutParams lnrLayout =new LinearLayout.LayoutParams(_tools.setDpInt(40),_tools.setDpInt(60));
            lnrLayout.gravity = Gravity.CENTER;
            imageView.setLayoutParams(lnrLayout);
            if(tb.gARSON_ADI!=null) imageView.setImageResource(R.drawable.full); else imageView.setImageResource(R.drawable.empty);

            //Tables Number
            txtTablesNo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            txtTablesNo.setTextSize(_tools.setDpInt(5));
            txtTablesNo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtTablesNo.setText(tb.mASANO);
            System.out.println("Hata->" + tb.mASANO);
            //Tables Exp
            txtTableExp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            txtTableExp.setTextSize(_tools.setDpInt(3));
            txtTableExp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtTableExp.setText(tb.gARSON_ADI);

            secondLayout.addView(imageView);
            secondLayout.addView(txtTablesNo);
            secondLayout.addView(txtTableExp);
            firstLayout.addView(secondLayout);
            gridTables.addView(firstLayout);

        }


    }

}