package com.cebs.pingfood;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {

    JSONObject jsonobject;
    JSONArray jsonarray;
    Button btnSearch;
    ProgressDialog mProgressDialog;
    ArrayList<String> arraylist,stateid,citylist,cityid;
    View view;

    Spinner state,city;
    public static int CITYID;
    public AddressFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_address, container, false);
        state= (Spinner)view.findViewById(R.id.state);
        city= (Spinner)view.findViewById(R.id.city);
        btnSearch=(Button)view.findViewById(R.id.btnSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    RestaurantFragment restaurantFragment=new RestaurantFragment();
                    FragmentStack.push(AddressFragment.this);
                    fragmentManager.beginTransaction().replace(R.id.flContent, restaurantFragment).commit();
                }
                catch (Exception ex){

                }
            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid=Integer.parseInt(stateid.get(position));
                new City_List(sid).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CITYID=Integer.parseInt(cityid.get( city.getSelectedItemPosition()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new state_list().execute();
        return view;
    }
    public class City_List extends AsyncTask<Void,Void,Void>
    {
        int sid;

        public City_List(int sid) {
            this.sid = sid;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Ping Food");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();;
            ArrayAdapter< String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,citylist);
            city.setAdapter(adapter);


        }

        @Override
        protected Void doInBackground(Void... params)
        {
            citylist=new ArrayList<String>();
            cityid=new ArrayList<String>();
            jsonobject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/cities.jsp?id="+sid);

            try {
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("Cities");
                Log.d("JSON", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    //HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);


                    // Retrieve JSON Objects
                    //map.put("id", jsonobject.getString("id"));
                    //map.put("state_name",jsonobject.getString("state_name"));

                    citylist.add(jsonobject.getString("city_name"));
                    cityid.add(jsonobject.getString("id"));
                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }
    public class state_list extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Ping Food");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();;;;;
            if(result)
            {
                ArrayAdapter< String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,arraylist);
                state.setAdapter(adapter);

            }
// Specify the layout to use when the list of choices appears



        }

        @Override
        protected Boolean doInBackground(Void...params) {
            // Create an array
            arraylist = new ArrayList<String>();
            stateid = new ArrayList<String>();
            // Retrieve JSON Objects from the given URL address
            jsonobject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/states.jsp");

            try {
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("States");
                Log.d("JSON",jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    //HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);


                    // Retrieve JSON Objects
                    //map.put("id", jsonobject.getString("id"));
                    //map.put("state_name",jsonobject.getString("state_name"));

                    arraylist.add(jsonobject.getString("state_name"));
                    stateid.add(jsonobject.getString("id"));
                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return true;
        }
    }

}
