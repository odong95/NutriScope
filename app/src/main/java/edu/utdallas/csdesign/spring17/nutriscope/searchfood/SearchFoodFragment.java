package edu.utdallas.csdesign.spring17.nutriscope.searchfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.R2;
import edu.utdallas.csdesign.spring17.nutriscope.addeditfood.AddEditFoodActivity;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Item;

/**
 * Created by john on 3/5/17.
 */

public class SearchFoodFragment extends Fragment implements SearchFoodContract.View {

    private final static String TAG = "SearchFoodFragment";

    FirebaseAuth auth;
    @BindView(R2.id.toolbar) Toolbar toolbar;
    @BindView(R2.id.search_food_text) EditText searchFoodEditText;
    RecyclerView searchFoodRecyclerView;

    /*    @BindView(R2.id.search_food_button)
        Button searchFoodButton;*/
    private SearchFoodContract.Presenter presenter;
    private ItemAdapter resultAdapter;

    public SearchFoodFragment() {

    }

    public static SearchFoodFragment newInstance() {
        return new SearchFoodFragment();
    }

    @Override
    public void setPresenter(SearchFoodContract.Presenter presenter) {
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
                submit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_food, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        searchFoodRecyclerView = (RecyclerView) view.findViewById(R.id.search_food_recycler_view);

        List<Item> results = new ArrayList<>();

        resultAdapter = new ItemAdapter(results);

        searchFoodRecyclerView.setAdapter(resultAdapter);

        searchFoodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchFoodEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG, "Enter pressed");
                    submit();
                }
                return false;
            }
        });


        return view;
    }


    public void submit() {
        Log.d(TAG, searchFoodEditText.getText().toString());
        presenter.searchFood(searchFoodEditText.getText().toString());

    }

    @Override
    public void showResults(List<Item> results) {

        if (resultAdapter == null) {
            resultAdapter = new ItemAdapter(results);
            searchFoodRecyclerView.setAdapter(resultAdapter);
        } else {
            resultAdapter.setResults(results);
            resultAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showAddEditFood(String name, String id) {
        Intent intent = new Intent(getActivity(), AddEditFoodActivity.class);
        intent.putExtra(AddEditFoodActivity.EXTRA_FOOD_ID, name);
        intent.putExtra(AddEditFoodActivity.EXTRA_NDB_ID, id);
        startActivity(intent);
    }


    private class ItemHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        private TextView name;

        private Item result;

        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            name = (TextView) itemView.findViewById(R.id.food_name);
        }

        public void bindItem(Item result) {
            this.result = result;
            name.setText(result.getName());

        }

        @Override
        public void onClick(View v) {
            presenter.chooseFood(result.getName(), result.getNdbno());
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private List<Item> results;

        public ItemAdapter(List<Item> results) {
            this.results = results;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_search_result, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.bindItem(results.get(position));
        }

        @Override
        public int getItemCount() {
            return this.results.size();
        }

        public void setResults(List<Item> results) {
            this.results = results;
        }
    }
}
