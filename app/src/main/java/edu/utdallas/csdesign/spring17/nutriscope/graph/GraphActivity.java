package edu.utdallas.csdesign.spring17.nutriscope.graph;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.Nutrition;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionFirebaseRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionFirebaseSpecification;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.FoodNutrients;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.User;
import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;


public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

    private LineChart chart;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private GraphRecyclerViewAdapter adapter;
    private Toolbar toolbar;
    private ArrayList<FoodNutrients> nutrientList;
    private SharedPreferences sharedPref;
    private NutritionRepository repo;
    private DatabaseReference db;
    private int calGoal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setTitle("Graph");
        chart = (LineChart) findViewById(R.id.demochart);
        fab = (FloatingActionButton) findViewById(R.id.fab_add_nutrient);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nutrientList = Lists.newArrayList();
        adapter = new GraphRecyclerViewAdapter(nutrientList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_nutrient_graph_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new MovieTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        fab.setOnClickListener(this);
        sharedPref = this.getSharedPreferences("GRAPH_PREFERENCE", Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
        setAxis();
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setData(new LineData());
        chart.invalidate();
        repo = new NutritionRepository(new NutritionFirebaseRepository());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                /* FIXME
                if(!TextUtils.isEmpty(u.getCalorieGoal())){
                    calGoal = Integer.parseInt(u.getCalorieGoal());
                }
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.addListenerForSingleValueEvent(valueEventListener);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_add_nutrient) {
            AddNutrient an = new AddNutrient(sharedPref, this, adapter, new AddNutrient.CallBackImpl() {

                @Override
                public void onComplete() {
                    LineData data = chart.getData();
                    data.clearValues();
                    repo.queryItem(new NutritionFirebaseSpecification(LocalDate.now().minusDays(14).toEpochDay(), LocalDate.now().toEpochDay()), new Repository.QueryCallback<Nutrition>() {
                        @Override
                        public void onQueryComplete(List<Nutrition> items) {
                            for (int n = 0; n < nutrientList.size(); n++) {
                                Map<String, Double> map = new HashMap<String, Double>();
                                for (int i = 0; i < items.size(); i++) {
                                    double val = 0;
                                    double goal = 0;
                                    if (items.get(i).getNutrients().containsKey(Integer.toString(nutrientList.get(n).getNutrientId()))) {
                                        val = items.get(i).getNutrient(Integer.toString(nutrientList.get(n).getNutrientId()));
                                        if(nutrientList.get(n).equal(208))
                                        {
                                            goal = calGoal;
                                        }
                                        else
                                        {
                                            goal = nutrientList.get(n).getNutrientValue();
                                        }

                                        val /= goal;
                                        String d = toDate(items.get(i).getLocalDate());
                                        map.put(d, val);
                                    }
                                }
                                addData(nutrientList.get(n).getNutrientString(), map);
                            }
                        }

                        @Override
                        public void onDataNotAvailable() {

                        }
                    });
                }
            });


        }
    }

    private String toDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd-YY");
        return date.format(formatter);
    }

    private HashMap<Integer, String> getDateMap() {
        String[] arr = getRange();
        HashMap<Integer, String> map = new HashMap<Integer, String>();

        for (int i = 0; i < arr.length; i++) {
            map.put(i, arr[i]);
        }

        return map;
    }

    private void addData(String name, Map<String, Double> map) {
        LineData data = chart.getData();
        Map<Integer, String> range = getDateMap();

        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < 14; i++) {

            String curDate = range.get(i);
            if (map.containsKey(curDate)) {
                entries.add(new Entry(i, map.get(curDate).floatValue()));
            } else {
                entries.add(new Entry(i, 0));
            }

        }

        String[] parts = name.split("-");
        String p1 = parts[0];
        LineDataSet set = new LineDataSet(entries, p1);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);

        set.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");
                //return mFormat.format(value * 100) + " %";
                return "";
            }
        });


        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        set.setColor(color);



        data.addDataSet(set);
        data.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();

    }


    private void setAxis() {
        final String[] values = getRange();

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String[] parts = values[(int) value].split("-");
                String p1 = parts[0];
                return p1;
            }

        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(1);
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");
                return mFormat.format(value * 100) + " %";
            }
        });
        YAxis rYAxis = chart.getAxisRight();
        rYAxis.setEnabled(false);
    }

    private String[] getRange() {
        String[] arr = new String[14];
        LocalDate today = LocalDate.now();
        int k = 0;
        for (int i = 13; i >= 0; i--) {
            LocalDate cur = today.minusDays(i);
            arr[k] = toDate(cur);
            k++;
        }

        return arr;
    }


    class MovieTouchHelper extends ItemTouchHelper.SimpleCallback {
        private GraphRecyclerViewAdapter adapter;

        public MovieTouchHelper(GraphRecyclerViewAdapter adapter) {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            SharedPreferences.Editor editor = sharedPref.edit();
            TextView t = (TextView) viewHolder.itemView.findViewById(R.id.title_text_view);
            String s = t.getText().toString().toLowerCase();

            LineData data = chart.getData();
            if (data.removeDataSet(viewHolder.getAdapterPosition())) {
                chart.notifyDataSetChanged();
                chart.invalidate();
            }
            adapter.remove(viewHolder.getAdapterPosition());
            editor.putBoolean(s, false);
            editor.commit();


        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        sharedPref.edit().clear().commit();
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        sharedPref.edit().clear().commit();
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

}