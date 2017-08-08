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
public class Meta {

    private int year;
    private String ramo;
    private String ramoDescr;
    private String programa;
    private String programaDescr;
    private int metaId;
    private String metaDescr;
    private String metId;
    private int municipio;
    private int localidad;
    private String meta;
    private int medida;
    private String calculo;
    private int tipo;
    private int claveMedida;
    private String finalidad;
    private String funcion;
    private String subfuncion;
    private String tipoProyecto;
    private int proyecto;
    private String proyectoDescr;
    private int depto;
    private int claveMeta;
    private String lineaPED;
    private int tipoCompromiso;
    private int beneficiados;
    private String presupuestar;
    private String aprobCongreso;
    private String convenio;
    private int conv;
    private int benefH;
    private int benefM;
    private String genero;
    private String principal;
    private String relatoria;
    private String obra;
    private String descrCorta;
    private String mayorCosto;
    private String fichaTecnica;
    private String medidaDescr;
    private int criterio;
    private String obj;
    private String objMeta;
    private int ponderado;
    private String lineaSectorial;
    private String clasificacion;
    private int compromiso;
    //private int grupoPoblacional;
    private String ppto;
    private String autorizacion;

    public int getCriterio() {
        return criterio;
    }

    public void setCriterio(int criterio) {
        this.criterio = criterio;
    }

    public int getClaveMedida() {
        return claveMedida;
    }

    public void setClaveMedida(int claveMedida) {
        this.claveMedida = claveMedida;
    }

    public String getLineaPED() {
        return lineaPED;
    }

    public int getClaveMeta() {
        return claveMeta;
    }

    public void setClaveMeta(int claveMeta) {
        this.claveMeta = claveMeta;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }


    
    public int getCompromiso() {
        return compromiso;
    }

    public void setCompromiso(int compromiso) {
        this.compromiso = compromiso;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    /*
     public int getGrupoPoblacional() {
     return grupoPoblacional;
     }

     public void setGrupoPoblacional(int grupoPoblacional) {
     this.grupoPoblacional = grupoPoblacional;
     }
     */
    public void setLineaPED(String lineaPED) {
        this.lineaPED = lineaPED;
    }

    public String getLineaSectorial() {
        return lineaSectorial;
    }

    public void setLineaSectorial(String lineaSectorial) {
        this.lineaSectorial = lineaSectorial;
    }

    public int getMetaId() {
        return metaId;
    }

    public void setMetaId(int metaId) {
        this.metaId = metaId;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public int getProyecto() {
        return proyecto;
    }

    public void setProyecto(int proyecto) {
        this.proyecto = proyecto;
    }

    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public int getMunicipio() {
        return municipio;
    }

    public void setMunicipio(int municipio) {
        this.municipio = municipio;
    }

    public String getCalculo() {
        return calculo;
    }

    public void setCalculo(String calculo) {
        this.calculo = calculo;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public int getDepto() {
        return depto;
    }

    public void setDepto(int depto) {
        this.depto = depto;
    }

    public int getBenefH() {
        return benefH;
    }

    public void setBenefH(int benefH) {
        this.benefH = benefH;
    }

    public int getBenefM() {
        return benefM;
    }

    public void setBenefM(int benefM) {
        this.benefM = benefM;
    }

    public int getPonderado() {
        return ponderado;
    }

    public void setPonderado(int ponderado) {
        this.ponderado = ponderado;
    }

    public String getPpto() {
        return ppto;
    }

    public void setPpto(String ppto) {
        this.ppto = ppto;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public int getMedida() {
        return medida;
    }

    public void setMedida(int medida) {
        this.medida = medida;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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

    public int getTipoCompromiso() {
        return tipoCompromiso;
    }

    public void setTipoCompromiso(int tipoCompromiso) {
        this.tipoCompromiso = tipoCompromiso;
    }

    public int getBeneficiados() {
        return beneficiados;
    }

    public void setBeneficiados(int beneficiados) {
        this.beneficiados = beneficiados;
    }

    public String getPresupuestar() {
        return presupuestar;
    }

    public void setPresupuestar(String presupuestar) {
        this.presupuestar = presupuestar;
    }

    public String getAprobCongreso() {
        return aprobCongreso;
    }

    public void setAprobCongreso(String aprobCongreso) {
        this.aprobCongreso = aprobCongreso;
    }

    public int getConv() {
        return conv;
    }

    public void setConv(int conv) {
        this.conv = conv;
    }

    public String getRelatoria() {
        return relatoria;
    }

    public void setRelatoria(String relatoria) {
        this.relatoria = relatoria;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getDescrCorta() {
        return descrCorta;
    }

    public void setDescrCorta(String descrCorta) {
        this.descrCorta = descrCorta;
    }

    public String getMayorCosto() {
        return mayorCosto;
    }

    public void setMayorCosto(String mayorCosto) {
        this.mayorCosto = mayorCosto;
    }

    public String getFichaTecnica() {
        return fichaTecnica;
    }

    public void setFichaTecnica(String fichaTecnica) {
        this.fichaTecnica = fichaTecnica;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getObjMeta() {
        return objMeta;
    }

    public void setObjMeta(String objMeta) {
        this.objMeta = objMeta;
    }

    public String getMetId() {
        return metId;
    }

    public void setMetId(String metId) {
        this.metId = metId;
    }

    public String getRamoDescr() {
        return ramoDescr;
    }

    public void setRamoDescr(String ramoDescr) {
        this.ramoDescr = ramoDescr;
    }

    public String getProgramaDescr() {
        return programaDescr;
    }

    public void setProgramaDescr(String programaDescr) {
        this.programaDescr = programaDescr;
    }

    public String getProyectoDescr() {
        return proyectoDescr;
    }

    public void setProyectoDescr(String proyectoDescr) {
        this.proyectoDescr = proyectoDescr;
    }

    public String getMetaDescr() {
        return metaDescr;
    }

    public void setMetaDescr(String metaDescr) {
        this.metaDescr = metaDescr;
    }

    public String getMedidaDescr() {
        return medidaDescr;
    }

    public void setMedidaDescr(String medidaDescr) {
        this.medidaDescr = medidaDescr;
    }
    
    
    
}
