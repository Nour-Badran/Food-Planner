package com.example.foodplanner.View.Menu.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<IngredientResponse.Ingredient> ingredients = new ArrayList<>();
    private OnIngredientClickListener onIngredientClickListener;

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientResponse.Ingredient ingredient = ingredients.get(position);
        if(ingredient.getMeasure()!=null)
            holder.ingredientName.setText(ingredient.getMeasure() + " " + ingredient.getName());
        else
            holder.ingredientName.setText(ingredient.getName());
        String thumbnailUrl = "https://www.themealdb.com/images/ingredients/" + ingredient.getName().replace(" ", "%20") + ".png";
        Glide.with(holder.itemView.getContext())
                .load(thumbnailUrl)
                .apply(new RequestOptions())
                .placeholder(R.drawable.img_11)
                .into(holder.ingredientImage);

        holder.itemView.setOnClickListener(v -> {
            if (onIngredientClickListener != null) {
                onIngredientClickListener.onIngredientClick(ingredient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredients(List<IngredientResponse.Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public void setOnIngredientClickListener(OnIngredientClickListener listener) {
        this.onIngredientClickListener = listener;
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        ImageView ingredientImage;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredientName);
            ingredientImage = itemView.findViewById(R.id.ingredientImage);
        }
    }

    public interface OnIngredientClickListener {
        void onIngredientClick(IngredientResponse.Ingredient ingredient);
    }
}
