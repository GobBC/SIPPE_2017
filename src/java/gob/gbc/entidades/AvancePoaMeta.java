/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author mavalle
 */
public class AvancePoaMeta {
    private int avance_periodo;
    private int estimacion_periodo;
    private String avance_valor;
    private String estimacion_valor;
    private int avance_meta;
    private String avance_ramo;
    private int avance_year;
    private String calculo;
    private String activo;
    private int parametro_prg_periodo;
    private String avance_observa;
    private String avance_observa_anual;

    public int getAvance_periodo() {
        return avance_periodo;
    }

    public int getEstimacion_periodo() {
        return estimacion_periodo;
    }

    public String getAvance_valor() {
        return avance_valor;
    }

    public String getEstimacion_valor() {
        return estimacion_valor;
    }

    public int getAvance_meta() {
        return avance_meta;
    }

    public String getAvance_ramo() {
        return avance_ramo;
    }

    public int getAvance_year() {
        return avance_year;
    }

    public int getParametro_prg_periodo() {
        return parametro_prg_periodo;
    }

    public void setAvance_periodo(int avance_periodo) {
        this.avance_periodo = avance_periodo;
    }

    public void setEstimacion_periodo(int estimacion_periodo) {
        this.estimacion_periodo = estimacion_periodo;
    }

    public void setAvance_valor(String avance_valor) {
        this.avance_valor = avance_valor;
    }

    public void setEstimacion_valor(String estimacion_valor) {
        this.estimacion_valor = estimacion_valor;
    }

    public void setAvance_meta(int avance_meta) {
        this.avance_meta = avance_meta;
    }

    public void setAvance_ramo(String avance_ramo) {
        this.avance_ramo = avance_ramo;
    }

    public void setAvance_year(int avance_year) {
        this.avance_year = avance_year;
    }

    public void setParametro_prg_periodo(int parametro_prg_periodo) {
        this.parametro_prg_periodo = parametro_prg_periodo;
    }

    public String getCalculo() {
        return calculo;
    }

    public String getActivo() {
        return activo;
    }

    public void setCalculo(String calculo) {
        this.calculo = calculo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getAvance_observa() {
        return avance_observa;
    }

    public String getAvance_observa_anual() {
        return avance_observa_anual;
    }

    public void setAvance_observa(String avance_observa) {
        this.avance_observa = avance_observa;
    }

    public void setAvance_observa_anual(String avance_observa_anual) {
        this.avance_observa_anual = avance_observa_anual;
    }
    
    
    
}
