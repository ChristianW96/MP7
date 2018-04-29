package com.example.christianwilliams.cocktails;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonListParser extends AsyncTask<Void,Void,Boolean>{

    Context c;
    String jsonData;
    ListView lv;

    ProgressDialog pd;
    ArrayList<Cocktail> cocktails=new ArrayList<>();

    public JsonListParser(Context c, String jsonData, ListView lv) {
        this.c = c;
        this.jsonData = jsonData;
        this.lv = lv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Cocktail List");
        pd.setMessage("Updating...Please wait");
        pd.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return parse();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);

        pd.dismiss();
        if(isParsed)
        {
            //BIND
            lv.setAdapter(new CustomAdapter(c,cocktails));
        }else
        {
            Toast.makeText(c, "Unable To Parse,Check Your Log output", Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean parse()
    {
        try
        {
            // json returns array inside object. get the object first
            JSONObject jsonRootObj = new JSONObject(jsonData);
            //then get the drink array
            JSONArray ja=  jsonRootObj.getJSONArray("drinks");

            JSONObject jo;


            cocktails.clear();
            Cocktail cocktail;

            for (int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);

                String name=jo.getString("strDrink");
                String id=jo.getString("idDrink");
                String thumb=jo.getString("strDrinkThumb");

                cocktail=new Cocktail();

                cocktail.setName(name);
                cocktail.setID(id);
                cocktail.setThumb(thumb);

                cocktails.add(cocktail);
            }

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}