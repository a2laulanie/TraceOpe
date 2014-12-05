package com.traceope.app.tools;

import java.io.Serializable;

public class ColorUtil implements Serializable {

    private Integer r = 255;
    private Integer v = 255;
    private Integer b = 255;
    private Integer a = 1;

    public ColorUtil() {
        super();
    }

    public ColorUtil(Integer r, Integer v, Integer b, Integer a) {
        super();
        this.r = r;
        this.v = v;
        this.b = b;
        this.a = a;
    }

    public ColorUtil setColor(String color) {

        ColorUtil couleur = null;
        couleur = new ColorUtil(255, 255, 255, 1);

        if (color != null) {
            if (color.trim().equalsIgnoreCase("BLC")) {
                couleur.setA(255);
                couleur.setR(255);
                couleur.setV(255);
                couleur.setB(255);

            } else if (color.trim().equalsIgnoreCase("VL")) {
                couleur.setA(255);
                couleur.setR(154);
                couleur.setV(50);
                couleur.setB(205);

            } else if (color.trim().equalsIgnoreCase("RG")) {
                couleur.setA(255);
                couleur.setR(255);
                couleur.setV(0);
                couleur.setB(0);

            } else if (color.trim().equalsIgnoreCase("GR")) {
                couleur.setA(255);
                couleur.setR(119);
                couleur.setV(136);
                couleur.setB(153);

            } else if (color.trim().equalsIgnoreCase("JA")) {
                couleur.setA(255);
                couleur.setR(255);
                couleur.setV(255);
                couleur.setB(0);

            } else if (color.trim().equalsIgnoreCase("MR")) {
                couleur.setA(255);
                couleur.setR(139);
                couleur.setV(119);
                couleur.setB(101);

            } else if (color.trim().equalsIgnoreCase("OR")) {
                couleur.setA(255);
                couleur.setR(255);
                couleur.setV(165);
                couleur.setB(36);

            } else if (color.trim().equalsIgnoreCase("BLE")) {
                couleur.setA(255);
                couleur.setR(0);
                couleur.setV(0);
                couleur.setB(255);

            } else if (color.trim().equalsIgnoreCase("NR")) {
                couleur.setA(255);
                couleur.setR(0);
                couleur.setV(0);
                couleur.setB(0);

            } else if (color.trim().equalsIgnoreCase("VR")) {
                couleur.setA(255);
                couleur.setR(51);
                couleur.setV(255);
                couleur.setB(51);
            } else if (color.trim().equalsIgnoreCase("MA")) {
                couleur.setA(255);
                couleur.setR(139);
                couleur.setV(119);
                couleur.setB(101);

            } else if (color.trim().equalsIgnoreCase("VE")) {
                couleur.setA(255);
                couleur.setR(0);
                couleur.setV(255);
                couleur.setB(0);

            }
        }

        return couleur;

    }


    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

}
