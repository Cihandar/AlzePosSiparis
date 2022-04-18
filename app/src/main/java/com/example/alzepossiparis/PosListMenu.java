package com.example.alzepossiparis;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import com.example.alzepossiparis.adapters.ProductGroupsViewAdapter;
import com.example.alzepossiparis.adapters.ProductSendViewAdapter;
import com.example.alzepossiparis.adapters.ProductViewAdapter;
import com.example.alzepossiparis.models.Adisyon;
import com.example.alzepossiparis.models.AdsSatir;
import com.example.alzepossiparis.sqliteModels.Product;
import com.example.alzepossiparis.sqliteModels.ProductGroup;
import com.example.alzepossiparis.tools.SpTools;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.orm.query.Select;


import java.util.ArrayList;
import java.util.List;

public class PosListMenu extends AppCompatActivity {

      Toolbar mToolBar;
      Button  btnCategory;
      EditText searchText,txtExplanation;
      ListView lstProduct;
      ListView lstview;
      ProductViewAdapter prAdapter;
      ProductGroupsViewAdapter grAdapter;
      TextView tvProductName,tvProductCount;
      Button btnAddProduct,btnAddMore,btnRemoveMore,btnProductClose;
      TextView tvCart;
      SpTools spTools;
      List<AdsSatir> lstAdsSatir;
      Adisyon adisyon;
    Button btnSendProduct ;
    Button btnSendCancel ;
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
        lstProduct = findViewById(R.id.lstviewProdcut);
        GetProduct("1=1");
        lstProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openFragmentProduct(prAdapter.getItem(position));
                //System.out.println(prAdapter.getItem(position).getProductId());
            }
        });

        spTools = new SpTools(this);
        lstAdsSatir = new ArrayList<>();
        adisyon = new Adisyon();
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        setTitle("Ürünler");
        getMenuInflater().inflate(R.menu.product_menu,menu);

        MenuItem searchItem =  menu.findItem(R.id.product_app_bar_search);
        SearchView searchText = (SearchView) searchItem.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
              //  GetProduct("product_name like '%"+s+"%'");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                GetProduct("product_name like '%"+s+"%'");
                return false;
            }
        });

       // getMenuInflater().inflate(R.menu.product_menu,menu);
        MenuItem CartItem = menu.findItem(R.id.product_cart);
        MenuItemCompat.setActionView(CartItem,R.layout.actionbar_badge_layout);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(CartItem);

        tvCart = (TextView) notifCount.findViewById(R.id.tv_cart_notification);
        tvCart.setText("0");

        ImageView image = notifCount.findViewById(R.id.burayatikla);

        image.setOnClickListener(view -> {OpenCart();});

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.product_app_bar_groups:
                openFragmentGroups();
                return true;
            case R.id.product_app_bar_allproduct:
                GetProduct("1=1");
                return true;
            case R.id.product_cart:
                OpenCart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void GetProduct(String Query)
    {
        List<Product> product = Select.from(Product.class).where(Query).orderBy("product_name").list();
       // List<String> pr = new ArrayList<>();
        prAdapter = new ProductViewAdapter(this,product);
        /*for (Product x: product) {
            pr.add(x.getProductName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,pr);*/
        lstProduct.setAdapter(prAdapter);
    }

    void openFragmentGroups()
    {
        View view = getLayoutInflater().inflate(R.layout.fragment_select_category, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(GetCategory(view));
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetProduct("group_code='"+grAdapter.getItem(position).getProductCode()+"'");
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    View GetCategory(View view)
    {
        lstview = view.findViewById(R.id.lstviewCategory);
        List<ProductGroup> group = Select.from(ProductGroup.class).orderBy("product_name").list();
        grAdapter = new ProductGroupsViewAdapter(this,group);
        lstview.setAdapter(grAdapter);
        return view;
    }


    void openFragmentProduct(Product product)
    {
        View view = getLayoutInflater().inflate(R.layout.fragment_add_product, null);
        Dialog dialog = new Dialog(this,R.style.DialogStyle);
        dialog.setContentView(GetProductDialog(view,product));
        tvProductName.setText(product.getProductName());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
        dialog.getWindow().setLayout(width, height);

        btnProductClose.setOnClickListener(view1 -> {dialog.dismiss();});
        btnAddProduct.setOnClickListener(view1-> {ProductAddCart(product); dialog.dismiss(); });

        dialog.show();
    }

    View GetProductDialog(View view,Product product)
    {
        tvProductName = view.findViewById(R.id.tv_product_name);
        tvProductCount = view.findViewById(R.id.tvProductCount);
        tvProductCount.setText("1");
        btnAddMore = view.findViewById(R.id.btnAddMore);
        btnAddMore.setOnClickListener(view1 -> {ProductCounterTrigger(1);} );
        btnRemoveMore = view.findViewById(R.id.btnRemoveMore);
        btnRemoveMore.setOnClickListener(view1 -> {ProductCounterTrigger(-1);} );
        txtExplanation = view.findViewById(R.id.txtExplanation);
        btnProductClose = view.findViewById(R.id.btnProductCancel);
        btnAddProduct = view.findViewById(R.id.addProduct);

        return view;
    }

    void ProductAddCart(Product product)
    {
       AdsSatir item = new AdsSatir();
       item.Aciklama = txtExplanation.getText().toString();
       item.Adet = tryParseInt(tvProductCount.getText().toString(),1);
       item.StokId = product.getProductId();
       item.BirimFiyat = product.getProductPrice();
       item.StokAdi = product.getProductName();
       lstAdsSatir.add(item);
       tvCart.setText(String.valueOf(lstAdsSatir.size()));

    }

    void ProductCounterTrigger(int number)
    {
        int count = tryParseInt(tvProductCount.getText().toString(),0);
        count = count + (number);
        if(count<2) count=1;
        tvProductCount.setText(String.valueOf(count));
    }

    void OpenCart()
    {
        View view = getLayoutInflater().inflate(R.layout.fragment_send_products, null);
        Dialog dialog = new Dialog(this,R.style.DialogStyle);
        dialog.setContentView(GetOpenCartDialog(view));

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        int width = (int)(getResources().getDisplayMetrics().widthPixels);
        int height = (int)(getResources().getDisplayMetrics().heightPixels);
        dialog.getWindow().setLayout(width, height);
        btnSendCancel.setOnClickListener(view1->{dialog.dismiss();});

        dialog.show();
    }

    View GetOpenCartDialog(View view)
    {
        TextView tvTitleAndTotal = view.findViewById(R.id.tv_send_title);
        tvTitleAndTotal.setText("Sipariş Sepeti");
        ListView lstsended = view.findViewById(R.id.lv_send_listview);
        ProductSendViewAdapter snAdapter = new ProductSendViewAdapter(this,lstAdsSatir);
        lstsended.setAdapter(snAdapter);
          btnSendProduct = view.findViewById(R.id.btn_send_product);
          btnSendCancel = view.findViewById(R.id.btn_send_cancel);
        return view;
    }

    public int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }






}