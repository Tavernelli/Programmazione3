package com.Subjectfind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {


    class userAttribute
    {
        String name;
        String userName;
        int age;
        boolean isLogin = false;
    }
    private userAttribute context = new userAttribute();

    Response.Listener<String> actionLogin(final boolean show_message)
    {
            return new Response.Listener<String>()
            {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        context.name     = jsonResponse.getString("name");
                        context.userName = jsonResponse.getString("username");
                        context.age      = jsonResponse.getInt("age");
                        context.isLogin  = true;
                        doLogin();
                    }
                    else if(show_message)
                    {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    void doLogin()
    {
        if(!context.isLogin) return ;
        Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
        intent.putExtra("name", context.name);
        intent.putExtra("age", context.age);
        intent.putExtra("username", context.userName);
        LoginActivity.this.startActivity(intent);
        context.isLogin = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);
        final Button bLogin = (Button) findViewById(R.id.bLogin);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoginRequest loginRequest = new LoginRequest(
                        etUsername.getText().toString(),
                        etPassword.getText().toString(),
                        actionLogin(true));
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        doLogin();
    }
}
