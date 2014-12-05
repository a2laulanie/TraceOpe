package com.traceope.app.geoloc;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.TraceOpReader.Beacon.Messages.Beacon_Record;
import com.TraceOpReader.RFIDReader;
import com.traceope.app.R;
import com.traceope.app.activity.RopeActivity;
import com.traceope.app.activity.SlideActivity;
import com.traceope.app.tools.BlueToothManager;
import com.traceope.app.tools.FileChooser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by ale on 07/11/14.
 * Apairage du device Beacon en BT
 * Mise en marche du Beacon
 */
public class GeolocActivity extends Activity {

    private String userName;
    private static final String LOGIN_USER = "LOGIN_USER";
    public static final String SLIDE_TUTORIEL = "SLIDE_TUTORIEL";

    private BlueToothManager managetBt = null;

    private static final int FILE_FIRMWARE_UPGRADE = 42;

    private ListView remoteDevices;

    //peripherique selectionné à afficher
    private BluetoothDevice readerDevice = null;

    //bouton de gestion du reader
    private static final int STOPPED = 0;
    private static int STATUS = STOPPED;
    private static final int RUNNING = 0;

    private String tagId;
    private TextView titleReaderStatus, tvTagId;
    private Button btnSearchDevice, btnReaderConnect, btnGeolocOn, btnReaderDisconnect, btnSendGeolocEmail, btnFirmwareUpdate, btnCalXStart, btnCalXStop, btnCalYStop, btnCalYStart, btnCalZStop, btnCalZStart, btnResetCom;
    private RFIDReader rfIDReader = null;
    private RadioButton rbMaintenance;

    //read and write tape tag
    private byte[] dataTape;
    private byte[] dataBuff = new byte[8 * 1024];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_periph);
        View rootView = getWindow().getDecorView().getRootView();
         /*
         * Manage Bluetooth connections
         * TODO external class with rope
		 */
        managetBt = new BlueToothManager(rootView);

        remoteDevices = managetBt.manageBluetoothConnection();


        btnSearchDevice = (Button) findViewById(R.id.btn_search_device);
        btnSearchDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    remoteDevices = managetBt.manageBluetoothConnection();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //gestion du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rop, menu);
        //affichage du login
        MenuItem itemLogin = menu.findItem(R.id.user);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString(LOGIN_USER);
        itemLogin.setTitle(userName);


         /*
        * Manage Button CLIENT
        *
        * **/

        btnReaderConnect = (Button) findViewById(R.id.btn_reader_connect);
        titleReaderStatus = (TextView) findViewById(R.id.TitleReaderStatus);


        /*
        * première étape : allumer le reader/beacon/détecteur
        */

        btnReaderConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readerDevice = managetBt.getReaderDevice();
                try {

                    if (rfIDReader == null) {
                        rfIDReader = new RFIDReader(readerDevice);
                    }
                    rfIDReader.connect();
                    Thread.sleep(500);
                    titleReaderStatus.setBackgroundColor(Color.GREEN);
                    titleReaderStatus.setText("Reader Connecté / charge = 3291");
                    titleReaderStatus.setText("Reader Connecté / charge = " + rfIDReader.getBatteryVoltage());
                    STATUS = RUNNING;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

         /*
        * seconde étape : Lancer la détection lointaine apres avoir
        * entré le numéto du tag
        * TODO: tagId a remonter de la bdd
        */
        btnGeolocOn = (Button) findViewById(R.id.btn_geoloc_on);
        tvTagId = (TextView) findViewById(R.id.txt_id_tag);

        btnGeolocOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tagId = tvTagId.getText().toString();
                    rfIDReader.beacon_StartSearch(tagId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        btnReaderDisconnect = (Button) findViewById(R.id.btn_reader_disconnect);
        btnReaderDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // bt.connectClient(handler, device, MY_UUID);
                        /* DEMO */
                    if (rfIDReader != null) {
                        rfIDReader.disConnect();
                        rfIDReader = null;
                        STATUS = STOPPED;
                    }
                    titleReaderStatus.setBackgroundColor(Color.RED);
                    titleReaderStatus.setText("Reader Déconnecté");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

          /*
        * Manage Button MAINTENANCE
        *
        * **/

        btnSendGeolocEmail = (Button) findViewById(R.id.btn_geoloc_mail);
        btnSendGeolocEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String output = "";
                    Vector<Beacon_Record> records = rfIDReader.getBeaconRecords();
                    for (Beacon_Record record : records) {
                        output += record.toString();
                        output += "\n";
                    }

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"lescane@eeware.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "TraceOP trace");
                    i.putExtra(LOGIN_USER, userName);
                    i.putExtra(Intent.EXTRA_TEXT, output);
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnFirmwareUpdate = (Button) findViewById(R.id.btn_firmware_update);
        btnFirmwareUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    managetBt.unRegister();

                   /* Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                  // intent.setType("*//*");
                  //  intent.putExtra(LOGIN_USER, userName);
                   // startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)),FILE_FIRMWARE_UPGRADE);*/
                    // startActivityForResult(intent, FILE_FIRMWARE_UPGRADE);

                    //TODO test Anne
                    try {

                        Intent intent = new Intent(getApplicationContext(), FileChooser.class);
                        ArrayList<String> extensions = new ArrayList<String>();
                        extensions.add(".xls");
                        extensions.add(".csv");
                        intent.putStringArrayListExtra("filterFileExtension", extensions);
                        startActivityForResult(intent, FILE_FIRMWARE_UPGRADE);

                    } catch (Throwable t) {
                        Toast.makeText(getApplicationContext(), "Doosier non trouvé: " + t.toString(),
                                Toast.LENGTH_LONG).show();
                    }

                    // rfIDReader.upgradeFW("test", ReaderMessage.Micro_103);
                    Log.d("Upgrade Firmware", "Upgrade 103 Firmware");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        btnCalXStart = (Button) findViewById(R.id.Cal_X_Start);
        btnCalXStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rfIDReader.startCompass_Calibration('X');
                    Log.d("TRACEOP_APP", "Starting X Calibration");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnCalYStart = (Button) findViewById(R.id.Cal_Y_Start);
        btnCalYStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rfIDReader.startCompass_Calibration('Y');
                    Log.d("TRACEOP_APP", "Starting Y Calibration");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnCalZStart = (Button) findViewById(R.id.Cal_Z_Start);
        btnCalZStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rfIDReader.startCompass_Calibration('Z');
                    Log.d("TRACEOP_APP", "Starting Z Calibration");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnCalXStop = (Button) findViewById(R.id.Cal_X_Stop);
        btnCalXStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rfIDReader.stopCompass_Calibration('X');
                    Log.d("TRACEOP_APP", "Stopping X Calibration");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnCalYStop = (Button) findViewById(R.id.Cal_Y_Stop);
        btnCalYStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rfIDReader.stopCompass_Calibration('Y');
                    Log.d("TRACEOP_APP", "Stopping Y Calibration");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnCalZStop = (Button) findViewById(R.id.Cal_Z_Stop);
        btnCalZStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rfIDReader.stopCompass_Calibration('Z');
                    Log.d("TRACEOP_APP", "Stopping Z Calibration");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    /*Manage maintenance et ecriture des tag
    * */

        rbMaintenance = (RadioButton) findViewById(R.id.rb_maintenance_view);
        rbMaintenance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.grid_reader_maintenance).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.grid_reader_maintenance).setVisibility(View.VISIBLE);


                } else {
                    findViewById(R.id.grid_reader_maintenance).setVisibility(View.GONE);
                    ((RadioButton) findViewById(R.id.rb_maintenance_view)).setChecked(false);

                }

            }
        });


        btnResetCom = (Button) findViewById(R.id.reset_com);
        btnResetCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rfIDReader.turnOFF_RADIO();
                    Log.d("RADIO OFF ! RADIO OFF", "RADIO OFF");
                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return true;
    }


    public void upgradeFW(byte[] uri) {
        if (rfIDReader != null) {
            if (STATUS == RUNNING) {
                try {
                    rfIDReader.upgradeFW(uri);
                } catch (Exception e) {
                    Log.e("UPGRADE_FIRMWARE", "Failed to upgrade firmware");
                }

            }
        }
    }

    public byte[] getFWBytes(Uri uri) throws IOException {
        InputStream iStream = getContentResolver().openInputStream(uri);
        return getBytes(iStream);
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 64 * 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ITEMLIST_ACTIVITY", "Request : " + requestCode + " ResultCode :" + resultCode + " ResultData" + data);
        if ((requestCode == FILE_FIRMWARE_UPGRADE) && resultCode == GeolocActivity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("FILE_FIRMWARE_UPGRADE", "Selected : " + (uri.toString()));
                try {
                    upgradeFW(getFWBytes(uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_detecteur);
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
            managetBt.unRegister();
            return true;
        }

        //telechargement fichier ROPE/geolocalisation//inutile ici reste sur la page donc grise
        if (id == R.id.action_geoloc) {
            Intent intent = new Intent(getApplicationContext(), RopeActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            managetBt.unRegister();
            return true;
        }

        //teectionBT avec apairage beacon
        if (id == R.id.action_detecteur) {
            Intent intent = new Intent(getApplicationContext(), GeolocActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            managetBt.unRegister();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managetBt.unRegister();
    }
}
