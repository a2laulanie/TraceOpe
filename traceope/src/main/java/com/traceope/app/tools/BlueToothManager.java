package com.traceope.app.tools;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.traceope.app.R;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by ale on 01/12/14.
 */
public class BlueToothManager {

    private BluetoothAdapter bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_PAIRED_DEVICE = 2;
    private static final int FILE_FIRMWARE_UPGRADE = 42;

    private View rootView;


    private ListView remoteDevices;
    public ArrayAdapter<String> listAdapter;
    private ArrayList<String> arrlist;
    public ListView pairedListView;
    //peripherique selectionné à afficher
    private BluetoothDevice readerDevice = null;

    public BlueToothManager(View rootView) {
        this.rootView = rootView;
    }



    //broadcast des peripheriques bt
    private BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                readerDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Toast.makeText(rootView.getContext(), "recherche en cours .....  ", Toast.LENGTH_SHORT).show();

                String deviceBTMajorClass = new BluetoothUtil().getBTMajorDeviceClass(readerDevice.getBluetoothClass().getMajorDeviceClass());
                String deviceBTAddress = readerDevice.getAddress();

                arrlist.add(deviceBTMajorClass + " - " + readerDevice.getName() + " - " + deviceBTAddress);

                pairedListView = (ListView) rootView.findViewById(R.id.new_devices);

                // TODO gestion pairedListView nulle en cas d intent pdt le discovery

                listAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrlist);
                if (listAdapter != null) {
                    pairedListView.setAdapter(listAdapter);
                } else {
                    IntentFilter filteradapter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(rootView.getContext(), " choisissez une device !!! ", Toast.LENGTH_SHORT).show();
            }

        }
    };

        /*
    * GESTION de L ACTIVATION DU PERIPHERIQUE BT
    * //active le bt si il ne l'est pas
    *
    * */


    public ListView manageBluetoothConnection() {

        // register intent for bt device
        arrlist = new ArrayList<String>();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        rootView.getContext().registerReceiver(bluetoothReceiver, filter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if (bluetoothAdapter == null) {
            Toast.makeText(rootView.getContext(), "Bluetooth NOT support !!", Toast.LENGTH_LONG).show();

        } else {
            if (bluetoothAdapter.isEnabled()) {
                if (bluetoothAdapter.isDiscovering()) {
                    Toast.makeText(rootView.getContext(), "Bluetooth is currently in device discovery process", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(rootView.getContext(), "Connection bluetooth détectée", Toast.LENGTH_LONG).show();
                    //lance un discovery
                    bluetoothAdapter.startDiscovery();

                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
                    remoteDevices = (ListView) rootView.findViewById(R.id.new_devices);
                    remoteDevices.setOnItemClickListener(remoteDeviceClickListener);
                    bluetoothAdapter.startDiscovery();
                }
            } else {
                Toast.makeText( rootView.getContext(), "Bluetooth non activé!", Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                rootView.getContext().startActivity(enableBtIntent);

            }
        }
        return remoteDevices;
    }

    /*
    * GESTION de L APPAIRAGE
    *
    * */

    //selection des devices bt trouvees
    private AdapterView.OnItemClickListener remoteDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View pView, int pPosition, long pID) {

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.cancelDiscovery();
            // Get the device MAC address, which is the last 17 chars in the
            // View
            String info = ((TextView) pView).getText().toString();
            String address = info.substring(info.length() - 17);

            readerDevice = bluetoothAdapter.getRemoteDevice(address);
            startPairingDevice(readerDevice);

            //open du champ de gestion du reader
            rootView.findViewById(R.id.grid_reader_control).setVisibility(View.VISIBLE);

        }
    };

    /*
    * Apairage du beacon
    * */
    private final BroadcastReceiver btpairingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(intent.getAction())) {
                int prevBondState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, -1);
                int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);

                if (prevBondState == BluetoothDevice.BOND_BONDING) {
                    if (bondState == BluetoothDevice.BOND_BONDED) {
                        readerDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


                    } else if (bondState == BluetoothDevice.BOND_NONE) {
                        readerDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        Toast.makeText( rootView.getContext(), "Périphérique non apairé !!! ", Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText( rootView.getContext(), "Périphérique apairé !!! ", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private BluetoothDevice startPairingDevice(BluetoothDevice readerDevice) {
        this.readerDevice = readerDevice;

        int bondState = readerDevice.getBondState();

        if (bondState == BluetoothDevice.BOND_NONE) {

            Toast.makeText( rootView.getContext(), "apairage en cours!", Toast.LENGTH_LONG).show();

            // intent to detect pairing

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            rootView.getContext().registerReceiver(btpairingReceiver, filter);

            try {
                Method m = readerDevice.getClass().getMethod("createBond", (Class[]) null);
                m.invoke(readerDevice, (Object[]) null);
                // Toast.makeText(this.getActivity(), "pairing is finished!",
                // Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                // Toast.makeText(this.getActivity(), "unsuccess pairing!",
                // Toast.LENGTH_LONG).show();
            }

        } else if (bondState == BluetoothDevice.BOND_BONDED || bondState==11) {

            //affichage de la device
            ((TextView)  rootView.findViewById(R.id.txt_bt_device)).setText("Reader sélectionné : " + readerDevice.getName());

        } else {
            // Toast.makeText(this.getActivity(),
            // "pairing already in progress!", Toast.LENGTH_LONG).show();
        }
        return  readerDevice;

    }

    public BluetoothDevice getReaderDevice() {
        return this.readerDevice;
    }

    public void unRegister(){
        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
            if (bluetoothReceiver != null) {
                rootView.getContext().unregisterReceiver(bluetoothReceiver);
                bluetoothReceiver = null;
            }

        }
    }
}
