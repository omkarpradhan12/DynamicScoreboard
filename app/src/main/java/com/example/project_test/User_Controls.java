package com.example.project_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class User_Controls extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_controls);

        TextView tv = findViewById(R.id.username);
        Intent intent = getIntent();

        tv.setText("Welcome , "+intent.getStringExtra("Uname")+" !");

        Button ng = findViewById(R.id.newgame);

        ng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(User_Controls.this);
                final EditText editText = new EditText(User_Controls.this);

                alert.setTitle("Enter Name for the Game please !");
                alert.setView(editText);

                alert.setPositiveButton("Start Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = new Intent(User_Controls.this,Test_Activity.class);
                        intent1.putExtra("UserName",intent.getStringExtra("Uname"));
                        intent1.putExtra("GameName",editText.getText().toString());
                        intent1.putExtra("Type","New");
                        GameDBHandler gdb = new GameDBHandler(User_Controls.this);
                        Boolean gameexist = gdb.checkgamename(editText.getText().toString());

                        if(gameexist)
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(User_Controls.this);
                            alert.setTitle("Sorry !");
                            final TextView textView = new TextView(User_Controls.this);
                            textView.setText("Game with same name Exists");
                            alert.setView(textView);
                            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alert.show();

                        }
                        else {
                            startActivity(intent1);
                        }

                    }
                });

                alert.show();

            }
        });

        Button vg = findViewById(R.id.viewgame);
        vg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PointDBHandler pdb = new PointDBHandler(User_Controls.this);
                System.out.println("Point.db");
                pdb.vd();

                LoginDBHandler ldb = new LoginDBHandler(User_Controls.this);
                System.out.println("Login.db");
                ldb.vd();

                GameDBHandler gdb = new GameDBHandler(User_Controls.this);
                System.out.println("Games.db");
                gdb.vd();

                Intent intent = new Intent(User_Controls.this,ViewGames.class);
                Intent intent1 = getIntent();
                intent.putExtra("username",intent1.getStringExtra("Uname"));
                startActivity(intent);
            }
        });
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
                AlertDialog.Builder alert = new AlertDialog.Builder(User_Controls.this);
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