package com.lethaqlch.qlch5application;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    private ArrayList<DonHangItem> donHangItemArrayList;
    private ArrayList<String> dateArr;

    private int[] sumMonth;
    private BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        donHangItemArrayList = new ArrayList<>();
        dateArr = new ArrayList<>();
        mChart = findViewById(R.id.chartBar);
        sumMonth = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


        if (getIntent() != null
                ) {
            donHangItemArrayList = getIntent().getParcelableArrayListExtra("listDonHang");
            dateArr = getIntent().getStringArrayListExtra("monthDH");
        }
      //  float start = 1f;

        for (int i = 0; i < dateArr.size() ; i++) {
            switch (dateArr.get(i)){
                case "01": sumMonth[0]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "02": sumMonth[1]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "03": sumMonth[2]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "04": sumMonth[3]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "05": sumMonth[4]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "06": sumMonth[5]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "07": sumMonth[6]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "08": sumMonth[7]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "09": sumMonth[8]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "10": sumMonth[9]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "11": sumMonth[10]+=donHangItemArrayList.get(i).getTongTien(); break;
                case "12": sumMonth[11]+=donHangItemArrayList.get(i).getTongTien(); break;
            }
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 1; i < sumMonth.length+1; i++) {
            float val = (float) sumMonth[(i-1)];


            yVals1.add(new BarEntry(i, val));

        }
        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");

            set1.setDrawIcons(false);

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.GREEN);
            colors.add(Color.BLUE);
            colors.add(Color.RED);

            set1.setColors(colors);


            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);


            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);

            data.setBarWidth(0.9f);


            mChart.setData(data);
            mChart.animateY(3000);
        }


        XAxis xAxis = mChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                ArrayList<String> mounth = new ArrayList<>();
                mounth.add("Jan");
                mounth.add("Feb");
                mounth.add("Mar");
                mounth.add("Apr");
                mounth.add("May");
                mounth.add("June");
                mounth.add("July");
                mounth.add("Aug");
                mounth.add("Sept");
                mounth.add("Oct");
                mounth.add("Nov");
                mounth.add("Dec");
                for (int i = 0; i < value; i++) {

                }
                if (value % 1 == 0) {
                    int second = (int) value / 1; // get second from value

                    return mounth.get(second - 1); //make it a string and return
                } else {
                    return ""; // return empty for other values where you don't want to print anything on the X Axis
                }
            }
        });


    }

  /*  private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();


        for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);


            yVals1.add(new BarEntry(i, val));

        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");

            set1.setDrawIcons(false);

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.GREEN);
            colors.add(Color.BLUE);
            colors.add(Color.RED);

            set1.setColors(colors);


            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);


            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);

            data.setBarWidth(0.9f);


            mChart.setData(data);
            mChart.animateY(3000);
        }
    }*/
}
