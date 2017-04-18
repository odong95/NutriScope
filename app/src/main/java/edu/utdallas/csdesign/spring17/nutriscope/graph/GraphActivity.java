package edu.utdallas.csdesign.spring17.nutriscope.graph;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;


public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

    private LineChart chart;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private GraphRecyclerViewAdapter adapter;
    private Toolbar toolbar;
    private ArrayList<String> nutrientList;
    private SharedPreferences sharedPref;
    int[] mColors = ColorTemplate.PASTEL_COLORS;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

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
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab_add_nutrient)
        {
            new AddNutrient(sharedPref,this,adapter);
            addSampleData();
        }
    }

    private void addSampleData() {
        LineData data = chart.getData();
        if(data != null) {

            int count = (data.getDataSetCount() + 1);
            List<Entry> entries = new ArrayList<Entry>();
            for (int i = 0; i < 7; i++) {

                float val = (float) (Math.random() * 2500) + 3;
                entries.add(new Entry(i, val));
            }

            LineDataSet set = new LineDataSet(entries, "Total Cals. " + count);
            set.setLineWidth(2.5f);
            set.setCircleRadius(4.5f);

            int color = mColors[count % mColors.length];
            set.setColor(color);
            set.setValueTextSize(10f);
            set.setValueTextColor(Color.BLACK);


            data.addDataSet(set);
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
            counter++;
        }
    }


    private void setAxis()
    {
        final String[] values = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return values[(int) value];
            }

        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMinimum(0);

        YAxis rYAxis = chart.getAxisRight();
        rYAxis.setEnabled(false);
    }
     class MovieTouchHelper extends ItemTouchHelper.SimpleCallback {
        private GraphRecyclerViewAdapter adapter;

        public MovieTouchHelper(GraphRecyclerViewAdapter adapter){
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
            adapter.remove(viewHolder.getAdapterPosition());
            editor.putBoolean(s, false);
            editor.commit();
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        sharedPref.edit().clear().commit();
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
        return true;
    }
    
}