package com.traceope.app.activity;

/**
 * Created by annedelaulanie on 29/10/14.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.traceope.app.db.DBAdapter;
import com.traceope.app.R;

public class LoginActivity extends Activity {
    DBAdapter dbAdapter;
    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Button btnRegister;
    private static final String LOGIN_USER= "LOGIN_USER";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide action bar

        txtUserName = (EditText) findViewById(R.id.et_user);
        txtPassword = (EditText) findViewById(R.id.et_pw);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_reg);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                if (username.length() > 0 && password.length() > 0) {
                    try {

                        if (dbAdapter.Login(username, password)) {
                            Toast.makeText(LoginActivity.this,
                                    "Successfully Logged In", Toast.LENGTH_LONG)
                                    .show();
                            //Start TraceOPE
                            Intent intent = new Intent("com.traceope.app.START_OPE");
                            intent.putExtra(LOGIN_USER,username);
                            //set login for session
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Invalid username or password",
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Some problem occurred",
                                Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Username or Password is empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
                try {

                    String username = txtUserName.getText().toString();
                    String password = txtPassword.getText().toString();
                    long i = dbAdapter.register(username, password);
                    if (i != -1)
                        Toast.makeText(LoginActivity.this, "You have successfully registered", Toast.LENGTH_LONG).show();

                } catch (SQLException e) {
                    Toast.makeText(LoginActivity.this, "Some problem occurred",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}

