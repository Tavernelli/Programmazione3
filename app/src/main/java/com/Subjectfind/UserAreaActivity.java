package com.Subjectfind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UserAreaActivity extends AppCompatActivity {
    private static final String REQUEST_URL = "http://andreatavernelli.altervista.org/Register/Subject.php";
    class userAttribute
    {
        String materia = "";

    }

    private UserAreaActivity.userAttribute context = new UserAreaActivity.userAttribute();

    StringRequest stringRequest = new StringRequest(Request.Method.POST, REQUEST_URL,
            new Response.Listener<String>() {


            @Override
            public void onResponse(String response)
            {
                context.materia = response;
                doStart();
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });


    void doStart()
    {
        Intent intent = new Intent(UserAreaActivity.this, Subjectfind.class);
        intent.putExtra("materia", context.materia);
        UserAreaActivity.this.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);


        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final TextView WelcomeMessage = (TextView) findViewById(R.id.tvWelcomeMessanger);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);

        String message = name+ " benvenuto nella tua User area";
        WelcomeMessage.setText(message);
        etUsername.setText(username);
        etAge.setText(age +"");


        final Button Startbutton = (Button) findViewById(R.id.Startbutton);
        Startbutton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(stringRequest);
            }

        });


    }



}