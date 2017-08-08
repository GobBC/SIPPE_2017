<%-- 
    Document   : ajaxCargaPlantillaCentral
    Created on : Oct 14, 2015, 4:14:13 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.Mes"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Ppto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.CargaCodigoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String tipoDependencia = new String();
    //String usuario = new String();
    int presup = 0;
    String ramo = new String();
    String strResultado = new String();
    String pptoTemp = new String();
    String descrOrigen = new String();
    String origen = new String();
    String mensaje = new String();
    String badCode = new String();
    String codigoStr = new  String();
    boolean resultado = false;
    boolean bandera = true;
    List<Ppto> presupList = new ArrayList<Ppto>();
    List<Mes> mesAsignad = new ArrayList<Mes>();
    int year = 0;
    double total = 0.0;
    CargaCodigoBean cargaBean = null;
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("origen") != null
                && !request.getParameter("origen").equals("")) {
            origen = (String) request.getParameter("origen");
            if(origen.equals("SIRHB"))
                descrOrigen = "Burocracia";
            else if(origen.endsWith("SIRHM"))
                descrOrigen = "Magisterio";
        }
        cargaBean = new CargaCodigoBean(tipoDependencia);
        cargaBean.setStrServer(request.getHeader("host"));
        cargaBean.setStrUbicacion(getServletContext().getRealPath(""));
        cargaBean.resultSQLConecta(tipoDependencia);
        presup = cargaBean.getCountPresupPlantill(year, ramo, origen);
        if(presup < 1){
            mensaje = "No hay datos de plantilla para cargar en " + descrOrigen;
        }
        if (mensaje.isEmpty()) {
            presupList = cargaBean.getGruposPresupPlantilla(origen);
            System.out.println("PRE CICLO " + new Date().toString());
            /*CARGA EN TABLAS */
            int iRow = 0;
            String sInvalido = "";
            boolean bFaltantes = false, bPptoFaltantes = false;
            resultado = cargaBean.isAccionRegistrada(year, ramo, origen);
            if (resultado) {
                bFaltantes = cargaBean.insertaCodigosFaltantes(year, ramo, origen);
            }
            if (resultado && bFaltantes) {
                bPptoFaltantes = cargaBean.insertoPPTOFaltantes(year, ramo, origen);
            }
            if (bFaltantes && resultado && bPptoFaltantes) {
                sInvalido = cargaBean.callValidaArchivo(year, ramo, origen);
            }
            if (presupList.size() > 0 && resultado && sInvalido.equals("exito") && bFaltantes && bPptoFaltantes) {
                for (Ppto pres : presupList) {
                    iRow++;
                    if (iRow % 50 == 0) {
                        System.out.println("CICLO " + new Date().toString());
                    }
                    if (pres != null) {
                        mesAsignad = new ArrayList<Mes>();
                        mesAsignad = pres.getdMeses();
                        pptoTemp = pres.getPrg() + "-" + pres.getDepto() + "-" + pres.getMetaId() + "-" + pres.getPartida() + "-" + pres.getFuente() + "-" + pres.getFondo() + "-" + pres.getRecurso() + "-" + pres.getRelLaboral();
                        for (Mes mes : mesAsignad) {
                            total += mes.getdImporte();
                        }
                        if (total > 0) {
                            resultado = cargaBean.insertRequerimientoPresupPlantilla(pres);
                            if (resultado) {
                                resultado = true;
                                int iCont = 0;
                                //Mes pptoList;                                                                            
                            } else {
                                cargaBean.transaccionRollback();
                                bandera = false;
                                strResultado += "0 | Ocurri&oacute; un error al cargar la informaci&oacute;n en ACCION_REQ";
                            }
                        } else {
                            cargaBean.transaccionRollback();
                            bandera = false;
                            strResultado += "0 | Existe un registro sin dinero asignado para el c&oacute;digo: " + pptoTemp;
                            break;
                        }

                        /*} else {
                         badCode = pres.getYear() + "-" + pres.getRamo() + "-" + pres.getDepto() + "-"
                         + pres.getFinalidad() + "-" + pres.getFuncion() + "-" + pres.getSubfuncion() + "-" + pres.getPrgConac() + "-" + pres.getPrg() + "-"
                         + pres.getTipoProy() + "-" + pres.getProyecto() + "-" + pres.getMeta() + "-" + pres.getAccion() + "-" + pres.getPartida() + "-"
                         + pres.getTipoGasto() + "-" + pres.getFuente() + "-" + pres.getFondo() + "-" + pres.getRecurso() + "-" + pres.getMunicipio() + "-"
                         + pres.getDelegacion() + "-" + pres.getRelLaboral();
                         System.out.println(badCode + "**");
                         }*/
                    } else {
                        cargaBean.getConectaBD().transaccionRollback();
                        bandera = false;
                        badCode = pres.getYear() + "-" + pres.getRamo() + "-" + pres.getPrgConac() + "-" + pres.getPrg() + "-" + pres.getDepto() + "-"
                                + pres.getFinalidad() + "-" + pres.getFuncion() + "-" + pres.getSubfuncion() + "-" + pres.getPrgConac() + "-" + pres.getPrg() + "-"
                                + pres.getTipoProy() + "-" + pres.getProyecto() + "-" + pres.getMeta() + "-" + pres.getAccion() + "-" + pres.getPartida() + "-"
                                + pres.getTipoGasto() + "-" + pres.getFuente() + "-" + pres.getFondo() + "-" + pres.getRecurso() + "-" + pres.getMunicipio() + "-"
                                + pres.getDelegacion() + "-" + pres.getRelLaboral();
                        strResultado += "0 | Hay un c&oacute;digo no existente en el archivo | " + badCode;
                        break;
                    }
                }
                if (bandera) {
                    String sError = "";
                    sError = cargaBean.actualizaPPTO(year, ramo, origen, true);
                    if (sError.equals("exito")) {
                        bandera = true;
                        System.out.println("TERMINO " + new Date().toString());
                    } else {
                        bandera = false;
                        strResultado += "0 | No se logro actualizar PPTO | " + badCode;
                    }
                }
                /* if (bandera)
                 cargaBean.transaccionCommit();//REVISADO*/
            } else {
                cargaBean.transaccionRollback();
                bandera = false;
                String sMensajeExtra = "";
                if (!sInvalido.equals("exito")) {
                    sMensajeExtra += sInvalido;
                } else if (!bFaltantes) {
                    sMensajeExtra += "al grabar en CODIGOS";
                } else if (!bPptoFaltantes) {
                    sMensajeExtra += "al grabar en PPTO";
                }
                strResultado += "0 | Ocurri&oacute; un error al cargar el archivo " + sMensajeExtra;
            }/*CARGA DE TABLAS */

        } else {
            cargaBean.transaccionRollback();
            bandera = false;
            if (mensaje.contains("ORA-01400")) {
                if (mensaje.indexOf("|") > 0) {
                    codigoStr = mensaje.substring(mensaje.indexOf("|") + 1);
                }
                mensaje = "El archivo excel contiene celdas vac&iacute;as. C&oacute;digo: ";
                mensaje += codigoStr;
            }
            if (mensaje.contains("ORA-00001")) {
                if (mensaje.indexOf("|") > 0) {
                    codigoStr = mensaje.substring(mensaje.indexOf("|") + 1);
                }
                mensaje = "El archivo en excel contiene datos repetidos. C&oacute;digo: ";
                mensaje += codigoStr;
            }
            strResultado += "0 | " + mensaje;
        }
        if (bandera) {
            cargaBean.updateEstatusPresupPlantilla();            
            cargaBean.getConectaBD().transaccionCommit();//revisado
            strResultado += "1 | Los datos se cargaron exitosamente";
        } else {
            cargaBean.getConectaBD().transaccionRollback();
        }
    }catch(Exception ex){
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
        strResultado += "0 | Sucedió un error no identificado";
        cargaBean.getConectaBD().transaccionRollback();
    }finally{
        if(cargaBean != null)
            cargaBean.resultSQLDesconecta();
        out.print(strResultado);
    }
%>
