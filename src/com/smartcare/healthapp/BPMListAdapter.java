package com.smartcare.healthapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BPMListAdapter extends BaseAdapter{
    private Context mContext;
    Cursor cursor;
    public BPMListAdapter(Context context,Cursor cur) 
    {
            super();
            mContext=context;
            cursor=cur;
           
    }
       
    public int getCount() 
    {
        // return the number of records in cursor
        return cursor.getCount();
    }

    // getView method is called for each item of ListView
    public View getView(int position,  View view, ViewGroup parent) 
    {
                    // inflate the layout for each item of listView
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.list_single, null);
            
                    // move the cursor to required position 
                    cursor.moveToPosition(position);
                    
                    // fetch the sender number and sms body from cursor
                    String bpm=cursor.getString(2)+" BPM";
    		 		String date = new java.text.SimpleDateFormat("dd/LLL").format(new java.util.Date(cursor.getInt(1) * 1000L));
    		 		String time = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(cursor.getInt(1) * 1000L));
                   
                    // get the reference of textViews
                    TextView textViewBPM = (TextView)view.findViewById(R.id.getBPM);
                    TextView textViewDate = (TextView)view.findViewById(R.id.getDate);
                    TextView textViewTime = (TextView)view.findViewById(R.id.getTime);
                    
                    // Set the Sender number and smsBody to respective TextViews 
                    textViewBPM.setText(bpm);
                    textViewDate.setText(date);
                    textViewTime.setText(time);
                    
        
                    return view;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}
