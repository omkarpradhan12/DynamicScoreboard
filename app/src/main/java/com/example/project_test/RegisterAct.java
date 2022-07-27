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
import android.widget.Toast;

public class RegisterAct extends AppCompatActivity {

    EditText name,uname,pass,cpass;
    Button reg,log;
    LoginDBHandler DB1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.edFullname);
        uname = findViewById(R.id.edUsername);
        pass = findViewById(R.id.etPassword);
        cpass = findViewById(R.id.edPassConf);

        reg = findViewById(R.id.regbut);

        DB1 = new LoginDBHandler(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String nm = name.getText().toString();
                    String un = uname.getText().toString();
                    String ps = pass.getText().toString();
                    String pschk = cpass.getText().toString();

                    if(ps.equals(pschk))
                    {
                        Boolean chkusr = DB1.checkusername(un);
                        if(chkusr == false)
                        {
                            Boolean ins = DB1.insertData(nm,un,ps);
                            if(ins)
                            {
                                Toast.makeText(RegisterAct.this,"Succesfully added user",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            }
        });

        log = findViewById(R.id.loginbut);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterAct.this,MainActivity.class);
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
                Intent intent = new Intent(RegisterAct.this,MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.closeapp:
                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterAct.this);
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