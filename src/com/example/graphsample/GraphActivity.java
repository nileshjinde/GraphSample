package com.example.graphsample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

public class GraphActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);

		GraphView graph = (GraphView) findViewById(R.id.graph);

		String graphType = getIntent().getStringExtra("GRAPH_TYPE");

		if(graphType.equalsIgnoreCase("Line Graph")){
			LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
					new DataPoint(0, 1),
					new DataPoint(1, 5),
					new DataPoint(2, 3),
					new DataPoint(3, 2),
					new DataPoint(4, 6)
			});
			series.setOnDataPointTapListener(new OnDataPointTapListener() {
				@Override
				public void onTap(Series series, DataPointInterface dataPoint) {
					Toast.makeText(GraphActivity.this, "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
				}
			});
			graph.addSeries(series);
		}else if(graphType.equalsIgnoreCase("Bar Graph")){
			BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {
					new DataPoint(0, 1),
					new DataPoint(1, 5),
					new DataPoint(2, 3),
					new DataPoint(3, 2),
					new DataPoint(4, 6)
			});
			series.setOnDataPointTapListener(new OnDataPointTapListener() {
				@Override
				public void onTap(Series series, DataPointInterface dataPoint) {
					Toast.makeText(GraphActivity.this, "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
				}
			});
			graph.addSeries(series);
		}else if(graphType.equalsIgnoreCase("Point Graph")){
			PointsGraphSeries<DataPoint> series = new PointsGraphSeries<DataPoint>(new DataPoint[] {
					new DataPoint(0, 1),
					new DataPoint(1, 5),
					new DataPoint(2, 3),
					new DataPoint(3, 2),
					new DataPoint(4, 6)
			});
			series.setOnDataPointTapListener(new OnDataPointTapListener() {
				@Override
				public void onTap(Series series, DataPointInterface dataPoint) {
					Toast.makeText(GraphActivity.this, "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
				}
			});
			graph.addSeries(series);
		}
	}
}
