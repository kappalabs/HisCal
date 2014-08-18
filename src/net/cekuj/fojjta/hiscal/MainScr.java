package net.cekuj.fojjta.hiscal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainScr extends Activity {
	
	ListView calendars_lW;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		calendars_lW = (ListView) findViewById(R.id.calendars_listView);
		calendars_lW.requestFocus();
		calendars_lW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//Toast.makeText(MainScr.this, arg0.get, Toast.LENGTH_LONG).show();
				
				String item = ((TextView)arg1).getText().toString();
                
                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

                if (item.equals(getResources().getString(R.string.cal_rome))) {
                	Intent intent = new Intent(MainScr.this, RomeCalActivity.class);
                	//    String message = editText.getText().toString();
                	//String EXTRA_MESSAGE="extra";
                	//intent.putExtra(EXTRA_MESSAGE, message);
                	startActivity(intent);
                }
			}
		});
	}
}
