package com.example.project_test;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Frag_1 extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/

        String strtext = getArguments().getString("edttext");
        String scoretxt = getArguments().getString("Score");

        View v = inflater.inflate(R.layout.frag_tester, null);
        Button min = v.findViewById(R.id.min);
        Button max = v.findViewById(R.id.add);
        Button rem = v.findViewById(R.id.remove);

        TextView playernum = v.findViewById(R.id.textView);
        playernum.setText("Player : "+strtext);

        TextView score = v.findViewById(R.id.score);
        score.setText(scoretxt);


        max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView sc = (TextView) v.findViewById(R.id.score);
                String cur_score = sc.getText().toString();
                int n = Integer.parseInt(cur_score);
                n++;
                sc.setText(String.valueOf(n));
            }
        });

        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView sc = (TextView) v.findViewById(R.id.score);
                String cur_score = sc.getText().toString();
                int n = Integer.parseInt(cur_score);
                n--;
                sc.setText(String.valueOf(n));
            }
        });

        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(Frag_1.this).commit();
            }
        });
        return v;
    }

}
