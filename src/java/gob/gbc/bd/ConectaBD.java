/*
 * ConectaBD.java
 *
 * Created on 18 de junio de 2007, 06:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gob.gbc.bd;

/**
 *
 * @author muribe
 */

import gob.gbc.util.Bitacora;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.SQLException;


public class ConectaBD {
    private String strServer = "";
    private String strUbicacion = "";
    private PreparedStatement pstmt;
    private DatosBdConfig datosConfig;
    private Connection conConexion;
    private Bitacora bitacora;
    private String sConexionBD = "";
    private String sEsquema = "";

    /** Creates a new instance of ConectaBD */
    public ConectaBD() throws IOException {
        setStrServer("");
        setPstmt(null);
        datosConfig = new DatosBdConfig("DatosBd.properties");
        bitacora = new Bitacora();
        setsConexionBD(datosConfig.getsConexionBD());
        setsEsquema(datosConfig.getsEsquemaBD());
    }
    
    public ConectaBD(String tipoDependencia) throws IOException {
        setStrServer("");
        setPstmt(null);
        datosConfig = new DatosBdConfig("DatosBd.properties",tipoDependencia);
        bitacora = new Bitacora();
        setsConexionBD(datosConfig.getsConexionBD());
        setsEsquema(datosConfig.getsEsquemaBD());
    }

    public String getsConexionBD() {
        return sConexionBD;
    }

    public void setsConexionBD(String sConexionBD) {
        this.sConexionBD = sConexionBD;
    }

    public String getsEsquema() {
        return sEsquema;
    }

    public void setsEsquema(String sEsquema) {
        this.sEsquema = sEsquema;
    }
    
    

    public DatosBdConfig getDatosConfig() {
        return datosConfig;
    }

    public void setDatosConfig(DatosBdConfig datosConfig) {
        this.datosConfig = datosConfig;
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
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

    private void getConPool(String bd) throws Exception, NamingException {
        InitialContext cnt = new InitialContext();
        DataSource ds = (DataSource)cnt.lookup(bd);
        setConConexion(ds.getConnection());
        getConConexion().setAutoCommit(false);//revisado
    }

    private void getConJDBC(String strDriver, String strConnString, String strUserName, String strPassword) throws Exception, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName(strDriver).newInstance();
        setConConexion(DriverManager.getConnection(strConnString, strUserName, strPassword));
        getConConexion().setAutoCommit(false);//revisado
    }



    public boolean esProduccion(){
       if(getStrServer().equals(datosConfig.getServerDominio()))
          return true;
       return false;
    }

    public void conecta(String bd) throws Exception  {
        String strConnPool = "";
        String strConnString = "";
        String strUser = "";
        String strPass = "";
        boolean blProduccion = false;

        blProduccion = esProduccion();

        //if(bd.equalsIgnoreCase("sppd")) {
            /*if(blProduccion) {
                // produccion
                strConnPool = datosConfig.getConnPoolProdMiscweb();
                strConnString = datosConfig.getConnStringProdMiscweb();
                strUser = datosConfig.getBdUserProdMiscweb();
                strPass = datosConfig.getBdPassProdMiscweb();
            }
            else {*/
                // desarrollo
                strConnPool = datosConfig.getConnPoolDesarMiscweb();
                strConnString = datosConfig.getConnStringDesarMiscweb();
                strUser = datosConfig.getBdUserDesarMiscweb();
                strPass = datosConfig.getBdPassDesarMiscweb();
            //}
        //}

        try {
            getConPool(strConnPool);
        }
        catch (Exception e) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, new Throwable());
            //bitacora.grabaBitacora();
            try {
                getConJDBC(datosConfig.getBdDriver(),strConnString,strUser,strPass);
            }
            catch (Exception e2) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(e2, new Throwable());
                bitacora.grabaBitacora();
                throw new Exception(e2.toString());
            }
        }
    }

    public void desconecta() throws Exception {
        try {
            if(getPstmt() != null) {
                getPstmt().close();
            }
            if(getConConexion() != null) {
                getConConexion().close();
            }
        }
        catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    public void transaccionCommit() throws Exception {
        try {
            getConConexion().commit();//revisado
        }
        catch(Exception e) {
            transaccionRollback();
            throw new Exception(e.toString());
        }
    }

    public void transaccionRollback() throws Exception {
        try {
            getConConexion().rollback();
        }
        catch(Exception e) {
            throw new Exception(e.toString());
        }
    }

    public CallableStatement prepareCall(String query) throws Exception {
        try {
            return getConConexion().prepareCall(query);
        }
        catch(Exception e) {
            throw new Exception(e.toString());
        }
    }


    public Vector ejecutaSQLConsulta(String strQuery) throws Exception {
        Vector vecRenglones = new Vector();
        Vector vecColumnas = null;

        try {
            setPstmt(getConConexion().prepareStatement(strQuery));
            ResultSet rsConsulta = getPstmt().executeQuery();
            ResultSetMetaData rsMeta = rsConsulta.getMetaData();
            int intNumColumnas = rsMeta.getColumnCount();

            while(rsConsulta.next()) {
                vecColumnas = new Vector();
                for(int i=1; i<=intNumColumnas; i++)
                    vecColumnas.addElement(rsConsulta.getString(i));

                vecRenglones.addElement(vecColumnas);
                vecColumnas = null;
            }
            rsConsulta.close();
            if(getPstmt() != null)
                getPstmt().close();


        }
        catch(Exception ex) {
            try {
                if(getConConexion() != null)
                    transaccionRollback();
            }
            catch(Exception e) {
                throw new Exception(e.toString());
            }
            throw new Exception(ex.toString());
        }

        return vecRenglones;
    }

    public ResultSet ejecutaConsultaRs(String strQuery) throws Exception {
        ResultSet rsConsulta = null;

        try {
            setPstmt(getConConexion().prepareStatement(strQuery));
            rsConsulta = getPstmt().executeQuery();
        }
        catch(Exception ex) {
            try {
                if(getConConexion() != null)
                    transaccionRollback();
            }
            catch(Exception e) {
                throw new Exception(e.toString());
            }
            throw new Exception(ex.toString());
        }
        getPstmt().close();
        return rsConsulta;
    }

    public boolean ejecutaSQLActualizacion(String strQuery) throws Exception, Exception {
        int intRegistros;
        boolean bolRegresa = false;
        intRegistros = 0;

        try {
            setPstmt(getConConexion().prepareStatement(strQuery));
            intRegistros = getPstmt().executeUpdate(strQuery);
            if(getPstmt() != null)
                getPstmt().close();
            if(intRegistros>0) {
                bolRegresa = true;
            }
        }
        catch(SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        catch(Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return bolRegresa;
    }

    public Connection getConConexion() {
        return conConexion;
    }

    public void setConConexion(Connection conConexion) {
        this.conConexion = conConexion;
    }
}
