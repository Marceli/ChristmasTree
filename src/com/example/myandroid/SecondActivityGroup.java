package com.example.myandroid;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivityGroup extends  ActivityGroup {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Start the root activity within the group and get its view
    View view = getLocalActivityManager().startActivity("SwitchingActivity", new
  	      							Intent(this,SwitchingActivity.class)
  	      							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
  	                                .getDecorView();

        // Replace the view of this ActivityGroup

	setContentView(view);
    }

}
