package com.smartcare.healthapp;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ChartItem;
import com.github.mikephil.charting.data.LineChartItem;
import com.smartcare.database.DataHandler;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class ChartActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.chart); 
        
        ListView lv = (ListView) findViewById(R.id.listViewChart1);

        ArrayList<ChartItem> list = new ArrayList<ChartItem>();

        list.add(new LineChartItem(generateData(1), getApplicationContext()));
        
        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        
        lv.setAdapter(cda);
        
        
        ListView listViewBPM = (ListView) findViewById(R.id.listView1);
         BPMListAdapter bpmListAdapter;

 		DataHandler d=new DataHandler(getApplicationContext());
 		d.open();
 		
 		d.insertData( 1.1f, 1, "");
 		Cursor cursor=d.returnData();

		// Create the Adapter
         bpmListAdapter=new BPMListAdapter(this,cursor);
         
      // Set The Adapter to ListView
         listViewBPM.setAdapter(bpmListAdapter);
    }
    
    /** adapter that supports 3 different item types */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {
        
        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }
        
        @Override
        public int getItemViewType(int position) {           
            // return the views type
            return getItem(position).getItemType();
        }
        
        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
    private ChartData generateData(int cnt) {

        ArrayList<Entry> entries = new ArrayList<Entry>();


 		DataHandler d=new DataHandler(getApplicationContext());
 		d.open();
 		
 		//d.insertData( 1.1f, 1, "");
 		Cursor cursor=d.returnData();
 		
        for (int i = 0; i < 12; i++) {
            entries.add(new Entry((int) (Math.random() * 70) + 30, i));
        }

        DataSet d1 = new DataSet(entries, "BPM");

        ChartData cd = new ChartData(getMonths(), d1);
        return cd;
    }
    
    

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Oct");
        m.add("Nov");
        m.add("Dec");

        return m;
    }
}
