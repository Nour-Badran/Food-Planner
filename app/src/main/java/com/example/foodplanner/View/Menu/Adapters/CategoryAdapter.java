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
import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Interfaces.OnCategoryClickListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<CategoryResponse.Category> categories = new ArrayList<>();
    private OnCategoryClickListener onCategoryClickListener;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryResponse.Category category = categories.get(position);
        holder.categoryName.setText(category.getName());
        Glide.with(holder.itemView.getContext())
                .load(category.getThumbnailUrl()).apply(new RequestOptions())
                .placeholder(R.drawable.img_11)
                .into(holder.categoryImage);

        holder.itemView.setOnClickListener(v -> {
            if (onCategoryClickListener != null) {
                onCategoryClickListener.onCategoryClick(category);
            }
        });
    }
    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.onCategoryClickListener = listener;
    }
    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<CategoryResponse.Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.ingredientName);
            categoryImage = itemView.findViewById(R.id.ingredientImage);
        }
    }
}