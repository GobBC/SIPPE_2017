package gob.gbc.util;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author gvillasenor
 */
public class DatosBitConfig extends java.lang.Object {

    /*Produccion*/
    private String strArchivoErrorProd = "";
    private String strArchivoTransaccionProd = "";
    private String strArchivoDebugProd = "";
    private String strUbicacionArchivoProd = "";
    /*Desarrollo*/
    private String strArchivoErrorDesar = "";
    private String strArchivoTransaccionDesar = "";
    private String strArchivoDebugDesar = "";
    private String strUbicacionArchivoDesar = "";

    private String serverDominio = "";

    public DatosBitConfig(String archivo) throws IOException {
        Properties propiedades = new Properties();
        propiedades.load(ResourceLoader.load(archivo));

        /*Produccion*/
        strArchivoErrorProd = propiedades.getProperty("ARCHIVOERRORPROD");
        strArchivoTransaccionProd = propiedades.getProperty("ARCHIVOTRANSACCIONPROD");
        strArchivoDebugProd = propiedades.getProperty("ARCHIVODEBUGPROD");
        strUbicacionArchivoProd = propiedades.getProperty("UBICACIONARCHIVOPROD");
        /*Desarrollo*/
        strArchivoErrorDesar = propiedades.getProperty("ARCHIVOERRORDESAR");
        strArchivoTransaccionDesar = propiedades.getProperty("ARCHIVOTRANSACCIONDESAR");
        strArchivoDebugDesar = propiedades.getProperty("ARCHIVODEBUGDESAR");
        strUbicacionArchivoDesar = propiedades.getProperty("UBICACIONARCHIVODESAR");

        serverDominio = propiedades.getProperty("SERVERDOMINIO");
    }

    public String getStrArchivoDebugDesar() {
        return strArchivoDebugDesar;
    }

    public void setStrArchivoDebugDesar(String strArchivoDebugDesar) {
        this.strArchivoDebugDesar = strArchivoDebugDesar;
    }

    public String getStrArchivoDebugProd() {
        return strArchivoDebugProd;
    }

    public void setStrArchivoDebugProd(String strArchivoDebugProd) {
        this.strArchivoDebugProd = strArchivoDebugProd;
    }

    public String getStrArchivoErrorDesar() {
        return strArchivoErrorDesar;
    }

    public void setStrArchivoErrorDesar(String strArchivoErrorDesar) {
        this.strArchivoErrorDesar = strArchivoErrorDesar;
    }

    public String getStrArchivoErrorProd() {
        return strArchivoErrorProd;
    }

    public void setStrArchivoErrorProd(String strArchivoErrorProd) {
        this.strArchivoErrorProd = strArchivoErrorProd;
    }

    public String getStrArchivoTransaccionDesar() {
        return strArchivoTransaccionDesar;
    }

    public void setStrArchivoTransaccionDesar(String strArchivoTransaccionDesar) {
        this.strArchivoTransaccionDesar = strArchivoTransaccionDesar;
    }

    public String getStrArchivoTransaccionProd() {
        return strArchivoTransaccionProd;
    }

    public void setStrArchivoTransaccionProd(String strArchivoTransaccionProd) {
        this.strArchivoTransaccionProd = strArchivoTransaccionProd;
    }

    public String getStrUbicacionArchivoDesar() {
        return strUbicacionArchivoDesar;
    }

    public void setStrUbicacionArchivoDesar(String strUbicacionArchivoDesar) {
        this.strUbicacionArchivoDesar = strUbicacionArchivoDesar;
    }

    public String getStrUbicacionArchivoProd() {
        return strUbicacionArchivoProd;
    }

    public void setStrUbicacionArchivoProd(String strUbicacionArchivoProd) {
        this.strUbicacionArchivoProd = strUbicacionArchivoProd;
    }
    
    public String getServerDominio() {
        return serverDominio;
    }
}
