/*
 * QuerysBD.java
 *
 * Created on 18 de mayo de 2009
 *
 */
package gob.gbc.sql;

import gob.gbc.aplicacion.Mes;
import gob.gbc.entidades.Ppto;
import gob.gbc.entidades.Proyeccion;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author muribe
 */
public class QuerysBD extends QueryBDMedio {

    /**
     * Creates a new instance of QuerysBD
     */
    public QuerysBD() {
    }

    public String getSQLConsultaEstatusUsuario(String strUsuario) {
        String strSQL = " SELECT 1, APP_PWD, SALT, PWD_AUTOGENERADO FROM DGI.DGI_USR USR "
                + "WHERE USR.APP_LOGIN = '" + strUsuario + "' AND "
                + "      USR.STATUS = 'A' AND "
                + "      USR.SYS_CLAVE = 1 ";
        return strSQL;
    }

    public String getSQLConsultaDatosUsuario(String strUsuario) {
        String strSQL = "SELECT "
                + " NOMBRE, ESTATUS, CORREO  "
                + " FROM DGI.POA_USUARIO  "
                + " WHERE USUARIO=UPPER('" + strUsuario + "') ";
        return strSQL;
    }

    public String getSQLgetCriterioTransversalidad() {
        String strSQL = "SELECT T.CRITERIO,T.DESCR "
                + "FROM DGI.POA_CRITERIO_TRANSVERSALIDAD T ";
        return strSQL;
    }

    public String getSQLGetTipoAccion() {
        String strSQL = " SELECT * FROM S_POA_TIPO_ACCION ";
        return strSQL;
    }

    public String getSQLConsultaDatosUsuario(String strUsuario, String strContrasena) {
        String strSQL = "SELECT (USR.AP_PATER || USR.AP_MATER || USR.NOMBRE) AS NOMBRE, 'correo', 3, 3 ,RUSR.ROL,usr.ramo,usr.depto "
                + "FROM DGI.DGI_USR USR, DGI.DGI_ROL_USR RUSR "
                + "WHERE USR.APP_LOGIN = '" + strUsuario + "' AND "
                + "      USR.APP_PWD = '" + strContrasena + "' AND "
                + "      STATUS = 'A' "
                + "      AND USR.SYS_CLAVE = 1 "
                + "      AND RUSR.APP_LOGIN = USR.APP_LOGIN "
                + "      AND RUSR.SYS_CLAVE = USR.SYS_CLAVE ";

        return strSQL;
    }

    public String getSQLActualizaDatosUsuario(String strUsuario, String strCorreo,
            String strEstatus, String strNombre) {
        String strSQL = "UPDATE DGI.POA_USUARIO "
                + "SET  CORREO='" + strCorreo + "',  "
                + "ESTATUS=" + strEstatus + ", "
                + "NOMBRE=UPPER('" + strNombre + "')   "
                + "WHERE USUARIO=UPPER('" + strUsuario + "')";
        return strSQL;
    }

    public String getSQLValidaInsertaUsuario(String strUsuario) {
        String strSQL = "SELECT 1 "
                + "FROM DGI.POA_USUARIO "
                + "WHERE USUARIO=UPPER('" + strUsuario + "')";
        return strSQL;
    }

    public String getSQLGetLongitudCodigo(int year) {
        String strSQL = "SELECT * FROM S_DGI_PARAM_LONG_COD "
                + "WHERE YEAR = " + year;
        return strSQL;
    }

    public String getSQLInsertaUsuario(String strUsuario, String strContrasena, String strCorreo,
            String strEstatus, String strNombre) {
        String strSQL = "INSERT INTO "
                + "DGI.POA_USUARIO "
                + "(USUARIO, CONTRASENA, TIPO_CONTRASENA, ESTATUS, CORREO, NOMBRE) "
                + "VALUES  "
                + "(UPPER('" + strUsuario + "'), '" + strContrasena + "', 1, " + strEstatus + ", '" + strCorreo + "',  UPPER('" + strNombre + "')) ";
        return strSQL;
    }

    public String getSQLValidaCambiaContrasena(String strUsuario, String strContrasena) {
        String strSQL = "SELECT 1 "
                + "FROM DGI.POA_USUARIO "
                + "WHERE USUARIO=UPPER('" + strUsuario + "') AND "
                + "CONTRASENA='" + strContrasena + "'";
        return strSQL;
    }

    public String getSQLCambiaContrasena(String strUsuario, String strContrasena) {
        String strSQL = "UPDATE DGI.POA_USUARIO  "
                + "SET CONTRASENA='" + strContrasena + "', "
                + "TIPO_CONTRASENA='3'  "
                + "WHERE USUARIO=UPPER('" + strUsuario + "') ";
        return strSQL;
    }

    public String getSQLConsultaUsuario(String strUsuario) {
        String strSQL = "SELECT "
                + "U.NOMBRE, "
                + "DECODE(U.ESTATUS,1,'ACTIVO','INACTIVO') AS ESTATUS, "
                + "U.CORREO  "
                + "FROM DGI.POA_USUARIO U "
                + "WHERE U.USUARIO='" + strUsuario + "'";
        return strSQL;
    }

    public String getSQLValidaSolicitaContrasena(String strUsuario) {
        String strSQL = "SELECT USR.STATUS, USR.APP_LOGIN, USR.CORREO "
                + "FROM DGI.DGI_USR USR "
                + "WHERE USR.APP_LOGIN = UPPER('" + strUsuario + "')";
        return strSQL;
    }

    public String getSQLSolicitaContrasena(String strUsuario, String strContrasena) {
        String strSQL = "UPDATE DGI.DGI_USR USR SET USR.APP_PWD = '" + strContrasena + "' "
                + "WHERE USR.APP_LOGIN = UPPER('" + strUsuario + "')";
        return strSQL;
    }

    public String getSQLConsultaUsuarios() {
        String strSQL = "SELECT U.NOMBRE AS NOMBRE, U.USUARIO, U.ESTATUS,  U.CORREO "
                + "FROM DGI.POA_USUARIO U "
                + "ORDER BY U.ESTATUS, U.USUARIO";
        return strSQL;
    }

    public String getSQLOpcionesMenuByUsuario(String usuario) {
        String strSQL = "SELECT USR.APP_LOGIN, RUSR.ROL, RPRO.PROCESO, PRO.MENU, PRO.DESCR, PRO.SYSMENUNAME, PRO.URL, PRO.SUBMENU "
                + "FROM DGI.DGI_USR USR, DGI.DGI_ROL_USR RUSR, DGI.DGI_ROL_PROCESO RPRO,DGI.DGI_PROCESO PRO "
                + "WHERE USR.APP_LOGIN = '" + usuario + "' AND  "
                + "      RUSR.APP_LOGIN = USR.APP_LOGIN AND  "
                + "      RUSR.ROL = RPRO.ROL AND  "
                + "      RPRO.PROCESO = PRO.PROCESO AND "
                + "      USR.SYS_CLAVE = 1 AND "
                + "      RPRO.SYS_CLAVE = USR.SYS_CLAVE AND "
                + "       PRO.SUBMENU IS NOT NULL "
                + "ORDER BY PRO.SUBMENU,PRO.PROCESO";
        return strSQL;
    }

    public String getSQLRamoAnioActual(int year) {
        String strSQL = "SELECT RAM.RAMO, RAM.DESCR AS RAMO_DESC, RAM.YEAR, RAM.RFC, RAM.HOMOCLAVE "
                + "FROM DGI.RAMOS RAM "
                + "WHERE RAM.YEAR = " + year;
        return strSQL;
    }

    public String getSQLRamoByIdAndYear(int ramoId, int year) {
        String strSQL = "SELECT RAM.RAMO, RAM.DESCR AS RAMO_DESC, RAM.YEAR, RAM.RFC, RAM.HOMOCLAVE, EMP.NOMBRE, "
                + "      (EMP.PATERNO ||' ' || EMP.MATERNO ||' '|| EMP.NOMBRE) AS NOMBRE_C "
                + "FROM DGI.RAMOS RAM, DGI.VW_EMPLEADOS EMP "
                + "WHERE RAM.RAMO = " + ramoId + " AND  "
                + "      RAM.YEAR = " + year + " AND "
                + "      EMP.RFC = RAM.RFC AND "
                + "      EMP.HOMOCLAVE = RAM.HOMOCLAVE";
        return strSQL;
    }

    public String getSQLGetRamoByUsuario(int year, String usuario) {
        String strSQL = "SELECT DISTINCT RAM.RAMO, RAM.DESCR, USR.APP_LOGIN, RAM.CIERRE_PRG, "
                + "                PAR.RAMO,RAM.CIERRE_PRG_AVA, RAM.CIERRE_ACT_AVA "
                + "FROM DGI.RAMOS RAM, POA.VW_ACC_USR_RAMO USR, DGI.PARAMETROS PAR "
                + "WHERE RAM.RAMO = USR.RAMO AND "
                + "      USR.YEAR = RAM.YEAR AND "
                + "      USR.APP_LOGIN = '" + usuario + "' AND "
                + "      RAM.YEAR = " + year
                + " ORDER BY RAM.RAMO";
        return strSQL;
    }

    public String getSQLGetRamoByUsuarioPara(int year, String usuario) {
        String strSQL = "  SELECT DISTINCT DGI.RAMOS.RAMO AS RAMO,   "
                + "         DGI.RAMOS.RAMO||' '||DGI.RAMOS.DESCR AS DESCR "
                + "    FROM DGI.RAMOS,   "
                + "         POA.VW_ACC_USR_RAMO  "
                + "   WHERE ( POA.VW_ACC_USR_RAMO.RAMO = DGI.RAMOS.RAMO ) and  "
                + "         ( DGI.RAMOS.YEAR = POA.VW_ACC_USR_RAMO.YEAR ) and  "
                + "         ( DGI.RAMOS.ING_ACT ='N' OR (SELECT (SELECT count(DISTINCT DGI.DGI_ROL_USR.ROL)  "
                + "        FROM DGI.DGI_ROL_USR  "
                + "        WHERE DGI.DGI_ROL_USR.APP_LOGIN = '" + usuario + "' AND  "
                + "              DGI.DGI_ROL_USR.ROL = DGI.PARAMETRO_PRG.ROL) +"
                + "		 (SELECT count(DISTINCT DGI.DGI_ROL_USR.ROL)  "
                + "        FROM DGI.DGI_ROL_USR  "
                + "        WHERE DGI.DGI_ROL_USR.APP_LOGIN = '" + usuario + "' AND  "
                + "              DGI.DGI_ROL_USR.ROL = DGI.PARAMETROS.ROL_PPTO) +"
                + "		 (SELECT count(DISTINCT DGI.DGI_ROL_USR.ROL)  "
                + "        FROM DGI.DGI_ROL_USR  "
                + "        WHERE DGI.DGI_ROL_USR.APP_LOGIN = '" + usuario + "' AND  "
                + "              DGI.DGI_ROL_USR.ROL = 2) +"
                + "		 (SELECT count(DISTINCT DGI.DGI_ROL_USR.ROL)  "
                + "        FROM DGI.DGI_ROL_USR  "
                + "        WHERE DGI.DGI_ROL_USR.APP_LOGIN = '" + usuario + "' AND "
                + "              DGI.DGI_ROL_USR.ROL = 10) AS li_rol  "
                + "FROM DGI.PARAMETRO_PRG,  "
                + "     DGI.PARAMETROS)>0) and  "
                + "         ( ( POA.VW_ACC_USR_RAMO.APP_LOGIN = '" + usuario + "') AND  "
                + "         ( DGI.RAMOS.YEAR = " + year + " ) )  ORDER BY  DGI.RAMOS.RAMO  ";
        return strSQL;
    }

    public String getSQLGetRamoById(String ramoId, int year, String usuario) {
        String strSQL = "SELECT RAM.RAMO, RAM.DESCR "
                + "FROM DGI.RAMOS RAM, POA.VW_ACC_USR_RAMO USR, DGI.PARAMETROS PAR  "
                + "WHERE RAM.RAMO = USR.RAMO AND  "
                + "    USR.YEAR = RAM.YEAR AND  "
                + "    USR.APP_LOGIN = '" + usuario + "' AND  "
                + "    RAM.YEAR = " + year + " AND "
                + "    RAM.RAMO = '" + ramoId + "' "
                + "ORDER BY RAM.RAMO";
        return strSQL;
    }

    public String getSQLBuscarPersona(String nombre, String apPaterno, String apMaterno) {
        String strSQL = "SELECT NVL(DGI.VW_EMPLEADOS.RFC,' ') AS RFC,   "
                + "        NVL(DGI.VW_EMPLEADOS.HOMOCLAVE,' ') AS HOMOCLAVE,  "
                + "        NVL(DGI.VW_EMPLEADOS.NOMBRE,' ') AS NOMBRE,   "
                + "        NVL(DGI.VW_EMPLEADOS.PATERNO,' ') AS PATERNO,   "
                + "        NVL(DGI.VW_EMPLEADOS.MATERNO,' ') AS MATERNO,   "
                + "        decode(DGI.VW_EMPLEADOS.NOMBRE,NULL,' ',DGI.VW_EMPLEADOS.NOMBRE)|| ' ' ||decode(DGI.VW_EMPLEADOS.PATERNO,NULL,' ' "
                + ", DGI.VW_EMPLEADOS.PATERNO)|| ' ' ||decode(DGI.VW_EMPLEADOS.MATERNO,NULL,' ',DGI.VW_EMPLEADOS.MATERNO) AS C_NOMBRE  "
                + " FROM DGI.VW_EMPLEADOS "
                + " WHERE (DECODE( DGI.VW_EMPLEADOS.NOMBRE,NULL,' ',DGI.VW_EMPLEADOS.NOMBRE) like '%" + nombre + "%' ) AND"
                + "         (DECODE( DGI.VW_EMPLEADOS.PATERNO,NULL,' ',DGI.VW_EMPLEADOS.PATERNO) like '%" + apPaterno + "%' ) AND "
                + "         (DECODE( DGI.VW_EMPLEADOS.MATERNO,NULL,' ',DGI.VW_EMPLEADOS.MATERNO) like '%" + apMaterno + "%' )";
        return strSQL;
    }

    public String getSQLActualizarResponsableRamo(String rfc, String homoclave, int year, int ramoId) {
        String strSQL = "UPDATE DGI.RAMOS RAM SET RAM.RFC = '" + rfc + "', RAM.HOMOCLAVE = '" + homoclave + "' "
                + "WHERE RAM.YEAR = " + year + " AND "
                + "      RAM.RAMO = " + ramoId;
        return strSQL;
    }

    public String getSQLGetPresupuestoIngresoByRamoConcepto(int year, String ramo, String concepto) {
        String strSQL = "SELECT * FROM DGI.POA_PPTO_ING PPI "
                + "WHERE PPI.YEAR = " + year + " AND "
                + "      PPI.RAMO = '" + ramo + "' AND "
                + "      PPI.CONCEPTO = '" + concepto + "'";
        return strSQL;
    }

    public String getSQLGetConceptoIngreso(int year) {
        String strSQL = "SELECT SIC.CONCEPTO AS CONCEPTO,  "
                + "         SIC.CONCEPTO||'-'||SIC.DESCR AS CONCEPTO_DESCR "
                + "FROM S_ING_CONCEP SIC "
                + "WHERE SIC.EJERCICIO = " + year + " "
                + "ORDER BY SIC.CONCEPTO";
        return strSQL;
    }

    public String getSQLUpdateEstimacion(int year, String ramoId, int metaId, int periodo, Double valor) {
        String strSQL = "UPDATE DGI.ESTIMACION EST SET VALOR = " + valor
                + "WHERE EST.YEAR = " + year + " AND "
                + "      EST.RAMO = '" + ramoId + "' AND "
                + "      EST.META = " + metaId + " AND "
                + "      EST.PERIODO = " + periodo;
        return strSQL;
    }

    public String getSQLGetTipoCalculo(int year, String ramoId, String programaId, int proyectoId, int metaId) {
        String strSQL = "SELECT MET.CALCULO FROM S_POA_META MET "
                + "WHERE MET.YEAR = " + year + " AND "
                + "      MET.RAMO = '" + ramoId + "' AND "
                + "      MET.PRG = '" + programaId + "' AND "
                + "      MET.PROY = " + proyectoId + "AND"
                + "      MET.META = " + metaId;
        return strSQL;
    }

    public String getSQLGetEvaluacionMeta(int year) {
        String strSQL = "SELECT EVA.NUMEVAL, EVA.DESCREVAL FROM DGI.EVALUACION EVA  "
                + "WHERE YEAR = " + year;
        return strSQL;
    }

    public String getSQLInsertEstimacion(int year, String ramoId, int metaId, int periodo, Double valor) {
        String strSQL = "INSERT INTO DGI.ESTIMACION EST (EST.META, EST.PERIODO, EST.RAMO, EST.VALOR, EST.YEAR) "
                + "VALUES (" + metaId + "," + periodo + ",'" + ramoId + "'," + valor + ",'" + year + "')";
        return strSQL;
    }

    public String getSQLGetProgramasByRamo(String ramoId, int year, String usuario) {

        String strSQL = "SELECT RPRO.PRG,PRO.DESCR, RPRO.RAMO, USU.APP_LOGIN,PAR.PRG "
                + "FROM POA.RAMO_PROGRAMA RPRO, POA.VW_ACC_USR_RAMO USU, DGI.PARAMETROS PAR, \"PUBLIC\".S_POA_PROGRAMA PRO "
                + "WHERE RPRO.PRG = PRO.PRG AND "
                + "      RPRO.PRG = USU.PRG AND "
                + "      USU.YEAR = RPRO.YEAR AND "
                + "      USU.RAMO = RPRO.RAMO AND"
                + "      PRO.YEAR = RPRO.YEAR AND "
                + "      (RPRO.RAMO = '" + ramoId + "' AND  "
                + "         USU.APP_LOGIN = '" + usuario + "' AND  "
                + "         RPRO.YEAR = " + year + ") "
                + "ORDER BY RPRO.PRG";

        return strSQL;
    }

    public String getSQLGetProgramaById(String ramoId, String programaId, int year, String usuario) {
        String strSQL = "SELECT RPRO.PRG,PRO.DESCR "
                + "FROM POA.RAMO_PROGRAMA RPRO, POA.VW_ACC_USR_RAMO USU, DGI.PARAMETROS PAR, S_POA_PROGRAMA PRO "
                + "WHERE RPRO.PRG = PRO.PRG AND "
                + "      RPRO.PRG = USU.PRG AND"
                + "     RPRO.RAMO = USU.RAMO AND \n"
                + "    PRO.YEAR = RPRO.YEAR AND "
                + "      USU.YEAR = RPRO.YEAR AND "
                + "      RPRO.PRG = '" + programaId + "' AND "
                + "      (RPRO.RAMO = '" + ramoId + "' AND "
                + "         USU.APP_LOGIN = '" + usuario + "' AND "
                + "         RPRO.YEAR = " + year + ") "
                + "ORDER BY RPRO.PRG";
        return strSQL;
    }

    public String getSQLGetProgramaById(String ramoId, String programaId, int year) {
        String strSQL = "SELECT PRO.PRG AS PROG_ID, PPRO.DESCR AS PROG, PRO.YEAR, PRO.OBJETIVO, PRO.RESULTADO, PRO.ASPECTOS, PRO.DEPTO, "
                + "       PRO.RFC, PRO.HOMOCLAVE, PRO.COORD_RFC, PRO.COORD_HOMO, PRO.FIN, NVL(PRO.PROPOSITO,' ') AS PROPOSITO , PRO.TIPO_PROGRAMA, "
                + "       PRO.PONDERADO,PRO.TIPO_OBJ "
                + "FROM POA.RAMO_PROGRAMA PRO, \"PUBLIC\".S_POA_PROGRAMA PPRO "
                + "WHERE PRO.RAMO = '" + ramoId + "' "
                + "      AND PRO.YEAR = " + year + " AND "
                + "      PRO.PRG = '" + programaId + "' AND "
                + "      PRO.PRG = PPRO.PRG  AND "
                + "      PRO.YEAR = PPRO.YEAR";
        return strSQL;
    }

    public String getSQLGetEmpleadoByRFC(String rfc, String homoclave) {
        String strSQL = "SELECT * FROM DGI.VW_EMPLEADOS EMP "
                + "WHERE EMP.RFC = '" + rfc + "' AND "
                + "      EMP.HOMOCLAVE = '" + homoclave + "'";
        return strSQL;
    }

    public String getSQLGetEmpleadoByRamoPrograma(String ramoId, String programaId, int year) {
        String strSQL = "SELECT * FROM DGI.DEPENDENCIA DEP "
                + "WHERE DEP.RAMO = '" + ramoId + "' AND "
                + "      DEP.YEAR = " + year + " AND "
                + "      DEP.DEPTO IN (SELECT COD.DEPTO FROM S_POA_CODPROG COD "
                + "                    WHERE COD.PRG = '" + programaId + "' AND "
                + "                          COD.RAMO = '" + ramoId + "' AND "
                + "                          COD.YEAR = " + year + "  )"
                + " ORDER BY DEPTO";
        return strSQL;
    }

    public String getSQLGetDeptoById(String ramoId, String programaId, int year, String depto) {
        String strSQL = "SELECT DEP.DEPTO, DEP.DESCR FROM DGI.DEPENDENCIA DEP  "
                + "                WHERE DEP.RAMO = '" + ramoId + "' AND  "
                + "                      DEP.YEAR = " + year + " AND "
                + "                      DEP.DEPTO IN (SELECT COD.DEPTO FROM S_POA_CODPROG COD  "
                + "                                    WHERE COD.PRG = '" + programaId + "' AND  "
                + "                                          COD.RAMO = '" + ramoId + "' AND  "
                + "                                          COD.YEAR = " + year + "  ) AND "
                + "                      DEP.DEPTO = '" + depto + "'";
        return strSQL;
    }

//    public String getSQLGetDeptoByRamo(int year, String ramoId) {
//        String strSQL = "SELECT DEP.DEPTO, DEP.DESCR FROM DGI.DEPENDENCIA DEP "
//                + "                WHERE DEP.RAMO = '" + ramoId + "' AND  "
//                + "                     DEP.YEAR = " + year + " "
//                + "                ORDER BY DEP.DEPTO";
//        return strSQL;
//    }
    public String getSQLGetDeptoByRamo(int year, String ramoId, String programaId) {
        String strSQL = "SELECT DEP.DEPTO, DEP.DESCR FROM DGI.DEPENDENCIA DEP  "
                + "                WHERE DEP.RAMO = '" + ramoId + "' AND  "
                + "                      DEP.YEAR = " + year + " AND "
                + "                      DEP.DEPTO IN (SELECT COD.DEPTO FROM S_POA_CODPROG COD  "
                + "                                    WHERE COD.PRG = '" + programaId + "' AND  "
                + "                                          COD.RAMO = '" + ramoId + "' AND  "
                + "                                          COD.YEAR = " + year + "  )";
        return strSQL;
    }

    public String getSQLGetFinRamo(int ramoId, int year) {
        String strSQL = "SELECT * FROM DGI.POA_FIN FIN "
                + "WHERE FIN.RAMO =  '" + ramoId + "' AND "
                + "      FIN.YEAR = " + year + " "
                + "ORDER BY FIN.RAMO";
        return strSQL;
    }

    public String getSQLUpdatePrograma(String programaId, String ramoId, int year, String rfc, String homoclave,
            int fin, String proposito, int ponderado, int depto) {
        String strSQL = "UPDATE POA.RAMO_PROGRAMA PRO "
                + "SET PRO.RFC = '" + rfc + "', "
                + "    PRO.HOMOCLAVE = '" + homoclave + "', "
                + "    PRO.FIN = " + fin + ", "
                + "    PRO.PROPOSITO = '" + proposito + "', "
                + "    PRO.PONDERADO = " + ponderado + ", "
                + "    PRO.DEPTO = " + depto + ""
                + "WHERE PRO.PRG = '" + programaId + "' AND "
                + "      PRO.RAMO = '" + ramoId + "' AND "
                + "      PRO.YEAR = " + year;
        return strSQL;
    }

    public String getSQLLocalidades(int mpo) {
        String strSQL = "SELECT LOC.LOCALIDAD, LOC.DESCR FROM \"PUBLIC\".S_POA_LOCALIDAD LOC "
                + "WHERE LOC.MPO = " + mpo;
        return strSQL;
    }

    public String getSQLGetMetaById(String ramoId, String programaId, int proyectoId, int year, int metaId, String tipoProyecto) {
        String strSQL = "SELECT * FROM S_POA_META META "
                + "WHERE META.YEAR = " + year + " AND "
                + "    META.RAMO = '" + ramoId + "' AND "
                + "    META.PRG = '" + programaId + "' AND "
                + "    META.PROY = " + proyectoId + "  AND "
                + "    META.META =  " + metaId + " AND"
                + "    META.TIPO_PROY = '" + tipoProyecto + "'";
        return strSQL;
    }

    public String getSQLGetMetaById() {
        String strSQL = "SELECT M.RAMO,\n" +
                        "    M.PRG,\n" +
                        "    M.TIPO_PROY,\n" +
                        "    M.PROY,\n" +
                        "    M.META,\n" +
                        "    M.DESCR,\n" +
                        "    M.CALCULO,\n" +
                        "    M.FINALIDAD,\n" +
                        "    M.FUNCION,\n" +
                        "    M.SUBFUNCION,\n" +
                        "    M.CVE_MEDIDA,\n" +
                        "    MM.DESCR DMEDIDA,\n" +
                        "    M.TIPO_COMPROMISO,\n" +
                        "    M.LINEA,\n" +
                        "    M.LINEA_SECTORIAL,\n" +
                        "    M.BENEF_HOMBRE,\n" +
                        "    M.BENEF_MUJER,\n" +
                        "    M.CONVENIO,\n" +
                        "    M.GENERO,\n" +
                        "    M.PONDERADO,\n" +
                        "    M.PROCESO_AUTORIZACION,\n" +
                        "    M.CRITERIO,\n" +
                        "    M.OBRA\n" +
                        "FROM\n" +
                        "    S_POA_META M,\n" +
                        "    S_POA_MEDIDA_META MM\n" +
                        "WHERE \n" +
                        "    M.YEAR = ? \n" +
                        "    AND M.RAMO = ? \n" +
                        "    AND M.META = ? \n" +
                        "    AND MM.YEAR = M.YEAR\n" +
                        "    AND MM.MEDIDA = M.CVE_MEDIDA";
        return strSQL;
    }

    public String getMovoficioEstimacion(){
        return "SELECT \n" +
                "    EST.PERIODO,VALOR,RAMO,META\n" +
                "FROM \n" +
                "    POA.MOVOFICIOS_ESTIMACION EST\n" +
                "WHERE \n" +
                "    EST.OFICIO = ? \n" +
                "    AND EST.RAMO = ? \n" +
                "    AND EST.META = ? ";
    }

    public String getMovoficioMeta(){
        String strSQL = "SELECT \n" +
                        "    M.RAMO, \n" +
                        "    M.PRG, \n" +
                        "    M.TIPO_PROY, \n" +
                        "    M.PROY, \n" +
                        "    M.META, \n" +
                        "    M.DESCR, \n" +
                        "    M.CALCULO, \n" +
                        "    M.FINALIDAD, \n" +
                        "    M.FUNCION, \n" +
                        "    M.SUBFUNCION, \n" +
                        "    M.CVE_MEDIDA, \n" +
                        "    MM.DESCR DMEDIDA, \n" +
                        "    M.TIPO_COMPROMISO, \n" +
                        "    M.LINEA, \n" +
                        "    M.LINEA_SECTORIAL, \n" +
                        "    M.BENEF_HOMBRE, \n" +
                        "    M.BENEF_MUJER, \n" +
                        "    M.CONVENIO, \n" +
                        "    M.GENERO, \n" +
                        "    M.PONDERADO, \n" +
                        "    M.PROCESO_AUTORIZACION, \n" +
                        "    M.CRITERIO, \n" +
                        "    M.OBRA \n" +
                        "FROM \n" +
                        "    POA.MOVOFICIOS_META M, \n" +
                        "    S_POA_MEDIDA_META MM \n" +
                        "WHERE  \n" +
                        "    M.OFICIO = ?  \n" +
                        "    AND M.RAMO = ?  \n" +
                        "    AND M.META = ?  \n" +
                        "    AND MM.YEAR = M.YEAR \n" +
                        "    AND MM.MEDIDA = M.CVE_MEDIDA";
        return strSQL;
    }

    public String getMovoficioAccion(){
        String strSQL = "SELECT  \n" +
                        "    ACC.RAMO, \n" +
                        "    ACC.PRG, \n" +
                        "    ACC.META, \n" +
                        "    ACC.ACCION, \n" +
                        "    ACC.DESCR, \n" +
                        "    ACC.DEPTO, \n" +
                        "    ACC.CVE_MEDIDA,  \n" +
                        "    ACC.GRUPO_POBLACION, \n" +
                        "    ACC.LINEA,  \n" +
                        "    ACC.CALCULO,  \n" +
                        "    ACC.LINEA_SECTORIAL,  \n" +
                        "    ACC.MPO,  \n" +
                        "    ACC.LOCALIDAD,  \n" +
                        "    ACC.BENEF_HOMBRE,  \n" +
                        "    ACC.BENEF_MUJER,  \n" +
                        "    ACC.TIPO_ACCION , \n" +
                        "    MM.DESCR DMEDIDA \n" +
                        "FROM  \n" +
                        "    POA.MOVOFICIOS_ACCION ACC, \n" +
                        "    S_POA_MEDIDA_META MM \n" +
                        "WHERE  \n" +
                        "    ACC.OFICIO = ?    \n" +
                        "    AND ACC.RAMO = ?  \n" +
                        "    AND ACC.META = ? \n" +
                        "    AND ACC.ACCION = ?  \n" +
                        "    AND MM.YEAR = ACC.YEAR \n" +
                        "    AND MM.MEDIDA = ACC.CVE_MEDIDA";
        return strSQL;
    }

    String getSQLgetMovoficiosEstimacionAccion(){
        return "SELECT \n" +
                "    RAMO,META,PERIODO,VALOR \n" +
                "FROM \n" +
                "    POA.MOVOFICIOS_ACCION_ESTIMACION ACE  \n" +
                "WHERE \n" +
                "    ACE.OFICIO = ? \n" +
                "    AND ACE.RAMO = ? \n" +
                "    AND ACE.META = ? \n" +
                "    AND ACE.ACCION = ? \n" +
                "ORDER BY ACE.PERIODO";
    }

    public String getSQLGetObraProyectoActividad(int year, int proyectoId, String tipoProy) {
        String strSQL = "SELECT NVL(PA.OBRA,'N') AS OBRA FROM "
                + "DGI.VW_SP_PROY_ACT PA "
                + "WHERE PA.YEAR = " + year + " AND "
                + "      PA.PROY = " + proyectoId + " AND "
                + "      PA.TIPO_PROY = '" + tipoProy + "'";
        return strSQL;
    }

    public String getSQLGetProyectosByPrograma(String ramo, String programa, int year) {
        String strSQL = "SELECT PROY.PROY,PP.DESCR,  PROY.RFC, PROY.HOMOCLAVE, PROY.TIPO_PROY, PROY.DEPTO_RESP, OBRA "
                + "FROM POA.PROYECTO PROY, "
                + "    DGI.VW_SP_PROY_ACT PP "
                + "WHERE PROY.PRG =   '" + programa + "'   AND  "
                + "   PROY.YEAR =   " + year + "   AND "
                + "   PROY.RAMO =   '" + ramo + "' AND "
                + "   PROY.PROY = PP.PROY AND "
                + "   PROY.TIPO_PROY = PP.TIPO_PROY AND "
                + "   PROY.YEAR = PP.YEAR "
                + "ORDER BY PROY.PROY";
        return strSQL;
    }

    /*
     public String getSQLGetProyectoById(String ramoId, int programaId, int proyectoId, int year) {
     String strSQL = "SELECT PROY.PROY, PROY.DESCR, PROY.RFC, PROY.HOMOCLAVE, PROY.TIPO_PROY, PROY.DEPTO_RESP "
     + "FROM POA.PROYECTO PROY "
     + "WHERE PROY.PRG = " + programaId + " AND "
     + "      PROY.YEAR = " + year + " AND "
     + "      PROY.RAMO = '" + ramoId + "' AND "
     + "      PROY.PROY = " + proyectoId + " "
     + "ORDER BY PROY.PROY";
     return strSQL;
     }
     */
    public String getSQLGetProyectoById(String ramoId, String programaId, int proyectoId, int year, String tipoProy) {
        String strSQL = "SELECT PROY.PROY, PP.DESCR, PROY.RFC, PROY.HOMOCLAVE, PROY.TIPO_PROY, PROY.DEPTO_RESP "
                + "FROM POA.PROYECTO PROY, "
                + "    DGI.VW_SP_PROY_ACT PP "
                + "WHERE PROY.PRG =   '" + programaId + "'   AND  "
                + "   PROY.YEAR =   " + year + "   AND  "
                + "   PROY.RAMO = '" + ramoId + "' AND  "
                + "   PROY.PROY =   " + proyectoId + "   AND "
                + "   PROY.TIPO_PROY = '" + tipoProy + "' AND "
                + "   PROY.PROY = PP.PROY AND "
                + "   PROY.TIPO_PROY = PP.TIPO_PROY AND "
                + "   PROY.YEAR = PP.YEAR "
                + "ORDER BY PROY.PROY";
        return strSQL;
    }

    public String getSQLGetLineasPrograma(String ramoId, String programaId, int year) {
        String strSQL = "SELECT LINE.OBJ, LINE.EST, LIN.LINEA, LINE.DESCR, LINE.TIPO "
                + "FROM POA.RAMO_PRG_LINEA LIN,  "
                + "	 S_POA_SP_LIN_ESTATAL LINE, "
                + "	 DGI.SP_PERIODO_ADMVO PER "
                + "WHERE LIN.LINEA = LINE.LINEA "
                + "AND LINE.YEAR = PER.YEAR "
                + "AND PER.YEAR_FISCAL = LIN.YEAR "
                + "AND LIN.YEAR = " + year + " "
                + "AND LIN.RAMO = '" + ramoId + "' "
                + "AND LIN.PRG = '" + programaId + "'";
        return strSQL;
    }

    public String getSQLGetUnidadMedida(int year) {
        String strSQL = "SELECT MED.MEDIDA, MED.DESCR FROM S_POA_MEDIDA_META MED "
                + "WHERE MED.YEAR = " + year + " AND "
                + "      MED.ACTIVO = 'S' "
                + "ORDER BY MED.DESCR ";
        return strSQL;
    }

    public String getSQLGetUnidadMedidaAccion(int year) {
        String strSQL = "SELECT MED.MEDIDA, MED.DESCR FROM S_POA_MEDIDA_META MED "
                + "WHERE MED.YEAR = " + year + " AND "
                + "      MED.ACTIVO = 'S'"
                + "ORDER BY MED.DESCR ";
        return strSQL;
    }

    public String getSQLGetMunicipio() {
        String strSQL = "SELECT MUN.MPO, MUN.NOMBREMPO FROM S_POA_MUNICIPIO MUN \n"
                + " WHERE MUN.REAL = 'S' "
                + " ORDER BY MUN.MPO";
        return strSQL;
    }

    public String getSQLGetLineaPED(String ramoId, int year) {
        String strSQL = "SELECT * FROM DGI.RAMO_LINEA RL "
                + "WHERE RL.RAMO = '" + ramoId + "' AND "
                + "      RL.YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetLineasProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProy) {
        String strSQL = "SELECT PLIN.LINEA, LINE.DESCR  "
                + "FROM POA.PROYECTO_LINEA PLIN, \"PUBLIC\".S_POA_SP_LIN_ESTATAL LINE "
                + "WHERE PLIN.YEAR = " + year + " AND "
                + "      PLIN.RAMO = '" + ramoId + "' AND "
                + "      PLIN.PRG = '" + programaId + "' AND "
                + "      PLIN.PROY = " + proyectoId + " AND "
                + "      PLIN.LINEA = LINE.LINEA AND "
                + "      PLIN.TIPO_PROY = '" + tipoProy + "'";
        return strSQL;
    }

    public String getSQLGetLineaSectorialByPED(String est, int year, String ramoId, String programa, int proyecto) {
        String strSQL = "SELECT PS.LINEA_SECTORIAL, LS.DESCR "
                + "FROM S_POA_LINEA_SECTORIAL LS, POA.PROYECTO_SECTORIAL PS "
                + "WHERE LS.ESTRATEGIA = '" + est + "' AND "
                + "      LS.LINEA_SECTORIAL = PS.LINEA_SECTORIAL AND "
                + "      LS.ESTRATEGIA = PS.LINEA AND "
                + "      LS.YEAR = DGI.F_OBTIENE_YEAR_ADMVO(" + year + ") AND "
                + "      PS.YEAR = " + year + " AND "
                + "      PS.RAMO = '" + ramoId + "' AND "
                + "      PS.PRG = '" + programa + "' aND "
                + "      PS.PROY = " + proyecto + " "
                + "ORDER BY PS.LINEA_SECTORIAL";
        return strSQL;
    }

    public String getSQLGetLineaSectorialByLineaAccion(String est, int year) {
        String strSQL = "SELECT LS.LINEA_SECTORIAL, LS.DESCR FROM S_POA_LINEA_SECTORIAL LS\n"
                + "WHERE LS.ESTRATEGIA = '" + est + "' AND\n"
                + "      LS.YEAR = DGI.F_OBTIENE_YEAR_ADMVO(" + year + ")";
        return strSQL;
    }

    public String getSQLDeleteAccion(int year, String ramo, String programa, int meta, int accion) {
        String strSQL = "DELETE FROM S_POA_ACCION ACC "
                + "WHERE ACC.YEAR = " + year + " AND "
                + "      ACC.RAMO = '" + ramo + "' AND "
                + "      ACC.PRG = '" + programa + "' AND "
                + "      ACC.META = " + meta + " AND "
                + "      ACC.ACCION = " + accion;
        return strSQL;
    }

    public String getSQLGetTipoCompromiso() {
        return "SELECT TC.TIPO_COMPROMISO, TC.DESCR||': '||TC.DESCR_LARGO AS DESCR\n"
                + "FROM DGI.POA_TIPO_COMPROMISO TC";
    }

    public String getSQLGetLineaSectorialProyecto(String ramoId, String programaId, int proyectoId, int year) {
        String strSQL = "SELECT LINS.ESTRATEGIA, PROS.LINEA_SECTORIAL, LINS.DESCR, PROS.TIPO_PROY  "
                + "FROM S_POA_LINEA_SECTORIAL LINS, POA.PROYECTO_SECTORIAL PROS "
                + "WHERE PROS.RAMO = '" + ramoId + "' AND  "
                + "   PROS.PRG = '" + programaId + "' AND  "
                + "   PROS.PROY =   " + proyectoId + "   AND  "
                + "   PROS.YEAR =   " + year + "   AND "
                + "   PROS.YEAR = LINS.YEAR AND "
                + "   PROs.LINEA_SECTORIAL = LINS.LINEA_SECTORIAL";
        return strSQL;
    }

    public String getSQLGetLineaSectorialRamo(int year, String ramoId) {
        String strSQL = "SELECT LINS.ESTRATEGIA||'.'||PROS.LINEA_SECTORIAL AS LINEA_SECTORIAL, LINS.DESCR, PROS.TIPO_PROY  "
                + "FROM S_POA_LINEA_SECTORIAL LINS, POA.PROYECTO_SECTORIAL PROS "
                + "WHERE PROS.RAMO = '" + ramoId + "'  AND "
                + "   PROS.YEAR = " + year + " AND  "
                + "   PROS.YEAR = LINS.YEAR AND  "
                + "   PROs.LINEA_SECTORIAL = LINS.LINEA_SECTORIAL";
        return strSQL;
    }

    public String getSQLInsertAccion(int metaId, String ramoId, int year, int accionId, String accionDesc,
            String depto, int medida, String tipoCalculo, String lineaPed, int grupoPob, int mpo, int localidad,
            String lineaSectorial, int benefMuj, int benefHom, String programaId) {
        String strSQL = new String();
        if (grupoPob != -1) {
            strSQL = "INSERT INTO POA.ACCION ( ACCION.META, ACCION.RAMO, ACCION.YEAR, "
                    + "ACCION.ACCION, ACCION.DESCR, ACCION.DEPTO, ACCION.MEDIDA, ACCION.CVE_MEDIDA, "
                    + "ACCION.CALCULO, ACCION.LINEA, ACCION.GRUPO_POBLACION, "
                    + "ACCION.MPO, ACCION.LOCALIDAD, ACCION.LINEA_SECTORIAL, ACCION.BENEF_MUJER, ACCION.BENEF_HOMBRE, ACCION.PRG, ACCION.PERIODO) "
                    + "VALUES ( " + metaId + ", '" + ramoId + "', '" + year + "', " + accionId + ", '" + accionDesc + "', '" + depto + "', NULL, "
                    + " " + medida + ", '" + tipoCalculo + "', '" + lineaPed + "', " + grupoPob + ", " + mpo + ","
                    + " " + localidad + ", '" + lineaSectorial + "', " + benefMuj + ", " + benefHom + ",'" + programaId + "', 1 )";
        } else {
            strSQL = "INSERT INTO POA.ACCION ( ACCION.META, ACCION.RAMO, ACCION.YEAR, "
                    + "ACCION.ACCION, ACCION.DESCR, ACCION.DEPTO, ACCION.MEDIDA, ACCION.CVE_MEDIDA, "
                    + "ACCION.CALCULO, ACCION.LINEA, "
                    + "ACCION.MPO, ACCION.LOCALIDAD, ACCION.LINEA_SECTORIAL, ACCION.BENEF_MUJER, ACCION.BENEF_HOMBRE, ACCION.PRG, ACCION.PERIODO) "
                    + "VALUES ( " + metaId + ", '" + ramoId + "', '" + year + "', " + accionId + ", '" + accionDesc + "', '" + depto + "', NULL, "
                    + " " + medida + ", '" + tipoCalculo + "', '" + lineaPed + "', " + mpo + ","
                    + " " + localidad + ", '" + lineaSectorial + "', " + benefMuj + ", " + benefHom + ",'" + programaId + "', 1 )";
        }
        return strSQL;
    }

    public String getSQLGetSumaBeneficiarios(int year, String ramoId, int meta) {
        String strSQL = "SELECT SUM(NVL(MET.BENEF_MUJER,0)) AS MUJ, SUM(NVL(MET.BENEF_HOMBRE,0)) AS HOM "
                + "FROM S_POA_ACCION MET "
                + "WHERE MET.YEAR = " + year + " AND "
                + "      MET.RAMO = '" + ramoId + "' AND "
                + "      MET.META = " + meta;
        return strSQL;
    }

    public String getSQLUpdateBeneficiatiosMeta(int year, String ramoId, String programaId, int meta,
            int[] sumaBenef) {
        String strSQL = "UPDATE POA.META MET SET MET.BENEF_MUJER = " + sumaBenef[0] + ", MET.BENEF_HOMBRE = " + +sumaBenef[1]
                + "WHERE MET.YEAR = " + year + " AND "
                + "      MET.PRG = '" + programaId + "' AND "
                + "      MET.RAMO = '" + ramoId + "' AND "
                + "      MET.META = " + meta;
        return strSQL;
    }

    public String getSQLGetGrupoPoblacional() {
        return "SELECT * FROM S_POA_GPO_POBLACIONAL";
    }

    public String getSQLGetLineasGuardadas(int ramoId, int programaId, int proyectoId, String lineaId, int year) {
        String strSQL = "SELECT 1 AS LINEA FROM POA.PROYECTO_LINEA LIN "
                + "WHERE LIN.RAMO = " + ramoId + " AND "
                + "      LIN.PRG = " + programaId + " AND "
                + "      LIN.PROY = " + proyectoId + " AND "
                + "      LIN.YEAR = " + year + " AND "
                + "      LIN.LINEA = '" + lineaId + "'";
        return strSQL;
    }

    public String getSQLSaveLinea(int year, String ramoId, String programaId, String tipoP, int proyectoId, String lineaId) {
        String strSQL = ""
                + " INSERT INTO "
                + " POA.PROYECTO_LINEA "
                + " ( "
                + "    PROYECTO_LINEA.YEAR, "
                + "    PROYECTO_LINEA.RAMO, "
                + "    PROYECTO_LINEA.PRG, "
                + "    PROYECTO_LINEA.TIPO_PROY, "
                + "    PROYECTO_LINEA.PROY, "
                + "    PROYECTO_LINEA.LINEA "
                + ") "
                + "VALUES"
                + "( "
                + "    '" + year + "', "
                + "    '" + ramoId + "', "
                + "    '" + programaId + "', "
                + "    '" + tipoP + "', "
                + "    " + proyectoId + ", "
                + "    '" + lineaId + "' "
                + ") ";
        return strSQL;
    }

    public String getSQLGetLineaEstatalByID(int year, String lineaId) {
        String strSQL = "SELECT LINE.LINEA, LINE.TIPO "
                + "FROM S_POA_SP_LIN_ESTATAL, DGI.SP_PERIODO_ADMVO PER "
                + "WHERE   PER.YEAR_FISCAL = " + year + " AND "
                + "        LINE.YEAR = PER.YEAR AND "
                + "        LINE.LINEA = '" + lineaId + "'";
        return strSQL;
    }

    public String getSQLDeleteLineasViejas(String ramoId, String programaid, int proyectoId, int year, String tipoProy, String lineaPed) {
        String strSQL = "DELETE FROM POA.PROYECTO_LINEA LIN "
                + "WHERE LIN.RAMO = '" + ramoId + "' AND "
                + "      LIN.PRG = '" + programaid + "' AND "
                + "      LIN.PROY = " + proyectoId + " AND "
                + "      LIN.TIPO_PROY = '" + tipoProy + "' AND "
                + "      LIN.LINEA= '" + lineaPed + "' AND "
                + "      LIN.YEAR = " + year;
        return strSQL;
    }

    public String getSQLUpdateProyecto(String rfc, String homoclave, int depto, int ramoId, int programaId,
            int proyectoId, int year) {
        String strSQL = "UPDATE POA.PROYECTO PROY SET PROY.RFC = '" + rfc + "', PROY.HOMOCLAVE = '" + homoclave + "', PROY.DEPTO_RESP = '" + depto + "' "
                + "WHERE PROY.RAMO = " + ramoId + " AND "
                + "      PROY.PRG = " + programaId + " AND "
                + "      PROY.PROY = " + proyectoId + " AND "
                + "      PROY.YEAR = " + year;
        return strSQL;
    }

    public String getSQLUpdateAccion(int year, String ramoId, int metaId, int accionId, String accDesc, String depto,
            int medida, String calculo, String linea, int grupoPob, int mpo, int localidad, String lineaSect, int benefMuj, int benefHom) {
        String strSQL = new String();
        if (grupoPob != -1) {
            strSQL = "UPDATE S_POA_ACCION ACC SET ACC.DESCR = '" + accDesc + "', ACC.DEPTO = '" + depto + "', "
                    + "        ACC.CVE_MEDIDA = " + medida + ", ACC.CALCULO = '" + calculo + "' , ACC.LINEA = '" + linea + "', ACC.GRUPO_POBLACION = '" + grupoPob + "', "
                    + "        ACC.MPO = " + mpo + ", ACC.LOCALIDAD = " + localidad + " , ACC.LINEA_SECTORIAL = '" + lineaSect + "'"
                    + "          , ACC.BENEF_HOMBRE = " + benefHom + ", ACC.BENEF_MUJER = " + benefMuj
                    + " WHERE ACC.YEAR = " + year + " AND "
                    + "      ACC.RAMO = '" + ramoId + "' AND "
                    + "      ACC.META = " + metaId + " AND "
                    + "      ACC.ACCION = " + accionId;
        } else {
            strSQL = "UPDATE S_POA_ACCION ACC SET ACC.DESCR = '" + accDesc + "', ACC.DEPTO = '" + depto + "', "
                    + "        ACC.CVE_MEDIDA = " + medida + ", ACC.CALCULO = '" + calculo + "' , ACC.LINEA = '" + linea + "', "
                    + "        ACC.MPO = " + mpo + ", ACC.LOCALIDAD = " + localidad + " , ACC.LINEA_SECTORIAL = '" + lineaSect + "'"
                    + "          , ACC.BENEF_HOMBRE = " + benefHom + ", ACC.BENEF_MUJER = " + benefMuj
                    + " WHERE ACC.YEAR = " + year + " AND "
                    + "      ACC.RAMO = '" + ramoId + "' AND "
                    + "      ACC.META = " + metaId + " AND "
                    + "      ACC.ACCION = " + accionId;
        }
        return strSQL;
    }

    public String getSQLValidacionRamo(String ramoId, int year) {
        String strSQL = "SELECT 1 AS VALIDA FROM DGI.RAMOS RAM "
                + "WHERE "
                + "      RAM.YEAR = " + year + " AND "
                + "      RAM.RAMO = '" + ramoId + "' AND"
                + "      RAM.CIERRE_PRG = 'S' ";
        return strSQL;
    }

    public String getSQLValidacionRamoCierre(String ramoId, int year) {
        String strSQL = "SELECT 1 AS VALIDA FROM DGI.RAMOS RAM "
                + "WHERE RAM.CIERRE_POA = 'S' AND "
                + "      RAM.YEAR = '" + year + "' AND "
                + "      RAM.RAMO = '" + ramoId + "'  ";
        return strSQL;
    }

    public String getSQLgetRolesPrg() {
        String strSQL = "SELECT P.ROL_PPTO, PP.ROL "
                + "FROM DGI.PARAMETRO_PRG PP, DGI.PARAMETROS P";
        return strSQL;
    }

    public String getSQLGetLineaSectorial(int year) {
        String strSQL = "SELECT LINS.LINEA_SECTORIAL, LINS.DESCR "
                + "FROM S_POA_LINEA_SECTORIAL LINS "
                + "WHERE LINS.YEAR = " + year;
        return strSQL;
    }

    public String getSQLDeleteLineaSectorial(String ramoId, String programaId, int proyectoId, int year, String lineaSectorial, String tipoProy, String estrategia) {
        String strSQL = "DELETE FROM POA.PROYECTO_SECTORIAL PROS "
                + "WHERE PROS.RAMO = '" + ramoId + "' AND "
                + "      PROS.PRG = '" + programaId + "' AND "
                + "      PROS.PROY = " + proyectoId + " AND "
                + "      PROS.TIPO_PROY = '" + tipoProy + "' AND "
                + "      PROS.LINEA_SECTORIAL = '" + lineaSectorial + "' AND "
                + "      PROS.LINEA = '" + estrategia + "' AND "
                + "      PROS.YEAR = " + year;
        return strSQL;
    }

    public String getSQLInsertLineaSectorial(String ramoId, String programaId, int proyectoId, int year, String tipoP, String lineaId, String estrategia) {
        String strSQL = "INSERT INTO POA.PROYECTO_SECTORIAL ( PROYECTO_SECTORIAL.YEAR, PROYECTO_SECTORIAL.RAMO, PROYECTO_SECTORIAL.PRG,"
                + " PROYECTO_SECTORIAL.TIPO_PROY, PROYECTO_SECTORIAL.PROY, PROYECTO_SECTORIAL.LINEA_SECTORIAL, PROYECTO_SECTORIAL.LINEA ) "
                + "VALUES ( '" + year + "', '" + ramoId + "', '" + programaId + "', '" + tipoP + "', " + proyectoId + ", '" + lineaId + "', '" + estrategia + "' )";
        return strSQL;
    }

    public String getSQLUpdateMeta(int year, String ramoId, String programaId, int proyectoId, int metaId, int medida, String calculo, String linea, int compromiso,
            int benefH, int benefM, String principal, String objMeta, int grupoPob, int ponderado, String lineaSect,
            String finalidad, String funcion, String subfuncion, String autorizacion, int criterio, String obra) {
        String strSQL = "UPDATE POA.META MET SET MET.CVE_MEDIDA = " + medida + ", MET.CALCULO = '" + calculo + "' , MET.LINEA = '" + linea + "', MET.TIPO_COMPROMISO = " + compromiso + ", "
                + "                        MET.PRINCIPAL = '" + principal + "', MET.DESCR = '" + objMeta + "', "
                + "                        MET.PONDERADO = " + ponderado + ", MET.LINEA_SECTORIAL = '" + lineaSect + "' , "
                + "                        MET.FINALIDAD = " + finalidad + " ,MET.FUNCION = " + funcion + " ,MET.SUBFUNCION = " + subfuncion + ""
                + "                        ,MET.PROCESO_AUTORIZACION = '" + autorizacion + "', MET.CRITERIO = " + criterio + ", MET.OBRA = '" + obra + "'"
                + " WHERE MET.YEAR = " + year + " AND "
                + "    MET.RAMO = '" + ramoId + "' AND "
                + "    MET.PRG = '" + programaId + "' AND "
                + "    MET.PROY = '" + proyectoId + "' AND "
                + "    MET.META = '" + metaId + "'";
        return strSQL;
    }

    public String getSQLGetMetas(String ramoId, String programaId, int proyectoId, int year, String tipoProyecto) {
        String strSQL = "SELECT * FROM S_POA_META META  "
                + " WHERE META.YEAR =   " + year + "  AND "
                + "       META.RAMO = '" + ramoId + "' AND "
                + "       META.PRG =   '" + programaId + "'   AND "
                + "       META.PROY =   " + proyectoId + " AND "
                + "       META.TIPO_PROY = '" + tipoProyecto + "' "
                + " ORDER BY META.META";
        return strSQL;
    }

    public String getSQLGetMetasAvance(String ramoId, String programaId, int proyectoId, int year, String tipoProyecto) {
        String strSQL = "SELECT * FROM S_POA_META META  "
                + " WHERE META.YEAR =   " + year + "  AND "
                + "       META.RAMO = '" + ramoId + "' AND "
                + "       META.PRG =   '" + programaId + "'   AND "
                + "       META.PROY =   " + proyectoId + " AND "
                + "       META.TIPO_PROY = '" + tipoProyecto + "' "
                + "       AND META.PERIODO IS NOT NULL \n"
                + " ORDER BY META.META";
        return strSQL;
    }

    public String getSQLGetMaxMeta(int year, String ramoId) {
        String strSQL = "SELECT NVL((MAX(MET.META)+1),1)  AS METAID "
                + "FROM S_POA_META MET "
                + "WHERE MET.YEAR = " + year + " AND  "
                + "      MET.RAMO =  '" + ramoId + "'";
        return strSQL;
    }

    public String getSQLMaxAccion(int year, String ramoId, int meta) {
        String strSQL = "SELECT NVL((MAX(ACC.ACCION)+1),1) as MAX_ACC "
                + "FROM S_POA_ACCION ACC "
                + "WHERE ACC.YEAR = " + year + " AND "
                + "      ACC.RAMO = '" + ramoId + "' AND "
                + "      ACC.META = " + meta;
        return strSQL;
    }

    public String getSQLMaxRequerimiento(int year, String ramoId, int metaId, int accionId) {
        String strSQL = "SELECT NVL((MAX(AR.REQUERIMIENTO)+1),1) AS MAX_REQ FROM S_POA_ACCION_REQ AR "
                + "WHERE AR.YEAR = " + year + " AND "
                + "      AR.RAMO = '" + ramoId + "' AND "
                + "      AR.META = " + metaId + " AND "
                + "      AR.ACCION = " + accionId;
        return strSQL;
    }

    public String getSQLGetPartidas(int year) {
        String strSQL = "SELECT PAR.PARTIDA, PAR.DESCR "
                + "FROM DGI.PARTIDA PAR "
                + "WHERE YEAR = " + year + " AND "
                + "      POA = 'S' "
                + "ORDER BY PAR.PARTIDA";
        return strSQL;
    }

    public String getSQLGetPartidasPara(int year) {
        String strSQL = "SELECT PAR.PARTIDA, PAR.DESCR "
                + "FROM DGI.PARTIDA PAR "
                + "WHERE YEAR = " + year + " AND "
                + "      POA = 'S' "
                + "ORDER BY PAR.PARTIDA";
        return strSQL;
    }

    public String getSQLvalidaRelacionLabora(String relLaboral) {
        String strSQL = "SELECT COUNT(1) AS REL FROM S_POA_REL_LABORAL S "
                + "WHERE S.REL_LABORAL = '" + relLaboral + "' ";
        return strSQL;
    }

    public String getSQLGetPartidasParaestatal(int year) {
        String strSQL = "SELECT PAR.PARTIDA, PAR.DESCR "
                + "FROM DGI.PARTIDA PAR "
                + "WHERE YEAR = " + year + " AND "
                + "      POA = 'S' "
                + "ORDER BY PAR.PARTIDA";
        return strSQL;
    }

    public String getSQLgetPartidasGeneral(int year) {
        String strSQL = "SELECT P.PARTIDA, P.DESCR FROM DGI.PARTIDA P\n"
                + "WHERE P.YEAR = " + year + " ORDER BY P.PARTIDA ";
        return strSQL;
    }

    public String getSQLisParaestatal() {
        String strSQL = "SELECT PAR.PARAESTATAL FROM DGI.PARAMETROS PAR  ";
        return strSQL;
    }

    public String getSQLDeleteMeta(int year, String ramoId, String programaId, int proyectoId, int metaId, String tipoProyecto) {
        String strSQL = "DELETE FROM S_POA_META MET "
                + "WHERE MET.YEAR = " + year + " AND "
                + "      MET.RAMO = '" + ramoId + "' AND "
                + "      MET.PRG = '" + programaId + "' AND "
                + "      MET.PROY = '" + proyectoId + "' AND "
                + "      MET.META = " + metaId + " AND "
                + "      MET.TIPO_PROY = '" + tipoProyecto + "'";
        return strSQL;
    }

    public String getSQLGetEstimacionByMeta(int year, String ramoId, int metaId) {
        String strSQL = "SELECT * FROM DGI.ESTIMACION EST "
                + "WHERE EST.YEAR = " + year + " AND "
                + "      EST.RAMO = '" + ramoId + "' AND "
                + "      EST.META = " + metaId;
        return strSQL;
    }

    public String getSQLGetCountEstimacionMeta(int year, String ramo, int meta) {
        String strSQL = "SELECT NVL(SUM(EST.VALOR),0) AS VALOR FROM DGI.ESTIMACION EST\n"
                + "WHERE EST.YEAR = " + year + " AND "
                + "   EST.RAMO = '" + ramo + "' AND "
                + "   EST.META = " + meta;
        return strSQL;
    }

    public String getSQLValidaCountEstimacionMeta(int year, String ramo, int meta) {
        String strSQL = "SELECT count(1)  "
                + "FROM "
                + "    DGI.ESTIMACION "
                + "WHERE "
                + "    META = " + meta + " "
                + "    AND RAMO = '" + ramo + "' "
                + "    AND YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetTipoCalculo() {
        return "SELECT * FROM POA.TIPO_CALCULO";
    }

    public String getSQLGetEstimacionByAccion(int year, String ramoId, int metaId, int accionId) {
        String strSQL = "SELECT * FROM DGI.ACCION_ESTIMACION ACE "
                + "WHERE ACE.YEAR = " + year + " AND "
                + "      ACE.RAMO = '" + ramoId + "' AND "
                + "      ACE.META = " + metaId + " AND "
                + "      ACE.ACCION = " + accionId + " "
                + "      ORDER BY ACE.PERIODO";
        return strSQL;
    }

    public String getSQLGetCalculoEstimacionAccion(int year, String ramoId, int metaId, int accionId) {
        String strSQL = "SELECT ACC.CALCULO FROM S_POA_ACCION ACC "
                + "WHERE ACC.YEAR = " + year + " AND "
                + "      ACC.RAMO = '" + ramoId + "' AND "
                + "      ACC.META = " + metaId + " AND "
                + "      ACC.ACCION = " + accionId;

        return strSQL;
    }

    public String getSQLGetRequerimientosByAccion(int year, String ramoId, String programaId,
            int metaId, int accionId) {
        String strSQL = "SELECT REQ.REQUERIMIENTO, REQ.DESCR, REQ.PARTIDA, REQ.COSTO_ANUAL, "
                + "REQ.FUENTE||'.'||REQ.FONDO||'.'||REQ.RECURSO AS FUENTE,R.DESCR AS F_DESC "
                + "FROM S_POA_ACCION_REQ REQ, S_POA_RECURSO R "
                + "WHERE REQ.YEAR =   " + year + "   AND "
                + "   REQ.RAMO = '" + ramoId + "' AND  "
                + "   REQ.PRG = '" + programaId + "' AND "
                + "   REQ.META = " + metaId + " AND "
                + "   REQ.ACCION = " + accionId + " AND "
                + "   R.FONDO = REQ.FONDO AND "
                + "   R.FUENTE = REQ.FUENTE AND "
                + "   R.RECURSO = REQ.RECURSO AND "
                + "   R.YEAR = REQ.YEAR "
                + "ORDER BY FUENTE,REQ.PARTIDA, REQ.REQUERIMIENTO";
        return strSQL;
    }

    public String getSQLGetTotalLineasPED(int year, String ramoId, String programaId, int proyectoId) {
        String strSQL = "SELECT count(*) AS LINEAS FROM POA.PROYECTO_LINEA LIN "
                + "WHERE LIN.YEAR = " + year + " AND "
                + "      LIN.RAMO = " + ramoId + " AND "
                + "      LIN.PRG = " + programaId + " AND "
                + "      LIN.PROY = " + proyectoId;
        return strSQL;
    }

    public String getSQLGetTotalLineasSectorial(int year, String ramoId, String programaId, int proyectoId) {
        String strSQL = "SELECT COUNT(*) AS SECTORIAL FROM POA.PROYECTO_SECTORIAL SEC "
                + "WHERE SEC.YEAR = " + year + " AND "
                + "      SEC.RAMO = " + ramoId + " AND "
                + "      SEC.PRG = " + programaId + " AND "
                + "      SEC.PROY = " + proyectoId;
        return strSQL;
    }

    public String getSQLInsertAccionEstimacion(int year, String ramoId, int metaId, int accionId, int periodo, Double valor) {
        String strSQL = "INSERT INTO DGI.ACCION_ESTIMACION "
                + "( ACCION_ESTIMACION.META, ACCION_ESTIMACION.RAMO, ACCION_ESTIMACION.YEAR, ACCION_ESTIMACION.ACCION, "
                + "ACCION_ESTIMACION.PERIODO, ACCION_ESTIMACION.VALOR ) VALUES ( " + metaId + ", '" + ramoId + "', '" + year + "'"
                + ", " + accionId + ", " + periodo + ", " + valor + " )";
        return strSQL;
    }

    public String getSQLUpdateAccionEstimacion(int year, String ramoId, int metaId, int accionId, Double valor, int periodo) {
        String strSQL = "UPDATE DGI.ACCION_ESTIMACION ACE "
                + "SET ACE.VALOR = " + valor + " "
                + "WHERE ACE.YEAR  = " + year + " AND "
                + "      ACE.RAMO = '" + ramoId + "' AND "
                + "      ACE.META = " + metaId + " AND "
                + "      ACE.ACCION = " + accionId + " AND "
                + "      ACE.PERIODO = " + periodo;
        return strSQL;
    }

    public String getSQLGetAccionFuente(int year, String ramoId, int metaId, int accionId) {
        String strSQL = "SELECT AF.FUENTE, FU.DESCR FROM POA.ACCION_FINANCIAMIENTO AF, \"PUBLIC\".S_POA_FUENTE FU "
                + "WHERE AF.YEAR = " + year + " AND      "
                + "AF.RAMO = '" + ramoId + "' AND      "
                + "AF.META = " + metaId + " AND      "
                + "AF.ACCION = " + accionId + " AND "
                + "FU.FUENTE = AF.FUENTE "
                + "ORDER BY FU.FUENTE";
        return strSQL;
    }

    public String getSQLGetFuenteFinanciamiento(int year) {
        String strSQL = "SELECT FU.FUENTE, FU.DESCR FROM S_POA_FUENTE FU "
                + "WHERE FU.YEAR = " + year + " "
                + "ORDER BY FU.FUENTE";
        return strSQL;
    }

    public String getSQLInsertAccionFuente(int year, String ramo, int meta, int accion, int fuente, String programa) {
        String strSQL = "INSERT INTO POA.ACCION_FINANCIAMIENTO ( ACCION_FINANCIAMIENTO.YEAR, ACCION_FINANCIAMIENTO.RAMO, "
                + "ACCION_FINANCIAMIENTO.META, ACCION_FINANCIAMIENTO.ACCION, ACCION_FINANCIAMIENTO.FUENTE, ACCION_FINANCIAMIENTO.PRG ) "
                + "VALUES ( " + year + ", '" + ramo + "', '" + meta + "', " + accion + ", " + fuente + ", '" + programa + "' )";
        return strSQL;
    }

    public String getSQLGetAcciones(int year, String ramoId, int metaId) {
        String strSQL = "SELECT * FROM S_POA_ACCION AC  "
                + "WHERE AC.YEAR = " + year + " AND "
                + "      AC.RAMO = '" + ramoId + "' AND "
                + "      AC.META = " + metaId + " "
                + "ORDER BY AC.ACCION";
        return strSQL;
    }

    public String getSQLGetAccionesAvance(int year, String ramoId, int metaId) {
        String strSQL = "SELECT * FROM S_POA_ACCION AC "
                + "WHERE AC.YEAR = " + year + " AND "
                + "      AC.RAMO = '" + ramoId + "' AND "
                + "      AC.META = " + metaId + " AND  "
                + "      AC.PERIODO IS NOT NULL "
                + "ORDER BY AC.ACCION";
        return strSQL;
    }

    public String getSQLInsertMeta(int metaId, String ramoId, String programaId, int year,
            int cvMedida, int proyectoId, String calculo, String tipoProy, String depto, String clave,
            String lineaPed, int tipoCompr, int benefM, int benefH, String principal, String objComp,
            int grupoPoblacion, int ponderado, String lineaSectorial, int finalidad, int funcion,
            int subfuncion, String autorizacion, int criterio, String obra) {
        String strSQL = "INSERT INTO POA.META ( META.META, META.RAMO, META.YEAR, "
                + "META.PRG, META.DESCR, META.MEDIDA, "
                + "META.CALCULO, META.TIPO, META.CVE_MEDIDA, META.TIPO_PROY, META.PROY, "
                + "META.DEPTO, META.CLAVE, META.LINEA, META.TIPO_COMPROMISO, META.BENEFICIADOS, "
                + "META.PRESUPUESTAR, META.APROB_CONGRESO, META.CONVENIO, META.CONV, "
                + "META.BENEF_HOMBRE, META.BENEF_MUJER, META.GENERO, META.PRINCIPAL, "
                + "META.RELATORIA, META.DESCR_CORTA, META.MAYOR_COSTO, "
                + "META.FICHA_TECNICA, META.CRITERIO, META.OBJ, META.OBJ_META,"
                + "META.PONDERADO, META.LINEA_SECTORIAL, META.FINALIDAD, META.FUNCION, META.SUBFUNCION,"
                + "META.PROCESO_AUTORIZACION, META.OBRA, META.PERIODO ) "
                + "VALUES ( " + metaId + ", '" + ramoId + "', '" + year + "', '" + programaId + "',"
                + " '" + objComp + "', NULL, '" + calculo + "', NULL, " + cvMedida + ", '" + tipoProy + "', "
                + " " + proyectoId + ", '" + depto + "', NULL, '" + lineaPed + "', " + tipoCompr + ", NULL, 'S', 'N', 'N', NULL, 0, "
                + " 0, 'N', '" + principal + "', NULL, NULL, NULL, NULL, " + criterio + ", NULL, NULL, "
                + ponderado + ", '" + lineaSectorial + "', '" + finalidad + "', '" + funcion + "', '" + subfuncion + "','" + autorizacion + "','" + obra + "', 1 )";
        return strSQL;
    }

    public String getSQLGetClasificacionFuncional(String ramoId, String programaId, int proyecto, int year, String tipoProyecto) {
        String strSQL = " SELECT PF.FINALIDAD||'.'||PF.FUNCION||'.'||PF.SUBFUNCION AS CLASIF, SUB.DESCR "
                + "                 FROM POA.PROYECTO_FUNCIONAL PF, \"PUBLIC\".S_POA_SUBFUNCION SUB "
                + "                 WHERE PF.RAMO = '" + ramoId + "' AND "
                + "                       PF.PRG = '" + programaId + "' AND "
                + "                       PF.PROY = " + proyecto + " AND "
                + "                       PF.YEAR = " + year + " AND "
                + "                       PF.TIPO_PROY = '" + tipoProyecto + "' AND "
                + "                       PF.FINALIDAD = SUB.FINALIDAD AND "
                + "                       PF.FUNCION = SUB.FUNCION AND "
                + "                       PF.SUBFUNCION = SUB.SUBFUNCION AND "
                + "                       PF.YEAR = SUB.YEAR";
        return strSQL;
    }

    public String getSQLDeleteFuenteFinanciamiento(int year, String ramoId, int metaId, int accionId) {
        String strSQL = "DELETE "
                + "FROM POA.ACCION_FINANCIAMIENTO AF "
                + "WHERE AF.YEAR = " + year + " AND "
                + "      AF.RAMO = '" + ramoId + "' AND "
                + "      AF.META = " + metaId + " AND "
                + "      AF.ACCION = " + accionId + " ";
        return strSQL;
    }

    public String getSQLTotalAccionFIN(int year, String ramoId, int metaId, int accionId) {
        String strSQL = "SELECT COUNT(*) AS TOT_ACC "
                + "FROM POA.ACCION_FINANCIAMIENTO AF "
                + "WHERE AF.YEAR = " + year + " AND "
                + "      AF.RAMO = '" + ramoId + "' AND "
                + "      AF.META = " + metaId + " AND "
                + "      AF.ACCION = " + accionId;
        return strSQL;
    }

    public String getSQLGetAccionById() {
        String strSQL = "SELECT \n" +
                        "    ACC.RAMO,\n" +
                        "    ACC.PRG,\n" +
                        "    ACC.META,\n" +
                        "    ACC.ACCION,\n" +
                        "    ACC.DESCR,\n" +
                        "    ACC.DEPTO,\n" +
                        "    ACC.CVE_MEDIDA, \n" +
                        "    ACC.GRUPO_POBLACION, \n" +
                        "    ACC.LINEA, \n" +
                        "    ACC.CALCULO, \n" +
                        "    ACC.LINEA_SECTORIAL, \n" +
                        "    ACC.MPO, \n" +
                        "    ACC.LOCALIDAD, \n" +
                        "    ACC.BENEF_HOMBRE, \n" +
                        "    ACC.BENEF_MUJER, \n" +
                        "    ACC.TIPO_ACCION ,\n" +
                        "    MM.DESCR DMEDIDA\n" +
                        "FROM \n" +
                        "    S_POA_ACCION ACC,\n" +
                        "    S_POA_MEDIDA_META MM\n" +
                        "WHERE \n" +
                        "    ACC.YEAR = ?   \n" +
                        "    AND ACC.RAMO = ? \n" +
                        "    AND ACC.META = ? \n" +
                        "    AND ACC.ACCION = ? \n" +
                        "    AND MM.YEAR = ACC.YEAR\n" +
                        "    AND MM.MEDIDA = ACC.CVE_MEDIDA";
        return strSQL;
    }

    public String getSQLGetProgramaConac(String programa, int year) {
        String strSQL = "SELECT "
                + " PROGCONAC.YEAR, "
                + " PROGCONAC.PRG_CONAC, "
                + " PROGCONAC.DESCR "
                + " FROM "
                + " S_POA_PROGRAMA_CONAC PROGCONAC, "
                + " S_POA_PROGRAMA PROG "
                + " WHERE "
                + " PROG.PRG = '" + programa + "' "
                + " AND PROG.YEAR = " + year + " "
                + " AND PROGCONAC.YEAR = PROG.YEAR "
                + " AND PROGCONAC.PRG_CONAC = PROG.PRG_CONAC ";

        return strSQL;
    }

    public String getSQLGetParametroPrgByRol(int rol) {
        String strSQL = "SELECT "
                + " PRMPRG.ROL "
                + " FROM "
                + " DGI.PARAMETRO_PRG PRMPRG "
                + " WHERE "
                + " PRMPRG.ROL = " + rol + " ";

        return strSQL;
    }

    public String getSQLGetAccionTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente) {
        String strSQL = "SELECT ACT.TIPO_GASTO,TG.DESCR "
                + "FROM POA.ACCION_TIPO_GASTO ACT, S_POA_TIPO_GASTO TG "
                + "WHERE ACT.YEAR = " + year + " AND "
                + "      ACT.RAMO = '" + ramoId + "' AND "
                + "      ACT.META = " + metaId + " AND "
                + "      ACT.ACCION = " + accionId + " AND "
                + "      ACT.FUENTE = " + fuente + " AND "
                + "      ACT.TIPO_GASTO = TG.TIPO_GASTO "
                + "ORDER BY ACT.TIPO_GASTO";
        return strSQL;
    }

    public String getSQLGetTipoGasto(int year) {
        String strSQL = "SELECT * FROM \"PUBLIC\".S_POA_TIPO_GASTO TG "
                + "WHERE TG.YEAR = " + year + " "
                + "ORDER BY TG.TIPO_GASTO";
        return strSQL;
    }

    public String getSQLGetTotalTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente) {
        String strSQL = "SELECT COUNT(*) AS TIPO_GASTO "
                + "FROM POA.ACCION_TIPO_GASTO ACT "
                + "WHERE ACT.YEAR = " + year + " AND "
                + "      ACT.RAMO = '" + ramoId + "' AND "
                + "      ACT.META = " + metaId + " AND "
                + "      ACT.ACCION = " + accionId + " AND "
                + "      ACT.FUENTE = " + fuente;
        return strSQL;
    }

    public String getSQLInsertAccionTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente, int gasto, String programa) {
        String strSQL = "INSERT INTO POA.ACCION_TIPO_GASTO ( ACCION_TIPO_GASTO.YEAR, ACCION_TIPO_GASTO.RAMO, "
                + "ACCION_TIPO_GASTO.META, ACCION_TIPO_GASTO.ACCION, ACCION_TIPO_GASTO.FUENTE, ACCION_TIPO_GASTO.TIPO_GASTO, ACCION_TIPO_GASTO.PRG ) "
                + "VALUES ( '" + year + "', '" + ramoId + "', " + metaId + ", " + accionId + ", " + fuente + ", '" + gasto + "','" + programa + "' )";
        return strSQL;
    }

    public String getSQLDeleteAccionTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente) {
        String strSQL = "DELETE FROM POA.ACCION_TIPO_GASTO ACT "
                + "WHERE ACT.YEAR = " + year + " AND "
                + "      ACT.RAMO = '" + ramoId + "' AND "
                + "      ACT.META = " + metaId + " AND "
                + "      ACT.ACCION = " + accionId + " AND "
                + "      ACT.FUENTE = " + fuente;
        return strSQL;
    }

    public String getSQLGetFinOnRamoPrograma(String ramoId, int year) {
        String strSQL = "SELECT "
                + " F.FIN, "
                + " F.YEAR, "
                + " F.DESCR, "
                + " F.NOMBRE "
                + " FROM "
                + " \"PUBLIC\".S_POA_FIN F, "
                + " POA.RAMO_FIN RF "
                + " WHERE "
                + "     RF.RAMO = '" + ramoId + "' "
                + "     AND RF.YEAR = " + year + " "
                + "     AND F.FIN = RF.FIN "
                + "     AND F.YEAR = RF.YEAR ";
        return strSQL;
    }

    public String getSQLIsArticuloPartida(int year, String partida) {
        String strSQL = "SELECT COUNT(*) AS CONT "
                + "FROM S_POA_ARTICULO_PARTIDA ARP "
                + "WHERE ARP.PARTIDA = '" + partida + "' AND "
                + "      ARP.YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetArticuloDescr(int year, String partida, int articulo) {
        String strSQL = "SELECT ART.ARTICULO, ART.DESCR FROM S_POA_ARTICULO_PARTIDA ART\n"
                + "WHERE ART.ARTICULO = " + articulo + " AND "
                + "      ART.PARTIDA = '" + partida + "' AND "
                + "      ART.YEAR = " + year;
        return strSQL;
    }

    public String getSQLisRelacionLaboral(int year, String partida) {
        String strSQL = "SELECT PAR.RELACION_LABORAL FROM DGI.PARTIDA PAR "
                + "WHERE PAR.PARTIDA = '" + partida + "' AND "
                + "      PAR.YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetRequerimientoPresupPlantilla(Ppto pres) {
        String strSQL = "SELECT PP.ORIGEN, PP.YEAR, PP.RAMO, PP.DEPTO, PP.FINALIDAD, PP.FUNCION, PP.SUBFUNCION, PP.PRG_CONAC, PP.PRG,PP.TIPO_PROY,PP.PROY, "
                + "        PP.META, PP.ACCION, PP.PARTIDA, PP.TIPO_GASTO,PP.FONDO, PP.FUENTE, PP.RECURSO, PP.MUNICIPIO, PP.DELEGACION, PP.REL_LABORAL, TO_NUMBER(TRIM(PP.MES)) AS MES, PP.CANTIDAD "
                + "FROM POA.PRESUP_PLANTILLA PP "
                + "WHERE PP.YEAR = " + pres.getYear() + " AND "
                + "      PP.RAMO = '" + pres.getRamo() + "' AND "
                + "      PP.DEPTO = " + pres.getDepto() + " AND "
                + "      PP.FINALIDAD = '" + pres.getFinalidad() + "' AND "
                + "      PP.FUNCION = '" + pres.getFuncion() + "' AND "
                + "      PP.SUBFUNCION = '" + pres.getSubfuncion() + "' AND "
                + "      PP.PRG_CONAC = '" + pres.getPrgConac() + "' AND "
                + "      PP.PRG = '" + pres.getPrg() + "' AND "
                + "      PP.TIPO_PROY = '" + pres.getTipoProy() + "' AND "
                + "      PP.PROY = " + pres.getProyecto() + " AND "
                + "      PP.META = " + pres.getMeta() + " AND "
                + "      PP.ACCION = " + pres.getAccion() + " AND "
                + "      PP.PARTIDA = '" + pres.getPartida() + "' AND "
                + "      PP.TIPO_GASTO = '" + pres.getTipoGasto() + "' AND "
                + "      PP.FONDO = '" + pres.getFondo() + "' AND "
                + "      PP.FUENTE = '" + pres.getFuente() + "' AND "
                + "      PP.RECURSO = '" + pres.getRecurso() + "' AND "
                + "      PP.MUNICIPIO = '" + pres.getMunicipio() + "' AND "
                + "      PP.DELEGACION = " + pres.getDelegacion() + " AND "
                + "      PP.REL_LABORAL = '" + pres.getRelLaboral() + "' "
                + " ORDER BY TO_NUMBER(TRIM(PP.MES))  ";
        return strSQL;
    }

    public String getSQLInsertRequerimiento(int year, String ramoId, String programaId, String depto, int metaId,
            int accionId, String fuete, String tipoGasto, int requerimientoId, String requerimiento, String partida,
            String reqLaboral, double cantidad, Double costoUnit, Double costoAnual, double ene, double feb, double marzo, double abril,
            double mayo, double junio, double julio, double agosto, double sept, double oct, double nov, double dic, int articulo, String justificacion,
            String fondo, String recurso, String usuario, int gpogto, int subgpo, String archivo) {
        DecimalFormat dFormat = new DecimalFormat("##.##");
        dFormat.setRoundingMode(RoundingMode.DOWN);
        costoUnit = Double.valueOf(dFormat.format(costoUnit));
        String strSQL = "INSERT INTO POA.ACCION_REQ (ACCION_REQ.YEAR, ACCION_REQ.RAMO, ACCION_REQ.PRG, ACCION_REQ.DEPTO, "
                + "ACCION_REQ.META, ACCION_REQ.ACCION, ACCION_REQ.FUENTE, ACCION_REQ.TIPO_GASTO, ACCION_REQ.REQUERIMIENTO, "
                + "ACCION_REQ.DESCR, ACCION_REQ.PARTIDA, ACCION_REQ.REL_LABORAL, ACCION_REQ.CANTIDAD, ACCION_REQ.COSTO_UNITARIO, "
                + "ACCION_REQ.COSTO_ANUAL, ACCION_REQ.ENE, ACCION_REQ.FEB, ACCION_REQ.MAR, ACCION_REQ.ABR, ACCION_REQ.MAY, "
                + "ACCION_REQ.JUN, ACCION_REQ.JUL, ACCION_REQ.AGO, ACCION_REQ.SEP, ACCION_REQ.OCT, ACCION_REQ.NOV, ACCION_REQ.DIC, "
                + "ACCION_REQ.ARTICULO, ACCION_REQ.CANTIDAD_ORG, ACCION_REQ.COSTO_UNITARIO_ORG, ACCION_REQ.ARCHIVO, ACCION_REQ.JUSTIFICACION,"
                + "ACCION_REQ.FONDO, ACCION_REQ.RECURSO, ACCION_REQ.FECHA_REGISTRO, ACCION_REQ.APP_LOGIN_REG, ACCION_REQ.GPOGTO, ACCION_REQ.SUBGPO) "
                + "VALUES ( '" + year + "', '" + ramoId + "', '" + programaId + "', '" + depto + "', " + metaId + ", " + accionId + ", '" + fuete + "', '" + tipoGasto + "', "
                + " " + requerimientoId + ", '" + requerimiento + "', '" + partida + "', '" + reqLaboral + "', " + cantidad + ", " + costoUnit + ", "
                + costoAnual + ", " + ene + ", " + feb + ", " + marzo + ", " + abril + ", " + mayo + ", " + junio + ", " + julio + ", " + agosto + ", " + sept + ", " + oct + ", " + nov + ", " + dic + ", "
                + " " + articulo + ", NULL, NULL, " + archivo + ", '" + justificacion + "','" + fondo + "','" + recurso + "', SYSDATE, '" + usuario + "', " + gpogto + ", " + subgpo + " )";
        return strSQL;
    }

    public String getSQLInsertRequerimientoPresupPlantilla(Ppto reqPres, int reqId) {
        double contCostoAnual = 0;
        int contList = 0;
        String strSQL = " INSERT INTO POA.ACCION_REQ (ACCION_REQ.YEAR, ACCION_REQ.RAMO, ACCION_REQ.PRG, ACCION_REQ.DEPTO,  "
                + "             ACCION_REQ.META, ACCION_REQ.ACCION, ACCION_REQ.FUENTE, ACCION_REQ.TIPO_GASTO, ACCION_REQ.REQUERIMIENTO, "
                + "             ACCION_REQ.DESCR, ACCION_REQ.PARTIDA, ACCION_REQ.REL_LABORAL, ACCION_REQ.COSTO_UNITARIO, "
                + "             ACCION_REQ.ENE, ACCION_REQ.FEB, ACCION_REQ.MAR, ACCION_REQ.ABR, ACCION_REQ.MAY, "
                + "             ACCION_REQ.JUN, ACCION_REQ.JUL, ACCION_REQ.AGO, ACCION_REQ.SEP, ACCION_REQ.OCT, ACCION_REQ.NOV, ACCION_REQ.DIC, "
                + "             ACCION_REQ.ARTICULO, ACCION_REQ.CANTIDAD_ORG, ACCION_REQ.COSTO_UNITARIO_ORG, ACCION_REQ.ARCHIVO,ACCION_REQ.JUSTIFICACION, "
                + "       ACCION_REQ.FONDO, ACCION_REQ.RECURSO, ACCION_REQ.COSTO_ANUAL,  ACCION_REQ.CANTIDAD) ";
        strSQL += "VALUES ( '" + reqPres.getYear() + "', '" + reqPres.getRamo() + "', '" + reqPres.getPrg() + "', '" + reqPres.getDepto() + "',"
                + " " + reqPres.getMeta() + ", " + reqPres.getAccion() + ", '" + reqPres.getFuente() + "', '" + reqPres.getTipoGasto() + "', "
                + " " + reqId + " , '" + reqPres.getDescrPlantilla() + "', '" + reqPres.getPartida() + "', '" + reqPres.getRelLaboral() + "',1, ";
        Mes mTemp;
        for (int it = 1; it < 13; it++) {
            if (contList < reqPres.getdMeses().size()) {
                mTemp = (Mes) reqPres.getdMeses().get(it - 1);
                if (mTemp.getiMes() == it) {
                    strSQL += mTemp.getdImporte() + ",";
                    contCostoAnual = contCostoAnual + mTemp.getdImporte();
                    contList++;
                } else {
                    strSQL += "0,";
                }
            } else {
                strSQL += "0,";
            }
        }
        strSQL += " 0  , NULL, NULL, 1, ' ','" + reqPres.getFondo() + "','" + reqPres.getRecurso() + "', " + contCostoAnual + "," + contCostoAnual + ")";
        return strSQL;
    }

    public String getSQLGetDescrPartidaPlantilla(String partida, String relLaboral, int year) {
        String strSQL = "SELECT P.DESCR||'-'||RL.DESCR AS REQ_DESCR "
                + "FROM "
                + "    DGI.PARTIDA P, S_POA_REL_LABORAL RL "
                + "WHERE "
                + "    P.PARTIDA = '" + partida + "' AND "
                + "    P.YEAR = " + year + " AND "
                + "    RL.REL_LABORAL = '" + relLaboral + "'";
        return strSQL;
    }

    public String getSQLGetArticuloPartida(int year, String partida) {
        String strSQL = "SELECT * FROM S_POA_ARTICULO_PARTIDA PAP "
                + "WHERE PAP.YEAR = " + year + " AND "
                + "      PAP.PARTIDA = '" + partida + "'";
        return strSQL;
    }

    public String getSQLGetFuenteRecurso(int year, int fuente) {
        String strSQL = "SELECT REC.FUENTE, REC.FONDO, REC.RECURSO, REC.DESCR "
                + "FROM S_POA_RECURSO REC "
                + "WHERE REC.FUENTE = '" + fuente + "' AND "
                + "      REC.YEAR = " + year + " "
                + "ORDER BY REC.FUENTE, REC.FONDO, REC.RECURSO ";
        return strSQL;
    }

    public String getSQLGetFuenteRecursoCompleta(int year) {
        String strSQL = "SELECT REC.FUENTE, REC.FONDO, REC.RECURSO, REC.DESCR "
                + "FROM S_POA_RECURSO REC "
                + "WHERE REC.YEAR = ? "
                + "ORDER BY REC.FUENTE, REC.FONDO, REC.RECURSO ";
        return strSQL;
    }

    public String getSQLGetFuenteRecursoFiltrado(int year, int fuente) {
        String strSQL = "SELECT REC.FUENTE, REC.FONDO, REC.RECURSO, REC.DESCR \n"
                + "FROM S_POA_RECURSO REC \n"
                + "WHERE \n"
                + "    REC.FUENTE =   '" + fuente + "'   AND \n"
                + "    REC.YEAR = " + year + " AND \n"
                + "    REC.VISIBLE = 'S'\n"
                + "ORDER BY REC.FUENTE, REC.FONDO, REC.RECURSO, REC.DESCR ";
        return strSQL;
    }

    public String getSQLGetCostoAccion(int year, String ramo, int meta, int accion) {
        String strSQL = "SELECT NVL(AR.ENE*AR.COSTO_UNITARIO,0) AS ENE, "
                + "       NVL(AR.ENE*AR.COSTO_UNITARIO,0) AS FEB, "
                + "       NVL(AR.MAR*AR.COSTO_UNITARIO,0) AS MAR, "
                + "       NVL(AR.ABR*AR.COSTO_UNITARIO,0) AS ABR, "
                + "       NVL(AR.MAY*AR.COSTO_UNITARIO,0) AS MAY, "
                + "       NVL(AR.JUN*AR.COSTO_UNITARIO,0) AS JUN, "
                + "       NVL(AR.JUL*AR.COSTO_UNITARIO,0) AS JUL, "
                + "       NVL(AR.AGO*AR.COSTO_UNITARIO,0) AS AGO, "
                + "       NVL(AR.SEP*AR.COSTO_UNITARIO,0) AS SEP, "
                + "       NVL(AR.OCT*AR.COSTO_UNITARIO,0) AS OCT, "
                + "       NVL(AR.NOV*AR.COSTO_UNITARIO,0) AS NOV, "
                + "       NVL(AR.DIC*AR.COSTO_UNITARIO,0) AS DIC "
                + "FROM S_POA_ACCION_REQ AR "
                + "WHERE AR.RAMO = '" + ramo + "' AND "
                + "      AR.YEAR = " + year + " AND "
                + "      AR.META = " + meta + " AND "
                + "      AR.ACCION = " + accion;
        return strSQL;
    }

    public String getSQLGetRelLaboral(int year) {
        String strSQL = "SELECT RL.DESCR,RL.REL_LABORAL "
                + "FROM S_POA_REL_LABORAL RL "
                + "WHERE RL.YEAR = " + year
                + " ORDER BY RL.REL_LABORAL";
        return strSQL;
    }

    public String getSQLGetTechoRamo(int year, String ramo) {
        String strSQL = "SELECT RAM.TECHO_PRESUP FROM DGI.RAMOS RAM "
                + "WHERE RAM.YEAR = " + year + " AND "
                + "      RAM.RAMO = '" + ramo + "'";
        return strSQL;
    }

    public String getSQLDeptpRamoPrograma(int year, String ramoId, String programaId) {
        String strSQL = "SELECT RP.DEPTO FROM POA.RAMO_PROGRAMA RP "
                + "WHERE RP.YEAR = " + year + " AND  "
                + "      RP.RAMO = '" + ramoId + "' AND "
                + "      RP.PRG = '" + programaId + "'";
        return strSQL;
    }

    public String getSQLDeptoAccion(int year, String ramoId, int meta, int accion) {
        String strSQL = "SELECT RP.DEPTO FROM S_POA_ACCION RP "
                + " WHERE RP.YEAR = " + year + " AND  "
                + "       RP.RAMO = '" + ramoId + "' AND "
                + "       RP.META = " + meta + " AND"
                + "       RP.ACCION = " + accion;
        return strSQL;
    }

    public String getSQLGetCierreRequerimientoPPTO(String ramoId, int year) {
        String strSQL = "SELECT RAM.CIERRE_ACCION_REQ_PPTO FROM DGI.RAMOS RAM "
                + "WHERE RAM.YEAR = " + year + " AND "
                + "      RAM.RAMO = '" + ramoId + "' "
                + "ORDER BY RAM.RAMO";
        return strSQL;
    }

    public String getSQLGetCierreAccion(String ramoId, int year) {
        String strSQL = "SELECT RAM.CIERRE_ACCION FROM DGI.RAMOS RAM "
                + "WHERE RAM.YEAR = " + year + " AND "
                + "      RAM.RAMO = '" + ramoId + "'"
                + "ORDER BY RAM.RAMO";
        return strSQL;
    }

    public String getSQLGetCountRequerimientosByAccion(int year, String ramo, String programa, int meta, int accion) {
        String strSQL = "SELECT COUNT(*) AS REQ FROM S_POA_ACCION_REQ REQ "
                + "WHERE REQ.YEAR = " + year + " AND "
                + "      REQ.RAMO = '" + ramo + "' AND "
                + "      REQ.META = " + meta + " AND "
                + "      REQ.ACCION = " + accion;
        return strSQL;
    }

    public String getSQLGetCierreAccionPPTO(String ramoId, int year) {
        String strSQL = "SELECT RAM.CIERRE_ACCION_PPTO FROM DGI.RAMOS RAM "
                + "WHERE RAM.YEAR = " + year + " AND "
                + "      RAM.RAMO = '" + ramoId + "'"
                + "ORDER BY RAM.RAMO";
        return strSQL;
    }

    public String getSQLGetFinOnRamoPrograma() {
        String strSQL = "SELECT"
                + " F.FIN, "
                + " F.YEAR, "
                + " F.DESCR, "
                + " F.NOMBRE "
                + " FROM "
                + " POA.RAMO_PROGRAMA RP, "
                + " POA.RAMO_FIN RF, "
                + " S_POA_FIN F "
                + " WHERE "
                + " RF.RAMO = RP.RAMO "
                + " AND RF.YEAR = RP.YEAR "
                + " AND RF.FIN = RP.FIN "
                + " AND F.FIN = RF.FIN "
                + " AND F.YEAR = RF.YEAR ";
        return strSQL;
    }

    public String getSQLGetMetaAccionPlatillaByYearRamoProgramaDepto(int year, int ramoId, int programaId, int deptoId) {
        String strSQL = ""
                + "SELECT   "
                + "   DISTINCT "
                + "   MET.META, "
                + "   MET.DESCR DESCR_META, "
                + "   ACC.ACCION,  "
                + "   ACC.DESCR DESCR_ACCION, "
                + "   REC.FUENTE, "
                + "   REC.FONDO, "
                + "   REC.RECURSO, "
                + "   ( "
                + "     SELECT  "
                + "         COUNT(*)  "
                + "         FROM  "
                + "             POA.META_ACCION_PLANTILLA PLANT "
                + "         WHERE  "
                + "             PLANT.YEAR = MET.YEAR "
                + "             AND PLANT.RAMO = MET.RAMO "
                + "             AND PLANT.PRG = MET.PRG "
                + "             AND PLANT.META = MET.META  "
                + "             AND PLANT.ACCION = ACC.ACCION  "
                + "             AND PLANT.FUENTE = REC.FUENTE "
                + "             AND PLANT.FONDO = REC.FONDO "
                + "             AND PLANT.RECURSO = REC.RECURSO  "
                + "   ) SELECCIONADO "
                + "   FROM   "
                + "        S_POA_META MET,  "
                + "        S_POA_ACCION ACC,   "
                + "        S_POA_ACCION_REQ ACCREQ,  "
                + "        S_POA_RECURSO REC "
                + "   WHERE  "
                + "         MET.YEAR = " + year + " "
                + "         AND MET.RAMO = '" + ramoId + "' "
                + "         AND MET.PRG = '" + programaId + "' "
                + "         AND MET.DEPTO = '" + deptoId + "' "
                + "         AND ACC.YEAR = MET.YEAR  "
                + "         AND ACC.RAMO = MET.RAMO  "
                + "         AND ACC.PRG = MET.PRG  "
                + "         AND ACC.META = MET.META  "
                + "         AND ACCREQ.YEAR = ACC.YEAR "
                + "         AND ACCREQ.RAMO = ACC.RAMO  "
                + "         AND ACCREQ.PRG = ACC.PRG         "
                + "         AND ACCREQ.META = ACC.META  "
                + "         AND ACCREQ.ACCION = ACC.ACCION  "
                + "         AND REC.FUENTE = ACCREQ.FUENTE "
                + "         AND REC.FONDO = ACCREQ.FONDO  "
                + "         AND REC.RECURSO = ACCREQ.RECURSO  "
                + "         AND REC.PLANTILLA = 'S'  "
                + "   ORDER BY  "
                + "         MET.META, "
                + "         ACC.ACCION, "
                + "         REC.FUENTE,  "
                + "         REC.FONDO,  "
                + "         REC.RECURSO  ";
        return strSQL;
    }

    public String getSQLGetDepartamentosByRamoProgramaYear(String ramoId, String programaId, int year) {
        String strSQL = ""
                + " SELECT "
                + "     DEP.YEAR, "
                + "     DEP.RAMO, "
                + "     DEP.DEPTO, "
                + "     DEP.DESCR, "
                + "     DEP.MPO, "
                + "     DEP.RFC, "
                + "     DEP.HOMOCLAVE "
                + "     FROM "
                + "         S_POA_CODPROG CP, "
                + "         DGI.DEPENDENCIA DEP "
                + "     WHERE "
                + "         CP.YEAR = " + year + " "
                + "         AND CP.RAMO = '" + ramoId + "' "
                + "         AND CP.PRG = '" + programaId + "' "
                + "         AND DEP.DEPTO = CP.DEPTO"
                + "         AND DEP.RAMO = CP.RAMO "
                + "         AND DEP.YEAR = CP.YEAR ";
        return strSQL;
    }

    public String getSQLGetPlantillasByRamoProgramaDepartamentoYear(String ramoId, String programaId, String departamentoId, int year) {
        String strSQL = ""
                + "SELECT   "
                + "   DISTINCT "
                + "   MET.META, "
                + "   MET.DESCR DESCR_META, "
                + "   ACC.ACCION,  "
                + "   ACC.DESCR DESCR_ACCION, "
                + "   REC.FUENTE, "
                + "   REC.FONDO, "
                + "   REC.RECURSO, "
                + "   ( "
                + "     SELECT  "
                + "         COUNT(*)  "
                + "         FROM  "
                + "             POA.META_ACCION_PLANTILLA PLANT "
                + "         WHERE  "
                + "             PLANT.YEAR = MET.YEAR "
                + "             AND PLANT.RAMO = MET.RAMO "
                + "             AND PLANT.PRG = MET.PRG "
                + "             AND PLANT.META = MET.META  "
                + "             AND PLANT.ACCION = ACC.ACCION  "
                + "             AND PLANT.FUENTE = REC.FUENTE "
                + "             AND PLANT.FONDO = REC.FONDO "
                + "             AND PLANT.RECURSO = REC.RECURSO  "
                + "   ) SELECCIONADO, "
                + "   ACCREQ.TIPO_GASTO "
                + "   FROM   "
                + "        S_POA_META MET,  "
                + "        S_POA_ACCION ACC,   "
                + "        S_POA_ACCION_REQ ACCREQ,  "
                + "        S_POA_RECURSO REC "
                + "   WHERE  "
                + "         MET.YEAR = " + year + " "
                + "         AND MET.RAMO = '" + ramoId + "' "
                + "         AND MET.PRG = '" + programaId + "' "
                + "         AND ACC.DEPTO = '" + departamentoId + "' "
                + "         AND ACC.YEAR = MET.YEAR  "
                + "         AND ACC.RAMO = MET.RAMO  "
                + "         AND ACC.PRG = MET.PRG  "
                + "         AND ACC.META = MET.META  "
                + "         AND ACCREQ.YEAR = ACC.YEAR "
                + "         AND ACCREQ.RAMO = ACC.RAMO  "
                + "         AND ACCREQ.PRG = ACC.PRG         "
                + "         AND ACCREQ.META = ACC.META  "
                + "         AND ACCREQ.ACCION = ACC.ACCION  "
                + "         AND REC.FUENTE = ACCREQ.FUENTE "
                + "         AND REC.FONDO = ACCREQ.FONDO  "
                + "         AND REC.RECURSO = ACCREQ.RECURSO  "
                + "         AND REC.PLANTILLA =  'S'  "
                + "   ORDER BY  "
                + "         MET.META, "
                + "         ACC.ACCION, "
                + "         REC.FUENTE,  "
                + "         REC.FONDO,  "
                + "         REC.RECURSO  ";
        return strSQL;
    }

    public String getSQLGetPlantillasByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT   "
                + "	DISTINCT  "
                + "		ACC.RAMO,  "
                + "		ACC.DEPTO,  "
                + "		DEP.DESCR DESCR_DEPENDENCIA,  "
                + "		ACC.PRG,  "
                + "		PRG.DESCR DESCR_PROGRAMA,  "
                + "		ACC.META,  "
                + "		MET.DESCR DESCR_META,  "
                + "		ACC.ACCION,  "
                + "		ACC.DESCR DESCR_ACCION,  "
                + "		(  "
                + "			SELECT  "
                + "				COUNT(*)  "
                + "			FROM  "
                + "				POA.META_ACCION_PLANTILLA PLANT  "
                + "			WHERE      "
                + "				PLANT.YEAR = ACC.YEAR AND  "
                + "				PLANT.RAMO = ACC.RAMO AND  "
                + "				PLANT.DEPTO = ACC.DEPTO AND  "
                + "				PLANT.PRG = ACC.PRG AND  "
                + "				PLANT.META = ACC.META AND  "
                + "				PLANT.ACCION = ACC.ACCION  "
                + "		) SELECCIONADO,     "
                + "		(SELECT TIPO_GASTO  FROM S_DGI_GRUPOS GPO WHERE GPO.YEAR = " + year + "  AND GRUPO = '10000') AS TIPO_GASTO  "
                + "FROM  "
                + "	S_POA_META MET,  "
                + "	S_POA_ACCION ACC,  "
                + "	S_POA_PROGRAMA PRG,  "
                + "	DGI.DEPENDENCIA DEP  "
                + "WHERE  "
                + "    ACC.YEAR = " + year + " AND  "
                + "	ACC.RAMO = '" + ramoId + "' AND  "
                + "	DEP.YEAR = ACC.YEAR AND  "
                + "	DEP.RAMO = ACC.RAMO AND  "
                + "	DEP.DEPTO = ACC.DEPTO AND  "
                + "	PRG.YEAR = ACC.YEAR AND  "
                + "	PRG.PRG = ACC.PRG AND  "
                + "	MET.YEAR = ACC.YEAR AND  "
                + "	MET.RAMO = ACC.RAMO AND  "
                + "	MET.META = ACC.META AND  "
                + "	NOT EXISTS (  "
                + "					SELECT  "
                + "						*  "
                + "					FROM  "
                + "						POA.META_ACCION_PLANTILLA PLANT  "
                + "					WHERE      "
                + "						PLANT.YEAR = ACC.YEAR AND  "
                + "						PLANT.RAMO = ACC.RAMO AND  "
                + "						PLANT.PRG = ACC.PRG AND  "
                + "						PLANT.DEPTO = ACC.DEPTO         "
                + "				)  "
                + "ORDER BY  "
                + "	ACC.DEPTO,  "
                + "	ACC.PRG,  "
                + "	ACC.META,  "
                + "	ACC.ACCION  "
                + "";
        return strSQL;
    }

    public String getSQLUpdateAccionActivarPlantilla(String ramoId, String programaId, String departamentoId, int metaId, int accionId, String plantilla, String year) {

        String strSQL = ""
                + " UPDATE  "
                + "     POA.ACCION  "
                + "         SET "
                + "             PLANTILLA = '" + plantilla + "'  "
                + "     WHERE "
                + "         YEAR = " + year + " "
                + "         AND RAMO = '" + ramoId + "' "
                + "         AND PRG = '" + programaId + "' "
                + "         AND DEPTO = '" + departamentoId + "' "
                + "         AND META = " + metaId + " "
                + "         AND ACCION = " + accionId + " ";

        return strSQL;
    }

    public String getSQLGetCierrePOA(String ramoId, int year) {
        String strSQL = "SELECT COUNT(1) AS CIERRE_POA "
                + "FROM   DGI.RAMOS "
                + "WHERE  YEAR = " + year + " AND "
                + "       RAMO = '" + ramoId + "' and "
                + "       CIERRE_POA = 'S'";
        return strSQL;
    }

    public String getSQLValidacionRamoCerrado(String ramoId, int year) {
        String strSQL = "SELECT 1 AS VALIDA FROM DGI.RAMOS RAM "
                + "WHERE RAM.CIERRE_POA = 'N' AND "
                + "      RAM.YEAR = " + year + " AND "
                + "      RAM.RAMO = '" + ramoId + "'";
        return strSQL;
    }

    public String getSQLIsMetaPresupuesta(String ramoId, String programa, int metaId, int year) {
        String strSQL = "SELECT MET.PRESUPUESTAR FROM S_POA_META MET "
                + "WHERE MET.YEAR = " + year + " AND "
                + "      MET.RAMO = '" + ramoId + "' AND "
                + "      MET.PRG = '" + programa + "' AND "
                + "      MET.META = " + metaId;
        return strSQL;
    }

    public String getSQLIsReqDescrCerrado(String ramoId, int year) {
        String strSQL = "SELECT RAM.CIERRE_REQ_DESCR "
                + "FROM DGI.RAMOS RAM  "
                + "WHERE RAM.YEAR = " + year + " AND "
                + "      RAM.RAMO = '" + ramoId + "' "
                + "ORDER BY RAM.RAMO";
        return strSQL;
    }

    public String getSQLGetCatalogoProgramaByYear(int year) {

        String strSQL = ""
                + " SELECT  "
                + "     PROG.YEAR, "
                + "     PROG.PRG, "
                + "     PROG.DESCR, "
                + "     PROG.PRG_CONAC  "
                + "     FROM  "
                + "        \"PUBLIC\".S_POA_PROGRAMA PROG "
                + "     WHERE "
                + "         PROG.YEAR = " + year + " ";

        return strSQL;

    }

    public String getSQLGetCatalogoDependenciaByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT "
                + "    DEP.RAMO, "
                + "    DEP.DEPTO, "
                + "    DEP.DESCR, "
                + "    DEP.MPO, "
                + "    DEP.YEAR"
                + "    FROM "
                + "        DGI.DEPENDENCIA DEP"
                + "    WHERE "
                + "        DEP.RAMO = '" + ramoId + "' "
                + "        AND DEP.YEAR =" + year;

        return strSQL;
    }

    public String getSQLisCodigoRepetido(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab) {
        String strSQL = "SELECT COUNT(1) AS CODREP FROM SPP.CODIGOS COD "
                + "WHERE COD.YEAR = " + year + " AND "
                + "      COD.RAMO = '" + ramo + "' AND "
                + "      COD.DEPTO = '" + depto + "' AND "
                + "      COD.FINALIDAD = '" + finalidad + "' AND "
                + "      COD.FUNCION = '" + funcion + "' AND "
                + "      COD.SUBFUNCION = '" + subfuncion + "' AND "
                + "      COD.PRG_CONAC = '" + prgConac + "' AND "
                + "      COD.PRG = '" + programa + "' AND "
                + "      COD.TIPO_PROY = '" + tipoProy + "' AND "
                + "      COD.PROYECTO = " + proyecto + " AND "
                + "      COD.META = " + meta + " AND "
                + "      COD.ACCION = " + accion + " AND "
                + "      COD.PARTIDA = '" + partida + "' AND "
                + "      COD.TIPO_GASTO = '" + tipoGasto + "' AND "
                + "      COD.FUENTE = '" + fuente + "' AND "
                + "      COD.FONDO = '" + fondo + "' AND "
                + "      COD.RECURSO = '" + recurso + "' AND "
                + "      COD.MUNICIPIO = '" + municipio + "' AND "
                + "      COD.DELEGACION = '" + delegacion + "' AND "
                + "      COD.REL_LABORAL = '" + relLab + "'";
        return strSQL;
    }

    public String getSQLGetCountPresupPlantilla(int year, String ramo, String origen) {
        String strSQL = "SELECT COUNT(PP.RAMO) AS PRESUP \n"
                + "FROM POA.PRESUP_PLANTILLA PP\n"
                + "WHERE PP.YEAR = " + year + " AND "
                + "PP.ESTATUS = 0 AND "
                + " PP.ORIGEN = '" + origen + "' ";
        return strSQL;
    }

    public String getSQLGetCountPresupPlantilla(int year, String ramo) {
        String strSQL = "SELECT COUNT(PP.RAMO) AS PRESUP \n"
                + "FROM POA.PRESUP_PLANTILLA PP\n"
                + "WHERE PP.RAMO = '" + ramo + "' AND"
                + " PP.YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetCountProyeccion(int year, String ramo) {
        String strSQL = "SELECT COUNT(PP.RAMO) AS PRESUP \n"
                + "FROM POA.PROYECCION PP\n"
                + "WHERE PP.RAMO = '" + ramo + "' AND"
                + " PP.YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetCatalogoCodigosByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT "
                + "	COD.YEAR, "
                + "	COD.RAMO, "
                + "	COD.DEPTO, "
                + "	COD.FINALIDAD, "
                + "	COD.FUNCION, "
                + "	COD.SUBFUNCION, "
                + "	COD.PRG_CONAC, "
                + "	COD.PRG, "
                + "	COD.TIPO_PROY, "
                + "	LPAD(COD.PROYECTO,PLC.PROYECTO,'0') AS PROYECTO, "
                + "	LPAD(COD.META,PLC.META,'0') AS META, "
                + "	LPAD(COD.ACCION,PLC.ACCION,'0') AS ACCION, "
                + "	COD.PARTIDA, "
                + "	COD.TIPO_GASTO, "
                + "	LPAD(COD.FUENTE,PLC.FUENTE,'0') AS FUENTE, "
                + "	LPAD(COD.FONDO,PLC.FONDO,'0') AS FONDO, "
                + "	LPAD(COD.RECURSO,PLC.RECURSO,'0') AS RECURSO, "
                + "	COD.MUNICIPIO, "
                + "	LPAD(COD.DELEGACION,PLC.DELEGACION,'0') AS DELEGACION, "
                + "	COD.REL_LABORAL "
                + "	FROM "
                + "		SPP.CODIGOS COD, "
                + "		S_DGI_PARAM_LONG_COD PLC "
                + "	WHERE "
                + "		COD.RAMO = '" + ramoId + "' AND"
                + "		COD.YEAR =  " + year + " AND "
                + "		PLC.YEAR = COD.YEAR "
                + "";

        return strSQL;
    }

    public String getSQLExisteCodigoPPTO(String ramoId, int year, String prg, String finalidad, String funcion, String subfuncion, String prg_conac, String depto, String tipo_proy, int proyecto, int meta, int accion, String partida, String tipo_gasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String rel_laboral) {
        String strSQL = ""
                + "SELECT "
                + "	COD.YEAR, "
                + "	COD.RAMO, "
                + "	COD.DEPTO, "
                + "	COD.FINALIDAD, "
                + "	COD.FUNCION, "
                + "	COD.SUBFUNCION, "
                + "	COD.PRG_CONAC, "
                + "	COD.PRG, "
                + "	COD.TIPO_PROY, "
                + "	COD.PROYECTO, "
                + "	COD.META, "
                + "	COD.ACCION, "
                + "	COD.PARTIDA, "
                + "	COD.TIPO_GASTO, "
                + "	COD.FUENTE, "
                + "	COD.FONDO, "
                + "	COD.RECURSO, "
                + "	COD.MUNICIPIO, "
                + "	COD.DELEGACION "
                + "FROM "
                + "   	SPP.CODIGOS COD "
                + "WHERE "
                + "	COD.RAMO = '" + ramoId + "' "
                + "	AND COD.YEAR =" + year + ""
                + "     AND COD.PRG = '" + prg + "'"
                + "     AND COD.FINALIDAD = '" + finalidad + "' "
                + "     AND COD.FUNCION = '" + funcion + "'"
                + "     AND COD.SUBFUNCION = '" + subfuncion + "'"
                + "     AND COD.PRG_CONAC = '" + prg_conac + "'"
                + "     AND COD.DEPTO = '" + depto + "'"
                + "     AND COD.TIPO_PROY =  '" + tipo_proy + "'"
                + "     AND COD.PROYECTO = " + proyecto + ""
                + "     AND COD.META = " + meta + ""
                + "     AND COD.ACCION = " + accion + ""
                + "     AND COD.PARTIDA = '" + partida + "' "
                + "     AND COD.TIPO_GASTO = '" + tipo_gasto + "'"
                + "     AND COD.FUENTE = '" + fuente + "'"
                + "     AND COD.FONDO = '" + fondo + "'"
                + "     AND COD.RECURSO = '" + recurso + "'"
                + "     AND COD.MUNICIPIO = '" + municipio + "'"
                + "     AND COD.DELEGACION = " + delegacion + ""
                + "     AND COD.REL_LABORAL = '" + rel_laboral + "'   ";

        return strSQL;
    }

    public String getSQLGetCatalogoCodProgByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT "
                + "    CODPROG.RAMO, "
                + "    CODPROG.PRG, "
                + "    CODPROG.DEPTO, "
                + "    CODPROG.YEAR "
                + "    FROM  "
                + "        \"PUBLIC\".S_POA_CODPROG CODPROG "
                + "    WHERE "
                + "        CODPROG.RAMO = '" + ramoId + "' "
                + "        AND CODPROG.YEAR = " + year;

        return strSQL;
    }

    public String getSQLInsertCodigoProgramatico(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab) {
        String strSQL = "INSERT INTO SPP.CODIGOS COD(COD.YEAR, COD.RAMO, COD.DEPTO, COD.FINALIDAD, COD.FUNCION, COD.SUBFUNCION, COD.PRG_CONAC "
                + ",COD.PRG, COD.TIPO_PROY, COD.PROYECTO, COD.META, COD.ACCION, COD.PARTIDA, COD.TIPO_GASTO, COD.FUENTE, COD.FONDO "
                + ",COD.RECURSO, COD.MUNICIPIO, COD.DELEGACION, COD.REL_LABORAL ) "
                + "VALUES ('" + year + "','" + ramo + "','" + depto + "','" + finalidad + "','" + funcion + "','" + subfuncion + "','" + prgConac + "'"
                + ",'" + programa + "','" + tipoProy + "','" + proyecto + "'," + meta + "," + accion + ",'" + partida + "','" + tipoGasto + "','" + fuente + "'"
                + ",'" + fondo + "','" + recurso + "','" + municipio + "'," + delegacion + ",'" + relLab + "')";
        return strSQL;
    }

    public String getSQLGetCodigoProgramatico(int year, String ramo, String programa, int proyecto, String requ, String partida, int meta, int accion, String tipoProy) {
        String strSQL = "SELECT MET.RAMO, ACC.DEPTO, MET.FINALIDAD, MET.FUNCION, MET.SUBFUNCION, PR.PRG_CONAC, PR.PRG, PROY.PROY, MET.META,  \n"
                + "                            ACC.ACCION, REQ.PARTIDA, REQ.TIPO_GASTO, REQ.FUENTE, REQ.FONDO, REQ.RECURSO, ACC.MPO, ACC.LOCALIDAD, PROY.TIPO_PROY \n"
                + " FROM S_POA_PROGRAMA PR, POA.RAMO_PROGRAMA RPR, S_POA_META MET, S_POA_ACCION ACC, S_POA_ACCION_REQ REQ, POA.PROYECTO PROY  \n"
                + "WHERE  \n"
                + "    RPR.YEAR = " + year + " AND "
                + "  RPR.RAMO = '" + ramo + "' AND "
                + "    RPR.PRG = '" + programa + "' AND "
                + "    PR.YEAR = RPR.YEAR AND  "
                + "    PR.PRG = RPR.PRG AND "
                + "    PROY.YEAR =  RPR.YEAR AND "
                + "    PROY.RAMO = RPR.RAMO AND "
                + "    PROY.PRG = RPR.PRG AND "
                + "  PROY.TIPO_PROY = '" + tipoProy + "' AND "
                + "    PROY.PROY = " + proyecto + " AND\n"
                + "    MET.YEAR = RPR.YEAR AND\n"
                + "    MET.RAMO = RPR.RAMO AND\n"
                + "    MET.PRG = RPR.PRG AND\n"
                + "    MET.PROY = PROY.PROY AND\n"
                + "    MET.TIPO_PROY = PROY.TIPO_PROY AND\n"
                + "    MET.META = " + meta + " AND  \n"
                + "    ACC.YEAR = RPR.YEAR AND \n"
                + "    ACC.RAMO = RPR.RAMO AND \n"
                + "    ACC.PRG = RPR.PRG AND \n"
                + "    ACC.META = MET.META AND  \n"
                + "    ACC.ACCION = " + accion + " AND \n"
                + "    REQ.YEAR = RPR.YEAR AND \n"
                + "    REQ.RAMO = RPR.RAMO AND\n"
                + "    REQ.PRG = RPR.PRG AND  \n"
                + "    REQ.META = MET.META AND  \n"
                + "    REQ.ACCION = ACC.ACCION AND \n"
                + "    REQ.YEAR = MET.YEAR AND  \n"
                + "    REQ.REQUERIMIENTO = " + requ + " AND  \n"
                + "    REQ.PARTIDA = '" + partida + "' ";
        return strSQL;

    }

    public String getSQLGetRequerimientoById(int year, String ramoId, String programa, int meta, int accion, int requ) {
        String strSQL = "SELECT * "
                + "FROM S_POA_ACCION_REQ REQ "
                + "WHERE REQ.RAMO = '" + ramoId + "' AND "
                + "      REQ.PRG = '" + programa + "' AND "
                + "      REQ.META = " + meta + " AND "
                + "      REQ.ACCION = " + accion + " AND "
                + "      REQ.REQUERIMIENTO = " + requ + " AND "
                + "      REQ.YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetProcedureActualizaCodPPTO(int year, String ramo, String depto,
            String finalidad, String funcion, String subFuncion, String progConac,
            String programa, String tipoProyecto, int proyecto, int meta, int accion,
            String partida, String tipoGasto, String fuente, String fondo, String recurso,
            String municipio, int delegacion, String relLaboral, double cantXcostoUn,
            int mes, int existeCod, String usuario, int requerimiento) {
        String strSQL = "CALL POA.P_POA_ACTUALIZA_COD_PPTO('" + year + "','" + ramo + "','" + depto + "','" + finalidad + "','" + funcion + "','" + subFuncion + "',"
                + "'" + progConac + "', '" + programa + "', '" + tipoProyecto + "'," + proyecto + ", " + meta + ", " + accion + ", '" + partida + "', '" + tipoGasto + "'"
                + ", '" + fuente + "', '" + fondo + "', '" + recurso + "', '" + municipio + "', " + delegacion + ",'" + relLaboral + "', " + cantXcostoUn + ", " + mes + ", " + existeCod + ",'" + usuario + "'," + requerimiento + " )";

        return strSQL;
    }

    public String getSQLUpdatePresupuesto() {
        return "CALL POA.P_POA_EDITAR_PRESUPUESTO("
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?)";
    }

    public String getSQLUpdatePresupuestoAv() {
        return "CALL POA.P_POA_EDITAR_PPTO_COMP("
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?)";
    }

    public String getSQLGetProcedureActualizaCodPPTO(int year, String ramo, String depto,
            String finalidad, String funcion, String subFuncion, String progConac,
            String programa, String tipoProyecto, int proyecto, int meta, int accion,
            String partida, String tipoGasto, String fuente, String fondo, String recurso,
            String municipio, int delegacion, String relLaboral, double cantXcostoUn,
            int mes, int existeCod, String usuario, int requerimiento, int validaBitacora) {
        String strSQL = "CALL POA.P_POA_ACTUALIZA_COD_PPTO_ANUAL('" + year + "','" + ramo + "','" + depto + "','" + finalidad + "','" + funcion + "','" + subFuncion + "',"
                + "'" + progConac + "', '" + programa + "', '" + tipoProyecto + "'," + proyecto + ", " + meta + ", " + accion + ", '" + partida + "', '" + tipoGasto + "'"
                + ", '" + fuente + "', '" + fondo + "', '" + recurso + "', '" + municipio + "', " + delegacion + ",'" + relLaboral + "', " + cantXcostoUn + ", " + mes + ","
                + " " + existeCod + ",'" + usuario + "'," + requerimiento + ", " + validaBitacora + " )";

        return strSQL;
    }

    public String getSQLGetProcedureValidaCarga(int year, String ramo, String sOrigen, int iParaestatal) {
        String strSQL = "CALL POA.P_POA_VALIDA_CARGA('" + year + "','" + ramo + "','" + sOrigen + "'," + iParaestatal + ")";

        return strSQL;
    }

    public String getSQLGetCatalogoFinByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "	FIN.YEAR, "
                + "	FIN.FIN, "
                + "	FIN.NOMBRE, "
                + "	FIN.DESCR  "
                + "	FROM  "
                + "		\"PUBLIC\".S_POA_FIN FIN  "
                + "	WHERE "
                + "		FIN.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoFinalidadByYear(int year) {
        String strSQL = ""
                + "SELECT "
                + "	FINAL.YEAR, "
                + "	FINAL.FINALIDAD, "
                + "	FINAL.DESCR "
                + "	FROM "
                + "		\"PUBLIC\".S_POA_FINALIDAD FINAL "
                + "	WHERE "
                + "		FINAL.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoFondoByYear(int year) {
        String strSQL = ""
                + "SELECT "
                + "	FON.YEAR, "
                + "	LPAD(FON.FUENTE,PLC.FUENTE,'0') AS FUENTE, "
                + "	LPAD(FON.FONDO,PLC.FONDO,'0') AS FONDO, "
                + "	FON.DESCR  "
                + "FROM  "
                + "	\"PUBLIC\".S_POA_FONDO FON, "
                + "     S_DGI_PARAM_LONG_COD PLC "
                + "WHERE "
                + "	FON.YEAR = " + year + " AND "
                + "	PLC.YEAR = FON.YEAR "
                + "";
        return strSQL;
    }

    public String getSQLGetCatalogoFuenteByYear(int year) {
        String strSQL = ""
                + "SELECT "
                + "	FNT.YEAR, "
                + "	LPAD(FNT.FUENTE,PLC.FUENTE,'0') AS FUENTE, "
                + "	FNT.DESCR "
                + "FROM "
                + "	\"PUBLIC\".S_POA_FUENTE FNT, "
                + "     S_DGI_PARAM_LONG_COD PLC "
                + "WHERE "
                + "	FNT.YEAR = " + year + " AND"
                + "	PLC.YEAR = FNT.YEAR "
                + "";

        return strSQL;
    }

    public String getSQLGetCatalogoFuncionByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "	FUNC.YEAR, "
                + "	FUNC.FINALIDAD, "
                + "	FUNC.FUNCION, "
                + "	FUNC.DESCR  "
                + "	FROM  "
                + "		\"PUBLIC\".S_POA_FUNCION FUNC  "
                + "	WHERE "
                + "		FUNC.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoGruposByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "    GRUP.GRUPO, "
                + "    GRUP.DESCR, "
                + "    GRUP.YEAR,  "
                + "    GRUP.TIPO_GASTO  "
                + "    FROM  "
                + "        S_DGI_GRUPOS GRUP "
                + "    WHERE "
                + "        GRUP.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoSubGruposByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "    SUBGRUP.GRUPO, "
                + "    SUBGRUP.SUBGRUPO, "
                + "    SUBGRUP.DESCR, "
                + "    SUBGRUP.YEAR  "
                + "    FROM  "
                + "        S_DGI_SUBGRUPOS SUBGRUP "
                + "    WHERE "
                + "        SUBGRUP.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoSubSubGpoByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "    SUBSUBGPO.GRUPO, "
                + "    SUBSUBGPO.SUBGRUPO, "
                + "    SUBSUBGPO.SUBSUBGRUPO, "
                + "    SUBSUBGPO.DESCR, "
                + "    SUBSUBGPO.YEAR  "
                + "    FROM  "
                + "        S_DGI_SUBSUBGPO SUBSUBGPO"
                + "    WHERE "
                + "        SUBSUBGPO.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoPartidaByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "    PART.PARTIDA, "
                + "    PART.DESCR, "
                + "    PART.PLANTILLA, "
                + "    PART.YEAR, "
                + "    PART.SUBSUBGPO, "
                + "    PART.CTA_APROBADO, "
                + "    PART.CTA_MODIFICADO, "
                + "    PART.CTA_COMPROMETIDO, "
                + "    PART.CTA_DEVENGADO, "
                + "    PART.CTA_EJERCIDO, "
                + "    PART.CTA_PAGADO, "
                + "    PART.CTA_PPTOXEJER, "
                + "    PART.RELACION_LABORAL  "
                + "    FROM  "
                + "        DGI.PARTIDA PART "
                + "	WHERE "
                + "		PART.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoPPTOByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT "
                + "	PPTO.YEAR, "
                + "	PPTO.RAMO, "
                + "	PPTO.DEPTO, "
                + "	PPTO.FINALIDAD, "
                + "	PPTO.FUNCION, "
                + "	PPTO.SUBFUNCION, "
                + "	PPTO.PRG_CONAC, "
                + "	PPTO.PRG, "
                + "	PPTO.TIPO_PROY, "
                + "	LPAD(PPTO.PROYECTO,PLC.PROYECTO,'0') AS PROYECTO, "
                + "	LPAD(PPTO.META,PLC.META,'0') AS META, "
                + "	LPAD(PPTO.ACCION,PLC.ACCION,'0') AS ACCION, "
                + "	PPTO.PARTIDA, "
                + "	PPTO.TIPO_GASTO, "
                + "	LPAD(PPTO.FUENTE,PLC.FUENTE,'0') AS FUENTE, "
                + "	LPAD(PPTO.FONDO,PLC.FONDO,'0') AS FONDO, "
                + "	LPAD(PPTO.RECURSO,PLC.RECURSO,'0') AS RECURSO, "
                + "	PPTO.MUNICIPIO, "
                + "	LPAD(PPTO.DELEGACION,PLC.DELEGACION,'0') AS DELEGACION, "
                + "	PPTO.REL_LABORAL, "
                + "	PPTO.MES, "
                + "	PPTO.ASIGNADO "
                + "	FROM  "
                + "		SPP.PPTO PPTO, "
                + "		S_DGI_PARAM_LONG_COD PLC "
                + "  WHERE "
                + "		PPTO.RAMO = '" + ramoId + "' AND "
                + "		PPTO.YEAR = " + year + " AND "
                + "		PLC.YEAR = PPTO.YEAR "
                + "";

        return strSQL;
    }

    public String getSQLGetCatalogoPrgConacByYear(int year) {
        String strSQL = ""
                + "SELECT "
                + "	PRGCONAC.YEAR, "
                + "	PRGCONAC.PRG_CONAC, "
                + "	PRGCONAC.DESCR  "
                + "	FROM  "
                + "		S_POA_PROGRAMA_CONAC PRGCONAC "
                + "	WHERE  "
                + "		PRGCONAC.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoRamoProgramaByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT  "
                + "	RAMPRG.YEAR, "
                + "	RAMPRG.RAMO, "
                + "	RAMPRG.PRG "
                + "	FROM  "
                + "		POA.RAMO_PROGRAMA RAMPRG "
                + "	WHERE "
                + "		RAMPRG.RAMO =  '" + ramoId + "' "
                + "		AND RAMPRG.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoRecursoByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "	REC.YEAR, "
                + "	LPAD(REC.FUENTE,PLC.FUENTE,'0') AS FUENTE, "
                + "	LPAD(REC.FONDO,PLC.FONDO,'0') AS FONDO,  "
                + "	LPAD(REC.RECURSO,PLC.RECURSO,'0') AS RECURSO, "
                + "	REC.DESCR "
                + "FROM  "
                + "	\"PUBLIC\".S_POA_RECURSO REC,"
                + "	S_DGI_PARAM_LONG_COD PLC "
                + "WHERE  "
                + "	REC.YEAR = " + year + " AND "
                + "	PLC.YEAR = REC.YEAR "
                + "";

        return strSQL;
    }

    public String getSQLGetCatalogoRelLaboralByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "	RELLAB.YEAR, "
                + "	RELLAB.REL_LABORAL, "
                + "	RELLAB.DESCR  "
                + "	FROM  "
                + "		\"PUBLIC\".S_POA_REL_LABORAL RELLAB "
                + "	WHERE "
                + "		RELLAB.YEAR = " + year
                + "	ORDER BY  "
                + "	RELLAB.DESCR  ";

        return strSQL;
    }

    public String getSQLGetCatalogoSubfuncionByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "	SUBFUNC.YEAR, "
                + "	SUBFUNC.FINALIDAD, "
                + "	SUBFUNC.FUNCION, "
                + "	SUBFUNC.SUBFUNCION, "
                + "	SUBFUNC.DESCR  "
                + "	FROM  "
                + "		\"PUBLIC\".S_POA_SUBFUNCION SUBFUNC "
                + "	WHERE "
                + "		SUBFUNC.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoTipoGastoByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "	TIPGAS.YEAR, "
                + "	TIPGAS.TIPO_GASTO, "
                + "	TIPGAS.DESCR  "
                + "	FROM  "
                + "		\"PUBLIC\".S_POA_TIPO_GASTO TIPGAS "
                + "	WHERE "
                + "		TIPGAS.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoTipoAccion() {
        String strSQL = ""
                + "SELECT  "
                + "	TIPACC.TIPO_ACCION, "
                + "	TIPACC.DESCR, "
                + "	TIPACC.REQ_DETALLE  "
                + "	FROM  "
                + "		\"PUBLIC\".S_POA_TIPO_ACCION TIPACC ";

        return strSQL;
    }

    public String getSQLGetCatalogoCentroCosto() {
        String strSQL = ""
                + "SELECT  "
                + "    CC.CENTRO_COSTO,  "
                + "    CC.DESCR  "
                + "    FROM  "
                + "        POA.CENTRO_COSTO CC ";
        return strSQL;
    }

    public String getSQLGetCatalogoMunicipio() {
        String strSQL = ""
                + "SELECT"
                + "  MUN.MPO, "
                + "  MUN.NOMBREMPO "
                + "  FROM \"PUBLIC\".S_POA_MUNICIPIO  MUN ";
        return strSQL;
    }

    public String getSQLGetCatalogoProyectoByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT "
                + "  LPAD(PROY.PROY,PLC.PROYECTO,'0') AS PROY, "
                + "  PROY.TIPO_PROY, "
                + "  PROY.YEAR, "
                + "  PROY.RAMO, "
                + "  PROY.PRG "
                + "  FROM "
                + "     POA.PROYECTO PROY, "
                + "     S_DGI_PARAM_LONG_COD PLC "
                + "  WHERE "
                + "      PROY.YEAR = " + year + " AND "
                + "      PROY.RAMO = '" + ramoId + "' AND"
                + "      PLC.YEAR = PROY.YEAR "
                + " ";
        return strSQL;
    }

    public String getSQLGetCatalogoProyectoFuncionalByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT "
                + "    PROYFUNC.YEAR, "
                + "    PROYFUNC.RAMO, "
                + "    PROYFUNC.PRG, "
                + "    PROYFUNC.TIPO_PROY, "
                + "    PROYFUNC.PROY, "
                + "    PROYFUNC.DEPTO, "
                + "    PROYFUNC.TIPO, "
                + "    PROYFUNC.FINALIDAD, "
                + "    PROYFUNC.FUNCION, "
                + "    PROYFUNC.SUBFUNCION "
                + "FROM "
                + "    POA.PROYECTO_FUNCIONAL PROYFUNC "
                + "WHERE "
                + "    PROYFUNC.YEAR = " + year
                + "    AND PROYFUNC.RAMO = '" + ramoId + "' ";
        return strSQL;
    }

    public String getSQLGetRamosByRamoYearUsuario(String ramoId, int year) {
        String strSQL = ""
                + " SELECT "
                + "     RAM.RAMO, "
                + "     RAM.DESCR, "
                + "     RAM.TRANSITORIO, "
                + "     RAM.YEAR "
                + "     FROM  "
                + "         DGI.RAMOS RAM "
                + "     WHERE  "
                + "         RAM.RAMO = '" + ramoId + "' "
                + "         AND RAM.YEAR =  " + year + " ";

        return strSQL;
    }

    public String getSQLGetYears() {
        String strSQL = "SELECT NVL(PAR.YEAR_ANT,0) AS ANTERIOR, NVL(PAR.YEAR_NVO,0) AS NUEVO, "
                + "NVL(PAR.YEAR,0) AS ACTUAL "
                + "FROM DGI.PARAMETROS PAR";
        return strSQL;
    }

    public String getSQLDeleteMetaAccionPlantilla(int year, String ramoId, String prgId, String deptoId, int metaId, int accionId, String fuenteId, String fondoId, String recursoId) {
        String strSQL = ""
                + "DELETE "
                + "FROM POA.META_ACCION_PLANTILLA PLANT "
                + "WHERE "
                + "      PLANT.YEAR = " + year + " AND "
                + "      PLANT.RAMO = '" + ramoId + "' AND "
                + "      PLANT.PRG = '" + prgId + "' AND "
                + "      PLANT.DEPTO = '" + deptoId + "' AND "
                + "      PLANT.META = " + metaId + " AND "
                + "      PLANT.ACCION = " + accionId + " ";
        return strSQL;
    }

    public String getSQLInsertMetaAccionPlantilla(int year, String ramoId, String prgId, String deptoId, int metaId, int accionId, String fuenteId, String fondoId, String recursoId, String tipoGasto) {
        String strSQL = ""
                + "INSERT INTO POA.META_ACCION_PLANTILLA(YEAR,RAMO,PRG,DEPTO,META,ACCION,TIPO_GASTO) "
                + " VALUES "
                + " ( "
                + "     " + year + ", "
                + "    '" + ramoId + "', "
                + "    '" + prgId + "', "
                + "    '" + deptoId + "', "
                + "     " + metaId + ", "
                + "     " + accionId + ", "
                + "    '" + tipoGasto + "' "
                + ") ";
        return strSQL;
    }

    public String getSQLGetAvisoInicial() {
        String strSQL = "SELECT PAR.AVISO_ENTRADA FROM DGI.PARAMETROS PAR";
        return strSQL;
    }

    public String getSQLGetCatalogoMetaByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT  "
                + "    MET.YEAR, "
                + "    MET.RAMO, "
                + "    MET.PRG, "
                + "    LPAD(MET.META,PLC.META,'0') AS META, "
                + "    MET.DESCR,  "
                + "    MET.LINEA,  "
                + "    MET.FINALIDAD,  "
                + "    MET.FUNCION,  "
                + "    MET.SUBFUNCION,  "
                + "    MET.PRESUPUESTAR,  "
                + "    MET.APROB_CONGRESO,  "
                + "    MET.CONVENIO   "
                + "FROM  "
                + "    \"PUBLIC\".S_POA_META MET, "
                + "    S_DGI_PARAM_LONG_COD PLC  "
                + "WHERE  "
                + "    	MET.YEAR = " + year + " AND"
                + "     MET.RAMO = '" + ramoId + "' AND "
                + "     PLC.YEAR = MET.YEAR "
                + "";
        return strSQL;
    }

    public String getSQLGetCatalogoAccionByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT  "
                + "	ACC.YEAR, "
                + "	ACC.RAMO, "
                + "	ACC.PRG, "
                + "	ACC.DEPTO, "
                + "	LPAD(ACC.META,PLC.META,'0') AS META, "
                + "	LPAD(ACC.ACCION,PLC.ACCION,'0') AS ACCION, "
                + "	ACC.DESCR "
                + "FROM  "
                + "     \"PUBLIC\".S_POA_ACCION ACC, "
                + "     S_DGI_PARAM_LONG_COD PLC "
                + "WHERE "
                + "    	ACC.YEAR = " + year + " AND "
                + "     ACC.RAMO = '" + ramoId + "' AND "
                + "    	PLC.YEAR = ACC.YEAR  "
                + "";
        return strSQL;
    }

    public String getSQLGetCatalogoAccionReqByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT  "
                + "    ACCREQ.YEAR, "
                + "    ACCREQ.RAMO, "
                + "    ACCREQ.PRG, "
                + "    ACCREQ.DEPTO, "
                + "    ACCREQ.META, "
                + "    ACCREQ.ACCION, "
                + "    ACCREQ.REQUERIMIENTO, "
                + "    ACCREQ.DESCR, "
                + "    ACCREQ.FUENTE, "
                + "	ACCREQ.TIPO_GASTO, "
                + "	ACCREQ.PARTIDA, "
                + "	ACCREQ.REL_LABORAL, "
                + "	ACCREQ.CANTIDAD, "
                + "	ACCREQ.COSTO_UNITARIO, "
                + "	ACCREQ.COSTO_ANUAL, "
                + "	ACCREQ.ENE, "
                + "	ACCREQ.FEB, "
                + "	ACCREQ.MAR, "
                + "	ACCREQ.ABR, "
                + "	ACCREQ.MAY, "
                + "	ACCREQ.JUN, "
                + "	ACCREQ.JUL, "
                + "	ACCREQ.AGO, "
                + "	ACCREQ.SEP, "
                + "	ACCREQ.OCT, "
                + "	ACCREQ.NOV, "
                + "	ACCREQ.DIC, "
                + "	ACCREQ.ARTICULO, "
                + "	ACCREQ.CANTIDAD_ORG, "
                + "	ACCREQ.COSTO_UNITARIO_ORG, "
                + "	ACCREQ.ARCHIVO, "
                + "	ACCREQ.JUSTIFICACION, "
                + "	ACCREQ.FONDO, "
                + "	ACCREQ.RECURSO  "
                + "	FROM  "
                + "         S_POA_ACCION_REQ ACCREQ "
                + "    WHERE "
                + "    	ACCREQ.YEAR = " + year
                + "     AND ACCREQ.RAMO = '" + ramoId + "' ";
        return strSQL;
    }

    public String getSQLDeleteRequerimiento(int year, String ramo, String programa, int meta, int accion, int requ) {
        String strSQL = "DELETE FROM POA.ACCION_REQ REQ "
                + "WHERE REQ.RAMO = '" + ramo + "' AND "
                + "      REQ.PRG = '" + programa + "' AND "
                + "      REQ.META = " + meta + " AND "
                + "      REQ.ACCION = " + accion + " AND "
                + "      REQ.REQUERIMIENTO = " + requ + " AND "
                + "      REQ.YEAR = " + year;
        return strSQL;
    }

    public String getSQLUpdateRequerimiento(int year, String ramoId, String programaId, int meta, int accion, int req,
            String justificacion, double cantidad, double costoUnitario, double costoAnual, String relLab,
            double ene, double feb, double mar, double abr, double may, double jun, double jul, double ago,
            double sep, double oct, double nov, double dic, String partidDesc, String usuario, int articulo, int gpogto, int subgpo) {
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        ene = Double.valueOf(df.format(ene));
        feb = Double.valueOf(df.format(feb));
        mar = Double.valueOf(df.format(mar));
        abr = Double.valueOf(df.format(abr));
        may = Double.valueOf(df.format(may));
        jul = Double.valueOf(df.format(jul));
        jul = Double.valueOf(df.format(jul));
        ago = Double.valueOf(df.format(ago));
        sep = Double.valueOf(df.format(sep));
        oct = Double.valueOf(df.format(oct));
        nov = Double.valueOf(df.format(nov));
        dic = Double.valueOf(df.format(dic));
        costoUnitario = Double.valueOf(df.format(costoUnitario));
        String strSQL = "UPDATE POA.ACCION_REQ REQ "
                + "SET REQ.JUSTIFICACION = '" + justificacion + "', "
                + "    REQ.CANTIDAD = " + cantidad + ", "
                + "    REQ.COSTO_UNITARIO = " + costoUnitario + ", "
                + "    REQ.COSTO_ANUAL = " + costoAnual + ", "
                + "    REQ.REL_LABORAL = '" + relLab + "', "
                + "    REQ.ENE = " + ene + ", "
                + "    REQ.FEB = " + feb + ", "
                + "    REQ.MAR= " + mar + ", "
                + "    REQ.ABR = " + abr + ", "
                + "    REQ.MAY = " + may + ", "
                + "    REQ.JUN = " + jun + ", "
                + "    REQ.JUL = " + jul + ", "
                + "    REQ.AGO = " + ago + ", "
                + "    REQ.SEP = " + sep + ", "
                + "    REQ.OCT = " + oct + ", "
                + "    REQ.NOV = " + nov + ", "
                + "    REQ.DIC =  " + dic + ", "
                + "    REQ.DESCR = '" + partidDesc + "',"
                + "    REQ.FECHA_MODIFICACION = SYSDATE, "
                + "    REQ.APP_LOGIN_MOD = '" + usuario + "', "
                + "    REQ.ARTICULO = '" + articulo + "', "
                + "    REQ.GPOGTO = " + gpogto + ", "
                + "    REQ.SUBGPO = " + subgpo + " "
                + "WHERE REQ.YEAR = " + year + " AND "
                + "      REQ.RAMO = '" + ramoId + "' AND "
                + "      REQ.PRG = '" + programaId + "' AND "
                + "      REQ.META = " + meta + " AND "
                + "      REQ.ACCION = " + accion + " AND "
                + "      REQ.REQUERIMIENTO = " + req;
        return strSQL;
    }

    public String getSQLGetLineaSectorialEstrategia(String ramoId, String programaId, int proyectoId, int year, String estrategia, String tipoProy) {
        String strSQL = "SELECT LINS.ESTRATEGIA, PROS.LINEA_SECTORIAL, LINS.DESCR, PROS.TIPO_PROY  "
                + "FROM \"PUBLIC\".S_POA_LINEA_SECTORIAL LINS, POA.PROYECTO_SECTORIAL PROS "
                + "WHERE PROS.RAMO = '" + ramoId + "' AND  "
                + "   PROS.PRG = '" + programaId + "' AND  "
                + "   PROS.PROY =   " + proyectoId + "   AND  "
                + "   PROS.TIPO_PROY =   '" + tipoProy + "'   AND  "
                + "   PROS.YEAR =   " + year + "   AND "
                + "   LINS.YEAR = DGI.F_OBTIENE_YEAR_ADMVO(" + year + ") AND "
                + "   LINS.ESTRATEGIA =  '" + estrategia + "' AND  "
                + "   PROS.LINEA_SECTORIAL = LINS.LINEA_SECTORIAL  AND "
                + "   PROS.LINEA = LINS.ESTRATEGIA ";
        return strSQL;
    }

    public String getSQLGetLineaSectorialByYearEstrategia(int year, String estrategia) {
        String strSQL = "SELECT LINS.LINEA_SECTORIAL, LINS.DESCR "
                + "FROM \"PUBLIC\".S_POA_LINEA_SECTORIAL LINS "
                + "WHERE "
                + "   LINS.YEAR = DGI.F_OBTIENE_YEAR_ADMVO(" + year + ") AND "
                + "   LINS.ESTRATEGIA =  '" + estrategia + "'";
        return strSQL;
    }

    public String getSQLGetCountAccionesByMeta(int year, String ramoId, String programaId, int meta) {
        String strSQL = "SELECT COUNT(*) AS ACC FROM S_POA_ACCION ACC "
                + "WHERE ACC.YEAR = " + year + " AND "
                + "      ACC.RAMO = '" + ramoId + "' AND "
                + "      ACC.PRG = '" + programaId + "' AND "
                + "      ACC.META = " + meta;
        return strSQL;
    }

    public String getSQLGetContLineasPED(int year, String ramoId, String programaId, int proyectoId, String linea, String tipoProy) {
        String strSQL = "SELECT count(*) AS LINEAS FROM POA.PROYECTO_LINEA LIN "
                + "WHERE LIN.YEAR = " + year + " AND "
                + "      LIN.RAMO = " + ramoId + " AND "
                + "      LIN.PRG = " + programaId + " AND "
                + "      LIN.LINEA = '" + linea + "' AND "
                + "      LIN.TIPO_PROY = '" + tipoProy + "' AND "
                + "      LIN.PROY = " + proyectoId;
        return strSQL;
    }

    public String getSQLGetProyectoByTipoProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProyecto) {
        String strSQL = "SELECT PROY.PROY, PP.DESCR, PROY.RFC, PROY.HOMOCLAVE, PROY.TIPO_PROY, PROY.DEPTO_RESP"
                + " FROM POA.PROYECTO PROY, "
                + "        DGI.VW_SP_PROY_ACT PP "
                + " WHERE PROY.PRG =   '" + programaId + "'   AND  "
                + "       PROY.YEAR =   " + year + "   AND  "
                + "       PROY.RAMO = '" + ramoId + "' AND "
                + "       PROY.TIPO_PROY = '" + tipoProyecto + "' AND "
                + "       PROY.PROY =   " + proyectoId + " AND "
                + "       PROY.PROY = PP.PROY AND "
                + "       PROY.TIPO_PROY = PP.TIPO_PROY AND "
                + "       PROY.YEAR = PP.YEAR     "
                + " ORDER BY PROY.PROY";
        return strSQL;
    }

    public String getSQLUpdateProyectoByTipoProyecto(String rfc, String homoclave, String depto, String ramoId, String programaId,
            int proyectoId, int year, String tipoProyecto) {
        String strSQL = "UPDATE POA.PROYECTO PROY SET PROY.RFC = '" + rfc + "', PROY.HOMOCLAVE = '" + homoclave + "', PROY.DEPTO_RESP = '" + depto + "' "
                + "WHERE PROY.RAMO = '" + ramoId + "' AND "
                + "      PROY.PRG = '" + programaId + "' AND "
                + "      PROY.PROY = " + proyectoId + " AND "
                + "      PROY.TIPO_PROY = '" + tipoProyecto + "' AND "
                + "      PROY.YEAR = " + year;
        return strSQL;
    }

    public String getSQLUpdateProyectoByTipoProyecto(String depto, String ramoId, String programaId,
            int proyectoId, int year, String tipoProyecto) {
        String strSQL = "UPDATE POA.PROYECTO PROY SET PROY.DEPTO_RESP = '" + depto + "' "
                + "WHERE PROY.RAMO = '" + ramoId + "' AND "
                + "      PROY.PRG = '" + programaId + "' AND "
                + "      PROY.PROY = " + proyectoId + " AND "
                + "      PROY.TIPO_PROY = '" + tipoProyecto + "' AND "
                + "      PROY.YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetLineasTipoProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProyecto) {
        String strSQL = "SELECT PLIN.LINEA, LINE.DESCR  "
                + "FROM POA.PROYECTO_LINEA PLIN, \"PUBLIC\".S_POA_SP_LIN_ESTATAL LINE "
                + "WHERE PLIN.YEAR = " + year + " AND "
                + "      PLIN.RAMO = '" + ramoId + "' AND "
                + "      PLIN.PRG = '" + programaId + "' AND "
                + "      PLIN.PROY = " + proyectoId + " AND "
                + "      PLIN.TIPO_PROY = '" + tipoProyecto + "' AND "
                + "      PLIN.LINEA = LINE.LINEA ";
        return strSQL;
    }

    public String getSQLValidarPresXCodigoPrg(String ramos, int year) {
        String strSQL = "SELECT nvl(max(count(*)),0) AS CUENTA "
                + "  FROM SPP.PPTO PTO,"
                + "       DGI.RAMOS R,"
                + "       \"PUBLIC\".S_POA_PROGRAMA PG,"
                + "       POA.RAMO_PROGRAMA RP,"
                + "       DGI.DEPENDENCIA DEP,"
                + "       DGI.PARTIDA P"
                + "  WHERE PTO.RAMO = R.RAMO "
                + "        AND PTO.YEAR = R.YEAR "
                + "        AND PTO.RAMO =  RP.RAMO"
                + "        AND PTO.PRG = PG.PRG"
                + "        AND PTO.YEAR = PG.YEAR"
                + "        AND PTO.RAMO = DEP.RAMO"
                + "        AND PTO.DEPTO = DEP.DEPTO"
                + "        AND PTO.YEAR = DEP.YEAR"
                + "        AND PTO.PARTIDA  = P.PARTIDA"
                + "        AND PTO.YEAR = P.YEAR"
                + "  		AND PTO.YEAR = " + year + " "
                + "        AND PTO.RAMO IN (" + ramos + ")"
                + "        AND RP.PRG = PG.PRG"
                + "        AND RP.YEAR = PG.YEAR"
                + " GROUP BY PTO.RAMO,"
                + "           R.DESCR,"
                + "           PTO.PRG,"
                + "           PG.DESCR,"
                + "           PTO.DEPTO,"
                + "           DEP.DESCR,"
                + "           PTO.PARTIDA,"
                + "           P.DESCR"
                + "  ORDER BY PTO.RAMO,PTO.PRG,PTO.DEPTO,PTO.PARTIDA";
        return strSQL;
    }

    public String getSQLValidarPresDetalleAccionString(String ramos, int year) {
        String strSQL = " SELECT nvl(count(*),0) as CUENTA "
                + "           FROM "
                + "           S_POA_META B, "
                + "           S_POA_PROGRAMA PRG, "
                + "           POA.RAMO_PROGRAMA RP, "
                + " DGI.VW_SP_PROY_ACT PROY, "
                + " S_POA_MEDIDA_META UMM "
                + "           WHERE "
                + "           	B.YEAR =" + year + " "
                + "           AND B.RAMO IN (" + ramos + ") "
                + "           AND PROY.YEAR = B.YEAR"
                + "           AND PROY.TIPO_PROY = B.TIPO_PROY"
                + "           AND PROY.PROY = B.PROY"
                + "           AND RP.YEAR = B.YEAR"
                + "           AND RP.RAMO = B.RAMO"
                + "           AND RP.PRG = B.PRG"
                + "           AND PRG.YEAR = B.YEAR"
                + "           AND PRG.PRG = B.PRG"
                + "           AND UMM.YEAR(+) = B.YEAR"
                + "           AND UMM.MEDIDA(+) = B.CVE_MEDIDA";
        return strSQL;
    }

    public String getSQLReportePOARamos(int year, String ramos) {
        String strSQL = "SELECT RP.RAMO, RP.PRG, P.FIN_PRG FROM POA.RAMO_PROGRAMA RP, S_POA_PROGRAMA P \n"
                + "WHERE \n"
                + "     RP.YEAR = " + year + " \n "
                + "     AND RP.RAMO IN  (" + ramos + ")  \n"
                + "     AND P.YEAR = RP.YEAR \n "
                + "     AND P.PRG = RP.PRG ";
        return strSQL;
    }

    public String getSQLReportePOARamosPrgs(int year, String ramos, String programaI, String programaF) {
        String strSQL = "SELECT RP.RAMO,\n"
                + "    RP.PRG,\n"
                + "    P.FIN_PRG \n"
                + "FROM POA.RAMO_PROGRAMA RP,\n"
                + "    S_POA_PROGRAMA P\n"
                + "WHERE      \n"
                + "    RP.YEAR = " + year + "\n"
                + "    AND RP.RAMO = " + ramos + "\n"
                + "    AND RP.PRG BETWEEN '" + programaI + "' AND '" + programaF + "'\n"
                + "    AND P.YEAR= RP.YEAR\n"
                + "    AND P.PRG = RP.PRG";
        return strSQL;
    }

    public String getSQLReportePOARamosPrg(int year, String ramos, String programa) {
        String strSQL = "SELECT RP.RAMO, RP.PRG FROM POA.RAMO_PROGRAMA RP "
                + "WHERE RP.RAMO = '" + ramos + "' AND "
                + "      RP.PRG I= '" + programa + "' AND "
                + "      RP.YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetCostoArticulo(int articulo, String partida, int year) {
        String strSQL = "SELECT * FROM S_POA_ARTICULO_PARTIDA ART "
                + "WHERE ART.PARTIDA = '" + partida + "' AND "
                + "      ART.ARTICULO = " + articulo + " AND "
                + "      ART.YEAR = " + year;
        return strSQL;
    }

    public String getSQLGetCountCodigoByRamoOrigen(String ramo, String origen, int year) {
        String strSQL = "SELECT count(*) as cuenta FROM POA.PRESUP_PLANTILLA PP\n"
                + "WHERE "
                + "    PP.ORIGEN = '" + origen + "' AND\n"
                + "    PP.RAMO = '" + ramo + "' AND "
                + "    PP.YEAR = " + year + " ";
        return strSQL;
    }

    public String getSQLGetCodigoByRamoOrigen(String ramo, String origen, int year) {
        String strSQL = "SELECT * FROM POA.PRESUP_PLANTILLA PP\n"
                + "WHERE "
                + "    PP.ORIGEN = '" + origen + "' AND "
                + "    PP.RAMO = '" + ramo + "' AND "
                + "    PP.YEAR = " + year + " ";
        return strSQL;
    }

    public String getSQLGetCodigoByRamoOrigenCntral(String origen, int year) {
        String strSQL = "SELECT * FROM POA.PRESUP_PLANTILLA PP\n"
                + "WHERE "
                + "    PP.ORIGEN = '" + origen + "' AND "
                + "    PP.YEAR = " + year + " ";
        return strSQL;
    }

    public String getSQLGetDeletePresupPlantillaParas(String origen, String ramo, int year) {
        String strSQL = "DELETE FROM \n"
                + "    POA.PRESUP_PLANTILLA PR\n"
                + "WHERE\n"
                + "    PR.YEAR = " + year + " AND\n"
                + "    PR.ORIGEN = '" + origen + "' AND\n"
                + "    PR.RAMO = '" + ramo + "'";
        return strSQL;
    }

    public String getSQLGetDeletePresupPlantillaCentral(String origen, int year) {
        String strSQL = "DELETE FROM \n"
                + "    POA.PRESUP_PLANTILLA PR\n"
                + "WHERE\n"
                + "    PR.YEAR = " + year + " AND\n"
                + "    PR.ORIGEN = '" + origen + "'";
        return strSQL;
    }

    public String getSQLDeleteAccionRePlantillaParaestatal(String ramo, String origen, int year) {
        String strSQL = "DELETE \n"
                + "FROM POA.ACCION_REQ P\n"
                + "WHERE EXISTS (\n"
                + "SELECT PP.RAMO, PP.PRG, PP.PRG_CONAC,PP.TIPO_PROY,PP.PROY, PP.FINALIDAD, PP.FUNCION, PP.SUBFUNCION, PP.META, PP.ACCION, PP.FUENTE, PP.FONDO, PP.RECURSO, PP.TIPO_GASTO, PP.MUNICIPIO, PP.DELEGACION, PP.REL_LABORAL\n"
                + "FROM \n"
                + "    POA.PRESUP_PLANTILLA PP\n"
                + "WHERE \n"
                + "    P.ARCHIVO = 1 AND \n"
                + "    P.RAMO = PP.RAMO AND \n"
                + "    P.PRG = PP.PRG  AND\n"
                + "    P.META = PP.META AND \n"
                + "    P.YEAR = PP.YEAR AND \n"
                + "    P.DEPTO = PP.DEPTO AND \n"
                + "    P.ACCION = PP.ACCION AND \n"
                + "    P.TIPO_GASTO = PP.TIPO_GASTO  AND \n"
                + "    P.FUENTE = PP.FUENTE AND\n"
                + "    P.FONDO = PP.FONDO AND\n"
                + "    P.RECURSO =PP.RECURSO AND\n"
                + "    P.PARTIDA = PP.PARTIDA AND\n"
                + "    P.REL_LABORAL = PP.REL_LABORAL AND \n"
                + "    PP.ORIGEN = '" + origen + "' AND\n"
                + "    PP.RAMO = '" + ramo + "' AND \n"
                + "  PP.YEAR = " + year
                + " )";
        return strSQL;
    }

    public String getSQLDeleteAccionRePlantillaCentral(String origen, int year) {
        String strSQL = "DELETE \n"
                + "FROM POA.ACCION_REQ P\n"
                + "WHERE EXISTS (\n"
                + "SELECT PP.RAMO, PP.PRG, PP.PRG_CONAC,PP.TIPO_PROY,PP.PROY, "
                + "PP.FINALIDAD, PP.FUNCION, PP.SUBFUNCION, PP.META, PP.ACCION, "
                + "PP.FUENTE, PP.FONDO, PP.RECURSO, PP.TIPO_GASTO, PP.MUNICIPIO, "
                + "PP.DELEGACION, PP.REL_LABORAL\n"
                + "FROM \n"
                + "    POA.PRESUP_PLANTILLA PP\n"
                + "WHERE \n"
                + "    PP.ORIGEN = '" + origen + "' AND\n"
                + "    P.ARCHIVO = 1 AND\n"
                + "    PP.RAMO = P.RAMO AND\n"
                + "    PP.PRG = P.PRG AND\n"
                + "    PP.META = P.META AND\n"
                + "    PP.YEAR = P.YEAR AND\n"
                + "    PP.DEPTO = P.DEPTO AND\n"
                + "    PP.ACCION = P.ACCION AND\n"
                + "    PP.TIPO_GASTO = P.TIPO_GASTO AND\n"
                + "    PP.FUENTE = P.FUENTE AND\n"
                + "    PP.FONDO = P.FONDO AND\n"
                + "    PP.RECURSO = P.RECURSO AND\n"
                + "    PP.PARTIDA = P.PARTIDA AND\n"
                + "    PP.REL_LABORAL = P.REL_LABORAL AND \n"
                + "  PP.YEAR = " + year + " AND "
                + "  P.YEAR = PP.YEAR "
                + ")";
        return strSQL;
    }

    public String getSQLValidarReporteLineasEstrategicas(int year) {
        String strSQL = "count(*) as cuenta "
                + "\n"
                + "FROM"
                + "	POA.RAMO_PROGRAMA RAMPRG,"
                + "	DGI.RAMOS RAM,"
                + "	S_POA_PROGRAMA PRG,"
                + "	--DGI.VW_SP_PROY_ACT VWSPPROYACT,"
                + "	S_POA_SP_LIN_ESTATAL LE,"
                + "	("
                + "		SELECT"
                + "			RAMO,"
                + "			PRG,"
                + "			LINEA,"
                + "			YEAR"
                + "		FROM"
                + "			POA.RAMO_PRG_LINEA\n"
                + "		WHERE"
                + "			YEAR = " + year + ""
                + "			MINUS"
                + "			("
                + "				SELECT"
                + "					M.RAMO,"
                + "					M.PRG,"
                + "					M.LINEA,"
                + "					M.YEAR"
                + "				FROM"
                + "					S_POA_META M,"
                + "					POA.RAMO_PRG_LINEA PL"
                + "				WHERE"
                + "					M.PRG = PL.PRG AND"
                + "					M.RAMO = PL.RAMO AND"
                + "					M.YEAR = PL.YEAR AND"
                + "					M.YEAR = 2016"
                + "\n"
                + "				UNION"
                + "\n"
                + "				SELECT"
                + "					A.RAMO,"
                + "					A.PRG,"
                + "					B.LINEA,"
                + "					A.YEAR"
                + "				FROM"
                + "					S_POA_META A,"
                + "					S_POA_ACCION B,"
                + "					POA.RAMO_PRG_LINEA PL"
                + "				WHERE"
                + "					A.YEAR = B.YEAR AND"
                + "					A.RAMO = B.RAMO AND"
                + "					A.META = B.META AND"
                + "					A.PRG = PL.PRG AND"
                + "					A.RAMO = PL.RAMO AND"
                + "					A.YEAR = PL.YEAR AND"
                + "					A.YEAR = " + year + ""
                + "			)"
                + "	) LIN,"
                + "	DGI.PARAMETROS PARAM"
                + "\n"
                + "WHERE"
                + "\n"
                + "	LIN.RAMO = RAMPRG.RAMO AND"
                + "	LIN.PRG = RAMPRG.PRG AND"
                + "	LIN.YEAR = RAMPRG.YEAR AND"
                + "\n"
                + "	LIN.RAMO = RAM.RAMO AND"
                + "	LIN.YEAR = RAM.YEAR AND"
                + "\n"
                + "	LIN.PRG = PRG.PRG AND"
                + "	LIN.YEAR = PRG.YEAR AND"
                + "/*\n"
                + "  	LIN.PRG = PROY.PRG AND"
                + "	LIN.RAMO = PROY.RAMO AND"
                + "	LIN.YEAR = PROY.YEAR AND"
                + "\n"
                + "	PROY.PROY = VWSPPROYACT.PROY AND"
                + "	PROY.TIPO_PROY = VWSPPROYACT.TIPO_PROY AND"
                + "	PROY.YEAR = VWSPPROYACT.YEAR AND*/"
                + "\n"
                + "	LIN.LINEA = LE.LINEA"
                + "\n"
                + "ORDER BY"
                + "	RAM.RAMO,"
                + "	PRG.PRG,"
                + "	/*PROY.PROY,"
                + "	PROY.TIPO_PROY,*/"
                + "	LIN.LINEA";
        return strSQL;
    }

    public String getSQLInsertPresupuestoIngreso(int year, String ramo, String concepto, int pptoId, String descr, double ene,
            double feb, double mar, double abr, double may, double jun, double jul,
            double ago, double sep, double oct, double nov, double dic) {
        String strSQL = "INSERT INTO DGI.POA_PPTO_ING ( POA_PPTO_ING.YEAR, POA_PPTO_ING.RAMO, "
                + "POA_PPTO_ING.CONCEPTO, POA_PPTO_ING.CONSEC, POA_PPTO_ING.DESCR, POA_PPTO_ING.ENE, "
                + "POA_PPTO_ING.FEB, POA_PPTO_ING.MZO, POA_PPTO_ING.ABR, POA_PPTO_ING.MAY, "
                + "POA_PPTO_ING.JUN, POA_PPTO_ING.JUL, POA_PPTO_ING.AGO, POA_PPTO_ING.SEP,"
                + " POA_PPTO_ING.OCT, POA_PPTO_ING.NOV, POA_PPTO_ING.DIC ) VALUES ( '" + year + "',"
                + " '" + ramo + "', '" + concepto + "', " + pptoId + ", '" + descr + "', " + ene + ", " + feb + ", " + mar + ","
                + " " + abr + ", " + may + ", " + jun + ", " + jul + ", " + ago + ", " + sep + ", " + oct + ", " + nov + ", " + dic + " )";
        return strSQL;
    }

    public String getSQLUpdatePresupuestoIngreso(int year, String ramo, String concepto, int pptoId, String descr, double ene,
            double feb, double mar, double abr, double may, double jun, double jul,
            double ago, double sep, double oct, double nov, double dic) {
        String strSQL = "UPDATE DGI.POA_PPTO_ING PPI SET "
                + "PPI.DESCR = '" + descr + "', "
                + "PPI.ENE = " + ene + ", "
                + "PPI.FEB = " + feb + ", "
                + "PPI.MZO = " + mar + ", "
                + "PPI.ABR = " + abr + ", "
                + "PPI.MAY = " + may + ", "
                + "PPI.JUN = " + jun + ", "
                + "PPI.JUL = " + jul + ", "
                + "PPI.AGO = " + ago + ", "
                + "PPI.SEP = " + sep + ", "
                + "PPI.OCT = " + oct + ", "
                + "PPI.NOV = " + nov + ", "
                + "PPI.DIC= " + dic + " "
                + "WHERE   PPI.YEAR='" + year + "' AND  "
                + "        PPI.RAMO='" + ramo + "' AND  "
                + "        PPI.CONCEPTO='" + concepto + "' AND "
                + "        PPI.CONSEC= " + pptoId;
        return strSQL;
    }

    public String getSLQDeletePresupuestoIngreso(int year, String ramo, String concepto, int pptoId) {
        String strSQL = "DELETE FROM DGI.POA_PPTO_ING PPI "
                + "WHERE   PPI.YEAR='" + year + "' AND  "
                + "        PPI.RAMO='" + ramo + "' AND "
                + "        PPI.CONCEPTO='" + concepto + "' AND "
                + "        PPI.CONSEC= " + pptoId;
        return strSQL;
    }

    public String getSQLGetPresupuestoIngresoById(int year, String ramo, String concepto, int pptoId) {
        String strSQL = "SELECT PPI.YEAR,   "
                + "         PPI.RAMO,   "
                + "         PPI.CONCEPTO,   "
                + "         PPI.CONSEC,   "
                + "         PPI.DESCR,   "
                + "         NVL(PPI.ENE,0) AS ENE,   "
                + "         NVL(PPI.FEB,0) AS FEB,   "
                + "         NVL(PPI.MZO,0) AS MZO,   "
                + "         NVL(PPI.ABR,0) AS ABR,   "
                + "         NVL(PPI.MAY,0) AS MAY,   "
                + "         NVL(PPI.JUN,0) AS JUN,   "
                + "         NVL(PPI.JUL,0) AS JUL,   "
                + "         NVL(PPI.AGO,0) AS AGO,   "
                + "         NVL(PPI.SEP,0) AS SEP,   "
                + "         NVL(PPI.OCT,0) AS OCT,   "
                + "         NVL(PPI.NOV,0) AS NOV,   "
                + "         NVL(PPI.DIC,0) AS DIC  "
                + "    FROM DGI.POA_PPTO_ING PPI "
                + "   WHERE  PPI.YEAR = " + year + " AND    "
                + "		  PPI.RAMO = '" + ramo + "' AND "
                + "          PPI.CONCEPTO = '" + concepto + "' AND "
                + "          PPI.CONSEC = " + pptoId;
        return strSQL;
    }

    public String getSQLGetMaxPresupuestoIngreso(int year, String ramo, String concepto) {
        String strSQL = "SELECT NVL(MAX(PPI.CONSEC),0)+1 AS MAX_PPTO "
                + "FROM DGI.POA_PPTO_ING PPI "
                + "WHERE PPI.YEAR = '" + year + "' AND "
                + "      PPI.CONCEPTO = '" + concepto + "' AND "
                + "      PPI.RAMO = '" + ramo + "'";
        return strSQL;
    }

    public String getSQLGetRamosPlantillaEmp(int year, String usuario) {
        String strSQL = "SELECT DISTINCT RAM.RAMO AS RAMO,   "
                + "         RAM.RAMO||' '||RAM.DESCR AS DESCR "
                + "    FROM DGI.RAMOS RAM,   "
                + "         POA.VW_ACC_USR_RAMO USR "
                + "   WHERE ( USR.RAMO = RAM.RAMO ) and  "
                + "         ( RAM.YEAR = USR.YEAR ) and  "
                + "         ( RAM.PLANTPER_ACT ='N'  OR (SELECT (SELECT count(DISTINCT DGI.DGI_ROL_USR.ROL)  "
                + "        FROM DGI.DGI_ROL_USR  "
                + "        WHERE DGI.DGI_ROL_USR.APP_LOGIN = '" + usuario + "' AND  "
                + "              DGI.DGI_ROL_USR.ROL = DGI.PARAMETRO_PRG.ROL) + "
                + "		 (SELECT count(DISTINCT DGI.DGI_ROL_USR.ROL)  "
                + "        FROM DGI.DGI_ROL_USR  "
                + "        WHERE DGI.DGI_ROL_USR.APP_LOGIN = '" + usuario + "' AND  "
                + "              DGI.DGI_ROL_USR.ROL = DGI.PARAMETROS.ROL_PPTO) + "
                + "		 (SELECT count(DISTINCT DGI.DGI_ROL_USR.ROL)  "
                + "        FROM DGI.DGI_ROL_USR  "
                + "        WHERE DGI.DGI_ROL_USR.APP_LOGIN = '" + usuario + "' AND  "
                + "              DGI.DGI_ROL_USR.ROL = 2) + "
                + "		 (SELECT count(DISTINCT DGI.DGI_ROL_USR.ROL)  "
                + "        FROM DGI.DGI_ROL_USR  "
                + "        WHERE DGI.DGI_ROL_USR.APP_LOGIN = '" + usuario + "' AND  "
                + "              DGI.DGI_ROL_USR.ROL = 10) AS li_rol  "
                + "FROM DGI.PARAMETRO_PRG,  "
                + "     DGI.PARAMETROS)>0) and  "
                + "         ( ( USR.APP_LOGIN = '" + usuario + "') AND  "
                + "         ( RAM.YEAR = " + year + " ) ) "
                + "ORDER BY RAM.RAMO ";
        return strSQL;
    }

    public String getSQLGetPlantillaEmpleado(int year, String ramo) {
        String strSQL = "SELECT RPP.REL_LABORAL, SR.DESCR, RPP.CANTIDAD "
                + "FROM POA.RAMO_PLANTILLA_PERSONAL RPP,S_POA_REL_LABORAL SR "
                + "WHERE RPP.YEAR = " + year + " AND "
                + "      RPP.RAMO = '" + ramo + "' AND "
                + "      RPP.REL_LABORAL = SR.REL_LABORAL AND "
                + "      RPP.YEAR = SR.YEAR";
        return strSQL;
    }

    public String getSQLInsertPlantillaEmpleado(int year, String ramo, String relLab, double cantidad) {
        String strSQL = "INSERT INTO POA.RAMO_PLANTILLA_PERSONAL "
                + "( RAMO_PLANTILLA_PERSONAL.YEAR, RAMO_PLANTILLA_PERSONAL.RAMO, "
                + "RAMO_PLANTILLA_PERSONAL.REL_LABORAL, RAMO_PLANTILLA_PERSONAL.CANTIDAD ) "
                + "VALUES ( '" + year + "', '" + ramo + "', '" + relLab + "', " + cantidad + ")";
        return strSQL;
    }

    public String getSQLGetPlantillaPersonalById(int year, String ramo, String relLaboral) {
        String strSQL = "SELECT RPP.REL_LABORAL, SR.DESCR, RPP.CANTIDAD  "
                + "FROM POA.RAMO_PLANTILLA_PERSONAL RPP,\"PUBLIC\".S_POA_REL_LABORAL SR "
                + "WHERE RPP.YEAR = " + year + " AND "
                + "      RPP.RAMO = '" + ramo + "' AND "
                + "      RPP.REL_LABORAL = SR.REL_LABORAL AND "
                + "      RPP.YEAR = SR.YEAR AND "
                + "      RPP.REL_LABORAL =  '" + relLaboral + "'";
        return strSQL;
    }

    public String getSQLUpdatePlantillaPersonal(int year, String ramo, String relLaboral, double cantidad, String relLaboralAnt) {
        String strSQL = "UPDATE POA.RAMO_PLANTILLA_PERSONAL RPP  "
                + "SET RPP.REL_LABORAL = '" + relLaboral + "', "
                + "    RPP.CANTIDAD = " + cantidad + " "
                + "WHERE RPP.YEAR = " + year + " AND "
                + "      RPP.RAMO = '" + ramo + "' AND "
                + "      RPP.REL_LABORAL = '" + relLaboralAnt + "'";
        return strSQL;
    }

    public String getSQLDeletePlantillaPersonal(int year, String ramo, String relLaboral) {
        String strSQL = "DELETE FROM POA.RAMO_PLANTILLA_PERSONAL RPP "
                + "WHERE RPP.YEAR = " + year + " AND "
                + "      RPP.RAMO = '" + ramo + "' AND "
                + "      RPP.REL_LABORAL = '" + relLaboral + "'";
        return strSQL;
    }

    public String getSQLInsertPresupPlantilla(String origen, int year, String ramo, String depto, String finalidad, String funcion,
            String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab,
            int mes, double cantidad) {
        String strSQL = "INSERT INTO POA.PRESUP_PLANTILLA ( PRESUP_PLANTILLA.ORIGEN, PRESUP_PLANTILLA.YEAR, "
                + "PRESUP_PLANTILLA.RAMO, PRESUP_PLANTILLA.DEPTO, PRESUP_PLANTILLA.FINALIDAD, PRESUP_PLANTILLA.FUNCION,"
                + "PRESUP_PLANTILLA.SUBFUNCION, PRESUP_PLANTILLA.PRG_CONAC, PRESUP_PLANTILLA.PRG, "
                + "PRESUP_PLANTILLA.TIPO_PROY, PRESUP_PLANTILLA.PROY, PRESUP_PLANTILLA.META, PRESUP_PLANTILLA.ACCION, "
                + "PRESUP_PLANTILLA.PARTIDA, PRESUP_PLANTILLA.TIPO_GASTO, PRESUP_PLANTILLA.FUENTE, "
                + "PRESUP_PLANTILLA.FONDO, PRESUP_PLANTILLA.RECURSO, PRESUP_PLANTILLA.MUNICIPIO, PRESUP_PLANTILLA.DELEGACION, "
                + "PRESUP_PLANTILLA.REL_LABORAL, PRESUP_PLANTILLA.MES, PRESUP_PLANTILLA.CANTIDAD ) \n"
                + "VALUES ( '" + origen + "', "
                + "'" + year + "', "
                + "'" + ramo + "', "
                + "'" + depto + "', "
                + "'" + finalidad + "', "
                + "'" + funcion + "', "
                + "'" + subfuncion + "', "
                + "'" + prgConac + "', "
                + "'" + programa + "', "
                + "'" + tipoProy + "', "
                + "" + proyecto + ", "
                + "" + meta + ", "
                + "" + accion + ", "
                + "'" + partida + "', "
                + "'" + tipoGasto + "', "
                + "'" + fuente + "', "
                + "'" + fondo + "', "
                + "'" + recurso + "', "
                + "'" + municipio + "', "
                + "" + delegacion + ", "
                + "'" + relLab + "', "
                + "'" + mes + "', "
                + "" + cantidad + " ) \n";
        return strSQL;
    }

    public String getSQLInsertPptoPresupPlantilla(List<Ppto> presList) {
        boolean bandera = false;
        String strSQL = "INSERT ALL \n";
        Ppto pres = presList.get(0);
        for (int it = 1; it < 13; it++) {
            bandera = false;
            strSQL += "INTO SPP.PPTO ( PPTO.YEAR, PPTO.RAMO, PPTO.DEPTO, PPTO.FINALIDAD, PPTO.FUNCION, "
                    + "PPTO.SUBFUNCION, PPTO.PRG_CONAC, PPTO.PRG, PPTO.TIPO_PROY, PPTO.PROYECTO, PPTO.META, "
                    + "PPTO.ACCION, PPTO.PARTIDA, PPTO.TIPO_GASTO, PPTO.FUENTE, PPTO.FONDO, PPTO.RECURSO, "
                    + "PPTO.MUNICIPIO, PPTO.DELEGACION, PPTO.REL_LABORAL, PPTO.MES, PPTO.ASIGNADO,PPTO.ORIGINAL) \n"
                    + "VALUES ( '" + pres.getYear() + "', '" + pres.getRamo() + "', '" + pres.getDepto() + "', '" + pres.getFinalidad() + "',"
                    + " '" + pres.getFuncion() + "', '" + pres.getSubfuncion() + "', '" + pres.getPrgConac() + "', '" + pres.getPrg() + "',"
                    + " '" + pres.getTipoProy() + "', " + pres.getProyecto() + ", " + pres.getMeta() + ", " + pres.getAccion() + ","
                    + " '" + pres.getPartida() + "', '" + pres.getTipoGasto() + "', '" + pres.getFuente() + "', '" + pres.getFondo() + "',"
                    + " '" + pres.getRecurso() + "', '" + pres.getMunicipio() + "', " + pres.getDelegacion() + ", '" + pres.getRelLaboral() + "',";

            for (Ppto resTemp : presList) {
                if (it == resTemp.getMes()) {
                    strSQL += " " + resTemp.getMes() + ", " + resTemp.getAsignado() + ", " + resTemp.getAsignado() + ") \n";
                    bandera = true;
                }
            }
            if (!bandera) {
                strSQL += " " + it + ", 0.00, 0.00) \n";
            }
        }
        strSQL += "\n SELECT * FROM SYS.DUAL";
        return strSQL;
    }

    public String getSQLgetContCodigoPlantilla(int year, String ramo, String programa, String depto) {
        String strSQL = "SELECT "
                + "    COUNT(*) AS CONT_COD "
                + "FROM "
                + "    POA.VW_CODIGO_PLANTILLA "
                + "WHERE "
                + "    YEAR = ? AND "
                + "    RAMO = ? AND "
                + "    PRG = ? AND "
                + "    DEPTO = ? ";
        return strSQL;
    }

    public String getSQLGetCodigoCompleto(int year, String ramo, String programa, String depto) {
        String strSQL = "SELECT \n"
                + "    * \n"
                + "FROM \n"
                + "    POA.VW_CODIGO_PLANTILLA\n"
                + "WHERE \n"
                + "    YEAR = " + year + " AND\n"
                + "    RAMO = '" + ramo + "' AND\n"
                + "    PRG = '" + programa + "' AND\n"
                + "    DEPTO = '" + depto + "' \n"
                + "ORDER BY\n"
                + "    YEAR, RAMO, PRG, DEPTO, FUENTE, FONDO, RECURSO";
        return strSQL;
    }

    public String getSQLInsertMasivoPresupPlantilla(List<Ppto> codigo) {
        String strSQL = new String();
        strSQL = "INSERT ALL ";
        for (Ppto cod : codigo) {
            strSQL += "  INTO POA.PRESUP_PLANTILLA( PRESUP_PLANTILLA.ORIGEN, PRESUP_PLANTILLA.YEAR, "
                    + "        PRESUP_PLANTILLA.RAMO, PRESUP_PLANTILLA.DEPTO, PRESUP_PLANTILLA.FINALIDAD, PRESUP_PLANTILLA.FUNCION, "
                    + "        PRESUP_PLANTILLA.SUBFUNCION, PRESUP_PLANTILLA.PRG_CONAC, PRESUP_PLANTILLA.PRG,  "
                    + "        PRESUP_PLANTILLA.TIPO_PROY, PRESUP_PLANTILLA.PROY, PRESUP_PLANTILLA.META, PRESUP_PLANTILLA.ACCION, "
                    + "        PRESUP_PLANTILLA.PARTIDA, PRESUP_PLANTILLA.TIPO_GASTO, PRESUP_PLANTILLA.FUENTE, "
                    + "        PRESUP_PLANTILLA.FONDO, PRESUP_PLANTILLA.RECURSO, PRESUP_PLANTILLA.MUNICIPIO, PRESUP_PLANTILLA.DELEGACION, "
                    + "        PRESUP_PLANTILLA.REL_LABORAL, PRESUP_PLANTILLA.MES, PRESUP_PLANTILLA.CANTIDAD) \n";
            strSQL += " VALUES ( '" + cod.getOrigen() + "', "
                    + "'" + cod.getYear() + "', "
                    + "'" + cod.getRamo() + "', "
                    + "'" + cod.getDepto() + "', "
                    + "'" + cod.getFinalidad() + "', "
                    + "'" + cod.getFuncion() + "', "
                    + "'" + cod.getSubfuncion() + "', "
                    + "'" + cod.getPrgConac() + "', "
                    + "'" + cod.getPrg() + "', "
                    + "'" + cod.getTipoProy() + "', "
                    + "'" + cod.getProyectoId() + "', "
                    + "" + cod.getMetaId() + ", "
                    + "" + cod.getAccionId() + ", "
                    + "'" + cod.getPartida() + "', "
                    + "'" + cod.getTipoGasto() + "', "
                    + "'" + cod.getFuente() + "', "
                    + "'" + cod.getFondo() + "', "
                    + "'" + cod.getRecurso() + "', "
                    + "'" + cod.getMunicipio() + "', "
                    + "" + cod.getDelegacionId() + ", "
                    + "'" + cod.getRelLaboral() + "', "
                    + "'" + cod.getMes() + "', "
                    + "" + cod.getAsignado() + " ) \n";
        }
        strSQL += "SELECT * FROM SYS.DUAL";
        return strSQL;
    }

    public String getSQLInsertMasivoPresupPlantilla(String origen, int year, String ramo, String depto, String finalidad, String funcion,
            String subFuncion, String prgConac, String programa, String tipoProy, String proyecto, String meta, String accion,
            String partida, String tipoGasto, String fuente, String fondo, String recurso, String municipio, String delegacion, String relLaboral,
            int mes, double asigando) {
        String strSQL = new String();
        strSQL = "INSERT INTO POA.PRESUP_PLANTILLA( PRESUP_PLANTILLA.ORIGEN, PRESUP_PLANTILLA.YEAR, "
                + "        PRESUP_PLANTILLA.RAMO, PRESUP_PLANTILLA.DEPTO, PRESUP_PLANTILLA.FINALIDAD, PRESUP_PLANTILLA.FUNCION, "
                + "        PRESUP_PLANTILLA.SUBFUNCION, PRESUP_PLANTILLA.PRG_CONAC, PRESUP_PLANTILLA.PRG,  "
                + "        PRESUP_PLANTILLA.TIPO_PROY, PRESUP_PLANTILLA.PROY, PRESUP_PLANTILLA.META, PRESUP_PLANTILLA.ACCION, "
                + "        PRESUP_PLANTILLA.PARTIDA, PRESUP_PLANTILLA.TIPO_GASTO, PRESUP_PLANTILLA.FUENTE, "
                + "        PRESUP_PLANTILLA.FONDO, PRESUP_PLANTILLA.RECURSO, PRESUP_PLANTILLA.MUNICIPIO, PRESUP_PLANTILLA.DELEGACION, "
                + "        PRESUP_PLANTILLA.REL_LABORAL, PRESUP_PLANTILLA.MES, PRESUP_PLANTILLA.CANTIDAD) \n"
                + " VALUES ( '" + origen + "', "
                + "'" + year + "', "
                + "'" + ramo + "', "
                + "'" + depto + "', "
                + "'" + finalidad + "', "
                + "'" + funcion + "', "
                + "'" + subFuncion + "', "
                + "'" + prgConac + "', "
                + "'" + programa + "', "
                + "'" + tipoProy + "', "
                + "'" + proyecto + "', "
                + "" + meta + ", "
                + "" + accion + ", "
                + "'" + partida + "', "
                + "'" + tipoGasto + "', "
                + "'" + fuente + "', "
                + "'" + fondo + "', "
                + "'" + recurso + "', "
                + "'" + municipio + "', "
                + "" + delegacion + ", "
                + "'" + relLaboral + "', "
                + "'" + mes + "', "
                + "TRUNC(" + asigando + ",2) ) \n";
        /* if(tipoGasto == null || tipoGasto.isEmpty()){
         System.out.println("sa");
         }
         System.out.println(tipoGasto);*/
        return strSQL;
    }

    public String getSQLGruposPresupPlantilla(String origen) {
        String strSQL = "SELECT PP.YEAR, PP.RAMO, PP.DEPTO, PP.FINALIDAD, PP.FUNCION, PP.SUBFUNCION, PP.PRG_CONAC, PP.PRG,PP.TIPO_PROY,PP.PROY, "
                + "        PP.META, PP.ACCION, PP.PARTIDA, PP.TIPO_GASTO,PP.FONDO, PP.FUENTE, PP.RECURSO, PP.MUNICIPIO, PP.DELEGACION, PP.REL_LABORAL,MES,CANTIDAD "
                + "FROM POA.PRESUP_PLANTILLA PP "
                + " WHERE PP.ESTATUS = '0' AND PP.ORIGEN = '" + origen + "' "
                + " ORDER BY PP.YEAR, PP.RAMO, PP.DEPTO, PP.FINALIDAD, PP.FUNCION, PP.SUBFUNCION, PP.PRG_CONAC, PP.PRG,PP.TIPO_PROY,PP.PROY, "
                + "        PP.META, PP.ACCION, PP.PARTIDA, PP.TIPO_GASTO,PP.FONDO, PP.FUENTE, PP.RECURSO, PP.MUNICIPIO, PP.DELEGACION, PP.REL_LABORAL,to_number(MES)";
        /*     + "GROUP BY PP.YEAR, PP.RAMO, PP.DEPTO, PP.FINALIDAD, PP.FUNCION, PP.SUBFUNCION, PP.PRG_CONAC, PP.PRG,PP.TIPO_PROY,PP.PROY, "
         + "    PP.META, PP.ACCION, PP.PARTIDA, PP.TIPO_GASTO,PP.FONDO, PP.FUENTE, PP.RECURSO, PP.MUNICIPIO, PP.DELEGACION, PP.REL_LABORAL"*/;
        return strSQL;
    }

    public String getSQLDeletePresupPlantillaMal() {
        String strSQL = "DELETE "
                + "FROM POA.PRESUP_PLANTILLA PP "
                + "WHERE PP.ESTATUS = 0";
        return strSQL;
    }

    public String getSQLUpdateEstatusPresupPlantilla() {
        String strSQL = "UPDATE POA.PRESUP_PLANTILLA PP "
                + "SET PP.ESTATUS = 1 "
                + "WHERE PP.ESTATUS = 0";
        return strSQL;
    }

    public String getSQLisCodigoCapturadoCarga(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion) {
        String strSQL = "SELECT COUNT (*) AS CODIGO FROM SPP.CODIGOS COD\n"
                + "WHERE COD.YEAR = " + year + " AND\n"
                + "      COD.RAMO = '" + ramo + "' AND\n"
                + "      COD.DEPTO = '" + depto + "' AND\n"
                + "      COD.FINALIDAD = '" + finalidad + "' AND\n"
                + "      COD.FUNCION = '" + funcion + "' AND\n"
                + "      COD.SUBFUNCION = '" + subfuncion + "' AND\n"
                + "      COD.PRG_CONAC = '" + prgConac + "' AND\n"
                + "      COD.PRG = '" + programa + "' AND\n"
                + "      COD.TIPO_PROY = '" + tipoProy + "' AND\n"
                + "      COD.PROYECTO = " + proyecto + " AND\n"
                + "      COD.META = " + meta + " AND\n"
                + "      COD.ACCION = " + accion + " AND\n"
                + "      COD.TIPO_GASTO = '" + tipoGasto + "' AND\n"
                + "      COD.FUENTE = '" + fuente + "' AND\n"
                + "      COD.FONDO = '" + fondo + "' AND\n"
                + "      COD.RECURSO = '" + recurso + "' AND\n"
                + "      COD.MUNICIPIO = '" + municipio + "' AND\n"
                + "      COD.DELEGACION = " + delegacion;
        return strSQL;
    }

    public String getSQLGetLineasRamoPrg(int year, String ramo, String prg) {
        String strSQL = "SELECT LI.LINEA, LE.DESCR FROM  "
                + "POA.RAMO_PRG_LINEA LI , S_POA_SP_LIN_ESTATAL LE "
                + "WHERE LI.YEAR = " + year + " AND "
                + "      LI.RAMO = '" + ramo + "' AND "
                + "      LI.PRG = '" + prg + "' AND "
                + "      LI.LINEA = LE.LINEA AND "
                + "      DGI.F_OBTIENE_YEAR_ADMVO(LI.YEAR) = LE.YEAR";
        return strSQL;
    }

    public String getSQLGetAllLineasRamoPrg(int year, String ramo) {
        String strSQL = "SELECT S_POA_SP_LIN_ESTATAL.LINEA,  "
                + "         S_POA_SP_LIN_ESTATAL.DESCR,  "
                + "         S_POA_SP_LIN_EST.EST,  "
                + "         S_POA_SP_LIN_EST.DESCR,  "
                + "         S_POA_SP_LIN_ESTATAL.TIPO ,"
                + "         S_POA_SP_LIN_OBJ.OBJ, "
                + "         S_POA_SP_LIN_OBJ.DESCR "
                + "    FROM S_POA_SP_LIN_EST,  "
                + "         S_POA_SP_LIN_ESTATAL, "
                + "         S_POA_SP_LIN_OBJ, "
                + "         DGI.SECTOR_RAMO "
                + "   WHERE ( S_POA_SP_LIN_ESTATAL.obj = S_POA_SP_LIN_EST.obj ) and  "
                + "         ( S_POA_SP_LIN_ESTATAL.est = S_POA_SP_LIN_EST.est ) AND "
                + "         ( S_POA_SP_LIN_ESTATAL.year = S_POA_SP_LIN_EST.year ) AND "
                + "         ( S_POA_SP_LIN_ESTATAL.obj = S_POA_SP_LIN_OBJ.obj) and "
                + "         ( S_POA_SP_LIN_ESTATAL.year = S_POA_SP_LIN_OBJ.year) and "
                + "         ( S_POA_SP_LIN_ESTATAL.obj = dgi.sector_ramo.obj) and "
                + "         ( dgi.sector_ramo.ramo = '" + ramo + "' ) and "
                + "         ( dgi.sector_ramo.year = " + year + " ) and "
                + "          S_POA_SP_LIN_OBJ.YEAR = DGI.F_OBTIENE_YEAR_ADMVO(" + year + ") AND "
                + "           S_POA_SP_LIN_ESTATAL.activo='S' "
                + "union all "
                + "  SELECT DGI.RAMO_LINEA.LINEA, "
                + "         S_POA_SP_LIN_ESTATAL.DESCR, "
                + "                                                S_POA_SP_LIN_EST.EST, "
                + "                                                S_POA_SP_LIN_EST.DESCR,"
                + "         S_POA_SP_LIN_ESTATAL.TIPO ,"
                + "         S_POA_SP_LIN_OBJ.OBJ, "
                + "         S_POA_SP_LIN_OBJ.DESCR "
                + "    FROM DGI.RAMO_LINEA, "
                + "         S_POA_SP_LIN_ESTATAL, "
                + "         S_POA_SP_LIN_EST, "
                + "         S_POA_SP_LIN_OBJ "
                + "   WHERE ( DGI.RAMO_LINEA.RAMO = '" + ramo + "' ) AND "
                + "         ( DGI.RAMO_LINEA.YEAR = " + year + " )   AND "
                + "           S_POA_SP_LIN_OBJ.YEAR = DGI.F_OBTIENE_YEAR_ADMVO(" + year + ") AND "
                + "         ( DGI.RAMO_LINEA.LINEA = S_POA_SP_LIN_ESTATAL.LINEA) AND "
                + "         ( S_POA_SP_LIN_ESTATAL.OBJ = S_POA_SP_LIN_EST.OBJ ) AND "
                + "         ( S_POA_SP_LIN_ESTATAL.EST = S_POA_SP_LIN_EST.EST ) AND "
                + "         ( S_POA_SP_LIN_ESTATAL.YEAR = S_POA_SP_LIN_EST.YEAR ) AND "
                + "         ( S_POA_SP_LIN_ESTATAL.OBJ = S_POA_SP_LIN_OBJ.OBJ) and "
                + "         ( S_POA_SP_LIN_ESTATAL.YEAR = S_POA_SP_LIN_OBJ.YEAR) and "
                + "           S_POA_SP_LIN_ESTatal.activo='S' "
                + "  ORDER BY LINEA ";
        return strSQL;
    }

    public String getSQLDeleteLineaAccion(int year, String ramo, String programa) {
        String strSQL = "DELETE FROM POA.RAMO_PRG_LINEA LI  "
                + "WHERE LI.YEAR = " + year + " AND "
                + "      LI.RAMO = " + ramo + " AND "
                + "      LI.PRG = '" + programa + "'";
        return strSQL;
    }

    public String getSQLInsertLineaAccion(int year, String ramo, String programa, String[] lineas) {
        String strSQL = new String();
        strSQL = "INSERT ALL \n";
        for (int it = 0; it < lineas.length; it++) {
            strSQL += "INTO POA.RAMO_PRG_LINEA  (YEAR, RAMO,PRG, LINEA) \n";
            strSQL += "VALUES (" + year + ", '" + ramo + "', '" + programa + "', '" + lineas[it] + "') \n";
        }
        strSQL += "SELECT * FROM SYS.DUAL";
        return strSQL;
    }

    public String getSQLCountLineasAccion(int year, String ramo, String programa) {
        String strSQL = "SELECT COUNT(*) AS LINEAS FROM  "
                + "POA.RAMO_PRG_LINEA LI  "
                + "WHERE LI.YEAR = " + year + " AND "
                + "      LI.RAMO = " + ramo + " AND "
                + "      LI.PRG = '" + programa + "'";
        return strSQL;
    }

    public String getSQLGetTipoCompromisoMeta(int year, String ramo, int meta) {
        String strSQL = "SELECT TP.BENEFICIADOS "
                + "FROM "
                + "    S_POA_META M,"
                + "    DGI.POA_TIPO_COMPROMISO TP "
                + "WHERE "
                + "    M.RAMO = '" + ramo + "' AND "
                + "    M.YEAR = " + year + " AND "
                + "    M.META = " + meta + " AND "
                + "    TP.TIPO_COMPROMISO = M.TIPO_COMPROMISO";
        return strSQL;
    }

    public String getSQLisPartidaJustif(int year, String partida) {
        String strSQL = "SELECT P.JUSTIF_REQ FROM DGI.PARTIDA P "
                + "WHERE P.YEAR = " + year + " AND "
                + "      P.PARTIDA = '" + partida + "'";
        return strSQL;
    }

    public String getSQLgetContEstimacionAccion(int year, String ramo, int meta, int accion) {
        String strSQL = "SELECT NVL(SUM(AE.VALOR),0) AS CONT_EST FROM DGI.ACCION_ESTIMACION AE "
                + "WHERE AE.YEAR = " + year + " AND "
                + "AE.RAMO = '" + ramo + "' AND "
                + "     AE.META = " + meta + " AND "
                + "     AE.ACCION = " + accion;
        return strSQL;
    }

    public String getSQLGetAsignadosAuto(int year, String ramo) {
        String strSQL = ""
                + "SELECT  "
                + "	DISTINCT "
                + "	AR.DEPTO, "
                + "	D.DESCR AS DEPTO_D,  "
                + "	AR.PRG, "
                + "	RP.DESCR AS PRG_D, "
                + "	AR.META, "
                + "	M.DESCR AS META_D, "
                + "	AR.ACCION, "
                + "	A.DESCR AS ACCION_D,  "
                + "	AR.FUENTE||'.'||AR.FONDO||'.'||AR.RECURSO||'-'||R.DESCR AS FUENTE_F,  "
                + "	1 AS AUTO "
                + "FROM  "
                + "	S_POA_ACCION_REQ AR, "
                + "	S_POA_PROGRAMA RP,  "
                + "	S_POA_META M, "
                + "	S_POA_ACCION A, "
                + "	S_POA_RECURSO R,  "
                + "	DGI.DEPENDENCIA D, "
                + "	( "
                + "		SELECT  "
                + "			DISTINCT  "
                + "			R.DEPTO "
                + "		FROM  "
                + "			( "
                + "				SELECT "
                + "					DISTINCT "
                + "					REQ.RAMO, "
                + "					REQ.DEPTO, "
                + "					REQ.META, "
                + "					REQ.ACCION, "
                + "					REQ.FUENTE, "
                + "					REQ.FONDO, "
                + "					REQ.RECURSO, "
                + "					REQ.YEAR "
                + "				FROM  "
                + "					S_POA_ACCION_REQ REQ, "
                + "					S_POA_RECURSO REC "
                + "				WHERE "
                + "					REQ.YEAR = " + year + " AND "
                + "					REQ.RAMO = '" + ramo + "' AND "
                + "					REC.YEAR = REQ.YEAR AND "
                + "					REC.FUENTE = REQ.FUENTE AND "
                + "					REC.FONDO = REQ.FONDO AND "
                + "			        REC.RECURSO = REQ.RECURSO AND "
                + "					REC.PLANTILLA = 'S' "
                + "				GROUP BY "
                + "					REQ.RAMO, "
                + "					REQ.DEPTO, "
                + "					REQ.META, "
                + "					REQ.ACCION, "
                + "					REQ.FUENTE, "
                + "					REQ.FONDO, "
                + "					REQ.RECURSO, "
                + "					REQ.YEAR "
                + "				ORDER BY "
                + "					REQ.RAMO, "
                + "					REQ.DEPTO, "
                + "					REQ.META, "
                + "					REQ.ACCION, "
                + "					REQ.FUENTE, "
                + "					REQ.FONDO, "
                + "					REQ.RECURSO, "
                + "					REQ.YEAR "
                + "			) R  "
                + "		WHERE  "
                + "			R.YEAR = " + year + " AND "
                + "			R.RAMO = '" + ramo + "'  "
                + "		GROUP BY  "
                + "			R.DEPTO "
                + "			HAVING COUNT(R.META) = 1 AND COUNT(R.ACCION) = 1 AND COUNT(R.FUENTE||'.'||R.FONDO||'.'||R.RECURSO) = 1 "
                + "		ORDER BY  "
                + "			R.DEPTO "
                + "	) DEP "
                + "WHERE  "
                + "	AR.YEAR = " + year + " AND "
                + "	AR.RAMO = '" + ramo + "' AND "
                + "	DEP.DEPTO = AR.DEPTO AND "
                + "	RP.YEAR = AR.YEAR AND "
                + "	RP.PRG = AR.PRG AND "
                + "	M.YEAR = AR.YEAR AND "
                + "	M.RAMO = AR.RAMO AND "
                + "	M.META = AR.META AND "
                + "	A.YEAR = AR.YEAR AND "
                + "	A.RAMO = AR.RAMO AND "
                + "	A.META = AR.META AND "
                + "	A.ACCION = AR.ACCION AND "
                + "	R.YEAR = AR.YEAR AND "
                + "	R.FUENTE = AR.FUENTE AND "
                + "	R.FONDO = AR.FONDO AND "
                + "	R.RECURSO = AR.RECURSO AND "
                + "	D.YEAR = AR.YEAR AND "
                + "	D.RAMO = AR.RAMO AND "
                + "	D.DEPTO = AR.DEPTO AND "
                + "	R.PLANTILLA = 'S'   ";
        return strSQL;
    }

    public String getSQLgetPlantillaAsignada(int year, String ramo) {
        String strSQL = ""
                + "                 SELECT       "
                + "                 	MAP.DEPTO,      "
                + "                 	D.DESCR AS DEPTO_D,      "
                + "                 	MAP.PRG,      "
                + "                 	RP.DESCR AS PRG_D,       "
                + "                	MAP.META,      "
                + "                 	M.DESCR AS META_D,       "
                + "                 	MAP.ACCION,      "
                + "                 	A.DESCR AS ACCION_D,      "
                + "                 	0 AS AUTO      "
                + "                 FROM      "
                + "                 	POA.META_ACCION_PLANTILLA MAP,   "
                + "                 	S_POA_PROGRAMA RP,     "
                + "                	S_POA_META M,     "
                + "                 	S_POA_ACCION A,     "
                + "                 	DGI.DEPENDENCIA D     "
                + "                 WHERE      "
                + "                 	MAP.YEAR =   " + year + "   AND     "
                + "                 	MAP.RAMO = '" + ramo + "' AND     "
                + "                 	RP.YEAR = MAP.YEAR AND     "
                + "                 	RP.PRG = MAP.PRG AND     "
                + "                 	M.YEAR = MAP.YEAR AND     "
                + "                 	M.RAMO = MAP.RAMO AND     "
                + "                 	M.META = MAP.META AND     "
                + "                 	A.YEAR = MAP.YEAR AND     "
                + "                 	A.RAMO = MAP.RAMO AND    "
                + "                 	A.META = MAP.META AND     "
                + "                 	A.ACCION = MAP.ACCION AND     "
                + "                 	D.YEAR = MAP.YEAR AND     "
                + "                 	D.RAMO = MAP.RAMO AND     "
                + "                 	D.DEPTO = MAP.DEPTO   "
                + "ORDER BY  "
                + "	MAP.DEPTO,  "
                + "	MAP.PRG,  "
                + "	MAP.META,  "
                + "	MAP.ACCION  "
                + "";

        return strSQL;
    }

    public String getSQLdeleteAsignado(int year, String ramo, String depto) {
        String strSQL = "DELETE POA.META_ACCION_PLANTILLA MAP "
                + "WHERE MAP.YEAR = " + year + " AND "
                + "      MAP.RAMO = '" + ramo + "' AND "
                + "      MAP.DEPTO = " + depto;
        return strSQL;
    }

    public String getSQLValidarCierreEjercicioByYear(int year) {
        String strSQL = ""
                + "SELECT  "
                + "   PARAM.YEAR_ANT, "
                + "   PARAM.YEAR, "
                + "   PARAM.YEAR_NVO  "
                + "FROM  "
                + "   DGI.PARAMETROS PARAM ";

        return strSQL;
    }

    public String getSQLGetCatalogoLocalidad(int year) {
        String strSQL = ""
                + "SELECT "
                + "    LOC.MPO, "
                + "    LPAD(LOC.LOCALIDAD,PLC.DELEGACION,'0') AS LOCALIDAD, "
                + "    LOC.DESCR  "
                + "FROM "
                + "    \"PUBLIC\".S_POA_LOCALIDAD LOC, "
                + "    S_DGI_PARAM_LONG_COD PLC "
                + "    WHERE PLC.YEAR = " + year;

        return strSQL;
    }

    public String getSQLGetCatalogoMedidaMeta(int year) {
        String strSQL = ""
                + "SELECT  "
                + "    MEDMET.MEDIDA, "
                + "    MEDMET.DESCR, "
                + "    MEDMET.ACTIVO, "
                + "    MEDMET.YEAR "
                + "FROM "
                + "    \"PUBLIC\".S_POA_MEDIDA_META MEDMET "
                + "    WHERE YEAR = " + year;
        return strSQL;
    }

    public String getSQLgetTipoGastoByPartida(String partida, int year) {
        String strSQL = "SELECT NVL(SSG.TIPO_GASTO,0) ||'-'||TG.DESCR AS TIPO_GASTO  "
                + "FROM S_DGI_SUBSUBGPO SSG,S_POA_TIPO_GASTO TG "
                + "WHERE SSG.SUBSUBGRUPO IN ( "
                + "    SELECT PAR.SUBSUBGPO FROM DGI.PARTIDA PAR  "
                + "    WHERE PAR.PARTIDA = '" + partida + "' AND   "
                + "        PAR.YEAR = " + year + ") AND "
                + "    SSG.TIPO_GASTO = TG.TIPO_GASTO AND "
                + "    TG.YEAR = " + year;
        return strSQL;
    }

    public String getSQLgetCountTipoGastoByPartida() {
        String strSQL = "SELECT COUNT(1) AS TIPO_GASTO  "
                + "FROM S_DGI_SUBSUBGPO SSG,S_POA_TIPO_GASTO TG \n"
                + "WHERE SSG.SUBSUBGRUPO IN ( \n"
                + "     SELECT PAR.SUBSUBGPO FROM DGI.PARTIDA PAR  \n"
                + "        WHERE PAR.PARTIDA = ? AND   \n"
                + "        PAR.YEAR =   ? ) \n"
                + "     AND SSG.TIPO_GASTO = TG.TIPO_GASTO \n"
                + "     AND TG.YEAR =  ? ";
        return strSQL;
    }

    public String getSQLgetTipoGastoPlantilla(String partida, int year) {
        String strSQL = "SELECT NVL(SSG.TIPO_GASTO,0) AS TIPO_GASTO  "
                + "    FROM S_DGI_SUBSUBGPO SSG \n"
                + "    WHERE SSG.SUBSUBGRUPO IN (  \n"
                + "        SELECT PAR.SUBSUBGPO FROM DGI.PARTIDA PAR   \n"
                + "        WHERE PAR.PARTIDA = '" + partida + "' AND    \n"
                + "            PAR.YEAR = " + year + ")";
        return strSQL;
    }

    public String getSQLisAccionRegistrado(int year, String ramo, String origen, boolean isParaestatal) {
        /*String strSQL = "SELECT COUNT(*) AS ACCION FROM POA.ACCION A\n" +
         "WHERE \n" +
         "    A.YEAR = "+year+" AND\n" +
         "    A.RAMO = '"+ramo+"' AND\n" +
         "    A.PRG = '"+programa+"' AND\n" +
         "    A.DEPTO = '"+depto+"' AND\n" +
         "    A.META = "+meta+" AND\n" +
         "    A.ACCION = " + accion;*/
        String strSQL = "SELECT "
                + " DISTINCT (SELECT COUNT(*) "
                + " FROM POA.ACCION A "
                + " WHERE A.YEAR = P.YEAR "
                + " AND A.RAMO = P.RAMO "
                + " AND A.PRG = P.PRG "
                + " AND A.DEPTO = P.DEPTO  "
                + " AND A.META = P.META "
                + " AND A.ACCION = P.ACCION"
                + ") FROM POA.PRESUP_PLANTILLA P  WHERE YEAR =" + year + " "
                + " AND Origen = '" + origen + "'";
        if (isParaestatal) {
            strSQL += " AND RAMO = '" + ramo + "' ";
        }
        return strSQL;
    }

    public String getSQLgetProyeccion(String ramo, String programa, String partida, int year) {
        String strSQL = "SELECT R.RAMO, PR.PRG,P.PARTIDA "
                + "FROM DGI.RAMOS R, DGI.PARTIDA P, S_POA_PROGRAMA PR "
                + "WHERE R.YEAR = " + year + " AND "
                + "      R.RAMO = '" + ramo + "' AND "
                + "      PR.PRG = '" + programa + "' AND "
                + "      PR.YEAR = R.YEAR AND "
                + "      P.YEAR = R.YEAR AND "
                + "      P.PARTIDA = '" + partida + "'";
        return strSQL;
    }

    public String getSQLInsertProyeccion(Proyeccion proyeccion, int year) {
        String strSQL = "INSERT ";
        proyeccion.setRamo(proyeccion.getRamo().trim());
        proyeccion.setPrograma(proyeccion.getPrograma().trim());
        proyeccion.setPartida(proyeccion.getPartida().trim());
        strSQL += "INTO POA.PROYECCION (RAMO,PRG,PARTIDA,PROYECTADO,REL_LABORAL, YEAR)\n "
                + "VALUES('" + proyeccion.getRamo() + "','" + proyeccion.getPrograma()
                + "','" + proyeccion.getPartida() + "'," + new BigDecimal(proyeccion.getProyectado()).setScale(2, RoundingMode.HALF_UP)
                + ",'" + proyeccion.getRelLaboral() + "'," + year + ") \n";

        return strSQL;
    }

    public String getSQLDeleteEstimacionMeta(int year, String ramo, int meta) {
        String strSQL = "DELETE "
                + "FROM DGI.ESTIMACION ES "
                + "WHERE ES.YEAR = " + year + " AND "
                + "      ES.RAMO = '" + ramo + "' AND "
                + "      ES.META = " + meta;
        return strSQL;
    }

    public String getSQLDeleteEstimacionAccion(int year, String ramo, int meta, int accion) {
        String strSQL = "DELETE FROM DGI.ACCION_ESTIMACION ES "
                + "WHERE ES.YEAR = " + year + " AND "
                + "      ES.RAMO = '" + ramo + "' AND "
                + "      ES.META = " + meta + " AND "
                + "      ES.ACCION =  " + accion;
        return strSQL;
    }

    public String getSQLGetObraMeta(int year, String ramo, int meta) {
        String strSQL = "SELECT NVL(M.OBRA,' ') AS OBRA FROM S_POA_META M "
                + "WHERE M.YEAR = " + year + " AND "
                + "      M.RAMO = '" + ramo + "' AND "
                + "      M.META = " + meta;
        return strSQL;
    }

    /*Cambios avance poa*/
    public String getSQLValidaPeriodoDefinido() {
        String strSQL = " SELECT NVL(PRG_PERIODO,0) AS PRG_PERIODO,NVL(CIERRE_PRG_AVA,'N') AS CIERRE_PRG_AVA "
                + " FROM DGI.RAMOS "
                + " WHERE RAMO = ? "
                + " AND YEAR = ? ";
        return strSQL;
    }

    public String getSQLValidaAccesoRolAvancePoa() {
        String strSQL = " SELECT ROL "
                + " FROM DGI.PARAMETRO_PRG "
                + " WHERE "
                + " ROL = ? ";
        return strSQL;
    }

    public String getSQLProgramacionAvancePoa() {
        String strSQL = " SELECT ESTIMACION.PERIODO AS AVANCE_PERIODO, "
                + " ESTIMACION.PERIODO AS ESTIMACION_PERIODO,   "
                + " NVL(AVANCE.VALOR,0) AS AVANCE_VALOR,   "
                + " NVL(ESTIMACION.VALOR,0) AS ESTIMACION_VALOR, "
                + " AVANCE.META AS AVANCE_META,   "
                + " AVANCE.RAMO AS AVANCE_RAMO, "
                + " AVANCE.YEAR AS AVANCE_YEAR, "
                + " ' ' AS CALCULO, "
                + "  ' ' AS ACTIVO, "
                + " PARAMETRO_PRG.PERIODO AS PARAMETRO_PRG_PERIODO,  "
                + " AVANCE.OBSERVA AS OBSERVA, "
                + " AVANCE.OBSERVA_ANUAL AS OBSERVA_ANUAL  "
                + " FROM dgi.AVANCE, "
                + " dgi.ESTIMACION, "
                + " dgi.PARAMETRO_PRG "
                + " WHERE ( avance.meta (+) = estimacion.meta) and  "
                + " ( estimacion.ramo = avance.ramo (+)) and  "
                + " ( avance.year (+) = estimacion.year) and  "
                + " ( avance.periodo (+) = estimacion.periodo) and  "
                + " ( ( DGI.ESTIMACION.RAMO = ? ) AND  "
                + " ( DGI.ESTIMACION.META = ? ) AND  "
                + "  ( DGI.ESTIMACION.YEAR = ? ) ) "
                + " ORDER BY ESTIMACION.PERIODO";

        return strSQL;
    }

    public String getSQLEvaluacionAvancePoa() {
        String strSQL = " SELECT NUMEVAL, "
                + " DESCREVAL "
                + " FROM DGI.EVALUACION "
                + " WHERE EVALUACION.YEAR = ? ";
        return strSQL;
    }

    public String getSQLEvaluacionEstimacionAvancePoa() {
        String strSQL = " SELECT EVALUACION "
                + " FROM DGI.ESTIMACION_PERIODO "
                + " WHERE PERIODO = ? ";
        return strSQL;
    }

    public String getSQLPeriodoParametroPrgAvancePoa() {
        String strSQL = " SELECT PERIODO "
                + " FROM DGI.PARAMETRO_PRG ";
        return strSQL;
    }

    public String getSQLMaxPeriodoEstimacionAvancePoa() {
        String strSQL = "  SELECT MAX(PERIODO) AS PERIODO "
                + "  FROM DGI.ESTIMACION_PERIODO "
                + "  WHERE EVALUACION = ? ";
        return strSQL;

    }

    public String getSQLIsParaestatalAvancePoa() {
        String strSQL = "  SELECT PARAESTATAL "
                + "  FROM DGI.PARAMETROS ";

        return strSQL;

    }

    public String getSQLExisteMetaAvancePoa() {

        String strSQL = " SELECT COUNT(*) AS EXISTE "
                + " FROM DGI.AVANCE A "
                + " WHERE ( A.META= ? ) AND "
                + " ( A.RAMO = ? ) AND "
                + " ( A.YEAR = ? ) AND "
                + " ( A.PERIODO = ? ) ";
        return strSQL;
    }

    public String getSQLInsertaMetaAvancePoa() {
        String strSQL = "  INSERT INTO DGI.AVANCE "
                + " (META,RAMO,YEAR,PERIODO,VALOR,OBSERVA,OBSERVA_ANUAL) "
                + " VALUES (?,?,?,?,?,?,?) ";

        return strSQL;
    }

    public String getSQLActualizaMetaAvancePoa() {
        String strSQL = "	UPDATE DGI.AVANCE A "
                + " SET VALOR = ?,"
                + " OBSERVA = ?,"
                + " OBSERVA_ANUAL = ? "
                + " WHERE ( A.META = ? ) AND "
                + "	( A.RAMO = ? ) AND  "
                + " ( A.YEAR = ? ) AND "
                + "	( A.PERIODO = ? ) ";
        return strSQL;
    }

    public String getSQLObtieneObservacionMetaAvancePoa() {
        String strSQL = " SELECT A.META AS META,"
                + " A.RAMO AS RAMO, "
                + " A.YEAR AS YEAR, "
                + " NVL(A.OBSERVA,' ') AS OBSERVA, "
                + " NVL(A.OBSERVA_ANUAL,' ') AS OBSERVA_ANUAL, "
                + " A.PERIODO  AS PERIODO "
                + " FROM DGI.AVANCE A "
                + " WHERE  "
                + " ( A.RAMO = ? ) and "
                + " ( A.META = ? ) and "
                + " ( A.PERIODO = ? ) and "
                + " ( A.YEAR = ? ) ";
        return strSQL;

    }

    public String getSQLValidaCierreAccionAvancePoa() {
        String strSQL = " SELECT NVL(CIERRE_ACCION,'N') AS CIERRE_ACCION "
                + " FROM   DGI.RAMOS "
                + " WHERE  RAMO = ? "
                + " AND YEAR = ? ";
        return strSQL;
    }

    public String getSQLobtieneListadoAccionesAvancePoa() {
        /*obtengo el listado de las acciones para la meta*/
        String strSQL = " SELECT A.META AS META, "
                + " A.RAMO AS RAMO, "
                + " A.YEAR AS YEAR, "
                + " A.ACCION AS ACCION, "
                + " A.DESCR AS DESCR, "
                + " A.DEPTO AS UE, "
                + " D.DESCR AS UE_DESCR, "
                + " SUM(NVL(AR.COSTO_ANUAL,0)) AS IMPORTE, "
                + " NVL(AC.EJERCIDO,0) AS EJERCIDO, "
                + " NVL(AC.ASIGNADO,0) AS ASIGNADO, "
                + " MM.DESCR AS UNIDAD_MEDIDA, "
                + " A.CALCULO  "
                + " FROM   POA.ACCION_REQ AR, "
                + " POA.ACCION A, "
                + " DGI.DEPENDENCIA D, "
                + " S_POA_MEDIDA_META MM, "
                + " (SELECT RAMO, "
                + "  META, "
                + "  ACCION, "
                + "  YEAR, "
                + " SUM(IMPT_EJER_XMES)AS EJERCIDO, "
                + " SUM(IMPT_ASIG_XMES)AS ASIGNADO  "
                + " FROM DGI.POA_AVANCE_ACCION "
                + " GROUP BY RAMO, "
                + "   META, "
                + "  ACCION, "
                + "   YEAR)AC "
                + " WHERE  A.META = AR.META(+)  "
                + " AND A.RAMO = AR.RAMO(+)  "
                + " AND A.YEAR = AR.YEAR(+) "
                + " AND A.ACCION = AR.ACCION(+) "
                + " AND A.RAMO = AC.RAMO(+) "
                + " AND A.META = AC.META(+) "
                + " AND A.ACCION = AC.ACCION(+) "
                + " AND A.YEAR = AC.YEAR(+) "
                + " AND D.RAMO = A.RAMO(+)  "
                + " AND D.DEPTO = A.DEPTO(+) "
                + " AND D.YEAR = A.YEAR(+)  "
                + " AND A.CVE_MEDIDA = MM.MEDIDA(+) "
                + " AND A.YEAR = MM.YEAR(+) "
                + " AND A.META = ? "
                + " AND A.RAMO = ? "
                + " AND A.YEAR = ? "
                + " AND A.PERIODO IS NOT NULL "
                + " GROUP BY A.META, A.RAMO, "
                + " A.YEAR, A.ACCION, "
                + " A.DESCR, A.DEPTO, "
                + " D.DESCR,MM.DESCR, "
                + " AC.EJERCIDO,AC.ASIGNADO,A.CALCULO "
                + " ORDER BY "
                + " A.ACCION";
        return strSQL;

    }

    public String getSQLAvanceAccionesAvancePoa() {
        /*obtengo el avance de la accion*/
        String strSQL = " SELECT AA.META AS META, "
                + " AA.RAMO AS RAMO, "
                + " AA.YEAR AS YEAR, "
                + " AA.ACCION AS ACCION, "
                + " AA.MES AS MES, "
                + " DECODE(AA.MES,1,'ENERO',2,'FEBRERO',3,'MARZO',4,'ABRIL',5,'MAYO',6,'JUNIO',7,'JULIO',8,'AGOSTO',9,'SEPTIEMBRE',10,'OCTUBRE',11,'NOVIEMBRE',12,'DICIEMBRE') AS DESCR_MES, "
                + " NVL(AA.VALOR,0) AS REALIZADO, "
                + " NVL(E.VALOR,0) AS PROGRAMADO, "
                + " AA.IMPT_ASIG_XMES AS IMPT_ASIG_XMES, "
                + " AA.IMPT_EJER_XMES AS IMPT_EJER_XMES, "
                + " NVL(AA.OBSERVACIONES,' ') AS OBSERVACIONES, "
                + " 0 AS PERIODO "
                + " FROM   DGI.POA_AVANCE_ACCION AA, "
                + " DGI.ACCION_ESTIMACION E "
                + " WHERE  AA.META = ? AND "
                + " AA.RAMO = ? AND "
                + " AA.YEAR = ? AND "
                + " AA.ACCION = ? AND "
                + " AA.RAMO=E.RAMO AND "
                + " AA.META= E.META AND "
                + " AA.YEAR= E.YEAR AND "
                + " AA.ACCION= E.ACCION AND "
                + " AA.MES= E.PERIODO "
                + " ORDER BY AA.MES ";
        return strSQL;

    }

    public String getSQLExisteAvanceAccionesAvancePoa() {
        String strSQL = " SELECT NVL(COUNT(1),'0') AS EXISTE "
                + " FROM   DGI.POA_AVANCE_ACCION "
                + " WHERE  "
                + " META = ? AND "
                + " RAMO = ? AND "
                + " YEAR = ? AND "
                + " ACCION = ? ";

        return strSQL;
    }

    public String getSQLUpdateAvanceAccionesAvancePoa() {

        String strSQL = " UPDATE DGI.POA_AVANCE_ACCION "
                + " SET VALOR=?,OBSERVACIONES=? "
                + " WHERE "
                + " RAMO=? AND "
                + " YEAR=? AND "
                + " MES=?  AND "
                + " META=? AND "
                + " ACCION=? ";

        return strSQL;
    }

    public String getSQLGetLineaSectorialByLineaMeta(String lineaPed, int year) {
        String strSQL = ""
                + "SELECT "
                + " LS.LINEA_SECTORIAL, "
                + " LS.DESCR "
                + "FROM "
                + " S_POA_LINEA_SECTORIAL LS "
                + "WHERE "
                + " LS.ESTRATEGIA = '" + lineaPed + "' AND "
                + " LS.YEAR = DGI.F_OBTIENE_YEAR_ADMVO(" + year + ") ";
        return strSQL;
    }

    public String existeCaratula() {
        String sQuery = "SELECT ID_CARATULA,status FROM POA.CARATULA C WHERE ID_CARATULA = ? ";
        return sQuery;
    }

    public String cargaCaratula() {
        String sQuery = "SELECT YEAR,RAMO,ID_CARATULA,FECHA_REVISION,NUMERO_SESION,TIPO_SESION,NUM_MOD_PRES, "
                + " NUM_MOD_PROG,SYS_CLAVE,APP_LOGIN,STATUS from poa.caratula WHERE YEAR =? AND RAMO =? "
                + " AND FECHA_REVISION = TO_DATE(?,'DD-MM-YYYY') AND NUMERO_SESION = ? AND TIPO_SESION = ? ";
        return sQuery;
    }

    public String getUpdateCaratula() {
        String sQuery = " UPDATE POA.CARATULA SET NUM_MOD_PRES = ?, NUM_MOD_PROG = ?, TIPO_SESION = ?, FECHA_REVISION = to_date(?,'DD/MM/YYYY'), NUMERO_SESION = ?, STATUS = ? , TIPO_MODIFICACION = ? WHERE YEAR = ?"
                + " AND RAMO = ? AND ID_CARATULA = ? ";
        return sQuery;
    }

    public String getInsertaCaratula() {
        String sQuery = "INSERT INTO POA.CARATULA (YEAR,RAMO,ID_CARATULA,FECHA_REVISION,NUMERO_SESION,"
                + "TIPO_SESION,NUM_MOD_PROG,NUM_MOD_PRES,SYS_CLAVE,APP_LOGIN,"
                + "FECHA_REGISTRO,STATUS,TIPO_MODIFICACION)"
                + "VALUES (?,?,?,to_date(?,'DD/MM/YYYY'),?,"
                + "?,?,?,?,?,"
                + "SYSDATE,?,?)";
        return sQuery;
    }

    public String getSQLgetSequenceMovimientoOficioCaratula(boolean isActual) {
        String strSQL = new String();
        strSQL = "SELECT LPAD(POA.SEQ_CARATULA.NEXTVAL,'6','0') AS SEQ FROM SYS.DUAL";
        return strSQL;
    }

    public String obtieneCaratulas(boolean bFiltraEstatusAbiertas, int tipoCaratula, boolean isNormativo) {
        String strEspaciosHtml = "chr(38)||'nbsp'||chr(38)||'nbsp'||chr(38)||'nbsp'||chr(38)||'nbsp'||chr(38)||'nbsp'||chr(38)||'nbsp'||chr(38)||'nbsp'||chr(38)||'nbsp'||chr(38)||'nbsp'";

        String sQuery = ""
                + "SELECT  "
                + "	CAR.YEAR, "
                + "	CAR.RAMO, "
                + "	CAR.ID_CARATULA, "
                + "	TO_CHAR(CAR.FECHA_REVISION,'DD/MM/YYYY'), "
                + "	CAR.NUMERO_SESION, "
                + "	CAR.TIPO_SESION, "
                + "	CAR.NUM_MOD_PRES, "
                + "	CAR.NUM_MOD_PROG, "
                + "	CAR.SYS_CLAVE, "
                + "	CAR.APP_LOGIN, "
                + "	CAR.STATUS, "
                + "	CAR.FECHA_REGISTRO, "
                + "	TIS.DESCR TIPOSESIONDESCR, "
                + "	REVS_NUMSES.DESCR_CORTA||' '||REVS_NUMSES.DESCR AS NUMSESIONDESCR,	"
                + "	NVL(REVS_NUMPRES.DESCR_CORTA, ' ')||' '||NVL(REVS_NUMPRES.DESCR, ' ') AS MODPRESUPDESCR, "
                + "	NVL(REVS_NUMPROG.DESCR_CORTA, ' ')||' '||NVL(REVS_NUMPROG.DESCR, ' ') AS MODPROGDESCR, ";
        //if (tipoCaratula == 1) {
        sQuery += "	REVS.DESCR_CORTA||' '||REVS.DESCR||' '||LOWER(TIS.DESCR)||" + strEspaciosHtml + " "
                + "||NVL(REVS_NUMPRES.DESCR_CORTA, 'No aplica')||' '||NVL(REVS_NUMPRES.DESCR, ' ')||' presupuestal '||" + strEspaciosHtml + " "
                + "||NVL(REVS_NUMPROG.DESCR_CORTA, 'No aplica')||' '||NVL(REVS_NUMPROG.DESCR, ' ')||' programática ' DESCR, "
                + "	CAR.TIPO_MODIFICACION, "
                + "	TIM.DESCR TIPOMODIFICACIONDESCR,  "
                + "	EXTRACT(YEAR FROM CAR.FECHA_REVISION) YEAR_SESION  ";
        /*} else {
         sQuery += "	NVL((SELECT REVS.DESCR_CORTA||' '||REVS.DESCR FROM POA.REVISIONES_CARATULA REVS WHERE REVS.REVISION = CAR.NUM_MOD_PRES),'-- No Aplica -- '||CAR.ID_CARATULA) DESCR ";
         }*/
        sQuery += "FROM "
                + "	POA.CARATULA CAR "
                + "INNER JOIN POA.TIPO_SESION TIS ON TIS.CVE_SESION = CAR.TIPO_SESION "
                + "INNER JOIN POA.REVISIONES_CARATULA REVS_NUMSES ON REVS_NUMSES.REVISION = CAR.NUMERO_SESION "
                + "LEFT OUTER JOIN POA.REVISIONES_CARATULA REVS_NUMPRES ON REVS_NUMPRES.REVISION = CAR.NUM_MOD_PRES "
                + "LEFT OUTER JOIN POA.REVISIONES_CARATULA REVS_NUMPROG ON REVS_NUMPROG.REVISION = CAR.NUM_MOD_PROG "
                + "INNER JOIN POA.REVISIONES_CARATULA REVS ON REVS.REVISION = CAR.NUMERO_SESION "
                + "INNER JOIN POA.TIPO_MODIFICACION TIM ON TIM.TIPO_MODIFICACION = CAR.TIPO_MODIFICACION "
                + "WHERE "
                + "	(CAR.YEAR = ? OR EXTRACT(YEAR FROM CAR.FECHA_REVISION) = ?)AND ";
        if (!isNormativo) {
            sQuery += "	CAR.RAMO = ? AND ";
        }
        if (bFiltraEstatusAbiertas) {
            sQuery += "	CAR.STATUS = 'A' AND ";
        }

        sQuery = sQuery.substring(0, sQuery.length() - 4);

        sQuery += "ORDER BY "
                + "	CAR.NUM_MOD_PRES*1 "
                + "	"
                + "";

        return sQuery;
    }

    public String getQueryBDCaratulaByYearIdRamoIdCaratula() {
        String sQuery = ""
                + "SELECT "
                + "	CAR.YEAR, "
                + "	CAR.RAMO, "
                + "	CAR.ID_CARATULA, "
                + "	CAR.FECHA_REVISION, "
                + "	CAR.NUMERO_SESION, "
                + "	CAR.TIPO_SESION, "
                + "	CAR.NUM_MOD_PRES, "
                + "	CAR.NUM_MOD_PROG, "
                + "	CAR.SYS_CLAVE, "
                + "	CAR.APP_LOGIN, "
                + "	CAR.STATUS, "
                + "	CAR.FECHA_REGISTRO, "
                + "	TIS.DESCR TIPOSESIONDESCR, "
                + "	(SELECT REVS.DESCR_CORTA||' '||REVS.DESCR FROM POA.REVISIONES_CARATULA REVS WHERE REVS.REVISION = CAR.NUMERO_SESION) NUMSESIONDESCR, "
                + "	(SELECT REVS.DESCR_CORTA||' '||REVS.DESCR FROM POA.REVISIONES_CARATULA REVS WHERE REVS.REVISION = CAR.NUM_MOD_PRES) MODPRESUPDESCR, "
                + "	(SELECT REVS.DESCR_CORTA||' '||REVS.DESCR FROM POA.REVISIONES_CARATULA REVS WHERE REVS.REVISION = CAR.NUM_MOD_PROG) MODPROGDESCR, "
                + "	(SELECT REVS.DESCR_CORTA||' '||REVS.DESCR FROM POA.REVISIONES_CARATULA REVS WHERE REVS.REVISION = CAR.NUMERO_SESION)||' '||LOWER(TIS.DESCR) DESCR, "
                + "	CAR.TIPO_MODIFICACION, "
                + "	TIM.DESCR TIPOMODIFICACIONDESCR,  "
                + "	EXTRACT(YEAR FROM CAR.FECHA_REVISION) YEAR_SESION  "
                + "FROM "
                + "	POA.CARATULA CAR, "
                + "	POA.TIPO_SESION TIS, "
                + "	POA.TIPO_MODIFICACION TIM "
                + "WHERE "
                + "	CAR.YEAR = ? AND "
                + "	CAR.RAMO = ? AND "
                + "	CAR.ID_CARATULA = ? AND "
                + "	TIS.CVE_SESION = CAR.TIPO_SESION AND "
                + "	TIM.TIPO_MODIFICACION = CAR.TIPO_MODIFICACION "
                + "";
        return sQuery;
    }

    public String insertaTraspaso() {
        String sQuery = " INSERT INTO POA.EXTRAER_OFICIOS (YEAR,RAMO,ID_EXTRACCION,OFICIO,TIPO_OFICIO,FECHA,USUARIO)"
                + " VALUES (?,?,?,?,?,SYSDATE,?) ";
        return sQuery;
    }

    public ArrayList<String> obtieneInformacionParaestatal() {
        String ls_sep = "||'|'||";
        ArrayList<String> arQuerys = new ArrayList();
        String sFiltro = " WHERE F.YEAR = ? AND F.RAMO = ? AND F.ID_EXTRACCION = ? AND T.OFICIO = F.OFICIO";
        String sFiltroCatsAmpTrans = " WHERE F.YEAR = ? AND F.RAMO = ? AND F.ID_EXTRACCION = ? AND T.OFICIO = F.OFICIO AND "
                + " OO.OFICIO = T.OFICIO AND OO.CONSEC = T.CONSEC and OT.OFICIO = T.OFICIO "
                + " AND OT.TIPO = OO.TIPO AND OT.STATUS = 'A' ";
        // <editor-fold defaultstate="collapsed" desc="AMPLIACIONES">
        arQuerys.add("SELECT T.OFICIO" + ls_sep + "T.CONSEC" + ls_sep + "T.RAMO" + ls_sep
                + "T.DEPTO" + ls_sep + "T.FINALIDAD" + ls_sep + "T.FUNCION" + ls_sep
                + "T.SUBFUNCION" + ls_sep + "T.PRG_CONAC" + ls_sep + "T.PRG" + ls_sep
                + "T.TIPO_PROY" + ls_sep + "T.PROYECTO" + ls_sep
                + "T.META" + ls_sep + "T.ACCION" + ls_sep + "T.PARTIDA" + ls_sep + "T.TIPO_GASTO" + ls_sep
                + "T.FUENTE" + ls_sep + "T.FONDO" + ls_sep + "T.RECURSO" + ls_sep + "T.MUNICIPIO" + ls_sep
                + "T.DELEGACION" + ls_sep + "T.REL_LABORAL" + ls_sep + "T.IMPTE" + ls_sep + "T.STATUS" + ls_sep
                + "T.TIPOMOV FROM  POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT " + sFiltroCatsAmpTrans);
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="AMP CAL">
        arQuerys.add("SELECT T.OFICIO" + ls_sep + "T.CONSEC" + ls_sep + "T.MES" + ls_sep
                + "T.IMPTE FROM POA.EXTRAER_OFICIOS F,DGI.AMPCAL T" + sFiltro);
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="TRANSFERENCIAS">        
        arQuerys.add("SELECT T.OFICIO" + ls_sep + "T.CONSEC" + ls_sep + "T.RAMO" + ls_sep + "T.DEPTO" + ls_sep + "T.FINALIDAD"
                + ls_sep + "T.FUNCION" + ls_sep + "T.SUBFUNCION" + ls_sep + "T.PRG_CONAC" + ls_sep + "T.PRG" + ls_sep + "T.TIPO_PROY"
                + ls_sep + "T.PROYECTO" + ls_sep + "T.META" + ls_sep + "T.ACCION" + ls_sep + "T.PARTIDA" + ls_sep + "T.TIPO_GASTO" + ls_sep
                + "T.FUENTE" + ls_sep + "T.FONDO" + ls_sep + "T.RECURSO" + ls_sep + "T.MUNICIPIO" + ls_sep + "T.DELEGACION" + ls_sep
                + "T.REL_LABORAL" + ls_sep + "T.IMPTE" + ls_sep + "T.STATUS FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT " + sFiltroCatsAmpTrans);
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="TRANSFREC">                 
        arQuerys.add("SELECT T.OFICIO" + ls_sep + "T.CONSEC" + ls_sep + "T.RAMO" + ls_sep + "T.DEPTO" + ls_sep + "T.FINALIDAD" + ls_sep
                + "T.FUNCION" + ls_sep + "T.SUBFUNCION" + ls_sep + "T.PRG_CONAC" + ls_sep + "T.PRG" + ls_sep + "T.TIPO_PROY" + ls_sep
                + "T.PROYECTO" + ls_sep + "T.META" + ls_sep + "T.ACCION" + ls_sep + "T.PARTIDA" + ls_sep + "T.TIPO_GASTO" + ls_sep
                + "T.FUENTE" + ls_sep + "T.FONDO" + ls_sep + "T.RECURSO" + ls_sep + "T.MUNICIPIO" + ls_sep + "T.DELEGACION" + ls_sep
                + "T.REL_LABORAL" + ls_sep + "T.IMPTE" + ls_sep + "T.TIPOMOV" + ls_sep + "T.REQUERIMIENTO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT " + sFiltroCatsAmpTrans);
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="MOVOFICIOS_ACCION">        

        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.RAMO" + ls_sep + "T.PRG" + ls_sep + "T.DEPTO" + ls_sep
                + "T.META" + ls_sep + "T.ACCION" + ls_sep + "T.DESCR" + ls_sep + "T.MEDIDA" + ls_sep + "T.CVE_MEDIDA" + ls_sep
                + "T.CALCULO" + ls_sep + "T.TIPO_GASTO" + ls_sep + "T.LINEA" + ls_sep + "T.GRUPO_POBLACION" + ls_sep + "T.BENEF_HOMBRE"
                + ls_sep + "T.BENEF_MUJER" + ls_sep + "T.MPO" + ls_sep + "T.LOCALIDAD" + ls_sep + "T.TIPO_ACCION" + ls_sep
                + "T.LINEA_SECTORIAL" + ls_sep + "T.OBRA FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T"
                + sFiltro);
        //</editor-fold>
        /* //MOVOFICIOS_ACCION_ESTIMACION
         arQuerys.add("SELECT T.OFICIO" + ls_sep + "T.META" + ls_sep + "T.RAMO" + ls_sep + "T.YEAR" + ls_sep + "T.ACCION" + ls_sep + 
         "T.PERIODO" + ls_sep + "T.VALOR" + ls_sep + "T.NVA_CREACION" + ls_sep + "T.IND_TRANSF FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_ESTIMACION T"+
         sFiltro);*/
        // <editor-fold defaultstate="collapsed" desc="TRANSFRECCAL">        

        arQuerys.add("SELECT T.OFICIO" + ls_sep + "T.CONSEC" + ls_sep + "T.RAMO" + ls_sep + "T.DEPTO" + ls_sep + "T.FINALIDAD"
                + ls_sep + "T.FUNCION" + ls_sep + "T.SUBFUNCION" + ls_sep + "T.PRG_CONAC" + ls_sep + "T.PRG" + ls_sep
                + "T.TIPO_PROY" + ls_sep + "T.PROYECTO" + ls_sep + "T.META" + ls_sep + "T.ACCION" + ls_sep + "T.PARTIDA"
                + ls_sep + "T.TIPO_GASTO" + ls_sep + "T.FUENTE" + ls_sep + "T.FONDO" + ls_sep + "T.RECURSO" + ls_sep
                + "T.MUNICIPIO" + ls_sep + "T.DELEGACION" + ls_sep + "T.REL_LABORAL" + ls_sep + "T.REQUERIMIENTO" + ls_sep
                + "MES" + ls_sep + "IMPTE FROM POA.EXTRAER_OFICIOS F, SPP.TRANSFRECCAL T" + sFiltro);
        //"MES" + ls_sep + "IMPTE FROM POA.EXTRAER_OFICIOS F,POA.TRANSFRECCAL T"+sFiltro);
        /*arQuerys.add("SELECT  FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T"
         + sFiltro);*/
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="TIPOFICIO">       +

        arQuerys.add("SELECT DISTINCT OT.OFICIO" + ls_sep + "OT.TIPO" + ls_sep + "OT.STATUS" + ls_sep + "OT.OBS_RECHAZO" + ls_sep
                + "OT.ID_TRAMITE FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS MO,DGI.TIPOFICIO OT,DGI.OFICONS OO "
                + " WHERE F.YEAR = ? AND F.RAMO = ? AND F.ID_EXTRACCION = ? AND "
                + " MO.OFICIO = F.OFICIO AND OT.OFICIO = MO.OFICIO AND " //OT.TIPO = MO.TIPOMOV AND " rharo
                + " OO.OFICIO = F.OFICIO AND OO.TIPO = OT.TIPO AND OT.STATUS = 'A'");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="OFICONS">       +        
        arQuerys.add("SELECT DISTINCT OO.OFICIO" + ls_sep + "OO.TIPO" + ls_sep + "OO.CONSEC FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS MO,DGI.TIPOFICIO OT,DGI.OFICONS OO "
                + " WHERE F.YEAR = ? AND F.RAMO = ? AND F.ID_EXTRACCION = ? AND "
                + " MO.OFICIO = F.OFICIO AND OT.OFICIO = MO.OFICIO AND " //OT.TIPO = MO.TIPOMOV AND "rharo
                + " OO.OFICIO = F.OFICIO AND OO.TIPO = OT.TIPO AND OT.STATUS = 'A'");
        //</editor-fold>
        /*//MOVOFICIOS_ESTIMACION
         arQuerys.add("SELECT T.OFICIO" + ls_sep + "T.META" + ls_sep + "T.RAMO" + ls_sep + "T.YEAR" + ls_sep + "T.PERIODO" + ls_sep + "T.VALOR,T.NVA_CREACION FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ESTIMACION T"+ sFiltro);*/
        // <editor-fold defaultstate="collapsed" desc="MOVICIOS_META">        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.RAMO" + ls_sep + "T.PRG" + ls_sep + "T.META" + ls_sep
                + "T.DESCR" + ls_sep + "T.MEDIDA" + ls_sep + "T.CALCULO" + ls_sep + "T.TIPO" + ls_sep + "T.CVE_MEDIDA" + ls_sep + "T.FINALIDAD"
                + ls_sep + "T.FUNCION" + ls_sep + "T.SUBFUNCION" + ls_sep + "T.TIPO_PROY" + ls_sep + "T.PROY" + ls_sep + "T.DEPTO" + ls_sep
                + "T.CLAVE" + ls_sep + "T.LINEA" + ls_sep + "T.TIPO_COMPROMISO" + ls_sep + "T.BENEFICIADOS" + ls_sep + "T.PRESUPUESTAR" + ls_sep
                + "T.APROB_CONGRESO" + ls_sep + "T.CONVENIO" + ls_sep + "T.CONV" + ls_sep + "T.BENEF_HOMBRE" + ls_sep + "T.BENEF_MUJER"
                + ls_sep + "T.GENERO" + ls_sep + "T.PRINCIPAL" + ls_sep + "T.RELATORIA" + ls_sep + "T.OBRA" + ls_sep + "T.DESCR_CORTA" + ls_sep
                + "T.MAYOR_COSTO" + ls_sep + "T.FICHA_TECNICA" + ls_sep + "T.CRITERIO" + ls_sep + "T.OBJ" + ls_sep + "T.OBJ_META" + ls_sep
                + "T.PONDERADO" + ls_sep + "T.LINEA_SECTORIAL" + ls_sep + "T.PROCESO_AUTORIZACION FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T"
                + sFiltro);
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="MOVOFICIOS">                
        arQuerys.add("SELECT T.OFICIO,to_char(T.FECHAELAB,'dd/MM/yyyy'),T.APP_LOGIN,T.TIPOMOV,T.STATUS,to_char(T.FECPPTO,'dd/MM/yyyy'),T.JUSTIFICACION,T.OBS_RECHAZO,T.CAPTURA_ESPECIAL "
                + " FROM "
                + " POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS T"
                + sFiltro);
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="BITMOVTOS">                        
        arQuerys.add("SELECT T.OFICIO" + ls_sep + "T.APP_LOGIN" + ls_sep + "to_char(T.FECHAAUT,'dd/MM/yyyy')" + ls_sep + "T.AUTORIZO" + ls_sep
                + " T.IMP_FIRMA" + ls_sep + "T.TIPO_OFICIO" + ls_sep + "T.TERMINAL" + ls_sep + "T.SYS_CLAVE" + ls_sep
                + " T.TIPO_FLUJO" + ls_sep + "T.TIPO_USR FROM (select max(T.fechaaut) AS FECHAAUT,T.OFICIO,T.TIPO_OFICIO FROM POA.EXTRAER_OFICIOS F, POA.BITMOVTOS T " + sFiltro
                + " AND T.AUTORIZO = 'A' GROUP BY T.OFICIO,T.TIPO_OFICIO ) INFO, POA.BITMOVTOS T WHERE T.OFICIO = INFO.OFICIO AND T.TIPO_OFICIO = INFO.TIPO_OFICIO AND T.FECHAAUT = INFO.FECHAAUT AND "
                + " T.AUTORIZO = 'A'");
        //</editor-fold>
        //CATALOGOS
        // <editor-fold defaultstate="collapsed" desc="RAMOS">                        
        arQuerys.add("SELECT DISTINCT  T.RAMO" + ls_sep + "T.DESCR" + ls_sep + "T.TRANSITORIO" + ls_sep + "T.YEAR FROM "
                + " (SELECT F.YEAR,T.RAMO FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,DGI.RAMOS D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO "
                + " UNION  "
                + "SELECT F.YEAR,T.RAMO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,DGI.RAMOS D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO "
                + " UNION  "
                + " SELECT F.YEAR,T.RAMO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,DGI.RAMOS D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO "
                + " UNION  "
                + " SELECT F.YEAR,T.RAMO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,DGI.RAMOS D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO "
                + " UNION "
                + " SELECT F.YEAR,T.RAMO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,DGI.RAMOS D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO "
                + " UNION  "
                + " SELECT F.YEAR,T.RAMO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,DGI.RAMOS D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO )"
                + " INFO,DGI.RAMOS T WHERE T.YEAR = INFO.YEAR AND T.RAMO = INFO.RAMO ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PROGRAMA">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.PRG" + ls_sep + "T.DESCR" + ls_sep + "T.PRG_CONAC FROM "
                + " (SELECT F.YEAR,T.PRG FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_PROGRAMA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.PRG = T.PRG "
                + " UNION "
                + "SELECT F.YEAR,T.PRG FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_PROGRAMA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.PRG = T.PRG "
                + " UNION "
                + " SELECT F.YEAR,T.PRG FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_PROGRAMA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.PRG = T.PRG "
                + " UNION "
                + " SELECT F.YEAR,T.PRG FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,S_POA_PROGRAMA D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.PRG = T.PRG "
                + " UNION "
                + " SELECT F.YEAR,T.PRG FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,S_POA_PROGRAMA D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.PRG = T.PRG "
                + " UNION "
                + " SELECT F.YEAR,T.PRG FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,S_POA_PROGRAMA D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.PRG = T.PRG )"
                + " INFO,S_POA_PROGRAMA T WHERE T.YEAR = INFO.YEAR AND T.PRG = INFO.PRG ");
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="DEPARTAMENTO">                        
        arQuerys.add("SELECT DISTINCT T.RAMO" + ls_sep + "T.DEPTO" + ls_sep + "T.DESCR" + ls_sep + "T.MPO" + ls_sep + "T.YEAR FROM "
                + " (SELECT F.YEAR,T.RAMO,T.DEPTO FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,DGI.DEPENDENCIA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.DEPTO = T.DEPTO"
                + " UNION "
                + "SELECT F.YEAR,T.RAMO,T.DEPTO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,DGI.DEPENDENCIA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.DEPTO = T.DEPTO"
                + " UNION "
                + " SELECT F.YEAR,T.RAMO,T.DEPTO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,DGI.DEPENDENCIA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.DEPTO = T.DEPTO"
                + " UNION "
                + " SELECT F.YEAR,T.RAMO,T.DEPTO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,DGI.DEPENDENCIA D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.DEPTO = T.DEPTO"
                + " UNION "
                //AQUI NO LLEVA MOVOFICIOS_META
                + " SELECT F.YEAR,T.RAMO,T.DEPTO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,DGI.DEPENDENCIA D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.DEPTO = T.DEPTO )"
                + " INFO,DGI.DEPENDENCIA T WHERE T.YEAR = INFO.YEAR AND T.RAMO = INFO.RAMO AND T.DEPTO = INFO.DEPTO ");
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="FINALIDAD">                        
        arQuerys.add("SELECT DISTINCT T.FINALIDAD" + ls_sep + "T.DESCR" + ls_sep + "T.YEAR FROM "
                + " (SELECT F.YEAR,T.FINALIDAD FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FINALIDAD D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD "
                + " UNION "
                + "SELECT F.YEAR,T.FINALIDAD FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FINALIDAD D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD "
                + " UNION "
                + " SELECT F.YEAR,T.FINALIDAD FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FINALIDAD D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD "
                + " UNION "
                + " SELECT F.YEAR,T.FINALIDAD FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,S_POA_FINALIDAD D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD "
                /*+ " UNION "
                 + " SELECT F.YEAR,T.FINALIDAD FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,SPP.FINALIDAD D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD "*/
                + ")"
                + " INFO,S_POA_FINALIDAD T WHERE T.YEAR = INFO.YEAR AND T.FINALIDAD = INFO.FINALIDAD ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="FUNCION">                        
        arQuerys.add("SELECT DISTINCT T.FINALIDAD" + ls_sep + "T.FUNCION" + ls_sep + "T.DESCR" + ls_sep + "T.YEAR FROM "
                + " (SELECT F.YEAR,T.FINALIDAD,T.FUNCION FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FUNCION D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD  AND D.FUNCION = T.FUNCION"
                + " UNION "
                + "SELECT F.YEAR,T.FINALIDAD,T.FUNCION FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FUNCION D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD  AND D.FUNCION = T.FUNCION"
                + " UNION "
                + " SELECT F.YEAR,T.FINALIDAD,T.FUNCION FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FUNCION D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD  AND D.FUNCION = T.FUNCION"
                + " UNION "
                + " SELECT F.YEAR,T.FINALIDAD,T.FUNCION FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,S_POA_FUNCION D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD  AND D.FUNCION = T.FUNCION"
                /*+ " UNION "
                 + " SELECT F.YEAR,T.FINALIDAD,T.FUNCION FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,SPP.FUNCION D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD  AND D.FUNCION = T.FUNCION */
                + ")"
                + " INFO,S_POA_FUNCION T WHERE T.YEAR = INFO.YEAR AND T.FINALIDAD = INFO.FINALIDAD AND T.FUNCION = INFO.FUNCION ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="SUBFUNCION">                        
        arQuerys.add("SELECT DISTINCT T.FINALIDAD" + ls_sep + "T.FUNCION" + ls_sep + "T.SUBFUNCION" + ls_sep + "T.DESCR" + ls_sep + "T.YEAR"
                + " FROM "
                + " (SELECT F.YEAR,T.FINALIDAD,T.FUNCION,T.SUBFUNCION FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_SUBFUNCION D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD AND D.FUNCION = T.FUNCION AND D.SUBFUNCION = T.SUBFUNCION "
                + " UNION "
                + "SELECT F.YEAR,T.FINALIDAD,T.FUNCION,T.SUBFUNCION FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_SUBFUNCION D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD AND D.FUNCION = T.FUNCION AND D.SUBFUNCION = T.SUBFUNCION "
                + " UNION "
                + " SELECT F.YEAR,T.FINALIDAD,T.FUNCION,T.SUBFUNCION FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_SUBFUNCION D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD AND D.FUNCION = T.FUNCION AND D.SUBFUNCION = T.SUBFUNCION "
                + " UNION "
                + " SELECT F.YEAR,T.FINALIDAD,T.FUNCION,T.SUBFUNCION FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,S_POA_SUBFUNCION D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD AND D.FUNCION = T.FUNCION AND D.SUBFUNCION = T.SUBFUNCION "
                + /*+ " UNION "
                 + " SELECT F.YEAR,T.FINALIDAD,T.FUNCION,T.SUBFUNCION FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,SPP.SUBFUNCION D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.FINALIDAD = T.FINALIDAD AND D.FUNCION = T.FUNCION AND D.SUBFUNCION = T.SUBFUNCION"*/ " )"
                + " INFO,S_POA_SUBFUNCION T WHERE T.YEAR = INFO.YEAR AND T.FINALIDAD = INFO.FINALIDAD AND T.FUNCION = INFO.FUNCION AND T.SUBFUNCION = INFO.SUBFUNCION ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="MEDIDA">                        
        arQuerys.add("SELECT DISTINCT T.MEDIDA" + ls_sep + "T.DESCR" + ls_sep + "T.ACTIVO" + ls_sep + "T.YEAR" + ls_sep + "T.TEMP" + ls_sep + "T.ACCION" + ls_sep + "T.META "
                + " FROM "
                + " (SELECT F.YEAR,T.CVE_MEDIDA FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,S_POA_MEDIDA_META D " + sFiltro
                + "  AND D.YEAR = F.YEAR  AND D.MEDIDA = T.CVE_MEDIDA "
                + " UNION "
                + " SELECT F.YEAR,T.CVE_MEDIDA FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,S_POA_MEDIDA_META D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.MEDIDA = T.CVE_MEDIDA )"
                + " INFO,S_POA_MEDIDA_META T WHERE T.YEAR = INFO.YEAR AND T.MEDIDA = INFO.CVE_MEDIDA ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PROGRAMA CONAC">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.PRG_CONAC" + ls_sep + "T.DESCR" + ls_sep + "T.PRG_FED FROM "
                + " (SELECT F.YEAR,T.PRG_CONAC FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_PROGRAMA_CONAC D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.PRG_CONAC = T.PRG_CONAC "
                + " UNION "
                + "SELECT F.YEAR,T.PRG_CONAC FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_PROGRAMA_CONAC D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.PRG_CONAC = T.PRG_CONAC "
                + " UNION "
                + " SELECT F.YEAR,T.PRG_CONAC FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_PROGRAMA_CONAC D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.PRG_CONAC = T.PRG_CONAC "
                /*+ " UNION "
                 + " SELECT F.YEAR,T.PRG_CONAC FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,POA.PROGRAMA_CONAC D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.PRG_CONAC = T.PRG_CONAC "
                 + " UNION "
                 + " SELECT F.YEAR,T.PRG_CONAC FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,POA.PROGRAMA_CONAC D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.PRG_CONAC = T.PRG_CONAC "*/
                + ")"
                + " INFO,S_POA_PROGRAMA_CONAC T WHERE T.YEAR = INFO.YEAR AND T.PRG_CONAC= INFO.PRG_CONAC ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PROYECTO">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.RAMO" + ls_sep + "T.PRG" + ls_sep + "T.TIPO_PROY" + ls_sep + "T.PROY" + ls_sep
                + "T.DEPTO" + ls_sep + "T.DEPTO_RESP" + ls_sep + "T.RFC" + ls_sep + "T.HOMOCLAVE" + ls_sep + "T.NOINCLUIR FROM "
                + " (SELECT F.YEAR,T.RAMO,T.PRG,T.TIPO_PROY,T.PROYECTO FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,POA.PROYECTO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG AND D.TIPO_PROY = T.TIPO_PROY AND D.PROY = T.PROYECTO "
                + " UNION "
                + "SELECT F.YEAR,T.RAMO,T.PRG,T.TIPO_PROY,T.PROYECTO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,POA.PROYECTO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG AND D.TIPO_PROY = T.TIPO_PROY AND D.PROY= T.PROYECTO "
                + " UNION "
                + " SELECT F.YEAR,T.RAMO,T.PRG,T.TIPO_PROY,T.PROYECTO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,POA.PROYECTO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG AND D.TIPO_PROY = T.TIPO_PROY AND D.PROY = T.PROYECTO "
                + " UNION "
                + " SELECT F.YEAR,T.RAMO,T.PRG,T.TIPO_PROY,T.PROY AS PROYECTO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,POA.PROYECTO D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG AND D.TIPO_PROY = T.TIPO_PROY AND D.PROY = T.PROY )"
                /*+ " UNION "
                 + " SELECT F.YEAR,T.RAMO,T.PRG,T.TIPO_PROY,T.PROYECTO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,POA.PROYECTO D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG AND D.TIPO_PROY = T.TIPO_PROY AND D.PROYECTO = T.PROYECTO )"*/
                + " INFO,POA.PROYECTO  T WHERE T.YEAR = INFO.YEAR AND T.RAMO = INFO.RAMO AND T.PRG = INFO.PRG AND T.TIPO_PROY = INFO.TIPO_PROY AND T.PROY = INFO.PROYECTO ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="partida">                                   
        arQuerys.add(" SELECT DISTINCT T.YEAR" + ls_sep + "T.PARTIDA" + ls_sep + "T.DESCR" + ls_sep + "T.SUBSUBGPO" + ls_sep
                + "T.FONDOPER" + ls_sep + "T.INTRANSFERIBLE" + ls_sep + "T.AMPAUTOM" + ls_sep + "T.ORDEN" + ls_sep + "T.PLANTILLA" + ls_sep
                + "T.SERVICIOS" + ls_sep + "T.TECHO" + ls_sep + "T.FACTURA_DETALLE" + ls_sep + "T.POA" + ls_sep + "T.COMP_EJER" + ls_sep
                + "T.JUSTIF_REQ" + ls_sep + "T.ACTIVO" + ls_sep + "T.RELACION_LABORAL" + ls_sep + "T.CTA_APROBADO" + ls_sep
                + "T.CTA_MODIFICADO" + ls_sep + "T.CTA_COMPROMETIDO" + ls_sep + "T.CTA_DEVENGADO" + ls_sep + "T.CTA_EJERCIDO" + ls_sep
                + "T.CTA_PAGADO" + ls_sep + "T.CTA_PPTOXEJER FROM "
                + " (SELECT F.YEAR,T.PARTIDA FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,DGI.PARTIDA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.PARTIDA = T.PARTIDA "
                + " UNION "
                + "SELECT F.YEAR,T.PARTIDA FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,DGI.PARTIDA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.PARTIDA = T.PARTIDA "
                + " UNION "
                + " SELECT F.YEAR,T.PARTIDA FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,DGI.PARTIDA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.PARTIDA = T.PARTIDA "
                + " UNION "
                /*+ " SELECT F.YEAR,T.PARTIDA FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,DGI.PARTIDA D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.PARTIDA = T.PARTIDA "
                 + " UNION "*/
                + " SELECT F.YEAR,T.PARTIDA FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,DGI.PARTIDA D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.PARTIDA = T.PARTIDA )"
                + " INFO,DGI.PARTIDA T WHERE T.YEAR = INFO.YEAR AND T.PARTIDA = INFO.PARTIDA ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="TIPO GASTO">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.TIPO_GASTO" + ls_sep + "T.DESCR FROM "
                + " (SELECT F.YEAR,D.TIPO_GASTO FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_TIPO_GASTO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.TIPO_GASTO = T.TIPO_GASTO "
                + " UNION "
                + "SELECT F.YEAR,D.TIPO_GASTO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_TIPO_GASTO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.TIPO_GASTO = T.TIPO_GASTO "
                + " UNION "
                + " SELECT F.YEAR,D.TIPO_GASTO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_TIPO_GASTO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.TIPO_GASTO = T.TIPO_GASTO "
                + " UNION "
                + " SELECT F.YEAR,D.TIPO_GASTO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,S_POA_TIPO_GASTO D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.TIPO_GASTO = T.TIPO_GASTO "
                + " UNION "
                + " SELECT F.YEAR,D.TIPO_GASTO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,S_POA_TIPO_GASTO D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.TIPO_GASTO = T.TIPO_GASTO )"
                + " INFO,S_POA_TIPO_GASTO T WHERE T.YEAR = INFO.YEAR AND T.TIPO_GASTO = INFO.TIPO_GASTO ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="FUENTE">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.FUENTE" + ls_sep + "T.DESCR FROM "
                + " (SELECT F.YEAR,T.FUENTE FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FUENTE D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE "
                + " UNION "
                + "SELECT F.YEAR,T.FUENTE FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FUENTE D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE "
                + " UNION "
                + " SELECT F.YEAR,T.FUENTE FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FUENTE D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE "
                + " UNION "
                + " SELECT F.YEAR,T.FUENTE FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,S_POA_FUENTE D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE "
                /* + " UNION "
                 + " SELECT F.YEAR,T.FUENTE FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,POA.FUENTE D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE "*/
                + " )"
                + " INFO,S_POA_FUENTE T WHERE T.YEAR = INFO.YEAR AND T.FUENTE = INFO.FUENTE ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="FONDO">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.FUENTE" + ls_sep + "T.FONDO" + ls_sep + "T.DESCR FROM "
                + " (SELECT F.YEAR,T.FUENTE,T.FONDO FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT, S_POA_FONDO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO"
                + " UNION "
                + "SELECT F.YEAR,T.FUENTE,T.FONDO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FONDO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO"
                + " UNION "
                + " SELECT F.YEAR,T.FUENTE,T.FONDO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_FONDO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO"
                /*+ " UNION "
                 + " SELECT F.YEAR,T.FUENTE,T.FONDO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,POA.FONDO D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO"*/
                + " UNION "
                + " SELECT F.YEAR,T.FUENTE,T.FONDO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,S_POA_FONDO D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO )"
                + " INFO,S_POA_FONDO T WHERE T.YEAR = INFO.YEAR AND T.FUENTE = INFO.FUENTE AND T.FONDO = INFO.FONDO ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="RECURSO">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.FUENTE" + ls_sep + "T.FONDO" + ls_sep + "T.RECURSO" + ls_sep
                + "T.DESCR" + ls_sep + "T.PLANTILLA" + ls_sep + "T.VISIBLE FROM "
                + " (SELECT F.YEAR,T.FUENTE,T.FONDO,T.RECURSO FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_RECURSO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO AND D.RECURSO = T.RECURSO "
                + " UNION "
                + "SELECT F.YEAR,T.FUENTE,T.FONDO,T.RECURSO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_RECURSO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO AND D.RECURSO = T.RECURSO "
                + " UNION "
                + " SELECT F.YEAR,T.FUENTE,T.FONDO,T.RECURSO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_RECURSO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO AND D.RECURSO = T.RECURSO "
                /*+ " UNION "
                 + " SELECT F.YEAR,T.FUENTE,T.FONDO,T.RECURSO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,POA.RECURSO D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO AND D.RECURSO = T.RECURSO "*/
                + " UNION "
                + " SELECT F.YEAR,T.FUENTE,T.FONDO,T.RECURSO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,S_POA_RECURSO D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.FUENTE = T.FUENTE AND D.FONDO = T.FONDO AND D.RECURSO = T.RECURSO  )"
                + " INFO,S_POA_RECURSO T WHERE T.YEAR = INFO.YEAR AND T.FUENTE = INFO.FUENTE AND T.FONDO = INFO.FONDO AND T.RECURSO = INFO.RECURSO ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="MUNICIPIO">                        

        arQuerys.add("SELECT DISTINCT T.MPO" + ls_sep + "T.NOMBREMPO" + ls_sep + "T.REAL" + ls_sep + "T.SIGLA FROM "
                + " (SELECT T.MUNICIPIO FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT, S_POA_MUNICIPIO D " + sFiltroCatsAmpTrans
                + "  AND D.MPO = T.MUNICIPIO "
                + " UNION "
                + "SELECT T.MUNICIPIO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_MUNICIPIO D " + sFiltroCatsAmpTrans
                + "  AND D.MPO = T.MUNICIPIO "
                + " UNION "
                + " SELECT T.MUNICIPIO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_MUNICIPIO D " + sFiltroCatsAmpTrans
                + "  AND D.MPO = T.MUNICIPIO "
                /*+ " UNION "
                 + " SELECT T.MUNICIPIO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,DGI.MUNICIPIO D " + sFiltro
                 + "  AND D.MPO = T.MUNICIPIO "
                 + " UNION "
                 + " SELECT T.MUNICIPIO FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,DGI.MUNICIPIO D " + sFiltro
                 + "  AND D.MPO = T.MUNICIPIO "*/
                + " )"
                + " INFO,S_POA_MUNICIPIO T WHERE T.MPO = INFO.MUNICIPIO ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="LOCALIDAD">                        
        arQuerys.add("SELECT DISTINCT T.MPO" + ls_sep + "T.LOCALIDAD" + ls_sep + "T.DESCR FROM "
                + " (SELECT T.MUNICIPIO,T.DELEGACION FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT, S_POA_LOCALIDAD D " + sFiltroCatsAmpTrans
                + "  AND D.MPO = T.MUNICIPIO AND D.LOCALIDAD = T.DELEGACION "
                + " UNION "
                + "SELECT T.MUNICIPIO,T.DELEGACION FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_LOCALIDAD D " + sFiltroCatsAmpTrans
                + "  AND D.MPO = T.MUNICIPIO AND D.LOCALIDAD = T.DELEGACION "
                + " UNION "
                + " SELECT T.MUNICIPIO,T.DELEGACION FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_LOCALIDAD D " + sFiltroCatsAmpTrans
                + "  AND D.MPO = T.MUNICIPIO AND D.LOCALIDAD = T.DELEGACION "
                /*+ " UNION "
                 + " SELECT T.MUNICIPIO,T.DELEGACION FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,DGI.LOCALIDAD D " + sFiltro
                 + "  AND D.MPO = T.MUNICIPIO AND D.LOCALIDAD = T.DELEGACION "
                 + " UNION "
                 + " SELECT T.MUNICIPIO,T.DELEGACION FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,DGI.LOCALIDAD D " + sFiltro
                 + "  AND D.MPO = T.MUNICIPIO AND D.LOCALIDAD = T.DELEGACION"*/
                + ")"
                + " INFO,S_POA_LOCALIDAD T WHERE T.MPO = INFO.MUNICIPIO AND T.LOCALIDAD = INFO.DELEGACION ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="RELACION LABORAL">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.REL_LABORAL" + ls_sep + "T.DESCR FROM "
                + " (SELECT F.YEAR,T.REL_LABORAL FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT, S_POA_REL_LABORAL D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.REL_LABORAL = T.REL_LABORAL "
                + " UNION "
                + "SELECT F.YEAR,T.REL_LABORAL FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_REL_LABORAL D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.REL_LABORAL = T.REL_LABORAL "
                + " UNION "
                + " SELECT F.YEAR,T.REL_LABORAL FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_REL_LABORAL D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.REL_LABORAL = T.REL_LABORAL "
                /* + " UNION "
                 + " SELECT F.YEAR,T.REL_LABORAL FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION T,POA.REL_LABORAL D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.REL_LABORAL = T.REL_LABORAL "
                 + " UNION "
                 + " SELECT F.YEAR,T.REL_LABORAL FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,POA.REL_LABORAL D " + sFiltro
                 + "  AND D.YEAR = F.YEAR AND D.REL_LABORAL = T.REL_LABORAL "*/
                + " )"
                + " INFO,S_POA_REL_LABORAL T WHERE T.YEAR = INFO.YEAR AND T.REL_LABORAL = INFO.REL_LABORAL ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ACTIVIDAD">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.ACTIVIDAD" + ls_sep + "T.DESCR" + ls_sep + "T.INDICADOR" + ls_sep + "T.OBRA FROM "
                + " (SELECT F.YEAR,D.ACTIVIDAD FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_ACTIVIDAD D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.ACTIVIDAD = T.PROYECTO "
                + " UNION "
                + "SELECT F.YEAR,D.ACTIVIDAD FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_ACTIVIDAD D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.ACTIVIDAD = T.PROYECTO "
                + " UNION "
                + " SELECT F.YEAR,D.ACTIVIDAD FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_ACTIVIDAD D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.ACTIVIDAD = T.PROYECTO "
                + " )"
                + " INFO,S_POA_ACTIVIDAD  T WHERE T.YEAR = INFO.YEAR AND T.ACTIVIDAD = INFO.ACTIVIDAD ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="CATALOGO_PROYECTO">                        
        arQuerys.add("SELECT T.YEAR" + ls_sep + "T.TIPO_PROY" + ls_sep + "T.PROY" + ls_sep + "T.DESCR" + ls_sep + "T.OBRA FROM "
                + " (SELECT F.YEAR,T.TIPO_PROY,T.PROYECTO FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT, S_POA_CATALOGO_PROYECTO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.TIPO_PROY = T.TIPO_PROY AND D.PROY = T.PROYECTO "
                + " UNION "
                + "SELECT F.YEAR,T.TIPO_PROY,T.PROYECTO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_CATALOGO_PROYECTO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.TIPO_PROY = T.TIPO_PROY AND D.PROY = T.PROYECTO  "
                + " UNION "
                + " SELECT F.YEAR,T.TIPO_PROY,T.PROYECTO FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,S_POA_CATALOGO_PROYECTO D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.TIPO_PROY = T.TIPO_PROY AND D.PROY = T.PROYECTO  "
                + " UNION "
                + " SELECT F.YEAR,T.TIPO_PROY,T.PROY FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,S_POA_CATALOGO_PROYECTO D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.TIPO_PROY = T.TIPO_PROY AND D.PROY = T.PROY "
                + " )"
                + " INFO,S_POA_CATALOGO_PROYECTO  T WHERE T.YEAR = INFO.YEAR AND T.TIPO_PROY = INFO.TIPO_PROY AND T.PROY = INFO.PROYECTO  ");
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="RAMO_P">                        
        arQuerys.add("SELECT DISTINCT T.YEAR" + ls_sep + "T.RAMO" + ls_sep + "T.PRG" + ls_sep + "T.OBJETIVO" + ls_sep + "T.RESULTADO" + ls_sep + "T.ASPECTOS" + ls_sep + "T.RFC" + ls_sep
                + "T.HOMOCLAVE" + ls_sep + "T.DEPTO" + ls_sep + "T.COORD_RFC" + ls_sep + "T.COORD_HOMO" + ls_sep + "T.FIN"
                + ls_sep + "T.PROPOSITO" + ls_sep + "T.TIPO_PROGRAMA" + ls_sep + "T.PONDERADO" + ls_sep + "T.TIPO_OBJ FROM "
                + " (SELECT F.YEAR,T.RAMO,T.PRG FROM POA.EXTRAER_OFICIOS F,SPP.AMPLIACIONES T,DGI.OFICONS OO,DGI.TIPOFICIO OT,POA.RAMO_PROGRAMA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG "
                + " UNION "
                + "SELECT F.YEAR,T.RAMO,T.PRG FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFERENCIAS T,DGI.OFICONS OO,DGI.TIPOFICIO OT,POA.RAMO_PROGRAMA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG  "
                + " UNION "
                + " SELECT F.YEAR,T.RAMO,T.PRG FROM POA.EXTRAER_OFICIOS F,SPP.TRANSFREC T,DGI.OFICONS OO,DGI.TIPOFICIO OT,POA.RAMO_PROGRAMA D " + sFiltroCatsAmpTrans
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG  "
                + " UNION "
                + " SELECT F.YEAR,T.RAMO,T.PRG FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_META T,POA.RAMO_PROGRAMA D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG "
                + " UNION "
                + " SELECT F.YEAR,T.RAMO,T.PRG FROM POA.EXTRAER_OFICIOS F,POA.MOVOFICIOS_ACCION_REQ T,POA.RAMO_PROGRAMA D " + sFiltro
                + "  AND D.YEAR = F.YEAR AND D.RAMO = T.RAMO AND D.PRG = T.PRG )"
                + " INFO,POA.RAMO_PROGRAMA  T WHERE T.YEAR = INFO.YEAR AND T.RAMO = INFO.RAMO AND T.PRG = INFO.PRG  ");
        //</editor-fold>

        arQuerys.add("SELECT T.OFICIO" + ls_sep
                + "T.RAMO" + ls_sep + "T.DEPTO" + ls_sep + "T.FINALIDAD"
                + ls_sep + "T.FUNCION" + ls_sep + "T.SUBFUNCION" + ls_sep + "T.PRG_CONAC" + ls_sep + "T.PRG" + ls_sep
                + "T.TIPO_PROY" + ls_sep + "T.PROYECTO" + ls_sep + "T.META" + ls_sep + "T.ACCION" + ls_sep + "T.PARTIDA"
                + ls_sep + "T.TIPO_GASTO" + ls_sep + "T.FUENTE" + ls_sep + "T.FONDO" + ls_sep + "T.RECURSO" + ls_sep
                + "T.MUNICIPIO" + ls_sep + "T.DELEGACION" + ls_sep + "T.REL_LABORAL" + ls_sep + "T.REQUERIMIENTO" + ls_sep
                + "MES" + ls_sep + "IMPTE" + ls_sep + " T.PPTO_ORIGINAL FROM POA.EXTRAER_OFICIOS F,SPP.RECALENDARIZACION T" + sFiltro);
        return arQuerys;

    }

    public String getSQLConsultaComparativoHash(String appPwd, String salt) {
        String strSQL = " SELECT SYS.F_COMPARATIVA_HASH('" + appPwd + "', '" + salt + "') FROM SYS.DUAL ";
        return strSQL;
    }

    public String getSQLConsultaDiasRestantes(String appLogin) {
        String strSQL = " SELECT "
                + "CASE  "
                + "WHEN PERMITE_ACCESO_PWDNVO - DIAS_TRANSC > 0 THEN PERMITE_ACCESO_PWDNVO - DIAS_TRANSC "
                + "ELSE 0 END AS DIAS_RESTANTES "
                + "FROM ( "
                + "SELECT PERMITE_ACCESO_PWDNVO FROM DGI.PARAMETROS) a, "
                + "(SELECT TRUNC(SYSDATE) - TRUNC(NVL(MIN(FECHA_ACCESO), SYSDATE)) AS DIAS_TRANSC   "
                + "FROM DGI.BIT_ACCESO_APLICACION "
                + "WHERE SYS_CLAVE = 1 "
                + "    AND APP_LOGIN = '" + appLogin + "') b ";
        return strSQL;
    }

    public String getSQLInsertaBitAcceso(String appLogin) {
        String strSQL = "INSERT INTO DGI.BIT_ACCESO_APLICACION "
                + "(APP_LOGIN, SYS_CLAVE, FECHA_ACCESO) "
                + "VALUES ('" + appLogin + "', 1, SYSDATE) ";
        return strSQL;
    }

    public String getSQLEncriptarContrasenia() {
        String strSQL = "SELECT ENCRIPTAR_CONTRASENIAS "
                + " FROM DGI.PARAMETROS ";
        return strSQL;
    }

    public String getSQLEstimacionMetaAvancePoa() {
        String strSQL = " SELECT NVL(COUNT(*),0) AS CONT FROM DGI.ESTIMACION "
                + " WHERE "
                + " RAMO=? AND "
                + " YEAR=? AND "
                + " META=? ";
        return strSQL;
    }

    public String getSQLEstimacionAccionAvancePoa() {
        String strSQL = " SELECT NVL(COUNT(*),0) AS CONT FROM DGI.ACCION_ESTIMACION "
                + " WHERE "
                + " RAMO=? AND "
                + " YEAR=? AND "
                + " META=? AND "
                + " ACCION=? ";
        return strSQL;
    }

    public String getQuerysBDGeneraNuevoHash() {
        String strSQL = " BEGIN ? := SYS.F_NUEVO_HASH(?,?); END; ";
        return strSQL;
    }

    public String getQuerysBDCambiaContrasena(String strUsuario, String strContrasenaAct, String strContrsenaNva, String strSalt) {
        String strSQL = " "
                + "UPDATE "
                + "	DGI.DGI_USR USR "
                + "SET "
                + "	USR.APP_PWD = '" + strContrsenaNva + "', "
                + "	USR.SALT = '" + strSalt + "', "
                + "	USR.PWD_AUTOGENERADO = 'N' "
                + "WHERE "
                + "	USR.APP_LOGIN = '" + strUsuario + "'  AND "
                + "	USR.APP_PWD = '" + strContrasenaAct + "' AND "
                + "	USR.STATUS = 'A' AND "
                + "	USR.SYS_CLAVE = 1 "
                + " ";
        return strSQL;
    }

    public String getSQLMunicipioUsuario(String strUsuario) {
        String strSQL = "SELECT USR.MPO "
                + "FROM DGI.DGI_USR USR "
                + "WHERE USR.APP_LOGIN = UPPER('" + strUsuario.trim() + "')"
                + "     AND SYS_CLAVE = 1 ";
        return strSQL;
    }

    public String getSQLValidacionRamoAvancePoa(String ramoId, int year) {
        String strSQL = "SELECT 1 AS VALIDA FROM DGI.RAMOS RAM "
                + "WHERE RAM.CIERRE_PRG_AVA = 'S' AND "
                + "      RAM.YEAR = " + year + " AND "
                + "      RAM.RAMO = '" + ramoId + "'";
        return strSQL;
    }

    public String getSQLGetCaratulaPresupuestoIngresoByCaratulaRamoConcepto(int year, String ramo, String concepto, long caratula) {
        String strSQL = ""
                + "SELECT "
                + "	INGRESO.YEAR, "
                + "	INGRESO.ID_CARATULA, "
                + "	INGRESO.RAMO, "
                + "	INGRESO.CONCEPTO, "
                + "	INGRESO.CONSEC, "
                + "	INGRESO.TIPOMOV, "
                + "	INGRESO.DESCR, "
                + "	INGRESO.ENE, "
                + "	INGRESO.FEB, "
                + "	INGRESO.MZO, "
                + "	INGRESO.ABR, "
                + "	INGRESO.MAY, "
                + "	INGRESO.JUN, "
                + "	INGRESO.JUL, "
                + "	INGRESO.AGO, "
                + "	INGRESO.SEP, "
                + "	INGRESO.OCT, "
                + "	INGRESO.NOV, "
                + "	INGRESO.DIC "
                + "FROM "
                + "	( "
                + "		SELECT "
                + "			CRTLA_PPTO_ING.YEAR, "
                + "			CRTLA_PPTO_ING.ID_CARATULA, "
                + "			CRTLA_PPTO_ING.RAMO, "
                + "			CRTLA_PPTO_ING.CONCEPTO, "
                + "			CRTLA_PPTO_ING.CONSEC, "
                + "			CRTLA_PPTO_ING.TIPOMOV, "
                + "			CRTLA_PPTO_ING.DESCR, "
                + "			CRTLA_PPTO_ING.ENE, "
                + "			CRTLA_PPTO_ING.FEB, "
                + "			CRTLA_PPTO_ING.MZO, "
                + "			CRTLA_PPTO_ING.ABR, "
                + "			CRTLA_PPTO_ING.MAY, "
                + "			CRTLA_PPTO_ING.JUN, "
                + "			CRTLA_PPTO_ING.JUL, "
                + "			CRTLA_PPTO_ING.AGO, "
                + "			CRTLA_PPTO_ING.SEP, "
                + "			CRTLA_PPTO_ING.OCT, "
                + "			CRTLA_PPTO_ING.NOV, "
                + "			CRTLA_PPTO_ING.DIC "
                + "		FROM "
                + "			POA.CARATULA_PPTO_ING CRTLA_PPTO_ING "
                + "		WHERE  "
                + "			CRTLA_PPTO_ING.YEAR = " + year + " AND "
                + "			CRTLA_PPTO_ING.RAMO = '" + ramo + "' AND "
                + "			CRTLA_PPTO_ING.CONCEPTO = '" + concepto + "' AND "
                + "			CRTLA_PPTO_ING.ID_CARATULA = " + caratula + ""
                + "	) INGRESO "
                + "ORDER BY "
                + "	INGRESO.YEAR, "
                + "	INGRESO.ID_CARATULA, "
                + "	INGRESO.RAMO, "
                + "	INGRESO.CONCEPTO, "
                + "	INGRESO.CONSEC "
                + "";
        return strSQL;
    }

    public String getSQLGetCapturaPresupuestoIngresoById(int year, String ramo, String concepto, int subConcepto, long caratula) {

        String strSQL = "";
        if (caratula != -2) {
            strSQL = ""
                    + "SELECT "
                    + "	CRTLA_PPTO_ING.ID_CARATULA, "
                    + "	CRTLA_PPTO_ING.YEAR, "
                    + "	CRTLA_PPTO_ING.RAMO, "
                    + "	CRTLA_PPTO_ING.CONCEPTO, "
                    + "	CRTLA_PPTO_ING.CONSEC, "
                    + "	CRTLA_PPTO_ING.DESCR, "
                    + "	CRTLA_PPTO_ING.TIPOMOV, "
                    + "	CRTLA_PPTO_ING.ENE, "
                    + "	CRTLA_PPTO_ING.FEB, "
                    + "	CRTLA_PPTO_ING.MZO, "
                    + "	CRTLA_PPTO_ING.ABR, "
                    + "	CRTLA_PPTO_ING.MAY, "
                    + "	CRTLA_PPTO_ING.JUN, "
                    + "	CRTLA_PPTO_ING.JUL, "
                    + "	CRTLA_PPTO_ING.AGO, "
                    + "	CRTLA_PPTO_ING.SEP, "
                    + "	CRTLA_PPTO_ING.OCT, "
                    + "	CRTLA_PPTO_ING.NOV, "
                    + "	CRTLA_PPTO_ING.DIC "
                    + "FROM "
                    + "	POA.CARATULA_PPTO_ING CRTLA_PPTO_ING "
                    + "WHERE "
                    + "	CRTLA_PPTO_ING.YEAR = " + year + " AND "
                    + "	CRTLA_PPTO_ING.RAMO = '" + ramo + "' AND "
                    + "	CRTLA_PPTO_ING.CONCEPTO = '" + concepto + "' AND "
                    + "	CRTLA_PPTO_ING.CONSEC= " + subConcepto + " AND "
                    + "	CRTLA_PPTO_ING.ID_CARATULA= " + caratula + " "
                    + "ORDER BY "
                    + "	CRTLA_PPTO_ING.YEAR, "
                    + "	CRTLA_PPTO_ING.ID_CARATULA, "
                    + "	CRTLA_PPTO_ING.RAMO, "
                    + "	CRTLA_PPTO_ING.CONCEPTO, "
                    + "	CRTLA_PPTO_ING.CONSEC "
                    + "";

        } else {
            strSQL = ""
                    + "SELECT DISTINCT"
                    + "	0 ID_CARATULA, "
                    + "	CRTLA_PPTO_ING.YEAR, "
                    + "	CRTLA_PPTO_ING.RAMO, "
                    + "	CRTLA_PPTO_ING.CONCEPTO, "
                    + "	CRTLA_PPTO_ING.CONSEC, "
                    + "	CRTLA_PPTO_ING.DESCR, "
                    + "	'' TIPOMOV, "
                    + "	0 ENE, "
                    + "	0 FEB, "
                    + "	0 MZO, "
                    + "	0 ABR, "
                    + "	0 MAY, "
                    + "	0 JUN, "
                    + "	0 JUL, "
                    + "	0 AGO, "
                    + "	0 SEP, "
                    + "	0 OCT, "
                    + "	0 NOV, "
                    + "	0 DIC "
                    + "FROM "
                    + "	POA.CARATULA_PPTO_ING CRTLA_PPTO_ING "
                    + "WHERE "
                    + "	CRTLA_PPTO_ING.YEAR = " + year + " AND "
                    + "	CRTLA_PPTO_ING.RAMO = '" + ramo + "' AND "
                    + "	CRTLA_PPTO_ING.CONCEPTO = '" + concepto + "' AND "
                    + "	CRTLA_PPTO_ING.CONSEC= " + subConcepto + " "
                    + "ORDER BY "
                    + "	CRTLA_PPTO_ING.YEAR, "
                    + "	CRTLA_PPTO_ING.RAMO, "
                    + "	CRTLA_PPTO_ING.CONCEPTO, "
                    + "	CRTLA_PPTO_ING.CONSEC "
                    + "";
        }
        return strSQL;
    }

    public String getSQLGetMaxSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto) {
        String strSQL = ""
                + "SELECT "
                + "	NVL(MAX(MAXIMOS.MAX_PPTO),0)+1 AS MAX_PPTO "
                + "FROM "
                + "	( "
                + "		SELECT "
                + "			NVL(MAX(PPI.CONSEC),0) AS MAX_PPTO "
                + "		FROM "
                + "			DGI.POA_PPTO_ING PPI "
                + "		WHERE "
                + "			PPI.YEAR = " + year + " AND "
                + "			PPI.RAMO = '" + ramo + "' AND "
                + "			PPI.CONCEPTO = '" + concepto + "' "
                + "		UNION ALL "
                + "		SELECT "
                + "			NVL(MAX(PPI.CONSEC),0) AS MAX_PPTO "
                + "		FROM "
                + "			POA.CARATULA_PPTO_ING PPI "
                + "		WHERE "
                + "			PPI.YEAR = " + year + " AND "
                + "			PPI.RAMO = '" + ramo + "' AND "
                + "			PPI.CONCEPTO = '" + concepto + "' "
                + "	) MAXIMOS "
                + "";
        return strSQL;
    }

    public String getSQLInsertSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto, int subConcepto, String subConceptoDescr, double ene, double feb, double mar, double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic, String tipoMov) {
        String strSQL = ""
                + "INSERT INTO "
                + "	POA.CARATULA_PPTO_ING "
                + "	( "
                + "		ID_CARATULA, "
                + "		YEAR, "
                + "		RAMO, "
                + "		CONCEPTO, "
                + "		CONSEC, "
                + "		DESCR, "
                + "		ENE, "
                + "		FEB, "
                + "		MZO, "
                + "		ABR, "
                + "		MAY, "
                + "		JUN, "
                + "		JUL, "
                + "		AGO, "
                + "		SEP, "
                + "		OCT, "
                + "		NOV, "
                + "		DIC, "
                + "		TIPOMOV "
                + "	 ) "
                + "VALUES "
                + "	( "
                + "		" + caratula + ", "
                + "		" + year + ", "
                + "		'" + ramo + "', "
                + "		'" + concepto + "', "
                + "		" + subConcepto + ", "
                + "		'" + subConceptoDescr + "', "
                + "		" + ene + ", "
                + "		" + feb + ", "
                + "		" + mar + ", "
                + "		" + abr + ", "
                + "		" + may + ", "
                + "		" + jun + ", "
                + "		" + jul + ", "
                + "		" + ago + ", "
                + "		" + sep + ", "
                + "		" + oct + ", "
                + "		" + nov + ", "
                + "		" + dic + ", "
                + "		'" + tipoMov + "' "
                + "	) "
                + "";
        return strSQL;
    }

    public String getSQLUpdateSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto, int subConcepto, String subConceptoDescr, double ene, double feb, double mar, double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic, String tipoMov) {
        String strSQL = ""
                + "UPDATE "
                + "	POA.CARATULA_PPTO_ING "
                + "SET "
                + "	DESCR = '" + subConceptoDescr + "', "
                + "	ENE = " + ene + ", "
                + "	FEB = " + feb + ", "
                + "	MZO = " + mar + ", "
                + "	ABR = " + abr + ", "
                + "	MAY = " + may + ", "
                + "	JUN = " + jun + ", "
                + "	JUL = " + jul + ", "
                + "	AGO = " + ago + ", "
                + "	SEP = " + sep + ", "
                + "	OCT = " + oct + ", "
                + "	NOV = " + nov + ", "
                + "	DIC = " + dic + ", "
                + "	TIPOMOV = '" + tipoMov + "' "
                + "WHERE"
                + "	ID_CARATULA = " + caratula + " AND "
                + "	YEAR = " + year + " AND "
                + "	RAMO = '" + ramo + "' AND "
                + "	CONCEPTO = '" + concepto + "' AND "
                + "	CONSEC = " + subConcepto + " "
                + "";
        return strSQL;
    }

    public String getSLQDeleteSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto, int subConcepto) {
        String strSQL = ""
                + "DELETE FROM "
                + "	POA.CARATULA_PPTO_ING P "
                + "WHERE"
                + "	( SELECT CARP.NUM_MOD_PRES*1 FROM POA.CARATULA CARP WHERE CARP.ID_CARATULA = P.ID_CARATULA ) >= ( SELECT CAR.NUM_MOD_PRES*1 FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA = " + caratula + " ) AND "
                + "	P.YEAR = " + year + " AND "
                + "	P.RAMO = '" + ramo + "' AND "
                + "	P.CONCEPTO = '" + concepto + "' AND "
                + "	P.CONSEC = " + subConcepto + " "
                + "";
        return strSQL;
    }

    public String getSQLGetHistEstimacion(int year, String ramoId, int metaId, int folio) {
        String strSQL = ""
                + "SELECT "
                + "	META, "
                + "	RAMO, "
                + "	PERIODO, "
                + "	VALOR "
                + "FROM "
                + "	POA.HIST_ESTIMACION "
                + "WHERE "
                + "	YEAR =  " + year + " AND "
                + "	OFICIO = " + folio + " AND "
                + "	RAMO = '" + ramoId + "' AND "
                + "	META = " + metaId + " "
                + "";
        return strSQL;
    }

    public String getSQLGetHistEstimacion(int year, String ramoId, int metaId, int accionId, int folio) {
        String strSQL = ""
                + "SELECT "
                + "	PERIODO, "
                + "	VALOR "
                + "FROM "
                + "	POA.HIST_ACCION_ESTIMACION "
                + "WHERE "
                + "	YEAR =  " + year + " AND "
                + "	OFICIO = " + folio + " AND "
                + "	RAMO = '" + ramoId + "' AND "
                + "	META = " + metaId + " AND "
                + "	ACCION = " + accionId + " "
                + "";
        return strSQL;
    }

    public String getSQLGetListRevisionesCaratulaByRamoCaratulaYear(String ramo, long caratula, int year, int yearSesion, int tipoModificacion, int tipoSesion) {

        String strSQL = " "
                + "SELECT "
                + "	REVS.REVISION, "
                + "	REVS.DESCR_CORTA, "
                + "	REVS.DESCR, "
                + "	REVS.YEAR, "
                + "	(SELECT CASE WHEN COUNT(*) < 1 THEN 1 ELSE 0 END LIBRE FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA <> " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + yearSesion + " AND CAR.TIPO_MODIFICACION = " + tipoModificacion + " AND CAR.TIPO_SESION = " + tipoSesion + " AND CAR.NUMERO_SESION = REVS.REVISION) LIBRE_NUM_SESION, "
                + "	(SELECT DECODE(COUNT(*),1,0,0,1) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA <> " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + year + " AND CAR.NUM_MOD_PRES = REVS.REVISION) LIBRE_MOD_PRESUP, "
                + "	(SELECT DECODE(COUNT(*),1,0,0,1) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA <> " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + year + " AND CAR.NUM_MOD_PROG = REVS.REVISION) LIBRE_MOD_PROG, ";

        if (caratula != -1) {
            if (year == yearSesion) {
                strSQL += " "
                        + "	(SELECT DECODE(COUNT(*),1,1,0,0) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA  = " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + year + " AND CAR.NUMERO_SESION = REVS.REVISION) SELECTED_NUM_SESION, "
                        + "	(SELECT DECODE(COUNT(*),1,1,0,0) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA  = " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + year + " AND CAR.NUM_MOD_PRES = REVS.REVISION) SELECTED_MOD_PRESUP, "
                        + "	(SELECT DECODE(COUNT(*),1,1,0,0) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA  = " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + year + " AND CAR.NUM_MOD_PROG = REVS.REVISION) SELECTED_MOD_PROG ";
            } else {
                strSQL += " "
                        + "	(SELECT DECODE(COUNT(*),1,1,0,0) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA  = " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + yearSesion + " AND CAR.NUMERO_SESION = REVS.REVISION) SELECTED_NUM_SESION, "
                        + "	(SELECT DECODE(COUNT(*),1,1,0,0) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA  = " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + yearSesion + " AND CAR.NUM_MOD_PRES = REVS.REVISION) SELECTED_MOD_PRESUP, "
                        + "	(SELECT DECODE(COUNT(*),1,1,0,0) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA  = " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + yearSesion + " AND CAR.NUM_MOD_PROG = REVS.REVISION) SELECTED_MOD_PROG ";
            }
        } else {
            strSQL += " "
                    + "	(SELECT DECODE(COUNT(*),1,1,0,0) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA  = " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + year + " AND CAR.NUMERO_SESION = REVS.REVISION) SELECTED_NUM_SESION, "
                    + "	(SELECT DECODE(COUNT(*),1,1,0,0) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA  = " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + year + " AND CAR.NUM_MOD_PRES = REVS.REVISION) SELECTED_MOD_PRESUP, "
                    + "	(SELECT DECODE(COUNT(*),1,1,0,0) FROM POA.CARATULA CAR WHERE CAR.ID_CARATULA  = " + caratula + " AND CAR.RAMO = '" + ramo + "' AND EXTRACT(YEAR FROM CAR.FECHA_REVISION) = " + year + " AND CAR.NUM_MOD_PROG = REVS.REVISION) SELECTED_MOD_PROG ";
        }

        strSQL += " "
                + "FROM "
                + "	POA.REVISIONES_CARATULA REVS "
                + " ";

        return strSQL;
    }

    public String getQuerysBDGetPartidaByIdPartidaYear(String idPartida, int year) {
        String strSQL = ""
                + "SELECT "
                + "	PAR.PARTIDA, "
                + "	PAR.DESCR, "
                + "	PAR.ARTICULO_POA, "
                + "	PAR.ARTICULO_SIP, "
                + "	NVL(PAR.JUSTIF_REQ,0) JUSTIF_REQ "
                + "FROM "
                + "	DGI.PARTIDA PAR "
                + "WHERE "
                + "	PAR.YEAR = '" + year + "' AND "
                + "	PAR.PARTIDA = '" + idPartida + "' "
                + "";
        return strSQL;
    }

    public String getSQLGetArticulosSipByYearPartida(int year, String partida) {
        String strSQL = ""
                + "SELECT "
                + "    ART.GPOGTO, "
                + "    ART.SUBGPO, "
                + "    ART.ARTICULO, "
                + "    ART.DESCR, "
                + "    ART.COSTO "
                + "FROM "
                + "    S_POA_ARTICULO ART "
                + "WHERE "
                + "	ART.PARTIDA = '" + partida + "' "
                + "ORDER BY "
                + "    ART.GPOGTO, "
                + "    ART.SUBGPO, "
                + "    ART.ARTICULO "
                + "";
        return strSQL;
    }

    public String getSQLIsArticuloSipPartida(int year, String partida) {
        String strSQL = "SELECT COUNT(*) AS CONT "
                + "FROM S_POA_ARTICULO ARP "
                + "WHERE ARP.PARTIDA = '" + partida + "' "
                + "";
        return strSQL;
    }

    public String getSQLGetArticuloSipDescr(String partida, int articulo, int gpogto, int subgpo) {
        String strSQL = "SELECT ART.DESCR FROM S_POA_ARTICULO ART\n"
                + "WHERE ART.ARTICULO = " + articulo + " AND "
                + "      ART.PARTIDA = '" + partida + "' AND "
                + "      ART.SUBGPO = " + subgpo + " AND "
                + "      ART.GPOGTO = " + gpogto;
        return strSQL;
    }

    public String getSQLIsPlantillaByRamoProgramaDeptoMetaAccionYear(String ramoId, String programaId, String deptoId, int metaId, int accionId, int year) {
        String strSQL = " "
                + " "
                + "SELECT "
                + "	NVL(COUNT(*),0) AS CONT "
                + "FROM "
                + "	POA.META_ACCION_PLANTILLA "
                + "WHERE "
                + "	YEAR = " + year + " AND "
                + "	RAMO = '" + ramoId + "' AND "
                + "	PRG = '" + programaId + "' AND "
                + "	DEPTO = '" + deptoId + "' AND "
                + "	META = " + metaId + " AND "
                + "	ACCION = " + accionId + " "
                + " ";
        return strSQL;
    }

    public String getSQLExistPlantillaByRamoProgramaDeptoMetaAccionYear(String ramoId, String programaId, String deptoId, int metaId, int accionId, int year) {
        String strSQL = " "
                + " "
                + "SELECT "
                + "	NVL(COUNT(*),0) AS CONT "
                + "FROM "
                + "	POA.META_ACCION_PLANTILLA "
                + "WHERE "
                + "	YEAR = " + year + " AND "
                + "	RAMO = '" + ramoId + "' AND "
                + "	PRG = '" + programaId + "' AND "
                + "	DEPTO = '" + deptoId + "'  "
                + " ";
        return strSQL;
    }

    public String getSQLPresupuestoIngresoById(int year, String ramo, String concepto, int subConcepto) {
        String strSQL = ""
                + "SELECT "
                + "	CRTLA_PPTO_ING.YEAR, "
                + "	CRTLA_PPTO_ING.RAMO, "
                + "	CRTLA_PPTO_ING.CONCEPTO, "
                + "	CRTLA_PPTO_ING.CONSEC, "
                + "	CRTLA_PPTO_ING.DESCR, "
                + "	CRTLA_PPTO_ING.ENE, "
                + "	CRTLA_PPTO_ING.FEB, "
                + "	CRTLA_PPTO_ING.MZO, "
                + "	CRTLA_PPTO_ING.ABR, "
                + "	CRTLA_PPTO_ING.MAY, "
                + "	CRTLA_PPTO_ING.JUN, "
                + "	CRTLA_PPTO_ING.JUL, "
                + "	CRTLA_PPTO_ING.AGO, "
                + "	CRTLA_PPTO_ING.SEP, "
                + "	CRTLA_PPTO_ING.OCT, "
                + "	CRTLA_PPTO_ING.NOV, "
                + "	CRTLA_PPTO_ING.DIC "
                + "FROM "
                + "	DGI.POA_PPTO_ING CRTLA_PPTO_ING "
                + "WHERE "
                + "	CRTLA_PPTO_ING.YEAR = " + year + " AND "
                + "	CRTLA_PPTO_ING.RAMO = '" + ramo + "' AND "
                + "	CRTLA_PPTO_ING.CONCEPTO = '" + concepto + "' AND "
                + "	CRTLA_PPTO_ING.CONSEC= " + subConcepto + " "
                + "ORDER BY "
                + "	CRTLA_PPTO_ING.YEAR, "
                + "	CRTLA_PPTO_ING.RAMO, "
                + "	CRTLA_PPTO_ING.CONCEPTO, "
                + "	CRTLA_PPTO_ING.CONSEC "
                + "";
        return strSQL;
    }

    public String getSQLGetSubconceptosPresupuestoIngresoByRamoConcepto(int year, String ramo, String concepto, long caratula) {
        String strSQL = ""
                + "SELECT  "
                + "	* "
                + "FROM "
                + "	( "
                + "		SELECT  "
                + "			PPTO_ING.YEAR,  "
                + "			PPTO_ING.RAMO,  "
                + "			PPTO_ING.CONCEPTO,  "
                + "			PPTO_ING.CONSEC,  "
                + "			PPTO_ING.DESCR,  "
                + "			PPTO_ING.ENE,  "
                + "			PPTO_ING.FEB,  "
                + "			PPTO_ING.MZO,  "
                + "			PPTO_ING.ABR,  "
                + "			PPTO_ING.MAY,  "
                + "			PPTO_ING.JUN,  "
                + "			PPTO_ING.JUL,  "
                + "			PPTO_ING.AGO,  "
                + "			PPTO_ING.SEP,  "
                + "			PPTO_ING.OCT,  "
                + "			PPTO_ING.NOV,  "
                + "			PPTO_ING.DIC  "
                + "		FROM  "
                + "			DGI.POA_PPTO_ING PPTO_ING  "
                + "		WHERE  "
                + "			PPTO_ING.YEAR =   " + year + "   AND  "
                + "			PPTO_ING.RAMO = '" + ramo + "' AND  "
                + "			PPTO_ING.CONCEPTO = '" + concepto + "'  AND  "
                + "			-1 <> " + caratula + "  AND  "
                + "			NOT EXISTS (  "
                + "				SELECT  "
                + "					*  "
                + "				FROM  "
                + "					POA.CARATULA_PPTO_ING CRTLA_PPTO_ING  "
                + "				WHERE  "
                + "					CRTLA_PPTO_ING.YEAR = PPTO_ING.YEAR AND  "
                + "					CRTLA_PPTO_ING.RAMO = PPTO_ING.RAMO AND  "
                + "					CRTLA_PPTO_ING.CONCEPTO = PPTO_ING.CONCEPTO AND  "
                + "					CRTLA_PPTO_ING.CONSEC = PPTO_ING.CONSEC AND "
                + "					CRTLA_PPTO_ING.ID_CARATULA = " + caratula + " "
                + "					)  "
                + "		UNION ALL "
                + "		SELECT  DISTINCT "
                + "			PPTO_ING.YEAR,  "
                + "			PPTO_ING.RAMO,  "
                + "			PPTO_ING.CONCEPTO,  "
                + "			PPTO_ING.CONSEC,  "
                + "			PPTO_ING.DESCR,  "
                + "			0 ENE,  "
                + "			0 FEB,  "
                + "			0 MZO,  "
                + "			0 ABR,  "
                + "			0 MAY,  "
                + "			0 JUN,  "
                + "			0 JUL,  "
                + "			0 AGO,  "
                + "			0 SEP,  "
                + "			0 OCT,  "
                + "			0 NOV,  "
                + "			0 DIC  "
                + "		FROM  "
                + "			POA.CARATULA_PPTO_ING PPTO_ING,"
                + "			POA.CARATULA CAR"
                + "		WHERE  "
                + "			PPTO_ING.YEAR =   " + year + "   AND  "
                + "			PPTO_ING.RAMO = '" + ramo + "' AND  "
                + "			PPTO_ING.CONCEPTO = '" + concepto + "'  AND  "
                + "			PPTO_ING.YEAR = CAR.YEAR AND  "
                + "			PPTO_ING.RAMO = CAR.RAMO AND  "
                + "			CAR.NUM_MOD_PRES*1 < (SELECT CF.NUM_MOD_PRES*1 FROM POA.CARATULA CF WHERE CF.ID_CARATULA = " + caratula + " ) AND "
                + "			NOT EXISTS (  "
                + "				SELECT  "
                + "					*  "
                + "				FROM  "
                + "					DGI.POA_PPTO_ING CRTLA_PPTO_ING  "
                + "				WHERE  "
                + "					CRTLA_PPTO_ING.YEAR = PPTO_ING.YEAR AND  "
                + "					CRTLA_PPTO_ING.RAMO = PPTO_ING.RAMO AND  "
                + "					CRTLA_PPTO_ING.CONCEPTO = PPTO_ING.CONCEPTO AND  "
                + "					CRTLA_PPTO_ING.CONSEC = PPTO_ING.CONSEC  "
                + "			) AND "
                + "			NOT EXISTS (  "
                + "				SELECT  "
                + "					*  "
                + "				FROM  "
                + "					POA.CARATULA_PPTO_ING CRTLA_PPTO_ING  "
                + "				WHERE  "
                + "					CRTLA_PPTO_ING.YEAR = PPTO_ING.YEAR AND  "
                + "					CRTLA_PPTO_ING.RAMO = PPTO_ING.RAMO AND  "
                + "					CRTLA_PPTO_ING.CONCEPTO = PPTO_ING.CONCEPTO AND  "
                + "					CRTLA_PPTO_ING.ID_CARATULA = " + caratula + " AND  "
                + "					CRTLA_PPTO_ING.CONSEC = PPTO_ING.CONSEC  "
                + "			)  "
                + "	) ING "
                + "ORDER BY  "
                + "	ING.YEAR,  "
                + "	ING.RAMO,  "
                + "	ING.CONCEPTO,  "
                + "	ING.CONSEC  "
                + "";
        return strSQL;
    }

    public String getSQLExisteSubConceptoCapturaIngreso(int year, String ramo, String concepto, int subconcepto, long caratula) {
        String strSQL = ""
                + "SELECT "
                + "	COUNT(*) CONT "
                + "FROM "
                + "	POA.CARATULA_PPTO_ING CRTLA_PPTO_ING "
                + "WHERE "
                + "	CRTLA_PPTO_ING.YEAR = " + year + " AND "
                + "	CRTLA_PPTO_ING.RAMO = '" + ramo + "' AND "
                + "	CRTLA_PPTO_ING.CONCEPTO = '" + concepto + "' AND "
                + "	CRTLA_PPTO_ING.CONSEC = " + subconcepto + " AND "
                + "	CRTLA_PPTO_ING.ID_CARATULA = " + caratula + " "
                + "";
        return strSQL;
    }

    public String getSQLGetRamoByRamoIdYear(String ramoId, int year) {
        String strSQL = " "
                + "SELECT "
                + "	RAM.RAMO, "
                + "	RAM.DESCR, "
                + "	RAM.ABREVIATURA "
                + "FROM "
                + "	DGI.RAMOS RAM "
                + "WHERE "
                + "	RAM.YEAR = " + year + " AND "
                + "	RAM.RAMO = '" + ramoId + "' "
                + "GROUP BY "
                + "	RAM.RAMO, "
                + "	RAM.DESCR, "
                + "	RAM.ABREVIATURA "
                + "ORDER BY "
                + "	RAM.RAMO "
                + " ";
        return strSQL;
    }

    public String getSQLgetParametroReporteCierre() {
        return "SELECT REPORTE_CIERRE FROM DGI.PARAMETROS";
    }

    public String getSQLgetEstatusMovReporte() {
        String strSQL = " SELECT ESTATUS,DESCR,ORDEN "
                + " FROM POA.ESTATUS_MOV  "
                + " WHERE "
                + " ESTATUS NOT IN('X','R','C','K') "
                + " ORDER BY "
                + " ORDEN ";
        return strSQL;
    }

    public String getSQLcountBitmovtosByOficio() {
        String strSQL = "SELECT COUNT(1) AS BITMOVTOS FROM POA.BITMOVTOS B\n"
                + "WHERE  B.OFICIO = ? ";
        return strSQL;
    }

    public String getSQLGetCalendarizacionIngresoPlusMod(int year, String ramo, String concepto, int subConcepto, long caratula) {

        String strSQL = "";

        strSQL = ""
                + "SELECT "
                + "	CAL_ING.YEAR, "
                + "	CAL_ING.RAMO,  "
                + "	CAL_ING.CONCEPTO, "
                + "	CAL_ING.CONSEC, "
                + "	SUM(CAL_ING.ENE) ENE, "
                + "	SUM(CAL_ING.FEB) FEB, "
                + "	SUM(CAL_ING.MZO) MZO, "
                + "	SUM(CAL_ING.ABR) ABR, "
                + "	SUM(CAL_ING.MAY) MAY, "
                + "	SUM(CAL_ING.JUN) JUN, "
                + "	SUM(CAL_ING.JUL) JUL, "
                + "	SUM(CAL_ING.AGO) AGO, "
                + "	SUM(CAL_ING.SEP) SEP, "
                + "	SUM(CAL_ING.OCT) OCT, "
                + "	SUM(CAL_ING.NOV) NOV, "
                + "	SUM(CAL_ING.DIC) DIC "
                + "FROM "
                + "	( "
                + "		SELECT "
                + "			'" + year + "' YEAR, "
                + "			'" + ramo + "' RAMO, "
                + "			'" + concepto + "' CONCEPTO, "
                + "			" + subConcepto + " CONSEC, "
                + "			0 ENE, "
                + "			0 FEB, "
                + "			0 MZO, "
                + "			0 ABR, "
                + "			0 MAY, "
                + "			0 JUN, "
                + "			0 JUL, "
                + "			0 AGO, "
                + "			0 SEP, "
                + "			0 OCT, "
                + "			0 NOV, "
                + "			0 DIC "
                + "		FROM "
                + "			SYS.DUAL "
                + " "
                + "		UNION ALL "
                + " "
                + "		SELECT "
                + "			CRTLA_PPTO_ING.YEAR, "
                + "			CRTLA_PPTO_ING.RAMO, "
                + "			CRTLA_PPTO_ING.CONCEPTO, "
                + "			CRTLA_PPTO_ING.CONSEC, "
                + "			CRTLA_PPTO_ING.ENE, "
                + "			CRTLA_PPTO_ING.FEB, "
                + "			CRTLA_PPTO_ING.MZO, "
                + "			CRTLA_PPTO_ING.ABR, "
                + "			CRTLA_PPTO_ING.MAY, "
                + "			CRTLA_PPTO_ING.JUN, "
                + "			CRTLA_PPTO_ING.JUL, "
                + "			CRTLA_PPTO_ING.AGO, "
                + "			CRTLA_PPTO_ING.SEP, "
                + "			CRTLA_PPTO_ING.OCT, "
                + "			CRTLA_PPTO_ING.NOV, "
                + "			CRTLA_PPTO_ING.DIC "
                + "		FROM "
                + "			DGI.POA_PPTO_ING CRTLA_PPTO_ING "
                + "		WHERE "
                + "			CRTLA_PPTO_ING.YEAR = " + year + " AND "
                + "			CRTLA_PPTO_ING.RAMO = '" + ramo + "' AND "
                + "			CRTLA_PPTO_ING.CONCEPTO = '" + concepto + "' AND "
                + "			CRTLA_PPTO_ING.CONSEC= " + subConcepto + " "
                + " "
                + "		UNION ALL "
                + " "
                + "		SELECT "
                + "			CRTLA_PPTO_ING.YEAR, "
                + "			CRTLA_PPTO_ING.RAMO, "
                + "			CRTLA_PPTO_ING.CONCEPTO, "
                + "			CRTLA_PPTO_ING.CONSEC, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.ENE,CRTLA_PPTO_ING.ENE*-1) AS ENE, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.FEB,CRTLA_PPTO_ING.FEB*-1) AS FEB, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.MZO,CRTLA_PPTO_ING.MZO*-1) AS MZO, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.ABR,CRTLA_PPTO_ING.ABR*-1) AS ABR, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.MAY,CRTLA_PPTO_ING.MAY*-1) AS MAY, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.JUN,CRTLA_PPTO_ING.JUN*-1) AS JUN, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.JUL,CRTLA_PPTO_ING.JUL*-1) AS JUL, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.AGO,CRTLA_PPTO_ING.AGO*-1) AS JUN, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.SEP,CRTLA_PPTO_ING.SEP*-1) AS SEP, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.OCT,CRTLA_PPTO_ING.OCT*-1) AS OCT, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.NOV,CRTLA_PPTO_ING.NOV*-1) AS NOV, "
                + "			DECODE(CRTLA_PPTO_ING.TIPOMOV,'A',CRTLA_PPTO_ING.DIC,CRTLA_PPTO_ING.DIC*-1) AS DIC "
                + "		FROM "
                + "			POA.CARATULA_PPTO_ING CRTLA_PPTO_ING,"
                + "			POA.CARATULA CAR  "
                + "		WHERE "
                + "			CRTLA_PPTO_ING.YEAR = " + year + " AND "
                + "			CRTLA_PPTO_ING.RAMO = '" + ramo + "' AND "
                + "			CRTLA_PPTO_ING.CONCEPTO = '" + concepto + "' AND "
                + "			CRTLA_PPTO_ING.CONSEC= " + subConcepto + " AND "
                + "			CRTLA_PPTO_ING.YEAR = CAR.YEAR AND "
                + "			CRTLA_PPTO_ING.RAMO = CAR.RAMO AND "
                + "			CRTLA_PPTO_ING.ID_CARATULA = CAR.ID_CARATULA AND "
                + "			CAR.NUM_MOD_PRES*1 < (SELECT CF.NUM_MOD_PRES*1 FROM POA.CARATULA CF WHERE CF.ID_CARATULA = " + caratula + " )  "
                + "	) CAL_ING "
                + "GROUP BY "
                + "	CAL_ING.YEAR, "
                + "	CAL_ING.RAMO, "
                + "	CAL_ING.CONCEPTO, "
                + "	CAL_ING.CONSEC "
                + "";

        return strSQL;
    }

    public String getSQLInsertStatusIngresoModificado(long caratula, int year, String ramo, String concepto, String status) {
        String strSQL = ""
                + "INSERT INTO "
                + "	POA.ING_MOD_STATUS "
                + "	( "
                + "		ID_CARATULA, "
                + "		YEAR, "
                + "		RAMO, "
                + "		STATUS "
                + "	 ) "
                + "VALUES "
                + "	( "
                + "		" + caratula + ", "
                + "		" + year + ", "
                + "		'" + ramo + "', "
                + "		'" + status + "' "
                + "	) "
                + "";
        return strSQL;
    }

    public String getSQLExisteStatusModificacionIngreso(long caratula, int year, String ramo, String concepto) {
        String strSQL = ""
                + "SELECT "
                + "	COUNT(1) AS cuenta "
                + "FROM "
                + "	POA.ING_MOD_STATUS ING_MOD_STAT "
                + "WHERE "
                + "	ING_MOD_STAT.YEAR = " + year + " AND "
                + "	ING_MOD_STAT.RAMO = '" + ramo + "' AND "
                + "	ING_MOD_STAT.ID_CARATULA = " + caratula + "  "
                + "";
        return strSQL;
    }

    public String getSQLUpdateStatusIngresoModificado(long caratula, int year, String ramo, String concepto, String status) {
        String strSQL = ""
                + "UPDATE "
                + "	POA.ING_MOD_STATUS ING_MOD_STAT "
                + "SET "
                + "	ING_MOD_STAT.STATUS = '" + status + "' "
                + "WHERE "
                + "	ING_MOD_STAT.YEAR = " + year + " AND "
                + "	ING_MOD_STAT.RAMO = '" + ramo + "' AND "
                + "	ING_MOD_STAT.ID_CARATULA = " + caratula + "  "
                + "";
        return strSQL;
    }

    public String getSQLGetStatusModificacionIngreso(long caratula, int year, String ramo, String concepto) {
        String strSQL = ""
                + "SELECT "
                + "	DECODE "
                + "		( "
                + "			( "
                + "				SELECT "
                + "					ING_MOD_STAT.STATUS "
                + "				FROM "
                + "					POA.ING_MOD_STATUS ING_MOD_STAT "
                + "				WHERE "
                + "					ING_MOD_STAT.YEAR = " + year + " AND "
                + "					ING_MOD_STAT.RAMO = '" + ramo + "' AND "
                + "					ING_MOD_STAT.ID_CARATULA = " + caratula + "  "
                + "			), "
                + "			NULL, "
                + "			'A', "
                + "			( "
                + "				SELECT "
                + "					ING_MOD_STAT.STATUS "
                + "				FROM  "
                + "					POA.ING_MOD_STATUS ING_MOD_STAT "
                + "				WHERE  "
                + "					ING_MOD_STAT.YEAR = " + year + " AND "
                + "					ING_MOD_STAT.RAMO = '" + ramo + "' AND "
                + "					ING_MOD_STAT.ID_CARATULA = " + caratula + "  "
                + "			) "
                + "		) STATUS "
                + "FROM "
                + "	SYS.DUAL "
                + "";
        return strSQL;
    }

    public String getSqlCaratulasTipoSesiones() {
        String strSQL = "SELECT CVE_SESION,DESCR FROM POA.TIPO_SESION";
        return strSQL;
    }

    public String getSqlDisponibleTipoSesion(int year, String ramo, String numSesion, String tipoSesion, long caratula) {
        String strSQL = ""
                + "SELECT "
                + "	DECODE(COUNT(*),1,0,0,1) DISPONIBLE "
                + "FROM "
                + "	POA.CARATULA CAR "
                + "WHERE "
                + "	CAR.YEAR = " + year + " AND "
                + "	CAR.RAMO = '" + ramo + "' AND "
                + "	CAR.NUMERO_SESION = " + numSesion + " AND "
                + "	CAR.TIPO_SESION = " + tipoSesion + " AND "
                + "	CAR.ID_CARATULA <> " + caratula + " "
                + "";
        return strSQL;
    }

    public String getSQLGetArticulosByPartida(int year, String partida) {
        String strSQL = " "
                + "SELECT "
                + "	ART.ARTICULO, "
                + "	ART.DESCR, "
                + "	ART.PARTIDA, "
                + "	ART.SIST "
                + "FROM "
                + "	POA.VW_ARTICULOS ART "
                + "WHERE "
                + "	ART.YEAR = " + year + " AND "
                + "	ART.PARTIDA = '" + partida + "' "
                + "ORDER BY "
                + "	CASE WHEN REGEXP_LIKE(ART.ARTICULO,'^([0-9]+)$') THEN LPAD(ART.ARTICULO,20) ELSE ART.ARTICULO END "
                + "";
        return strSQL;
    }

    public String getSQLIsArticuloPartidas(int year, String partida) {
        String strSQL = "SELECT COUNT(*) AS CONT "
                + "FROM POA.VW_ARTICULOS ARP "
                + "WHERE "
                + "ARP.YEAR = " + year + " AND "
                + "ARP.PARTIDA = '" + partida + "'  ";
        return strSQL;
    }

    public String getSQLGetArticuloDescrByArticulo(int year, String partida, String articuloId) {
        String strSQL = "SELECT ART.DESCR FROM POA.VW_ARTICULOS ART\n"
                + " WHERE ART.YEAR = " + year + " AND "
                + " ART.PARTIDA = '" + partida + "' AND "
                + " ART.ARTICULO = '" + articuloId + "' ";
        return strSQL;
    }

    public String getSQLGetAccionesByFuenteFinanciamiento() {

        String strSQL = "SELECT AR.RAMO, AR.META,M.DESCR AS M_DESCR, AR.ACCION,A.DESCR AS A_DESCR, SUM(AR.COSTO_ANUAL) AS COSTO\n"
                + "FROM POA.ACCION_REQ AR,\n"
                + "    S_POA_META M,\n"
                + "    S_POA_ACCION A\n"
                + "WHERE \n"
                + "    AR.YEAR = ? \n"
                + "    AND AR.RAMO = ? \n"
                + "    AND AR.FUENTE = ? \n"
                + "    AND AR.FONDO = ? \n"
                + "    AND AR.RECURSO = ? \n"
                + "    AND M.YEAR = AR.YEAR\n"
                + "    AND M.RAMO = AR.RAMO\n"
                + "    AND M.META = AR.META\n"
                + "    AND A.YEAR = AR.YEAR\n"
                + "    AND A.RAMO = AR.RAMO\n"
                + "    AND A.META = AR.META\n"
                + "    AND A.ACCION = AR.ACCION\n"
                + "GROUP BY\n"
                + "    AR.RAMO,\n"
                + "    AR.META,\n"
                + "    M.DESCR,\n"
                + "    AR.ACCION,\n"
                + "    A.DESCR\n"
                + "ORDER BY\n"
                + "    AR.META,\n"
                + "    AR.ACCION";
        return strSQL;
    }

    public String getSQLValidarPresXCodigoPrgModif(String ramos, int year) {
        String strSQL = " SELECT SUM(CUENTA) AS CUENTA FROM(\n"
                + " SELECT\n"
                + " COUNT(1) AS CUENTA\n"
                + " FROM\n"
                + " SPP.PPTO PPTO,\n"
                + " DGI.RAMOS RAM,\n"
                + " S_POA_PROGRAMA PRG,\n"
                + " DGI.DEPENDENCIA DEPTO,\n"
                + " DGI.PARTIDA PART,\n"
                + " S_POA_META MET,\n"
                + " S_POA_ACCION ACC,\n"
                + " S_POA_RECURSO RES,\n"
                + " DGI.VW_SP_PROY_ACT PROY\n"
                + " WHERE\n"
                + " PPTO.YEAR = " + year + " AND\n"
                + " PPTO.RAMO IN (" + ramos + ") AND\n"
                + " RAM.YEAR = PPTO.YEAR AND\n"
                + " RAM.RAMO = PPTO.RAMO AND\n"
                + " PRG.YEAR = PPTO.YEAR AND\n"
                + " PRG.PRG = PPTO.PRG AND\n"
                + " DEPTO.YEAR = PPTO.YEAR AND\n"
                + " DEPTO.RAMO = PPTO.RAMO AND\n"
                + " DEPTO.DEPTO = PPTO.DEPTO AND\n"
                + " PART.YEAR = PPTO.YEAR AND\n"
                + " PART.PARTIDA = PPTO.PARTIDA AND\n"
                + " MET.YEAR = PPTO.YEAR AND\n"
                + " MET.RAMO = PPTO.RAMO AND\n"
                + " MET.META = PPTO.META AND\n"
                + " ACC.YEAR = PPTO.YEAR AND\n"
                + " ACC.RAMO = PPTO.RAMO AND\n"
                + " ACC.META = PPTO.META AND\n"
                + " ACC.ACCION = PPTO.ACCION AND\n"
                + " RES.YEAR = PPTO.YEAR AND\n"
                + " RES.FUENTE = PPTO.FUENTE AND\n"
                + " RES.FONDO = PPTO.FONDO AND\n"
                + " RES.RECURSO = PPTO.RECURSO AND\n"
                + " PROY.YEAR = PPTO.YEAR AND\n"
                + " PROY.PROY = PPTO.PROYECTO AND\n"
                + " PROY.TIPO_PROY = PPTO.TIPO_PROY\n"
                + "\n"
                + " UNION ALL\n"
                + "\n"
                + " SELECT\n"
                + " COUNT(1) AS CUENTA\n"
                + " FROM\n"
                + " SPP.VW_PPTO_MOD_AUT PPTO,\n"
                + " DGI.RAMOS RAM,\n"
                + " S_POA_PROGRAMA PRG,\n"
                + " DGI.DEPENDENCIA DEPTO,\n"
                + " DGI.PARTIDA PART,\n"
                + " S_POA_META MET,\n"
                + " S_POA_ACCION ACC,\n"
                + " S_POA_RECURSO RES,\n"
                + " DGI.VW_SP_PROY_ACT PROY\n"
                + " WHERE\n"
                + " PPTO.YEAR =" + year + "  AND\n"
                + " PPTO.RAMO IN (" + ramos + ") AND\n"
                + " RAM.YEAR = PPTO.YEAR AND\n"
                + " RAM.RAMO = PPTO.RAMO AND\n"
                + " PRG.YEAR = PPTO.YEAR AND\n"
                + " PRG.PRG = PPTO.PRG AND\n"
                + " DEPTO.YEAR = PPTO.YEAR AND\n"
                + " DEPTO.RAMO = PPTO.RAMO AND\n"
                + " DEPTO.DEPTO = PPTO.DEPTO AND\n"
                + " PART.YEAR = PPTO.YEAR AND\n"
                + " PART.PARTIDA = PPTO.PARTIDA AND\n"
                + " MET.YEAR = PPTO.YEAR AND\n"
                + " MET.RAMO = PPTO.RAMO AND\n"
                + " MET.META = PPTO.META AND\n"
                + " ACC.YEAR = PPTO.YEAR AND\n"
                + " ACC.RAMO = PPTO.RAMO AND\n"
                + " ACC.META = PPTO.META AND\n"
                + " ACC.ACCION = PPTO.ACCION AND\n"
                + " RES.YEAR = PPTO.YEAR AND\n"
                + " RES.FUENTE = PPTO.FUENTE AND\n"
                + " RES.FONDO = PPTO.FONDO AND\n"
                + " RES.RECURSO = PPTO.RECURSO AND\n"
                + " PROY.YEAR = PPTO.YEAR AND\n"
                + " PROY.PROY = PPTO.PROYECTO AND\n"
                + " PROY.TIPO_PROY = PPTO.TIPO_PROY)";
        return strSQL;
    }

    public String getSQLParametros() {
        String strSQL = " "
                + "SELECT "
                + "	PARAM.REPWEB_IMPFIRMA, "
                + "	PARAM.VALIDA_TRIMESTRE, "
                + "	PARAM.VALIDA_TODOS_TRIMESTRE, "
                + "	PARAM.REPORTE_CIERRE, "
                + "	PARAM.REPVALIDA_INFOCIM "
                + "FROM "
                + "	DGI.PARAMETROS PARAM "
                + " ";
        return strSQL;
    }

    public String getSQLSaveParametros(String repWebImpFirma, String validaTrimestre, String reporteCierre, String repValidaInfoCim, String validaTodosTrimestre) {
        String strSQL = " "
                + "UPDATE "
                + "	DGI.PARAMETROS PARAM "
                + "SET "
                + "	PARAM.REPWEB_IMPFIRMA = '" + repWebImpFirma + "', "
                + "	PARAM.VALIDA_TRIMESTRE = '" + validaTrimestre + "', "
                + "	PARAM.VALIDA_TODOS_TRIMESTRE = '" + validaTodosTrimestre + "', "
                + "	PARAM.REPORTE_CIERRE = '" + reporteCierre + "', "
                + "	PARAM.REPVALIDA_INFOCIM = '" + repValidaInfoCim + "' "
                + " ";
        return strSQL;
    }

    public String getSQLUpdateDeptoPlantilla(int year, String ramo, String prg, int meta, int accion, String depto) {
        String strSQL = "UPDATE \n"
                + "POA.META_ACCION_PLANTILLA MAP\n"
                + "SET \n"
                + "    MAP.DEPTO = '" + depto + "'   \n"
                + "WHERE\n"
                + "    MAP.YEAR = " + year + " \n"
                + "    AND MAP.RAMO = '" + ramo + "' \n"
                + "    AND MAP.PRG = '" + prg + "' \n"
                + "    AND MAP.META = " + meta + " \n"
                + "    AND MAP.ACCION = " + accion + " ";
        return strSQL;
    }

    public String getQueryBDObtieneIndicadoresGeneralesSei(String sYear) {
        String sQuery = ""
                + "SELECT "
                + "    CLAVE_INDICADOR, "
                + "    NOMBRE_INDICADOR, "
                + "    YEAR "
                + "FROM "
                + "    DGI.VW_SEI_INDIC_GRAL "
                + "WHERE "
                + "    YEAR = " + sYear + " AND "
                + "    DIMENSION_ID = 1 "
                + "GROUP BY "
                + "    CLAVE_INDICADOR, "
                + "    NOMBRE_INDICADOR, "
                + "    YEAR "
                + "ORDER BY "
                + "    CLAVE_INDICADOR "
                + "";
        return sQuery;
    }

    public String getQueryBDObtieneSectorRamos(String sYear, String sClaveIndicador) {
        String sQuery = ""
                + "SELECT "
                + "	DISTINCT "
                + "		SECRAM.YEAR, "
                + "		SECRAM.RAMO, "
                + "		RAM.DESCR RAMO_DESCR "
                + "FROM "
                + "	DGI.SECTOR_RAMO SECRAM, "
                + "	DGI.RAMOS RAM "
                + "WHERE "
                + "	SECRAM.OBJ IN ( "
                + "				SELECT "
                + "					DISTINCT "
                + "						SRAM.OBJ "
                + "				FROM "
                + "					DGI.SECTOR_RAMO SRAM "
                + "				WHERE "
                + "					SRAM.YEAR = SECRAM.YEAR AND "
                + "					SRAM.RAMO = SECRAM.RAMO "
                + "			) AND "
                + "	SECRAM.YEAR = " + sYear + " AND "
                + "	RAM.YEAR = SECRAM.YEAR AND "
                + "	RAM.RAMO = SECRAM.RAMO "
                + "ORDER BY "
                + "	SECRAM.RAMO "
                + "";
        return sQuery;
    }

    public String getQueryBDObtieneIndicadorSectorRamos(String sYear, String sClaveIndicador, String sRamo) {
        String sQuery = ""
                + " SELECT   "
                + "     INDICSECRAM.YEAR,  "
                + "     INDICSECRAM.CLAVE_INDICADOR,   "
                + "     INDIC.NOMBRE_INDICADOR,      "
                + "     INDICSECRAM.RAMO,   "
                + "     RAM.DESCR RAMO_DESCR,   "
                + "     INDICSECRAM.PRG,   "
                + "     PRG.DESCR PRG_DESCR   "
                + " FROM    "
                + "     POA.INDICADORES_SECTOR_RAMOS INDICSECRAM,  "
                + "     (  "
                + "         SELECT   "
                + "             CLAVE_INDICADOR,   "
                + "             NOMBRE_INDICADOR,   "
                + "             YEAR   "
                + "         FROM   "
                + "             DGI.VW_SEI_INDIC_GRAL   "
                + "         WHERE   "
                + "             YEAR =     " + sYear + "   AND   "
                + "             CLAVE_INDICADOR =   '" + sClaveIndicador + "'  AND   "
                + "             DIMENSION_ID = 1   "
                + "         GROUP BY    "
                + "             CLAVE_INDICADOR,   "
                + "             NOMBRE_INDICADOR,   "
                + "             YEAR   "
                + "         ORDER BY   "
                + "             CLAVE_INDICADOR   "
                + "     ) INDIC,  "
                + "     DGI.RAMOS RAM, "
                + "     S_POA_PROGRAMA PRG      "
                + " WHERE   "
                + "     INDICSECRAM.YEAR =   " + sYear + "   AND   "
                + "     INDICSECRAM.CLAVE_INDICADOR = '" + sClaveIndicador + "' AND  "
                + "     INDICSECRAM.RAMO = '" + sRamo + "' AND "
                + "     INDIC.YEAR = INDICSECRAM.YEAR AND  "
                + "     INDIC.CLAVE_INDICADOR = INDICSECRAM.CLAVE_INDICADOR AND  "
                + "     RAM.YEAR = INDICSECRAM.YEAR AND  "
                + "     RAM.RAMO = INDICSECRAM.RAMO AND "
                + "     PRG.YEAR = INDICSECRAM.YEAR AND  "
                + "     PRG.PRG = INDICSECRAM.PRG AND "
                + "     PRG.FIN_PRG = 1 "
                + " ORDER BY  "
                + "    INDICSECRAM.RAMO  "
                + "";
        return sQuery;
    }

    public String getQueryCountValidaAccionesInhabilitadasByMeta() {
        String strSQL = "SELECT RAMO, META, ACCION \n"
                + "FROM S_POA_ACCION\n"
                + "WHERE \n"
                + "    YEAR = ?\n"
                + "    AND RAMO = ?\n"
                + "    AND META = ?\n"
                + "    AND PERIODO IS NOT NULL";
        return strSQL;
    }

    public String getSQLGetRamosAsociadosOficioByOficio(int oficio, int year) {
        String strSQL = " "
                + "SELECT "
                + "	R.RAMO, "
                + "	R.DESCR "
                + "FROM "
                + "	( "
                + "		SELECT "
                + "			DISTINCT "
                + "				OFICIO, "
                + "				RAMO "
                + "		FROM "
                + "			POA.MOVOFICIOS_META "
                + "		WHERE "
                + "			OFICIO = " + oficio + " "
                + "		UNION "
                + "		SELECT "
                + "			DISTINCT "
                + "				OFICIO, "
                + "				RAMO "
                + "		FROM "
                + "			POA.MOVOFICIOS_ESTIMACION "
                + "		WHERE "
                + "			OFICIO = " + oficio + " "
                + "		UNION "
                + "		SELECT "
                + "			DISTINCT "
                + "				OFICIO, "
                + "				RAMO "
                + "		FROM "
                + "			POA.MOVOFICIOS_ACCION "
                + "		WHERE "
                + "			OFICIO = " + oficio + " "
                + "		UNION "
                + "		SELECT "
                + "			DISTINCT "
                + "				OFICIO, "
                + "				RAMO "
                + "		FROM "
                + "			POA.MOVOFICIOS_ACCION_ESTIMACION "
                + "		WHERE "
                + "			OFICIO = " + oficio + " "
                + "		UNION "
                + "		SELECT "
                + "			DISTINCT "
                + "				OFICIO, "
                + "				RAMO "
                + "		FROM "
                + "			POA.MOVOFICIOS_ACCION_REQ "
                + "		WHERE "
                + "			OFICIO = " + oficio + " "
                + "		UNION "
                + "		SELECT "
                + "			DISTINCT "
                + "				OFICIO, "
                + "				RAMO "
                + "		FROM "
                + "			SPP.TRANSFERENCIAS "
                + "		WHERE "
                + "			OFICIO = " + oficio + " "
                + "		UNION "
                + "		SELECT "
                + "			DISTINCT "
                + "				OFICIO, "
                + "				RAMO "
                + "		FROM "
                + "			SPP.AMPLIACIONES "
                + "		WHERE "
                + "			OFICIO = " + oficio + " "
                + "		UNION "
                + "		SELECT "
                + "			DISTINCT "
                + "				OFICIO, "
                + "				RAMO "
                + "		FROM "
                + "			SPP.TRANSFREC "
                + "		WHERE "
                + "			OFICIO = " + oficio + " "
                + "	) RO, "
                + "	DGI.RAMOS R "
                + "WHERE "
                + "	R.YEAR = " + year + " AND "
                + "	R.RAMO = RO.RAMO "
                + " ";
        return strSQL;
    }

    public String getSQLGetImporteMovto(int oficio, String tipoMov) {
        String strSQL = "";
        if (tipoMov.equalsIgnoreCase("A")) {
            strSQL = ""
                    + "SELECT "
                    + "	SUM(A.IMPTE) IMPORTE "
                    + "FROM "
                    + "	SPP.AMPLIACIONES A "
                    + "WHERE "
                    + "	A.OFICIO = " + oficio + " "
                    + "";
        } else {
            if (tipoMov.equalsIgnoreCase("T")) {
                strSQL = ""
                        + "SELECT "
                        + "	SUM(A.IMPTE) IMPORTE "
                        + "FROM "
                        + "	SPP.TRANSFERENCIAS A "
                        + "WHERE "
                        + "	A.OFICIO = " + oficio + " "
                        + "";
            } else {
                strSQL = ""
                        + "SELECT "
                        + "	SUM(COSTO_ANUAL) IMPORTE "
                        + "FROM "
                        + "	POA.MOVOFICIOS_ACCION_REQ MER "
                        + "WHERE "
                        + "	MER.OFICIO = " + oficio + " "
                        + "";
            }
        }

        return strSQL;
    }

    public String getCaratulasEvaluacion(){
        String strSQL = "SELECT \n" +
                        "  	CAR.YEAR, \n" +
                        " 	CAR.ID_CARATULA, \n" +
                        "    CAR.RAMO||' '||R.DESCR RDESCR,\n" +
                        " 	NVL(CAR.EVALUACION,'N') EVALUACION, \n" +
                        " 	TO_CHAR(CAR.FECHA_REVISION,'DD/MM/YYYY') FECHA_SESION, \n" +
                        " 	CAR.NUMERO_SESION, \n" +
                        " 	TIS.DESCR TIPOSESIONDESCR, \n" +
                        " 	REVS_NUMSES.DESCR_CORTA||' '||REVS_NUMSES.DESCR AS NUMSESIONDESCR, \n" +
                        "    NVL(REVS_NUMPRES.DESCR_CORTA, ' ')||' '||NVL(REVS_NUMPRES.DESCR, ' ') AS MODPRESUPDESCR, \n" +
                        "    NVL(REVS_NUMPROG.DESCR_CORTA, ' ')||' '||NVL(REVS_NUMPROG.DESCR, ' ') AS MODPROGDESCR \n" +
                        " FROM \n" +
                        " 	POA.CARATULA CAR  \n" +
                        "    INNER JOIN POA.TIPO_SESION TIS ON TIS.CVE_SESION = CAR.TIPO_SESION  \n" +
                        "    INNER JOIN POA.REVISIONES_CARATULA REVS_NUMSES ON REVS_NUMSES.REVISION = CAR.NUMERO_SESION  \n" +
                        "    LEFT OUTER JOIN POA.REVISIONES_CARATULA REVS_NUMPRES ON REVS_NUMPRES.REVISION = CAR.NUM_MOD_PRES  \n" +
                        "    LEFT OUTER JOIN POA.REVISIONES_CARATULA REVS_NUMPROG ON REVS_NUMPROG.REVISION = CAR.NUM_MOD_PROG  \n" +
                        "    INNER JOIN POA.REVISIONES_CARATULA REVS ON REVS.REVISION = CAR.NUMERO_SESION,\n" +
                        "    DGI.RAMOS R,\n" +
                        "    POA.CARATULA_MOVOFICIO CM\n" +
                        " WHERE \n" +
                        " 	CAR.YEAR = ? \n" +
                        "    AND R.YEAR = CAR.YEAR\n" +
                        "    AND R.RAMO = CAR.RAMO\n" +
                        "    AND CM.YEAR = CAR.YEAR\n" +
                        "    AND CM.RAMO = CAR.RAMO\n" +
                        "    AND CM.ID_CARATULA = CAR.ID_CARATULA\n" +
                        " GROUP BY\n" +
                        "    CAR.YEAR, \n" +
                        " 	CAR.ID_CARATULA, \n" +
                        "    CAR.RAMO||' '||R.DESCR,\n" +
                        " 	NVL(CAR.EVALUACION,'N') , \n" +
                        " 	TO_CHAR(CAR.FECHA_REVISION,'DD/MM/YYYY'), \n" +
                        " 	CAR.NUMERO_SESION, \n" +
                        " 	TIS.DESCR, \n" +
                        " 	REVS_NUMSES.DESCR_CORTA||' '||REVS_NUMSES.DESCR , \n" +
                        "    NVL(REVS_NUMPRES.DESCR_CORTA, ' ')||' '||NVL(REVS_NUMPRES.DESCR, ' ') , \n" +
                        "    NVL(REVS_NUMPROG.DESCR_CORTA, ' ')||' '||NVL(REVS_NUMPROG.DESCR, ' ')  \n" +
                        " ORDER BY \n" +
                        " 	CAR.RAMO||' '||R.DESCR,CAR.ID_CARATULA";
        return strSQL;
    }

    public String getSQLGetPonderado() {
        return "SELECT PONDERADO, DESCR FROM DGI.PONDERADO UNION SELECT '0' AS PONDERADO, 'Sin Ponderar' AS DESCR FROM SYS.DUAL";
    }

    public String getSQLtieneProcesoEspecial(){
        return "SELECT \n" +
                "CASE \n" +
                "    WHEN COUNT(1) > 0 \n" +
                "    THEN 'TRUE' \n" +
                "    ELSE 'FALSE' END AS ESPECIAL\n" +
                "FROM POA.USUARIO_PROCESO_ESPECIAL PRE\n" +
                "WHERE PRE.APP_LOGIN = ?\n" +
                "AND PRE.ID_PROCESO_ESPECIAL = ?";
    }

}

