package com.cebs.pingfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseItemActivity extends AppCompatActivity
{
    public static int ITEMID;
    public static String ITEMNAME,ITEMUNIT;
    public static String ITEMDESCRIPTION;
    public static double ITEMPRICE,ITEMTOTAL;
    public static String ITEMLOGO;
    ProgressDialog mProgressDialog;
    TextView txtDescription,txtName,txtPrice,txtTotal;
    EditText txtQty;
    ImageLoader imageLoader;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);


        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(ITEMNAME);
        txtName=(TextView)findViewById(R.id.lblName);
        txtDescription=(TextView)findViewById(R.id.lblDescription);
        txtPrice=(TextView)findViewById(R.id.lblPrice);
        txtQty=(EditText) findViewById(R.id.qty);
        txtTotal=(TextView) findViewById(R.id.lblTotal);
        logo=(ImageView)findViewById(R.id.logo);
        imageLoader = new ImageLoader(getBaseContext());
        imageLoader.DisplayImage(ITEMLOGO,logo);
        txtName.setText(ITEMNAME);
        txtDescription.setText(ITEMDESCRIPTION);
        txtPrice.setText("Rs. "+ITEMPRICE+" | "+ITEMUNIT);
        txtTotal.setText(ITEMPRICE+"");
        FloatingActionButton button=(FloatingActionButton)findViewById(R.id.cart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ChooseItemActivity.this,OrderActivity.class);
                startActivity(i);
            }
        });
        TextView title=(TextView)findViewById(R.id.carttitle);
        if(Cart.getSize()>0)
        {
            title.setText(Cart.getSize()+"");
            title.setVisibility(View.VISIBLE);
            button.setVisibility(FloatingActionButton.VISIBLE);
        }
        else
        {
            title.setVisibility(View.GONE);
            button.setVisibility(FloatingActionButton.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        FloatingActionButton button=(FloatingActionButton)findViewById(R.id.cart);
        TextView title=(TextView)findViewById(R.id.carttitle);
        if(Cart.getSize()>0)
        {
            title.setText(Cart.getSize()+"");
            title.setVisibility(View.VISIBLE);
            button.setVisibility(FloatingActionButton.VISIBLE);
        }
        else
        {
            title.setVisibility(View.GONE);
            button.setVisibility(FloatingActionButton.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public  void add(View view)
    {
        int qty=Integer.parseInt(txtQty.getText().toString());
        qty++;
        ITEMTOTAL=ITEMPRICE*qty;
        if(qty>0)
        {
            txtQty.setText(qty+"");

        }
        else
        {
            ITEMTOTAL=ITEMPRICE;
            txtQty.setText("1");
        }
        txtTotal.setText(ITEMTOTAL+"");
    }
    public void minus(View view)
    {
        int qty=Integer.parseInt(txtQty.getText().toString());
        qty--;
        ITEMTOTAL=ITEMPRICE*qty;
        if(qty>0)
        {
            txtQty.setText(qty+"");
        }
        else
        {
            txtQty.setText("1");
            ITEMTOTAL=ITEMPRICE;
        }
        txtTotal.setText(ITEMTOTAL+"");
    }
    public  void addToCart(View view)
    {
        FoodItem fi=new FoodItem();
        fi.setId(ITEMID);
        fi.setName(ITEMNAME);
        fi.setPrice(ITEMPRICE);
        fi.setQty(Integer.parseInt(txtQty.getText().toString()));
        fi.setTotal(ITEMPRICE*fi.getQty());
        Cart.add(fi);
        FloatingActionButton button=(FloatingActionButton)findViewById(R.id.cart);
        TextView title=(TextView)findViewById(R.id.carttitle);
        if(Cart.getSize()>0)
        {
            title.setText(Cart.getSize()+"");
            title.setVisibility(View.VISIBLE);
            button.setVisibility(FloatingActionButton.VISIBLE);
        }
        else
        {
            title.setVisibility(View.GONE);
            button.setVisibility(FloatingActionButton.GONE);
        }

    }

}
