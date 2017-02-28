package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.DailySummary;
import edu.utdallas.csdesign.spring17.nutriscope.data.NutritionTracker;
import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by john on 2/10/17.
 */

public class OverviewFragment extends Fragment implements OverviewContract.View {

    private RecyclerView summaryRecyclerView;
    private SummaryAdapter adapter;

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

        summaryRecyclerView = (RecyclerView) view.findViewById(R.id.overview_recycler_view);
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    public void showSummaries(List<DailySummary> dailySummaryList) {
        NutritionTracker nutritionTracker = NutritionTracker.get();
        List<DailySummary> history = nutritionTracker.getDailyHistory();

        adapter = new SummaryAdapter(history);
        summaryRecyclerView.setAdapter(adapter);

    }

    private class TrackableHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView name;

        private Trackable trackable;

        public TrackableHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            dateTextView = (TextView) itemView.findViewById(R.id.list_item_daily_summary_date);
        }

        public void bindCrime(DailySummary summary) {
            this.summary = summary;
            dateTextView.setText(summary.getDay().toString());

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    summary.getDay() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class TrackableAdapter extends RecyclerView.Adapter<TrackableHolder> {

        private List<DailySummary> summaries;

        public SummaryAdapter(List<DailySummary> summaries) {
            this.summaries = summaries;
        }

        @Override
        public SummaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_daily_summary, parent, false);
            return new SummaryHolder(view);
        }

        @Override
        public void onBindViewHolder(SummaryHolder holder, int position) {
            DailySummary summary = summaries.get(position);
            holder.bindCrime(summary);
        }

        @Override
        public int getItemCount() {
            return this.summaries.size();
        }
    }

}
