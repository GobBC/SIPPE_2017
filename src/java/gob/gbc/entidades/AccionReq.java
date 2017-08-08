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
public class AccionReq {
    
    private int year;
    private String ramo;
    private String prg;
    private String depto;
    private int meta;
    private int accion;
    private int requerimiento;
    private String descr;
    private int fuente;
    private int tipoGasto;
    private String partida;
    private int relacionLaboral;
    private int cantidad;
    private int costoUnitario;
    private int costoAnual;
    private int ene;
    private int feb;
    private int mar;
    private int abr;
    private int may;
    private int jun;
    private int jul;
    private int ago;
    private int sep;
    private int oct;
    private int nov;
    private int dic;
    private int articulo;
    private int cantidadOrg;
    private int costoUnitarioOrg;
    private int archivo;
    private String justificado;
    private int fondo;
    private int recurso;
    private String relLaboral;
    
    private String sFuente; 
    private String sFondo; 
    private String sRecurso; 
    private String sArchivo; 
    
    private double cant;
    private double costoUni;
    private double costoAn;
    private double enero;
    private double febrero;
    private double marzo;
    private double abril;
    private double mayo;
    private double junio;
    private double julio;
    private double agosto;
    private double septiembre;
    private double octubre;
    private double noviembre;
    private double diciembre;
    
    private String gpoGasto;
    private String subGrupo;

    public AccionReq() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getPrg() {
        return prg;
    }

    public void setPrg(String prg) {
        this.prg = prg;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
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

    public int getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(int requerimiento) {
        this.requerimiento = requerimiento;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getFuente() {
        return fuente;
    }

    public void setFuente(int fuente) {
        this.fuente = fuente;
    }

    public int getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(int tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public int getRelacionLaboral() {
        return relacionLaboral;
    }

    public void setRelacionLaboral(int relacionLaboral) {
        this.relacionLaboral = relacionLaboral;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(int costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCostoAnual() {
        return costoAnual;
    }

    public void setCostoAnual(int costoAnual) {
        this.costoAnual = costoAnual;
    }

    public int getEne() {
        return ene;
    }

    public void setEne(int ene) {
        this.ene = ene;
    }

    public int getFeb() {
        return feb;
    }

    public void setFeb(int feb) {
        this.feb = feb;
    }

    public int getMar() {
        return mar;
    }

    public void setMar(int mar) {
        this.mar = mar;
    }

    public int getAbr() {
        return abr;
    }

    public void setAbr(int abr) {
        this.abr = abr;
    }

    public int getMay() {
        return may;
    }

    public void setMay(int may) {
        this.may = may;
    }

    public int getJun() {
        return jun;
    }

    public void setJun(int jun) {
        this.jun = jun;
    }

    public int getJul() {
        return jul;
    }

    public void setJul(int jul) {
        this.jul = jul;
    }

    public int getAgo() {
        return ago;
    }

    public void setAgo(int ago) {
        this.ago = ago;
    }

    public int getSep() {
        return sep;
    }

    public void setSep(int sep) {
        this.sep = sep;
    }

    public int getOct() {
        return oct;
    }

    public void setOct(int oct) {
        this.oct = oct;
    }

    public int getNov() {
        return nov;
    }

    public void setNov(int nov) {
        this.nov = nov;
    }

    public int getDic() {
        return dic;
    }

    public void setDic(int dic) {
        this.dic = dic;
    }

    public int getArticulo() {
        return articulo;
    }

    public void setArticulo(int articulo) {
        this.articulo = articulo;
    }

    public int getCantidadOrg() {
        return cantidadOrg;
    }

    public void setCantidadOrg(int cantidadOrg) {
        this.cantidadOrg = cantidadOrg;
    }

    public int getCostoUnitarioOrg() {
        return costoUnitarioOrg;
    }

    public void setCostoUnitarioOrg(int costoUnitarioOrg) {
        this.costoUnitarioOrg = costoUnitarioOrg;
    }

    public int getArchivo() {
        return archivo;
    }

    public void setArchivo(int archivo) {
        this.archivo = archivo;
    }

    public String getJustificado() {
        return justificado;
    }

    public void setJustificado(String justificado) {
        this.justificado = justificado;
    }

    public int getFondo() {
        return fondo;
    }

    public void setFondo(int fondo) {
        this.fondo = fondo;
    }

    public int getRecurso() {
        return recurso;
    }

    public void setRecurso(int recurso) {
        this.recurso = recurso;
    }

    public String getRelLaboral() {
        return relLaboral;
    }

    public void setRelLaboral(String relLaboral) {
        this.relLaboral = relLaboral;
    }  

    public double getEnero() {
        return enero;
    }

    public void setEnero(double enero) {
        this.enero = enero;
    }

    public double getFebrero() {
        return febrero;
    }

    public void setFebrero(double febrero) {
        this.febrero = febrero;
    }

    public double getMarzo() {
        return marzo;
    }

    public void setMarzo(double marzo) {
        this.marzo = marzo;
    }

    public double getAbril() {
        return abril;
    }

    public void setAbril(double abril) {
        this.abril = abril;
    }

    public double getMayo() {
        return mayo;
    }

    public void setMayo(double mayo) {
        this.mayo = mayo;
    }

    public double getJunio() {
        return junio;
    }

    public void setJunio(double junio) {
        this.junio = junio;
    }

    public double getJulio() {
        return julio;
    }

    public void setJulio(double julio) {
        this.julio = julio;
    }

    public double getAgosto() {
        return agosto;
    }

    public void setAgosto(double agosto) {
        this.agosto = agosto;
    }

    public double getSeptiembre() {
        return septiembre;
    }

    public void setSeptiembre(double septiembre) {
        this.septiembre = septiembre;
    }

    public double getOctubre() {
        return octubre;
    }

    public void setOctubre(double octubre) {
        this.octubre = octubre;
    }

    public double getNoviembre() {
        return noviembre;
    }

    public void setNoviembre(double noviembre) {
        this.noviembre = noviembre;
    }

    public double getDiciembre() {
        return diciembre;
    }

    public void setDiciembre(double diciembre) {
        this.diciembre = diciembre;
    }

    public double getCant() {
        return cant;
    }

    public void setCant(double cant) {
        this.cant = cant;
    }

    public double getCostoUni() {
        return costoUni;
    }

    public void setCostoUni(double costoUni) {
        this.costoUni = costoUni;
    }

    public double getCostoAn() {
        return costoAn;
    }

    public void setCostoAn(double costoAn) {
        this.costoAn = costoAn;
    }

    public String getGpoGasto() {
        return gpoGasto;
    }

    public void setGpoGasto(String gpoGasto) {
        this.gpoGasto = gpoGasto;
    }

    public String getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(String subGrupo) {
        this.subGrupo = subGrupo;
    }

    public String getsFuente() {
        return sFuente;
    }

    public void setsFuente(String sFuente) {
        this.sFuente = sFuente;
    }

    public String getsFondo() {
        return sFondo;
    }

    public void setsFondo(String sFondo) {
        this.sFondo = sFondo;
    }

    public String getsRecurso() {
        return sRecurso;
    }

    public void setsRecurso(String sRecurso) {
        this.sRecurso = sRecurso;
    }

    public String getsArchivo() {
        return sArchivo;
    }

    public void setsArchivo(String sArchivo) {
        this.sArchivo = sArchivo;
    }
    
    
}
