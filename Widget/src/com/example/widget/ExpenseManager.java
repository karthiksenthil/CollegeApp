package com.example.widget;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

public class ExpenseManager extends Activity implements OnClickListener{

	String path1,path2,path3;
	ArrayList<String>dates = new ArrayList();
	ArrayList<Integer>enterys = new ArrayList();
	ArrayList<String>cost = new ArrayList();
	ArrayList<String>reason = new ArrayList();
	
	void readtempexpense()
	{
		File f = new File(path1);
		if(!f.exists())
			f.mkdirs();
		f = new File(path2);
		String datatowrite="";
		String datatoread="";
		String s="";
		try {
			FileInputStream fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			while((s=bfr.readLine())!=null)
				datatoread+=s+"\n";
			bfr.close();
			fis.close();
			f = new File(path3);
			if(!f.exists())
				f.createNewFile();
			s="";
			fis = new FileInputStream(f);
			bfr = new BufferedReader(new InputStreamReader(fis));
			while((s=bfr.readLine())!=null)
				datatowrite+=s+"\n";
			datatowrite+=datatoread;
			bfr.close();
			fis.close();
			FileOutputStream fos = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			osw.write(datatowrite);
			osw.close();
			fos.close();
			f = new File(path2);
			fos = new FileOutputStream(f);
			osw = new OutputStreamWriter(fos);
			osw.write(" ");
			osw.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void currentexpensestatus()
	{
		dates.clear();
		enterys.clear();
		reason.clear();
		cost.clear();
		int no=0;
		File f = new File(path3);
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			String s ="";
			while((s=bfr.readLine())!=null)
			{
				if(s.contains("/"))
					{
					dates.add(s);
					
					}
				else if (s.equals("EndOfDaTe"))
					{
					cost.add(s);
					enterys.add(no);
					no=0;
					}
				else
					{
					    no++;
						cost.add(s);
						s=bfr.readLine();
						reason.add(s);
					}
			bfr.close();
			fis.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	void setMonthly()
	{
	
	}
	
	void setDaily()
	{
		
	}
	
	void setDetailed(String s)
	{
		ArrayList<String> reasons= new ArrayList();
		ArrayList<Integer> costs = new ArrayList();
		int ks2=0;
		for(int ks=0;ks<dates.size();ks++)
			{
			if(s.equals(dates.get(ks)))
				{
				ks2=ks;
				break;
				}
			
			}
			String lastdate = dates.get(dates.size()-1);
			int skip=0,skip2=0;
			int count = enterys.get(enterys.size()-1);
			for(int k =0; k<ks2;k++)
			{
				skip+=enterys.get(k);
				skip2+=enterys.get(k)+1;
			}
			for(int counter=0;counter<count;counter++)
			{
				reasons.add(reason.get(skip+counter));
				costs.add(Integer.valueOf(cost.get(skip2+counter)));	
			}
			
		
		ArrayList<GraphViewData> gvd = new ArrayList();
		for(int x =0; x<costs.size();x++)
		{
			gvd.add(new GraphViewData(x+1,costs.get(x)));
		}
		GraphViewData[] gvdd = (GraphViewData[]) gvd.toArray();
		GraphViewSeries series = new GraphViewSeries(gvdd);
		
	GraphView graphView = new  BarGraphView(this,"Detailed expense for "+s);
	graphView.addSeries(series); 
	LinearLayout layout = (LinearLayout) findViewById(R.id.detailed);
	layout.addView(graphView);
	}
	void update(String s1, String s2)
	{
		File f = new File(path3);
		String s="";
		ArrayList<String> al =new ArrayList();
		String date="";
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			while((s=bfr.readLine())!=null)
				{
				al.add(s);
				if(al.contains("/"))
					date =s;
				}
			bfr.close();
			fis.close();
			Calendar c = Calendar.getInstance();
			//System.out.println("Current time => " + c.getTime());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate = df.format(c.getTime());
			if(formattedDate.equals(date))
			{
				al.remove(al.size()-1);
				
			}
			else
			{
				al.add(formattedDate);
			}
			al.add(s1);
			al.add(s2);
			al.add("EndOfDaTe");
			FileOutputStream fos = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			int k=0;
			while(k<al.size())
			{
				osw.write(al.get(k)+"\n");
				k++;
			}
			osw.close();
			fos.close();
			Toast.makeText(getApplicationContext(), "Expense Updated", Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense);
	}
	
		@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp" +File.separator+"Expenses";
		path3=path1+File.separator+"main.txt";
		path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CollApp" +File.separator+"tempexpense.txt";
		readtempexpense();
		currentexpensestatus();
		ImageButton ib = (ImageButton)findViewById(R.id.newexpense);
		ib.setOnClickListener(this);
		TabHost th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		LinearLayout detailed = (LinearLayout)findViewById(R.id.detailed);
		LinearLayout daily = (LinearLayout) findViewById(R.id.daily);
		LinearLayout monthly = (LinearLayout) findViewById(R.id.monthly);
		setDetailed(dates.get(dates.size()-1));
		setDaily();
		setMonthly();
		/*GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
			      new GraphViewData(1, 2.0d)
			      , new GraphViewData(2, 1.5d)
			      , new GraphViewData(3, 2.5d)
			      , new GraphViewData(4, 1.0d)
			});
			 
			GraphView graphView = new LineGraphView(this,"GraphViewDemo");
			graphView.addSeries(exampleSeries); 
			LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
			layout.addView(graphView);
			*/
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			final AlertDialog hint2 =new AlertDialog.Builder(ExpenseManager.this).create();
	        hint2.setTitle("New Expense :( ");  
	        hint2.setMessage("Enter expense details");
	        LinearLayout l = new LinearLayout(hint2.getContext());
	        l.setOrientation(LinearLayout.VERTICAL);
	        final EditText et2 = new EditText(hint2.getContext());
	        TextView tv = new TextView(hint2.getContext());
	        tv.setText("Amount :");
	        tv.setTextSize(30);
	        TextView tv2 = new TextView(hint2.getContext());
	        final EditText et22 = new EditText(hint2.getContext());
	        tv2.setText("Reason :");
	        tv2.setTextSize(30);
	        Button b = new Button(hint2.getContext());
	        b.setText("Update expense");
	        b.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(et2.getText().toString()!=null && et22.getText().toString()!=null)
					{
						update(et2.getText().toString(),et22.getText().toString());
						currentexpensestatus();
					}
					else
						Toast.makeText(getApplicationContext(), "Fill both fields", Toast.LENGTH_LONG).show();
				}
			});
	        l.addView(tv);
	        l.addView(et2);
	        l.addView(tv2);
	        l.addView(et22);
	        l.addView(b);
	        hint2.setView(l);
	        hint2.show();
		}
	
		

}
