package com.example.temperaturestations;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentA extends Fragment {

    ListView lv;

    SearchView searchView;
    Integer ID;
    String temp;
    String hum;
    Integer sig;
    Long unix;
    String name;
    ArrayList<MyModel> list;
    MyAdapter adapter;
    public static Timer timer;
    public static JsonArrayRequest request;
    public static RequestQueue queue;
    public FragmentA() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a, container, false);
        lv = (ListView) view.findViewById(R.id.ListView);
        list = new ArrayList<>();
        queue = Volley.newRequestQueue(this.getContext());
        String url ="http://raspberrypitemp.ddns.net:8080/api/temp";

        request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    list.clear();
                    for(int i = 0; i < response.length(); i++){
                        try {

                            JSONObject o = (JSONObject) response.get(i);
                            if(o.getString("temp") != "null") {
                                ID = o.getInt("addr");
                                temp = o.getString("temp");
                                if(o.has("name")){
                                    name = o.getString("name");
                                }else{
                                    name = "No name";
                                }
                                hum = o.getString("hum");
                                sig = o.getInt("sig");
                                unix = o.getLong("unix");
                            }
                            list.add(new MyModel(name, temp, hum, sig, unix));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Context cont = this.getContext();
                    adapter = new MyAdapter(cont,list);
                    lv.setAdapter(adapter);
                },
                error -> {
                });
        int delay = 0; // delay for 0 sec.
        int period = 1000; // repeat every 10 sec.
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                queue.add(request);
            }
        }, delay, period);


        return view;
    }
}