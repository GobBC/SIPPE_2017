/*
 * ResultSQLEstandar.java
 *
 * Created on 18 de mayo de 2009
 *
 */
package gob.gbc.estandar;

import gob.gbc.bd.ConectaBD;
import gob.gbc.bd.DatosBdConfig;
import gob.gbc.estandar.Usuarios;
import gob.gbc.estandar.Proceso;
import gob.gbc.mail.clases.Configuraciones;
import gob.gbc.util.Bitacora;
import gob.gbc.ws.client.email.Archivo;
import gob.gbc.ws.client.email.Resultado;
import gob.gbc.mail.clases.ConfiguracionCorreo;
import gob.gbc.util.ResourceLoader;
import gob.gbc.ws.client.email.Correo;
import gob.gbc.ws.client.email.Email_PortType;
import gob.gbc.ws.client.email.Email_ServiceLocator;
import gob.gbc.ws.client.email.Usuario;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class ResultSQLEstandar {

    private String strServer = "";
    private String strUbicacion = "";
    private Bitacora bitacora;
    private ConectaBD conectaBD;
    private QueryEstandar query;
    private int iRegistrosXPagina = 15;
    private int iPaginaInicio = 1;
    private int iPaginaFinal = (iPaginaInicio + iRegistrosXPagina) - 1;
    /*Constantes para menu*/
    private final short IN_OPCION_INHABILITADA = 0;
    private final short IN_TIPO_OPCION_BOTON = 2;
    private final short IN_TIPO_OPCION_LINK = 1;
    private String sConexionBD = "";
    private DatosBdConfig configBd;
    private ConfiguracionCorreo configuracionCorreo;
    /*Constantes para menu termina*/

    /**
     * Creates a new instance of ResultSQLEstandar
     */
    public ResultSQLEstandar() throws Exception {
        conectaBD = new ConectaBD();
        bitacora = new Bitacora();
        query = new QueryEstandar();
        query.setsEsquema(conectaBD.getsEsquema());
        //SI HUBIERA VARIAS CONEXIONES ASIGNARLAS AQUI
        setsConexionBD(conectaBD.getsConexionBD());
    }

    public String getStrServer() {
        return strServer;
    }

    public void setStrServer(String strServer) {
        this.strServer = strServer;
    }

    public String getStrUbicacion() {
        return strUbicacion;
    }

    public void setStrUbicacion(String strUbicacion) {
        this.strUbicacion = strUbicacion;
    }

    public boolean esProduccion() {
        getConectaBD().setStrServer(getStrServer());
        getConectaBD().setStrUbicacion(getStrUbicacion());
        return getConectaBD().esProduccion();
    }

    public void resultSQLConecta(String strBd) {
        getConectaBD().setStrServer(getStrServer());
        getConectaBD().setStrUbicacion(getStrUbicacion());
        try {
            getConectaBD().conecta(strBd);
            /*    Resultado resultado = null;
             Service1 servicio = new Service1();
             Service1Soap timbrado = servicio.getService1Soap();
             resultado = timbrado.timbrar(new Peticion(), "", true);*/
        } catch (Exception e) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, new Throwable());
            bitacora.grabaBitacora();

        }
    }

    public void resultSQLDesconecta() {
        try {
            getConectaBD().desconecta();
        } catch (Exception e) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, new Throwable());
            bitacora.grabaBitacora();
        }
    }

    public void transaccionCommit() {
        try {
            getConectaBD().transaccionCommit();//revisado
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void transaccionRollback() {
        try {
            getConectaBD().transaccionRollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void grabaBitacoraError(Exception ex, Throwable thThrowable) throws IOException {
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(getStrServer());
        bitacora.setStrUbicacion(getStrUbicacion());
        bitacora.setITipoBitacora(Bitacora.TIPO_ERROR);
        bitacora.setStrInformacion(ex, thThrowable);
        bitacora.grabaBitacora();
    }

    public String desconectaDblink(List<String> arDesconecta) {
        JSONObject resultado = null;
        CallableStatement cstmt = null;

        if (getConectaBD() != null) {
            try {
                for (int i = 0; i < arDesconecta.size(); i++) {
                    cstmt = getConectaBD().prepareCall(query.getSQLDesconectaDblink());
                    if (cstmt != null) {
                        cstmt.setString(1, arDesconecta.get(i));
                        cstmt.execute();
                    }
                }

            } catch (Exception ex) {
                return "Ocurrio un error al cerrar las conexiones";
            }

        }
        return "";
    }

    public String desconectaDblink(String arDesconecta) {
        JSONObject resultado = null;
        CallableStatement cstmt = null;

        if (getConectaBD() != null) {
            try {
                //for (int i = 0; i < arDesconecta.size(); i++) {
                cstmt = getConectaBD().prepareCall(query.getSQLDesconectaDblink());
                if (cstmt != null) {
                    cstmt.setString(1, arDesconecta);
                    cstmt.execute();
                    //   }
                }

            } catch (Exception ex) {
                return "Ocurrio un error al cerrar las conexiones";
            }

        }
        return "";
    }

    /**
     *
     * @param parametros
     *
     * @return
     */
    public Hashtable resulGetParametros(List<Integer> parametros) throws Exception {
        Hashtable htParametros = null;

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            ResultSet rsResultado = null;

            try {

                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLObtenerParametros(parametros.size()));

                for (int i = 0; i < parametros.size(); i++) {
                    pstmt.setObject(i + 1, parametros.get(i));
                }

                rsResultado = pstmt.executeQuery();

                if (rsResultado != null) {
                    htParametros = new Hashtable();
                    while (rsResultado.next()) {
                        htParametros.put(String.valueOf(rsResultado.getInt(1)), rsResultado.getString(2));
                    }
                }

            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                try {
                    if (rsResultado != null) {
                        rsResultado.close();
                    }
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }

        return htParametros;
    }

    /**
     * @return the conectaBD
     */
    public ConectaBD getConectaBD() {
        return conectaBD;
    }

    /**
     * @param conectaBD the conectaBD to set
     */
    public void setConectaBD(ConectaBD conectaBD) {
        this.conectaBD = conectaBD;
    }

    /**
     * @return the query
     */
    public QueryEstandar getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(QueryEstandar query) {
        this.query = query;
    }

    public int getiPaginaFinal() {
        return iPaginaFinal;
    }

    public void setiPaginaFinal(int iPaginaFinal) {
        this.iPaginaFinal = iPaginaFinal;
    }

    public int getiPaginaInicio() {
        return iPaginaInicio;
    }

    public void setiPaginaInicio(int iPaginaInicio) {
        this.iPaginaInicio = iPaginaInicio;
    }

    public void setearPaginacion(int iPagina) {
        int tmpInicio = ((iPagina - 1) * getiRegistrosXPagina()) + 1;
        int tmpFin = (tmpInicio + getiRegistrosXPagina()) - 1;
        setiPaginaInicio(tmpInicio);
        setiPaginaFinal(tmpFin);
    }

    public int getiRegistrosXPagina() {
        return iRegistrosXPagina;
    }

    public void setiRegistrosXPagina(int iRegistrosXPagina) {
        this.iRegistrosXPagina = iRegistrosXPagina;
    }

    public boolean actualizaPasswordEmail(String strUsuario, String strPass) throws Exception {
        String query;
        PreparedStatement pstmt = null;
        boolean bExito = false;
        try {
            query = getQuery().getSQLUpdatePass();
            pstmt = getConectaBD().getConConexion().prepareStatement(query);
            if (pstmt != null) {
                pstmt.setString(1, strPass);
                pstmt.setString(2, strUsuario);

                if (pstmt.executeUpdate() > 0) {
                    bExito = true;
                }
            }
        } catch (SQLException ex) {
            grabaBitacoraError(ex, new Throwable());
            throw new Exception(ex.getMessage());

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return bExito;
    }

    public boolean resultInsertaCodigoGenerado(String strRfc, String strHomoclave, String strEmail, String strCodigo) throws SQLException, Exception {
        boolean bResultado = false;
        String strCodigoAnterior = null;

        try {
            strCodigoAnterior = consultaCodigoAnterior(strRfc, strHomoclave, strEmail);

            if (strCodigoAnterior == null) {
                bResultado = insertaCodigoConfirmacion(strRfc, strHomoclave, strEmail, strCodigo);
            } else {
                bResultado = actualizaCodigoConfirmacion(strRfc, strHomoclave, strEmail, strCodigo);
            }

        } catch (SQLException ex) {
            grabaBitacoraError(ex, new Throwable());
            throw new SQLException(ex.getMessage());

        } catch (Exception ex) {
            grabaBitacoraError(ex, new Throwable());
            throw new Exception(ex.getMessage());
        }
        return bResultado;
    }

    public String consultaCodigoAnterior(String strRfc, String strHomoclave, String strEmail) throws SQLException, Exception {
        String strCodigo = null;

        if (getConectaBD().getConConexion() != null) {
            PreparedStatement pstmt = null;
            ResultSet rsConsulta = null;

            try {
                String strSql = "";
                QueryEstandar query = new QueryEstandar();
                strSql = query.getSqlBuscaCodigoAnterior();

                pstmt = getConectaBD().getConConexion().prepareStatement(strSql);

                if (pstmt != null) {
                    pstmt.setString(1, strRfc);
                    pstmt.setString(2, strHomoclave);
                    pstmt.setString(3, strEmail);

                    rsConsulta = pstmt.executeQuery();

                    if (rsConsulta != null && rsConsulta.next()) {
                        strCodigo = rsConsulta.getString(1);
                    }
                }

            } catch (SQLException ex) {
                grabaBitacoraError(ex, new Throwable());
                throw new SQLException(ex.getMessage());

            } catch (Exception ex) {
                grabaBitacoraError(ex, new Throwable());
                throw new Exception(ex.getMessage());
            } finally {
                pstmt.close();
                rsConsulta.close();
            }
        }

        return strCodigo;
    }

    private boolean insertaCodigoConfirmacion(String strRfc, String strHomoclave, String strEmail, String strCodigo) throws SQLException, Exception {

        boolean bResultado = false;

        if (getConectaBD().getConConexion() != null) {
            PreparedStatement pstmt = null;
            try {
                String strSql = "";
                QueryEstandar query = new QueryEstandar();

                strSql = query.getSqlInsertarCodigo();

                pstmt = getConectaBD().getConConexion().prepareStatement(strSql);

                if (pstmt != null) {
                    pstmt.setString(1, strRfc);
                    pstmt.setString(2, strHomoclave);
                    pstmt.setString(3, strEmail);
                    pstmt.setString(4, strCodigo);

                    int iResultado = pstmt.executeUpdate();

                    if (iResultado > 0) {
                        bResultado = true;
                    }
                }
            } catch (SQLException ex) {
                grabaBitacoraError(ex, new Throwable());
                throw new SQLException(ex.getMessage());

            } catch (Exception ex) {
                grabaBitacoraError(ex, new Throwable());
                throw new Exception(ex.getMessage());
            } finally {
                pstmt.close();
            }

        }

        return bResultado;
    }

    private boolean actualizaCodigoConfirmacion(String strRfc, String strHomoclave, String strEmail, String strCodigo) throws SQLException, Exception {

        boolean bResultado = false;

        if (getConectaBD().getConConexion() != null) {
            PreparedStatement pstmt = null;
            try {
                String strSql = "";
                QueryEstandar query = new QueryEstandar();

                strSql = query.getSqlActualizaCodigo();

                pstmt = getConectaBD().getConConexion().prepareStatement(strSql);

                if (pstmt != null) {
                    pstmt.setString(1, strCodigo);
                    pstmt.setString(2, strRfc);
                    pstmt.setString(3, strHomoclave);
                    pstmt.setString(4, strEmail);

                    int iResultado = pstmt.executeUpdate();

                    if (iResultado > 0) {
                        bResultado = true;
                    }
                }

            } catch (SQLException ex) {
                grabaBitacoraError(ex, new Throwable());
                throw new SQLException(ex.getMessage());

            } catch (Exception ex) {
                grabaBitacoraError(ex, new Throwable());
                throw new Exception(ex.getMessage());
            } finally {
                pstmt.close();
            }

        }

        return bResultado;
    }

    public Usuarios getConsultaUsuarios(String sCve, String sPass) throws Exception {
        ResultSet rsResultado;
        rsResultado = null;
        //rsResultadoProceso = null;
        String query;
        String rfcHomo = "";
        Usuarios usuario = null;
        PreparedStatement pstmt = null;

        try {
            query = getQuery().getSqlDatosUsuario();
            pstmt = getConectaBD().getConConexion().prepareStatement(query);
            if (pstmt != null) {
                rsResultado = pstmt.executeQuery();

                if (rsResultado != null) {
                    while (rsResultado.next()) {
                        if (rsResultado.getString(1).compareTo(sCve) == 0
                                && rsResultado.getString(2).compareTo(sPass) == 0) {
                            usuario = new Usuarios();
                            usuario.setsCveUsuario(rsResultado.getString(1));
                            usuario.setsPassword(rsResultado.getString(2));
                            usuario.setsNombre(rsResultado.getString(3));
                            usuario.setsPater(rsResultado.getString(4));
                            usuario.setsMater(rsResultado.getString(5));
                            usuario.setsActivo(rsResultado.getString(6));
                            usuario.setsCorreo(rsResultado.getString(7));
                            usuario.setsIndAdmin(rsResultado.getString(10));
                            usuario.setsDependencia(rsResultado.getString(11));
                            usuario.setsCapturaSolicitante(rsResultado.getString(12));
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            grabaBitacoraError(ex, new Throwable());
            throw new Exception(ex.getMessage());

        } finally {
            if (rsResultado != null) {
                rsResultado.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }
        return usuario;
    }

    public List<Proceso> obtieneProcesos(String strUsuario) throws Exception {
        Proceso proceso = null;
        List<Proceso> arProcesos = null;
        String query = "";
        PreparedStatement pstmt = null;
        ResultSet rsResultado = null;
        query = getQuery().getObtieneProcesos();
        try {
            pstmt = getConectaBD().getConConexion().prepareStatement(query);
            pstmt.setString(1, strUsuario);
            rsResultado = pstmt.executeQuery();
            if (rsResultado != null) {
                arProcesos = new ArrayList<Proceso>();
                while (rsResultado.next()) {
                    proceso = new Proceso();
                    proceso.setStrDescripcion(rsResultado.getString(1));
                    proceso.setStrUrl(rsResultado.getString(2));
                    proceso.setStrIcono(rsResultado.getString(3));
                    proceso.setStrEtiqueta(rsResultado.getString(4));
                    proceso.setStrApoyo(rsResultado.getString(5));
                    proceso.setShTipo(rsResultado.getShort(6));
                    proceso.setInCveMenu(rsResultado.getInt(7));
                    proceso.setInOrden(rsResultado.getInt(8));
                    proceso.setStrDescMenu(rsResultado.getString(9));
                    proceso.setStrIconoMenu(rsResultado.getString(10));
                    arProcesos.add(proceso);
                }
            }
        } catch (Exception ex) {
            grabaBitacoraError(ex, new Throwable());
            throw new Exception(ex.getMessage());
        } finally {
            if (rsResultado != null) {
                rsResultado.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return arProcesos;
    }

    public String armaMenu(List<Proceso> alProceso) {
        int inMenuAnt = -1;
        String strMenu = "";
        Proceso proOpcion;
        for (int inRow = 0; inRow < alProceso.size(); inRow++) {

            proOpcion = (Proceso) alProceso.get(inRow);
            //NO SE HA CREADO LA OPCION DEL MENU
            if (inMenuAnt != proOpcion.getInCveMenu()) {
                if (proOpcion.getStrIconoMenu() != null) {
                    strMenu += "<H3> <img src='" + proOpcion.getStrIconoMenu() + "' width='32' height='32' id='icono-menu'>    " + proOpcion.getStrDescMenu() + "</H3>";
                } else {
                    strMenu += "<H3> " + proOpcion.getStrDescMenu() + "</H3>";
                }
            }
            inMenuAnt = proOpcion.getInCveMenu();
            //if (proOpcion.getShCveEstatus() != IN_OPCION_INHABILITADA) {
            switch (proOpcion.getShTipo()) {
                case IN_TIPO_OPCION_BOTON:
                    strMenu += "<button name ='" + proOpcion.getStrApoyo() + "' type='submit'>" + proOpcion.getStrEtiqueta() + "</button><br>";
                    break;
                case IN_TIPO_OPCION_LINK:

                    if (proOpcion.getStrIcono().trim().equals("") == false) {
                        strMenu += "<a href ='" + proOpcion.getStrUrl() + "'>";
                        strMenu += "<img src='" + proOpcion.getStrIcono() + "' alt='" + proOpcion.getStrApoyo() + "'> " + proOpcion.getStrEtiqueta() + "</a><br>";
                    } else {
                        strMenu += "<a href ='" + proOpcion.getStrUrl() + "' class='linkazul'>" + proOpcion.getStrEtiqueta() + "</a><br>";
                    }
                    break;
            }
            //}
        }
        return strMenu;
    }

    /**
     * @return the sConexionBD
     */
    public String getsConexionBD() {
        return sConexionBD;
    }

    /**
     * @param sConexionBD the sConexionBD to set
     */
    public void setsConexionBD(String sConexionBD) {
        this.sConexionBD = sConexionBD;
    }

    public Usuarios getExisteUsuario(String sCve) throws Exception {
        ResultSet rsResultado;
        rsResultado = null;
        //rsResultadoProceso = null;
        String query;
        String rfcHomo = "";
        Usuarios usuario = null;
        PreparedStatement pstmt = null;

        try {
            query = getQuery().getSqlExisteUsuario();
            pstmt = getConectaBD().getConConexion().prepareStatement(query);
            if (pstmt != null) {
                pstmt.setString(1, sCve);
                rsResultado = pstmt.executeQuery();
                if (rsResultado != null) {
                    while (rsResultado.next()) {
                        usuario = new Usuarios();
                        usuario.setsCveUsuario(rsResultado.getString(1));
                        usuario.setsPassword(rsResultado.getString(2));
                        usuario.setsNombre(rsResultado.getString(3));
                        usuario.setsPater(rsResultado.getString(4));
                        usuario.setsMater(rsResultado.getString(5));
                        usuario.setsActivo(rsResultado.getString(6));
                        usuario.setsCorreo(rsResultado.getString(7));
                    }
                }
            }
        } catch (SQLException ex) {
            grabaBitacoraError(ex, new Throwable());
            throw new Exception(ex.getMessage());

        } finally {
            if (rsResultado != null) {
                rsResultado.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }
        return usuario;
    }

    public void obtenerConfiguracion(Long lrecibo) throws IOException {
        ConfiguracionCorreo configuracion;

        if (getConfigBd() == null) {
            try {
                setConfigBd(new DatosBdConfig("DatosBd.properties"));
            } catch (IOException ex) {
                grabaBitacoraError(new Exception("Error al leer archivo de configuracion para obtener URL de Webservices del Padron : " + ex.getMessage()), ex);
            }
        }

        Configuraciones config = new Configuraciones("CorreoElectronico.properties");
        configuracion = new ConfiguracionCorreo();
        try {
            if (getConfigBd().isProduccion(getStrServer())) {
                if (!configuracion.cargarProduccion()) {
                    configuracion = null;
                } else {
                    configuracion.setContrasenaWebServices("Picf3p0Rt4l");
                }
            } else {
                if (!configuracion.cargarDesarrollo()) {
                    configuracion = null;
                }
            }
            setConfiguracionCorreo(configuracion);
        } catch (Exception ex) {
        }
    }

    public Resultado envioMailPassword(String strRFC, String Password, String strMail) {
        try {
            long ltemp = 1;
            this.obtenerConfiguracion(ltemp);

            getConfiguracionCorreo().setAsuntoCorreo("Recuperar Password");
            String dest[] = {strMail};
            String destCC[] = {strMail};
            String destBCC[] = {strMail};
            Archivo adjuntos[] = null;
            String strMensaje = generaHTMLMailPass(strRFC, Password);
            return enviaCorreo(dest, destCC, destBCC, adjuntos, strMensaje, false, "");
        } catch (Exception ex) {
            ex.getMessage();
        }
        return null;
    }

    /**
     * @return the configBd
     */
    public DatosBdConfig getConfigBd() {
        return configBd;
    }

    /**
     * @param configBd the configBd to set
     */
    public void setConfigBd(DatosBdConfig configBd) {
        this.configBd = configBd;
    }

    /**
     * @return the configuracionCorreo
     */
    public ConfiguracionCorreo getConfiguracionCorreo() {
        return configuracionCorreo;
    }

    /**
     * @param configuracionCorreo the configuracionCorreo to set
     */
    public void setConfiguracionCorreo(ConfiguracionCorreo configuracionCorreo) {
        this.configuracionCorreo = configuracionCorreo;
    }

    public String generaHTMLMailPass(String strUsuario, String strPass) {
        String strHtml = "<!DOCTYPE HTML> "
                + "<html>   "
                + "  <head>   "
                + "    <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">   "
                + "    <title>Comprobante Fiscal Digital</title>   "
                + "  </head>   "
                + "  <body> "
                + "	<table width=\"900px\" border=\"0\"  cellspacing=0 cellpadding=2 bordercolor=\"CDCDCD\" style=\"margin:0 auto; border-collapse: 'collapse'; font-family: 'Arial'; \"> "
                + "		<tr> "
                + "			<td><img src=\"http://servicios.ebajacalifornia.gob.mx/Servicios/template/imagenes/bannerHeader.jpg\" alt=\"cabecera\" ></td> "
                + "		</tr> "
                + "		<tr> "
                + "			<td><h2>Recuperar Contraseña</h2></td> "
                + "		</tr> "
                + "		<tr> "
                + "			<td><p>Se genero la contraseña:" + strPass + " para el usuario:" + strUsuario + "</p> "
                + "        <p>Estamos para Servirle.</p><br><br><br><br></td> "
                + "		</tr> "
                + "		<tr> "
                + "			<td><img src=\"http://servicios.ebajacalifornia.gob.mx/Servicios/template/imagenes/pie.jpg\"  alt=\"pie\"> "
                + "		</tr> "
                + "	</table> "
                + "  </body> "
                + "</html>";
        return strHtml;
    }

    public Resultado enviaCorreo(String dest[], String destCC[],
            String destBCC[], Archivo adjuntos[], String strMensaje, boolean bReenvio, String strFolio) {

        boolean bResultado = false;
        final int CORREO_ENVIADO = 0;
        Resultado resultado = null;
        try {
            obtenerConfiguracion(new Long(1));
            ConfiguracionCorreo configuracion = getConfiguracionCorreo();

            if (configuracion != null) {
                String destinatarios[] = dest;
                String destinatariosCC[] = destCC;
                String destinatariosBCC[] = destBCC;

                Correo correo = new Correo();
                if (bReenvio) {
                    Properties propiedades = new Properties();
                    propiedades.load(ResourceLoader.load("CorreoElectronico.properties"));
                    if (!getConfigBd().isProduccion(getStrServer())) {
                        correo.setAsunto(propiedades.getProperty("ASUNTO_DESAR_REENVIO") + ", Folio: " + strFolio);
                    } else {
                        correo.setAsunto(propiedades.getProperty("ASUNTO_PROD_REENVIO") + ", Folio: " + strFolio);
                    }
                } else {
                    correo.setAsunto(configuracion.getAsuntoCorreo());
                }
                correo.setMensaje(strMensaje);
                correo.setUsuarioNombre(configuracion.getNombreUsuario());
                correo.setUsuario(configuracion.getUsuarioCuentaCorreo());
                correo.setContrasena(configuracion.getContrasenaCuentaCorreo());
                correo.setDestinatarios(destinatarios);
                correo.setDestinatariosCC(destinatariosCC);
                correo.setDestinatariosBCC(destinatariosBCC);
                correo.setArchivosAdjuntos(adjuntos);

                Usuario usuario = new Usuario();
                usuario.setNombre(configuracion.getUsuarioWebServices());
                usuario.setContrasena(configuracion.getContrasenaWebServices());
                usuario.setHash(configuracion.getHashWebServices());

                Email_ServiceLocator loc = new Email_ServiceLocator();
                loc.setEmailPortEndpointAddress(configuracion.getUrlWebServices());
                Email_PortType email = loc.getEmailPort();
                try {
                    resultado = email.enviaCorreo(correo, usuario);
                } catch (RemoteException ex) {
                    Logger.getLogger(ResultSQLEstandar.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (resultado.getCodigo() == CORREO_ENVIADO) {
                    bResultado = true;
                }

            } else {
                throw new Exception("Error al obtener la configuracion para consumir.");
            }

        } catch (Throwable ex) {
            bResultado = false;
        }

        return resultado;

    }

    public List<Usuarios> getObtieneUsuarios() throws Exception {
        ResultSet rsResultado;
        List arDatos = null;
        rsResultado = null;
        //rsResultadoProceso = null;
        String query;
        String rfcHomo = "";
        Usuarios usuario = null;
        PreparedStatement pstmt = null;

        try {
            query = getQuery().getSqlDatosUsuario();
            pstmt = getConectaBD().getConConexion().prepareStatement(query);
            if (pstmt != null) {
                rsResultado = pstmt.executeQuery();

                if (rsResultado != null) {
                    arDatos = new ArrayList<Usuarios>();
                    while (rsResultado.next()) {
                        usuario = new Usuarios();
                        usuario.setsCveUsuario(rsResultado.getString(1));
                        usuario.setsPassword(rsResultado.getString(2));
                        usuario.setsNombre(rsResultado.getString(3));
                        usuario.setsPater(rsResultado.getString(4));
                        usuario.setsMater(rsResultado.getString(5));
                        usuario.setsActivo(rsResultado.getString(6));
                        usuario.setsCorreo(rsResultado.getString(7));
                        usuario.setiRol(rsResultado.getString(8));
                        usuario.setsDescrRol(rsResultado.getString(9));
                        arDatos.add(usuario);
                    }
                }
            }
        } catch (SQLException ex) {
            grabaBitacoraError(ex, new Throwable());
            throw new Exception(ex.getMessage());

        } finally {
            if (rsResultado != null) {
                rsResultado.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }
        return arDatos;
    }

    public List<Roles> getObtieneRoles() throws Exception {
        ResultSet rsResultado;
        List<Roles> arDatos = null;
        rsResultado = null;
        //rsResultadoProceso = null;
        String query;
        String rfcHomo = "";
        Roles rol = null;
        PreparedStatement pstmt = null;

        try {
            query = getQuery().getSqlRoles();
            pstmt = getConectaBD().getConConexion().prepareStatement(query);
            if (pstmt != null) {
                rsResultado = pstmt.executeQuery();

                if (rsResultado != null) {
                    arDatos = new ArrayList<Roles>();
                    while (rsResultado.next()) {
                        rol = new Roles();
                        rol.setiRol(rsResultado.getInt(1));
                        rol.setsDescr(rsResultado.getString(2));
                        arDatos.add(rol);
                    }
                }
            }
        } catch (SQLException ex) {
            grabaBitacoraError(ex, new Throwable());
            throw new Exception(ex.getMessage());

        } finally {
            if (rsResultado != null) {
                rsResultado.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }
        return arDatos;
    }

    public JSONObject mantenimientoUsuario(Usuarios usuario, int iOpcion) {
        Resultado resMail = null;
        String strQuery = "";
        PreparedStatement pstmt = null;
        JSONObject json = null;
        boolean bExito = false;
        ResultSet sql = null;
        int iExiste = 0;
        try {
            json = new JSONObject();
            pstmt = getConectaBD().getConConexion().prepareStatement(this.getQuery().getSqlExisteUsuario());
            pstmt.setString(1, usuario.getsCveUsuario());
            sql = pstmt.executeQuery();
            if (sql != null) {
                while (sql.next()) {
                    iExiste = 1;
                }
                if (iExiste <= 0) {
                    if (iOpcion == 0) {
                        pstmt = getConectaBD().getConConexion().prepareStatement(this.getQuery().getSqlInsertaUsuario());
                        pstmt.setString(1, usuario.getsCveUsuario());
                        pstmt.setString(2, usuario.getsNombre());
                        pstmt.setString(3, usuario.getsPater());
                        pstmt.setString(4, usuario.getsMater());
                        pstmt.setString(5, usuario.getsPassword());
                        pstmt.setString(6, usuario.getsActivo());
                        pstmt.setString(7, usuario.getsCorreo());
                        if (pstmt.executeUpdate() > 0) {
                            pstmt = getConectaBD().getConConexion().prepareStatement(this.getQuery().getSqlInsertaRolUsuario());
                            pstmt.setString(1, usuario.getsCveUsuario());
                            pstmt.setString(2, usuario.getiRol());
                            if (pstmt.executeUpdate() > 0) {
                                bExito = true;
                                json.put("resultado", "0");
                                json.put("mensaje", "El usuario fue agregado Exitosamente");
                                json.put("accion", "1");//insercion
                            } else {
                                json.put("resultado", "0");
                                json.put("mensaje", "No se logro asignar el rol al usuario");
                            }
                        } else {
                            json.put("resultado", "0");
                            json.put("mensaje", "No se inserto el usuario");
                        }
                    }
                } else {
                    if (iOpcion == 0) {
                        pstmt = getConectaBD().getConConexion().prepareStatement(this.getQuery().getSqlUpdateUsuario());
                        pstmt.setString(1, usuario.getsNombre());
                        pstmt.setString(2, usuario.getsPater());
                        pstmt.setString(3, usuario.getsMater());
                        pstmt.setString(4, usuario.getsPassword());
                        pstmt.setString(5, usuario.getsActivo());
                        pstmt.setString(6, usuario.getsCorreo());
                        pstmt.setString(7, usuario.getsCveUsuario());
                        if (pstmt.executeUpdate() > 0) {
                            pstmt = getConectaBD().getConConexion().prepareStatement(this.getQuery().getSqlUpdateRolUsuario());
                            pstmt.setString(1, usuario.getiRol());
                            pstmt.setString(2, usuario.getsCveUsuario());
                            if (pstmt.executeUpdate() > 0) {
                                bExito = true;
                                json.put("resultado", "0");
                                json.put("mensaje", "El usuario fue actualizado Exitosamente");
                                json.put("accion", "3");//actualizacion
                            } else {
                                json.put("resultado", "0");
                                json.put("mensaje", "No se logro asignar el rol al usuario");
                            }
                        } else {
                            json.put("resultado", "0");
                            json.put("mensaje", "No se actualizo el usuario");
                        }
                    } else if (iOpcion == 2) {
                        pstmt = getConectaBD().getConConexion().prepareStatement(this.getQuery().getSqlDeleteRolUsuario());
                        pstmt.setString(1, usuario.getsCveUsuario());
                        if (pstmt.executeUpdate() > 0) {
                            pstmt = getConectaBD().getConConexion().prepareStatement(this.getQuery().getSqlDeleteUsuario());
                            pstmt.setString(1, usuario.getsCveUsuario());
                            if (pstmt.executeUpdate() > 0) {
                                bExito = true;
                                json.put("resultado", "0");
                                json.put("mensaje", "El usuario fue eliminado Exitosamente");
                                json.put("accion", "2");//insercion
                            } else {
                                json.put("resultado", "0");
                                json.put("mensaje", "No se elimino el rol del usuario");
                            }

                        } else {
                            json.put("resultado", "0");
                            json.put("mensaje", "No se actualizo el usuario");
                        }
                    }
                }
                this.transaccionCommit();//revisado
            }

        } catch (Exception ex) {
            this.transaccionRollback();
            json.put("resultado", "-1");
            json.put("mensaje", "Ocurrio un error" + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (sql != null) {
                    sql.close();
                }
            } catch (Exception ex) {
            }
        }

        return json;
    }

    public int anioActual() throws Exception {
        int iAnio = 0;
        ResultSet rsResultado;
        rsResultado = null;
        String query;
        PreparedStatement pstmt = null;
        try {
            query = getQuery().getSQLAnioActual();
            pstmt = getConectaBD().getConConexion().prepareStatement(query);

            rsResultado = pstmt.executeQuery();

            if (rsResultado != null) {
                while (rsResultado.next()) {
                    iAnio = rsResultado.getInt(1);
                }
            }
        } catch (SQLException ex) {
            grabaBitacoraError(ex, new Throwable());
            throw new Exception(ex.getMessage());

        } finally {
            if (rsResultado != null) {
                rsResultado.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }
        return iAnio;
    }
}
