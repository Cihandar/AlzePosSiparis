package com.example.alzepossiparis.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.alzepossiparis.R;
import com.example.alzepossiparis.sqliteModels.Product;
import com.example.alzepossiparis.sqliteModels.ProductGroup;

import java.util.ArrayList;
import java.util.List;
public class ProductGroupsViewAdapter  extends  ArrayAdapter<ProductGroup> {
    public ProductGroupsViewAdapter(@NonNull Context context, List<ProductGroup> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.product_group_list_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        ProductGroup currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.product_image);
        assert currentNumberPosition != null;
        numbersImage.setImageResource(R.drawable.drink);


        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.tv_product_group_name);

        textView1.setText(currentNumberPosition.getProductName());

        // then return the recyclable view
        return currentItemView;
    }
}
