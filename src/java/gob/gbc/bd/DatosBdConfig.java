package gob.gbc.bd;

import gob.gbc.util.ResourceLoader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author muribe
 */
public class DatosBdConfig extends java.lang.Object {

    /*SPPD*/
    private String connPoolDesarMiscweb = "";
    private String connStringDesarMiscweb = "";
    private String bdUserDesarMiscweb = "";
    private String bdPassDesarMiscweb = "";

    private String bdDriver = "";
    private String serverDominio = "";
    private String sEsquemaBD  = "";
    private String sConexionBD  = "";

    public DatosBdConfig(String archivo) throws IOException {
        Properties propiedades = new Properties();
        propiedades.load(ResourceLoader.load(archivo));

        /*SPPD*/
        connPoolDesarMiscweb = propiedades.getProperty("CONNPOOLDESARSPPD");
        connStringDesarMiscweb = propiedades.getProperty("CONNSTRINGDESARSPPD");
        bdUserDesarMiscweb = propiedades.getProperty("BDUSERDESARSPPD");
        bdPassDesarMiscweb = propiedades.getProperty("BDPASSDESARSPPD");

        serverDominio = propiedades.getProperty("SERVERDOMINIO");
        bdDriver = propiedades.getProperty("BDDRIVER");
        sEsquemaBD = propiedades.getProperty("BDESQUEMA");
        sConexionBD = propiedades.getProperty("BDCONEXION");
    }
    
    public DatosBdConfig(String archivo, String tipoDependencia) throws IOException {
        Properties propiedades = new Properties();
        propiedades.load(ResourceLoader.load(archivo));
        if(tipoDependencia.equalsIgnoreCase("SPPD")){
            /*SPPD*/
            connPoolDesarMiscweb = propiedades.getProperty("CONNPOOLDESARSPPD");
            connStringDesarMiscweb = propiedades.getProperty("CONNSTRINGDESARSPPD");
            bdUserDesarMiscweb = propiedades.getProperty("BDUSERDESARSPPD");
            bdPassDesarMiscweb = propiedades.getProperty("BDPASSDESARSPPD");
        }else if(tipoDependencia.equalsIgnoreCase("PROGD")){
            /*PARAS*/
            connPoolDesarMiscweb = propiedades.getProperty("CONNPOOLDESARPARAS");
            connStringDesarMiscweb = propiedades.getProperty("CONNSTRINGDESARPARAS");
            bdUserDesarMiscweb = propiedades.getProperty("BDUSERDESARPARAS");
            bdPassDesarMiscweb = propiedades.getProperty("BDPASSDESARPARAS");
        }   

        serverDominio = propiedades.getProperty("SERVERDOMINIO");
        bdDriver = propiedades.getProperty("BDDRIVER");
        sEsquemaBD = propiedades.getProperty("BDESQUEMA");
        sConexionBD = propiedades.getProperty("BDCONEXION");
    }
    
    public String getsEsquemaBD() {
        return sEsquemaBD;
    }

    public void setsEsquemaBD(String sEsquemaBD) {
        this.sEsquemaBD = sEsquemaBD;
    }

    public String getsConexionBD() {
        return sConexionBD;
    }

    public void setsConexionBD(String sConexionBD) {
        this.sConexionBD = sConexionBD;
    }
    public String getBdDriver() {
        return bdDriver;
    }

    public void setBdDriver(String bdDriver) {
        this.bdDriver = bdDriver;
    }

    public String getBdPassDesarMiscweb() {
        return bdPassDesarMiscweb;
    }

    public void setBdPassDesarMiscweb(String bdPassDesarMiscweb) {
        this.bdPassDesarMiscweb = bdPassDesarMiscweb;
    }
    
    public String getBdUserDesarMiscweb() {
        return bdUserDesarMiscweb;
    }

    public void setBdUserDesarMiscweb(String bdUserDesarMiscweb) {
        this.bdUserDesarMiscweb = bdUserDesarMiscweb;
    }
    
    public String getConnPoolDesarMiscweb() {
        return connPoolDesarMiscweb;
    }

    public void setConnPoolDesarMiscweb(String connPoolDesarMiscweb) {
        this.connPoolDesarMiscweb = connPoolDesarMiscweb;
    }
    
    public String getConnStringDesarMiscweb() {
        return connStringDesarMiscweb;
    }

    public void setConnStringDesarMiscweb(String connStringDesarMiscweb) {
        this.connStringDesarMiscweb = connStringDesarMiscweb;
    }
    
    public String getServerDominio() {
        return serverDominio;
    }
    
    public boolean isProduccion(String strHost) {
        boolean bProduccion = false;

        if (strHost.equals(getServerDominio())) {
            bProduccion = true;
        }

        return bProduccion;
    }
}
