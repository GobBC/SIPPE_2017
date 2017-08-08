package gob.gbc.estandar;

import gob.gbc.estandar.Usuarios;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Utilerias;

public class FirmaUsuario extends HttpServlet {

    private Bitacora bitacora;

    public Bitacora getBitacora() {
        return bitacora;
    }

    public void setBitacora(Bitacora bitacora) {
        this.bitacora = bitacora;
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String sCveUsuario = "";
        String sContrasena = "";
        Usuarios datosUsuario = new Usuarios();
        ResultSQLEstandar sql = null;
        try {
            if (Utilerias.existeParametro("sCveUsuario", request)) {
                sCveUsuario = new String(request.getParameter("sCveUsuario").getBytes("ISO-8859-1"), "UTF-8");
                sCveUsuario = sCveUsuario.toUpperCase();
            }
            if (Utilerias.existeParametro("sContrasena", request)) {
                sContrasena = new String(request.getParameter("sContrasena").getBytes("ISO-8859-1"), "UTF-8");
                sContrasena = sContrasena.toUpperCase();
            }

            setBitacora(new Bitacora());
            datosUsuario.setsCveUsuario(sCveUsuario.toUpperCase());
            datosUsuario.setsPassword(sContrasena.toUpperCase());
            boolean bExisteEnte = false;

            sql = new ResultSQLEstandar();


            sql.setStrServer(request.getHeader("host"));
            sql.setStrUbicacion(getServletContext().getRealPath("").toString());
            sql.resultSQLConecta(sql.getsConexionBD());
            //datosUsuario.setBd(sql.getConectaBD());

            datosUsuario = sql.getConsultaUsuarios(sCveUsuario, sContrasena);

            if (datosUsuario != null) {
                HttpSession sessionUsuario = request.getSession(true);

                if (datosUsuario.getsActivo().compareTo("S") == 0) {//activo 

                    sessionUsuario.setAttribute("datosUsuario", datosUsuario);
                    response.sendRedirect("menuPrincipal.jsp");
                } else {
                    generaMensaje(response, 4, ""); //usuario inactivo
                }
            } else {
                generaMensaje(response, 3, sCveUsuario);   //contrasena incorrecta         
            }



        } catch (Exception ex) {
            getBitacora().setStrUbicacion(getServletContext().getRealPath("").toString());
            getBitacora().setStrServer(((String) request.getHeader("Host")));
            getBitacora().setStrInformacion(ex, request.getServletPath());
            getBitacora().setITipoBitacora(1);
            getBitacora().grabaBitacora();
        } finally {
            if (sql != null) {
                sql.resultSQLDesconecta();
            }
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Servlet de validación de usuario";
    }

    public void generaMensaje(HttpServletResponse response, int opcion, String sCadenaAuxiliar) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-store");
        response.setHeader("expires", "0");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
            out.println("<title>GOBIERNO DEL ESTADO DE BAJA CALIFORNIA</title>");
            out.println("<link href='librerias/css/portal.css' type='text/css' rel='stylesheet'/>");
            out.println("<link type='text/css' rel='stylesheet' href='librerias/css/estilos.css'>");
            out.println("<link href='librerias/css/estilo.css' type='text/css' rel='stylesheet'/>");

            out.println("<script src='librerias/js/jquery/jquery.js' type='text/javascript'></script>");
            out.println("<link href='librerias/js/jquery/tabla/tabla.css' type='text/css' rel='stylesheet'/>");
            out.println("<script src='librerias/js/jquery/tabla/jquery.tabla.js' type='text/javascript'></script>");
            out.println("<script src='librerias/js/mensajes.js' type='text/javascript'></script>");
            out.println("<script src='librerias/js/funciones.js' type='text/javascript'></script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<header class='header'>");
            out.println("<img class=\"header\" src=\"imagenes/banner_contabilidad.jpg\">");
            out.println("</header>");

            out.println("<div class='contenidoPrincipal'>");
            out.println("<section class='contenido-index'>");
            out.println("<br/>");
            out.println("<div class='formulario-index'>");




            switch (opcion) {
                case 1:
                    // out.println("<form name='form1' method='post' action='"+datosEstilo.getSPaginaInicio(sCadenaAuxiliar)+"'>");
                    out.println("</form>");
                    out.println("<script language='javascript'>document.form1.submit();</script>");
                    break;
                case 2:
                    out.println("<form name='form1' method='post' action='nuevoPassword.jsp'>");
                    out.println("</form>");
                    out.println("<script language='javascript'>document.form1.submit();</script>");
                    break;
                case 3:
                    out.println("<form name='form1' method='post' action='inicioSesion.jsp'>");
                    out.println("<input type='hidden' name='sMensaje' value='Usuario y Contrasentildea no coinciden. <br /> Por favor verifique.' />");
                    out.println("<input type='hidden' name='sCveUsuario' value='" + sCadenaAuxiliar + "' />");
                    out.println("</form>");
                    out.println("<script language='javascript'>document.form1.submit();</script>");
                    break;
                /*case 4:
                 out.println("<form name='form1' method='post' action='inicioSesion.jsp'>");
                 out.println("<center>");
                 out.println("<br />");
                 out.println("<span class='error'> Usuario Inactivo. <br /> <br />Por favor contacte a su administrador de sistema.</span>");
                 out.println("<br /><br />");
                 out.println("<span class='error'> Si usted acaba de realizar su registro no olvide verificar su correo electr&oacute;nico para activar su cuenta.");
                 out.println("<br />");
                 out.println("<br />");
                 out.println("<input type='submit' name='aceptar' value='Aceptar' class='submheadaz' />");
                 out.println("<br />");
                 out.println("</center>");
                 out.println("</form>");
                 break;*/
            }
            out.println("</section>");
            out.println("<footer>");
            out.println("<img class=\"footer\"  src=\"imagenes/footer.jpg\"> ");
            out.println("</footer>");
            out.println("</div>");


            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }
    // </editor-fold>
}