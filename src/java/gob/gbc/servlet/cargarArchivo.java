/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.servlet;

import gob.gbc.aplicacion.CargaCodigoBean;
import gob.gbc.aplicacion.Mes;
import gob.gbc.entidades.Archivo;
import gob.gbc.entidades.Ppto;
import gob.gbc.util.Bitacora;
import gob.gbc.util.DatosUpload;
import gob.gbc.util.UtilUploads;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

/**
 *
 * @author ugarcia
 */
public class cargarArchivo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String usuario = new String();
        String codigoStr = new String();
        String tipoDependencia = new String();
        String strRutaFile = new String();
        String badCode = new String();
        String ramo = new String();
        String msjProcedure = new String();
        String partidaPlantilla = new String();
        String mensaje = new String();
        int year = 0;
        int codRepetido = -2;
        String pptoTemp = new String();
        List<Archivo> archivoList = new ArrayList<Archivo>();
        List<Ppto> presupList = new ArrayList<Ppto>();
        List<Mes> mesAsignad = new ArrayList<Mes>();
        //List<Ppto> reqPpto = new ArrayList<Ppto>();
        JSONObject json = null;
        boolean resultado = false;
        boolean bandera = true;
        double total = 0.0;
        List<Ppto> codigoPpto = new ArrayList<Ppto>();
        CargaCodigoBean cargaBean = null;
        Bitacora bitacora = new Bitacora();
        XSSFWorkbook libroXLS;
        String origen = new String();
        boolean isParaestatal = false;
        boolean validaRamo = false;
        File codigo = null;
        HttpSession session = null;
        RequestDispatcher dispatcher = request.getRequestDispatcher("cargaPlantillaCodigos.jsp");
        //String strHTML = "<HTML><body onLoad='redirect(cargaPlantillaCodigos.jsp)'></body></HTML>";
        try {
            session = request.getSession();
            if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }
            if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
                usuario = (String) session.getAttribute("strUsuario");
            }
            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
            }
            cargaBean = new CargaCodigoBean(tipoDependencia);
            DatosUpload datos = null;
            UtilUploads upload = new UtilUploads();
            cargaBean.setStrServer(((String) request.getHeader("Host")));
            cargaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            cargaBean.resultSQLConecta(tipoDependencia);
            upload.setStrServer(request.getHeader("host"));
            upload.setStrUbicacion(getServletContext().getRealPath("").toString());
            upload.setStrDirectorioTemp(getServletContext().getRealPath("Documentos"));
            datos = upload.getDatos(request);
            if (cargaBean.isParaestatal()) {
                ramo = (String) datos.getParametros().get("selRamo");
            }
            archivoList = datos.getArchivos();
            isParaestatal = cargaBean.isParaestatal();
            for (int it = 0; it < archivoList.size(); it++) {
                strRutaFile = getServletContext().getRealPath("").toString()
                        + File.separatorChar + "temp" + File.separatorChar;
                File fTemp = new File(strRutaFile);
                if (!fTemp.isDirectory()) {
                    fTemp.mkdirs();
                }
                strRutaFile += "codigo-" + usuario + ".xlsx";
                FileOutputStream outFile = new FileOutputStream(strRutaFile);
                outFile.write(archivoList.get(it).getContenido());
                bitacora.setStrUbicacion(cargaBean.getStrUbicacion());
                bitacora.setStrServer(cargaBean.getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(strRutaFile);
                bitacora.grabaBitacora();
                outFile.close();
            }
            codigo = new File(strRutaFile);
            libroXLS = new XSSFWorkbook(new FileInputStream(codigo));
            System.out.println("1" + new Date().toString());

            codigoPpto = cargaBean.validaPartidaPlantilla(libroXLS, year, ramo);
            if (codigoPpto.size() > 0) {
                validaRamo = true;
            }
            System.out.println("2 " + new Date().toString());
            if (cargaBean.bError.isEmpty()) {/*                 */

                System.out.println("DESPUES DE PROCESAR EXCEL " + new Date().toString());
                if (validaRamo && cargaBean.bError.isEmpty()) {
                    if (codigoPpto != null) {
                        origen = codigoPpto.get(0).getOrigen().trim();
                        if (cargaBean.borrarCargaPlantilla(libroXLS, ramo, year, usuario)) {
                            System.out.println("borró");

                            System.out.println("ANTES DE CARGA DATOS " + new Date().toString());
                            mensaje = cargaBean.cargaDatosArchivo(codigoPpto);

                            //AQUI YA EXISTE PRESUP_PLANTILLA
                            System.out.println("DESPUES DE CARGA DATOS " + new Date().toString());
                            if (mensaje.isEmpty()) {
                                presupList = cargaBean.getGruposPresupPlantilla(origen);
                                System.out.println("PRE CICLO " + new Date().toString());
                                /*CARGA EN TABLAS */
                                int iRow = 0;
                                //SE MOVIO
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
                                                    json = new JSONObject();
                                                    json.put("resultado", "-1");
                                                    json.put("mensaje", "Ocurrió un error al cargar la información en ACCION_REQ");
                                                }
                                            } else {
                                                cargaBean.transaccionRollback();
                                                bandera = false;
                                                json = new JSONObject();
                                                json.put("resultado", "-1");
                                                json.put("mensaje", "Existe un registro sin dinero asignado para el código: " + pptoTemp);
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
                                            cargaBean.deletePresupPlantilla();
                                            json = new JSONObject();
                                            json.put("resultado", "-2");
                                            json.put("mensaje", "Hay un código no existente en el archivo");
                                            json.put("badCode", badCode);
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
                                            json = new JSONObject();
                                            json.put("resultado", "-2");
                                            json.put("mensaje", "No se logro actualizar PPTO ");
                                            json.put("badCode", badCode);
                                        }
                                    }
                                    /* if (bandera)
                                     cargaBean.transaccionCommit();//REVISADO*/
                                } else {
                                    cargaBean.transaccionRollback();
                                    bandera = false;
                                    json = new JSONObject();
                                    json.put("resultado", "-1");
                                    cargaBean.deletePresupPlantilla();
                                    String sMensajeExtra = "";
                                    if(!cargaBean.bError.isEmpty()){
                                        sMensajeExtra = cargaBean.bError;
                                    }
                                    if (!sInvalido.equals("exito")) {
                                        sMensajeExtra += sInvalido;
                                    } else if (!bFaltantes) {
                                        sMensajeExtra += "al grabar en CODIGOS";
                                    } else if (!bPptoFaltantes) {
                                        sMensajeExtra += "al grabar en PPTO";
                                    }
                                    json.put("mensaje", "Ocurrió un error al cargar el archivo " + sMensajeExtra);
                                }/*CARGA DE TABLAS */

                            } else {
                                cargaBean.transaccionRollback();
                                bandera = false;
                                json = new JSONObject();
                                json.put("resultado", "-1");
                                cargaBean.deletePresupPlantilla();
                                if (mensaje.contains("ORA-01400")) {
                                    if (mensaje.indexOf("|") > 0) {
                                        codigoStr = mensaje.substring(mensaje.indexOf("|") + 1);
                                    }
                                    mensaje = "El archivo excel contiene celdas vacias. Código: ";
                                    mensaje += codigoStr;
                                }
                                if (mensaje.contains("ORA-00001")) {
                                    if (mensaje.indexOf("|") > 0) {
                                        codigoStr = mensaje.substring(mensaje.indexOf("|") + 1);
                                    }
                                    mensaje = "El archivo en excel contiene datos repetidos. Código: ";
                                    mensaje += codigoStr;
                                }
                                json.put("mensaje", mensaje);
                            }
                        } else {
                            cargaBean.transaccionRollback();
                            bandera = false;
                            json = new JSONObject();
                            json.put("resultado", "-1");
                            cargaBean.deletePresupPlantilla();
                            json.put("mensaje", "No se pudo borrar la información actual de la plantilla.");
                        }
                        if (bandera) {
                            cargaBean.getConectaBD().transaccionCommit();//revisado
                            cargaBean.updateEstatusPresupPlantilla();
                            json = new JSONObject();
                            json.put("resultado", "0");
                            json.put("mensaje", "Los datos se cargaron exitosamente");
                        } else {
                            cargaBean.getConectaBD().transaccionRollback();
                        }
                    } else {//RELACION
                        cargaBean.transaccionRollback();
                        json = new JSONObject();
                        json.put("resultado", "-1");
                        json.put("mensaje", "La relación año-ramo-programa-depto "
                                + "que se cargó en el archivo no concuerda con la plantilla");
                    }
                } else {//ramo invalido
                    cargaBean.transaccionRollback();
                    json = new JSONObject();
                    json.put("resultado", "-1");
                    if (!cargaBean.bError.isEmpty()) {
                        json.put("mensaje", cargaBean.bError);
                    } else {
                        json.put("mensaje", "Uno de los ramos encontrados en el archivo no concuerda con el ramo seleccionado");
                    }
                }
            } else {//NO HAY INFO
                bandera = false;
                cargaBean.transaccionRollback();
                json = new JSONObject();
                json.put("resultado", "-1");
                json.put("mensaje", cargaBean.bError);
            }
        } catch (Exception e) {
            cargaBean.transaccionRollback();
            bitacora.setStrUbicacion(cargaBean.getStrUbicacion());
            bitacora.setStrServer(cargaBean.getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, new Throwable());
            bitacora.grabaBitacora();
            json = new JSONObject();
            json.put("resultado", "-1");
            if (e.getMessage().equals("Cannot get a text value from a numeric cell")) {
                json.put("mensaje", "No se puede obtener un valor de texto de una celda numérica a partir del renglon " + cargaBean.renglonError);
            } else {
                json.put("mensaje", e.getMessage());
            }
        } finally {
            if (codigo != null) {
                codigo.delete();
            }
            session.setAttribute("selRamo", ramo);
            session.setAttribute("resultadoCarga", json);
            //response.sendRedirect("cargaPlantillaCodigos.jsp");
            cargaBean.resultSQLDesconecta();
            //out.print(strHTML);
            dispatcher.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
