/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.sql;

import gob.gbc.entidades.UsuarioSession;
import gob.gbc.Framework.DataSourceRequest;
import gob.gbc.Framework.KendoGridSort;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ealonso
 */
public class QuerysBDCatalogos extends QueryBDMedio {
    
    // <editor-fold defaultstate="collapsed" desc="CONSULTAS PARA CONTROLES">
    
    public String getSqlObtenerValorParametros() {
        String strQuery = "";
        strQuery = "SELECT VALOR FROM POA.PARAMETROS WHERE OPCION = ? ";

        return strQuery;
    }
    
    public String getSQLConsultaRamoListFiltros(UsuarioSession session, String ramo, String programa, DataSourceRequest options, boolean count) {
        String strQuery = "";
        String strSelect = "SELECT ID, DESCR";
        String strSelectCount = "SELECT COUNT(*)";
        String strIntSelect = " SELECT ";
        String strFrom = " FROM ";
        String strWhere = " WHERE ";
        String strOrder = " ORDER BY ";
        String strGroup = " GROUP BY ";
        int iOrder = 0;
        String strOrientacion = "";        

        strIntSelect += "  "
                + "  RAM.RAMO ID, "
                + "  RAM.RAMO||' - '||RAM.DESCR DESCR, "
                + "  ROW_NUMBER() OVER (ORDER BY %s) rn ";
        
        strFrom += " DGI.RAMOS RAM, POA.VW_ACC_USR_RAMO USR  ";
        
        strWhere += "   RAM.RAMO = USR.RAMO "
                + " AND USR.YEAR = RAM.YEAR "
                + " AND USR.APP_LOGIN = '" + session.getStrUsuario() + "' "
                + " AND RAM.YEAR = " + session.getYear();
        
        strGroup += "  RAM.RAMO, "
                + "     RAM.RAMO||' - '||RAM.DESCR ";

        if (!ramo.isEmpty() && programa.isEmpty()) {
            strWhere += " AND USR.RAMO = " + ramo;
        }

        if (!ramo.isEmpty() && !programa.isEmpty()) {             
            strWhere += "   AND USR.RAMO = " + ramo
                      + "   AND USR.PRG = " + programa;
        }
        
        if (ramo.isEmpty() && !programa.isEmpty()) {
            strWhere += " AND USR.PRG = " + programa;
        }
                
        if(!options.getSearch().isEmpty()){
            //Se comenta esta linea porque siempre empieza con AND ya que hay filtros previos, se deja como referencia
            //strWhere += strWhere.equals(" WHERE ") ? " UPPER(S.DESCRIPCION) LIKE '%" + options.getSearch().toUpperCase()  + "%'" : " AND UPPER(S.DESCRIPCION) LIKE '%" + options.getSearch().toUpperCase() + "%'";
            strWhere += " AND UPPER(RAM.RAMO||' - '||RAM.DESCR) LIKE '%" + options.getSearch().toUpperCase() + "%'";
        }
        
        strIntSelect = String.format(strIntSelect, " RAM.RAMO ASC");
        strOrder = " ORDER BY DESCR ASC"; 

        if (!count) {
            strQuery = String.format("%s FROM (%s) WHERE rn BETWEEN " + ((options.getPage() - 1) * options.getTake()) + " AND " + options.getPage() * options.getTake() + " %s", (count ? strSelectCount : strSelect), strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : "")+ (!strGroup.equals(" GROUP BY ") ? strGroup : ""), strOrder);
        } else {
            strQuery = String.format("%s FROM (%s) %s", (count ? strSelectCount : strSelect), strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : ""), strOrder);
        }
        
        return strQuery;
    }
       
    public String getSQLConsultaProgramaListFiltros(UsuarioSession session, String ramo, String programa, DataSourceRequest options, boolean count) {
        String strQuery = "";
        String strSelect = "SELECT ID, DESCR";
        String strSelectCount = "SELECT COUNT(*)";
        String strIntSelect = " SELECT ";
        String strFrom = " FROM ";
        String strWhere = " WHERE ";
        String strOrder = " ORDER BY ";
        String strGroup = " GROUP BY ";
        int iOrder = 0;
        String strOrientacion = "";        

        strIntSelect += "  "
                + "  RPRO.PRG ID, "
                + "  RPRO.PRG||' - '||PRO.DESCR DESCR, "
                + "  ROW_NUMBER() OVER (ORDER BY %s) rn ";
        
        strFrom += " POA.RAMO_PROGRAMA RPRO, POA.VW_ACC_USR_RAMO USU, S_POA_PROGRAMA PRO  ";
        
        strWhere += "      RPRO.YEAR = USU.YEAR AND"
                + "    RPRO.RAMO = USU.RAMO AND"
                + "    RPRO.PRG = USU.PRG AND"
                + "    USU.YEAR = PRO.YEAR AND"
                + "    USU.PRG = PRO.PRG AND  "
                + "    USU.APP_LOGIN = '" + session.getStrUsuario() + "' AND"
                + "    USU.YEAR =  " + session.getYear();
                        
        strGroup += "  RPRO.PRG, "
                + "    RPRO.PRG||' - '||PRO.DESCR  ";

        if (!ramo.isEmpty() && programa.isEmpty()) {
            strWhere += " AND USU.RAMO = " + ramo;
        }

        if (!ramo.isEmpty() && !programa.isEmpty()) {             
            strWhere += "   AND USU.RAMO = " + ramo
                      + "   AND USU.PRG = " + programa;
        }
        
        if (ramo.isEmpty() && !programa.isEmpty()) {
            strWhere += " AND USU.PRG = " + programa;
        }
                
        if(!options.getSearch().isEmpty()){
            //Se comenta esta linea porque siempre empieza con AND ya que hay filtros previos, se deja como referencia
            //strWhere += strWhere.equals(" WHERE ") ? " UPPER(S.DESCRIPCION) LIKE '%" + options.getSearch().toUpperCase()  + "%'" : " AND UPPER(S.DESCRIPCION) LIKE '%" + options.getSearch().toUpperCase() + "%'";
            strWhere += " AND UPPER(RPRO.PRG ID||' - '||PRO.DESCR) LIKE '%" + options.getSearch().toUpperCase() + "%'";
        }
        
        strIntSelect = String.format(strIntSelect, " RPRO.PRG ASC");
        strOrder = " ORDER BY DESCR ASC"; 

        if (!count) {
            strQuery = String.format("%s FROM (%s) WHERE rn BETWEEN " + ((options.getPage() - 1) * options.getTake()) + " AND " + options.getPage() * options.getTake() + " %s", (count ? strSelectCount : strSelect), strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : "")+ (!strGroup.equals(" GROUP BY ") ? strGroup : ""), strOrder);
        } else {
            strQuery = String.format("%s FROM (%s) %s", (count ? strSelectCount : strSelect), strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : ""), strOrder);
        }
        
        return strQuery;
    }
    
    public String getSQLConsultaGraficaMir( String ramo, String programa){
        String strSQL;
        strSQL =  "SELECT \n" +
                "    CANCELADA, \n" +
                "    BORRADOR, \n" +
                "    ENVIADA, \n" +
                "    RECHAZADA, \n" +
                "    VALIDADA, \n" +
                "    ENVIADA_P, \n" +
                "    RECHAZADA_P, \n" +
                "    VALIDADA_P \n" +
                "FROM \n" +
                "    ( \n" +
                "    SELECT  \n" +
                "        ME.DESCR, \n" +
                "        M.STATUS \n" +
                "    FROM  \n" +
                "        POA.MIR M, \n" +
                "        POA.MIR_ESTATUS ME    \n" +
                "    WHERE\n" +
                "        M.RAMO IN (\n" +
                "                        SELECT \n" +
                "                            RAMO \n" +
                "                        FROM \n" +
                "                            POA.VW_ACC_USR_RAMO USU\n" +
                "                        WHERE \n" +
                "                            USU.YEAR = M.YEAR\n" +
                "                            AND USU.RAMO = M.RAMO\n" +
                "                            AND USU.PRG = M.PRG\n" +
                "                            AND USU.APP_LOGIN = ? \n" +
                "                        )\n" +
                "    \n" +
                "        AND M.YEAR = ? \n";
        if(!ramo.isEmpty())
            strSQL += "        AND M.RAMO = '"+ramo+"' \n";
        if(!programa.isEmpty())
            strSQL += "        AND M.PRG = '"+programa+"'\n";
        strSQL += "        AND ME.ESTATUS = M.STATUS \n" +
                "     ) \n" +
                "PIVOT \n" +
                "    ( \n" +
                "        COUNT(DESCR) \n" +
                "        FOR STATUS IN(0 CANCELADA, 1 BORRADOR, 2 ENVIADA, 3 RECHAZADA, 4 VALIDADA, 5 ENVIADA_P, 6 RECHAZADA_P, 7 VALIDADA_P)\n" +
                "    )";
        return strSQL;
    }
    
    public String getSQLgetStatusMIR(){
        return "SELECT \n" +
                "    STATUS\n" +
                "FROM \n" +
                "    POA.MIR\n" +
                "WHERE \n" +
                "    YEAR = ? \n" +
                "    AND RAMO = ? \n" +
                "    AND PRG = ? ";
    }
    
    public String getSqlEstatusEtapaMIR() {
        String strQuery = ""; 
        strQuery = " SELECT MIR.STATUS, E.ETAPA,"
                + "    CASE WHEN P.FIN_PRG = 1 THEN 1 ELSE 0 END AS politicaPublica "
                + "  FROM POA.MIR MIR,"
                + "     POA.MIR_ESTATUS E,"
                + "     S_POA_PROGRAMA P "
                + "  WHERE MIR.STATUS = E.ESTATUS AND MIR.YEAR = P.YEAR AND MIR.PRG = P.PRG "
                + "     AND MIR.YEAR = ? AND MIR.RAMO = ? AND MIR.PRG = ? AND MIR.STATUS <> 0 ";
        return strQuery;
    }
      
    public String getSQLConsultaFinProposito(){
       return "SELECT \n" +
                "    RP.YEAR,\n" +
                "    RP.RAMO,\n" +
                "    RP.PRG,\n" +
                "    MI.INDICADOR_SEI,\n" +
                "    FIN.DESCR FIN_PROPOSITO,\n" +
                "    MI.DESCR_NOID INDESCR,\n" +
                "    MI.MEDIOS_VERIFICACION,\n" +
                "    MI.SUPUESTO, '1' DIMENSION \n" +
                "FROM \n" +
                "    POA.RAMO_PROGRAMA RP,\n" +
                "    DGI.VW_MIR_INDICADOR MI,\n" +
                "    S_POA_FIN FIN\n" +
                "WHERE \n" +
                "    RP.YEAR = ? \n" +
                "    AND RP.RAMO = ? \n" +
                "    AND RP.PRg = ? \n" +
                "    AND MI.YEAR (+) = RP.YEAR\n" +
                "    AND MI.RAMO (+)= RP.RAMO\n" +
                "    AND MI.PRG  (+)= RP.PRG\n" +
                "    AND MI.DIMENSION (+) = '1'\n" +
                "    AND FIN.YEAR = RP.YEAR\n" +
                "    AND FIN.FIN = RP.FIN\n" +
                "UNION\n" +
                "SELECT RP.YEAR,\n" +
                "    RP.RAMO,\n" +
                "    RP.PRG,\n" +
                "    MI.INDICADOR_SEI,\n" +
                "    RP.PROPOSITO  FIN_PROPOSITO,\n" +
                "    MI.DESCR_NOID INDESCR,\n" +
                "    MI.MEDIOS_VERIFICACION,\n" +
                "    MI.SUPUESTO,\n" +
                "    '2' DIMENSION \n" +
                "FROM \n" +
                "    POA.RAMO_PROGRAMA RP,\n" +
                "    DGI.VW_MIR_INDICADOR MI\n" +
                "WHERE \n" +
                "    RP.YEAR = ? \n" +
                "    AND RP.RAMO = ? \n" +
                "    AND RP.PRg = ? \n" +
                "    AND MI.YEAR (+) = RP.YEAR\n" +
                "    AND MI.RAMO (+) = RP.RAMO\n" +
                "    AND MI.PRG  (+) = RP.PRG\n" +
                "    AND MI.DIMENSION (+) = '2' ORDER BY DIMENSION";
   }
   
    public String getSQLConsultaMIRFiltros(UsuarioSession session, String ramo, String programa, DataSourceRequest options, boolean count) {
        String strQuery = "";
        String strSelect = "SELECT mir, strYear, ramo, ramoDescr, prg, prgDescr, fechaRegistro, statusIniDescr, statusPosDescr, politicaPublica";
        String strSelectCount = "SELECT COUNT(*)";
        String strIntSelect = " SELECT ";
        String strFrom = " FROM ";
        String strWhere = " WHERE ";
        String strOrder = " ORDER BY ";
        String strGroup = " GROUP BY ";
        int iOrder = 0;
        String strOrientacion = "";        

        strIntSelect += "  "
                + "  M.MIR, "
                + "  M.YEAR AS strYear, "
                + "  M.RAMO, "
                + "  R.RAMO||' - '||R.DESCR AS ramoDescr,"
                + "  M.PRG, "
                + "  P.PRG||' - '||P.DESCR AS prgDescr,  "
                + "  M.FECHA_REGISTRO AS fechaRegistro, "
                + "  CASE WHEN TO_NUMBER(M.STATUS) > 4 THEN '4' ELSE M.STATUS END AS statusIniDescr,"
                + "  CASE WHEN TO_NUMBER(M.STATUS) > 4 THEN M.STATUS ELSE '' END AS statusPosDescr,"
                + "  CASE WHEN P.FIN_PRG = 1 THEN 1 ELSE 0 END AS politicaPublica, "
                + "  ROW_NUMBER() OVER (ORDER BY %s) rn ";
        
        strFrom += " POA.MIR M, S_POA_PROGRAMA P, DGI.RAMOS R, POA.VW_ACC_USR_RAMO USU  ";
        
        strWhere += "   M.YEAR = P.YEAR"
                + "    AND M.PRG = P.PRG "
                + "    AND M.YEAR = R.YEAR"
                + "    AND M.RAMO = R.RAMO"
                + "    AND M.STATUS <> 'C'"
                + "    AND M.YEAR = USU.YEAR"
                + "    AND M.RAMO = USU.RAMO"
                + "    AND M.PRG = USU.PRG"
                + "    AND USU.APP_LOGIN = '" + session.getStrUsuario() + "' "
                + "    AND M.YEAR =  " + session.getYear();
                        
       // strGroup += "  ";

        if (!ramo.isEmpty() && programa.isEmpty()) {
            strWhere += " AND M.RAMO  = " + ramo;
        }

        if (!ramo.isEmpty() && !programa.isEmpty()) {             
            strWhere += "   AND M.RAMO  = " + ramo
                      + "   AND M.PRG = " + programa;
        }
        
        if (ramo.isEmpty() && !programa.isEmpty()) {
            strWhere += " AND M.PRG = " + programa;
        }
                
        if(!options.getSearch().isEmpty()){
              strWhere += " AND ( TO_CHAR(M.MIR) LIKE '%" + options.getSearch().toUpperCase() + "%' OR UPPER(R.RAMO||' - '||R.DESCR) LIKE '%" + options.getSearch().toUpperCase() + "%' OR UPPER(P.PRG||' - '||P.DESCR) LIKE '%" + options.getSearch().toUpperCase() + "%' )"; 
        }

        strIntSelect = String.format(strIntSelect, " M.RAMO ASC");
        
        if (options.getSort() != null) {
            if (options.getSort().size() > 0) {
                strOrder = " ORDER BY \n";
                int x = 0;
                for (KendoGridSort sort : options.getSort()) {
                    if (x > 0) {
                        strOrder += ",\n";
                    }
                    strOrder += sort.getField() + " " + sort.getDir();
                    x++;
                }
            } else {
                strOrder = " ORDER BY RAMO ASC, PRG ASC\n";
            }
        }
        
        if (!count) {
            strQuery = String.format("%s FROM (%s) WHERE rn BETWEEN " + (options.getSkip()+1) + " AND " + ((options.getSkip())+options.getTake()) + " %s", (count ? strSelectCount : strSelect), strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : "")+ (!strGroup.equals(" GROUP BY ") ? strGroup : ""), strOrder);
        } else {
            strQuery = String.format("%s FROM (%s) %s", (count ? strSelectCount : strSelect), strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : ""), strOrder);
        }
        
        return strQuery;
    }
    
    public String getSQLConsultaComponenteFiltros(UsuarioSession session, String ramo, String programa, DataSourceRequest options, boolean count) {
        String strQuery = "";
        String strSelect = "SELECT YEAR, RAMO, PRG, RENGLON, COMPONENTE, DESCR, META, INDICADORSEI, INDICADORES, MEDIOS, SUPUESTOS";
        String strSelectCount = "SELECT COUNT(*)";
        String strIntSelect = " SELECT ";
        String strFrom = " FROM ";
        String strWhere = " WHERE ";
        String strOrder = " ORDER BY ";
        String strGroup = " GROUP BY ";
        int iOrder = 0;
        String strOrientacion = "";

        strIntSelect += "  "
                + "  M.YEAR, M.RAMO, M.PRG, 'C'||ROW_NUMBER() OVER (ORDER BY  M.COMPONENTE ASC) ||'.' AS RENGLON, M.COMPONENTE, M.DESCR, M.META,"
                + " ICOM.INDICADOR_SEI AS INDICADORSEI,"
                + " ICOM.DESCR AS INDICADORES, "
                + " ICOM.MEDIOS_VERIFICACION AS MEDIOS, "
                + " ICOM.SUPUESTO AS SUPUESTOS, "
                + "  ROW_NUMBER() OVER (ORDER BY %s) rn ";

        strFrom += " POA.MIR_COMPONENTE M "
                + " LEFT JOIN DGI.VW_MIR_INDICADOR ICOM "
                + " ON ICOM.YEAR = M.YEAR AND ICOM.RAMO = M.RAMO AND ICOM.PRG = M.PRG AND ICOM.COMPONENTE = M.COMPONENTE AND ICOM.DIMENSION = 3 ";

        strWhere += "   M.YEAR =  " + session.getYear()
                + "    AND M.RAMO = " + ramo
                + "    AND M.PRG = " + programa;

        if (!options.getSearch().isEmpty()) {
            //Se comenta esta linea porque siempre empieza con AND ya que hay filtros previos, se deja como referencia
            //strWhere += strWhere.equals(" WHERE ") ? " UPPER(S.DESCRIPCION) LIKE '%" + options.getSearch().toUpperCase()  + "%'" : " AND UPPER(S.DESCRIPCION) LIKE '%" + options.getSearch().toUpperCase() + "%'";
            strWhere += " AND (  UPPER(M.DESCR) LIKE '%" + options.getSearch().toUpperCase() + "%' )";
            //    strWhere += " AND ( TO_CHAR(M.MIR) LIKE '%" + options.getSearch().toUpperCase() + "%' OR UPPER(P.PRG||' - '||P.DESCR) LIKE '%" + options.getSearch().toUpperCase() + "%' )";
        }

        strIntSelect = String.format(strIntSelect, " M.COMPONENTE ASC");

        if (options.getSort() != null) {
            if (options.getSort().size() > 0) {
                strOrder = " ORDER BY \n";
                int x = 0;
                for (KendoGridSort sort : options.getSort()) {
                    if (x > 0) {
                        strOrder += ",\n";
                    }
                    strOrder += sort.getField().toUpperCase() + " " + sort.getDir().toUpperCase();
                    x++;
                }
            } else {
                strOrder = " ORDER BY COMPONENTE ASC\n";
            }
        }

        if (!count) {
            strQuery = String.format("%s FROM (%s) WHERE rn BETWEEN " + (options.getSkip() + 1) + " AND " + ((options.getSkip()) + options.getTake()) + " %s", strSelect, strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : "") + (!strGroup.equals(" GROUP BY ") ? strGroup : ""), strOrder);
        } else {
            strQuery = String.format("%s FROM (%s) %s", (count ? strSelectCount : strSelect), strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : ""), strOrder);
        }

        return strQuery;
    }

    public String getSQLConsultaActividadFiltros(UsuarioSession session, String ramo, String programa, int componente, DataSourceRequest options, boolean count) {
        String strQuery = "";
        String strSelect = "SELECT YEAR, RAMO, PRG, RENGLON, COMPONENTE, ACTIVIDAD, DESCR, META, ACCION, INDICADORSEI, INDICADORES, MEDIOS, SUPUESTOS";
        String strSelectCount = "SELECT COUNT(*)";
        String strIntSelect = " SELECT ";
        String strFrom = " FROM ";
        String strWhere = " WHERE ";
        String strOrder = " ORDER BY ";
        String strGroup = " GROUP BY ";
        int iOrder = 0;
        String strOrientacion = "";

        strIntSelect += "  "
                + "  M.YEAR, M.RAMO, M.PRG, C.COMPONENTEROW||'A'||ROW_NUMBER() OVER (ORDER BY  M.ACTIVIDAD ASC) ||'.' AS RENGLON, M.COMPONENTE, M.ACTIVIDAD, M.DESCR, M.META, M.ACCION, IACT.INDICADOR_SEI AS INDICADORSEI, IACT.DESCR AS INDICADORES, IACT.MEDIOS_VERIFICACION AS MEDIOS, IACT.SUPUESTO AS SUPUESTOS, "
                + "  ROW_NUMBER() OVER (ORDER BY %s) rn ";

        strFrom += " POA.MIR_ACTIVIDAD M"
                + " INNER JOIN               "
                + "     (                "
                + "         SELECT     M.YEAR, M.RAMO, M.PRG, 'C'||ROW_NUMBER() OVER (ORDER BY  M.COMPONENTE ASC) AS COMPONENTEROW, M.COMPONENTE                  "
                + "         FROM  POA.MIR_COMPONENTE M                   WHERE    M.YEAR = " + session.getYear()
                + "                    AND M.RAMO = " + ramo
                + "                    AND M.PRG = " + programa
                + "     ) C   ON M.YEAR = C.YEAR AND M.RAMO = C.RAMO AND M.PRG = C.PRG AND M.COMPONENTE = C.COMPONENTE "
                + " LEFT JOIN DGI.VW_MIR_INDICADOR IACT  ON IACT.YEAR = M.YEAR AND IACT.RAMO = M.RAMO AND IACT.PRG = M.PRG AND IACT.COMPONENTE = M.COMPONENTE AND IACT.ACTIVIDAD = M.ACTIVIDAD AND IACT.DIMENSION = 4";

        strWhere += "   M.YEAR =  " + session.getYear()
                + "    AND M.RAMO = " + ramo
                + "    AND M.PRG = " + programa
                + "    AND M.COMPONENTE = " + componente
                + "    AND M.YEAR = C.YEAR AND M.RAMO = C.RAMO AND M.PRG = C.PRG AND M.COMPONENTE = C.COMPONENTE ";

        if (!options.getSearch().isEmpty()) {
            //Se comenta esta linea porque siempre empieza con AND ya que hay filtros previos, se deja como referencia
            //strWhere += strWhere.equals(" WHERE ") ? " UPPER(S.DESCRIPCION) LIKE '%" + options.getSearch().toUpperCase()  + "%'" : " AND UPPER(S.DESCRIPCION) LIKE '%" + options.getSearch().toUpperCase() + "%'";
            strWhere += " AND (  UPPER(M.DESCR) LIKE '%" + options.getSearch().toUpperCase() + "%' OR UPPER(C.COMPONENTEROW||'A'||ROW_NUMBER() OVER (ORDER BY  M.ACTIVIDAD ASC) ||'.') LIKE '%" + options.getSearch().toUpperCase() + "%')";
        }

        strIntSelect = String.format(strIntSelect, " M.ACTIVIDAD ASC");

        if (options.getSort() != null) {
            if (options.getSort().size() > 0) {
                strOrder = " ORDER BY \n";
                int x = 0;
                for (KendoGridSort sort : options.getSort()) {
                    if (x > 0) {
                        strOrder += ",\n";
                    }
                    strOrder += sort.getField().toUpperCase() + " " + sort.getDir().toUpperCase();
                    x++;
                }
            } else {
                strOrder = " ORDER BY ACTIVIDAD ASC\n";
            }
        }

        if (!count) {
            strQuery = String.format("%s FROM (%s) WHERE rn BETWEEN " + (options.getSkip() + 1) + " AND " + ((options.getSkip()) + options.getTake()) + " %s", strSelect, strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : "") + (!strGroup.equals(" GROUP BY ") ? strGroup : ""), strOrder);
        } else {
            strQuery = String.format("%s FROM (%s) %s", (count ? strSelectCount : strSelect), strIntSelect + strFrom + (!strWhere.equals(" WHERE ") ? strWhere : ""), strOrder);
        }

        return strQuery;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="MANTENIMIENTOS">
    // <editor-fold defaultstate="collapsed" desc="MIR">
   public String getSqlExisteMIR() {
        String strQuery = "";
        strQuery = " SELECT COUNT(PRG) EXISTE FROM POA.MIR "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ? AND STATUS <> 0 ";
        return strQuery;
    }
           
    public String getSqlInsertMIR() {
        String strQuery = "";
        strQuery = " INSERT INTO POA.MIR (YEAR, RAMO, PRG, MIR, FECHA_REGISTRO, STATUS) "
                + " VALUES (?,?,?,POA.SEQ_MIR.NEXTVAL,SYSDATE,1) ";
        return strQuery;
    }
    
    public String getSqlActualizarMIR() {
        String strQuery = "";
        strQuery = " UPDATE POA.MIR SET PRG = ?  "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ?  AND STATUS <> 0 ";
        return strQuery;
    }
    
    public String getSqlBorrarMIR() {
        String strQuery = "";
        strQuery = " UPDATE POA.MIR SET STATUS = 0  "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ? ";
        return strQuery;
    }
    public String getSQLValidaPropositoMIR(){
        String strQuery = "";
        strQuery = " SELECT PROPOSITO "
                + "    FROM POA.RAMO_PROGRAMA "
                + "    WHERE YEAR = ? AND RAMO = ? AND PRG = ?  ";
        return strQuery;
    }
        // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="COMPONENTE">     
    public String getSqlInsertComponente() {
        String strQuery = "";
        strQuery = " INSERT INTO POA.MIR_COMPONENTE (YEAR, RAMO, PRG, COMPONENTE, DESCR) "
                + " VALUES (?,?,?,POA.SEQ_MIR_COMPONENTE.NEXTVAL,UPPER(?)) ";
        return strQuery;
    }
    
    public String getSqlActualizarComponente() {
        String strQuery = "";
        strQuery = " UPDATE POA.MIR_COMPONENTE SET DESCR = UPPER(?)  "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ?  AND COMPONENTE = ? ";
        return strQuery;
    }
    
    public String getSqlActualizarDescrMeta() {
        String strQuery = "";
        strQuery = " UPDATE POA.META SET DESCR = UPPER(?)  "
                + " WHERE YEAR = ? AND RAMO = ? AND META = ? ";
        return strQuery;
    }
    
    public String getSqlBorrarComponente() {
        String strQuery = "";
        strQuery = " DELETE FROM POA.MIR_COMPONENTE "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ? AND COMPONENTE = ? ";
        return strQuery;
    }
    
    public String getSqlTieneHijosComponente() {
        String strQuery = "";
        strQuery = " SELECT COUNT(ACTIVIDAD) EXISTE FROM POA.MIR_ACTIVIDAD "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ? AND COMPONENTE = ? ";
        return strQuery;
    }
        // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="ACTIVIDAD">     
    public String getSqlInsertActividad() {
        String strQuery = "";
        strQuery = " INSERT INTO POA.MIR_ACTIVIDAD (YEAR, RAMO, PRG, COMPONENTE, ACTIVIDAD, DESCR) "
                + " VALUES (?,?,?,?,POA.SEQ_MIR_ACTIVIDAD.NEXTVAL,UPPER(?)) ";
        return strQuery;
    }
    
    public String getSqlActualizarActividad() {
        String strQuery = "";
        strQuery = " UPDATE POA.MIR_ACTIVIDAD SET DESCR = UPPER(?)  "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ?  AND COMPONENTE = ? AND ACTIVIDAD = ? ";
        return strQuery;
    }
    
    public String getSqlActualizarDescrAccion() {
        String strQuery = "";
        strQuery = " UPDATE POA.ACCION SET DESCR = UPPER(?)  "
                + " WHERE YEAR = ? AND RAMO = ? AND META = ? AND ACCION = ? ";
        return strQuery;
    }
    
    public String getSqlBorrarActividad() {
        String strQuery = "";
        strQuery = " DELETE FROM POA.MIR_ACTIVIDAD "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ? AND COMPONENTE = ? AND ACTIVIDAD = ? ";
        return strQuery;
    }
        // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="INDICADOR">         
    public String getInformacionIndicador(){
        return "SELECT \n" +
                "    MI.NOMBRE_INDICADOR,\n" +
                "    MI.FUENTE_DATOS\n" +
                "FROM\n" +
                "    DGI.VW_SEI_INDIC_GRAL MI\n" +
                "WHERE \n" +
                "    MI.YEAR = ? \n" +
                "    AND MI.RAMO = ? \n" +
                "    AND MI.PROG = ? \n" +
                "    AND MI.CLAVE_INDICADOR = ? ";
    }
    
    public String insertMIRIndicadorFinProposito(){
        return "INSERT INTO \n" +
                "    POA.MIR_INDICADOR MI (MI.YEAR, MI.RAMO, MI.PRG, MI.INDICADOR,MI.DESCR, MI.DIMENSION, MI.SUPUESTO, MI.MEDIOS_VERIFICACION, MI.INDICADOR_SEI, FECHA)\n" +
                "VALUES(?,?,?,(SELECT NVL(MAX(INDICADOR)+1,0) FROM POA.MIR_INDICADOR),?,?,?,?,?,SYSDATE)";
    }
    
    public String insertMIRIndicadorComponente(){
        return "INSERT INTO \n" +
                "    POA.MIR_INDICADOR MI (MI.YEAR, MI.RAMO, MI.PRG, MI.INDICADOR,MI.DESCR, MI.DIMENSION, MI.SUPUESTO, MI.MEDIOS_VERIFICACION, MI.INDICADOR_SEI, FECHA,COMPONENTE)\n" +
                "VALUES(?,?,?,(SELECT NVL(MAX(INDICADOR)+1,0) FROM POA.MIR_INDICADOR),?,?,?,?,?,SYSDATE,?)";
    }
    
    public String insertMIRIndicadorActividad(){
        return "INSERT INTO \n" +
                "    POA.MIR_INDICADOR MI (MI.YEAR, MI.RAMO, MI.PRG, MI.INDICADOR,MI.DESCR, MI.DIMENSION, MI.SUPUESTO, MI.MEDIOS_VERIFICACION, MI.INDICADOR_SEI, FECHA,COMPONENTE,ACTIVIDAD)\n" +
                "VALUES(?,?,?,(SELECT NVL(MAX(INDICADOR)+1,0) FROM POA.MIR_INDICADOR),?,?,?,?,?,SYSDATE,?,?)";
    }
        
    public String existeIndicadorFinProposito(){
        return "SELECT \n" +
                "    CASE WHEN COUNT(1) > 0 THEN '1' ELSE '0' END INDICADOR\n" +
                "FROM \n" +
                "    POA.MIR_INDICADOR\n" +
                "WHERE YEAR = ? \n" +
                "    AND RAMO = ? \n" +
                "    AND PRG = ? \n" +
                "    AND DIMENSION = ? ";
    }
    
     public String existeIndicadorComponente(){
        return "SELECT \n" +
                "    CASE WHEN COUNT(1) > 0 THEN '1' ELSE '0' END INDICADOR\n" +
                "FROM \n" +
                "    POA.MIR_INDICADOR \n" +
                "WHERE YEAR = ? \n" +
                "    AND RAMO = ? \n" +
                "    AND PRG = ? \n" +
                "    AND DIMENSION = ? \n" +
                "    AND COMPONENTE = ? ";
    }
     
    public String existeIndicadorActividad(){
        return "SELECT \n" +
                "    CASE WHEN COUNT(1) > 0 THEN '1' ELSE '0' END INDICADOR \n" +
                "FROM \n" +
                "    POA.MIR_INDICADOR \n" +
                "WHERE YEAR = ? \n" +
                "    AND RAMO = ? \n" +
                "    AND PRG = ? \n" +
                "    AND DIMENSION = ?\n" +
                "    AND COMPONENTE = ?\n" +
                "    AND ACTIVIDAD = ? ";
    }
    
    public String updateIndicadorFinProposito(){
        
        return "UPDATE POA.MIR_INDICADOR MI\n" +
                "SET \n" +
                "    MI.DESCR = ?,\n" +
                "    MI.MEDIOS_VERIFICACION = ?,\n" +
                "    MI.SUPUESTO = ?,\n" + 
                "    MI.INDICADOR_SEI = ?\n" +
                "WHERE\n" +
                "    MI.YEAR = ?\n" +
                "    AND MI.RAMO = ?\n" +
                "    AND MI.PRG = ?\n" +
                "    AND MI.DIMENSION = ? ";
    }
    
    public String updateIndicadorComponente(){
        
        return "UPDATE POA.MIR_INDICADOR MI\n" +
                "SET \n" +
                "    MI.DESCR = ?,\n" +
                "    MI.MEDIOS_VERIFICACION = ?,\n" +
                "    MI.SUPUESTO = ?,\n" + 
                "    MI.INDICADOR_SEI = ?\n" +
                "WHERE\n" +
                "    MI.YEAR = ?\n" +
                "    AND MI.RAMO = ?\n" +
                "    AND MI.PRG = ?\n" +
                "    AND MI.DIMENSION = ?\n" +
                "    AND MI.COMPONENTE = ? ";
    }
    
    public String updateIndicadorActividad(){
        
        return "UPDATE POA.MIR_INDICADOR MI\n" +
                "SET \n" +
                "    MI.DESCR = ?,\n" +
                "    MI.MEDIOS_VERIFICACION = ?,\n" +
                "    MI.SUPUESTO = ?,\n" + 
                "    MI.INDICADOR_SEI = ?\n" +
                "WHERE\n" +
                "    MI.YEAR = ?\n" +
                "    AND MI.RAMO = ?\n" +
                "    AND MI.PRG = ?\n" +
                "    AND MI.DIMENSION = ?\n" +
                "    AND MI.COMPONENTE = ?\n" +
                "    AND MI.ACTIVIDAD = ? ";
    }
    
    public String getIndicadoresList(){
        return  " SELECT\n" +
                "    SIG.CLAVE_INDICADOR, SIG.CLAVE_INDICADOR||' '||SIG.NOMBRE_INDICADOR NOMBRE_INDICADOR \n" +
                " FROM\n" +
                "    DGI.VW_SEI_INDIC_GRAL SIG \n" +
                " WHERE\n" +         
                "    SIG.YEAR = ? \n" +
                "    AND SIG.RAMO = ? \n" +
                "    AND SIG.PROG = ? \n" +
                "    AND SIG.DIMENSION_ID = ? \n"
                //PARA EXCLUIR LOS INDICADORES QUE YA ESTAN EN USO, PERO ENTONCES NO SE VE EL VALOR SELECCIONADO EN EL COMBO, PLOP
//                + "     AND SIG.CLAVE_INDICADOR NOT IN ( \n"
//                + "             SELECT \n"
//                + "                 MI.INDICADOR_SEI \n"
//                + "             FROM \n"
//                + "                 POA.MIR_INDICADOR MI \n"
//                + "             WHERE \n"
//                + "                 MI.YEAR = ? \n"
//                + "                 AND MI.RAMO = ? \n"
//                + "                 AND MI.PRG = ? \n"
//                + "                 AND MI.DIMENSION = ? \n"
//                + "                 AND (MI.INDICADOR_SEI IS NOT NULL OR MI.INDICADOR_SEI != '') \n"
//                + "         ) \n"
                + " ";
    }
    // </editor-fold>
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="AUTORIZACION MIR">   
    public String getSqlEstatusMIR() {
        String strQuery = "";
        strQuery = " SELECT STATUS FROM POA.MIR "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ? AND STATUS <> 0 ";
        return strQuery;
    }
    public String getSqlActualizarEstatusMIR() {
        String strQuery = "";
        strQuery = " UPDATE POA.MIR SET STATUS = ?  "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ?  AND STATUS <> 0 ";
        return strQuery;
    }
    public String getSqlComponentes() {
        String strQuery = "";
        strQuery = " SELECT  YEAR, RAMO, PRG, COMPONENTE, DESCR, META  "
                + " FROM POA.MIR_COMPONENTE "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ? ";
        return strQuery;
    }
    public String getSqlActividades() {
        String strQuery = "";
        strQuery = " SELECT  COUNT(*)  "
                + " FROM POA.MIR_ACTIVIDAD "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ? AND COMPONENTE = ? ";
        return strQuery;
    }
    public String getSQLValidaPropositoFinProblemaMIR(){
        String strQuery = "";
        strQuery = " SELECT PROPOSITO, FIN, PROBLEMA_FOCAL "
                + "    FROM POA.RAMO_PROGRAMA "
                + "    WHERE YEAR = ? AND RAMO = ? AND PRG = ?  "; 
        return strQuery;
    }
    public String getSQLRegistraObsRechazoMIR(){
        String strQuery = "";
        strQuery = " INSERT INTO POA.MIR_OBSERVACION(YEAR, RAMO, PRG, FECHA, OBSERVACION) "
                + "    VALUES(?,?,?,SYSDATE,UPPER(?)) ";
        return strQuery;
    }
    public String getSQLGetObsRechazoMIR(){
        String strQuery = "";
        strQuery = " SELECT OBSERVACION FROM POA.MIR_OBSERVACION "
                + " WHERE YEAR = ? AND RAMO = ? AND PRG = ? "
                + " ORDER BY FECHA DESC ";
        return strQuery;
    }
    public String getSQLValidaDimensionFinProposito(){
        return  "SELECT \n" +
                "    CASE WHEN \n" +
                "    COUNT(1) > 1 \n" +
                "    THEN '1' \n" +
                "    ELSE '0' END INDICADOR\n" +
                "FROM\n" +
                "    POA.MIR_INDICADOR MI\n" +
                "WHERE \n" +
                "    MI.YEAR = ? \n" +
                "    AND MI.RAMO = ? \n" +
                "    AND MI.PRG = ? \n" +
                "    AND MI.DIMENSION IN (1,2) ";
    }
    public String getSQLValidaDimensionComponente(){
        return "SELECT CASE WHEN COUNT(1) = 0\n" +
                " THEN '1' ELSE '0' END COMPONENTE\n" +
                " FROM\n" +
                " (\n" +
                "    SELECT \n" +
                "        MI.YEAR,MI.RAMO,MI.PRG,MI.COMPONENTE\n" +
                "    FROM\n" +
                "        POA.MIR_COMPONENTE MI\n" +
                "    WHERE \n" +
                "        MI.YEAR = ? \n" +
                "        AND MI.RAMO = ? \n" +
                "        AND MI.PRG = ? \n" +
                "    MINUS    \n" +
                "    SELECT \n" +
                "        MI.YEAR,MI.RAMO,MI.PRG,MI.COMPONENTE\n" +
                "    FROM\n" +
                "        POA.MIR_INDICADOR MI\n" +
                "    WHERE \n" +
                "        MI.YEAR = ? \n" +
                "        AND MI.RAMO = ? \n" +
                "        AND MI.PRG = ? \n" +
                "        AND MI.DIMENSION = '3'\n" +
                ")";
    }
    public String getSQLValidaDimensionActividad(){
        return "SELECT CASE WHEN COUNT(1) = 0\n" +
                " THEN '1' ELSE '0' END ACTIVIDAD\n" +
                " FROM\n" +
                " (\n" +
                "    SELECT \n" +
                "        MI.YEAR,MI.RAMO,MI.PRG,MI.COMPONENTE,MI.ACTIVIDAD\n" +
                "    FROM\n" +
                "        POA.MIR_ACTIVIDAD MI\n" +
                "    WHERE \n" +
                "        MI.YEAR = ? \n" +
                "        AND MI.RAMO = ? \n" +
                "        AND MI.PRG = ? \n" +
                "    MINUS    \n" +
                "    SELECT \n" +
                "        MI.YEAR,MI.RAMO,MI.PRG,MI.COMPONENTE, MI.ACTIVIDAD\n" +
                "    FROM\n" +
                "        POA.MIR_INDICADOR MI\n" +
                "    WHERE \n" +
                "        MI.YEAR = ? \n" +
                "        AND MI.RAMO = ? \n" +
                "        AND MI.PRG = ? \n" +
                "        AND MI.DIMENSION = '4'\n" +
                ")";
    }
    public String getSQLValidaDatosIndicadorMIR(){
        return "SELECT MI.YEAR, MI.RAMO, MI.PRG, MI.INDICADOR\n" +
                "FROM \n" +
                "    POA.MIR_INDICADOR MI\n" +
                "WHERE \n" +
                "    MI.YEAR = ? \n" +
                "    AND MI.RAMO = ? \n" +
                "    AND MI.PRG = ? \n" +
                "    AND \n" +
                "    (MI.DESCR IS NULL \n" +
                "        OR MI.MEDIOS_VERIFICACION IS NULL\n" +
                "        OR MI.SUPUESTO IS NULL)";
    }
    public String getSQLValidaDatosIndicadorSEIMIR(){
        return "SELECT CASE WHEN COUNT(1) > 0 THEN '1' ELSE '0' END INDICADOR\n" +
                "    FROM \n" +
                "    POA.MIR_INDICADOR MI\n" +
                "WHERE \n" +
                "    MI.YEAR = ? \n" +
                "    AND MI.RAMO = ? \n" +
                "    AND MI.PRG = ? \n" +
                "    AND MI.INDICADOR = ? \n" +
                "    AND MI.SUPUESTO IS NOT NULL\n" +
                "    AND MI.INDICADOR_SEI IS NOT NULL";
    }
         // </editor-fold>
}
