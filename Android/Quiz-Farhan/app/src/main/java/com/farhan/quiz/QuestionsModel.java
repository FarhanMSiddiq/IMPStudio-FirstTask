package com.farhan.quiz;

/**
 * Created by delaroy on 4/30/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionsModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("soal")
    @Expose
    private String soal;
    @SerializedName("jawaban_a")
    @Expose
    private String jawabanA;
    @SerializedName("jawaban_b")
    @Expose
    private String jawabanB;
    @SerializedName("jawaban_c")
    @Expose
    private String jawabanC;
    @SerializedName("jawaban_d")
    @Expose
    private String jawabanD;
    @SerializedName("jawaban_e")
    @Expose
    private String jawabanE;
    @SerializedName("jawaban_benar")
    @Expose
    private String jawabanBenar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public String getJawabanA() {
        return jawabanA;
    }

    public void setJawabanA(String jawabanA) {
        this.jawabanA = jawabanA;
    }

    public String getJawabanB() {
        return jawabanB;
    }

    public void setJawabanB(String jawabanB) {
        this.jawabanB = jawabanB;
    }

    public String getJawabanC() {
        return jawabanC;
    }

    public void setJawabanC(String jawabanC) {
        this.jawabanC = jawabanC;
    }

    public String getJawabanD() {
        return jawabanD;
    }

    public void setJawabanD(String jawabanD) {
        this.jawabanD = jawabanD;
    }

    public String getJawabanE() {
        return jawabanE;
    }

    public void setJawabanE(String jawabanE) {
        this.jawabanE = jawabanE;
    }

    public String getJawabanBenar() {
        return jawabanBenar;
    }

    public void setJawabanBenar(String jawabanBenar) {
        this.jawabanBenar = jawabanBenar;
    }


}