package com.Subjectfind;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Subjectfind extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    //creo gli oggetti
    TextView mainTextView;
    Button main_button;
    EditText main_edittext;
    ListView listView;
    JSONAdapter mJSONAdapter;
    ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        // scrivo sulla textview creata nel activity_main.xml
        mainTextView = (TextView) findViewById(R.id.main_textview);
        main_button = (Button) findViewById(R.id.main_button);
        main_edittext = (EditText) findViewById(R.id.main_edittext);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setFastScrollEnabled(false);
        mJSONAdapter = new JSONAdapter(this, getLayoutInflater());
        listView.setAdapter(mJSONAdapter);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Searching");
        mDialog.setCancelable(false);

        main_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {

                String a = main_edittext.getText().toString();

                if (a.equals("")){
                    new AlertDialog.Builder(Subjectfind.this)
                            .setTitle("Errore")
                            .setMessage("Inserisci un corso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    //lista mateire ordinate per distanza
                    ArrayList<ListMateriaField> materie = listQuery(a);
                    //richiesta solo sulle prime 3 materie
                    JSONArray listMaterie = new JSONArray();
                    int nit = materie.size() < 3 ? materie.size() : 3;
                    for(int i = 0; i < nit; ++i){
                        listMaterie.put(materie.get(i).materia);
                    }
                    //esegui la richiesta
                    MaterieRequest cfg = new MaterieRequest(listMaterie.toString(), bravo());
                    mDialog.show();
                    RequestQueue queue = Volley.newRequestQueue(Subjectfind.this);
                    queue.add(cfg);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
        // create an Intent to take you over to a new DetailActivity
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("materia", context.get(position).materia);
        detailIntent.putExtra("docente", context.get(position).docente);
        detailIntent.putExtra("info", context.get(position).info);
        startActivity(detailIntent);
    }



    class MateriaAttributi
    {
        int id;
        String materia;
        String docente;
        String info;

    }
    private ArrayList<MateriaAttributi> context = new  ArrayList<> ();



    //creo il response listener
    Response.Listener <String> bravo ()
    {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mDialog.dismiss();
                try {
                    context.clear();
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i=0;i < jsonResponse.length(); ++i){
                        JSONObject job = jsonResponse.getJSONObject(i);
                        MateriaAttributi matt = new MateriaAttributi();
                        matt.id      = job.getInt("user_id");
                        matt.materia = job.getString("materia");
                        matt.docente = job.getString("docente");
                        matt.info = job.getString("info");
                        context.add(matt);
                    }
                    mJSONAdapter.updateData(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    public ArrayList<ListMateriaField> listQuery(String searchString )
    {
        Intent intent = getIntent();
        String risultato = intent.getStringExtra("materia");

        return Levenshtein.listaDistanza(searchString, risultato);
    }

}




