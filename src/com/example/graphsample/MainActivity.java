package com.example.graphsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		callService();
		
	
	}
	
   private void callService() {
	   String[] mParams = null ;//= new String[]{mEdtEmail.getText().toString(),  mEdtPwd.getText().toString()};
	   
	   new IncidentGetListTask(new IncidentGetListTask.IncidentGetListResult() {
		
		@Override
		public void onReceiveResult(String strResponse) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onReceiveError() {
			// TODO Auto-generated method stub
			
		}
	},this).execute(mParams);
	}
   

public void showGraph(View v){
	   	Toast.makeText(this,((Button)v).getText(), Toast.LENGTH_SHORT).show();
	   	Intent intent = new Intent(MainActivity.this, GraphActivity.class);
	   	intent.putExtra("GRAPH_TYPE", ((Button)v).getText());
	   	startActivity(intent);
   }
}
