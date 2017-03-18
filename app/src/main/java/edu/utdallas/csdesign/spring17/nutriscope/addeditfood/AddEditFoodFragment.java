package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.R2;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.FoodNutrients;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Nutrient;
import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;
import edu.utdallas.csdesign.spring17.nutriscope.searchfood.SearchFoodActivity;

/**
 * Created by john on 2/20/17.
 */

public class AddEditFoodFragment extends Fragment implements AddEditFoodContract.View {

    static final ButterKnife.Setter<View, Integer> ChangeVisibility
            = new ButterKnife.Setter<View, Integer>() {
        @Override
        public void set(View view, Integer value, int index) {
            view.setVisibility(value);
        }
    };
    final private static String TAG = "AddEditFoodFragment";
    @BindView(R2.id.toolbar) Toolbar toolbar;
    @BindView(R2.id.fab_add_food) FloatingActionButton fab;
    private AddEditFoodContract.Presenter presenter;
    private RecyclerView recyclerView;
    private AddEditFoodRecyclerViewAdapter adapter;

    public AddEditFoodFragment() {

    }

    public static AddEditFoodFragment newInstance() {
        return new AddEditFoodFragment();
    }

    @Override
    public void setPresenter(AddEditFoodContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_add_edit_food, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            case R.id.menu_item_search_food:
                showSearch("");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_food, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "addeditfoodfragment onCreateView");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<Object> defaultNutrients = Lists.newArrayList(new FoodName(""), new Quantity(""), FoodNutrients.CALORIE, FoodNutrients.FAT, FoodNutrients.PROTEIN);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_add_edit_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new AddEditFoodRecyclerViewAdapter(defaultNutrients));

        return view;
    }

    @OnClick(R2.id.fab_add_food)
    public void submit() {
        Log.d(TAG, "fab clicked");
        // FIXME: 3/12/17
        presenter.addFood(3);

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    public void showSearch(String foodName) {
        Log.d(TAG, "show search");
        Intent intent = new Intent(getActivity(), SearchFoodActivity.class);
        intent.putExtra(SearchFoodActivity.EXTRA_FOOD_ID, foodName);
        startActivity(intent);
    }

    @Override
    public void showOverview(String key) {
        Intent intent = new Intent(getActivity(), OverviewActivity.class);
        intent.putExtra(OverviewActivity.EXTRA_CONSUMED_FOOD_KEY, key);
        startActivity(intent);
    }


    @Override
    public void populateContent(List<Object> list) {
        if (adapter == null) {
            adapter = new AddEditFoodRecyclerViewAdapter(list);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }

    }

    class ViewHolderFoodName extends RecyclerView.ViewHolder {

        @BindView(R2.id.edit_text_add_edit_food) TextInputEditText foodName;
        @BindView(R2.id.text_input_wrapper_add_edit_food) TextInputLayout textInputLayout;


        ViewHolderFoodName(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }

        private void setFoodName(String name) {
            foodName.setText(name);
        }

        private void setHint(String hint) {
            textInputLayout.setHint(hint);
        }

    }


    class ViewHolderQuantity extends RecyclerView.ViewHolder {

        @BindView(R2.id.edit_text_add_edit_food) TextInputEditText quantity;
        @BindView(R2.id.text_input_wrapper_add_edit_food) TextInputLayout textInputLayout;


        ViewHolderQuantity(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }

        private void setQuantity(String quantity) {
            this.quantity.setText(quantity);
        }

        private void setHint(String hint) {
            textInputLayout.setHint(hint);
        }

    }

    class ViewHolderFoodNutrient extends RecyclerView.ViewHolder {

        @BindView(R2.id.edit_text_add_edit_food) TextInputEditText nutrient;
        @BindView(R2.id.text_input_wrapper_add_edit_food) TextInputLayout textInputLayout;


        ViewHolderFoodNutrient(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }

        private void setNutrient(String nutrient) {
            this.nutrient.setText(nutrient);
        }

        private void setHint(String hint) {
            textInputLayout.setHint(hint);
        }

    }


    class ViewHolderNutrient extends RecyclerView.ViewHolder {

        @BindView(R2.id.edit_text_add_edit_food) TextInputEditText nutrient;
        @BindView(R2.id.text_input_wrapper_add_edit_food) TextInputLayout textInputLayout;


        ViewHolderNutrient(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }

        private void setNutrient(String nutrient) {
            this.nutrient.setText(nutrient);
        }

        private void setHint(String hint) {
            textInputLayout.setHint(hint);
        }


    }


    private class AddEditFoodRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int NUTRIENT = 0;
        private final int QUANTITY = 1;
        private final int NAME = 2;
        private final int FOODNUTRIENT = 3;
        // The items to display in your RecyclerView
        private List<Object> items;

        // Provide a suitable constructor (depends on the kind of dataset)
        public AddEditFoodRecyclerViewAdapter(List<Object> items) {
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
            if (items.get(position) instanceof FoodNutrients) {
                return FOODNUTRIENT;
            } else if (items.get(position) instanceof Nutrient) {
                return NUTRIENT;
            } else if (items.get(position) instanceof Quantity) {
                return QUANTITY;
            } else if (items.get(position) instanceof FoodName) {
                return NAME;
            }
            return -1;
        }

        /**
         * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
         *
         * @param viewGroup ViewGroup container for the item
         * @param viewType  type of view to be inflated
         * @return viewHolder to be inflated
         */
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            RecyclerView.ViewHolder viewHolder;
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

            switch (viewType) {
                case FOODNUTRIENT:
                    View v0 = inflater.inflate(R.layout.list_item_addeditfood, viewGroup, false);
                    viewHolder = new ViewHolderFoodNutrient(v0);
                    break;
                case NUTRIENT:
                    View v1 = inflater.inflate(R.layout.list_item_addeditfood, viewGroup, false);
                    viewHolder = new ViewHolderNutrient(v1);
                    break;
                case QUANTITY:
                    View v2 = inflater.inflate(R.layout.list_item_addeditfood, viewGroup, false);
                    viewHolder = new ViewHolderQuantity(v2);
                    break;
                case NAME:
                    View v3 = inflater.inflate(R.layout.list_item_addeditfood, viewGroup, false);
                    viewHolder = new ViewHolderFoodName(v3);
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
         * @param position   Item position in the viewgroup.
         */
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            switch (viewHolder.getItemViewType()) {
                case FOODNUTRIENT:
                    ViewHolderFoodNutrient vh0 = (ViewHolderFoodNutrient) viewHolder;
                    configureViewHolderFoodNutrient(vh0, position);
                    break;
                case NUTRIENT:
                    ViewHolderNutrient vh1 = (ViewHolderNutrient) viewHolder;
                    configureViewHolderNutrient(vh1, position);
                    break;
                case QUANTITY:
                    ViewHolderQuantity vh2 = (ViewHolderQuantity) viewHolder;
                    configureViewHolderQuantity(vh2, position);
                    break;
                case NAME:
                    ViewHolderFoodName vh3 = (ViewHolderFoodName) viewHolder;
                    configureViewHolderFoodName(vh3, position);
                    break;
                default:
                    break;
            }
        }

        private void configureViewHolderFoodNutrient(
                ViewHolderFoodNutrient viewHolderFoodNutrient, int position) {
            FoodNutrients nutrient = (FoodNutrients) items.get(position);
            if (nutrient != null) {
                viewHolderFoodNutrient
                        .setHint(nutrient.getNutrientString());
            }
        }

        private void configureViewHolderNutrient(
                ViewHolderNutrient viewHolderNutrient, int position) {
            Nutrient nutrient = (Nutrient) items.get(position);
            if (nutrient != null) {
                viewHolderNutrient
                        .setHint(FoodNutrients.getValue(
                                Integer.parseInt(nutrient.getNutrientId())).getNutrientString());
                viewHolderNutrient.setNutrient(nutrient.getValue());
            }
        }

        private void configureViewHolderFoodName(
                ViewHolderFoodName viewHolderFoodName, int position) {
            FoodName foodName = (FoodName) items.get(position);
            if (foodName != null) {
                viewHolderFoodName.setHint(foodName.label);
                viewHolderFoodName.setFoodName(foodName.name);
            }
        }

        private void configureViewHolderQuantity(
                ViewHolderQuantity viewHolderQuantity, int position) {
            Quantity quantity = (Quantity) items.get(position);
            if (quantity != null) {
                viewHolderQuantity.setHint(quantity.label);
                viewHolderQuantity.setQuantity(quantity.quantity);
            }
        }


        public void setList(List<Object> items) {
            this.items = items;
        }

    }


}
