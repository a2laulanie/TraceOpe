package com.traceope.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.traceope.app.geoloc.GeolocActivity;
import com.traceope.app.rope.RouteActivity;
import com.traceope.app.tools.FileChooser;
import com.traceope.app.R;

import java.util.ArrayList;


/**
 * Téléchargement de la route optique
 * destination coordonnées GPS
 */


public class RopeActivity extends Activity {

    private Button btn_fileChooser;
    private String userName;
    private static final int FILE_CHOOSER = 3;
    private static final String LOGIN_USER = "LOGIN_USER";
    public static final String SLIDE_TUTORIEL = "SLIDE_TUTORIEL";
    public static final String SLIDE_CODE_COULEUR = "SLIDE_CODE_COULEUR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rope);

        //telechargement du fichier
        btn_fileChooser = (Button) findViewById(R.id.btn_file_chooser);

        btn_fileChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new Intent(getApplicationContext(), FileChooser.class);
                    ArrayList<String> extensions = new ArrayList<String>();
                    extensions.add(".xls");
                    extensions.add(".csv");

                    intent.putStringArrayListExtra("filterFileExtension", extensions);
                    startActivityForResult(intent, FILE_CHOOSER);


                } catch (Throwable t) {
                    Toast.makeText(getApplicationContext(), "Recheche stoppée recommencez: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }

            }


        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == FILE_CHOOSER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                //recup de l extra fileSelected dans l'intent
                String fileSelected = data.getStringExtra("fileSelected");

                //Start ROPE file
                Intent intent = new Intent(this, RouteActivity.class);
                intent.putExtra("fileSelected", fileSelected);
                intent.putExtra(LOGIN_USER, userName);
                startActivity(intent);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rop, menu);
        //affichage du login
        MenuItem itemLogin = menu.findItem(R.id.user);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString(LOGIN_USER);
        itemLogin.setTitle(userName);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_geoloc);
            item.setEnabled(false);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //start tutorial activity whith slider
        int id = item.getItemId();
        if (id == R.id.action_tutoriel) {
            Intent intent = new Intent(getApplicationContext(), SlideActivity.class);
            intent.putExtra("slideType",SLIDE_TUTORIEL);
            startActivity(intent);
            return true;
        }

        //telechargement fichier ROPE/geolocalisation//inutile ici reste sur la page donc grise
        if (id == R.id.action_geoloc) {
            Intent intent = new Intent(getApplicationContext(), RopeActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            return true;
        }

        //teectionBT avec apairage beacon
        if (id == R.id.action_detecteur) {
            Intent intent = new Intent(getApplicationContext(), GeolocActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
