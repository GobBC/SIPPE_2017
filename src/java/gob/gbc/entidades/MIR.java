/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author ealonso
 */
import java.sql.Date;

public class MIR {
    private int mir;
    private String strYear;
    private String ramo;
    private String ramoDescr;
    private String prg;
    private String prgDescr;
    private Date fechaRegistro;
    private String status;
    private String statusP;
    private String statusIniDescr;
    private String statusPosDescr;
    private String colorIni;
    private String colorPos;

    //Para permisos de edicion inline
    private boolean normativo = false;
    private int etapa = 0;
    private int estatus = 0;
    private boolean politicaPublica = false;

    public MIR(){
        
    }

    public MIR(String year, String ramo, String programa) {
        this.strYear = year;
        this.ramo = ramo;
        this.prg = programa;
    }
    
    public int getMir() {
        return mir;
    }

    public void setMir(int mir) {
        this.mir = mir;
    }

    public String getStrYear() {
        return strYear;
    }

    public void setStrYear(String strYear) {
        this.strYear = strYear;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }
    public String getRamoDescr() {
        return ramoDescr;
    }

    public void setRamoDescr(String ramoDescr) {
        this.ramoDescr = ramoDescr;
    }
    
    public String getPrg() {
        return prg;
    }

    public void setPrg(String prg) {
        this.prg = prg;
    }

    public String getPrgDescr() {
        return prgDescr;
    }

    public void setPrgDescr(String prgDescr) {
        this.prgDescr = prgDescr;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatusP() {
        return statusP;
    }

    public void setStatusP(String statusP) {
        this.statusP = statusP;
    }

    public String getStatusIniDescr() {
        return statusIniDescr;
    }

    public void setStatusIniDescr(String statusIniDescr) {
        this.statusIniDescr = statusIniDescr;
    }

    public String getStatusPosDescr() {
        return statusPosDescr;
    }

    public void setStatusPosDescr(String statusPosDescr) {
        this.statusPosDescr = statusPosDescr;
    }

    public String getColorIni() {
        return colorIni;
    }

    public void setColorIni(String colorIni) {
        this.colorIni = colorIni;
    }

    public String getColorPos() {
        return colorPos;
    }

    public void setColorPos(String colorPos) {
        this.colorPos = colorPos;
    }

    public boolean isPoliticaPublica() {
        return politicaPublica;
    }

    public void setPoliticaPublica(boolean politicaPublica) {
        this.politicaPublica = politicaPublica;
    }

    public boolean isNormativo() {
        return normativo;
    }

    public void setNormativo(boolean normativo) {
        this.normativo = normativo;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
    
}
