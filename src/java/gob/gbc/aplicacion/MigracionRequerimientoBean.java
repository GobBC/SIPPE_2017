/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Accion;
import gob.gbc.entidades.AccionReq;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.Meta;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class MigracionRequerimientoBean extends ResultSQL {

    Bitacora bitacora;

    public MigracionRequerimientoBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public MigracionRequerimientoBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
    }

    public boolean isParaestatal() {
        boolean isParaestatal = false;
        try {
            isParaestatal = super.getResultSQLisParaestatal();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isParaestatal;
    }

    public String getRolNormativo() {
        String normativo = new String();
        try {
            normativo = super.getResultSQLGetRolesPrg();
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return normativo;
    }
    
    public boolean migrarAccion(int year, String ramo, int metaAct,
            int accionAct, int metaNueva, int accionNueva, String usuario){
        
        boolean resultado = false;
        boolean banPlantilla = false;
        int reqId = 0;
        
        String mensajeProcedure = new String();        
        
        List<AccionReq> accionReqList = new ArrayList<AccionReq>();
        CodigoPPTO codigoN = null;
        CodigoPPTO codigoAct;
        Meta metaN;
        Meta metaAc;
        Accion accionN;
        Accion accionAc;
        
        try{
            accionReqList = super.getResultSQLgetRequerimientosByAccion(year, ramo,metaAct, accionAct);
            
            metaN = super.getResultGetMetaById(year,ramo,metaNueva);
            accionN = super.getResultGetAccionById(year, ramo, metaNueva, accionNueva);
            
            metaAc = super.getResultGetMetaById(year,ramo,metaAct);
            accionAc = super.getResultGetAccionById(year, ramo, metaAct, accionAct);
            
            if(metaN != null && accionN != null){
                for(AccionReq accionReq : accionReqList){
                    codigoAct = super.getRestulSQLgetCodigoPPTOsinRequerimiento(year,
                            ramo, accionReq.getPrg(), metaAc.getTipoProyecto(),
                            metaAc.getProyecto(),metaAc.getMetaId(),
                            accionAc.getAccionId(), accionReq.getPartida(),
                            accionReq.getsFuente(),accionReq.getsFondo(),
                            accionReq.getsRecurso(), accionReq.getRelLaboral());
                    
                    mensajeProcedure = super.getRestulSQLCallUpdatePresupuesto(year, codigoAct.getRamoId(), codigoAct.getDepto(), codigoAct.getFinalidad(),
                        codigoAct.getFuncion(), codigoAct.getSubfuncion(),codigoAct.getProgCONAC() ,codigoAct.getPrograma(), codigoAct.getTipoProy(),
                        Integer.parseInt(codigoAct.getProyecto()),codigoAct.getMeta(), codigoAct.getAccion(), codigoAct.getPartida(), codigoAct.getTipoGasto(), codigoAct.getFuente(),
                        codigoAct.getFondo(), codigoAct.getRecurso(), codigoAct.getMunicipio(), codigoAct.getDelegacion(),codigoAct.getRelLaboral(),
                        accionReq.getRequerimiento(), accionReq.getEnero(), accionReq.getFebrero(), accionReq.getMarzo(), accionReq.getAbril(),
                        accionReq.getMayo(),accionReq.getJunio(), accionReq.getJulio(), accionReq.getAgosto(), accionReq.getSeptiembre(), accionReq.getOctubre(),
                        accionReq.getNoviembre(), accionReq.getDiciembre(), accionReq.getCant(), accionReq.getCostoUni(), "N",
                        "S",accionReq.getDescr().replaceAll("'", ""),String.valueOf(accionReq.getArticulo()),
                        accionReq.getJustificado().replaceAll("'", ""), usuario,accionReq.getGpoGasto(),accionReq.getSubGrupo());
                    
                    if(mensajeProcedure.equals("exito")){
                        
                        codigoN = super.getRestulSQLgetCodigoPPTOsinRequerimiento(year,
                            ramo, metaN.getPrograma(), metaN.getTipoProyecto(),
                            metaN.getProyecto(),metaN.getMetaId(),
                            accionN.getAccionId(), accionReq.getPartida(),
                            accionReq.getsFuente(),accionReq.getsFondo(),
                            accionReq.getsRecurso(), accionReq.getRelLaboral());
                        if(codigoN != null){
                            reqId = super.getResultSQLMaxRequerimiento(year, ramo, metaNueva, accionNueva);
                            /* Se inserta el requerimiento con la fuente de financiamiento nueva */
                            if(accionReq.getGpoGasto() == null || accionReq.getGpoGasto().isEmpty())
                                accionReq.setGpoGasto("0");
                            if(accionReq.getSubGrupo() == null || accionReq.getSubGrupo().isEmpty())
                                accionReq.setSubGrupo("0");
                            resultado = super.getResultSQLInsertRequerimiento(year, ramo, codigoN.getPrograma(),
                                codigoN.getDepto(),metaN.getMetaId(),accionN.getAccionId(),codigoN.getFuente(), codigoN.getTipoGasto(),
                                reqId,accionReq.getDescr(), codigoN.getPartida(), codigoN.getRelLaboral(), accionReq.getCant(),
                                accionReq.getCostoUni(),accionReq.getCostoAn(), accionReq.getEnero(),accionReq.getFebrero(),
                                accionReq.getMarzo(), accionReq.getAbril(), accionReq.getMayo(),accionReq.getJunio(), accionReq.getJulio(),
                                accionReq.getAgosto(), accionReq.getSeptiembre(), accionReq.getOctubre(),accionReq.getNoviembre(),accionReq.getDiciembre(),
                                accionReq.getArticulo(), accionReq.getJustificado(), codigoN.getFondo(), codigoN.getRecurso(),
                                usuario,Integer.parseInt(accionReq.getGpoGasto()), Integer.parseInt(accionReq.getGpoGasto()), accionReq.getsArchivo());

                            if(resultado){
                                /*Se edita el código existente */
                                mensajeProcedure = super.getRestulSQLCallUpdatePresupuesto(year, codigoN.getRamoId(), accionN.getDeptoId(), metaN.getFinalidad(),
                                    metaN.getFuncion(), metaN.getSubfuncion(),codigoN.getProgCONAC() ,codigoN.getPrograma(), codigoN.getTipoProy(),
                                    Integer.parseInt(codigoN.getProyecto()),metaN.getMetaId(), accionN.getAccionId(), codigoN.getPartida(), codigoN.getTipoGasto(),
                                    codigoN.getFuente(),codigoN.getFondo(), codigoN.getRecurso(), codigoN.getMunicipio(), codigoN.getDelegacion(),codigoN.getRelLaboral(),
                                    reqId, accionReq.getEnero(), accionReq.getFebrero(), accionReq.getMarzo(), accionReq.getAbril(),
                                    accionReq.getMayo(),accionReq.getJunio(), accionReq.getJulio(), accionReq.getAgosto(), accionReq.getSeptiembre(), accionReq.getOctubre(),
                                    accionReq.getNoviembre(), accionReq.getDiciembre(), accionReq.getCant(), accionReq.getCostoUni(), "S",
                                    "N",accionReq.getDescr(),String.valueOf(accionReq.getArticulo()),accionReq.getJustificado(), usuario,accionReq.getGpoGasto(),accionReq.getSubGrupo());
                                if(mensajeProcedure.equals("exito"))
                                    if(getResultGetPresuPlantillaByAccion(year,
                                            ramo,metaAct,accionAct,accionReq.getPartida(),
                                            String.valueOf(accionReq.getTipoGasto()),accionReq.getsFuente(),
                                            accionReq.getsFondo(),accionReq.getsRecurso(), accionReq.getRelLaboral())){
                                        resultado = super.getResultSQLUpdatePresupPlantillaByAccionReq(codigoN, year, accionReq.getRamo(),
                                                accionReq.getMeta(), accionReq.getAccion(),accionReq.getPartida(), String.valueOf(accionReq.getTipoGasto()),
                                                accionReq.getsFuente(), accionReq.getsFondo(), accionReq.getsRecurso(), accionReq.getRelLaboral());
                                        if(!resultado)
                                            break;
                                        else
                                            banPlantilla = true;
                                    }                                    
                                else{
                                    resultado = false;
                                    break;
                                }
                            }
                        }else{
                            resultado = false;
                            break;
                        }
                        
                    }else{
                        resultado = false;
                        break;
                    }
                }      
                if(resultado){
                    if(resultado && banPlantilla){
                        if(codigoN != null)
                            resultado = super.getResultSQLUpdateMetaAccionPlantilla(codigoN, year, ramo,
                                    accionAc.getPrg(), accionAc.getDeptoId(), metaAct, accionAct);
                        else
                            resultado = false;
                    }
                    if(resultado)
                        if(super.getResultSQLDeletePPTOenCeroByAccion(year, ramo, metaAct, accionAct))
                            resultado = super.getResultSQLDeleteCodigosByAccion(year, ramo, metaAct, accionAct);
                        else
                            resultado = false;
                }
            }else{
                mensajeProcedure = "La relación meta-acción nueva no existe";
            }
        }catch (SQLException ex) {
            resultado = false;
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora(); 
        }catch (Exception ex) {
            resultado = false;
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        
        return resultado;
    }
    
    public boolean cambiaFuenteFinanciamientoByMetaAccion(int year, String ramo, 
            int meta, int accion, String fuente, String fondo, String recurso,
            String fuenteN,String fondoN, String recursoN, String usuario){
        boolean resultado = false;
        boolean banPlantilla = false;
        
        int reqId = 0;
        
        String mensajeProcedure = new String();
        
        List<AccionReq> accionReqList = new ArrayList<AccionReq>();
        CodigoPPTO codigo;
        
        try{
            accionReqList = super.getResultSQLgetRequerimientosByFuenteFin(year, ramo,meta, accion,fuente,fondo, recurso);
            for(AccionReq accionReq : accionReqList){
                codigo = new CodigoPPTO();
                /*Se obtiene el código por cada requerimiento*/
                codigo = super.getRestulSQLGetCodigoPPTOByReq(year,
                        accionReq.getRamo(), accionReq.getPrg(),
                        accionReq.getRequerimiento(), accionReq.getPartida(),
                        accionReq.getMeta(), accionReq.getAccion());
                    mensajeProcedure = super.getRestulSQLCallUpdatePresupuesto(year, codigo.getRamoId(), codigo.getDepto(), codigo.getFinalidad(),
                        codigo.getFuncion(), codigo.getSubfuncion(),codigo.getProgCONAC() ,codigo.getPrograma(), codigo.getTipoProy(),
                        Integer.parseInt(codigo.getProyecto()),codigo.getMeta(), codigo.getAccion(), codigo.getPartida(), codigo.getTipoGasto(), codigo.getFuente(),
                        codigo.getFondo(), codigo.getRecurso(), codigo.getMunicipio(), codigo.getDelegacion(),codigo.getRelLaboral(),
                        accionReq.getRequerimiento(), accionReq.getEnero(), accionReq.getFebrero(), accionReq.getMarzo(), accionReq.getAbril(),
                        accionReq.getMayo(),accionReq.getJunio(), accionReq.getJulio(), accionReq.getAgosto(), accionReq.getSeptiembre(), accionReq.getOctubre(),
                        accionReq.getNoviembre(), accionReq.getDiciembre(), accionReq.getCant(), accionReq.getCostoUni(), "N",
                        "S",accionReq.getDescr(),String.valueOf(accionReq.getArticulo()),accionReq.getJustificado(), usuario,accionReq.getGpoGasto(),accionReq.getSubGrupo());
                    
                    if(mensajeProcedure.equals("exito")){
                        reqId = super.getResultSQLMaxRequerimiento(year, ramo, meta, accion);
                        /* Se inserta el requerimiento con la fuente de financiamiento nueva */
                        if(accionReq.getGpoGasto() == null || accionReq.getGpoGasto().isEmpty())
                            accionReq.setGpoGasto("0");
                        if(accionReq.getSubGrupo() == null || accionReq.getSubGrupo().isEmpty())
                            accionReq.setSubGrupo("0");
                        resultado = super.getResultSQLInsertRequerimiento(year, accionReq.getRamo(), accionReq.getPrg(),
                                accionReq.getDepto(), accionReq.getMeta(),accionReq.getAccion(),fuenteN, String.valueOf(accionReq.getTipoGasto()),
                                reqId,accionReq.getDescr(), accionReq.getPartida(), accionReq.getRelLaboral(), accionReq.getCant(),
                                accionReq.getCostoUni(),accionReq.getCostoAn(), accionReq.getEnero(),accionReq.getFebrero(),
                                accionReq.getMarzo(), accionReq.getAbril(), accionReq.getMayo(),accionReq.getJunio(), accionReq.getJulio(),
                                accionReq.getAgosto(), accionReq.getSeptiembre(), accionReq.getOctubre(),accionReq.getNoviembre(),accionReq.getDiciembre(),
                                accionReq.getArticulo(), accionReq.getJustificado(), fondoN, recursoN, usuario,
                                Integer.parseInt(accionReq.getGpoGasto()), Integer.parseInt(accionReq.getGpoGasto()),accionReq.getsArchivo());
                        
                        if(resultado){
                            /*Se edita el código existente */
                            mensajeProcedure = super.getRestulSQLCallUpdatePresupuesto(year, codigo.getRamoId(), codigo.getDepto(), codigo.getFinalidad(),
                                codigo.getFuncion(), codigo.getSubfuncion(),codigo.getProgCONAC() ,codigo.getPrograma(), codigo.getTipoProy(),
                                Integer.parseInt(codigo.getProyecto()),codigo.getMeta(), codigo.getAccion(), codigo.getPartida(), codigo.getTipoGasto(),
                                fuenteN,fondoN, recursoN, codigo.getMunicipio(), codigo.getDelegacion(),codigo.getRelLaboral(),
                                reqId, accionReq.getEnero(), accionReq.getFebrero(), accionReq.getMarzo(), accionReq.getAbril(),
                                accionReq.getMayo(),accionReq.getJunio(), accionReq.getJulio(), accionReq.getAgosto(), accionReq.getSeptiembre(), accionReq.getOctubre(),
                                accionReq.getNoviembre(), accionReq.getDiciembre(), accionReq.getCant(), accionReq.getCostoUni(), "S",
                                "N",accionReq.getDescr(),String.valueOf(accionReq.getArticulo()),accionReq.getJustificado(), usuario,accionReq.getGpoGasto(),accionReq.getSubGrupo());
                            if(mensajeProcedure.equals("exito"))
                                if(getResultGetPresuPlantillaByAccion(year,
                                        ramo,meta,accion,accionReq.getPartida(),
                                        String.valueOf(accionReq.getTipoGasto()),accionReq.getsFuente(),
                                        accionReq.getsFondo(),accionReq.getsRecurso(), accionReq.getRelLaboral())){
                                    resultado = super.getResultSQLUpdatePresupPlantillaByAccionReqFF(year, accionReq.getRamo(),
                                            accionReq.getMeta(), accionReq.getAccion(),accionReq.getPartida(),String.valueOf(accionReq.getTipoGasto()),
                                            accionReq.getsFuente(),accionReq.getsFondo(), accionReq.getsRecurso(),accionReq.getRelLaboral(),
                                            fuenteN,fondoN,recursoN);
                                    if(!resultado)
                                        break;
                                    else
                                        banPlantilla = true;
                                }  
                        }else{
                            resultado = false;
                            break;
                        }
                        
                    }else{
                        resultado = false;
                        break;
                    }                
            }
        }catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora(); 
        }catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        
        return resultado;
    }
    
}
