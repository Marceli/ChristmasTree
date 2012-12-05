package com.example.myandroid;


import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class SwitchingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switching_activity);
        bindList();
    }


   
    private void bindList() {
    	String[] values=new String[8];
    	for(int i=0;i<8;i++)
    	{
    		values[i]=""+i;
    	}
    	ListView listView=(ListView)findViewById(R.id.levelList);
          ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.tree_row,values);
          listView.setAdapter(adapter);
          listView.setOnItemClickListener(new OnItemClickListener()
          {
        	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		  Log.i(ShowPrograms.class.getName(),"Position:"+position);        	
        		  SendRequest(position);
        	  }
          });
    }

   
    public void SendRequest(int lineNo)
    {
    	
    
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpContext localContext = new BasicHttpContext();
    	String serverIp=getResources().getString(R.string.server_ip);
		HttpPut httpPut = new HttpPut(String.format("http://%s/line/%d", serverIp,lineNo));
		
		//try {
			//HttpResponse response = httpClient.execute(httpPut, localContext);
			String result=httpPut.getURI().toString();
			EditText  mEdit   = (EditText)findViewById(R.id.edit_message);
	    	mEdit.setText(result);
		//} catch (ClientProtocolException e) {
		//	e.printStackTrace();
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	
    }

}
