/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.estandar;

//import gob.gbc.clases.Solicitud;
/**
 *
 * @author joquintero
 */
public class QueryEstandar {

    private String sEsquema = "";

    public String getSQLDesconectaDblink() {
        String strSQL = "{CALL " + getsEsquema() + ".P_CERRAR_SESION_DBLINK(?)}";
        return strSQL;
    }

    public String getSQLObtenerParametros(int cant) {

        String strFiltros = "";

        for (int i = 0; i < cant; i++) {
            strFiltros += "?";

            if (i < cant - 1) {
                strFiltros += ",";
            }
        }

        String strSql = "SELECT CONSEC, "
                + "             VALOR "
                + "     FROM " + getsEsquema() + ".CONFIGURACION "
                + "     WHERE CONSEC IN (" + strFiltros + ")";



        return strSql;

    }

    public String getSQLUpdatePass() {
        String strSQL = " UPDATE " + getsEsquema() + ".USUARIO SET APP_PWD = ? WHERE APP_LOGIN = ?";
        return strSQL;
    }

    public String getSqlBuscaCodigoAnterior() {
        String strSql = "SELECT CODIGO "
                + "     FROM " + getsEsquema() + ".CODIGO_CONFIRMACION "
                + "     WHERE APP_LOGIN = ? "
                + "         AND EMAIL = ?";


        return strSql;
    }

    public String getSqlInsertarCodigo() {
        String strSql = "INSERT INTO " + getsEsquema() + ".CODIGO_CONFIRMACION "
                + " (ID_CODIGO,APP_LOGIN,EMAIL,CODIGO,FECHA)"
                + " VALUES ((" + getsEsquema() + ".SEC_IDCOD.NEXTVAL),?,?,?,SYSDATE)";

        return strSql;
    }

    public String getSqlActualizaCodigo() {
        String strSql = "UPDATE " + getsEsquema() + ".CODIGO_CONFIRMACION "
                + "         SET CODIGO = ? "
                + "     WHERE APP_LOGIN  =?  "
                + "         AND EMAIL = ?";

        return strSql;
    }

    public String getSqlDatosUsuario() {
        String strSql = "";
        strSql = "SELECT us.APP_LOGIN,us.APP_PWD,us.NOMBRE,us.ap_PATER,us.ap_MATER,us.ACTIVO,us.correo,rlus.rol,rl.descr,rl.ind_admin,d.Dependencia,"
                + "de.captura_Solicitante  "
                + " FROM " + getsEsquema() + ".USUARIO us,"
                + " " + getsEsquema() + ".ROL_USR rlus,"
                + " " + getsEsquema() + ".ROL rl, "
                + " " + getsEsquema() + ".USR_DEP d,"
                + " " + getsEsquema() + ".DEPENDENCIA de"
                + " WHERE rlus.APP_LOGIN = us.APP_LOGIN AND"
                + " rl.ROL = rlus.ROL  and "
                + " d.APP_LOGIN = us.app_login and"
                + " de.cvedependencia= d.dependencia ";
        return strSql;
    }

    public String getObtieneProcesos() {
        String strSql = "";
        strSql = "SELECT P.DESCRIPCION,P.URL,P.ICONO,P.ETIQUETA,P.APOYO,P.TIPO,P.CVEMENU,P.ORDEN,M.DESCRIPCION,M.ICONO "
                + " FROM "
                + " " + getsEsquema() + ".ROL_USR rlus,"
                + " " + getsEsquema() + ".ROL_PROCESO rl, "
                + " " + getsEsquema() + ".PROCESO P,"
                + " " + getsEsquema() + ".MENU M"
                + " WHERE rlus.APP_LOGIN = ? "
                + " AND rl.ROL = rlus.ROL  "
                + " AND P.cvePROCESO = rl.proceso "
                + " AND P.CVEESTATUS = 1 "
                + " AND M.CVEMENU = P.CVEMENU "
                + " order by P.cvemenu,P.orden";
        return strSql;
    }

    /**
     * @return the sEsquema
     */
    public String getsEsquema() {
        return sEsquema;
    }

    /**
     * @param sEsquema the sEsquema to set
     */
    public void setsEsquema(String sEsquema) {
        this.sEsquema = sEsquema;
    }

    public String getSqlExisteUsuario() {
        String strSql = "";
        strSql = "SELECT us.APP_LOGIN,us.APP_PWD,us.NOMBRE,us.ap_PATER,us.ap_MATER,us.ACTIVO,us.correo "
                + " FROM " + getsEsquema() + ".USUARIO us,"
                + " " + getsEsquema() + ".ROL_USR rlus,"
                + " " + getsEsquema() + ".ROL rl "
                + " WHERE us.app_login = ? and "
                + "rlus.APP_LOGIN = us.APP_LOGIN AND"
                + " rl.ROL = rlus.ROL  ";
        return strSql;
    }

    public String getSqlRoles() {
        String strSql = "";
        strSql = "SELECT ROL, DESCR FROM " + getsEsquema() + ".ROL ";
        return strSql;
    }

    public String getSqlInsertaUsuario() {
        String strSql = "";
        strSql = "INSERT INTO " + getsEsquema() + ".USUARIO (APP_LOGIN,NOMBRE,AP_PATER,AP_MATER,APP_PWD,ACTIVO,CORREO)"
                + " VALUES (?,?,?,?,?,?,?)";
        return strSql;
    }

    public String getSqlInsertaRolUsuario() {
        String strSql = "";
        strSql = "INSERT INTO " + getsEsquema() + ".ROL_USR (APP_LOGIN,ROL)"
                + " VALUES (?,?)";
        return strSql;
    }

    public String getSqlUpdateRolUsuario() {
        String strSql = "";
        strSql = "UPDATE " + getsEsquema() + ".ROL_USR SET ROL = ? WHERE APP_LOGIN = ?";
        return strSql;
    }

    public String getSqlUpdateUsuario() {
        String strSql = "";
        strSql = "UPDATE " + getsEsquema() + ".USUARIO SET NOMBRE = ? , AP_PATER= ? ,AP_MATER = ?, APP_PWD = ?,ACTIVO = ?,CORREO = ?"
                + " WHERE APP_LOGIN = ?";
        return strSql;
    }

    public String getSqlDeleteUsuario() {
        String strSql = "";
        strSql = "DELETE " + getsEsquema() + ".USUARIO  WHERE APP_LOGIN = ?";
        return strSql;
    }

    public String getSqlDeleteRolUsuario() {
        String strSql = "";
        strSql = "DELETE " + getsEsquema() + ".ROL_USR  WHERE APP_LOGIN = ?";
        return strSql;
    }

    public String getSQLAnioActual() {
        String strSQL = "";
        strSQL = "SELECT EXTRACT(YEAR FROM SYSDATE) FROM SYS.DUAL ";
        return strSQL;
    }
}
