package com.traceope.app.file.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tape implements Serializable,Cloneable {
	
	private String id;
    private String titre;
    private String titreSaved;
	private int nbre_tube_face_A;
	private int nbre_tube_face_B;
	private String face_A;
	private String colonne_A;
	private String face_B;
	private String colonne_B;
	
	private ArrayList<Pipe> pipeAA = new ArrayList<Pipe>();
	private ArrayList<Pipe> pipeAB= new ArrayList<Pipe>();
	private ArrayList<Pipe> pipeBA= new ArrayList<Pipe>();
	private ArrayList<Pipe> pipeBB= new ArrayList<Pipe>();


	public Tape() {
		// TODO Auto-generated constructor stub
	}

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitreSaved() {
        return titreSaved;
    }

    public void setTitreSaved(String titreSaved) {
        this.titreSaved = titreSaved;
    }

    public String getTitre() {
        return titre;

    }

    public Tape(String id, String titre, String titreSaved,int nbre_tube_face_A, int nbre_tube_face_B, String face_A, String colonne_A, String face_B, String colonne_B,
			ArrayList<Pipe> pipeAA, ArrayList<Pipe> pipeAB, ArrayList<Pipe> pipeBA, ArrayList<Pipe> pipeBB) {
		super();
		this.id = id;
        this.titre = titre;
        this.titreSaved = titreSaved;
		this.nbre_tube_face_A = nbre_tube_face_A;
		this.nbre_tube_face_B = nbre_tube_face_B;
		this.face_A = face_A;
		this.colonne_A = colonne_A;
		this.face_B = face_B;
		this.colonne_B = colonne_B;
		this.pipeAA = pipeAA;
		this.pipeAB = pipeAB;
		this.pipeBA = pipeBA;
		this.pipeBB = pipeBB;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNbre_tube_face_A() {
		return nbre_tube_face_A;
	}

	public void setNbre_tube_face_A(int nbre_tube_face_A) {
		this.nbre_tube_face_A = nbre_tube_face_A;
	}

	public int getNbre_tube_face_B() {
		return nbre_tube_face_B;
	}

	public void setNbre_tube_face_B(int nbre_tube_face_B) {
		this.nbre_tube_face_B = nbre_tube_face_B;
	}

	public String getFace_A() {
		return face_A;
	}

	public void setFace_A(String face_A) {
		this.face_A = face_A;
	}

	public String getColonne_A() {
		return colonne_A;
	}

	public void setColonne_A(String colonne_A) {
		this.colonne_A = colonne_A;
	}

	public String getFace_B() {
		return face_B;
	}

	public void setFace_B(String face_B) {
		this.face_B = face_B;
	}

	public String getColonne_B() {
		return colonne_B;
	}

	public void setColonne_B(String colonne_B) {
		this.colonne_B = colonne_B;
	}

	public ArrayList<Pipe> getPipeAA() {
		return pipeAA;
	}

	public void setPipeAA(ArrayList<Pipe> pipeAA) {
		this.pipeAA = pipeAA;
	}

	public ArrayList<Pipe> getPipeAB() {
		return pipeAB;
	}

	public void setPipeAB(ArrayList<Pipe> pipeAB) {
		this.pipeAB = pipeAB;
	}

	public ArrayList<Pipe> getPipeBA() {
		return pipeBA;
	}

	public void setPipeBA(ArrayList<Pipe> pipeBA) {
		this.pipeBA = pipeBA;
	}

	public ArrayList<Pipe> getPipeBB() {
		return pipeBB;
	}

	public void setPipeBB(ArrayList<Pipe> pipeBB) {
		this.pipeBB = pipeBB;
	}

	
	

}
