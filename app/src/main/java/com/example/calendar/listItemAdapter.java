package com.example.calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class listItemAdapter extends RecyclerView.Adapter<listItemAdapter.ItemViewHolder> {

    private List<listDbItem> itemList;

    public void setItems(List<listDbItem> itemList) {
        this.itemList = itemList;
    }

    public listItemAdapter(List<listDbItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_textview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.nameTextView.setText(itemList.get(position).name);
        holder.categoryTextView.setText(itemList.get(position).category);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView categoryTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
        }
    }
}