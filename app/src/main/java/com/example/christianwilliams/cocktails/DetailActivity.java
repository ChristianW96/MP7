package com.example.christianwilliams.cocktails;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;

public class DetailActivity extends AppCompatActivity {

    String jsonDURL="https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
    String jsonDetailURL;
    TextView nameTxt,idTxt, instructionTxt,ingredientsTxt,categoryTxt,glassTxt;
    ImageView thumbImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameTxt = (TextView) findViewById(R.id.nameDetailTxt);
        idTxt = (TextView) findViewById(R.id.idTxt);

        thumbImage = (ImageView) findViewById(R.id.drinkDetailImg);
        instructionTxt = (TextView) findViewById(R.id.instructionTxt);
        ingredientsTxt = (TextView) findViewById(R.id.ingredientsTxt);
        categoryTxt = (TextView) findViewById(R.id.categoryTxt);
        glassTxt = (TextView) findViewById(R.id.glassTxt);

        //GET INTENT
        Intent i=this.getIntent();

        //RECEIVE DATA
        String name=i.getExtras().getString("NAME_KEY");
        String id=i.getExtras().getString("ID_KEY");
        String thumb=i.getExtras().getString("THUMB_KEY");

        //BIND DATA
        nameTxt.setText(name);
        idTxt.setText("Drink ID:" +id);

        jsonDetailURL= jsonDURL + id;
        new JsonDetailDownloader().execute();
        Picasso.get().load(thumb).resize(160,160)
                .into(thumbImage);





    }
    public class JsonDetailDownloader extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return download();
        }

        @Override
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);
            if (jsonData.startsWith("Error")) {
                String error = jsonData;
            } else {
               UpdateUI(jsonData);
            }

        }
    }
    private String download() {
        Object connection = HttpConnector.connect(jsonDetailURL);
        if (connection.toString().startsWith("Error")) {
            return connection.toString();
        }

        try {
            HttpURLConnection con = (HttpURLConnection) connection;
            if (con.getResponseCode() == con.HTTP_OK) {
                //GET INPUT FROM STREAM
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line;
                StringBuffer jsonData = new StringBuffer();

                //READ
                while ((line = br.readLine()) != null) {
                    jsonData.append(line + "n");
                }

                //CLOSE RESOURCES
                br.close();
                is.close();

                //RETURN JSON
                return jsonData.toString();

            } else {
                return "Error " + con.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error " + e.getMessage();

        }
    }
    private void UpdateUI(String json){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Ingredient im;

        try {
            // json returns array inside object. get the object first
            // json returns array inside object. get the object first
            JSONObject jsonRootObj = new JSONObject(json);
            //then get the drink array
            JSONArray ja=  jsonRootObj.getJSONArray("drinks");
            JSONObject jo =ja.getJSONObject(0);
            String drinkId = jo.getString("idDrink");
            String drink = jo.getString("strDrink");
            String category = jo.getString("strCategory");
            String alcoholic = jo.getString("strAlcoholic");
            String glass = jo.getString("strGlass");
            String instructions = jo.getString("strInstructions");
            String drinkThumb = jo.getString("strDrinkThumb");
            im = new Ingredient(jo.getString("strIngredient1"),jo.getString("strMeasure1"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient2"),jo.getString("strMeasure2"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient3"),jo.getString("strMeasure3"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient4"),jo.getString("strMeasure4"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient5"),jo.getString("strMeasure5"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient6"),jo.getString("strMeasure6"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient7"),jo.getString("strMeasure7"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient8"),jo.getString("strMeasure8"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient9"),jo.getString("strMeasure9"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient10"),jo.getString("strMeasure10"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient11"),jo.getString("strMeasure11"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient12"),jo.getString("strMeasure12"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient13"),jo.getString("strMeasure13"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient14"),jo.getString("strMeasure14"));
            ingredients.add(im);
            im = new Ingredient(jo.getString("strIngredient15"),jo.getString("strMeasure15"));
            ingredients.add(im);


            // Build Ingredient List
            StringBuilder sb = new StringBuilder();
            Iterator itr = ingredients.iterator();
            while (itr.hasNext()){
                Ingredient i = (Ingredient)itr.next();
                if (!i.ingredient.equals("") && !i.ingredient.equals("null")){
                    if (i.measurement.contains("\n")){
                        sb.append(i.ingredient + i.measurement);
                    }else {
                        if (!i.measurement.equals("")) {
                            sb.append(i.measurement + " " + i.ingredient + "\n");
                        } else {
                            sb.append(i.ingredient + "\n");
                        }
                        }
                    }
                }

            // BIND ADDITONAL DATA
            instructionTxt.setText(instructions);
            ingredientsTxt.setText(sb);
            categoryTxt.setText("Category: " + category);
            glassTxt.setText("Glass: " + glass);

        } catch (JSONException e) {
        }
    }
    public class Ingredient{
        String ingredient;
        String measurement;

        public Ingredient(String i, String m){
            this.ingredient = i;
            this.measurement = m;
        }
    }

}
