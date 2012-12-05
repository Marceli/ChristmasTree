package com.example.myandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowProgram extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  Bundle extras = getIntent().getExtras();
	  String city = extras.getString("program");
	  TextView tv = new TextView(this);
      tv.setText(city);
      setContentView(tv);
	}
}
