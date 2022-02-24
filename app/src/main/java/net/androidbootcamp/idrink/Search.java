package net.androidbootcamp.idrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Search extends AppCompatActivity {
    RecyclerView recyclerView;         // the widget
    CustomAdapter customAdapter;       // for inflater to move items in a layout row
    ArrayList<DrinkCell> drinkCells;   // for a list of cell items in a row
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recycVListDrinks);
        drinkCells = new ArrayList<DrinkCell>();
        requestQueue = Volley.newRequestQueue(this);

    }   // onCreate

    private void getdata(String url)  {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            // create request and implement listener
            @Override
            public void onResponse(JSONObject response) {
                // get JSON data
                drinkCells.clear();
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("drinks");    // find JSONArrayObjs
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                int len = jsonArray.length();

                for(int i=0; i<jsonArray.length(); i++)  {
                    // go through JSONArrayObjs
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);     // use obj
                        DrinkCell drinkCell = new DrinkCell();
                        // set value to cell obj
                        drinkCell.setIdDrink(jsonObject.getString("idDrink").toString());
                        drinkCell.setStrDrink(jsonObject.getString("strDrink").toString());
                        drinkCell.setStrAlcoholic(jsonObject.getString("strAlcoholic").toString());
                        drinkCell.setStrGlass(jsonObject.getString("strGlass").toString());
                        drinkCell.setStrInstructions(jsonObject.getString("strInstructions").toString());
                        drinkCell.setStrDrinkThumb(jsonObject.getString("strDrinkThumb").toString());
                        drinkCell.setStrIngredient1(jsonObject.getString("strIngredient1").toString());
                        drinkCell.setStrIngredient2(jsonObject.getString("strIngredient2").toString());
                        drinkCell.setStrIngredient3(jsonObject.getString("strIngredient3").toString());
                        drinkCells.add(drinkCell);      // add obj to list
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                customAdapter = new CustomAdapter(getApplicationContext(), drinkCells);
                // use obj list and inflate with custom adapter
                recyclerView.setAdapter(customAdapter);     // apply to recyclerView widget
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        }); // JSONObjectRequest)

        requestQueue.add(jsonObjectRequest);    // add to volley queue to be process
    }   // getData

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search for Drinks");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private final Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (p.matcher(query).find()) {
                    String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + query;
                    getdata(url);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                 String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + newText;
//                 getdata(url);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }  // menu
}   // class Search