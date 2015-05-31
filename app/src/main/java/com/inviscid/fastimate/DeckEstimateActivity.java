package com.inviscid.fastimate;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class DeckEstimateActivity extends ActionBarActivity implements DeckEstimateResultsFragment.OnFragmentInteractionListener {

    public final static String EXTRA_MESSAGE = "com.inviscid.fastimate.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_estimate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck_estimate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


        public void onFragmentInteraction(Uri uri){


        }


    public void openDeckMaterialList(View view){

        Intent intent = new Intent(this, DisplayDeckMaterialsList.class);
            Bundle args = new Bundle();
        double length,ledgerLength,width,height;

        EditText editT = (EditText) findViewById(R.id.de_field_length);
        if(!editT.getText().toString().trim().equals("")) {
            length = Double.parseDouble(editT.getText().toString()); //Getting the input from the user.  Must parse to double
        }
        else length = 0;


        editT = (EditText) findViewById(R.id.de_field_ledger);
        if(!editT.getText().toString().trim().equals(""))
        ledgerLength = Double.parseDouble(editT.getText().toString());
        else ledgerLength = 0;
        editT = (EditText) findViewById(R.id.de_field_width);
        if(!editT.getText().toString().trim().equals(""))
        width = Double.parseDouble(editT.getText().toString());
        else width = 0;

        editT = (EditText) findViewById(R.id.de_field_height);
        if(!editT.getText().toString().trim().equals(""))
        height = Double.parseDouble(editT.getText().toString());
        else height = 0;

        String results;
        if(ledgerLength == 0){
            DeckEstimate deckEstimate = new DeckEstimate(length,width,height);
            results = deckEstimate.getDeckEstimate();
        }else{
            DeckEstimate deckEstimate = new DeckEstimate(length,ledgerLength,width,height);
            results = deckEstimate.getDeckEstimate();
        }

        args.putString("res", results);

        intent.putExtra(EXTRA_MESSAGE, results);

        //Check if fragment is running


        //Creates fragment
        DeckEstimateResultsFragment deckEstimateResultsFragment = DeckEstimateResultsFragment.newInstance("test", results);
        //Pass any information from the intent(not necessary in this case)
        deckEstimateResultsFragment.setArguments(args);
        //Add fragment to the fragment container layout, in this case, 'fragment_deck_estimate_results -> deck_fragment_container'
        getFragmentManager().beginTransaction().add(R.id.deck_fragment_container, deckEstimateResultsFragment).commit();

        startActivity(intent);
    }
}

