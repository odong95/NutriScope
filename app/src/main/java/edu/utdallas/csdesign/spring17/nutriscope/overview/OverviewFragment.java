package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by john on 2/10/17.
 */

public class OverviewFragment extends Fragment implements OverviewContract.View {

    private RecyclerView overviewRecyclerView;
    private OverviewRecyclerViewAdapter adapter;

    private OverviewContract.Presenter presenter;


    public OverviewFragment() {

    }

    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(@NonNull OverviewContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        overviewRecyclerView = (RecyclerView) view.findViewById(R.id.overview_recycler_view);
        overviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    @Override
    public void showHistory(List<Trackable> list) {

        if (adapter == null) {

            adapter = new OverviewRecyclerViewAdapter(list);
            overviewRecyclerView.setAdapter(adapter);
        }
        else {
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }

    }


}
