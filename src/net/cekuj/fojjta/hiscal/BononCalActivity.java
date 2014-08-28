package net.cekuj.fojjta.hiscal;

import net.cekuj.fojjta.hiscal.Converters.BononConverter;
import net.cekuj.fojjta.hiscal.Converters.Converter;
import net.cekuj.fojjta.hiscal.Converters.RomanConverter;
import net.cekuj.fojjta.hiscal.RomanCalActivity.ImageToggler;
import net.cekuj.fojjta.hiscal.RomanCalActivity.MiniKeyBoard;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BononCalActivity extends Activity {

	Resources res;
	RomanConverter romec;
	BononConverter bonec;
	Typeface font;
	
	TextView std_day_tv, std_month_tv, std_year_tv;
	EditText roman_year_et, roman_day_et, std_year_et, std_day_et;
	Button i_b, v_b, x_b, l_b, c_b, d_b, m_b, back_b, execute_b, toggle_input_b, reset_b;
	Spinner mparts_sp, mbeacons_sp, months_sp;

	boolean upper_input_active=true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bonon_layout);
		
		res = getResources();
		romec = new RomanConverter();
		bonec = new BononConverter();
		
		font = Typeface.createFromAsset(getAssets(), "fonts/Roman_SD.ttf");
		
		std_day_tv = (TextView) findViewById(R.id.std_day_textView);
		std_month_tv = (TextView) findViewById(R.id.std_month_textView);
		std_year_tv = (TextView) findViewById(R.id.std_year_textView);
		
		roman_day_et = (EditText) findViewById(R.id.roman_day_editText);
		roman_year_et = (EditText) findViewById(R.id.roman_year_editText);
		std_day_et = (EditText) findViewById(R.id.std_day_editText);
		std_year_et = (EditText) findViewById(R.id.std_year_editText);

		i_b = (Button) findViewById(R.id.I_button);
		i_b.setOnClickListener(new MiniKeyBoard());
		i_b.setOnTouchListener(new ImageToggler());
		v_b = (Button) findViewById(R.id.V_button);
		v_b.setOnClickListener(new MiniKeyBoard());
		v_b.setOnTouchListener(new ImageToggler());
		x_b = (Button) findViewById(R.id.X_button);
		x_b.setOnClickListener(new MiniKeyBoard());
		x_b.setOnTouchListener(new ImageToggler());
		l_b = (Button) findViewById(R.id.L_button);
		l_b.setOnClickListener(new MiniKeyBoard());
		l_b.setOnTouchListener(new ImageToggler());
		c_b = (Button) findViewById(R.id.C_button);
		c_b.setOnClickListener(new MiniKeyBoard());
		c_b.setOnTouchListener(new ImageToggler());
		d_b = (Button) findViewById(R.id.D_button);
		d_b.setOnClickListener(new MiniKeyBoard());
		d_b.setOnTouchListener(new ImageToggler());
		m_b = (Button) findViewById(R.id.M_button);
		m_b.setOnClickListener(new MiniKeyBoard());
		m_b.setOnTouchListener(new ImageToggler());
		back_b = (Button) findViewById(R.id.back_button);
		back_b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String aktText;
				if (upper_input_active) {
					aktText = roman_year_et.getText().toString();
					if (aktText.length()>0)
						writeYear(aktText.substring(0, aktText.length()-1));
				} else {
					aktText = roman_day_et.getText().toString();
					if (aktText.length()>0)
						writeDay(aktText.substring(0, aktText.length()-1));
				}
				
			}
		});
		back_b.setOnTouchListener(new ImageToggler());
		execute_b = (Button) findViewById(R.id.execute_button);
		execute_b.setTypeface(font);
		execute_b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				countEverything();
			}
		});
		toggle_input_b = (Button) findViewById(R.id.toggle_button);
		toggle_input_b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleInputFocus();
			}
		});
		toggle_input_b.setOnTouchListener(new ImageToggler());
		reset_b = (Button) findViewById(R.id.reset_button);
		reset_b.setTypeface(font);
		reset_b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resetInputs();
			}
		});
		
		roman_year_et = (EditText) findViewById(R.id.roman_year_editText);
		roman_year_et.setTypeface(font);
		roman_year_et.getBackground().setColorFilter(
				getResources().getColor(R.color.selected),
				android.graphics.PorterDuff.Mode.MULTIPLY);
		std_year_et = (EditText) findViewById(R.id.std_year_editText);
		std_year_et.setTypeface(font);
		
		roman_day_et = (EditText) findViewById(R.id.roman_day_editText);
		roman_day_et.setTypeface(font);
		std_day_et = (EditText) findViewById(R.id.std_day_editText);
		std_day_et.setTypeface(font);
		
		mparts_sp = (Spinner) findViewById(R.id.mparts_spinner);
		mbeacons_sp = (Spinner) findViewById(R.id.mbeacons_spinner);
		months_sp = (Spinner) findViewById(R.id.months_spinner);

		TextView tv2 = (TextView) findViewById(R.id.textView2);
		tv2.setTypeface(font);
		TextView tv3 = (TextView) findViewById(R.id.textView3);
		tv3.setTypeface(font);
		std_day_tv = (TextView) findViewById(R.id.std_day_textView);
		std_day_tv.setTypeface(font);
		std_month_tv = (TextView) findViewById(R.id.std_month_textView);
		std_month_tv.setTypeface(font);
		std_year_tv = (TextView) findViewById(R.id.std_year_textView);
		std_year_tv.setTypeface(font);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.bonon_cal, menu);
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
			String idname = res.getResourceEntryName(v.getId());
			String s = idname.split("_")[0];
			if (upper_input_active)
				writeYearChar(s);
			else
				writeDayChar(s);
		}
	}

	class ImageToggler implements View.OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			String idname = res.getResourceEntryName(v.getId());
			String norname = idname.split("_")[0].toLowerCase();
			String pushed = norname + "_push";
			
			int id;
			if(event.getAction()==MotionEvent.ACTION_DOWN) {
				id = res.getIdentifier(getPackageName()
						+ ":drawable/" + pushed, null, null);
				v.setBackgroundDrawable(res.getDrawable(id));
			}
			else {
				id = res.getIdentifier(getPackageName()
						+ ":drawable/" + norname, null, null);
				v.setBackgroundDrawable(res.getDrawable(id));
			}
			return false;
		}
		
	}
	
	private void resetInputs() {
		upper_input_active = true;
		
//		bis_chb.setChecked(false);

		// Select year_rome_et
		roman_year_et.getBackground().setColorFilter(
				getResources().getColor(R.color.selected),
				android.graphics.PorterDuff.Mode.MULTIPLY);
		roman_year_et.setText("");
		roman_year_et.invalidate();
		std_year_et.setText("0");
		// Unselect day_rome_et
		roman_day_et.setBackgroundDrawable(
				res.getDrawable(android.R.drawable.edit_text));
		roman_day_et.setText("");
		roman_day_et.invalidate();
		std_day_et.setText("0");
		
		std_day_tv.setText("");
		std_month_tv.setText("");
		std_year_tv.setText("");
		
		mparts_sp.setSelection(0);
		mbeacons_sp.setSelection(0);
		months_sp.setSelection(0);
	}

	private void toggleInputFocus() {
		if(upper_input_active) {
			// Select day_rome_et
			roman_day_et.getBackground().setColorFilter(
					getResources().getColor(R.color.selected),
					android.graphics.PorterDuff.Mode.MULTIPLY);
			
			// Unselect year_rome_et
			roman_year_et.setBackgroundDrawable(res.getDrawable(android.R.drawable.edit_text));
		}
		else {
			// Select year_rome_et
			roman_year_et.getBackground().setColorFilter(
					getResources().getColor(R.color.selected),
					android.graphics.PorterDuff.Mode.MULTIPLY);

			// Unselect day_rome_et
			roman_day_et.setBackgroundDrawable(res.getDrawable(android.R.drawable.edit_text));
		}
		
		roman_year_et.invalidate();
		roman_day_et.invalidate();
		upper_input_active ^= true;
	}
	
	private void countEverything() {
		String errorM = res.getString(R.string.incorrect_format_message);
		
		writeDay(roman_day_et.getText().toString().toUpperCase());
		writeYear(roman_year_et.getText().toString().toUpperCase());

		int mpart = mparts_sp.getSelectedItemPosition();
		int mbeacon = mbeacons_sp.getSelectedItemPosition();
		int month = months_sp.getSelectedItemPosition();
		
		String sy = std_year_et.getText().toString();
		String sd = std_day_et.getText().toString();
		// Incorrect roman format input
		if (!Converter.isNumeric(sy) || !Converter.isNumeric(sd)) {
			std_year_tv.setText(errorM);
			std_month_tv.setText("");
			std_day_tv.setText("");
			return;
		}
		int year = Integer.valueOf(std_year_et.getText().toString());
		int day = Integer.valueOf(std_day_et.getText().toString());
		
		Integer[] params = {year, month+1, mpart-1, day, mbeacon-1};
		Integer[] std = bonec.toStdDate(params);
		
		std_day_tv.setText(std[0] + ". ");
		String mon = res.getStringArray(R.array.std_months)[std[1]-1];
		std_month_tv.setText(mon + " ");
		std_year_tv.setText(std[2] + "");
	}
	
	private int resolveIndexInResArray(int arrayID, String text) {
		String[] sarr = res.getStringArray(arrayID);
		for (int i=0; i<sarr.length; i++) {
			if (sarr[i].equals(text)) return i;
		}
		return -1;
	}
	
	private String romanToStd(String roman) {
		String tr = romec.toDecimalNumber(roman);
		if (tr == null) tr = res.getString(R.string.incorrect_format_message);
		return tr;
	}
	
	private void writeYear(String romanYear) {
		if (!romanYear.equals(roman_year_et.getText().toString().toUpperCase()))
			roman_year_et.setText(romanYear);
		String tr = romanToStd(romanYear);
		
		std_year_et.setText(tr);
	}
	
	private void writeDay(String romanDay) {
		if (!romanDay.equals(roman_day_et.getText().toString().toUpperCase()))
			roman_day_et.setText(romanDay);
		String tr = romanToStd(romanDay);
		
		std_day_et.setText(tr);
	}
	
	private void writeYearChar(String character) {
		writeYear(roman_year_et.getText()+character);
	}
	
	private void writeDayChar(String character) {
		writeDay(roman_day_et.getText()+character);
	}
}
