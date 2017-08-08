/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.entidades;

/**
 *
 * @author ugarcia
 */
public class RelacionLaboral {
    private int year;
    private String relacionLabId;
    private String relacionLab;

    public String getRelacionLabId() {
        return relacionLabId;
    }

    public void setRelacionLabId(String relacionLabId) {
        this.relacionLabId = relacionLabId;
    }

    public String getRelacionLab() {
        return relacionLab;
    }

    public void setRelacionLab(String relacionLab) {
        this.relacionLab = relacionLab;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    
}
