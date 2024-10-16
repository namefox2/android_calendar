package com.example.calendar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class listItemAdapter extends RecyclerView.Adapter<listItemAdapter.ItemViewHolder> {

    private List<listDbItem> itemList;
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    public void setItems(List<listDbItem> itemList) {
        this.itemList = itemList;
    }

    public listItemAdapter(List<listDbItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_HEADER){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_header, parent, false);
            return new ItemViewHolder(view, true);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_textview, parent, false);
            return new ItemViewHolder(view, false);
        }
    }
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM; // 헤더와 일반 항목 구분
    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if(position == 0) {
        } else {
            listDbItem currentItem = itemList.get(position - 1);
            holder.numTextView.setText(String.valueOf(currentItem.id));
            holder.nameTextView.setText(currentItem.name);
            holder.categoryTextView.setText(currentItem.category);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size() + 1;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView categoryTextView;
        public TextView numTextView;

        public ItemViewHolder(View itemView, boolean isHeader) {
            super(itemView);
            numTextView = itemView.findViewById(R.id.numTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
        }
    }
}