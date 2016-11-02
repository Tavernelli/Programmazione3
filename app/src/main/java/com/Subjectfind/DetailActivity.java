package com.Subjectfind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String materia = intent.getStringExtra("materia");
        String docente = intent.getStringExtra("docente");
        String info = intent.getStringExtra("info");


        TextView Textview = (TextView) findViewById(R.id.prova);
        TextView Textview1 = (TextView) findViewById(R.id.prova1);
        TextView Textview2 = (TextView) findViewById(R.id.prova3);


        Textview.setText(materia);
        Textview1.setText(docente);
        Textview2.setText(info);

    }




}

