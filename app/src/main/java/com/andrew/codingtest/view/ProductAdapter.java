package com.andrew.codingtest.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.codingtest.OnRecyclerViewItemOnClickListener;
import com.andrew.codingtest.R;
import com.andrew.codingtest.data.Product;
import com.andrew.codingtest.util.TimeUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew.liu on 2022/1/18.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    public enum Category {
        DAY_PASS,
        HOUR_PASS
    }

    private List<Product> dataList;
    private OnRecyclerViewItemOnClickListener listener;

    private Category category = Category.DAY_PASS;

    public ProductAdapter(Category category, ArrayList<Product> productList) {
        this.category = category;
        if (category == Category.DAY_PASS) {
            dataList = productList;
        } else {
            dataList = productList;
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProductViewHolder viewHolder = new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pass, parent, false));

        // Add item click event
        if (listener != null) {
            viewHolder.purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener((View)v.getParent());
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        Product item = dataList.get(position);
        holder.passName.setText(item.getPassType().getPassName());
        holder.passPrice.setText("Rp " + decimalFormat.format(item.getPassType().getPassPrice()));
        holder.purchaseButton.setText(item.isPurchaseStatus() ? "Activate" : "BUY");
        holder.purchaseButton.setTextSize(item.isPurchaseStatus() ? 15 : 18);
        holder.passDuration.setText(item.isPurchaseStatus() ? item.getPassActivatedTime() + " ~ " + item.getPassExpiredTime() : "");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setItemClickListener(OnRecyclerViewItemOnClickListener itemClickListener) {
        listener = itemClickListener;
    }

    public void updateItem(int position) {
        boolean status = dataList.get(position).isPurchaseStatus();

        // Reset all items first
        resetItem();

        // update target
        dataList.get(position).setPurchaseStatus(!status);

        if (category == Category.DAY_PASS) {
            // update Activated / Expired Time
            String activatedTime = TimeUtils.getDateTimeFormatWithHourMinute(System.currentTimeMillis());
            String expiredTime = TimeUtils.getDateTimeFormat(System.currentTimeMillis() +
                    dataList.get(position).getPassType().getPassRange() * TimeUtils.DATE_TIME_MILLS);
            dataList.get(position).setPassActivatedTime(activatedTime);
            dataList.get(position).setPassExpiredTime(expiredTime);
        } else {
            // update Activated / Expired Time
            String activatedTime = TimeUtils.getDateTimeFormatWithHourMinuteSecond(System.currentTimeMillis());
            String expiredTime = TimeUtils.getDateTimeFormatWithHourMinuteSecond(System.currentTimeMillis() +
                    dataList.get(position).getPassType().getPassRange() * TimeUtils.HOUR_TIME_MILLS);
            dataList.get(position).setPassActivatedTime(activatedTime);
            dataList.get(position).setPassExpiredTime(expiredTime);
        }
    }

    public void resetItem() {
        // Reset all items first
        for (int pos = 0; pos < dataList.size(); pos++) {
            dataList.get(pos).setPurchaseStatus(false);
            dataList.get(pos).setPassActivatedTime("");
            dataList.get(pos).setPassExpiredTime("");
        }
    }
}
