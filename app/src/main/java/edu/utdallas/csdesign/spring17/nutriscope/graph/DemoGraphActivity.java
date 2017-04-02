package edu.utdallas.csdesign.spring17.nutriscope.graph;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;


public class DemoGraphActivity extends AppCompatActivity implements View.OnClickListener {

    LineChart chart;
    Button button;
    Button g;
    int[] mColors = ColorTemplate.PASTEL_COLORS;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demograph);

        chart = (LineChart) findViewById(R.id.demochart);
        button = (Button) findViewById(R.id.button2);
        g = (Button) findViewById(R.id.go_back_button);
        button.setOnClickListener(this);
        g.setOnClickListener(this);

        setAxis();
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setData(new LineData());
        chart.invalidate();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.button2)
        {
            if(counter < 5) {
                addSampleData();
            }
        }
        if(view.getId()== R.id.go_back_button)
        {
            startActivity(new Intent(this, OverviewActivity.class));
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
}
