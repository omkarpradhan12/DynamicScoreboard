package com.example.project_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    String unames[]={"Omkar","Mohit","Kunal","Nikhil",""};
    String passs[]={"Test","Meow","Valo","Home",""};
    LoginDBHandler DB1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        DB1 = new LoginDBHandler(this);
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
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.closeapp:
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
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

    public void log_db(View v)
    {
        EditText uname = (EditText) findViewById(R.id.activity_main_usernameEditText);
        EditText pass = (EditText) findViewById(R.id.activity_main_passwordEditText);

        String u,p;
        u = uname.getText().toString();
        p = pass.getText().toString();

        Boolean chkuser = DB1.checkusername(u);
        if(chkuser)
        {
            Boolean chkusrpass = DB1.checkusernamepassword(u,p);
            if(chkusrpass)
            {
                Toast myToast = Toast.makeText(this, "Correct Credentials", Toast.LENGTH_LONG);
                myToast.show();
                Intent intent = new Intent(this,User_Controls.class);
                intent.putExtra("Uname",u);
                startActivity(intent);
            }
            else
            {
                Toast myToast = Toast.makeText(this, "Incorrect Password", Toast.LENGTH_LONG);
                myToast.show();
            }
        }
        else
        {
            Toast myToast = Toast.makeText(this, "Username not found", Toast.LENGTH_LONG);
            myToast.show();
        }

    }



    public void reg(View v)
    {
        Intent intent = new Intent(MainActivity.this,RegisterAct.class);
        startActivity(intent);
    }

    public void clr(View v)
    {
        EditText uname = (EditText) findViewById(R.id.activity_main_usernameEditText);
        EditText pass = (EditText) findViewById(R.id.activity_main_passwordEditText);
        uname.setText("");
        pass.setText("");
    }
}