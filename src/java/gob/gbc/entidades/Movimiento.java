/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.entidades;

import java.sql.Date;

/**
 *
 * @author ugarcia
 */
public class Movimiento {
    protected int oficio;
    protected Date fechaElab;
    protected String appLogin;
    protected String ramo;
    protected String tipoMovimiento;
    protected String status;
    protected String statusDescr; 
    protected Date fecPPTO;
    protected String jutificacion;
    protected String obsRechazo;
    protected String tipoOficio;
    protected Date fechaAutorizacion;
    protected String capturaEspecial;
    protected int folioRelacionado;
    protected String comentarioAutorizacion;
    protected String comentarioPlaneacion;
    protected String evaluacion;
    
    public Movimiento(){
        this.folioRelacionado = 0;
    }

    public int getOficio() {
        return oficio;
    }

    public void setOficio(int oficio) {
        this.oficio = oficio;
    }

    public Date getFechaElab() {
        return fechaElab;
    }

    public void setFechaElab(Date fechaElab) {
        this.fechaElab = fechaElab;
    }

    public String getAppLogin() {
        return appLogin;
    }

    public void setAppLogin(String appLogin) {
        this.appLogin = appLogin;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFecPPTO() {
        return fecPPTO;
    }

    public void setFecPPTO(Date fecPPTO) {
        this.fecPPTO = fecPPTO;
    }

    public String getJutificacion() {
        return jutificacion;
    }

    public void setJutificacion(String jutificacion) {
        this.jutificacion = jutificacion;
    }

    public String getObsRechazo() {
        return obsRechazo;
    }

    public void setObsRechazo(String obsRechazo) {
        this.obsRechazo = obsRechazo;
    }

    public String getTipoOficio() {
        return tipoOficio;
    }

    public void setTipoOficio(String tipoOficio) {
        this.tipoOficio = tipoOficio;
    }  

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getCapturaEspecial() {
        return capturaEspecial;
    }

    public void setCapturaEspecial(String capturaEspecial) {
        this.capturaEspecial = capturaEspecial;
    }
    
    public String getStatusDescr() {
        return statusDescr;
    }

    public void setStatusDescr(String statusDescr) {
        this.statusDescr = statusDescr;
    }
    
    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public int getFolioRelacionado() {
        return folioRelacionado;
    }

    public void setFolioRelacionado(int folioRelacionado) {
        this.folioRelacionado = folioRelacionado;
    }

    public String getComentarioAutorizacion() {
        return comentarioAutorizacion;
    }

    public void setComentarioAutorizacion(String comentarioAutorizacion) {
        this.comentarioAutorizacion = comentarioAutorizacion;
    }    

    public String getComentarioPlaneacion() {
        return comentarioPlaneacion;
    }

    public void setComentarioPlaneacion(String comentarioPlaneacion) {
        this.comentarioPlaneacion = comentarioPlaneacion;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }
    
    
}
