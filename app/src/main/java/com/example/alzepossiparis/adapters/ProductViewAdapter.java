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

import java.util.ArrayList;
import java.util.List;

public class ProductViewAdapter  extends ArrayAdapter<Product> {
    // invoke the suitable constructor of the ArrayAdapter class
    public ProductViewAdapter(@NonNull Context context, List<Product> arrayList) {

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
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.product_list_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Product currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.product_image);
        assert currentNumberPosition != null;
        switch (currentNumberPosition.getProductGGRUP()) {
            case "12":
                numbersImage.setImageResource(R.drawable.drink);
                break;
            default:
                numbersImage.setImageResource(R.drawable.food);
                break;
        }




        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.tv_product_name);

        textView1.setText(currentNumberPosition.getProductName());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.tv_product_price);
        textView2.setText(currentNumberPosition.getProductPrice() + " ₺");

        // then return the recyclable view
        return currentItemView;
    }
}
