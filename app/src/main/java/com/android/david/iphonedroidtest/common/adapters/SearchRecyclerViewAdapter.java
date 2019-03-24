package com.android.david.iphonedroidtest.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.david.iphonedroidtest.R;
import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.SearchItem;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    public List<SearchItem> repositories;
    private LayoutInflater inflater;
    private ItemClickListener listener;

    public SearchRecyclerViewAdapter(Context context, List<SearchItem> data) {
        this.inflater = LayoutInflater.from(context);
        this.repositories = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchItem repository = repositories.get(position);
        holder.textView1.setText(repository.getName());
        holder.textView2.setText(repository.getDescription());
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public void addItems(List<SearchItem> list) {
        repositories.addAll(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView1;
        TextView textView2;

        ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) listener.onItemClick(view, getAdapterPosition());
        }
    }

    SearchItem getItem(int id) {
        return repositories.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}