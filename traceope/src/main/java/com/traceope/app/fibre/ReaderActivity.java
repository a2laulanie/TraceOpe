package com.traceope.app.fibre;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.TraceOpReader.RFIDReader;
import com.TraceOpReader.Tag.Tag;
import com.traceope.app.R;
import com.traceope.app.activity.FibreActivity;
import com.traceope.app.activity.SlideActivity;
import com.traceope.app.file.model.Tape;
import com.traceope.app.tools.BlueToothManager;

import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

import static java.util.Arrays.copyOf;

/**
 * Created by ale on 01/12/14.
 */
public class ReaderActivity extends Activity {

    private String userName;
    private static final String LOGIN_USER = "LOGIN_USER";

    public static final String SLIDE_CODE_COULEUR = "SLIDE_CODE_COULEUR";

    private BlueToothManager managetBt = null;

    private ListView remoteDevices;
    private TextView titleReaderStatus, tvTagId;

    //peripherique selectionné à afficher
    private BluetoothDevice readerDevice = null;

    //bouton de gestion du reader
    private static final int STOPPED = 0;
    private static int STATUS = STOPPED;
    private static final int RUNNING = 0;


    private Button btnSearchDevice, btnReaderConnect, btnReaderDisconnect, btnWriteTag, btnCompareTag, btnResetCom;

    private RFIDReader rfIDReader = null;

    //read and write tape tag
    private byte[] dataTape;
    private byte[] dataReader;
    private byte[] dataTapeRead;
    private int dataTapeSize = 0;
    private int dataReaderSize = 0;
    private byte[] dataBuff = new byte[8 * 1024];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bpe_work);
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

        //test tape tranfert
        try {
            Tape tape = (Tape) getIntent().getSerializableExtra("tape");
            dataTape = SerializationUtils.serialize(tape);
            dataTapeSize = dataTape.length;


            StringBuilder sb = new StringBuilder();
            for (byte b : dataTape) {
                sb.append(String.format("%02X ", b));
            }
            System.out.println(sb.toString());

        } catch (Exception e) {
            Log.d("impossible de calculer la taille.....", "TAPE NULL/ET/OU/ PAS SERIALISABLE");
        }
        Log.d("le fichier excel est  chargé...", "TAPE OK");

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
                try {
                    readerDevice = managetBt.getReaderDevice();

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

        btnWriteTag = (Button) findViewById(R.id.btn_write_bpe);
        btnWriteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    rfIDReader.turnON_RADIO();
                    Vector<Tag> myTags = rfIDReader.GetTags();

                    //Tester en replissant de 0 à 0x7F chaque Chunk
                        /*
                        long j = 0;
                        for (int i = 0; i< dataBuff.length ; i++)
                        {
                            dataBuff[i] = (byte) j ;
                            j++;
                            if (j == 0x80) j=0;
                        }
                        */
                    //Tester en écrivant que des 0
                    //Arrays.fill(dataBuff,(byte) 0);

                    for (int i = 0; i < myTags.size(); i++) {
                        Tag current_Tag = myTags.elementAt(i);
                        dataBuff = copyOf(dataTape, dataBuff.length);
                        current_Tag.setContent(dataBuff);
                    }

                    rfIDReader.writeTagsDataToReader(myTags);
                    rfIDReader.commitDataToTags(myTags);
                    rfIDReader.turnOFF_RADIO();


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.err.println(e.toString());

                }

            }
        });

        btnCompareTag = (Button) findViewById(R.id.btn_compare_bpe);
        btnCompareTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    rfIDReader.turnON_RADIO();
                    Vector<Tag> myTags = rfIDReader.GetTags();
                    rfIDReader.readTags(myTags);
                    rfIDReader.readTagsValue(myTags);

                    System.out.println("mytags length" + myTags.size());
                    Tag current_Tag = myTags.elementAt(0);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>tcurrent_Tag.id" + current_Tag.getID().toString());
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>tcurrent_Tag.content" + current_Tag.toString());

                    ByteArrayOutputStream data;
                    data = current_Tag.getContent();
                    dataReader = data.toByteArray();

                    dataTapeRead = new byte[dataTapeSize];
                    for (int j = 0; j < dataTapeSize; j++) {
                        dataTapeRead[j] = dataReader[j];
                    }
                    Tape fromReader = SerializationUtils.deserialize(dataTapeRead);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Tape from reader" + fromReader.getPipeAA().get(0).getPipe_status());


                    rfIDReader.turnOFF_RADIO();
                    Log.e("READ_TAG", "tag reading...");


                } catch (Exception e) {
                    e.printStackTrace();
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

    }

    //gestion de menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fibre, menu);
        //affichage du login
        MenuItem itemLogin = menu.findItem(R.id.user);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString(LOGIN_USER);
        itemLogin.setTitle(userName);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_bpe_compare);
        item.setEnabled(false);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //start show color help
        int id = item.getItemId();
        if (id == R.id.action_code_couleur) {
            Intent intent = new Intent(getApplicationContext(), SlideActivity.class);
            intent.putExtra("slideType", SLIDE_CODE_COULEUR);
            startActivity(intent);
            managetBt.unRegister();
            return true;
        }

        //start download excel file
        if (id == R.id.action_fibre) {
            Intent intent = new Intent(getApplicationContext(), FibreActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            managetBt.unRegister();
            return true;
        }

        //Comparaison des BPE tag et fichier téléchargé/ ecriture des tag
        if (id == R.id.action_bpe_compare) {
            Intent intent = new Intent(getApplicationContext(), ReaderActivity.class);
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
