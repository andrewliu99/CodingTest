package com.andrew.codingtest.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.codingtest.R;


/**
 * Created by andrew.liu on 2022/1/18.
 */
public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView passName;
    public TextView passPrice;
    public Button purchaseButton;
    public TextView passDuration;

    public ProductViewHolder(View itemView) {
        super(itemView);

        passName = itemView.findViewById(R.id.itemName);
        passPrice = itemView.findViewById(R.id.itemPrice);
        purchaseButton = itemView.findViewById(R.id.purchase);
        passDuration = itemView.findViewById(R.id.itemDuration);
    }
}
