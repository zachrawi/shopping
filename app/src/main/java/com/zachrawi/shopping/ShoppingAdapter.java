package com.zachrawi.shopping;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    private Context mContext;
    private int mResource;
    private ArrayList<Shopping> mShoppings;
    private OnClickListener mOnclickListener;

    public ShoppingAdapter(Context context, int resource, ArrayList<Shopping> shoppings, OnClickListener onClickListener) {
        mContext = context;
        mResource = resource;
        mShoppings = shoppings;
        mOnclickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 1) buat layout inflater
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        // 2) ambil item view
        View view = layoutInflater.inflate(mResource, parent, false);

        // 3) initiate viewholder
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // 1) ambil data pada posisi bersangkutan
        Shopping shopping = mShoppings.get(position);

        // 2) isi data ke view
        holder.cbDone.setChecked(shopping.isDone());
        holder.tvShopping.setText(shopping.getDescription());

        if (shopping.isDone()) {
            holder.tvShopping.setPaintFlags(holder.tvShopping.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvShopping.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            holder.tvShopping.setPaintFlags(holder.tvShopping.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvShopping.setTypeface(null, Typeface.NORMAL);
        }

        holder.cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                mOnclickListener.onCheck(holder.getAdapterPosition(), checkBox.isChecked());
            }
        });

        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnclickListener.onRemove(holder.getAdapterPosition());
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnclickListener.onEdit(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShoppings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbDone;
        TextView tvShopping;
        ImageView ivRemove;
        ImageView ivEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // ambil referensi ke layout
            cbDone = itemView.findViewById(R.id.cbDone);
            tvShopping = itemView.findViewById(R.id.tvShopping);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }

    public interface OnClickListener {
        void onCheck(int position, boolean checked);
        void onRemove(int position);
        void onEdit(int position);
    }
}
