package com.example.widget;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class NotesList extends Activity implements OnClickListener{

	ImageButton ib;
	TextView tv;
	String passed,path;
	LinearLayout l ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noteslist);
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(this);
		ib = (ImageButton)findViewById(R.id.add2);
		tv = (TextView) findViewById(R.id.nonotes);
	    l = (LinearLayout) findViewById(R.id.list);
	    path= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp"+File.separator+"Notes"+File.separator;
	    ib.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		l.removeAllViews();
		String path2= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp"+File.separator+"noteslog.txt";
	    File f = new File(path2);
	    if(!f.exists())
	    {
	    	tv.setVisibility(View.VISIBLE);
	    }
	    else
	    {
	    	
	    	tv.setVisibility(View.INVISIBLE);
	    	try {
				FileInputStream fis = new FileInputStream(f);
				BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
				ArrayList <String> str = new ArrayList();
				str.clear();
				String s ="";
				while((s=bfr.readLine())!=null)
				{
					str.add(s);
				}
				bfr.close();
				fis.close();
				final ArrayList<TextView> ets = new ArrayList();
				for(int i =0; i<str.size();i++)
				{
					LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					lp.setMargins(60, 20, 0, 0);
					ets.add(new TextView(l.getContext()));
					ets.get(i).setText(str.get(i));
					ets.get(i).setTextSize(30);
					ets.get(i).setLayoutParams(lp);
					ets.get(i).setId(i);
					ets.get(i).setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							int ss = v.getId();
							passed = ets.get(ss).getText().toString();
							loads();
						}
					});
					l.addView(ets.get(i));
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	void loads()
	{
		File f = new File(path+passed+".txt");
		if(f.exists())
		{
			Intent i = new Intent(getApplicationContext(),Notes.class);
			i.putExtra("filename", passed);
			startActivity(i);
		}
			
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.add2 : final AlertDialog hint2 =new AlertDialog.Builder(NotesList.this).create();
        hint2.setTitle("New Note");  
        hint2.setMessage("New Note description");
        LinearLayout l = new LinearLayout(hint2.getContext());
        l.setOrientation(LinearLayout.VERTICAL);
        final EditText et2 = new EditText(hint2.getContext());
        TextView tv = new TextView(hint2.getContext());
        tv.setText("Enter the note name");
        Button b = new Button(hint2.getContext());
        b.setText("Save");
        b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(et2.getText().toString()==null)
				{
					Toast.makeText(getApplicationContext(), "Please fill the note name", Toast.LENGTH_LONG).show();
					
				}
				else
				{
				Intent intent = new Intent(getApplicationContext(),Notes.class);
				String take=et2.getText().toString();
				intent.putExtra("filename", take);
				hint2.dismiss();
				startActivity(intent);
				}
			}
		});
        l.addView(tv);
        l.addView(et2);
        l.addView(b);
        hint2.setView(l);
        hint2.show();
        break;
		case R.id.button1 : Intent i = new Intent(this,ExpenseManager.class);
		startActivity(i);
		}
	}
}
