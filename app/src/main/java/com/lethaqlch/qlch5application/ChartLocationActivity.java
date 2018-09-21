package com.lethaqlch.qlch5application;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Iterator;

public class ChartLocationActivity extends AppCompatActivity {
    private PieChart pieChart;
    private ArrayList<TaiKhoan> listTaiKhoan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chart_location);

        listTaiKhoan = new ArrayList<>();
        if (getIntent() != null){
            listTaiKhoan = getIntent().getParcelableArrayListExtra("listAccount");
        }

        int[] area = new int[]{0,0,0,0};
        for (int i = 0; i < listTaiKhoan.size() ; i++) {
            if (listTaiKhoan.get(i).getDiaChi().equals("Ho Chi Minh")){
                area[1] +=1;
            }else if (listTaiKhoan.get(i).getDiaChi().equals("Ha Noi")){
                area[2] +=1;
            }else if (listTaiKhoan.get(i).getDiaChi().equals("Da Nang")){
                area[3] +=1;
            }else {
                area[0] +=1;
            }

        }

       /* Iterator<TaiKhoan> itrTaiKhoan = listTaiKhoan.iterator();
        while (itrTaiKhoan.hasNext()){

                if (itrTaiKhoan.next().getDiaChi().equals("Ho Chi Minh")){
                    area[1] +=1;
                }else if (itrTaiKhoan.next().getDiaChi().equals("Ha Noi")){
                    area[2] +=1;
                }else if (itrTaiKhoan.next().getDiaChi().equals("Da Nang")){
                    area[3] +=1;
                }else {
                    area[0] +=1;
                }

        }
*/


        pieChart = findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        //pieChart.setCenterTextTypeface(mTfLight);
        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);
        ArrayList<PieEntry>  entries = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            entries.add(new PieEntry(area[i],mParties[i % mParties.length]));
        }

        PieDataSet pieDataSet = new PieDataSet(entries,"Location");
        pieDataSet.setDrawIcons(false);

        pieDataSet.setSliceSpace(3f);
        pieDataSet.setIconsOffset(new MPPointF(0, 40));
        pieDataSet.setSelectionShift(5f);


        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        pieDataSet.setColors(colors);

        PieData data = new PieData(pieDataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        //data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();

        pieChart.animateY(1400);
        // mChart.spin(2000, 0, 360);


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.rgb(139,58,58));

        pieChart.setEntryLabelTextSize(12f);


    }
    protected String[] mParties = new String[] {
            "Khu Vực Khác", "Hồ Chí Minh", "Hà Nội", "Đà Nẵng"
    };


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Chart Location\ndeveloped by Le Thai San");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
}
