package com.example.myandroid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ToggleButton;

public class ShowPrograms extends Activity {
  
/** Called when the activity is first created. */
  JSONArray programList;
  boolean isStandardList=true;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_parse_json);
    RefreshMockPrograms();  
        
  }
  private void RefreshMockPrograms() {
	String result;
	if(this.isStandardList){
		result="[ {ID:\"38f0d4be-abd7-4bf9-8844-60b4945263c4\", Author:\"StandardPrograms\",  Name: \"Name\"} ,";
		result+=" {ID:\"38f0d4be-abd7-4bf9-8844-60b4945263c3\", Author:\"Marcel P\",  Name: \"Kotek\"} ]";
	}else{
		result="[ {ID:\"38f0d4be-abd7-4bf9-8844-60b4945263c4\", Author:\"Custom Programs\",  Name: \"Name\"} ,";
		result+=" {ID:\"38f0d4be-abd7-4bf9-8844-60b4945263c3\", Author:\"Marcel P\",  Name: \"Kotek\"} ]";
	}
     
	try {
		programList=new JSONArray(result);
	} catch (JSONException e) {
		e.printStackTrace();
	}
	bindList(getItems());
 }

 public void RefreshPrograms() {
    StringBuilder builder = new StringBuilder();
    HttpClient client = new DefaultHttpClient();
    String serverIp=getResources().getString(R.string.server_ip);
    HttpGet httpGet = new HttpGet(String.format("http://%s/stdprogram",serverIp));
    try {
      HttpResponse response = client.execute(httpGet);
      StatusLine statusLine = response.getStatusLine();
      int statusCode = statusLine.getStatusCode();
      if (statusCode == 200) {
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        String line;
        while ((line = reader.readLine()) != null) {
          builder.append(line);
        }
      } else {
        Log.e(ShowPrograms.class.toString(), "Failed to program list");
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
	try {
		programList=new JSONArray(builder.toString());
	} catch (JSONException e) {
		e.printStackTrace();
	}

  }
 
 public void onStandardBtnClick(View view) {
	 this.isStandardList=!isStandardList;
	 String btnCaption;
	 if(isStandardList)
	 {
		 btnCaption="Standard programs";
	 }else
	 {
		 btnCaption="Custom programs";
	 }
 	((ToggleButton)view).setText(btnCaption);
 	RefreshMockPrograms();
 	
 }

private String[] getItems() {
	Log.i(ShowPrograms.class.getName(),
          "Number of entries " + programList.length());
      String[] values=new String[programList.length()];
      for (int i = 0; i < programList.length(); i++) {
        JSONObject jsonObject;
		try {
			jsonObject = programList.getJSONObject(i);
			Log.i(ShowPrograms.class.getName(), jsonObject.getString("ID"));
			Log.i(ShowPrograms.class.getName(), jsonObject.getString("Author"));
			Log.i(ShowPrograms.class.getName(), jsonObject.getString("Name"));
			values[i]=jsonObject.getString("Name") + " by " +jsonObject.getString("Author");
		} catch (JSONException e) {
			e.printStackTrace();
		}
      }
	return values;
}


private void bindList(String[] values) {
	ListView listView=(ListView)findViewById(R.id.mylist);
      ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,values);
      listView.setAdapter(adapter);  
 
      listView.setOnItemClickListener(new OnItemClickListener()
      {
      
	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	  
		  PlayProgram(id);
	}});}


	private void PlayProgram(long id) {
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpContext localContext = new BasicHttpContext();
    	String serverIp=getResources().getString(R.string.server_ip);
    	if(isStandardList)
    	{
    		HttpPut httpPut = new HttpPut(String.format("http://%s/stdprogram/%d", serverIp,id));
    	}else{
    		HttpPut httpPut = new HttpPut(String.format("http://%s/program/%d", serverIp,id));    		
    	}
		
		//try {
			//HttpResponse response = httpClient.execute(httpPut, localContext);
			Log.i(ShowPrograms.class.getName(),"Turn std program no:"+id);
		//} catch (ClientProtocolException e) {
		//	e.printStackTrace();
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}
}

  
