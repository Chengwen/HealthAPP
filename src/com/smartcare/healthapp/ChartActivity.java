package com.smartcare.healthapp;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ChartItem;
import com.github.mikephil.charting.data.LineChartItem;
import com.google.analytics.tracking.android.EasyTracker;
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
import android.widget.TextView;

public class ChartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart);

		ListView lv = (ListView) findViewById(R.id.listViewChart1);

		DataHandler d = new DataHandler(getApplicationContext());
		d.open();

		Cursor cursor = d.returnData();
		if (cursor.getCount() > 0) {
			ArrayList<ChartItem> list = new ArrayList<ChartItem>();

			list.add(new LineChartItem(generateData(1), getApplicationContext()));

			ChartDataAdapter cda = new ChartDataAdapter(
					getApplicationContext(), list);

			lv.setAdapter(cda);
		} else {
			lv.setVisibility(1);
			TextView t = (TextView) findViewById(R.id.textView1);
			t.setText(R.string.no_data);
		}

		ListView listViewBPM = (ListView) findViewById(R.id.listView1);

		BPMListAdapter bpmListAdapter;

		// Create the Adapter
		bpmListAdapter = new BPMListAdapter(this, cursor);

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
			return getItem(position).getView(position, convertView,
					getContext());
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
		ArrayList<String> m = new ArrayList<String>();

		DataHandler d = new DataHandler(getApplicationContext());
		d.open();
		Cursor cursor = d.returnData();
		int i = 0;

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToPosition(cursor.getCount() - 1);
			do {
				String date = new java.text.SimpleDateFormat("dd/LLL")
						.format(new java.util.Date(cursor.getInt(1) * 1000L));

				// Prevent only one data get a error
				if (cursor.getCount() == 1) {
					entries.add(new Entry(cursor.getInt(2), i));
					m.add(date);
				}
				entries.add(new Entry(cursor.getInt(2), i));
				m.add(date);

				i++;

				// last 30 records
				if (i > 30)
					break;

			} while (cursor.moveToPrevious());

		} else {

		}

		DataSet d1 = new DataSet(entries, "BPM");

		ChartData cd = new ChartData(m, d1);
		return cd;
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}

}
