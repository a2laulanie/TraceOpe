package com.traceope.app.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.traceope.app.R;

import java.io.File;

/**
 * Cration de la bdd
 * Creation du répertoire de travail
 * com.traceope.app dans sdcard/com.traceope.app
 *
 * */


public class MainActivity extends Activity {

    private View menu_1 = null;
    private View menu_2 = null;
    private View menu_3 = null;
    private View menu_4 = null;

    private String userName;
    private static final String LOGIN_USER = "LOGIN_USER";

    private File traceOpeDirectory = null;
   // public static  File TRACE_OPE_DIRECTORY = traceOpeDirectory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //file directory
        traceOpeDirectory = new File(Environment.getExternalStorageDirectory(), getPackageName() + "/");

        if(!traceOpeDirectory.exists()) {
            if(traceOpeDirectory.mkdir());
        }


        menu_1 = (TextView) findViewById(R.id.menu_1);
        menu_2 = (TextView) findViewById(R.id.menu_2);
        menu_3 = (TextView) findViewById(R.id.menu_3);
        menu_4 = (TextView) findViewById(R.id.menu_4);



        //Menu_1 : GEOLOCALISATION
        menu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageColorMenu();

                //menu_1.getBackground().setAlpha(190);
                menu_1.setBackgroundColor(getResources().getColor(R.color.darkpurple));

                try {
                    //startTRACEOPE geoloc
                    View userView = view.getRootView().findViewById(R.id.user);
                    Intent intent = new Intent("com.traceope.app.START_MENU_1");
                    //set login for session
                    intent.putExtra(LOGIN_USER, userName);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        //Menu_2 : FIBRE
        menu_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //startTRACEOPE FIBRE
                    View userView = view.getRootView().findViewById(R.id.user);
                    Intent intent = new Intent("com.traceope.app.START_MENU_2");
                    //set login for session
                    intent.putExtra(LOGIN_USER, userName);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        //Menu_3 : MAINTENANCE
        menu_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageColorMenu();
                menu_3.setBackgroundColor(getResources().getColor(R.color.darkorange));
                try {
                    //startTRACEOPE MAINTENANCE
                    View userView = view.getRootView().findViewById(R.id.user);
                    Intent intent = new Intent("com.traceope.app.START_MENU_3");
                    //set login for session
                    intent.putExtra(LOGIN_USER, userName);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        //Menu_4 : COMPARE BPE
        menu_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //startTRACEOPE MAINTENANCE
                    View userView = view.getRootView().findViewById(R.id.user);
                    Intent intent = new Intent("com.traceope.app.START_MENU_4");
                    //set login for session
                    intent.putExtra(LOGIN_USER, userName);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //affichage du login dans la barre d emenu
        MenuItem itemLogin = menu.findItem(R.id.user);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString(LOGIN_USER);
        itemLogin.setTitle(userName);

        return true;
    }

    public void manageColorMenu(){
        menu_1.setBackgroundColor(getResources().getColor(R.color.traceope_purple));
        menu_2.setBackgroundColor(getResources().getColor(R.color.traceope_orange));
        menu_3.setBackgroundColor(getResources().getColor(R.color.traceope_orange));
        //menu_4.setBackgroundColor(getResources().getColor(R.color.traceope_orange));

    }
}
