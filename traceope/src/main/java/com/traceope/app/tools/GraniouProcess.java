package com.traceope.app.tools;

import android.graphics.Color;
import android.widget.Spinner;
import android.widget.TextView;

import com.traceope.app.file.model.Tape;

import java.util.ArrayList;
import java.io.Serializable;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;


public class GraniouProcess implements  Serializable {


    /**
     * Created by annedelaulanie on 12/04/14.
     * /* enregistrement de la donnee
     * gestion des couleurs
     * I/System.out﹕ >>>>>>>>>>>>>>>>>dataChanged color-16711936 //vert
     * I/System.out﹕ >>>>>>>>>>>>>>>>>dataChanged color-13487566 //noir
     */

    //nouvelle cellule

    private WritableFont cellFont;
    private WritableCellFormat cellFormat;
    private WritableCellFormat cellFormatNoir;
    private String color;

    public GraniouProcess() {

    }

    public WritableCellFormat makeCell(String color) {
        try {

            cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            cellFormat = new WritableCellFormat(cellFont);
            cellFormat.setAlignment(Alignment.CENTRE);
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

            if (color.matches("rouge")) {
                cellFont.setColour(Colour.RED);

            } else if (color.matches("vert")) {
                cellFont.setColour(Colour.GREEN);

            } else if (color.matches("noir")) {
                cellFont.setColour(Colour.BLACK);
            } else {
                cellFont.setColour(Colour.BLACK);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellFormat;


    }


    public void dataChanged(
                            TextView titreBpeSaved,
                            Tape tapeBkp,
                            Tape tape,
                            ArrayList<TextView> pipeNumber_AA,
                            ArrayList<Spinner> pipeStatus_AA,
                            ArrayList<Spinner> pipeStatus_BA,
                            ArrayList<TextView> pipeNumber_BA,
                            ArrayList<TextView> pipeType_AA,
                            ArrayList<TextView> pipeRobe_AA,
                            ArrayList<TextView> pipeRobe_AB,
                            ArrayList<TextView> pipeOrigin_AA,
                            ArrayList<TextView> pipeOrigin_AB,
                            ArrayList<TextView> fibreType_AA,
                            ArrayList<TextView> fibreType_AB,
                            ArrayList<TextView> fibreNumber_AA,
                            ArrayList<TextView> fibreNumber_AB,
                            ArrayList<TextView> pipeColor_AA,
                            ArrayList<TextView> pipeColor_AB,
                            ArrayList<TextView> pipeColorName_AA,
                            ArrayList<TextView> pipeColorName_AB,

                            ArrayList<TextView> pipeType_BA,
                            ArrayList<TextView> pipeRobe_BA,
                            ArrayList<TextView> pipeRobe_BB,
                            ArrayList<TextView> pipeOrigin_BA,
                            ArrayList<TextView> pipeOrigin_BB,
                            ArrayList<TextView> fibreType_BA,
                            ArrayList<TextView> fibreType_BB,
                            ArrayList<TextView> fibreNumber_BA,
                            ArrayList<TextView> fibreNumber_BB,
                            ArrayList<TextView> pipeColor_BA,
                            ArrayList<TextView> pipeColor_BB,
                            ArrayList<TextView> pipeColorName_BA,
                            ArrayList<TextView> pipeColorName_BB,

                            WritableSheet sheet2

    ) {

        //compare le clone
        //rouge =10 noir=8
        if (tapeBkp.getPipeAA().size() != 0) {
            for (int i = 0; i < tape.getPipeAA().size(); i++) {
                String dataChanged = pipeStatus_AA.get(i).getSelectedItem().toString();
                ColorUtil cuc = tapeBkp.getPipeAA().get(i).getPipe_line_color();

               // int dataChangedColor = tapeBkp.getPipeAA().get(i).getPipe_line_color().getValue();
                String statusBkp = tapeBkp.getPipeAA().get(i).getPipe_status();
                if (!dataChanged.matches(statusBkp)) {
                     if (cuc.getR() == 1||cuc.getR() == 0)  {
                        pipeStatus_AA.get(i).setBackgroundColor(Color.GREEN);
                        pipeNumber_AA.get(i).setTextColor(Color.GREEN);
                        pipeType_AA.get(i).setTextColor(Color.GREEN);
                        pipeRobe_AA.get(i).setTextColor(Color.GREEN);
                        pipeRobe_AB.get(i).setTextColor(Color.GREEN);
                        pipeOrigin_AA.get(i).setTextColor(Color.GREEN);
                        pipeOrigin_AB.get(i).setTextColor(Color.GREEN);
                        fibreType_AA.get(i).setTextColor(Color.GREEN);
                        fibreType_AB.get(i).setTextColor(Color.GREEN);
                        fibreNumber_AA.get(i).setTextColor(Color.GREEN);
                        fibreNumber_AB.get(i).setTextColor(Color.GREEN);
                        pipeColor_AA.get(i).setTextColor(Color.GREEN);
                        pipeColor_AB.get(i).setTextColor(Color.GREEN);
                        pipeColorName_AA.get(i).setTextColor(Color.GREEN);
                        pipeColorName_AB.get(i).setTextColor(Color.GREEN);
                    } else if (cuc.getR() == 255) {
                        //   pipeStatus_AA.get(i).setBackgroundColor(Color.BLACK);
                        pipeNumber_AA.get(i).setTextColor(Color.BLACK);
                        pipeType_AA.get(i).setTextColor(Color.BLACK);
                        pipeRobe_AA.get(i).setTextColor(Color.BLACK);
                        pipeRobe_AB.get(i).setTextColor(Color.BLACK);
                        pipeOrigin_AA.get(i).setTextColor(Color.BLACK);
                        pipeOrigin_AB.get(i).setTextColor(Color.BLACK);
                        fibreType_AA.get(i).setTextColor(Color.BLACK);
                        fibreType_AB.get(i).setTextColor(Color.BLACK);
                        fibreNumber_AA.get(i).setTextColor(Color.BLACK);
                        fibreNumber_AB.get(i).setTextColor(Color.BLACK);
                        pipeColor_AA.get(i).setTextColor(Color.BLACK);
                        pipeColor_AB.get(i).setTextColor(Color.BLACK);
                        pipeColorName_AA.get(i).setTextColor(Color.BLACK);
                        pipeColorName_AB.get(i).setTextColor(Color.BLACK);
                    }

                }
            }
        }

        if (tapeBkp.getPipeBA().size() != 0) {
            for (int i = 0; i < tape.getPipeBA().size(); i++) {
                String dataChanged = pipeStatus_BA.get(i).getSelectedItem().toString();

                ColorUtil cuc = tapeBkp.getPipeBA().get(i).getPipe_line_color();

               // int dataChangedColor = tapeBkp.getPipeBA().get(i).getPipe_line_color().toString();
                String statusBkp = tapeBkp.getPipeBA().get(i).getPipe_status();
                if (!dataChanged.matches(statusBkp)) {
                    if (cuc.getR() == 1||cuc.getR() == 0) {
                        pipeStatus_BA.get(i).setBackgroundColor(Color.GREEN);
                        pipeNumber_BA.get(i).setTextColor(Color.GREEN);
                        pipeType_BA.get(i).setTextColor(Color.GREEN);
                        pipeRobe_BA.get(i).setTextColor(Color.GREEN);
                        pipeRobe_BB.get(i).setTextColor(Color.GREEN);
                        pipeOrigin_BA.get(i).setTextColor(Color.GREEN);
                        pipeOrigin_BB.get(i).setTextColor(Color.GREEN);
                        fibreType_BA.get(i).setTextColor(Color.GREEN);
                        fibreType_BB.get(i).setTextColor(Color.GREEN);
                        fibreNumber_BA.get(i).setTextColor(Color.GREEN);
                        fibreNumber_BB.get(i).setTextColor(Color.GREEN);
                        pipeColor_BA.get(i).setTextColor(Color.GREEN);
                        pipeColor_BB.get(i).setTextColor(Color.GREEN);
                        pipeColorName_BA.get(i).setTextColor(Color.GREEN);
                        pipeColorName_BB.get(i).setTextColor(Color.GREEN);
                    } else if (cuc.getR() == 255) {
                        // pipeStatus_BA.get(i).setBackgroundColor(Color.BLACK);
                        pipeNumber_BA.get(i).setTextColor(Color.BLACK);
                        pipeType_BA.get(i).setTextColor(Color.BLACK);
                        pipeRobe_BA.get(i).setTextColor(Color.BLACK);
                        pipeRobe_BB.get(i).setTextColor(Color.BLACK);
                        pipeOrigin_BA.get(i).setTextColor(Color.BLACK);
                        pipeOrigin_BB.get(i).setTextColor(Color.BLACK);
                        fibreType_BA.get(i).setTextColor(Color.BLACK);
                        fibreType_BB.get(i).setTextColor(Color.BLACK);
                        fibreNumber_BA.get(i).setTextColor(Color.BLACK);
                        fibreNumber_BB.get(i).setTextColor(Color.BLACK);
                        pipeColor_BA.get(i).setTextColor(Color.BLACK);
                        pipeColor_BB.get(i).setTextColor(Color.BLACK);
                        pipeColorName_BA.get(i).setTextColor(Color.BLACK);
                        pipeColorName_BB.get(i).setTextColor(Color.BLACK);
                    }

                }
            }
        }

        try {

            /*
            * Have to recreate cell to create new Excel file*/

            for (int i = 0; i < pipeStatus_AA.size(); i++) {
                String dataChanged = pipeStatus_AA.get(i).getSelectedItem().toString();
                Label label = new Label(9, 8 + i, dataChanged);
                WritableCellFormat cellFormat = this.makeCell("vert");
                WritableCellFormat cellFormatNoir = this.makeCell("noir");

                int colorText;

                colorText = pipeNumber_AA.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeNumber_AA.get(i).getText().toString();
                    Label labelColor = new Label(1, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeNumber_AA.get(i).getText().toString();
                    Label labelColor = new Label(1, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = pipeType_AA.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeType_AA.get(i).getText().toString();
                    Label labelColor = new Label(2, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);

                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeType_AA.get(i).getText().toString();
                    Label labelColor = new Label(2, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = pipeRobe_AA.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeRobe_AA.get(i).getText().toString();
                    Label labelColor = new Label(3, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeRobe_AA.get(i).getText().toString();
                    Label labelColor = new Label(3, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }

                colorText = pipeOrigin_AA.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeOrigin_AA.get(i).getText().toString();
                    Label labelColor = new Label(4, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeOrigin_AA.get(i).getText().toString();
                    Label labelColor = new Label(4, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }

                colorText = pipeColorName_AB.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeColorName_AB.get(i).getText().toString();
                    Label labelColor = new Label(5, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeColorName_AB.get(i).getText().toString();
                    Label labelColor = new Label(5, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = fibreNumber_AA.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = fibreNumber_AA.get(i).getText().toString();
                    Label labelColor = new Label(7, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = fibreNumber_AA.get(i).getText().toString();
                    Label labelColor = new Label(7, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = fibreType_AA.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = fibreType_AA.get(i).getText().toString();
                    Label labelColor = new Label(8, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = fibreType_AA.get(i).getText().toString();
                    Label labelColor = new Label(8, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                //si la ligne change c'est que le status a changé
                dataChanged = pipeStatus_AA.get(i).getSelectedItem().toString();
                Label labelColorStatus = new Label(9, 8 + i, dataChanged, cellFormat);
                sheet2.addCell(labelColorStatus);


                colorText = fibreNumber_AB.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = fibreNumber_AB.get(i).getText().toString();
                    Label labelColor = new Label(10, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = fibreNumber_AB.get(i).getText().toString();
                    Label labelColor = new Label(10, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = fibreType_AB.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = fibreType_AB.get(i).getText().toString();
                    Label labelColor = new Label(11, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = fibreType_AB.get(i).getText().toString();
                    Label labelColor = new Label(11, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }

                colorText = pipeColorName_AB.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeColorName_AB.get(i).getText().toString();
                    Label labelColor = new Label(13, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeColorName_AB.get(i).getText().toString();
                    Label labelColor = new Label(13, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }


                colorText = pipeRobe_AB.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeRobe_AB.get(i).getText().toString();
                    Label labelColor = new Label(14, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeRobe_AB.get(i).getText().toString();
                    Label labelColor = new Label(14, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = pipeOrigin_AB.get(i).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeOrigin_AB.get(i).getText().toString();
                    Label labelColor = new Label(15, 8 + i, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeOrigin_AB.get(i).getText().toString();
                    Label labelColor = new Label(15, 8 + i, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }

                sheet2.addCell(label);

            }

            for (int j = 0; j < pipeStatus_BA.size(); j++) {
                String dataChanged = pipeStatus_BA.get(j).getSelectedItem().toString();
                Label label = new Label(9, 30 + j, dataChanged);

                WritableCellFormat cellFormat = this.makeCell("vert");
                WritableCellFormat cellFormatNoir = this.makeCell("noir");
                int colorText;

                colorText = pipeNumber_BA.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeNumber_BA.get(j).getText().toString();
                    Label labelColor = new Label(1, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeNumber_BA.get(j).getText().toString();
                    Label labelColor = new Label(1, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = pipeType_BA.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeType_BA.get(j).getText().toString();
                    Label labelColor = new Label(2, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeType_BA.get(j).getText().toString();
                    Label labelColor = new Label(2, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = pipeRobe_BA.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeRobe_BA.get(j).getText().toString();
                    Label labelColor = new Label(3, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeRobe_BA.get(j).getText().toString();
                    Label labelColor = new Label(3, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }

                colorText = pipeOrigin_BA.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeOrigin_BA.get(j).getText().toString();
                    Label labelColor = new Label(4, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeOrigin_BA.get(j).getText().toString();
                    Label labelColor = new Label(4, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }

                colorText = pipeColorName_BB.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeColorName_BB.get(j).getText().toString();
                    Label labelColor = new Label(5, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeColorName_BB.get(j).getText().toString();
                    Label labelColor = new Label(5, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = fibreNumber_BA.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = fibreNumber_BA.get(j).getText().toString();
                    Label labelColor = new Label(7, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = fibreNumber_BA.get(j).getText().toString();
                    Label labelColor = new Label(7, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = fibreType_BA.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = fibreType_BA.get(j).getText().toString();
                    Label labelColor = new Label(8, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = fibreType_BA.get(j).getText().toString();
                    Label labelColor = new Label(8, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }

                //si la ligne change c'est que le status a changé

                dataChanged = pipeStatus_BA.get(j).getSelectedItem().toString();
                Label labelColorStatus = new Label(9, 30 + j, dataChanged, cellFormat);
                sheet2.addCell(labelColorStatus);


                colorText = fibreNumber_BB.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = fibreNumber_BB.get(j).getText().toString();
                    Label labelColor = new Label(10, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = fibreNumber_BB.get(j).getText().toString();
                    Label labelColor = new Label(10, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = fibreType_BB.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = fibreType_BB.get(j).getText().toString();
                    Label labelColor = new Label(11, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = fibreType_BB.get(j).getText().toString();
                    Label labelColor = new Label(11, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }

                colorText = pipeColorName_BB.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeColorName_BB.get(j).getText().toString();
                    Label labelColor = new Label(13, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeColorName_BB.get(j).getText().toString();
                    Label labelColor = new Label(13, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }


                colorText = pipeRobe_BB.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeRobe_BB.get(j).getText().toString();
                    Label labelColor = new Label(14, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeRobe_BB.get(j).getText().toString();
                    Label labelColor = new Label(14, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }
                colorText = pipeOrigin_BB.get(j).getCurrentTextColor();
                if (colorText == -16711936) {
                    String dataNotChanged = pipeOrigin_BB.get(j).getText().toString();
                    Label labelColor = new Label(15, 30 + j, dataNotChanged, cellFormat);
                    sheet2.addCell(labelColor);
                } else if (colorText == -16777216) {
                    String dataNotChanged = pipeOrigin_BB.get(j).getText().toString();
                    Label labelColor = new Label(15, 30 + j, dataNotChanged, cellFormatNoir);
                    sheet2.addCell(labelColor);

                }

                sheet2.addCell(label);
            }

            //maj du recolle
            // String newTitre = titreBpe.toString();
            // Label labelTitre = new Label(5, 1, newTitre);
            // sheet2.addCell(labelTitre);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dataCahangedMaintetance(Tape tapeRead,
                                        TextView titreBpeSaved,
                                        Tape tapebkp,
                                        Tape tape,
                                        ArrayList<TextView> pipeNumber_AA,
                                        ArrayList<Spinner> pipeStatus_AA,
                                        ArrayList<Spinner> pipeStatus_BA,
                                        ArrayList<TextView> pipeNumber_BA,
                                        ArrayList<TextView> pipeType_AA,
                                        ArrayList<TextView> pipeRobe_AA,
                                        ArrayList<TextView> pipeRobe_AB,
                                        ArrayList<TextView> pipeOrigin_AA,
                                        ArrayList<TextView> pipeOrigin_AB,
                                        ArrayList<TextView> fibreType_AA,
                                        ArrayList<TextView> fibreType_AB,
                                        ArrayList<TextView> fibreNumber_AA,
                                        ArrayList<TextView> fibreNumber_AB,
                                        ArrayList<TextView> pipeColor_AA,
                                        ArrayList<TextView> pipeColor_AB,
                                        ArrayList<TextView> pipeColorName_AA,
                                        ArrayList<TextView> pipeColorName_AB,

                                        ArrayList<TextView> pipeType_BA,
                                        ArrayList<TextView> pipeRobe_BA,
                                        ArrayList<TextView> pipeRobe_BB,
                                        ArrayList<TextView> pipeOrigin_BA,
                                        ArrayList<TextView> pipeOrigin_BB,
                                        ArrayList<TextView> fibreType_BA,
                                        ArrayList<TextView> fibreType_BB,
                                        ArrayList<TextView> fibreNumber_BA,
                                        ArrayList<TextView> fibreNumber_BB,
                                        ArrayList<TextView> pipeColor_BA,
                                        ArrayList<TextView> pipeColor_BB,
                                        ArrayList<TextView> pipeColorName_BA,
                                        ArrayList<TextView> pipeColorName_BB,

                                        WritableSheet sheet2) {


        for (int i = 0; i < tape.getPipeAA().size(); i++) {
            String pipeStatusIn = tape.getPipeAA().get(i).getPipe_status();
            String pipeStatusOut = tapeRead.getPipeAA().get(i).getPipe_status();

            if (!pipeStatusIn.matches(pipeStatusOut)) {
                pipeNumber_AA.get(i).setTextColor(Color.RED);
                pipeType_AA.get(i).setTextColor(Color.RED);
                pipeRobe_AA.get(i).setTextColor(Color.RED);
                pipeRobe_AB.get(i).setTextColor(Color.RED);
                pipeOrigin_AA.get(i).setTextColor(Color.RED);
                pipeOrigin_AB.get(i).setTextColor(Color.RED);
                fibreType_AA.get(i).setTextColor(Color.RED);
                fibreType_AB.get(i).setTextColor(Color.RED);
                fibreNumber_AA.get(i).setTextColor(Color.RED);
                fibreNumber_AB.get(i).setTextColor(Color.RED);
                pipeColor_AA.get(i).setTextColor(Color.RED);
                pipeColor_AB.get(i).setTextColor(Color.RED);
                pipeColorName_AA.get(i).setTextColor(Color.RED);
                pipeColorName_AB.get(i).setTextColor(Color.RED);


            }
        }

        for (int i = 0; i < tape.getPipeBA().size(); i++) {
            String pipeStatusIn = tape.getPipeBA().get(i).getPipe_status();
            String pipeStatusOut = tapeRead.getPipeBA().get(i).getPipe_status();
            if (!pipeStatusIn.matches(pipeStatusOut)) {

                pipeType_BA.get(i).setTextColor(Color.RED);
                pipeRobe_BA.get(i).setTextColor(Color.RED);
                pipeRobe_BB.get(i).setTextColor(Color.RED);
                pipeOrigin_BA.get(i).setTextColor(Color.RED);
                pipeOrigin_BB.get(i).setTextColor(Color.RED);
                fibreType_BA.get(i).setTextColor(Color.RED);
                fibreType_BB.get(i).setTextColor(Color.RED);
                fibreNumber_BA.get(i).setTextColor(Color.RED);
                fibreNumber_BB.get(i).setTextColor(Color.RED);
                pipeColor_BA.get(i).setTextColor(Color.RED);
                pipeColor_BB.get(i).setTextColor(Color.RED);
                pipeColorName_BA.get(i).setTextColor(Color.RED);
                pipeColorName_BB.get(i).setTextColor(Color.RED);


            }
        }


    }

}
