package edu.utdallas.csdesign.spring17.nutriscope.graph;


import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.R;

public class GraphRecyclerViewAdapter extends RecyclerView.Adapter<GraphRecyclerViewAdapter.ViewHolder>{
    private List<String> items;
    public GraphRecyclerViewAdapter(List<String> items) {
        this.items = items;
    }
    @Override
    public GraphRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_graph_nutrients,viewGroup, false);
        GraphRecyclerViewAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(String item) {
        items.add(item);
        notifyItemInserted(items.size()-1);
        notifyDataSetChanged();
    }



    public void remove(int index) {
        items.remove(index);
        notifyItemRemoved(index);
        notifyDataSetChanged();

    }

    public void clear() {
        int size = this.items.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.items.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.title_text_view);

        }



    }
}
