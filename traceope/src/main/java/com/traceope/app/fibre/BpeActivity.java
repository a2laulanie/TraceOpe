package com.traceope.app.fibre;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.traceope.app.R;
import com.traceope.app.activity.FibreActivity;
import com.traceope.app.activity.SlideActivity;
import com.traceope.app.file.model.BpeView;
import com.traceope.app.file.model.Pipe;
import com.traceope.app.file.model.Tape;
import com.traceope.app.file.model.ViewMake;
import com.traceope.app.tools.ColorUtil;
import com.traceope.app.tools.GraniouProcess;

import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by ale on 25/11/14.
 */
public class BpeActivity extends Activity {

    private String fileSelected = "";
    public static final String FILE_SELECTED = "FILE_SELECTED";

    public static final String SLIDE_CODE_COULEUR = "SLIDE_CODE_COULEUR";
    private String userName;
    private static final String LOGIN_USER = "LOGIN_USER";

    //layout bpe
    BpeView excelView = new BpeView();

    //fichier de travail temp
    private File temp;

    //image objet du fichier excel
    private Tape tape = null;
    private byte[] dataTape;
    //backup fichier en base????
    private Tape tapeBkp = null;

    //mapping des data EXCEL
    private ArrayList<Pipe> pipeAA;
    private ArrayList<Pipe> pipeAB;
    private ArrayList<Pipe> pipeBA;
    private ArrayList<Pipe> pipeBB;
    //titres
    private TextView titreBpe;
    private TextView titreBpeSaved;

    private ArrayList<TextView> pipeNumber_AA;
    private ArrayList<Spinner> pipeStatus_AA;
    private ArrayList<TextView> pipeType_AA;
    private ArrayList<TextView> pipeRobe_AA;
    private ArrayList<TextView> pipeRobe_AB;
    private ArrayList<TextView> pipeOrigin_AA;
    private ArrayList<TextView> pipeOrigin_AB;
    private ArrayList<TextView> fibreType_AA;
    private ArrayList<TextView> fibreType_AB;
    private ArrayList<TextView> fibreNumber_AA;
    private ArrayList<TextView> fibreNumber_AB;
    private ArrayList<TextView> pipeColor_AA;
    private ArrayList<TextView> pipeColor_AB;
    private ArrayList<TextView> pipeColorName_AA;
    private ArrayList<TextView> pipeColorName_AB;
    private ArrayList<TextView> pipeNumber_BA;

    /* FACE b */
    private ArrayList<Spinner> pipeStatus_BA;
    private ArrayList<TextView> pipeType_BA;
    private ArrayList<TextView> pipeRobe_BA;
    private ArrayList<TextView> pipeRobe_BB;
    private ArrayList<TextView> fibreType_BA;
    private ArrayList<TextView> fibreType_BB;
    private ArrayList<TextView> fibreNumber_BA;
    private ArrayList<TextView> fibreNumber_BB;
    private ArrayList<TextView> pipeColor_BA;
    private ArrayList<TextView> pipeOrigin_BA;
    private ArrayList<TextView> pipeOrigin_BB;
    private ArrayList<TextView> pipeColor_BB;
    private ArrayList<TextView> pipeColorName_BA;
    private ArrayList<TextView> pipeColorName_BB;
    private ArrayAdapter<CharSequence> adapter;

    View rootView;


    //manage ACTIONS
    private Button btnSendData, btnSaveData, btnCompareData, btnSendMailData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bpe);
        rootView = getWindow().getDecorView().getRootView();

        //recuperation du fichier a travailler EXCEL
        Bundle b = getIntent().getExtras();
        fileSelected = b.getString("fileSelected");

        //preparation du tablelayout
        ViewMake vm = new ViewMake(rootView);
        excelView = vm.makeInputXmlFile();

        //titres
        titreBpe = excelView.getTitreBpe();
        titreBpeSaved = excelView.getTitreBpeSaved();
         /* face a */
        pipeNumber_AA = excelView.getPipeNumber_AA();
        pipeStatus_AA = excelView.getPipeStatus_AA();
        pipeType_AA = excelView.getPipeType_AA();
        pipeRobe_AA = excelView.getPipeRobe_AA();
        pipeRobe_AB = excelView.getPipeRobe_AB();
        pipeOrigin_AA = excelView.getPipeOrigin_AA();
        pipeOrigin_AB = excelView.getPipeOrigin_AB();
        fibreType_AA = excelView.getFibreType_AA();
        fibreType_AB = excelView.getFibreType_AB();
        fibreNumber_AA = excelView.getFibreNumber_AA();
        fibreNumber_AB = excelView.getFibreNumber_AB();
        pipeColor_AA = excelView.getPipeColor_AA();
        pipeColor_AB = excelView.getPipeColor_AB();
        pipeColorName_AA = excelView.getPipeColorName_AA();
        pipeColorName_AB = excelView.getPipeColorName_AB();
        pipeNumber_BA = excelView.getPipeNumber_BA();

          /* FACE b */
        pipeStatus_BA = excelView.getPipeStatus_BA();
        pipeType_BA = excelView.getPipeType_BA();
        pipeRobe_BA = excelView.getPipeRobe_BA();
        pipeRobe_BB = excelView.getPipeRobe_BB();
        pipeOrigin_BA = excelView.getPipeOrigin_BA();
        pipeOrigin_BB = excelView.getPipeOrigin_BB();
        fibreType_BA = excelView.getFibreType_BA();
        fibreType_BB = excelView.getFibreType_BB();
        fibreNumber_BA = excelView.getFibreNumber_BA();
        fibreNumber_BB = excelView.getFibreNumber_BB();
        pipeColor_BA = excelView.getPipeColor_BA();
        pipeColor_BB = excelView.getPipeColor_BB();
        pipeColorName_BA = excelView.getPipeColorName_BA();
        pipeColorName_BB = excelView.getPipeColorName_BB();


        //gestion menu choix multiple listbox
        adapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.fibre_state_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (int i = 0; i < pipeStatus_AA.size(); i++) {
            pipeStatus_AA.get(i).setAdapter(adapter);
        }
        for (int i = 0; i < pipeStatus_BA.size(); i++) {
            pipeStatus_BA.get(i).setAdapter(adapter);
        }
        //Import du fichier excel
        tape = new Tape();
        pipeAA = getExcelFileA("A", fileSelected);
        pipeAB = getExcelFileB("B", fileSelected);
        pipeBA = getExcelFileC("C", fileSelected);
        pipeBB = getExcelFileD("D", fileSelected);

        tape.setPipeAA(pipeAA);
        tape.setPipeAB(pipeAB);
        tape.setPipeBA(pipeBA);
        tape.setPipeBB(pipeBB);

        //construction

        makeView(tape);

        //TODO backp fileSelected temp
        tapeBkp = SerializationUtils.clone(tape);


        btnSaveData = (Button) rootView.findViewById(R.id.btn_save_excel_data);
        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageSavingData(rootView);
            }
        });

        btnSendMailData = (Button) rootView.findViewById(R.id.btn_send_mail_excel_data);
        btnSendMailData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageSendingMail(rootView);
            }
        });
    }

    public ArrayList<Pipe> getExcelFileA(String faceColonne, String fileSelected) {
        try {

            String youFilePath = fileSelected;
            Log.d("TRACE_OP_FILE_LOADER", "Selected : " + youFilePath);

            File file = new File(youFilePath);

            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet = workbook.getSheet(0);
            String codecouleur = null;
            Cell a1 = null;

            //gestion des titres
            Cell cellTitre = sheet.getCell(5, 1);
            tape.setTitre(cellTitre.getContents());

            Cell cellTitreSaved = sheet.getCell(12, 1);
            tape.setTitreSaved(cellTitreSaved.getContents());


            int li = 8;
            int m = 0;
            int col = 0;
            int maxRow = 10;

            pipeAA = new ArrayList<Pipe>();

            for (int k = 0; k < maxRow; k++) {
                m = k + li;
                Pipe pipe = new Pipe();


                if (faceColonne.equals("A")) {

                    for (col = 1; col < 10; col++) {
                        a1 = sheet.getCell(col, m);
                        pipe.setTape_side("A");
                        if (col == 1)
                            pipe.setPipe_number(a1.getContents().toString());

                        Colour jxlc = a1.getCellFormat().getFont().getColour();
                        ColorUtil cuc = new ColorUtil(jxlc.getDefaultRGB().getRed(), jxlc.getDefaultRGB().getGreen(), jxlc.getDefaultRGB().getBlue(), 255);
                        pipe.setPipe_line_color(cuc);

                        if (col == 2)
                            pipe.setPipe_type(a1.getContents().toString());
                        if (col == 3)
                            pipe.setPipe_rope(a1.getContents().toString());
                        if (col == 4)
                            pipe.setPipe_origin(a1.getContents().toString());
                        if (col == 5) {
                            //TODO find why length =0 dans le cas de la copie du fichier excel??????
                            if (a1.getContents().toString().length() == 0 || a1.getContents().toString() == "") {
                                codecouleur = "";

                            } else {
                                if (a1.getContents().toString().length() > 2) {
                                    codecouleur = a1.getContents().toString().trim().substring(0, 3);
                                } else {
                                    codecouleur = a1.getContents().toString().trim().substring(0, 2);

                                }
                            }
                            pipe.setPipe_colorText(a1.getContents().toString());
                            pipe.setPipe_color(new ColorUtil().setColor(codecouleur));
                        }
                        if (col == 7)
                            pipe.setFibre_number(a1.getContents().toString());
                        if (col == 8)
                            pipe.setFibre_type(a1.getContents().toString());
                        if (col == 9)
                            pipe.setPipe_status(a1.getContents().toString());

                    }
                    pipeAA.add(pipe);


                }
            }
        } catch (IOException e)

        {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return pipeAA;
    }


    public ArrayList<Pipe> getExcelFileB(String faceColonne, String fileSelected) {
        try {

            String youFilePath = fileSelected;
            Log.d("TRACE_OP_FILE_LOADER", "Selected : " + youFilePath);

            File file = new File(youFilePath);

            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet = workbook.getSheet(0);
            String codecouleur = null;
            Cell a1 = null;

            int li = 8;
            int m = 0;
            int col = 0;
            int maxRow = 10;

            pipeAB = new ArrayList<Pipe>();
            for (int k = 0; k < maxRow; k++) {
                m = k + li;
                Pipe pipe = new Pipe();

                if (faceColonne.equals("B")) {

                    for (col = 10; col < 16; col++) {
                        a1 = sheet.getCell(col, m);
                        pipe.setTape_side("B");
                        // if (col == 1)
                        // pipe.setPipe_number(a1.getContents().toString());
                        // if (col == 2)
                        // pipe.setPipe_type(a1.getContents().toString());
                        if (col == 10)
                            pipe.setFibre_number(a1.getContents().toString());
                        if (col == 11)
                            pipe.setFibre_type(a1.getContents().toString());
                        if (col == 13) {

                            if (a1.getContents().toString().length() == 0 || a1.getContents().toString() == "") {
                                codecouleur = "";

                            } else {
                                if (a1.getContents().toString().length() > 2) {
                                    codecouleur = a1.getContents().toString().trim().substring(0, 3);
                                } else {
                                    codecouleur = a1.getContents().toString().trim().substring(0, 2);

                                }
                            }
                            pipe.setPipe_colorText(a1.getContents().toString());
                            pipe.setPipe_color(new ColorUtil().setColor(codecouleur));
                        }
                        if (col == 14)
                            pipe.setPipe_rope(a1.getContents().toString());
                        if (col == 15)
                            pipe.setPipe_origin(a1.getContents().toString());

                    }
                    pipeAB.add(pipe);
                }
            }
        } catch (IOException e)

        {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return pipeAB;
    }

    public ArrayList<Pipe> getExcelFileC(String faceColonne, String fileSelected) {
        try {

            String youFilePath = fileSelected;
            Log.d("TRACE_OP_FILE_LOADER", "Selected : " + youFilePath);

            File file = new File(youFilePath);

            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet = workbook.getSheet(0);
            String codecouleur = null;
            Cell a1 = null;


            int li = 30;
            int m = 0;
            int col = 0;
            int maxRow = 14;

            pipeBA = new ArrayList<Pipe>();
            for (int k = 0; k < maxRow; k++) {
                m = k + li;
                Pipe pipe = new Pipe();

                if (faceColonne.equals("C")) {

                    for (col = 1; col < 10; col++) {
                        a1 = sheet.getCell(col, m);
                        pipe.setTape_side("C");
                        if (col == 1)
                            pipe.setPipe_number(a1.getContents().toString());

                        Colour jxlc = a1.getCellFormat().getFont().getColour();
                        ColorUtil cuc = new ColorUtil(jxlc.getDefaultRGB().getRed(), jxlc.getDefaultRGB().getGreen(), jxlc.getDefaultRGB().getBlue(), 255);
                        pipe.setPipe_line_color(cuc);

                        pipe.setPipe_line_color(cuc);
                        if (col == 2)
                            pipe.setPipe_type(a1.getContents().toString());
                        if (col == 3)
                            pipe.setPipe_rope(a1.getContents().toString());
                        if (col == 4)
                            pipe.setPipe_origin(a1.getContents().toString());
                        if (col == 5) {
                            if (a1.getContents().toString().length() == 0 || a1.getContents().toString() == "") {
                                codecouleur = "";

                            } else {
                                if (a1.getContents().toString().length() > 2) {
                                    codecouleur = a1.getContents().toString().trim().substring(0, 3);
                                } else {
                                    codecouleur = a1.getContents().toString().trim().substring(0, 2);

                                }
                            }
                            pipe.setPipe_colorText(a1.getContents().toString());
                            pipe.setPipe_color(new ColorUtil().setColor(codecouleur));


                        }
                        if (col == 7)
                            pipe.setFibre_number(a1.getContents().toString());
                        if (col == 8)
                            pipe.setFibre_type(a1.getContents().toString());
                        if (col == 9)
                            pipe.setPipe_status(a1.getContents().toString());

                    }
                    pipeBA.add(pipe);
                }
            }
        } catch (IOException e)

        {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return pipeBA;
    }

    public ArrayList<Pipe> getExcelFileD(String faceColonne, String fileSelected) {
        try {

            String youFilePath = fileSelected;
            Log.d("TRACE_OP_FILE_LOADER", "Selected : " + youFilePath);

            File file = new File(youFilePath);

            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet = workbook.getSheet(0);
            String codecouleur = null;
            Cell a1 = null;

            int li = 30;
            int m = 0;
            int col = 0;
            int maxRow = 14;

            pipeBB = new ArrayList<Pipe>();
            for (int k = 0; k < maxRow; k++) {
                m = k + li;
                Pipe pipe = new Pipe();

                if (faceColonne.equals("D")) {


                    for (col = 10; col < 16; col++) {
                        a1 = sheet.getCell(col, m);
                        pipe.setTape_side("D");
                        // if (col == 1)
                        // pipe.setPipe_number(a1.getContents().toString());
                        // if (col == 2)
                        // pipe.setPipe_type(a1.getContents().toString());
                        if (col == 10)
                            pipe.setFibre_number(a1.getContents().toString());
                        if (col == 11)
                            pipe.setFibre_type(a1.getContents().toString());
                        if (col == 13) {

                            if (a1.getContents().toString().length() == 0 || a1.getContents().toString() == "") {
                                codecouleur = "";

                            } else {
                                if (a1.getContents().toString().length() > 2) {
                                    codecouleur = a1.getContents().toString().trim().substring(0, 3);
                                } else {
                                    codecouleur = a1.getContents().toString().trim().substring(0, 2);

                                }
                            }
                            pipe.setPipe_colorText(a1.getContents().toString());

                            pipe.setPipe_color(new ColorUtil().setColor(codecouleur));
                        }
                        if (col == 14)
                            pipe.setPipe_rope(a1.getContents().toString());
                        if (col == 15)
                            pipe.setPipe_origin(a1.getContents().toString());

                    }
                    pipeBB.add(pipe);
                }
            }
        } catch (IOException e)

        {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return pipeBB;
    }


    public void makeView(Tape tape) {

        pipeAA = tape.getPipeAA();
        pipeAB = tape.getPipeAB();
        pipeBA = tape.getPipeBA();
        pipeBB = tape.getPipeBB();


        if (pipeAA.size() != 0)

        {

            for (int l = 0; l < 10; l++) {

                pipeColor_AA.get(l).setBackgroundColor(
                        Color.argb(255, pipeAA.get(l).getPipe_color().getR(), pipeAA.get(l).getPipe_color().getV(), pipeAA.get(l).getPipe_color()
                                .getB())
                );

                pipeNumber_AA.get(l).setText(pipeAA.get(l).getPipe_number());
                pipeNumber_AA.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));
                pipeType_AA.get(l).setText(pipeAA.get(l).getPipe_type());
                pipeType_AA.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));
                pipeRobe_AA.get(l).setText(pipeAA.get(l).getPipe_rope());
                pipeRobe_AA.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));

                pipeOrigin_AA.get(l).setText(pipeAA.get(l).getPipe_origin());
                pipeOrigin_AA.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));

                pipeColorName_AA.get(l).setText(pipeAA.get(l).getPipe_colorText());
                pipeColorName_AA.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));

                fibreType_AA.get(l).setText(pipeAA.get(l).getFibre_type());
                fibreType_AA.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));

                fibreNumber_AA.get(l).setText(pipeAA.get(l).getFibre_number());
                fibreNumber_AA.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));


                if (pipeAA.get(l).getPipe_status().matches(adapter.getItem(0).toString())) {
                    pipeStatus_AA.get(l).setSelection(0);
                } else if (pipeAA.get(l).getPipe_status().matches(adapter.getItem(1).toString())) {
                    pipeStatus_AA.get(l).setSelection(1);
                } else {
                    pipeStatus_AA.get(l).setSelection(2);
                }

            }
        }

        if (pipeBA.size() != 0)

        {
            for (int l = 0; l < 14; l++) {

                pipeColor_BA.get(l).setBackgroundColor(
                        Color.argb(255, pipeBA.get(l).getPipe_color().getR(), pipeBA.get(l).getPipe_color().getV(), pipeBA.get(l).getPipe_color()
                                .getB())
                );

                pipeNumber_BA.get(l).setText(pipeBA.get(l).getPipe_number());
                pipeNumber_BA.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                pipeType_BA.get(l).setText(pipeBA.get(l).getPipe_type());
                pipeType_BA.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                pipeRobe_BA.get(l).setText(pipeBA.get(l).getPipe_rope());
                pipeRobe_BA.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                pipeOrigin_BA.get(l).setText(pipeBA.get(l).getPipe_origin());
                pipeOrigin_BA.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                pipeColorName_BA.get(l).setText(pipeBA.get(l).getPipe_colorText());
                pipeColorName_BA.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                fibreType_BA.get(l).setText(pipeBA.get(l).getFibre_type());
                fibreType_BA.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                fibreNumber_BA.get(l).setText(pipeBA.get(l).getFibre_number());
                fibreNumber_BA.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));


                if (pipeBA.get(l).getPipe_status().matches(adapter.getItem(0).toString())) {
                    pipeStatus_BA.get(l).setSelection(0);
                } else if (pipeBA.get(l).getPipe_status().matches(adapter.getItem(1).toString())) {
                    pipeStatus_BA.get(l).setSelection(1);
                } else {
                    pipeStatus_BA.get(l).setSelection(2);
                }

            }
        }

        if (pipeAB.size() != 0 && pipeAA.size() != 0)

        {

            for (int l = 0; l < 10; l++) {

                pipeColor_AB.get(l).setBackgroundColor(
                        Color.argb(255, pipeAB.get(l).getPipe_color().getR(), pipeAB.get(l).getPipe_color().getV(), pipeAB.get(l).getPipe_color()
                                .getB())
                );
                pipeRobe_AB.get(l).setText(pipeAB.get(l).getPipe_rope());

                pipeRobe_AB.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));

                pipeOrigin_AB.get(l).setText(pipeAB.get(l).getPipe_origin());
                pipeOrigin_AB.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));

                pipeColorName_AB.get(l).setText(pipeAB.get(l).getPipe_colorText());
                pipeColorName_AB.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));

                fibreType_AB.get(l).setText(pipeAB.get(l).getFibre_type());
                fibreType_AB.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));

                fibreNumber_AB.get(l).setText(pipeAB.get(l).getFibre_number());
                fibreNumber_AB.get(l).setTextColor(Color.argb(255, pipeAA.get(l).getPipe_line_color().getR(), pipeAA.get(l).getPipe_line_color().getV(), pipeAA.get(l).getPipe_line_color().getB()));


            }
        }

        if (pipeBB.size() != 0 && pipeBA.size() != 0)

        {

            for (int l = 0; l < 14; l++) {

                pipeColor_BB.get(l).setBackgroundColor(
                        Color.argb(255, pipeBB.get(l).getPipe_color().getR(), pipeBB.get(l).getPipe_color().getV(), pipeBB.get(l).getPipe_color()
                                .getB())
                );
                pipeRobe_BB.get(l).setText(pipeBB.get(l).getPipe_rope());
                pipeRobe_BB.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                pipeOrigin_BB.get(l).setText(pipeBB.get(l).getPipe_origin());
                pipeOrigin_BB.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                pipeColorName_BB.get(l).setText(pipeBB.get(l).getPipe_colorText());
                pipeColorName_BB.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                fibreType_BB.get(l).setText(pipeBB.get(l).getFibre_type());
                fibreType_BB.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

                fibreNumber_BB.get(l).setText(pipeBB.get(l).getFibre_number());
                fibreNumber_BB.get(l).setTextColor(Color.argb(255, pipeBA.get(l).getPipe_line_color().getR(), pipeBA.get(l).getPipe_line_color().getV(), pipeBA.get(l).getPipe_line_color().getB()));

            }
        }

        //gestion des titres

        titreBpe.setText(tape.getTitre());
        titreBpeSaved.setText(tape.getTitreSaved());


    }

    public void manageSavingData(final View rootView) {

        try {
            //creation du fichier temp.xls
            //TODO change temp name if not register when sending data by mail
            String tempName = "temp.xls";
            temp = new File(Environment.getExternalStorageDirectory(), getPackageName() + "/" + tempName);

            if (!temp.exists()) {
                temp.createNewFile();
            }
            File file = new File(fileSelected);
            final Workbook workbook = Workbook.getWorkbook(file);

            //copy
            final String youNewFilePath = temp.getPath();
            final WritableWorkbook copy = Workbook.createWorkbook(new File(youNewFilePath), workbook);
            final WritableSheet sheet2 = copy.getSheet(0);


            GraniouProcess process = new GraniouProcess();

            process.dataChanged(titreBpeSaved,
                    tapeBkp,
                    tape,
                    pipeNumber_AA,
                    pipeStatus_AA,
                    pipeStatus_BA,
                    pipeNumber_BA,
                    pipeType_AA,
                    pipeRobe_AA,
                    pipeRobe_AB,
                    pipeOrigin_AA,
                    pipeOrigin_AB,
                    fibreType_AA,
                    fibreType_AB,
                    fibreNumber_AA,
                    fibreNumber_AB,
                    pipeColor_AA,
                    pipeColor_AB,
                    pipeColorName_AA,
                    pipeColorName_AB,

                    pipeType_BA,
                    pipeRobe_BA,
                    pipeRobe_BB,
                    pipeOrigin_BA,
                    pipeOrigin_BB,
                    fibreType_BA,
                    fibreType_BB,
                    fibreNumber_BA,
                    fibreNumber_BB,
                    pipeColor_BA,
                    pipeColor_BB,
                    pipeColorName_BA,
                    pipeColorName_BB,
                    sheet2
            );


            final AlertDialog alertDialog = new AlertDialog.Builder(BpeActivity.this).create();

            final EditText inputTitle = new EditText(getApplicationContext());
            alertDialog.setTitle("Enregistrement des modifications");
            alertDialog.setMessage("Vous devez modifier le diff en RECOK/RECNOK");
            inputTitle.setText(titreBpeSaved.getText());
            alertDialog.setView(inputTitle);

            alertDialog.setButton("Validez..", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try {


                        final AlertDialog alertDialog = new AlertDialog.Builder(BpeActivity.this).create();
                        final EditText inputSheet = new EditText(getApplicationContext());
                        alertDialog.setTitle("Enregistrement des modifications");
                        alertDialog.setMessage("Vous devez modifier titre de la page");
                        inputSheet.setText("");
                        alertDialog.setView(inputSheet);

                        alertDialog.setButton("Validez..", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                try {
                                    // enregistrement du titre
                                    String newTitreSaved = inputTitle.getText().toString();

                                    WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
                                    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
                                    cellFormat.setAlignment(Alignment.CENTRE);
                                    cellFont.setColour(Colour.RED);
                                    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
                                    Label labelTitreSaved = new Label(12, 1, newTitreSaved, cellFormat);
                                    titreBpeSaved.setText(inputTitle.getText().toString());
                                    sheet2.addCell(labelTitreSaved);
                                    //enregistrement de la page
                                    sheet2.setName(inputSheet.getText().toString());
                                    copy.write();
                                    copy.close();
                                    workbook.close();


                                    if (null != temp && temp.exists()) {

                                        try {
                                            //TODO sauvegarde fichier si pas envoie mail
                                            tape = new Tape();

                                            pipeAA = getExcelFileA("A", temp.getPath());
                                            pipeAB = getExcelFileB("B", temp.getPath());
                                            pipeBA = getExcelFileC("C", temp.getPath());
                                            pipeBB = getExcelFileD("D", temp.getPath());

                                            tape.setPipeAA(pipeAA);
                                            tape.setPipeAB(pipeAB);
                                            tape.setPipeBA(pipeBA);
                                            tape.setPipeBB(pipeBB);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                        alertDialog.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "Enregistrement réussi!", Toast.LENGTH_LONG).show();
                }

            });

            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Enregistrement abandonné!", Toast.LENGTH_LONG).show();
        }


    }

    public void manageSendingMail(View rootView) {


        try {
            if (null != temp && temp.exists()) {
                final String oldFile = fileSelected.substring(37, fileSelected.length());//substring sdcard/com

                final AlertDialog alertDialog = new AlertDialog.Builder(BpeActivity.this).create();

                final EditText excelName = new EditText(getApplicationContext());
                alertDialog.setTitle("Enregistrement des modifications");
                alertDialog.setMessage("Vous devez modifier le nom du fichier");
                excelName.setText(oldFile);
                alertDialog.setView(excelName);

                alertDialog.setButton("Validez..", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String newExcelName = excelName.getText().toString();
                            File newFile = null;
                            try {
                                          /* copy */
                                newFile = new File(Environment.getExternalStorageDirectory(), getPackageName() + "/" + newExcelName);

                                newFile.createNewFile();
                                temp.renameTo(newFile);

                                String email = "ivan.jovicic@trace-ope.com";
                                String subject = "BPE : " + newFile.getName();
                                String message = "travaux effectues";

                                // Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:ivan.jovicic@trace-ope.com"));
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:a2laulanie@gmail.com"));
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(newFile));
                                startActivity(emailIntent);

                            } catch (Throwable t) {
                                Toast.makeText(getApplicationContext(), "Envoie stoppé recommencez: " + t.toString(),
                                        Toast.LENGTH_LONG).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });

                alertDialog.show();
            } else {
                Toast.makeText(getApplicationContext(), "Vous devez enregistrer votre feuille de travail !", Toast.LENGTH_LONG).show();
            }

        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(), "Vous devez enregistrer votre feuille de travail !" + t.toString(),
                    Toast.LENGTH_LONG).show();

        }
    }

    //gestion perte couleur à la rotation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {

            if (!temp.exists()) {
                temp.createNewFile();
            }
            File file = new File(fileSelected);
            final Workbook workbook = Workbook.getWorkbook(file);

            //copy
            final String youNewFilePath = temp.getPath();
            final WritableWorkbook copy = Workbook.createWorkbook(new File(youNewFilePath), workbook);
            final WritableSheet sheet2 = copy.getSheet(0);

            GraniouProcess process = new GraniouProcess();

            process.dataChanged(titreBpeSaved,
                    tapeBkp,
                    tape,
                    pipeNumber_AA,
                    pipeStatus_AA,
                    pipeStatus_BA,
                    pipeNumber_BA,
                    pipeType_AA,
                    pipeRobe_AA,
                    pipeRobe_AB,
                    pipeOrigin_AA,
                    pipeOrigin_AB,
                    fibreType_AA,
                    fibreType_AB,
                    fibreNumber_AA,
                    fibreNumber_AB,
                    pipeColor_AA,
                    pipeColor_AB,
                    pipeColorName_AA,
                    pipeColorName_AB,

                    pipeType_BA,
                    pipeRobe_BA,
                    pipeRobe_BB,
                    pipeOrigin_BA,
                    pipeOrigin_BB,
                    fibreType_BA,
                    fibreType_BB,
                    fibreNumber_BA,
                    fibreNumber_BB,
                    pipeColor_BA,
                    pipeColor_BB,
                    pipeColorName_BA,
                    pipeColorName_BB,
                    sheet2
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        MenuItem item = menu.findItem(R.id.action_fibre);
        item.setEnabled(false);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //start download excel file
        int id = item.getItemId();
        if (id == R.id.action_code_couleur) {
            Intent intent = new Intent(getApplicationContext(), SlideActivity.class);
            intent.putExtra("slideType", SLIDE_CODE_COULEUR);
            startActivity(intent);
            return true;
        }


        if (id == R.id.action_fibre) {
            Intent intent = new Intent(getApplicationContext(), FibreActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            return true;
        }

        //Comparaison des BPE tag et fichier téléchargé
        if (id == R.id.action_bpe_compare) {
            Intent intent = new Intent(getApplicationContext(), ReaderActivity.class);
            intent.putExtra(LOGIN_USER, userName);

            //serialization du tape
            if (tape != null) {
                System.out.println(tapeBkp);
                System.out.println(">>>>>>>>>>>>>>>>>>>>taille des tableaux de l objet TAPE :" + this.tape.getPipeAA().size() + " " + this.tape.getPipeAB().size() + " " + this.tape.getPipeBB().size() + " " + this.tape.getPipeBA().size());
                intent.putExtra("tape", this.tape);
            }
            startActivity(intent);
            return true;
        }

        //teectionBT avec apairage beacon
       /* if (id == R.id.action_detecteur) {
            Intent intent = new Intent(getApplicationContext(), GeolocActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            return true;
        }*/


        return super.onOptionsItemSelected(item);
    }

}
