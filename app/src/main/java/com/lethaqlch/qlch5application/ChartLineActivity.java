package com.lethaqlch.qlch5application;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class ChartLineActivity extends AppCompatActivity {
    private LineChart lineChart;
    private ArrayList<DonHangItem> donHangItemArrayList;
    private ArrayList<String> dateArr;

    private int[] sumMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chart_line);

        donHangItemArrayList = new ArrayList<>();
        dateArr = new ArrayList<>();
        sumMonth = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        if (getIntent() != null
                ) {
            donHangItemArrayList = getIntent().getParcelableArrayListExtra("listDonHang");
            dateArr = getIntent().getStringArrayListExtra("monthDH");
        }
        for (int i = 0; i < dateArr.size(); i++) {
            switch (dateArr.get(i)) {
                case "01":
                    sumMonth[0] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "02":
                    sumMonth[1] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "03":
                    sumMonth[2] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "04":
                    sumMonth[3] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "05":
                    sumMonth[4] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "06":
                    sumMonth[5] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "07":
                    sumMonth[6] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "08":
                    sumMonth[7] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "09":
                    sumMonth[8] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "10":
                    sumMonth[9] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "11":
                    sumMonth[10] += donHangItemArrayList.get(i).getTongTien();
                    break;
                case "12":
                    sumMonth[11] += donHangItemArrayList.get(i).getTongTien();
                    break;
            }
        }
        init();

        XAxis xAxis = lineChart.getXAxis();

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


        ArrayList<Entry> yVals1 = new ArrayList<>();
        for (int i = 1; i < sumMonth.length + 1; i++) {
            float val = (float) sumMonth[(i - 1)];


            yVals1.add(new Entry(i, val));

        }
        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "Doanh thu");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);


            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            lineChart.setData(data);
        }


        lineChart.setDrawGridBackground(false);

        // no description text
        lineChart.getDescription().setEnabled(true);
        Description description = new Description();
        description.setText("Bieu do doanh thu theo thang");
        lineChart.setDescription(description);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.animateY(1400);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(true);


    }

    private void init() {
        lineChart = findViewById(R.id.lineChart);
    }


}
