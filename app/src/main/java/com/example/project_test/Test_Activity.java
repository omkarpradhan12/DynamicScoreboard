package com.example.project_test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class Test_Activity extends AppCompatActivity {

    String x = "";
    String u,g;
    TextView table_head;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button button = (Button) findViewById(R.id.loadbutton);

        table_head = findViewById(R.id.table_head);

        Intent intent = getIntent();


        setTitle(intent.getStringExtra("GameName"));

        String type=intent.getStringExtra("Type");

        if(type.equals("existing"))
        {
            String username = intent.getStringExtra("username");
            String gamename = intent.getStringExtra("gamename");

            continue_game(username,gamename);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PointDBHandler pdb = new PointDBHandler(Test_Activity.this);

                AlertDialog.Builder alert = new AlertDialog.Builder(Test_Activity.this);
                final EditText editText = new EditText(Test_Activity.this);

                alert.setTitle("Enter Number of Players Please!");
                alert.setView(editText);

                alert.setPositiveButton("Add Players", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String et = editText.getText().toString();
                        System.out.println(Integer.parseInt(et));

                        for(int x=Integer.parseInt(et);x>0;x--)
                        {
                            System.out.print(x);
                            player(x+1);
                        }

                        Intent intent = getIntent();
                        Boolean created = pdb.crtable(intent.getStringExtra("UserName"),intent.getStringExtra("GameName"),Integer.parseInt(et));

                    }
                });

                alert.show();

            }
        });

        Button score = (Button) findViewById(R.id.get_score);
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> scores = new ArrayList<String>();
                ArrayList<Integer> scores_val = new ArrayList<Integer>();

                LinearLayout ll = findViewById(R.id.fragment1);
                for(int i=0;i<ll.getChildCount();i++)
                {
                    TextView t = ll.getChildAt(i).findViewById(R.id.score);
                    String x = t.getText().toString();
                    scores.add("Player "+(1+i)+" : "+x);
                    int score = Integer.parseInt(x);
                    scores_val.add(score);
                }


                Intent intent = getIntent();
                PointDBHandler pdb = new PointDBHandler(Test_Activity.this);
                pdb.insertData(intent.getStringExtra("UserName"),intent.getStringExtra("GameName"),scores_val);

                LinearLayout lx = findViewById(R.id.points);

                String joined = TextUtils.join("\t  ", scores_val);

                TextView tx = new TextView(Test_Activity.this);
                tx.setTextSize(20);
                tx.setTypeface(null,Typeface.BOLD);
                tx.setText("X\t  ");
                tx.setText(tx.getText().toString() + joined);
                lx.addView(tx);
                //System.out.println(scores_val);
               // graphmaster();
            }
        });

    }

    public void clear(View v)
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.fragment1);
        ll.removeAllViewsInLayout();

        TextView t = findViewById(R.id.table_head);
        t.setText("X");
        LinearLayout lx = findViewById(R.id.points);
        lx.removeAllViewsInLayout();


    }

    public void continue_game(String username,String gamename)
    {
        PointDBHandler pdb = new PointDBHandler(Test_Activity.this);
        ArrayList<ArrayList<Integer>> scores = pdb.disp_data(username,gamename);

        LinearLayout ll = findViewById(R.id.fragment1);

        for(int i=0;i<scores.size()-1;i++)
        {
            Bundle bundle = new Bundle();
            bundle.putString("edttext", "Player"+(i+1));
            int last = scores.get(i).size() - 1;
            bundle.putString("Score",String.valueOf(scores.get(i).get(last)));
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            Frag_1 fm2 = new Frag_1();
            fm2.setArguments(bundle);
            fragmentTransaction.add(R.id.fragment1, fm2, "HELLO");
            fragmentTransaction.commit();
            table_head.setText(table_head.getText().toString() + "\t  "+x);
        }



    }

    private void graphmaster()
    {

        GraphView graphView;
        graphView = findViewById(R.id.GraphTest);
        graphView.removeAllSeries();
        Intent intent = getIntent();
        PointDBHandler pdb = new PointDBHandler(Test_Activity.this);
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
            graphView.setTitle("My Graph View");
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            seriex.setColor(color);
            seriex.setDrawDataPoints(true);
            seriex.setTitle("Player"+i);
            graphView.addSeries(seriex);

        }


    }

    private void player(int a)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(Test_Activity.this);
        final EditText editText = new EditText(Test_Activity.this);

        alert.setTitle("Enter Player "+(a-1)+" Name !");
        alert.setView(editText);

        alert.setPositiveButton("Add Player", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String et = editText.getText().toString();
                System.out.println(et);

                LinearLayout ll = findViewById(R.id.fragment1);
                int x=0;
                x = ll.getChildCount() + 1;
                Bundle bundle = new Bundle();
                bundle.putString("edttext", et);
                bundle.putString("Score", "0");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                Frag_1 fm2 = new Frag_1();
                fm2.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment1, fm2, "HELLO");
                fragmentTransaction.commit();
                table_head.setText(table_head.getText().toString() + "\t  "+x);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Test_Activity.this,"Player Not Added",Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();
    }

    public void gotograph(View v)
    {
        Intent intent = new Intent(Test_Activity.this,Graph_Display.class);

        Intent intent1 = getIntent();
        String username = intent1.getStringExtra("UserName");
        String gamename = intent1.getStringExtra("GameName");
        intent.putExtra("UserName",username);
        intent.putExtra("GameName",gamename);
        startActivity(intent);
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
                Intent intent = new Intent(Test_Activity.this,MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.closeapp:


                AlertDialog.Builder alert = new AlertDialog.Builder(Test_Activity.this);
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