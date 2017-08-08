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
public class Dependencia {

    private String ramo;
    private String deptoId;
    private String departamento;
    private String mpo;
    private int year;
    private String rfc;
    private String homoclave;

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getDeptoId() {
        return deptoId;
    }

    public void setDeptoId(String deptoId) {
        this.deptoId = deptoId;
    }

    public String getDepartamento() {
        if (departamento.equals("null")) {
            return "";
        } else {
            return departamento;
        }
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMpo() {
        return mpo;
    }

    public void setMpo(String mpo) {
        this.mpo = mpo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getHomoclave() {
        return homoclave;
    }

    public void setHomoclave(String homoclave) {
        this.homoclave = homoclave;
    }
}
