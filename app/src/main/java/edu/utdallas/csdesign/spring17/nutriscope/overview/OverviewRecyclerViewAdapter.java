package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.Food;
import edu.utdallas.csdesign.spring17.nutriscope.data.FoodConsumed;
import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * Created by john on 2/24/17.
 */

public class OverviewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Trackable> items;

    private final int FOOD = 0;

    // Provide a suitable constructor (depends on the kind of dataset)
    public OverviewRecyclerViewAdapter(List<Trackable> items) {
        this.items = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof FoodConsumed) {
            return FOOD;
        }
        return -1;
    }

    /**
     * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
     *
     * @param viewGroup ViewGroup container for the item
     * @param viewType type of view to be inflated
     * @return viewHolder to be inflated
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case FOOD:
                View v1 = inflater.inflate(R.layout.list_item_food, viewGroup, false);
                viewHolder = new ViewHolderFood(v1);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    /**
     * This method internally calls onBindViewHolder(ViewHolder, int) to update the
     * RecyclerView.ViewHolder contents with the item at the given position
     * and also sets up some private fields to be used by RecyclerView.
     *
     * @param viewHolder The type of RecyclerView.ViewHolder to populate
     * @param position Item position in the viewgroup.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case FOOD:
                ViewHolderFood vh1 = (ViewHolderFood) viewHolder;
                configureViewHolderFood(vh1, position);
                break;
            default:
                break;
        }
    }


    private void configureViewHolderFood(ViewHolderFood vhf, int position) {
        FoodConsumed food = (FoodConsumed) items.get(position);
        if (food != null) {
            vhf.getFoodName().setText("Name: " + food.getFood().getName());
        }
    }


    public void setList(List<Trackable> trackable) { this.items = trackable; }

}



