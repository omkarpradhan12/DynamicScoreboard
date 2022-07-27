package com.example.project_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewGames extends AppCompatActivity {

    ListView listView;
    ArrayList<String> gamenames;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);

        listView = findViewById(R.id.Games);

        Intent intent = getIntent();

        GameDBHandler gdb = new GameDBHandler(this);
        gamenames = gdb.dispdata(intent.getStringExtra("username"));

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,gamenames);
        listView.setAdapter(arrayAdapter);

        registerForContextMenu(listView);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.table_options, menu);
        menu.setHeaderTitle("Select The Action");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==R.id.continuegame){

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;

            Intent intent = new Intent(ViewGames.this,Test_Activity.class);
            intent.putExtra("Type","existing");
            intent.putExtra("username",getIntent().getStringExtra("username"));
            intent.putExtra("gamename",gamenames.get(index));
            intent.putExtra("UserName",getIntent().getStringExtra("username"));
            intent.putExtra("GameName",gamenames.get(index));
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Continue Game",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId()==R.id.viewgraph){

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;

            Toast.makeText(getApplicationContext(),"View Current Graph",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ViewGames.this,Graph_Display.class);
            intent.putExtra("UserName",getIntent().getStringExtra("username"));
            intent.putExtra("GameName",gamenames.get(index));
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.deletetable){

            Intent intent = getIntent();

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;

            Toast.makeText(getApplicationContext(),"Deleting Current Table"+gamenames.get(index)+"\n"+intent.getStringExtra("username"),Toast.LENGTH_LONG).show();

            GameDBHandler gdb = new GameDBHandler(ViewGames.this);
            gdb.deltablelink(gamenames.get(index));

            PointDBHandler pdb = new PointDBHandler(ViewGames.this);
            pdb.tabledroper(intent.getStringExtra("username"),gamenames.get(index));

            gamenames = gdb.dispdata(intent.getStringExtra("username"));

            arrayAdapter.clear();
            arrayAdapter.addAll(gamenames);
            arrayAdapter.notifyDataSetChanged();


        }
        else{
            return false;
        }
        return true;
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
                Intent intent = new Intent(ViewGames.this,MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.closeapp:

                AlertDialog.Builder alert = new AlertDialog.Builder(ViewGames.this);
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