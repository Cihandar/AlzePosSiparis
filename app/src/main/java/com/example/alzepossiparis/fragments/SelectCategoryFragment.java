package com.example.alzepossiparis.fragments;

import android.icu.lang.UCharacter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.alzepossiparis.R;
import com.example.alzepossiparis.sqliteModels.Groups;
import com.example.alzepossiparis.sqliteModels.ProductGroup;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;


public class SelectCategoryFragment extends BottomSheetDialogFragment {


    public SelectCategoryFragment() {
        // Required empty public constructor
    }

    ListView  lstview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        init();
        return inflater.inflate(R.layout.fragment_select_category, container, false);


    }

    void init()
    {
        System.out.println("Ben Buraya Girdim KArdeş....");
        lstview = getView().findViewById(R.id.lstviewCategory);
        GetCategory();

    }


    void GetCategory()
    {
        List<ProductGroup> group = Select.from(ProductGroup.class).orderBy("groupName").list();
        List<String> g = new ArrayList<>();

        for (ProductGroup gr: group) {
            g.add(gr.getProductName());
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,g);

        lstview.setAdapter(adapter);

    }

}