/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.sql;

import gob.gbc.entidades.AccionReq;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.MovOficiosAccionReq;
import gob.gbc.entidades.Requerimiento;

/**
 *
 * @author ugarcia
 */
public class QueryBDMedio {

    public String getSQLGetCountLineaAccionMeta(int year, String ramo, String programa, String linea) {
        String strSQL = " SELECT COUNT(M.LINEA) AS CONT_L FROM S_POA_META M "
                + "WHERE M.YEAR = " + year + " AND "
                + "      M.RAMO = '" + ramo + "' AND "
                + "    M.PRG = '" + programa + "' AND "
                + "      M.LINEA = '" + linea + "'";
        return strSQL;
    }

    public String getSQLGetCountLineaAccionAccion(int year, String ramo, String programa, String linea) {
        String strSQL = "SELECT COUNT(M.LINEA) AS CONT_L FROM S_POA_META M "
                + "WHERE M.YEAR = " + year + " AND "
                + "      M.RAMO = '" + ramo + "' AND "
                + "      M.PRG = '" + programa + "' AND "
                + "      M.LINEA = '" + linea + "'  ";
        return strSQL;
    }

    public String getSQLValidarReportePptoCalAnualCodigo(String ramos, int year) {
        String strSQL = "SELECT "
                + "	NVL(COUNT(*),0) AS cuenta\n"
                + "FROM\n"
                + "	SPP.VW_PRESUP_ASIG_MEN VPAM,\n"
                + "	DGI.RAMOS R,\n"
                + "	S_POA_PROGRAMA P,\n"
                + "	DGI.DEPENDENCIA D,\n"
                + "	S_POA_VW_SP_PROY_ACT VSPPA,\n"
                + "	S_POA_META M,\n"
                + "	S_POA_ACCION ACC,\n"
                + "	DGI.PARTIDA PART,\n"
                + "	S_DGI_GRUPOS G,\n"
                + "	S_DGI_SUBGRUPOS SG,\n"
                + "	S_DGI_SUBSUBGPO SSG,\n"
                + "	S_POA_FUENTE FU,\n"
                + "	S_POA_FONDO FO,\n"
                + "	S_POA_RECURSO RE,\n"
                + "	S_POA_REL_LABORAL REL,\n"
                + "	S_DGI_PARAM_LONG_COD PLC,\n"
                + "	DGI.PARAMETROS PRAM\n"
                + "\n"
                + "WHERE\n"
                + "	VPAM.RAMO IN(" + ramos + ") AND\n"
                + "	VPAM.YEAR = " + year + " AND\n"
                + "	R.YEAR = VPAM.YEAR AND\n"
                + "	R.RAMO = VPAM.RAMO AND\n"
                + "	P.YEAR = VPAM.YEAR AND\n"
                + "	P.PRG = VPAM.PRG AND\n"
                + "	D.YEAR = VPAM.YEAR AND\n"
                + "	D.RAMO = VPAM.RAMO AND\n"
                + "	D.DEPTO = VPAM.DEPTO AND\n"
                + "	VSPPA.YEAR = VPAM.YEAR AND\n"
                + "	VSPPA.TIPO_PROY = VPAM.TIPO_PROY AND\n"
                + "	VSPPA.PROY = VPAM.PROYECTO AND\n"
                + "	M.YEAR = VPAM.YEAR AND\n"
                + "	M.RAMO = VPAM.RAMO AND\n"
                + "	M.META = VPAM.META AND\n"
                + "	ACC.YEAR = VPAM.YEAR AND\n"
                + "	ACC.RAMO = VPAM.RAMO AND\n"
                + "	ACC.META = VPAM.META AND\n"
                + "	ACC.ACCION = VPAM.ACCION AND\n"
                + "	PART.YEAR = VPAM.YEAR AND\n"
                + "	PART.PARTIDA = VPAM.PARTIDA AND\n"
                + "	SSG.YEAR = PART.YEAR AND\n"
                + "	SSG.SUBSUBGRUPO = PART.SUBSUBGPO AND\n"
                + "	SG.YEAR = SSG.YEAR AND\n"
                + "	SG.SUBGRUPO = SSG.SUBGRUPO AND\n"
                + "	G.YEAR = SG.YEAR AND\n"
                + "	G.GRUPO = SG.GRUPO AND\n"
                + "	FU.YEAR = VPAM.YEAR AND\n"
                + "	FU.FUENTE = VPAM.FUENTE AND\n"
                + "	FO.YEAR = VPAM.YEAR AND\n"
                + "	FO.FUENTE = VPAM.FUENTE AND\n"
                + "	FO.FONDO = VPAM.FONDO AND\n"
                + "	RE.YEAR = VPAM.YEAR AND\n"
                + "	RE.FUENTE = VPAM.FUENTE  AND\n"
                + "	RE.FONDO = VPAM.FONDO AND\n"
                + "	RE.RECURSO = VPAM.RECURSO AND\n"
                + "	REL.YEAR (+)= VPAM.YEAR AND\n"
                + "	REL.REL_LABORAL (+)= VPAM.REL_LABORAL AND "
                + "	PLC.YEAR = VPAM.YEAR ";
        return strSQL;
    }

    public String getSQLValidarReportePptoCalAnualRamoPartida(String ramos, int year) {
        String strSQL = ""
                + "SELECT nvl(max(count(*)),0) AS CUENTA "
                + "FROM "
                + "	SPP.VW_PRESUP_ASIG_MEN VPAM, "
                + "	DGI.RAMOS R, "
                + "	DGI.PARTIDA PART, "
                + "	S_DGI_GRUPOS G, "
                + "	S_DGI_SUBGRUPOS SG, "
                + "	S_DGI_SUBSUBGPO SSG, "
                + "	DGI.PARAMETROS PRAM "
                + "WHERE "
                + "	VPAM.RAMO IN (" + ramos + ")" + " AND "
                + "	VPAM.YEAR = " + year + " AND "
                + "	VPAM.RAMO = R.RAMO AND "
                + "	VPAM.YEAR = R.YEAR AND "
                + "	VPAM.PARTIDA = PART.PARTIDA AND "
                + "	VPAM.YEAR = PART.YEAR AND "
                + "	PART.SUBSUBGPO = SSG.SUBSUBGRUPO AND "
                + "	PART.YEAR = SSG.YEAR AND "
                + "	SSG.SUBGRUPO = SG.SUBGRUPO AND "
                + "	SSG.YEAR = SG.YEAR AND "
                + "	SG.GRUPO = G.GRUPO AND "
                + "	SG.YEAR = G.YEAR "
                + "GROUP BY "
                + "	VPAM.RAMO, "
                + "	R.DESCR, "
                + "	G.GRUPO, "
                + "	G.DESCR, "
                + "	SG.SUBGRUPO, "
                + "	SG.DESCR, "
                + "	SSG.SUBSUBGRUPO, "
                + "	SSG.DESCR, "
                + "	VPAM.PARTIDA, "
                + "	PART.DESCR, "
                + "	VPAM.YEAR, "
                + "	PRAM.GOBIERNO, "
                + "	PRAM.SECRETARIA "
                + "ORDER BY "
                + "	VPAM.RAMO, "
                + "	G.GRUPO, "
                + "	SG.SUBGRUPO, "
                + "	SSG.SUBSUBGRUPO, "
                + "	VPAM.PARTIDA ";
        return strSQL;
    }

    public String getSQLValidarReportePptoCalAnualRamoProgramaPartida(String ramos, int year) {
        String strSQL = ""
                + "SELECT nvl(max(count(*)),0) AS CUENTA "
                + "FROM "
                + "	SPP.VW_PRESUP_ASIG_MEN VPAM, "
                + "	DGI.RAMOS R, "
                + "	S_POA_PROGRAMA P, "
                + "	DGI.PARTIDA PART, "
                + "	S_DGI_GRUPOS G, "
                + "	S_DGI_SUBGRUPOS SG, "
                + "	S_DGI_SUBSUBGPO SSG, "
                + "	DGI.PARAMETROS PRAM "
                + "WHERE "
                + "	VPAM.RAMO IN (" + ramos + ")" + " AND "
                + "	VPAM.YEAR = " + year + " AND "
                + "	VPAM.RAMO = R.RAMO AND "
                + "	VPAM.YEAR = R.YEAR AND "
                + "	VPAM.PRG = P.PRG AND "
                + "	VPAM.YEAR = P.YEAR AND "
                + "	VPAM.PARTIDA = PART.PARTIDA AND "
                + "	VPAM.YEAR = PART.YEAR AND "
                + "	PART.SUBSUBGPO = SSG.SUBSUBGRUPO AND "
                + "	PART.YEAR = SSG.YEAR AND "
                + "	SSG.SUBGRUPO = SG.SUBGRUPO AND "
                + "	SSG.YEAR = SG.YEAR AND "
                + "	SG.GRUPO = G.GRUPO AND "
                + "	SG.YEAR = G.YEAR "
                + "GROUP BY "
                + "	VPAM.RAMO, "
                + "	R.DESCR, "
                + "	VPAM.PRG, "
                + "	P.DESCR, "
                + "	G.GRUPO, "
                + "	G.DESCR, "
                + "	SG.SUBGRUPO, "
                + "	SG.DESCR, "
                + "	SSG.SUBSUBGRUPO, "
                + "	SSG.DESCR, "
                + "	VPAM.PARTIDA, "
                + "	PART.DESCR, "
                + "	VPAM.YEAR, "
                + "	PRAM.GOBIERNO, "
                + "	PRAM.SECRETARIA "
                + "ORDER BY "
                + "	VPAM.RAMO, "
                + "	VPAM.PRG, "
                + "	P.DESCR, "
                + "	G.GRUPO, "
                + "	SG.SUBGRUPO, "
                + "	SSG.SUBSUBGRUPO, "
                + "	VPAM.PARTIDA ";
        return strSQL;
    }

    public String getSQLValidarReportePptoAnualRamoGrupo(String ramos, int year) {
        String strSQL = ""
                + "SELECT  "
                + "	nvl(max(count(*)),0) AS CUENTA  "
                + "FROM "
                + "	SPP.PPTO PPTO, "
                + "	DGI.RAMOS R, "
                + "	S_POA_PROGRAMA P, "
                + "	DGI.DEPENDENCIA D, "
                + "	S_POA_VW_SP_PROY_ACT VSPPA, "
                + "	S_POA_META M, "
                + "	S_POA_ACCION ACC, "
                + "	S_POA_FUENTE FU, "
                + "	S_POA_FONDO FO, "
                + "	S_POA_RECURSO RE, "
                + "	DGI.PARTIDA PART, "
                + "	S_DGI_GRUPOS G, "
                + "	S_DGI_SUBGRUPOS SG, "
                + "	S_DGI_SUBSUBGPO SSG, "
                + "	S_POA_REL_LABORAL REL, "
                + "	S_DGI_PARAM_LONG_COD PLC, "
                + "	DGI.PARAMETROS PRAM "
                + "WHERE "
                + "	PPTO.YEAR = " + year + " AND "
                + "	PPTO.RAMO IN (" + ramos + ") AND "
                + "	R.YEAR = PPTO.YEAR AND "
                + "	R.RAMO = PPTO.RAMO AND "
                + "	P.YEAR = PPTO.YEAR AND "
                + "	P.PRG = PPTO.PRG AND "
                + "	D.YEAR = PPTO.YEAR AND "
                + "	D.RAMO = PPTO.RAMO AND "
                + "	D.DEPTO = PPTO.DEPTO AND "
                + "	VSPPA.YEAR = PPTO.YEAR AND "
                + "	VSPPA.TIPO_PROY = PPTO.TIPO_PROY AND "
                + "	VSPPA.PROY = PPTO.PROYECTO AND "
                + "	M.YEAR = PPTO.YEAR AND "
                + "	M.RAMO = PPTO.RAMO AND "
                + "	M.META = PPTO.META AND "
                + "	ACC.YEAR = PPTO.YEAR AND "
                + "	ACC.RAMO = PPTO.RAMO AND "
                + "	ACC.META = PPTO.META AND "
                + "	ACC.ACCION = PPTO.ACCION AND "
                + "	FU.YEAR = PPTO.YEAR AND "
                + "	FU.FUENTE = PPTO.FUENTE AND "
                + "	FO.YEAR = PPTO.YEAR AND "
                + "	FO.FUENTE = PPTO.FUENTE AND "
                + "	FO.FONDO = PPTO.FONDO AND "
                + "	RE.YEAR = PPTO.YEAR AND "
                + "	RE.FUENTE = PPTO.FUENTE  AND "
                + "	RE.FONDO = PPTO.FONDO AND "
                + "	RE.RECURSO = PPTO.RECURSO AND "
                + "	PART.YEAR = PPTO.YEAR AND "
                + "	PART.PARTIDA = PPTO.PARTIDA AND "
                + "	SSG.YEAR = PART.YEAR AND "
                + "	SSG.SUBSUBGRUPO = PART.SUBSUBGPO AND "
                + "	SG.YEAR = SSG.YEAR AND "
                + "	SG.SUBGRUPO = SSG.SUBGRUPO AND "
                + "	G.YEAR = SG.YEAR AND "
                + "	G.GRUPO = SG.GRUPO  AND "
                + "	REL.YEAR (+)= PPTO.YEAR AND "
                + "	REL.REL_LABORAL (+)= PPTO.REL_LABORAL AND "
                + "	PLC.YEAR = PPTO.YEAR "
                + "GROUP BY "
                + "	PPTO.YEAR, "
                + "	PPTO.RAMO, "
                + "	R.DESCR, "
                + "	G.GRUPO, "
                + "	G.DESCR, "
                + "	PRAM.GOBIERNO, "
                + "	PRAM.SECRETARIA, "
                + "	SYSDATE "
                + "ORDER BY "
                + "	PPTO.YEAR, "
                + "	PPTO.RAMO, "
                + "	R.DESCR, "
                + "	G.GRUPO, "
                + "	G.DESCR, "
                + "   	PRAM.GOBIERNO, "
                + "	PRAM.SECRETARIA, "
                + "	SYSDATE "
                + "";
        return strSQL;
    }

    public String getSQLValidarReportePptoAnualRamoProgramaGrupo(String ramos, int year) {
        String strSQL = ""
                + "SELECT  "
                + "	nvl(max(count(*)),0) AS CUENTA  "
                + "FROM "
                + "	SPP.PPTO PPTO, "
                + "	DGI.RAMOS R, "
                + "	S_POA_PROGRAMA P, "
                + "	DGI.DEPENDENCIA D, "
                + "	S_POA_VW_SP_PROY_ACT VSPPA, "
                + "	S_POA_META M, "
                + "	S_POA_ACCION ACC, "
                + "	S_POA_FUENTE FU, "
                + "	S_POA_FONDO FO, "
                + "	S_POA_RECURSO RE, "
                + "	DGI.PARTIDA PART, "
                + "	S_DGI_GRUPOS G, "
                + "	S_DGI_SUBGRUPOS SG, "
                + "	S_DGI_SUBSUBGPO SSG, "
                + "	S_POA_REL_LABORAL REL, "
                + "	S_DGI_PARAM_LONG_COD PLC, "
                + "	DGI.PARAMETROS PRAM "
                + "WHERE "
                + "	PPTO.YEAR = " + year + " AND "
                + "	PPTO.RAMO IN (" + ramos + ") AND "
                + "	R.YEAR = PPTO.YEAR AND "
                + "	R.RAMO = PPTO.RAMO AND "
                + "	P.YEAR = PPTO.YEAR AND "
                + "	P.PRG = PPTO.PRG AND "
                + "	D.YEAR = PPTO.YEAR AND "
                + "	D.RAMO = PPTO.RAMO AND "
                + "	D.DEPTO = PPTO.DEPTO AND "
                + "	VSPPA.YEAR = PPTO.YEAR AND "
                + "	VSPPA.TIPO_PROY = PPTO.TIPO_PROY AND "
                + "	VSPPA.PROY = PPTO.PROYECTO AND "
                + "	M.YEAR = PPTO.YEAR AND "
                + "	M.RAMO = PPTO.RAMO AND "
                + "	M.META = PPTO.META AND "
                + "	ACC.YEAR = PPTO.YEAR AND "
                + "	ACC.RAMO = PPTO.RAMO AND "
                + "	ACC.META = PPTO.META AND "
                + "	ACC.ACCION = PPTO.ACCION AND "
                + "	FU.YEAR = PPTO.YEAR AND "
                + "	FU.FUENTE = PPTO.FUENTE AND "
                + "	FO.YEAR = PPTO.YEAR AND "
                + "	FO.FUENTE = PPTO.FUENTE AND "
                + "	FO.FONDO = PPTO.FONDO AND "
                + "	RE.YEAR = PPTO.YEAR AND "
                + "	RE.FUENTE = PPTO.FUENTE  AND "
                + "	RE.FONDO = PPTO.FONDO AND "
                + "	RE.RECURSO = PPTO.RECURSO AND "
                + "	PART.YEAR = PPTO.YEAR AND "
                + "	PART.PARTIDA = PPTO.PARTIDA AND "
                + "	SSG.YEAR = PART.YEAR AND "
                + "	SSG.SUBSUBGRUPO = PART.SUBSUBGPO AND "
                + "	SG.YEAR = SSG.YEAR AND "
                + "	SG.SUBGRUPO = SSG.SUBGRUPO AND "
                + "	G.YEAR = SG.YEAR AND "
                + "	G.GRUPO = SG.GRUPO  AND "
                + "	REL.YEAR (+)= PPTO.YEAR AND "
                + "	REL.REL_LABORAL (+)= PPTO.REL_LABORAL AND "
                + "	PLC.YEAR = PPTO.YEAR "
                + "GROUP BY "
                + "	PPTO.YEAR, "
                + "	PPTO.RAMO, "
                + "	R.DESCR, "
                + "	PPTO.PRG, "
                + "	P.DESCR, "
                + "	G.GRUPO, "
                + "	G.DESCR, "
                + "	PRAM.GOBIERNO, "
                + "	PRAM.SECRETARIA, "
                + "	SYSDATE "
                + "ORDER BY "
                + "	PPTO.YEAR, "
                + "	PPTO.RAMO, "
                + "	R.DESCR, "
                + "	PPTO.PRG, "
                + "	P.DESCR, "
                + "	G.GRUPO, "
                + "	G.DESCR, "
                + "   	PRAM.GOBIERNO, "
                + "	PRAM.SECRETARIA, "
                + "	SYSDATE "
                + "";
        return strSQL;
    }

    public String getSQLValidarReportePptoAnualCodigo(String ramos, int year) {
        String strSQL = ""
                + "SELECT  "
                + "	nvl(max(count(*)),0) AS CUENTA  "
                + "FROM "
                + "	SPP.PPTO PPTO, "
                + "	DGI.RAMOS R, "
                + "	S_POA_PROGRAMA P, "
                + "	DGI.DEPENDENCIA D, "
                + "	S_POA_VW_SP_PROY_ACT VSPPA, "
                + "	S_POA_META M, "
                + "	S_POA_ACCION ACC, "
                + "	S_POA_FUENTE FU, "
                + "	S_POA_FONDO FO, "
                + "	S_POA_RECURSO RE, "
                + "	DGI.PARTIDA PART, "
                + "	S_DGI_GRUPOS G, "
                + "	S_DGI_SUBGRUPOS SG, "
                + "	S_DGI_SUBSUBGPO SSG, "
                + "	S_POA_REL_LABORAL REL, "
                + "	S_DGI_PARAM_LONG_COD PLC, "
                + "	DGI.PARAMETROS PRAM "
                + "WHERE "
                + "	PPTO.YEAR = " + year + " AND "
                + "	PPTO.RAMO IN (" + ramos + ") AND "
                + "	R.YEAR = PPTO.YEAR AND "
                + "	R.RAMO = PPTO.RAMO AND "
                + "	P.YEAR = PPTO.YEAR AND "
                + "	P.PRG = PPTO.PRG AND "
                + "	D.YEAR = PPTO.YEAR AND "
                + "	D.RAMO = PPTO.RAMO AND "
                + "	D.DEPTO = PPTO.DEPTO AND "
                + "	VSPPA.YEAR = PPTO.YEAR AND "
                + "	VSPPA.TIPO_PROY = PPTO.TIPO_PROY AND "
                + "	VSPPA.PROY = PPTO.PROYECTO AND "
                + "	M.YEAR = PPTO.YEAR AND "
                + "	M.RAMO = PPTO.RAMO AND "
                + "	M.META = PPTO.META AND "
                + "	ACC.YEAR = PPTO.YEAR AND "
                + "	ACC.RAMO = PPTO.RAMO AND "
                + "	ACC.META = PPTO.META AND "
                + "	ACC.ACCION = PPTO.ACCION AND "
                + "	FU.YEAR = PPTO.YEAR AND "
                + "	FU.FUENTE = PPTO.FUENTE AND "
                + "	FO.YEAR = PPTO.YEAR AND "
                + "	FO.FUENTE = PPTO.FUENTE AND "
                + "	FO.FONDO = PPTO.FONDO AND "
                + "	RE.YEAR = PPTO.YEAR AND "
                + "	RE.FUENTE = PPTO.FUENTE  AND "
                + "	RE.FONDO = PPTO.FONDO AND "
                + "	RE.RECURSO = PPTO.RECURSO AND "
                + "	PART.YEAR = PPTO.YEAR AND "
                + "	PART.PARTIDA = PPTO.PARTIDA AND "
                + "	SSG.YEAR = PART.YEAR AND "
                + "	SSG.SUBSUBGRUPO = PART.SUBSUBGPO AND "
                + "	SG.YEAR = SSG.YEAR AND "
                + "	SG.SUBGRUPO = SSG.SUBGRUPO AND "
                + "	G.YEAR = SG.YEAR AND "
                + "	G.GRUPO = SG.GRUPO  AND "
                + "	REL.YEAR (+)= PPTO.YEAR AND "
                + "	REL.REL_LABORAL (+)= PPTO.REL_LABORAL AND "
                + "	PLC.YEAR = PPTO.YEAR "
                + "GROUP BY "
                + "	PPTO.YEAR, "
                + "	PPTO.RAMO, "
                + "	R.DESCR, "
                + "	PPTO.PRG, "
                + "	P.DESCR, "
                + "	PPTO.DEPTO, "
                + "	D.DESCR, "
                + "	PPTO.TIPO_PROY||LPAD(PPTO.PROYECTO, PLC.PROYECTO, '0')||'-'||LPAD(PPTO.META, PLC.META, '0')||'-'||LPAD(PPTO.ACCION, PLC.ACCION, '0')||'-'||LPAD(PPTO.FUENTE, PLC.FUENTE, '0')||''||LPAD(PPTO.FONDO, PLC.FONDO, '0')||''||LPAD(PPTO.RECURSO, PLC.RECURSO, '0'), "
                + "	G.GRUPO, "
                + "	G.DESCR, "
                + "	PPTO.PARTIDA, "
                + "	PART.DESCR, "
                + "	PPTO.REL_LABORAL, "
                + "	PRAM.GOBIERNO, "
                + "	PRAM.SECRETARIA, "
                + "	SYSDATE "
                + "ORDER BY "
                + "	PPTO.YEAR, "
                + "	PPTO.RAMO, "
                + "	R.DESCR, "
                + "	PPTO.PRG, "
                + "	P.DESCR, "
                + "	PPTO.DEPTO, "
                + "	D.DESCR, "
                + "	PPTO.TIPO_PROY||LPAD(PPTO.PROYECTO, PLC.PROYECTO, '0')||'-'||LPAD(PPTO.META, PLC.META, '0')||'-'||LPAD(PPTO.ACCION, PLC.ACCION, '0')||'-'||LPAD(PPTO.FUENTE, PLC.FUENTE, '0')||''||LPAD(PPTO.FONDO, PLC.FONDO, '0')||''||LPAD(PPTO.RECURSO, PLC.RECURSO, '0'), "
                + "	G.GRUPO, "
                + "	G.DESCR, "
                + "	PPTO.PARTIDA, "
                + "	PART.DESCR, "
                + "	PPTO.REL_LABORAL, "
                + "   	PRAM.GOBIERNO, "
                + "	PRAM.SECRETARIA, "
                + "	SYSDATE "
                + "";
        return strSQL;
    }

    public String getSQLGetNumObraAccion(int year, String ramo, int meta, int accion) {
        String strSQL = "SELECT NVL(A.OBRA,'0') AS OBRA FROM S_POA_ACCION A "
                + "WHERE "
                + "    A.YEAR = " + year + " AND "
                + "    A.RAMO = '" + ramo + "' AND "
                + "    A.META = " + meta + " AND "
                + "    A.ACCION = " + accion;
        return strSQL;
    }

    public String getSQLValidarReporteComparativoPresupuesto(String ramos, int year, String queryPartidas) {
        String strSQL = ""
                + "SELECT "
                + "    nvl(max(count(*)),0) AS CUENTA	 "
                + "FROM "
                + "	( "
                + "		SELECT "
                + "			P.YEAR, "
                + "			P.RAMO, "
                + "			P.PARTIDA, "
                + "			SUM(NVL(P.ASIGNADO,0)) AS PRESUPUESTADO_ACT, "
                + "			0 AS INICIAL_ANT, "
                + "			0 AS ASIGNADO_ANT, "
                + "			0 AS PROYECTADO_ANT "
                + "		FROM "
                + "			SPP.PPTO P "
                + "		WHERE "
                + "			P.RAMO IN(" + ramos + ") "
                + "			AND P.PARTIDA " + queryPartidas + " "
                + "			AND P.YEAR = " + year + " "
                + "		GROUP BY "
                + "			P.YEAR, "
                + "			P.RAMO, "
                + "			P.PARTIDA "
                + " "
                + "		UNION "
                + " "
                + "		SELECT "
                + "			PAREQ.YEAR_NVO, "
                + "			RAMEQ.RAMO_ACT RAMO, "
                + "			PAREQ.PARTIDA_NVO, "
                + "			0 AS PRESUPUESTADO_ACT, "
                + "			SUM(ACT.ASIGNADO) AS INICIAL_ANT, "
                + "			SUM(ACT.ASIGNADO) + SUM(ACT.MVTOS) AS ASIGNADO_ANT, "
                + "			0 AS PROYECTADO_ANT "
                + "		FROM "
                + "			( "
                + "				SELECT "
                + "					ANT.YEAR, "
                + "					ANT.RAMO, "
                + "					ANT.PRG, "
                + "					ANT.PARTIDA, "
                + "					SUM(NVL(ANT.ASIGNADO,0)) AS ASIGNADO, "
                + "					SUM(NVL(ANT.MVTOS_PRESUP,0)) AS MVTOS "
                + "				FROM "
                + "					( "
                + "						SELECT "
                + "							ASIG.RAMO, "
                + "							ASIG.PRG, "
                + "							ASIG.PARTIDA, "
                + "							ASIG.YEAR, "
                + "							SUM(NVL(ASIG.ASIGNADO,0)) AS ASIGNADO, "
                + "							0 AS MVTOS_PRESUP "
                + "						FROM "
                + "							( "
                + "								SELECT "
                + "									YEAR, "
                + "									RAMO, "
                + "									PRG, "
                + "									PARTIDA, "
                + "									ASIGNADO "
                + "								FROM "
                + "									S_SPP_VM_PPTO_ANUAL_ANT "
                + "								UNION "
                + "								SELECT "
                + "									TO_CHAR(EJERCICIO) YEAR, "
                + "									RAMO, "
                + "									PROGRAMA PRG, "
                + "									PARTIDA, "
                + "									INICIAL ASIGNADO "
                + "								FROM "
                + "									S_CIM_CIERRE_PPTO_ARMO "
                + "							) ASIG "
                + "						WHERE "
                + "							ASIG.YEAR = (" + year + " - 1) "
                + "							AND RAMO IN(" + ramos + ") "
                + "						GROUP BY    "
                + "							ASIG.RAMO, "
                + "							ASIG.PRG, "
                + "							ASIG.PARTIDA,  "
                + "							ASIG.YEAR "
                + "						 "
                + "						UNION "
                + "						 "
                + "						SELECT "
                + "							MVTOS.RAMO, "
                + "							MVTOS.PRG, "
                + "							MVTOS.PARTIDA, "
                + "							MVTOS.YEAR, "
                + "							0 AS ASIGNADO, "
                + "							SUM(NVL(MVTOS.AMPLIACION,0)) + SUM(NVL(MVTOS.REDUCCION,0)) AS MVTOS_PRESUP "
                + "						FROM "
                + "							( "
                + "								SELECT "
                + "									YEAR, "
                + "									RAMO, "
                + "									PRG,   "
                + "									PARTIDA,     "
                + "									ASIGNADO, "
                + "									AMPLIACION, "
                + "									REDUCCION, "
                + "									MES "
                + "								FROM "
                + "									S_SPP_VW_PPTO_MOD_AUT_ANT "
                + "								UNION "
                + "								SELECT "
                + "									TO_CHAR(EJERCICIO) YEAR, "
                + "									RAMO, "
                + "									PROGRAMA PRG, "
                + "									PARTIDA,     "
                + "									INICIAL ASIGNADO, "
                + "									AMPLIACION, "
                + "									REDUCCION, "
                + "									MES "
                + "								FROM "
                + "									S_CIM_CIERRE_PPTO_ARMO "
                + "							) MVTOS "
                + "						WHERE "
                + "							MVTOS.YEAR = (" + year + " - 1) "
                + "							AND MVTOS.RAMO IN(" + ramos + ") "
                + "							AND MVTOS.MES <= 12 "
                + "						GROUP BY  "
                + "							MVTOS.RAMO,  "
                + "							MVTOS.PRG,  "
                + "							MVTOS.PARTIDA,  "
                + "							MVTOS.YEAR "
                + "					) ANT "
                + "				GROUP BY  "
                + "					ANT.YEAR, "
                + "					ANT.RAMO,  "
                + "					ANT.PRG, "
                + "					ANT.PARTIDA  "
                + "			) ACT, "
                + "			S_DGI_PART_EQUIV PAREQ, "
                + "			DGI.RAMO_EQUIV RAMEQ "
                + "		WHERE  "
                + "			PAREQ.YEAR_ACT = ACT.YEAR "
                + "			AND PAREQ.PARTIDA_ACT = ACT.PARTIDA "
                + "			AND PAREQ.YEAR_NVO = " + year + " "
                + "			AND RAMEQ.YEAR_ACT = ACT.YEAR "
                + "                      AND RAMEQ.RAMO_ACT = ACT.RAMO  "
                + "                      AND RAMEQ.YEAR_NVO = " + year + "             "
                + "			AND RAMEQ.RAMO_NVO IN(" + ramos + ") "
                + "			AND PAREQ.PARTIDA_NVO " + queryPartidas + " "
                + "		GROUP BY  "
                + "			PAREQ.YEAR_NVO,  "
                + "			RAMEQ.RAMO_ACT,  "
                + "			PAREQ.PARTIDA_NVO "
                + "		 "
                + "		UNION "
                + "		 "
                + "		SELECT   "
                + "			PAREQ.YEAR_NVO, "
                + "			RAMEQ.RAMO_ACT RAMO, "
                + "			PAREQ.PARTIDA_NVO, "
                + "			0 AS PRESUPUESTADO_ACT, "
                + "			0 AS INICIAL_ANT, "
                + "			0 AS ASIGNADO_ANT, "
                + "			SUM(NVL(PROY.PROYECTADO,0)) AS PROYECTADO_ANT "
                + "		FROM  "
                + "			S_SPP_VW_PROYECCION_ANT PROY, "
                + "			S_DGI_PART_EQUIV PAREQ, "
                + "                      DGI.RAMO_EQUIV RAMEQ "
                + "		WHERE              "
                + "			PAREQ.YEAR_ACT = PROY.YEAR "
                + "			AND PAREQ.PARTIDA_ACT = PROY.PARTIDA "
                + "			AND PAREQ.YEAR_NVO = " + year + " "
                + "			AND RAMEQ.YEAR_ACT = PROY.YEAR "
                + "                      AND RAMEQ.RAMO_ACT = PROY.RAMO "
                + "                      AND RAMEQ.YEAR_NVO = " + year + " "
                + "                      AND RAMEQ.RAMO_NVO IN(" + ramos + ") "
                + "			AND PAREQ.PARTIDA_NVO " + queryPartidas + " "
                + "		GROUP BY  "
                + "			PAREQ.YEAR_NVO,  "
                + "			RAMEQ.RAMO_ACT,  "
                + "			PAREQ.PARTIDA_NVO "
                + "	) COMP, "
                + "	( "
                + "		SELECT "
                + "			P.PARTIDA, "
                + "			P.YEAR, "
                + "			G.GRUPO, "
                + "			G.DESCR AS GPO_DESCR, "
                + "			SG.SUBGRUPO, "
                + "			SG.DESCR AS SUBGRUPO_DESCR, "
                + "			SSG.SUBSUBGRUPO, "
                + "			SSG.DESCR AS SUBSUBGPO_DESCR, "
                + "			P.DESCR AS PARTIDA_DESCR "
                + "		FROM "
                + "			DGI.PARTIDA P, "
                + "			S_DGI_SUBSUBGPO SSG, "
                + "			S_DGI_SUBGRUPOS SG, "
                + "			S_DGI_GRUPOS G "
                + "		WHERE  "
                + "			SSG.YEAR = P.YEAR "
                + "			AND SSG.SUBSUBGRUPO = P.SUBSUBGPO "
                + "			AND SG.SUBGRUPO = SSG.SUBGRUPO "
                + "			AND G.YEAR = SSG.YEAR "
                + "			AND G.GRUPO = SSG.GRUPO "
                + "			AND SG.GRUPO = G.GRUPO "
                + "			AND SG.YEAR = G.YEAR "
                + "			AND P.YEAR = " + year + " "
                + "			AND P.PARTIDA " + queryPartidas + " "
                + "	) GPO, "
                + "	DGI.PARAMETROS PRM, "
                + "	DGI.RAMOS RAM "
                + "WHERE "
                + "	GPO.YEAR = COMP.YEAR "
                + "	AND GPO.PARTIDA = COMP.PARTIDA "
                + "	AND RAM.RAMO = COMP.RAMO "
                + "	AND RAM.YEAR = COMP.YEAR "
                + "GROUP BY  "
                + "	PRM.GOBIERNO, "
                + "	PRM.SECRETARIA,  "
                + "	PRM.LOGO,  "
                + "	COMP.YEAR,  "
                + "	GPO.GRUPO,  "
                + "	GPO.GPO_DESCR,  "
                + "	COMP.RAMO, "
                + "	GPO.SUBGRUPO, "
                + "	GPO.SUBGRUPO_DESCR, "
                + "	GPO.SUBSUBGRUPO, "
                + "	GPO.SUBSUBGPO_DESCR, "
                + "	RAM.DESCR, "
                + "	GPO.PARTIDA, "
                + "	GPO.PARTIDA_DESCR "
                + "ORDER BY "
                + "	COMP.RAMO, "
                + "	GPO.GRUPO, "
                + "	GPO.SUBGRUPO, "
                + "	GPO.SUBSUBGRUPO, "
                + "	GPO.PARTIDA "
                + "";
        return strSQL;
    }

    public String getSQLDesconectaDblink() {
        String strSQL = "{CALL DGI.P_CERRAR_SESION_DBLINK(?)}";
        return strSQL;
    }

    public String getSQLDeleteProyeccion(String ramo, int year) {
        String strSQL = "DELETE "
                + "FROM POA.PROYECCION P "
                + "WHERE "
                + "    P.RAMO = '" + ramo + "' AND "
                + "    P.YEAR = " + year;
        return strSQL;
    }

    public String getSQLgetCountRamoAnterior() {
        String strSQL
                = "SELECT count(*) AS RAMO_ANT FROM DGI.RAMOS R "
                + "WHERE "
                + "    R.YEAR = ? AND "
                + "    R.RAMO = ? ";
        return strSQL;
    }

    public String getSQLgetCountPartidaAnterior() {
        String strSQL = "SELECT count(*) AS PARTIDA_ANT FROM DGI.PARTIDA R "
                + "WHERE "
                + "    R.YEAR = ? AND "
                + "    R.PARTIDA = ? ";
        return strSQL;
    }

    public String getSQLgetCountRelLaboralAnterior() {
        String strSQL = "SELECT count(*) AS REL_LABORAL FROM S_POA_REL_LABORAL R "
                + "WHERE "
                + "    R.YEAR = ? AND "
                + "    R.REL_LABORAL = ? ";
        return strSQL;
    }

    public String getSQLgetCountPartidaPlantilla(String partida, int year) {
        String strSQL = "SELECT COUNT(1) AS PLANTILLA FROM DGI.PARTIDA P "
                + "WHERE P.YEAR = ? AND P.PARTIDA = ? AND "
                + " P.PLANTILLA = 'S' ";
        //     "      P.PARTIDA = '"+partida+"' AND " +
        //   "      P.YEAR = " + year;

        return strSQL;
    }

    public String getSLQGetAnioPresupuestalValidacion(String ramo, String depto,
            int meta, int accion, String partida, String fuente, String fondo, String recurso) {
        String strSLQ = "select \n"
                + " sum(ene*costo_unitario) as ene,\n"
                + "sum(feb*costo_unitario) as feb,\n"
                + "sum(mar*costo_unitario) as mar,\n"
                + "sum(abr*costo_unitario) as abr,\n"
                + "sum(may*costo_unitario) as may,\n"
                + "sum(jun*costo_unitario) as jun,\n"
                + "sum(jul*costo_unitario) as jul,\n"
                + "sum(ago*costo_unitario) as ago,\n"
                + "sum(sep*costo_unitario) as sep,\n"
                + "sum(oct*costo_unitario) as oct,\n"
                + "sum(nov*costo_unitario) as nov,\n"
                + "sum(dic*costo_unitario) as dic,\n"
                + "from poa.accion_req\n"
                + "where  ramo='" + ramo + "' and depto='" + depto + "' and meta=" + meta + " "
                + "and accion=" + accion + " and partida='" + partida + "'  and fuente= '" + fuente + "'"
                + "and fondo='" + fondo + "' and recurso='" + recurso + "'";
        return strSLQ;
    }

    public String getSQLgetPPTOvalidacion(String ramo, String depto,
            int meta, int accion, String partida, String fuente, String fondo, String recurso) {
        String strSQL = "select "
                + "P.ASIGNADO "
                + "from SPP.PPTO P "
                + "where  P.ramo='" + ramo + "' and P.depto='" + depto + "' and P.meta= " + meta + " and "
                + "P.accion= " + accion + " and P.partida='" + partida + "'  and P.fuente='" + fuente + "' and "
                + "P.fondo= '" + fondo + "' and P.recurso='" + recurso + "' order by P.mes ";
        return strSQL;
    }

    public String getSQLGetTipoRequerimientoPlantilla(int year, String ramo, String prg, int meta,
            int accion, String partida, String fuente, String fondo, String recurso, int requerimiento) {
        String strSQL = "SELECT count(1) AS ARCHIVO FROM POA.ACCION_REQ AC "
                + "WHERE  "
                + "    AC.YEAR = " + year + " AND "
                + "    AC.RAMO = '" + ramo + "' AND "
                + "    AC.PRG = '" + prg + "' AND "
                + "    AC.META = " + meta + " AND "
                + "    AC.ACCION = " + accion + " AND "
                + "    AC.PARTIDA = '" + partida + "' AND "
                + "    AC.FUENTE = '" + fuente + "' AND "
                + "    AC.FONDO = '" + fondo + "' AND "
                + "    AC.RECURSO = '" + recurso + "' AND "
                + "    AC.REQUERIMIENTO = " + requerimiento + "AND "
                + "    AC.ARCHIVO = 1 ";
        return strSQL;
    }

    public String getSQLInsertaCodigosPlantilla(int iYear, String sRamo, String sOrigen, boolean bParaestatal) {
        String strSQL = "INSERT INTO SPP.CODIGOS (YEAR,RAMO,DEPTO,FINALIDAD,FUNCION,SUBFUNCION,PRG_CONAC, "
                + " PRG,TIPO_PROY,PROYECTO,META,ACCION,PARTIDA,TIPO_GASTO,FUENTE,FONDO,RECURSO,MUNICIPIO,DELEGACION,REL_LABORAL "
                + " )"
                + " SELECT distinct YEAR,RAMO,DEPTO,FINALIDAD,FUNCION,SUBFUNCION,PRG_CONAC, "
                + " PRG,TIPO_PROY,PROY,META,ACCION,PARTIDA,TIPO_GASTO,FUENTE,FONDO,RECURSO,MUNICIPIO,DELEGACION,REL_LABORAL "
                + " FROM POA.PRESUP_PLANTILLA P "
                + " WHERE YEAR = " + iYear + " AND ORIGEN = '" + sOrigen + "' AND ";
        if (bParaestatal) {
            strSQL += " RAMO = '" + sRamo + "' AND";
        }
        strSQL += " NOT EXISTS (SELECT 1 FROM SPP.CODIGOS C WHERE C.YEAR = P.YEAR AND C.RAMO = P.RAMO AND  "
                + " C.PRG = P.PRG AND  C.FINALIDAD = P.FINALIDAD AND  C.FUNCION = P.FUNCION AND "
                + " C.SUBFUNCION = P.SUBFUNCION AND C.PRG_CONAC = P.PRG_CONAC AND C.DEPTO = P.DEPTO AND "
                + " C.TIPO_PROY = P.TIPO_PROY AND C.PROYECTO = P.PROY AND C.META = P.META  AND  "
                + " C.ACCION = P.ACCION AND C.PARTIDA = P.PARTIDA AND C.TIPO_GASTO = P.TIPO_GASTO "
                + " AND C.FUENTE = P.FUENTE AND C.FONDO = P.FONDO AND C.RECURSO = P.RECURSO AND "
                + " C.MUNICIPIO = P.MUNICIPIO AND C.DELEGACION = P.DELEGACION AND C.REL_LABORAL = P.REL_LABORAL) ";
        return strSQL;
    }

    public String getSQLInsertaPPTOFaltantes(int iYear, String sRamo, String sOrigen, boolean bParaestatal) {
        String strSQL = "INSERT INTO SPP.PPTO (YEAR,RAMO,DEPTO,FINALIDAD,FUNCION,SUBFUNCION,PRG_CONAC, "
                + " PRG,TIPO_PROY,PROYECTO,META,ACCION,PARTIDA,TIPO_GASTO,FUENTE,FONDO,RECURSO,MUNICIPIO,DELEGACION,REL_LABORAL, "
                + " MES,"
                + "ASIGNADO,ACTUALIZADO,COMPROMISO,EJERCIDO,ORIGINAL,FECHA_REGISTRO,FECHA_MODIFICACION)"
                + " SELECT distinct YEAR,RAMO,DEPTO,FINALIDAD,FUNCION,SUBFUNCION,PRG_CONAC, "
                + " PRG,TIPO_PROY,PROY,META,ACCION,PARTIDA,TIPO_GASTO,FUENTE,FONDO,RECURSO,MUNICIPIO,DELEGACION,REL_LABORAL,"
                + "MES, "
                + " 0,0,0,0,0,SYSDATE,SYSDATE "
                + " FROM POA.PRESUP_PLANTILLA P "
                + " WHERE YEAR = " + iYear + " AND ORIGEN = '" + sOrigen + "' AND ";
        if (bParaestatal) {
            strSQL += " RAMO = '" + sRamo + "' AND";
        }
        strSQL += " NOT EXISTS "
                + "( "
                + "SELECT 1 FROM SPP.PPTO C WHERE C.YEAR = P.YEAR AND C.RAMO = P.RAMO AND  "
                + " C.PRG = P.PRG AND  C.FINALIDAD = P.FINALIDAD AND  C.FUNCION = P.FUNCION AND "
                + " C.SUBFUNCION = P.SUBFUNCION AND C.PRG_CONAC = P.PRG_CONAC AND C.DEPTO = P.DEPTO AND "
                + " C.TIPO_PROY = P.TIPO_PROY AND C.PROYECTO = P.PROY AND C.META = P.META  AND  "
                + " C.ACCION = P.ACCION AND C.PARTIDA = P.PARTIDA AND C.TIPO_GASTO = P.TIPO_GASTO "
                + " AND C.FUENTE = P.FUENTE AND C.FONDO = P.FONDO AND C.RECURSO = P.RECURSO AND "
                + " C.MUNICIPIO = P.MUNICIPIO AND C.DELEGACION = P.DELEGACION AND C.REL_LABORAL = P.REL_LABORAL "
                + " AND C.MES = P.MES ) ";
        return strSQL;
    }

    public String getSQLUpdatePPTO(int year, String ramo, String sOrigen, boolean bParaestatal, boolean bSuma) {
        String sSigno;
        if (bSuma) {
            sSigno = "+";
        } else {
            sSigno = "-";
        }
        String strSQLSub = "SELECT SUM(CANTIDAD) "
                + " FROM POA.PRESUP_PLANTILLA C "
                + " WHERE C.YEAR = P.YEAR "
                + " AND C.RAMO = P.RAMO "
                + " AND C.PRG = P.PRG "
                + " AND C.FINALIDAD = P.FINALIDAD "
                + " AND C.FUNCION = P.FUNCION "
                + " AND C.SUBFUNCION = P.SUBFUNCION "
                + " AND C.PRG_CONAC = P.PRG_CONAC "
                + " AND C.DEPTO = P.DEPTO "
                + " AND C.TIPO_PROY = P.TIPO_PROY "
                + " AND C.PROY = P.PROYECTO "
                + " AND C.META = P.META  "
                + " AND C.ACCION = P.ACCION "
                + " AND C.PARTIDA = P.PARTIDA "
                + " AND C.TIPO_GASTO = P.TIPO_GASTO "
                + " AND C.FUENTE = P.FUENTE "
                + " AND C.FONDO = P.FONDO "
                + " AND C.RECURSO = P.RECURSO "
                + " AND C.MUNICIPIO = P.MUNICIPIO "
                + " AND C.DELEGACION = P.DELEGACION "
                + " AND C.REL_LABORAL = P.REL_LABORAL "
                + " AND C.MES = P.MES ";
        String strSQL = "UPDATE SPP.PPTO P set ASIGNADO = ASIGNADO " + sSigno + "  (" + strSQLSub + ")"
                + ",ORIGINAL = ORIGINAL " + sSigno + " (" + strSQLSub + "), FECHA_MODIFICACION = SYSDATE"
                + " WHERE EXISTS "
                + " (SELECT 1 "
                + " FROM POA.PRESUP_PLANTILLA S "
                + " WHERE S.YEAR = " + year + " "
                + " AND S.ORIGEN = '" + sOrigen + "' ";
        if (bParaestatal) {
            strSQL += " AND S.RAMO = '" + ramo + "' ";
        }

        strSQL += " AND P.YEAR = S.YEAR "
                + " AND P.RAMO = S.RAMO "
                + " AND P.PRG = S.PRG "
                + " AND P.FINALIDAD = S.FINALIDAD "
                + " AND P.FUNCION = S.FUNCION "
                + " AND P.SUBFUNCION = S.SUBFUNCION "
                + " AND P.PRG_CONAC = S.PRG_CONAC "
                + " AND P.DEPTO = S.DEPTO "
                + " AND P.TIPO_PROY = S.TIPO_PROY "
                + " AND P.PROYECTO = S.PROY "
                + " AND P.META = S.META  "
                + " AND P.ACCION = S.ACCION "
                + " AND P.PARTIDA = S.PARTIDA "
                + " AND P.TIPO_GASTO = S.TIPO_GASTO "
                + " AND P.FUENTE = S.FUENTE "
                + " AND P.FONDO = S.FONDO "
                + " AND P.RECURSO = S.RECURSO "
                + " AND P.MUNICIPIO = S.MUNICIPIO "
                + " AND P.DELEGACION = S.DELEGACION "
                + " AND P.REL_LABORAL = S.REL_LABORAL "
                + " AND P.MES = S.MES) ";

        return strSQL;
    }

    public String getSQLGetAsignadoAnterior(int year, String ramo,
            String programa, String depto, int meta, int accion, int req) {
        String strSQL = "SELECT A.ENE,A.FEB,A.MAR,A.ABR,A.MAY,A.JUN,A.JUL,A.AGO,A.SEP,A.OCT,A.NOV,A.DIC, A.COSTO_UNITARIO FROM POA.ACCION_REQ A "
                + "WHERE "
                + "    A.YEAR = " + year + " AND "
                + "    A.RAMO = '" + ramo + "' AND "
                + "    A.PRG = '" + programa + "' AND "
                + "    A.DEPTO = '" + depto + "' AND "
                + "    A.META = " + meta + " AND "
                + "    A.ACCION = " + accion + " AND "
                + "    A.REQUERIMIENTO = " + req;
        return strSQL;
    }

    public String getSQLCountPresupPlantillaCentral(int year, String origen) {
        String strSQL = "SELECT COUNT(1) AS CUENTA "
                + "FROM POA.PRESUP_PLANTILLA PP "
                + "WHERE "
                + "    PP.ORIGEN = '" + origen + "' AND "
                + "    PP.YEAR = " + year;
        return strSQL;
    }

    public String getSQLCountFuenteFinanciamiento(int year, String fuente, String fondo, String recurso) {
        String strSQL = "SELECT COUNT(1) AS CUENTA "
                + "FROM S_POA_RECURSO R "
                + "WHERE "
                + "    R.YEAR = " + year + " AND "
                + "    R.FUENTE = '" + fuente + "' AND "
                + "    R.FONDO = '" + fondo + "' AND "
                + "    R.RECURSO = '" + recurso + "'";
        return strSQL;
    }

    public String getSQLGetProgramasByRamoInList(String ramoInList, int year, String usuario) {

        String strSQL = " "
                + "SELECT  "
                + "	DISTINCT  "
                + "		RPRO.PRG, "
                + "		PRO.DESCR,  "
                + "		USU.APP_LOGIN "
                + "FROM  "
                + "	POA.RAMO_PROGRAMA RPRO,  "
                + "	POA.VW_ACC_USR_RAMO USU,  "
                + "	S_POA_PROGRAMA PRO "
                + "WHERE  "
                + "	RPRO.YEAR = " + year + " AND "
                + "	RPRO.RAMO IN(" + ramoInList + ") AND   "
                + "	PRO.YEAR = RPRO.YEAR AND "
                + "	PRO.PRG = RPRO.PRG AND "
                + "	USU.PRG = RPRO.PRG AND "
                + "	USU.APP_LOGIN = '" + usuario + "' AND "
                + "	USU.YEAR = RPRO.YEAR      "
                + "ORDER BY  "
                + "	RPRO.PRG "
                + " ";

        return strSQL;
    }

    public String getSQLGetProyectosByRamPrgInList(String ramoInList, String programaInList, int year) {
        String strSQL = "SELECT PROY.PROY,PP.DESCR,  PROY.RFC, PROY.HOMOCLAVE, PROY.TIPO_PROY, PROY.DEPTO_RESP, OBRA "
                + "FROM POA.PROYECTO PROY, "
                + "    DGI.VW_SP_PROY_ACT PP "
                + "WHERE PROY.PRG  IN(" + programaInList + ")   AND  "
                + "   PROY.YEAR =   " + year + "   AND "
                + "   PROY.RAMO IN(" + ramoInList + ") AND "
                + "   PROY.PROY = PP.PROY AND "
                + "   PROY.TIPO_PROY = PP.TIPO_PROY AND "
                + "   PROY.YEAR = PP.YEAR "
                + "ORDER BY PROY.PROY";
        return strSQL;
    }

    public String getSQLgetParametroIndicadores() {
        String strSQL = "SELECT MUESTRA_INDICADORES FROM DGI.PARAMETROS";
        return strSQL;
    }

    public String getSQLgetParametroTransAnaul() {
        String strSQL = "SELECT TRANSF_ANUAL FROM DGI.PARAMETROS P";
        return strSQL;
    }

    public String getSQLcongelaMeta(int year) {
        String strSQL = "INSERT INTO POA.META_AUTORIZADO MA (MA.YEAR,MA.RAMO,MA.PRG,MA.META,MA.DESCR,MA.MEDIDA, MA.CALCULO,MA.TIPO, MA.CVE_MEDIDA,MA.FINALIDAD,\n"
                + "MA.FUNCION, MA.SUBFUNCION, MA.TIPO_PROY,MA.PROY, MA.DEPTO, MA.CLAVE, MA.LINEA, MA.TIPO_COMPROMISO, MA.BENEFICIADOS, MA.PRESUPUESTAR,\n"
                + "MA.APROB_CONGRESO,MA.CONVENIO, MA.CONV, MA.BENEF_HOMBRE, MA.BENEF_MUJER, MA.GENERO, MA.PRINCIPAL,MA.RELATORIA, MA.OBRA, MA.DESCR_CORTA,MA.MAYOR_COSTO, MA.FICHA_TECNICA, MA.CRITERIO, MA.OBJ, MA.OBJ_META,MA.PONDERADO,MA.LINEA_SECTORIAL, MA.PROCESO_AUTORIZACION)\n"
                + "SELECT M.YEAR,M.RAMO,M.PRG,M.META,M.DESCR,M.MEDIDA, M.CALCULO,M.TIPO, M.CVE_MEDIDA,M.FINALIDAD,\n"
                + "M.FUNCION, M.SUBFUNCION, M.TIPO_PROY,M.PROY, M.DEPTO, M.CLAVE, M.LINEA, M.TIPO_COMPROMISO, M.BENEFICIADOS, M.PRESUPUESTAR,\n"
                + "M.APROB_CONGRESO,M.CONVENIO, M.CONV, M.BENEF_HOMBRE, M.BENEF_MUJER, M.GENERO, M.PRINCIPAL,M.RELATORIA, M.OBRA, M.DESCR_CORTA,M.MAYOR_COSTO, M.FICHA_TECNICA, M.CRITERIO, M.OBJ, M.OBJ_META,M.PONDERADO,M.LINEA_SECTORIAL, M.PROCESO_AUTORIZACION \n"
                + "FROM POA.META M\n"
                + "WHERE YEAR = " + year;
        return strSQL;
    }

    public String getSQLcongelaAccion(int year) {
        String strSQL = "INSERT INTO POA.ACCION_AUTORIZADO AA (AA.YEAR,AA.ramo,AA.PRG,AA.DEPTO,AA.META, AA.ACCION,AA.DESCR,AA.MEDIDA,AA.CVE_MEDIDA,AA.CALCULO,\n"
                + "AA.TIPO_GASTO,AA.LINEA,AA.GRUPO_POBLACION,AA.BENEF_HOMBRE,AA.BENEF_MUJER,AA.MPO,AA.LOCALIDAD,AA.TIPO_ACCION,AA.LINEA_SECTORIAL,AA.OBRA)\n"
                + "SELECT A.YEAR,A.ramo,A.PRG,A.DEPTO,A.META, A.ACCION,A.DESCR,A.MEDIDA,A.CVE_MEDIDA,A.CALCULO,A.TIPO_GASTO,A.LINEA,A.GRUPO_POBLACION,A.BENEF_HOMBRE,A.BENEF_MUJER,A.MPO,A.LOCALIDAD,A.TIPO_ACCION,A.LINEA_SECTORIAL,A.OBRA \n"
                + "FROM POA.ACCION A\n"
                + "WHERE A.YEAR = " + year;
        return strSQL;
    }

    public String getSQLcongelaEstimacion(int year) {
        String strSQL = "INSERT INTO DGI.ESTIMACION_AUTORIZADO EA (EA.META, EA.RAMO, EA.YEAR, EA.PERIODO,EA.VALOR)\n"
                + "SELECT E.META, E.RAMO, E.YEAR, E.PERIODO,E.VALOR\n"
                + "FROM DGI.ESTIMACION E\n"
                + "WHERE E.YEAR = " + year;
        return strSQL;
    }

    public String getSQLcongelaAccionEstimacion(int year) {
        String strSQL = "INSERT INTO DGI.ACCION_ESTIMACION_AUTORIZADO EA (EA.META, EA.RAMO, EA.YEAR,EA.ACCION, EA.PERIODO,EA.VALOR)\n"
                + "SELECT E.META, E.RAMO, E.YEAR,E.ACCION, E.PERIODO,E.VALOR\n"
                + "FROM DGI.ACCION_ESTIMACION E\n"
                + "WHERE E.YEAR = " + year;
        return strSQL;
    }

    public String getSQLcongelaAccionReq(int year) {
        String strSQL = "INSERT INTO POA.ACCION_REQ_AUTORIZADO AA (AA.YEAR,AA.RAMO,AA.PRG,AA.DEPTO,AA.META,AA.ACCION,AA.REQUERIMIENTO, AA.DESCR,AA.FUENTE,AA.TIPO_GASTO,AA.PARTIDA,AA.REL_LABORAL,AA.CANTIDAD,AA.COSTO_UNITARIO,AA.COSTO_ANUAL, AA.ENE,AA.FEB,AA.MAR,AA.ABR,AA.MAY, AA.JUN,\n"
                + "AA.JUL,AA.AGO,AA.SEP, AA.OCT, AA.NOV,AA.DIC,AA.ARTICULO, AA.JUSTIFICACION,AA.FONDO,AA.RECURSO,AA.FECHA_MODIFICACION,AA.FECHA_REGISTRO,AA.APP_LOGIN_REG,AA.APP_LOGIN_MOD)\n"
                + "SELECT A.YEAR,A.RAMO,A.PRG,A.DEPTO,A.META,A.ACCION,A.REQUERIMIENTO,A.DESCR,A.FUENTE,A.TIPO_GASTO,A.PARTIDA,A.REL_LABORAL,A.CANTIDAD,A.COSTO_UNITARIO,A.COSTO_ANUAL,A.ENE,A.FEB,A.MAR,A.ABR,A.MAY,A.JUN,\n"
                + "A.JUL,A.AGO,A.SEP,A.OCT,A.NOV,A.DIC,A.ARTICULO,A.JUSTIFICACION,A.FONDO,A.RECURSO,A.FECHA_MODIFICACION,A.FECHA_REGISTRO,A.APP_LOGIN_REG,A.APP_LOGIN_MOD\n"
                + "FROM POA.ACCION_REQ A\n"
                + "WHERE A.YEAR = " + year;
        return strSQL;
    }

    public String getSQLcongelaProyecto(int year) {
        String strSQL = "INSERT INTO POA.PROYECTO_AUTORIZADO PA (PA.YEAR,PA.RAMO,PA.PRG,PA.TIPO_PROY,PA.PROY,PA.DEPTO,PA.DEPTO_RESP,PA.RFC,PA.HOMOCLAVE,PA.NOINCLUIR)\n"
                + "SELECT P.YEAR,P.RAMO,P.PRG,P.TIPO_PROY,P.PROY,P.DEPTO,P.DEPTO_RESP,P.RFC,P.HOMOCLAVE,P.NOINCLUIR\n"
                + "FROM POA.PROYECTO P\n"
                + "WHERE P.YEAR = " + year;
        return strSQL;
    }

    public String getSQLcongelaPPTO(int year) {
        String strSQL = "INSERT INTO SPP.PPTO_AUTORIZADO PA (PA.YEAR, PA.RAMO,PA.DEPTO,PA.FINALIDAD,PA.FUNCION,PA.SUBFUNCION,PA.PRG_CONAC,PA.PRG,PA.TIPO_PROY, PA.PROYECTO,PA.META,PA.ACCION,PA.PARTIDA,PA.TIPO_GASTO,PA.FUENTE,PA.FONDO,PA.RECURSO,PA.MUNICIPIO,PA.DELEGACION,PA.REL_LABORAL,PA.MES, PA.ASIGNADO,PA.ORIGINAL, PA.FECHA_MODIFICACION,PA.FECHA_REGISTRO)\n"
                + "SELECT P.YEAR, P.RAMO,P.DEPTO,P.FINALIDAD,P.FUNCION,P.SUBFUNCION,P.PRG_CONAC,P.PRG,P.TIPO_PROY, P.PROYECTO,P.META,P.ACCION,P.PARTIDA,P.TIPO_GASTO,P.FUENTE,P.FONDO,P.RECURSO,P.MUNICIPIO,P.DELEGACION,P.REL_LABORAL,P.MES, P.ASIGNADO,P.ORIGINAL, P.FECHA_MODIFICACION,P.FECHA_REGISTRO\n"
                + "FROM SPP.PPTO P\n"
                + "WHERE P.YEAR = " + year + " ";
        return strSQL;
    }

    public String getCountCongelaPresupuesto(int year) {
        String strSQL = "SELECT (PO.C_PROY + ME.C_META + EM.C_EST + AC.C_ACC + AE.C_AES + AR.C_REQ + PP.C_PPTO) AS TOTAL FROM\n"
                + "   (SELECT \n"
                + "        COUNT(1) AS C_PROY\n"
                + "    FROM \n"
                + "        POA.PROYECTO_AUTORIZADO\n"
                + "    WHERE \n"
                + "        YEAR = " + year + ") PO,\n"
                + "    (SELECT\n"
                + "        COUNT(1) AS C_META\n"
                + "    FROM \n"
                + "        POA.META_AUTORIZADO\n"
                + "    WHERE \n"
                + "        YEAR = " + year + ") ME,\n"
                + "    (SELECT\n"
                + "        COUNT(1) AS C_EST\n"
                + "    FROM\n"
                + "        DGI.ESTIMACION_AUTORIZADO\n"
                + "    WHERE \n"
                + "        YEAR = " + year + ") EM,\n"
                + "    (SELECT\n"
                + "        COUNT(1) AS C_ACC\n"
                + "    FROM\n"
                + "        POA.ACCION_AUTORIZADO\n"
                + "    WHERE \n"
                + "        YEAR = " + year + ") AC,\n"
                + "    (SELECT\n"
                + "        COUNT(1) AS C_AES\n"
                + "    FROM\n"
                + "        DGI.ACCION_ESTIMACION_AUTORIZADO\n"
                + "    WHERE \n"
                + "        YEAR = " + year + ") AE,\n"
                + "    (SELECT\n"
                + "        COUNT(1) AS C_REQ\n"
                + "    FROM\n"
                + "        POA.ACCION_REQ_AUTORIZADO\n"
                + "    WHERE \n"
                + "        YEAR = " + year + ") AR,\n"
                + "    (SELECT\n"
                + "        COUNT(1) AS C_PPTO\n"
                + "    FROM\n"
                + "        SPP.PPTO_AUTORIZADO\n"
                + "    WHERE \n"
                + "        YEAR = " + year + ") PP  ";
        return strSQL;
    }

    public String getSQLGetRamoByUsuario(int year, String Ramo, String Prg, String Proy, String tipoProy, int Meta) {
        String strSQL = " "
                + " ";
        return strSQL;
    }

    public String getSQLInfoUsuarios() {
        String strSQL = "SELECT "
                + "    USR.APP_LOGIN,USR.NOMBRE,USR.AP_PATER, USR.AP_MATER, USR.RAMO,R.DESCR AS RAMO_DESCR, USR.DEPTO, D.DESCR AS DEPTO_DESCR "
                + "FROM "
                + "    DGI.DGI_USR USR, DGI.RAMOS R,DGI.DEPENDENCIA D "
                + "WHERE "
                + "    USR.APP_LOGIN = ?     \n"
                + "    AND USR.SYS_CLAVE = 1 "
                + "     AND R.RAMO = USR.RAMO\n"
                + "     AND R.YEAR = ? \n"
                + "     AND D.YEAR = R.YEAR\n"
                + "     AND D.RAMO = R.RAMO\n"
                + "     AND D.DEPTO = USR.DEPTO";
        return strSQL;
    }

    public String getSQLGetRamosByUsuario() {
        String strSQL = "SELECT distinct UC.RAMO, R.DESCR  "
                + "FROM  "
                + "    SPP.VW_USUCODIGO UC, DGI.RAMOS R "
                + "WHERE "
                + "    UC.APP_LOGIN = ?  "
                + "    AND UC.YEAR = ? "
                + "    AND R.YEAR = UC.YEAR "
                + "    AND R.RAMO = UC.RAMO "
                + "ORDER BY UC.RAMO ";
        return strSQL;
    }

    public String getSQLGetRamosTransitorioByUsuario() {
        String strSQL = "SELECT distinct UC.RAMO, R.DESCR  "
                + "FROM  "
                + "    SPP.VW_USUCODIGO UC, DGI.RAMOS R "
                + "WHERE "
                + "    UC.APP_LOGIN = ?  "
                + "    AND UC.YEAR = ? "
                + "    AND R.YEAR = UC.YEAR "
                + "    AND R.RAMO = UC.RAMO "
                + "    AND R.TRANSITORIO = ? "
                + "ORDER BY UC.RAMO ";
        return strSQL;
    }

    public String getSQLGetProgramaByRamoUsuario() {
        String strSQL = "SELECT DISTINCT UC.PRG, P.DESCR "
                + "FROM "
                + "    SPP.VW_USUCODIGO UC, S_POA_PROGRAMA P "
                + "WHERE\n"
                + "    UC.APP_LOGIN = ? "
                + "    AND UC.YEAR = ? "
                + "    AND UC.RAMO = ? "
                + "    AND P.YEAR = UC.YEAR "
                + "    AND P.PRG = UC.PRG "
                + "ORDER BY UC.PRG";
        return strSQL;
    }
    /*
     public String getSQLgetProyectoByProgramaUsuario() {
     String strSQL = "SELECT DISTINCT P.PROY,P.TIPO_PROY,P.DESCR "
     + "FROM  "
     + "    SPP.VW_USUCODIGO UC, S_POA_VW_SP_PROY_ACT P "
     + "WHERE "
     + "    UC.APP_LOGIN = ? "
     + "    AND UC.YEAR = ? "
     + "    AND UC.RAMO = ? "
     + "    AND UC.PRG = ? "
     + "    AND P.YEAR = UC.YEAR  "
     + "    AND P.PROY = UC.PROYECTO  "
     + "    AND P.TIPO_PROY = UC.TIPO_PROY "
     + "ORDER BY P.TIPO_PROY,P.PROY";
     return strSQL;
     }
     */

    public String getSQLgetProyectoByProgramaUsuario() {
        String strSQL = "SELECT DISTINCT * FROM (\n"
                + "SELECT \n"
                + "    DISTINCT P.PROY,P.TIPO_PROY,P.DESCR \n"
                + "FROM  \n"
                + "    SPP.VW_USUCODIGO UC, S_POA_VW_SP_PROY_ACT P \n"
                + "WHERE \n"
                + "    UC.APP_LOGIN = ? \n"
                + "    AND UC.YEAR = ? \n"
                + "    AND UC.RAMO = ?  \n"
                + "    AND UC.PRG = ? \n"
                + "    AND P.YEAR = UC.YEAR  \n"
                + "    AND P.PROY = UC.PROYECTO  \n"
                + "    AND P.TIPO_PROY = UC.TIPO_PROY \n"
                + "UNION ALL\n"
                + "SELECT \n"
                + "    DISTINCT P.PROY,P.TIPO_PROY,P.DESCR \n"
                + "FROM  \n"
                + "    POA.PROYECTO UC, S_POA_VW_SP_PROY_ACT P \n"
                + "WHERE \n"
                + "    UC.YEAR = ? \n"
                + "    AND UC.RAMO = ? \n"
                + "    AND UC.PRG = ? \n"
                + "    AND P.YEAR = UC.YEAR  \n"
                + "    AND P.PROY = UC.PROY \n"
                + "    AND P.TIPO_PROY = UC.TIPO_PROY)\n"
                + "ORDER BY PROY, TIPO_PROY";
        return strSQL;
    }

    public String getSQLGetMetasByProyectoUsuario() {
        String strSQL = "SELECT DISTINCT UC.META, M.DESCR "
                + "FROM \n"
                + "    SPP.VW_USUCODIGO UC, S_POA_META M "
                + "WHERE\n"
                + "    UC.APP_LOGIN = ?  "
                + "    AND UC.YEAR = ? "
                + "    AND UC.RAMO = ? "
                + "    AND UC.PRG = ? "
                + "    AND UC.PROYECTO = ? "
                + "    AND UC.TIPO_PROY = ? "
                + "    AND M.YEAR = UC.YEAR "
                + "    AND M.RAMO = UC.RAMO "
                + "    AND M.PRG = UC.PRG "
                + "    AND M.PROY = UC.PROYECTO "
                + "    AND M.TIPO_PROY = UC.TIPO_PROY "
                + "    AND M.META = UC.META "
                + "    AND M.PERIODO IS NOT NULL \n"
                + "ORDER BY UC.META";
        return strSQL;
    }

    public String getSQLGetMetasByProyecto() {
        String strSQL = "SELECT M.META, M.DESCR \n"
                + " FROM \n"
                + "     S_POA_META M \n"
                + " WHERE \n"
                + "     M.YEAR = ? \n"
                + "     AND M.RAMO = ? \n"
                + "     AND M.PRG = ? \n"
                + "     AND M.PROY = ? \n"
                + "     AND M.TIPO_PROY = ? \n"
                + "     AND M.PERIODO IS NOT NULL \n"
                + " ORDER BY M.META";
        return strSQL;
    }

    public String getSQLGetAccionesByMetaUsuario() {
        String strSQL = "SELECT DISTINCT UC.ACCION, A.DESCR "
                + "FROM \n"
                + "    SPP.VW_USUCODIGO UC, S_POA_ACCION A "
                + "WHERE\n"
                + "    UC.APP_LOGIN = ? "
                + "    AND UC.YEAR = ? "
                + "    AND UC.RAMO = ? "
                + "    AND UC.PRG = ? "
                + "    AND UC.PROYECTO = ? "
                + "    AND UC.TIPO_PROY = ? "
                + "    AND UC.META = ? "
                + "    AND A.YEAR = UC.YEAR "
                + "    AND A.RAMO = UC.RAMO "
                + "    AND A.PRG = UC.PRG "
                + "    AND A.META = UC.META "
                + "    AND A.ACCION = UC.ACCION "
                + "    AND A.PERIODO IS NOT NULL  \n"
                + "ORDER BY UC.ACCION";
        return strSQL;
    }

    public String getSQLGetAccionesByMeta() {
        String strSQL = "SELECT DISTINCT A.ACCION, A.DESCR \n"
                + "FROM \n"
                + "    S_POA_ACCION A \n"
                + "WHERE\n"
                + "    A.YEAR = ? \n"
                + "    AND A.RAMO = ? \n"
                + "    AND A.PRG = ? \n"
                + "    AND A.META = ?  \n"
                + "    AND A.PERIODO IS NOT NULL  \n"
                + "ORDER BY A.ACCION";
        return strSQL;
    }

    public String getSQLGetAccionesObraByMetaUsuario() {
        String strSQL = "SELECT DISTINCT UC.ACCION, A.DESCR "
                + "FROM \n"
                + "    SPP.VW_USUCODIGO UC, S_POA_ACCION A "
                + "WHERE\n"
                + "    UC.APP_LOGIN = ? "
                + "    AND UC.YEAR = ? "
                + "    AND UC.RAMO = ? "
                + "    AND UC.PRG = ? "
                + "    AND UC.PROYECTO = ? "
                + "    AND UC.TIPO_PROY = ? "
                + "    AND UC.META = ? "
                + "    AND A.YEAR = UC.YEAR "
                + "    AND A.RAMO = UC.RAMO "
                + "    AND A.PRG = UC.PRG "
                + "    AND A.META = UC.META "
                + "    AND A.ACCION = UC.ACCION "
                + "    AND A.PERIODO IS NOT NULL  \n"
                + "ORDER BY UC.ACCION";
        return strSQL;
    }

    public String getSQLGetPartidaByAccionUsuario() {
        String strSQL = "SELECT DISTINCT UC.PARTIDA, P.DESCR\n"
                + "FROM \n"
                + "    SPP.VW_USUCODIGO UC, DGI.PARTIDA P\n"
                + "WHERE\n"
                + "    UC.APP_LOGIN = ? "
                + "    AND UC.YEAR = ? "
                + "    AND UC.RAMO = ? "
                + "    AND UC.PRG = ? "
                + "    AND UC.PROYECTO = ? "
                + "    AND UC.TIPO_PROY = ? "
                + "    AND UC.META = ? "
                + "    AND UC.ACCION = ? "
                + "    AND P.YEAR = UC.YEAR "
                + "    AND P.PARTIDA = UC.PARTIDA "
                + "ORDER BY UC.PARTIDA";
        return strSQL;
    }

    public String getSQLGetPartidaByAccionAmplRed() {
        String strSQL = "SELECT DISTINCT UC.PARTIDA, P.DESCR\n"
                + "FROM \n"
                + "    SPP.VW_USUCODIGO UC, DGI.PARTIDA P\n"
                + "WHERE\n"
                + "    UC.APP_LOGIN = ? "
                + "    AND UC.YEAR = ? "
                + "    AND UC.RAMO = ? "
                + "    AND UC.PRG = ? "
                + "    AND UC.PROYECTO = ? "
                + "    AND UC.TIPO_PROY = ? "
                + "    AND UC.META = ? "
                + "    AND UC.ACCION = ? "
                + "    AND P.YEAR = UC.YEAR "
                + "    AND P.PARTIDA = UC.PARTIDA "
                + "    AND P.SUBSUBGPO IN (\n"
                + "        SELECT SUBGRUPO FROM DGI.SUBGRUPOS S\n"
                + "        WHERE S.GRUPO <> '60000'\n"
                + "        AND S.YEAR = ?)\n"
                + "ORDER BY UC.PARTIDA";
        return strSQL;
    }

    public String getSQLGetRelLaboralByPartidaUsuario() {
        String strSQL = "SELECT DISTINCT UC.REL_LABORAL, RL.DESCR \n"
                + " FROM \n"
                + "     SPP.VW_USUCODIGO UC,\n"
                + "     S_POA_REL_LABORAL RL,\n"
                + "     DGI.PARTIDA P\n"
                + " WHERE \n"
                + "     UC.APP_LOGIN = ? \n"
                + "     AND UC.YEAR = ?"
                + "     AND UC.RAMO = ? "
                + "     AND UC.PRG = ? "
                + "     AND UC.PROYECTO = ? "
                + "     AND UC.TIPO_PROY = ? "
                + "     AND UC.META = ? "
                + "     AND UC.ACCION = ? "
                + "     AND UC.PARTIDA = ? \n"
                + "     AND RL.YEAR = UC.YEAR \n"
                + "     AND RL.REL_LABORAL = UC.REL_LABORAL\n"
                + "     AND P.YEAR = UC.YEAR\n"
                + "     AND P.PARTIDA = UC.PARTIDA\n"
                + "     AND P.RELACION_LABORAL = 'S'\n"
                + "  ORDER BY UC.REL_LABORAL";
        return strSQL;
    }

    public String getSQLGetFuenteFinanciamientoByPartidaUsuario() {
        String strSQL = "SELECT DISTINCT UC.FUENTE||'.'||UC.FONDO||'.'||UC.RECURSO AS FUENTE, R.DESCR\n"
                + "FROM \n"
                + "    SPP.VW_USUCODIGO UC, S_POA_RECURSO R\n"
                + "WHERE\n"
                + "    UC.APP_LOGIN = ? "
                + "    AND UC.YEAR = ? "
                + "    AND UC.RAMO = ? "
                + "    AND UC.PRG = ? "
                + "    AND UC.PROYECTO = ? "
                + "    AND UC.TIPO_PROY = ? "
                + "    AND UC.META = ? "
                + "    AND UC.ACCION = ? "
                + "    AND UC.PARTIDA = ? "
                + "    AND R.YEAR = UC.YEAR "
                + "    AND R.FUENTE = UC.FUENTE "
                + "    AND R.FONDO = UC.FONDO "
                + "    AND R.RECURSO = UC.RECURSO"
                + " ORDER BY UC.FUENTE||'.'||UC.FONDO||'.'||UC.RECURSO";
        return strSQL;
    }

    public String getSQLGetFuenteFinanciamientoByRelLaboralUsuario() {
        String strSQL = "SELECT DISTINCT UC.FUENTE||'.'||UC.FONDO||'.'||UC.RECURSO AS FUENTE, R.DESCR "
                + "FROM  "
                + "    SPP.VW_USUCODIGO UC, S_POA_RECURSO R\n"
                + "WHERE "
                + "    UC.APP_LOGIN = ? "
                + "    AND UC.YEAR = ? "
                + "    AND UC.RAMO = ? "
                + "    AND UC.PRG = ? "
                + "    AND UC.PROYECTO = ? "
                + "    AND UC.TIPO_PROY = ? "
                + "    AND UC.META = ? "
                + "    AND UC.ACCION = ? "
                + "    AND UC.PARTIDA = ? "
                + "    AND UC.REL_LABORAL = ? "
                + "    AND R.YEAR = UC.YEAR "
                + "    AND R.FUENTE = UC.FUENTE "
                + "    AND R.FONDO = UC.FONDO "
                + "    AND R.RECURSO = UC.RECURSO"
                + " ORDER BY UC.FUENTE||'.'||UC.FONDO||'.'||UC.RECURSO ";
        return strSQL;
    }

    public String getSQLGetRequerimientoByFuenteFinanciamientoUsuario() {
        String strSQL = "SELECT AR.REQUERIMIENTO, AR.DESCR FROM POA.ACCION_REQ AR\n"
                + "WHERE \n"
                + "    AR.YEAR = ? "
                + "    AND AR.RAMO = ? "
                + "    AND AR.PRG = ? "
                + "    AND AR.META = ? "
                + "    AND AR.ACCION = ? "
                + "    AND AR.PARTIDA = ? "
                + "    AND AR.REL_LABORAL = ? "
                + "    AND AR.FUENTE = ? "
                + "    AND AR.FONDO = ? "
                + "    AND AR.RECURSO = ? "
                + "ORDER BY AR.REQUERIMIENTO ";
        return strSQL;
    }

    public String getSQLgetMovEstimacion() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.YEAR \n"
                + "FROM POA.MOVOFICIOS_ESTIMACION ME\n"
                + "WHERE ME.OFICIO = ? "
                + "GROUP BY ME.OFICIO, ME.RAMO, ME.META, ME.YEAR";
        return strSQL;
    }

    public String getSQLgetMovEstimacionCompleto() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO, ME.VALOR, ME.YEAR, ME.VAL_AUTORIZADO "
                + "FROM POA.MOVOFICIOS_ESTIMACION ME "
                + "WHERE ME.OFICIO = ? "
                + "    AND ME.YEAR = ? "
                + "    AND ME.RAMO = ? "
                + "    AND ME.META = ? "
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO";
        return strSQL;
    }

    public String getSQLgetMovEstimacionAmpRed() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO, ME.VALOR, ME.YEAR "
                + "FROM POA.MOVOFICIOS_ESTIMACION ME "
                + "WHERE ME.OFICIO = ? "
                + "    AND ME.YEAR = ? "
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO";
        return strSQL;
    }

    public String getSQLgetMovEstimacionAmpRedRechazado() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO, ME.VALOR, ME.YEAR "
                + "FROM POA.MOVOFICIOS_ESTIMACION ME "
                + "WHERE ME.OFICIO = ? "
                + "    AND ME.YEAR = ? "
                + "    AND ME.RAMO = ? "
                + "    AND ME.META = ? "
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO";
        return strSQL;
    }

    public String getSQLgetMovAccionEstimacion() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.ACCION, ME.YEAR "
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION ME "
                + "WHERE ME.OFICIO = ? "
                + "GROUP BY ME.OFICIO,ME.RAMO, ME.META, ME.ACCION, ME.YEAR ";
        return strSQL;
    }

    public String getSQLgetMovAccionRequerimientoByAmpliacion() {
        String strSQL = "SELECT MAR.OFICIO, MAR.YEAR, MAR.RAMO, MAR.PRG, MAR.DEPTO, MAR.META, MAR.ACCION, "
                + "MAR.REQUERIMIENTO, MAR.DESCR, MAR.FUENTE, MAR.FONDO, MAR.RECURSO, MAR.TIPO_GASTO, "
                + "MAR.PARTIDA, MAR.REL_LABORAL, MAR.CANTIDAD, MAR.COSTO_UNITARIO, MAR.COSTO_ANUAL, "
                + "MAR.ENE,MAR.FEB, MAR.MAR, MAR.ABR, MAR.MAY, MAR.JUN, MAR.JUL, MAR.AGO, MAR.SEP, "
                + "MAR.OCT, MAR.NOV, MAR.DIC, MAR.ARTICULO, R.TRANSITORIO, MAR.JUSTIFICACION \n"
                + "FROM POA.MOVOFICIOS_ACCION_REQ MAR, DGI.RAMOS R\n"
                + "                 WHERE MAR.OFICIO = ? \n"
                + "                     AND MAR.RAMO = ? \n"
                + "                     AND MAR.PRG = ? \n"
                + "                     AND MAR.META = ? \n"
                + "                     AND MAR.ACCION = ? \n"
                + "                     AND MAR.REQUERIMIENTO = ?\n"
                + "                     AND R.YEAR = MAR.YEAR\n"
                + "                     AND R.RAMO = MAR.RAMO "
                + " ORDER BY MAR.OFICIO, MAR.REQUERIMIENTO ";
        return strSQL;
    }

    public String getSQLgetMovAccionRequerimiento() {
        String strSQL = "SELECT * FROM POA.MOVOFICIOS_ACCION_REQ MER "
                + "WHERE MER.OFICIO = ? ";
        return strSQL;
    }

    public String getSQLgetMovAccionEstimacionCompleto() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO, ME.VALOR , ME.YEAR,ME.VAL_AUTORIZADO  "
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION ME  "
                + "WHERE ME.OFICIO = ?  "
                + "    AND ME.YEAR = ? "
                + "    AND ME.RAMO = ? "
                + "    AND ME.META = ? "
                + "    AND ME.ACCION = ? "
                //Se quita el Indicador para evaluar si se usa o no en el sistema
                //+ "    AND ME.IND_TRANSF = ? "
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO, ME.VALOR  ";
        return strSQL;
    }

    public String getSQLgetMovAccionEstimacionAmpRed() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO, ME.VALOR , ME.YEAR  "
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION ME  "
                + "WHERE ME.OFICIO = ?  "
                + "    AND ME.YEAR = ? "
                //Se quita el Indicador para evaluar si se usa o no en el sistema
                //+ "    AND ME.IND_TRANSF = ? "
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO  ";
        return strSQL;
    }

    public String getSQLgetMovAccionEstimacionRechazadoAmpRed() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO, ME.VALOR , ME.YEAR  "
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION ME  "
                + "WHERE ME.OFICIO = ?  "
                + "    AND ME.YEAR = ? "
                + "    AND ME.RAMO = ? "
                + "    AND ME.META = ? "
                + "    AND ME.ACCION = ? "
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO  ";
        return strSQL;
    }

    public String getSQLgetPlainMovoficiosSequenceValue() {
        return "CALL POA.P_REINICIAR_SECUENCIA()";
    }

    public String getSQLgetSequenceMovimientoOficio(boolean isActual) {
        String strSQL = new String();
        if (isActual) {
            strSQL = "SELECT LPAD(POA.SEQ_MOVOFICIOS.NEXTVAL,'6','0') AS SEQ FROM SYS.DUAL";
        } else {
            strSQL = "SELECT LPAD(POA.SEQ_MOVOFICIOS_ANT.NEXTVAL,'6','0') AS SEQ FROM SYS.DUAL";
        }
        return strSQL;
    }

    public String getSQLinsertMovEstimacion(int folio,
            int year,
            String ramo,
            int meta,
            int periodo,
            double valor,
            String nvaCreacion,
            String indTransf,
            String valAutorizado) {
        String strSQL = "INSERT INTO POA.MOVOFICIOS_ESTIMACION E  "
                + "(E.OFICIO,E.YEAR, E.RAMO, E.META, E.PERIODO, E.VALOR, E.NVA_CREACION,E.IND_TRANSF, E.VAL_AUTORIZADO) "
                + "VALUES (" + folio + ", " + year + ", '" + ramo + "', " + meta + ","
                + " " + periodo + ", " + valor + ", '" + nvaCreacion + "' " + ", '" + indTransf + "', '" + valAutorizado + "')";
        return strSQL;
    }

    public String getSQLinsertMovAccionEstimacion(int folio, int year, String ramo, int meta, int accion, int periodo, double valor, String nvaCreacion, String indTransf, String valAutorizado) {
        String strSQL = "INSERT INTO POA.MOVOFICIOS_ACCION_ESTIMACION E "
                + "(E.OFICIO,E.YEAR, E.RAMO, E.META, E.PERIODO, E.VALOR, E.ACCION,E.NVA_CREACION,E.IND_TRANSF, E.VAL_AUTORIZADO) \n"
                + "VALUES (" + folio + ", " + year + ", '" + ramo + "', " + meta + ","
                + " " + periodo + ", " + valor + "," + accion + ", '" + nvaCreacion + "'" + ", '" + indTransf + "', '" + valAutorizado + "')";
        return strSQL;
    }

    public String getSQLinsertMovAccionReq(int folio, int year, String ramo, String prg, String depto, int meta, int accion,
            int req, String descReq, String fuente, String fondo, String recurso, String tipoGasto, String partida,
            String relLaboral, double cantidad, double costoUnitario, double costoAnual, double ene, double feb, double mar,
            double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic,
            String articulo, String nvaCreacion, String justificacion, int consec, String considerar) {

        if (justificacion == null || justificacion.trim().isEmpty()) {
            justificacion = new String();
        }

        String strSQL = "INSERT INTO POA.MOVOFICIOS_ACCION_REQ AR(AR.OFICIO, AR.YEAR,AR.RAMO, AR.PRG, AR.DEPTO, AR.META, AR.ACCION, AR.REQUERIMIENTO,\n"
                + "AR.DESCR, AR.FUENTE, AR.FONDO,AR.RECURSO, AR.TIPO_GASTO, AR.PARTIDA, AR.REL_LABORAL, AR.CANTIDAD, AR.COSTO_UNITARIO, Ar.COSTO_ANUAL,\n"
                + "AR.ENE,AR.FEB,AR.MAR, AR.ABR, AR.MAY, AR.JUN, AR.JUL, AR.AGO, AR.SEP, AR.OCT, AR.NOV, AR.DIC, AR.ARTICULO, AR.NVA_CREACION, AR.JUSTIFICACION,AR.CONSEC,AR.CONSIDERAR)\n"
                + "VALUES\n"
                + "(" + folio + "," + year + ",'" + ramo + "','" + prg + "','" + depto + "'," + meta + "," + accion + "," + req + ",'" + descReq + "','" + fuente + "',"
                + "'" + fondo + "','" + recurso + "','" + tipoGasto + "','" + partida + "','" + relLaboral + "'," + cantidad + "," + costoUnitario + ","
                + " " + costoAnual + "," + ene + "," + feb + "," + mar + "," + abr + "," + may + "," + jun + "," + jul + "," + ago + "," + sep + "," + oct + "," + nov + ","
                + " " + dic + "," + articulo + ",'" + nvaCreacion + "','" + justificacion + "'," + consec + ",'" + considerar + "')";
        return strSQL;
    }

    public String getSQLinsertMovAccionReq() {
        String strSQL = "INSERT INTO POA.MOVOFICIOS_ACCION_REQ AR(AR.OFICIO, AR.YEAR,AR.RAMO, AR.PRG, AR.DEPTO, AR.META, AR.ACCION, AR.REQUERIMIENTO,\n"
                + "AR.DESCR, AR.FUENTE, AR.FONDO,AR.RECURSO, AR.TIPO_GASTO, AR.PARTIDA, AR.REL_LABORAL, AR.CANTIDAD, AR.COSTO_UNITARIO, Ar.COSTO_ANUAL,\n"
                + "AR.ENE,AR.FEB,AR.MAR, AR.ABR, AR.MAY, AR.JUN, AR.JUL, AR.AGO, AR.SEP, AR.OCT, AR.NOV, AR.DIC, AR.ARTICULO, AR.NVA_CREACION, AR.JUSTIFICACION,AR.CONSEC,AR.CONSIDERAR)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return strSQL;
    }

    public String getSQLinsertMovoficio(int oficio, String appLogin, String estatus,
            String tipoMov, String justificacion, String fecha, boolean isActual, String capturaEspecial) {
        String strSQL = new String();
        if (isActual) {
            strSQL = "INSERT INTO POA.MOVOFICIOS M "
                    + "(M.OFICIO, M.APP_LOGIN, M.FECHAELAB, M.STATUS, M.TIPOMOV,M.JUSTIFICACION, M.CAPTURA_ESPECIAL) "
                    + "VALUES "
                    + "(" + oficio + ",'" + appLogin + "',SYSDATE,'" + estatus + "','" + tipoMov + "','" + justificacion + "','" + capturaEspecial + "')";
        } else {
            strSQL = "INSERT INTO POA.MOVOFICIOS M "
                    + "(M.OFICIO, M.APP_LOGIN, M.FECHAELAB, M.STATUS, M.TIPOMOV,M.JUSTIFICACION, M.CAPTURA_ESPECIAL) "
                    + "VALUES "
                    + "(" + oficio + ",'" + appLogin + "',TO_DATE('" + fecha.split("/")[2] + fecha.split("/")[1] + fecha.split("/")[0] + "','YYYYMMDD'),'"
                    + estatus + "','" + tipoMov + "','" + justificacion + "','" + capturaEspecial + "')";
        }
        return strSQL;
    }

    /*public String getSQLUpdateMovOficioRecalendarizacion(int oficio) {
     String strSQL = "UPDATE POA.MOVOFICIOS M "
     + "SET M.FECPPTO = SYSDATE, "
     + "    M.STATUS = 'V' "
     + "WHERE M.OFICIO =  " + oficio;
     return strSQL;
     }*/

    /*public String getSQLUpdateMovOficio(int oficio, String estatus) {
     String strSQL = "UPDATE POA.MOVOFICIOS M "
     + "SET M.FECPPTO = SYSDATE, "
     + "    M.STATUS = '" + estatus + "' "
     + "WHERE M.OFICIO =  " + oficio;
     return strSQL;
     }*/
    public String getSQLUpdateMovOficio(int oficio, String estatus, String fecha, boolean isActual, String capturaEspecial) {
        String strSQL = "UPDATE POA.MOVOFICIOS M "
                + "SET ";
        if (!isActual || capturaEspecial.equals("S")) {
            strSQL += " M.FECPPTO = TO_DATE('" + fecha + "','DD/MM/YYYY'), ";
        } else {
            strSQL += " M.FECPPTO = SYSDATE , ";
        }
        strSQL += "    M.STATUS = '" + estatus + "' "
                + " WHERE M.OFICIO =  " + oficio;
        return strSQL;
    }

    public String getSQLGetFlujoAutorizacion() {
        String strSQL = "SELECT F.ESTATUS, E.DESCR FROM POA.FLUJO_AUTORIZACION F "
                + "INNER JOIN POA.ESTATUS_MOV E ON F.ESTATUS = E.ESTATUS "
                + "WHERE F.ORDEN = NVL(( "
                + "        SELECT ORDEN FROM POA.FLUJO_AUTORIZACION "
                + "        WHERE "
                + "        TIPOMOV = ? "
                + "        AND TIPO_USR = ? "
                + "        AND ESTATUS = ? "
                + "    ), 0) + 1 "
                + "AND F.TIPOMOV = ? "
                + "AND F.TIPO_USR = ? "
                + "AND ? NOT IN ('C', 'A')";
        return strSQL;
    }

    public String getSQLGetTipoMovimiento() {
        String strSQL = "SELECT TIPOMOV, DESCR "
                + "FROM POA.TIPO_MOV "
                + "ORDER BY DESCR ";
        return strSQL;
    }

    public String getSQLTipoFlujoByUsuario(String usuario, String tipoDependencia) {
        String strSQL = "SELECT T.TIPO_FLUJO, T.DESCR, T.ESTATUS_BASE "
                + "FROM  POA.TIPO_FLUJO T "
                + "INNER JOIN POA.FLUJO_FIRMAS F ON T.TIPO_FLUJO = F.TIPO_FLUJO AND TIPO_USR = '" + tipoDependencia + "' "
                + "                   AND SYS_CLAVE = 1 AND APP_LOGIN = '" + usuario + "' ";
        return strSQL;
    }

    public String getSQLMovimientoByTipoMovUsr(String tipoMovimiento, String estatusBase, String appLogin, String tipoOficio, int year, int tipoFlujo, String ramoInList) {
        String strSQL = "SELECT M.OFICIO, M.FECHAELAB, M.APP_LOGIN, M.TIPOMOV, M.STATUS, M.FECPPTO, M.JUSTIFICACION, M.OBS_RECHAZO, U.RAMO "
                + "FROM POA.MOVOFICIOS M , DGI.DGI_USR U     "
                + "WHERE M.TIPOMOV = '" + tipoMovimiento + "' AND M.STATUS = '" + estatusBase + "' "
                + "     AND M.OFICIO NOT IN ( "
                + "        SELECT OFICIO  "
                + "        FROM POA.BITMOVTOS B  "
                + "        WHERE B.TIPO_OFICIO = '" + tipoOficio + "'   "
                + "             AND B.SYS_CLAVE = 1 AND B.APP_LOGIN = '" + appLogin + "'   "
                + "             AND B.TIPO_FLUJO = " + tipoFlujo + "   "
                + "     )     "
                + "     AND EXTRACT (YEAR FROM FECHAELAB) = " + year + "     "
                + "     AND TRIM(U.APP_LOGIN) = TRIM(M.APP_LOGIN)     "
                + "     AND U.SYS_CLAVE = 1    "
                + "     AND U.RAMO IN (" + ramoInList + ")    "
                + "ORDER BY OFICIO";
        return strSQL;
    }

    public String getSQLObtenerTipoFlujoinicial(String tipoMov, String tipoUsr) {
        String strSQL = "SELECT TIPO_FLUJO "
                + "FROM POA.FLUJO_AUTORIZACION "
                + "WHERE TIPOMOV = '" + tipoMov + "' "
                + "    AND TIPO_USR = '" + tipoUsr + "' "
                + "    AND ORDEN = 1 ";
        return strSQL;
    }

    public String getSQLEstatusSiguiente(String tipoMov, String tipoUsr, String estatusActual) {
        String strSQL = " SELECT F.ESTATUS, E.DESCR FROM POA.FLUJO_AUTORIZACION F "
                + " INNER JOIN POA.ESTATUS_MOV E ON F.ESTATUS = E.ESTATUS "
                + " WHERE F.ORDEN = NVL(( "
                + "        SELECT ORDEN FROM POA.FLUJO_AUTORIZACION  "
                + "        WHERE  "
                + "        TIPOMOV = '" + tipoMov + "' "
                + "        AND TIPO_USR = '" + tipoUsr + "' "
                + "        AND ESTATUS = '" + estatusActual + "' "
                + "        AND ORDEN <> 0 "
                + "    ), 0) + 1 "
                + " AND F.TIPOMOV = '" + tipoMov + "' "
                + " AND F.TIPO_USR = '" + tipoUsr + "' "
                + " AND '" + estatusActual + "' NOT IN ('C', 'A')";

        return strSQL;
    }

    public String getSQLEstatusMovimientos() {
        String strSQL = " "
                + "SELECT "
                + "	ESTMOV.ESTATUS, "
                + "	ESTMOV.DESCR "
                + "FROM  "
                + "	POA.ESTATUS_MOV ESTMOV "
                + "ORDER BY ORDEN ";

        return strSQL;
    }

    public String getSQLUpdateEstatusMov(String estatus, int folio) {
        String strSQL = "UPDATE POA.MOVOFICIOS "
                + "SET STATUS = '" + estatus + "' "
                + "WHERE OFICIO = " + folio;

        return strSQL;
    }

    public String getSQLInsertBitMovto(int oficio, String appLogin, String autorizo,
            String impFirma, String tipoOficio, String terminal, int tipoFlujo, String tipoUsr) {
        String strSQL = "INSERT INTO POA.BITMOVTOS(OFICIO, APP_LOGIN, FECHAAUT, "
                + "AUTORIZO, IMP_FIRMA, TIPO_OFICIO, TERMINAL, SYS_CLAVE, "
                + "TIPO_FLUJO, TIPO_USR,FECPPTO) "
                + "VALUES(" + oficio + ",'" + appLogin + "', SYSDATE, "
                + "'" + autorizo + "', '" + impFirma + "', '" + tipoOficio + "', '" + terminal + "', 1, "
                + "" + tipoFlujo + ", '" + tipoUsr + "',(SELECT FECPPTO FROM POA.MOVOFICIOS WHERE OFICIO = " + oficio + "))";
        return strSQL;
    }

    public String getSQLDeleteBitMovtoByOficio(int oficio) {
        String strSQL = "DELETE FROM POA.BITMOVTOS "
                + "WHERE OFICIO = " + oficio;
        return strSQL;
    }

    public String getSQLDeleteBitMovtoByOficioTipoOficio(int oficio, String tipoOficio, int tipoFlujo) {
        String strSQL = "DELETE FROM POA.BITMOVTOS "
                + "WHERE OFICIO = " + oficio + " "
                + "     AND TIPO_OFICIO = '" + tipoOficio + "'"
                + "     AND TIPO_FLUJO >= " + tipoFlujo;
        return strSQL;
    }

    public String getSQLUsuariosAFirmar(int tipoFlujo, String tipoUsr) {
        String strSQL = "SELECT APP_LOGIN, ORDEN FROM POA.FLUJO_FIRMAS "
                + "WHERE TIPO_USR = '" + tipoUsr + "' "
                + "    AND TIPO_FLUJO = " + tipoFlujo
                + "    AND SYS_CLAVE = 1 ";

        return strSQL;
    }

    public String getSQLValidaFirma(int oficio, int tipoFlujo, String appLogin) {
        String strSQL = "SELECT COUNT(APP_LOGIN) AS CUANTOS "
                + "FROM POA.BITMOVTOS "
                + "WHERE OFICIO = " + oficio
                + "   AND TIPO_FLUJO = " + tipoFlujo
                + "   AND APP_LOGIN = '" + appLogin + "'"
                + "   AND AUTORIZO = 'A'";

        return strSQL;
    }

    public String getSQLMovimientoByFolio(int folio) {
        String strSQL = "SELECT OFICIO, FECHAELAB, APP_LOGIN, TIPOMOV, STATUS, FECPPTO, JUSTIFICACION, OBS_RECHAZO "
                + "FROM POA.MOVOFICIOS "
                + "WHERE oficio = " + folio;
        return strSQL;
    }

    public String getSQLTipoFlujoByTipoMov(String tipoMov, String tipoUsr, String estatus) {
        String strSQL = "SELECT TIPO_FLUJO FROM POA.FLUJO_AUTORIZACION "
                + "WHERE TIPOMOV = '" + tipoMov + "' "
                + "    AND TIPO_USR = '" + tipoUsr + "' "
                + "   AND ESTATUS = '" + estatus + "' ";
        return strSQL;
    }
    /*
     public String getSQLEstatusSiguiente(String tipoMov, String tipoUsr, String estatusActual){
     String strSQL = " SELECT F.ESTATUS, E.DESCR FROM POA.FLUJO_AUTORIZACION F " +
     " INNER JOIN POA.ESTATUS_MOV E ON F.ESTATUS = E.ESTATUS " +
     " WHERE F.ORDEN = NVL(( " +
     "        SELECT ORDEN FROM POA.FLUJO_AUTORIZACION  " +
     "        WHERE  " +
     "        TIPOMOV = '" + tipoMov + "' " +
     "        AND TIPO_USR = '" + tipoUsr + "' " +
     "        AND ESTATUS = '" + estatusActual + "' " +
     "        AND ORDEN <> 0 " +
     "    ), 0) + 1 " +
     " AND F.TIPOMOV = '" + tipoMov + "' " +
     " AND F.TIPO_USR = '" + tipoUsr + "' " +
     " AND '" + estatusActual + "' NOT IN ('C', 'A') ";        
     return strSQL;
     } 
     */

    public String getSQLExistenMovOficiosByRamoMetaAccion(String ramo, int meta, int accion) {
        String strSQL = ""
                + "SELECT "
                + "	nvl(max(count(MOVOF.OFICIO)),0) EXISTEN "
                + "FROM "
                + "	POA.MOVOFICIOS MOVOF, "
                + "	(	 "
                + "		SELECT "
                + "			OFICIOS.OFICIO "
                + "		FROM "
                + "			( "
                + "				( "
                + "					SELECT "
                + "						MOVOFEST.OFICIO "
                + "					FROM  "
                + "						POA.MOVOFICIOS_ESTIMACION MOVOFEST "
                + "					WHERE  "
                + "						MOVOFEST.RAMO = '" + ramo + "' AND "
                + "						MOVOFEST.META = " + meta + " "
                + "					GROUP BY  "
                + "						MOVOFEST.OFICIO "
                + "				) "
                + "				UNION ALL "
                + "				( "
                + "					SELECT "
                + "						MOVOFACCEST.OFICIO "
                + "					FROM "
                + "						POA.MOVOFICIOS_ACCION_ESTIMACION MOVOFACCEST "
                + "					WHERE "
                + "						MOVOFACCEST.RAMO = '" + ramo + "' AND "
                + "						MOVOFACCEST.META = " + meta + " AND "
                + "						MOVOFACCEST.ACCION = " + accion + " "
                + "					GROUP BY "
                + "						MOVOFACCEST.OFICIO "
                + "				)  "
                + "			) OFICIOS "
                + "		GROUP BY "
                + "			OFICIOS.OFICIO "
                + "	) OFACCMET "
                + "WHERE "
                + "	MOVOF.OFICIO = OFACCMET.OFICIO AND "
                + "	MOVOF.STATUS <> 'A' AND "
                + "	MOVOF.STATUS <> 'C' "
                + "GROUP BY "
                + "	MOVOF.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLInsertHistCalendarizacionMeta(int oficio) {
        String strSQL = "INSERT INTO POA.HIST_ESTIMACION(OFICIO, META, RAMO, YEAR, PERIODO, VALOR) "
                + "SELECT " + oficio + ", E.META, E.RAMO, E.YEAR, E.PERIODO, E.VALOR "
                + "FROM DGI.ESTIMACION E "
                + "INNER JOIN POA.MOVOFICIOS_ESTIMACION M ON E.META  = M.META AND  "
                + "       E.PERIODO = M.PERIODO AND E.RAMO = M.RAMO AND E.YEAR = M.YEAR AND (NVA_CREACION IS NULL OR NVA_CREACION = 'N')  "
                + "WHERE M.OFICIO =  " + oficio;
        return strSQL;
    }

    public String getSQLInsertHistCalendarizacionAccion(int oficio) {
        String strSQL = "INSERT INTO POA.HIST_ACCION_ESTIMACION(OFICIO, META, RAMO, YEAR, ACCION, PERIODO, VALOR) "
                + "SELECT " + oficio + ", E.META, E.RAMO, E.YEAR, E.ACCION, E.PERIODO, E.VALOR "
                + "FROM DGI.ACCION_ESTIMACION E "
                + "INNER JOIN POA.MOVOFICIOS_ACCION_ESTIMACION M ON E.META  = M.META AND E.ACCION = M.ACCION AND "
                + "       E.PERIODO = M.PERIODO AND E.RAMO = M.RAMO AND E.YEAR = M.YEAR AND (NVA_CREACION IS NULL OR NVA_CREACION = 'N') "
                + "WHERE M.OFICIO = " + oficio;
        return strSQL;
    }

    public String getSQLInsertHistCalendarizacionRequerimiento(int oficio) {
        String strSQL = "INSERT INTO POA.HIST_ACCION_REQ(OFICIO, YEAR, RAMO, PRG, DEPTO, META, ACCION, REQUERIMIENTO, DESCR, FUENTE, TIPO_GASTO, "
                + "   PARTIDA, REL_LABORAL, CANTIDAD, COSTO_UNITARIO, COSTO_ANUAL, ENE, FEB, MAR, ABR, MAY, JUN, JUL,  "
                + "   AGO, SEP, OCT, NOV, DIC, ARTICULO, CANTIDAD_ORG, COSTO_UNITARIO_ORG, ARCHIVO, JUSTIFICACION, FONDO,  "
                + "   RECURSO)       "
                + " SELECT " + oficio + ", R.YEAR, R.RAMO, R.PRG, R.DEPTO, R.META, R.ACCION, R.REQUERIMIENTO, R.DESCR, R.FUENTE, R.TIPO_GASTO, "
                + "   R.PARTIDA, R.REL_LABORAL, R.CANTIDAD, R.COSTO_UNITARIO, R.COSTO_ANUAL, R.ENE, R.FEB, R.MAR, R.ABR, R.MAY, R.JUN, R.JUL,  "
                + "   R.AGO, R.SEP, R.OCT, R.NOV, R.DIC, R.ARTICULO, R.CANTIDAD_ORG, R.COSTO_UNITARIO_ORG, R.ARCHIVO, R.JUSTIFICACION, R.FONDO,  "
                + "   R.RECURSO "
                + " FROM POA.ACCION_REQ R "
                + " INNER JOIN POA.MOVOFICIOS_ACCION_REQ M ON R.YEAR = M.YEAR AND R.RAMO = M.RAMO AND R.PRG = M.PRG "
                + "     AND R.DEPTO = M.DEPTO AND R.META = M.META AND R.ACCION = M.ACCION AND R.REQUERIMIENTO = M.REQUERIMIENTO AND (NVA_CREACION IS NULL OR NVA_CREACION = 'N')  "
                + " WHERE M.OFICIO =  " + oficio;
        return strSQL;
    }

    public String getSQLUpdateCalendarizacionMeta(int oficio) {
        String strSQL = "UPDATE DGI.ESTIMACION a SET "
                + " (a.VALOR) = ( SELECT  "
                + "  b.VALOR "
                + "FROM POA.MOVOFICIOS_ESTIMACION b "
                + "WHERE b.OFICIO = " + oficio + " AND "
                + "      b.META  = a.META AND  "
                + "      b.PERIODO = a.PERIODO AND  "
                + "      b.RAMO = a.RAMO AND  "
                + "      b.YEAR = a.YEAR AND "
                + "      (b.NVA_CREACION IS NULL OR b.NVA_CREACION = 'N') ) "
                + "WHERE EXISTS (  SELECT 1 "
                + "                FROM POA.MOVOFICIOS_ESTIMACION c "
                + "                WHERE c.OFICIO = " + oficio + " AND "
                + "                      c.META  = a.META AND  "
                + "                      c.PERIODO = a.PERIODO AND  "
                + "                      c.RAMO = a.RAMO AND  "
                + "                      c.YEAR = a.YEAR AND "
                + "                      (c.NVA_CREACION IS NULL OR c.NVA_CREACION = 'N') ) ";
        return strSQL;
    }

    public String getSQLUpdateCalendarizacionAccion(int oficio) {
        String strSQL = "UPDATE DGI.ACCION_ESTIMACION a SET "
                + "(a.VALOR) = ( SELECT  "
                + " b.VALOR "
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION b "
                + "WHERE b.OFICIO = " + oficio + " AND "
                + "      b.META  = a.META AND  "
                + "      b.ACCION = a.ACCION AND "
                + "      b.PERIODO = a.PERIODO AND  "
                + "      b.RAMO = a.RAMO AND  "
                + "      b.YEAR = a.YEAR  AND "
                + "      (b.NVA_CREACION IS NULL OR b.NVA_CREACION = 'N') ) "
                + "WHERE EXISTS (  SELECT 1 "
                + "                FROM POA.MOVOFICIOS_ACCION_ESTIMACION c "
                + "                WHERE c.OFICIO = " + oficio + " AND "
                + "                      c.META  = a.META AND  "
                + "                      c.ACCION = a.ACCION AND "
                + "                      c.PERIODO = a.PERIODO AND  "
                + "                      c.RAMO = a.RAMO AND  "
                + "                      c.YEAR = a.YEAR AND "
                + "                      (c.NVA_CREACION IS NULL OR c.NVA_CREACION = 'N') ) ";
        return strSQL;
    }

    public String getSQLUpdateCalendarizacionRequerimiento(int oficio) {
        String strSQL = "UPDATE POA.ACCION_REQ a SET "
                + "(a.COSTO_UNITARIO, "
                + " a.ENE,     "
                + " a.FEB, "
                + " a.MAR, "
                + " a.ABR, "
                + " a.MAY,  "
                + " a.JUN, "
                + " a.JUL, "
                + " a.AGO, "
                + " a.SEP, "
                + " a.OCT, "
                + " a.NOV, "
                + " a.DIC) = ( SELECT  "
                + " b.COSTO_UNITARIO, "
                + " b.ENE,     "
                + " b.FEB, "
                + " b.MAR, "
                + " b.ABR, "
                + " b.MAY,  "
                + " b.JUN, "
                + " b.JUL, "
                + " b.AGO, "
                + " b.SEP, "
                + " b.OCT, "
                + " b.NOV, "
                + " b.DIC "
                + "FROM POA.MOVOFICIOS_ACCION_REQ b "
                + "WHERE b.OFICIO = " + oficio + " AND "
                + "      b.YEAR = a.YEAR   AND  "
                + "      b.RAMO = a.RAMO   AND  "
                + "      b.PRG = a.PRG     AND  "
                + "      b.DEPTO = a.DEPTO AND "
                + "      b.META = a.META   AND "
                + "      b.ACCION = a.ACCION AND "
                + "      b.REQUERIMIENTO = a.REQUERIMIENTO  AND "
                + "      (b.NVA_CREACION IS NULL OR b.NVA_CREACION = 'N') ) "
                + "WHERE EXISTS (  SELECT 1 "
                + "                FROM POA.MOVOFICIOS_ACCION_REQ c "
                + "                WHERE c.OFICIO = " + oficio + " AND "
                + "                      c.YEAR = a.YEAR   AND  "
                + "                      c.RAMO = a.RAMO   AND  "
                + "                      c.PRG = a.PRG     AND  "
                + "                      c.DEPTO = a.DEPTO AND "
                + "                      c.META = a.META   AND "
                + "                      c.ACCION = a.ACCION AND "
                + "                      c.REQUERIMIENTO = a.REQUERIMIENTO  AND "
                + "                      (c.NVA_CREACION IS NULL OR c.NVA_CREACION = 'N') ) ";
        return strSQL;
    }

    public String getSQLFirmantesAnteriores(String tipoMov, String estatusBase, String appLogin, String tipoUsr) {
        String strSQL = "SELECT F.TIPO_USR, F.TIPO_FLUJO, F.SYS_CLAVE, F.APP_LOGIN, F.ORDEN "
                + "FROM POA.FLUJO_AUTORIZACION A "
                + "INNER JOIN POA.FLUJO_FIRMAS F ON A.TIPO_USR = F.TIPO_USR AND A.TIPO_FLUJO = F.TIPO_FLUJO "
                + "WHERE A.TIPOMOV = '" + tipoMov + "'  "
                + "       AND A.ESTATUS = '" + estatusBase + "' "
                + "       AND A.TIPO_USR = '" + tipoUsr + "' "
                + "       AND F.APP_LOGIN <> '" + appLogin + "' "
                + "       AND F.ORDEN < ( "
                + "        SELECT F.ORDEN  "
                + "       FROM POA.FLUJO_AUTORIZACION A "
                + "        INNER JOIN POA.FLUJO_FIRMAS F ON A.TIPO_USR = F.TIPO_USR AND  "
                + "                A.TIPO_FLUJO = F.TIPO_FLUJO AND F.APP_LOGIN = '" + appLogin + "' "
                + "       WHERE A.TIPOMOV = '" + tipoMov + "'  "
                + "              AND A.ESTATUS = '" + estatusBase + "' "
                + "              AND A.TIPO_USR = '" + tipoUsr + "' "
                + "      )";
        return strSQL;
    }

    public String getSQLUpdateEstatusMotivoMov(String estatus, String motivo, int folio) {
        String strSQL = "UPDATE POA.MOVOFICIOS "
                + "SET STATUS = '" + estatus + "', "
                + "OBS_RECHAZO = '" + motivo + "' "
                + "WHERE OFICIO = " + folio;

        return strSQL;
    }

    public String getSQLMovimientoByTipoMov(String tipoMovimiento, String estatusBase, int year) {
        String strSQL = " "
                + " SELECT  "
                + "	MOVOF.OFICIO,  "
                + "	MOVOF.FECHAELAB,  "
                + "	MOVOF.APP_LOGIN,  "
                + "	MOVOF.TIPOMOV,  "
                + "	MOVOF.STATUS,  "
                + "	MOVOF.FECPPTO,  "
                + "	MOVOF.JUSTIFICACION,  "
                + "	MOVOF.OBS_RECHAZO, "
                //+ "	TIPOF.TIPO,     "
                + "     R.DESCR AS RAMO_DESCR, "
                + "     E.DESCR AS STATUSDESCR   "
                + " FROM   "
                + "	POA.MOVOFICIOS MOVOF,  "
                //+ "	DGI.TIPOFICIO  TIPOF,   "
                + "     POA.ESTATUS_MOV E,   "
                + "     DGI.DGI_USR U, "
                + "     DGI.RAMOS R "
                + " WHERE  "
                + "	MOVOF.TIPOMOV = '" + tipoMovimiento + "' AND   "
                + "	MOVOF.STATUS = '" + estatusBase + "' AND "
                + "     MOVOF.STATUS = E.ESTATUS AND   "
                + "     TRIM(MOVOF.APP_LOGIN) = TRIM(U.APP_LOGIN) AND"
                + "     R.RAMO = U.RAMO AND "
                + "     U.SYS_CLAVE = 1 AND "
                + "     R.YEAR = " + year + " AND  "
                + "	EXTRACT(YEAR FROM MOVOF.FECHAELAB) = " + year //+ " AND  "
                //+ "	TIPOF.OFICIO (+)= MOVOF.OFICIO  "
                + " ORDER BY   "
                + "	MOVOF.OFICIO,  "
                + "	MOVOF.STATUS,  "
                + "	MOVOF.TIPOMOV  "//,  "
                //+ "	TIPOF.TIPO  "
                + " ";
        return strSQL;
    }

    public String getSQLMovimientoByTipoMovEstatusMovAppLogin() {
        String strSQL = "SELECT \n"
                + "    MOVOF.OFICIO,  \n"
                + "    MOVOF.FECHAELAB,  \n"
                + "    MOVOF.APP_LOGIN,  \n"
                + "    MOVOF.TIPOMOV,  \n"
                + "    MOVOF.STATUS,  \n"
                + "    MOVOF.FECPPTO,  \n"
                + "    MOVOF.JUSTIFICACION,  \n"
                + "    MOVOF.OBS_RECHAZO, \n"
                + "    R.DESCR AS RAMO_DESCR, \n"
                + "    E.DESCR AS STATUSDESCR,\n"
                + "    U.RAMO\n"
                + "FROM   \n"
                + "    POA.MOVOFICIOS MOVOF,    \n"
                + "    POA.ESTATUS_MOV E,   \n"
                + "    DGI.DGI_USR U, \n"
                + "    DGI.RAMOS R \n"
                + "WHERE  \n"
                + "    MOVOF.TIPOMOV = ? AND   \n"
                + "    MOVOF.STATUS = ? AND   \n"
                + "    EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? AND   \n"
                + "    TRIM(MOVOF.APP_LOGIN) IN \n"
                + "        (SELECT UT.APP_LOGIN FROM DGI.DGI_USR UT WHERE UT.RAMO = U.RAMO AND UT.SYS_CLAVE = 1) AND\n"
                + "    U.APP_LOGIN = ? AND    \n"
                + "    U.SYS_CLAVE = 1 AND \n"
                + "    R.YEAR = ? AND \n"
                + "    R.RAMO = U.RAMO AND\n"
                + "    E.ESTATUS = MOVOF.STATUS\n"
                + "ORDER BY   \n"
                + "    MOVOF.OFICIO,  \n"
                + "    MOVOF.STATUS,  \n"
                + "    MOVOF.TIPOMOV";
        return strSQL;
    }

    public String getSQLgetServerDate() {
        return "SELECT SYSDATE FROM SYS.DUAL";
    }

    public String getSQLcountMovAccionRequerimiento() {
        String strSQL = "SELECT "
                + "    count(1) AS COUNT "
                + "FROM\n"
                + "    POA.MOVOFICIOS MOVOF,\n"
                + "    (\n"
                + "        SELECT\n"
                + "            MAR.OFICIO\n"
                + "        FROM \n"
                + "            POA.MOVOFICIOS_ACCION_REQ MAR\n"
                + "        WHERE \n"
                + "            MAR.RAMO = ?  "
                + "            AND MAR.PRG = ? "
                + "            AND MAR.META = ? "
                + "            AND MAR.ACCION = ? "
                + "            AND MAR.PARTIDA = ? "
                + "            AND MAR.FUENTE = ?  "
                + "            AND MAR.FONDO = ? "
                + "            AND MAR.RECURSO = ? "
                + "            AND MAR.REQUERIMIENTO = ? "
                + "    ) MAR\n"
                + "WHERE \n"
                + "    MOVOF.OFICIO = MAR.OFICIO AND\n"
                + "    MOVOF.STATUS <> 'A' AND \n"
                + "    MOVOF.STATUS <> 'C'";
        return strSQL;
    }

    public String getSQLGetMetaByRamoMetaYear(String ramoId, int metaId, int year) {
        String strSQL = ""
                + "SELECT  "
                + "	MET.YEAR, "
                + "	RAM.RAMO, "
                + "	RAM.DESCR RAMO_DESCR, "
                + "	PRG.PRG, "
                + "	PRG.DESCR PRG_DESCR, "
                + "	MET.META, "
                + "	MET.DESCR META_DESCR, "
                + "	MET.MEDIDA, "
                + "	MET.CALCULO, "
                + "	MET.TIPO, "
                + "	MET.CVE_MEDIDA, "
                + "	MET.FINALIDAD, "
                + "	MET.FUNCION, "
                + "	MET.SUBFUNCION, "
                + "	PROYACT.TIPO_PROY, "
                + "	PROYACT.PROY, "
                + "	PROYACT.DESCR PROY_DESCR, "
                + "	MET.DEPTO, "
                + "	MET.CLAVE, "
                + "	MET.LINEA, "
                + "	MET.TIPO_COMPROMISO, "
                + "	MET.BENEFICIADOS, "
                + "	MET.PRESUPUESTAR, "
                + "	MET.APROB_CONGRESO, "
                + "	MET.CONVENIO, "
                + "	MET.CONV, "
                + "	MET.BENEF_HOMBRE, "
                + "	MET.BENEF_MUJER, "
                + "	MET.GENERO, "
                + "	MET.PRINCIPAL, "
                + "	MET.RELATORIA, "
                + "	MET.OBRA, "
                + "	MET.DESCR_CORTA, "
                + "	MET.MAYOR_COSTO, "
                + "	MET.FICHA_TECNICA, "
                + "	MET.CRITERIO, "
                + "	MET.OBJ, "
                + "	MET.OBJ_META, "
                + "	MET.PONDERADO, "
                + "	MET.LINEA_SECTORIAL, "
                + "	MET.PROCESO_AUTORIZACION  "
                + "FROM  "
                + "	S_POA_META MET, "
                + "	DGI.RAMOS RAM, "
                + "	S_POA_PROGRAMA PRG, "
                + "	S_POA_VW_SP_PROY_ACT PROYACT "
                + "WHERE  "
                + "	MET.YEAR = " + year + " AND "
                + "	MET.RAMO = '" + ramoId + "' AND "
                + "	MET.META = " + metaId + " AND     "
                + "	RAM.YEAR = MET.YEAR AND "
                + "	RAM.RAMO = MET.RAMO AND    "
                + "	PRG.YEAR = MET.YEAR AND "
                + "	PRG.PRG = MET.PRG AND    "
                + "	PROYACT.YEAR = MET.YEAR AND "
                + "	PROYACT.PROY = MET.PROY AND "
                + "	PROYACT.TIPO_PROY = MET.TIPO_PROY "
                + "";
        return strSQL;
    }

    public String getSQLGetAccionByYearRamoMetaAccion(int year, String ramoId, int metaId, int accionId) {
        String strSQL = " "
                + "SELECT "
                + "	ACC.ACCION, "
                + "	ACC.DESCR ACC_DESCR, "
                + "	RAM.RAMO, "
                + "	RAM.DESCR RAMO_DESCR, "
                + "	PRG.PRG, "
                + "	PRG.DESCR PRG_DESCR, "
                + "	MET.META, "
                + "	MET.DESCR MET_DESCR, "
                + "	PROYACT.TIPO_PROY, "
                + "	PROYACT.PROY, "
                + "	PROYACT.DESCR PROY_DESCR, "
                + "	ACC.CALCULO, "
                + "	ACC.CVE_MEDIDA "
                + "FROM  "
                + "	S_POA_ACCION ACC, "
                + "	DGI.RAMOS RAM, "
                + "	S_POA_PROGRAMA PRG, "
                + "	S_POA_META MET, "
                + "	S_POA_VW_SP_PROY_ACT PROYACT "
                + "WHERE  "
                + "	ACC.YEAR = " + year + " AND "
                + "	ACC.RAMO = '" + ramoId + "' AND "
                + "	ACC.META = " + metaId + " AND "
                + "	ACC.ACCION = " + accionId + " AND     "
                + "	RAM.YEAR = ACC.YEAR AND "
                + "	RAM.RAMO = ACC.RAMO AND     "
                + "	PRG.YEAR = ACC.YEAR AND "
                + "	PRG.PRG =  ACC.PRG AND     "
                + "	MET.YEAR = ACC.YEAR AND "
                + "	MET.RAMO = ACC.RAMO AND "
                + "	MET.PRG = ACC.PRG AND "
                + "	MET.META = ACC.META AND     "
                + "	PROYACT.YEAR = MET.YEAR AND "
                + "	PROYACT.TIPO_PROY = MET.TIPO_PROY AND "
                + "	PROYACT.PROY = MET.PROY "
                + " ";
        return strSQL;
    }

    public String getSQLgetRequerimientoByIdUsuario() {
        String strSQL = "SELECT AR.RAMO, \n"
                + "AR.RAMO||'-'||R.DESCR AS RAMO_D, \n"
                + "AR.PRG, \n"
                + "AR.PRG||'-'||P.DESCR AS PROGRAMA_D, \n"
                + "PA.TIPO_PROY,\n"
                + "PA.PROY, \n"
                + "PA.TIPO_PROY||PA.PROY||'-'||PA.DESCR AS PROYECTO_D,\n"
                + "AR.META,\n"
                + "AR.META||'-'||M.DESCR AS META_D, \n"
                + "AR.REQUERIMIENTO,\n"
                + "AR.REQUERIMIENTO||'-'||AR.DESCR AS REQUERIMIENTO_D, \n"
                + "AR.ACCION, AR.ACCION||'-'||AC.DESCR AS ACCION_D,\n"
                + "AR.PARTIDA,\n"
                + "    AR.PARTIDA||'-'||PAR.DESCR AS PARTIDA_D,\n"
                + "    AR.REL_LABORAL,\n"
                + "    AR.REL_LABORAL||'-'||RL.DESCR AS REL_LABORAL_D,\n"
                + "    AR.FUENTE||'.'||AR.FONDO||'.'||AR.RECURSO AS FUENTE,\n"
                + "    AR.FUENTE||'.'||AR.FONDO||'.'||AR.RECURSO||'-'||REC.DESCR AS FUENTE_D "
                + "FROM \n"
                + "    POA.ACCION_REQ AR,\n"
                + "    S_POA_ACCION AC, \n"
                + "    S_POA_META M, \n"
                + "    S_POA_VW_SP_PROY_ACT PA, \n"
                + "    S_POA_PROGRAMA P, \n"
                + "    DGI.RAMOS R, \n"
                + "    DGI.PARTIDA PAR,\n"
                + "    S_POA_REL_LABORAL RL,\n"
                + "    S_POA_RECURSO REC\n"
                + "WHERE\n"
                + "    AR.YEAR = ? "
                + "    AND AR.RAMO = ? "
                + "    AND AR.PRG = ? "
                + "    AND AR.META = ? "
                + "    AND AR.ACCION = ? "
                + "    AND AR.REQUERIMIENTO = ? "
                + "    AND M.YEAR = AR.YEAR\n"
                + "    AND M.RAMO = AR.RAMO\n"
                + "    AND M.META = AR.META\n"
                + "    AND AC.YEAR = AR.YEAR\n"
                + "    AND AC.RAMO = AR.RAMO\n"
                + "    AND AC.META = AR.META\n"
                + "    AND AC.ACCION = AR.ACCION\n"
                + "    AND PA.YEAR =AR.YEAR\n"
                + "    AND PA.PROY = M.PROY\n"
                + "    AND PA.TIPO_PROY = M.TIPO_PROY\n"
                + "    AND P.YEAR = AR.YEAR\n"
                + "    AND P.PRG = AR.PRG\n"
                + "    AND R.YEAR = AR.YEAR\n"
                + "    AND R.RAMO = AR.RAMO\n"
                + "    AND PAR.YEAR = AR.YEAR\n"
                + "    AND PAR.PARTIDA = AR.PARTIDA\n"
                + "    AND RL.YEAR = AR.YEAR\n"
                + "    AND RL.REL_LABORAL = AR.REL_LABORAL\n"
                + "    AND REC.YEAR = AR.YEAR\n"
                + "    AND REC.FUENTE = AR.FUENTE\n"
                + "    AND REC.FONDO = AR.FONDO\n"
                + "    AND REC.RECURSO = AR.RECURSO";
        return strSQL;
    }

    public String getSQLgetRequerimientoByDatosMovtos() {
        String strSQL = "SELECT AC.RAMO, \n"
                + "    AC.RAMO||'-'||R.DESCR AS RAMO_D, \n"
                + "    AC.PRG, \n"
                + "    AC.PRG||'-'||P.DESCR AS PROGRAMA_D, \n"
                + "    PA.TIPO_PROY,\n"
                + "    PA.PROY, \n"
                + "    PA.TIPO_PROY||PA.PROY||'-'||PA.DESCR AS PROYECTO_D,\n"
                + "    AC.META,\n"
                + "    AC.META||'-'||M.DESCR AS META_D,\n"
                + "    AC.ACCION, AC.ACCION||'-'||AC.DESCR AS ACCION_D,\n"
                + "    PAR.PARTIDA,\n"
                + "    PAR.PARTIDA||'-'||PAR.DESCR AS PARTIDA_D,\n"
                + "    RL.REL_LABORAL,\n"
                + "    RL.REL_LABORAL||'-'||RL.DESCR AS REL_LABORAL_D,\n"
                + "    REC.FUENTE||'.'||REC.FONDO||'.'||REC.RECURSO AS FUENTE,\n"
                + "    REC.FUENTE||'.'||REC.FONDO||'.'||REC.RECURSO||'-'||REC.DESCR AS FUENTE_D \n"
                + "FROM \n"
                + "    S_POA_ACCION AC, \n"
                + "    S_POA_META M, \n"
                + "    S_POA_VW_SP_PROY_ACT PA, \n"
                + "    S_POA_PROGRAMA P, \n"
                + "    DGI.RAMOS R, \n"
                + "    DGI.PARTIDA PAR,\n"
                + "    S_POA_REL_LABORAL RL,\n"
                + "    S_POA_RECURSO REC\n"
                + "WHERE\n"
                + "    AC.YEAR = ? \n"
                + "    AND AC.RAMO =? \n"
                + "    AND AC.PRG = ? \n"
                + "    AND AC.META = ? \n"
                + "    AND AC.ACCION = ? \n"
                + "    AND M.YEAR = AC.YEAR\n"
                + "    AND M.RAMO = AC.RAMO\n"
                + "    AND M.META = AC.META\n"
                + "    AND PA.YEAR =AC.YEAR\n"
                + "    AND PA.PROY = M.PROY\n"
                + "    AND PA.TIPO_PROY = M.TIPO_PROY\n"
                + "    AND P.YEAR = AC.YEAR\n"
                + "    AND P.PRG = AC.PRG\n"
                + "    AND R.YEAR = AC.YEAR\n"
                + "    AND R.RAMO = AC.RAMO\n"
                + "    AND PAR.YEAR = AC.YEAR\n"
                + "    AND PAR.PARTIDA = ? \n"
                + "    AND RL.YEAR = AC.YEAR\n"
                + "    AND RL.REL_LABORAL = ? \n"
                + "    AND REC.YEAR = AC.YEAR\n"
                + "    AND REC.FUENTE = ? \n"
                + "    AND REC.FONDO = ? \n"
                + "    AND REC.RECURSO = ? ";
        return strSQL;
    }

    public String getSQLdeleteMovtoAccionReq(int folio) {
        String strSQL = "DELETE \n"
                + "FROM POA.MOVOFICIOS_ACCION_REQ MER\n"
                + "WHERE MER.OFICIO = '" + folio + "'";
        return strSQL;
    }

    public String getSQLdeleteMovtoAccionReqByTipoOficio(int folio, String tipoOficio) {
        String strSQL = "DELETE FROM POA.MOVOFICIOS_ACCION_REQ M\n"
                + "WHERE EXISTS(\n"
                + "    SELECT MER.OFICIO, MER.RAMO, MER.META, MER.ACCION, MER.REQUERIMIENTO\n"
                + "    FROM POA.MOVOFICIOS_ACCION_REQ MER,\n"
                + "        DGI.TIPOFICIO TF,\n"
                + "        SPP.TRANSFREC TR,\n"
                + "        DGI.OFICONS OC\n"
                + "    WHERE MER.OFICIO = " + folio + "  \n"
                + "        AND TR.OFICIO = MER.OFICIO\n"
                + "        AND TR.RAMO = MER.RAMO\n"
                + "        AND TR.META = MER.META\n"
                + "        AND TR.ACCION = MER.ACCION\n"
                + "        AND TR.REQUERIMIENTO = MER.REQUERIMIENTO   \n"
                + "        AND OC.OFICIO = TR.OFICIO\n"
                + "        AND OC.CONSEC = TR.CONSEC\n"
                + "        AND OC.TIPO = '" + tipoOficio + "' \n"
                + "        AND TF.OFICIO = OC.OFICIO\n"
                + "        AND TF.TIPO = OC.TIPO\n"
                + "        AND TR.OFICIO = M.OFICIO\n"
                + "        AND TR.RAMO = M.RAMO\n"
                + "        AND TR.META = M.META\n"
                + "        AND TR.ACCION = M.ACCION\n"
                + "        AND TR.REQUERIMIENTO = M.REQUERIMIENTO)";
        return strSQL;
    }

    public String getSQLdeleteMovtoAccion(int folio) {
        String strSQL = "DELETE \n"
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION MER\n"
                + "WHERE MER.OFICIO = '" + folio + "'";
        return strSQL;
    }

    public String getSQLdeleteMovtoMeta(int folio) {
        String strSQL = "DELETE \n"
                + "FROM POA.MOVOFICIOS_ESTIMACION MER\n"
                + "WHERE MER.OFICIO = '" + folio + "'";
        return strSQL;
    }

    public String getSQLcountMovtoAccionReq() {
        String strSQL = "SELECT COUNT(1) AS CUENTA\n"
                + "FROM POA.MOVOFICIOS_ACCION_REQ MER\n"
                + "WHERE MER.OFICIO = ? ";
        return strSQL;
    }

    public String getSQLcountMovtoAccionReqByTipoOficio() {
        String strSQL = "SELECT COUNT(1) AS CUENTA \n"
                + "FROM POA.MOVOFICIOS_ACCION_REQ MER,\n"
                + "    DGI.TIPOFICIO TF,\n"
                + "    SPP.TRANSFREC TR,\n"
                + "    DGI.OFICONS OC\n"
                + "WHERE MER.OFICIO = ? \n"
                + "    AND TR.OFICIO = MER.OFICIO\n"
                + "    AND TR.RAMO = MER.RAMO\n"
                + "    AND TR.META = MER.META\n"
                + "    AND TR.ACCION = MER.ACCION\n"
                + "    AND TR.REQUERIMIENTO = MER.REQUERIMIENTO   \n"
                + "    AND OC.OFICIO = TR.OFICIO\n"
                + "    AND OC.CONSEC = TR.CONSEC\n"
                + "    AND OC.TIPO = ?\n"
                + "    AND TF.OFICIO = OC.OFICIO\n"
                + "    AND TF.TIPO = OC.TIPO ";
        return strSQL;
    }

    public String getSQLContFoliosMovtoMeta(int folio) {
        String strSQL = ""
                + "SELECT "
                + "	nvl(max(count(MOVOFEST.OFICIO)),0) EXISTEN "
                + "FROM  "
                + "	POA.MOVOFICIOS_ESTIMACION MOVOFEST "
                + "WHERE  "
                + "	MOVOFEST.OFICIO = '" + folio + "' "
                + "GROUP BY  "
                + "	MOVOFEST.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLContFoliosMovtoAccion(int folio) {
        String strSQL = ""
                + "SELECT "
                + "	nvl(max(count(MOVOFACCEST.OFICIO)),0) EXISTEN "
                + "FROM  "
                + "	POA.MOVOFICIOS_ACCION_ESTIMACION MOVOFACCEST "
                + "WHERE  "
                + "	MOVOFACCEST.OFICIO = '" + folio + "' "
                + "GROUP BY  "
                + "	MOVOFACCEST.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLCountTransferencia() {
        String strSQL = "SELECT COUNT(1) AS CUENTA FROM SPP.TRANSFERENCIAS\n"
                + "WHERE OFICIO = ? ";
        return strSQL;
    }

    public String getSQLCountTransferenciaByTipoOficio() {
        String strSQL = "SELECT COUNT(1) AS CUENTA\n"
                + "FROM \n"
                + "    SPP.TRANSFERENCIAS TR,\n"
                + "    DGI.OFICONS OC,\n"
                + "    DGI.TIPOFICIO TF\n"
                + "WHERE TR.OFICIO =  ?   \n"
                + "    AND OC.OFICIO = TR.OFICIO\n"
                + "    AND OC.CONSEC = TR.CONSEC\n"
                + "    AND OC.TIPO = ? \n"
                + "    AND TF.OFICIO = OC.OFICIO\n"
                + "    AND TF.TIPO = OC.TIPO";
        return strSQL;
    }

    public String getSQLCountTransfrec() {
        String strSQL = "SELECT COUNT(1) AS CUENTA FROM SPP.TRANSFREC\n"
                + "WHERE OFICIO = ? ";
        return strSQL;
    }

    public String getSQLCountTransfrecByTipoOficio() {
        String strSQL = "SELECT COUNT(1) AS CUENTA "
                + "FROM \n"
                + "    SPP.TRANSFREC TR,\n"
                + "    DGI.OFICONS OC,\n"
                + "    DGI.TIPOFICIO TF\n"
                + "WHERE TR.OFICIO = ? \n"
                + "    AND OC.OFICIO = TR.OFICIO\n"
                + "    AND OC.CONSEC = TR.CONSEC\n"
                + "    AND OC.TIPO = ? \n"
                + "    AND TF.OFICIO = OC.OFICIO\n"
                + "    AND TF.TIPO = OC.TIPO";
        return strSQL;
    }

    public String getSQLDeleteTransfrec(int folio) {
        String strSQL = "DELETE FROM SPP.TRANSFREC \n"
                + "WHERE OFICIO = " + folio;
        return strSQL;
    }

    public String getSQLDeleteTransfrecByTipoOficio(int folio, String tipoOficio) {
        String strSQL = "DELETE FROM SPP.TRANSFREC M\n"
                + "WHERE EXISTS(\n"
                + "    SELECT TR.OFICIO, TR.CONSEC CUENTA\n"
                + "    FROM \n"
                + "        SPP.TRANSFREC TR,\n"
                + "        DGI.OFICONS OC,\n"
                + "        DGI.TIPOFICIO TF\n"
                + "    WHERE TR.OFICIO = " + folio + "   \n"
                + "        AND OC.OFICIO = TR.OFICIO\n"
                + "        AND OC.CONSEC = TR.CONSEC\n"
                + "        AND OC.TIPO = '" + tipoOficio + "' \n"
                + "        AND TF.OFICIO = OC.OFICIO\n"
                + "        AND TF.TIPO = OC.TIPO\n"
                + "        AND M.OFICIO = TR.OFICIO\n"
                + "        AND M.CONSEC = TR.CONSEC)";
        return strSQL;
    }

    public String getSQLDeleteTransferencia(int folio) {
        String strSQL = "DELETE FROM SPP.TRANSFERENCIAS \n"
                + "WHERE OFICIO = " + folio;
        return strSQL;
    }

    public String getSQLDeleteTransferenciaByTipoOficio(int folio, String tipoOficio) {
        String strSQL = "DELETE FROM SPP.TRANSFERENCIAS M\n"
                + "WHERE EXISTS(\n"
                + "    SELECT TR.OFICIO, TR.CONSEC CUENTA\n"
                + "    FROM \n"
                + "        SPP.TRANSFERENCIAS TR,\n"
                + "        DGI.OFICONS OC,\n"
                + "        DGI.TIPOFICIO TF\n"
                + "    WHERE TR.OFICIO = " + folio + "   \n"
                + "        AND OC.OFICIO = TR.OFICIO\n"
                + "        AND OC.CONSEC = TR.CONSEC\n"
                + "        AND OC.TIPO = '" + tipoOficio + "' \n"
                + "        AND TF.OFICIO = OC.OFICIO\n"
                + "        AND TF.TIPO = OC.TIPO\n"
                + "        AND M.OFICIO = TR.OFICIO\n"
                + "        AND M.CONSEC = TR.CONSEC)";
        return strSQL;
    }

    public String getSQLContFoliosMovtoMetaByClave(int year, String ramoId, int metaId, int oficio) {
        String strSQL = ""
                + "SELECT "
                + "	MOVOF.OFICIO EXISTEN "
                + "FROM "
                + "	POA.MOVOFICIOS MOVOF, "
                + "	(	 "
                + "		SELECT "
                + "			OFICIOS.OFICIO "
                + "		FROM "
                + "			( "
                + "				( "
                + "					SELECT "
                + "						MOVOFEST.OFICIO "
                + "					FROM  "
                + "						POA.MOVOFICIOS_ESTIMACION MOVOFEST "
                + "					WHERE "
                + "                                             MOVOFEST.OFICIO <> " + oficio + " AND                     "
                + "                                             MOVOFEST.YEAR = " + year + " AND                     "
                + "						MOVOFEST.RAMO = '" + ramoId + "' AND "
                + "						MOVOFEST.META = " + metaId + " "
                + "					GROUP BY  "
                + "						MOVOFEST.OFICIO "
                + "				) "
                + "			) OFICIOS "
                + "		GROUP BY "
                + "			OFICIOS.OFICIO "
                + "	) OFACCMET "
                + "WHERE "
                + "	MOVOF.OFICIO <> " + oficio + " AND "
                + "	MOVOF.OFICIO = OFACCMET.OFICIO AND "
                + "	MOVOF.STATUS <> 'A' AND "
                + "	MOVOF.STATUS <> 'C' AND "
                + "	MOVOF.STATUS <> 'X' AND "
                + "	MOVOF.STATUS <> 'R' AND "
                + "	MOVOF.STATUS <> 'K' "
                + "GROUP BY "
                + "	MOVOF.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLContFoliosMovtoAccionByClave(int year, String ramoId, int metaId, int accionId, int oficio) {
        String strSQL = ""
                + "SELECT "
                + "	MOVOF.OFICIO EXISTEN "
                + "FROM "
                + "	POA.MOVOFICIOS MOVOF, "
                + "	(	 "
                + "		SELECT "
                + "			OFICIOS.OFICIO "
                + "		FROM "
                + "			( "
                + "				( "
                + "					SELECT "
                + "						MOVOFACCEST.OFICIO "
                + "					FROM  "
                + "						POA.MOVOFICIOS_ACCION_ESTIMACION MOVOFACCEST "
                + "					WHERE  "
                + "						MOVOFACCEST.OFICIO <> " + oficio + " AND "
                + "						MOVOFACCEST.YEAR = " + year + " AND "
                + "						MOVOFACCEST.RAMO = '" + ramoId + "' AND "
                + "						MOVOFACCEST.META = " + metaId + " AND "
                + "						MOVOFACCEST.ACCION = " + accionId + " "
                + "					GROUP BY  "
                + "						MOVOFACCEST.OFICIO "
                + "				)  "
                + "			) OFICIOS "
                + "		GROUP BY "
                + "			OFICIOS.OFICIO "
                + "	) OFACCMET "
                + "WHERE "
                + "	MOVOF.OFICIO <> " + oficio + " AND "
                + "	MOVOF.OFICIO = OFACCMET.OFICIO AND "
                + "	MOVOF.STATUS <> 'A' AND "
                + "	MOVOF.STATUS <> 'C' AND "
                + "	MOVOF.STATUS <> 'X' AND "
                + "	MOVOF.STATUS <> 'R' AND "
                + "	MOVOF.STATUS <> 'K' "
                + "GROUP BY "
                + "	MOVOF.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLCountMovtoAccionReqByInfo() {
        String strSQL = "SELECT COUNT(1) AS CUENTA\n"
                + "FROM\n"
                + "    POA.MOVOFICIOS_ACCION_REQ MAR\n"
                + "WHERE \n"
                + "    MAR.YEAR = ? "
                + "    AND MAR.RAMO = ? "
                + "    AND MAR.PRG = ? "
                + "    AND MAR.META = ? "
                + "    AND MAR.ACCION = ? "
                + "    AND MAR.PARTIDA = ? "
                + "    AND MAR.FUENTE = ? "
                + "    AND MAR.FONDO = ? "
                + "    AND MAR.RECURSO = ? "
                + "    AND MAR.REL_LABORAL = ? "
                + "    AND MAR.REQUERIMIENTO = ?";
        return strSQL;
    }

    public String getSQLDatosMovOficio() {
        String strSQL = "SELECT M.APP_LOGIN,M.STATUS, M.OFICIO, M.JUSTIFICACION, M.FECHAELAB, M.OBS_RECHAZO, M.CAPTURA_ESPECIAL, U.RAMO, COMENTARIO_PLANEACION,"
                + "(SELECT COMENTARIO \n"
                + "FROM POA.BITACORA_COMENTARIO \n"
                + "WHERE OFICIO = ? \n"
                + "AND NUM_COMENTARIO = \n"
                + "(\n"
                + "    SELECT MAX(NUM_COMENTARIO) \n"
                + "    FROM POA.BITACORA_COMENTARIO \n"
                + "    WHERE OFICIO = ? \n"
                + ")) COMENTARIO"
                + " FROM POA.MOVOFICIOS M\n , DGI.DGI_USR U  "
                + " WHERE M.OFICIO = ? "
                + "     AND TRIM(U.APP_LOGIN) = TRIM(M.APP_LOGIN)     "
                + "     AND U.SYS_CLAVE = 1    ";
        return strSQL;
    }

    public String getSQLupdateMovoficio() {
        String strSQL = "UPDATE POA.MOVOFICIOS M SET M.JUSTIFICACION = ?, M.COMENTARIO_PLANEACION = ? \n"
                + "WHERE M.OFICIO = ? ";
        return strSQL;
    }

    public String getSQLgetMovOficiosMeta() {
        String strSQL = "SELECT \n"
                + "MM.OFICIO, MM.RAMO, MM.PRG, MM.META, MM.CALCULO, MM.TIPO, MM.CVE_MEDIDA,MM.FINALIDAD, MM.FUNCION, MM.SUBFUNCION,\n"
                + "MM.TIPO_PROY, MM.PROY, MM.LINEA, MM.TIPO_COMPROMISO,MM.PONDERADO, MM.LINEA_SECTORIAL, MM.DESCR, MM.CRITERIO, MM.NVA_CREACION, MM.VAL_AUTORIZADO  \n"
                + "FROM POA.MOVOFICIOS_META MM\n"
                + "WHERE \n"
                + "    MM.OFICIO = ?";
        return strSQL;
    }

    public String getSQLgetMovOficiosMetaAmpRed() {
        String strSQL = "SELECT \n"
                + "MM.OFICIO, MM.RAMO, MM.PRG, MM.META, MM.CALCULO, MM.TIPO, MM.CVE_MEDIDA,MM.FINALIDAD, MM.FUNCION, MM.SUBFUNCION,\n"
                + "MM.TIPO_PROY, MM.PROY, MM.LINEA, MM.TIPO_COMPROMISO,MM.PONDERADO, MM.LINEA_SECTORIAL, MM.DESCR, MM.CRITERIO, MM.NVA_CREACION \n"
                + "FROM POA.MOVOFICIOS_META MM\n"
                + "WHERE \n"
                + "    MM.OFICIO = ?"
                + "    AND MM.YEAR = ?"
                + "    AND MM.RAMO = ?"
                + "    AND MM.META = ? ";
        return strSQL;
    }

    public String getSQLgetMovOficioAccion() {
        String strSQL = "SELECT MA.OFICIO,MA.YEAR, MA.RAMO, MA.PRG, MA.DEPTO,MA.META,MA.ACCION, MA.DESCR,MA.CVE_MEDIDA,\n"
                + "MA.CALCULO, MA.TIPO_GASTO, MA.LINEA, MA.GRUPO_POBLACION, MA.BENEF_HOMBRE,MA.OBRA, MA.BENEF_MUJER, Ma.MPO,\n"
                + "MA.LOCALIDAD, MA.LINEA_SECTORIAL, MA.NVA_CREACION, MA.VAL_AUTORIZADO FROM POA.MOVOFICIOS_ACCION MA\n"
                + "WHERE MA.OFICIO = ? ";
        return strSQL;
    }

    public String getSQLgetMovOficioAccionAmpRed() {
        String strSQL = "SELECT MA.OFICIO,MA.YEAR, MA.RAMO, MA.PRG, MA.DEPTO,MA.META,MA.ACCION, MA.DESCR,MA.CVE_MEDIDA,\n"
                + "MA.CALCULO, MA.TIPO_GASTO, MA.LINEA, MA.GRUPO_POBLACION, MA.BENEF_HOMBRE, MA.BENEF_MUJER, Ma.MPO,\n"
                + "MA.LOCALIDAD, MA.LINEA_SECTORIAL, MA.NVA_CREACION, MA.OBRA FROM POA.MOVOFICIOS_ACCION MA\n"
                + "WHERE MA.OFICIO = ? "
                + "     AND MA.YEAR = ? "
                + "     AND MA.RAMO = ?"
                + "     AND MA.META = ? "
                + "     AND MA.ACCION = ?";
        return strSQL;
    }

    public String getSQLinsertMovReprogramacionMeta(int year, int folio,
            String ramo, String prg, int meta, String metaDescr,
            String calculo, int cveMedida, String finalidad, String funcion,
            String subFuncion, String tipoProy, int proy, String linea,
            int tipoCompromiso, String ponderado, String lineaSectorial,
            int criterio, String nvaCreacion, String valAutorizado) {
        String strSQL = " "
                + "INSERT INTO "
                + "	POA.MOVOFICIOS_META "
                + "	( "
                + "		OFICIO,  "
                + "		RAMO,  "
                + "		PRG,  "
                + "		META, "
                + "		DESCR,  "
                + "		CALCULO,  "
                + "		CVE_MEDIDA, "
                + "		FINALIDAD, "
                + "		FUNCION,  "
                + "		SUBFUNCION, "
                + "		TIPO_PROY, "
                + "		PROY,  "
                + "		LINEA, "
                + "		TIPO_COMPROMISO, "
                + "		PONDERADO,  "
                + "		LINEA_SECTORIAL,   "
                + "		CRITERIO,   "
                + "		NVA_CREACION,   "
                + "		YEAR,   "
                + "		VAL_AUTORIZADO   "
                + "	) "
                + "	VALUES  "
                + "	( "
                + "		" + folio + ", "
                + "		'" + ramo + "', "
                + "		'" + prg + "', "
                + "		" + meta + ", "
                + "		'" + metaDescr + "', "
                + "		'" + calculo + "', "
                + "		" + cveMedida + ", "
                + "		'" + finalidad + "', "
                + "		'" + funcion + "', "
                + "		'" + subFuncion + "', "
                + "		'" + tipoProy + "', "
                + "		" + proy + ", "
                + "		'" + linea + "', "
                + "		" + tipoCompromiso + ", "
                + "		'" + ponderado + "', "
                + "		'" + lineaSectorial + "', "
                + "		" + criterio + ", "
                + "		'" + nvaCreacion + "', "
                + "		" + year + ", "
                + "		'" + valAutorizado + "' "
                + "	)                 "
                + " ";
        return strSQL;
    }

    public String getSQLContFoliosMovOfReprogramacionMeta(int folio) {
        String strSQL = ""
                + "SELECT "
                + "	nvl(max(count(MOVOFMETA.OFICIO)),0) EXISTEN "
                + "FROM  "
                + "	POA.MOVOFICIOS_META MOVOFMETA "
                + "WHERE  "
                + "	MOVOFMETA.OFICIO = '" + folio + "' "
                + "GROUP BY  "
                + "	MOVOFMETA.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLdeleteMovOfReprogramacionMeta(int folio) {
        String strSQL = "DELETE \n"
                + "FROM POA.MOVOFICIOS_META MER\n"
                + "WHERE MER.OFICIO = '" + folio + "'";
        return strSQL;
    }

    public String getSQLinsertMovReprogramacionAccion(int year, int folio,
            String ramo, String prg, String depto, int meta, int accion,
            String accionDescr, String calculo, int cveMedida, String grupo,
            int benefHombre, int benefMujer, String mpo, int localidad, String linea,
            String lineaSectorial, String nvaCreacion, String obra, String valAutorizado) {
        String strSQL = " "
                + "INSERT INTO "
                + "	POA.MOVOFICIOS_ACCION "
                + "	( "
                + "		OFICIO, "
                + "		RAMO, "
                + "		PRG,  "
                + "		DEPTO, "
                + "		META, "
                + "		ACCION, "
                + "		DESCR, "
                + "		CVE_MEDIDA, "
                + "		CALCULO, "
                + "		LINEA,  "
                + "		GRUPO_POBLACION, "
                + "		BENEF_HOMBRE, "
                + "		BENEF_MUJER, "
                + "		MPO, "
                + "		LOCALIDAD,  "
                + "		LINEA_SECTORIAL, "
                + "		NVA_CREACION, "
                + "		YEAR,   "
                + "		OBRA,   "
                + "		VAL_AUTORIZADO   "
                + "	) "
                + "	VALUES "
                + "	( "
                + "		" + folio + ", "
                + "		'" + ramo + "', "
                + "		'" + prg + "', "
                + "		'" + depto + "', "
                + "		" + meta + ", "
                + "		" + accion + ", "
                + "		'" + accionDescr + "', "
                + "		" + cveMedida + ",         "
                + "		'" + calculo + "', "
                + "		'" + linea + "', "
                + "		" + grupo + ", "
                + "		" + benefHombre + ", "
                + "		" + benefMujer + ", "
                + "		'" + mpo + "', "
                + "		" + localidad + ", "
                + "		'" + lineaSectorial + "', "
                + "		'" + nvaCreacion + "', "
                + "		'" + year + "', "
                + "		'" + obra + "', "
                + "		'" + valAutorizado + "' "
                + "	) "
                + " ";
        return strSQL;
    }

    public String getSQLContFoliosMovOfReprogramacionAccion(int folio) {
        String strSQL = ""
                + "SELECT "
                + "	nvl(max(count(MOVOFACCION.OFICIO)),0) EXISTEN "
                + "FROM  "
                + "	POA.MOVOFICIOS_ACCION MOVOFACCION "
                + "WHERE  "
                + "	MOVOFACCION.OFICIO = '" + folio + "' "
                + "GROUP BY  "
                + "	MOVOFACCION.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLdeleteMovOfReprogramacionAccion(int folio) {
        String strSQL = "DELETE \n"
                + "FROM POA.MOVOFICIOS_ACCION MER\n"
                + "WHERE MER.OFICIO = '" + folio + "'";
        return strSQL;
    }

    public String getSQLGetFinalidadByYear(int intYear) {
        String strSQL = "SELECT F.YEAR, F.FINALIDAD, F.DESCR "
                + "FROM S_POA_FINALIDAD F "
                + "WHERE "
                + "      F.YEAR = " + intYear
                + " ORDER BY F.FINALIDAD";
        return strSQL;
    }

    public String getSQLGetFuncionByYearFinalidad(int intYear, String strF1, String strF2) {
        String strSQL = " SELECT F.YEAR, F.FINALIDAD,F.FUNCION, F.DESCR "
                + " FROM S_POA_FUNCION F "
                + " WHERE "
                + " F.YEAR = " + intYear
                + " AND F.FINALIDAD BETWEEN '" + strF1 + "' AND '" + strF2 + "' "
                + " ORDER BY F.FUNCION";
        return strSQL;
    }

    public String getSQLGetSubfuncionByYearFinalidadFuncion(int intYear, String strF1, String strF2, String strSF1, String strSF2) {
        String strSQL = " SELECT F.YEAR, F.FINALIDAD,F.FUNCION,F.SUBFUNCION,F.DESCR "
                + " FROM S_POA_SUBFUNCION F "
                + " WHERE "
                + " F.YEAR = " + intYear
                + " AND F.FINALIDAD BETWEEN '" + strF1 + "' AND '" + strF2 + "' "
                + "  AND F.FUNCION BETWEEN '" + strSF1 + "'  AND '" + strSF2 + "' "
                + " ORDER BY F.SUBFUNCION";
        return strSQL;
    }

    public String getEntePublico() {
        String strSQL = " SELECT T.ENTE_PUBLICO,T.DESCRIPCION,T.SECTOR_CONAC,T.SECTOR_TIPO,T.SECTOR_ECONOMICO,T.SUBSECTOR,T.TIPO_ENTE_PUBLICO "
                + " FROM S_POA_ENTE_PUBLICO T "
                + " ORDER BY T.SECTOR_CONAC,T.SECTOR_TIPO,T.SECTOR_ECONOMICO,T.SUBSECTOR,T.TIPO_ENTE_PUBLICO,T.ENTE_PUBLICO ";

        return strSQL;
    }

    public String getSQLContFoliosMovtoMetaReprogramacionByClave(int year, String ramoId, int metaId, int oficio) {
        String strSQL = ""
                + "SELECT "
                + "	MOVOF.OFICIO EXISTEN "
                + "FROM "
                + "	POA.MOVOFICIOS MOVOF, "
                + "	(	 "
                + "		SELECT "
                + "			OFICIOS.OFICIO "
                + "		FROM "
                + "			( "
                + "				( "
                + "					SELECT "
                + "						MOVOFEST.OFICIO "
                + "					FROM  "
                + "						POA.MOVOFICIOS_META  MOVOFEST "
                + "					WHERE "
                + "                                             MOVOFEST.OFICIO <> " + oficio + " AND                     "
                + "                                             MOVOFEST.YEAR = " + year + " AND                     "
                + "						MOVOFEST.RAMO = '" + ramoId + "' AND "
                + "						MOVOFEST.META = " + metaId + " "
                + "					GROUP BY  "
                + "						MOVOFEST.OFICIO  "
                + "				) "
                + "			) OFICIOS "
                + "		GROUP BY "
                + "			OFICIOS.OFICIO "
                + "	) OFACCMET "
                + "WHERE "
                + "	MOVOF.OFICIO <> " + oficio + " AND "
                + "	MOVOF.OFICIO = OFACCMET.OFICIO AND "
                + "	MOVOF.STATUS <> 'A' AND "
                + "	MOVOF.STATUS <> 'C' AND "
                + "	MOVOF.STATUS <> 'X' AND "
                + "	MOVOF.STATUS <> 'R' AND "
                + "	MOVOF.STATUS <> 'K' "
                + "GROUP BY "
                + "	MOVOF.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLContFoliosMovtoAccionReprogramacionByClave(int year, String ramoId, int metaId, int accionId, int oficio) {
        String strSQL = ""
                + "SELECT "
                + "	MOVOF.OFICIO EXISTEN "
                + "FROM "
                + "	POA.MOVOFICIOS MOVOF, "
                + "	(	 "
                + "		SELECT "
                + "			OFICIOS.OFICIO "
                + "		FROM "
                + "			( "
                + "				( "
                + "					SELECT "
                + "						MOVOFACCEST.OFICIO "
                + "					FROM  "
                + "						POA.MOVOFICIOS_ACCION MOVOFACCEST "
                + "					WHERE  "
                + "						MOVOFACCEST.OFICIO <> " + oficio + " AND "
                + "						MOVOFACCEST.YEAR = " + year + " AND "
                + "						MOVOFACCEST.RAMO = '" + ramoId + "' AND "
                + "						MOVOFACCEST.META = " + metaId + " AND "
                + "						MOVOFACCEST.ACCION = " + accionId + " "
                + "					GROUP BY  "
                + "						MOVOFACCEST.OFICIO "
                + "				)  "
                + "			) OFICIOS "
                + "		GROUP BY "
                + "			OFICIOS.OFICIO "
                + "	) OFACCMET "
                + "WHERE "
                + "	MOVOF.OFICIO <> " + oficio + " AND "
                + "	MOVOF.OFICIO = OFACCMET.OFICIO AND "
                + "	MOVOF.STATUS <> 'A' AND "
                + "	MOVOF.STATUS <> 'C' AND "
                + "	MOVOF.STATUS <> 'X' AND "
                + "	MOVOF.STATUS <> 'R' AND "
                + "	MOVOF.STATUS <> 'K' "
                + "GROUP BY "
                + "	MOVOF.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLModificarEnero() {
        return "SELECT P.MODIFICAR_ENERO FROM DGI.PARAMETROS P";
    }

    public String getSQLGetCodigoProgramaticoByReq() {
        String strSQL = "SELECT REQ.RAMO, REQ.DEPTO, MET.FINALIDAD, MET.FUNCION, MET.SUBFUNCION, PR.PRG_CONAC, REQ.PRG, PROY.PROY, REQ.META,  "
                + "    ACC.ACCION, REQ.PARTIDA, REQ.TIPO_GASTO, REQ.FUENTE, REQ.FONDO, REQ.RECURSO, ACC.MPO, ACC.LOCALIDAD, PROY.TIPO_PROY, "
                + "    REQ.REL_LABORAL  "
                + "FROM S_POA_ACCION_REQ REQ "
                + "INNER JOIN S_POA_META MET ON REQ.YEAR = MET.YEAR AND REQ.RAMO = MET.RAMO AND REQ.META = MET.META "
                + "INNER JOIN S_POA_ACCION ACC ON REQ.YEAR = ACC.YEAR AND REQ.RAMO = ACC.RAMO AND REQ.META = ACC.META AND  "
                + "                            REQ.ACCION = ACC.ACCION "
                + "INNER JOIN S_POA_PROGRAMA PR ON REQ.YEAR = PR.YEAR AND REQ.PRG = PR.PRG "
                + "INNER JOIN POA.PROYECTO PROY ON REQ.YEAR = PROY.YEAR AND REQ.RAMO = PROY.RAMO AND REQ.PRG = PROY.PRG AND "
                + "                            MET.PROY = PROY.PROY AND MET.TIPO_PROY = PROY.TIPO_PROY "
                + "WHERE   "
                + "    REQ.YEAR = ? AND  "
                + "    REQ.RAMO = ? AND "
                + "    REQ.PRG = ? AND   "
                + "    REQ.REQUERIMIENTO =  ?  AND   "
                + "    REQ.PARTIDA = ? AND "
                + "    REQ.META = ? AND   "
                + "    REQ.ACCION = ? ";
        return strSQL;

    }

    public String getSQLGetProcedureRecalendarizaCodPPTO() {
        String strSQL = "CALL POA.P_POA_RECALEND_COD_PPTO("
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?)";

        return strSQL;
    }

    public String getSQLInsertHistMeta(int oficio) {
        String strSQL = "INSERT INTO POA.HIST_META(OFICIO,YEAR,RAMO,PRG,META,DESCR,MEDIDA,CALCULO,TIPO,CVE_MEDIDA,FINALIDAD,FUNCION,SUBFUNCION,"
                + "TIPO_PROY,PROY,DEPTO,CLAVE,LINEA,TIPO_COMPROMISO,BENEFICIADOS,PRESUPUESTAR,APROB_CONGRESO,"
                + "CONVENIO,CONV,BENEF_HOMBRE,BENEF_MUJER,GENERO,PRINCIPAL,RELATORIA,OBRA,DESCR_CORTA,MAYOR_COSTO,"
                + "FICHA_TECNICA,CRITERIO,OBJ,OBJ_META,PONDERADO,LINEA_SECTORIAL,PROCESO_AUTORIZACION) "
                + "SELECT " + oficio + ",M.YEAR,M.RAMO,M.PRG,M.META,M.DESCR,M.MEDIDA,M.CALCULO,M.TIPO,M.CVE_MEDIDA,M.FINALIDAD,M.FUNCION,M.SUBFUNCION,"
                + "M.TIPO_PROY,M.PROY,M.DEPTO,M.CLAVE,M.LINEA,M.TIPO_COMPROMISO,M.BENEFICIADOS,M.PRESUPUESTAR,M.APROB_CONGRESO,"
                + "M.CONVENIO,M.CONV,M.BENEF_HOMBRE,M.BENEF_MUJER,M.GENERO,M.PRINCIPAL,M.RELATORIA,M.OBRA,M.DESCR_CORTA,M.MAYOR_COSTO,"
                + "M.FICHA_TECNICA,M.CRITERIO,M.OBJ,M.OBJ_META,M.PONDERADO,M.LINEA_SECTORIAL,M.PROCESO_AUTORIZACION "
                + "FROM POA.META M "
                + "INNER JOIN POA.MOVOFICIOS_META MM ON M.YEAR = MM.YEAR AND  "
                + "       M.RAMO = MM.RAMO AND M.META = MM.META "
                + "WHERE MM.OFICIO =  " + oficio + "  AND (MM.NVA_CREACION IS NULL OR MM.NVA_CREACION = 'N')  ";
        return strSQL;
    }

    public String getSQLInsertHistAccion(int oficio) {
        String strSQL = "INSERT INTO POA.HIST_ACCION(OFICIO,YEAR,RAMO,PRG,DEPTO,META,ACCION,DESCR,MEDIDA,CVE_MEDIDA,CALCULO,"
                + "TIPO_GASTO,LINEA,GRUPO_POBLACION,BENEF_HOMBRE,BENEF_MUJER,MPO,LOCALIDAD,"
                + "TIPO_ACCION,LINEA_SECTORIAL,OBRA) "
                + "SELECT " + oficio + ",A.YEAR,A.RAMO,A.PRG,A.DEPTO,A.META,A.ACCION,A.DESCR,A.MEDIDA,A.CVE_MEDIDA,A.CALCULO,"
                + "A.TIPO_GASTO,A.LINEA,A.GRUPO_POBLACION,A.BENEF_HOMBRE,A.BENEF_MUJER,A.MPO,A.LOCALIDAD, "
                + "A.TIPO_ACCION,A.LINEA_SECTORIAL,A.OBRA "
                + "FROM POA.ACCION A "
                + "INNER JOIN POA.MOVOFICIOS_ACCION MA ON A.YEAR = MA.YEAR AND  "
                + "       A.RAMO = MA.RAMO AND A.META = MA.META AND A.ACCION = MA.ACCION "
                + "WHERE MA.OFICIO =  " + oficio + " AND (MA.NVA_CREACION IS NULL OR MA.NVA_CREACION = 'N') ";
        return strSQL;
    }

    public String getSQLUpdateReprogramacionMeta(int oficio) {
        String strSQL = "UPDATE POA.META a SET "
                + " (a.DESCR,a.CVE_MEDIDA,a.CALCULO,a.TIPO_COMPROMISO,a.LINEA,a.LINEA_SECTORIAL,a.PONDERADO,a.CRITERIO) = "
                + " ( SELECT  "
                + "  b.DESCR,b.CVE_MEDIDA,b.CALCULO,b.TIPO_COMPROMISO,b.LINEA,b.LINEA_SECTORIAL,b.PONDERADO,b.CRITERIO "
                + "FROM POA.MOVOFICIOS_META b "
                + "WHERE b.OFICIO = " + oficio + " AND "
                + "      b.YEAR = a.YEAR AND  "
                + "      b.RAMO = a.RAMO AND  "
                + "      b.META  = a.META AND "
                + "      (b.NVA_CREACION IS NULL OR b.NVA_CREACION = 'N') ) "
                + "WHERE EXISTS (  SELECT 1 "
                + "                FROM POA.MOVOFICIOS_META c "
                + "                WHERE c.OFICIO = " + oficio + " AND "
                + "                      c.YEAR = a.YEAR AND  "
                + "                      c.RAMO = a.RAMO AND  "
                + "                      c.META  = a.META AND "
                + "                      (c.NVA_CREACION IS NULL OR c.NVA_CREACION = 'N') ) ";
        return strSQL;
    }

    public String getSQLUpdateReprogramacionAccion(int oficio) {
        String strSQL = "UPDATE POA.ACCION a SET "
                + " (a.DESCR,a.CVE_MEDIDA,a.CALCULO,a.LINEA,a.LINEA_SECTORIAL,a.BENEF_HOMBRE,a.BENEF_MUJER) = "
                + " ( SELECT  "
                + "  b.DESCR,b.CVE_MEDIDA,b.CALCULO,b.LINEA,b.LINEA_SECTORIAL,b.BENEF_HOMBRE,b.BENEF_MUJER "
                + "FROM POA.MOVOFICIOS_ACCION b "
                + "WHERE b.OFICIO = " + oficio + " AND "
                + "      b.YEAR = a.YEAR AND  "
                + "      b.RAMO = a.RAMO AND  "
                + "      b.META = a.META AND  "
                + "      b.ACCION  = a.ACCION AND "
                + "      (b.NVA_CREACION IS NULL OR b.NVA_CREACION = 'N') ) "
                + "WHERE EXISTS (  SELECT 1 "
                + "                FROM POA.MOVOFICIOS_ACCION c "
                + "                WHERE c.OFICIO = " + oficio + " AND "
                + "                      c.YEAR = a.YEAR AND  "
                + "                      c.RAMO = a.RAMO AND  "
                + "                      c.META = a.META AND  "
                + "                      c.ACCION  = a.ACCION AND "
                + "                      (c.NVA_CREACION IS NULL OR c.NVA_CREACION = 'N') ) ";
        return strSQL;
    }

    public String getSQLRamoDescrByRamoYear(String ramoId, int year) {
        String strSQL = ""
                + "SELECT"
                + "   RAM.DESCR"
                + " FROM "
                + "   DGI.RAMOS RAM "
                + "WHERE "
                + "      RAM.RAMO = '" + ramoId + "' AND  "
                + "      RAM.YEAR = " + year + "  "
                + "";
        return strSQL;
    }

    public String getSQLProgramaDescrByProgramaYear(String programaId, int year) {
        String strSQL = ""
                + "SELECT  "
                + "    PRG.DESCR  "
                + " FROM "
                + "    S_POA_PROGRAMA PRG "
                + " WHERE  "
                + "    PRG.YEAR = " + year + " AND "
                + "    PRG.PRG = '" + programaId + "' "
                + "";
        return strSQL;
    }

    public String getSQLProyectoDescrByProyectoTipoProyYear(int proyectoId, String tipoProy, int year) {
        String strSQL = ""
                + "SELECT "
                + "	PROYACT.DESCR "
                + "FROM "
                + "	DGI.VW_SP_PROY_ACT PROYACT "
                + "WHERE "
                + "	PROYACT.PROY = " + proyectoId + " AND "
                + "	PROYACT.TIPO_PROY = '" + tipoProy + "' AND "
                + "	PROYACT.YEAR = " + year + " "
                + "";
        return strSQL;
    }

    public String getSQLGetTipoCompromisoByTipoCompromiso(int tipoCompromisoId) {
        String strSQL = ""
                + "SELECT "
                + "    TP.BENEFICIADOS "
                + "FROM "
                + "    DGI.POA_TIPO_COMPROMISO TP "
                + "WHERE "
                + "    TP.TIPO_COMPROMISO = " + tipoCompromisoId + "";
        return strSQL;
    }

    public String getSQLCodigoPPTOSinRequerimiento() {
        String strSQL = "SELECT A.YEAR, A.RAMO, A.DEPTO, M.FINALIDAD, M.FUNCION, M.SUBFUNCION, P.PRG_CONAC, P.PRG, M.TIPO_PROY, M.PROY, M.META, A.ACCION, TG.TIPO_GASTO, A.MPO, A.LOCALIDAD\n"
                + "FROM\n"
                + "    S_POA_ACCION A, "
                + "    S_POA_META M,\n"
                + "    S_POA_PROGRAMA P,\n"
                + "    (SELECT NVL(SSG.TIPO_GASTO,0) AS TIPO_GASTO  \n"
                + "                FROM S_DGI_SUBSUBGPO SSG,S_POA_TIPO_GASTO TG \n"
                + "                WHERE SSG.SUBSUBGRUPO IN ( \n"
                + "                    SELECT PAR.SUBSUBGPO FROM DGI.PARTIDA PAR  \n"
                + "                    WHERE PAR.PARTIDA = ? AND   \n"
                + "                        PAR.YEAR = ?) AND \n"
                + "                    SSG.TIPO_GASTO = TG.TIPO_GASTO AND \n"
                + "                    SSG.YEAR = TG.YEAR AND \n"
                + "                    TG.YEAR = ?) TG\n"
                + "WHERE\n"
                + "    A.YEAR = ? \n"
                + "    AND A.RAMO = ? \n"
                + "    AND A.PRG = ? \n"
                + "    AND A.META = ? \n"
                + "    AND A.ACCION = ? \n"
                + "    AND M.YEAR = A.YEAR\n"
                + "    AND M.RAMO = A.RAMO\n"
                + "    AND M.META = A.META\n"
                + "    AND M.PRG = A.PRG\n"
                + "    AND M.TIPO_PROY = ? \n"
                + "    AND M.PROY = ? \n"
                + "    AND P.YEAR = A.YEAR\n"
                + "    AND P.PRG = A.PRG";
        return strSQL;
    }

    public String getSQLgetConsecutivoNegativoAccionReq() {
        String strSQL = "SELECT DECODE((NVL(MIN(AR.REQUERIMIENTO),0) -1),0,-1,NVL(MIN(AR.REQUERIMIENTO),0) -1) AS REQ_NEG \n"
                + "FROM POA.MOVOFICIOS_ACCION_REQ AR "
                + "WHERE AR.REQUERIMIENTO <= 0";
        return strSQL;
    }

    public String getSQLgetConsecutivoNegativoAccion() {
        String strSQL = ""
                + "SELECT  "
                + "	DECODE((NVL(MIN(ACC.ACCION),0) -1),0,-1,NVL(MIN(ACC.ACCION),0) -1) AS ACCION_NEG  "
                + "FROM  "
                + "	POA.MOVOFICIOS_ACCION ACC "
                + "WHERE  "
                + "	ACC.ACCION <= 0 "
                + "";
        return strSQL;
    }

    public String getSQLgetConsecutivoNegativoMeta() {
        String strSQL = ""
                + " SELECT "
                + "	DECODE((NVL(MIN(MET.META),0) -1),0,-1,NVL(MIN(MET.META),0) -1) AS META_NEG "
                + " FROM "
                + "	POA.MOVOFICIOS_META MET"
                + " WHERE "
                + "	MET.META <= 0 "
                + "";
        return strSQL;
    }

    public String getSLQgetAmpliacionesByFolio() {
        String strSQL = "SELECT A.RAMO,A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION,\n"
                + "                 A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, A.DELEGACION, A.REL_LABORAL,A.STATUS, A.IMPTE, "
                + "                 A.CONSEC,A.TIPOMOV, R.TRANSITORIO, A.REQUERIMIENTO, A.CONSIDERAR, A.INGRESO_PROPIO \n"
                + "                 FROM SPP.AMPLIACIONES A, DGI.RAMOS R\n"
                + "                 WHERE A.OFICIO = ? \n"
                + "                    AND R.YEAR = ? \n"
                + "                    AND R.RAMO = A.RAMO";
        return strSQL;
    }

    public String getSQLGetAmpliacionesByTipoOficio() {
        String strSQL = "SELECT A.RAMO,A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION,\n"
                + "A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, A.DELEGACION, A.CONSIDERAR, A.INGRESO_PROPIO, \n "
                + " A.REL_LABORAL,A.STATUS, A.IMPTE, A.TIPOMOV, A.CONSEC, A.REQUERIMIENTO, NVL(R.TRANSITORIO, 'N') AS TRANSITORIO \n"
                + "FROM SPP.AMPLIACIONES A, DGI.TIPOFICIO T, DGI.OFICONS O, DGI.RAMOS R\n"
                + "WHERE\n"
                + "    A.OFICIO = T.OFICIO "
                + "    AND A.CONSEC = O.CONSEC "
                + "    AND T.OFICIO = ? "
                + "    AND T.TIPO = ? "
                + "    AND O.OFICIO = T.OFICIO "
                + "    AND O.TIPO = T.TIPO"
                + "    AND R.YEAR = ?"
                + "    AND R.RAMO = A.RAMO";
        return strSQL;
    }

    public String getSQLInsetAmpliaciones(int oficio, int consecutivo, String ramo, String depto, String finalidad, String funcion,
            String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral,
            double importe, String status, String tipoMov, int requerimiento, String considerar) {
        String strSQL = "INSERT INTO SPP.AMPLIACIONES A(A.OFICIO, A.CONSEC, A.RAMO, A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, "
                + "A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION, A.PARTIDA, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, A.DELEGACION,"
                + "A.REL_LABORAL, A.IMPTE, A.STATUS, A.TIPOMOV, A.TIPO_GASTO, A.REQUERIMIENTO, A.CONSIDERAR)\n"
                + "VALUES(" + oficio + ", " + consecutivo + ", '" + ramo + "', '" + depto + "', '" + finalidad + "', '" + funcion + "', '" + subfuncion + "', '" + prgConac + "', '" + programa + "', '" + tipoProy + "', "
                + " " + proyecto + ", " + meta + ", " + accion + ", '" + partida + "' , '" + fuente + "', '" + fondo + "', '" + recurso + "', '" + municipio + "'," + delegacion + " , '" + relLaboral + "', "
                + "" + importe + ", '" + status + "', '" + tipoMov + "','" + tipoGasto + "'," + requerimiento + ", '" + considerar + "')";
        return strSQL;
    }

    public String getSQLgetTipoOficio() {
        String strSQL = "SELECT P.AMPAUTOM FROM DGI.PARTIDA P\n"
                + "WHERE P.YEAR = ? "
                + "    AND P.PARTIDA = ?";
        return strSQL;
    }

    public String getSQLgetAsignadoByMes() {
        String strSQL = "SELECT PP.ASIGNADO FROM SPP.PPTO PP "
                + "WHERE "
                + "    PP.YEAR = ? "
                + "    AND PP.RAMO = ? "
                + "    AND PP.PRG = ? "
                + "    AND PP.TIPO_PROY = ? "
                + "    AND PP.PROYECTO = ? "
                + "    AND PP.META = ? "
                + "    AND PP.ACCION = ? "
                + "    AND PP.PARTIDA = ? "
                + "    AND PP.FUENTE = ? "
                + "    AND PP.FONDO = ?  "
                + "    AND PP.RECURSO = ? "
                + "    AND PP.REL_LABORAL = ? "
                + "    AND PP.MES = ?";
        return strSQL;
    }

    public String getSQLMovimientoByTipoMovUsrTipoOficio(String tipoMovimiento, String estatusBase, String appLogin, int year, int tipoFlujo, String ramoInList) {
        String strSQL = "SELECT M.OFICIO, M.FECHAELAB, M.APP_LOGIN, M.TIPOMOV, M.STATUS, M.FECPPTO, M.JUSTIFICACION, M.OBS_RECHAZO, "
                + "T.TIPO, T.STATUS AS STATUS_TIPO_OFICIO, T.OBS_RECHAZO AS OBS_RECHAZO_TIPO_OFICIO, U.RAMO "
                + " FROM DGI.DGI_USR U, POA.MOVOFICIOS M"
                + " INNER JOIN DGI.TIPOFICIO T ON M.OFICIO = T.OFICIO AND T.STATUS = '" + estatusBase + "' "
                + " WHERE M.TIPOMOV = '" + tipoMovimiento + "' "
                + "     AND M.OFICIO NOT IN ( "
                + "        SELECT OFICIO  "
                + "        FROM POA.BITMOVTOS B  "
                + "        WHERE B.TIPO_OFICIO = T.TIPO   "
                + "             AND B.SYS_CLAVE = 1 AND B.APP_LOGIN = '" + appLogin + "'   "
                + "             AND B.TIPO_FLUJO = " + tipoFlujo + "   "
                + "     )     "
                + "     AND EXTRACT (YEAR FROM FECHAELAB) = " + year + "     "
                + "     AND TRIM(U.APP_LOGIN) = TRIM(M.APP_LOGIN)     "
                + "     AND U.SYS_CLAVE = 1    "
                + "     AND U.RAMO IN (" + ramoInList + ")  "
                + " ORDER BY OFICIO";
        return strSQL;
    }

    public String getSQLgetMovimientosEvaluacion() {
        String strSQL = "SELECT M.OFICIO,\n"
                + "    M.FECHAELAB,\n"
                + "    M.APP_LOGIN,\n"
                + "    M.TIPOMOV,\n"
                + "    M.STATUS,\n"
                + "    M.FECPPTO,\n"
                + "    M.JUSTIFICACION,\n"
                + "    M.OBS_RECHAZO, \n"
                + "    NVL(M.EVALUACION,'N') EVALUACION, \n"
                + "    T.TIPO,\n"
                + "    T.STATUS AS STATUS_TIPO_OFICIO,\n"
                + "    T.OBS_RECHAZO AS OBS_RECHAZO_TIPO_OFICIO,\n"
                + "    U.RAMO||' '||R.DESCR RAMO \n"
                + "FROM \n"
                + "    DGI.DGI_USR U,\n"
                + "    POA.MOVOFICIOS M,\n"
                + "    DGI.TIPOFICIO T,"
                + "  DGI.RAMOS R\n"
                + "WHERE \n"
                + "    (T.TIPO||M.TIPOMOV <> 'UT')\n"
                + "    AND M.TIPOMOV <> 'C'\n"
                + "    AND T.OFICIO(+) = M.OFICIO \n"
                + "    AND EXTRACT (YEAR FROM FECHAELAB) = ?   \n"
                + "    AND TRIM(U.APP_LOGIN) = TRIM(M.APP_LOGIN)     \n"
                + "    AND U.SYS_CLAVE = 1     \n"
                + "    AND M.STATUS IN ('W','Y','T','V','A')\n"
                + "    AND R.YEAR = ? "
                + "    AND R.RAMO = U.RAMO "
                + "ORDER BY OFICIO";
        return strSQL;
    }

    public String getSQLMovimientoByFolioTipoOficio(int folio, String tipoOficio) {
        String strSQL = "SELECT M.OFICIO, M.FECHAELAB, M.APP_LOGIN, M.TIPOMOV, M.STATUS, M.FECPPTO, M.JUSTIFICACION, M.OBS_RECHAZO, "
                + "T.TIPO, T.STATUS AS STATUS_TIPO_OFICIO, T.OBS_RECHAZO AS OBS_RECHAZO_TIPO_OFICIO "
                + "FROM POA.MOVOFICIOS M "
                + "INNER JOIN DGI.TIPOFICIO T ON M.OFICIO = T.OFICIO AND T.TIPO = '" + tipoOficio + "' "
                + "WHERE M.OFICIO = " + folio;
        return strSQL;
    }

    public String getSQLUpdateEstatusMovTipoOficio(String estatus, int folio, String tipoOficio) {
        String strSQL = "UPDATE DGI.TIPOFICIO "
                + "SET STATUS = '" + estatus + "' "
                + "WHERE OFICIO = " + folio + " "
                + "     AND TIPO = '" + tipoOficio + "' ";

        return strSQL;
    }

    public String getSQLUpdateEstatusMotivoMovTipoOficio(String estatus, String motivo, int folio, String tipoOficio) {
        String strSQL = "UPDATE DGI.TIPOFICIO "
                + "SET STATUS = '" + estatus + "', "
                + "OBS_RECHAZO = '" + motivo + "' "
                + "WHERE OFICIO = " + folio + " "
                + "     AND TIPO = '" + tipoOficio + "' ";

        return strSQL;
    }

    public String getSQLUpdateEstatusAmpliacionesTipoOficio(String estatus, int folio, String tipoOficio) {
        String strSQL = "UPDATE SPP.AMPLIACIONES a "
                + "SET a.STATUS = '" + estatus + "' "
                + "WHERE A.OFICIO = " + folio + " AND "
                + "   a.CONSEC IN ( SELECT b.CONSEC "
                + "               FROM SPP.AMPLIACIONES b "
                + "               INNER JOIN DGI.OFICONS c ON b.OFICIO = c.OFICIO AND b.CONSEC = c.CONSEC AND c.TIPO = '" + tipoOficio + "' "
                + "               INNER JOIN DGI.TIPOFICIO t ON t.OFICIO = c.OFICIO AND t.TIPO = c.TIPO "
                + "               WHERE b.OFICIO = " + folio + " "
                + "             ) ";

        return strSQL;
    }

    public String getSQLGetEjercicioActivoConta() {
        String strSQL = "SELECT EJERCICIO "
                + "FROM S_CG_EJERCICIO CGE, "
                + "     DGI.PARAMETROS PRM "
                + "WHERE PRM.EMPRESA_CG = CGE.EMPRESA "
                + "      AND CGE.ACTIVO = 'S'";

        return strSQL;
    }

    public String getSQLGetFechaAplicacionGasto(int ejercicio) {
        String strSQL = "SELECT VMAM.FECHA_MOMENTO_GASTO, "
                + "     TRUNC(SYSDATE) AS FECHA_ACTUAL "
                + "FROM S_CG_VW_MAX_APLICACION_MOMENTO VMAM,"
                + "     DGI.PARAMETROS PRM "
                + "WHERE VMAM.EMPRESA = PRM.EMPRESA_CG "
                + "AND VMAM.EJERCICIO = " + ejercicio;

        return strSQL;
    }

    public String getSQLGetValidaOficioSINVP(int folio) {
        String strSQL = "SELECT 1 "
                + "FROM	POA.MOVOFICIOS MO "
                + "WHERE 	MO.OFICIO = " + folio + " AND  "
                + "             MO.INVERSION = 'S' AND "
                + "	EXISTS    (SELECT 1 "
                + "                  FROM DGI.S_INVP_SIP_OFIC SISO "
                + "                  WHERE SISO.SIP_OFIC = MO.OFICIO)";

        return strSQL;
    }

    /*public String getSQLGetEstatusOficioSINVP(int folio) {
     String strSQL = "SELECT STATUS "
     + "FROM	DGI.S_INVP_SIP_OFIC "
     + "WHERE 	SIP_OFICIO = " + folio;

     return strSQL;
     }*/

    /*public String getSQLUpdateEstatusOficioSINVP(int folio, int estatus) {
     String strSQL = "UPDATE DGI.S_INVP_SIP_OFIC "
     + "SET STATUS = " + estatus + " "
     + "WHERE 	SIP_OFICIO = " + folio;
     return strSQL;
     }*/

    /*public String getSQLUpdateEstatusOficioDetSINVP(int folio) {
     String strSQL = "UPDATE DGI.S_INVP_OBRA_FONDO_DET SOD "
     + "   SET	STS_TSIP = 71 "
     + "WHERE	EXISTS (SELECT 1 "
     + "               FROM	DGI.S_INVP_SIP_OFIC_DET SIOFD "
     + "               WHERE SIOFD.OBRA = SOD.OBRA AND "
     + "               SIOFD.ANIO_FISCAL = SOD.ANIO_FISCAL AND "
     + "               SIOFD.TIPO_RECURSO = SOD.TIPO_RECURSO AND "
     + "               SIOFD.FONDO = SOD.FONDO AND "
     + "               SIOFD.MOVIMIENTO = SOD.MOVIMIENTO AND "
     + "               SIOFD.SIP_OFIC = " + folio + ")";
     return strSQL;
     }*/
    public String getSQLupdatePPTOreduccion(double importe, int year, String ramo, String depto, String finanlidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida, String tipoGasto,
            String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral, int mes) {
        String strSQL = new String();
        strSQL = "UPDATE SPP.PPTO P SET P.ACTUALIZADO = (ACTUALIZADO + (" + importe + "))\n"
                + "WHERE P.YEAR = " + year + " \n"
                + "    AND P.RAMO = '" + ramo + "'\n"
                + "    AND P.DEPTO = '" + depto + "'\n"
                + "    AND P.FINALIDAD = '" + finanlidad + "'\n"
                + "    AND P.FUNCION = '" + funcion + "'\n"
                + "    AND P.SUBFUNCION = '" + subfuncion + "'\n"
                + "    AND P.PRG_CONAC = '" + prgConac + "'\n"
                + "    AND P.PRG = '" + programa + "'\n"
                + "    AND P.TIPO_PROY = '" + tipoProy + "'\n"
                + "    AND P.PROYECTO = " + proyecto + " \n"
                + "    AND P.META = " + meta + " \n"
                + "    AND P.ACCION = " + accion + " \n"
                + "    AND P.PARTIDA = '" + partida + "'\n"
                + "    AND P.TIPO_GASTO = '" + tipoGasto + "'\n"
                + "    AND P.FUENTE = '" + fuente + "'\n"
                + "    AND P.FONDO = '" + fondo + "'\n"
                + "    AND P.RECURSO = '" + recurso + "'\n"
                + "    AND P.MUNICIPIO = '" + municipio + "'\n"
                + "    AND P.DELEGACION = " + delegacion + " \n"
                + "    AND P.REL_LABORAL = '" + relLaboral + "' \n"
                + "    AND P.MES = " + mes + " ";
        return strSQL;
    }

    public String getSQLInsertOficons(int folio, int consecutivo, String tipo) {
        String strSQL = "INSERT INTO DGI.OFICONS O (O.OFICIO, O.TIPO, O.CONSEC)\n"
                + "VALUES (" + folio + ",'" + tipo + "'," + consecutivo + ")";
        return strSQL;
    }

    public String getSQLgetTipooficioByFolio(int folio) {
        String strSQL = "SELECT OFICIO,TIPO FROM DGI.OFICONS\n"
                + "    WHERE OFICIO = " + folio + " "
                + "    GROUP BY OFICIO,TIPO";
        return strSQL;
    }

    public String getSQLgetTipooficioByFolioTipo(int folio, String tipoOficio) {
        String strSQL = "SELECT OFICIO,TIPO FROM DGI.OFICONS\n"
                + "    WHERE OFICIO = " + folio + " "
                + "     AND TIPO = '" + tipoOficio + "' "
                + "    GROUP BY OFICIO,TIPO";
        return strSQL;
    }

    public String getSQLinsertTipoOficio(int oficio, String tipo, String estatus) {
        String strSQL = "INSERT INTO DGI.TIPOFICIO T (T.OFICIO, T.TIPO, T.STATUS) VALUES \n"
                + "(" + oficio + ",'" + tipo + "','" + estatus + "')";
        return strSQL;
    }

    public String getSQLMesTrimestreByPeriodo(boolean validaTrimestre) {
        String trimestreAnt = new String();
        if (!validaTrimestre) {
            trimestreAnt = "- 1";
        }
        String strSQL = "SELECT E.PERIODO FROM DGI.ESTIMACION_PERIODO E\n"
                + "WHERE E.EVALUACION = (\n"
                + "    SELECT EP.EVALUACION FROM DGI.ESTIMACION_PERIODO EP\n"
                + "    WHERE EP.PERIODO = ? "
                + "    )" + trimestreAnt;
        return strSQL;
    }

    public String getProgramaByRamoMeta() {
        String strSQL = "SELECT M.PRG FROM POA.META M\n"
                + "WHERE M.YEAR = ? "
                + "    AND M.RAMO = ? \n"
                + "    AND M.META = ? ";
        return strSQL;
    }

    public String getProgramaByRamoMetaAccion() {
        String strSQL = "SELECT A.PRG FROM POA.ACCION A\n"
                + "WHERE A.YEAR = ? "
                + "    AND A.RAMO = ? "
                + "    AND A.META = ? "
                + "    AND A.ACCION = ?";
        return strSQL;
    }

    public String getProgramaByRamoMetaMov() {
        String strSQL = "SELECT M.PRG FROM POA.MOVOFICIOS_META M\n"
                + "WHERE M.OFICIO = ?"
                + " AND M.YEAR = ? "
                + "    AND M.RAMO = ? \n"
                + "    AND M.META = ? ";
        return strSQL;
    }

    public String getProgramaByRamoMetaAccionMov() {
        String strSQL = "SELECT A.PRG FROM POA.MOVOFICIOS_ACCION A\n"
                + "WHERE A.OFICIO = ?"
                + "     AND A.YEAR = ? "
                + "    AND A.RAMO = ? "
                + "    AND A.META = ? "
                + "    AND A.ACCION = ?";
        return strSQL;
    }

    public String getSQLgetDeptoDescripcion() {
        String strSQL = "SELECT D.DESCR FROM DGI.DEPENDENCIA D\n"
                + "WHERE D.RAMO = ? "
                + "AND D.DEPTO = ? \n"
                + "AND D.YEAR = ? ";
        return strSQL;
    }

    public String getSQLGetFuncionCreaCodigo() {
        String strSQL = "CALL POA.P_CREAR_CODIGO(?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?)";

        return strSQL;
    }

    public String getSQLGetInsertMetaFromMovoficios(int folio, int year, String ramo, int meta, int claveMetaNueva, int periodo) {
        String strSQL = "INSERT INTO POA.META a ( a.META, a.RAMO, a.YEAR, "
                + "a.PRG, a.DESCR, a.MEDIDA,  "
                + "a.CALCULO, a.TIPO, a.CVE_MEDIDA, a.TIPO_PROY, a.PROY,  "
                + "a.DEPTO, a.CLAVE, a.LINEA, a.TIPO_COMPROMISO, a.BENEFICIADOS,  "
                + "                a.PRESUPUESTAR, a.APROB_CONGRESO, a.CONVENIO, a.CONV,  "
                + "                a.BENEF_HOMBRE, a.BENEF_MUJER, a.GENERO, a.PRINCIPAL,  "
                + "                a.RELATORIA, a.DESCR_CORTA, a.MAYOR_COSTO,  "
                + "                a.FICHA_TECNICA, a.CRITERIO, a.OBJ, a.OBJ_META, "
                + "                a.PONDERADO, a.LINEA_SECTORIAL, a.FINALIDAD, a.FUNCION, a.SUBFUNCION, "
                + "                a.PROCESO_AUTORIZACION, a.OBRA, a.PERIODO )    "
                + "        SELECT  " + claveMetaNueva + ",  m.RAMO, m.YEAR,  "
                + "                m.PRG, m.DESCR, m.MEDIDA,  "
                + "                m.CALCULO, m.TIPO, m.CVE_MEDIDA, m.TIPO_PROY, m.PROY,  "
                + "                m.DEPTO, m.CLAVE, m.LINEA, m.TIPO_COMPROMISO, m.BENEFICIADOS,  "
                + "                m.PRESUPUESTAR, m.APROB_CONGRESO, m.CONVENIO, m.CONV,  "
                + "                m.BENEF_HOMBRE, m.BENEF_MUJER, m.GENERO, m.PRINCIPAL,  "
                + "                m.RELATORIA, m.DESCR_CORTA, m.MAYOR_COSTO,  "
                + "                m.FICHA_TECNICA, m.CRITERIO, m.OBJ, m.OBJ_META, "
                + "                m.PONDERADO, m.LINEA_SECTORIAL, m.FINALIDAD, m.FUNCION, m.SUBFUNCION, "
                + "                m.PROCESO_AUTORIZACION, m.OBRA, " + periodo + "  "
                + "        FROM POA.MOVOFICIOS_META m "
                + "        WHERE m.OFICIO = " + folio
                + "                AND m.YEAR = '" + year + "' "
                + "                AND m.RAMO = '" + ramo + "' "
                + "                AND m.META = " + meta
                + "                AND m.NVA_CREACION = 'S'";

        return strSQL;
    }

    public String getSQLUpdateMovoficiosMeta(int metaNva, int oficio, int year, String ramo, int metaAnt) {
        String strSQL = "UPDATE POA.MOVOFICIOS_META "
                + "SET META = " + metaNva + " "
                + "WHERE OFICIO = " + oficio + " "
                + "   AND YEAR = " + year + " "
                + "   AND RAMO = '" + ramo + "' "
                + "   AND META = " + metaAnt + " ";

        return strSQL;
    }

    public String getSQLUpdateMovoficiosMetaAccion(int metaNva, int oficio, int year, String ramo, int metaAnt) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ACCION "
                + "SET META = " + metaNva + " "
                + "WHERE OFICIO = " + oficio + " "
                + "   AND YEAR = " + year + " "
                + "   AND RAMO = '" + ramo + "' "
                + "   AND META = " + metaAnt + " ";

        return strSQL;
    }

    public String getSQLGetInsertAccionFromMovoficios(int folio, int year, String ramo, int meta, int accion, int claveAccionNueva, int periodo) {
        String strSQL = "INSERT INTO POA.ACCION a ( a.META, a.RAMO, a.YEAR, "
                + "           a.ACCION, a.DESCR, a.DEPTO, a.MEDIDA, a.CVE_MEDIDA,  "
                + "           a.CALCULO, a.LINEA, a.GRUPO_POBLACION,  "
                + "           a.MPO, a.LOCALIDAD, a.LINEA_SECTORIAL, a.BENEF_MUJER, a.BENEF_HOMBRE, a.PRG, a.OBRA, a.PERIODO )    "
                + "SELECT     m.META, m.RAMO, m.YEAR,  "
                + "           " + claveAccionNueva + ", m.DESCR, m.DEPTO, m.MEDIDA, m.CVE_MEDIDA,  "
                + "           m.CALCULO, m.LINEA, m.GRUPO_POBLACION,  "
                + "           m.MPO, m.LOCALIDAD, m.LINEA_SECTORIAL, m.BENEF_MUJER, m.BENEF_HOMBRE, m.PRG, m.OBRA, " + periodo + "  "
                + "FROM POA.MOVOFICIOS_ACCION m "
                + "WHERE m.OFICIO = " + folio
                + "                AND m.YEAR = '" + year + "' "
                + "                AND m.RAMO = '" + ramo + "' "
                + "                AND m.META = " + meta
                + "                AND m.ACCION = " + accion
                + "                AND m.NVA_CREACION = 'S'";

        return strSQL;
    }

    public String getSQLUpdateMovoficiosAccion(int accionNva, int oficio, int year, String ramo, int meta, int accionAnt) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ACCION "
                + "SET ACCION = " + accionNva + " "
                + "WHERE OFICIO = " + oficio + " "
                + "   AND YEAR = " + year + " "
                + "   AND RAMO = '" + ramo + "' "
                + "   AND META = " + meta + " "
                + "   AND ACCION = " + accionAnt + " ";

        return strSQL;
    }

    public String getSQLUpdateMovoficiosAccionReq(int accionNva, int oficio, int year, String ramo, int meta, int accionAnt) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ACCION_REQ "
                + "SET ACCION = " + accionNva + " "
                + "WHERE OFICIO = " + oficio + " "
                + "   AND YEAR = " + year + " "
                + "   AND RAMO = '" + ramo + "' "
                + "   AND META = " + meta + " "
                + "   AND ACCION = " + accionAnt + " ";

        return strSQL;
    }

    public String getSQLInsertBitMovto(int oficio, String appLogin, String autorizo,
            String impFirma, String tipoOficio, String terminal, int tipoFlujo, String tipoUsr, String fecha) {
        String strSQL = "INSERT INTO POA.BITMOVTOS(OFICIO, APP_LOGIN, FECHAAUT, "
                + "AUTORIZO, IMP_FIRMA, TIPO_OFICIO, TERMINAL, SYS_CLAVE, "
                + "TIPO_FLUJO, TIPO_USR, FECPPTO) "
                + "VALUES(" + oficio + ",'" + appLogin + "', TO_DATE('" + fecha + "', 'DD/MM/YYYY'), "
                + "'" + autorizo + "', '" + impFirma + "', '" + tipoOficio + "', '" + terminal + "', 1, "
                + "" + tipoFlujo + ", '" + tipoUsr + "',(SELECT FECPPTO FROM POA.MOVOFICIOS WHERE OFICIO = " + oficio + "))";
        return strSQL;
    }

    public String getSQLGetInsertRequerimientoFromMovoficios(int reqNvo, int folio, int year, String ramo, String programa, String depto,
            int meta, int accion, int reqAnt) {
        String strSQL = "INSERT INTO POA.ACCION_REQ a (a.YEAR, a.RAMO, a.PRG, a.DEPTO, "
                + "             a.META, a.ACCION, a.FUENTE, a.TIPO_GASTO, a.REQUERIMIENTO,  "
                + "             a.DESCR, a.PARTIDA, a.REL_LABORAL, a.CANTIDAD, a.COSTO_UNITARIO,  "
                + "             a.COSTO_ANUAL, a.ENE, a.FEB, a.MAR, a.ABR, a.MAY,  "
                + "             a.JUN, a.JUL, a.AGO, a.SEP, a.OCT, a.NOV, a.DIC,  "
                + "             a.ARTICULO, a.CANTIDAD_ORG, a.COSTO_UNITARIO_ORG, a.ARCHIVO, a.JUSTIFICACION, "
                + "             a.FONDO, a.RECURSO, a.FECHA_REGISTRO, a.APP_LOGIN_REG, a.PPTO_ORIGINAL) "
                + "     SELECT  m.YEAR, m.RAMO, m.PRG, m.DEPTO,  "
                + "                m.META, m.ACCION, m.FUENTE, m.TIPO_GASTO, " + reqNvo + ",  "
                + "                m.DESCR, m.PARTIDA, m.REL_LABORAL, m.CANTIDAD, m.COSTO_UNITARIO,  "
                + "                m.COSTO_ANUAL, m.ENE, m.FEB, m.MAR, m.ABR, m.MAY,  "
                + "                m.JUN, m.JUL, m.AGO, m.SEP, m.OCT, m.NOV, m.DIC,  "
                + "                m.ARTICULO, m.CANTIDAD_ORG, m.COSTO_UNITARIO_ORG, m.ARCHIVO, m.JUSTIFICACION, "
                + "                m.FONDO, m.RECURSO, m.FECHA_REGISTRO, m.APP_LOGIN_REG, 'N'  "
                + "        FROM POA.MOVOFICIOS_ACCION_REQ m "
                + "        WHERE m.NVA_CREACION = 'S'  "
                + "            AND m.OFICIO = " + folio + " "
                + "            AND m.YEAR = " + year + " "
                + "            AND m.RAMO = '" + ramo + "' "
                + "            AND m.PRG = '" + programa + "' "
                + "            AND m.DEPTO = '" + depto + "' "
                + "            AND m.META = " + meta + " "
                + "            AND m.ACCION = " + accion + " "
                + "            AND REQUERIMIENTO = " + reqAnt + " ";

        return strSQL;
    }

    public String getSQLGetUpdateMovoficiosRquerimiento(int reqNvo, int folio, int year, String ramo, String programa, String depto,
            int meta, int accion, int reqAnt) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ACCION_REQ m "
                + " SET m.REQUERIMIENTO = " + reqNvo + "  "
                + "        WHERE m.NVA_CREACION = 'S'  "
                + "            AND m.OFICIO = " + folio + " "
                + "            AND m.YEAR = " + year + " "
                + "            AND m.RAMO = '" + ramo + "' "
                + "            AND m.PRG = '" + programa + "' "
                + "            AND m.DEPTO = '" + depto + "' "
                + "            AND m.META = " + meta + " "
                + "            AND m.ACCION = " + accion + " "
                + "            AND REQUERIMIENTO = " + reqAnt + " ";

        return strSQL;
    }

    public String getSQLGetProcedureAmpliacionCodPPTO() {
        String strSQL = "CALL POA.P_POA_AMPLIACION_COD_PPTO("
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?)";

        return strSQL;
    }

    public String getSQLGetStatusTipoOficioByOficio(int oficio) {
        String strSQL = "SELECT STATUS "
                + "FROM DGI.TIPOFICIO "
                + "WHERE OFICIO = " + oficio;

        return strSQL;
    }

    public String getSQLCountTipoOficioByOficioEstatus(int oficio, String estatus) {
        String strSQL = "SELECT COUNT(STATUS) AS CUANTOS "
                + "FROM DGI.TIPOFICIO "
                + "WHERE OFICIO = " + oficio + " "
                + "     AND STATUS = '" + estatus + "'";

        return strSQL;
    }

    public String getSQLCountMovoficiosMetasEditadas(int oficio) {
        String strSQL = "SELECT COUNT(1) AS CUANTOS "
                + "FROM POA.MOVOFICIOS_ESTIMACION "
                + "WHERE OFICIO = " + oficio + " "
                + "     AND (NVA_CREACION IS NULL OR NVA_CREACION = 'N')";

        return strSQL;
    }

    public String getSQLCountMovoficiosAccionEditadas(int oficio) {
        String strSQL = "SELECT COUNT(1) AS CUANTOS "
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION "
                + "WHERE OFICIO = " + oficio + " "
                + "     AND (NVA_CREACION IS NULL OR NVA_CREACION = 'N')";

        return strSQL;
    }

    public String getSQLAjustarAcumuladoMesesAnteriores(MovOficiosAccionReq movOficioAccionReq, int metaId, int accionId, int mesActual) {
        double douMesesAnteriores = 0;
        String strUpdate = "UPDATE POA.MOVOFICIOS_ACCION_REQ SET REQUERIMIENTO = REQUERIMIENTO, ";

        for (int i = mesActual - 1; i >= 0; i--) {
            switch (i) {
                case 11:
                    if (movOficioAccionReq.getNov() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getNov();
                        strUpdate += "NOV = 0,";
                    }
                    break;
                case 10:
                    if (movOficioAccionReq.getOct() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getOct();
                        strUpdate += "OCT = 0,";
                    }
                    break;
                case 9:
                    if (movOficioAccionReq.getSep() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getSep();
                        strUpdate += "SEP = 0,";
                    }
                    break;
                case 8:
                    if (movOficioAccionReq.getAgo() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getAgo();
                        strUpdate += "AGO = 0,";
                    }
                    break;
                case 7:
                    if (movOficioAccionReq.getJul() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getJul();
                        strUpdate += "JUL = 0,";
                    }
                    break;
                case 6:
                    if (movOficioAccionReq.getJun() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getJun();
                        strUpdate += "JUN = 0,";
                    }
                    break;
                case 5:
                    if (movOficioAccionReq.getMay() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getMay();
                        strUpdate += "MAY = 0,";
                    }
                    break;
                case 4:
                    if (movOficioAccionReq.getAbr() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getAbr();
                        strUpdate += "ABR = 0,";
                    }
                    break;
                case 3:
                    if (movOficioAccionReq.getMar() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getMar();
                        strUpdate += "MAR = 0,";
                    }
                    break;
                case 2:
                    if (movOficioAccionReq.getFeb() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getFeb();
                        strUpdate += "FEB = 0,";
                    }
                    break;
                case 1:
                    if (movOficioAccionReq.getEne() > 0) {
                        douMesesAnteriores += movOficioAccionReq.getEne();
                        strUpdate += "ENE = 0,";
                    }
                    break;
            }
        }

        if (douMesesAnteriores > 0) {
            switch (mesActual) {
                case 12:
                    strUpdate += "DIC = DIC + " + douMesesAnteriores;
                    break;
                case 11:
                    strUpdate += "NOV = NOV + " + douMesesAnteriores;
                    break;
                case 10:
                    strUpdate += "OCT = OCT + " + douMesesAnteriores;
                    break;
                case 9:
                    strUpdate += "SEP = SEP + " + douMesesAnteriores;
                    break;
                case 8:
                    strUpdate += "AGO = AGO + " + douMesesAnteriores;
                    break;
                case 7:
                    strUpdate += "JUL = JUL + " + douMesesAnteriores;
                    break;
                case 6:
                    strUpdate += "JUN = JUN + " + douMesesAnteriores;
                    break;
                case 5:
                    strUpdate += "MAY = MAY + " + douMesesAnteriores;
                    break;
                case 4:
                    strUpdate += "ABR = ABR + " + douMesesAnteriores;
                    break;
                case 3:
                    strUpdate += "MAR = MAR + " + douMesesAnteriores;
                    break;
                case 2:
                    strUpdate += "FEB = FEB + " + douMesesAnteriores;
                    break;
                case 1:
                    strUpdate += "ENE = ENE + " + douMesesAnteriores;
                    break;
            }

            strUpdate += " WHERE OFICIO = " + movOficioAccionReq.getOficio() + " "
                    + "       AND YEAR = '" + movOficioAccionReq.getYear() + "' "
                    + "       AND RAMO = '" + movOficioAccionReq.getRamo() + "' "
                    + "       AND PRG = '" + movOficioAccionReq.getPrograma() + "' "
                    + "       AND DEPTO = '" + movOficioAccionReq.getDepto() + "' "
                    + "       AND META = " + metaId + " "
                    + "       AND ACCION = " + accionId + " "
                    + "       AND REQUERIMIENTO = " + movOficioAccionReq.getRequerimiento();
        } else {
            strUpdate = "";
        }

        return strUpdate;
    }

    public String getSQLAjustarAcumuladoMesesAnteriores(MovOficiosAccionReq movOficioAccionReq, Requerimiento reqAnterior, int metaId, int accionId, int mesActual) {
        double douMesesAnteriores = 0;
        String strUpdate = "UPDATE POA.MOVOFICIOS_ACCION_REQ SET REQUERIMIENTO = REQUERIMIENTO, ";

        for (int i = mesActual - 1; i >= 0; i--) {
            switch (i) {
                case 11:
                    if (movOficioAccionReq.getNov() > 0 && movOficioAccionReq.getNov() != reqAnterior.getCantNov()) {
                        douMesesAnteriores += movOficioAccionReq.getNov();
                        strUpdate += "NOV = 0,";
                    }
                    break;
                case 10:
                    if (movOficioAccionReq.getOct() > 0 && movOficioAccionReq.getOct() != reqAnterior.getCantOct()) {
                        douMesesAnteriores += movOficioAccionReq.getOct();
                        strUpdate += "OCT = 0,";
                    }
                    break;
                case 9:
                    if (movOficioAccionReq.getSep() > 0 && movOficioAccionReq.getSep() != reqAnterior.getCantSep()) {
                        douMesesAnteriores += movOficioAccionReq.getSep();
                        strUpdate += "SEP = 0,";
                    }
                    break;
                case 8:
                    if (movOficioAccionReq.getAgo() > 0 && movOficioAccionReq.getAgo() != reqAnterior.getCantAgo()) {
                        douMesesAnteriores += movOficioAccionReq.getAgo();
                        strUpdate += "AGO = 0,";
                    }
                    break;
                case 7:
                    if (movOficioAccionReq.getJul() > 0 && movOficioAccionReq.getJul() != reqAnterior.getCantJul()) {
                        douMesesAnteriores += movOficioAccionReq.getJul();
                        strUpdate += "JUL = 0,";
                    }
                    break;
                case 6:
                    if (movOficioAccionReq.getJun() > 0 && movOficioAccionReq.getJun() != reqAnterior.getCantJun()) {
                        douMesesAnteriores += movOficioAccionReq.getJun();
                        strUpdate += "JUN = 0,";
                    }
                    break;
                case 5:
                    if (movOficioAccionReq.getMay() > 0 && movOficioAccionReq.getMay() != reqAnterior.getCantMay()) {
                        douMesesAnteriores += movOficioAccionReq.getMay();
                        strUpdate += "MAY = 0,";
                    }
                    break;
                case 4:
                    if (movOficioAccionReq.getAbr() > 0 && movOficioAccionReq.getAbr() != reqAnterior.getCantAbr()) {
                        douMesesAnteriores += movOficioAccionReq.getAbr();
                        strUpdate += "ABR = 0,";
                    }
                    break;
                case 3:
                    if (movOficioAccionReq.getMar() > 0 && movOficioAccionReq.getMar() != reqAnterior.getCantMar()) {
                        douMesesAnteriores += movOficioAccionReq.getMar();
                        strUpdate += "MAR = 0,";
                    }
                    break;
                case 2:
                    if (movOficioAccionReq.getFeb() > 0 && movOficioAccionReq.getFeb() != reqAnterior.getCantFeb()) {
                        douMesesAnteriores += movOficioAccionReq.getFeb();
                        strUpdate += "FEB = 0,";
                    }
                    break;
                case 1:
                    if (movOficioAccionReq.getEne() > 0 && movOficioAccionReq.getEne() != reqAnterior.getCantEne()) {
                        douMesesAnteriores += movOficioAccionReq.getEne();
                        strUpdate += "ENE = 0,";
                    }
                    break;
            }
        }

        if (douMesesAnteriores > 0) {
            switch (mesActual) {
                case 12:
                    strUpdate += "DIC = DIC + " + douMesesAnteriores;
                    break;
                case 11:
                    strUpdate += "NOV = NOV + " + douMesesAnteriores;
                    break;
                case 10:
                    strUpdate += "OCT = OCT + " + douMesesAnteriores;
                    break;
                case 9:
                    strUpdate += "SEP = SEP + " + douMesesAnteriores;
                    break;
                case 8:
                    strUpdate += "AGO = AGO + " + douMesesAnteriores;
                    break;
                case 7:
                    strUpdate += "JUL = JUL + " + douMesesAnteriores;
                    break;
                case 6:
                    strUpdate += "JUN = JUN + " + douMesesAnteriores;
                    break;
                case 5:
                    strUpdate += "MAY = MAY + " + douMesesAnteriores;
                    break;
                case 4:
                    strUpdate += "ABR = ABR + " + douMesesAnteriores;
                    break;
                case 3:
                    strUpdate += "MAR = MAR + " + douMesesAnteriores;
                    break;
                case 2:
                    strUpdate += "FEB = FEB + " + douMesesAnteriores;
                    break;
                case 1:
                    strUpdate += "ENE = ENE + " + douMesesAnteriores;
                    break;
            }

            strUpdate += " WHERE OFICIO = " + movOficioAccionReq.getOficio() + " "
                    + "       AND YEAR = '" + movOficioAccionReq.getYear() + "' "
                    + "       AND RAMO = '" + movOficioAccionReq.getRamo() + "' "
                    + "       AND PRG = '" + movOficioAccionReq.getPrograma() + "' "
                    + "       AND DEPTO = '" + movOficioAccionReq.getDepto() + "' "
                    + "       AND META = " + metaId + " "
                    + "       AND ACCION = " + accionId + " "
                    + "       AND REQUERIMIENTO = " + movOficioAccionReq.getRequerimiento();
        } else {
            strUpdate = "";
        }

        return strUpdate;
    }

    public String getSQLUpdateEstatusAmpliaciones(String estatus, int folio) {
        String strSQL = "UPDATE SPP.AMPLIACIONES a "
                + "SET a.STATUS = '" + estatus + "' "
                + "WHERE A.OFICIO = " + folio;

        return strSQL;
    }

    public String getSQLContFoliosMovtoMetaAmpliacionReduccionByClave(int year, String ramoId, int metaId, int oficio) {
        String strSQL = ""
                + "SELECT "
                + "	MOVOF.OFICIO EXISTEN "
                + "FROM  "
                + "	POA.MOVOFICIOS MOVOF, "
                + "	( "
                + "		SELECT "
                + "			OFICIOS.OFICIO "
                + "		FROM "
                + "			( "
                + "				( "
                + "					( "
                + "						SELECT "
                + "							MOVOFEST.OFICIO "
                + "						FROM "
                + "							POA.MOVOFICIOS_META MOVOFEST "
                + "						WHERE "
                + "							MOVOFEST.OFICIO <> " + oficio + " AND "
                + "							MOVOFEST.YEAR = " + year + " AND "
                + "							MOVOFEST.RAMO = '" + ramoId + "' AND "
                + "							MOVOFEST.META = " + metaId + " "
                + "						GROUP BY "
                + "							MOVOFEST.OFICIO "
                + "					) "
                + "					UNION ALL "
                + "					( "
                + "						SELECT "
                + "							MOVOFEST.OFICIO "
                + "						FROM "
                + "							POA.MOVOFICIOS_ESTIMACION  MOVOFEST "
                + "						WHERE "
                + "							MOVOFEST.OFICIO <> " + oficio + " AND "
                + "							MOVOFEST.YEAR = " + year + " AND "
                + "							MOVOFEST.RAMO = '" + ramoId + "' AND "
                + "							MOVOFEST.META = " + metaId + " "
                + "						GROUP BY "
                + "							MOVOFEST.OFICIO "
                + "					) "
                + "				) "
                + "			) OFICIOS "
                + "		GROUP BY "
                + "			OFICIOS.OFICIO "
                + "	) OFACCMET "
                + "WHERE "
                + "	MOVOF.OFICIO <> " + oficio + " AND "
                + "	MOVOF.OFICIO = OFACCMET.OFICIO AND "
                + "	MOVOF.STATUS <> 'A' AND "
                + "	MOVOF.STATUS <> 'C' AND "
                + "	MOVOF.STATUS <> 'X' AND "
                + "	MOVOF.STATUS <> 'R' AND "
                + "	MOVOF.STATUS <> 'K' "
                + "GROUP BY "
                + "	MOVOF.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLContFoliosMovtoAccionAmpliacionReduccionByClave(int year, String ramoId, int metaId, int accionId, int oficio) {
        String strSQL = ""
                + "SELECT "
                + "	MOVOF.OFICIO EXISTEN "
                + "FROM "
                + "	POA.MOVOFICIOS MOVOF, "
                + "	( "
                + "		SELECT "
                + "			OFICIOS.OFICIO "
                + "		FROM "
                + "			( "
                + "				( "
                + "					( "
                + "						SELECT "
                + "							MOVOFACCEST.OFICIO "
                + "						FROM "
                + "							POA.MOVOFICIOS_ACCION MOVOFACCEST "
                + "						WHERE "
                + "							MOVOFACCEST.OFICIO <> " + oficio + " AND "
                + "							MOVOFACCEST.YEAR = " + year + " AND "
                + "							MOVOFACCEST.RAMO = '" + ramoId + "' AND "
                + "							MOVOFACCEST.META = " + metaId + " AND "
                + "							MOVOFACCEST.ACCION = " + accionId + " "
                + "						GROUP BY "
                + "							MOVOFACCEST.OFICIO "
                + "					) "
                + "					UNION ALL "
                + "					( "
                + "						SELECT "
                + "							MOVOFACCEST.OFICIO "
                + "						FROM "
                + "							POA.MOVOFICIOS_ACCION_ESTIMACION MOVOFACCEST "
                + "						WHERE "
                + "							MOVOFACCEST.OFICIO <> " + oficio + " AND "
                + "							MOVOFACCEST.YEAR = " + year + " AND "
                + "							MOVOFACCEST.RAMO = '" + ramoId + "' AND "
                + "							MOVOFACCEST.META = " + metaId + " AND "
                + "							MOVOFACCEST.ACCION = " + accionId + " "
                + "						GROUP BY   "
                + "							MOVOFACCEST.OFICIO "
                + "					) "
                + "				) "
                + "			) OFICIOS "
                + "		GROUP BY "
                + "			OFICIOS.OFICIO "
                + "	) OFACCMET  "
                + "WHERE "
                + "	MOVOF.OFICIO <> 0 AND "
                + "	MOVOF.OFICIO = OFACCMET.OFICIO AND "
                + "	MOVOF.STATUS <> 'A' AND "
                + "	MOVOF.STATUS <> 'C' AND "
                + "	MOVOF.STATUS <> 'X' AND "
                + "	MOVOF.STATUS <> 'R' AND "
                + "	MOVOF.STATUS <> 'K' "
                + "GROUP BY "
                + "	MOVOF.OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLdeleteOficons(int oficio) {
        String strSQL = "DELETE DGI.OFICONS\n"
                + "WHERE OFICIO = " + oficio;
        return strSQL;
    }

    public String getSQLdeleteTipoOficio(int oficio) {
        String strSQL = "DELETE DGI.TIPOFICIO\n"
                + "WHERE OFICIO = " + oficio;
        return strSQL;
    }

    public String getSQLdeleteOficonsByTipoOficio(int oficio, String tipoOficio) {
        String strSQL = "DELETE DGI.OFICONS\n"
                + "WHERE OFICIO = " + oficio + " "
                + "      AND TIPO = '" + tipoOficio + "'";
        return strSQL;
    }

    public String getSQLdeleteTipoOficioByTipoOficio(int oficio, String tipoOficio) {
        String strSQL = "DELETE DGI.TIPOFICIO\n"
                + "WHERE OFICIO = " + oficio + " "
                + "      AND TIPO = '" + tipoOficio + "'";
        return strSQL;
    }

    public String getSQLdeleteAmpliaciones(int oficio) {
        String strSQL = "DELETE DGI.TIPOFICIO\n"
                + "WHERE OFICIO = " + oficio;
        return strSQL;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Metodos Reporte Amp/Red/Trans.">
    public String getSQLGetTipoMovimientoFiltrado() {
        String strSQL = "SELECT TIPOMOV, DESCR "
                + "FROM POA.TIPO_MOV  WHERE TIPOMOV IN ('A','T','C','R')"
                + "ORDER BY DESCR ";
        return strSQL;
    }

    public String getSQLMovimientoByTipoMovReporte(String tipoMovimiento, String estatusBase, int year) {
        String strSQL = ""
                + "SELECT "
                + "	MOVOF.OFICIO, "
                + "	MOVOF.FECHAELAB, "
                + "	MOVOF.APP_LOGIN, "
                + "	MOVOF.TIPOMOV TIPO, "
                + "	MOVOF.STATUS, "
                + "	MOVOF.FECPPTO, "
                + "	MOVOF.JUSTIFICACION, "
                + "	MOVOF.OBS_RECHAZO, "
                + "     TIPOF.TIPO AS TIPOFICIO     "
                + "FROM "
                + "	POA.MOVOFICIOS MOVOF, "
                + "	DGI.TIPOFICIO  TIPOF "
                + "WHERE "
                + "	MOVOF.TIPOMOV = '" + tipoMovimiento + "' AND "
                + "	MOVOF.STATUS = '" + estatusBase + "' AND "
                + "	EXTRACT(YEAR FROM MOVOF.FECHAELAB) = " + year + " AND "
                + "	TIPOF.OFICIO (+)= MOVOF.OFICIO "
                + "ORDER BY "
                + "	MOVOF.OFICIO, "
                + "	MOVOF.STATUS, "
                + "	MOVOF.TIPOMOV, "
                + "	TIPOF.TIPO "
                + "";
        return strSQL;
    }

    // </editor-fold>
    public String getSqlTipoSesion() {
        String strSQL = "SELECT CVE_SESION,DESCR FROM POA.TIPO_SESION";
        return strSQL;
    }

    public String getSQLValoresMomentosContables() {
        String strSQL = ""
                + "SELECT  "
                + "   SIGLAS_IDTRAMITE, "
                + "   EMPRESA_CG, "
                + "   ORIGEN_POLMCCG  "
                + "FROM  "
                + "   DGI.PARAMETROS ";

        return strSQL;
    }

    public String getSQLgetSequenceMomentoCont(boolean isActual, String siglas, int anio) {
        String strSQL = new String();
        if (isActual) {
            strSQL = "SELECT '" + siglas + "' || SUBSTR(" + anio + ",-2, 2) || LPAD(SPP.SEC_IDTRAMITE_MCCG.NEXTVAL,'10','0') AS SEQ FROM SYS.DUAL";
        } else {
            strSQL = "SELECT '" + siglas + "' || SUBSTR(" + anio + ",-2, 2) || LPAD(SPP.SEC_IDTRAMITE_MCCG_ANT.NEXTVAL,'10','0') AS SEQ FROM SYS.DUAL";
        }
        return strSQL;
    }

    public String getSQLgetSequencePoliza(boolean isActual, String fechaYYMMDD) {
        String strSQL = new String();
        if (isActual) {
            strSQL = "SELECT " + fechaYYMMDD + " || LPAD(SPP.SEC_POL_MCCG.NEXTVAL,'10','0') AS SEQ FROM SYS.DUAL";
        } else {
            strSQL = "SELECT " + fechaYYMMDD + " || LPAD(SPP.SEC_POL_MCCG_ANT.NEXTVAL,'10','0') AS SEQ FROM SYS.DUAL";
        }
        return strSQL;
    }

    public String getSQLInsertBuzonMomentos(int empresa, int ejercicio, long poliza, int origen, String descr, String fecha,
            int noRegistros, String appLogin, String fechaRegistro, String status) {
        String strSQL = "INSERT INTO S_SPP_BUZON_MOMENTOS( "
                + "EMPRESA, EJERCICIO, POLTMP_MOMENTO, ORIGEN, DESCR, "
                + "FECHA, NO_REGISTROS, APP_LOGIN, FECHA_REGISTRO, STATUS) "
                + "VALUES(" + empresa + "," + ejercicio + "," + poliza + "," + origen + ",'" + descr + "', "
                + "TO_DATE('" + fecha + "', 'DD/MM/YYYY')," + noRegistros + ",'" + appLogin + "',TO_DATE('" + fechaRegistro + "', 'DD/MM/YYYY'),'" + status + "') ";
        return strSQL;
    }

    public String getSQLInsertBuzonMomentosDet(int empresa, int ejercicio, long poliza, int origen, int consec,
            int tipoMomento, int momento, String concepto, String descr_concepto, String idTramite, String municipio, int folioSPP,
            int tramiteSPP, double importe, int tipoMovto, String ctaContable, String status, int momentoPoliza, int origenMovto,
            String partida) {
        String ctaContNiveles[] = null;
        ctaContNiveles = ctaContable.split("\\.");
        String strSQL = "INSERT INTO S_SPP_BUZON_MOMENTOS_DET( "
                + "EMPRESA, EJERCICIO, POLTMP_MOMENTO, ORIGEN, CONSEC, TIPO_MOMENTO, MOMENTO, "
                + "CONCEPTO, DESCR_CONCEPTO, ID_TRAMITE, MUNICIPIO, FOLIO_SPP, TRAMITE_SPP, "
                + "IMPORTE, TIPO_MOVTO, NIVEL1, NIVEL2, NIVEL3, NIVEL4, NIVEL5, NIVEL6, STATUS, "
                + "AJUSTE, MOMENTO_POLIZA, ORIGEN_MVTO, PARTIDA) "
                + "VALUES(" + empresa + "," + ejercicio + "," + poliza + "," + origen + "," + consec + "," + tipoMomento + "," + momento + ",'"
                + "" + concepto + "','" + descr_concepto + "','" + idTramite + "'," + municipio + "," + folioSPP + "," + tramiteSPP + ","
                + "" + importe + "," + tipoMovto + ",'" + ctaContNiveles[0] + "','" + ctaContNiveles[1] + "','" + ctaContNiveles[2] + "', "
                + "'" + ctaContNiveles[3] + "','" + ctaContNiveles[4] + "','" + ctaContNiveles[5] + "','" + status + "',"
                + "'N'," + momentoPoliza + "," + origenMovto + ", '" + partida + "') ";
        return strSQL;
    }

    public String getSQLInsertBuzonMomentosCod(int empresa, int ejercicio, long poliza, int origen, int consec,
            String idTramite, String concepto, String ramo, String depto, String finalidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral,
            double importe) {

        String strSQL = "INSERT INTO S_SPP_BUZON_MOMENTOS_COD( "
                + "EMPRESA, EJERCICIO, POLTMP_MOMENTO, ORIGEN, CONSEC, ID_TRAMITE, LLAVE, "
                + "RAMO, DEPTO, FINALIDAD, FUNCION, SUBFUNCION, PRG_CONAC, PRG, "
                + "TIPO_PROY, PROYECTO, META, ACCION, PARTIDA, TIPO_GASTO, FUENTE, "
                + "FONDO, RECURSO, MUNICIPIO, DELEGACION, REL_LABORAL, IMPORTE, AJUSTE) "
                + "VALUES(" + empresa + "," + ejercicio + "," + poliza + "," + origen + "," + consec + ",'" + idTramite + "','" + concepto + "',"
                + "'" + ramo + "', '" + depto + "', '" + finalidad + "', '" + funcion + "', '" + subfuncion + "', '" + prgConac + "', '" + programa + "', "
                + "'" + tipoProy + "', " + proyecto + ", " + meta + ", " + accion + ", '" + partida + "', '" + tipoGasto + "', '" + fuente + "', "
                + "'" + fondo + "', '" + recurso + "', '" + municipio + "'," + delegacion + " , '" + relLaboral + "', " + importe + ", 0) ";
        return strSQL;
    }

    public String getSQLProcesoMomentoByTipo(String tipo) {
        String strSQL = "SELECT CLAVE, PROCESO_MOMENTO "
                + "FROM SPP.CAT_TIPOOFICIO "
                + "WHERE TIPO = '" + tipo + "'";
        return strSQL;
    }

    public String getSQLProcesoMomentoCFG(int proceso) {
        String strSQL = "SELECT PROCESO, CONSEC, TIPO_MOMENTO, MOMENTO_POLIZA, MOMENTO, MOMENTO_RELACIONADO "
                + "FROM SPP.PROCESO_MOMENTOS_CFG "
                + "WHERE PROCESO = " + proceso;
        return strSQL;
    }

    public String getSQLMovtosContabilidadAmpliacion(int oficio, String tipoOficio, int year) {
        String strSQL = "SELECT a.OFICIO, c.TIPO, "
                + "CASE a.TIPOMOV WHEN 'C' THEN 'A' ELSE a.TIPOMOV END AS TIPOMOV, "
                + "a.PARTIDA, SUM(IMPTE) AS IMPORTE "
                + "FROM SPP.AMPLIACIONES a "
                + "INNER JOIN DGI.OFICONS c ON a.OFICIO = c.OFICIO AND a.CONSEC = c.CONSEC "
                + "INNER JOIN DGI.RAMOS r ON a.RAMO = r.RAMO AND r.YEAR = " + year + " AND r.TRANSITORIO = 'N' "
                + "WHERE a.OFICIO = " + oficio + " AND TIPO = '" + tipoOficio + "' "
                + "GROUP BY a.OFICIO, c.TIPO, CASE a.TIPOMOV WHEN 'C' THEN 'A' ELSE a.TIPOMOV END, a.PARTIDA ";
        return strSQL;
    }

    public String getSQLCuentaContable(int year, String partida, int momento) {
        String strSQL = "SELECT S_SPP_F_OBTIENE_CTAMOMENTO('" + year + "', '" + partida + "', " + momento + ") AS CTACONTABLE FROM SYS.DUAL";
        return strSQL;
    }

    public String getSQLValidaPolizaCuadrada(int empresa, int ejercicio, long poliza, int origen) {
        String strSQL = "SELECT     SUM(DECODE(TIPO_MOVTO,1,IMPORTE,3,IMPORTE,0)) AS CARGO, "
                + "            SUM(DECODE(TIPO_MOVTO,2,IMPORTE,4,IMPORTE,0)) AS ABONO, "
                + "            COUNT(CONSEC) AS REGISTROS "
                + "FROM         S_SPP_BUZON_MOMENTOS_DET "
                + "WHERE     EMPRESA = " + empresa
                + "            AND EJERCICIO = " + ejercicio
                + "            AND POLTMP_MOMENTO = " + poliza
                + "            AND ORIGEN = " + origen;
        return strSQL;
    }

    public String getSQLExistePoliza(int empresa, int ejercicio, long poliza, int origen) {
        String strSQL = "SELECT 	COUNT(POLTMP_MOMENTO) AS EXISTE "
                + "FROM 		S_CG_BUZON_MOMENTOS "
                + "WHERE 	EMPRESA = " + empresa
                + "             AND EJERCICIO = " + ejercicio
                + "             AND POLTMP_MOMENTO = " + poliza
                + "             AND ORIGEN = " + origen;
        return strSQL;
    }

    public String getSQLTraspasaEncabezadoPoliza(int empresa, int ejercicio, long poliza, int origen) {
        String strSQL = "INSERT INTO 	S_CG_BUZON_MOMENTOS(EMPRESA, EJERCICIO, POLTMP_MOMENTO, ORIGEN, DESCR,  "
                + "			FECHA, NO_REGISTROS, APP_LOGIN, FECHA_REGISTRO,STATUS) "
                + "SELECT 	EMPRESA, EJERCICIO, POLTMP_MOMENTO, ORIGEN, DESCR, "
                + "		FECHA, NO_REGISTROS, APP_LOGIN, FECHA_REGISTRO,STATUS "
                + "FROM 		S_SPP_BUZON_MOMENTOS "
                + "WHERE 	EMPRESA = " + empresa
                + "             AND EJERCICIO = " + ejercicio
                + "             AND POLTMP_MOMENTO = " + poliza
                + "             AND ORIGEN = " + origen;
        return strSQL;
    }

    public String getSQLUpdateRegistrosEncabezadoPoliza(int empresa, int ejercicio, long poliza, int origen, int registros) {
        String strSQL = "UPDATE	S_CG_BUZON_MOMENTOS "
                + " SET 		NO_REGISTROS = " + registros
                + " WHERE 	EMPRESA = " + empresa
                + "             AND EJERCICIO = " + ejercicio
                + "             AND POLTMP_MOMENTO = " + poliza
                + "             AND ORIGEN = " + origen;
        return strSQL;
    }

    public String getSQLTraspasaDetallePoliza(int empresa, int ejercicio, long poliza, int origen) {
        String strSQL = "INSERT INTO 	S_CG_BUZON_MOMENTOS_DET(EMPRESA, EJERCICIO, POLTMP_MOMENTO, ORIGEN, CONSEC, TIPO_MOMENTO, MOMENTO, "
                + " 		CONCEPTO, DESCR_CONCEPTO, ID_TRAMITE, CAJA, OFI_REC, MUNICIPIO, FOLIO_SPP,  "
                + " 		TRAMITE_SPP, PEDIDO, COMPROBANTE, IMPORTE, TIPO_MOVTO, NIVEL1, NIVEL2,  "
                + " 				NIVEL3, NIVEL4, NIVEL5, NIVEL6, STATUS, POLTMP_MOMENTO_CANC, MOMENTO_POLIZA, "
                + "				ORIGEN_MOVTO, AJUSTE, ORIGEN_CANC) "
                + "SELECT 	SPPBMD.EMPRESA, SPPBMD.EJERCICIO, SPPBMD.POLTMP_MOMENTO, SPPBMD.ORIGEN, SPPBMD.CONSEC, SPPBMD.TIPO_MOMENTO, SPPBMD.MOMENTO, "
                + "			SPPBMD.CONCEPTO, SPPBMD.DESCR_CONCEPTO, SPPBMD.ID_TRAMITE, SPPBMD.CAJA, SPPBMD.OFI_REC, SPPBMD.MUNICIPIO, SPPBMD.FOLIO_SPP,  "
                + "			SPPBMD.TRAMITE_SPP, SPPBMD.PEDIDO, SPPBMD.COMPROBANTE, SPPBMD.IMPORTE, SPPBMD.TIPO_MOVTO, SPPBMD.NIVEL1, SPPBMD.NIVEL2,  "
                + "			SPPBMD.NIVEL3, SPPBMD.NIVEL4, SPPBMD.NIVEL5, SPPBMD.NIVEL6, SPPBMD.STATUS, SPPBMD.POLTMP_MOMENTO_CANC, SPPBMD.MOMENTO_POLIZA,  "
                + "			SPPBMD.ORIGEN_MVTO, SPPBMD.AJUSTE, SPPBMD.ORIGEN_CANC "
                + "FROM S_SPP_BUZON_MOMENTOS_DET SPPBMD "
                + "WHERE 	SPPBMD.EMPRESA = " + empresa
                + "			AND SPPBMD.EJERCICIO = " + ejercicio
                + "			AND SPPBMD.POLTMP_MOMENTO = " + poliza
                + "			AND SPPBMD.ORIGEN = " + origen
                + "			AND NOT EXISTS (SELECT 	1 "
                + "					FROM 	S_CG_BUZON_MOMENTOS_DET CGBMD "
                + "					WHERE 	CGBMD.EMPRESA = SPPBMD.EMPRESA "
                + "						AND CGBMD.EJERCICIO = SPPBMD.EJERCICIO  "
                + "						AND CGBMD.POLTMP_MOMENTO = SPPBMD.POLTMP_MOMENTO  "
                + "						AND CGBMD.ORIGEN = SPPBMD.ORIGEN "
                + "						AND CGBMD.CONSEC = SPPBMD.CONSEC "
                + "						AND CGBMD.EMPRESA = " + empresa
                + "						AND CGBMD.EJERCICIO = " + ejercicio
                + "						AND CGBMD.POLTMP_MOMENTO = " + poliza
                + "						AND CGBMD.ORIGEN = " + origen + ") ";
        return strSQL;
    }

    public String getSQLMovimientoByRamoUsr(int year, String appLogin, String ramo) {
        String strSQL = "SELECT OFICIO, FECHAELAB, APP_LOGIN, TIPOMOV, STATUS, FECPPTO, FECHAAUT "
                + "FROM ( "
                + "        SELECT M.OFICIO, M.FECHAELAB, M.APP_LOGIN, M.TIPOMOV, M.STATUS, M.FECPPTO, "
                + "        MAX(B.FECHAAUT) AS FECHAAUT "
                + "        FROM POA.MOVOFICIOS M,  "
                + "             POA.MOVOFICIOS_ACCION_REQ MR,    "
                + "             POA.BITMOVTOS B,  "
                + "             DGI.DGI_USR U "
                + "        WHERE M.STATUS = 'A' AND M.TIPOMOV IN ('C','A','T') "
                + "              AND  M.OFICIO = MR.OFICIO(+) "
                + "              AND  M.OFICIO = B.OFICIO "
                + "              AND TO_CHAR(M.FECPPTO,'YYYY') = '" + year + "' "
                + "              AND TRIM(U.APP_LOGIN)=TRIM(M.APP_LOGIN) "
                + "              AND U.SYS_CLAVE=1 "
                + "              AND (U.RAMO=(SELECT RAMO FROM DGI.DGI_USR WHERE APP_LOGIN='" + appLogin + "') AND U.RAMO='" + ramo + "')"
                + "        GROUP BY M.OFICIO, M.FECHAELAB, M.APP_LOGIN, M.TIPOMOV, M.STATUS, M.FECPPTO "
                + "    UNION ALL "
                + "        SELECT M.OFICIO, M.FECHAELAB, M.APP_LOGIN, M.TIPOMOV, M.STATUS, M.FECPPTO, "
                + "        MAX(B.FECHAAUT) AS FECHAAUT "
                + "        FROM POA.MOVOFICIOS M,  "
                + "             POA.MOVOFICIOS_ACCION ME,    "
                + "             POA.BITMOVTOS B,  "
                + "             DGI.DGI_USR U   "
                + "        WHERE M.STATUS = 'A' AND M.TIPOMOV IN ('R') "
                + "               AND TRIM(U.APP_LOGIN) = TRIM(M.APP_LOGIN)    "
                + "              AND TO_CHAR(M.FECPPTO,'YYYY') = '" + year + "' "
                + "              AND M.OFICIO = ME.OFICIO "
                + "              AND M.OFICIO = B.OFICIO "
                + "              AND ME.RAMO(+)= '" + ramo + "' "
                + "              AND (U.RAMO=(SELECT RAMO FROM DGI.DGI_USR WHERE APP_LOGIN='" + appLogin + "') AND U.RAMO='" + ramo + "')"
                + "              AND U.SYS_CLAVE=1 "
                + "        GROUP BY M.OFICIO, M.FECHAELAB, M.APP_LOGIN, M.TIPOMOV, M.STATUS, M.FECPPTO "
                + "    UNION ALL "
                + "        SELECT M.OFICIO, M.FECHAELAB, M.APP_LOGIN, M.TIPOMOV, M.STATUS, M.FECPPTO, "
                + "        MAX(B.FECHAAUT) AS FECHAAUT "
                + "        FROM POA.MOVOFICIOS M,  "
                + "        POA.MOVOFICIOS_ACCION MA,   "
                + "        POA.BITMOVTOS B, "
                + "        DGI.DGI_USR U "
                + "        WHERE M.STATUS = 'A' AND M.TIPOMOV IN ('R') "
                + "              AND TRIM(U.APP_LOGIN) = TRIM(M.APP_LOGIN) "
                + "              AND TO_CHAR(M.FECPPTO,'YYYY') = '" + year + "' "
                + "              AND U.SYS_CLAVE=1 "
                + "              AND TRIM(U.APP_LOGIN) = TRIM(M.APP_LOGIN)   "
                + "              AND M.OFICIO = MA.OFICIO "
                + "              AND M.OFICIO = B.OFICIO "
                + "              AND MA.RAMO(+)= '" + ramo + "' "
                + "              AND (U.RAMO=(SELECT RAMO FROM DGI.DGI_USR WHERE APP_LOGIN='" + appLogin + "') AND U.RAMO='" + ramo + "')"
                + "        GROUP BY M.OFICIO, M.FECHAELAB, M.APP_LOGIN, M.TIPOMOV, M.STATUS, M.FECPPTO "
                + ")"
                + "GROUP BY OFICIO, FECHAELAB, APP_LOGIN, TIPOMOV, STATUS, FECPPTO, FECHAAUT "
                + "ORDER BY OFICIO";
        return strSQL;
    }

    public String getSQLUpdateEstatusTipoOficio(String estatus, int folio) {
        String strSQL = "UPDATE DGI.TIPOFICIO "
                + "SET STATUS = '" + estatus + "' "
                + "WHERE OFICIO = " + folio + " ";

        return strSQL;
    }

    public String getSQLgetMonthTramite() {
        String strSQL = "SELECT EXTRACT(MONTH FROM A.FECHAELAB) AS MONTH \n"
                + "FROM POA.MOVOFICIOS A "
                + "WHERE A.OFICIO = ? ";
        return strSQL;
    }

    public String getSQLdeleteAmpliacionesByFolio(int oficio) {
        String strSQL = "DELETE FROM SPP.AMPLIACIONES "
                + "WHERE OFICIO = " + oficio;
        return strSQL;
    }

    public String getSQLMovimientoByTipoMovART(String tipoMovimiento, String estatusBase, int year) {
        String strSQL = " "
                + "SELECT  "
                + "	MOVOF.OFICIO,  "
                + "	MOVOF.FECHAELAB,  "
                + "	MOVOF.APP_LOGIN,  "
                + "	MOVOF.TIPOMOV,  "
                + "	TIPOF.STATUS,  "
                + "	MOVOF.FECPPTO,  "
                + "	MOVOF.JUSTIFICACION,  "
                + "	MOVOF.OBS_RECHAZO, "
                + "	TIPOF.TIPO,     "
                + "     R.DESCR AS RAMO_DESCR, "
                + "     E.DESCR AS STATUSDESCR   "
                + "FROM   "
                + "	POA.MOVOFICIOS MOVOF,  "
                + "	DGI.TIPOFICIO  TIPOF,   "
                + "     POA.ESTATUS_MOV E,   "
                + "     DGI.DGI_USR U, "
                + "     DGI.RAMOS R "
                + "WHERE  "
                + "	MOVOF.TIPOMOV = '" + tipoMovimiento + "' AND   "
                + "     TIPOF.STATUS = E.ESTATUS AND   "
                + "     TRIM(MOVOF.APP_LOGIN) = TRIM(U.APP_LOGIN) AND"
                + "     R.RAMO = U.RAMO AND"
                + "     U.SYS_CLAVE = 1 AND "
                + "     R.YEAR = " + year + " AND  "
                + "	EXTRACT(YEAR FROM MOVOF.FECHAELAB) = " + year + " AND  "
                + "	TIPOF.OFICIO (+)= MOVOF.OFICIO AND "
                + "	TIPOF.STATUS = '" + estatusBase + "' "
                + "ORDER BY   "
                + "	MOVOF.OFICIO,  "
                + "	MOVOF.STATUS,  "
                + "	MOVOF.TIPOMOV,  "
                + "	TIPOF.TIPO  "
                + " ";
        return strSQL;
    }

    public String getSQLMovimientoByTipoMovEstatusMovAppLoginART() {
        String strSQL = "SELECT  \n"
                + "    MOVOF.OFICIO,  \n"
                + "    MOVOF.FECHAELAB,  \n"
                + "    MOVOF.APP_LOGIN,  \n"
                + "    MOVOF.TIPOMOV,  \n"
                + "    TIPOF.STATUS,  \n"
                + "    MOVOF.FECPPTO,  \n"
                + "    MOVOF.JUSTIFICACION,  \n"
                + "    MOVOF.OBS_RECHAZO, \n"
                + "    TIPOF.TIPO,     \n"
                + "    R.DESCR AS RAMO_DESCR, \n"
                + "    E.DESCR AS STATUSDESCR   \n"
                + "FROM   \n"
                + "    POA.MOVOFICIOS MOVOF,  \n"
                + "    DGI.TIPOFICIO  TIPOF,   \n"
                + "    POA.ESTATUS_MOV E,   \n"
                + "    DGI.DGI_USR U, \n"
                + "    DGI.RAMOS R \n"
                + "WHERE  \n"
                + "    EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?  AND \n"
                + "    MOVOF.TIPOMOV = ? AND   \n"
                + "    TRIM(MOVOF.APP_LOGIN) IN \n"
                + "        (SELECT UT.APP_LOGIN FROM DGI.DGI_USR UT WHERE UT.RAMO = U.RAMO AND UT.SYS_CLAVE = 1) AND\n"
                + "    U.APP_LOGIN = ? AND \n"
                + "    U.SYS_CLAVE = 1 AND \n"
                + "    R.RAMO = U.RAMO AND\n"
                + "    R.YEAR = ? AND   \n"
                + "    TIPOF.OFICIO (+)= MOVOF.OFICIO  AND \n"
                + "    TIPOF.STATUS = ? AND\n"
                + "    TIPOF.STATUS = E.ESTATUS\n"
                + "ORDER BY   \n"
                + "    MOVOF.OFICIO,  \n"
                + "    MOVOF.STATUS,  \n"
                + "    MOVOF.TIPOMOV,  \n"
                + "    TIPOF.TIPO  ";
        return strSQL;
    }

    public String getSQLgetFechaContabilidad() {
        return "SELECT FECHAPOLCONTABLE FROM DGI.PARAMETROS";
    }

    public String getSQLGetValidaMetasNuevasFromMovoficios(int folio, int year, String ramo, int meta) {
        String strSQL = "SELECT  1 "
                + "        FROM POA.MOVOFICIOS_META m "
                + "        WHERE m.OFICIO = " + folio
                + "                AND m.YEAR = '" + year + "' "
                + "                AND m.RAMO = '" + ramo + "' "
                + "                AND m.META = " + meta
                + "                AND m.NVA_CREACION = 'S'"
                + "                AND m.META < " + 0;

        return strSQL;
    }

    public String getSQLGetValidaAccionesNuevasFromMovoficios(int folio, int year, String ramo, int meta, int accion) {
        String strSQL = "SELECT  1 "
                + "        FROM POA.MOVOFICIOS_ACCION m "
                + "        WHERE m.OFICIO = " + folio
                + "                AND m.YEAR = '" + year + "' "
                + "                AND m.RAMO = '" + ramo + "' "
                + "                AND m.META = " + meta
                + "                AND m.ACCION = " + accion
                + "                AND m.NVA_CREACION = 'S'"
                + "                AND m.ACCION < " + 0;

        return strSQL;
    }

    public String getSQLInsertEstimacionFromMovOficios(int folio, int year, String ramo, int meta, int claveMetaNueva) {
        String strSQL = "INSERT INTO DGI.ESTIMACION a (a.META, a.PERIODO, a.RAMO, a.VALOR, a.YEAR) "
                + "      SELECT  " + claveMetaNueva + ",  m.PERIODO, m.RAMO, m.VALOR, m.YEAR "
                + "        FROM POA.MOVOFICIOS_ESTIMACION m "
                + "        WHERE m.OFICIO = " + folio
                + "                AND m.YEAR = '" + year + "' "
                + "                AND m.RAMO = '" + ramo + "' "
                + "                AND m.META = " + meta
                + "                AND m.NVA_CREACION = 'S'";

        return strSQL;
    }

    public String getSQLInsertAccionEstimacionFromMovOficios(int folio, int year, String ramo, int meta, int accion, int claveAccionNueva) {
        String strSQL = "INSERT INTO DGI.ACCION_ESTIMACION a (a.ACCION, a.META, a.PERIODO, a.RAMO, a.VALOR, a.YEAR) "
                + "      SELECT  " + claveAccionNueva + ", m.META, m.PERIODO, m.RAMO, m.VALOR, m.YEAR "
                + "        FROM POA.MOVOFICIOS_ACCION_ESTIMACION m "
                + "        WHERE m.OFICIO = " + folio
                + "                AND m.YEAR = '" + year + "' "
                + "                AND m.RAMO = '" + ramo + "' "
                + "                AND m.META = " + meta
                + "                AND m.ACCION = " + accion
                + "                AND m.NVA_CREACION = 'S'";

        return strSQL;
    }

    public String getSQLUpdateMovoficiosEstimacion(int metaNva, int oficio, int year, String ramo, int metaAnt) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ESTIMACION "
                + "SET META = " + metaNva + " "
                + "WHERE OFICIO = " + oficio + " "
                + "   AND YEAR = " + year + " "
                + "   AND RAMO = '" + ramo + "' "
                + "   AND META = " + metaAnt + " ";

        return strSQL;
    }

    public String getSQLUpdateMovoficiosAccionEstimacion(int accionNva, int oficio, int year, String ramo, int meta, int accionAnt) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ACCION_ESTIMACION "
                + "SET ACCION = " + accionNva + " "
                + "WHERE OFICIO = " + oficio + " "
                + "   AND YEAR = " + year + " "
                + "   AND RAMO = '" + ramo + "' "
                + "   AND META = " + meta + " "
                + "   AND ACCION = " + accionAnt + " ";

        return strSQL;
    }

    public String getSQLEstatusBaseByTipoFlujo(int tipoFlujo) {
        String strSQL = "SELECT T.ESTATUS_BASE "
                + "FROM  POA.TIPO_FLUJO T "
                + "WHERE T.TIPO_FLUJO = " + tipoFlujo;
        return strSQL;
    }

    public String getSQLisUsuarioCapturaEspecial() {
        String strSQL = "SELECT COUNT(1) AS USU FROM DGI.USUARIO_CAPTURA_ESPECIAL\n"
                + "WHERE APP_LOGIN = ? ";
        return strSQL;
    }

    public String getSQLgetDisponibleByRangoMes() {
        String strSQL = "SELECT \n"
                + "    SUM(NVL(ASIGNADO,0)) AS ASIGNADO, \n"
                + "    SUM(NVL(ACTUALIZADO,0)) AS ACTUALIZADO ,\n"
                + "    SUM(NVL(COMPROMISO,0)) AS COMPROMISO , \n"
                + "    SUM(NVL(EJERCIDO,0)) AS EJERCIDO \n"
                + "FROM                   \n"
                + "    SPP.PPTO\n"
                + "WHERE                \n"
                + "    YEAR = ? \n"
                + "    AND RAMO = ? \n"
                + "    AND DEPTO = ? \n"
                + "    AND FINALIDAD = ? \n"
                + "    AND FUNCION = ? \n"
                + "    AND SUBFUNCION = ? \n"
                + "    AND PRG_CONAC = ? \n"
                + "    AND PRG = ? \n"
                + "    AND TIPO_PROY = ? \n"
                + "    AND PROYECTO = ? \n"
                + "    AND META = ? \n"
                + "    AND ACCION = ? \n"
                + "    AND PARTIDA = ? \n"
                + "    AND TIPO_GASTO = ? \n"
                + "    AND FUENTE = ? \n"
                + "    AND FONDO = ? \n"
                + "    AND RECURSO = ? \n"
                + "    AND MUNICIPIO = ? \n"
                + "    AND DELEGACION = ? \n"
                + "    AND REL_LABORAL = ? \n"
                + "    AND MES BETWEEN ? AND ? ";
        return strSQL;
    }

    public String getSQLUpdateAmpliacionesByTipomov(String status, int folio, String tipoMov) {
        String strSQL = "UPDATE SPP.AMPLIACIONES A\n"
                + "SET A.STATUS = '" + status + "'\n"
                + "WHERE A.OFICIO = " + folio + " \n"
                + "    AND A.TIPOMOV = '" + tipoMov + "'";
        return strSQL;
    }

    public String getSQLUpdateTransferenciaByTipomov(String status, int folio, String tipoMov) {
        String strSQL = "UPDATE SPP.TRANSFERENCIAS A\n"
                + "SET A.STATUS = '" + status + "'\n"
                + "WHERE A.OFICIO = " + folio + " \n"
                + "    AND A.TIPOMOV = '" + tipoMov + "'";
        return strSQL;
    }

    public String getSQLUpdateMovoficiosMetaAccionEstimacion(int metaNva, int oficio, int year, String ramo, int metaAnt) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ACCION_ESTIMACION "
                + "SET META = " + metaNva + " "
                + "WHERE OFICIO = " + oficio + " "
                + "   AND YEAR = " + year + " "
                + "   AND RAMO = '" + ramo + "' "
                + "   AND META = " + metaAnt + " ";

        return strSQL;
    }

    public String getSQLUpdateMovoficiosMetaAccionReq(int metaNva, int oficio, int year, String ramo, int metaAnt) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ACCION_REQ "
                + "SET META = " + metaNva + " "
                + "WHERE OFICIO = " + oficio + " "
                + "   AND YEAR = " + year + " "
                + "   AND RAMO = '" + ramo + "' "
                + "   AND META = " + metaAnt + " ";

        return strSQL;
    }

    public String getSQLgetQuincePorCiento() {
        String strSQL = "SELECT SUM (ASIGNADO) AS MONTO \n"
                + "FROM   SPP.PPTO \n"
                + "WHERE  ( YEAR = ? ) AND\n"
                + "       ( RAMO = ?  ) AND\n"
                + "       ( PARTIDA = ? )\n ";
        return strSQL;
    }

    public String getSQLgetMovEstimacionTransferencia() {
        String strSQL = "SELECT ME.YEAR ,ME.OFICIO, ME.RAMO, ME.META "
                + "FROM POA.MOVOFICIOS_ESTIMACION ME "
                + "WHERE ME.OFICIO = ? "
                + "    AND ME.YEAR = ? "
                //Se quita el Indicador para evaluar si se usa o no en el sistema
                //+ "    AND ME.IND_TRANSF = ? "
                + "GROUP BY ME.YEAR, ME.OFICIO, ME.RAMO, ME.META";
        return strSQL;
    }

    public String getSQLgetMovEstimacionTransferenciarecibe() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO, ME.VALOR, ME.YEAR  "
                + "FROM POA.MOVOFICIOS_ESTIMACION ME "
                + "WHERE ME.OFICIO = ? "
                + "    AND ME.YEAR = ? "
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO";
        return strSQL;
    }

    public String getSQLgetMovEstimacionRechazoTransferenciarecibe() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO, ME.VALOR, ME.YEAR  "
                + "FROM POA.MOVOFICIOS_ESTIMACION ME "
                + "WHERE ME.OFICIO = ? "
                + "    AND ME.YEAR = ? "
                + "    AND ME.RAMO = ? "
                + "    AND ME.META = ? "
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO";
        return strSQL;
    }

    public String getSQLgetMovOficiosMetaTransferencia() {
        String strSQL = "SELECT \n"
                + "MM.OFICIO, MM.RAMO, MM.PRG, MM.META, MM.CALCULO, MM.TIPO, MM.CVE_MEDIDA,MM.FINALIDAD, MM.FUNCION, MM.SUBFUNCION,\n"
                + "MM.TIPO_PROY, MM.PROY, MM.LINEA, MM.TIPO_COMPROMISO,MM.PONDERADO, MM.LINEA_SECTORIAL, MM.DESCR, MM.CRITERIO, MM.NVA_CREACION \n"
                + "FROM POA.MOVOFICIOS_META MM\n"
                + "WHERE \n"
                + "    MM.OFICIO = ?"
                + "    AND MM.YEAR = ?"
                + "    AND MM.RAMO = ?"
                + "    AND MM.META = ? ";
        return strSQL;
    }

    public String getSQLgetMovAccionEstimacionTransferencia() {
        String strSQL = "SELECT ME.OFICIO,ME.YEAR, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO, ME.VALOR  "
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION ME  "
                + "WHERE ME.OFICIO = ?  "
                + "    AND ME.YEAR = ? "
                //Se quita el Indicador para evaluar si se usa o no en el sistema
                //+ "    AND ME.IND_TRANSF = ?"
                + "ORDER BY ME.OFICIO,ME.YEAR, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO  ";
        return strSQL;
    }

    public String getSQLgetMovAccionEstimacionRechazoTransferencia() {
        String strSQL = "SELECT ME.OFICIO,ME.YEAR, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO, ME.VALOR  "
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION ME  "
                + "WHERE ME.OFICIO = ?  "
                + "    AND ME.YEAR = ? "
                + "    AND ME.RAMO = ? "
                + "    AND ME.META = ? "
                + "    AND ME.ACCION = ? "
                + "ORDER BY ME.OFICIO,ME.YEAR, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO  ";
        return strSQL;
    }

    public String getSQLgetMovOficioAccionTransferencia() {
        String strSQL = "SELECT MA.OFICIO,MA.YEAR, MA.RAMO, MA.PRG, MA.DEPTO,MA.META,MA.ACCION, MA.DESCR,MA.CVE_MEDIDA,\n"
                + "MA.CALCULO, MA.TIPO_GASTO, MA.LINEA, MA.GRUPO_POBLACION, MA.BENEF_HOMBRE, MA.BENEF_MUJER, Ma.MPO,\n"
                + "MA.LOCALIDAD, MA.LINEA_SECTORIAL, MA.NVA_CREACION, MA.OBRA FROM POA.MOVOFICIOS_ACCION MA\n"
                + "WHERE MA.OFICIO = ? "
                + "     AND MA.YEAR = ? "
                + "     AND MA.RAMO = ?"
                + "     AND MA.META = ? "
                + "     AND MA.ACCION = ? ";
        //Se quita el Indicador para evaluar si se usa o no en el sistema
        //+ "     AND MA.IND_TRANSF = ?";
        return strSQL;
    }

    public String getSLQgetTransferenciasByFolioConsec() {
        String strSQL = "SELECT A.RAMO,A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION,\n"
                + "A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, A.DELEGACION, A.REL_LABORAL, A.IMPTE, A.CONSEC, A.TIPOMOV, A.REQUERIMIENTO, A.TIPOMOV \n"
                + "FROM SPP.TRANSFREC A \n"
                + "WHERE A.OFICIO = ? "
                + "AND A.CONSEC = ? "
                + "ORDER BY A.OFICIO, A.CONSEC";
        return strSQL;
    }

    public String getSQLgetMovEstimacionCompletoTransferencia() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO, ME.VALOR, ME.YEAR "
                + "FROM POA.MOVOFICIOS_ESTIMACION ME "
                + "WHERE ME.OFICIO = ? "
                + "    AND ME.YEAR = ? "
                + "    AND ME.RAMO = ? "
                + "    AND ME.META = ? "
                //Se quita el Indicador para evaluar si se usa o no en el sistema
                //+ "    AND ME.IND_TRANSF = ?"
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.PERIODO";
        return strSQL;
    }

    public String getSLQgetTransferenciasByFolio() {
        String strSQL = "SELECT A.RAMO,A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION,\n"
                + "A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, A.DELEGACION, A.REL_LABORAL,A.STATUS, A.IMPTE, A.CONSEC, R.TRANSITORIO, A.CONSIDERAR \n"
                + "FROM SPP.TRANSFERENCIAS A, DGI.RAMOS R\n"
                + "WHERE A.OFICIO = ? "
                + "AND R.YEAR = ? "
                + "AND R.RAMO = A.RAMO "
                + "ORDER BY A.OFICIO, A.CONSEC ";
        return strSQL;
    }

    public String getSQLGetTransferenciasByTipoOficio() {
        String strSQL = "SELECT A.RAMO,A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION,\n"
                + "A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, A.DELEGACION, A.REL_LABORAL,A.STATUS, A.IMPTE,"
                + "  A.CONSEC, R.TRANSITORIO, A.CONSIDERAR \n"
                + "FROM SPP.TRANSFERENCIAS A, DGI.TIPOFICIO T, DGI.OFICONS O, DGI.RAMOS R\n"
                + "WHERE\n"
                + "    A.OFICIO = T.OFICIO "
                + "    AND A.CONSEC = O.CONSEC "
                + "    AND T.OFICIO = ? "
                + "    AND T.TIPO = ? "
                + "    AND O.OFICIO = T.OFICIO "
                + "    AND O.TIPO = T.TIPO "
                + "    AND R.YEAR = ? "
                + "    AND R.RAMO = A.RAMO "
                + " ORDER BY A.OFICIO, A.CONSEC";
        return strSQL;
    }

    public String getSQLgetMovAccionEstimacionCompletoTransferencia() {
        String strSQL = "SELECT ME.OFICIO, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO, ME.VALOR , ME.YEAR  "
                + "FROM POA.MOVOFICIOS_ACCION_ESTIMACION ME  "
                + "WHERE ME.OFICIO = ?  "
                + "    AND ME.YEAR = ? "
                + "    AND ME.RAMO = ? "
                + "    AND ME.META = ? "
                + "    AND ME.ACCION = ? "
                //Se quita el Indicador para evaluar si se usa o no en el sistema
                // + "    AND ME.IND_TRANSF = ? "
                + "ORDER BY ME.OFICIO, ME.RAMO, ME.META, ME.ACCION, ME.PERIODO, ME.VALOR  ";
        return strSQL;
    }

    public String getSQLUpdateEstatusTransferencia(String estatus, int folio) {
        String strSQL = "UPDATE SPP.TRANSFERENCIAS a "
                + "SET a.STATUS = '" + estatus + "' "
                + "WHERE A.OFICIO = " + folio;

        return strSQL;
    }

    public String getSQLUpdateEstatusTransferenciaTipoOficio(String estatus, int folio, String tipoOficio) {
        String strSQL = "UPDATE SPP.TRANSFERENCIAS a "
                + "SET a.STATUS = '" + estatus + "' "
                + "WHERE A.OFICIO = " + folio + " AND "
                + "   a.CONSEC IN ( SELECT b.CONSEC "
                + "               FROM SPP.TRANSFERENCIAS b "
                + "               INNER JOIN DGI.OFICONS c ON b.OFICIO = c.OFICIO AND b.CONSEC = c.CONSEC AND c.TIPO = '" + tipoOficio + "' "
                + "               INNER JOIN DGI.TIPOFICIO t ON t.OFICIO = c.OFICIO AND t.TIPO = c.TIPO "
                + "               WHERE b.OFICIO = " + folio + " "
                + "             ) ";

        return strSQL;
    }

    public String getSQLMovtosContabilidadTransferencia(int oficio, String tipoOficio, int year) {
        String strSQL = "SELECT a.OFICIO, c.TIPO, "
                + "'R' AS TIPOMOV, "
                + "a.PARTIDA, SUM(IMPTE) AS IMPORTE "
                + "FROM SPP.TRANSFERENCIAS a "
                + "INNER JOIN DGI.OFICONS c ON a.OFICIO = c.OFICIO AND a.CONSEC = c.CONSEC "
                + "INNER JOIN DGI.RAMOS r ON a.RAMO = r.RAMO AND r.YEAR = " + year + " AND r.TRANSITORIO = 'N' "
                + "WHERE a.OFICIO = " + oficio + " AND TIPO = '" + tipoOficio + "' "
                + "GROUP BY a.OFICIO, c.TIPO, 'R', a.PARTIDA "
                + "UNION ALL "
                + "SELECT a.OFICIO, c.TIPO, "
                + "'A' AS TIPOMOV,  "
                + "a.PARTIDA, SUM(IMPTE) AS IMPORTE "
                + "FROM SPP.TRANSFREC a "
                + "INNER JOIN DGI.OFICONS c ON a.OFICIO = c.OFICIO AND a.CONSEC = c.CONSEC "
                + "INNER JOIN DGI.RAMOS r ON a.RAMO = r.RAMO AND r.YEAR = " + year + " AND r.TRANSITORIO = 'N' "
                + "WHERE a.OFICIO = " + oficio + " AND TIPO = '" + tipoOficio + "' "
                + "GROUP BY a.OFICIO, c.TIPO, 'A', a.PARTIDA ";
        return strSQL;
    }

    public String getSQLMovCaratula() {
        String strSQL = ""
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
                + "	(SELECT REVS.DESCR_CORTA||' '||REVS.DESCR FROM POA.REVISIONES_CARATULA REVS WHERE REVS.REVISION = CAR.NUMERO_SESION)||' '||LOWER(TIS.DESCR) DESCR "
                + "FROM "
                + "	POA.CARATULA_MOVOFICIO CARMOV, "
                + "	POA.CARATULA CAR, "
                + "	POA.TIPO_SESION TIS "
                + "WHERE "
                + "	CARMOV.YEAR = ? AND "
                + "	CARMOV.RAMO = ? AND "
                + "	CARMOV.OFICIO = ? AND "
                + "	CAR.YEAR = CARMOV.YEAR AND "
                + "	CAR.RAMO = CARMOV.RAMO AND "
                + "	CAR.ID_CARATULA = CARMOV.ID_CARATULA AND "
                + "	TIS.CVE_SESION = CAR.TIPO_SESION "
                + "";
        return strSQL;
    }

    public String getSQLInsertMovCaratula(int oficio, int year, String ramoSession, long selCaratula) {
        String strSQL = new String();
        strSQL = ""
                + "INSERT INTO  "
                + "	POA.CARATULA_MOVOFICIO CM  "
                + "	( "
                + "		CM.YEAR, "
                + "		CM.RAMO, "
                + "		CM.ID_CARATULA, "
                + "		CM.OFICIO "
                + "	) "
                + "VALUES  "
                + "	(  "
                + "		" + year + ", "
                + "		'" + ramoSession + "', "
                + "		" + selCaratula + ", "
                + "		" + oficio + " "
                + "	) "
                + "";
        return strSQL;
    }

    public String getSQLDeleteMovCaratulaReprogramacion(int folio) {
        String strSQL = ""
                + "DELETE FROM "
                + "	POA.CARATULA_MOVOFICIO CM "
                + "WHERE "
                + "	CM.OFICIO = '" + folio + "' "
                + "";
        return strSQL;
    }

    public String getSQLUpdateMovCaratula(int oficio, int year, String ramoSession, long selCaratula, long actCaratula) {
        String strSQL = new String();
        strSQL = ""
                + "UPDATE "
                + "	POA.CARATULA_MOVOFICIO CM "
                + "SET "
                + "	CM.ID_CARATULA = " + selCaratula + " "
                + "WHERE "
                + "	CM.YEAR = " + year + " AND "
                + "	CM.RAMO = '" + ramoSession + "' AND "
                + "	CM.ID_CARATULA = " + actCaratula + " AND "
                + "	CM.OFICIO = " + oficio + " "
                + "";
        return strSQL;
    }

    public String getSQLgetMovimientoAcumulado() {
        String strSQL = "SELECT amp.amp+trans.trans+rec.rec+red.red AS ACUMULADO\n"
                + "FROM ( SELECT NVL(SUM(SPP.AMPLIACIONES.IMPTE),0)  as amp\n"
                + "FROM SPP.AMPLIACIONES,\n"
                + "     DGI.OFICONS,\n"
                + "     POA.MOVOFICIOS\n"
                + "WHERE  SPP.AMPLIACIONES.OFICIO = DGI.OFICONS.OFICIO AND\n"
                + "         SPP.AMPLIACIONES.CONSEC = DGI.OFICONS.CONSEC AND\n"
                + "                  DGI.OFICONS.OFICIO = POA.MOVOFICIOS.OFICIO AND \n"
                + "         SPP.AMPLIACIONES.PARTIDA = ?  AND \n"
                + "          SPP.AMPLIACIONES.RAMO = ?  AND\n"
                + "          DGI.OFICONS.TIPO IN ('V','A') AND\n"
                + "          SPP.AMPLIACIONES.STATUS in ('A','T','V','W','Y','X') AND\n"
                + "          SPP.AMPLIACIONES.tipomov in ('A','C') and\n"
                + "          POA.MOVOFICIOS.FECPPTO <= sysdate AND\n"
                + "          TO_CHAR(POA.MOVOFICIOS.FECPPTO,'YYYY')= ? ) amp,\n"
                + "( SELECT NVL(SUM(SPP.AMPLIACIONES.IMPTE),0) *-1 as red\n"
                + "FROM SPP.AMPLIACIONES,\n"
                + "DGI.OFICONS,\n"
                + "     POA.MOVOFICIOS\n"
                + "WHERE SPP.AMPLIACIONES.OFICIO = DGI.OFICONS.OFICIO AND\n"
                + "        SPP.AMPLIACIONES.CONSEC = DGI.OFICONS.CONSEC AND\n"
                + "                  DGI.OFICONS.OFICIO = POA.MOVOFICIOS.OFICIO AND \n"
                + "                  SPP.AMPLIACIONES.PARTIDA = ? AND  \n"
                + "          SPP.AMPLIACIONES.RAMO = ?  AND\n"
                + "          SPP.AMPLIACIONES.STATUS in ('A','T','V','W','Y','X') AND\n"
                + "          DGI.OFICONS.TIPO IN ('V','A') AND \n"
                + "          SPP.AMPLIACIONES.TIPOMOV='R' and\n"
                + "          POA.MOVOFICIOS.FECPPTO <= sysdate AND\n"
                + "         TO_CHAR(POA.MOVOFICIOS.FECPPTO,'YYYY')= ? ) red,               \n"
                + "\n"
                + "(  SELECT   NVL(SUM(SPP.TRANSFERENCIAS.IMPTE) ,0) as trans\n"
                + "FROM SPP.TRANSFERENCIAS,\n"
                + "   DGI.OFICONS,\n"
                + "   POA.MOVOFICIOS\n"
                + "WHERE  SPP.TRANSFERENCIAS.OFICIO = DGI.OFICONS.OFICIO AND\n"
                + "SPP.TRANSFERENCIAS.CONSEC = DGI.OFICONS.CONSEC AND\n"
                + "   DGI.OFICONS.OFICIO = POA.MOVOFICIOS.OFICIO and\n"
                + "SPP.TRANSFERENCIAS.PARTIDA = ? AND\n"
                + "          SPP.TRANSFERENCIAS.RAMO = ? AND\n"
                + "          SPP.TRANSFERENCIAS.STATUS in ('A','T','V','W','Y','X') AND\n"
                + "DGI.OFICONS.TIPO IN ('V','A') AND\n"
                + "          POA.MOVOFICIOS.FECPPTO <= sysdate AND\n"
                + "          TO_CHAR(POA.MOVOFICIOS.FECPPTO,'YYYY')= ? ) trans,\n"
                + "\n"
                + "(  SELECT NVL(SUM (SPP.TRANSFREC.IMPTE),0)  as rec\n"
                + "FROM SPP.TRANSFERENCIAS,  \n"
                + "    SPP.TRANSFREC ,\n"
                + "   DGI.OFICONS,\n"
                + "   POA.MOVOFICIOS\n"
                + "WHERE SPP.TRANSFREC.OFICIO = SPP.TRANSFERENCIAS.OFICIO AND\n"
                + "         SPP.TRANSFREC.CONSEC = SPP.TRANSFERENCIAS.CONSEC AND\n"
                + "         SPP.TRANSFERENCIAS.OFICIO = DGI.OFICONS.OFICIO and\n"
                + "         SPP.TRANSFERENCIAS.CONSEC = DGI.OFICONS.CONSEC and\n"
                + "         DGI.OFICONS.OFICIO = POA.MOVOFICIOS.OFICIO AND\n"
                + "        DGI.OFICONS.TIPO IN ('V','A') AND\n"
                + "         SPP.TRANSFREC.PARTIDA = ?  AND \n"
                + "         SPP.TRANSFREC.RAMO = ?  AND \n"
                + "         SPP.TRANSFERENCIAS.STATUS in ('A','T','V','W','Y','X') AND\n"
                + "         POA.MOVOFICIOS.FECPPTO <= sysdate AND\n"
                + "         TO_CHAR(POA.MOVOFICIOS.FECPPTO,'YYYY') = ? ) rec";
        return strSQL;
    }

    public String getSQLgetMovimientoAcumuladoRechazado() {
        String strSQL = "SELECT amp.amp+trans.trans+rec.rec+red.red AS ACUMULADO\n"
                + " FROM ( SELECT NVL(SUM(SPP.AMPLIACIONES.IMPTE),0)  as amp\n"
                + " FROM SPP.AMPLIACIONES,\n"
                + "      DGI.OFICONS,\n"
                + "      POA.MOVOFICIOS\n"
                + " WHERE  SPP.AMPLIACIONES.OFICIO = DGI.OFICONS.OFICIO AND\n"
                + "          SPP.AMPLIACIONES.CONSEC = DGI.OFICONS.CONSEC AND\n"
                + "                   DGI.OFICONS.OFICIO = POA.MOVOFICIOS.OFICIO AND                                \n"
                + "          SPP.AMPLIACIONES.PARTIDA = ?  AND                  \n"
                + "           SPP.AMPLIACIONES.RAMO = ?  AND\n"
                + "           DGI.OFICONS.TIPO IN ('V','A') AND\n"
                + "           SPP.AMPLIACIONES.STATUS = ('R') AND\n"
                + "           SPP.AMPLIACIONES.tipomov in ('A','C') and\n"
                + "           POA.MOVOFICIOS.OFICIO = ? AND\n"
                + "           TO_CHAR(POA.MOVOFICIOS.FECPPTO,'YYYY')= ? ) amp,\n"
                + " ( SELECT NVL(SUM(SPP.AMPLIACIONES.IMPTE),0) *-1 as red\n"
                + " FROM SPP.AMPLIACIONES,\n"
                + " DGI.OFICONS,\n"
                + "      POA.MOVOFICIOS\n"
                + " WHERE SPP.AMPLIACIONES.OFICIO = DGI.OFICONS.OFICIO AND\n"
                + "         SPP.AMPLIACIONES.CONSEC = DGI.OFICONS.CONSEC AND\n"
                + "                   DGI.OFICONS.OFICIO = POA.MOVOFICIOS.OFICIO AND                                \n"
                + "                   SPP.AMPLIACIONES.PARTIDA = ? AND                  \n"
                + "           SPP.AMPLIACIONES.RAMO = ?  AND\n"
                + "           SPP.AMPLIACIONES.STATUS = ('R') AND\n"
                + "           DGI.OFICONS.TIPO IN ('V','A') AND                                                 \n"
                + "           SPP.AMPLIACIONES.TIPOMOV='R' and\n"
                + "           POA.MOVOFICIOS.OFICIO = ? AND\n"
                + "          TO_CHAR(POA.MOVOFICIOS.FECPPTO,'YYYY')= ? ) red,               \n"
                + " \n"
                + " (  SELECT   NVL(SUM(SPP.TRANSFERENCIAS.IMPTE) ,0) as trans\n"
                + " FROM SPP.TRANSFERENCIAS,\n"
                + "    DGI.OFICONS,\n"
                + "    POA.MOVOFICIOS\n"
                + " WHERE  SPP.TRANSFERENCIAS.OFICIO = DGI.OFICONS.OFICIO AND\n"
                + " SPP.TRANSFERENCIAS.CONSEC = DGI.OFICONS.CONSEC AND\n"
                + "    DGI.OFICONS.OFICIO = POA.MOVOFICIOS.OFICIO and\n"
                + " SPP.TRANSFERENCIAS.PARTIDA = ? AND\n"
                + "           SPP.TRANSFERENCIAS.RAMO = ? AND\n"
                + "           SPP.TRANSFERENCIAS.STATUS = ('R') AND\n"
                + " DGI.OFICONS.TIPO IN ('V','A') AND\n"
                + "           POA.MOVOFICIOS.OFICIO = ? AND\n"
                + "           TO_CHAR(POA.MOVOFICIOS.FECPPTO,'YYYY')= ? ) trans,\n"
                + " \n"
                + " (  SELECT NVL(SUM (SPP.TRANSFREC.IMPTE),0)  as rec\n"
                + " FROM SPP.TRANSFERENCIAS,  \n"
                + "     SPP.TRANSFREC ,\n"
                + "    DGI.OFICONS,\n"
                + "    POA.MOVOFICIOS\n"
                + " WHERE SPP.TRANSFREC.OFICIO = SPP.TRANSFERENCIAS.OFICIO AND\n"
                + "          SPP.TRANSFREC.CONSEC = SPP.TRANSFERENCIAS.CONSEC AND\n"
                + "          SPP.TRANSFERENCIAS.OFICIO = DGI.OFICONS.OFICIO and\n"
                + "          SPP.TRANSFERENCIAS.CONSEC = DGI.OFICONS.CONSEC and\n"
                + "          DGI.OFICONS.OFICIO = POA.MOVOFICIOS.OFICIO AND\n"
                + "         DGI.OFICONS.TIPO IN ('V','A') AND\n"
                + "          SPP.TRANSFREC.PARTIDA = ?  AND \n"
                + "          SPP.TRANSFREC.RAMO = ?  AND \n"
                + "          SPP.TRANSFERENCIAS.STATUS = ('R') AND\n"
                + "          POA.MOVOFICIOS.OFICIO = ? AND\n"
                + "          TO_CHAR(POA.MOVOFICIOS.FECPPTO,'YYYY') = ? ) rec";
        return strSQL;
    }

    public String getSQLgetGrupoPartida() {
        String strSQL = "SELECT G.GRUPO FROM S_DGI_GRUPOS G\n"
                + "WHERE G.GRUPO = (\n"
                + "    SELECT SG.GRUPO FROM S_DGI_SUBGRUPOS SG\n"
                + "    WHERE SG.SUBGRUPO = (\n"
                + "        SELECT SSG.SUBGRUPO FROM S_DGI_SUBSUBGPO SSG\n"
                + "            WHERE SSG.SUBSUBGRUPO = (\n"
                + "                SELECT P.SUBSUBGPO FROM DGI.PARTIDA P\n"
                + "                WHERE P.PARTIDA = ? \n"
                + "                    AND P.YEAR = ? \n"
                + "            )\n"
                + "        AND SSG.YEAR = ? \n"
                + "    )\n"
                + "    AND SG.YEAR = ? \n"
                + ")\n"
                + "AND G.YEAR = ? ";
        return strSQL;
    }

    public String getSQLgetIsRamoTransitorio() {
        String strSQL = "SELECT TRANSITORIO FROM DGI.RAMOS\n"
                + "WHERE RAMO = ? \n"
                + "AND YEAR = ? ";
        return strSQL;
    }

    public String getSQLInsertTransferencia(int folio, int consec, String ramo, String depto, String finalidad,
            String funcion, String subfuncion, String prgConac, String prg, String tipoProy, int proy, int meta,
            int accion, String partida, String tipoGasto, String fuente, String fondo, String recurso, String mpo,
            int delegacion, String relLaboral, double importe, String status, String considerar) {
        String strSQL = "INSERT INTO SPP.TRANSFERENCIAS\n"
                + "	( OFICIO,\n"
                + "	CONSEC,\n"
                + "	RAMO,\n"
                + "	DEPTO,\n"
                + "	FINALIDAD,\n"
                + "	FUNCION,\n"
                + "	SUBFUNCION,\n"
                + "	PRG_CONAC,\n"
                + "	PRG,\n"
                + "	TIPO_PROY,\n"
                + "	PROYECTO,\n"
                + "	META,\n"
                + "	ACCION,\n"
                + "	PARTIDA,\n"
                + "	TIPO_GASTO,\n"
                + "	FUENTE,\n"
                + "	FONDO,\n"
                + "	RECURSO,\n"
                + "	MUNICIPIO,\n"
                + "	DELEGACION,\n"
                + "	REL_LABORAL,\n"
                + "	IMPTE,\n"
                + "	STATUS, CONSIDERAR ) \n"
                + "VALUES\n"
                + "	( " + folio + ",\n"
                + "	" + consec + ",\n"
                + "	'" + ramo + "',\n"
                + "	'" + depto + "',\n"
                + "	" + finalidad + ",\n"
                + "	'" + funcion + "',\n"
                + "	'" + subfuncion + "',\n"
                + "	'" + prgConac + "',\n"
                + "	'" + prg + "',\n"
                + "	'" + tipoProy + "',\n"
                + "	" + proy + ",\n"
                + "	" + meta + ",\n"
                + "	" + accion + ",\n"
                + "	'" + partida + "',\n"
                + "	'" + tipoGasto + "',\n"
                + "	'" + fuente + "',\n"
                + "	'" + fondo + "',\n"
                + "	'" + recurso + "',\n"
                + "	'" + mpo + "',\n"
                + "	" + delegacion + ",\n"
                + "	'" + relLaboral + "',\n"
                + "	" + importe + ",\n"
                + "	'" + status + "','" + considerar + "' )";
        return strSQL;
    }

    public String getSQLInsertTransfrec(int folio, int consec, String ramo, String depto, String finalidad,
            String funcion, String subfuncion, String prgConac, String prg, String tipoProy, int proy, int meta,
            int accion, String partida, String tipoGasto, String fuente, String fondo, String recurso, String mpo,
            int delegacion, String relLaboral, double importe, String tipoMov, int requ) {
        String strSQL = "INSERT INTO SPP.TRANSFREC\n"
                + "	( OFICIO,\n"
                + "	CONSEC,\n"
                + "	RAMO,\n"
                + "	DEPTO,\n"
                + "	FINALIDAD,\n"
                + "	FUNCION,\n"
                + "	SUBFUNCION,\n"
                + "	PRG_CONAC,\n"
                + "	PRG,\n"
                + "	TIPO_PROY,\n"
                + "	PROYECTO,\n"
                + "	META,\n"
                + "	ACCION,\n"
                + "	PARTIDA,\n"
                + "	TIPO_GASTO,\n"
                + "	FUENTE,\n"
                + "	FONDO,\n"
                + "	RECURSO,\n"
                + "	MUNICIPIO,\n"
                + "	DELEGACION,\n"
                + "	REL_LABORAL,\n"
                + "	IMPTE,\n"
                + "	TIPOMOV, REQUERIMIENTO ) \n"
                + "VALUES\n"
                + "	( " + folio + ",\n"
                + "	" + consec + ",\n"
                + "	'" + ramo + "',\n"
                + "	'" + depto + "',\n"
                + "	'" + finalidad + "',\n"
                + "	'" + funcion + "',\n"
                + "	'" + subfuncion + "',\n"
                + "	'" + prgConac + "',\n"
                + "	'" + prg + "',\n"
                + "	'" + tipoProy + "',\n"
                + "	" + proy + ",\n"
                + "	" + meta + ",\n"
                + "	" + accion + ",\n"
                + "	'" + partida + "',\n"
                + "	'" + tipoGasto + "',\n"
                + "	'" + fuente + "',\n"
                + "	'" + fondo + "',\n"
                + "	'" + recurso + "',\n"
                + "	'" + mpo + "',\n"
                + "	" + delegacion + ",\n"
                + "	'" + relLaboral + "',\n"
                + "	" + importe + ",\n"
                + "	'" + tipoMov + "', " + requ + " )";
        return strSQL;
    }

    public String getSQLgetUnidadMedidaDescr() {
        String strSQL = "SELECT MEDIDA||'-'||DESCR AS MEDIDA FROM S_POA_MEDIDA_META \n"
                + "WHERE MEDIDA = ? \n"
                + "    AND YEAR = ? \n"
                + "    AND (ACCION = ? \n"
                + "    OR META = ?)";
        return strSQL;
    }

    public String getSQLgetUnidadResponsable() {
        String strSQL = "SELECT P.DEPTO_RESP||'-'||D.DESCR AS UNIDAD FROM \n"
                + "    POA.PROYECTO P ,DGI.DEPENDENCIA D\n"
                + "WHERE P.YEAR = ? \n"
                + "    AND P.RAMO = ? \n"
                + "    AND P.PRG = ? \n"
                + "    AND P.TIPO_PROY = ? \n"
                + "    AND P.PROY = ? \n"
                + "    AND D.YEAR = P.YEAR\n"
                + "    AND D.DEPTO = P.DEPTO_RESP"
                + "    AND D.RAMO = P.RAMO";
        return strSQL;
    }

    public String getSQLdeleteMovoficios(int folio, String estatus) {
        String strSQL = "DELETE FROM POA.MOVOFICIOS M\n"
                + "WHERE M.OFICIO = " + folio + " "
                + " AND M.STATUS = '" + estatus + "'";
        return strSQL;
    }

    public String getSQLgetCountAmpliaciones() {
        String strSQL = "SELECT COUNT(1) AS AMP "
                + "FROM SPP.AMPLIACIONES\n "
                + "WHERE OFICIO = ?";
        return strSQL;
    }

    public String getSQLgetSumaEstimacion(int year, String ramo, int meta, int folio, String status) {
        String strSQL = ""
                + "SELECT \n"
                + "    DECODE(CALCULO,'AC',NVL(SUM(VALOR),0),DECODE(CALCULO,'MI',NVL(MIN(VALOR),0),NVL(MAX(VALOR),0))) AS VALOR\n"
                + "FROM \n"
                + "( \n"
                + "    SELECT \n"
                + "        VALOR,\n"
                + "        CALCULO\n"
                + "    FROM \n"
                + "        S_POA_META M,\n"
                + "        DGI.ESTIMACION E\n"
                + "    WHERE \n"
                + "        M.YEAR = " + year + " AND \n"
                + "        M.RAMO = '" + ramo + "' AND \n"
                + "        M.META = '" + meta + "' AND \n"
                + "        E.YEAR = M.YEAR AND\n"
                + "        E.RAMO = M.RAMO AND\n"
                + "        E.META = M.META AND\n"
                + "        'A' <> '" + status + "'\n"
                + "        \n"
                + "    UNION ALL\n"
                + "    SELECT  \n"
                + "        VALOR,\n"
                + "        CALCULO\n"
                + "    FROM \n"
                + "        POA.HIST_META M,\n"
                + "        POA.HIST_ESTIMACION E\n"
                + "    WHERE \n"
                + "        M.YEAR = " + year + " AND \n"
                + "        M.RAMO = '" + ramo + "' AND \n"
                + "        M.META = '" + meta + "' AND \n"
                + "        E.YEAR = M.YEAR AND\n"
                + "        E.RAMO = M.RAMO AND\n"
                + "        E.META = M.META AND\n"
                + "        'A' = '" + status + "' \n"
                + ") \n"
                + "GROUP BY CALCULO";
        return strSQL;
    }

    public String getSQLgetSumaAccionEstimacion(int year, String ramo, int meta, int accion, int folio, String status) {
        String strSQL = ""
                + "SELECT \n"
                + "    DECODE(CALCULO,'AC',NVL(SUM(VALOR),0),DECODE(CALCULO,'MI',NVL(MIN(VALOR),0),NVL(MAX(VALOR),0))) AS VALOR \n"
                + " FROM \n"
                + "    ( \n"
                + "        SELECT \n"
                + "            AE.VALOR,       \n"
                + "            A.CALCULO\n"
                + "        FROM \n"
                + "            POA.ACCION A,\n"
                + "            DGI.ACCION_ESTIMACION AE\n"
                + "        WHERE \n"
                + "            A.YEAR = " + year + " AND \n"
                + "            A.RAMO = '" + ramo + "' AND \n"
                + "            A.META = " + meta + " AND \n"
                + "            A.ACCION = " + accion + " AND \n"
                + "            AE.YEAR = A.YEAR AND\n"
                + "            AE.RAMO = A.RAMO AND\n"
                + "            AE.META = A.META AND\n"
                + "            AE.ACCION = A.ACCION AND\n"
                + "            'A' <> '" + status + "' \n"
                + "        UNION ALL\n"
                + "        SELECT  \n"
                + "            AE.VALOR,\n"
                + "            A.CALCULO\n"
                + "        FROM \n"
                + "            POA.HIST_ACCION A,\n"
                + "            POA.HIST_ACCION_ESTIMACION AE\n"
                + "        WHERE \n"
                + "            A.YEAR = " + year + " AND \n"
                + "            A.RAMO = '" + ramo + "' AND \n"
                + "            A.META = " + meta + " AND \n"
                + "            A.ACCION = '" + accion + "' AND \n"
                + "            AE.YEAR = A.YEAR AND\n"
                + "            AE.RAMO = A.RAMO AND\n"
                + "            AE.META = A.META AND\n"
                + "            AE.ACCION = A.ACCION AND\n"
                + "            'A' = '" + status + "'  \n"
                + "    )\n"
                + "GROUP BY CALCULO";
        return strSQL;
    }

    public String getSQLgetDescrRamo() {
        String strSQL = "SELECT RAMO||'-'||DESCR AS DESCR FROM DGI.RAMOS\n"
                + "WHERE YEAR = ? \n"
                + "AND RAMO = ? ";
        return strSQL;
    }

    public String getSQLgetDescrPrograma() {
        String strSQL = "SELECT PRG||'-'||DESCR AS DESCR FROM S_POA_PROGRAMA\n"
                + "WHERE YEAR = ? \n"
                + "AND PRG = ? ";
        return strSQL;
    }

    public String getSQLgetDescrPartida() {
        String strSQL = "SELECT PARTIDA||'-'||DESCR AS DESCR FROM DGI.PARTIDA\n"
                + "WHERE YEAR = ? \n"
                + "AND PARTIDA = ? ";
        return strSQL;
    }

    public String getSQLgetDescrRelLaboral() {
        String strSQL = "SELECT REL_LABORAL||'-'||DESCR AS DESCR FROM S_POA_REL_LABORAL\n"
                + "WHERE YEAR = ? \n"
                + "AND REL_LABORAL = ? ";
        return strSQL;
    }

    public String getSQLgetDescrFuenteFin() {
        String strSQL = "SELECT FUENTE||'.'||FONDO||'.'||RECURSO||'-'||DESCR AS DESCR FROM S_POA_RECURSO\n"
                + "WHERE YEAR = ? \n"
                + "AND FUENTE = ? \n"
                + "AND FONDO = ? \n"
                + "AND RECURSO = ? ";
        return strSQL;
    }

    public String getSQLgetDescrProyecto() {
        String strSQL = "SELECT TIPO_PROY||PROY||' - '||DESCR AS DESCR\n"
                + "FROM DGI.VW_SP_PROY_ACT\n"
                + "WHERE YEAR = ? "
                + " AND TIPO_PROY = ? "
                + "AND  PROY = ? ";
        return strSQL;
    }

    public String getSQLUpdateIdTramiteTipoOficio(int folio, String tipoOficio, String idTramite) {
        String strSQL = "UPDATE DGI.TIPOFICIO "
                + "SET ID_TRAMITE = '" + idTramite + "' "
                + "WHERE OFICIO = " + folio + " AND TIPO = '" + tipoOficio + "' ";
        return strSQL;
    }

    public String getSQLUpdateMetaAmpliaciones(int folio, String ramo, int metaNva, int metaAnt) {
        String strSQL = "UPDATE SPP.AMPLIACIONES "
                + " SET META = " + metaNva + " "
                + " WHERE OFICIO = " + folio
                + "     AND META = " + metaAnt
                + "     AND RAMO = '" + ramo + "'";
        return strSQL;
    }

    public String getSQLUpdateAccionAmpliaciones(int folio, String ramo, int meta, int accionNva, int accionAnt) {
        String strSQL = "UPDATE SPP.AMPLIACIONES "
                + " SET ACCION = " + accionNva + " "
                + " WHERE OFICIO = " + folio
                + "     AND META = " + meta
                + "     AND ACCION = " + accionAnt
                + "     AND RAMO = '" + ramo + "'";
        return strSQL;
    }

    public String getSQLUpdateRequerimientoAmpliaciones(int folio, int consec, int meta, int accion, int requerimiento) {
        String strSQL = "UPDATE SPP.AMPLIACIONES "
                + " SET REQUERIMIENTO = " + requerimiento + ", "
                + " META = " + meta + ", "
                + " ACCION = " + accion + " "
                + " WHERE OFICIO = " + folio
                + "     AND CONSEC = " + consec;
        return strSQL;
    }

    public String getSQLUpdateMetaTransfrec(int folio, String ramo, int metaNva, int metaAnt) {
        String strSQL = "UPDATE SPP.TRANSFREC "
                + " SET META = " + metaNva + " "
                + " WHERE OFICIO = " + folio
                + "     AND META = " + metaAnt
                + "     AND RAMO = '" + ramo + "'";
        return strSQL;
    }

    public String getSQLUpdateAccionTransfrec(int folio, String ramo, int meta, int accionNva, int accionAnt) {
        String strSQL = "UPDATE SPP.TRANSFREC "
                + " SET ACCION = " + accionNva + " "
                + " WHERE OFICIO = " + folio
                + "     AND META = " + meta
                + "     AND ACCION = " + accionAnt
                + "     AND RAMO = '" + ramo + "'";
        return strSQL;
    }

    public String getSQLUpdateRequerimientoTransfrec(int folio, String ramo, int meta, int accion, int requerimientoNvo, int requerimientoAnt) {
        String strSQL = "UPDATE SPP.TRANSFREC "
                + " SET REQUERIMIENTO = " + requerimientoNvo + " "
                + " WHERE OFICIO = " + folio
                + "     AND META = " + meta
                + "     AND ACCION = " + accion
                + "     AND REQUERIMIENTO = " + requerimientoAnt
                + "     AND RAMO = '" + ramo + "'";
        return strSQL;
    }

    public String getSQLgetMovoficioTipoOficioByFolio(boolean isNormativo) {
        String strSQL = "SELECT M.APP_LOGIN , M.FECHAELAB , M.FECPPTO , M.JUSTIFICACION\n"
                + "      , M.OFICIO , M.TIPOMOV , T.OBS_RECHAZO , T.STATUS\n"
                + "      , T.TIPO , R.DESCR AS RAMO_DESCR , E.DESCR AS STATUSDESCR \n"
                + "FROM \n"
                + "    POA.MOVOFICIOS M ,\n"
                + "    DGI.TIPOFICIO T ,\n"
                + "    POA.ESTATUS_MOV E , \n"
                + "    DGI.DGI_USR U,\n"
                + "    DGI.RAMOS R \n"
                + "WHERE \n"
                + "    M.OFICIO = ? \n"
                + "    AND U.APP_LOGIN = TRIM(M.APP_LOGIN) \n"
                + "    AND U.SYS_CLAVE = 1\n"
                + "    AND R.YEAR = ?  \n"
                + "    AND R.RAMO = U.RAMO\n"
                + "    AND EXTRACT(YEAR FROM M.FECHAELAB) =  R.YEAR  \n"
                + "    AND T.OFICIO = M.OFICIO \n"
                + "    AND T.STATUS = E.ESTATUS \n";
        if (!isNormativo) {
            strSQL += "AND U.RAMO = (SELECT UT.RAMO FROM DGI.DGI_USR UT WHERE UT.APP_LOGIN = ? AND UT.SYS_CLAVE = 1)";
        }
        return strSQL;
    }

    public String getSQLgetMovoficioByFolio(boolean isNormativo) {
        String strSQL = "SELECT M.*,\n"
                + "     R.DESCR AS RAMO_DESCR,\n"
                + "     E.DESCR AS STATUSDESCR \n"
                + "FROM \n"
                + "    POA.MOVOFICIOS M ,\n"
                + "    POA.ESTATUS_MOV E,\n"
                + "    DGI.DGI_USR U,\n"
                + "    DGI.RAMOS R  \n"
                + "WHERE \n"
                + "    M.OFICIO = ? \n"
                + "    AND M.STATUS = E.ESTATUS \n"
                + "    AND U.APP_LOGIN = TRIM(M.APP_LOGIN)\n"
                + "    AND U.SYS_CLAVE = 1\n"
                + "    AND R.YEAR = ? \n"
                + "    AND EXTRACT(YEAR FROM M.FECHAELAB) =  R.YEAR  \n"
                + "    AND R.RAMO = U.RAMO \n ";
        if (!isNormativo) {
            strSQL += "AND U.RAMO = (SELECT UT.RAMO FROM DGI.DGI_USR UT WHERE UT.APP_LOGIN = ? AND UT.SYS_CLAVE = 1)";
        }
        return strSQL;
    }

    public String getSQLGetRequerimientoHistorico() {
        String strSQL = "SELECT * FROM POA.HIST_ACCION_REQ\n"
                + "WHERE OFICIO = ?\n"
                + "AND YEAR = ? \n"
                + "AND RAMO = ?\n"
                + "AND PRG = ? \n"
                + "AND META = ?\n"
                + "AND ACCION = ?\n"
                + "AND REQUERIMIENTO = ?";
        return strSQL;
    }

    public String getSQLGetValorPeridoAvanceMetaByYearRamoMetaPeriodo(int year, String ramo, int meta, int periodo) {
        String strSQL = ""
                + "SELECT "
                + "	AVC.VALOR "
                + "FROM "
                + "	DGI.AVANCE AVC "
                + "WHERE "
                + "	AVC.YEAR = '" + year + "' AND "
                + "	AVC.RAMO = '" + ramo + "' AND "
                + "	AVC.META = " + meta + " AND "
                + "	AVC.PERIODO = " + periodo + " "
                + "";
        return strSQL;
    }

    public String getSQLGetValorPeridoAvanceAccionByYearRamoMetaAccionMes(int year, String ramo, int meta, int accion, int mes) {
        String strSQL = ""
                + "SELECT "
                + "	AVC.VALOR "
                + "FROM "
                + "	DGI.POA_AVANCE_ACCION AVC "
                + "WHERE "
                + "	AVC.YEAR = '" + year + "' AND "
                + "	AVC.RAMO = '" + ramo + "' AND "
                + "	AVC.META = " + meta + " AND "
                + "	AVC.ACCION = " + accion + " AND "
                + "	AVC.MES = " + mes + " "
                + "";
        return strSQL;
    }

    public String getSQLgetCoutnTipoOficioByOficio() {
        String strSQL = "SELECT COUNT(1) AS CUENTA FROM DGI.TIPOFICIO\n"
                + "WHERE OFICIO = ? "
                + "AND TIPO = ? ";
        return strSQL;
    }

    public String getSQLgetMaxConsecTransferencia() {
        String strSQL = "SELECT (NVL(MAX(CONSEC),0)+1) AS CONSEC\n"
                + "FROM SPP.TRANSFERENCIAS\n"
                + "WHERE OFICIO = ? ";
        return strSQL;
    }

    public String getSQLgetTipoOficioNuevo() {
        String strSQL = "SELECT TR.OFICIO, TR.CONSEC\n"
                + "FROM SPP.TRANSFERENCIAS TR\n"
                + "WHERE \n"
                + "    TR.OFICIO = ? \n"
                + "    AND CONSEC NOT IN (\n"
                + "        SELECT CONSEC \n"
                + "        FROM DGI.OFICONS\n"
                + "        WHERE OFICIO = ? \n"
                + "    )";
        return strSQL;
    }

    public String getSQLGetProcedureINVP() {
        String strSQL = "CALL DGI.PCKG_SINVP.P_ACTUALIZA_OFICIO@INVP(?, ?)";

        return strSQL;
    }

    public String getSQLinsertTipoOficioNuevo(int oficio, String tipoOficio, String estatus) {
        String strSQL = "INSERT INTO DGI.TIPOFICIO TF \n"
                + "(TF.OFICIO,TF.TIPO,TF.STATUS)\n"
                + "VALUES (" + oficio + ",'" + tipoOficio + "','" + estatus + "')";
        return strSQL;
    }

    public String getSQLinsertOficonsNuevo(int oficio, String tipoOficio, int consec) {
        String strSQL = "INSERT INTO DGI.OFICONS OC \n"
                + "(OC.OFICIO,OC.TIPO,OC.CONSEC)\n"
                + "VALUES (" + oficio + ",'" + tipoOficio + "'," + consec + ")";
        return strSQL;
    }

    public String getSQLcountTipoOficioByOficioTipo() {
        String strSQL = "SELECT COUNT(1) AS TIPO \n"
                + "FROM DGI.TIPOFICIO\n"
                + "WHERE OFICIO = ? \n"
                + "AND TIPO = ? ";
        return strSQL;
    }

    public String getSQLgetEstimacionOriginal() {
        String strSQL = "SELECT \n"
                + "	NVL(ENE+FEB+MAR+ABR+MAY+JUN+JUL+AGO+SEP+OCT+NOV+DIC,0) TOTAL\n"
                + "FROM\n"
                + "	(\n"
                + "		SELECT\n"
                + "			PERIODO,\n"
                + "			VALOR\n"
                + "		FROM\n"
                + "			DGI.ESTIMACION\n"
                + "		WHERE\n"
                + "			YEAR = ? AND\n"
                + "			RAMO = ? AND\n"
                + "			META = ? \n"
                + "	)\n"
                + "PIVOT\n"
                + "	(\n"
                + "		SUM(VALOR)\n"
                + "		FOR PERIODO IN(1 ENE, 2 FEB, 3 MAR, 4 ABR, 5 MAY, 6 JUN, 7 JUL, 8 AGO, 9 SEP, 10 OCT, 11 NOV, 12 DIC)\n"
                + "	)\n";
        return strSQL;
    }

    public String getSQLgetEstimacionHistorico() {
        String strSQL = "SELECT\n"
                + "	NVL(ENE+FEB+MAR+ABR+MAY+JUN+JUL+AGO+SEP+OCT+NOV+DIC,0) TOTAL\n"
                + "FROM\n"
                + "	(\n"
                + "		SELECT\n"
                + "			PERIODO,\n"
                + "			VALOR\n"
                + "		FROM\n"
                + "			POA.HIST_ESTIMACION\n"
                + "		WHERE        \n"
                + "			OFICIO = ? AND\n"
                + "			YEAR = ? AND\n"
                + "			RAMO = ? AND\n"
                + "			META = ? \n"
                + "	)\n"
                + "PIVOT\n"
                + "	(\n"
                + "		SUM(VALOR)\n"
                + "		FOR PERIODO IN(1 ENE, 2 FEB, 3 MAR, 4 ABR, 5 MAY, 6 JUN, 7 JUL, 8 AGO, 9 SEP, 10 OCT, 11 NOV, 12 DIC)\n"
                + "	)";
        return strSQL;
    }

    public String getSQLgetAccionEstimacionOriginal() {
        String strSQL = "SELECT \n"
                + "	NVL(ENE+FEB+MAR+ABR+MAY+JUN+JUL+AGO+SEP+OCT+NOV+DIC,0) TOTAL\n"
                + "FROM\n"
                + "	(\n"
                + "		SELECT\n"
                + "			PERIODO,\n"
                + "			VALOR\n"
                + "		FROM\n"
                + "			DGI.ACCION_ESTIMACION\n"
                + "		WHERE\n"
                + "			YEAR = ? AND\n"
                + "			RAMO = ? AND\n"
                + "			META = ? AND \n"
                + "			ACCION = ? \n"
                + "	)\n"
                + "PIVOT\n"
                + "	(\n"
                + "		SUM(VALOR)\n"
                + "		FOR PERIODO IN(1 ENE, 2 FEB, 3 MAR, 4 ABR, 5 MAY, 6 JUN, 7 JUL, 8 AGO, 9 SEP, 10 OCT, 11 NOV, 12 DIC)\n"
                + "	)\n";
        return strSQL;
    }

    public String getSQLgetAccionEstimacionHistorico() {
        String strSQL = "SELECT\n"
                + "	NVL(ENE+FEB+MAR+ABR+MAY+JUN+JUL+AGO+SEP+OCT+NOV+DIC,0) TOTAL\n"
                + "FROM\n"
                + "	(\n"
                + "		SELECT\n"
                + "			PERIODO,\n"
                + "			VALOR\n"
                + "		FROM\n"
                + "			POA.HIST_ACCION_ESTIMACION\n"
                + "		WHERE        \n"
                + "			OFICIO = ? AND\n"
                + "			YEAR = ? AND\n"
                + "			RAMO = ? AND\n"
                + "			META = ? AND \n"
                + "			ACCION = ? \n"
                + "	)\n"
                + "PIVOT\n"
                + "	(\n"
                + "		SUM(VALOR)\n"
                + "		FOR PERIODO IN(1 ENE, 2 FEB, 3 MAR, 4 ABR, 5 MAY, 6 JUN, 7 JUL, 8 AGO, 9 SEP, 10 OCT, 11 NOV, 12 DIC)\n"
                + "	)";
        return strSQL;
    }

    public String getSQLDeleteMovCaratula(int oficio, int year, String ramoSession, long actCaratula) {
        String strSQL = new String();
        strSQL = ""
                + "DELETE "
                + "	POA.CARATULA_MOVOFICIO CM "
                + "WHERE "
                + "	CM.YEAR = " + year + " AND "
                + "	CM.RAMO = '" + ramoSession + "' AND "
                + "	CM.ID_CARATULA = " + actCaratula + " AND "
                + "	CM.OFICIO = " + oficio + " "
                + "";
        return strSQL;
    }

    public String getSQLMovimientosReporteByCaratula(int year, String ramo, String appLogin, boolean isNormativo, long caratula) {
        String strSQL = "";

        if (caratula != -2) {
            strSQL += "SELECT "
                    + "	MOVS.OFICIO, "
                    + "	MOVS.FECHAELAB, "
                    + "	MOVS.APP_LOGIN, "
                    + "	MOVS.TIPO, "
                    + "	MOVS.STATUS, "
                    + "	E.DESCR STATUS_DESCR, "
                    + "	MOVS.FECPPTO, "
                    + "	MOVS.JUSTIFICACION, "
                    + "	MOVS.OBS_RECHAZO, "
                    + "	MOVS.TIPO AS TIPOFICIO "
                    + "FROM "
                    + "	POA.CARATULA_MOVOFICIO CARMOVS, "
                    + "	( "
                    + "		 SELECT  "
                    + "			MOVOF.OFICIO, "
                    + "			MOVOF.FECHAELAB, "
                    + "			MOVOF.APP_LOGIN, "
                    + "			MOVOF.TIPOMOV TIPO, "
                    + "			MOVOF.STATUS,  "
                    + "			MOVOF.FECPPTO,  "
                    + "			MOVOF.JUSTIFICACION, "
                    + "			MOVOF.OBS_RECHAZO, "
                    + "			TIPOF.TIPO AS TIPOFICIO "
                    + "		 FROM  "
                    + "			POA.MOVOFICIOS MOVOF,  "
                    + "			DGI.TIPOFICIO TIPOF "
                    + "		 WHERE  "
                    + "			EXTRACT(YEAR FROM MOVOF.FECHAELAB) =   " + year + "  AND "
                    + "			TIPOF.OFICIO (+)= MOVOF.OFICIO "
                    + "			";

            if (!isNormativo) {
                strSQL += "			AND MOVOF.APP_LOGIN = '" + appLogin + "' ";
            }

            strSQL += "		 ORDER BY "
                    + "			MOVOF.OFICIO, "
                    + "			MOVOF.STATUS, "
                    + "			MOVOF.TIPOMOV, "
                    + "			TIPOF.TIPO "
                    + "	) MOVS, "
                    + " POA.ESTATUS_MOV E   "
                    + "WHERE "
                    + "	CARMOVS.YEAR = " + year + " AND "
                    + "	CARMOVS.RAMO = '" + ramo + "' AND "
                    + "	CARMOVS.ID_CARATULA = " + caratula + " AND "
                    + "	EXTRACT(YEAR FROM MOVS.FECHAELAB) = CARMOVS.YEAR AND "
                    + "	E.ESTATUS = MOVS.STATUS AND "
                    + "	MOVS.OFICIO = CARMOVS.OFICIO "
                    + "ORDER BY "
                    + "	MOVS.OFICIO "
                    + "";
        } else {
            strSQL += ""
                    + "SELECT  "
                    + "	MOVOF.OFICIO,  "
                    + "	MOVOF.FECHAELAB,  "
                    + "	MOVOF.APP_LOGIN,  "
                    + "	MOVOF.TIPOMOV TIPO,  "
                    + "	MOVOF.STATUS,  "
                    + "	E.DESCR STATUS_DESCR, "
                    + "	MOVOF.FECPPTO,  "
                    + "	MOVOF.JUSTIFICACION,  "
                    + "	MOVOF.OBS_RECHAZO,  "
                    + "	TIPOF.TIPO AS TIPOFICIO	  "
                    + "FROM  "
                    + "	POA.MOVOFICIOS MOVOF,  "
                    + "	DGI.TIPOFICIO TIPOF,	 "
                    + " POA.ESTATUS_MOV E   "
                    + "WHERE  "
                    + "	EXTRACT(YEAR FROM MOVOF.FECHAELAB) =   " + year + "  AND  "
                    + "	E.ESTATUS = MOVOF.STATUS AND "
                    + "	TIPOF.OFICIO (+)= MOVOF.OFICIO  ";

            if (!isNormativo) {
                strSQL += "AND MOVOF.APP_LOGIN = '" + appLogin + "' ";
            }

            strSQL += "AND NOT EXISTS ( "
                    + "					SELECT  "
                    + "						* "
                    + "					FROM  "
                    + "						POA.CARATULA_MOVOFICIO CARMOVS "
                    + "					WHERE  "
                    + "						CARMOVS.OFICIO = MOVOF.OFICIO "
                    + "				)      "
                    + "ORDER BY "
                    + "	MOVOF.OFICIO "
                    + "      ";

        }

        return strSQL;

    }

    public String getSQLValidaCuadreTransferencias() {
        String strSQL = "SELECT NVL(COUNT(1),0) \"VAL\" FROM "
                + "(\n"
                + "SELECT TR.OFICIO, TR.CONSEC,SUM(TR.IMPTE) \"TRIMP\", M.STATUS\n"
                + "FROM SPP.TRANSFREC TR, POA.MOVOFICIOS M\n"
                + "WHERE \n"
                + "    TR.OFICIO = ? \n"
                + "    AND TR.OFICIO = M.OFICIO\n"
                + "GROUP BY\n"
                + "    TR.OFICIO,TR.CONSEC, M.STATUS\n"
                + "ORDER BY \n"
                + "    TR.OFICIO,TR.CONSEC\n"
                + " ) A,(   \n"
                + "SELECT T.OFICIO, T.CONSEC,SUM(T.IMPTE) \"TIMP\",M.STATUS\n"
                + "FROM SPP.TRANSFERENCIAS T, POA.MOVOFICIOS M\n"
                + "WHERE \n"
                + "    T.OFICIO = ? \n"
                + "    AND T.OFICIO = M.OFICIO\n"
                + "GROUP BY\n"
                + "    T.OFICIO,T.CONSEC, M.STATUS\n"
                + "ORDER BY \n"
                + "    T.OFICIO,T.CONSEC) B\n"
                + "WHERE A.OFICIO = B.OFICIO\n"
                + "AND A.CONSEC = B.CONSEC\n"
                + "AND A.TRIMP <> B.TIMP";
        return strSQL;
    }

    public String getSQLMovsReporteByOficio(int oficio, int year, String appLogin, boolean isNormativo) {
        String strSQL = new String();

        strSQL = ""
                + "SELECT "
                + "	MOVOF.OFICIO,  "
                + "	MOVOF.FECHAELAB,  "
                + "	MOVOF.APP_LOGIN,  "
                + "	MOVOF.TIPOMOV TIPO,  "
                + "	MOVOF.STATUS,  "
                + "	MOVOF.FECPPTO,  "
                + "	MOVOF.JUSTIFICACION,  "
                + "	MOVOF.OBS_RECHAZO,  "
                + "	TIPOF.TIPO AS TIPOFICIO      "
                + "FROM  "
                + "	POA.MOVOFICIOS MOVOF,  "
                + "	DGI.TIPOFICIO  TIPOF  "
                + "WHERE  "
                + "	MOVOF.OFICIO = " + oficio + " AND "
                + "	EXTRACT(YEAR FROM MOVOF.FECHAELAB) = " + year + " AND  "
                + "	TIPOF.OFICIO (+)= MOVOF.OFICIO ";

        if (!isNormativo) {
            strSQL += "AND MOVOF.APP_LOGIN = '" + appLogin + "' ";
        }

        strSQL += "ORDER BY "
                + "	MOVOF.OFICIO, "
                + "	MOVOF.STATUS, "
                + "	MOVOF.TIPOMOV,"
                + "	TIPOF.TIPO "
                + "";
        return strSQL;
    }

    public String getSQLisAyuntamiento() {
        String strSQL = "SELECT AYUNTAMIENTO\n"
                + "FROM DGI.PARAMETROS";
        return strSQL;
    }

    public String getSQLgetParametroTrimestre() {
        String strSQL = "SELECT VALIDA_TRIMESTRE \n"
                + "FROM DGI.PARAMETROS";
        return strSQL;
    }

    public String getSQLvalidaRequerimientoRecalendarizacion() {
        String strSQL = "SELECT COUNT(1) AS VALIDA \n"
                + "FROM POA.MOVOFICIOS_ACCION_REQ MAR,\n"
                + "    SPP.VW_USUCODIGO UC\n"
                + "WHERE\n"
                + "    MAR.OFICIO = ? \n"
                + "    AND MAR.RAMO = ? \n"
                + "    AND MAR.PRG = ? \n"
                + "    AND MAR.META = ? \n"
                + "    AND MAR.ACCION = ? \n"
                + "    AND MAR.REQUERIMIENTO = ? \n"
                + "    AND MAR.DEPTO = ? \n"
                + "    AND MAR.PARTIDA = ? \n"
                + "    AND MAR.FUENTE = ? \n"
                + "    AND MAR.FONDO = ? \n"
                + "    AND MAR.RECURSO = ? \n"
                + "    AND UC.APP_LOGIN = ? \n"
                + "    AND UC.RAMO = MAR.RAMO\n"
                + "    AND UC.PRG = MAR.PRG\n"
                + "    AND UC.META = MAR.META\n"
                + "    AND UC.ACCION = MAR.ACCION\n"
                + "    AND UC.DEPTO = MAR.DEPTO\n"
                + "    AND UC.PARTIDA = MAR.PARTIDA\n"
                + "    AND UC.FUENTE = MAR.FUENTE\n"
                + "    AND UC.RECURSO = MAR.RECURSO";
        return strSQL;
    }

    public String getSQLgetPeriodoByFechaBitmovtos() {
        String strSQL = "SELECT NVL(CEIL(EXTRACT (MONTH FROM MAX(FECHAAUT))/3), 0) AS PERIODO \n"
                + " FROM POA.BITMOVTOS \n"
                + " WHERE OFICIO = ? AND TIPO_OFICIO = ? \n"
                + "    AND AUTORIZO = 'A' ";
        return strSQL;
    }

    public String getSQLUpdateMovimientoOficioRecalAccionReqConsiderar(int year, int folio, String ramo, String prg, String depto, int meta, int accion, int requerimiento, String considerar) {
        String strSQL = ""
                + "UPDATE "
                + "	POA.MOVOFICIOS_ACCION_REQ "
                + "SET "
                + "	CONSIDERAR = '" + considerar + "' "
                + "WHERE  "
                + "	OFICIO = " + folio + " AND "
                + "	YEAR = " + year + " AND "
                + "	RAMO = '" + ramo + "' AND "
                + "	PRG = '" + prg + "' AND "
                + "	DEPTO = '" + depto + "' AND "
                + "	META = " + meta + " AND  "
                + "	ACCION = " + accion + " AND "
                + "	REQUERIMIENTO = " + requerimiento + " "
                + "";
        return strSQL;
    }

    public String getSQLMovimientosByCaratula(int year, String ramo, String appLogin, boolean isNormativo, long caratula) {
        String strSQL = "";

        if (caratula != -2) {
            strSQL += "SELECT "
                    + "	MOVS.OFICIO, "
                    + "	MOVS.FECHAELAB, "
                    + "	MOVS.APP_LOGIN, "
                    + "	MOVS.TIPO, "
                    + "	MOVS.STATUS, "
                    + "	E.DESCR STATUS_DESCR, "
                    + "	MOVS.FECPPTO, "
                    + "	MOVS.OBS_RECHAZO "
                    + "FROM "
                    + "	POA.CARATULA_MOVOFICIO CARMOVS, "
                    + "	( "
                    + "		 SELECT  "
                    + "			MOVOF.OFICIO, "
                    + "			MOVOF.FECHAELAB, "
                    + "			MOVOF.APP_LOGIN, "
                    + "			MOVOF.TIPOMOV TIPO, "
                    + "			MOVOF.STATUS,  "
                    + "			MOVOF.FECPPTO,  "
                    + "			MOVOF.OBS_RECHAZO "
                    + "		 FROM  "
                    + "			POA.MOVOFICIOS MOVOF  "
                    + "		 WHERE  "
                    + "			EXTRACT(YEAR FROM MOVOF.FECHAELAB) =   " + year + "  "
                    + "		 ORDER BY "
                    + "			MOVOF.OFICIO, "
                    + "			MOVOF.STATUS, "
                    + "			MOVOF.TIPOMOV "
                    + "	) MOVS, "
                    + " POA.ESTATUS_MOV E   "
                    + "WHERE "
                    + "	CARMOVS.YEAR = " + year + " AND "
                    + "	CARMOVS.RAMO = '" + ramo + "' AND "
                    + "	CARMOVS.ID_CARATULA = " + caratula + " AND "
                    + "	EXTRACT(YEAR FROM MOVS.FECHAELAB) = CARMOVS.YEAR AND "
                    + "	MOVS.STATUS <> 'R' AND "
                    + "	MOVS.STATUS <> 'K' AND "
                    + "	MOVS.STATUS <> 'C' AND "
                    + "	E.ESTATUS = MOVS.STATUS AND "
                    + "	MOVS.OFICIO = CARMOVS.OFICIO AND"
                    + "	MOVS.TIPO <> 'C' "
                    + "ORDER BY "
                    + "	MOVS.OFICIO "
                    + "";
        } else {
            strSQL += ""
                    + "SELECT "
                    + "    FOLIOS.OFICIO, "
                    + "  	FOLIOS.FECHAELAB, "
                    + "  	FOLIOS.APP_LOGIN, "
                    + "  	FOLIOS.TIPO, "
                    + "  	FOLIOS.STATUS, "
                    + "  	FOLIOS.STATUS_DESCR, "
                    + " 	FOLIOS.FECPPTO, "
                    + "  	FOLIOS.OBS_RECHAZO "
                    + "FROM (  "
                    + "SELECT  "
                    + "	DISTINCT  "
                    + "	MOVOF.OFICIO,  "
                    + "	MOVOF.FECHAELAB,  "
                    + "	MOVOF.APP_LOGIN,  "
                    + "	MOVOF.TIPOMOV TIPO,  "
                    + "	MOVOF.STATUS,  "
                    + "	E.DESCR STATUS_DESCR, "
                    + "	MOVOF.FECPPTO,  "
                    + "	MOVOF.OBS_RECHAZO,  "
                    + "	USR.RAMO  "
                    + "FROM  "
                    + "	POA.MOVOFICIOS MOVOF,  "
                    + " POA.ESTATUS_MOV E,   "
                    + " DGI.DGI_USR USR   "
                    + "WHERE  "
                    + "	EXTRACT(YEAR FROM MOVOF.FECHAELAB) =   " + year + "  AND  "
                    + "	MOVOF.TIPOMOV <> 'C' AND"
                    + "	MOVOF.STATUS <> 'K' AND"
                    + "	MOVOF.STATUS <> 'R' AND"
                    + "	MOVOF.STATUS <> 'C' AND"
                    + "	E.ESTATUS = MOVOF.STATUS AND "
                    + "	TRIM(USR.APP_LOGIN) = TRIM(MOVOF.APP_LOGIN) "
                    + "AND NOT EXISTS ( "
                    + "					SELECT  "
                    + "						* "
                    + "					FROM  "
                    + "						POA.CARATULA_MOVOFICIO CARMOVS "
                    + "					WHERE  "
                    + "						CARMOVS.OFICIO = MOVOF.OFICIO "
                    + "				)      "
                    + ") FOLIOS "
                    + "WHERE "
                    + "   FOLIOS.RAMO = '" + ramo + "'      "
                    + "ORDER BY "
                    + "	FOLIOS.OFICIO "
                    + "      ";

        }

        return strSQL;

    }

    public String getSQLgetUpdateAutorizoBitmovtos(int oficio, String appLogin, String autorizo,
            int tipoFlujo, String tipoUsr) {
        String strSQL = "UPDATE POA.BITMOVTOS \n"
                + " SET AUTORIZO = '" + autorizo + "'  \n"
                + " WHERE OFICIO = " + oficio + " \n"
                + "     AND APP_LOGIN = '" + appLogin + "'      \n"
                + "     AND SYS_CLAVE = 1 \n"
                + "     AND TIPO_FLUJO = " + tipoFlujo + " \n"
                + "     AND TIPO_USR = '" + tipoUsr + "'  \n";
        return strSQL;
    }

    public String getSQLGetTransferenciasByTipoOficioMomentos() {
        String strSQL = "SELECT A.RAMO,A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION, "
                + " A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, A.DELEGACION, A.REL_LABORAL, SUM(A.IMPTE) AS IMPTE "
                + " FROM SPP.TRANSFERENCIAS A, DGI.TIPOFICIO T, DGI.OFICONS O, DGI.RAMOS R "
                + " WHERE "
                + "     A.OFICIO = T.OFICIO "
                + "     AND A.CONSEC = O.CONSEC "
                + "     AND T.OFICIO = ? "
                + "     AND T.TIPO = ? "
                + "     AND O.OFICIO = T.OFICIO "
                + "     AND O.TIPO = T.TIPO "
                + "     AND R.YEAR = ? "
                + "     AND R.RAMO = A.RAMO "
                + "     AND R.TRANSITORIO = 'N' "
                + "  GROUP BY A.RAMO, A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, "
                + "     A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION,  "
                + "     A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, "
                + "     A.DELEGACION, A.REL_LABORAL ";
        return strSQL;
    }

    public String getSLQgetTransferenciasByFolioMomentos() {
        String strSQL = "SELECT A.RAMO,A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION, "
                + "    A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, A.DELEGACION, A.REL_LABORAL, SUM(A.IMPTE) AS IMPTE "
                + "FROM SPP.TRANSFREC A  "
                + "WHERE A.OFICIO = ? "
                + "AND A.CONSEC in ( "
                + "                    SELECT TR.CONSEC "
                + "                    FROM SPP.TRANSFERENCIAS TR, DGI.TIPOFICIO T, DGI.OFICONS O, DGI.RAMOS R "
                + "                    WHERE "
                + "                        TR.OFICIO = T.OFICIO "
                + "                        AND TR.CONSEC = O.CONSEC "
                + "                        AND T.OFICIO = ? "
                + "                        AND T.TIPO = ? "
                + "                        AND O.OFICIO = T.OFICIO "
                + "                        AND O.TIPO = T.TIPO "
                + "                        AND R.YEAR = ? "
                + "                        AND R.RAMO = TR.RAMO "
                + "                        AND R.TRANSITORIO = 'N' "
                + "                )  "
                + "GROUP BY A.RAMO, A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, "
                + "    A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION,  "
                + "    A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, "
                + "    A.DELEGACION, A.REL_LABORAL  ";
        return strSQL;
    }

    public String getSQLUpdateFolioRelacionado(int oficio, String tipoOficio, int folioNuevo) {
        String strSQL = "UPDATE DGI.TIPOFICIO T\n"
                + "SET T.OFICIO_RELACIONADO = " + folioNuevo + " \n"
                + "WHERE T.OFICIO = " + oficio + " \n"
                + "AND T.TIPO = '" + tipoOficio + "' ";
        return strSQL;
    }

    public String getSQLGetAmpliacionesByTipoOficioMomentos() {
        String strSQL = "SELECT A.RAMO,A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION,\n "
                + "A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, A.DELEGACION, A.REL_LABORAL, \n "
                + "CASE A.TIPOMOV WHEN 'C' THEN 'A' ELSE A.TIPOMOV END AS TIPOMOV, SUM(A.IMPTE) AS IMPTE, SUM(A.IMPTE) AS IMPTE \n "
                + "FROM SPP.AMPLIACIONES A, DGI.TIPOFICIO T, DGI.OFICONS O, DGI.RAMOS R\n"
                + "WHERE\n"
                + "    A.OFICIO = T.OFICIO "
                + "    AND A.CONSEC = O.CONSEC "
                + "    AND T.OFICIO = ? "
                + "    AND T.TIPO = ? "
                + "    AND O.OFICIO = T.OFICIO "
                + "    AND O.TIPO = T.TIPO"
                + "    AND R.YEAR = ? "
                + "    AND R.RAMO = A.RAMO "
                + "    AND R.TRANSITORIO = 'N' "
                + "GROUP BY A.RAMO, A.DEPTO, A.FINALIDAD, A.FUNCION, A.SUBFUNCION, "
                + "    A.PRG_CONAC, A.PRG, A.TIPO_PROY, A.PROYECTO, A.META, A.ACCION,  "
                + "    A.PARTIDA, A.TIPO_GASTO, A.FUENTE, A.FONDO, A.RECURSO, A.MUNICIPIO, "
                + "    A.DELEGACION, A.REL_LABORAL, CASE A.TIPOMOV WHEN 'C' THEN 'A' ELSE A.TIPOMOV END  ";
        return strSQL;
    }

    public String getSQLcountTipoOficioByFolio() {
        String strSQL = "SELECT COUNT(1) TIPOFICIO \n"
                + "FROM DGI.TIPOFICIO\n"
                + "WHERE OFICIO = ?";
        return strSQL;
    }

    public String getSQLcountOficonsByFolio() {
        String strSQL = "SELECT COUNT(1) OFICONS\n"
                + "FROM DGI.OFICONS\n"
                + "WHERE OFICIO = ?";
        return strSQL;
    }

    public String getSQLgetFolioRelacionado() {
        String strSQL = "SELECT OFICIO FROM \n"
                + "DGI.TIPOFICIO T\n"
                + "WHERE T.OFICIO_RELACIONADO = ? ";
        return strSQL;
    }

    public String getSQLInsertRecalendarizacion(int oficio, String ramo, String depto, String finalidad, String funcion, String subfuncion, String programa,
            String prgConac, String tipoProy, int proy, int meta, int accion, String partida, String tipoGasto, String fuente, String fondo,
            String recurso, String mpo, int deleg, String relLaboral, int requerimiento, int mes, double diferencia, String tipoRequerimiento) {
        String strSQL = "INSERT INTO SPP.RECALENDARIZACION(OFICIO, RAMO, DEPTO, FINALIDAD, FUNCION, SUBFUNCION, PRG_CONAC, PRG,"
                + "              TIPO_PROY,PROYECTO, META, ACCION, PARTIDA, TIPO_GASTO, FUENTE, FONDO, RECURSO, MUNICIPIO, DELEGACION,"
                + "              REL_LABORAL, REQUERIMIENTO, MES, IMPTE, PPTO_ORIGINAL)\n"
                + "        VALUES (" + oficio + ", '" + ramo + "', '" + depto + "', '" + finalidad + "', '" + funcion + "', '" + subfuncion + "', '" + prgConac + "', '" + programa + "',"
                + "         '" + tipoProy + "'," + proy + ", " + meta + "," + accion + ",'" + partida + "', '" + tipoGasto + "', '" + fuente + "', '" + fondo + "', '" + recurso + "', '" + mpo + "',\n"
                + "            " + deleg + ", '" + relLaboral + "', " + requerimiento + ", " + mes + ", " + diferencia + ",'" + tipoRequerimiento + "')";
        return strSQL;
    }

    public String getSQLgetRequerimientoOriginal() {
        String strSQL = "SELECT A.COSTO_UNITARIO, \n"
                + "            CASE ? \n"
                + "            WHEN 1 THEN A.ENE\n"
                + "            WHEN 2 THEN A.FEB\n"
                + "            WHEN 3 THEN A.MAR\n"
                + "            WHEN 4 THEN A.ABR\n"
                + "            WHEN 5 THEN A.MAY\n"
                + "            WHEN 6 THEN A.JUN\n"
                + "            WHEN 7 THEN A.JUL\n"
                + "            WHEN 8 THEN A.AGO\n"
                + "            WHEN 9 THEN A.SEP\n"
                + "            WHEN 10 THEN A.OCT\n"
                + "            WHEN 11 THEN A.NOV\n"
                + "            WHEN 12 THEN A.DIC\n"
                + "            ELSE 0 END AS CANT,\n"
                + "            A.PPTO_ORIGINAL\n"
                + "        FROM POA.ACCION_REQ A \n"
                + "        WHERE A.YEAR = ? AND \n"
                + "              A.RAMO = ? AND \n"
                + "              A.PRG = ? AND \n"
                + "              A.DEPTO = ? AND \n"
                + "              A.META = ? AND \n"
                + "              A.ACCION = ? AND \n"
                + "              A.REQUERIMIENTO = ? ";
        return strSQL;
    }

    public String getSQLdeleteRecalendarizacionByFolio(int folio) {
        String strSQL = "DELETE FROM SPP.RECALENDARIZACION\n"
                + "WHERE OFICIO = " + folio;
        return strSQL;
    }

    public String getSQLgetEjercidoCompromisoMeta() {
        String strSQL = "SELECT \n"
                + "    SUM(P.EJERCIDO) AS EJERCIDO,\n"
                + "    SUM(P.COMPROMISO) AS COMPROMISO \n"
                + "FROM SPP.PPTO P\n"
                + "WHERE P.YEAR = ? \n"
                + "    AND P.RAMO = ? \n"
                + "    AND P.META = ? \n"
                + "GROUP BY P.YEAR, P.RAMO, P.PRG, P.META";
        return strSQL;
    }

    public String getSQLgetEjercidoCompromisoAccion() {
        String strSQL = "SELECT \n"
                + "    SUM(P.EJERCIDO) AS EJERCIDO,\n"
                + "    SUM(P.COMPROMISO) AS COMPROMISO \n"
                + "FROM SPP.PPTO P\n"
                + "WHERE P.YEAR = ? \n"
                + "    AND P.RAMO = ? \n"
                + "    AND P.META = ? \n"
                + "    AND P.ACCION = ? \n"
                + "GROUP BY P.YEAR, P.RAMO, P.PRG, P.META";
        return strSQL;
    }

    public String getSQLgetSumaAsignadoActualizadoMeta() {
        String strSQL = "SELECT \n"
                + "    SUM(P.ASIGNADO) AS ASIGNADO,\n"
                + "    SUM(P.ACTUALIZADO) AS ACTUALIZADO \n"
                + "FROM SPP.PPTO P\n"
                + "WHERE P.YEAR = ? \n"
                + "    AND P.RAMO = ? \n"
                + "    AND P.META = ? \n"
                + "GROUP BY P.YEAR, P.RAMO, P.PRG, P.META";
        return strSQL;
    }

    public String getSQLgetSumaAsignadoActualizadoAccion() {
        String strSQL = "SELECT \n"
                + "    SUM(P.ASIGNADO) AS ASIGNADO,\n"
                + "    SUM(P.ACTUALIZADO) AS ACTUALIZADO \n"
                + "FROM SPP.PPTO P\n"
                + "WHERE P.YEAR = ? \n"
                + "    AND P.RAMO = ? \n"
                + "    AND P.META = ? \n"
                + "    AND P.ACCION = ? \n"
                + "GROUP BY P.YEAR, P.RAMO, P.PRG, P.META";
        return strSQL;
    }

    public String getSQLisAvanceCapturadoMeta() {
        String strSQL = "SELECT SUM(NVL(VALOR,0)) AS AVANCE\n"
                + "FROM DGI.AVANCE AV \n"
                + "WHERE AV.YEAR = ? \n"
                + "    AND AV.RAMO = ? \n"
                + "    AND AV.META = ? ";
        return strSQL;
    }

    public String getSQLisAvanceCapturadoAccion() {
        String strSQL = "SELECT SUM(NVL(VALOR,0)) AS AVANCE\n"
                + "FROM DGI.POA_AVANCE_ACCION AV \n"
                + "WHERE AV.YEAR = ? \n"
                + "    AND AV.RAMO = ? \n"
                + "    AND AV.META = ? \n"
                + "    AND AV.ACCION = ?  ";
        return strSQL;
    }

    public String getSQLupdatePeriodoMeta(int year, String ramo, int meta) {
        String strSQL = "UPDATE POA.META M SET M.PERIODO = NULL\n"
                + "WHERE M.YEAR = " + year + " \n"
                + "   AND M.RAMO = '" + ramo + "'\n"
                + "   AND M.META = " + meta;
        return strSQL;
    }

    public String getSQLupdatePeriodoAccion(int year, String ramo, int meta, int accion) {
        String strSQL = "UPDATE POA.ACCION M SET M.PERIODO = NULL\n"
                + "WHERE M.YEAR = " + year + " \n"
                + "   AND M.RAMO = '" + ramo + "'\n"
                + "   AND M.META = " + meta + " \n"
                + "   AND M.ACCION = " + accion;
        return strSQL;
    }

    public String getSQLupdatePeriodoAccionByMeta(int year, String ramo, int meta) {
        String strSQL = "UPDATE POA.ACCION M SET M.PERIODO = NULL\n"
                + "WHERE M.YEAR = " + year + " \n"
                + "   AND M.RAMO = '" + ramo + "'\n"
                + "   AND M.META = " + meta + " \n";
        return strSQL;
    }

    public String getSQLcountRecalendarizacion() {
        String strSQL = "SELECT COUNT(1) AS CUENTA \n"
                + "FROM SPP.RECALENDARIZACION R\n"
                + "WHERE OFICIO = ? ";
        return strSQL;
    }

    public String getSQLgetAccionReqByLlave() {
        String strSQL = "SELECT AR.ENE, AR.FEB, AR.MAR, AR.ABR, AR.MAY, AR.JUN, AR.JUL, AR.AGO, AR.SEP, AR.OCT, AR.NOV, AR.DIC, AR.COSTO_UNITARIO \n"
                + "FROM POA.ACCION_REQ AR \n"
                + "WHERE AR.YEAR = ?\n"
                + "   AND AR.RAMO = ?\n"
                + "   AND AR.META = ? \n"
                + "   AND AR.ACCION = ?\n"
                + "   AND AR.REQUERIMIENTO = ?";
        return strSQL;
    }

    public String getSQLgetDisponibleParaestatal() {
        String filtro = "        M.STATUS NOT IN ('R','K','R','C')    \n"
                + "        AND P.OFICIO = M.OFICIO\n"
                + "        AND EXTRACT(YEAR FROM M.FECPPTO) = ? \n"
                + "        AND EXTRACT(MONTH FROM M.FECPPTO) <= ? \n"
                + "        AND P.RAMO = ? \n"
                + "        AND P.PRG = ? \n"
                + "        AND P.TIPO_PROY = ? \n"
                + "        AND P.PROYECTO = ? \n"
                + "        AND P.META = ? \n"
                + "        AND P.ACCION = ? \n"
                + "        AND P.PARTIDA = ? \n"
                + "        AND P.REL_LABORAL = ? \n"
                + "        AND P.FUENTE = ? \n"
                + "        AND P.FONDO = ? \n"
                + "        AND P.RECURSO = ?      \n";
        String strSQL = "SELECT SUM(T.IMPORTE) AS IMPORTE\n"
                + "FROM (\n"
                + "    SELECT (NVL(SUM(P.IMPTE),0)*-1) AS IMPORTE\n"
                + "    FROM SPP.TRANSFERENCIAS P, POA.MOVOFICIOS M\n"
                + "    WHERE \n" + filtro + "\n"
                + "    UNION ALL\n"
                + "    SELECT NVL(SUM(P.IMPTE),0) AS IMPORTE\n"
                + "    FROM SPP.TRANSFREC P, POA.MOVOFICIOS M\n"
                + "    WHERE \n" + filtro + "\n"
                + "    UNION ALL\n"
                + "    SELECT NVL(SUM(P.IMPTE),0) AS IMPORTE\n"
                + "    FROM SPP.AMPLIACIONES P, POA.MOVOFICIOS M\n"
                + "    WHERE \n" + filtro + "\n"
                + ") T";
        return strSQL;
    }

    public String getSQLgetAsignadoParaestatalByMes() {
        String strSQL = "SELECT SUM(P.ASIGNADO) AS ASIGNADO "
                + " FROM SPP.PPTO P\n"
                + "WHERE \n"
                + "P.YEAR = ?\n"
                + "AND P.MES <= ? \n"
                + "AND P.RAMO = ?\n"
                + "AND P.PRG = ?\n"
                + "AND P.TIPO_PROY = ?\n"
                + "AND P.PROYECTO = ?\n"
                + "AND P.META = ?\n"
                + "AND P.ACCION = ?\n"
                + "AND P.PARTIDA = ?\n"
                + "AND P.REL_LABORAL = ?\n"
                + "AND P.FUENTE = ?\n"
                + "AND P.FONDO = ?\n"
                + "AND P.RECURSO = ?";
        return strSQL;
    }

    public String getSQLgetMovimientosByRamo() {
        String strSQL = "SELECT MV.OFICIO,\n"
                + "    MV.APP_LOGIN,\n"
                + "    NVL(MV.TIPO_OFICIO,' ') AS TIPO,\n"
                + "    MV.FECHAELAB, MV.FECPPTO,\n"
                + "    MV.RAMO, \n"
                + "    MV.RAMO||' '||MV.RAMO_DESCR AS RAMO_DESCR,\n"
                + "    MV.STATUS,\n"
                + "    MV.STATUS_DESCR AS STATUSDESCR,\n"
                + "    MV.TIPOMOV,\n"
                + "    MV.JUSTIFICACION,\n"
                + "    MV.OBS_RECHAZO \n"
                + "FROM POA.VW_MOVOFICIOS MV,\n"
                + "    DGI.DGI_USR U\n"
                + "WHERE     \n"
                + "    U.SYS_CLAVE = 1\n"
                + "    AND U.RAMO = ? \n"
                + "    AND EXTRACT(YEAR FROM MV.FECHAELAB) =  ?  \n"
                + "    AND TRIM(MV.APP_LOGIN) = TRIM(U.APP_LOGIN)\n"
                + "ORDER BY\n"
                + "    MV.OFICIO,MV.TIPO_OFICIO,MV.FECHAELAB, MV.FECPPTO";
        return strSQL;
    }

    public String getSQLGetMovOficioLigadoPendiente(int year, int oficio, String tipoProceso) {
        String strSQL = ""
                + "SELECT "
                + "	MOVOFICIOS_LIG.YEAR, "
                + "	MOVOFICIOS_LIG.OFICIO OFICIO, "
                + "	MOVOFICIOS_LIG.OFICIO_LIGADO OFICIO_LIGADO, "
                + "	MOVOFICIOS_LIG.LIGADO_PARAESTATAL LIGADO_PARAESTATAL, "
                + "	NVL( CASE WHEN MOVOFICIOS_LIG.LIGADO_PARAESTATAL = 'S' THEN "
                + "		( "
                + "                 SELECT "
                + "                     CASE WHEN MOVOFICIOS_PAR.STATUS = 'C' AND  'C' = '" + tipoProceso + "' THEN          "
                + "                         0  "
                + "                     ELSE  "
                + "                         CASE WHEN MOVOFICIOS_PAR.STATUS = 'A' AND  'A' = '" + tipoProceso + "' THEN  "
                + "                             0 "
                + "                         ELSE  "
                + "                             1  "
                + "                         END "
                + "                     END  "
                + "                 FROM "
                + "                     S_POA_MOVOFICIOS_PARAESTATAL MOVOFICIOS_PAR "
                + "                  WHERE "
                + "                     MOVOFICIOS_PAR.OFICIO = MOVOFICIOS_LIG.OFICIO_LIGADO "
                + "		) "
                + "     ELSE "
                + "		( "
                + "			SELECT "
                + "                         CASE WHEN MOVOFICIOS.STATUS = 'C' AND  'C' = '" + tipoProceso + "' THEN      "
                + "                             0  "
                + "                         ELSE  "
                + "                             CASE WHEN MOVOFICIOS.STATUS = 'A' AND  'A' = '" + tipoProceso + "' THEN  "
                + "                                 0  "
                + "                             ELSE  "
                + "                                 1  "
                + "                             END "
                + "                         END  "
                + "			FROM "
                + "				POA.MOVOFICIOS MOVOFICIOS "
                + "			WHERE "
                + "				MOVOFICIOS.OFICIO = MOVOFICIOS_LIG.OFICIO_LIGADO "
                + "		) "
                + "	END,0) AS PENDIENTE "
                + "FROM "
                + "	POA.MOVOFICIOS_LIGADOS MOVOFICIOS_LIG "
                + "WHERE "
                + "	MOVOFICIOS_LIG.YEAR = '" + year + "' AND "
                + "	MOVOFICIOS_LIG.OFICIO = '" + oficio + "' "
                + "ORDER BY "
                + " 	MOVOFICIOS_LIG.LIGADO_PARAESTATAL, "
                + "     MOVOFICIOS_LIG.OFICIO_LIGADO "
                + "";
        return strSQL;
    }

    public String getSQLUpdateConsiderarTransAmp(String tabla, String considerar, int oficio, int consec) {
        String strSQL = "UPDATE SPP." + tabla + " A\n"
                + "SET \n"
                + "    A.CONSIDERAR = '" + considerar + "' \n"
                + "WHERE \n"
                + "    OFICIO = \n" + oficio
                + "    AND CONSEC = " + consec;
        return strSQL;
    }

    public String getSQLUpdateIngresoPropio() {
        String strSQL = "UPDATE SPP.AMPLIACIONES A\n"
                + "SET \n"
                + "    A.INGRESO_PROPIO = ? \n"
                + "WHERE \n"
                + "    OFICIO = ? \n"
                + "    AND CONSEC = ?";
        return strSQL;
    }

    public String getSQLValidaMetaInhabilitadaTransfrec() {
        String strSQL = "SELECT COUNT(1) AS COUNT \n"
                + "   FROM SPP.TRANSFREC\n"
                + "WHERE OFICIO = ?\n"
                + "   AND RAMO = ? \n"
                + "   AND META = ? \n";
        return strSQL;
    }

    public String getSQLValidaAccionInhabilitadaTransfrec() {
        String strSQL = "SELECT COUNT(1) AS COUNT \n"
                + "   FROM SPP.TRANSFREC\n"
                + "WHERE OFICIO = ?\n"
                + "   AND RAMO = ? \n"
                + "   AND META = ? \n"
                + "   AND ACCION = ?";
        return strSQL;
    }

    public String getSQLgetRequerimientosByFuenteFin() {
        String strSQL = "SELECT * \n"
                + "FROM  \n"
                + "    POA.ACCION_REQ AR \n"
                + "WHERE YEAR = ? \n"
                + "    AND AR.RAMO = ? \n"
                + "    AND AR.META = ? \n"
                + "    AND AR.ACCION = ? \n"
                + "    AND AR.FUENTE = ? \n"
                + "    AND AR.FONDO = ? \n"
                + "    AND AR.RECURSO = ? \n"
                + "ORDER BY \n"
                + "    AR.RAMO, \n"
                + "    AR.META, \n"
                + "    AR.ACCION, \n"
                + "    AR.REQUERIMIENTO";
        return strSQL;
    }

    public String getSQLgetRequerimientosByAccion() {
        String strSQL = "SELECT * \n"
                + "FROM \n"
                + "    POA.ACCION_REQ\n"
                + "WHERE \n"
                + "    YEAR = ? \n"
                + "    AND RAMO = ? \n"
                + "    AND META =  ? \n"
                + "    AND ACCION = ? \n"
                + "ORDER BY \n"
                + "    RAMO,\n"
                + "    META,\n"
                + "    ACCION,\n"
                + "    REQUERIMIENTO";
        return strSQL;
    }

    public String getSQLupdateAccionReqFuente(int year, String ramo, int meta,
            int accion, int reqerimiento, String fuente, String fondo, String recurso) {
        String strSQL = "UPDATE POA.ACCION_REQ AR\n"
                + "SET AR.FUENTE = '" + fuente + "', \n"
                + "    AR.FONDO = '" + fondo + "', \n"
                + "    AR.RECURSO = '" + recurso + "', \n"
                + "    AR.FECHA_MODIFICACION = SYSDATE \n"
                + "WHERE \n"
                + "    AR.YEAR = " + year + " \n"
                + "    AND AR.RAMO = '" + ramo + "'\n"
                + "    AND AR.META = " + meta + "\n"
                + "    AND AR.ACCION = " + accion + "\n"
                + "    AND AR.REQUERIMIENTO = " + reqerimiento + "";
        return strSQL;
    }

    public String getSQLupdatePttoFuente(int year, String ramo, int meta,
            int accion, String fuenteN, String fondoN, String recursoN,
            String fuente, String fondo, String recurso) {
        String strSQL = "UPDATE SPP.PPTO AR\n"
                + "SET AR.FUENTE = '" + fuenteN + "', \n"
                + "    AR.FONDO = '" + fondoN + "', \n"
                + "    AR.RECURSO = '" + recursoN + "', \n"
                + "    AR.FECHA_MODIFICACION = SYSDATE \n"
                + "WHERE \n"
                + "    AR.YEAR = " + year + " \n"
                + "    AND AR.RAMO = '" + ramo + "'\n"
                + "    AND AR.META = " + meta + "\n"
                + "    AND AR.ACCION = " + accion + "\n"
                + "    AND AR.FUENTE = " + fuente + " \n "
                + "    AND AR.FONDO = " + fondo + "\n "
                + "    AND AR.RECURSO = " + recurso;
        return strSQL;
    }

    public String getSQLCuentaPlantillaDepto() {
        String strSQL = "SELECT CASE \n"
                + "        WHEN count(1) > 0 \n"
                + "        THEN 'TRUE' \n"
                + "        ELSE 'FALSE' END \n"
                + "        AS PLANTILLA "
                + " FROM \n"
                + "    POA.META_ACCION_PLANTILLA \n"
                + " WHERE YEAR = ? \n"
                + "    AND RAMO = ? \n"
                + "    AND PRG = ? \n"
                + "    AND DEPTO = ? \n"
                + "    AND META = ? \n"
                + "    AND ACCION = ?";
        return strSQL;

    }

    public String getSQLValidaCapturaCIM(int periodo, int year, String ramos, String prgI, String prgF) {
        String strSQL = " SELECT COUNT(1) AS CONTADOR "
                + " FROM "
                + " S_CIM_CIERRE_PPTO_ARMO "
                + " WHERE "
                + " PERIODO= " + periodo + " "
                + " AND EJERCICIO= " + year + " "
                + " AND MES=" + (periodo * 3) + " "
                + " AND RAMO IN ( " + ramos + " ) ";
        if (!prgI.equals("-1") && !prgF.equals("-1")) {
            strSQL += " AND PROGRAMA BETWEEN '" + prgI + "' AND '" + prgF + "' ";
        }

        return strSQL;
    }

    public String getSQLParamValidaCIM() {
        String strSQL = " SELECT REPVALIDA_INFOCIM FROM DGI.PARAMETROS ";
        return strSQL;
    }

    public String getSQLDesligarIndicadorRamoSector(String sYear, String sClaveIndicador, String sRamo, String sPrograma) {
        String strSQL = ""
                + "DELETE  "
                + "FROM      "
                + "    POA.INDICADORES_SECTOR_RAMOS "
                + "WHERE "
                + "    YEAR = " + sYear + " AND "
                + "    CLAVE_INDICADOR = '" + sClaveIndicador + "' AND  "
                + "    RAMO = '" + sRamo + "' AND  "
                + "    PRG = '" + sPrograma + "'  "
                + "";
        return strSQL;
    }

    public String getSQLLigarIndicadorRamoSector(String sYear, String sClaveIndicador, String sRamo, String sPrograma) {
        String strSQL = ""
                + "INSERT INTO "
                + "    POA.INDICADORES_SECTOR_RAMOS "
                + "    ( "
                + "       INDICADORES_SECTOR_RAMOS.YEAR, "
                + "       INDICADORES_SECTOR_RAMOS.CLAVE_INDICADOR, "
                + "       INDICADORES_SECTOR_RAMOS.RAMO, "
                + "       INDICADORES_SECTOR_RAMOS.PRG "
                + "   )  "
                + "VALUES "
                + "    ( "
                + "        '" + sYear + "', "
                + "        '" + sClaveIndicador + "', "
                + "        '" + sRamo + "', "
                + "        '" + sPrograma + "' "
                + "    )  "
                + "";
        return strSQL;
    }

    public String getSQLgetTipoCalculoMeta() {
        String strSQL = "SELECT DECODE(M.CALCULO,'AC',SUM(AV.VALOR),DECODE(M.CALCULO,'MI',MIN(AV.VALOR),MAX(AV.VALOR))) CALCULO\n"
                + "FROM \n"
                + "    POA.MOVOFICIOS_ESTIMACION AV,\n"
                + "    POA.MOVOFICIOS_META M\n"
                + "WHERE M.OFICIO = ? \n"
                + "    AND M.RAMO = ? \n"
                + "    AND M.META = ? \n"
                + "    AND AV.OFICIO = M.OFICIO\n"
                + "    AND AV.RAMO = M.RAMO\n"
                + "    AND AV.META = M.META\n"
                + "GROUP BY CALCULO";
        return strSQL;
    }

    public String getSQLgetTipoCalculoAccion() {
        String strSQL = "SELECT DECODE(M.CALCULO,'AC',SUM(AV.VALOR),DECODE(M.CALCULO,'MI',MIN(AV.VALOR),MAX(AV.VALOR))) CALCULO\n"
                + "FROM \n"
                + "    POA.MOVOFICIOS_ACCION_ESTIMACION AV,\n"
                + "    POA.MOVOFICIOS_ACCION M\n"
                + "WHERE M.OFICIO = ? \n"
                + "    AND M.RAMO = ? \n"
                + "    AND M.META = ? \n"
                + "    AND M.ACCION = ? \n"
                + "    AND AV.OFICIO = M.OFICIO\n"
                + "    AND AV.RAMO = M.RAMO\n"
                + "    AND AV.META = M.META\n"
                + "    AND AV.ACCION = M.ACCION\n"
                + "GROUP BY CALCULO";
        return strSQL;
    }

    public String getSQLGetProgramasByYearAppLogin(int year, String usuario, String sRamo, String sClaveIndicador) {

        String strSQL = " "
                + "SELECT  "
                + "	DISTINCT  "
                + "		RPRO.PRG, "
                + "		PRO.DESCR,  "
                + "		USU.APP_LOGIN "
                + "FROM  "
                + "	POA.RAMO_PROGRAMA RPRO,  "
                + "	POA.VW_ACC_USR_RAMO USU,  "
                + "	S_POA_PROGRAMA PRO "
                + "WHERE  "
                + "	PRO.FIN_PRG = " + "1" + " AND "
                + "	RPRO.YEAR = " + year + " AND "
                + "	RPRO.RAMO = " + sRamo + " AND "
                + "	PRO.YEAR = RPRO.YEAR AND "
                + "	PRO.PRG = RPRO.PRG AND "
                + "	USU.PRG = RPRO.PRG AND "
                + "	USU.APP_LOGIN = '" + usuario + "' AND "
                + "	USU.YEAR = RPRO.YEAR      "
                + "     AND NOT EXISTS "
                + "        ( "
                + "            SELECT "
                + "                * "
                + "            FROM  "
                + "                POA.INDICADORES_SECTOR_RAMOS INDICSECRAM "
                + "            WHERE "
                + "                INDICSECRAM.YEAR = RPRO.YEAR AND "
                + "                INDICSECRAM.PRG = PRO.PRG AND "
                + "                INDICSECRAM.CLAVE_INDICADOR = '" + sClaveIndicador + "' AND "
                + "                INDICSECRAM.RAMO = '" + sRamo + "' "
                + "        )  "
                + "ORDER BY  "
                + "	RPRO.PRG "
                + " ";

        return strSQL;
    }

    public String getSQLAllMetasByRamo() {
        String strSQL = "SELECT M.META, M.DESCR \n"
                + "FROM \n"
                + "    S_POA_META M \n"
                + "WHERE \n"
                + "    M.YEAR = ? \n"
                + "    AND M.RAMO = ? \n"
                + "ORDER BY M.META";
        return strSQL;
    }

    public String getSQLAllAccionesByMeta() {
        String strSQL = "SELECT ACCION, DESCR FROM \n"
                + "    S_POA_ACCION\n"
                + "WHERE\n"
                + "    YEAR = ? \n"
                + "    AND RAMO = ? \n"
                + "    AND META = ? ORDER BY ACCION ";
        return strSQL;
    }

    public String getSQLDeletePPTOenCeroByAccion(int year, String ramo, int meta, int accion) {
        String strSQL = "DELETE FROM\n"
                + "    SPP.PPTO P\n"
                + "WHERE\n"
                + "    (P.YEAR,P.RAMO,P.META,P.ACCION)\n"
                + "    IN\n"
                + "    (SELECT YEAR,RAMO,META,ACCION\n"
                + "    FROM SPP.PPTO\n"
                + "    WHERE YEAR = " + year + "  \n"
                + "        AND RAMO = '" + ramo + "'\n"
                + "        AND META = " + meta + " \n"
                + "        AND ACCION = " + accion + " \n"
                + "    GROUP BY YEAR,RAMO,META,ACCION\n"
                + "    HAVING SUM(ASIGNADO) = 0)";
        return strSQL;
    }

    public String getSQLDeleteCodigosByAccion(int year, String ramo, int meta, int accion) {
        String strSQL = "DELETE FROM\n"
                + "    SPP.CODIGOS P\n"
                + " WHERE YEAR = " + year + "  \n"
                + "   AND RAMO = '" + ramo + "'\n"
                + "   AND META = " + meta + " \n"
                + "   AND ACCION = " + accion;
        return strSQL;
    }

    public String getSQLcountPresupPlantillaByAccionReq() {
        return " SELECT \n"
                + "    CASE\n"
                + "    WHEN COUNT(1) > 0\n"
                + "    THEN 'TRUE'\n"
                + "    ELSE 'FALSE' END\n"
                + "    AS PRES_PLANTILLA\n"
                + "FROM \n"
                + "    POA.PRESUP_PLANTILLA\n"
                + "WHERE YEAR = ? \n"
                + "    AND RAMO = ? \n"
                + "    AND META = ? \n"
                + "    AND ACCION = ? \n"
                + "    AND PARTIDA = ? \n"
                + "    AND TIPO_GASTO = ? \n"
                + "    AND FUENTE = ? \n"
                + "    AND FONDO = ? \n"
                + "    AND RECURSO = ? \n"
                + "    AND REL_LABORAL = ?";
    }

    public String getSQLupdatePresupPlantillaByAccionReq(CodigoPPTO codigoN, int year, String ramo, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String relLaboral) {
        return "UPDATE\n"
                + "    POA.PRESUP_PLANTILLA\n"
                + "SET\n"
                + "    DEPTO = '" + codigoN.getDepto() + "',\n"
                + "    FINALIDAD = '" + codigoN.getFinalidad() + "',\n"
                + "    FUNCION = '" + codigoN.getFuncion() + "',\n"
                + "    SUBFUNCION = '" + codigoN.getSubfuncion() + "',\n"
                + "    PRG_CONAC = '" + codigoN.getProgCONAC() + "',\n"
                + "    PRG = '" + codigoN.getPrograma() + "',\n"
                + "    TIPO_PROY = '" + codigoN.getTipoProy() + "',\n"
                + "    PROY = " + codigoN.getProyecto() + ",\n"
                + "    META = " + codigoN.getMeta() + ",\n"
                + "    ACCION = " + codigoN.getAccion() + ",\n"
                + "    MUNICIPIO = " + codigoN.getMunicipio() + ",\n"
                + "    DELEGACION = " + codigoN.getDelegacion() + "\n"
                + "WHERE\n"
                + "    YEAR = '" + year + "' \n"
                + "    AND RAMO = '" + ramo + "'\n"
                + "    AND META = " + meta + "\n"
                + "    AND ACCION = " + accion + "\n"
                + "    AND PARTIDA = '" + partida + "'\n"
                + "    AND TIPO_GASTO = '" + tipoGasto + "'\n"
                + "    AND FUENTE = '" + fuente + "'\n"
                + "    AND FONDO = '" + fondo + "'\n"
                + "    AND RECURSO = '" + recurso + "'\n"
                + "    AND REL_LABORAL = '" + relLaboral + "'";
    }

    public String getSQLupdatePresupPlantillaByAccionReqFF(int year, String ramo, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String relLaboral, String fuenteN, String fondoN, String recursoN) {
        return "UPDATE\n"
                + "    POA.PRESUP_PLANTILLA\n"
                + "SET\n"
                + "    FUENTE = '" + fuenteN + "', \n"
                + "    FONDO = '" + fondoN + "', \n"
                + "    RECURSO = '" + recursoN + "'\n"
                + "WHERE\n"
                + "    YEAR = '" + year + "' \n"
                + "    AND RAMO = '" + ramo + "'\n"
                + "    AND META = " + meta + "\n"
                + "    AND ACCION = " + accion + "\n"
                + "    AND PARTIDA = '" + partida + "'\n"
                + "    AND TIPO_GASTO = '" + tipoGasto + "'\n"
                + "    AND FUENTE = '" + fuente + "'\n"
                + "    AND FONDO = '" + fondo + "'\n"
                + "    AND RECURSO = '" + recurso + "'\n"
                + "    AND REL_LABORAL = '" + relLaboral + "'";
    }

    public String getSQLUpdateMetaAccionPlantilla(CodigoPPTO codigo, int year,
            String ramo, String prg, String depto, int meta, int accion) {
        return "UPDATE\n"
                + "    POA.META_ACCION_PLANTILLA MAP\n"
                + "SET\n"
                + "    MAP.PRG = '" + codigo.getPrograma() + "'  ,\n"
                + "    MAP.DEPTO = '" + codigo.getDepto() + "',\n"
                + "    MAP.META = '" + codigo.getMeta() + "',\n"
                + "    MAP.ACCION = '" + codigo.getAccion() + "'\n"
                + "WHERE\n"
                + "    MAP.YEAR = " + year + " \n"
                + "    AND MAP.RAMO = '" + ramo + "' \n"
                + "    AND MAP.PRG = '" + prg + "' \n"
                + "    AND MAP.DEPTO = '" + depto + "' \n"
                + "    AND MAP.META = " + meta + " \n"
                + "    AND MAP.ACCION = " + accion + "";
    }

    public String getSQLIsSequenceRebooted() {
        return " SELECT CASE \n"
                + "        WHEN COUNT(1) > 0\n"
                + "        THEN 'TRUE'\n"
                + "        ELSE 'FALSE' END AS REG_SEQ\n"
                + " FROM POA.REGISTRO_REINICIO_ANUAL\n"
                + " WHERE YEAR = ?\n"
                + " AND SECUENCIA = 1";
    }

    public String getSQLIsPPTOCongelado() {
        return " SELECT CASE \n"
                + "        WHEN COUNT(1) > 0\n"
                + "        THEN 'TRUE'\n"
                + "        ELSE 'FALSE' END AS REG_CONG\n"
                + " FROM POA.REGISTRO_REINICIO_ANUAL\n"
                + " WHERE YEAR = ?\n"
                + " AND CONGELADO = 1";
    }

    public String getSQLIsYearReinicioRegistrado() {
        return " SELECT CASE \n"
                + "        WHEN COUNT(1) > 0\n"
                + "        THEN 'TRUE'\n"
                + "        ELSE 'FALSE' END AS REG_YEAR\n"
                + " FROM POA.REGISTRO_REINICIO_ANUAL\n"
                + " WHERE YEAR = ?\n";
    }

    public String getSQLInsertRegistroReinicio(int year, int secuencia, int congelado) {
        return "INSERT INTO\n"
                + "POA.REGISTRO_REINICIO_ANUAL\n"
                + "VALUES(" + year + "," + secuencia + "," + congelado + ")";
    }

    public String getSQLUpdateRegistroReinicio(int year, boolean isSecuencia) {
        String strSQL = "UPDATE POA.REGISTRO_REINICIO_ANUAL\n";
        if (isSecuencia) {
            strSQL += "SET SECUENCIA = 1\n";
        } else {
            strSQL += "SET CONGELADO = 1\n";
        }
        strSQL += "WHERE YEAR = " + year;
        return strSQL;
    }

    public String getSQLCountMetaAccionPlantilla() {
        return "SELECT \n"
                + "    CASE\n"
                + "    WHEN COUNT(1) > 0\n"
                + "    THEN 'TRUE'\n"
                + "    ELSE 'FALSE' END\n"
                + "    AS PLANTILLA\n"
                + "FROM \n"
                + "    POA.META_ACCION_PLANTILLA\n"
                + "WHERE \n"
                + "    YEAR = ? \n"
                + "    AND RAMO = ? \n"
                + "    AND PRG = ? \n"
                + "    AND DEPTO = ? ";
    }

    public String getSQLAcumuladoCaptura() {
        return "SELECT TRANS.trans+TRANSF.trans ACUM\n"
                + "FROM\n"
                + "    (\n"
                + "    SELECT NVL(SUM(TR.IMPTE) ,0) as trans\n"
                + "    FROM \n"
                + "        SPP.TRANSFERENCIAS TR,\n"
                + "        POA.MOVOFICIOS MV\n"
                + "    WHERE  \n"
                + "        TR.OFICIO = ? \n"
                + "        AND TR.PARTIDA = ? \n"
                + "        AND TR.RAMO = ? \n"
                + "        AND MV.OFICIO = TR.OFICIO\n"
                + "        AND TO_CHAR(MV.FECPPTO,'YYYY')= ? ) TRANS,\n"
                + "    (SELECT   \n"
                + "        NVL(SUM(TR.IMPTE) ,0) as trans\n"
                + "    FROM \n"
                + "        SPP.TRANSFREC TR,\n"
                + "        POA.MOVOFICIOS MV\n"
                + "    WHERE  \n"
                + "        TR.OFICIO = ? \n"
                + "        AND TR.PARTIDA = ? \n"
                + "        AND TR.RAMO = ? \n"
                + "        AND MV.OFICIO = TR.OFICIO\n"
                + "        AND TO_CHAR(MV.FECPPTO,'YYYY')= ? ) TRANSF";
    }

    public String getSQLCountValidaTransferenciasOficons(int oficio) {
        String strSQL = "SELECT COUNT(1) \n"
                + "FROM POA.MOVOFICIOS M,\n"
                + "DGI.TIPOFICIO TF,\n"
                + "SPP.TRANSFERENCIAS T\n"
                + "WHERE\n"
                + "M.OFICIO=" + oficio + " \n"
                + "AND M.TIPOMOV='T'\n"
                + "AND M.OFICIO=TF.OFICIO\n"
                + "AND T.OFICIO=M.OFICIO\n"
                + "AND (T.OFICIO||T.CONSEC NOT IN(SELECT O.OFICIO||O.CONSEC FROM DGI.OFICONS O WHERE O.OFICIO=T.OFICIO)\n"
                + "OR T.OFICIO||T.CONSEC NOT IN(SELECT R.OFICIO||R.CONSEC FROM SPP.TRANSFREC R WHERE R.OFICIO=T.OFICIO))";

        return strSQL;
    }

    public String getSQLCountValidaAmpliacionesOficons(int oficio) {
        String strSQL = "SELECT COUNT(1) \n"
                + "FROM POA.MOVOFICIOS M,\n"
                + "DGI.TIPOFICIO TF,\n"
                + "SPP.AMPLIACIONES A\n"
                + "WHERE\n"
                + "M.OFICIO=" + oficio + "\n"
                + "AND M.TIPOMOV='A'\n"
                + "AND M.OFICIO=TF.OFICIO\n"
                + "AND A.OFICIO=M.OFICIO\n"
                + "AND (A.OFICIO||A.CONSEC NOT IN(SELECT O.OFICIO||O.CONSEC FROM DGI.OFICONS O WHERE O.OFICIO=A.OFICIO))";

        return strSQL;
    }

    public String getSQLgetAccionesByMeta() {
        String strSQL = "SELECT COUNT(1) AS CONT \n"
                + "FROM S_POA_ACCION\n"
                + "WHERE YEAR = ?\n"
                + "AND RAMO = ?\n"
                + "AND META = ?";
        return strSQL;
    }

    public String getSQLcountMovoficiosAccion() {
        String strSQL = "SELECT COUNT(1) AS ACCION \n"
                + "FROM POA.MOVOFICIOS_ACCION\n"
                + "WHERE OFICIO = ?\n"
                + "AND RAMO = ?\n"
                + "AND META = ?";
        return strSQL;
    }

    public String getSQLGetMovOficioLigados(int year, int oficio) {
        String strSQL = ""
                + "SELECT "
                + "    * "
                + "FROM "
                + "    S_POA_MOVOFICIOS_LIGADOS "
                + "WHERE "
                + "    YEAR = " + year + " AND "
                + "    OFICIO = " + oficio + " "
                + "ORDER BY "
                + "    LIGADO_PARAESTATAL ASC "
                + "";
        return strSQL;
    }

    public String getSQLGetOficioLigadoNormativo(int year, int oficio, String isParaestatal) {
        String strSQL = ""
                + "SELECT "
                + "    OFICIO "
                + "FROM "
                + "    S_POA_MOVOFICIOS_LIGADOS "
                + "WHERE "
                + "    YEAR = " + year + " AND "
                + "    LIGADO_PARAESTATAL = '" + isParaestatal + "' AND "
                + "    OFICIO_LIGADO = " + oficio + " "
                + "";
        return strSQL;
    }

    public String getSQLIsOficioLigado(int year, int oficio, String isParaestatal) {
        String strSQL = ""
                + "SELECT "
                + "    CASE WHEN SUM(TOTAL) > 0 THEN 1 ELSE 0 END TOTAL "
                + "FROM "
                + "   ( "
                + "       SELECT "
                + "            COUNT(*) TOTAL "
                + "        FROM "
                + "            S_POA_MOVOFICIOS_LIGADOS "
                + "        WHERE "
                + "            YEAR = " + year + " AND "
                + "            OFICIO =  " + oficio + " AND "
                + "             'S' <> '" + isParaestatal + "' "
                + "        UNION ALL "
                + "        SELECT "
                + "            COUNT(*) TOTAL "
                + "        FROM "
                + "            S_POA_MOVOFICIOS_LIGADOS "
                + "        WHERE "
                + "            YEAR = " + year + " AND "
                + "            OFICIO_LIGADO = " + oficio + " AND "
                + "            LIGADO_PARAESTATAL = '" + isParaestatal + "' "
                + "    ) "
                + "";
        return strSQL;
    }

    public String getSQLIsOficioLigadoNormativo(int year, int oficio) {
        String strSQL = ""
                + "SELECT "
                + "    CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END TOTAL "
                + "FROM "
                + "    S_POA_MOVOFICIOS_LIGADOS "
                + "WHERE "
                + "    OFICIO = " + oficio + " "
                + "    AND YEAR = " + year + " "
                + "GROUP BY  "
                + "    OFICIO "
                + "";
        return strSQL;
    }

    public String getSQLGetRamoByOficio(int oficio) {
        String strSQL = ""
                + "SELECT "
                + "    US.RAMO "
                + "FROM "
                + "    POA.MOVOFICIOS M, "
                + "    DGI.DGI_USR US "
                + "WHERE "
                + "     M.OFICIO = " + oficio + " AND "
                + "     TRIM(US.APP_LOGIN) = TRIM(M.APP_LOGIN) AND "
                + "     US.SYS_CLAVE = 1 "
                + "";
        return strSQL;
    }

    public String getSQLValidaOficioAutorizadoMeta() {
        String strSQL = "SELECT DISTINCT MAX(OFICIO) OFICIO FROM (\n"
                + "    SELECT M.OFICIO FROM \n"
                + "        POA.MOVOFICIOS_META MM,\n"
                + "        POA.MOVOFICIOS M\n"
                + "    WHERE \n"
                + "        MM.YEAR = ?\n"
                + "        AND MM.RAMO = ?\n"
                + "        AND MM.META = ?\n"
                + "        AND M.OFICIO = MM.OFICIO \n"
                + "        AND M.STATUS = 'A'\n"
                + "        AND M.FECHAELAB > TO_DATE(?,'YYYY-MM-DD')\n"
                + "    UNION\n"
                + "        SELECT M.OFICIO FROM \n"
                + "        POA.MOVOFICIOS_ESTIMACION MM,\n"
                + "        POA.MOVOFICIOS M\n"
                + "    WHERE \n"
                + "        MM.YEAR = ?\n"
                + "        AND MM.RAMO = ?\n"
                + "        AND MM.META = ?\n"
                + "        AND M.OFICIO = MM.OFICIO \n"
                + "        AND M.STATUS = 'A'\n"
                + "        AND M.FECHAELAB > TO_DATE(?,'YYYY-MM-DD')\n"
                + ")";
        return strSQL;
    }

    public String getSQLValidaOficioAutorizadoAccion() {
        String strSQL = "SELECT DISTINCT MAX(OFICIO) OFICIO FROM (\n"
                + "SELECT M.OFICIO FROM \n"
                + "    POA.MOVOFICIOS_ACCION MM,\n"
                + "    POA.MOVOFICIOS M\n"
                + "WHERE \n"
                + "    MM.YEAR = ?\n"
                + "    AND MM.RAMO = ?\n"
                + "    AND MM.META = ?\n"
                + "    AND MM.ACCION = ?\n"
                + "    AND M.OFICIO = MM.OFICIO\n"
                + "    AND M.STATUS = 'A'\n"
                + "    AND M.FECHAELAB > TO_DATE(?,'YYYY-MM-DD')\n"
                + "UNION\n"
                + "    SELECT M.OFICIO FROM \n"
                + "    POA.MOVOFICIOS_ACCION_ESTIMACION MM,\n"
                + "    POA.MOVOFICIOS M\n"
                + "WHERE \n"
                + "    MM.YEAR = ?\n"
                + "    AND MM.RAMO = ?\n"
                + "    AND MM.META = ?\n"
                + "    AND MM.ACCION = ?\n"
                + "    AND M.OFICIO = MM.OFICIO\n"
                + "    AND M.STATUS = 'A'\n"
                + "    AND M.FECHAELAB > TO_DATE(?,'YYYY-MM-DD')\n"
                + ")";
        return strSQL;
    }

    public String getSQLValidaModificacionEstimacionAutorizado() {
        String strSQL = "SELECT VAL_AUTORIZADO \n"
                + "FROM \n"
                + "    POA.MOVOFICIOS_ESTIMACION\n"
                + "WHERE OFICIO = ?\n"
                + "    AND RAMO = ?\n"
                + "    AND META = ?";
        return strSQL;
    }

    public String getSQLUpdateValidaMoficacionEstimacion(String valAutorizado, int oficio, String ramo, int meta) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ESTIMACION ME\n"
                + "SET ME.VAL_AUTORIZADO = '" + valAutorizado + "'\n"
                + "WHERE OFICIO = " + oficio + "\n"
                + "AND RAMO = '" + ramo + "'\n"
                + "AND META = " + meta;
        return strSQL;
    }

    public String getSQLUpdateValidaMoficacionMeta(String valAutorizado, int oficio, String ramo, int meta) {
        String strSQL = "UPDATE POA.MOVOFICIOS_META ME\n"
                + "SET ME.VAL_AUTORIZADO = '" + valAutorizado + "'\n"
                + "WHERE OFICIO = " + oficio + "\n"
                + "AND RAMO = '" + ramo + "'\n"
                + "AND META = " + meta;
        return strSQL;
    }

    public String getSQLValidaModificacionMetaAutorizado() {
        String strSQL = "SELECT VAL_AUTORIZADO \n"
                + "FROM \n"
                + "    POA.MOVOFICIOS_META\n"
                + "WHERE OFICIO = ?\n"
                + "    AND RAMO = ?\n"
                + "    AND META = ?";
        return strSQL;
    }

    public String getSQLValidaModificacionAccionEstimacionAutorizado() {
        String strSQL = "SELECT VAL_AUTORIZADO \n"
                + "FROM \n"
                + "    POA.MOVOFICIOS_ACCION_ESTIMACION\n"
                + "WHERE OFICIO = ?\n"
                + "    AND RAMO = ?\n"
                + "    AND META = ? \n"
                + "    AND ACCION = ?";
        return strSQL;
    }

    public String getSQLValidaModificacionAccionAutorizado() {
        String strSQL = "SELECT VAL_AUTORIZADO \n"
                + "FROM \n"
                + "    POA.MOVOFICIOS_ACCION\n"
                + "WHERE OFICIO = ?\n"
                + "    AND RAMO = ?\n"
                + "    AND META = ?"
                + "    AND ACCION = ?";
        return strSQL;
    }

    public String getSQLUpdateValidaMoficacionAccionEstimacion(String valAutorizado,
            int oficio, String ramo, int meta, int accion) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ACCION_ESTIMACION ME\n"
                + "SET ME.VAL_AUTORIZADO = '" + valAutorizado + "'\n"
                + "WHERE OFICIO = " + oficio + "\n"
                + "   AND RAMO = '" + ramo + "'\n"
                + "   AND META = " + meta + " \n"
                + "   AND ACCION = " + accion;
        return strSQL;
    }

    public String getSQLUpdateValidaMoficacionAccion(String valAutorizado,
            int oficio, String ramo, int meta, int accion) {
        String strSQL = "UPDATE POA.MOVOFICIOS_ACCION ME\n"
                + "SET ME.VAL_AUTORIZADO = '" + valAutorizado + "'\n"
                + "WHERE OFICIO = " + oficio + "\n"
                + "   AND RAMO = '" + ramo + "'\n"
                + "   AND META = " + meta + " \n"
                + "   AND ACCION = " + accion;
        return strSQL;
    }

    public String getSQLIsTraeResultadoMeta() {
        String strSQL = " SELECT RESULTADO \n"
                + "FROM \n"
                + "S_POA_META \n"
                + "WHERE \n"
                + "META=? AND \n"
                + "YEAR=? AND \n"
                + "RAMO=?";
        return strSQL;
    }

    public String getSQLOnTraeResultadoMeta(int year, String ramo, int meta) {
        String strSQL = "UPDATE S_POA_META SET RESULTADO='S'  WHERE META=" + meta + " AND YEAR=" + year + " AND RAMO='" + ramo + "' ";
        return strSQL;
    }

    public String getSQLOffTraeResultadoMeta(int year, String ramo, int meta) {
        String strSQL = "UPDATE S_POA_META SET RESULTADO='N'  WHERE META=" + meta + " AND YEAR=" + year + " AND RAMO='" + ramo + "' ";
        return strSQL;
    }

    public String getSQLIsTraeResultadoAccion() {
        String strSQL = " SELECT RESULTADO \n"
                + "FROM \n"
                + "S_POA_ACCION \n"
                + "WHERE \n"
                + "META=? AND \n"
                + "YEAR=? AND \n"
                + "RAMO=? AND "
                + "ACCION=? ";
        return strSQL;
    }

    public String getSQLOnTraeResultadoAccion(int year, String ramo, int meta, int accion) {
        String strSQL = "UPDATE S_POA_ACCION SET RESULTADO='S'  WHERE META=" + meta + " AND YEAR=" + year + " AND RAMO='" + ramo + "' AND ACCION=" + accion + " ";
        return strSQL;
    }

    public String getSQLOffTraeResultadoAccion(int year, String ramo, int meta, int accion) {
        String strSQL = "UPDATE S_POA_ACCION SET RESULTADO='N'  WHERE META=" + meta + " AND YEAR=" + year + " AND RAMO='" + ramo + "' AND ACCION=" + accion + "";
        return strSQL;
    }

    public String getSQLAvanceAccionesAvancePoaMuestraUnidadMedida() {
        /*obtengo LA UNIDAD DE MEDIDA el avance de la accion*/
        String strSQL = " SELECT /*M.MEDIDA||'-'||*/A.RESULTADO||','||M.DESCR AS ACCION_MEDIDA_DESCR \n"
                + " FROM\n"
                + " S_POA_ACCION A,\n"
                + " S_POA_MEDIDA_META M\n"
                + " WHERE \n"
                + " A.ACCION=?\n"
                + " AND A.META=?\n"
                + " AND A.RAMO=?\n"
                + " AND A.YEAR=?\n"
                + " AND A.CVE_MEDIDA=M.MEDIDA\n"
                + " AND A.YEAR=M.YEAR ";
        return strSQL;

    }

    public String getSQLAvanceMetasAvancePoaMuestraUnidadMedida() {
        /*obtengo LA UNIDAD DE MEDIDA avance de la META*/
        String strSQL = " SELECT /*M.MEDIDA||'-'||*/A.RESULTADO||','||M.DESCR AS META_MEDIDA_DESCR \n"
                + " FROM\n"
                + " S_POA_META A,\n"
                + " S_POA_MEDIDA_META M\n"
                + " WHERE \n"
                + " A.META=?\n"
                + " AND A.RAMO=?\n"
                + " AND A.YEAR=?\n"
                + " AND A.CVE_MEDIDA=M.MEDIDA\n"
                + " AND A.YEAR=M.YEAR ";
        return strSQL;

    }

    public String getSQLgetRamosListByMovimientos() {
        String strSQL = "SELECT M.RAMO, R.DESCR FROM \n"
                + "DGI.RAMOS R,(\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS_META MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS_ACCION MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS_ACCION_REQ MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS_ESTIMACION MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS_ACCION_ESTIMACION MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + ") M\n"
                + "WHERE \n"
                + "R.YEAR = ?\n"
                + "AND M.RAMO = R.RAMO";
        return strSQL;
    }

    public String getSQLgetRamosListByMovimientosAmpTrans() {
        String strSQL = "SELECT M.RAMO, R.DESCR FROM \n"
                + "DGI.RAMOS R,(\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS_META MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS_ACCION MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        SPP.TRANSFERENCIAS MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        SPP.AMPLIACIONES MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS_ESTIMACION MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        MM.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS_ACCION_ESTIMACION MM,\n"
                + "        POA.MOVOFICIOS MOVOF,    \n"
                + "        POA.ESTATUS_MOV E    \n"
                + "    WHERE  \n"
                + "        MOVOF.TIPOMOV = ?\n"
                + "        AND MOVOF.STATUS = ?\n"
                + "        AND MOVOF.STATUS = E.ESTATUS \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND MM.OFICIO = MOVOF.OFICIO\n"
                + ") M\n"
                + "WHERE \n"
                + "R.YEAR = ?\n"
                + "AND M.RAMO = R.RAMO";
        return strSQL;
    }

    public String getSQLgetRamosListByMovimientosAmpTransTipof() {
        String strSQL = "SELECT M.RAMO, R.DESCR FROM    \n"
                + "(\n"
                + "    SELECT  \n"
                + "        T.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS MOVOF,  \n"
                + "        DGI.TIPOFICIO  TIPOF,   \n"
                + "        SPP.AMPLIACIONES T\n"
                + "    WHERE  \n"
                + "        T.OFICIO = MOVOF.OFICIO \n"
                + "        AND MOVOF.TIPOMOV = ?  \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND TIPOF.OFICIO (+)= MOVOF.OFICIO\n"
                + "        AND TIPOF.STATUS = ? \n"
                + "UNION    SELECT  \n"
                + "        T.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS MOVOF,  \n"
                + "        DGI.TIPOFICIO  TIPOF,   \n"
                + "        SPP.TRANSFERENCIAS T\n"
                + "    WHERE  \n"
                + "        T.OFICIO = MOVOF.OFICIO \n"
                + "        AND MOVOF.TIPOMOV = ?  \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND TIPOF.OFICIO (+)= MOVOF.OFICIO\n"
                + "        AND TIPOF.STATUS = ? \n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        T.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS MOVOF,  \n"
                + "        DGI.TIPOFICIO  TIPOF,   \n"
                + "        SPP.TRANSFREC T\n"
                + "    WHERE  \n"
                + "        T.OFICIO = MOVOF.OFICIO \n"
                + "        AND MOVOF.TIPOMOV = ?  \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND TIPOF.OFICIO (+)= MOVOF.OFICIO\n"
                + "        AND TIPOF.STATUS = ?\n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        T.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS MOVOF,  \n"
                + "        DGI.TIPOFICIO  TIPOF,   \n"
                + "        POA.MOVOFICIOS_META T\n"
                + "    WHERE  \n"
                + "        T.OFICIO = MOVOF.OFICIO \n"
                + "        AND MOVOF.TIPOMOV = ?  \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND TIPOF.OFICIO (+)= MOVOF.OFICIO\n"
                + "        AND TIPOF.STATUS = ? \n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        T.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS MOVOF,  \n"
                + "        DGI.TIPOFICIO  TIPOF,   \n"
                + "        POA.MOVOFICIOS_ESTIMACION T\n"
                + "    WHERE  \n"
                + "        T.OFICIO = MOVOF.OFICIO \n"
                + "        AND MOVOF.TIPOMOV = ?  \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND TIPOF.OFICIO (+)= MOVOF.OFICIO\n"
                + "        AND TIPOF.STATUS = ? \n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        T.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS MOVOF,  \n"
                + "        DGI.TIPOFICIO  TIPOF,   \n"
                + "        POA.MOVOFICIOS_ACCION T\n"
                + "    WHERE  \n"
                + "        T.OFICIO = MOVOF.OFICIO \n"
                + "        AND MOVOF.TIPOMOV = ?  \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND TIPOF.OFICIO (+)= MOVOF.OFICIO\n"
                + "        AND TIPOF.STATUS = ? \n"
                + "UNION\n"
                + "    SELECT  \n"
                + "        T.RAMO\n"
                + "    FROM   \n"
                + "        POA.MOVOFICIOS MOVOF,  \n"
                + "        DGI.TIPOFICIO  TIPOF,   \n"
                + "        POA.MOVOFICIOS_ACCION_ESTIMACION T\n"
                + "    WHERE  \n"
                + "        T.OFICIO = MOVOF.OFICIO \n"
                + "        AND MOVOF.TIPOMOV = ?  \n"
                + "        AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ?\n"
                + "        AND TIPOF.OFICIO (+)= MOVOF.OFICIO\n"
                + "        AND TIPOF.STATUS = ?     \n"
                + ") M, DGI.RAMOS R\n"
                + "WHERE \n"
                + "    R.YEAR = ?\n"
                + "    AND R.RAMO = M.RAMO\n"
                + "ORDER BY R.RAMO";
        return strSQL;
    }

    public String getSQLGetMovimientosListByRamoAfectado() {
        String strSQL = "SELECT MODS.YEAR,\n"
                + "    M.OFICIO,\n"
                + "    M.FECHAELAB, \n"
                + "    M.FECPPTO, \n"
                + "    M.APP_LOGIN,\n"
                + "    M.TIPOMOV,\n"
                + "    M.STATUS,\n"
                + "    M.JUSTIFICACION,\n"
                + "    M.OBS_RECHAZO,\n"
                + "    R.DESCR AS RAMO_DESCR,\n"
                + "    US.RAMO,\n"
                + "    EM.DESCR AS STATUSDESCR\n"
                + "FROM \n"
                + "(   SELECT M.OFICIO, M.RAMO,R.YEAR FROM  \n"
                + "    DGI.RAMOS R,( \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_META MM, \n"
                + "            POA.MOVOFICIOS MOVOF \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ACCION MM, \n"
                + "            POA.MOVOFICIOS MOVOF\n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ?  \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ACCION_REQ MM, \n"
                + "            POA.MOVOFICIOS MOVOF   \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ESTIMACION MM, \n"
                + "            POA.MOVOFICIOS MOVOF\n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ?  \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ACCION_ESTIMACION MM, \n"
                + "            POA.MOVOFICIOS MOVOF\n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ?  \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    ) M \n"
                + "    WHERE  \n"
                + "    R.YEAR = ? \n"
                + "    AND M.RAMO = R.RAMO\n"
                + ")MODS,\n"
                + "    POA.MOVOFICIOS M,\n"
                + "    DGI.RAMOS R,\n"
                + "    DGI.DGI_USR US,\n"
                + "    POA.ESTATUS_MOV EM\n"
                + "WHERE\n"
                + "    MODS.OFICIO = M.OFICIO\n"
                + "    AND TRIM(US.APP_LOGIN) = TRIM(M.APP_LOGIN)\n"
                + "    AND US.SYS_CLAVE = 1\n"
                + "    AND R.YEAR = MODS.YEAR\n"
                + "    AND R.RAMO = US.RAMO\n"
                + "    AND MODS.RAMO = ?\n"
                + "    AND EM.ESTATUS = M.STATUS\n"
                + "ORDER BY \n"
                + "    M.OFICIO";
        return strSQL;
    }

    public String getSQLGetMovimientosListByRamoAfectadoTransAmp() {
        String strSQL = "SELECT MODS.YEAR,\n"
                + "    M.OFICIO,\n"
                + "    M.FECHAELAB, \n"
                + "    M.FECPPTO, \n"
                + "    M.APP_LOGIN,\n"
                + "    M.TIPOMOV,\n"
                + "    M.STATUS,\n"
                + "    M.JUSTIFICACION,\n"
                + "    M.OBS_RECHAZO,\n"
                + "    R.DESCR AS RAMO_DESCR,\n"
                + "    US.RAMO,\n"
                + "    EM.DESCR AS STATUSDESCR\n"
                + "FROM \n"
                + "(   SELECT M.OFICIO, M.RAMO,R.YEAR FROM  \n"
                + "    DGI.RAMOS R,( \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_META MM, \n"
                + "            POA.MOVOFICIOS MOVOF \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ACCION MM, \n"
                + "            POA.MOVOFICIOS MOVOF\n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ?  \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            SPP.TRANSFERENCIAS MM, \n"
                + "            POA.MOVOFICIOS MOVOF   \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            SPP.TRANSFREC MM, \n"
                + "            POA.MOVOFICIOS MOVOF   \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            SPP.AMPLIACIONES MM, \n"
                + "            POA.MOVOFICIOS MOVOF   \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO             \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ESTIMACION MM, \n"
                + "            POA.MOVOFICIOS MOVOF\n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ?  \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ACCION_ESTIMACION MM, \n"
                + "            POA.MOVOFICIOS MOVOF\n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ?  \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    ) M \n"
                + "    WHERE  \n"
                + "    R.YEAR = ? \n"
                + "    AND M.RAMO = R.RAMO\n"
                + ")MODS,\n"
                + "    POA.MOVOFICIOS M,\n"
                + "    DGI.RAMOS R,\n"
                + "    DGI.DGI_USR US,\n"
                + "    POA.ESTATUS_MOV EM\n"
                + "WHERE\n"
                + "    MODS.OFICIO = M.OFICIO\n"
                + "    AND TRIM(US.APP_LOGIN) = TRIM(M.APP_LOGIN)\n"
                + "    AND US.SYS_CLAVE = 1\n"
                + "    AND R.YEAR = MODS.YEAR\n"
                + "    AND R.RAMO = US.RAMO\n"
                + "    AND MODS.RAMO = ?\n"
                + "    AND EM.ESTATUS = M.STATUS\n"
                + "ORDER BY \n"
                + "    M.OFICIO";
        return strSQL;
    }

    public String getSQLGetMovimientosListByRamoAfectadoTransAmpTipOf() {
        String strSQL = "SELECT MODS.YEAR,\n"
                + "    M.OFICIO,\n"
                + "    M.FECHAELAB, \n"
                + "    M.FECPPTO, \n"
                + "    M.APP_LOGIN,\n"
                + "    M.TIPOMOV,\n"
                + "    M.STATUS,\n"
                + "    M.JUSTIFICACION,\n"
                + "    M.OBS_RECHAZO,\n"
                + "    TP.TIPO,\n"
                + "    R.DESCR AS RAMO_DESCR,\n"
                + "    US.RAMO,\n"
                + "    EM.DESCR AS STATUSDESCR "
                + "FROM \n"
                + "(   SELECT M.OFICIO, M.RAMO,R.YEAR FROM  \n"
                + "    DGI.RAMOS R,( \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_META MM, \n"
                + "            POA.MOVOFICIOS MOVOF \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ACCION MM, \n"
                + "            POA.MOVOFICIOS MOVOF\n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ?  \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            SPP.TRANSFERENCIAS MM, \n"
                + "            POA.MOVOFICIOS MOVOF   \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            SPP.TRANSFREC MM, \n"
                + "            POA.MOVOFICIOS MOVOF   \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            SPP.AMPLIACIONES MM, \n"
                + "            POA.MOVOFICIOS MOVOF   \n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ? \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO             \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ESTIMACION MM, \n"
                + "            POA.MOVOFICIOS MOVOF\n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ?  \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    UNION \n"
                + "        SELECT   \n"
                + "            MM.OFICIO, MM.RAMO\n"
                + "        FROM    \n"
                + "            POA.MOVOFICIOS_ACCION_ESTIMACION MM, \n"
                + "            POA.MOVOFICIOS MOVOF\n"
                + "        WHERE   \n"
                + "            MOVOF.TIPOMOV = ? \n"
                + "            AND MOVOF.STATUS = ?  \n"
                + "            AND EXTRACT(YEAR FROM MOVOF.FECHAELAB) = ? \n"
                + "            AND MM.OFICIO = MOVOF.OFICIO \n"
                + "    ) M \n"
                + "    WHERE  \n"
                + "    R.YEAR = ? \n"
                + "    AND M.RAMO = R.RAMO\n"
                + ")MODS,\n"
                + "    POA.MOVOFICIOS M,\n"
                + "    DGI.RAMOS R,\n"
                + "    DGI.DGI_USR US,\n"
                + "    POA.ESTATUS_MOV EM,\n"
                + "    DGI.TIPOFICIO TP\n"
                + "WHERE\n"
                + "    MODS.OFICIO = M.OFICIO\n"
                + "    AND TRIM(US.APP_LOGIN) = TRIM(M.APP_LOGIN)\n"
                + "    AND US.SYS_CLAVE = 1\n"
                + "    AND R.YEAR = MODS.YEAR\n"
                + "    AND R.RAMO = US.RAMO\n"
                + "    AND MODS.RAMO = ?\n"
                + "    AND EM.ESTATUS = M.STATUS\n"
                + "    AND TP.OFICIO (+)= M.OFICIO\n"
                + "    AND TP.STATUS = M.STATUS\n"
                + "ORDER BY \n"
                + "    M.OFICIO";
        return strSQL;
    }

    public String getSQLgetComentariosAutorizacion() {
        String strSQL = "SELECT APP_LOGIN, NVL(COMENTARIO,'N/A') COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUM_COMENTARIO\n"
                + "FROM POA.BITACORA_COMENTARIO\n"
                + "WHERE OFICIO = ? \n"
                + "ORDER BY NUM_COMENTARIO";
        return strSQL;
    }

    public String gerSQLInsertComentarioAut() {
        return "INSERT INTO POA.BITACORA_COMENTARIO VALUES(?,?,?,SYSDATE,\n"
                + "(SELECT NVL(MAX(NUM_COMENTARIO),0)+1 \n"
                + "FROM POA.BITACORA_COMENTARIO \n"
                + "WHERE OFICIO = ?))\n"
                + "";
    }

    public String gerSQLUpdateComentarioAut() {
        return "UPDATE POA.BITACORA_COMENTARIO \n"
                + "SET COMENTARIO = ?\n"
                + "WHERE OFICIO = ?\n"
                + "    AND APP_LOGIN = ?\n"
                + "    AND NUM_COMENTARIO = ?";
    }

    public String gerSQLEdicionComentario() {
        return "SELECT COMENTARIO\n"
                + "FROM POA.BITACORA_COMENTARIO \n"
                + "WHERE OFICIO = ?\n"
                + "AND NUM_COMENTARIO = ?";
    }

    public String gerSQLDeleteComentario() {
        return "DELETE FROM POA.BITACORA_COMENTARIO \n"
                + "WHERE OFICIO = ?\n"
                + "AND NUM_COMENTARIO = ?";
    }

    public String getUltimoComentario() {
        return "SELECT COMENTARIO  \n"
                + "    FROM POA.BITACORA_COMENTARIO  \n"
                + "    WHERE OFICIO = ? \n"
                + "    AND NUM_COMENTARIO =  \n"
                + "    ( \n"
                + "        SELECT MAX(NUM_COMENTARIO)  \n"
                + "        FROM POA.BITACORA_COMENTARIO  \n"
                + "        WHERE OFICIO = ? \n"
                + "    )";
    }

    public String getSQLsiUsuarioEvaluador() {
        return "SELECT CASE WHEN COUNT(1) > 0 THEN 'TRUE' ELSE 'FALSE' END EVALUADOR\n"
                + "FROM POA.USUARIO_EVALUADOR\n"
                + "WHERE APP_LOGIN = ?";
    }

    public String getSQLisMovimientoEvaluado() {
        return "SELECT \n"
                + "    CASE WHEN COUNT(1) > 0 \n"
                + "    THEN 'TRUE' ELSE 'FALSE' \n"
                + "    END EVALUADO\n"
                + "FROM POA.MOVOFICIOS \n"
                + "WHERE OFICIO = ? AND EVALUACION = 'S'";
    }

    public String getSQLisCaratulaEvaluada() {
        return "SELECT CASE WHEN COUNT(1) > 0\n"
                + "        THEN 'TRUE' ELSE 'FALSE' END EVALUADO\n"
                + "FROM POA.CARATULA CAR \n"
                + "WHERE YEAR = ? \n"
                + "AND ID_CARATULA = ? \n"
                + "AND EVALUACION = 'S'";
    }

    public String getSQLevaluarMovimiento() {
        return "UPDATE POA.MOVOFICIOS\n"
                + "SET EVALUACION = 'S'\n"
                + "WHERE OFICIO = ? ";
    }

    public String getSQLevaluarCaratula() {
        return "UPDATE POA.CARATULA \n"
                + "SET EVALUACION = 'S'\n"
                + "WHERE YEAR = ? \n"
                + "AND ID_CARATULA = ? \n";
    }

    public String getSQLinsertBitacoraEvaluacion() {
        return "INSERT INTO POA.BITACORA_EVALUACION VALUES(?,SYSDATE,?,?)";
    }

    public String getTiposModificaciones() {
        String strSQL = "SELECT * FROM POA.TIPO_MODIFICACION";
        return strSQL;
    }

    public String getConsultaFoliosReportesMonitoreoPresup(int op, int anio, int mI, int mF, String ramos) {
        String strSQL = "";
        if (op == 1) {
            //FOLIOS PARA *REPORTE CONSOLIDADO DE RECALENDARIZACIONES*
            strSQL = "SELECT\n"
                    + "	DISTINCT TR.OFICIO AS OFICIO \n"
                    + "FROM\n"
                    + "	POA.MOVOFICIOS MOVOF,\n"
                    + "	POA.MOVOFICIOS_ACCION_REQ TR,\n"
                    + "	DGI.RAMOS RAMOS,\n"
                    + "	S_POA_PROGRAMA PROGRAMAS,\n"
                    + "	DGI.DEPENDENCIA DEPTOS,\n"
                    + "	S_POA_VW_SP_PROY_ACT PROYACT,\n"
                    + "	S_POA_META METAS,\n"
                    + "	S_POA_ACCION ACCIONES,\n"
                    + "	SPP.RECALENDARIZACION REC,\n"
                    + "	DGI.PARTIDA PARTIDAS,\n"
                    + "	POA.ACCION_REQ ACCREQ,\n"
                    + "	POA.ESTATUS_MOV ESTATUS,\n"
                    + "	POA.HIST_ACCION_REQ HISTREQ\n"
                    + "\n"
                    + "WHERE\n"
                    + "	EXTRACT(YEAR FROM MOVOF.FECPPTO) = " + anio + " AND\n"
                    + "	EXTRACT(MONTH FROM MOVOF.FECPPTO) >=" + mI + " AND\n"
                    + "	EXTRACT(MONTH FROM MOVOF.FECPPTO) <=" + mF + " AND\n"
                    + "	MOVOF.TIPOMOV = 'C' AND\n"
                    + "	MOVOF.STATUS <> 'X' AND\n"
                    + "	TR.YEAR = EXTRACT(YEAR FROM MOVOF.FECHAELAB) AND\n"
                    + "	TR.OFICIO = MOVOF.OFICIO AND\n"
                    + "	TR.RAMO IN(" + ramos + ") AND\n"
                    + "	RAMOS.YEAR = TR.YEAR AND\n"
                    + "	RAMOS.RAMO = TR.RAMO AND\n"
                    + "	PROGRAMAS.YEAR = TR.YEAR AND\n"
                    + "	PROGRAMAS.PRG = TR.PRG AND\n"
                    + "	DEPTOS.YEAR = TR.YEAR AND\n"
                    + "	DEPTOS.RAMO = TR.RAMO AND\n"
                    + "	DEPTOS.DEPTO = TR.DEPTO AND\n"
                    + "	METAS.YEAR = TR.YEAR AND\n"
                    + "	METAS.RAMO = TR.RAMO AND\n"
                    + "	METAS.META = TR.META AND\n"
                    + "	PROYACT.YEAR = METAS.YEAR AND\n"
                    + "	PROYACT.TIPO_PROY = METAS.TIPO_PROY AND\n"
                    + "	PROYACT.PROY = METAS.PROY AND\n"
                    + "	ACCIONES.YEAR = TR.YEAR AND\n"
                    + "	ACCIONES.RAMO = TR.RAMO AND\n"
                    + "	ACCIONES.META = TR.META AND\n"
                    + "	ACCIONES.ACCION = TR.ACCION AND\n"
                    + "	REC.OFICIO (+)= TR.OFICIO AND\n"
                    + "	REC.RAMO (+)= TR.RAMO AND\n"
                    + "	REC.PRG (+)= TR.PRG AND\n"
                    + "	REC.DEPTO (+)= TR.DEPTO AND\n"
                    + "	REC.META (+)= TR.META AND\n"
                    + "	REC.ACCION (+)= TR.ACCION AND\n"
                    + "	REC.REQUERIMIENTO (+)= TR.REQUERIMIENTO AND\n"
                    + "	(REC.IMPTE > 0 OR REC.IMPTE IS NULL) AND\n"
                    + "	PARTIDAS.YEAR = TR.YEAR AND\n"
                    + "	PARTIDAS.PARTIDA = TR.PARTIDA AND\n"
                    + "	ACCREQ.YEAR = TR.YEAR AND\n"
                    + "	ACCREQ.RAMO = TR.RAMO AND\n"
                    + "	ACCREQ.PRG = TR.PRG AND\n"
                    + "	ACCREQ.DEPTO = TR.DEPTO AND\n"
                    + "	ACCREQ.META = TR.META AND\n"
                    + "	ACCREQ.ACCION = TR.ACCION AND\n"
                    + "	ACCREQ.REQUERIMIENTO = TR.REQUERIMIENTO AND\n"
                    + "	HISTREQ.OFICIO (+)= TR.OFICIO AND\n"
                    + "	HISTREQ.RAMO (+)= TR.RAMO AND\n"
                    + "	HISTREQ.PRG (+)= TR.PRG AND\n"
                    + "	HISTREQ.DEPTO (+)= TR.DEPTO AND\n"
                    + "	HISTREQ.META (+)= TR.META AND\n"
                    + "	HISTREQ.ACCION (+)= TR.ACCION AND\n"
                    + "	HISTREQ.REQUERIMIENTO (+)= TR.REQUERIMIENTO AND\n"
                    + "	ESTATUS.ESTATUS = MOVOF.STATUS\n"
                    + "GROUP BY\n"
                    + "	TR.YEAR,\n"
                    + "	TR.OFICIO,\n"
                    + "	TR.RAMO,\n"
                    + "	RAMOS.DESCR,\n"
                    + "	TR.PRG,\n"
                    + "	PROGRAMAS.DESCR,\n"
                    + "	TR.DEPTO,\n"
                    + "	DEPTOS.DESCR,\n"
                    + "	METAS.TIPO_PROY,\n"
                    + "	METAS.PROY,\n"
                    + "	PROYACT.DESCR,\n"
                    + "	TR.META,\n"
                    + "	METAS.DESCR,\n"
                    + "	TR.ACCION,\n"
                    + "	ACCIONES.DESCR,\n"
                    + "	TR.PARTIDA,\n"
                    + "	PARTIDAS.DESCR,\n"
                    + "	TR.REQUERIMIENTO,\n"
                    + "	TR.DESCR,\n"
                    + "	ACCREQ.COSTO_ANUAL,\n"
                    + "	CASE WHEN TR.ENE <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.ENE ELSE ACCREQ.ENE END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.FEB <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.FEB ELSE ACCREQ.FEB END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.MAR <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.MAR ELSE ACCREQ.MAR END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.ABR <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.ABR ELSE ACCREQ.ABR END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.MAY <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.MAY ELSE ACCREQ.MAY END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.JUN <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.JUN ELSE ACCREQ.JUN END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.JUL <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.JUL ELSE ACCREQ.JUL END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.AGO <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.AGO ELSE ACCREQ.AGO END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.SEP <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.SEP ELSE ACCREQ.SEP END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.OCT <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.OCT ELSE ACCREQ.OCT END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.NOV <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.NOV ELSE ACCREQ.NOV END) THEN 1 ELSE 0 END,\n"
                    + "	CASE WHEN TR.DIC <> (CASE WHEN MOVOF.STATUS = 'A' THEN HISTREQ.DIC ELSE ACCREQ.DIC END) THEN 1 ELSE 0 END,\n"
                    + "	MOVOF.FECHAELAB,\n"
                    + "	MOVOF.FECPPTO,\n"
                    + "	ESTATUS.DESCR,\n"
                    + "	TR.CONSIDERAR\n"
                    + "\n"
                    + "ORDER BY\n"
                    + "	TR.OFICIO";
        }

        if (op == 2) {
            //FOLIOS PARA Reporte de Transferencias
            strSQL = "SELECT\n"
                    + "	DISTINCT TR.OFICIO AS OFICIO\n"
                    + "\n"
                    + "FROM\n"
                    + "	(\n"
                    + "		SELECT\n"
                    + "			T.OFICIO,\n"
                    + "			T.CONSEC,\n"
                    + "			T.CONSIDERAR,\n"
                    + "			EM.DESCR AS DESCR_EM,\n"
                    + "			MV.FECPPTO,\n"
                    + "			(SELECT MAX(BM.FECHAAUT) FROM POA.BITMOVTOS BM WHERE BM.OFICIO = MV.OFICIO) AS FECHAAUT,\n"
                    + "			MV.FECHAELAB,\n"
                    + "			MV.TIPOMOV||'-R' AS TIPO_MOV,\n"
                    + "			T.RAMO,\n"
                    + "			R.DESCR AS DESCR_R,\n"
                    + "			T.PRG,\n"
                    + "			PR.DESCR AS DESCR_PR,T.DEPTO,\n"
                    + "			DEP.DESCR AS DESCR_DEP,\n"
                    + "			T.TIPO_PROY,\n"
                    + "			T.PROYECTO,\n"
                    + "			PA.DESCR AS DESCR_PA,\n"
                    + "			M.META,\n"
                    + "			DECODE(M.DESCR,NVL(MM.DESCR,M.DESCR),M.DESCR,MM.DESCR) AS DESCR_ME,\n"
                    + "			A.ACCION,\n"
                    + "			DECODE(A.DESCR,NVL(MA.DESCR,A.DESCR),A.DESCR,MA.DESCR) AS DESCR_MA,\n"
                    + "			(\n"
                    + "				SELECT\n"
                    + "					G.GRUPO\n"
                    + "				FROM\n"
                    + "					DGI.GRUPOS G\n"
                    + "				WHERE\n"
                    + "					G.YEAR = " + anio + " AND\n"
                    + "					G.GRUPO =\n"
                    + "								(\n"
                    + "									SELECT\n"
                    + "										SG.GRUPO\n"
                    + "									FROM\n"
                    + "										DGI.SUBGRUPOS SG\n"
                    + "									WHERE\n"
                    + "										SG.YEAR = " + anio + " AND\n"
                    + "										SG.SUBGRUPO =\n"
                    + "														(\n"
                    + "															SELECT\n"
                    + "																SSG.SUBGRUPO\n"
                    + "															FROM\n"
                    + "																DGI.SUBSUBGPO SSG\n"
                    + "															WHERE\n"
                    + "																SSG.YEAR = " + anio + " AND\n"
                    + "																SSG.SUBSUBGRUPO =\n"
                    + "																					(\n"
                    + "																						SELECT\n"
                    + "																							PAR.SUBSUBGPO\n"
                    + "																						FROM\n"
                    + "																							DGI.PARTIDA PAR\n"
                    + "																						WHERE\n"
                    + "																							PAR.YEAR =" + anio + " AND\n"
                    + "																							PAR.PARTIDA = T.PARTIDA\n"
                    + "																					)\n"
                    + "														)\n"
                    + "								)\n"
                    + "			AND YEAR = " + anio + ") AS GRUPO,\n"
                    + "			T.PARTIDA,\n"
                    + "			P.DESCR AS DESCR_P,\n"
                    + "			T.TIPO_GASTO,\n"
                    + "			T.FUENTE,\n"
                    + "			T.FONDO,\n"
                    + "			T.RECURSO,\n"
                    + "			RE.DESCR AS DESCR_RE,\n"
                    + "			T.IMPTE AS REDU,\n"
                    + "			0 AS AMPL,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),1,T.IMPTE,0) AS ENE,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),2,T.IMPTE,0) AS FEB,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),3,T.IMPTE,0) AS MAR,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),4,T.IMPTE,0) AS ABR,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),5,T.IMPTE,0) AS MAY,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),6,T.IMPTE,0) AS JUN,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),7,T.IMPTE,0) AS JUL,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),8,T.IMPTE,0) AS AGO,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),9,T.IMPTE,0) AS SEP,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),10,T.IMPTE,0) AS OCT,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),11,T.IMPTE,0) AS NOV,\n"
                    + "			DECODE(EXTRACT(MONTH FROM MV.FECPPTO),12,T.IMPTE,0) AS DIC\n"
                    + "		FROM\n"
                    + "			POA.MOVOFICIOS MV,\n"
                    + "			SPP.TRANSFERENCIAS T,\n"
                    + "			DGI.RAMOS R,\n"
                    + "			S_POA_PROGRAMA PR,\n"
                    + "			S_POA_VW_SP_PROY_ACT PA,\n"
                    + "			DGI.DEPENDENCIA DEP,\n"
                    + "			S_POA_ACCION A,\n"
                    + "			POA.MOVOFICIOS_ACCION MA,\n"
                    + "			S_POA_META M,\n"
                    + "			POA.MOVOFICIOS_META MM,\n"
                    + "			DGI.PARTIDA P,\n"
                    + "			S_POA_RECURSO RE,\n"
                    + "			POA.ESTATUS_MOV EM\n"
                    + "		WHERE\n"
                    + "			EXTRACT(YEAR FROM MV.FECPPTO) = " + anio + " \n"
                    + "			AND EXTRACT(MONTH FROM MV.FECPPTO) BETWEEN " + mI + " AND " + mF + " \n"
                    + "			AND MV.TIPOMOV = 'T'\n"
                    + "			AND T.RAMO IN(" + ramos + ")\n"
                    + "			AND T.OFICIO = MV.OFICIO\n"
                    + "\n"
                    + "			AND R.YEAR = " + anio + " \n"
                    + "			AND R.RAMO = T.RAMO\n"
                    + "			AND PR.YEAR = " + anio + " \n"
                    + "			AND PR.PRG = T.PRG\n"
                    + "			AND PA.YEAR = " + anio + " \n"
                    + "			AND PA.TIPO_PROY = T.TIPO_PROY\n"
                    + "			AND PA.PROY = T.PROYECTO\n"
                    + "			AND DEP.YEAR = " + anio + " \n"
                    + "			AND DEP.RAMO = T.RAMO\n"
                    + "			AND DEP.DEPTO = T.DEPTO\n"
                    + "			AND M.YEAR (+)= " + anio + " \n"
                    + "			AND M.RAMO (+)= T.RAMO\n"
                    + "			AND M.META (+)= T.META\n"
                    + "			AND MM.YEAR (+)= " + anio + " \n"
                    + "			AND MM.OFICIO (+)= T.OFICIO\n"
                    + "			AND MM.RAMO (+)= T.RAMO\n"
                    + "			AND MM.META (+)= T.META\n"
                    + "			AND A.YEAR (+)= " + anio + " \n"
                    + "			AND A.RAMO (+)= T.RAMO\n"
                    + "			AND A.META (+)= T.META\n"
                    + "			AND A.ACCION (+)= T.ACCION\n"
                    + "			AND MA.YEAR (+)= " + anio + " \n"
                    + "			AND MA.OFICIO (+)= T.OFICIO\n"
                    + "			AND MA.RAMO (+)= T.RAMO\n"
                    + "			AND MA.META (+)= T.META\n"
                    + "			AND MA.ACCION (+)= T.ACCION\n"
                    + "			AND P.YEAR = " + anio + " \n"
                    + "			AND P.PARTIDA = T.PARTIDA\n"
                    + "			AND RE.YEAR = " + anio + " \n"
                    + "			AND RE.FUENTE = T.FUENTE\n"
                    + "			AND RE.FONDO = T.FONDO\n"
                    + "			AND RE.RECURSO = T.RECURSO\n"
                    + "			AND EM.ESTATUS = MV.STATUS\n"
                    + "\n"
                    + "		UNION ALL\n"
                    + "\n"
                    + "		SELECT\n"
                    + "			T.OFICIO,\n"
                    + "			T.CONSEC,\n"
                    + "			TF.CONSIDERAR,\n"
                    + "			EM.DESCR AS DESCR_EM,\n"
                    + "			MV.FECPPTO,\n"
                    + "			(SELECT MAX(BM.FECHAAUT) AS FECHAAUT FROM POA.BITMOVTOS BM WHERE BM.OFICIO = MV.OFICIO) AS FECHAAUT,\n"
                    + "            MV.FECHAELAB,\n"
                    + "			MV.TIPOMOV||'-A' AS TIPO_MOV,\n"
                    + "			T.RAMO,\n"
                    + "			R.DESCR AS DESCR_R,\n"
                    + "			T.PRG,\n"
                    + "			PR.DESCR AS DESCR_PR,\n"
                    + "			T.DEPTO,\n"
                    + "			DEP.DESCR AS DESCR_DEP,\n"
                    + "			T.TIPO_PROY,\n"
                    + "			T.PROYECTO,\n"
                    + "			PA.DESCR AS DESCR_PA,\n"
                    + "			DECODE(MM.NVA_CREACION,'S',MM.META,DECODE(MM.NVA_CREACION,NULL,M.META,MM.META)) AS META,\n"
                    + "			DECODE(MM.NVA_CREACION,'S',MM.DESCR,DECODE(MM.NVA_CREACION,NULL,M.DESCR,MM.DESCR)) AS DESCR_ME,\n"
                    + "			DECODE(MA.NVA_CREACION,'S',MA.ACCION,DECODE(MA.NVA_CREACION,NULL,A.ACCION,MA.ACCION)) AS ACCION,\n"
                    + "			DECODE(MA.NVA_CREACION,'S',MA.DESCR,DECODE(MA.NVA_CREACION,NULL,A.DESCR,MA.DESCR)) AS DESCR_MA,\n"
                    + "			(\n"
                    + "				SELECT\n"
                    + "					G.GRUPO\n"
                    + "				FROM\n"
                    + "					DGI.GRUPOS G\n"
                    + "				WHERE\n"
                    + "					G.GRUPO =\n"
                    + "								(\n"
                    + "									SELECT\n"
                    + "										SG.GRUPO\n"
                    + "									FROM\n"
                    + "										DGI.SUBGRUPOS SG\n"
                    + "									WHERE\n"
                    + "										SG.YEAR = " + anio + " AND\n"
                    + "										SG.SUBGRUPO =\n"
                    + "														(\n"
                    + "															SELECT\n"
                    + "																SSG.SUBGRUPO\n"
                    + "															FROM\n"
                    + "																DGI.SUBSUBGPO SSG\n"
                    + "															WHERE\n"
                    + "																SSG.YEAR = " + anio + " AND\n"
                    + "																SSG.SUBSUBGRUPO =\n"
                    + "																					(\n"
                    + "																						SELECT\n"
                    + "																							PAR.SUBSUBGPO\n"
                    + "																						FROM\n"
                    + "																							DGI.PARTIDA PAR\n"
                    + "																						WHERE\n"
                    + "																							PAR.YEAR = " + anio + " AND\n"
                    + "																							PAR.PARTIDA = T.PARTIDA\n"
                    + "																					)\n"
                    + "														)\n"
                    + "								)\n"
                    + "			AND YEAR =" + anio + ") AS GRUPO,\n"
                    + "			T.PARTIDA,\n"
                    + "			P.DESCR AS DESCR_P,\n"
                    + "			T.TIPO_GASTO,\n"
                    + "			T.FUENTE,\n"
                    + "			T.FONDO,\n"
                    + "			T.RECURSO,\n"
                    + "			RE.DESCR AS DESCR_RE,\n"
                    + "			0 AS REDU,\n"
                    + "			T.IMPTE AS AMPL,\n"
                    + "			MAR.ENE*MAR.COSTO_UNITARIO AS ENE,\n"
                    + "			MAR.FEB*MAR.COSTO_UNITARIO AS FEB,\n"
                    + "			MAR.MAR*MAR.COSTO_UNITARIO AS MAR,\n"
                    + "			MAR.ABR*MAR.COSTO_UNITARIO AS ABR,\n"
                    + "			MAR.MAY*MAR.COSTO_UNITARIO AS MAY,\n"
                    + "			MAR.JUN*MAR.COSTO_UNITARIO AS JUN,\n"
                    + "			MAR.JUL*MAR.COSTO_UNITARIO AS JUL,\n"
                    + "			MAR.AGO*MAR.COSTO_UNITARIO AS AGO,\n"
                    + "			MAR.SEP*MAR.COSTO_UNITARIO AS SEP,\n"
                    + "			MAR.OCT*MAR.COSTO_UNITARIO AS OCT,\n"
                    + "			MAR.NOV*MAR.COSTO_UNITARIO AS NOV,\n"
                    + "			MAR.DIC*MAR.COSTO_UNITARIO AS DIC\n"
                    + "		FROM\n"
                    + "			POA.MOVOFICIOS MV,\n"
                    + "			SPP.TRANSFREC T,\n"
                    + "            		SPP.TRANSFERENCIAS TF,\n"
                    + "			DGI.RAMOS R,\n"
                    + "			S_POA_PROGRAMA PR,\n"
                    + "			S_POA_VW_SP_PROY_ACT PA,\n"
                    + "			DGI.DEPENDENCIA DEP,\n"
                    + "			S_POA_META M,\n"
                    + "			POA.MOVOFICIOS_META MM,\n"
                    + "			S_POA_ACCION A,\n"
                    + "			POA.MOVOFICIOS_ACCION MA,\n"
                    + "			POA.MOVOFICIOS_ACCION_REQ MAR,\n"
                    + "			DGI.PARTIDA P,\n"
                    + "			S_POA_RECURSO RE,\n"
                    + "			POA.ESTATUS_MOV EM\n"
                    + "		WHERE\n"
                    + "			EXTRACT(YEAR FROM MV.FECPPTO) = " + anio + " \n"
                    + "			AND EXTRACT(MONTH FROM MV.FECPPTO) BETWEEN " + mI + " AND " + mF + " \n"
                    + "			AND MV.TIPOMOV = 'T'\n"
                    + "			AND T.RAMO IN(" + ramos + ")\n"
                    + "			AND T.OFICIO = MV.OFICIO\n"
                    + "           		AND TF.OFICIO = T.OFICIO\n"
                    + "            		AND TF.CONSEC = T.CONSEC\n"
                    + "			AND R.YEAR = " + anio + " \n"
                    + "			AND R.RAMO = T.RAMO\n"
                    + "			AND PR.YEAR = " + anio + " \n"
                    + "			AND PR.PRG = T.PRG\n"
                    + "			AND PA.YEAR = " + anio + " \n"
                    + "			AND PA.TIPO_PROY = T.TIPO_PROY\n"
                    + "			AND PA.PROY = T.PROYECTO\n"
                    + "			AND DEP.YEAR = " + anio + " \n"
                    + "			AND DEP.RAMO = T.RAMO\n"
                    + "			AND DEP.DEPTO = T.DEPTO\n"
                    + "			AND M.YEAR (+)= " + anio + " \n"
                    + "			AND M.RAMO (+)= T.RAMO\n"
                    + "			AND M.META (+)= T.META\n"
                    + "			AND MM.YEAR (+)= " + anio + " \n"
                    + "			AND MM.OFICIO (+)= T.OFICIO\n"
                    + "			AND MM.RAMO (+)= T.RAMO\n"
                    + "			AND MM.META (+)= T.META\n"
                    + "			AND A.YEAR (+)= " + anio + " \n"
                    + "			AND A.RAMO (+)= T.RAMO\n"
                    + "			AND A.META (+)= T.META\n"
                    + "			AND A.ACCION (+)= T.ACCION\n"
                    + "			AND MA.YEAR (+)= " + anio + " \n"
                    + "			AND MA.OFICIO (+)= T.OFICIO\n"
                    + "			AND MA.RAMO (+)= T.RAMO\n"
                    + "			AND MA.META (+)= T.META\n"
                    + "			AND MA.ACCION (+)= T.ACCION\n"
                    + "			AND MAR.YEAR = " + anio + " \n"
                    + "			AND MAR.OFICIO = T.OFICIO\n"
                    + "			AND MAR.RAMO = T.RAMO\n"
                    + "			AND MAR.META = T.META\n"
                    + "			AND MAR.ACCION = T.ACCION\n"
                    + "			AND MAR.REQUERIMIENTO = T.REQUERIMIENTO\n"
                    + "			AND P.YEAR =" + anio + " \n"
                    + "			AND P.PARTIDA = T.PARTIDA\n"
                    + "			AND RE.YEAR = " + anio + " \n"
                    + "			AND RE.FUENTE = T.FUENTE\n"
                    + "			AND RE.FONDO = T.FONDO\n"
                    + "			AND RE.RECURSO = T.RECURSO\n"
                    + "			AND EM.ESTATUS = MV.STATUS\n"
                    + "	) TR\n"
                    + "\n"
                    + "WHERE\n"
                    + "	EXTRACT(YEAR FROM TR.FECPPTO) =" + anio + " \n"
                    + "	AND TR.RAMO IN(" + ramos + ")\n"
                    + "	AND EXTRACT(MONTH FROM TR.FECPPTO) BETWEEN " + mI + " AND " + mF + "\n"
                    + "ORDER BY\n"
                    + "	TR.OFICIO";

        }
        if (op == 3) {
            // folios para Reporte de Ampliaciones\Reducciones
            strSQL = "SELECT\n"
                    + "	DISTINCT TR.OFICIO AS OFICIO\n"
                    + "FROM\n"
                    + "	POA.MOVOFICIOS MV,\n"
                    + "	SPP.AMPLIACIONES TR,\n"
                    + "	DGI.RAMOS R,\n"
                    + "	S_POA_PROGRAMA PR,\n"
                    + "	S_POA_VW_SP_PROY_ACT PA,\n"
                    + "	DGI.DEPENDENCIA DEP,\n"
                    + "	POA.MOVOFICIOS_META MM,\n"
                    + "	POA.MOVOFICIOS_ACCION MA,\n"
                    + "	S_POA_ACCION A,\n"
                    + "	S_POA_META M,\n"
                    + "	DGI.PARTIDA P,\n"
                    + "	S_POA_RECURSO RE,\n"
                    + "	POA.ESTATUS_MOV EM,\n"
                    + "	POA.MOVOFICIOS_ACCION_REQ MAR\n"
                    + "\n"
                    + "WHERE\n"
                    + "	EXTRACT(YEAR FROM MV.FECHAELAB) =" + anio + " \n"
                    + "	AND EXTRACT(MONTH FROM MV.FECPPTO) BETWEEN " + mI + " AND " + mF + " \n"
                    + "	AND MV.TIPOMOV = 'A'\n"
                    + "	AND TR.RAMO IN (" + ramos + ")\n"
                    + "	AND TR.OFICIO = MV.OFICIO\n"
                    + "	AND R.YEAR = " + anio + "\n"
                    + "	AND R.RAMO = TR.RAMO\n"
                    + "	AND PR.YEAR = " + anio + " \n"
                    + "	AND PR.PRG = TR.PRG\n"
                    + "	AND DEP.YEAR = " + anio + " \n"
                    + "	AND DEP.RAMO = TR.RAMO\n"
                    + "	AND DEP.DEPTO = TR.DEPTO\n"
                    + "	AND M.YEAR (+)= " + anio + " \n"
                    + "	AND M.RAMO (+)= TR.RAMO\n"
                    + "	AND M.META (+)= TR.META\n"
                    + "	AND MM.YEAR (+)= " + anio + " \n"
                    + "	AND MM.OFICIO (+)= TR.OFICIO\n"
                    + "	AND MM.RAMO (+)= TR.RAMO\n"
                    + "	AND MM.META (+)= TR.META\n"
                    + "	AND A.YEAR (+)= " + anio + " \n"
                    + "	AND A.RAMO (+)= TR.RAMO\n"
                    + "	AND A.META (+)= TR.META\n"
                    + "	AND A.ACCION (+)= TR.ACCION\n"
                    + "\n"
                    + "    AND MAR.YEAR (+)= " + anio + " \n"
                    + "    AND MAR.OFICIO (+)= TR.OFICIO\n"
                    + "    AND MAR.RAMO (+)= TR.RAMO\n"
                    + "	AND MAR.META (+)= TR.META\n"
                    + "	AND MAR.ACCION (+)= TR.ACCION\n"
                    + "    AND MAR.REQUERIMIENTO (+)= TR.REQUERIMIENTO\n"
                    + "\n"
                    + "	AND MA.YEAR (+)= " + anio + " \n"
                    + "	AND MA.OFICIO (+)= TR.OFICIO\n"
                    + "	AND MA.RAMO (+)= TR.RAMO\n"
                    + "	AND MA.META (+)= TR.META\n"
                    + "	AND MA.ACCION (+)= TR.ACCION\n"
                    + "	AND PA.YEAR = " + anio + " \n"
                    + "	AND PA.TIPO_PROY = TR.TIPO_PROY\n"
                    + "	AND PA.PROY = TR.PROYECTO\n"
                    + "	AND P.YEAR = " + anio + " \n"
                    + "	AND P.PARTIDA = TR.PARTIDA\n"
                    + "	AND RE.YEAR = " + anio + " \n"
                    + "	AND RE.FUENTE = TR.FUENTE\n"
                    + "	AND RE.FONDO = TR.FONDO\n"
                    + "	AND RE.RECURSO = TR.RECURSO\n"
                    + "	AND EM.ESTATUS = MV.STATUS\n"
                    + "	ORDER BY\n"
                    + "	TR.OFICIO";

        }

        return strSQL;
    }

    public String getSQLGetFoliosByYearRamoCaratulaTipoMovStatusMov(int year, String ramoId, long caratula, String tipoMov, String statusMov, String appLogin, boolean isNormativo) {
        String strSQL = "";

        if (caratula == -2) {
            strSQL += ""
                    + "SELECT\n"
                    + "	MOV.OFICIO\n"
                    + "FROM\n"
                    + "	POA.MOVOFICIOS MOV,\n"
                    + "	DGI.DGI_USR USR\n"
                    + "WHERE\n"
                    + "	EXTRACT(YEAR FROM MOV.FECHAELAB) = 2016 AND\n"
                    + "	MOV.TIPOMOV = '" + tipoMov + "' AND\n"
                    + "	MOV.STATUS = '" + statusMov + "' AND\n"
                    + "	TRIM(USR.APP_LOGIN) = TRIM(MOV.APP_LOGIN) AND\n"
                    + "	USR.RAMO = '" + ramoId + "' AND\n"
                    + "	NOT EXISTS (SELECT CARMOV.OFICIO FROM POA.CARATULA_MOVOFICIO CARMOV WHERE CARMOV.OFICIO = MOV.OFICIO)"
                    + "";
        } else {
            strSQL += ""
                    + "SELECT\n"
                    + "	MOV.OFICIO\n"
                    + "FROM\n"
                    + "	POA.CARATULA_MOVOFICIO CARMOV,\n"
                    + "	POA.MOVOFICIOS MOV\n"
                    + "WHERE\n"
                    + "	CARMOV.YEAR = " + year + " AND\n"
                    + "	CARMOV.RAMO = '" + ramoId + "' AND\n"
                    + "	CARMOV.ID_CARATULA = " + caratula + " AND\n"
                    + "	MOV.OFICIO = CARMOV.OFICIO AND\n"
                    + "	MOV.TIPOMOV = '" + tipoMov + "' AND\n"
                    + "	MOV.STATUS = '" + statusMov + "'\n"
                    + "ORDER BY\n"
                    + "	MOV.OFICIO"
                    + "";
        }

        return strSQL;

    }

    public String getSQLGetJustificacionesByFolio(int folio) {
        String strSQL = "";

        strSQL += ""
                + "SELECT\n"
                + "	JUST.ID_JUSTIFICACION,\n"
                + "	JUST.JUSTIFICACION\n"
                + "FROM\n"
                + "	POA.JUSTIFICACION JUST\n"
                + "WHERE\n"
                + "	OFICIO = " + folio + "\n"
                + "";

        return strSQL;

    }

    public String getSQLGetMovimientosByFolioJustificacion(int folio, long justificacion) {
        String strSQL = "";

        //1.- SPP.TRANSFERENCIAS
        //2.- SPP.TRANSFREC
        //3.- SPP.AMPLIACIONES
        //4.- POA.MOVOFICIOS_META
        //5.- POA.MOVOFICIOS_ACCION
        strSQL += ""
                + "SELECT\n"
                + "	ROWNUM MOVIMIENTO,\n"
                + "	MOVS.*\n"
                + "FROM\n"
                + "(\n"
                + "	SELECT\n"
                + "		1 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		MOV.TIPO_PROY||'-'||MOV.PROYECTO PROYECTO_ABR,\n"
                + "		MOV.TIPO_PROY,\n"
                + "		MOV.PROYECTO PROY,\n"
                + "		MOV.META,\n"
                + "		MOV.ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		MOV.PARTIDA,\n"
                + "		MOV.FUENTE||'.'||MOV.FONDO||'.'||MOV.RECURSO FFR_ABR,\n"
                + "		MOV.FUENTE,\n"
                + "		MOV.FONDO,\n"
                + "		MOV.RECURSO,\n"
                + "		MOV.IMPTE IMPORTE,\n"
                + "		MOV.CONSEC DIFERENCIADOR,\n"
                + "		(SELECT NVL(COUNT(1),0) ASIGNADO FROM SPP.TRANSFERENCIAS WHERE OFICIO = MOV.OFICIO AND ID_JUSTIFICACION = " + justificacion + " AND CONSEC = MOV.CONSEC) ASIGNADO\n"
                + "	FROM\n"
                + "		SPP.TRANSFERENCIAS MOV\n"
                + "	WHERE\n"
                + "		OFICIO = " + folio + "\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		2 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		MOV.TIPO_PROY||'-'||MOV.PROYECTO PROYECTO_ABR,\n"
                + "		MOV.TIPO_PROY,\n"
                + "		MOV.PROYECTO PROY,\n"
                + "		MOV.META,\n"
                + "		MOV.ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		MOV.PARTIDA,\n"
                + "		MOV.FUENTE||'.'||MOV.FONDO||'.'||MOV.RECURSO FFR_ABR,\n"
                + "		MOV.FUENTE,\n"
                + "		MOV.FONDO,\n"
                + "		MOV.RECURSO,\n"
                + "		MOV.IMPTE IMPORTE,\n"
                + "		MOV.REQUERIMIENTO DIFERENCIADOR,\n"
                + "		(SELECT NVL(COUNT(1),0) ASIGNADO FROM SPP.TRANSFREC WHERE OFICIO = MOV.OFICIO AND ID_JUSTIFICACION = " + justificacion + " AND REQUERIMIENTO = MOV.REQUERIMIENTO) ASIGNADO\n"
                + "	FROM\n"
                + "		SPP.TRANSFREC MOV\n"
                + "	WHERE\n"
                + "		OFICIO = " + folio + "\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		3 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		MOV.TIPO_PROY||'-'||MOV.PROYECTO PROYECTO_ABR,\n"
                + "		MOV.TIPO_PROY,\n"
                + "		MOV.PROYECTO PROY,\n"
                + "		MOV.META,\n"
                + "		MOV.ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		MOV.PARTIDA,\n"
                + "		MOV.FUENTE||'.'||MOV.FONDO||'.'||MOV.RECURSO FFR_ABR,\n"
                + "		MOV.FUENTE,\n"
                + "		MOV.FONDO,\n"
                + "		MOV.RECURSO,\n"
                + "		MOV.IMPTE IMPORTE,\n"
                + "		MOV.CONSEC DIFERENCIADOR,\n"
                + "		(SELECT NVL(COUNT(1),0) ASIGNADO FROM SPP.AMPLIACIONES WHERE OFICIO = MOV.OFICIO AND ID_JUSTIFICACION = " + justificacion + " AND CONSEC = MOV.CONSEC) ASIGNADO\n"
                + "	FROM\n"
                + "		SPP.AMPLIACIONES MOV\n"
                + "	WHERE\n"
                + "		OFICIO = " + folio + "\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		4 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		MOV.TIPO_PROY||'-'||MOV.PROY PROYECTO_ABR,\n"
                + "		MOV.TIPO_PROY,\n"
                + "		MOV.PROY,\n"
                + "		MOV.META,\n"
                + "		NULL ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		NULL PARTIDA,\n"
                + "		NULL FFR_ABR,\n"
                + "		NULL FUENTE,\n"
                + "		NULL FONDO,\n"
                + "		NULL RECURSO,\n"
                + "		0 IMPORTE,\n"
                + "		0 DIFERENCIADOR,\n"
                + "		(SELECT NVL(COUNT(1),0) ASIGNADO FROM POA.MOVOFICIOS_META WHERE OFICIO = MOV.OFICIO AND ID_JUSTIFICACION = " + justificacion + ") ASIGNADO\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_META MOV\n"
                + "	WHERE\n"
                + "		OFICIO = " + folio + "\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		5 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		CASE WHEN MOV.META < 0 THEN MOVMET.TIPO_PROY||'-'||MOVMET.PROY ELSE MET.TIPO_PROY||'-'||MET.PROY END PROYECTO_ABR,\n"
                + "		CASE WHEN MOV.META < 0 THEN MOVMET.TIPO_PROY ELSE MET.TIPO_PROY END TIPO_PROY,\n"
                + "		CASE WHEN MOV.META < 0 THEN MOVMET.PROY ELSE MET.PROY END PROY,\n"
                + "		MOV.META,\n"
                + "		MOV.ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		NULL PARTIDA,\n"
                + "		NULL FFR_ABR,\n"
                + "		NULL FUENTE,\n"
                + "		NULL FONDO,\n"
                + "		NULL RECURSO,\n"
                + "		0 IMPORTE,\n"
                + "		0 DIFERENCIADOR,\n"
                + "		(SELECT NVL(COUNT(1),0) ASIGNADO FROM POA.MOVOFICIOS_ACCION WHERE OFICIO = MOV.OFICIO AND ID_JUSTIFICACION = " + justificacion + ") ASIGNADO\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ACCION MOV,\n"
                + "		POA.MOVOFICIOS_META MOVMET,\n"
                + "		S_POA_META MET\n"
                + "	WHERE\n"
                + "		MOV.OFICIO = " + folio + "  AND\n"
                + "		MET.YEAR (+)= MOV.YEAR AND\n"
                + "		MET.RAMO (+)= MOV.RAMO AND\n"
                + "		MET.PRG (+)= MOV.PRG AND\n"
                + "		MET.META (+)= MOV.META AND\n"
                + "		MOVMET.YEAR (+)= MOV.YEAR AND\n"
                + "		MOVMET.OFICIO (+)= MOV.OFICIO AND\n"
                + "		MOVMET.RAMO (+)= MOV.RAMO AND\n"
                + "		MOVMET.PRG (+)= MOV.PRG AND\n"
                + "		MOVMET.META (+)= MOV.META\n"
                + ") MOVS"
                + "";

        return strSQL;

    }

    public String getSQLActualizaAsignacionMovimientos(int tabla, String asignado, long justificacion, int folio, String ramo, String programa, int proyecto, String tipoProy, int meta, int accion, String depto, String partida, String fuente, String fondo, String recurso, int diferenciador) {

        String strSQL = "";
        String id_justificacion = null;

        if (asignado.equalsIgnoreCase("S")) {
            id_justificacion = "" + justificacion;
        } else {
            id_justificacion = null;
        }

        //1.- SPP.TRANSFERENCIAS
        //2.- SPP.TRANSFREC
        //3.- SPP.AMPLIACIONES
        //4.- POA.MOVOFICIOS_META
        //5.- POA.MOVOFICIOS_ACCION
        switch (tabla) {
            case 1:
                strSQL += ""
                        + "UPDATE\n"
                        + "	SPP.TRANSFERENCIAS\n"
                        + "SET\n"
                        + "	ID_JUSTIFICACION = " + id_justificacion + "\n"
                        + "WHERE\n"
                        + "	OFICIO = " + folio + " AND\n"
                        + "	RAMO = '" + ramo + "' AND\n"
                        + "	PRG = '" + programa + "' AND\n"
                        + "	PROYECTO = " + proyecto + " AND\n"
                        + "	TIPO_PROY = '" + tipoProy + "' AND\n"
                        + "	META = " + meta + " AND\n"
                        + "	ACCION = " + accion + " AND\n"
                        + "	DEPTO = '" + depto + "' AND\n"
                        + "	PARTIDA = '" + partida + "' AND\n"
                        + "	FUENTE = '" + fuente + "' AND\n"
                        + "	FONDO = '" + fondo + "' AND\n"
                        + "	RECURSO = '" + recurso + "' AND\n"
                        + "	CONSEC = " + diferenciador + "\n"
                        + "";
                break;
            case 2:
                strSQL += ""
                        + "UPDATE\n"
                        + "	SPP.TRANSFREC\n"
                        + "SET\n"
                        + "	ID_JUSTIFICACION = " + id_justificacion + "\n"
                        + "WHERE\n"
                        + "	OFICIO = " + folio + " AND\n"
                        + "	RAMO = '" + ramo + "' AND\n"
                        + "	PRG = '" + programa + "' AND\n"
                        + "	PROYECTO = " + proyecto + " AND\n"
                        + "	TIPO_PROY = '" + tipoProy + "' AND\n"
                        + "	META = " + meta + " AND\n"
                        + "	ACCION = " + accion + " AND\n"
                        + "	DEPTO = '" + depto + "' AND\n"
                        + "	PARTIDA = '" + partida + "' AND\n"
                        + "	FUENTE = '" + fuente + "' AND\n"
                        + "	FONDO = '" + fondo + "' AND\n"
                        + "	RECURSO = '" + recurso + "' AND\n"
                        + "	REQUERIMIENTO = " + diferenciador + "\n"
                        + "";
                break;
            case 3:
                strSQL += ""
                        + "UPDATE\n"
                        + "	SPP.AMPLIACIONES\n"
                        + "SET\n"
                        + "	ID_JUSTIFICACION = " + id_justificacion + "\n"
                        + "WHERE\n"
                        + "	OFICIO = " + folio + " AND\n"
                        + "	RAMO = '" + ramo + "' AND\n"
                        + "	PRG = '" + programa + "' AND\n"
                        + "	PROYECTO = " + proyecto + " AND\n"
                        + "	TIPO_PROY = '" + tipoProy + "' AND\n"
                        + "	META = " + meta + " AND\n"
                        + "	ACCION = " + accion + " AND\n"
                        + "	DEPTO = '" + depto + "' AND\n"
                        + "	PARTIDA = '" + partida + "' AND\n"
                        + "	FUENTE = '" + fuente + "' AND\n"
                        + "	FONDO = '" + fondo + "' AND\n"
                        + "	RECURSO = '" + recurso + "' AND\n"
                        + "	CONSEC = " + diferenciador + "\n"
                        + "";
                break;
            case 4:
                strSQL += ""
                        + "UPDATE\n"
                        + "	POA.MOVOFICIOS_META\n"
                        + "SET\n"
                        + "	ID_JUSTIFICACION = " + id_justificacion + "\n"
                        + "WHERE\n"
                        + "	OFICIO = " + folio + " AND\n"
                        + "	RAMO = '" + ramo + "' AND\n"
                        + "	PRG = '" + programa + "' AND\n"
                        + "	PROY = " + proyecto + " AND\n"
                        + "	TIPO_PROY = '" + tipoProy + "' AND\n"
                        + "	META = " + meta + "\n"
                        + "";
                break;
            case 5:
                strSQL += ""
                        + "UPDATE\n"
                        + "	POA.MOVOFICIOS_ACCION\n"
                        + "SET\n"
                        + "	ID_JUSTIFICACION = " + id_justificacion + "\n"
                        + "WHERE\n"
                        + "	OFICIO = " + folio + " AND\n"
                        + "	RAMO = '" + ramo + "' AND\n"
                        + "	PRG = '" + programa + "' AND\n"
                        + "	META = " + meta + " AND\n"
                        + "	ACCION = " + accion + " AND\n"
                        + "	DEPTO = '" + depto + "'\n"
                        + "";
                break;

        }

        return strSQL;

    }

    public String getSQLGetJustificacionByFolioJustificacion(int folio, long justificacion) {
        String strSQL = "";

        strSQL += ""
                + "SELECT\n"
                + "	JUST.ID_JUSTIFICACION,\n"
                + "	JUST.JUSTIFICACION\n"
                + "FROM\n"
                + "	POA.JUSTIFICACION JUST\n"
                + "WHERE\n"
                + "	OFICIO = " + folio + "AND\n"
                + "	ID_JUSTIFICACION = " + justificacion + "\n"
                + "";

        return strSQL;

    }

    public String getSQLSaveJustificacion(int folio, long idJustificacion, String justificacion) {

        String strSQL = "";

        if (idJustificacion == -1) {
            strSQL += ""
                    + "INSERT INTO\n"
                    + "	POA.JUSTIFICACION(OFICIO,ID_JUSTIFICACION,JUSTIFICACION)\n"
                    + "VALUES\n"
                    + "	(\n"
                    + "		" + folio + ",\n"
                    + "		(SELECT\n"
                    + "			NVL(MAX(ID_JUSTIFICACION),0)+1\n"
                    + "		FROM\n"
                    + "			POA.JUSTIFICACION\n"
                    + "		WHERE\n"
                    + "			OFICIO = " + folio + "),\n"
                    + "		'" + justificacion + "'\n"
                    + "	)"
                    + "";
        } else {
            strSQL += ""
                    + "UPDATE\n"
                    + "	POA.JUSTIFICACION\n"
                    + "SET\n"
                    + "	JUSTIFICACION = '" + justificacion + "'\n"
                    + "WHERE\n"
                    + "	OFICIO = " + folio + " AND\n"
                    + "	ID_JUSTIFICACION = " + idJustificacion + " "
                    + "";
        }

        return strSQL;

    }

    public String getMetasMovoficiosAutorizadas() {
        return "SELECT DISTINCT\n"
                + "    META,\n"
                + "    DESCR \n"
                + "FROM \n"
                + "    POA.MOVOFICIOS_META MM,\n"
                + "    POA.MOVOFICIOS M\n"
                + "WHERE YEAR = ? \n"
                + "    AND MM.RAMO = ? \n"
                + "    AND MM.PRG = ? \n"
                + "    AND MM.PROY = ? \n"
                + "    AND MM.TIPO_PROY = ? \n"
                + "    AND M.OFICIO = MM.OFICIO\n"
                + "    AND M.STATUS = 'A'\n"
                + "ORDER BY MM.META";
    }

    public String getAccionesMovoficiosAutorizadas() {
        return "SELECT DISTINCT\n"
                + "    ACCION,\n"
                + "    DESCR \n"
                + "FROM \n"
                + "    POA.MOVOFICIOS_ACCION MM,\n"
                + "    POA.MOVOFICIOS M\n"
                + "WHERE YEAR = ? \n"
                + "    AND MM.RAMO = ? \n"
                + "    AND MM.PRG = ?\n"
                + "    AND MM.META = ?\n"
                + "    AND M.OFICIO = MM.OFICIO\n"
                + "    AND M.STATUS = 'A'\n"
                + "ORDER BY MM.ACCION";
    }

    public String getSQLGetMovimientosByFolio(int folio) {
        String strSQL = "";

        //1.- SPP.TRANSFERENCIAS
        //2.- SPP.TRANSFREC
        //3.- SPP.AMPLIACIONES
        //4.- POA.MOVOFICIOS_META
        //5.- POA.MOVOFICIOS_ACCION
        strSQL += ""
                + "SELECT\n"
                + "	ROWNUM MOVIMIENTO,\n"
                + "	MOVS.*\n"
                + "FROM\n"
                + "(\n"
                + "	SELECT\n"
                + "		1 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		MOV.TIPO_PROY||'-'||MOV.PROYECTO PROYECTO_ABR,\n"
                + "		MOV.TIPO_PROY,\n"
                + "		MOV.PROYECTO PROY,\n"
                + "		MOV.META,\n"
                + "		MOV.ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		MOV.PARTIDA,\n"
                + "		MOV.FUENTE||'.'||MOV.FONDO||'.'||MOV.RECURSO FFR_ABR,\n"
                + "		MOV.FUENTE,\n"
                + "		MOV.FONDO,\n"
                + "		MOV.RECURSO,\n"
                + "		MOV.IMPTE IMPORTE,\n"
                + "		MOV.CONSEC DIFERENCIADOR\n"
                + "	FROM\n"
                + "		SPP.TRANSFERENCIAS MOV\n"
                + "	WHERE\n"
                + "		OFICIO = " + folio + "\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		2 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		MOV.TIPO_PROY||'-'||MOV.PROYECTO PROYECTO_ABR,\n"
                + "		MOV.TIPO_PROY,\n"
                + "		MOV.PROYECTO PROY,\n"
                + "		MOV.META,\n"
                + "		MOV.ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		MOV.PARTIDA,\n"
                + "		MOV.FUENTE||'.'||MOV.FONDO||'.'||MOV.RECURSO FFR_ABR,\n"
                + "		MOV.FUENTE,\n"
                + "		MOV.FONDO,\n"
                + "		MOV.RECURSO,\n"
                + "		MOV.IMPTE IMPORTE,\n"
                + "		MOV.REQUERIMIENTO DIFERENCIADOR\n"
                + "	FROM\n"
                + "		SPP.TRANSFREC MOV\n"
                + "	WHERE\n"
                + "		OFICIO = " + folio + "\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		3 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		MOV.TIPO_PROY||'-'||MOV.PROYECTO PROYECTO_ABR,\n"
                + "		MOV.TIPO_PROY,\n"
                + "		MOV.PROYECTO PROY,\n"
                + "		MOV.META,\n"
                + "		MOV.ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		MOV.PARTIDA,\n"
                + "		MOV.FUENTE||'.'||MOV.FONDO||'.'||MOV.RECURSO FFR_ABR,\n"
                + "		MOV.FUENTE,\n"
                + "		MOV.FONDO,\n"
                + "		MOV.RECURSO,\n"
                + "		MOV.IMPTE IMPORTE,\n"
                + "		MOV.CONSEC DIFERENCIADOR\n"
                + "	FROM\n"
                + "		SPP.AMPLIACIONES MOV\n"
                + "	WHERE\n"
                + "		OFICIO = " + folio + "\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		4 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		MOV.TIPO_PROY||'-'||MOV.PROY PROYECTO_ABR,\n"
                + "		MOV.TIPO_PROY,\n"
                + "		MOV.PROY,\n"
                + "		MOV.META,\n"
                + "		NULL ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		NULL PARTIDA,\n"
                + "		NULL FFR_ABR,\n"
                + "		NULL FUENTE,\n"
                + "		NULL FONDO,\n"
                + "		NULL RECURSO,\n"
                + "		0 IMPORTE,\n"
                + "		0 DIFERENCIADOR\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_META MOV\n"
                + "	WHERE\n"
                + "		OFICIO = " + folio + "\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		5 TABLA,\n"
                + "		MOV.OFICIO,\n"
                + "		MOV.ID_JUSTIFICACION,\n"
                + "		MOV.RAMO,\n"
                + "		MOV.PRG,\n"
                + "		CASE WHEN MOV.META < 0 THEN MOVMET.TIPO_PROY||'-'||MOVMET.PROY ELSE MET.TIPO_PROY||'-'||MET.PROY END PROYECTO_ABR,\n"
                + "		CASE WHEN MOV.META < 0 THEN MOVMET.TIPO_PROY ELSE MET.TIPO_PROY END TIPO_PROY,\n"
                + "		CASE WHEN MOV.META < 0 THEN MOVMET.PROY ELSE MET.PROY END PROY,\n"
                + "		MOV.META,\n"
                + "		MOV.ACCION,\n"
                + "		MOV.DEPTO,\n"
                + "		NULL PARTIDA,\n"
                + "		NULL FFR_ABR,\n"
                + "		NULL FUENTE,\n"
                + "		NULL FONDO,\n"
                + "		NULL RECURSO,\n"
                + "		0 IMPORTE,\n"
                + "		0 DIFERENCIADOR\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ACCION MOV,\n"
                + "		POA.MOVOFICIOS_META MOVMET,\n"
                + "		S_POA_META MET\n"
                + "	WHERE\n"
                + "		MOV.OFICIO = " + folio + "  AND\n"
                + "		MET.YEAR (+)= MOV.YEAR AND\n"
                + "		MET.RAMO (+)= MOV.RAMO AND\n"
                + "		MET.PRG (+)= MOV.PRG AND\n"
                + "		MET.META (+)= MOV.META AND\n"
                + "		MOVMET.YEAR (+)= MOV.YEAR AND\n"
                + "		MOVMET.OFICIO (+)= MOV.OFICIO AND\n"
                + "		MOVMET.RAMO (+)= MOV.RAMO AND\n"
                + "		MOVMET.PRG (+)= MOV.PRG AND\n"
                + "		MOVMET.META (+)= MOV.META\n"
                + ") MOVS"
                + "";

        return strSQL;

    }

    public String getSQLIsValidaJustificacionesMovsActivo() {
        String strSQL = "SELECT PAR.VALIDA_JUSTIFICACIONES FROM DGI.PARAMETROS PAR  ";
        return strSQL;
    }

    public String getSqlIsLigadaIngreso(long caratula) {
        String strSQL = ""
                + "SELECT\n"
                + "	CASE WHEN NVL(COUNT(*),0) > 0 THEN 1 ELSE 0 END LIGADA\n"
                + "FROM\n"
                + "	POA.CARATULA_PPTO_ING CING\n"
                + "WHERE\n"
                + "	CING.ID_CARATULA = " + caratula + "\n"
                + "";
        return strSQL;
    }

    public String getSqlIsLigadaPresupuesto(long caratula) {
        String strSQL = ""
                + "SELECT\n"
                + "	CASE WHEN NVL(COUNT(*),0) > 0 THEN 1 ELSE 0 END LIGADA\n"
                + "FROM\n"
                + "	POA.CARATULA_MOVOFICIO CPPTO\n"
                + "WHERE\n"
                + "	CPPTO.ID_CARATULA = " + caratula + "\n"
                + "";
        return strSQL;
    }

    public String getSQLEliminarCaratula(long caratula) {
        String strSQL = ""
                + "DELETE FROM "
                + "	POA.CARATULA CAR "
                + "WHERE "
                + "	CAR.ID_CARATULA = '" + caratula + "' "
                + "";
        return strSQL;
    }

    public String getSQLEliminarEstatusIngresoCaratula(long caratula) {
        String strSQL = ""
                + "DELETE FROM "
                + "	POA.ING_MOD_STATUS CAR "
                + "WHERE "
                + "	CAR.ID_CARATULA = '" + caratula + "' "
                + "";
        return strSQL;
    }

    public String getSQLInsertaAvanceAccionesAvancePoa(int meta, String ramo, int year, int accion, int mes) {
        String strSQL = new String();
        strSQL = ""
                + "INSERT INTO\n"
                + "	DGI.POA_AVANCE_ACCION\n"
                + "	(\n"
                + "		META,\n"
                + "		RAMO,\n"
                + "		YEAR,\n"
                + "		ACCION,\n"
                + "		MES,\n"
                + "		REALIZADO,\n"
                + "		IMPT_ASIG_XMES,\n"
                + "		IMPT_EJER_XMES,\n"
                + "		OBSERVACIONES,\n"
                + "		VALOR\n"
                + "	)\n"
                + "	VALUES\n"
                + "	(\n"
                + "		" + meta + ",\n"
                + "		'" + ramo + "',\n"
                + "		" + year + ",\n"
                + "		" + accion + ",\n"
                + "		" + mes + ",\n"
                + "		NULL,\n"
                + "		NULL,\n"
                + "		NULL,\n"
                + "		NULL,\n"
                + "		NULL\n"
                + "	)"
                + "";
        return strSQL;
    }
               
    public String getSQLExistenMetasAccionesRequerimientosNuevaCreacionErroneas(int folio, String tipoOficio) {
        
        if(tipoOficio.equals(""))
            tipoOficio = "-1";
        
        String strSQL = ""
                + "SELECT\n"
                + "	DISTINCT 1\n"
                + "FROM\n"
                + "(\n"
                + "	SELECT\n"
                + "		1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_META M\n"
                + "	WHERE\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'S' AND\n"
                + "		M.META > 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_META M\n"
                + "	WHERE\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'N' AND\n"
                + "		M.META < 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ESTIMACION M\n"
                + "	WHERE\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'S' AND\n"
                + "		M.META > 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ESTIMACION M\n"
                + "	WHERE\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'N' AND\n"
                + "		M.META < 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ACCION M\n"
                + "	WHERE\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'S' AND\n"
                + "		M.ACCION > 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ACCION M\n"
                + "	WHERE\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'N' AND\n"
                + "		M.ACCION < 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ACCION_ESTIMACION M\n"
                + "	WHERE\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'S' AND\n"
                + "		M.ACCION > 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ACCION_ESTIMACION M\n"
                + "	WHERE\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'N' AND\n"
                + "		M.ACCION < 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ACCION_REQ M\n"
                + "	WHERE\n"
                + "		'-1' = '" + tipoOficio + "' AND\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'S' AND\n"
                + "		M.REQUERIMIENTO > 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		POA.MOVOFICIOS_ACCION_REQ M\n"
                + "	WHERE\n"
                + "		'-1' = '" + tipoOficio + "' AND\n"
                + "		M.OFICIO = " + folio + " AND\n"
                + "		M.NVA_CREACION = 'N' AND\n"
                + "		M.REQUERIMIENTO < 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		DGI.TIPOFICIO TPOF,\n"
                + "		DGI.OFICONS CONS,\n"
                + "		SPP.TRANSFREC TRANSF,\n"
                + "		POA.MOVOFICIOS_ACCION_REQ M\n"
                + "	WHERE\n"
                + "		TPOF.OFICIO = " + folio + " AND\n"
                + "		TPOF.STATUS <> 'A' AND\n"
                + "		TPOF.TIPO = '" + tipoOficio + "' AND	\n"
                + "		CONS.OFICIO = TPOF.OFICIO AND\n"
                + "		CONS.TIPO = TPOF.TIPO AND\n"
                + "		TRANSF.OFICIO = CONS.OFICIO AND\n"
                + "		TRANSF.CONSEC = CONS.CONSEC AND\n"
                + "		M.OFICIO = TRANSF.OFICIO AND\n"
                + "		M.REQUERIMIENTO = TRANSF.REQUERIMIENTO AND\n"
                + "		M.NVA_CREACION = 'S' AND\n"
                + "		M.REQUERIMIENTO > 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		DGI.TIPOFICIO TPOF,\n"
                + "		DGI.OFICONS CONS,\n"
                + "		SPP.TRANSFREC TRANSF,\n"
                + "		POA.MOVOFICIOS_ACCION_REQ M\n"
                + "	WHERE\n"
                + "		TPOF.OFICIO = " + folio + " AND\n"
                + "		TPOF.STATUS <> 'A' AND\n"
                + "		TPOF.TIPO = '" + tipoOficio + "' AND\n"
                + "		CONS.OFICIO = TPOF.OFICIO AND\n"
                + "		CONS.TIPO = TPOF.TIPO AND\n"
                + "		TRANSF.OFICIO = CONS.OFICIO AND\n"
                + "		TRANSF.CONSEC = CONS.CONSEC AND\n"
                + "		M.OFICIO = TRANSF.OFICIO AND\n"
                + "		M.REQUERIMIENTO = TRANSF.REQUERIMIENTO AND\n"
                + "		M.NVA_CREACION = 'N' AND\n"
                + "		M.REQUERIMIENTO < 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		DGI.TIPOFICIO TPOF,\n"
                + "		DGI.OFICONS CONS,\n"
                + "		SPP.AMPLIACIONES TRANSF,\n"
                + "		POA.MOVOFICIOS_ACCION_REQ M\n"
                + "	WHERE\n"
                + "		TPOF.OFICIO = " + folio + " AND\n"
                + "		TPOF.STATUS <> 'A' AND\n"
                + "		TPOF.TIPO = '" + tipoOficio + "' AND\n"
                + "		CONS.OFICIO = TPOF.OFICIO AND\n"
                + "		CONS.TIPO = TPOF.TIPO AND\n"
                + "		TRANSF.OFICIO = CONS.OFICIO AND\n"
                + "		TRANSF.CONSEC = CONS.CONSEC AND\n"
                + "		M.OFICIO = TRANSF.OFICIO AND\n"
                + "		M.REQUERIMIENTO = TRANSF.REQUERIMIENTO AND\n"
                + "		M.NVA_CREACION = 'S' AND\n"
                + "		M.REQUERIMIENTO > 0\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		DGI.TIPOFICIO TPOF,\n"
                + "		DGI.OFICONS CONS,\n"
                + "		SPP.AMPLIACIONES TRANSF,\n"
                + "		POA.MOVOFICIOS_ACCION_REQ M\n"
                + "	WHERE\n"
                + "		TPOF.OFICIO = " + folio + " AND\n"
                + "		TPOF.STATUS <> 'A' AND\n"
                + "		TPOF.TIPO = '" + tipoOficio + "' AND\n"
                + "		CONS.OFICIO = TPOF.OFICIO AND\n"
                + "		CONS.TIPO = TPOF.TIPO AND\n"
                + "		TRANSF.OFICIO = CONS.OFICIO AND\n"
                + "		TRANSF.CONSEC = CONS.CONSEC AND\n"
                + "		M.OFICIO = TRANSF.OFICIO AND\n"
                + "		M.REQUERIMIENTO = TRANSF.REQUERIMIENTO AND\n"
                + "		M.NVA_CREACION = 'N' AND\n"
                + "		M.REQUERIMIENTO < 0\n"
                + "\n"
                + ") MODIFICACIONES"
                + "";

        return strSQL;
    }

    public String getSQLExistenAmpliacionesTransferenciasAccionMetaErroneas(int folio, String tipoMov) {
        String strSQL = ""
                + "SELECT\n"
                + "   DISTINCT 1\n"
                + "FROM\n"
                + "(\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		SPP.AMPLIACIONES AMP\n"
                + "	WHERE\n"
                + "		'A' = '" + tipoMov + "' AND\n"
                + "		AMP.OFICIO = " + folio + " AND\n"
                + "		AMP.ACCION < 0 AND\n"
                + "		NOT EXISTS (\n"
                + "						SELECT\n"
                + "							DISTINCT 1\n"
                + "						FROM\n"
                + "							POA.MOVOFICIOS_ACCION MOVACC\n"
                + "						WHERE\n"
                + "							MOVACC.OFICIO = AMP.OFICIO AND\n"
                + "							MOVACC.RAMO = AMP.RAMO AND\n"
                + "							MOVACC.PRG = AMP.PRG AND\n"
                + "							MOVACC.META = AMP.META AND\n"
                + "							MOVACC.ACCION = AMP.ACCION AND\n"
                + "							MOVACC.DEPTO = AMP.DEPTO AND\n"
                + "							MOVACC.MPO = AMP.MUNICIPIO AND\n"
                + "							MOVACC.LOCALIDAD = AMP.DELEGACION\n"
                + "					)\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		SPP.TRANSFREC TRANS\n"
                + "	WHERE\n"
                + "		'T' = '" + tipoMov + "' AND\n"
                + "		TRANS.OFICIO = " + folio + " AND\n"
                + "		TRANS.META < 0 AND\n"
                + "		NOT EXISTS \n"
                + "		(\n"
                + "			SELECT\n"
                + "				DISTINCT 1\n"
                + "			FROM\n"
                + "				POA.MOVOFICIOS_META MOVMET\n"
                + "			WHERE\n"
                + "				MOVMET.OFICIO = TRANS.OFICIO AND\n"
                + "				MOVMET.RAMO = TRANS.RAMO AND\n"
                + "				MOVMET.PRG = TRANS.PRG AND\n"
                + "				MOVMET.META = TRANS.META AND\n"
                + "				MOVMET.FUNCION = TRANS.FUNCION AND\n"
                + "				MOVMET.SUBFUNCION = TRANS.SUBFUNCION AND\n"
                + "				MOVMET.FINALIDAD = TRANS.FINALIDAD\n"
                + "		)\n"
                + "	UNION ALL\n"
                + "	SELECT\n"
                + "		DISTINCT 1\n"
                + "	FROM\n"
                + "		SPP.TRANSFREC TRANS\n"
                + "	WHERE\n"
                + "		'T' = '" + tipoMov + "' AND\n"
                + "		TRANS.OFICIO =   " + folio + "   AND\n"
                + "		TRANS.ACCION < 0 AND\n"
                + "		NOT EXISTS \n"
                + "             (\n"
                + "                     SELECT\n"
                + "                         DISTINCT 1\n"
                + "			FROM\n"
                + "                         POA.MOVOFICIOS_ACCION MOVACC\n"
                + "			WHERE\n"
                + "                         MOVACC.OFICIO = TRANS.OFICIO AND\n"
                + "                         MOVACC.RAMO = TRANS.RAMO AND\n"
                + "                         MOVACC.PRG = TRANS.PRG AND\n"
                + "                         MOVACC.META = TRANS.META AND\n"
                + "                         MOVACC.ACCION = TRANS.ACCION AND\n"
                + "                         MOVACC.DEPTO = TRANS.DEPTO AND\n"
                + "                         MOVACC.MPO = TRANS.MUNICIPIO AND\n"
                + "			MOVACC.LOCALIDAD = TRANS.DELEGACION\n"
                + "             )\n"
                + ") ERRORES"
                + "";
        return strSQL;
    }

}
