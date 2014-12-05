package com.traceope.app.file.model;



import com.traceope.app.tools.ColorUtil;

import java.io.Serializable;




public class Pipe implements Serializable {


    private String pipe_number;
    private ColorUtil pipe_line_color;
    private String pipe_type;
    private String pipe_rope;
    private String pipe_origin;
    private String pipe_colorText;
    private ColorUtil pipe_color;
    private String fibre_type;
    private String fibre_number;
    private String tape_side;
    private String pipe_status;

    public String getPipe_status() {
        return pipe_status;
    }

    public void setPipe_status(String pipe_status) {
        this.pipe_status = pipe_status;
    }

    public String getTape_side() {
        return tape_side;
    }

    public void setTape_side(String tape_side) {
        this.tape_side = tape_side;
    }

    public Pipe() {
        // TODO Auto-generated constructor stub
    }

    public Pipe(String pipe_number, String pipe_type, String pipe_rope, String pipe_origin, String pipe_colorText, ColorUtil pipe_color,
                String fibre_type, String fibre_number, String tape_side, String pipe_status) {
        super();
        this.pipe_number = pipe_number;
        this.pipe_type = pipe_type;
        this.pipe_rope = pipe_rope;
        this.pipe_origin = pipe_origin;
        this.pipe_colorText = pipe_colorText;
        this.pipe_color = pipe_color;
        this.fibre_type = fibre_type;
        this.fibre_number = fibre_number;
        this.tape_side = tape_side;
        this.pipe_status = pipe_status;
    }

    public String getPipe_colorText() {
        return pipe_colorText;
    }

    public void setPipe_colorText(String pipe_colorText) {
        this.pipe_colorText = pipe_colorText;
    }

    public String getPipe_number() {
        return pipe_number;
    }

    public void setPipe_number(String pipe_number) {
        this.pipe_number = pipe_number;
    }

    public String getPipe_type() {
        return pipe_type;
    }

    public void setPipe_type(String pipe_type) {
        this.pipe_type = pipe_type;
    }

    public String getPipe_rope() {
        return pipe_rope;
    }

    public void setPipe_rope(String pipe_rope) {
        this.pipe_rope = pipe_rope;
    }

    public String getPipe_origin() {
        return pipe_origin;
    }

    public void setPipe_origin(String pipe_origin) {
        this.pipe_origin = pipe_origin;
    }

    public ColorUtil getPipe_color() {
        return pipe_color;
    }

    public void setPipe_color(ColorUtil pipe_color) {
        this.pipe_color = pipe_color;
    }

    public String getFibre_type() {
        return fibre_type;
    }

    public void setFibre_type(String fibre_type) {
        this.fibre_type = fibre_type;
    }

    public String getFibre_number() {
        return fibre_number;
    }

    public void setFibre_number(String fibre_number) {
        this.fibre_number = fibre_number;
    }

    public ColorUtil getPipe_line_color() {
        return pipe_line_color;
    }

    public void setPipe_line_color(ColorUtil pipe_line_color) {
        this.pipe_line_color = pipe_line_color;
    }

}
