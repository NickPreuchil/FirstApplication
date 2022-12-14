package com.example.firstapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
    public interface OnStateClickListener{
        void onStateClick(Receipt state, int position);
    }

    public interface OnLongClickListener{
        void onLongClick(Receipt state, int position);
    }

    private final OnStateClickListener onClickListener;
    private final OnLongClickListener onLongClickListener;

    private final LayoutInflater inflater;
    private final List<Receipt> receipts;

    public ReceiptAdapter(Context context,
                          List<Receipt> receipts,
                          OnStateClickListener onClickListener,
                          OnLongClickListener onLongClickListener) {
        this.receipts = receipts;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public ReceiptAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiptAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Receipt receipt = receipts.get(position);
        holder.receiptnameView.setText(receipt.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onStateClick(receipt, position);
            }
        });
        if (this.onLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickListener.onLongClick(receipt, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView receiptnameView;
        ViewHolder(View view){
            super(view);
            receiptnameView = view.findViewById(R.id.receiptname);
        }
    }
}
