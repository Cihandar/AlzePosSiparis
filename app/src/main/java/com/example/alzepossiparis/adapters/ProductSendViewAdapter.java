package com.example.alzepossiparis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.alzepossiparis.R;
import com.example.alzepossiparis.models.AdsSatir;
import com.example.alzepossiparis.sqliteModels.Product;

import java.util.List;

public class ProductSendViewAdapter extends ArrayAdapter<AdsSatir> {
    public ProductSendViewAdapter(@NonNull Context context, List<AdsSatir> arrayList) {

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
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.product_send_list_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        AdsSatir currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.product_image);
        assert currentNumberPosition != null;





        // then according to the position of the view assign the desired TextView 1 for the same
        TextView stokadi = currentItemView.findViewById(R.id.tv_lv_send_product_name);
        stokadi.setText(currentNumberPosition.StokAdi);
        TextView stokadet = currentItemView.findViewById(R.id.tv_product_count);
        stokadet.setText(String.valueOf(currentNumberPosition.Adet));
        TextView stokfiyat = currentItemView.findViewById(R.id.tv_send_product_price);
        stokfiyat.setText(String.valueOf(currentNumberPosition.Adet* currentNumberPosition.BirimFiyat)+"₺");
        // then return the recyclable view
        return currentItemView;
    }

}
