package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.utdallas.csdesign.spring17.nutriscope.R;

/**
 * Created by john on 2/24/17.
 */

public class ViewHolderFood extends RecyclerView.ViewHolder {

    private TextView foodName;

    public ViewHolderFood(View v) {
        super(v);
        foodName = (TextView) v.findViewById(R.id.list_item_food_name);
    }

    public TextView getFoodName() {
        return foodName;
    }

    public void setFoodName(TextView foodName) {
        this.foodName = foodName;
    }

}
