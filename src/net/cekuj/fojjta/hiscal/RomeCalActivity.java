package net.cekuj.fojjta.hiscal;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RomeCalActivity extends Activity {
	
	RomeConverter romec;
	
	Button i_b, v_b, x_b, l_b, c_b, d_b, m_b, back_b;
	EditText year_rome_et, year_std_et, day_rome_et, day_std_et;
	TextView year_std_tv;
	Resources res;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rome_layout);
		
		res = getResources();
		romec = new RomeConverter();

		i_b = (Button) findViewById(R.id.I_button);
		i_b.setOnClickListener(new MiniKeyBoard());
		v_b = (Button) findViewById(R.id.V_button);
		v_b.setOnClickListener(new MiniKeyBoard());
		x_b = (Button) findViewById(R.id.X_button);
		x_b.setOnClickListener(new MiniKeyBoard());
		l_b = (Button) findViewById(R.id.L_button);
		l_b.setOnClickListener(new MiniKeyBoard());
		c_b = (Button) findViewById(R.id.C_button);
		c_b.setOnClickListener(new MiniKeyBoard());
		d_b = (Button) findViewById(R.id.D_button);
		d_b.setOnClickListener(new MiniKeyBoard());
		m_b = (Button) findViewById(R.id.M_button);
		m_b.setOnClickListener(new MiniKeyBoard());
		back_b = (Button) findViewById(R.id.back_button);
		back_b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String aktText = year_rome_et.getText().toString();
				if (aktText.length()>0)
					writeYear(aktText.substring(0, aktText.length()-1));
				
			}
		});
		
		year_rome_et = (EditText) findViewById(R.id.rome_year_editText);
		year_rome_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				writeYear(year_rome_et.getText().toString().toUpperCase());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
//		year_rome_et.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				writeYear(year_rome_et.getText().toString().toUpperCase());
//			}
//		});
		year_std_et = (EditText) findViewById(R.id.std_year_editText);
		
		day_rome_et = (EditText) findViewById(R.id.rome_day_editText);
		day_rome_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				writeDay(day_rome_et.getText().toString().toUpperCase());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		day_std_et = (EditText) findViewById(R.id.std_day_editText);
		
		year_std_tv = (TextView) findViewById(R.id.std_year_textView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class MiniKeyBoard implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			writeYearChar(((Button)v).getText().toString());
		}
	}
	
	private void writeYear(String romanYear) {
		if (!romanYear.equals(year_rome_et.getText().toString().toUpperCase()))
			year_rome_et.setText(romanYear);
		String tr = romanToStd(romanYear);
		
		year_std_tv.setText(tr);
		year_std_et.setText(tr);
	}
	
	private void writeDay(String romanDay) {
		String tr = romanToStd(romanDay);
		day_std_et.setText(tr);
		
	}
	
	private String romanToStd(String roman) {
		String tr = romec.toStdYear(roman);
		if (tr == null) tr = res.getString(R.string.incorrect_format_message);
		return tr;
	}
	
	private void writeYearChar(String character) {
		writeYear(year_rome_et.getText()+character);
	}
}
