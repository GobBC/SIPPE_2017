/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author jarguelles
 */
public class Parametro {

    int year_ant;
    int year;
    int year_nvo;
    private String siglasIdTramite;
    private int empresaCG;
    private int OrigenPolMCCG;

    public Parametro() {
    }

    public int getYear_ant() {
        return year_ant;
    }

    public void setYear_ant(int year_ant) {
        this.year_ant = year_ant;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear_nvo() {
        return year_nvo;
    }

    public void setYear_nvo(int year_nvo) {
        this.year_nvo = year_nvo;
    }

    public String getSiglasIdTramite() {
        return siglasIdTramite;
    }

    public void setSiglasIdTramite(String siglasIdTramite) {
        this.siglasIdTramite = siglasIdTramite;
    }

    public int getEmpresaCG() {
        return empresaCG;
    }

    public void setEmpresaCG(int empresaCG) {
        this.empresaCG = empresaCG;
    }

    public int getOrigenPolMCCG() {
        return OrigenPolMCCG;
    }

    public void setOrigenPolMCCG(int OrigenPolMCCG) {
        this.OrigenPolMCCG = OrigenPolMCCG;
    }
}
