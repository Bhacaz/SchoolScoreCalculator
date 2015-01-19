package com.bhacaz.schoolscorecalculator;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	List<String> resultats = new ArrayList<>();
	List<String> ponderation = new ArrayList<>();
	List<String> formater = new ArrayList<>();
	String noteDePassage = "";
	ArrayAdapter<String> adapter = null;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
		Button button_calculer = (Button)findViewById(R.id.calculer);
		button_calculer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView textView_moyenneInput = (TextView)findViewById(R.id.moyenneText);
				TextView textView_calculeInput = (TextView)findViewById(R.id.calculeText);
				Double noteCumuler = 0.0;
				Double sommeNoteEntrer = 0.0;
				Double fNoteDePassage = Double.parseDouble(noteDePassage);
				Log.i("Test", fNoteDePassage.toString());
				
				for(int i = 0; i < ponderation.size(); i++)
				{
					noteCumuler = noteCumuler + DoubleConvert(ponderation).get(i) * DoubleConvert(resultats).get(i) / 100;
					sommeNoteEntrer = sommeNoteEntrer + DoubleConvert(ponderation).get(i);
				}
				
				Double aAvoir = (fNoteDePassage - noteCumuler) / (100 - sommeNoteEntrer) * 100;
				
				Double moyenne = noteCumuler / sommeNoteEntrer * 100;
				
				textView_moyenneInput.setText("Moyenne : " + moyenne.toString());
				textView_calculeInput.setText("Moyenne pour la suite : " + aAvoir.toString());
				
				if(moyenne >= fNoteDePassage)
					textView_moyenneInput.setTextColor(Color.GREEN);
				else
					textView_moyenneInput.setTextColor(Color.RED);

				
				if(aAvoir <= 100)
					textView_calculeInput.setTextColor(Color.GREEN);
				else
					textView_calculeInput.setTextColor(Color.RED);			
				

			
				
				
			}
		});
		

		
		//effacer_button.setOnClickListener(effacer);
		
		ListView liste = (ListView)findViewById(R.id.liste_view);
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, formater);
				liste.setAdapter(adapter);
				
		getMoyenne();
		
		
		//adapter.notifyDataSetChanged();

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public List<Double> DoubleConvert(List<String> li)
	{
		List<Double> re = new ArrayList<>();
		for(int i = 0; i < li.size(); i++)
		{
			re.add(Double.parseDouble(li.get(i)));
		}
		return re;
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
		if(id == R.id.add_settings){
			onCreateDialog();
			return true;
		}
		if(id == R.id.reset_settings)
		{
			finish();
			startActivity(getIntent());
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getMoyenne(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Note de passge");
		builder.setMessage("Entrez la note de passage en pourcentage");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		builder.setView(input);

		builder.setPositiveButton("Terminer", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			noteDePassage = input.getText().toString();
		  TextView view_noteDePassage = (TextView)findViewById(R.id.notedepassageText);
		  view_noteDePassage.setText("Note de passage : " + noteDePassage + "%");
		  }
		});
		
		

		builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});
		builder.create().show();
	}
	
	public void onCreateDialog() {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LinearLayout layout = new LinearLayout(this);
	    layout.setOrientation(LinearLayout.VERTICAL);
	    
	    final EditText ponderation_input = new EditText(this);
	    final EditText resultat_input = new EditText(this);
	    
	    resultat_input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
	    resultat_input.setHint("Resultat /100");
		ponderation_input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		ponderation_input.setHint("Ponderation /100");
	    
	    layout.addView(ponderation_input);
	    layout.addView(resultat_input);
	    
		builder.setView(layout);
	    
	   // builder.setView(input_view);
	    builder.setTitle("Examens et ponderations");
	    
	    		

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    // Add action buttons
	           
	    
	           builder.setPositiveButton("Suivant", new DialogInterface.OnClickListener() {
	   			
	   			@Override
	   			public void onClick(DialogInterface dialog, int id) {
	   				
           	    	String value_resultat = resultat_input.getText().toString();
           	    	String value_ponderation = ponderation_input.getText().toString();
           	    	
           	    	resultats.add(value_resultat);
           	    	ponderation.add(value_ponderation);
           	    	
           	    	String buf = "";
           			buf = buf + "Resultats : " + value_resultat + "% --> Ponderation : " + value_ponderation + "%";
           				formater.add(buf);
           				buf = "";
           	    	
	   				
	   			}
	   		});
	           builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   
	               }
	           });      
	    builder.create().show();
	}

	
}
