<%-- 
    Document   : ajaxGetProgramasByRamo
    Created on : Mar 20, 2015, 4:08:07 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.FolioMonitoreoPresup"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.aplicacion.ProgramaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  
    String strResultado = new String();
    String ramoId = new String();
    boolean cierre = false;
    ProgramaBean programaBean = null;
    int year = 0;
    int op = 0;
    int periodoIni=0;
    int periodoFin=0;
    int contReg1 = 0;
    int contRegSelect1 = 0;
    String msjSelect1 = "Seleccionados";
    String rol = new String();
    String usuario = new String();
    String tipoDependencia = new String();
    List<FolioMonitoreoPresup> folioList = new ArrayList<FolioMonitoreoPresup>();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try{
        
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != ""){
            usuario = (String)session.getAttribute("strUsuario");
        }
        if ((String) session.getAttribute("strRol") != null) {
            rol = (String) session.getAttribute("strRol");
        }
        programaBean = new ProgramaBean(tipoDependencia);
        programaBean.setStrServer(request.getHeader("host"));
        programaBean.setStrUbicacion(getServletContext().getRealPath(""));
        programaBean.resultSQLConecta(tipoDependencia);
        
         if(request.getParameter("periodoIni") != null && request.getParameter("periodoIni") != ""){
            periodoIni =Integer.parseInt((String) request.getParameter("periodoIni"));
        }
        
        if(request.getParameter("periodoFin") != null && request.getParameter("periodoFin") != ""){
            periodoFin =Integer.parseInt((String) request.getParameter("periodoFin"));
        }
        
        
        if(request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")){
             ramoId = (String)request.getParameter("ramo");
        }
        if(request.getParameter("ramos") != null && !request.getParameter("ramos").equals("")){
             ramoId = (String)request.getParameter("ramos");
             ramoId = ramoId.replace("'", "");
        }
        if(request.getParameter("op") != null && !request.getParameter("op").equals("")){
             op = Integer.parseInt((String)request.getParameter("op"));
        }
        if(!ramoId.equals("-1")){
            
              folioList=programaBean.getResultSQLconsultaFolioMonitoreoPresup(op, year, periodoIni, periodoFin, ramoId);
              contReg1 = folioList.size();
        contRegSelect1 = contReg1;
        int RegTemp1 = 0;
        strResultado +="<div id='divCombo' class='comboRamos'>";
        strResultado +="<label> Folio:</label>";
        strResultado +="<ul>";
        strResultado +="<li><div id='comboSelectRep' name='comboSelectRep' ><input id='labelCont1' name='labelCont1' type='Text' value='"+contReg1+" "+msjSelect1+"' disabled='true'/><img id='dropDown' name='dropDown' src='imagenes/OpenArrow.png' ></div>";
        strResultado +="<ul>";
        strResultado +=" <li>";
        strResultado +=" <div id='divFolios' name='divFolios'  >";
        strResultado +=" <input type='checkbox' id='allChecks1' name='allChecks1' onclick='allChecksRptFoliosRepMonPresup()' checked='true'/>Todos/Ninguno ";
        strResultado +=" <table>";
        for (FolioMonitoreoPresup folio : folioList) {
            RegTemp1++;
            strResultado +="<tr>";
            strResultado+="<td align='left' >";
            strResultado +="<input id='checkk" + RegTemp1 + "' name='checkk" + RegTemp1 + "' type='checkbox' onclick='contCheckRptFolioRepMonPresup(" + RegTemp1 + ")' checked='true' />";
            strResultado +="<input id='folioCheck" + RegTemp1 + "' name='folioCheck" + RegTemp1 + "' type='hidden'  value='" + folio.getFolio() + "' />";
            strResultado +="</td>";
            strResultado +="<td align='left'>"+folio.getFolio()+"</td>";
            strResultado +="</tr>";
        }
        
         strResultado +="  </table>";
         strResultado +="    </div>";
         strResultado +="    </li> ";
         strResultado +="    </ul> ";
         strResultado +="    </li> ";
         strResultado +="    </ul> ";
         strResultado +="   </div> ";
        
        
        
        
         strResultado +="<input id='contReg1' name='contReg1' type='hidden' value='"+contReg1+"'/>";
         strResultado +="<input id='contRegSelect1' name='contRegSelect1' type='hidden' value='"+contRegSelect1+"' />";
        } 
        
     }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (programaBean != null) {
            programaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>