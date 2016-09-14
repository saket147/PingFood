package com.cebs.pingfood;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    int id;
    ListView listMenu;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    Map<String,String> itemId=new HashMap<String,String>();
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Cuisine");
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


        listMenu=(ListView) findViewById(R.id.listMenu);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"Clicked",Toast.LENGTH_LONG).show();
                String menu= arrayList.get(position);
                ItemActivity.MENUID=Integer.valueOf( itemId.get(menu));
                ItemActivity.MENUNAME=menu;
                Intent i=new Intent(getBaseContext(),ItemActivity.class);
                startActivity(i);
            }
        });

        new Menu().execute();
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent i=new Intent(this, ActivityStack.pop().getClass());
                startActivity(i);
               break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    public class Menu extends AsyncTask<Void,Void,Void>
    {
        public  Menu()
        {
            Intent i = getIntent();
            Bundle b= i.getExtras();
            id=Integer.parseInt( b.getString("id"));
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try {
                JSONObject jsonObject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/menus.jsp?id="+ id);
                Log.d("Test1","Test1");
                JSONArray jsonArray = jsonObject.getJSONArray("Menu");

                Log.d("JSON",jsonArray.toString());
                arrayList=new ArrayList<String>();

                for (int i=0;i<jsonArray.length();i++)
                {
                    arrayList.add(jsonArray.getJSONObject(i).getString("menu_name"));
                    itemId.put(jsonArray.getJSONObject(i).getString("menu_name"), jsonArray.getJSONObject(i).getString("id"));
                }


            }
            catch (Exception ex)
            {
                Toast.makeText(getBaseContext(),ex+"",Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
            adapter=new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
            listMenu.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MenuActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Ping Food");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
    }
}
