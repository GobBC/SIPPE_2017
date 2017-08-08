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
public class TransferenciaAccionReq {
    
    private int identidicador;
    private int consecutivo;
    private MovOficiosAccionReq movOficioAccionReq;
    private String ramo;
    private String depto;
    private String finalidad;
    private String funcion;
    private String subfuncion;
    private String prgConac;
    private String programa;
    private String tipoProy;
    private int proy;
    private int meta;
    private int accion;
    private String partida;
    private String tipoGasto;
    private String fuente;
    private String fondo;
    private String recurso;
    private String municipio;
    private int delegacion;
    private String relLaboral;
    private String estatusTipoOficio;
    private double importe;
    private double disponible;
    private double disponibleAnual;
    private double quincePor;
    private double acumulado;
    private String tipoMovTransf;
    private int metaAux;
    private int accionAux;
    private boolean bMetaInsertada = false;
    private boolean bAccionInsertada = false;
    private int accionReq;

    public int getIdentidicador() {
        return identidicador;
    }

    public void setIdentidicador(int identidicador) {
        this.identidicador = identidicador;
    }

    public MovOficiosAccionReq getMovOficioAccionReq() {
        return movOficioAccionReq;
    }

    public void setMovOficioAccionReq(MovOficiosAccionReq movOficioAccionReq) {
        this.movOficioAccionReq = movOficioAccionReq;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getFinalidad() {
        return finalidad;
    }

    public void setFinalidad(String finalidad) {
        this.finalidad = finalidad;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getSubfuncion() {
        return subfuncion;
    }

    public void setSubfuncion(String subfuncion) {
        this.subfuncion = subfuncion;
    }

    public String getPrgConac() {
        return prgConac;
    }

    public void setPrgConac(String prgConac) {
        this.prgConac = prgConac;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getTipoProy() {
        return tipoProy;
    }

    public void setTipoProy(String tipoProy) {
        this.tipoProy = tipoProy;
    }

    public int getProy() {
        return proy;
    }

    public void setProy(int proy) {
        this.proy = proy;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getFondo() {
        return fondo;
    }

    public void setFondo(String fondo) {
        this.fondo = fondo;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(int delegacion) {
        this.delegacion = delegacion;
    }

    public String getRelLaboral() {
        return relLaboral;
    }

    public void setRelLaboral(String relLaboral) {
        this.relLaboral = relLaboral;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getDisponible() {
        return disponible;
    }

    public void setDisponible(double disponible) {
        this.disponible = disponible;
    }

    public double getQuincePor() {
        return quincePor;
    }

    public void setQuincePor(double quincePor) {
        this.quincePor = quincePor;
    }

    public double getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(double acumulado) {
        this.acumulado = acumulado;
    }

    public String getEstatus() {
        return estatusTipoOficio;
    }

    public void setEstatus(String estatusTipoOficio) {
        this.estatusTipoOficio = estatusTipoOficio;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getEstatusTipoOficio() {
        return estatusTipoOficio;
    }

    public void setEstatusTipoOficio(String estatusTipoOficio) {
        this.estatusTipoOficio = estatusTipoOficio;
    }

    public String getTipoMovTransf() {
        return tipoMovTransf;
    }

    public void setTipoMovTransf(String tipoMovTransf) {
        this.tipoMovTransf = tipoMovTransf;
    }
    
    public int getMetaAux() {
        return metaAux;
    }

    public void setMetaAux(int metaAux) {
        this.metaAux = metaAux;
    }

    public int getAccionAux() {
        return accionAux;
    }

    public void setAccionAux(int accionAux) {
        this.accionAux = accionAux;
    }

    public boolean isbMetaInsertada() {
        return bMetaInsertada;
    }

    public void setbMetaInsertada(boolean bMetaInsertada) {
        this.bMetaInsertada = bMetaInsertada;
    }

    public boolean isbAccionInsertada() {
        return bAccionInsertada;
    }

    public void setbAccionInsertada(boolean bAccionInsertada) {
        this.bAccionInsertada = bAccionInsertada;
    }

    public int getAccionReq() {
        return accionReq;
    }

    public void setAccionReq(int accionReq) {
        this.accionReq = accionReq;
    }

    public double getDisponibleAnual() {
        return disponibleAnual;
    }

    public void setDisponibleAnual(double disponibleAnual) {
        this.disponibleAnual = disponibleAnual;
    }
    
    
}
