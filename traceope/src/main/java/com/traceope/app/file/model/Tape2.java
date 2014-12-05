package com.traceope.app.file.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tape2 implements Serializable {

	private ArrayList<String[]> pipe_Title_A;
	private ArrayList<String[]> pipe_Title_B;
	private ArrayList<String[]> pipe_end_file;

	private ArrayList<Pipe2> pipeA;
	private ArrayList<Pipe2> pipeTitleA;
	private ArrayList<Pipe2> pipeB;
	

	public String getUnit_A() {
		return unit_A;
	}

	public void setUnit_A(String unit_A) {
		this.unit_A = unit_A;
	}

	public String getCol1_A() {
		return col1_A;
	}

	public void setCol1_A(String col1_A) {
		this.col1_A = col1_A;
	}

	public String getCol2_A() {
		return col2_A;
	}

	public void setCol2_A(String col2_A) {
		this.col2_A = col2_A;
	}

	public String getFace_B() {
		return face_B;
	}

	public void setFace_B(String face_B) {
		this.face_B = face_B;
	}

	public String getUnit_B() {
		return unit_B;
	}

	public void setUnit_B(String unit_B) {
		this.unit_B = unit_B;
	}

	public String getCol1_B() {
		return col1_B;
	}

	public void setCol1_B(String col1_B) {
		this.col1_B = col1_B;
	}

	public String getCol2_B() {
		return col2_B;
	}

	public void setCol2_B(String col2_B) {
		this.col2_B = col2_B;
	}

	private String unit_A;
	private String col1_A;
	private String col2_A;
	
	private String face_B;
	private String unit_B;
	private String col1_B;
	private String col2_B;
	
	private String face_A;

	public String getFace_A() {
		return face_A;
	}

	public void setFace_A(String face_A) {
		this.face_A = face_A;
	}
	

	public ArrayList<Pipe2> getPipeTitleA() {
		return pipeTitleA;
	}

	public void setPipeTitleA(ArrayList<Pipe2> pipeTitleA) {
		this.pipeTitleA = pipeTitleA;
	}

	public ArrayList<Pipe2> getPipeTitleB() {
		return pipeTitleB;
	}

	public void setPipeTitleB(ArrayList<Pipe2> pipeTitleB) {
		this.pipeTitleB = pipeTitleB;
	}

	private ArrayList<Pipe2> pipeTitleB;

	public ArrayList<Pipe2> getPipeA() {
		return pipeA;
	}

	public void setPipeA(ArrayList<Pipe2> pipeA) {
		this.pipeA = pipeA;
	}

	public ArrayList<Pipe2> getPipeB() {
		return pipeB;
	}

	public void setPipeB(ArrayList<Pipe2> pipeB) {
		this.pipeB = pipeB;
	}

	public ArrayList<String[]> getPipe_Title_A() {
		return pipe_Title_A;
	}

	public void setPipe_Title_A(ArrayList<String[]> pipe_Title_A) {
		this.pipe_Title_A = pipe_Title_A;
	}

	public ArrayList<String[]> getPipe_Title_B() {
		return pipe_Title_B;
	}

	public void setPipe_Title_B(ArrayList<String[]> pipe_Title_B) {
		this.pipe_Title_B = pipe_Title_B;
	}

	public ArrayList<String[]> getPipe_end_file() {
		return pipe_end_file;
	}

	public void setPipe_end_file(ArrayList<String[]> pipe_end_file) {
		this.pipe_end_file = pipe_end_file;
	}
	
	

	



	

}
