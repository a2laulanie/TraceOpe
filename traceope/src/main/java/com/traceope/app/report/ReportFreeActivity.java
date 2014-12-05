package com.traceope.app.report;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

import com.traceope.app.R;
import com.traceope.app.activity.SlideActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ale on 10/11/14.
 */


/**
 * Stockage des images dans sdcard/Pictures/traceope.app/repOfTheDay/
 *
 *
 * */


 public class ReportFreeActivity extends Activity {

    private String userName;
    private static final String LOGIN_USER = "LOGIN_USER";
    public static final String SLIDE_CODE_COULEUR = "SLIDE_CODE_COULEUR";

    private static final String TAG = "CallCamera";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;

    File directory;
    Uri fileUri = null;
    File[] imageList = null;
    ImageView photoImage = null;
    ImageView refresh = null;
    ImageView photoImage_n = null;
    ArrayList imageListView = null;
    TableRow tr = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);


        photoImage = (ImageView) findViewById(R.id.photo_image);
        tr = (TableRow) findViewById(R.id.diapo);
        photoImage.setImageDrawable(null);


        //affichage des images du jour
        showSlider();


        Button callCameraButton = (Button) findViewById(R.id.button_callcamera);
        callCameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getOutputPhotoFile();
                fileUri = Uri.fromFile(getOutputPhotoFile());
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ);
            }
        });

        ImageView refresh = (ImageView) findViewById(R.id.diapo_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //TODO refresh
            }
        });

    }


    private File getOutputPhotoFile() {
        //nom de l image
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
        //Nom du dossier de sauvegarde à la date du jour
        Date d = new Date(new Date().getTime());
        String sFile = new SimpleDateFormat("dd-MM-yyyy").format(d);

        directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName() + "/" + sFile);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e(TAG, "Failed to create storage directory.");
                return null;
            }
        }

        return new File(directory.getPath() + File.separator + "IMG_"
                + timeStamp + ".jpg");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
            if (resultCode == RESULT_OK) {
                Uri photoUri = null;
                if (data == null) {
                    Toast.makeText(this, "Image sauvegardée",
                            Toast.LENGTH_LONG).show();
                    photoUri = fileUri;

                } else {
                    photoUri = data.getData();
                    Toast.makeText(this, "Image sauvegardée dans: " + data.getData(),
                            Toast.LENGTH_LONG).show();
                }
                showPhoto(photoUri.getPath());
                showSlider();


            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Supprimée", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Callout for image capture failed!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showSlider() {

        try {


            String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
            Date d = new Date(new Date().getTime());
            String sFile = new SimpleDateFormat("dd-MM-yyyy").format(d);

            directory = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), getPackageName() + "/" + sFile);
            if (directory.exists()) {
                imageList = directory.listFiles(new FilenameFilter() {

                    public boolean accept(File dir, String name) {
                        return (name.endsWith(".jpg"));
                    }
                });

                imageListView = new ArrayList(imageList.length);
                tr.removeAllViews();

                for (int i = 0; i < imageList.length; i++) {

                    ImageView photoImage_n = new ImageView(this);

                    ImageView iv = new ImageView(this);


                    Bitmap bitmap = BitmapFactory.decodeFile(imageList[i].getAbsolutePath());
                    BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);



                    photoImage_n.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    photoImage_n.setImageDrawable(drawable);
                    photoImage_n.setRotation(90);
                    imageListView.add(photoImage_n);


                }
                for (int i = 0; i < imageListView.size(); i++) {
                    tr.addView((View) imageListView.get(i));

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPhoto(String photoUri) {

        File imageFile = new File(photoUri);
        if (imageFile.exists()) {
            Drawable oldDrawable = photoImage.getDrawable();
            if (oldDrawable != null) {
                ((BitmapDrawable) oldDrawable).getBitmap().recycle();
            }
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
            photoImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            photoImage.setRotation(90);
            photoImage.setImageDrawable(drawable);

        }
    }


    //gestion du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report, menu);
        //affichage du login
        MenuItem itemLogin = menu.findItem(R.id.user);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString(LOGIN_USER);
        itemLogin.setTitle(userName);

        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_report_free);
        item.setEnabled(false);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_code_couleur) {
            Intent intent = new Intent(getApplicationContext(), SlideActivity.class);
            intent.putExtra("slideType", SLIDE_CODE_COULEUR);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_report_free) {
            Intent intent = new Intent(getApplicationContext(), ReportFreeActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_report_ticket) {
            Intent intent = new Intent(getApplicationContext(), ReportTicketActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
