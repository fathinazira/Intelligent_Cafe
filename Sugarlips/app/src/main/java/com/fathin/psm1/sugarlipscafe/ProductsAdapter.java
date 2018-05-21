package com.fathin.psm1.sugarlipscafe;

/**
 * Created by user-pc on 5/19/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage_url())
                .into(holder.imageView);

        holder.textViewName.setText(product.getProduct_name());
        holder.textViewCategory.setText(product.getProduct_category());
        holder.textViewCalories.setText(String.valueOf(product.getProduct_calories()));
        holder.textViewPrice.setText(String.valueOf(product.getProduct_price()));

        holder.cartButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(mCtx, CartActivity.class);
                intent.putExtra("product", product);
                mCtx.startActivity(intent);
            }
        });
        //view.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewCategory, textViewCalories, textViewPrice;
        ImageView imageView;
        Button cartButton;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewCalories = itemView.findViewById(R.id.textViewCalories);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            cartButton = itemView.findViewById(R.id.cartButton);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
