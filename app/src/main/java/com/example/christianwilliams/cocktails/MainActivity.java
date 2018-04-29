package com.example.christianwilliams.cocktails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    String jsonDetailURL;
    String jsonURL="https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic";
    ListView lv;
    // Search EditText
    EditText inputSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(this);
        new JsonListDownloader(MainActivity.this,jsonURL, lv).execute();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        CustomAdapter myAdapter= (CustomAdapter)this.lv.getAdapter();
        myAdapter.getFilter().filter(s);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
