package net.androidbootcamp.idrink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    LayoutInflater inflater;
    ArrayList<DrinkCell> drinkCells;
    ArrayList<String> ingredients = new ArrayList<>();
    String str4ingredients, imgURL;

    public CustomAdapter(Context context, ArrayList<DrinkCell> drinkCells) {
        this.inflater = LayoutInflater.from(context);
        this.drinkCells = drinkCells;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.idDrink.setText(drinkCells.get(position).getIdDrink());
        holder.strDrink.setText(drinkCells.get(position).getStrDrink());
        holder.strAlcoholic.setText(drinkCells.get(position).getStrAlcoholic());
        holder.strGlass.setText(drinkCells.get(position).getStrGlass());
        holder.strInstructions.setText("Instructions: " + drinkCells.get(position).getStrInstructions());
        str4ingredients = "Ingredients: " + drinkCells.get(position).getStrIngredient1() + ", " +
                drinkCells.get(position).getStrIngredient2() + ", " +
                drinkCells.get(position).getStrIngredient3();
        holder.strIngredients.setText(str4ingredients);
        imgURL = drinkCells.get(position).getStrDrinkThumb();
        Picasso.get().load(imgURL).resize(600,600).into(holder.imgVDrink);
    }

    @Override
    public int getItemCount() {  return drinkCells.size();  }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idDrink, strDrink, strAlcoholic, strGlass, strInstructions, strIngredients;
        ImageView imgVDrink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idDrink = itemView.findViewById(R.id.txtVidDrink);
            strDrink = itemView.findViewById(R.id.txtVstrDrink);
            strAlcoholic = itemView.findViewById(R.id.txtVstrAlcoholic);
            strGlass = itemView.findViewById(R.id.txtVstrGlass);
            strInstructions = itemView.findViewById(R.id.txtVstrInstructions);
            strIngredients = itemView.findViewById(R.id.txtVstrIngredients);
            imgVDrink = itemView.findViewById(R.id.imgVdrink);
        }
    }
}
