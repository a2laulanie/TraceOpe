package com.traceope.app.rope;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.traceope.app.R;
import com.traceope.app.activity.RopeActivity;
import com.traceope.app.activity.SlideActivity;
import com.traceope.app.geoloc.GeolocActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ale on 05/11/14.
 */
public class RouteActivity extends Activity {

    private String fileSelected = "";
    public static final String SLIDE_TUTORIEL = "SLIDE_TUTORIEL";

    public static final String FILE_SELECTED = "FILE_SELECTED";
    private ArrayList<String[]> routeTab = new ArrayList<String[]>();
    private String userName;
    private static final String LOGIN_USER= "LOGIN_USER";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rope);
        View rootView = getWindow().getDecorView().getRootView();

        //recuperation du fichier a travailler BPE
        Bundle b = getIntent().getExtras();
        fileSelected = b.getString("fileSelected");
        getCsvFile(fileSelected, rootView);
    }


    public void getCsvFile(String fileSelected, View rootView) {

        try {

            //String youFilePath = Environment.getExternalStorageDirectory().toString() + "/rop.csv";
            File file = new File(fileSelected);

            BufferedReader br = null;
            String line = "^M";
            String cvsSplitBy = ";";
            routeTab = new ArrayList<String[]>();
            String UTF8 = "utf8";
            int BUFFER_SIZE = 8192;

            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "iso-8859-1"), 8192);

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                routeTab.add(data);
            }
            br.close();

            this.buildRoute(rootView);

        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    public void buildRoute(View rootView) {

        TableLayout tl = (TableLayout) rootView.findViewById(R.id.route_file);

        for (int i = 0; i < routeTab.size(); i++) {

            if (routeTab.get(i).length > 0) {

                LinearLayout tr_A = new LinearLayout(this);

                if ((routeTab.get(i).length > 3)) {
                    // String val = routeTab.get(i)[1].trim();
                    // if (val.matches("^[0-9]+(|,\\d*[0-9])+$")) {

                    String val = routeTab.get(i)[3].trim();
                    if (val.matches("Câble")) {

                        tr_A.setBackgroundColor(Color.LTGRAY);

                    } else {

                        tr_A.setBackgroundColor(Color.WHITE);
                    }
                }

                // construction du layout

                String[] arr = routeTab.get(i);

                for (int j = 0; j < arr.length; j++) {
                    TextView tv = new TextView(this);

                    TextView tvspace = new TextView(this);
                    tvspace.setText("");
                    tvspace.setWidth(100);

                    tv.setGravity(2);
                    tv.setText(arr[j]);
                    if (arr[j].contains("COORD GPS")) {
                        String coord = arr[j].trim().substring(11, arr[j].length() - 1);

                        // https://maps.google.fr/maps?saddr=43.2829341874484,5.5305647696051
                        String url = "https://maps.google.fr/maps?f=q&hl=fr&q=" + coord.trim();
                        // System.out.println(url);
                        tv.setText(url);
                        Linkify.addLinks(tv, Linkify.WEB_URLS);
                    } else if (arr[j].contains("TRAVAUX")) {
                        tv.setTextColor(Color.RED);
                    }

                    tr_A.addView(tv);
                    tr_A.addView(tvspace);

                }

                tl.addView(tr_A);

            }
        }
    }


    //gestion du menu géolocalisation proche
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
            intent.putExtra("slideType", SLIDE_TUTORIEL);
            startActivity(intent);
            return true;
        }

        //telechargement fichier ROPE/geolocalisation//inutile ici reste sur la page
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
