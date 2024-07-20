package com.example.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Calendar_view extends LinearLayout {

    private RecyclerView recyclerView;
//    private MyAdapter adapter;

    public Calendar_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_item, this, true);

        recyclerView = view.findViewById(R.id.calendar_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

//    public void setAdapterData(List<String> data){
//        adapter = new MyAdapter(getContext(), data);
//        recyclerView.setAdapter(adapter);
//    }
//    public void setItemClickListener(MyAdapter.ItemClickListener itemClickListener) {
//        if(adapter != null) {
//            adapter.setClickListener(itemClickListener);
//        }
//    }

}
