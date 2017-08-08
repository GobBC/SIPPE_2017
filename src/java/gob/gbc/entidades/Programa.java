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
public class Programa {

    private String ramo;
    private String programaId;
    private String programaDesc;
    private int year;
    private String objetivo;
    private String resultado;
    private String aspectos;
    private String rfc;
    private String homoclave;
    private String rfcCoord;
    private String homoCoord;
    private int fin;
    private String proposito;
    private int tipoPrograma;
    private int ponderado;
    private int tipoObjeto;
    private String unidadResponsable;
    private String usuario;
    private String programaConac;

    public String getProgramaConac() {
        return programaConac;
    }

    public void setProgramaConac(String programaConac) {
        this.programaConac = programaConac;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getProgramaId() {
        return programaId;
    }

    public void setProgramaId(String programaId) {
        this.programaId = programaId;
    }

    public String getProgramaDesc() {
        if (programaDesc.equals("null")) {
            return "";
        } else {
            return programaDesc;
        }
    }

    public void setProgramaDesc(String programaDesc) {
        this.programaDesc = programaDesc;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getObjetivo() {
        if (objetivo.equals("null")) {
            return "";
        } else {
            return objetivo;
        }
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getAspectos() {
        return aspectos;
    }

    public void setAspectos(String aspectos) {
        this.aspectos = aspectos;
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

    public String getRfcCoord() {
        return rfcCoord;
    }

    public void setRfcCoord(String rfcCoord) {
        this.rfcCoord = rfcCoord;
    }

    public String getHomoCoord() {
        return homoCoord;
    }

    public void setHomoCoord(String homoCoord) {
        this.homoCoord = homoCoord;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public String getProposito() {
        if (proposito.equals("null")) {
            return "";
        } else {
            return proposito;
        }
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public int getTipoPrograma() {
        return tipoPrograma;
    }

    public void setTipoPrograma(int tipoPrograma) {
        this.tipoPrograma = tipoPrograma;
    }

    public int getPonderado() {
        return ponderado;
    }

    public void setPonderado(int ponderado) {
        this.ponderado = ponderado;
    }

    public String getUnidadResponsable() {
        return unidadResponsable;
    }

    public void setUnidadResponsable(String unidadResponsable) {
        this.unidadResponsable = unidadResponsable;
    }

    public int getTipoObjeto() {
        return tipoObjeto;
    }

    public void setTipoObjeto(int tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }
}
