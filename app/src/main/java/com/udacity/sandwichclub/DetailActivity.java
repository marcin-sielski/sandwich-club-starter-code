package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        if (!isNetworkAvailable()) {
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        StringBuffer alsoKnownAsText = new StringBuffer();
        for (int index = 0; index < alsoKnownAs.size(); index++) {
            alsoKnownAsText.append(alsoKnownAs.get(index));
            if (index < alsoKnownAs.size() - 1) {
                alsoKnownAsText.append(", ");
            }
        }
        if (alsoKnownAs.size() > 0) {
            alsoKnownAsTv.setText(alsoKnownAsText);
        } else {
            findViewById(R.id.also_known_as_label).setVisibility(View.GONE);
            findViewById(R.id.also_known_tv).setVisibility(View.GONE);
        }

        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        List<String> ingredients = sandwich.getIngredients();
        StringBuffer ingredientsText = new StringBuffer();
        for (int index = 0; index < ingredients.size(); index++) {
            ingredientsText.append(ingredients.get(index));
            if (index < ingredients.size() - 1) {
                ingredientsText.append(", ");
            }
        }
        if (ingredientsText.length() > 0) {
            ingredientsTv.setText(ingredientsText);
        } else {
            findViewById(R.id.ingredients_label).setVisibility(View.GONE);
            findViewById(R.id.ingredients_tv).setVisibility(View.GONE);
        }

        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        String placeOfOriginText = sandwich.getPlaceOfOrigin();
        if (placeOfOriginText.length() > 0) {
            placeOfOriginTv.setText(placeOfOriginText);
        } else {
            findViewById(R.id.place_of_origin_label).setVisibility(View.GONE);
            findViewById(R.id.origin_tv).setVisibility(View.GONE);
        }

        TextView descriptionTv = findViewById(R.id.description_tv);
        String descriptionText = sandwich.getDescription();
        if (descriptionText.length() > 0) {
            descriptionTv.setText(descriptionText);
        } else {
            findViewById(R.id.description_label).setVisibility(View.GONE);
            findViewById(R.id.description_tv).setVisibility(View.GONE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
