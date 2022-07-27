
package com.example.project_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

public class Graph_Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_display);

        GraphView graphView;
        graphView = findViewById(R.id.GraphTest);
        graphView.removeAllSeries();
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("GameName"));
        PointDBHandler pdb = new PointDBHandler(Graph_Display.this);
        ArrayList<ArrayList<Integer>> scores = pdb.disp_data(intent.getStringExtra("UserName"), intent.getStringExtra("GameName"));

        int nplayers = scores.size() - 1;

        for(int i=1;i<=nplayers;i++)
        {

            DataPoint[] dp = new DataPoint[scores.get(i).size()];
            for(int j=0;j<scores.get(i).size();j++)
            {
                int rno = j;
                int score = scores.get(i).get(j);

                dp[j] = new DataPoint(j,score);

            }
            LineGraphSeries<DataPoint> seriex = new LineGraphSeries<>(dp);
            seriex.setAnimated(true);
            graphView.setTitle(intent.getStringExtra("GameName"));

            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            seriex.setColor(color);
            seriex.setDrawDataPoints(true);
            seriex.setTitle("Player"+i);
            graphView.addSeries(seriex);
            graphView.getLegendRenderer().setVisible(true);

        }
    }

    public void fabback(View v)
    {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.closeapp:
                AlertDialog.Builder alert = new AlertDialog.Builder(Graph_Display.this);
                alert.setTitle("Sure ? ");
                alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
                return true;
            case R.id.back:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}