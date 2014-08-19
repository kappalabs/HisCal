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
import android.widget.Spinner;
import android.widget.TextView;

public class RomeCalActivity extends Activity {
	
	RomeConverter romec;
	
	Button i_b, v_b, x_b, l_b, c_b, d_b, m_b, back_b, execute_b, toggle_input_b;
	EditText year_rome_et, year_std_et, day_rome_et, day_std_et;
	TextView day_std_tv, month_std_tv, year_std_tv;
	Spinner prefix_sp, beacons_sp, months_sp;
	Resources res;
	
	boolean upper_input_active=true;

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
				String aktText;
				if (upper_input_active) {
					aktText = year_rome_et.getText().toString();
					if (aktText.length()>0)
						writeYear(aktText.substring(0, aktText.length()-1));
				} else {
					aktText = day_rome_et.getText().toString();
					if (aktText.length()>0)
						writeDay(aktText.substring(0, aktText.length()-1));
				}
				
			}
		});
		execute_b = (Button) findViewById(R.id.execute_button);
		execute_b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				countEverything();
			}
		});
		toggle_input_b = (Button) findViewById(R.id.toggle_input_button);
		toggle_input_b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleInputFocus();
			}
		});
		
		year_rome_et = (EditText) findViewById(R.id.rome_year_editText);
//		year_rome_et.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				writeYear(year_rome_et.getText().toString().toUpperCase());
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//			
//			@Override
//			public void afterTextChanged(Editable s) {}
//		});
//		year_rome_et.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				writeYear(year_rome_et.getText().toString().toUpperCase());
//			}
//		});
		year_std_et = (EditText) findViewById(R.id.std_year_editText);
		
		day_rome_et = (EditText) findViewById(R.id.rome_day_editText);
//		day_rome_et.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				writeDay(day_rome_et.getText().toString().toUpperCase());
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//			
//			@Override
//			public void afterTextChanged(Editable s) {}
//		});
		day_std_et = (EditText) findViewById(R.id.std_day_editText);
		
		prefix_sp = (Spinner) findViewById(R.id.prefix_spinner);
		beacons_sp = (Spinner) findViewById(R.id.beacons_spinner);
		months_sp = (Spinner) findViewById(R.id.months_spinner);
		
		day_std_tv = (TextView) findViewById(R.id.std_day_textView);
		month_std_tv = (TextView) findViewById(R.id.std_month_textView);
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
			if (upper_input_active)
				writeYearChar(((Button)v).getText().toString());
			else
				writeDayChar(((Button)v).getText().toString());
		}
	}
	
	private void toggleInputFocus() {
		if(upper_input_active) day_rome_et.requestFocus();
		else year_rome_et.requestFocus();
		
		upper_input_active ^= true;
	}
	
	private void countEverything() {
		writeYear(year_rome_et.getText().toString().toUpperCase());
//		writeMonth(months_sp.getSelectedItem().toString());
		writeMonthAndDay(prefix_sp.getSelectedItem().toString(),
				beacons_sp.getSelectedItem().toString(),
				months_sp.getSelectedItem().toString());
	}
	
	//TODO
	private void writeMonthAndDay(String prefix, String beacon, String romanMonth) {
		int prefixIndx = resolveIndexInResArray(R.array.roman_prefix, prefix);
		int beaconIndx = resolveIndexInResArray(R.array.roman_beacons, beacon);
		int monIndx = resolveIndexInResArray(R.array.roman_months, romanMonth);
		
		writeDay(day_rome_et.getText().toString().toUpperCase());
		int ndays;
		if (!isNumeric(day_std_et.getText().toString())
				|| day_std_et.getText().toString()==null) {
			day_std_tv.setText(res.getString(R.string.error_message));
			return;
		}
		ndays = Integer.parseInt(day_std_et.getText().toString());
		
		int day = romec.toStdDay(ndays, prefixIndx, beaconIndx, monIndx);
		// rotate months and resolve new date
		if (day<=0) {
			monIndx = (monIndx+11)%12;
			day = Converter.Month.getIthMonth(monIndx+1).days + day;
		}

		// Write calculated month
		String tr = resolveIthResArrayString(R.array.std_months, monIndx);
		month_std_tv.setText(tr + " ");
		
		// Write calculated day
		day_std_tv.setText(day + ". ");
	}
	
	public boolean isNumeric(String str) {
		try { int x = Integer.parseInt(str); }
		catch(NumberFormatException nfe)
		{ return false; } return true;
	}
	
	private int resolveIndexInResArray(int arrayID, String text) {
		String[] sarr = res.getStringArray(arrayID);
		for (int i=0; i<sarr.length; i++) {
			if (sarr[i].equals(text)) return i;
		}
		return -1;
	}
	
	private String resolveIthResArrayString(int arrayID, int indx) {
		return res.getStringArray(arrayID)[indx];
	}
	
	private String romanToStd(String roman) {
		String tr = romec.toDecimalNumber(roman);
		if (tr == null) tr = res.getString(R.string.incorrect_format_message);
		return tr;
	}
	
	private void writeYear(String romanYear) {
		if (!romanYear.equals(year_rome_et.getText().toString().toUpperCase()))
			year_rome_et.setText(romanYear);
		String tr = romanToStd(romanYear);
		
		year_std_tv.setText(tr);
		year_std_et.setText(tr);
	}
	
	private void writeDay(String romanDay) {
		if (!romanDay.equals(day_rome_et.getText().toString().toUpperCase()))
			day_rome_et.setText(romanDay);
		String tr = romanToStd(romanDay);
		
		day_std_et.setText(tr);
	}
	
	private void writeYearChar(String character) {
		writeYear(year_rome_et.getText()+character);
	}
	
	private void writeDayChar(String character) {
		writeDay(day_rome_et.getText()+character);
	}
}
