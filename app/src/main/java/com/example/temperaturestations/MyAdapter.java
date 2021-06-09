package com.example.temperaturestations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.temperaturestations.FragmentA.queue;
import static com.example.temperaturestations.FragmentA.timer;
import static com.example.temperaturestations.FragmentA.request;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<MyModel> list;

    public MyAdapter(Context context, ArrayList<MyModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        setUpData(view,position);
        Button edit = view.findViewById(R.id.editButton);
        TextInputLayout editTxt = view.findViewById(R.id.editText);
        TextView name = view.findViewById(R.id.sensor_name);
        edit.setOnClickListener(v -> {
            name.setVisibility(View.GONE);
            editTxt.setVisibility(view.VISIBLE);
            timer.cancel();
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask()
            {
                public void run()
                {
                    queue.add(request);
                }
            }, 0, 1000);
        });
        return view;
    }

    private void setUpData(View view, final int position) {
        LinearLayout linear_layout_id = view.findViewById(R.id.linear_layout_id);
        TextInputLayout editTxt = view.findViewById(R.id.editText);
        TextView name = view.findViewById(R.id.sensor_name);
        TextView temp = view.findViewById(R.id.sensor_temp);
        TextView hum = view.findViewById(R.id.sensor_hum);
        ImageView sig = view.findViewById(R.id.signal_id);
        TextView date = view.findViewById(R.id.sensor_date);
        name.setText(list.get(position).getName());
        temp.setText(list.get(position).getTemp());
        hum.setText(list.get(position).getHum());


        Date newDate = new Date(list.get(position).getUnix() * 1000);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YY HH:mm");
        String strDate = dateFormat.format(newDate);
        date.setText(strDate);
        Integer signal = list.get(position).getSig();

        if(signal < 40){
            sig.setImageResource(R.drawable.ic_wireless_1);
        } else if(signal >= 40 && signal <= 65){
            sig.setImageResource(R.drawable.ic_wireless_2);
        }else if(signal > 65 && signal <= 90){
            sig.setImageResource(R.drawable.ic_wireless_3);
        }else if(signal > 90){
            sig.setImageResource(R.drawable.ic_wireless_4);
        }


    }
}
