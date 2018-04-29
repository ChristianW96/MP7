package com.example.christianwilliams.cocktails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter  extends BaseAdapter implements Filterable
{

    Context c;
    ArrayList<Cocktail> cocktails, tempCocktails;
    ImageView thumbImage;
    CustomFilter cf;

    public CustomAdapter(Context c, ArrayList<Cocktail> cocktails) {
        this.c = c;
        this.cocktails = cocktails;
        this.tempCocktails = cocktails;
    }

    @Override
    public int getCount() {
        return cocktails.size();
    }

    @Override
    public Object getItem(int i) {
        return cocktails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null)
        {
            view=LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);
        }


        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);
        thumbImage = (ImageView) view.findViewById(R.id.thumbImg);



        Cocktail cocktail= (Cocktail) this.getItem(i);

        final String name=cocktail.getName();
        final String id=cocktail.getID();
        final String thumb=cocktail.getThumb();

        nameTxt.setText(name);
        Picasso.get().load(thumb).resize(160,160)
                .into(thumbImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OPEN DETAIL ACTIVITY
                openDetailActivity(name,id,thumb);

            }
        });
        return view;
    }
    ////open activity
    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(c,DetailActivity.class);
        i.putExtra("NAME_KEY",details[0]);
        i.putExtra("ID_KEY",details[1]);
        i.putExtra("THUMB_KEY",details[2]);

        c.startActivity(i);

    }

    @Override
    public Filter getFilter() {
        if (cf == null){
            cf= new CustomFilter();
        }

        return cf;
    }
    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<Cocktail> filters = new ArrayList<>();

                for (int i = 0; i < tempCocktails.size(); i++) {
                    if (tempCocktails.get(i).getName().toUpperCase().contains(constraint)) {
                        Cocktail cocktail = new Cocktail(tempCocktails.get(i));
                        filters.add(cocktail);
                    }
                }
                filterResults.count = filters.size();
                filterResults.values = filters;
            }
            else
            {
                filterResults.count = tempCocktails.size();
                filterResults.values = tempCocktails;
            }
                return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cocktails = (ArrayList<Cocktail>)results.values;
            notifyDataSetChanged();
        }
    }
}