package com.example.alzepossiparis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class PosListMenu extends AppCompatActivity {

    private Toolbar mToolBar;
    private Button  btnCategory;
    private EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_list_menu);
        init();
    }

    public void init()
    {
        mToolBar = findViewById(R.id.product_toolbar);
        setSupportActionBar(mToolBar);
        searchText = findViewById(R.id.product_app_bar_search);
        btnCategory = findViewById(R.id.product_app_bar_groups);
        btnCategory.setOnClickListener(view -> {openFragmentGroups();});

    }

    void openFragmentGroups()
    {
        View view = getLayoutInflater().inflate(R.layout.fragment_select_category,null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
    }


    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        setTitle("Ürünler");
        getMenuInflater().inflate(R.menu.product_menu,menu);
        return true;
    }

}