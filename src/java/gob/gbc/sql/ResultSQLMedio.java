/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.sql;

import gob.gbc.bd.ConectaBD;
import gob.gbc.entidades.Accion;
import gob.gbc.entidades.AccionReq;
import gob.gbc.entidades.AmpliacionReduccionAccion;
import gob.gbc.entidades.AmpliacionReduccionAccionReq;
import gob.gbc.entidades.AmpliacionReduccionMeta;
import gob.gbc.entidades.AnioPresupuestal;
import gob.gbc.entidades.BitMovto;
import gob.gbc.entidades.Caratula;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.EntePublico;
import gob.gbc.entidades.EstatusMov;
import gob.gbc.entidades.Estimacion;
import gob.gbc.entidades.Finalidad;
import gob.gbc.entidades.FlujoFirmas;
import gob.gbc.entidades.FolioMonitoreoPresup;
import gob.gbc.entidades.Fuente;
import gob.gbc.entidades.Funcion;
import gob.gbc.entidades.Justificacion;
import gob.gbc.entidades.Linea;
import gob.gbc.entidades.Localidad;
import gob.gbc.entidades.MedidaMeta;
import gob.gbc.entidades.Meta;
import gob.gbc.entidades.MovOficioAccionEstimacion;
import gob.gbc.entidades.MovOficioEstimacion;
import gob.gbc.entidades.MovOficiosAccionReq;
import gob.gbc.entidades.Movimiento;
import gob.gbc.entidades.MovimientoContabilidad;
import gob.gbc.entidades.MovimientoFolio;
import gob.gbc.entidades.MovimientoOficioAccion;
import gob.gbc.entidades.MovimientoOficioMeta;
import gob.gbc.entidades.MovimientosAmpliacionReduccion;
import gob.gbc.entidades.MovimientosRecalendarizacion;
import gob.gbc.entidades.MovimientosReprogramacion;
import gob.gbc.entidades.MovimientosTransferencia;
import gob.gbc.entidades.MovoficioLigado;
import gob.gbc.entidades.Parametro;
import gob.gbc.entidades.Parametros;
import gob.gbc.entidades.Partida;
import gob.gbc.entidades.Plantilla;
import gob.gbc.entidades.Ppto;
import gob.gbc.entidades.ProcesoMomento;
import gob.gbc.entidades.ProcesoMomentoCFG;
import gob.gbc.entidades.Programa;
import gob.gbc.entidades.Proyeccion;
import gob.gbc.entidades.Proyecto;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.RecalendarizacionAccion;
import gob.gbc.entidades.RecalendarizacionAccionReq;
import gob.gbc.entidades.RecalendarizacionMeta;
import gob.gbc.entidades.RelacionLaboral;
import gob.gbc.entidades.ReprogramacionAccion;
import gob.gbc.entidades.ReprogramacionMeta;
import gob.gbc.entidades.Requerimiento;
import gob.gbc.entidades.Subfuncion;
import gob.gbc.entidades.TipoFlujo;
import gob.gbc.entidades.TipoModificacion;
import gob.gbc.entidades.TipoMovimiento;
import gob.gbc.entidades.TipoOficio;
import gob.gbc.entidades.TipoSesion;
import gob.gbc.entidades.Transferencia;
import gob.gbc.entidades.TransferenciaAccion;
import gob.gbc.entidades.TransferenciaAccionReq;
import gob.gbc.entidades.TransferenciaMeta;
import gob.gbc.entidades.Usuario;
import gob.gbc.estandar.ResultSQLEstandar;
import gob.gbc.util.Bitacora;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ugarcia
 */
public class ResultSQLMedio extends ResultSQLEstandar {

    private String strServer = "";
    private String strUbicacion = "";
    public Bitacora bitacora;
    protected ConectaBD conectaBD;
    public String errorBD = new String();
    public String tipoDependencia = new String();  

    ResultSQLMedio() throws Exception {

        conectaBD = new ConectaBD();
        bitacora = new Bitacora();
    }

    ResultSQLMedio(String tipoDependencia) throws Exception {
        this.tipoDependencia = tipoDependencia;
        conectaBD = new ConectaBD(tipoDependencia);
        bitacora = new Bitacora();
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
        conectaBD.setStrServer(getStrServer());
        conectaBD.setStrUbicacion(getStrUbicacion());
        return conectaBD.esProduccion();
    }

    public void resultSQLConecta(String strBd) {
        conectaBD.setStrServer(getStrServer());
        conectaBD.setStrUbicacion(getStrUbicacion());
        try {
            //conectaBD.conecta(strBd);
            conectaBD.conecta(strBd);
        } catch (Exception e) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, new Throwable());
            bitacora.grabaBitacora();
        }
    }

    @Override
    public void resultSQLDesconecta(/*Connection con*/) {
        try {

            desconectaDblink("SPP");
            desconectaDblink("ING");
            desconectaDblink("APPSWEB");

            //conectaBD.desconecta();
        } catch (Exception e) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, new Throwable());
            bitacora.grabaBitacora();
        } finally {
            try {
                conectaBD.desconecta();
            } catch (Exception ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
    }

    public String getResultSQLGetTipoCompromisoMeta(int year, String ramo, int meta) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String tipoCompromiso = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoCompromisoMeta(year, ramo, meta));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoCompromiso = rsResult.getString("BENEFICIADOS");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return tipoCompromiso;
    }

    public int getResultSQLContLineasACcion(int year, String ramoId, String programaId) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int valida = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountLineasAccion(year, ramoId, programaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valida = rsResult.getInt("LINEAS");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return valida;
    }

    public Proyecto getResultSQLProyectoByTipoProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProyecto) throws SQLException {
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        Proyecto proyecto = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProyectoByTipoProyecto(ramoId, programaId, proyectoId, year, tipoProyecto));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            proyecto = new Proyecto();
                            proyecto.setProyectoId(rsResult.getInt("PROY"));
                            proyecto.setProyecto(rsResult.getString("DESCR"));
                            proyecto.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            proyecto.setRfc(rsResult.getString("RFC"));
                            proyecto.setHomoclave(rsResult.getString("HOMOCLAVE"));
                            proyecto.setDepto_resp(rsResult.getString("DEPTO_RESP"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                rsResult.close();
            }
        }
        return proyecto;
    }

    public boolean getResultSQLActualizaProyectoByTipoProyecto(String rfc, String homoclave, String depto, String ramoId, String programaId,
            int proyectoId, int year, String tipoProyecto) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                if (tipoProyecto.equalsIgnoreCase("P")) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateProyectoByTipoProyecto(rfc, homoclave, depto, ramoId, programaId, proyectoId, year, tipoProyecto));
                } else if (tipoProyecto.equalsIgnoreCase("A")) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateProyectoByTipoProyecto(depto, ramoId, programaId, proyectoId, year, tipoProyecto));
                }
                conectaBD.transaccionCommit();//REVISADO
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    //bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public List<Linea> getResultLineaByTipoProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProyecto) throws SQLException {
        List<Linea> lineaList = new ArrayList<Linea>();
        Linea linea;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineasTipoProyecto(ramoId, programaId, proyectoId, year, tipoProyecto));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            linea = new Linea();
                            linea.setLineaId(rsResult.getString("LINEA"));
                            linea.setLinea(rsResult.getString("DESCR"));
                            lineaList.add(linea);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return lineaList;
    }

    public int getResultValidarPresXCodigoPrg(String ramos, int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarPresXCodigoPrg(ramos, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultGetValidarPresDetalleAccionString(String ramos, int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarPresDetalleAccionString(ramos, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultValidarReporteLineasEstrategicas(int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarReporteLineasEstrategicas(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public String getResultSQLgetDescrPartidaPlantilla(String partida, String relLaboral, int year) throws SQLException {
        String descrPlantilla = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetDescrPartidaPlantilla(partida, relLaboral, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descrPlantilla = rsResult.getString("REQ_DESCR");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return descrPlantilla;
    }

    public int getResultSLQisPartidaJustific(int year, String partida) throws SQLException {
        int maxPPTO = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisPartidaJustif(year, partida));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            maxPPTO = rsResult.getInt("JUSTIF_REQ");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                rsResult.close();
            }
        }
        return maxPPTO;
    }

    public int getResultSLQGetMaxPresupuestoIngreso(int year, String ramo, String concepto) throws SQLException {
        int maxPPTO = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMaxPresupuestoIngreso(year, ramo, concepto));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            maxPPTO = rsResult.getInt("MAX_PPTO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return maxPPTO;
    }

    public String getResultSLQGetObraMeta(int year, String ramo, int meta) throws SQLException {
        String obra = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetObraMeta(year, ramo, meta));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            obra = rsResult.getString("OBRA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return obra;
    }

    public List<Plantilla> getResultSQLgetPlantillaAsignadosAuto(int year, String ramo) throws SQLException {
        List<Plantilla> plantillaList = new ArrayList<Plantilla>();
        Plantilla plantilla;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAsignadosAuto(year, ramo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            plantilla = new Plantilla();
                            plantilla.setPrgId(rsResult.getString("PRG"));
                            plantilla.setDescePrg("PRG_D");
                            plantilla.setDepto(rsResult.getString("DEPTO"));
                            plantilla.setDescrDepto(rsResult.getString("DEPTO_D"));
                            plantilla.setDescrMeta((rsResult.getString("META_D")));
                            plantilla.setMeta(rsResult.getInt("META"));
                            plantilla.setAccion(rsResult.getInt("ACCION"));
                            plantilla.setDescrAccion(rsResult.getString("ACCION_D"));
                            plantilla.setAuto(rsResult.getInt("AUTO"));
                            plantillaList.add(plantilla);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return plantillaList;
    }

    public List<Plantilla> getResultSQLgetPlantillaAsignados(int year, String ramo) throws SQLException {
        List<Plantilla> plantillaList = new ArrayList<Plantilla>();
        Plantilla plantilla;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetPlantillaAsignada(year, ramo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            plantilla = new Plantilla();
                            plantilla.setPrgId(rsResult.getString("PRG"));
                            plantilla.setDescePrg("PRG_D");
                            plantilla.setDepto(rsResult.getString("DEPTO"));
                            plantilla.setDescrDepto(rsResult.getString("DEPTO_D"));
                            plantilla.setDescrMeta((rsResult.getString("META_D")));
                            plantilla.setMeta(rsResult.getInt("META"));
                            plantilla.setAccion(rsResult.getInt("ACCION"));
                            plantilla.setDescrAccion(rsResult.getString("ACCION_D"));
                            plantilla.setAuto(rsResult.getInt("AUTO"));
                            plantillaList.add(plantilla);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return plantillaList;
    }

    public boolean getResultSQLDeleteAsignado(int year, String ramo, String depto) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteAsignado(year, ramo, depto));
                //conectaBD.transaccionCommit();//revisado
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteAmpliacionesByFolio(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteAmpliacionesByFolio(folio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public List<Parametro> validarCierreEjercicioByYear(int year) throws Exception {
        List<Parametro> parametroList = new ArrayList<Parametro>();
        ResultSet rsResult = null;
        Parametro parametro;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarCierreEjercicioByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        parametro = new Parametro();
                        parametro.setYear_ant(rsResult.getInt("YEAR_ANT"));
                        parametro.setYear(rsResult.getInt("YEAR"));
                        parametro.setYear_nvo(rsResult.getInt("YEAR_NVO"));
                        parametroList.add(parametro);
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return parametroList;
    }

    public List<Localidad> getResultSQLCatalogoLocalidad(int year) throws SQLException {
        List<Localidad> localidadList = new ArrayList<Localidad>();
        Localidad localidad = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoLocalidad(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            localidad = new Localidad();
                            localidad.setMpo(rsResult.getString("MPO"));
                            localidad.setLocId(rsResult.getString("LOCALIDAD"));
                            localidad.setLocalidad(rsResult.getString("DESCR"));
                            localidadList.add(localidad);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return localidadList;
    }

    public List<MedidaMeta> getResultSQLCatalogoMedidaMeta(int year) throws SQLException {
        List<MedidaMeta> medidaMetaList = new ArrayList<MedidaMeta>();
        MedidaMeta medidaMeta = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoMedidaMeta(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            medidaMeta = new MedidaMeta();
                            medidaMeta.setMedida(rsResult.getInt("MEDIDA"));
                            medidaMeta.setDescr(rsResult.getString("DESCR"));
                            medidaMeta.setActivo(rsResult.getString("ACTIVO"));
                            medidaMeta.setYear(rsResult.getInt("YEAR"));
                            medidaMetaList.add(medidaMeta);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return medidaMetaList;
    }

    public Proyeccion getResultSQLgetProyeccion(String ramo, String programa, String partida, int year) throws Exception {
        ResultSet rsResult = null;
        Proyeccion proyeccion = new Proyeccion();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetProyeccion(ramo, programa, partida, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        proyeccion.setRamo(rsResult.getString("RAMO"));
                        proyeccion.setRamoDescr(rsResult.getString("RAMO_D"));
                        proyeccion.setPrograma(rsResult.getString("PRG"));
                        proyeccion.setProgramaDescr(rsResult.getString("PRG_D"));
                        proyeccion.setPartida(rsResult.getString("PARTIDA"));
                        proyeccion.setPartidaDescr(rsResult.getString("PAR_D"));
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return proyeccion;
    }

    public String getResultSQLgetTipoDeGastoByPartida(String partida, int year) throws Exception {
        String tipoGasto = new String();

        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetTipoGastoByPartida(partida, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        tipoGasto = rsResult.getString("TIPO_GASTO");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return tipoGasto;
    }

    public boolean getResultSQLgetCountTipoDeGastoByPartida(String partida, int year) throws Exception {
        int tipoGasto = 0;
        boolean res = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetCountTipoGastoByPartida());
                if (pstmt != null) {
                    pstmt.setString(1, partida);
                    pstmt.setInt(2, year);
                    pstmt.setInt(3, year);
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        tipoGasto = rsResult.getInt("TIPO_GASTO");
                    }
                }
                if (tipoGasto > 0) {
                    res = true;
                } else {
                    res = false;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return res;
    }

    public String getResultSQLgetTipoDeGastoByPartidaPlantilla(String partida, int year) throws Exception {
        String tipoGasto = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetTipoGastoPlantilla(partida, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        tipoGasto = rsResult.getString("TIPO_GASTO");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return tipoGasto;
    }

    public int getResultSQLgetLineaAccionMeta(int year, String ramo, String programa, String linea) throws Exception {
        int contL = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCountLineaAccionMeta(year, ramo, programa, linea));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        contL = rsResult.getInt("CONT_L");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return contL;
    }

    public int getResultSQLgetLineaAccionAccion(int year, String ramo, String programa, String linea) throws Exception {
        int contL = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCountLineaAccionAccion(year, ramo, programa, linea));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        contL = rsResult.getInt("CONT_L");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return contL;
    }

    public int getResultValidarReportePptoCalAnualCodigo(String ramos, int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarReportePptoCalAnualCodigo(ramos, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultValidarReportePptoCalAnualRamoPartida(String ramos, int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarReportePptoCalAnualRamoPartida(ramos, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultValidarReportePptoCalAnualRamoProgramaPartida(String ramos, int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarReportePptoCalAnualRamoProgramaPartida(ramos, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultValidarReportePptoAnualRamoGrupo(String ramos, int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarReportePptoAnualRamoGrupo(ramos, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultValidarReportePptoAnualRamoProgramaGrupo(String ramos, int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarReportePptoAnualRamoProgramaGrupo(ramos, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultValidarReportePptoAnualCodigo(String ramos, int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarReportePptoAnualCodigo(ramos, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultValidarReporteComparativoPresupuesto(String ramos, int year, String queryPartidas) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarReporteComparativoPresupuesto(ramos, year, queryPartidas));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public double[] getResultSQLGetAnioPresupuestalValidacion(String ramo, String depto,
            int meta, int accion, String partida, String fuente, String fondo, String recurso) throws SQLException {
        double anioPresup[] = new double[12];
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSLQGetAnioPresupuestalValidacion(ramo, depto,
                        meta, accion, partida, fuente, fondo, recurso));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            anioPresup[0] = rsResult.getInt("ene");
                            anioPresup[1] = rsResult.getInt("feb");
                            anioPresup[2] = rsResult.getInt("mar");
                            anioPresup[3] = rsResult.getInt("abr");
                            anioPresup[4] = rsResult.getInt("may");
                            anioPresup[5] = rsResult.getInt("jun");
                            anioPresup[6] = rsResult.getInt("jul");
                            anioPresup[7] = rsResult.getInt("ago");
                            anioPresup[8] = rsResult.getInt("sep");
                            anioPresup[9] = rsResult.getInt("oct");
                            anioPresup[10] = rsResult.getInt("nov");
                            anioPresup[11] = rsResult.getInt("dic");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return anioPresup;
    }

    public List<AnioPresupuestal> getSQLgetPPTOvalidacion(String ramo, String depto,
            int meta, int accion, String partida, String fuente, String fondo, String recurso) throws SQLException {
        List<AnioPresupuestal> importeList = new ArrayList<AnioPresupuestal>();
        AnioPresupuestal anioPres;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetPPTOvalidacion(ramo, depto,
                        meta, accion, partida, fuente, fondo, recurso));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            anioPres = new AnioPresupuestal();
                            anioPres.setAsignadoMes(rsResult.getInt("ASIGNADO"));
                            importeList.add(anioPres);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return importeList;
    }

    public double[] getSQLgetAsignadosMesesRequerimiento(int year, String ramo,
            String programa, String depto, int meta, int accion, int req) throws SQLException {
        double meses[] = new double[12];
        BigDecimal costoUnitario = new BigDecimal(0.0);
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAsignadoAnterior(year, ramo, programa, depto, meta, accion, req));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            costoUnitario = new BigDecimal(rsResult.getDouble("COSTO_UNITARIO"));
                            meses[0] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("ENE"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[1] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("FEB"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[2] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("MAR"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[3] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("ABR"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[4] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("MAY"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[5] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("JUN"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[6] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("JUL"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[7] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("AGO"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[8] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("SEP"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[9] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("OCT"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[10] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("NOV"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            meses[11] = costoUnitario.multiply(new BigDecimal(rsResult.getDouble("DIC"))).setScale(2, RoundingMode.HALF_UP).doubleValue();
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return meses;
    }

    public boolean getResultSQLisParaestatal() throws Exception {
        String paraestatal = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisParaestatal());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        paraestatal = rsResult.getString("PARAESTATAL");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        if (paraestatal.equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String desconectaDblink(String cadena) {
        try {
            //if (this.getResultSQLisParaestatal()) {
            String resultado = null;
            CallableStatement cstmt = null;
            QuerysBD query = new QuerysBD();
            if (getConectaBD() != null) {
                try {
                    cstmt = getConectaBD().prepareCall(query.getSQLDesconectaDblink());
                    if (cstmt != null) {
                        cstmt.setString(1, cadena);
                        cstmt.execute();
                    }
                } catch (Exception ex) {
                    return "Ocurrio un error al cerrar las conexiones";
                }
            }
            /*return "";
             }*/
        } catch (Exception sqle) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sqle, new Throwable());
            bitacora.grabaBitacora();
        }
        return "";
    }

    public boolean getInsertaCodigosFaltantes(int iYear, String sRamo, String sOrigen, boolean bParaestatal) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean bError = false;
        int iExito;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLInsertaCodigosPlantilla(iYear, sRamo, sOrigen, bParaestatal));
                iExito = pstmt.executeUpdate();
                //if (iExito >= 0) {
                bError = true;
                //}
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return bError;
    }

    public boolean getResultSQLInsertPPTOFaltantes(int year, String ramo, String sOrigen, boolean bParaestatal) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertaPPTOFaltantes(year, ramo, sOrigen, bParaestatal));
                resultado = true;
                conectaBD.transaccionCommit();// revisado
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            } finally {
                return resultado;
            }
        }
        return resultado;
    }

    public String getUpdatePPTO(int year, String ramo, String sOrigen, boolean bParaestatal, boolean bSuma) throws SQLException {
        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();
        boolean bError = false;
        if (conectaBD != null) {
            PreparedStatement clstm = null;
            try {
                bError = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdatePPTO(year, ramo, sOrigen, bParaestatal, bSuma));
            } catch (SQLException sqle) {
                try {
                    conectaBD.transaccionRollback();
                    resultado = false;
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(sqle, new Throwable());
                    bitacora.grabaBitacora();
                    mensaje = sqle.getMessage();
                } catch (Exception ex) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(ex, new Throwable());
                    bitacora.grabaBitacora();
                    resultado = false;
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            } finally {
                // clstm.close();
            }
        }
        if (bError) {
            mensaje = "exito";
        }
        return mensaje;
    }

    public int getResultSQLcountFuenteFinanciamianto(int year, String fuente, String fondo, String recurso) throws SQLException {
        int fuenteFin = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountFuenteFinanciamiento(year, fuente, fondo, recurso));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fuenteFin = rsResult.getInt("CUENTA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return fuenteFin;
    }

    public int getResultSQLcountPresupPlantillaCentral(int year, String origen) throws SQLException {
        int presup = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountPresupPlantillaCentral(year, origen));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            presup = rsResult.getInt("CUENTA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return presup;
    }

    public String getRestulSQLValidaCarga(int year, String ramo, String sOrigen, boolean bParaestatal) throws SQLException {
        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            CallableStatement clstm = null;
            int iCarga = 0;
            if (bParaestatal) {
                iCarga = 1;
            } else {
                iCarga = 0;
            }
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLGetProcedureValidaCarga(year, ramo, sOrigen, iCarga));
                if (clstm != null) {
                    clstm.executeQuery();
                }
            } catch (SQLException sqle) {
                try {
                    conectaBD.transaccionRollback();
                    resultado = false;
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(sqle, new Throwable());
                    bitacora.grabaBitacora();
                    mensaje = sqle.getMessage();
                } catch (Exception ex) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(ex, new Throwable());
                    bitacora.grabaBitacora();
                    resultado = false;
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            } finally {
                clstm.close();
            }
        }
        if (resultado) {
            mensaje = "exito";
        }
        return mensaje;
    }

    public List<Programa> getResultSQLProgramasByRamoInList(String ramoInList, int year, String usuario) throws Exception {
        List<Programa> programaList = new ArrayList<Programa>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Programa programa = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProgramasByRamoInList(ramoInList, year, usuario));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = new Programa();
                            programa.setProgramaId(rsResult.getString("PRG"));
                            programa.setProgramaDesc(rsResult.getString("DESCR"));
                            programa.setUsuario(rsResult.getString("APP_LOGIN"));
                            programaList.add(programa);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return programaList;
    }

    public List<Proyecto> getResultSQLProyectosByRamPrgInList(String ramoInList, String programaInList, int year) throws SQLException {
        List<Proyecto> proyectoList = new ArrayList<Proyecto>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Proyecto proyecto = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProyectosByRamPrgInList(ramoInList, programaInList, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            proyecto = new Proyecto();
                            proyecto.setProyectoId(rsResult.getInt("PROY"));
                            proyecto.setProyecto(rsResult.getString("DESCR"));
                            proyecto.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            proyecto.setRfc(rsResult.getString("RFC"));
                            proyecto.setHomoclave(rsResult.getString("HOMOCLAVE"));
                            proyecto.setDepto_resp(rsResult.getString("DEPTO_RESP"));
                            proyecto.setObra(rsResult.getString("OBRA"));
                            proyectoList.add(proyecto);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return proyectoList;
    }

    public String getResultSQLcongelaMeta(int year) {
        String mensaje = new String();
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLcongelaMeta(year));
                if (resultado) {
                    mensaje = new String();
                } else {
                    mensaje = "Ocurrió un error al congelar las metas";
                }
            } catch (Exception sqle) {
                mensaje = sqle.getMessage();
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return mensaje;
    }

    public String getResultSQLcongelaAccion(int year) {
        String mensaje = new String();
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLcongelaAccion(year));
                if (resultado) {
                    mensaje = new String();
                } else {
                    mensaje = "Ocurrió un error al congelar las acciones";
                }
            } catch (Exception sqle) {
                mensaje = sqle.getMessage();
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return mensaje;
    }

    public String getResultSQLcongelaEstimacion(int year) {
        String mensaje = new String();
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLcongelaEstimacion(year));
                if (resultado) {
                    mensaje = new String();
                } else {
                    mensaje = "Ocurrió un error al congelar la calendarización de la meta";
                }
            } catch (Exception sqle) {
                mensaje = sqle.getMessage();
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return mensaje;
    }

    public String getResultSQLcongelaAccionEstimacion(int year) {
        String mensaje = new String();
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLcongelaAccionEstimacion(year));
                if (resultado) {
                    mensaje = new String();
                } else {
                    mensaje = "Ocurrió un error al congelar la calendarización de acciones";
                }
            } catch (Exception sqle) {
                mensaje = sqle.getMessage();
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return mensaje;
    }

    public String getResultSQLcongelaAccionReq(int year) {
        String mensaje = new String();
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLcongelaAccionReq(year));
                if (resultado) {
                    mensaje = new String();
                } else {
                    mensaje = "Ocurrió un error al congelar los requerimientos";
                }
            } catch (Exception sqle) {
                mensaje = sqle.getMessage();
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return mensaje;
    }

    public String getResultSQLcongelaProyecto(int year) {
        String mensaje = new String();
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLcongelaProyecto(year));
                if (resultado) {
                    mensaje = new String();
                } else {
                    mensaje = "Ocurrió un error al congelar los proyectos";
                }
            } catch (Exception sqle) {
                mensaje = sqle.getMessage();
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return mensaje;
    }

    public String getResultSQLcongelaPpto(int year) {
        String mensaje = new String();
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLcongelaPPTO(year));
                if (resultado) {
                    mensaje = new String();
                } else {
                    mensaje = "Ocurrió un error al congelar el presupuesto";
                }
            } catch (Exception sqle) {
                mensaje = sqle.getMessage();
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return mensaje;
    }

    public int getResultSQLCountCongelaPresupuesto(int year) throws SQLException {
        int presup = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getCountCongelaPresupuesto(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            presup = rsResult.getInt("TOTAL");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return presup;
    }

    public Usuario getResultGetInfoUsuario(String appLogin, int year) throws SQLException {
        Usuario usuario = new Usuario();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLInfoUsuarios());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin.trim());
                    pstmt.setInt(2, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            usuario.setAppLogin(rsResult.getString("APP_LOGIN"));
                            usuario.setNombre(rsResult.getString("NOMBRE"));
                            usuario.setApPaterno(rsResult.getString("AP_PATER"));
                            usuario.setApMaterno(rsResult.getString("AP_MATER"));
                            usuario.setRamo(rsResult.getString("RAMO"));
                            usuario.setRamoDescr(rsResult.getString("RAMO_DESCR"));
                            usuario.setDepto(rsResult.getString("DEPTO"));
                            usuario.setDeptoDescr(rsResult.getString("DEPTO_DESCR"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return usuario;
    }

    public List<Ramo> getResultGetRamoByUsuario(int year, String appLogin) throws SQLException {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        Ramo ramo = new Ramo();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamosByUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ramo = new Ramo();
                            ramo.setRamo(rsResult.getString("RAMO"));
                            ramo.setRamoDescr(rsResult.getString("DESCR"));
                            ramoList.add(ramo);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return ramoList;
    }

    public List<Ramo> getResultGetRamoTransitorioByUsuario(int year, String appLogin, String transitorio) throws SQLException {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        Ramo ramo = new Ramo();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamosTransitorioByUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, transitorio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ramo = new Ramo();
                            ramo.setRamo(rsResult.getString("RAMO"));
                            ramo.setRamoDescr(rsResult.getString("DESCR"));
                            ramoList.add(ramo);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return ramoList;
    }

    public List<Programa> getResultGetProgramaByRamoUsuario(int year, String appLogin, String ramo) throws SQLException {
        List<Programa> programaList = new ArrayList<Programa>();
        Programa programa;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProgramaByRamoUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = new Programa();
                            programa.setProgramaId(rsResult.getString("PRG"));
                            programa.setProgramaDesc(rsResult.getString("DESCR"));
                            programaList.add(programa);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return programaList;
    }

    public List<Proyecto> getResultGetProyectoByProyectoUsuario(int year, String appLogin, String ramo, String programa) throws SQLException {
        List<Proyecto> proyectoList = new ArrayList<Proyecto>();
        Proyecto proyecto;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetProyectoByProgramaUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);
                    pstmt.setInt(5, year);
                    pstmt.setString(6, ramo);
                    pstmt.setString(7, programa);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            proyecto = new Proyecto();
                            proyecto.setProyId(rsResult.getString("PROY"));
                            proyecto.setProyecto(rsResult.getString("DESCR"));
                            proyecto.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            proyectoList.add(proyecto);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return proyectoList;
    }

    public List<Meta> getResultGetMetaByProyectoUsuario(int year, String appLogin, String ramo, String programa, int proy, String tipoProy) throws SQLException {
        List<Meta> metaList = new ArrayList<Meta>();
        Meta meta;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMetasByProyectoUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);
                    pstmt.setInt(5, proy);
                    pstmt.setString(6, tipoProy);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            meta = new Meta();
                            meta.setMetaId(rsResult.getInt("META"));
                            meta.setMeta(rsResult.getString("DESCR"));
                            metaList.add(meta);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return metaList;
    }

    public List<Meta> getResultGetMetaMovoficiosAutorizado(int year, String appLogin, String ramo, String programa, int proy, String tipoProy) throws SQLException {
        List<Meta> metaList = new ArrayList<Meta>();
        Meta meta;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getMetasMovoficiosAutorizadas());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, proy);
                    pstmt.setString(5, tipoProy);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            meta = new Meta();
                            meta.setMetaId(rsResult.getInt("META"));
                            meta.setMeta(rsResult.getString("DESCR"));
                            metaList.add(meta);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return metaList;
    }

    public List<Accion> getResultGetAccionMovoficiosAutorizado(int year, String appLogin, String ramo,
            String programa, int proy, String tipoProy, int meta) throws SQLException {
        List<Accion> accionList = new ArrayList<Accion>();
        Accion accion;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getAccionesMovoficiosAutorizadas());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accion = new Accion();
                            accion.setAccionId(rsResult.getInt("ACCION"));
                            accion.setAccion(rsResult.getString("DESCR"));
                            accionList.add(accion);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return accionList;
    }

    public List<Meta> getResultGetMetaByProyecto(int year, String ramo, String programa, int proy, String tipoProy) throws SQLException {
        List<Meta> metaList = new ArrayList<Meta>();
        Meta meta;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMetasByProyecto());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, proy);
                    pstmt.setString(5, tipoProy);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            meta = new Meta();
                            meta.setMetaId(rsResult.getInt("META"));
                            meta.setMeta(rsResult.getString("DESCR"));
                            metaList.add(meta);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return metaList;
    }

    public List<Accion> getResultGetAccionByMetaUsuario(int year, String appLogin, String ramo,
            String programa, int proy, String tipoProy, int meta) throws SQLException {
        List<Accion> accionList = new ArrayList<Accion>();
        Accion accion;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAccionesByMetaUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);
                    pstmt.setInt(5, proy);
                    pstmt.setString(6, tipoProy);
                    pstmt.setInt(7, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accion = new Accion();
                            accion.setAccionId(rsResult.getInt("ACCION"));
                            accion.setAccion(rsResult.getString("DESCR"));
                            accionList.add(accion);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return accionList;
    }

    public List<Accion> getResultGetAccionByMeta(int year, String ramo,
            String programa, int meta) throws SQLException {
        List<Accion> accionList = new ArrayList<Accion>();
        Accion accion;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAccionesByMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accion = new Accion();
                            accion.setAccionId(rsResult.getInt("ACCION"));
                            accion.setAccion(rsResult.getString("DESCR"));
                            accionList.add(accion);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return accionList;
    }

    public List<Partida> getResultGetPartidaByAccionUsuario(int year, String appLogin, String ramo,
            String programa, int proy, String tipoProy, int meta, int accion) throws SQLException {
        List<Partida> partidaList = new ArrayList<Partida>();
        Partida partida;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPartidaByAccionUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);
                    pstmt.setInt(5, proy);
                    pstmt.setString(6, tipoProy);
                    pstmt.setInt(7, meta);
                    pstmt.setInt(8, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            partida = new Partida();
                            partida.setPartidaId(rsResult.getString("PARTIDA"));
                            partida.setPartida(rsResult.getString("DESCR"));
                            partidaList.add(partida);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return partidaList;
    }

    public List<Partida> getResultGetPartidaByAccionAmplRed(int year, String appLogin, String ramo,
            String programa, int proy, String tipoProy, int meta, int accion) throws SQLException {
        List<Partida> partidaList = new ArrayList<Partida>();
        Partida partida;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPartidaByAccionAmplRed());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);
                    pstmt.setInt(5, proy);
                    pstmt.setString(6, tipoProy);
                    pstmt.setInt(7, meta);
                    pstmt.setInt(8, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            partida = new Partida();
                            partida.setPartidaId(rsResult.getString("PARTIDA"));
                            partida.setPartida(rsResult.getString("DESCR"));
                            partidaList.add(partida);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return partidaList;
    }

    public List<RelacionLaboral> getResultGetRelLaboralByPartidaUsuario(int year, String appLogin, String ramo,
            String programa, int proy, String tipoProy, int meta, int accion, String partida) throws SQLException {
        List<RelacionLaboral> relLaboralList = new ArrayList<RelacionLaboral>();
        RelacionLaboral relLaboral;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRelLaboralByPartidaUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);
                    pstmt.setInt(5, proy);
                    pstmt.setString(6, tipoProy);
                    pstmt.setInt(7, meta);
                    pstmt.setInt(8, accion);
                    pstmt.setString(9, partida);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            relLaboral = new RelacionLaboral();
                            relLaboral.setRelacionLabId(rsResult.getString("REL_LABORAL"));
                            relLaboral.setRelacionLab(rsResult.getString("DESCR"));
                            relLaboralList.add(relLaboral);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return relLaboralList;
    }

    public List<Fuente> getResultGetFuenteFinanciamientoByPartidaUsuario(int year, String appLogin, String ramo,
            String programa, int proy, String tipoProy, int meta, int accion, String partida) throws SQLException {
        List<Fuente> fuenteList = new ArrayList<Fuente>();
        Fuente fuenteFin;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFuenteFinanciamientoByPartidaUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);
                    pstmt.setInt(5, proy);
                    pstmt.setString(6, tipoProy);
                    pstmt.setInt(7, meta);
                    pstmt.setInt(8, accion);
                    pstmt.setString(9, partida);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fuenteFin = new Fuente();
                            fuenteFin.setFuente(rsResult.getString("FUENTE"));
                            fuenteFin.setDescr(rsResult.getString("DESCR"));
                            fuenteList.add(fuenteFin);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return fuenteList;
    }

    public List<Fuente> getResultGetFuenteFinanciamientoByRelLaboralUsuario(int year, String appLogin, String ramo,
            String programa, int proy, String tipoProy, int meta, int accion, String partida, String relLaboral) throws SQLException {
        List<Fuente> fuenteList = new ArrayList<Fuente>();
        Fuente fuenteFin;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFuenteFinanciamientoByRelLaboralUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);
                    pstmt.setInt(5, proy);
                    pstmt.setString(6, tipoProy);
                    pstmt.setInt(7, meta);
                    pstmt.setInt(8, accion);
                    pstmt.setString(9, partida);
                    pstmt.setString(10, relLaboral);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fuenteFin = new Fuente();
                            fuenteFin.setFuente(rsResult.getString("FUENTE"));
                            fuenteFin.setDescr(rsResult.getString("DESCR"));
                            fuenteList.add(fuenteFin);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return fuenteList;
    }

    public List<Requerimiento> getResultGetRequerimientoByFuenteFinanciamientoUsuario(int year, String ramo,
            String programa, int meta, int accion, String partida, String relLaboral, String fuente, String fondo, String recurso) throws SQLException {
        List<Requerimiento> requList = new ArrayList<Requerimiento>();
        Requerimiento requerimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRequerimientoByFuenteFinanciamientoUsuario());
                if (pstmt != null) {
                    if (relLaboral.equals("-1")) {
                        relLaboral = "0";
                    }
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, meta);
                    pstmt.setInt(5, accion);
                    pstmt.setString(6, partida);
                    pstmt.setString(7, relLaboral);
                    pstmt.setString(8, fuente);
                    pstmt.setString(9, fondo);
                    pstmt.setString(10, recurso);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            requerimiento = new Requerimiento();
                            requerimiento.setReqId(rsResult.getInt("REQUERIMIENTO"));
                            requerimiento.setReq(rsResult.getString("DESCR"));
                            requList.add(requerimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return requList;
    }

    public List<RecalendarizacionAccionReq> getResultgetMovAccionRequerimiento(int folio) throws SQLException {
        List<RecalendarizacionAccionReq> recalAccReqList = new ArrayList<RecalendarizacionAccionReq>();
        MovOficiosAccionReq requerimiento;
        RecalendarizacionAccionReq recalAccReqL = new RecalendarizacionAccionReq();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionRequerimiento());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            recalAccReqL = new RecalendarizacionAccionReq();
                            requerimiento = new MovOficiosAccionReq();
                            requerimiento.setOficio(rsResult.getString("OFICIO"));
                            requerimiento.setYear(rsResult.getInt("YEAR"));
                            requerimiento.setRamo(rsResult.getString("RAMO"));
                            requerimiento.setPrograma(rsResult.getString("PRG"));
                            requerimiento.setDepto(rsResult.getString("DEPTO"));
                            requerimiento.setMeta(rsResult.getInt("META"));
                            requerimiento.setAccion(rsResult.getInt("ACCION"));
                            requerimiento.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                            requerimiento.setReqDescr(rsResult.getString("DESCR"));
                            requerimiento.setReqDescr(rsResult.getString("DESCR"));
                            requerimiento.setFuente(rsResult.getString("FUENTE"));
                            requerimiento.setFondo(rsResult.getString("FONDO"));
                            requerimiento.setRecurso(rsResult.getString("RECURSO"));
                            requerimiento.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            requerimiento.setPartida(rsResult.getString("PARTIDA"));
                            requerimiento.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            requerimiento.setCantidad(rsResult.getDouble("CANTIDAD"));
                            requerimiento.setCostoUnitario(rsResult.getDouble("COSTO_UNITARIO"));
                            requerimiento.setCostoAnual(rsResult.getDouble("COSTO_ANUAL"));
                            requerimiento.setEne(rsResult.getDouble("ENE"));
                            requerimiento.setFeb(rsResult.getDouble("FEB"));
                            requerimiento.setMar(rsResult.getDouble("MAR"));
                            requerimiento.setAbr(rsResult.getDouble("ABR"));
                            requerimiento.setMay(rsResult.getDouble("MAY"));
                            requerimiento.setJun(rsResult.getDouble("JUN"));
                            requerimiento.setJul(rsResult.getDouble("JUL"));
                            requerimiento.setAgo(rsResult.getDouble("AGO"));
                            requerimiento.setSep(rsResult.getDouble("SEP"));
                            requerimiento.setOct(rsResult.getDouble("OCT"));
                            requerimiento.setNov(rsResult.getDouble("NOV"));
                            requerimiento.setDic(rsResult.getDouble("DIC"));
                            requerimiento.setArticulo(rsResult.getString("ARTICULO"));
                            requerimiento.setDeptoDescr(this.getResultSQLgetDeptoDescripcion(requerimiento.getYear(),
                                    requerimiento.getRamo(), requerimiento.getDepto()));
                            recalAccReqL.setIdentificador(cont);
                            recalAccReqL.setMovAccionReq(requerimiento);
                            requerimiento.setConsiderar(rsResult.getString("CONSIDERAR"));
                            recalAccReqList.add(recalAccReqL);
                            cont++;
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return recalAccReqList;
    }

    public List<RecalendarizacionMeta> getResultSQLgetMovEstimacion(int folio) throws SQLException {
        List<MovOficioEstimacion> movOficioEstimacionList = new ArrayList<MovOficioEstimacion>();
        List<MovOficioEstimacion> movOficioEstimacionTempList = new ArrayList<MovOficioEstimacion>();
        List<RecalendarizacionMeta> recalenMetaList = new ArrayList<RecalendarizacionMeta>();
        RecalendarizacionMeta recalenMeta;
        MovOficioEstimacion movOficioEstimacion;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacion());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movOficioEstimacion = new MovOficioEstimacion();
                            movOficioEstimacion.setOficio(rsResult.getInt("OFICIO"));
                            movOficioEstimacion.setYear(rsResult.getInt("YEAR"));
                            movOficioEstimacion.setRamo(rsResult.getString("RAMO"));
                            movOficioEstimacion.setMeta(rsResult.getInt("META"));
                            movOficioEstimacionList.add(movOficioEstimacion);
                        }

                        for (MovOficioEstimacion movTemp : movOficioEstimacionList) {
                            identificador++;
                            recalenMeta = new RecalendarizacionMeta();
                            pstmt = null;
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacionCompleto());
                            movOficioEstimacionTempList = new ArrayList<MovOficioEstimacion>();
                            if (pstmt != null) {
                                pstmt.setInt(1, movTemp.getOficio());
                                pstmt.setInt(2, movTemp.getYear());
                                pstmt.setString(3, movTemp.getRamo());
                                pstmt.setInt(4, movTemp.getMeta());
                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    while (rsResult.next()) {
                                        movOficioEstimacion = new MovOficioEstimacion();
                                        movOficioEstimacion.setYear(rsResult.getInt("YEAR"));
                                        movOficioEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                        movOficioEstimacion.setRamo(rsResult.getString("RAMO"));
                                        movOficioEstimacion.setMeta(rsResult.getInt("META"));
                                        movOficioEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                        movOficioEstimacion.setValor(rsResult.getDouble("VALOR"));
                                        recalenMeta.setValAutorizado(rsResult.getString("VAL_AUTORIZADO"));
                                        movOficioEstimacion.setPrograma(this.getResultSQLgetProgramaByRamoMeta(movOficioEstimacion.getYear(),
                                                movOficioEstimacion.getRamo(), movOficioEstimacion.getMeta()));
                                        movOficioEstimacionTempList.add(movOficioEstimacion);
                                    }
                                    recalenMeta.setIdentificado(identificador);
                                    recalenMeta.setMovEstimacionList(movOficioEstimacionTempList);
                                    recalenMetaList.add(recalenMeta);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return recalenMetaList;
    }

    public List<RecalendarizacionAccion> getResultSQLgetMovAccionEstimacion(int folio) throws SQLException {
        List<MovOficioAccionEstimacion> movOficioAccionEstList = new ArrayList<MovOficioAccionEstimacion>();
        List<MovOficioAccionEstimacion> movOficioAccionEsTempList = new ArrayList<MovOficioAccionEstimacion>();
        List<RecalendarizacionAccion> recalenAccionList = new ArrayList<RecalendarizacionAccion>();
        RecalendarizacionAccion recalenAccion = new RecalendarizacionAccion();
        int identificado = 0;
        MovOficioAccionEstimacion movOficioAccionEst;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacion());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movOficioAccionEst = new MovOficioAccionEstimacion();
                            movOficioAccionEst.setOficio(rsResult.getInt("OFICIO"));
                            movOficioAccionEst.setYear(rsResult.getInt("YEAR"));
                            movOficioAccionEst.setRamo(rsResult.getString("RAMO"));
                            movOficioAccionEst.setMeta(rsResult.getInt("META"));
                            movOficioAccionEst.setAccion(rsResult.getInt("ACCION"));
                            movOficioAccionEstList.add(movOficioAccionEst);
                        }
                        for (MovOficioAccionEstimacion movTemp : movOficioAccionEstList) {
                            recalenAccion = new RecalendarizacionAccion();
                            pstmt = null;
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionCompleto());
                            movOficioAccionEsTempList = new ArrayList<MovOficioAccionEstimacion>();
                            if (pstmt != null) {
                                pstmt.setInt(1, movTemp.getOficio());
                                pstmt.setInt(2, movTemp.getYear());
                                pstmt.setString(3, movTemp.getRamo());
                                pstmt.setInt(4, movTemp.getMeta());
                                pstmt.setInt(5, movTemp.getAccion());
                                //pstmt.setString(6, "N");
                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    while (rsResult.next()) {
                                        movOficioAccionEst = new MovOficioAccionEstimacion();
                                        movOficioAccionEst.setYear(rsResult.getInt("YEAR"));
                                        movOficioAccionEst.setOficio(rsResult.getInt("OFICIO"));
                                        movOficioAccionEst.setRamo(rsResult.getString("RAMO"));
                                        movOficioAccionEst.setMeta(rsResult.getInt("META"));
                                        movOficioAccionEst.setAccion(rsResult.getInt("ACCION"));
                                        movOficioAccionEst.setPeriodo(rsResult.getInt("PERIODO"));
                                        movOficioAccionEst.setValor(rsResult.getDouble("VALOR"));
                                        recalenAccion.setValAutorizacion(rsResult.getString("VAL_AUTORIZADO"));
                                        movOficioAccionEst.setPrograma(this.getResultSQLgetProgramaByRamoMetaAccion(movOficioAccionEst.getYear(),
                                                movOficioAccionEst.getRamo(), movOficioAccionEst.getMeta(), movOficioAccionEst.getAccion()));
                                        movOficioAccionEsTempList.add(movOficioAccionEst);
                                    }
                                    identificado++;
                                    recalenAccion.setIdentificado(identificado);
                                    recalenAccion.setMovEstimacionList(movOficioAccionEsTempList);
                                    recalenAccionList.add(recalenAccion);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return recalenAccionList;
    }

    public String getResultSQLGetFlujoAutorizacion(String tipoMov, String tipoUsuario, String estatus) throws SQLException {
        String valor = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRequerimientoByFuenteFinanciamientoUsuario());
                if (pstmt != null) {
                    pstmt.setString(1, tipoMov);
                    pstmt.setString(2, tipoUsuario);
                    pstmt.setString(3, estatus);
                    pstmt.setString(4, tipoMov);
                    pstmt.setString(5, tipoUsuario);
                    pstmt.setString(6, estatus);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valor = rsResult.getString("ESTATUS");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return valor;
    }

    public String getResultSQLgetSecuenciaMovimientoOficio(boolean isActual) throws SQLException {
        String sequencia = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetSequenceMovimientoOficio(isActual));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            sequencia = rsResult.getString("SEQ");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return sequencia;
    }

    public boolean getResultSQLInsertMovEstimacion(int folio, int year, String ramo, int meta, int periodo, double valor, String nvaCreacion, String indTransf, String valAutorizado) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertMovEstimacion(folio, year, ramo, meta, periodo, valor, nvaCreacion, indTransf, valAutorizado));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertMovOficio(int oficio, String appLogin, String estatus,
            String tipoMov, String justificacion, String fecha, boolean isActual, String capturaEspecial) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertMovoficio(oficio, appLogin,
                        estatus, tipoMov, justificacion, fecha, isActual, capturaEspecial));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMovOficio(int oficio, String appLogin, String estatus,
            String tipoMov, String justificacion, String fecha, boolean isActual, String capturaEspecial) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertMovoficio(oficio,
                        appLogin, estatus, tipoMov, justificacion, fecha, isActual, capturaEspecial));
                //conectaBD.transaccionCommit();
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateAmpliacionByTipoMov(String status, int oficio, String tipoMov) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateAmpliacionesByTipomov(status, oficio, tipoMov));
                //conectaBD.transaccionCommit();
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateTransferenciaByTipoOficio(String status, int oficio, String tipoOficio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusTransferenciaTipoOficio(status, oficio, tipoOficio));
                //conectaBD.transaccionCommit();
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertMovAccionEstimacion(int folio, int year,
            String ramo, int meta, int accion, int periodo, double valor,
            String nvaCreacion, String indTransf, String valAutorizado) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertMovAccionEstimacion(folio,
                        year, ramo, meta, accion, periodo, valor, nvaCreacion, indTransf, valAutorizado));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }
    /*
     public boolean getResultSQLInsertMovAccionReqEstimacion(int folio, int year, String ramo, String prg, String depto, int meta, int accion,
     int req, String descReq, String fuente, String fondo, String recurso, String tipoGasto, String partida,
     String relLaboral, double cantidad, double costoUnitario, double costoAnual, double ene, double feb, double mar,
     double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic,
     String articulo, String nvaCreacion, String justificacion, int consec, String considerar) {
     boolean resultado = false;
     QuerysBD query = new QuerysBD();
     if (conectaBD != null) {
     try {
     resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertMovAccionReq(folio, year, ramo,
     prg, depto, meta, accion, req, descReq, fuente, fondo, recurso, tipoGasto, partida,
     relLaboral, cantidad, costoUnitario, costoAnual, ene, feb, mar, abr, may, jun, jul,
     ago, sep, oct, nov, dic, articulo, nvaCreacion, justificacion, consec, considerar));
     if(!resultado){
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     //bitacora.setStrInformacion(sqle, new Throwable());
     bitacora.grabaBitacora();
     }
     } catch (Exception sqle) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(sqle, new Throwable());
     bitacora.grabaBitacora();
     try {
     conectaBD.transaccionRollback();
     } catch (Exception e) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(e, new Throwable());
     bitacora.grabaBitacora();
     }
     }
     }
     return resultado;
     }
     */

    public boolean getResultSQLInsertMovAccionReqEstimacion(int folio, int year, String ramo, String prg, String depto, int meta, int accion,
            int req, String descReq, String fuente, String fondo, String recurso, String tipoGasto, String partida,
            String relLaboral, double cantidad, double costoUnitario, double costoAnual, double ene, double feb, double mar,
            double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic,
            String articulo, String nvaCreacion, String justificacion, int consec, String considerar) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        PreparedStatement pstmt = null;
        if (conectaBD != null) {
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLinsertMovAccionReq());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, prg);
                    pstmt.setString(5, depto);
                    pstmt.setInt(6, meta);
                    pstmt.setInt(7, accion);
                    pstmt.setInt(8, req);
                    pstmt.setString(9, descReq);
                    pstmt.setString(10, fuente);
                    pstmt.setString(11, fondo);
                    pstmt.setString(12, recurso);
                    pstmt.setString(13, tipoGasto);
                    pstmt.setString(14, partida);
                    pstmt.setString(15, relLaboral);
                    pstmt.setDouble(16, cantidad);
                    pstmt.setDouble(17, costoUnitario);
                    pstmt.setDouble(18, costoAnual);
                    pstmt.setDouble(19, ene);
                    pstmt.setDouble(20, feb);
                    pstmt.setDouble(21, mar);
                    pstmt.setDouble(22, abr);
                    pstmt.setDouble(23, may);
                    pstmt.setDouble(24, jun);
                    pstmt.setDouble(25, jul);
                    pstmt.setDouble(26, ago);
                    pstmt.setDouble(27, sep);
                    pstmt.setDouble(28, oct);
                    pstmt.setDouble(29, nov);
                    pstmt.setDouble(30, dic);
                    pstmt.setString(31, articulo);
                    pstmt.setString(32, nvaCreacion);
                    pstmt.setString(33, justificacion);
                    pstmt.setInt(34, consec);
                    pstmt.setString(35, considerar);
                    if (pstmt.executeUpdate() > 0) {
                        resultado = true;
                    } else {
                        bitacora.setStrUbicacion(getStrUbicacion());
                        bitacora.setStrServer(getStrServer());
                        bitacora.setITipoBitacora(1);
                        //bitacora.setStrInformacion(sqle, new Throwable());
                        bitacora.grabaBitacora();
                    }
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            } finally {
                pstmt.close();
            }
        }
        return resultado;
    }

    /*public boolean getResultSQLupdateMovRecalendarizacion(int folio) {
     boolean resultado = false;
     QuerysBD query = new QuerysBD();
     if (conectaBD != null) {
     try {
     resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovOficioRecalendarizacion(folio));
     } catch (Exception sqle) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(sqle, new Throwable());
     bitacora.grabaBitacora();
     try {
     conectaBD.transaccionRollback();
     } catch (Exception e) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(e, new Throwable());
     bitacora.grabaBitacora();
     }
     }
     }
     return resultado;
     }*/

    /*public boolean getResultSQLupdateMovOficio(int folio, String estatus) {
     boolean resultado = false;
     QuerysBD query = new QuerysBD();
     if (conectaBD != null) {
     try {
     resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovOficio(folio, estatus));
     } catch (Exception sqle) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(sqle, new Throwable());
     bitacora.grabaBitacora();
     try {
     conectaBD.transaccionRollback();
     } catch (Exception e) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(e, new Throwable());
     bitacora.grabaBitacora();
     }
     }
     }
     return resultado;
     }*/
    public boolean getResultSQLupdateMovOficio(int folio, String estatus, String fecha, boolean isActual, String capturaEspecial) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovOficio(folio, estatus, fecha, isActual, capturaEspecial));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public Estimacion getResultEstimacionByYearRamoPrgProyMeta(int year, String Ramo, String Prg, String Proy, String tipoProy, int Meta) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        Estimacion objEstimacion = new Estimacion();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Ramo ramo;
            try {
                //            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamoByUsuario(year, usuario));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        ramo = new Ramo();
                        ramo.setRamo(rsResultado.getString("RAMO"));
                        ramo.setRamoDescr(rsResultado.getString("DESCR"));
                        ramo.setUsaurio(rsResultado.getString("APP_LOGIN"));

                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return objEstimacion;
    }

    public List<TipoMovimiento> getResultTipoMovimiento() throws SQLException {
        List<TipoMovimiento> tipoMovimientoList = new ArrayList<TipoMovimiento>();
        TipoMovimiento tipoMovimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoMovimiento());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoMovimiento = new TipoMovimiento();
                            tipoMovimiento.setTipoMovId(rsResult.getString("TIPOMOV"));
                            tipoMovimiento.setTipoMov(rsResult.getString("DESCR"));
                            tipoMovimientoList.add(tipoMovimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return tipoMovimientoList;
    }

    public List<TipoFlujo> getResultTipoFlujoByUsuario(String usuario, String tipoDependencia) throws SQLException {
        List<TipoFlujo> tipoFlujoList = new ArrayList<TipoFlujo>();
        TipoFlujo tipoFlujo;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLTipoFlujoByUsuario(usuario, tipoDependencia));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoFlujo = new TipoFlujo();
                            tipoFlujo.setTipoFlujoId(rsResult.getString("TIPO_FLUJO"));
                            tipoFlujo.setTipoFlujo(rsResult.getString("DESCR"));
                            tipoFlujo.setEstatusBase(rsResult.getString("ESTATUS_BASE"));
                            tipoFlujoList.add(tipoFlujo);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return tipoFlujoList;
    }

    public List<Movimiento> getResultMovimientoByTipoMovUsr(String tipoMovimiento, String estatusBase, String appLogin,
            String tipoOficio, int year, int tipoFlujo, String ramoInList) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByTipoMovUsr(tipoMovimiento, estatusBase,
                        appLogin, tipoOficio, year, tipoFlujo, ramoInList));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            movimiento.setRamo(rsResult.getString("RAMO"));
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public Movimiento getResultSQLMovimientoByFolio(String appLogin, int folio, int year, boolean isNormativo) throws SQLException, IOException {
        Movimiento movimiento = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovoficioByFolio(isNormativo));
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    if (!isNormativo) {
                        pstmt.setString(3, appLogin);
                    }
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            movimiento.setTipoOficio("");
                            movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimiento;
    }

    public List<Movimiento> getResultSQLMovimientoTipoOficioByFolio(String appLogin, int folio, int year, boolean isNormativo)
            throws SQLException, IOException {
        Movimiento movimiento = null;
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovoficioTipoOficioByFolio(isNormativo));
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    if (!isNormativo) {
                        pstmt.setString(3, appLogin);
                    }
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            movimiento.setTipoOficio(rsResult.getString("TIPO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            if (rsResult.getString("TIPO") != null) {
                                movimiento.setTipoOficio(rsResult.getString("TIPO"));
                            } else {
                                movimiento.setTipoOficio("");
                            }
                            if (rsResult.getString("STATUSDESCR") != null) {
                                movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            } else {
                                movimiento.setStatusDescr("");
                            }
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public EstatusMov getResultEstatusSiguiente(String tipoMov, String tipoUsr, String estatusActual) throws SQLException {
        EstatusMov estatusMov = new EstatusMov();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLEstatusSiguiente(tipoMov, tipoUsr, estatusActual));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            estatusMov.setEstatusMovId(rsResult.getString("ESTATUS"));
                            estatusMov.setEstatusMov(rsResult.getString("DESCR"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return estatusMov;
    }

    public boolean getResultSQLUpdateEstatusMov(String estatus, int folio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusMov(estatus, folio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public int getResultTipoFlujoInicial(String tipoMov, String tipoUsr) throws SQLException {
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        int tipoFlujo = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLObtenerTipoFlujoinicial(tipoMov, tipoUsr));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            tipoFlujo = rsResult.getInt("TIPO_FLUJO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return tipoFlujo;
    }

    public boolean getResultInsertBitMovto(int oficio, String appLogin, String autorizo, String impFirma,
            String tipoOficio, String terminal, int tipoFlujo, String tipoUsr) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertBitMovto(oficio, appLogin, autorizo, impFirma, tipoOficio, terminal, tipoFlujo, tipoUsr));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultDeleteBitMovtoByOficio(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteBitMovtoByOficio(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultDeleteBitMovtoByOficioTipoOficio(int oficio, String tipoOficio, int tipoFlujo) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteBitMovtoByOficioTipoOficio(oficio, tipoOficio, tipoFlujo));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public List<FlujoFirmas> getResultUsuariosAFirmar(int tipoFlujo, String tipoUsr) throws SQLException {
        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        FlujoFirmas flujoFirmas;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLUsuariosAFirmar(tipoFlujo, tipoUsr));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            flujoFirmas = new FlujoFirmas();
                            flujoFirmas.setAppLogin(rsResult.getString("APP_LOGIN"));
                            flujoFirmas.setOrden(rsResult.getInt("ORDEN"));
                            flujoFirmasList.add(flujoFirmas);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return flujoFirmasList;
    }

    public boolean getResultValidaFirma(int oficio, int tipoFlujo, String appLogin) throws SQLException {
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaFirma(oficio, tipoFlujo, appLogin));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            if (rsResult.getInt("CUANTOS") > 0) {
                                resultado = true;
                            }
                        }
                    }
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public List<EstatusMov> getResultEstatusMovimientos() throws SQLException {
        List<EstatusMov> estatusMovimientosList = new ArrayList<EstatusMov>();
        EstatusMov estatusMovimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLEstatusMovimientos());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            estatusMovimiento = new EstatusMov();
                            estatusMovimiento.setEstatusMovId(rsResult.getString("ESTATUS"));
                            estatusMovimiento.setEstatusMov(rsResult.getString("DESCR"));
                            estatusMovimientosList.add(estatusMovimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return estatusMovimientosList;
    }

    public Movimiento getResultMovimientoByFolio(int folio) throws SQLException {
        Movimiento movimiento = new Movimiento();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByFolio(folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") != null) {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            } else {
                                movimiento.setJutificacion("");
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimiento;
    }

    public int getResultTipoFlujoByTipoMov(String tipoMov, String tipoUsr, String estatus) throws SQLException {
        int tipoFlujo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLTipoFlujoByTipoMov(tipoMov, tipoUsr, estatus));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            tipoFlujo = rsResult.getInt("TIPO_FLUJO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return tipoFlujo;
    }

    public boolean getResultInsertHistCalendarizacionMeta(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertHistCalendarizacionMeta(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultInsertHistCalendarizacionAccion(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertHistCalendarizacionAccion(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultInsertHistCalendarizacionRequerimiento(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertHistCalendarizacionRequerimiento(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultUpdateCalendarizacionMeta(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateCalendarizacionMeta(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultUpdateCalendarizacionAccion(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateCalendarizacionAccion(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultUpdateCalendarizacionRequerimiento(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateCalendarizacionRequerimiento(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public List<FlujoFirmas> getResultFirmantesAnteriores(String tipoMov, String estatusBase, String appLogin, String tipoUsr) throws SQLException {
        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        FlujoFirmas flujoFirmas;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLFirmantesAnteriores(tipoMov, estatusBase, appLogin, tipoUsr));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            flujoFirmas = new FlujoFirmas();
                            flujoFirmas.setAppLogin(rsResult.getString("APP_LOGIN"));
                            flujoFirmas.setOrden(rsResult.getInt("ORDEN"));
                            flujoFirmas.setTipoFlujo(rsResult.getInt("TIPO_FLUJO"));
                            flujoFirmasList.add(flujoFirmas);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return flujoFirmasList;
    }

    public boolean getResultSQLUpdateEstatusMotivoMov(String estatus, String motivo, int folio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusMotivoMov(estatus, motivo, folio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public int getResultExistenMovOficiosByRamoMetaAccion(String ramo, int meta, int accion) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExistenMovOficiosByRamoMetaAccion(ramo, meta, accion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultCountMovAccionRequerimiento(String ramo, String programa, int meta, int accion,
            String partida, String fuente, String fondo, String recurso, int requerimiento) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountMovAccionRequerimiento());
                if (pstmt != null) {
                    pstmt.setString(1, ramo);
                    pstmt.setString(2, programa);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    pstmt.setString(5, partida);
                    pstmt.setString(6, fuente);
                    pstmt.setString(7, fondo);
                    pstmt.setString(8, recurso);
                    pstmt.setInt(9, requerimiento);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("COUNT");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public List<Movimiento> getResultMovimientoByTipoMov(String tipoMovimiento, String estatusBase, int year) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByTipoMov(tipoMovimiento, estatusBase, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            /*if (rsResult.getString("TIPO") != null) {
                             movimiento.setTipoOficio(rsResult.getString("TIPO"));
                             } else {*/
                            movimiento.setTipoOficio("");
                            /*}*/
                            if (rsResult.getString("STATUSDESCR") != null) {
                                movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            } else {
                                movimiento.setStatusDescr("");
                            }
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public List<Movimiento> getResultMovimientoByTipoMovEstatusMovAppLogin(String tipoMovimiento, String estatusBase, int year, String appLogin) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByTipoMovEstatusMovAppLogin());
                if (pstmt != null) {
                    pstmt.setString(1, tipoMovimiento);
                    pstmt.setString(2, estatusBase);
                    pstmt.setInt(3, year);
                    pstmt.setString(4, appLogin);
                    pstmt.setInt(5, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            /*if (rsResult.getString("TIPO") != null) {
                             movimiento.setTipoOficio(rsResult.getString("TIPO"));
                             } else {*/
                            movimiento.setTipoOficio("");
                            /*}*/
                            if (rsResult.getString("STATUSDESCR") != null) {
                                movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            } else {
                                movimiento.setStatusDescr("");
                            }
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public Meta getResultGetMetaByRamoMetaYear(String ramoId, int metaId, int year) throws SQLException {
        Meta meta = new Meta();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMetaByRamoMetaYear(ramoId, metaId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {

                            meta = new Meta();
                            meta.setRamo(rsResult.getString("RAMO"));
                            meta.setRamoDescr(rsResult.getString("RAMO_DESCR"));
                            meta.setPrograma(rsResult.getString("PRG"));
                            meta.setProgramaDescr(rsResult.getString("PRG_DESCR"));
                            meta.setProyecto(rsResult.getInt("PROY"));
                            meta.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            meta.setProyectoDescr(rsResult.getString("PROY_DESCR"));
                            meta.setMetaId(rsResult.getInt("META"));
                            meta.setMeta(rsResult.getString("META_DESCR"));
                            meta.setCalculo(rsResult.getString("CALCULO"));
                            meta.setMetId(rsResult.getString("CVE_MEDIDA"));

                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return meta;
    }

    public Accion getResultGetAccionByYearRamoMetaAccion(int year, String ramoId, int metaId, int accionId) throws SQLException {
        Accion accion = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAccionByYearRamoMetaAccion(year, ramoId, metaId, accionId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accion = new Accion();
                            accion.setAccionId(rsResult.getInt("ACCION"));
                            accion.setAccion(rsResult.getString("ACC_DESCR"));
                            accion.setRamo(rsResult.getString("RAMO"));
                            accion.setRamoDescr(rsResult.getString("RAMO_DESCR"));
                            accion.setPrg(rsResult.getString("PRG"));
                            accion.setProgramaDescr(rsResult.getString("PRG_DESCR"));
                            accion.setMeta(rsResult.getInt("META"));
                            accion.setMetaDescr(rsResult.getString("MET_DESCR"));
                            accion.setTipoproy(rsResult.getString("TIPO_PROY"));
                            accion.setProyId(rsResult.getInt("PROY"));
                            accion.setProyDescr(rsResult.getString("PROY_DESCR"));
                            accion.setCalculo(rsResult.getString("CALCULO"));
                            accion.setMedida(rsResult.getString("CVE_MEDIDA"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return accion;
    }

    public String[] getResultSQLgetRequerimientoByIdUsuario(int year, String ramoId, String programa, int metaId, int accionId, int requerimiento) throws SQLException {
        String[] accionReq = new String[19];
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetRequerimientoByIdUsuario());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramoId);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, metaId);
                    pstmt.setInt(5, accionId);
                    pstmt.setInt(6, requerimiento);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accionReq[0] = rsResult.getString("RAMO");
                            accionReq[1] = rsResult.getString("RAMO_D");
                            accionReq[2] = rsResult.getString("PRG");
                            accionReq[3] = rsResult.getString("PROGRAMA_D");
                            accionReq[4] = rsResult.getString("TIPO_PROY");
                            accionReq[5] = rsResult.getString("PROY");
                            accionReq[6] = rsResult.getString("PROYECTO_D");
                            accionReq[7] = rsResult.getString("META");
                            accionReq[8] = rsResult.getString("META_D");
                            accionReq[9] = rsResult.getString("ACCION");
                            accionReq[10] = rsResult.getString("ACCION_D");
                            accionReq[11] = rsResult.getString("REQUERIMIENTO");
                            accionReq[12] = rsResult.getString("REQUERIMIENTO_D");
                            accionReq[13] = rsResult.getString("PARTIDA");
                            accionReq[14] = rsResult.getString("PARTIDA_D");
                            accionReq[15] = rsResult.getString("REL_LABORAL");
                            accionReq[16] = rsResult.getString("REL_LABORAL_D");
                            accionReq[17] = rsResult.getString("FUENTE");
                            accionReq[18] = rsResult.getString("FUENTE_D");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return accionReq;
    }

    public String[] getResultSQLgetRequerimientoByDatosMovtos(int year, String ramoId, String programa,
            int metaId, int accionId, String partida, String relLaboral, String fuente, String fondo, String recurso) throws SQLException {
        String[] accionReq = new String[19];
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetRequerimientoByDatosMovtos());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramoId);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, metaId);
                    pstmt.setInt(5, accionId);
                    pstmt.setString(6, partida);
                    pstmt.setString(7, relLaboral);
                    pstmt.setString(8, fuente);
                    pstmt.setString(9, fondo);
                    pstmt.setString(10, recurso);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accionReq[0] = rsResult.getString("RAMO");
                            accionReq[1] = rsResult.getString("RAMO_D");
                            accionReq[2] = rsResult.getString("PRG");
                            accionReq[3] = rsResult.getString("PROGRAMA_D");
                            accionReq[4] = rsResult.getString("TIPO_PROY");
                            accionReq[5] = rsResult.getString("PROY");
                            accionReq[6] = rsResult.getString("PROYECTO_D");
                            accionReq[7] = rsResult.getString("META");
                            accionReq[8] = rsResult.getString("META_D");
                            accionReq[9] = rsResult.getString("ACCION");
                            accionReq[10] = rsResult.getString("ACCION_D");
                            accionReq[11] = rsResult.getString("PARTIDA");
                            accionReq[12] = rsResult.getString("PARTIDA_D");
                            accionReq[13] = rsResult.getString("REL_LABORAL");
                            accionReq[14] = rsResult.getString("REL_LABORAL_D");
                            accionReq[15] = rsResult.getString("FUENTE");
                            accionReq[16] = rsResult.getString("FUENTE_D");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return accionReq;
    }

    public int getResultSQLcountMovtoAccionReqByInfo(int year, String ramoId, String programa, int metaId, int accionId,
            String partida, String fuente, String fondo, String recurso, String relLaboral, int requerimiento) throws SQLException {
        int cuenta = 0;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountMovtoAccionReqByInfo());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramoId);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, metaId);
                    pstmt.setInt(5, accionId);
                    pstmt.setString(6, partida);
                    pstmt.setString(7, fuente);
                    pstmt.setString(8, fondo);
                    pstmt.setString(9, recurso);
                    pstmt.setString(10, relLaboral);
                    pstmt.setInt(11, requerimiento);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuenta = rsResult.getInt("CUENTA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuenta;
    }

    public boolean getResultSQLDeleteMovtoAccion(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteMovtoAccion(folio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteMovtoMeta(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteMovtoMeta(folio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLupdateJustificacionMovoficio(int folio, String justificacion, String comentarioPlaneacion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        PreparedStatement pstmt = null;
        if (conectaBD != null) {
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLupdateMovoficio());
                if (pstmt != null) {
                    pstmt.setString(1, justificacion);
                    pstmt.setString(2, comentarioPlaneacion);
                    pstmt.setInt(3, folio);
                    if (pstmt.executeUpdate() > 0) {
                        resultado = true;
                    }
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public int getResultSQLgetCountMovtoAccionReq(int folio) throws SQLException {
        int numAccionReq = 0;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountMovtoAccionReq());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            numAccionReq = rsResult.getInt("CUENTA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return numAccionReq;
    }

    public int getResultSQLgetCountMovtoAccionReqByTipoOficio(int folio, String tipoOficio) throws SQLException {
        int numAccionReq = 0;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountMovtoAccionReqByTipoOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setString(2, tipoOficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            numAccionReq = rsResult.getInt("CUENTA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return numAccionReq;
    }

    public int getResultExistenMovMetaByFolio(int folio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovtoMeta(folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultExistenMovAccionByFolio(int folio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovtoAccion(folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultExistenMovMetaByClave(int year, String ramoId, int metaId, int oficio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovtoMetaByClave(year, ramoId, metaId, oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultExistenMovAccionByClave(int year, String ramoId, int metaId, int accionId, int oficio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovtoAccionByClave(year, ramoId, metaId, accionId, oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public MovimientosRecalendarizacion getResultGetInfoMovOficio(int folio) throws SQLException {
        MovimientosRecalendarizacion movRecal = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLDatosMovOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, folio);
                    pstmt.setInt(3, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        movRecal = new MovimientosRecalendarizacion();
                        while (rsResult.next()) {
                            movRecal.setFechaElab(rsResult.getDate("FECHAELAB"));
                            movRecal.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movRecal.setStatus(rsResult.getString("STATUS"));
                            movRecal.setComentarioAutorizacion(rsResult.getString("COMENTARIO"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movRecal.setJutificacion(new String());
                            } else {
                                movRecal.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") == null) {
                                movRecal.setObsRechazo(new String());
                            } else {
                                movRecal.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            }
                            if (rsResult.getString("RAMO") == null) {
                                movRecal.setRamo(new String());
                            } else {
                                movRecal.setRamo(rsResult.getString("RAMO"));
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movRecal;
    }

    public MovimientosReprogramacion getResultGetInfoMovOficioReprogramacion(int folio) throws SQLException, IOException {
        MovimientosReprogramacion movReprog = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLDatosMovOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, folio);
                    pstmt.setInt(3, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        movReprog = new MovimientosReprogramacion();
                        while (rsResult.next()) {
                            movReprog.setFechaElab(rsResult.getDate("FECHAELAB"));
                            movReprog.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movReprog.setComentarioAutorizacion(rsResult.getString("COMENTARIO"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movReprog.setJutificacion(new String());
                            } else {
                                movReprog.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }

                            if (rsResult.getString("OBS_RECHAZO") == null) {
                                movReprog.setObsRechazo(new String());
                            } else {
                                movReprog.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            }
                            if (rsResult.getString("RAMO") == null) {
                                movReprog.setRamo(new String());
                            } else {
                                movReprog.setRamo(rsResult.getString("RAMO"));
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movReprog;
    }

    public MovimientosAmpliacionReduccion getResultGetInfoMovOficioAmpliacionReduccion(int folio) throws SQLException {
        MovimientosAmpliacionReduccion movAmpRed = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLDatosMovOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, folio);
                    pstmt.setInt(3, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        movAmpRed = new MovimientosAmpliacionReduccion();
                        while (rsResult.next()) {
                            movAmpRed.setFechaElab(rsResult.getDate("FECHAELAB"));
                            movAmpRed.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movAmpRed.setComentarioAutorizacion(rsResult.getString("COMENTARIO"));
                            movAmpRed.setComentarioPlaneacion(rsResult.getString("COMENTARIO_PLANEACION"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movAmpRed.setJutificacion(new String());
                            } else {
                                movAmpRed.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") == null) {
                                movAmpRed.setObsRechazo(new String());
                            } else {
                                movAmpRed.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            }
                            if (rsResult.getString("CAPTURA_ESPECIAL") == null) {
                                movAmpRed.setCapturaEspecial("N");
                            } else {
                                movAmpRed.setCapturaEspecial(rsResult.getString("CAPTURA_ESPECIAL"));
                            }
                            if (rsResult.getString("RAMO") == null) {
                                movAmpRed.setRamo(new String());
                            } else {
                                movAmpRed.setRamo(rsResult.getString("RAMO"));
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movAmpRed;
    }

    public List<ReprogramacionMeta> getResultGetMovOficioMeta(int folio, int year) throws SQLException {
        ReprogramacionMeta repMeta = null;
        List<ReprogramacionMeta> repMetaList = new ArrayList<ReprogramacionMeta>();
        List<MovOficioEstimacion> movEstimacionList = new ArrayList<MovOficioEstimacion>();
        MovOficioEstimacion movEstimacion;
        MovimientoOficioMeta movOficioMeta = new MovimientoOficioMeta();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficiosMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        repMeta = new ReprogramacionMeta();
                        while (rsResult.next()) {
                            repMeta = new ReprogramacionMeta();
                            movOficioMeta = new MovimientoOficioMeta();
                            repMeta.setIdentificador(identificador);
                            movOficioMeta.setYear(year);
                            repMeta.setValAutorizado(rsResult.getString("VAL_AUTORIZADO"));
                            movOficioMeta.setRamoId(rsResult.getString("RAMO"));
                            movOficioMeta.setPrgId(rsResult.getString("PRG"));
                            movOficioMeta.setMetaId(rsResult.getInt("META"));
                            movOficioMeta.setMetaDescr(rsResult.getString("DESCR"));
                            movOficioMeta.setCalculoId(rsResult.getString("CALCULO"));
                            movOficioMeta.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                            movOficioMeta.setFinalidad(rsResult.getString("FINALIDAD"));
                            movOficioMeta.setFuncion(rsResult.getString("FUNCION"));
                            movOficioMeta.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            movOficioMeta.setCriterioTransversalidad(rsResult.getInt("CRITERIO"));
                            movOficioMeta.setClasificacionFuncionalId(movOficioMeta.getFinalidad() + "."
                                    + movOficioMeta.getFuncion() + "." + movOficioMeta.getSubfuncion());
                            movOficioMeta.setTipoProy(rsResult.getString("TIPO_PROY"));
                            movOficioMeta.setProyId(rsResult.getInt("PROY"));
                            movOficioMeta.setLineaPedId(rsResult.getString("LINEA"));
                            movOficioMeta.setCompromisoId(rsResult.getInt("TIPO_COMPROMISO"));
                            movOficioMeta.setPonderacionId(rsResult.getString("PONDERADO"));
                            movOficioMeta.setLineaSectorialId(rsResult.getString("LINEA_SECTORIAL"));
                            movOficioMeta.setNvaCreacion(rsResult.getString("NVA_CREACION"));
                            movOficioMeta.setNva_creacion(rsResult.getString("NVA_CREACION"));
                            repMeta.setMetaInfo(movOficioMeta);
                            repMetaList.add(repMeta);
                            identificador++;
                        }
                        for (ReprogramacionMeta repMetaTemp : repMetaList) {
                            movEstimacionList = new ArrayList<MovOficioEstimacion>();
                            pstmt = null;
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacionCompleto());
                            if (pstmt != null) {
                                pstmt.setInt(1, folio);
                                pstmt.setInt(2, year);
                                pstmt.setString(3, repMetaTemp.getMetaInfo().getRamoId());
                                pstmt.setInt(4, repMetaTemp.getMetaInfo().getMetaId());
                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    while (rsResult.next()) {
                                        movEstimacion = new MovOficioEstimacion();
                                        movEstimacion.setYear(rsResult.getInt("YEAR"));
                                        movEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                        movEstimacion.setRamo(rsResult.getString("RAMO"));
                                        movEstimacion.setMeta(rsResult.getInt("META"));
                                        movEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                        movEstimacion.setValor(rsResult.getDouble("VALOR"));
                                        movEstimacionList.add(movEstimacion);
                                    }
                                    repMetaTemp.setMovEstimacionList(movEstimacionList);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return repMetaList;
    }

    public double getEstimacionMovoficiosMeta(int oficio, String ramo, int meta) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double estimado = 0;
        try {
            if (conectaBD != null) {
                PreparedStatement pstmt = null;
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetTipoCalculoMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            estimado = rsResult.getDouble("CALCULO");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return estimado;
    }

    public double getEstimacionMovoficiosAccion(int oficio, String ramo, int meta, int accion) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double estimado = 0;
        try {
            if (conectaBD != null) {
                PreparedStatement pstmt = null;
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetTipoCalculoAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            estimado = rsResult.getDouble("CALCULO");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return estimado;
    }

    public List<AmpliacionReduccionMeta> getResultGetMovOficioMetaAmpRed(int folio, int year) throws SQLException {
        AmpliacionReduccionMeta ampRedMeta = null;
        List<AmpliacionReduccionMeta> ampRedMetaList = new ArrayList<AmpliacionReduccionMeta>();
        List<MovOficioEstimacion> movEstimacionList = new ArrayList<MovOficioEstimacion>();
        MovOficioEstimacion movEstimacion;
        MovimientoOficioMeta movOficioMeta = new MovimientoOficioMeta();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacionAmpRed());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        ampRedMeta = new AmpliacionReduccionMeta();
                        while (rsResult.next()) {
                            cont++;
                            movEstimacion = new MovOficioEstimacion();
                            ampRedMeta.setIdentificador(identificador);
                            movEstimacion.setYear(rsResult.getInt("YEAR"));
                            movEstimacion.setOficio(rsResult.getInt("OFICIO"));
                            movEstimacion.setRamo(rsResult.getString("RAMO"));
                            movEstimacion.setMeta(rsResult.getInt("META"));
                            movEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                            movEstimacion.setValor(rsResult.getDouble("VALOR"));
                            cantPropuesta += rsResult.getDouble("VALOR");
                            movEstimacionList.add(movEstimacion);
                            if (cont == 12) {
                                ampRedMeta.setMovOficioEstimacion(movEstimacionList);
                                ampRedMeta.setPropuestaEstimacion(getEstimacionMovoficiosMeta(folio,
                                        movEstimacion.getRamo(),
                                        movEstimacion.getMeta()));
                                ampRedMetaList.add(ampRedMeta);
                                cantPropuesta = 0.0;
                                cont = 0;
                                identificador++;
                                movEstimacionList = new ArrayList<MovOficioEstimacion>();
                                ampRedMeta = new AmpliacionReduccionMeta();
                            }
                        }
                        for (AmpliacionReduccionMeta ampRedMetaTemp : ampRedMetaList) {
                            movEstimacionList = new ArrayList<MovOficioEstimacion>();
                            pstmt.close();
                            rsResult.close();
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficiosMetaAmpRed());
                            if (pstmt != null) {
                                pstmt.setInt(1, folio);
                                pstmt.setInt(2, year);
                                pstmt.setString(3, ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                pstmt.setInt(4, ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    if (rsResult.next()) {
                                        movOficioMeta = new MovimientoOficioMeta();
                                        movOficioMeta.setYear(year);
                                        movOficioMeta.setRamoId(rsResult.getString("RAMO"));
                                        movOficioMeta.setPrgId(rsResult.getString("PRG"));
                                        movOficioMeta.setMetaId(rsResult.getInt("META"));
                                        movOficioMeta.setMetaDescr(rsResult.getString("DESCR"));
                                        movOficioMeta.setCalculoId(rsResult.getString("CALCULO"));
                                        movOficioMeta.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                        movOficioMeta.setFinalidad(rsResult.getString("FINALIDAD"));
                                        movOficioMeta.setFuncion(rsResult.getString("FUNCION"));
                                        movOficioMeta.setSubfuncion(rsResult.getString("SUBFUNCION"));
                                        movOficioMeta.setCriterioTransversalidad(rsResult.getInt("CRITERIO"));
                                        movOficioMeta.setClasificacionFuncionalId(movOficioMeta.getFinalidad() + "."
                                                + movOficioMeta.getFuncion() + "." + movOficioMeta.getSubfuncion());
                                        movOficioMeta.setTipoProy(rsResult.getString("TIPO_PROY"));
                                        movOficioMeta.setProyId(rsResult.getInt("PROY"));
                                        movOficioMeta.setLineaPedId(rsResult.getString("LINEA"));
                                        movOficioMeta.setCompromisoId(rsResult.getInt("TIPO_COMPROMISO"));
                                        movOficioMeta.setPonderacionId(rsResult.getString("PONDERADO"));
                                        movOficioMeta.setLineaSectorialId(rsResult.getString("LINEA_SECTORIAL"));
                                        movOficioMeta.setNva_creacion(rsResult.getString("NVA_CREACION"));
                                        ampRedMeta.setMovOficioMeta(movOficioMeta);
                                    } else {
                                        movOficioMeta = new MovimientoOficioMeta();
                                        movOficioMeta.setYear(year);
                                        movOficioMeta.setRamoId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                        movOficioMeta.setPrgId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getPrograma());
                                        movOficioMeta.setMetaId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                        movOficioMeta.setNva_creacion("N");
                                    }
                                    ampRedMetaTemp.setMovOficioMeta(movOficioMeta);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return ampRedMetaList;
    }

    public List<AmpliacionReduccionMeta> getResultGetMovOficioMetaAmpRedRechazado(int folio, int year, List<AmpliacionReduccionAccionReq> ampliRedList) throws SQLException {
        AmpliacionReduccionMeta ampRedMeta = null;
        List<AmpliacionReduccionMeta> ampRedMetaList = new ArrayList<AmpliacionReduccionMeta>();
        List<AmpliacionReduccionMeta> ampRedMetaRechList = new ArrayList<AmpliacionReduccionMeta>();
        List<MovOficioEstimacion> movEstimacionList = new ArrayList<MovOficioEstimacion>();
        Set<AmpliacionReduccionMeta> hash = new HashSet<AmpliacionReduccionMeta>();
        MovOficioEstimacion movEstimacion;
        MovimientoOficioMeta movOficioMeta = new MovimientoOficioMeta();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                for (AmpliacionReduccionAccionReq accionReq : ampliRedList) {
                    ampRedMetaList = new ArrayList<AmpliacionReduccionMeta>();
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacionAmpRedRechazado());
                    if (pstmt != null) {
                        pstmt.setInt(1, folio);
                        pstmt.setInt(2, year);
                        pstmt.setString(3, accionReq.getRamo());
                        pstmt.setInt(4, accionReq.getMeta());
                        rsResult = pstmt.executeQuery();
                        if (rsResult != null) {
                            ampRedMeta = new AmpliacionReduccionMeta();
                            while (rsResult.next()) {
                                cont++;
                                movEstimacion = new MovOficioEstimacion();
                                ampRedMeta.setIdentificador(identificador);
                                movEstimacion.setYear(rsResult.getInt("YEAR"));
                                movEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                movEstimacion.setRamo(rsResult.getString("RAMO"));
                                movEstimacion.setMeta(rsResult.getInt("META"));
                                movEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                movEstimacion.setValor(rsResult.getDouble("VALOR"));
                                cantPropuesta += rsResult.getDouble("VALOR");
                                movEstimacionList.add(movEstimacion);
                                if (cont == 12) {
                                    ampRedMeta.setMovOficioEstimacion(movEstimacionList);
                                    ampRedMeta.setPropuestaEstimacion(getEstimacionMovoficiosMeta(folio,
                                            movEstimacion.getRamo(),
                                            movEstimacion.getMeta()));
                                    ampRedMetaList.add(ampRedMeta);
                                    cantPropuesta = 0.0;
                                    cont = 0;
                                    identificador++;
                                    movEstimacionList = new ArrayList<MovOficioEstimacion>();
                                    ampRedMeta = new AmpliacionReduccionMeta();
                                }
                            }
                            for (AmpliacionReduccionMeta ampRedMetaTemp : ampRedMetaList) {
                                movEstimacionList = new ArrayList<MovOficioEstimacion>();
                                pstmt = null;
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficiosMetaAmpRed());
                                if (pstmt != null) {
                                    pstmt.setInt(1, folio);
                                    pstmt.setInt(2, year);
                                    pstmt.setString(3, ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                    pstmt.setInt(4, ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null) {
                                        if (rsResult.next()) {
                                            movOficioMeta = new MovimientoOficioMeta();
                                            movOficioMeta.setYear(year);
                                            movOficioMeta.setRamoId(rsResult.getString("RAMO"));
                                            movOficioMeta.setPrgId(rsResult.getString("PRG"));
                                            movOficioMeta.setMetaId(rsResult.getInt("META"));
                                            movOficioMeta.setMetaDescr(rsResult.getString("DESCR"));
                                            movOficioMeta.setCalculoId(rsResult.getString("CALCULO"));
                                            movOficioMeta.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                            movOficioMeta.setFinalidad(rsResult.getString("FINALIDAD"));
                                            movOficioMeta.setFuncion(rsResult.getString("FUNCION"));
                                            movOficioMeta.setSubfuncion(rsResult.getString("SUBFUNCION"));
                                            movOficioMeta.setCriterioTransversalidad(rsResult.getInt("CRITERIO"));
                                            movOficioMeta.setClasificacionFuncionalId(movOficioMeta.getFinalidad() + "."
                                                    + movOficioMeta.getFuncion() + "." + movOficioMeta.getSubfuncion());
                                            movOficioMeta.setTipoProy(rsResult.getString("TIPO_PROY"));
                                            movOficioMeta.setProyId(rsResult.getInt("PROY"));
                                            movOficioMeta.setLineaPedId(rsResult.getString("LINEA"));
                                            movOficioMeta.setCompromisoId(rsResult.getInt("TIPO_COMPROMISO"));
                                            movOficioMeta.setPonderacionId(rsResult.getString("PONDERADO"));
                                            movOficioMeta.setLineaSectorialId(rsResult.getString("LINEA_SECTORIAL"));
                                            movOficioMeta.setNva_creacion(rsResult.getString("NVA_CREACION"));
                                            ampRedMeta.setMovOficioMeta(movOficioMeta);
                                        } else {
                                            movOficioMeta = new MovimientoOficioMeta();
                                            movOficioMeta.setYear(year);
                                            movOficioMeta.setRamoId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                            movOficioMeta.setPrgId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getPrograma());
                                            movOficioMeta.setMetaId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                            movOficioMeta.setNva_creacion("N");
                                        }
                                        ampRedMetaTemp.setMovOficioMeta(movOficioMeta);
                                        if (ampRedMetaRechList.isEmpty()) {
                                            ampRedMetaRechList.add(ampRedMetaTemp);
                                        } else {
                                            for (AmpliacionReduccionMeta meta : ampRedMetaRechList) {
                                                if (!meta.getMovOficioMeta().getRamoId().equals(ampRedMetaTemp.getMovOficioMeta().getRamoId())
                                                        && meta.getMovOficioMeta().getMetaId() != ampRedMetaTemp.getMovOficioMeta().getMetaId()) {
                                                    ampRedMetaRechList.add(ampRedMetaTemp);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
                /*
                 hash.addAll(ampRedMetaRechList);
                 ampRedMetaRechList = new ArrayList<AmpliacionReduccionMeta>();
                 ampRedMetaRechList.addAll(hash);
                 */
            }
        }
        return ampRedMetaRechList;
    }

    public List<ReprogramacionAccion> getResultGetMovOficioAccion(int folio, int year) throws SQLException {
        ReprogramacionAccion repAccion = null;
        List<ReprogramacionAccion> repAccionList = new ArrayList<ReprogramacionAccion>();
        List<MovOficioAccionEstimacion> movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
        MovOficioAccionEstimacion movAccionEstimacion;
        MovimientoOficioAccion movOficioAccion = new MovimientoOficioAccion();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficioAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            repAccion = new ReprogramacionAccion();
                            movOficioAccion = new MovimientoOficioAccion();
                            repAccion.setIdentificador(identificador);
                            movOficioAccion.setYear(year);
                            repAccion.setValAutorizado(rsResult.getString("VAL_AUTORIZADO"));
                            movOficioAccion.setRamoId(rsResult.getString("RAMO"));
                            movOficioAccion.setProgramaId(rsResult.getString("PRG"));
                            movOficioAccion.setMetaId(rsResult.getInt("META"));
                            movOficioAccion.setDeptoId(rsResult.getString("DEPTO"));
                            movOficioAccion.setAccionId(rsResult.getInt("ACCION"));
                            movOficioAccion.setAccionDescr(rsResult.getString("DESCR"));
                            movOficioAccion.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                            movOficioAccion.setCalculo(rsResult.getString("CALCULO"));
                            movOficioAccion.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            movOficioAccion.setLinea(rsResult.getString("LINEA"));
                            movOficioAccion.setGrupoPoblacional(rsResult.getInt("GRUPO_POBLACION"));
                            movOficioAccion.setBenefHombre(rsResult.getInt("BENEF_HOMBRE"));
                            movOficioAccion.setBenefMujer(rsResult.getInt("BENEF_MUJER"));
                            movOficioAccion.setMunicipio(rsResult.getString("MPO"));
                            movOficioAccion.setLocalidad(rsResult.getInt("LOCALIDAD"));
                            movOficioAccion.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                            movOficioAccion.setNvaCreacion(rsResult.getString("NVA_CREACION"));
                            movOficioAccion.setObra(rsResult.getString("OBRA"));
                            repAccion.setMovOficioAccion(movOficioAccion);
                            repAccionList.add(repAccion);
                            identificador++;
                        }
                        for (ReprogramacionAccion repAccionTemp : repAccionList) {
                            movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
                            pstmt = null;
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionCompleto());
                            if (pstmt != null) {
                                pstmt.setInt(1, folio);
                                pstmt.setInt(2, year);
                                pstmt.setString(3, repAccionTemp.getMovOficioAccion().getRamoId());
                                pstmt.setInt(4, repAccionTemp.getMovOficioAccion().getMetaId());
                                pstmt.setInt(5, repAccionTemp.getMovOficioAccion().getAccionId());
                                //pstmt.setString(6, "N");

                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    while (rsResult.next()) {
                                        movAccionEstimacion = new MovOficioAccionEstimacion();
                                        movAccionEstimacion.setYear(rsResult.getInt("YEAR"));
                                        movAccionEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                        movAccionEstimacion.setRamo(rsResult.getString("RAMO"));
                                        movAccionEstimacion.setMeta(rsResult.getInt("META"));
                                        movAccionEstimacion.setAccion(rsResult.getInt("ACCION"));
                                        movAccionEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                        movAccionEstimacion.setValor(rsResult.getDouble("VALOR"));
                                        movAccionEstimacionList.add(movAccionEstimacion);
                                    }
                                    repAccionTemp.setMovAcionEstimacionList(movAccionEstimacionList);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return repAccionList;
    }

    public List<AmpliacionReduccionAccion> getResultGetMovOficioAccionAmpRed(int folio, int year) throws SQLException {
        AmpliacionReduccionAccion ampRedAccion = null;
        List<AmpliacionReduccionAccion> ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
        List<MovOficioAccionEstimacion> movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
        MovOficioAccionEstimacion movAccionEstimacion;
        MovimientoOficioAccion movOficioAccion = new MovimientoOficioAccion();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionAmpRed());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    //pstmt.setString(3, "N");
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        ampRedAccion = new AmpliacionReduccionAccion();
                        while (rsResult.next()) {
                            cont++;
                            movAccionEstimacion = new MovOficioAccionEstimacion();
                            ampRedAccion.setIdentificador(identificador);
                            movAccionEstimacion = new MovOficioAccionEstimacion();
                            movAccionEstimacion.setYear(rsResult.getInt("YEAR"));
                            movAccionEstimacion.setOficio(rsResult.getInt("OFICIO"));
                            movAccionEstimacion.setRamo(rsResult.getString("RAMO"));
                            movAccionEstimacion.setMeta(rsResult.getInt("META"));
                            movAccionEstimacion.setAccion(rsResult.getInt("ACCION"));
                            movAccionEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                            movAccionEstimacion.setValor(rsResult.getDouble("VALOR"));
                            cantPropuesta += rsResult.getDouble("VALOR");
                            movAccionEstimacionList.add(movAccionEstimacion);
                            if (cont == 12) {
                                ampRedAccion.setMovOficioAccionEstList(movAccionEstimacionList);
                                ampRedAccion.setPropuestaEstimacion(getEstimacionMovoficiosAccion(folio,
                                        movAccionEstimacion.getRamo(),
                                        movAccionEstimacion.getMeta(),
                                        movAccionEstimacion.getAccion()));
                                ampRedAccionList.add(ampRedAccion);
                                cont = 0;
                                cantPropuesta = 0.0;
                                movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
                                ampRedAccion = new AmpliacionReduccionAccion();
                                identificador++;
                            }
                        }
                        for (AmpliacionReduccionAccion ampRedAccionTemp : ampRedAccionList) {
                            pstmt.close();
                            rsResult.close();
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficioAccionAmpRed());
                            if (pstmt != null) {
                                pstmt.setInt(1, folio);
                                pstmt.setInt(2, year);
                                pstmt.setString(3, ampRedAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                pstmt.setInt(4, ampRedAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                pstmt.setInt(5, ampRedAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    if (rsResult.next()) {
                                        movOficioAccion = new MovimientoOficioAccion();
                                        movOficioAccion.setYear(year);
                                        movOficioAccion.setRamoId(rsResult.getString("RAMO"));
                                        movOficioAccion.setProgramaId(rsResult.getString("PRG"));
                                        movOficioAccion.setMetaId(rsResult.getInt("META"));
                                        movOficioAccion.setDeptoId(rsResult.getString("DEPTO"));
                                        movOficioAccion.setAccionId(rsResult.getInt("ACCION"));
                                        movOficioAccion.setAccionDescr(rsResult.getString("DESCR"));
                                        movOficioAccion.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                        movOficioAccion.setCalculo(rsResult.getString("CALCULO"));
                                        movOficioAccion.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                        movOficioAccion.setLinea(rsResult.getString("LINEA"));
                                        movOficioAccion.setGrupoPoblacional(rsResult.getInt("GRUPO_POBLACION"));
                                        movOficioAccion.setBenefHombre(rsResult.getInt("BENEF_HOMBRE"));
                                        movOficioAccion.setBenefMujer(rsResult.getInt("BENEF_MUJER"));
                                        movOficioAccion.setMunicipio(rsResult.getString("MPO"));
                                        movOficioAccion.setLocalidad(rsResult.getInt("LOCALIDAD"));
                                        movOficioAccion.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                                        movOficioAccion.setObra(rsResult.getString("OBRA"));
                                        movOficioAccion.setNvaCreacion(rsResult.getString("NVA_CREACION"));
                                    } else {
                                        movOficioAccion = new MovimientoOficioAccion();
                                        movOficioAccion.setYear(year);
                                        movOficioAccion.setRamoId(ampRedAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                        movOficioAccion.setProgramaId(ampRedAccionTemp.getMovOficioAccionEstList().get(0).getPrograma());
                                        movOficioAccion.setMetaId(ampRedAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                        movOficioAccion.setAccionId(ampRedAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                        movOficioAccion.setNvaCreacion("N");
                                    }
                                    ampRedAccionTemp.setMovOficioAccion(movOficioAccion);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return ampRedAccionList;
    }

    public List<AmpliacionReduccionAccion> getResultGetMovOficioAccionRechazoAmpRed(int folio, int year, List<AmpliacionReduccionAccionReq> amplRedList) throws SQLException {
        AmpliacionReduccionAccion ampRedAccion = null;
        List<AmpliacionReduccionAccion> ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
        List<MovOficioAccionEstimacion> movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
        List<AmpliacionReduccionAccion> ampRedAccionRechList = new ArrayList<AmpliacionReduccionAccion>();
        Set<AmpliacionReduccionAccion> hash = new HashSet<AmpliacionReduccionAccion>();
        MovOficioAccionEstimacion movAccionEstimacion;
        MovimientoOficioAccion movOficioAccion = new MovimientoOficioAccion();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                for (AmpliacionReduccionAccionReq amplRedAccionR : amplRedList) {
                    ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionRechazadoAmpRed());
                    if (pstmt != null) {
                        pstmt.setInt(1, folio);
                        pstmt.setInt(2, year);
                        pstmt.setString(3, amplRedAccionR.getRamo());
                        pstmt.setInt(4, amplRedAccionR.getMeta());
                        pstmt.setInt(5, amplRedAccionR.getAccion());
                        rsResult = pstmt.executeQuery();
                        if (rsResult != null) {
                            ampRedAccion = new AmpliacionReduccionAccion();
                            while (rsResult.next()) {
                                cont++;
                                movAccionEstimacion = new MovOficioAccionEstimacion();
                                ampRedAccion.setIdentificador(identificador);
                                movAccionEstimacion = new MovOficioAccionEstimacion();
                                movAccionEstimacion.setYear(rsResult.getInt("YEAR"));
                                movAccionEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                movAccionEstimacion.setRamo(rsResult.getString("RAMO"));
                                movAccionEstimacion.setMeta(rsResult.getInt("META"));
                                movAccionEstimacion.setAccion(rsResult.getInt("ACCION"));
                                movAccionEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                movAccionEstimacion.setValor(rsResult.getDouble("VALOR"));
                                cantPropuesta += rsResult.getDouble("VALOR");
                                movAccionEstimacionList.add(movAccionEstimacion);
                                if (cont == 12) {
                                    ampRedAccion.setMovOficioAccionEstList(movAccionEstimacionList);
                                    ampRedAccion.setPropuestaEstimacion(getEstimacionMovoficiosAccion(folio,
                                            movAccionEstimacion.getRamo(),
                                            movAccionEstimacion.getMeta(),
                                            movAccionEstimacion.getAccion()));
                                    ampRedAccionList.add(ampRedAccion);
                                    cont = 0;
                                    cantPropuesta = 0.0;
                                    movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
                                    ampRedAccion = new AmpliacionReduccionAccion();
                                    identificador++;
                                }
                            }
                            for (AmpliacionReduccionAccion ampRedAccionTemp : ampRedAccionList) {
                                pstmt = null;
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficioAccionAmpRed());
                                if (pstmt != null) {
                                    pstmt.setInt(1, folio);
                                    pstmt.setInt(2, year);
                                    pstmt.setString(3, ampRedAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                    pstmt.setInt(4, ampRedAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                    pstmt.setInt(5, ampRedAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null) {
                                        if (rsResult.next()) {
                                            movOficioAccion = new MovimientoOficioAccion();
                                            movOficioAccion.setYear(year);
                                            movOficioAccion.setRamoId(rsResult.getString("RAMO"));
                                            movOficioAccion.setProgramaId(rsResult.getString("PRG"));
                                            movOficioAccion.setMetaId(rsResult.getInt("META"));
                                            movOficioAccion.setDeptoId(rsResult.getString("DEPTO"));
                                            movOficioAccion.setAccionId(rsResult.getInt("ACCION"));
                                            movOficioAccion.setAccionDescr(rsResult.getString("DESCR"));
                                            movOficioAccion.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                            movOficioAccion.setCalculo(rsResult.getString("CALCULO"));
                                            movOficioAccion.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                            movOficioAccion.setLinea(rsResult.getString("LINEA"));
                                            movOficioAccion.setGrupoPoblacional(rsResult.getInt("GRUPO_POBLACION"));
                                            movOficioAccion.setBenefHombre(rsResult.getInt("BENEF_HOMBRE"));
                                            movOficioAccion.setBenefMujer(rsResult.getInt("BENEF_MUJER"));
                                            movOficioAccion.setMunicipio(rsResult.getString("MPO"));
                                            movOficioAccion.setLocalidad(rsResult.getInt("LOCALIDAD"));
                                            movOficioAccion.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                                            movOficioAccion.setNvaCreacion(rsResult.getString("NVA_CREACION"));
                                            movOficioAccion.setObra(rsResult.getString("OBRA"));
                                        } else {
                                            movOficioAccion = new MovimientoOficioAccion();
                                            movOficioAccion.setYear(year);
                                            movOficioAccion.setRamoId(ampRedAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                            movOficioAccion.setProgramaId(ampRedAccionTemp.getMovOficioAccionEstList().get(0).getPrograma());
                                            movOficioAccion.setMetaId(ampRedAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                            movOficioAccion.setAccionId(ampRedAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                            movOficioAccion.setNvaCreacion("N");
                                        }
                                        ampRedAccionTemp.setMovOficioAccion(movOficioAccion);
                                        if (ampRedAccionRechList.isEmpty()) {
                                            ampRedAccionRechList.add(ampRedAccionTemp);
                                        } else {
                                            for (AmpliacionReduccionAccion accion : ampRedAccionRechList) {
                                                if (!accion.getMovOficioAccion().getRamoId().equals(ampRedAccionTemp.getMovOficioAccion().getRamoId())
                                                        && accion.getMovOficioAccion().getMetaId() != ampRedAccionTemp.getMovOficioAccion().getMetaId()
                                                        && accion.getMovOficioAccion().getAccionId() != ampRedAccionTemp.getMovOficioAccion().getAccionId()) {
                                                    ampRedAccionRechList.add(ampRedAccionTemp);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
                /*hash.addAll(ampRedAccionRechList);
                 ampRedAccionRechList = new ArrayList<AmpliacionReduccionAccion>();
                 ampRedAccionRechList.addAll(hash);*/
            }
        }
        return ampRedAccionRechList;
    }

    public boolean getResultSQLInsertMovReprogramacionMeta(int year,
            int folio, String ramo, String prg, int meta, String metaDescr,
            String calculo, int cveMedida, String finalidad, String funcion,
            String subFuncion, String tipoProy, int proy, String linea,
            int tipoCompromiso, String ponderado, String lineaSectorial,
            int criterio, String nvaCreacion, String valAutorizado) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertMovReprogramacionMeta(year,
                        folio, ramo, prg, meta, metaDescr.replaceAll("'","''"), calculo, cveMedida, finalidad,
                        funcion, subFuncion, tipoProy, proy, linea, tipoCompromiso,
                        ponderado, lineaSectorial, criterio, nvaCreacion, valAutorizado));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public List<AmpliacionReduccionAccionReq> getAmpliacionReduccionAccionReqByFolio(int folio, int year) throws SQLException {
        List<AmpliacionReduccionAccionReq> amplRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
        AmpliacionReduccionAccionReq amplRedAccionReq = new AmpliacionReduccionAccionReq();
        MovOficiosAccionReq requerimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSLQgetAmpliacionesByFolio());
                pstmt.setInt(1, folio);
                pstmt.setInt(2, year);
                if (pstmt != null) {
                    try {
                        rsResult = pstmt.executeQuery();
                        if (rsResult != null) {
                            while (rsResult.next()) {
                                amplRedAccionReq = new AmpliacionReduccionAccionReq();
                                amplRedAccionReq.setIdentidicador(identificador);
                                amplRedAccionReq.setConsecutivo(rsResult.getInt("CONSEC"));
                                amplRedAccionReq.setRamo(rsResult.getString("RAMO"));
                                amplRedAccionReq.setDepto(rsResult.getString("DEPTO"));
                                amplRedAccionReq.setFinalidad(rsResult.getString("FINALIDAD"));
                                amplRedAccionReq.setFuncion(rsResult.getString("FUNCION"));
                                amplRedAccionReq.setSubfuncion(rsResult.getString("SUBFUNCION"));
                                amplRedAccionReq.setPrgConac(rsResult.getString("PRG_CONAC"));
                                amplRedAccionReq.setPrograma(rsResult.getString("PRG"));
                                amplRedAccionReq.setTipoProy(rsResult.getString("TIPO_PROY"));
                                amplRedAccionReq.setProy(rsResult.getInt("PROYECTO"));
                                amplRedAccionReq.setMeta(rsResult.getInt("META"));
                                amplRedAccionReq.setAccion(rsResult.getInt("ACCION"));
                                amplRedAccionReq.setPartida(rsResult.getString("PARTIDA"));
                                amplRedAccionReq.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                amplRedAccionReq.setFuente(rsResult.getString("FUENTE"));
                                amplRedAccionReq.setFondo(rsResult.getString("FONDO"));
                                amplRedAccionReq.setRecurso(rsResult.getString("RECURSO"));
                                amplRedAccionReq.setMunicipio(rsResult.getString("MUNICIPIO"));
                                amplRedAccionReq.setDelegacion(rsResult.getInt("DELEGACION"));
                                amplRedAccionReq.setRelLaboral(rsResult.getString("REL_LABORAL"));
                                amplRedAccionReq.setImporte(rsResult.getDouble("IMPTE"));
                                amplRedAccionReq.setEstatus(rsResult.getString("STATUS"));
                                amplRedAccionReq.setTipoMovAmpRed(rsResult.getString("TIPOMOV"));
                                amplRedAccionReq.setTransitorio(rsResult.getString("TRANSITORIO"));
                                amplRedAccionReq.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                                amplRedAccionReq.setConsiderar(rsResult.getString("CONSIDERAR"));
                                amplRedAccionReq.setIsIngresoPropio(rsResult.getString("INGRESO_PROPIO"));
                                amplRedAccionReqList.add(amplRedAccionReq);
                                identificador++;
                            }
                            rsResult.close();
                            for (AmpliacionReduccionAccionReq amplRedAccionReqTemp : amplRedAccionReqList) {
                                rsResult.close();
                                pstmt.close();
                                if (amplRedAccionReqTemp.getImporte() > 0) {
                                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionRequerimientoByAmpliacion());
                                    if (pstmt != null) {
                                        pstmt.setInt(1, folio);
                                        pstmt.setString(2, amplRedAccionReqTemp.getRamo());
                                        pstmt.setString(3, amplRedAccionReqTemp.getPrograma());
                                        pstmt.setInt(4, amplRedAccionReqTemp.getMeta());
                                        pstmt.setInt(5, amplRedAccionReqTemp.getAccion());
                                        if (amplRedAccionReqTemp.getRequerimiento() == 0) {
                                            pstmt.setInt(6, (amplRedAccionReqTemp.getConsecutivo() * -1));
                                        } else {
                                            pstmt.setInt(6, (amplRedAccionReqTemp.getRequerimiento()));
                                        }
                                        try {
                                            rsResult = pstmt.executeQuery();
                                            if (rsResult != null) {
                                                while (rsResult.next()) {
                                                    requerimiento = new MovOficiosAccionReq();
                                                    requerimiento.setOficio(rsResult.getString("OFICIO"));
                                                    requerimiento.setYear(rsResult.getInt("YEAR"));
                                                    requerimiento.setRamo(rsResult.getString("RAMO"));
                                                    requerimiento.setPrograma(rsResult.getString("PRG"));
                                                    requerimiento.setDepto(rsResult.getString("DEPTO"));
                                                    requerimiento.setMeta(rsResult.getInt("META"));
                                                    requerimiento.setAccion(rsResult.getInt("ACCION"));
                                                    requerimiento.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                                                    requerimiento.setReqDescr(rsResult.getString("DESCR"));
                                                    requerimiento.setFuente(rsResult.getString("FUENTE"));
                                                    requerimiento.setFondo(rsResult.getString("FONDO"));
                                                    requerimiento.setRecurso(rsResult.getString("RECURSO"));
                                                    requerimiento.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                                    requerimiento.setPartida(rsResult.getString("PARTIDA"));
                                                    requerimiento.setRelLaboral(rsResult.getString("REL_LABORAL"));
                                                    requerimiento.setCantidad(rsResult.getDouble("CANTIDAD"));
                                                    requerimiento.setCostoUnitario(rsResult.getDouble("COSTO_UNITARIO"));
                                                    requerimiento.setCostoAnual(rsResult.getDouble("COSTO_ANUAL"));
                                                    requerimiento.setJustificacion(rsResult.getString("JUSTIFICACION"));
                                                    requerimiento.setEne(rsResult.getDouble("ENE"));
                                                    requerimiento.setFeb(rsResult.getDouble("FEB"));
                                                    requerimiento.setMar(rsResult.getDouble("MAR"));
                                                    requerimiento.setAbr(rsResult.getDouble("ABR"));
                                                    requerimiento.setMay(rsResult.getDouble("MAY"));
                                                    requerimiento.setJun(rsResult.getDouble("JUN"));
                                                    requerimiento.setJul(rsResult.getDouble("JUL"));
                                                    requerimiento.setAgo(rsResult.getDouble("AGO"));
                                                    requerimiento.setSep(rsResult.getDouble("SEP"));
                                                    requerimiento.setOct(rsResult.getDouble("OCT"));
                                                    requerimiento.setNov(rsResult.getDouble("NOV"));
                                                    requerimiento.setDic(rsResult.getDouble("DIC"));
                                                    if (rsResult.getString("ARTICULO") == null) {
                                                        requerimiento.setArticulo("0");
                                                    } else {
                                                        requerimiento.setArticulo(rsResult.getString("ARTICULO"));
                                                    }
                                                    amplRedAccionReqTemp.setMovOficioAccionReq(requerimiento);
                                                }
                                            }
                                        } catch (Exception sqle) {
                                            bitacora.setStrUbicacion(getStrUbicacion());
                                            bitacora.setStrServer(getStrServer());
                                            bitacora.setITipoBitacora(1);
                                            bitacora.setStrInformacion(sqle, new Throwable());
                                            bitacora.grabaBitacora();
                                        }
                                        pstmt.close();
                                    }
                                }
                            }
                        }
                    } catch (SQLException sqle) {
                        bitacora.setStrUbicacion(getStrUbicacion());
                        bitacora.setStrServer(getStrServer());
                        bitacora.setITipoBitacora(1);
                        bitacora.setStrInformacion(sqle, new Throwable());
                        bitacora.grabaBitacora();
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return amplRedAccionReqList;
    }

    public List<AmpliacionReduccionAccionReq> getAmpliacionReduccionAccionReqByTipoOficio(int folio, String tipoOficio, int year) throws SQLException {
        List<AmpliacionReduccionAccionReq> amplRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
        AmpliacionReduccionAccionReq amplRedAccionReq = new AmpliacionReduccionAccionReq();
        MovOficiosAccionReq requerimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAmpliacionesByTipoOficio());
                pstmt.setInt(1, folio);
                pstmt.setString(2, tipoOficio);
                pstmt.setInt(3, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            amplRedAccionReq = new AmpliacionReduccionAccionReq();
                            amplRedAccionReq.setIdentidicador(identificador);
                            amplRedAccionReq.setConsecutivo(rsResult.getInt("CONSEC"));
                            amplRedAccionReq.setRamo(rsResult.getString("RAMO"));
                            amplRedAccionReq.setDepto(rsResult.getString("DEPTO"));
                            amplRedAccionReq.setFinalidad(rsResult.getString("FINALIDAD"));
                            amplRedAccionReq.setFuncion(rsResult.getString("FUNCION"));
                            amplRedAccionReq.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            amplRedAccionReq.setPrgConac(rsResult.getString("PRG_CONAC"));
                            amplRedAccionReq.setPrograma(rsResult.getString("PRG"));
                            amplRedAccionReq.setTipoProy(rsResult.getString("TIPO_PROY"));
                            amplRedAccionReq.setProy(rsResult.getInt("PROYECTO"));
                            amplRedAccionReq.setMeta(rsResult.getInt("META"));
                            amplRedAccionReq.setAccion(rsResult.getInt("ACCION"));
                            amplRedAccionReq.setPartida(rsResult.getString("PARTIDA"));
                            amplRedAccionReq.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            amplRedAccionReq.setFuente(rsResult.getString("FUENTE"));
                            amplRedAccionReq.setFondo(rsResult.getString("FONDO"));
                            amplRedAccionReq.setRecurso(rsResult.getString("RECURSO"));
                            amplRedAccionReq.setMunicipio(rsResult.getString("MUNICIPIO"));
                            amplRedAccionReq.setDelegacion(rsResult.getInt("DELEGACION"));
                            amplRedAccionReq.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            amplRedAccionReq.setImporte(rsResult.getDouble("IMPTE"));
                            amplRedAccionReq.setEstatus(rsResult.getString("STATUS"));
                            amplRedAccionReq.setTipoMovAmpRed(rsResult.getString("TIPOMOV"));
                            amplRedAccionReq.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                            amplRedAccionReq.setTransitorio(rsResult.getString("TRANSITORIO"));
                            amplRedAccionReq.setConsiderar(rsResult.getString("CONSIDERAR"));
                            amplRedAccionReq.setIsIngresoPropio(rsResult.getString("INGRESO_PROPIO"));
                            amplRedAccionReqList.add(amplRedAccionReq);
                            identificador++;
                        }
                        rsResult.close();
                        for (AmpliacionReduccionAccionReq amplRedAccionReqTemp : amplRedAccionReqList) {
                            if (amplRedAccionReqTemp.getImporte() > 0) {
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionRequerimientoByAmpliacion());
                                if (pstmt != null) {
                                    pstmt.setInt(1, folio);
                                    pstmt.setString(2, amplRedAccionReqTemp.getRamo());
                                    pstmt.setString(3, amplRedAccionReqTemp.getPrograma());
                                    pstmt.setInt(4, amplRedAccionReqTemp.getMeta());
                                    pstmt.setInt(5, amplRedAccionReqTemp.getAccion());
                                    if (amplRedAccionReqTemp.getRequerimiento() == 0) {
                                        pstmt.setInt(6, (amplRedAccionReqTemp.getConsecutivo() * -1));
                                    } else {
                                        pstmt.setInt(6, (amplRedAccionReqTemp.getRequerimiento()));
                                    }
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null) {
                                        while (rsResult.next()) {
                                            requerimiento = new MovOficiosAccionReq();
                                            requerimiento.setOficio(rsResult.getString("OFICIO"));
                                            requerimiento.setYear(rsResult.getInt("YEAR"));
                                            requerimiento.setRamo(rsResult.getString("RAMO"));
                                            requerimiento.setPrograma(rsResult.getString("PRG"));
                                            requerimiento.setDepto(rsResult.getString("DEPTO"));
                                            requerimiento.setMeta(rsResult.getInt("META"));
                                            requerimiento.setAccion(rsResult.getInt("ACCION"));
                                            requerimiento.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                                            requerimiento.setJustificacion(rsResult.getString("JUSTIFICACION"));
                                            requerimiento.setReqDescr(rsResult.getString("DESCR"));
                                            requerimiento.setFuente(rsResult.getString("FUENTE"));
                                            requerimiento.setFondo(rsResult.getString("FONDO"));
                                            requerimiento.setRecurso(rsResult.getString("RECURSO"));
                                            requerimiento.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                            requerimiento.setPartida(rsResult.getString("PARTIDA"));
                                            requerimiento.setRelLaboral(rsResult.getString("REL_LABORAL"));
                                            requerimiento.setCantidad(rsResult.getDouble("CANTIDAD"));
                                            requerimiento.setCostoUnitario(rsResult.getDouble("COSTO_UNITARIO"));
                                            requerimiento.setCostoAnual(rsResult.getDouble("COSTO_ANUAL"));
                                            requerimiento.setEne(rsResult.getDouble("ENE"));
                                            requerimiento.setFeb(rsResult.getDouble("FEB"));
                                            requerimiento.setMar(rsResult.getDouble("MAR"));
                                            requerimiento.setAbr(rsResult.getDouble("ABR"));
                                            requerimiento.setMay(rsResult.getDouble("MAY"));
                                            requerimiento.setJun(rsResult.getDouble("JUN"));
                                            requerimiento.setJul(rsResult.getDouble("JUL"));
                                            requerimiento.setAgo(rsResult.getDouble("AGO"));
                                            requerimiento.setSep(rsResult.getDouble("SEP"));
                                            requerimiento.setOct(rsResult.getDouble("OCT"));
                                            requerimiento.setNov(rsResult.getDouble("NOV"));
                                            requerimiento.setDic(rsResult.getDouble("DIC"));
                                            if (rsResult.getString("ARTICULO") == null) {
                                                requerimiento.setArticulo("0");
                                            } else {
                                                requerimiento.setArticulo(rsResult.getString("ARTICULO"));
                                            }
                                            amplRedAccionReqTemp.setMovOficioAccionReq(requerimiento);
                                        }
                                    }
                                    pstmt.close();
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return amplRedAccionReqList;
    }

    public int getResultExistenMovReprogramacionMetaByFolio(int folio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovOfReprogramacionMeta(folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public boolean getResultSQLDeleteMovOfReprogramacionMeta(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteMovOfReprogramacionMeta(folio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteAmpliacion(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteAmpliaciones(folio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteTipoOficio(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteTipoOficio(folio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteOfiCons(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteOficons(folio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteTipoOficioByTipoOficio(int folio, String tipoOficio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteTipoOficioByTipoOficio(folio, tipoOficio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteOfiConsByTipoOficio(int folio, String tipoOficio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteOficonsByTipoOficio(folio, tipoOficio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertMovReprogramacionAccion(int year, int folio,
            String ramo, String prg, String depto, int meta, int accion,
            String accionDescr, String calculo, int cveMedida, int grupoPoblacion,
            int benefHombre, int benefMujer, String mpo, int localidad, String linea,
            String lineaSectorial, String nvaCreacion, String obra, String valAutorizado) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        String grupo = null;
        if (grupoPoblacion != 0) {
            grupo = String.valueOf(grupoPoblacion);
        }
        if (obra == null) {
            obra = new String();
        }
        if (conectaBD != null) {
            try {

                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertMovReprogramacionAccion(year,
                        folio, ramo, prg, depto, meta, accion, accionDescr.replaceAll("'","''"), calculo,
                        cveMedida, grupo, benefHombre, benefMujer, mpo,
                        localidad, linea, lineaSectorial, nvaCreacion, obra, valAutorizado));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public int getResultExistenMovReprogramacionAccionByFolio(int folio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovOfReprogramacionAccion(folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public boolean getResultSQLDeleteMovOfReprogramacionAccion(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteMovOfReprogramacionAccion(folio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public List<Finalidad> getResultFinalidadByYear(int intYear) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<Finalidad> finalidadList = new ArrayList<Finalidad>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Finalidad finalidad;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFinalidadByYear(intYear));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        finalidad = new Finalidad();
                        finalidad.setFinalidad(rsResultado.getString("FINALIDAD"));
                        finalidad.setDescr(rsResultado.getString("DESCR"));
                        finalidad.setYear(Integer.parseInt(rsResultado.getString("YEAR")));
                        finalidadList.add(finalidad);
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return finalidadList;
    }

    public List<Funcion> getResultFuncionByYearFinalidad(int intYear, String strF1, String strF2) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<Funcion> funcionList = new ArrayList<Funcion>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Funcion funcion;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFuncionByYearFinalidad(intYear, strF1, strF2));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        funcion = new Funcion();
                        funcion.setFinalidad(rsResultado.getString("FINALIDAD"));
                        funcion.setFuncion(rsResultado.getString("FUNCION"));
                        funcion.setDescr(rsResultado.getString("DESCR"));
                        funcion.setYear(Integer.parseInt(rsResultado.getString("YEAR")));
                        funcionList.add(funcion);
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return funcionList;
    }

    public List<Subfuncion> getResultSubfuncionByYearFinalidadFuncion(int intYear, String strF1, String strF2, String strSF1, String strSF2) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<Subfuncion> subfuncionList = new ArrayList<Subfuncion>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Subfuncion subfuncion;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetSubfuncionByYearFinalidadFuncion(intYear, strF1, strF2, strSF1, strSF2));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        subfuncion = new Subfuncion();
                        subfuncion.setFinalidad(rsResultado.getString("FINALIDAD"));
                        subfuncion.setFuncion(rsResultado.getString("FUNCION"));
                        subfuncion.setSubfuncion(rsResultado.getString("SUBFUNCION"));
                        subfuncion.setDescr(rsResultado.getString("DESCR"));
                        subfuncion.setYear(Integer.parseInt(rsResultado.getString("YEAR")));
                        subfuncionList.add(subfuncion);
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return subfuncionList;
    }

    public List<EntePublico> getEntePublico() throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<EntePublico> entepublicoList = new ArrayList<EntePublico>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            EntePublico entepublico;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getEntePublico());
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        entepublico = new EntePublico();
                        entepublico.setEntePublico(rsResultado.getString("ENTE_PUBLICO"));
                        entepublico.setDescripcion(rsResultado.getString("DESCRIPCION"));
                        entepublico.setSectorConac(rsResultado.getInt("SECTOR_CONAC"));
                        entepublico.setSectorTipo(rsResultado.getInt("SECTOR_TIPO"));
                        entepublico.setSectorEconomico(rsResultado.getInt("SECTOR_ECONOMICO"));
                        entepublico.setSubsector(rsResultado.getInt("SUBSECTOR"));
                        entepublico.setTipoEntePublico(rsResultado.getString("TIPO_ENTE_PUBLICO"));
                        entepublicoList.add(entepublico);
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return entepublicoList;
    }

    public int getResultExistenMovMetaReprogramacionByClave(int year, String ramoId, int metaId, int oficio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovtoMetaReprogramacionByClave(year, ramoId, metaId, oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultExistenMovAccionReprogramacionByClave(int year, String ramoId, int metaId, int accionId, int oficio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovtoAccionReprogramacionByClave(year, ramoId, metaId, accionId, oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public boolean getResultSQLModificarEnero() throws SQLException {
        boolean resultado = false;
        String enero = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLModificarEnero());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            enero = rsResult.getString("MODIFICAR_ENERO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (enero.equals("N")) {
                    resultado = false;
                } else if (enero.equals("S")) {
                    resultado = true;
                } else {
                    resultado = false;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public CodigoPPTO getRestulSQLGetCodigoPPTOByReq(int year, String ramo, String programa, int requ, String partida, int meta, int accion) throws SQLException {
        ResultSet rsResult = null;
        CodigoPPTO codigo = null;
        int cont = 0;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCodigoProgramaticoByReq());
                if (pstmt != null) {

                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, requ);
                    pstmt.setString(5, partida);
                    pstmt.setInt(6, meta);
                    pstmt.setInt(7, accion);

                    rsResult = pstmt.executeQuery();

                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont++;
                            codigo = new CodigoPPTO();
                            codigo.setRamoId(rsResult.getString("RAMO"));
                            codigo.setDepto(rsResult.getString("DEPTO"));
                            codigo.setFinalidad(rsResult.getString("FINALIDAD"));
                            codigo.setFuncion(rsResult.getString("FUNCION"));
                            codigo.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            codigo.setProgCONAC(rsResult.getString("PRG_CONAC"));
                            codigo.setPrograma(rsResult.getString("PRG"));
                            codigo.setProyecto(rsResult.getString("PROY"));
                            codigo.setMeta(rsResult.getInt("META"));
                            codigo.setAccion(rsResult.getInt("ACCION"));
                            codigo.setPartida(rsResult.getString("PARTIDA"));
                            codigo.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            codigo.setFuente(rsResult.getString("FUENTE"));
                            codigo.setFondo(rsResult.getString("FONDO"));
                            codigo.setRecurso(rsResult.getString("RECURSO"));
                            codigo.setMunicipio(rsResult.getString("MPO"));
                            codigo.setDelegacion(rsResult.getInt("LOCALIDAD"));
                            codigo.setTipoProy(rsResult.getString("TIPO_PROY"));
                            codigo.setRelLaboral(rsResult.getString("REL_LABORAL"));
                        }
                        if (cont > 1) {
                            bitacora.setStrUbicacion(getStrUbicacion());
                            bitacora.setStrServer(getStrServer());
                            bitacora.setITipoBitacora(1);
                            bitacora.setStrInformacion("Mas de 1 registro codigo CodigoPPTOByReq");
                            bitacora.grabaBitacora();
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return codigo;
    }

    public String getRestulSQLCallRecalendarizaCodPPTO(String oficio, int year, String ramo, String depto, String finalidad, String funcion,
            String subFuncion, String progConac, String programa, String tipoProyecto, String proyecto,
            int meta, int accion, String partida, String tipoGasto, String fuente,
            String fondo, String recurso, String municipio, int delegacion, String relLaboral,
            int requerimiento, double cantXmes, int mes, double costoUnitario) throws SQLException {

        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            CallableStatement clstm = null;
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLGetProcedureRecalendarizaCodPPTO());
                if (clstm != null) {
                    clstm.setString(1, oficio);
                    clstm.setInt(2, year);
                    clstm.setString(3, ramo);
                    clstm.setString(4, depto);
                    clstm.setString(5, finalidad);
                    clstm.setString(6, funcion);
                    clstm.setString(7, subFuncion);
                    clstm.setString(8, progConac);
                    clstm.setString(9, programa);
                    clstm.setString(10, tipoProyecto);
                    clstm.setString(11, proyecto);
                    clstm.setInt(12, meta);
                    clstm.setInt(13, accion);
                    clstm.setString(14, partida);
                    clstm.setString(15, tipoGasto);
                    clstm.setString(16, fuente);
                    clstm.setString(17, fondo);
                    clstm.setString(18, recurso);
                    clstm.setString(19, municipio);
                    clstm.setInt(20, delegacion);
                    clstm.setString(21, relLaboral);
                    clstm.setInt(22, requerimiento);
                    clstm.setDouble(23, cantXmes);
                    clstm.setInt(24, mes);
                    clstm.setDouble(25, costoUnitario);

                    clstm.executeQuery();
                }
            } catch (SQLException sqle) {
                try {
                    conectaBD.transaccionRollback();
                    resultado = false;
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(sqle, new Throwable());
                    bitacora.grabaBitacora();
                    mensaje = sqle.getMessage();
                } catch (Exception ex) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(ex, new Throwable());
                    bitacora.grabaBitacora();
                    resultado = false;
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            } finally {
                clstm.close();
            }
        }
        if (resultado) {
            mensaje = "exito";
        }
        return mensaje;
    }

    public CodigoPPTO getRestulSQLgetCodigoPPTOsinRequerimiento(int year, String ramo, String programa, String tipoProyecto, int proyecto,
            int meta, int accion, String partida, String fuente, String fondo, String recurso, String relLaboral) throws SQLException {
        ResultSet rsResult = null;
        CodigoPPTO codigo = null;
        int cont = 0;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCodigoPPTOSinRequerimiento());
                if (pstmt != null) {

                    pstmt.setString(1, partida);
                    pstmt.setInt(2, year);
                    pstmt.setInt(3, year);
                    pstmt.setInt(4, year);
                    pstmt.setString(5, ramo);
                    pstmt.setString(6, programa);
                    pstmt.setInt(7, meta);
                    pstmt.setInt(8, accion);
                    pstmt.setString(9, tipoProyecto);
                    pstmt.setInt(10, proyecto);

                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont++;
                            codigo = new CodigoPPTO();
                            codigo.setRamoId(rsResult.getString("RAMO"));
                            codigo.setDepto(rsResult.getString("DEPTO"));
                            codigo.setFinalidad(rsResult.getString("FINALIDAD"));
                            codigo.setFuncion(rsResult.getString("FUNCION"));
                            codigo.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            codigo.setProgCONAC(rsResult.getString("PRG_CONAC"));
                            codigo.setPrograma(rsResult.getString("PRG"));
                            codigo.setProyecto(rsResult.getString("PROY"));
                            codigo.setMeta(rsResult.getInt("META"));
                            codigo.setAccion(rsResult.getInt("ACCION"));
                            codigo.setPartida(partida);
                            codigo.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            codigo.setFuente(fuente);
                            codigo.setFondo(fondo);
                            codigo.setRecurso(recurso);
                            codigo.setMunicipio(rsResult.getString("MPO"));
                            codigo.setDelegacion(rsResult.getInt("LOCALIDAD"));
                            codigo.setTipoProy(rsResult.getString("TIPO_PROY"));
                            codigo.setRelLaboral(relLaboral);
                        }
                        if (cont > 1) {
                            bitacora.setStrUbicacion(getStrUbicacion());
                            bitacora.setStrServer(getStrServer());
                            bitacora.setITipoBitacora(1);
                            bitacora.setStrInformacion("Mas de 1 registro codigo CodigoPPTOsinRequerimiento");
                            bitacora.grabaBitacora();
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return codigo;
    }

    public boolean getResultInsertHistMeta(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertHistMeta(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultInsertHistAccion(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertHistAccion(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultUpdateReprogramacionMeta(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateReprogramacionMeta(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultUpdateReprogramacionAccion(int oficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateReprogramacionAccion(oficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultInsertAmpliaciones(int oficio, int consecutivo, String ramo, String depto, String finalidad, String funcion,
            String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral,
            double importe, String status, String tipoMov, int requerimiento, String considerar) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsetAmpliaciones(oficio, consecutivo,
                        ramo, depto, finalidad, funcion, subfuncion, prgConac, programa, tipoProy, proyecto, meta,
                        accion, partida, tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLaboral,
                        importe, status, tipoMov, requerimiento, considerar));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public String getRamoDescrByRamoYear(String ramoId, int year) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        Ramo ramo = new Ramo();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLRamoDescrByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            ramo.setRamoDescr(rsResultado.getString("DESCR"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return ramo.getRamoDescr();
    }

    public String getProgramaDescrByProgramaYear(String programaId, int year) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        Programa programa = new Programa();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLProgramaDescrByProgramaYear(programaId, year));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            programa.setProgramaDesc(rsResultado.getString("DESCR"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return programa.getProgramaDesc();
    }

    public String getProyectoDescrByProyectoTipoProyYear(int proyectoId, String tipoProy, int year) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        Proyecto proyecto = new Proyecto();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLProyectoDescrByProyectoTipoProyYear(proyectoId, tipoProy, year));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            proyecto.setProyecto(rsResultado.getString("DESCR"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return proyecto.getProyecto();
    }

    public String getResultSQLGetTipoCompromisoByTipoCompromiso(int tipoCompromisoId) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String tipoCompromiso = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoCompromisoByTipoCompromiso(tipoCompromisoId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoCompromiso = rsResult.getString("BENEFICIADOS");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return tipoCompromiso;
    }

    public int getResultSQLGetConsecutovoNegativoAccionReq() throws SQLException {
        int negativo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetConsecutivoNegativoAccionReq());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            negativo = rsResult.getInt("REQ_NEG");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return negativo;
    }

    public int getResultSQLGetConsecutovoNegativoAccion() throws SQLException {
        int negativo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetConsecutivoNegativoAccion());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            negativo = rsResult.getInt("ACCION_NEG");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return negativo;
    }

    public int getResultSQLGetConsecutovoNegativoMeta() throws SQLException {
        int negativo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetConsecutivoNegativoMeta());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            negativo = rsResult.getInt("META_NEG");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return negativo;
    }

    public String getResultSQLGetTipoOficioByPartida(int year, String partida) throws SQLException {
        String tipoOficio = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetTipoOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, partida);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            if (rsResult.getString("AMPAUTOM") != null) {
                                tipoOficio = rsResult.getString("AMPAUTOM");
                            } else {
                                tipoOficio = "N";
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return tipoOficio;
    }

    public double getResultSQLgetAsignadoByMes(int year, String ramo, String prg, String tipoProy, int proy,
            int meta, int accion, String partida, String fuente, String fondo, String recurso, String relLaboral, int mes) throws SQLException {
        double asignado = 0.0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetAsignadoByMes());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, prg);
                    pstmt.setString(4, tipoProy);
                    pstmt.setInt(5, proy);
                    pstmt.setInt(6, meta);
                    pstmt.setInt(7, accion);
                    pstmt.setString(8, partida);
                    pstmt.setString(9, fuente);
                    pstmt.setString(10, fondo);
                    pstmt.setString(11, recurso);
                    pstmt.setString(12, relLaboral);
                    pstmt.setInt(13, mes);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            asignado = rsResult.getDouble("ASIGNADO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return asignado;
    }

    public List<TipoOficio> getResultMovimientoByTipoMovUsrTipoOficio(String tipoMovimiento, String estatusBase, String appLogin,
            int year, int tipoFlujo, String ramoInList) throws SQLException, IOException {
        List<TipoOficio> movimientoList = new ArrayList<TipoOficio>();
        TipoOficio movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByTipoMovUsrTipoOficio(tipoMovimiento, estatusBase,
                        appLogin, year, tipoFlujo, ramoInList));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new TipoOficio();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            movimiento.setTipoOficio(rsResult.getString("TIPO"));
                            movimiento.setStatusTipoOficio(rsResult.getString("STATUS_TIPO_OFICIO"));
                            if (rsResult.getString("OBS_RECHAZO_TIPO_OFICIO") != null) {
                                movimiento.setObsRechazoTipoOficio(rsResult.getString("OBS_RECHAZO_TIPO_OFICIO"));
                            } else {
                                movimiento.setObsRechazoTipoOficio("");
                            }
                            movimiento.setRamo(rsResult.getString("RAMO"));
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public List<TipoOficio> getResultMovimientoEvaluacion(int year) throws SQLException, IOException {
        List<TipoOficio> movimientoList = new ArrayList<TipoOficio>();
        TipoOficio movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovimientosEvaluacion());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setInt(2, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new TipoOficio();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            movimiento.setEvaluacion(rsResult.getString("EVALUACION"));
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            movimiento.setTipoOficio(rsResult.getString("TIPO"));
                            movimiento.setStatusTipoOficio(rsResult.getString("STATUS_TIPO_OFICIO"));
                            if (rsResult.getString("OBS_RECHAZO_TIPO_OFICIO") != null) {
                                movimiento.setObsRechazoTipoOficio(rsResult.getString("OBS_RECHAZO_TIPO_OFICIO"));
                            } else {
                                movimiento.setObsRechazoTipoOficio("");
                            }
                            movimiento.setRamo(rsResult.getString("RAMO"));
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public TipoOficio getResultMovimientoByFolioTipoOficio(int folio, String tipoOficio) throws SQLException {
        TipoOficio movimiento = new TipoOficio();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByFolioTipoOficio(folio, tipoOficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") != null) {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            } else {
                                movimiento.setJutificacion("");
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            movimiento.setTipoOficio(rsResult.getString("TIPO"));
                            movimiento.setStatusTipoOficio(rsResult.getString("STATUS_TIPO_OFICIO"));
                            if (rsResult.getString("OBS_RECHAZO_TIPO_OFICIO") != null) {
                                movimiento.setObsRechazoTipoOficio(rsResult.getString("OBS_RECHAZO_TIPO_OFICIO"));
                            } else {
                                movimiento.setObsRechazoTipoOficio("");
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimiento;
    }

    public boolean getResultSQLUpdateEstatusMotivoMovTipoOficio(String estatus, String motivo, int folio, String tipoOficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusMotivoMovTipoOficio(estatus, motivo, folio, tipoOficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateEstatusAmpliacionesTipoOficio(String estatus, int folio, String tipoOficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusAmpliacionesTipoOficio(estatus, folio, tipoOficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public int getResultSQLGetEjercicioActivoConta() throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int ejercicio = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetEjercicioActivoConta());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            ejercicio = rsResult.getInt("EJERCICIO");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return ejercicio;
    }

    public Date getResultSQLGetFechaAplicacionGasto(int ejercicio) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        Date fecha = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFechaAplicacionGasto(ejercicio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            if (rsResult.getDate("FECHA_MOMENTO_GASTO") != null) {
                                fecha = rsResult.getDate("FECHA_MOMENTO_GASTO");
                            } else {
                                fecha = rsResult.getDate("FECHA_ACTUAL");
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return fecha;
    }

    public boolean getResultSQLValidaOficioSINVP(int folio) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        boolean resultado = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetValidaOficioSINVP(folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            if (rsResult.getInt(1) == 1) {
                                resultado = true;
                            }
                        }
                        rsResult.close();
                    }
                    pstmt.close();
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    /*public int getResultSQLGetEstatusOficioSINVP(int oficio) throws SQLException {
     ResultSet rsResult = null;
     QuerysBD query = new QuerysBD();
     int estatus = 0;
     if (conectaBD != null) {
     PreparedStatement pstmt = null;
     try {
     pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetEstatusOficioSINVP(oficio));
     if (pstmt != null) {
     rsResult = pstmt.executeQuery();
     if (rsResult != null) {
     if (rsResult.next()) {
     estatus = rsResult.getInt("STATUS");
     }
     }
     }
     } catch (SQLException ex) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(ex, new Throwable());
     bitacora.grabaBitacora();
     }
     }
     return estatus;
     }*/
    public boolean getResultSQLUpdateEstatusMovTipoOficio(String estatus, int folio, String tipoOficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusMovTipoOficio(estatus, folio, tipoOficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLupdatePPTOreduccion(double importe, int year, String ramo, String depto, String finanlidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida, String tipoGasto,
            String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral, int mes) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLupdatePPTOreduccion(importe, year, ramo, depto, finanlidad, funcion,
                        subfuncion, prgConac, programa, tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio,
                        delegacion, relLaboral, mes));
                if (!resultado) {
                    System.out.println("CORRAR");
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertOficons(int folio, int consecutivo, String tipo) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertOficons(folio, consecutivo, tipo));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertTipoOficio(int folio, String tipo, String estatus) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertTipoOficio(folio, tipo, estatus));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public List<TipoOficio> getResultSQLgetTipoficioByFolio(int folio) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        List<TipoOficio> tipoOficioList = new ArrayList<TipoOficio>();
        TipoOficio tipoOficio;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetTipooficioByFolio(folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoOficio = new TipoOficio();
                            tipoOficio.setOficio(rsResult.getInt("OFICIO"));
                            tipoOficio.setTipoOficio(rsResult.getString("TIPO"));
                            tipoOficioList.add(tipoOficio);
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return tipoOficioList;
    }

    public List<TipoOficio> getResultSQLgetTipoficioByFolioTipo(int folio, String tipoficio) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        List<TipoOficio> tipoOficioList = new ArrayList<TipoOficio>();
        TipoOficio tipoOficio;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetTipooficioByFolioTipo(folio, tipoficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoOficio = new TipoOficio();
                            tipoOficio.setOficio(rsResult.getInt("OFICIO"));
                            tipoOficio.setTipoOficio(rsResult.getString("TIPO"));
                            tipoOficioList.add(tipoOficio);
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return tipoOficioList;
    }

    public int[] getResultSQLgetMesTrimestreByPeriodo(int mes, boolean validaTrimestre) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int meses[] = new int[3];
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMesTrimestreByPeriodo(validaTrimestre));
                if (pstmt != null) {
                    pstmt.setInt(1, mes);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            meses[cont] = rsResult.getInt("PERIODO");
                            cont++;
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return meses;
    }

    public String getResultSQLgetProgramaByRamoMeta(int year, String ramo, int meta) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String programa = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getProgramaByRamoMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = rsResult.getString("PRG");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return programa;
    }

    public String getResultSQLgetProgramaByRamoMetaMov(int year, String ramo, int meta, int oficio) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String programa = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getProgramaByRamoMetaMov());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setInt(4, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = rsResult.getString("PRG");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return programa;
    }

    public String getResultSQLgetProgramaByRamoMetaAccion(int year, String ramo, int meta, int accion) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String programa = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getProgramaByRamoMetaAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = rsResult.getString("PRG");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return programa;
    }

    public String getResultSQLgetProgramaByRamoMetaAccionMov(int year, String ramo, int meta, int accion, int oficio) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String programa = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getProgramaByRamoMetaAccionMov());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setInt(4, meta);
                    pstmt.setInt(5, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = rsResult.getString("PRG");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return programa;
    }

    public String getResultSQLgetDeptoDescripcion(int year, String ramo, String depto) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String programa = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetDeptoDescripcion());
                if (pstmt != null) {
                    pstmt.setString(1, ramo);
                    pstmt.setString(2, depto);
                    pstmt.setInt(3, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = rsResult.getString("DESCR");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return programa;
    }

    public MovimientosTransferencia getResultGetInfoMovOficioTransferencia(int folio) throws SQLException {
        MovimientosTransferencia movTransferencia = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLDatosMovOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, folio);
                    pstmt.setInt(3, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        movTransferencia = new MovimientosTransferencia();
                        while (rsResult.next()) {
                            movTransferencia.setFechaElab(rsResult.getDate("FECHAELAB"));
                            movTransferencia.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movTransferencia.setComentarioAutorizacion(rsResult.getString("COMENTARIO"));
                            movTransferencia.setComentarioPlaneacion(rsResult.getString("COMENTARIO_PLANEACION"));
                            movTransferencia.setOficio(rsResult.getInt("OFICIO"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movTransferencia.setJutificacion(new String());
                            } else {
                                movTransferencia.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") == null) {
                                movTransferencia.setObsRechazo(new String());
                            } else {
                                movTransferencia.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            }
                            if (rsResult.getString("RAMO") == null) {
                                movTransferencia.setRamo(new String());
                            } else {
                                movTransferencia.setRamo(rsResult.getString("RAMO"));
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movTransferencia;
    }

    public List<TransferenciaMeta> getResultGetMovOficioMetaTransferencia(int folio,
            int year, String tipoTransferencia) throws SQLException {
        TransferenciaMeta transMeta = null;
        List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
        List<MovOficioEstimacion> movEstimacionList = new ArrayList<MovOficioEstimacion>();
        MovOficioEstimacion movEstimacion;
        MovimientoOficioMeta movOficioMeta = new MovimientoOficioMeta();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacionTransferenciarecibe());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        transMeta = new TransferenciaMeta();
                        while (rsResult.next()) {
                            cont++;
                            movEstimacion = new MovOficioEstimacion();
                            transMeta.setIdentificador(identificador);
                            movEstimacion.setYear(rsResult.getInt("YEAR"));
                            movEstimacion.setOficio(rsResult.getInt("OFICIO"));
                            movEstimacion.setRamo(rsResult.getString("RAMO"));
                            movEstimacion.setMeta(rsResult.getInt("META"));
                            movEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                            movEstimacion.setValor(rsResult.getDouble("VALOR"));
                            movEstimacion.setPrograma(this.getResultSQLgetProgramaByRamoMetaMov(year, movEstimacion.getRamo(), movEstimacion.getMeta(), folio));
                            cantPropuesta += rsResult.getDouble("VALOR");
                            movEstimacionList.add(movEstimacion);
                            if (cont == 12) {
                                transMeta.setMovOficioEstimacion(movEstimacionList);
                                transMeta.setPropuestaEstimacion(cantPropuesta);
                                cantPropuesta = 0.0;
                                transMetaList.add(transMeta);
                                cont = 0;
                                identificador++;
                                movEstimacionList = new ArrayList<MovOficioEstimacion>();
                                transMeta = new TransferenciaMeta();
                            }
                        }
                        for (TransferenciaMeta ampRedMetaTemp : transMetaList) {
                            movEstimacionList = new ArrayList<MovOficioEstimacion>();
                            pstmt.close();
                            rsResult.close();
                            pstmt = null;
                            rsResult = null;
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficiosMetaTransferencia());
                            if (pstmt != null) {
                                pstmt.setInt(1, folio);
                                pstmt.setInt(2, year);
                                pstmt.setString(3, ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                pstmt.setInt(4, ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    if (rsResult.next()) {
                                        movOficioMeta = new MovimientoOficioMeta();
                                        movOficioMeta.setYear(year);
                                        movOficioMeta.setRamoId(rsResult.getString("RAMO"));
                                        movOficioMeta.setPrgId(rsResult.getString("PRG"));
                                        movOficioMeta.setMetaId(rsResult.getInt("META"));
                                        movOficioMeta.setMetaDescr(rsResult.getString("DESCR"));
                                        movOficioMeta.setCalculoId(rsResult.getString("CALCULO"));
                                        movOficioMeta.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                        movOficioMeta.setFinalidad(rsResult.getString("FINALIDAD"));
                                        movOficioMeta.setFuncion(rsResult.getString("FUNCION"));
                                        movOficioMeta.setSubfuncion(rsResult.getString("SUBFUNCION"));
                                        movOficioMeta.setCriterioTransversalidad(rsResult.getInt("CRITERIO"));
                                        movOficioMeta.setClasificacionFuncionalId(movOficioMeta.getFinalidad() + "."
                                                + movOficioMeta.getFuncion() + "." + movOficioMeta.getSubfuncion());
                                        movOficioMeta.setTipoProy(rsResult.getString("TIPO_PROY"));
                                        movOficioMeta.setProyId(rsResult.getInt("PROY"));
                                        movOficioMeta.setLineaPedId(rsResult.getString("LINEA"));
                                        movOficioMeta.setCompromisoId(rsResult.getInt("TIPO_COMPROMISO"));
                                        movOficioMeta.setPonderacionId(rsResult.getString("PONDERADO"));
                                        movOficioMeta.setLineaSectorialId(rsResult.getString("LINEA_SECTORIAL"));
                                        movOficioMeta.setNva_creacion(rsResult.getString("NVA_CREACION"));
                                        movOficioMeta.setNvaCreacion(rsResult.getString("NVA_CREACION"));
                                        transMeta.setMovOficioMeta(movOficioMeta);
                                    } else {
                                        movOficioMeta = new MovimientoOficioMeta();
                                        movOficioMeta.setYear(year);
                                        movOficioMeta.setRamoId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                        movOficioMeta.setPrgId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getPrograma());
                                        movOficioMeta.setMetaId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                        movOficioMeta.setNva_creacion("N");
                                    }
                                    ampRedMetaTemp.setMovOficioMeta(movOficioMeta);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
                pstmt = null;
                rsResult = null;
            }
        }
        return transMetaList;
    }

    public List<TransferenciaMeta> getResultGetMovOficioMetaRechazoTransferencia(int folio,
            int year, List<Transferencia> transfList) throws SQLException {
        TransferenciaMeta transMeta = null;
        List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
        List<TransferenciaMeta> transMetaRList = new ArrayList<TransferenciaMeta>();
        List<MovOficioEstimacion> movEstimacionList = new ArrayList<MovOficioEstimacion>();
        MovOficioEstimacion movEstimacion;
        MovimientoOficioMeta movOficioMeta = new MovimientoOficioMeta();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                for (Transferencia trans : transfList) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacionRechazoTransferenciarecibe());
                    if (pstmt != null) {
                        pstmt.setInt(1, folio);
                        pstmt.setInt(2, year);
                        pstmt.setString(3, trans.getRamo());
                        pstmt.setInt(4, trans.getMeta());
                        rsResult = pstmt.executeQuery();
                        if (rsResult != null) {
                            transMeta = new TransferenciaMeta();
                            while (rsResult.next()) {
                                cont++;
                                movEstimacion = new MovOficioEstimacion();
                                transMeta.setIdentificador(identificador);
                                movEstimacion.setYear(rsResult.getInt("YEAR"));
                                movEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                movEstimacion.setRamo(rsResult.getString("RAMO"));
                                movEstimacion.setMeta(rsResult.getInt("META"));
                                movEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                movEstimacion.setValor(rsResult.getDouble("VALOR"));
                                movEstimacion.setPrograma(this.getResultSQLgetProgramaByRamoMetaMov(year, movEstimacion.getRamo(), movEstimacion.getMeta(), folio));
                                cantPropuesta += rsResult.getDouble("VALOR");
                                movEstimacionList.add(movEstimacion);
                                if (cont == 12) {
                                    transMeta.setMovOficioEstimacion(movEstimacionList);
                                    transMeta.setPropuestaEstimacion(cantPropuesta);
                                    cantPropuesta = 0.0;
                                    transMetaList.add(transMeta);
                                    cont = 0;
                                    identificador++;
                                    movEstimacionList = new ArrayList<MovOficioEstimacion>();
                                    transMeta = new TransferenciaMeta();
                                }
                            }
                            for (TransferenciaMeta ampRedMetaTemp : transMetaList) {
                                movEstimacionList = new ArrayList<MovOficioEstimacion>();
                                pstmt = null;
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficiosMetaTransferencia());
                                if (pstmt != null) {
                                    pstmt.setInt(1, folio);
                                    pstmt.setInt(2, year);
                                    pstmt.setString(3, ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                    pstmt.setInt(4, ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null) {
                                        if (rsResult.next()) {
                                            movOficioMeta = new MovimientoOficioMeta();
                                            movOficioMeta.setYear(year);
                                            movOficioMeta.setRamoId(rsResult.getString("RAMO"));
                                            movOficioMeta.setPrgId(rsResult.getString("PRG"));
                                            movOficioMeta.setMetaId(rsResult.getInt("META"));
                                            movOficioMeta.setMetaDescr(rsResult.getString("DESCR"));
                                            movOficioMeta.setCalculoId(rsResult.getString("CALCULO"));
                                            movOficioMeta.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                            movOficioMeta.setFinalidad(rsResult.getString("FINALIDAD"));
                                            movOficioMeta.setFuncion(rsResult.getString("FUNCION"));
                                            movOficioMeta.setSubfuncion(rsResult.getString("SUBFUNCION"));
                                            movOficioMeta.setCriterioTransversalidad(rsResult.getInt("CRITERIO"));
                                            movOficioMeta.setClasificacionFuncionalId(movOficioMeta.getFinalidad() + "."
                                                    + movOficioMeta.getFuncion() + "." + movOficioMeta.getSubfuncion());
                                            movOficioMeta.setTipoProy(rsResult.getString("TIPO_PROY"));
                                            movOficioMeta.setProyId(rsResult.getInt("PROY"));
                                            movOficioMeta.setLineaPedId(rsResult.getString("LINEA"));
                                            movOficioMeta.setCompromisoId(rsResult.getInt("TIPO_COMPROMISO"));
                                            movOficioMeta.setPonderacionId(rsResult.getString("PONDERADO"));
                                            movOficioMeta.setLineaSectorialId(rsResult.getString("LINEA_SECTORIAL"));
                                            movOficioMeta.setNva_creacion(rsResult.getString("NVA_CREACION"));
                                            transMeta.setMovOficioMeta(movOficioMeta);
                                        } else {
                                            movOficioMeta = new MovimientoOficioMeta();
                                            movOficioMeta.setYear(year);
                                            movOficioMeta.setRamoId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                            movOficioMeta.setPrgId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getPrograma());
                                            movOficioMeta.setMetaId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                            movOficioMeta.setNva_creacion("N");
                                        }
                                        ampRedMetaTemp.setMovOficioMeta(movOficioMeta);
                                        if (transMetaRList.isEmpty()) {
                                            transMetaRList.add(ampRedMetaTemp);
                                        } else {
                                            for (TransferenciaMeta meta : transMetaRList) {
                                                if (!meta.getMovOficioMeta().getRamoId().equals(ampRedMetaTemp.getMovOficioMeta().getRamoId())
                                                        && meta.getMovOficioMeta().getMetaId() != ampRedMetaTemp.getMovOficioMeta().getMetaId()) {
                                                    transMetaRList.add(ampRedMetaTemp);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    transMetaRList.addAll(this.getResultGetMovOficioMetaRechIntTransferencia(folio, year, trans.getTransferenciaAccionReqList()));
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transMetaRList;
    }

    public List<TransferenciaMeta> getResultGetMovOficioMetaRechIntTransferencia(int folio,
            int year, List<TransferenciaAccionReq> transfList) throws SQLException {
        TransferenciaMeta transMeta = null;
        List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
        List<TransferenciaMeta> transMetaRList = new ArrayList<TransferenciaMeta>();
        List<MovOficioEstimacion> movEstimacionList = new ArrayList<MovOficioEstimacion>();
        MovOficioEstimacion movEstimacion;
        MovimientoOficioMeta movOficioMeta = new MovimientoOficioMeta();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                for (TransferenciaAccionReq trans : transfList) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacionRechazoTransferenciarecibe());
                    if (pstmt != null) {
                        pstmt.setInt(1, folio);
                        pstmt.setInt(2, year);
                        pstmt.setString(3, trans.getRamo());
                        pstmt.setInt(4, trans.getMeta());
                        rsResult = pstmt.executeQuery();
                        if (rsResult != null) {
                            transMeta = new TransferenciaMeta();
                            while (rsResult.next()) {
                                cont++;
                                movEstimacion = new MovOficioEstimacion();
                                transMeta.setIdentificador(identificador);
                                movEstimacion.setYear(rsResult.getInt("YEAR"));
                                movEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                movEstimacion.setRamo(rsResult.getString("RAMO"));
                                movEstimacion.setMeta(rsResult.getInt("META"));
                                movEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                movEstimacion.setValor(rsResult.getDouble("VALOR"));
                                movEstimacion.setPrograma(this.getResultSQLgetProgramaByRamoMetaMov(year, movEstimacion.getRamo(), movEstimacion.getMeta(), folio));
                                cantPropuesta += rsResult.getDouble("VALOR");
                                movEstimacionList.add(movEstimacion);
                                if (cont == 12) {
                                    transMeta.setMovOficioEstimacion(movEstimacionList);
                                    transMeta.setPropuestaEstimacion(cantPropuesta);
                                    cantPropuesta = 0.0;
                                    transMetaList.add(transMeta);
                                    cont = 0;
                                    identificador++;
                                    movEstimacionList = new ArrayList<MovOficioEstimacion>();
                                    transMeta = new TransferenciaMeta();
                                }
                            }
                            for (TransferenciaMeta ampRedMetaTemp : transMetaList) {
                                movEstimacionList = new ArrayList<MovOficioEstimacion>();
                                pstmt = null;
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficiosMetaTransferencia());
                                if (pstmt != null) {
                                    pstmt.setInt(1, folio);
                                    pstmt.setInt(2, year);
                                    pstmt.setString(3, ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                    pstmt.setInt(4, ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null) {
                                        if (rsResult.next()) {
                                            movOficioMeta = new MovimientoOficioMeta();
                                            movOficioMeta.setYear(year);
                                            movOficioMeta.setRamoId(rsResult.getString("RAMO"));
                                            movOficioMeta.setPrgId(rsResult.getString("PRG"));
                                            movOficioMeta.setMetaId(rsResult.getInt("META"));
                                            movOficioMeta.setMetaDescr(rsResult.getString("DESCR"));
                                            movOficioMeta.setCalculoId(rsResult.getString("CALCULO"));
                                            movOficioMeta.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                            movOficioMeta.setFinalidad(rsResult.getString("FINALIDAD"));
                                            movOficioMeta.setFuncion(rsResult.getString("FUNCION"));
                                            movOficioMeta.setSubfuncion(rsResult.getString("SUBFUNCION"));
                                            movOficioMeta.setCriterioTransversalidad(rsResult.getInt("CRITERIO"));
                                            movOficioMeta.setClasificacionFuncionalId(movOficioMeta.getFinalidad() + "."
                                                    + movOficioMeta.getFuncion() + "." + movOficioMeta.getSubfuncion());
                                            movOficioMeta.setTipoProy(rsResult.getString("TIPO_PROY"));
                                            movOficioMeta.setProyId(rsResult.getInt("PROY"));
                                            movOficioMeta.setLineaPedId(rsResult.getString("LINEA"));
                                            movOficioMeta.setCompromisoId(rsResult.getInt("TIPO_COMPROMISO"));
                                            movOficioMeta.setPonderacionId(rsResult.getString("PONDERADO"));
                                            movOficioMeta.setLineaSectorialId(rsResult.getString("LINEA_SECTORIAL"));
                                            movOficioMeta.setNva_creacion(rsResult.getString("NVA_CREACION"));
                                            transMeta.setMovOficioMeta(movOficioMeta);
                                        } else {
                                            movOficioMeta = new MovimientoOficioMeta();
                                            movOficioMeta.setYear(year);
                                            movOficioMeta.setRamoId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getRamo());
                                            movOficioMeta.setPrgId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getPrograma());
                                            movOficioMeta.setMetaId(ampRedMetaTemp.getMovOficioEstimacion().get(0).getMeta());
                                            movOficioMeta.setNva_creacion("N");
                                        }
                                        ampRedMetaTemp.setMovOficioMeta(movOficioMeta);
                                        if (movOficioMeta.getNva_creacion().equals("S")) {
                                            if (transMetaRList.isEmpty()) {
                                                transMetaRList.add(ampRedMetaTemp);
                                            } else {
                                                for (TransferenciaMeta meta : transMetaRList) {
                                                    if (!meta.getMovOficioMeta().getRamoId().equals(ampRedMetaTemp.getMovOficioMeta().getRamoId())
                                                            && meta.getMovOficioMeta().getMetaId() != ampRedMetaTemp.getMovOficioMeta().getMetaId()) {
                                                        transMetaRList.add(ampRedMetaTemp);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transMetaRList;
    }

    public List<TransferenciaAccion> getResultGetMovOficioAccionTransferencia(int folio, int year,
            String tipoTransferencia) throws SQLException {
        TransferenciaAccion transAccion = null;
        List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
        List<MovOficioAccionEstimacion> movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
        MovOficioAccionEstimacion movAccionEstimacion;
        MovimientoOficioAccion movOficioAccion = new MovimientoOficioAccion();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionTransferencia());
                //pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionAmpRed());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        transAccion = new TransferenciaAccion();
                        while (rsResult.next()) {
                            cont++;
                            movAccionEstimacion = new MovOficioAccionEstimacion();
                            transAccion.setIdentificador(identificador);
                            movAccionEstimacion = new MovOficioAccionEstimacion();
                            movAccionEstimacion.setYear(rsResult.getInt("YEAR"));
                            movAccionEstimacion.setOficio(rsResult.getInt("OFICIO"));
                            movAccionEstimacion.setRamo(rsResult.getString("RAMO"));
                            movAccionEstimacion.setMeta(rsResult.getInt("META"));
                            movAccionEstimacion.setAccion(rsResult.getInt("ACCION"));
                            movAccionEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                            movAccionEstimacion.setValor(rsResult.getDouble("VALOR"));
                            movAccionEstimacion.setPrograma(this.getResultSQLgetProgramaByRamoMetaAccionMov(year,
                                    movAccionEstimacion.getRamo(), movAccionEstimacion.getMeta(), movAccionEstimacion.getAccion(), folio));
                            cantPropuesta += rsResult.getDouble("VALOR");
                            movAccionEstimacionList.add(movAccionEstimacion);
                            if (cont == 12) {
                                transAccion.setMovOficioAccionEstList(movAccionEstimacionList);
                                transAccion.setPropuestaEstimacion(cantPropuesta);
                                cantPropuesta = 0.0;
                                transAccionList.add(transAccion);
                                cont = 0;
                                movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
                                transAccion = new TransferenciaAccion();
                                identificador++;
                            }
                        }
                        for (TransferenciaAccion transAccionTemp : transAccionList) {
                            pstmt.close();
                            rsResult.close();
                            pstmt = null;
                            rsResult = null;
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficioAccionAmpRed());
                            if (pstmt != null) {
                                pstmt.setInt(1, folio);
                                pstmt.setInt(2, year);
                                pstmt.setString(3, transAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                pstmt.setInt(4, transAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                pstmt.setInt(5, transAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    if (rsResult.next()) {
                                        movOficioAccion = new MovimientoOficioAccion();
                                        movOficioAccion.setYear(year);
                                        movOficioAccion.setRamoId(rsResult.getString("RAMO"));
                                        movOficioAccion.setProgramaId(rsResult.getString("PRG"));
                                        movOficioAccion.setMetaId(rsResult.getInt("META"));
                                        movOficioAccion.setDeptoId(rsResult.getString("DEPTO"));
                                        movOficioAccion.setAccionId(rsResult.getInt("ACCION"));
                                        movOficioAccion.setAccionDescr(rsResult.getString("DESCR"));
                                        movOficioAccion.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                        movOficioAccion.setCalculo(rsResult.getString("CALCULO"));
                                        movOficioAccion.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                        movOficioAccion.setLinea(rsResult.getString("LINEA"));
                                        movOficioAccion.setGrupoPoblacional(rsResult.getInt("GRUPO_POBLACION"));
                                        movOficioAccion.setBenefHombre(rsResult.getInt("BENEF_HOMBRE"));
                                        movOficioAccion.setBenefMujer(rsResult.getInt("BENEF_MUJER"));
                                        movOficioAccion.setMunicipio(rsResult.getString("MPO"));
                                        movOficioAccion.setLocalidad(rsResult.getInt("LOCALIDAD"));
                                        movOficioAccion.setObra(rsResult.getString("OBRA"));
                                        movOficioAccion.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                                        movOficioAccion.setNvaCreacion(rsResult.getString("NVA_CREACION"));
                                    } else {
                                        movOficioAccion = new MovimientoOficioAccion();
                                        movOficioAccion.setYear(year);
                                        movOficioAccion.setRamoId(transAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                        movOficioAccion.setProgramaId(transAccionTemp.getMovOficioAccionEstList().get(0).getPrograma());
                                        movOficioAccion.setMetaId(transAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                        movOficioAccion.setAccionId(transAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                        movOficioAccion.setNvaCreacion("N");
                                    }
                                    transAccionTemp.setMovOficioAccion(movOficioAccion);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
                pstmt = null;
                rsResult = null;
            }
        }
        return transAccionList;
    }

    public List<TransferenciaAccion> getResultGetMovOficioAccionRechazoTransferencia(int folio, int year, List<Transferencia> transList) throws SQLException {
        TransferenciaAccion transAccion = null;
        List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
        List<TransferenciaAccion> transAccionRList = new ArrayList<TransferenciaAccion>();
        List<MovOficioAccionEstimacion> movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
        MovOficioAccionEstimacion movAccionEstimacion;
        MovimientoOficioAccion movOficioAccion = new MovimientoOficioAccion();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                for (Transferencia trans : transList) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionRechazoTransferencia());
                    //pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionAmpRed());
                    if (pstmt != null) {
                        pstmt.setInt(1, folio);
                        pstmt.setInt(2, year);
                        pstmt.setString(3, trans.getRamo());
                        pstmt.setInt(4, trans.getMeta());
                        pstmt.setInt(5, trans.getAccion());
                        rsResult = pstmt.executeQuery();
                        if (rsResult != null) {
                            transAccion = new TransferenciaAccion();
                            while (rsResult.next()) {
                                cont++;
                                movAccionEstimacion = new MovOficioAccionEstimacion();
                                transAccion.setIdentificador(identificador);
                                movAccionEstimacion = new MovOficioAccionEstimacion();
                                movAccionEstimacion.setYear(rsResult.getInt("YEAR"));
                                movAccionEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                movAccionEstimacion.setRamo(rsResult.getString("RAMO"));
                                movAccionEstimacion.setMeta(rsResult.getInt("META"));
                                movAccionEstimacion.setAccion(rsResult.getInt("ACCION"));
                                movAccionEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                movAccionEstimacion.setValor(rsResult.getDouble("VALOR"));
                                movAccionEstimacion.setPrograma(this.getResultSQLgetProgramaByRamoMetaAccionMov(year,
                                        movAccionEstimacion.getRamo(), movAccionEstimacion.getMeta(), movAccionEstimacion.getAccion(), folio));
                                cantPropuesta += rsResult.getDouble("VALOR");
                                movAccionEstimacionList.add(movAccionEstimacion);
                                if (cont == 12) {
                                    transAccion.setMovOficioAccionEstList(movAccionEstimacionList);
                                    transAccion.setPropuestaEstimacion(cantPropuesta);
                                    cantPropuesta = 0.0;
                                    transAccionList.add(transAccion);
                                    cont = 0;
                                    movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
                                    transAccion = new TransferenciaAccion();
                                    identificador++;
                                }
                            }
                            for (TransferenciaAccion transAccionTemp : transAccionList) {
                                pstmt = null;
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficioAccionAmpRed());
                                if (pstmt != null) {
                                    pstmt.setInt(1, folio);
                                    pstmt.setInt(2, year);
                                    pstmt.setString(3, transAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                    pstmt.setInt(4, transAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                    pstmt.setInt(5, transAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null) {
                                        if (rsResult.next()) {
                                            movOficioAccion = new MovimientoOficioAccion();
                                            movOficioAccion.setYear(year);
                                            movOficioAccion.setRamoId(rsResult.getString("RAMO"));
                                            movOficioAccion.setProgramaId(rsResult.getString("PRG"));
                                            movOficioAccion.setMetaId(rsResult.getInt("META"));
                                            movOficioAccion.setDeptoId(rsResult.getString("DEPTO"));
                                            movOficioAccion.setAccionId(rsResult.getInt("ACCION"));
                                            movOficioAccion.setAccionDescr(rsResult.getString("DESCR"));
                                            movOficioAccion.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                            movOficioAccion.setCalculo(rsResult.getString("CALCULO"));
                                            movOficioAccion.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                            movOficioAccion.setLinea(rsResult.getString("LINEA"));
                                            movOficioAccion.setGrupoPoblacional(rsResult.getInt("GRUPO_POBLACION"));
                                            movOficioAccion.setBenefHombre(rsResult.getInt("BENEF_HOMBRE"));
                                            movOficioAccion.setBenefMujer(rsResult.getInt("BENEF_MUJER"));
                                            movOficioAccion.setMunicipio(rsResult.getString("MPO"));
                                            movOficioAccion.setLocalidad(rsResult.getInt("LOCALIDAD"));
                                            movOficioAccion.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                                            movOficioAccion.setNvaCreacion(rsResult.getString("NVA_CREACION"));
                                            movOficioAccion.setObra(rsResult.getString("OBRA"));
                                        } else {
                                            movOficioAccion = new MovimientoOficioAccion();
                                            movOficioAccion.setYear(year);
                                            movOficioAccion.setRamoId(transAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                            movOficioAccion.setProgramaId(transAccionTemp.getMovOficioAccionEstList().get(0).getPrograma());
                                            movOficioAccion.setMetaId(transAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                            movOficioAccion.setAccionId(transAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                            movOficioAccion.setNvaCreacion("N");
                                        }
                                        transAccionTemp.setMovOficioAccion(movOficioAccion);
                                        if (transAccionRList.isEmpty()) {
                                            transAccionRList.add(transAccionTemp);
                                        } else {
                                            for (TransferenciaAccion accion : transAccionRList) {
                                                if (!accion.getMovOficioAccion().getRamoId().equals(transAccionTemp.getMovOficioAccion().getRamoId())
                                                        && accion.getMovOficioAccion().getMetaId() != transAccionTemp.getMovOficioAccion().getMetaId()
                                                        && accion.getMovOficioAccion().getAccionId() != transAccionTemp.getMovOficioAccion().getAccionId()) {
                                                    transAccionRList.add(transAccionTemp);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    transAccionRList.addAll(this.getResultGetMovOficioAccionRecIntTransferencia(folio, year, trans.getTransferenciaAccionReqList()));
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transAccionRList;
    }

    public List<TransferenciaAccion> getResultGetMovOficioAccionRecIntTransferencia(int folio, int year, List<TransferenciaAccionReq> transList) throws SQLException {
        TransferenciaAccion transAccion = null;
        List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
        List<TransferenciaAccion> transAccionRList = new ArrayList<TransferenciaAccion>();
        List<MovOficioAccionEstimacion> movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
        MovOficioAccionEstimacion movAccionEstimacion;
        MovimientoOficioAccion movOficioAccion = new MovimientoOficioAccion();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cantPropuesta = 0.0;
        int identificador = 0;
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                for (TransferenciaAccionReq trans : transList) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionRechazoTransferencia());
                    //pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionAmpRed());
                    if (pstmt != null) {
                        pstmt.setInt(1, folio);
                        pstmt.setInt(2, year);
                        pstmt.setString(3, trans.getRamo());
                        pstmt.setInt(4, trans.getMeta());
                        pstmt.setInt(5, trans.getAccion());
                        rsResult = pstmt.executeQuery();
                        if (rsResult != null) {
                            transAccion = new TransferenciaAccion();
                            while (rsResult.next()) {
                                cont++;
                                movAccionEstimacion = new MovOficioAccionEstimacion();
                                transAccion.setIdentificador(identificador);
                                movAccionEstimacion = new MovOficioAccionEstimacion();
                                movAccionEstimacion.setYear(rsResult.getInt("YEAR"));
                                movAccionEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                movAccionEstimacion.setRamo(rsResult.getString("RAMO"));
                                movAccionEstimacion.setMeta(rsResult.getInt("META"));
                                movAccionEstimacion.setAccion(rsResult.getInt("ACCION"));
                                movAccionEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                movAccionEstimacion.setValor(rsResult.getDouble("VALOR"));
                                movAccionEstimacion.setPrograma(this.getResultSQLgetProgramaByRamoMetaAccionMov(year,
                                        movAccionEstimacion.getRamo(), movAccionEstimacion.getMeta(), movAccionEstimacion.getAccion(), folio));
                                cantPropuesta += rsResult.getDouble("VALOR");
                                movAccionEstimacionList.add(movAccionEstimacion);
                                if (cont == 12) {
                                    transAccion.setMovOficioAccionEstList(movAccionEstimacionList);
                                    transAccion.setPropuestaEstimacion(cantPropuesta);
                                    cantPropuesta = 0.0;
                                    transAccionList.add(transAccion);
                                    cont = 0;
                                    movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
                                    transAccion = new TransferenciaAccion();
                                    identificador++;
                                }
                            }
                            for (TransferenciaAccion transAccionTemp : transAccionList) {
                                pstmt = null;
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovOficioAccionAmpRed());
                                if (pstmt != null) {
                                    pstmt.setInt(1, folio);
                                    pstmt.setInt(2, year);
                                    pstmt.setString(3, transAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                    pstmt.setInt(4, transAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                    pstmt.setInt(5, transAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null) {
                                        if (rsResult.next()) {
                                            movOficioAccion = new MovimientoOficioAccion();
                                            movOficioAccion.setYear(year);
                                            movOficioAccion.setRamoId(rsResult.getString("RAMO"));
                                            movOficioAccion.setProgramaId(rsResult.getString("PRG"));
                                            movOficioAccion.setMetaId(rsResult.getInt("META"));
                                            movOficioAccion.setDeptoId(rsResult.getString("DEPTO"));
                                            movOficioAccion.setAccionId(rsResult.getInt("ACCION"));
                                            movOficioAccion.setAccionDescr(rsResult.getString("DESCR"));
                                            movOficioAccion.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                                            movOficioAccion.setCalculo(rsResult.getString("CALCULO"));
                                            movOficioAccion.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                            movOficioAccion.setLinea(rsResult.getString("LINEA"));
                                            movOficioAccion.setGrupoPoblacional(rsResult.getInt("GRUPO_POBLACION"));
                                            movOficioAccion.setBenefHombre(rsResult.getInt("BENEF_HOMBRE"));
                                            movOficioAccion.setBenefMujer(rsResult.getInt("BENEF_MUJER"));
                                            movOficioAccion.setMunicipio(rsResult.getString("MPO"));
                                            movOficioAccion.setLocalidad(rsResult.getInt("LOCALIDAD"));
                                            movOficioAccion.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                                            movOficioAccion.setNvaCreacion(rsResult.getString("NVA_CREACION"));
                                            movOficioAccion.setObra(rsResult.getString("OBRA"));
                                        } else {
                                            movOficioAccion = new MovimientoOficioAccion();
                                            movOficioAccion.setYear(year);
                                            movOficioAccion.setRamoId(transAccionTemp.getMovOficioAccionEstList().get(0).getRamo());
                                            movOficioAccion.setProgramaId(transAccionTemp.getMovOficioAccionEstList().get(0).getPrograma());
                                            movOficioAccion.setMetaId(transAccionTemp.getMovOficioAccionEstList().get(0).getMeta());
                                            movOficioAccion.setAccionId(transAccionTemp.getMovOficioAccionEstList().get(0).getAccion());
                                            movOficioAccion.setNvaCreacion("N");
                                        }
                                        transAccionTemp.setMovOficioAccion(movOficioAccion);
                                        if (movOficioAccion.getNvaCreacion().equals("S")) {
                                            if (transAccionRList.isEmpty()) {
                                                transAccionRList.add(transAccionTemp);
                                            } else {
                                                for (TransferenciaAccion accion : transAccionRList) {
                                                    if (!accion.getMovOficioAccion().getRamoId().equals(transAccionTemp.getMovOficioAccion().getRamoId())
                                                            && accion.getMovOficioAccion().getMetaId() != transAccionTemp.getMovOficioAccion().getMetaId()
                                                            && accion.getMovOficioAccion().getAccionId() != transAccionTemp.getMovOficioAccion().getAccionId()) {
                                                        transAccionRList.add(transAccionTemp);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transAccionRList;
    }

    public List<TransferenciaAccionReq> getTransferenciaAccionReqByFolioConsec(int folio, int consec) throws SQLException {
        List<TransferenciaAccionReq> transferenciaAccionReqList = new ArrayList<TransferenciaAccionReq>();
        TransferenciaAccionReq transferenciaAccionReq = new TransferenciaAccionReq();
        MovOficiosAccionReq requerimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 1;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSLQgetTransferenciasByFolioConsec());
                pstmt.setInt(1, folio);
                pstmt.setInt(2, consec);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            transferenciaAccionReq = new TransferenciaAccionReq();
                            transferenciaAccionReq.setIdentidicador(identificador);
                            transferenciaAccionReq.setConsecutivo(rsResult.getInt("CONSEC"));
                            transferenciaAccionReq.setRamo(rsResult.getString("RAMO"));
                            transferenciaAccionReq.setDepto(rsResult.getString("DEPTO"));
                            transferenciaAccionReq.setFinalidad(rsResult.getString("FINALIDAD"));
                            transferenciaAccionReq.setFuncion(rsResult.getString("FUNCION"));
                            transferenciaAccionReq.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            transferenciaAccionReq.setPrgConac(rsResult.getString("PRG_CONAC"));
                            transferenciaAccionReq.setPrograma(rsResult.getString("PRG"));
                            transferenciaAccionReq.setTipoProy(rsResult.getString("TIPO_PROY"));
                            transferenciaAccionReq.setProy(rsResult.getInt("PROYECTO"));
                            transferenciaAccionReq.setMeta(rsResult.getInt("META"));
                            transferenciaAccionReq.setAccion(rsResult.getInt("ACCION"));
                            transferenciaAccionReq.setPartida(rsResult.getString("PARTIDA"));
                            transferenciaAccionReq.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            transferenciaAccionReq.setFuente(rsResult.getString("FUENTE"));
                            transferenciaAccionReq.setFondo(rsResult.getString("FONDO"));
                            transferenciaAccionReq.setRecurso(rsResult.getString("RECURSO"));
                            transferenciaAccionReq.setMunicipio(rsResult.getString("MUNICIPIO"));
                            transferenciaAccionReq.setDelegacion(rsResult.getInt("DELEGACION"));
                            transferenciaAccionReq.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            transferenciaAccionReq.setImporte(rsResult.getDouble("IMPTE"));
                            transferenciaAccionReq.setAccionReq(rsResult.getInt("REQUERIMIENTO"));
                            transferenciaAccionReq.setTipoMovTransf(rsResult.getString("TIPOMOV"));
                            transferenciaAccionReqList.add(transferenciaAccionReq);
                            identificador++;
                        }
                        rsResult.close();
                        pstmt.close();
                        for (TransferenciaAccionReq transferenciaAccionReqTemp : transferenciaAccionReqList) {
                            pstmt.close();
                            rsResult.close();
                            pstmt = null;
                            rsResult = null;
                            if (transferenciaAccionReqTemp.getImporte() > 0) {
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionRequerimientoByAmpliacion());
                                if (pstmt != null) {
                                    pstmt.setInt(1, folio);
                                    pstmt.setString(2, transferenciaAccionReqTemp.getRamo());
                                    pstmt.setString(3, transferenciaAccionReqTemp.getPrograma());
                                    pstmt.setInt(4, transferenciaAccionReqTemp.getMeta());
                                    pstmt.setInt(5, transferenciaAccionReqTemp.getAccion());
                                    pstmt.setInt(6, transferenciaAccionReqTemp.getAccionReq());
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null) {
                                        while (rsResult.next()) {
                                            requerimiento = new MovOficiosAccionReq();
                                            requerimiento.setOficio(rsResult.getString("OFICIO"));
                                            requerimiento.setYear(rsResult.getInt("YEAR"));
                                            requerimiento.setRamo(rsResult.getString("RAMO"));
                                            requerimiento.setPrograma(rsResult.getString("PRG"));
                                            requerimiento.setDepto(rsResult.getString("DEPTO"));
                                            requerimiento.setMeta(rsResult.getInt("META"));
                                            requerimiento.setAccion(rsResult.getInt("ACCION"));
                                            requerimiento.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                                            requerimiento.setReqDescr(rsResult.getString("DESCR"));
                                            requerimiento.setFuente(rsResult.getString("FUENTE"));
                                            requerimiento.setFondo(rsResult.getString("FONDO"));
                                            requerimiento.setRecurso(rsResult.getString("RECURSO"));
                                            requerimiento.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                            requerimiento.setPartida(rsResult.getString("PARTIDA"));
                                            requerimiento.setRelLaboral(rsResult.getString("REL_LABORAL"));
                                            requerimiento.setCantidad(rsResult.getDouble("CANTIDAD"));
                                            requerimiento.setCostoUnitario(rsResult.getDouble("COSTO_UNITARIO"));
                                            requerimiento.setCostoAnual(rsResult.getDouble("COSTO_ANUAL"));
                                            requerimiento.setEne(rsResult.getDouble("ENE"));
                                            requerimiento.setFeb(rsResult.getDouble("FEB"));
                                            requerimiento.setMar(rsResult.getDouble("MAR"));
                                            requerimiento.setAbr(rsResult.getDouble("ABR"));
                                            requerimiento.setMay(rsResult.getDouble("MAY"));
                                            requerimiento.setJun(rsResult.getDouble("JUN"));
                                            requerimiento.setJul(rsResult.getDouble("JUL"));
                                            requerimiento.setAgo(rsResult.getDouble("AGO"));
                                            requerimiento.setSep(rsResult.getDouble("SEP"));
                                            requerimiento.setOct(rsResult.getDouble("OCT"));
                                            requerimiento.setNov(rsResult.getDouble("NOV"));
                                            requerimiento.setDic(rsResult.getDouble("DIC"));
                                            requerimiento.setConsec(consec);
                                            if (rsResult.getString("ARTICULO") == null) {
                                                requerimiento.setArticulo("0");
                                            } else {
                                                requerimiento.setArticulo(rsResult.getString("ARTICULO"));
                                            }
                                            requerimiento.setTransitorio(rsResult.getString("TRANSITORIO"));
                                            requerimiento.setJustificacion(rsResult.getString("JUSTIFICACION"));
                                            transferenciaAccionReqTemp.setMovOficioAccionReq(requerimiento);
                                        }
                                    }
                                    pstmt.close();
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transferenciaAccionReqList;
    }

    public List<TransferenciaAccionReq> getTransferenciaAccionReqByTipoOficio(int folio, String tipoOficio) throws SQLException {
        List<TransferenciaAccionReq> transferenciaAccionReqList = new ArrayList<TransferenciaAccionReq>();
        TransferenciaAccionReq transferenciaAccionReq = new TransferenciaAccionReq();
        MovOficiosAccionReq requerimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 1;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAmpliacionesByTipoOficio());
                pstmt.setInt(1, folio);
                pstmt.setString(1, tipoOficio);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            transferenciaAccionReq = new TransferenciaAccionReq();
                            transferenciaAccionReq.setIdentidicador(identificador);
                            transferenciaAccionReq.setConsecutivo(rsResult.getInt("CONSEC"));
                            transferenciaAccionReq.setRamo(rsResult.getString("RAMO"));
                            transferenciaAccionReq.setDepto(rsResult.getString("DEPTO"));
                            transferenciaAccionReq.setFinalidad(rsResult.getString("FINALIDAD"));
                            transferenciaAccionReq.setFuncion(rsResult.getString("FUNCION"));
                            transferenciaAccionReq.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            transferenciaAccionReq.setPrgConac(rsResult.getString("PRG_CONAC"));
                            transferenciaAccionReq.setPrograma(rsResult.getString("PRG"));
                            transferenciaAccionReq.setTipoProy(rsResult.getString("TIPO_PROY"));
                            transferenciaAccionReq.setProy(rsResult.getInt("PROYECTO"));
                            transferenciaAccionReq.setMeta(rsResult.getInt("META"));
                            transferenciaAccionReq.setAccion(rsResult.getInt("ACCION"));
                            transferenciaAccionReq.setPartida(rsResult.getString("PARTIDA"));
                            transferenciaAccionReq.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            transferenciaAccionReq.setFuente(rsResult.getString("FUENTE"));
                            transferenciaAccionReq.setFondo(rsResult.getString("FONDO"));
                            transferenciaAccionReq.setRecurso(rsResult.getString("RECURSO"));
                            transferenciaAccionReq.setMunicipio(rsResult.getString("MUNICIPIO"));
                            transferenciaAccionReq.setDelegacion(rsResult.getInt("DELEGACION"));
                            transferenciaAccionReq.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            transferenciaAccionReq.setImporte(rsResult.getDouble("REL_LABORAL"));
                            transferenciaAccionReq.setEstatus(rsResult.getString("STATUS"));
                            transferenciaAccionReqList.add(transferenciaAccionReq);
                            identificador++;
                        }
                        rsResult.close();
                        pstmt.close();
                        for (TransferenciaAccionReq transferenciaAccionReqTemp : transferenciaAccionReqList) {
                            if (transferenciaAccionReqTemp.getImporte() > 0) {
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionRequerimientoByAmpliacion());
                                if (pstmt != null) {
                                    pstmt.setInt(1, folio);
                                    pstmt.setString(2, transferenciaAccionReqTemp.getRamo());
                                    pstmt.setString(3, transferenciaAccionReqTemp.getPrograma());
                                    pstmt.setInt(4, transferenciaAccionReqTemp.getMeta());
                                    pstmt.setInt(5, transferenciaAccionReqTemp.getAccion());
                                    pstmt.setInt(6, transferenciaAccionReqTemp.getConsecutivo());
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null) {
                                        while (rsResult.next()) {
                                            requerimiento = new MovOficiosAccionReq();
                                            requerimiento.setOficio(rsResult.getString("OFICIO"));
                                            requerimiento.setYear(rsResult.getInt("YEAR"));
                                            requerimiento.setRamo(rsResult.getString("RAMO"));
                                            requerimiento.setPrograma(rsResult.getString("PRG"));
                                            requerimiento.setDepto(rsResult.getString("DEPTO"));
                                            requerimiento.setMeta(rsResult.getInt("META"));
                                            requerimiento.setAccion(rsResult.getInt("ACCION"));
                                            requerimiento.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                                            requerimiento.setReqDescr(rsResult.getString("DESCR"));
                                            requerimiento.setFuente(rsResult.getString("FUENTE"));
                                            requerimiento.setFondo(rsResult.getString("FONDO"));
                                            requerimiento.setRecurso(rsResult.getString("RECURSO"));
                                            requerimiento.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                                            requerimiento.setPartida(rsResult.getString("PARTIDA"));
                                            requerimiento.setRelLaboral(rsResult.getString("REL_LABORAL"));
                                            requerimiento.setCantidad(rsResult.getDouble("CANTIDAD"));
                                            requerimiento.setCostoUnitario(rsResult.getDouble("COSTO_UNITARIO"));
                                            requerimiento.setCostoAnual(rsResult.getDouble("COSTO_ANUAL"));
                                            requerimiento.setEne(rsResult.getDouble("ENE"));
                                            requerimiento.setFeb(rsResult.getDouble("FEB"));
                                            requerimiento.setMar(rsResult.getDouble("MAR"));
                                            requerimiento.setAbr(rsResult.getDouble("ABR"));
                                            requerimiento.setMay(rsResult.getDouble("MAY"));
                                            requerimiento.setJun(rsResult.getDouble("JUN"));
                                            requerimiento.setJul(rsResult.getDouble("JUL"));
                                            requerimiento.setAgo(rsResult.getDouble("AGO"));
                                            requerimiento.setSep(rsResult.getDouble("SEP"));
                                            requerimiento.setOct(rsResult.getDouble("OCT"));
                                            requerimiento.setNov(rsResult.getDouble("NOV"));
                                            requerimiento.setDic(rsResult.getDouble("DIC"));
                                            if (rsResult.getString("ARTICULO") == null) {
                                                requerimiento.setArticulo("0");
                                            } else {
                                                requerimiento.setArticulo(rsResult.getString("ARTICULO"));
                                            }
                                            requerimiento.setJustificacion(rsResult.getString("JUSTIFICACION"));
                                            transferenciaAccionReqTemp.setMovOficioAccionReq(requerimiento);
                                        }
                                    }
                                    pstmt.close();
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transferenciaAccionReqList;
    }

    public List<TransferenciaMeta> getResultSQLgetMovEstimacionTransferencia(int folio, int year, String tipoTransferencia) throws SQLException {
        List<MovOficioEstimacion> movOficioEstimacionList = new ArrayList<MovOficioEstimacion>();
        List<MovOficioEstimacion> movOficioEstimacionTempList = new ArrayList<MovOficioEstimacion>();
        List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
        TransferenciaMeta transMeta;
        MovOficioEstimacion movOficioEstimacion;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacionTransferencia());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    //pstmt.setString(3, tipoTransferencia);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movOficioEstimacion = new MovOficioEstimacion();
                            movOficioEstimacion.setOficio(rsResult.getInt("OFICIO"));
                            movOficioEstimacion.setYear(rsResult.getInt("YEAR"));
                            movOficioEstimacion.setRamo(rsResult.getString("RAMO"));
                            movOficioEstimacion.setMeta(rsResult.getInt("META"));
                            movOficioEstimacionList.add(movOficioEstimacion);
                        }

                        for (MovOficioEstimacion movTemp : movOficioEstimacionList) {
                            identificador++;
                            transMeta = new TransferenciaMeta();
                            pstmt = null;
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovEstimacionCompletoTransferencia());
                            movOficioEstimacionTempList = new ArrayList<MovOficioEstimacion>();
                            if (pstmt != null) {
                                pstmt.setInt(1, movTemp.getOficio());
                                pstmt.setInt(2, movTemp.getYear());
                                pstmt.setString(3, movTemp.getRamo());
                                pstmt.setInt(4, movTemp.getMeta());
                                //pstmt.setString(5, tipoTransferencia);
                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    while (rsResult.next()) {
                                        movOficioEstimacion = new MovOficioEstimacion();
                                        movOficioEstimacion.setYear(rsResult.getInt("YEAR"));
                                        movOficioEstimacion.setOficio(rsResult.getInt("OFICIO"));
                                        movOficioEstimacion.setRamo(rsResult.getString("RAMO"));
                                        movOficioEstimacion.setMeta(rsResult.getInt("META"));
                                        movOficioEstimacion.setPeriodo(rsResult.getInt("PERIODO"));
                                        movOficioEstimacion.setValor(rsResult.getDouble("VALOR"));
                                        movOficioEstimacion.setPrograma(this.getResultSQLgetProgramaByRamoMeta(movOficioEstimacion.getYear(),
                                                movOficioEstimacion.getRamo(), movOficioEstimacion.getMeta()));
                                        movOficioEstimacionTempList.add(movOficioEstimacion);
                                    }
                                    transMeta.setIdentificador(identificador);
                                    transMeta.setMovOficioEstimacion(movOficioEstimacionTempList);
                                    transMetaList.add(transMeta);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transMetaList;
    }

    public List<TransferenciaAccion> getResultSQLgetMovAccionEstimacionTransferencia(int folio, int year, String tipoTransferencia) throws SQLException {
        List<MovOficioAccionEstimacion> movOficioAccionEstList = new ArrayList<MovOficioAccionEstimacion>();
        List<MovOficioAccionEstimacion> movOficioAccionEsTempList = new ArrayList<MovOficioAccionEstimacion>();
        List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
        TransferenciaAccion transAccion = new TransferenciaAccion();
        int identificado = 0;
        MovOficioAccionEstimacion movOficioAccionEst;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionTransferencia());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    //pstmt.setString(3, tipoTransferencia);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movOficioAccionEst = new MovOficioAccionEstimacion();
                            movOficioAccionEst.setOficio(rsResult.getInt("OFICIO"));
                            movOficioAccionEst.setYear(rsResult.getInt("YEAR"));
                            movOficioAccionEst.setRamo(rsResult.getString("RAMO"));
                            movOficioAccionEst.setMeta(rsResult.getInt("META"));
                            movOficioAccionEst.setAccion(rsResult.getInt("ACCION"));
                            movOficioAccionEstList.add(movOficioAccionEst);
                        }
                        for (MovOficioAccionEstimacion movTemp : movOficioAccionEstList) {
                            transAccion = new TransferenciaAccion();
                            pstmt = null;
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovAccionEstimacionCompleto());
                            movOficioAccionEsTempList = new ArrayList<MovOficioAccionEstimacion>();
                            if (pstmt != null) {
                                pstmt.setInt(1, movTemp.getOficio());
                                pstmt.setInt(2, movTemp.getYear());
                                pstmt.setString(3, movTemp.getRamo());
                                pstmt.setInt(4, movTemp.getMeta());
                                pstmt.setInt(5, movTemp.getAccion());
                                //pstmt.setString(6, tipoTransferencia);
                                rsResult = pstmt.executeQuery();
                                if (rsResult != null) {
                                    while (rsResult.next()) {
                                        movOficioAccionEst = new MovOficioAccionEstimacion();
                                        movOficioAccionEst.setYear(rsResult.getInt("YEAR"));
                                        movOficioAccionEst.setOficio(rsResult.getInt("OFICIO"));
                                        movOficioAccionEst.setRamo(rsResult.getString("RAMO"));
                                        movOficioAccionEst.setMeta(rsResult.getInt("META"));
                                        movOficioAccionEst.setAccion(rsResult.getInt("ACCION"));
                                        movOficioAccionEst.setPeriodo(rsResult.getInt("PERIODO"));
                                        movOficioAccionEst.setValor(rsResult.getDouble("VALOR"));
                                        movOficioAccionEst.setPrograma(this.getResultSQLgetProgramaByRamoMetaAccion(movOficioAccionEst.getYear(),
                                                movOficioAccionEst.getRamo(), movOficioAccionEst.getMeta(), movOficioAccionEst.getAccion()));
                                        movOficioAccionEsTempList.add(movOficioAccionEst);
                                    }
                                    transAccion.setIdentificador(identificado);
                                    transAccion.setMovOficioAccionEstList(movOficioAccionEsTempList);
                                    transAccionList.add(transAccion);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transAccionList;
    }

    public boolean getResultSQLInsertMetaFromMovoficios(int folio, int year, String ramo, int meta, int claveMeta, String tipoOficio) {
        boolean resultado = false;
        int periodo = 0;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                periodo = getResultSQLgetPeriodoByFechaBitmovtos(folio, tipoOficio);
                if (periodo > 0) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLGetInsertMetaFromMovoficios(folio, year, ramo, meta, claveMeta, periodo));
                }

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMovoficiosMeta(int metaNva, int oficio, int year, String ramo, int metaAnt) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovoficiosMeta(metaNva, oficio, year, ramo, metaAnt));
                if (resultado) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovoficiosEstimacion(metaNva, oficio, year, ramo, metaAnt));
                }

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMovoficiosMetaAccion(int metaNva, int oficio, int year, String ramo, int metaAnt) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovoficiosMetaAccion(metaNva, oficio, year, ramo, metaAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertAccionFromMovoficios(int folio, int year, String ramo, int meta, int accion, int claveAccionNueva, String tipoOficio) {
        boolean resultado = false;
        int periodo = 0;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                periodo = getResultSQLgetPeriodoByFechaBitmovtos(folio, tipoOficio);
                if (periodo > 0) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLGetInsertAccionFromMovoficios(folio, year, ramo, meta, accion, claveAccionNueva, periodo));
                }

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMovoficiosAccion(int accionNva, int oficio, int year, String ramo, int meta, int accionAnt) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovoficiosAccion(accionNva, oficio, year, ramo, meta, accionAnt));
                if (resultado) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovoficiosAccionEstimacion(accionNva, oficio, year, ramo, meta, accionAnt));
                }

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMovoficiosAccionReq(int accionNva, int oficio, int year, String ramo, int meta, int accionAnt) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovoficiosAccionReq(accionNva, oficio, year, ramo, meta, accionAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultInsertBitMovto(int oficio, String appLogin, String autorizo, String impFirma,
            String tipoOficio, String terminal, int tipoFlujo, String tipoUsr, String fecha) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertBitMovto(oficio, appLogin, autorizo, impFirma, tipoOficio, terminal, tipoFlujo, tipoUsr, fecha));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertRequerimientoFromMovoficios(int reqNvo, int folio, int year, String ramo, String programa, String depto,
            int meta, int accion, int reqAnt) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLGetInsertRequerimientoFromMovoficios(reqNvo, folio, year, ramo, programa, depto, meta, accion, reqAnt));
                if (!resultado) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    //bitacora.setStrInformacion("", new Throwable());
                    bitacora.grabaBitacora();
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMovoficiosRequerimiento(int reqNvo, int folio, int year, String ramo, String programa, String depto,
            int meta, int accion, int reqAnt) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLGetUpdateMovoficiosRquerimiento(reqNvo, folio, year, ramo, programa, depto, meta, accion, reqAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public String getRestulSQLCallAmpliacionCodPPTO(String oficio, int year, String ramo, String depto, String finalidad, String funcion,
            String subFuncion, String progConac, String programa, String tipoProyecto, int proyecto,
            int meta, int accion, String partida, String tipoGasto, String fuente,
            String fondo, String recurso, String municipio, int delegacion, String relLaboral,
            int requerimiento, int mes) throws SQLException {

        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            CallableStatement clstm = null;
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLGetProcedureAmpliacionCodPPTO());
                if (clstm != null) {

                    clstm.setString(1, oficio);
                    clstm.setInt(2, year);
                    clstm.setString(3, ramo);
                    clstm.setString(4, depto);
                    clstm.setString(5, finalidad);
                    clstm.setString(6, funcion);
                    clstm.setString(7, subFuncion);
                    clstm.setString(8, progConac);
                    clstm.setString(9, programa);
                    clstm.setString(10, tipoProyecto);
                    clstm.setInt(11, proyecto);
                    clstm.setInt(12, meta);
                    clstm.setInt(13, accion);
                    clstm.setString(14, partida);
                    clstm.setString(15, tipoGasto);
                    clstm.setString(16, fuente);
                    clstm.setString(17, fondo);
                    clstm.setString(18, recurso);
                    clstm.setString(19, municipio);
                    clstm.setInt(20, delegacion);
                    clstm.setString(21, relLaboral);
                    clstm.setInt(22, requerimiento);
                    clstm.setInt(23, mes);

                    clstm.executeQuery();
                }
            } catch (SQLException sqle) {
                try {
                    conectaBD.transaccionRollback();
                    resultado = false;
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(sqle, new Throwable());
                    bitacora.grabaBitacora();
                    mensaje = sqle.getMessage();
                } catch (Exception ex) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(ex, new Throwable());
                    bitacora.grabaBitacora();
                    resultado = false;
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            } finally {
                clstm.close();
            }
        }
        if (resultado) {
            mensaje = "exito";
        }
        return mensaje;
    }

    public List<TipoOficio> getResultSQLGetStatusTipoOficioByOficio(int oficio) throws SQLException {
        List<TipoOficio> tipoOficioList = new ArrayList<TipoOficio>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            TipoOficio tipoOficio = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetStatusTipoOficioByOficio(oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoOficio = new TipoOficio();
                            tipoOficio.setStatusTipoOficio(rsResult.getString("STATUS"));
                            tipoOficioList.add(tipoOficio);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return tipoOficioList;
    }

    public int getResultSQLCountTipoOficioByOficioEstatus(int oficio, String estatus) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountTipoOficioByOficioEstatus(oficio, estatus));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("CUANTOS");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public int getResultSQLCountTranferencia(int oficio) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountTransferencia());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("CUENTA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public int getResultSQLCountTranferenciaByTipoOficio(int oficio, String tipoOficio) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountTransferenciaByTipoOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, tipoOficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("CUENTA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public int getResultSQLCountTranfrec(int oficio) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountTransfrec());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("CUENTA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public int getResultSQLCountTranfrecByTipoOficio(int oficio, String tipoOficio) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountTransfrecByTipoOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, tipoOficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("CUENTA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    /*public boolean getResultSQLUpdateEstatusOficioSINVP(int folio, int estatus) {
     boolean resultado = false;
     QuerysBD query = new QuerysBD();
     if (conectaBD != null) {
     try {
     resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusOficioSINVP(folio, estatus));

     } catch (Exception sqle) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(sqle, new Throwable());
     bitacora.grabaBitacora();
     }
     }
     return resultado;
     }*/
    public boolean getResultSQLdeleteTransferencia(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteTransferencia(folio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLdeleteTransferenciaByTipoOficio(int folio, String tipoOficio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteTransferenciaByTipoOficio(folio, tipoOficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteTransfrec(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteTransfrec(folio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteTransfrecByTipoOficio(int folio, String tipoOficio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteTransfrecByTipoOficio(folio, tipoOficio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    /*public boolean getResultSQLUpdateEstatusOficioDetSINVP(int folio) {
     boolean resultado = false;
     QuerysBD query = new QuerysBD();
     if (conectaBD != null) {
     try {
     resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusOficioDetSINVP(folio));

     } catch (Exception sqle) {
     bitacora.setStrUbicacion(getStrUbicacion());
     bitacora.setStrServer(getStrServer());
     bitacora.setITipoBitacora(1);
     bitacora.setStrInformacion(sqle, new Throwable());
     bitacora.grabaBitacora();
     }
     }
     return resultado;
     }*/
    public int getResultSQLCountMovoficiosMetasEditadas(int oficio) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountMovoficiosMetasEditadas(oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("CUANTOS");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public int getResultSQLCountMovoficiosAccionEditadas(int oficio) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountMovoficiosAccionEditadas(oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("CUANTOS");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public boolean getResultSQLAjustarAcumuladoMesesAnteriores(MovOficiosAccionReq movOficioAccionReq, int metaId, int accionId, int mesActual) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        String strQuery = "";
        if (conectaBD != null) {
            try {
                strQuery = query.getSQLAjustarAcumuladoMesesAnteriores(movOficioAccionReq, metaId, accionId, mesActual);
                if (!strQuery.equals("")) {
                    resultado = conectaBD.ejecutaSQLActualizacion(strQuery);
                } else {
                    resultado = true;
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLAjustarAcumuladoMesesAnterioresParaestatal(MovOficiosAccionReq movOficioAccionReq, int metaId, int accionId, int mesActual) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        String strQuery = "";
        Requerimiento reqAnterior;
        try {
            reqAnterior = getResultSQLgetRequerimientoByLlave(movOficioAccionReq.getYear(),
                    movOficioAccionReq.getRamo(),
                    movOficioAccionReq.getMeta(),
                    movOficioAccionReq.getAccion(),
                    movOficioAccionReq.getRequerimiento());

            if (conectaBD != null) {
                try {
                    strQuery = query.getSQLAjustarAcumuladoMesesAnteriores(movOficioAccionReq, reqAnterior, metaId, accionId, mesActual);
                    if (!strQuery.equals("")) {
                        resultado = conectaBD.ejecutaSQLActualizacion(strQuery);
                    } else {
                        resultado = true;
                    }
                } catch (Exception sqle) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(sqle, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        } catch (Exception sqle) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sqle, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean getResultSQLUpdateEstatusAmpliaciones(String estatus, int folio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusAmpliaciones(estatus, folio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getRestulSQLGetFuncionCreaCodigo(int year, String ramo, String depto, String finalidad, String funcion, String subFuncion,
            String progConac, String programa, String tipoProyecto, int proyecto, int meta, int accion, String partida, String tipoGasto, String fuente,
            String fondo, String recurso, String municipio, int delegacion, String relLaboral) throws SQLException {

        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            CallableStatement clstm = null;
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLGetFuncionCreaCodigo());
                if (clstm != null) {

                    clstm.setString(1, String.valueOf(year));
                    clstm.setString(2, ramo);
                    clstm.setString(3, depto);
                    clstm.setString(4, finalidad);
                    clstm.setString(5, funcion);
                    clstm.setString(6, subFuncion);
                    clstm.setString(7, progConac);
                    clstm.setString(8, programa);
                    clstm.setString(9, tipoProyecto);
                    clstm.setInt(10, proyecto);
                    clstm.setInt(11, meta);
                    clstm.setInt(12, accion);
                    clstm.setString(13, partida);
                    clstm.setString(14, tipoGasto);
                    clstm.setString(15, fuente);
                    clstm.setString(16, fondo);
                    clstm.setString(17, recurso);
                    clstm.setString(18, municipio);
                    clstm.setInt(19, delegacion);
                    clstm.setString(20, relLaboral);

                    clstm.executeQuery();
                }
            } catch (SQLException sqle) {
                try {
                    conectaBD.transaccionRollback();
                    resultado = false;
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(sqle, new Throwable());
                    bitacora.grabaBitacora();
                    mensaje = sqle.getMessage();
                } catch (Exception ex) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(ex, new Throwable());
                    bitacora.grabaBitacora();
                    resultado = false;
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            } finally {
                clstm.close();
            }
        }

        return resultado;
    }

    public int getResultExistenMovMetaAmpliacionReduccionByClave(int year, String ramoId, int metaId, int oficio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovtoMetaAmpliacionReduccionByClave(year, ramoId, metaId, oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public int getResultExistenMovAccionAmpliacionReduccionByClave(int year, String ramoId, int metaId, int accionId, int oficio) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLContFoliosMovtoAccionAmpliacionReduccionByClave(year, ramoId, metaId, accionId, oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("EXISTEN");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }
    // <editor-fold defaultstate="collapsed" desc="Metodos Caratula.">

    public ArrayList<TipoSesion> getTipoSesiones() {
        ResultSet rsResult = null;
        CodigoPPTO codigo = null;
        int cont = 0;
        TipoSesion sesiones = new TipoSesion();
        QuerysBD query = new QuerysBD();
        ArrayList<TipoSesion> lSesiones = null;
        PreparedStatement pstmt = null;
        if (conectaBD != null) {

            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlTipoSesion());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();

                    if (rsResult != null) {
                        lSesiones = new ArrayList();
                        while (rsResult.next()) {
                            sesiones = new TipoSesion();
                            sesiones.setiSesion(rsResult.getInt(1));
                            sesiones.setsDescripcion(rsResult.getString(2));
                            lSesiones.add(sesiones);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                try {
                    pstmt.close();
                    rsResult.close();
                } catch (Exception ex) {
                }
            }

        }
        return lSesiones;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos Reporte Amp/Red/Trans.">
    public List<TipoMovimiento> getResultTipoMovimientoFiltrado() throws SQLException {
        List<TipoMovimiento> tipoMovimientoList = new ArrayList<TipoMovimiento>();
        TipoMovimiento tipoMovimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoMovimientoFiltrado());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoMovimiento = new TipoMovimiento();
                            tipoMovimiento.setTipoMovId(rsResult.getString("TIPOMOV"));
                            tipoMovimiento.setTipoMov(rsResult.getString("DESCR"));
                            tipoMovimientoList.add(tipoMovimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return tipoMovimientoList;
    }

    public List<Movimiento> getResultMovimientoByTipoMovReporte(String tipoMovimiento, String estatusBase, int year) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByTipoMovReporte(tipoMovimiento, estatusBase, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPO"));
                            movimiento.setTipoOficio(rsResult.getString("TIPOFICIO"));
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }
    // </editor-fold>

    public Parametro getResultValoresMomentosContables() throws Exception {
        ResultSet rsResult = null;
        Parametro parametro = new Parametro();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValoresMomentosContables());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult.next()) {
                        parametro.setSiglasIdTramite(rsResult.getString("SIGLAS_IDTRAMITE"));
                        parametro.setEmpresaCG(rsResult.getInt("EMPRESA_CG"));
                        parametro.setOrigenPolMCCG(rsResult.getInt("ORIGEN_POLMCCG"));
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return parametro;
    }

    public String getResultSQLgetSecuenciaMomentoCont(boolean isActual, String siglas, int anio) throws SQLException {
        String sequencia = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetSequenceMomentoCont(isActual, siglas, anio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            sequencia = rsResult.getString("SEQ");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return sequencia;
    }

    public long getResultSQLgetSecuenciaPoliza(boolean isActual, String fechaYYMMDD) throws SQLException {
        long sequencia = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetSequencePoliza(isActual, fechaYYMMDD));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            sequencia = rsResult.getLong("SEQ");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return sequencia;
    }

    public boolean getResultSQLInsertBuzonMomentos(int empresa, int ejercicio, long poliza, int origen, String descr, String fecha,
            int noRegistros, String appLogin, String fechaRegistro, String status) throws SQLException {
        boolean bResultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {

            try {
                bResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertBuzonMomentos(empresa, ejercicio, poliza, origen,
                        descr, fecha, noRegistros, appLogin, fechaRegistro, status));

            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception e) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(e, new Throwable());
                bitacora.grabaBitacora();
            } finally {
            }
        }
        return bResultado;
    }

    public boolean getResultSQLInsertBuzonMomentosDet(int empresa, int ejercicio, long poliza, int origen, int consec,
            int tipoMomento, int momento, String concepto, String descr_concepto, String idTramite, String municipio, int folioSPP,
            int tramiteSPP, double importe, int tipoMovto, String ctaContable, String status, int momentoPoliza, int origenMovto,
            String partida) throws SQLException {
        boolean bResultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                bResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertBuzonMomentosDet(empresa, ejercicio, poliza, origen, consec,
                        tipoMomento, momento, concepto, descr_concepto, idTramite, municipio, folioSPP,
                        tramiteSPP, importe, tipoMovto, ctaContable, status, momentoPoliza, origenMovto,
                        partida));

            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception e) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(e, new Throwable());
                bitacora.grabaBitacora();
            } finally {
            }
        }
        return bResultado;
    }

    public boolean getResultSQLInsertBuzonMomentosCod(int empresa, int ejercicio, long poliza, int origen, int consec,
            String idTramite, String concepto, String ramo, String depto, String finalidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral,
            double importe) throws SQLException {
        boolean bResultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                bResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertBuzonMomentosCod(empresa, ejercicio, poliza, origen, consec,
                        idTramite, concepto, ramo, depto, finalidad, funcion, subfuncion,
                        prgConac, programa, tipoProy, proyecto, meta, accion, partida,
                        tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLaboral,
                        importe));

            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception e) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(e, new Throwable());
                bitacora.grabaBitacora();
            } finally {
            }
        }
        return bResultado;
    }

    public Date getResultSQLGetFechaContabilidad(int ejercicio) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        Date fecha = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFechaAplicacionGasto(ejercicio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            if (rsResult.getDate("FECHA_MOMENTO_GASTO") != null) {
                                fecha = rsResult.getDate("FECHA_MOMENTO_GASTO");
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return fecha;
    }

    public ProcesoMomento getResultSQLProcesoMomentoByTipo(String tipo) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        ProcesoMomento procesoMomento = new ProcesoMomento();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLProcesoMomentoByTipo(tipo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            procesoMomento.setClave(rsResult.getInt("CLAVE"));
                            procesoMomento.setProcesoMomento(rsResult.getInt("PROCESO_MOMENTO"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return procesoMomento;
    }

    public List<ProcesoMomentoCFG> getResultSQLProcesoMomentoCFG(int proceso) throws Exception {
        ResultSet rsResult = null;
        List<ProcesoMomentoCFG> procesoMomentoList = new ArrayList<ProcesoMomentoCFG>();
        ProcesoMomentoCFG procesoMomento = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLProcesoMomentoCFG(proceso));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        procesoMomento = new ProcesoMomentoCFG();
                        procesoMomento.setProceso(rsResult.getInt("PROCESO"));
                        procesoMomento.setConsec(rsResult.getInt("CONSEC"));
                        procesoMomento.setTipoMomento(rsResult.getInt("TIPO_MOMENTO"));
                        procesoMomento.setMomentoPoliza(rsResult.getInt("MOMENTO_POLIZA"));
                        procesoMomento.setMomento(rsResult.getInt("MOMENTO"));
                        procesoMomento.setMomentoRelacionado(rsResult.getInt("MOMENTO_RELACIONADO"));
                        procesoMomentoList.add(procesoMomento);
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return procesoMomentoList;
    }

    public List<MovimientoContabilidad> getResultSQLMovtosContabilidad(int year, int oficio, String tipoOficio, String tipoMov) throws Exception {
        ResultSet rsResult = null;
        List<MovimientoContabilidad> movtoContabilidadList = new ArrayList<MovimientoContabilidad>();
        MovimientoContabilidad movtoContabilidad = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                if (tipoMov.equals("A")) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovtosContabilidadAmpliacion(oficio, tipoOficio, year));
                } else if (tipoMov.equals("T")) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovtosContabilidadTransferencia(oficio, tipoOficio, year));
                }
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        movtoContabilidad = new MovimientoContabilidad();
                        movtoContabilidad.setImporte(rsResult.getDouble("IMPORTE"));
                        movtoContabilidad.setOficio(rsResult.getInt("OFICIO"));
                        movtoContabilidad.setPartida(rsResult.getString("PARTIDA"));
                        movtoContabilidad.setTipoMov(rsResult.getString("TIPOMOV"));
                        movtoContabilidad.setTipoOficio(rsResult.getString("TIPO"));
                        movtoContabilidadList.add(movtoContabilidad);
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return movtoContabilidadList;
    }

    public String getResultSQLCuentaContable(int year, String partida, int momento) throws SQLException {
        String cuenta = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCuentaContable(year, partida, momento));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuenta = rsResult.getString("CTACONTABLE");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuenta;
    }

    public int getResultSQLValidaPolizaCuadrada(int empresa, int ejercicio, long poliza, int origen, String mensaje) throws SQLException {
        String resultado = "";
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double cargo = 0;
        double abono = 0;
        int registros = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaPolizaCuadrada(empresa, ejercicio, poliza, origen));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            cargo = rsResult.getDouble("CARGO");
                            abono = rsResult.getDouble("ABONO");

                            if (cargo != abono) {
                                mensaje = "La póliza generada no puede ser traspasada ya que el importe de cargos y abonos no son iguales";
                            } else {
                                registros = rsResult.getInt("REGISTROS");
                            }
                        } else {
                            mensaje = "No se encontró información de los cargos y abonos para verificar si la póliza esta cuadrada";
                        }
                    } else {
                        mensaje = "No se encontró información de los cargos y abonos para verificar si la póliza esta cuadrada";
                    }
                } else {
                    mensaje = "No se encontró información de los cargos y abonos para verificar si la póliza esta cuadrada";
                }
            } catch (SQLException sqle) {
                resultado = "No se pudo determinar el importe de los cargos y abonos para verificar si la póliza esta cuadrada";
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return registros;
    }

    public int getResultSQLExistePoliza(int empresa, int ejercicio, long poliza, int origen) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int existe = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExistePoliza(empresa, ejercicio, poliza, origen));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            existe = rsResult.getInt("EXISTE");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return existe;
    }

    public boolean getResultSQLTraspasaEncabezadoPoliza(int empresa, int ejercicio, long poliza, int origen) throws SQLException {
        boolean bResultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                bResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLTraspasaEncabezadoPoliza(empresa, ejercicio, poliza, origen));

            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception e) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(e, new Throwable());
                bitacora.grabaBitacora();
            } finally {
            }
        }
        return bResultado;
    }

    public boolean getResultSQLUpdateRegistrosEncabezadoPoliza(int empresa, int ejercicio, long poliza, int origen, int registros) throws SQLException {
        boolean bResultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                bResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateRegistrosEncabezadoPoliza(empresa, ejercicio, poliza, origen, registros));

            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception e) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(e, new Throwable());
                bitacora.grabaBitacora();
            } finally {
            }
        }
        return bResultado;
    }

    public boolean getResultSQLTraspasaDetallePoliza(int empresa, int ejercicio, long poliza, int origen) throws SQLException {
        boolean bResultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                bResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLTraspasaDetallePoliza(empresa, ejercicio, poliza, origen));

            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception e) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(e, new Throwable());
                bitacora.grabaBitacora();
            } finally {
            }
        }
        return bResultado;
    }

    public List<Movimiento> getResultMovimientoByRamoUsr(int year, String appLogin, String ramo) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByRamoUsr(year, appLogin, ramo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            movimiento.setFechaAutorizacion(rsResult.getDate("FECHAAUT"));
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public boolean getResultSQLUpdateEstatusTipoOficio(String estatus, int folio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusTipoOficio(estatus, folio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public int getResultSQLgetMonthTramite(int folio) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int month = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMonthTramite());
                pstmt.setInt(1, folio);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            month = rsResult.getInt("MONTH");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return month;
    }

    public List<Movimiento> getResultMovimientoByTipoMovEstatusMovAppLoginART(String tipoMovimiento, String estatusBase, int year, String appLogin) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByTipoMovEstatusMovAppLoginART());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, tipoMovimiento);
                    pstmt.setString(3, appLogin);
                    pstmt.setInt(4, year);
                    pstmt.setString(5, estatusBase);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            if (rsResult.getString("TIPO") != null) {
                                movimiento.setTipoOficio(rsResult.getString("TIPO"));
                            } else {
                                movimiento.setTipoOficio("");
                            }
                            if (rsResult.getString("STATUSDESCR") != null) {
                                movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            } else {
                                movimiento.setStatusDescr("");
                            }
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public List<Movimiento> getResultMovimientoByTipoMovART(String tipoMovimiento, String estatusBase, int year) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientoByTipoMovART(tipoMovimiento, estatusBase, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            if (rsResult.getString("TIPO") != null) {
                                movimiento.setTipoOficio(rsResult.getString("TIPO"));
                            } else {
                                movimiento.setTipoOficio("");
                            }
                            if (rsResult.getString("STATUSDESCR") != null) {
                                movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            } else {
                                movimiento.setStatusDescr("");
                            }
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public Date getResultSQLgetFechaContabilidad() throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        Date fechaContable = new Date();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetFechaContabilidad());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            fechaContable = rsResult.getDate("FECHAPOLCONTABLE");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return fechaContable;
    }

    public boolean getResultSQLGetValidaMetasNuevasFromMovoficios(int folio, int year, String ramo, int meta) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        boolean existe = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetValidaMetasNuevasFromMovoficios(folio, year, ramo, meta));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            if (rsResult.getInt(1) == 1) {
                                existe = true;
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return existe;
    }

    public boolean getResultSQLGetValidaAccionesNuevasFromMovoficios(int folio, int year, String ramo, int meta, int accion) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        boolean existe = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetValidaAccionesNuevasFromMovoficios(folio, year, ramo, meta, accion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            if (rsResult.getInt(1) == 1) {
                                existe = true;
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return existe;
    }

    public boolean getResultSQLInsertEstimacionFromMovoficios(int folio, int year, String ramo, int meta, int claveMetaNueva) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertEstimacionFromMovOficios(folio, year, ramo, meta, claveMetaNueva));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertAccionEstimacionFromMovoficios(int folio, int year, String ramo, int meta, int accion, int claveAccion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertAccionEstimacionFromMovOficios(folio, year, ramo, meta, accion, claveAccion));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public String getResultSQLEstatusBaseByTipoFlujo(int tipoFlujo) throws SQLException {
        String estatusBase = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLEstatusBaseByTipoFlujo(tipoFlujo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            estatusBase = rsResult.getString("ESTATUS_BASE");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return estatusBase;
    }

    public boolean getResultSQLisUsuarioEspacialCaptura(String appLogin) throws SQLException {
        boolean isUsuarioCaptira = false;
        int resultado = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisUsuarioCapturaEspecial());
                pstmt.setString(1, appLogin);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            resultado = rsResult.getInt("USU");
                        }
                    }
                }
                if (resultado > 0) {
                    isUsuarioCaptira = true;
                } else {
                    isUsuarioCaptira = false;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return isUsuarioCaptira;
    }

    public boolean getResultSQLisTransferenciaAnual() throws SQLException {
        boolean transAnual = false;
        String resultado = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetParametroTransAnaul());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            resultado = rsResult.getString("TRANSF_ANUAL");
                        }
                    }
                }
                if (resultado.equals("S")) {
                    transAnual = true;
                } else {
                    transAnual = false;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transAnual;
    }

    public double getResultSQLgetImporteAnual(int year, String ramo, String programa, String tipoProy,
            int proy, int meta, int accion, String partida, String fuente, String fondo,
            String recurso, String relLaboral, int mesActual) throws SQLException {
        double asignado = 0;
        double actualizado = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        PreparedStatement pstmt = null;
        CodigoPPTO codPPTO = new CodigoPPTO();
        try {
            codPPTO = getRestulSQLgetCodigoPPTOsinRequerimiento(year, ramo, programa,
                    tipoProy, proy, meta, accion, partida, fuente, fondo, recurso, relLaboral);
            if (codPPTO != null) {
                if (conectaBD != null) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetDisponibleByRangoMes());
                    if (pstmt != null) {
                        pstmt.setInt(1, year);
                        pstmt.setString(2, codPPTO.getRamoId());
                        pstmt.setString(3, codPPTO.getDepto());
                        pstmt.setString(4, codPPTO.getFinalidad());
                        pstmt.setString(5, codPPTO.getFuncion());
                        pstmt.setString(6, codPPTO.getSubfuncion());
                        pstmt.setString(7, codPPTO.getProgCONAC());
                        pstmt.setString(8, codPPTO.getPrograma());
                        pstmt.setString(9, codPPTO.getTipoProy());
                        pstmt.setInt(10, proy);
                        pstmt.setInt(11, meta);
                        pstmt.setInt(12, accion);
                        pstmt.setString(13, codPPTO.getPartida());
                        pstmt.setString(14, codPPTO.getTipoGasto());
                        pstmt.setString(15, codPPTO.getFuente());
                        pstmt.setString(16, codPPTO.getFondo());
                        pstmt.setString(17, codPPTO.getRecurso());
                        pstmt.setString(18, codPPTO.getMunicipio());
                        pstmt.setInt(19, codPPTO.getDelegacion());
                        pstmt.setString(20, codPPTO.getRelLaboral());
                        pstmt.setInt(21, 1);
                        pstmt.setInt(22, 12);
                        rsResult = pstmt.executeQuery();
                        if (rsResult != null) {
                            if (rsResult.next()) {
                                asignado = rsResult.getDouble("ASIGNADO");
                                actualizado = rsResult.getDouble("ACTUALIZADO");
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } finally {
            if (codPPTO != null) {
                pstmt.close();
                rsResult.close();
            }
        }
        return new BigDecimal(asignado)
                .add(new BigDecimal(actualizado))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public double[] getResultSQLgetDisponibleByRangoMes(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proy, int meta, int accion, String partida, String tipoGasto, String fuente,
            String fondo, String recurso, String municipio, int localidad, String relLaboral, int mesI, int mesF) throws SQLException {
        double montos[] = new double[4];
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetDisponibleByRangoMes());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, depto);
                    pstmt.setString(4, finalidad);
                    pstmt.setString(5, funcion);
                    pstmt.setString(6, subfuncion);
                    pstmt.setString(7, prgConac);
                    pstmt.setString(8, programa);
                    pstmt.setString(9, tipoProy);
                    pstmt.setInt(10, proy);
                    pstmt.setInt(11, meta);
                    pstmt.setInt(12, accion);
                    pstmt.setString(13, partida);
                    pstmt.setString(14, tipoGasto);
                    pstmt.setString(15, fuente);
                    pstmt.setString(16, fondo);
                    pstmt.setString(17, recurso);
                    pstmt.setString(18, municipio);
                    pstmt.setInt(19, localidad);
                    pstmt.setString(20, relLaboral);
                    pstmt.setInt(21, mesI);
                    pstmt.setInt(22, mesF);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            montos[0] = rsResult.getDouble("ASIGNADO");
                            montos[1] = rsResult.getDouble("ACTUALIZADO");
                            montos[2] = rsResult.getDouble("COMPROMISO");
                            montos[3] = rsResult.getDouble("EJERCIDO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return montos;
    }

    public boolean getResultSQLUpdateMovoficiosMetaAccionEstimacion(int metaNva, int oficio, int year, String ramo, int metaAnt) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovoficiosMetaAccionEstimacion(metaNva, oficio, year, ramo, metaAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMovoficiosMetaAccionReq(int metaNva, int oficio, int year, String ramo, int metaAnt) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovoficiosMetaAccionReq(metaNva, oficio, year, ramo, metaAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public BigDecimal getResultSQLgetQuincePorcientoTipoOficio(int year, String ramo, String partida,
            BigDecimal acRamoPartida) throws SQLException {
        double acumulado = this.getResultSQLgetAcumluladoMovtos(year, partida, ramo);
        acRamoPartida = acRamoPartida.add(new BigDecimal(acumulado));
        double asignado = this.getResultSQLgetquincePorciento(partida, ramo, year);
        if (asignado > 0) {
            //if (acRamoPartida.compareTo(new BigDecimal(0)) > 0) {
            return acRamoPartida.divide(new BigDecimal(asignado), 2, RoundingMode.HALF_UP);
        } else {
            return new BigDecimal(1);
        }

    }

    public double getResultSQLgetquincePorciento(String partida, String ramo, int year) throws SQLException {
        double monto = 0.0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetQuincePorCiento());
                pstmt.setInt(1, year);
                pstmt.setString(2, ramo);
                pstmt.setString(3, partida);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            monto = rsResult.getDouble("MONTO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return monto;
    }

    public double getResultSQLgetAcumluladoMovtos(int year, String partida, String ramo) throws SQLException {
        double monto = 0.0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovimientoAcumulado());
                pstmt.setString(1, partida);
                pstmt.setString(2, ramo);
                pstmt.setInt(3, year);
                pstmt.setString(4, partida);
                pstmt.setString(5, ramo);
                pstmt.setInt(6, year);
                pstmt.setString(7, partida);
                pstmt.setString(8, ramo);
                pstmt.setInt(9, year);
                pstmt.setString(10, partida);
                pstmt.setString(11, ramo);
                pstmt.setInt(12, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            monto = rsResult.getDouble("ACUMULADO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return monto;
    }

    public BigDecimal getResultSQLgetAcumluladoMovtosRechazado(int year, String partida, String ramo, int oficio) throws SQLException {
        double monto = 0.0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovimientoAcumuladoRechazado());
                pstmt.setString(1, partida);
                pstmt.setString(2, ramo);
                pstmt.setInt(3, oficio);
                pstmt.setInt(4, year);
                pstmt.setString(5, partida);
                pstmt.setString(6, ramo);
                pstmt.setInt(7, oficio);
                pstmt.setInt(8, year);
                pstmt.setString(9, partida);
                pstmt.setString(10, ramo);
                pstmt.setInt(11, oficio);
                pstmt.setInt(12, year);
                pstmt.setString(13, partida);
                pstmt.setString(14, ramo);
                pstmt.setInt(15, oficio);
                pstmt.setInt(16, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            monto = rsResult.getDouble("ACUMULADO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return new BigDecimal(monto);
    }

    public String getResultSQLgetGrupoPartida(int year, String partida) throws SQLException {
        String grupo = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetGrupoPartida());
                pstmt.setString(1, partida);
                pstmt.setInt(2, year);
                pstmt.setInt(3, year);
                pstmt.setInt(4, year);
                pstmt.setInt(5, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            grupo = rsResult.getString("GRUPO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return grupo;
    }

    public String getResultSQLisRamoTransotorio(int year, String ramo) throws SQLException {
        String trans = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetIsRamoTransitorio());
                pstmt.setString(1, ramo);
                pstmt.setInt(2, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            trans = rsResult.getString("TRANSITORIO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return trans;
    }

    public List<Transferencia> getTransferenciasByFolio(int folio, int year) throws SQLException {
        List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
        Transferencia transferencia = new Transferencia();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSLQgetTransferenciasByFolio());
                pstmt.setInt(1, folio);
                pstmt.setInt(2, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            transferencia = new Transferencia();
                            transferencia.setConsec(rsResult.getInt("CONSEC"));
                            transferencia.setRamo(rsResult.getString("RAMO"));
                            transferencia.setDepto(rsResult.getString("DEPTO"));
                            transferencia.setFinalidad(rsResult.getString("FINALIDAD"));
                            transferencia.setFuncion(rsResult.getString("FUNCION"));
                            transferencia.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            transferencia.setPrgConac(rsResult.getString("PRG_CONAC"));
                            transferencia.setPrograma(rsResult.getString("PRG"));
                            transferencia.setTipoProy(rsResult.getString("TIPO_PROY"));
                            transferencia.setProyecto(rsResult.getInt("PROYECTO"));
                            transferencia.setMeta(rsResult.getInt("META"));
                            transferencia.setAccion(rsResult.getInt("ACCION"));
                            transferencia.setPartida(rsResult.getString("PARTIDA"));
                            transferencia.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            transferencia.setFuente(rsResult.getString("FUENTE"));
                            transferencia.setFondo(rsResult.getString("FONDO"));
                            transferencia.setRecurso(rsResult.getString("RECURSO"));
                            transferencia.setMunicipio(rsResult.getString("MUNICIPIO"));
                            transferencia.setDelegacion(rsResult.getInt("DELEGACION"));
                            transferencia.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            transferencia.setImporte(rsResult.getDouble("IMPTE"));
                            transferencia.setEstatus(rsResult.getString("STATUS"));
                            transferencia.setTransitorio(rsResult.getString("TRANSITORIO"));
                            transferencia.setConsiderar(rsResult.getString("CONSIDERAR"));
                            transferenciaList.add(transferencia);
                            identificador++;
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
                pstmt = null;
                rsResult = null;
            }
        }
        return transferenciaList;
    }

    public List<Transferencia> getTransferenciaByTipoOficio(int folio, String tipoOficio, int year) throws SQLException {
        List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
        Transferencia transferencia = new Transferencia();
        MovOficiosAccionReq requerimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {

            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTransferenciasByTipoOficio());
                pstmt.setInt(1, folio);
                pstmt.setString(2, tipoOficio);
                pstmt.setInt(3, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            transferencia = new Transferencia();
                            transferencia.setConsec(rsResult.getInt("CONSEC"));
                            transferencia.setRamo(rsResult.getString("RAMO"));
                            transferencia.setDepto(rsResult.getString("DEPTO"));
                            transferencia.setFinalidad(rsResult.getString("FINALIDAD"));
                            transferencia.setFuncion(rsResult.getString("FUNCION"));
                            transferencia.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            transferencia.setPrgConac(rsResult.getString("PRG_CONAC"));
                            transferencia.setPrograma(rsResult.getString("PRG"));
                            transferencia.setTipoProy(rsResult.getString("TIPO_PROY"));
                            transferencia.setProyecto(rsResult.getInt("PROYECTO"));
                            transferencia.setMeta(rsResult.getInt("META"));
                            transferencia.setAccion(rsResult.getInt("ACCION"));
                            transferencia.setPartida(rsResult.getString("PARTIDA"));
                            transferencia.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            transferencia.setFuente(rsResult.getString("FUENTE"));
                            transferencia.setFondo(rsResult.getString("FONDO"));
                            transferencia.setRecurso(rsResult.getString("RECURSO"));
                            transferencia.setMunicipio(rsResult.getString("MUNICIPIO"));
                            transferencia.setDelegacion(rsResult.getInt("DELEGACION"));
                            transferencia.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            transferencia.setImporte(rsResult.getDouble("IMPTE"));
                            transferencia.setEstatus(rsResult.getString("STATUS"));
                            transferencia.setTransitorio(rsResult.getString("TRANSITORIO"));
                            transferencia.setConsiderar(rsResult.getString("CONSIDERAR"));
                            transferenciaList.add(transferencia);
                            identificador++;
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transferenciaList;
    }

    public List<Transferencia> getTransferenciaByTipoOficioNuevo(int folio) throws SQLException {
        List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
        Transferencia transferencia = new Transferencia();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {

            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetTipoOficioNuevo());
                pstmt.setInt(1, folio);
                pstmt.setInt(2, folio);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            transferencia = new Transferencia();
                            transferencia.setConsec(rsResult.getInt("OFICIO"));
                            transferencia.setFolio(rsResult.getInt("CONSEC"));
                            transferenciaList.add(transferencia);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transferenciaList;
    }

    public boolean getResultSQLUpdateEstatusTransferencia(String estatus, int folio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusTransferencia(estatus, folio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateEstatusTransferenciaTipoOficio(String estatus, int folio, String tipoOficio) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusTransferenciaTipoOficio(estatus, folio, tipoOficio));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public Caratula getResultGetMovCaratula(int year, String ramoSession, int folio) throws SQLException {

        Caratula objMovCaratula = new Caratula();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLMovCaratula());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramoSession);
                    pstmt.setInt(3, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            objMovCaratula.setiYear(rsResult.getInt(1));
                            objMovCaratula.setsRamo(rsResult.getString(2));
                            objMovCaratula.setsIdCaratula(Long.parseLong(rsResult.getString(3)));
                            objMovCaratula.setsFechaRevision(rsResult.getString(4));
                            objMovCaratula.setsNumeroSesion(rsResult.getString(5));
                            objMovCaratula.setiTipoSesion(rsResult.getInt(6));
                            objMovCaratula.setsNumModPres(rsResult.getString(7));
                            objMovCaratula.setsNumodProg(rsResult.getString(8));
                            objMovCaratula.setsSysClave(rsResult.getString(9));
                            objMovCaratula.setsAppLogin(rsResult.getString(10));
                            objMovCaratula.setiStatus(rsResult.getString(11));
                            objMovCaratula.setsFechaRegistro(rsResult.getString(12));
                            objMovCaratula.setsTipoSesionDescr(rsResult.getString(13));
                            objMovCaratula.setsNumSesionDescr(rsResult.getString(14));
                            if (rsResult.getString(7) == null) {
                                objMovCaratula.setsModPresupDescr("No Aplica");
                            } else {
                                objMovCaratula.setsModPresupDescr(rsResult.getString(15));
                            }
                            if (rsResult.getString(8) == null) {
                                objMovCaratula.setsModProgDescr("No Aplica");
                            } else {
                                objMovCaratula.setsModProgDescr(rsResult.getString(16));
                            }
                            objMovCaratula.setsDescr(rsResult.getString(17));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return objMovCaratula;
    }

    public String getResultGetUnidadMedidaById(int year, String unidadMedida, String accion, String meta) throws SQLException {

        String unidadDescr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetUnidadMedidaDescr());
                if (pstmt != null) {
                    pstmt.setString(1, unidadMedida);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, accion);
                    pstmt.setString(4, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            unidadDescr = rsResult.getString("MEDIDA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return unidadDescr;
    }

    public String getResultGetUnidadResponsable(int year, String ramo, String prg, String tipoProy, int proy) throws SQLException {

        String unidadDescr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetUnidadResponsable());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, prg);
                    pstmt.setString(4, tipoProy);
                    pstmt.setInt(5, proy);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            unidadDescr = rsResult.getString("UNIDAD");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return unidadDescr;
    }

    public int getResultGetCountAmpliaciones(int folio) throws SQLException {

        int ampCount = 0;
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetCountAmpliaciones());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ampCount = rsResult.getInt("AMP");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return ampCount;
    }

    public double getResultSQLGetSumaEstimacion(int year, String ramo, int meta, int folio, String status) throws SQLException {
        double suma = 0;
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetSumaEstimacion(year, ramo, meta, folio, status));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            suma = rsResult.getInt("VALOR");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return suma;
    }

    public double getResultSQLGetSumaAccionEstimacion(int year, String ramo, int meta, int accion, int folio, String status) throws SQLException {
        double suma = 0;
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetSumaAccionEstimacion(year, ramo, meta, accion, folio, status));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            suma = rsResult.getInt("VALOR");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return suma;
    }

    public int getResultSQLGetMaxConsecTransferencia(int folio) throws SQLException {
        int consec = 0;
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetMaxConsecTransferencia());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            consec = rsResult.getInt("CONSEC");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return consec;
    }

    public boolean getResultSQLInsertMovCaratula(int oficio, int year, String ramoSession, long selCaratula) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertMovCaratula(oficio, year, ramoSession, selCaratula));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertTransferencia(int folio, int consec, String ramo, String depto, String finalidad,
            String funcion, String subfuncion, String prgConac, String prg, String tipoProy, int proy, int meta,
            int accion, String partida, String tipoGasto, String fuente, String fondo, String recurso, String mpo,
            int delegacion, String relLaboral, double importe, String status, String considerar) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertTransferencia(folio, consec,
                        ramo, depto, finalidad, funcion, subfuncion, prgConac, prg, tipoProy, proy, meta,
                        accion, partida, tipoGasto, fuente, fondo, recurso, mpo, delegacion, relLaboral, importe, status, considerar));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertTransfrec(int folio, int consec, String ramo, String depto, String finalidad,
            String funcion, String subfuncion, String prgConac, String prg, String tipoProy, int proy, int meta,
            int accion, String partida, String tipoGasto, String fuente, String fondo, String recurso, String mpo,
            int delegacion, String relLaboral, double importe, String tipoMov, int requ) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertTransfrec(folio, consec, ramo,
                        depto, finalidad, funcion, subfuncion, prgConac, prg, tipoProy, proy, meta, accion,
                        partida, tipoGasto, fuente, fondo, recurso, mpo, delegacion, relLaboral, importe, tipoMov, requ));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteMovCaratula(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteMovCaratulaReprogramacion(folio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMovCaratula(int oficio, int year, String ramoSession, long selCaratula, long actCaratula) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovCaratula(oficio, year, ramoSession, selCaratula, actCaratula));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }

        return resultado;
    }

    public boolean getResultSQLdeleteMovOficio(int oficio, String estatus) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteMovoficios(oficio, estatus));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }

        return resultado;
    }

    public String getResultSQLGetRamoDescr(int year, String ramo) throws SQLException {
        String descr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetDescrRamo());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descr = rsResult.getString("Descr");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return descr;
    }

    public String getResultSQLGetProgramaDescr(int year, String programa) throws SQLException {
        String descr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetDescrPrograma());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, programa);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descr = rsResult.getString("Descr");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return descr;
    }

    public String getResultSQLGetPartidaDescr(int year, String partida) throws SQLException {
        String descr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetDescrPartida());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, partida);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descr = rsResult.getString("Descr");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return descr;
    }

    public String getResultSQLGetRelLaboralDescr(int year, String relLaboral) throws SQLException {
        String descr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetDescrRelLaboral());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, relLaboral);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descr = rsResult.getString("Descr");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return descr;
    }

    public String getResultSQLGetFuenteFinDescr(int year, String fuente, String fondo, String recurso) throws SQLException {
        String descr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetDescrFuenteFin());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, fuente);
                    pstmt.setString(3, fondo);
                    pstmt.setString(4, recurso);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descr = rsResult.getString("Descr");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return descr;
    }

    public String getResultSQLGetProyectoDescr(int year, String tipoProy, int proy) throws SQLException {
        String descr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetDescrProyecto());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, tipoProy);
                    pstmt.setInt(3, proy);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descr = rsResult.getString("Descr");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return descr;
    }

    public boolean getResultSQLexisteTipoOficio(int folio, String tipoOficio) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();
        int count = 0;
        boolean existeTipoOficio = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLgetCoutnTipoOficioByOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setString(2, tipoOficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("CUENTA");
                        }
                    }
                }
                if (count > 0) {
                    existeTipoOficio = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return existeTipoOficio;
    }

    public boolean getResultSQLUpdateIdTramiteTipoOficio(int folio, String tipoOficio, String idTramite) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateIdTramiteTipoOficio(folio, tipoOficio, idTramite));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateRequerimientoAmpliaciones(int folio, int consec, int meta, int accion, int requerimiento) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateRequerimientoAmpliaciones(folio, consec, meta, accion, requerimiento));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMetaAmpliaciones(int folio, String ramo, int metaNva, int metaAnt) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMetaAmpliaciones(folio, ramo, metaNva, metaAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateAccionAmpliaciones(int folio, String ramo, int meta, int accionNva, int accionAnt) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateAccionAmpliaciones(folio, ramo, meta, accionNva, accionAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateRequerimientoTransfrec(int folio, String ramo, int meta, int accion, int requerimientoNvo, int requerimientoAnt) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateRequerimientoTransfrec(folio, ramo, meta, accion, requerimientoNvo, requerimientoAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMetaTransfrec(int folio, String ramo, int metaNva, int metaAnt) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMetaTransfrec(folio, ramo, metaNva, metaAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateAccionTransfrec(int folio, String ramo, int meta, int accionNva, int accionAnt) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateAccionTransfrec(folio, ramo, meta, accionNva, accionAnt));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertTipoOficioNuevo(int folio, String tipoOficio, String estatus) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertTipoOficioNuevo(folio, tipoOficio, estatus));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertOficonsNuevo(int folio, String tipoOficio, int consec) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLinsertOficonsNuevo(folio, tipoOficio, consec));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public Requerimiento getResultGetRequerimientoHistorico(int year, String ramoId, String programaId,
            int metaId, int accionId, int requerimientoId, int folio) throws SQLException {
        Requerimiento requerimiento = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRequerimientoHistorico());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramoId);
                    pstmt.setString(4, programaId);
                    pstmt.setInt(5, metaId);
                    pstmt.setInt(6, accionId);
                    pstmt.setInt(7, requerimientoId);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            requerimiento = new Requerimiento();
                            requerimiento.setReqId(rsResult.getInt("REQUERIMIENTO"));
                            requerimiento.setReq(rsResult.getString("DESCR"));
                            requerimiento.setPartida(rsResult.getString("PARTIDA"));
                            requerimiento.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            requerimiento.setArticulo(rsResult.getInt("ARTICULO"));
                            requerimiento.setCostoUnitario(rsResult.getDouble("COSTO_UNITARIO"));
                            requerimiento.setCostoAnual(rsResult.getDouble("COSTO_ANUAL"));
                            requerimiento.setCantidad(rsResult.getDouble("CANTIDAD"));
                            requerimiento.setDepto(rsResult.getString("DEPTO"));
                            requerimiento.setCantEne(rsResult.getDouble("ENE"));
                            requerimiento.setCantFeb(rsResult.getDouble("FEB"));
                            requerimiento.setCantMar(rsResult.getDouble("MAR"));
                            requerimiento.setCantAbr(rsResult.getDouble("ABR"));
                            requerimiento.setCantMay(rsResult.getDouble("MAY"));
                            requerimiento.setCantJun(rsResult.getDouble("JUN"));
                            requerimiento.setCantJul(rsResult.getDouble("JUL"));
                            requerimiento.setCantAgo(rsResult.getDouble("AGO"));
                            requerimiento.setCantSep(rsResult.getDouble("SEP"));
                            requerimiento.setCantOct(rsResult.getDouble("OCT"));
                            requerimiento.setCantNov(rsResult.getDouble("NOV"));
                            requerimiento.setCantDic(rsResult.getDouble("DIC"));
                            requerimiento.setFuenteFin(rsResult.getString("FUENTE"));
                            requerimiento.setFondo(rsResult.getString("FONDO"));
                            requerimiento.setRecurso(rsResult.getString("RECURSO"));
                            requerimiento.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            requerimiento.setJustif(rsResult.getString("JUSTIFICACION"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return requerimiento;
    }

    public double getResultGetValorPeridoAvanceMetaByYearRamoMetaPeriodo(int year, String ramoId, int metaId, int periodo) throws SQLException {
        double valorPeriodo = -1;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetValorPeridoAvanceMetaByYearRamoMetaPeriodo(year, ramoId, metaId, periodo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valorPeriodo = rsResult.getDouble("VALOR");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    valorPeriodo = -1;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return valorPeriodo;
    }

    public boolean getResultExisteTipoOficioByOficioTipo(int folio, String tipoOficio) throws SQLException {
        int cuenta = 0;
        boolean existTipo = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountTipoOficioByOficioTipo());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setString(2, tipoOficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuenta = rsResult.getInt("TIPO");
                        }
                    }
                }
                if (cuenta > 0) {
                    existTipo = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return existTipo;
    }

    public double getResultGetValorPeridoAvanceAccionByYearRamoMetaAccionMes(int year, String ramoId, int metaId, int accionId, int mes) throws SQLException {
        double valorPeriodo = -1;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetValorPeridoAvanceAccionByYearRamoMetaAccionMes(year, ramoId, metaId, accionId, mes));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valorPeriodo = rsResult.getDouble("VALOR");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    valorPeriodo = -1;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return valorPeriodo;
    }

    public double getResultSQLgetEstimacionOrigiginal(String estatus, int folio, int year, String ramo, int meta) throws SQLException {
        double valorPeriodo = -1;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                if (estatus.equals("A")) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetEstimacionHistorico());
                } else {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetEstimacionOriginal());
                }
                if (pstmt != null) {
                    if (estatus.equals("A")) {
                        pstmt.setInt(1, folio);
                        pstmt.setInt(2, year);
                        pstmt.setString(3, ramo);
                        pstmt.setInt(4, meta);
                    } else {
                        pstmt.setInt(1, year);
                        pstmt.setString(2, ramo);
                        pstmt.setInt(3, meta);
                    }
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valorPeriodo = rsResult.getDouble("TOTAL");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    valorPeriodo = -1;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return valorPeriodo;
    }

    public double getResultSQLgetAccionEstimacionOriginal(String estatus, int folio, int year, String ramo, int meta, int accion) throws SQLException {
        double valorPeriodo = -1;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                if (estatus.equals("A")) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetAccionEstimacionHistorico());
                } else {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetAccionEstimacionOriginal());
                }
                if (pstmt != null) {
                    if (estatus.equals("A")) {
                        pstmt.setInt(1, folio);
                        pstmt.setInt(2, year);
                        pstmt.setString(3, ramo);
                        pstmt.setInt(4, meta);
                        pstmt.setInt(5, accion);
                    } else {
                        pstmt.setInt(1, year);
                        pstmt.setString(2, ramo);
                        pstmt.setInt(3, meta);
                        pstmt.setInt(4, accion);
                    }
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valorPeriodo = rsResult.getDouble("TOTAL");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    valorPeriodo = -1;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return valorPeriodo;
    }

    public boolean getRestulSQLCallProcedureINVP(int oficio, String estatus) throws SQLException {

        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            CallableStatement clstm = null;
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLGetProcedureINVP());
                if (clstm != null) {

                    clstm.setInt(1, oficio);
                    clstm.setString(2, estatus);

                    clstm.executeQuery();
                }
            } catch (SQLException sqle) {
                try {
                    conectaBD.transaccionRollback();
                    resultado = false;
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(sqle, new Throwable());
                    bitacora.grabaBitacora();
                    mensaje = sqle.getMessage();
                } catch (Exception ex) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(ex, new Throwable());
                    bitacora.grabaBitacora();
                    resultado = false;
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            } finally {
                clstm.close();
            }
        }
        if (resultado) {
            mensaje = "exito";
        }
        return resultado;
    }

    public boolean getResultSQLDeleteMovCaratula(int oficio, int year, String ramoSession, long actCaratula) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteMovCaratula(oficio, year, ramoSession, actCaratula));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }

        return resultado;
    }

    public List<Movimiento> getResultMovimientosReporteByCaratula(int year, String ramo, String appLogin, boolean isNormativo, long caratula) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientosReporteByCaratula(year, ramo, appLogin, isNormativo, caratula));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setStatusDescr(rsResult.getString("STATUS_DESCR"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPO"));
                            movimiento.setTipoOficio(rsResult.getString("TIPOFICIO"));
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public List<Movimiento> getResultSQLMovsReporteByOficio(int oficio, int year, String appLogin, boolean isNormativo) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovsReporteByOficio(oficio, year, appLogin, isNormativo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPO"));
                            movimiento.setTipoOficio(rsResult.getString("TIPOFICIO"));
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public int getResultSLQGetMaxSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto) throws SQLException {
        int maxPPTO = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMaxSubConceptoCapturaIngreso(caratula, year, ramo, concepto));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            maxPPTO = rsResult.getInt("MAX_PPTO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return maxPPTO;
    }

    public int getResultSLQGetFolioRelacionado(int folioNuevo) throws SQLException {
        int folio = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetFolioRelacionado());
                if (pstmt != null) {
                    pstmt.setInt(1, folioNuevo);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            folio = rsResult.getInt("OFICIO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return folio;
    }

    public boolean getResultSqlGetValidaCuadreTransferencias(int folio) throws SQLException {
        int descuadre = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaCuadreTransferencias());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    pstmt.setInt(2, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descuadre = rsResult.getInt("VAL");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        if (descuadre > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getResultSqlGetIsAyuntamiento() throws SQLException {
        String ayuntamiento = new String();
        boolean isAyunt = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisAyuntamiento());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ayuntamiento = rsResult.getString("AYUNTAMIENTO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (!ayuntamiento.isEmpty() && ayuntamiento.equals("S")) {
                    isAyunt = true;
                } else {
                    isAyunt = false;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return isAyunt;
    }

    public boolean getResultSqlGetparametroTrimestre() throws SQLException {
        String trimestre = new String();
        boolean isTrimestre = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetParametroTrimestre());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            trimestre = rsResult.getString("VALIDA_TRIMESTRE");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (!trimestre.isEmpty() && trimestre.equals("S")) {
                    isTrimestre = true;
                } else {
                    isTrimestre = false;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return isTrimestre;
    }

    public boolean getResultSqlGetValidaRequerimientoRecal(int oficio, String ramo, String programa, int meta, int accion,
            int requerimiento, String depto, String partida, String fuente, String fondo, String recurso, String appLogin) throws SQLException {
        int cuenta = 0;
        boolean valido = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLvalidaRequerimientoRecalendarizacion());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);
                    pstmt.setInt(4, meta);
                    pstmt.setInt(5, accion);
                    pstmt.setInt(6, requerimiento);
                    pstmt.setString(7, depto);
                    pstmt.setString(8, partida);
                    pstmt.setString(9, fuente);
                    pstmt.setString(10, fondo);
                    pstmt.setString(11, recurso);
                    pstmt.setString(12, appLogin);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuenta = rsResult.getInt("VALIDA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (cuenta > 0) {
                    valido = true;
                } else {
                    valido = false;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return valido;
    }

    public int getResultSQLgetPeriodoByFechaBitmovtos(int oficio, String tipoOficio) throws SQLException, NullPointerException {
        int periodo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetPeriodoByFechaBitmovtos());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, tipoOficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            periodo = rsResult.getInt("PERIODO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return periodo;
    }

    public int getResultSQLCountTipoficioByFolio(int oficio) throws SQLException, NullPointerException {
        int tipoficio = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountTipoOficioByFolio());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoficio = rsResult.getInt("TIPOFICIO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return tipoficio;
    }

    public int getResultSQLCountOficonsByFolio(int oficio) throws SQLException, NullPointerException {
        int oficons = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountOficonsByFolio());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            oficons = rsResult.getInt("OFICONS");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return oficons;
    }

    public boolean getResultSQLUpdateMovimientoOficioRecalAccionReqConsiderar(int year, int folio, String ramo, String prg, String depto, int meta, int accion, int requerimiento, String considerar) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMovimientoOficioRecalAccionReqConsiderar(year, folio, ramo, prg, depto, meta, accion, requerimiento, considerar));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateFolioRelacionado(int folio, String tipoOficio, int folioNuevo) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateFolioRelacionado(folio, tipoOficio, folioNuevo));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateRecalendarizacion(int oficio, String ramo, String depto, String finalidad, String funcion, String subfuncion, String programa,
            String prgConac, String tipoProy, int proy, int meta, int accion, String partida, String tipoGasto, String fuente, String fondo,
            String recurso, String mpo, int deleg, String relLaboral, int requerimiento, int mes, double diferencia, String tipoRequerimiento) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertRecalendarizacion(oficio, ramo, depto, finalidad,
                        funcion, subfuncion, programa, prgConac, tipoProy, proy, meta, accion, partida, tipoGasto, fuente, fondo,
                        recurso, mpo, deleg, relLaboral, requerimiento, mes, diferencia, tipoRequerimiento));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteRecalendarizacionByFolio(int oficio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteRecalendarizacionByFolio(oficio));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public Requerimiento getResultSQLgetRequerimientoOriginal(int year, String ramo, String programa,
            String depto, int meta, int accion, int requ, int mes) throws SQLException, IOException {

        Requerimiento requerimiento = new Requerimiento();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetRequerimientoOriginal());
                if (pstmt != null) {
                    pstmt.setInt(1, mes);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);
                    pstmt.setString(5, depto);
                    pstmt.setInt(6, meta);
                    pstmt.setInt(7, accion);
                    pstmt.setInt(8, requ);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        requerimiento = new Requerimiento();
                        while (rsResult.next()) {
                            requerimiento.setCostoAnual(rsResult.getDouble("CANT"));
                            requerimiento.setCostoUnitario(rsResult.getDouble("COSTO_UNITARIO"));
                            requerimiento.setTipoRequerimiento(rsResult.getString("PPTO_ORIGINAL"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return requerimiento;
    }

    public List<Movimiento> getResultMovimientosByCaratula(int year, String ramo, String appLogin, boolean isNormativo, long caratula) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientosByCaratula(year, ramo, appLogin, isNormativo, caratula));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setStatusDescr(rsResult.getString("STATUS_DESCR"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPO"));
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public String getResultSQLgetFoliosByCaratula(int year, String ramo, String appLogin, boolean isNormativo, long caratula) throws SQLException, IOException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String foliosList = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMovimientosByCaratula(year, ramo, appLogin, isNormativo, caratula));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            foliosList += rsResult.getString("OFICIO") + ",";
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
                if (foliosList != null && foliosList.length() > 0) {
                    foliosList = foliosList.substring(0, foliosList.length() - 1);
                }
            }
        }
        return foliosList;
    }

    public boolean getResultSQLgetUpdateAutorizoBitmovtos(int oficio, String appLogin, String autorizo,
            int tipoFlujo, String tipoUsr) throws SQLException {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLgetUpdateAutorizoBitmovtos(oficio, appLogin, autorizo, tipoFlujo, tipoUsr));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public List<Transferencia> getTransferenciaByTipoOficioMomentos(int folio, String tipoOficio, int year) throws SQLException {
        List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
        Transferencia transferencia = new Transferencia();
        MovOficiosAccionReq requerimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {

            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTransferenciasByTipoOficioMomentos());
                pstmt.setInt(1, folio);
                pstmt.setString(2, tipoOficio);
                pstmt.setInt(3, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            transferencia = new Transferencia();
                            transferencia.setRamo(rsResult.getString("RAMO"));
                            transferencia.setDepto(rsResult.getString("DEPTO"));
                            transferencia.setFinalidad(rsResult.getString("FINALIDAD"));
                            transferencia.setFuncion(rsResult.getString("FUNCION"));
                            transferencia.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            transferencia.setPrgConac(rsResult.getString("PRG_CONAC"));
                            transferencia.setPrograma(rsResult.getString("PRG"));
                            transferencia.setTipoProy(rsResult.getString("TIPO_PROY"));
                            transferencia.setProyecto(rsResult.getInt("PROYECTO"));
                            transferencia.setMeta(rsResult.getInt("META"));
                            transferencia.setAccion(rsResult.getInt("ACCION"));
                            transferencia.setPartida(rsResult.getString("PARTIDA"));
                            transferencia.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            transferencia.setFuente(rsResult.getString("FUENTE"));
                            transferencia.setFondo(rsResult.getString("FONDO"));
                            transferencia.setRecurso(rsResult.getString("RECURSO"));
                            transferencia.setMunicipio(rsResult.getString("MUNICIPIO"));
                            transferencia.setDelegacion(rsResult.getInt("DELEGACION"));
                            transferencia.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            transferencia.setImporte(rsResult.getDouble("IMPTE"));
                            transferenciaList.add(transferencia);
                            identificador++;
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transferenciaList;
    }

    public List<TransferenciaAccionReq> getTransferenciaAccionReqByFolioMomentos(int folio, String tipoOficio, int year) throws SQLException {
        List<TransferenciaAccionReq> transferenciaAccionReqList = new ArrayList<TransferenciaAccionReq>();
        TransferenciaAccionReq transferenciaAccionReq = new TransferenciaAccionReq();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 1;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSLQgetTransferenciasByFolioMomentos());
                pstmt.setInt(1, folio);
                pstmt.setInt(2, folio);
                pstmt.setString(3, tipoOficio);
                pstmt.setInt(4, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            transferenciaAccionReq = new TransferenciaAccionReq();
                            transferenciaAccionReq.setIdentidicador(identificador);
                            transferenciaAccionReq.setRamo(rsResult.getString("RAMO"));
                            transferenciaAccionReq.setDepto(rsResult.getString("DEPTO"));
                            transferenciaAccionReq.setFinalidad(rsResult.getString("FINALIDAD"));
                            transferenciaAccionReq.setFuncion(rsResult.getString("FUNCION"));
                            transferenciaAccionReq.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            transferenciaAccionReq.setPrgConac(rsResult.getString("PRG_CONAC"));
                            transferenciaAccionReq.setPrograma(rsResult.getString("PRG"));
                            transferenciaAccionReq.setTipoProy(rsResult.getString("TIPO_PROY"));
                            transferenciaAccionReq.setProy(rsResult.getInt("PROYECTO"));
                            transferenciaAccionReq.setMeta(rsResult.getInt("META"));
                            transferenciaAccionReq.setAccion(rsResult.getInt("ACCION"));
                            transferenciaAccionReq.setPartida(rsResult.getString("PARTIDA"));
                            transferenciaAccionReq.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            transferenciaAccionReq.setFuente(rsResult.getString("FUENTE"));
                            transferenciaAccionReq.setFondo(rsResult.getString("FONDO"));
                            transferenciaAccionReq.setRecurso(rsResult.getString("RECURSO"));
                            transferenciaAccionReq.setMunicipio(rsResult.getString("MUNICIPIO"));
                            transferenciaAccionReq.setDelegacion(rsResult.getInt("DELEGACION"));
                            transferenciaAccionReq.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            transferenciaAccionReq.setImporte(rsResult.getDouble("IMPTE"));
                            transferenciaAccionReqList.add(transferenciaAccionReq);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return transferenciaAccionReqList;
    }

    public List<AmpliacionReduccionAccionReq> getAmpliacionReduccionAccionReqByTipoOficioMomentos(int folio, String tipoOficio, int year) throws SQLException {
        List<AmpliacionReduccionAccionReq> amplRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
        AmpliacionReduccionAccionReq amplRedAccionReq = new AmpliacionReduccionAccionReq();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int identificador = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAmpliacionesByTipoOficioMomentos());
                pstmt.setInt(1, folio);
                pstmt.setString(2, tipoOficio);
                pstmt.setInt(3, year);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            amplRedAccionReq = new AmpliacionReduccionAccionReq();
                            amplRedAccionReq.setIdentidicador(identificador);
                            amplRedAccionReq.setRamo(rsResult.getString("RAMO"));
                            amplRedAccionReq.setDepto(rsResult.getString("DEPTO"));
                            amplRedAccionReq.setFinalidad(rsResult.getString("FINALIDAD"));
                            amplRedAccionReq.setFuncion(rsResult.getString("FUNCION"));
                            amplRedAccionReq.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            amplRedAccionReq.setPrgConac(rsResult.getString("PRG_CONAC"));
                            amplRedAccionReq.setPrograma(rsResult.getString("PRG"));
                            amplRedAccionReq.setTipoProy(rsResult.getString("TIPO_PROY"));
                            amplRedAccionReq.setProy(rsResult.getInt("PROYECTO"));
                            amplRedAccionReq.setMeta(rsResult.getInt("META"));
                            amplRedAccionReq.setAccion(rsResult.getInt("ACCION"));
                            amplRedAccionReq.setPartida(rsResult.getString("PARTIDA"));
                            amplRedAccionReq.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            amplRedAccionReq.setFuente(rsResult.getString("FUENTE"));
                            amplRedAccionReq.setFondo(rsResult.getString("FONDO"));
                            amplRedAccionReq.setRecurso(rsResult.getString("RECURSO"));
                            amplRedAccionReq.setMunicipio(rsResult.getString("MUNICIPIO"));
                            amplRedAccionReq.setDelegacion(rsResult.getInt("DELEGACION"));
                            amplRedAccionReq.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            amplRedAccionReq.setTipoMovAmpRed(rsResult.getString("TIPOMOV"));
                            amplRedAccionReq.setImporte(rsResult.getDouble("IMPTE"));
                            amplRedAccionReqList.add(amplRedAccionReq);
                            identificador++;
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return amplRedAccionReqList;
    }

    public boolean getResultSLQExisteSubConceptoCapturaIngreso(int year, String ramo, String concepto, int subconcepto, long caratula) throws SQLException {
        int cont = 0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExisteSubConceptoCapturaIngreso(year, ramo, concepto, subconcepto, caratula));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont = rsResult.getInt("CONT");
                        }
                    }
                }
                if (cont > 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSQLValidaMetaInhabilitadaTransfrec(String ramo, int meta, int oficio) throws SQLException {
        double cuenta = 0.0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaMetaInhabilitadaTransfrec());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuenta = rsResult.getInt("COUNT");
                        }
                    }
                }
                if (cuenta == 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSQLValidaAccionInhabilitadaTransfrec(String ramo, int meta, int accion, int oficio) throws SQLException {
        double cuenta = 0.0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaAccionInhabilitadaTransfrec());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuenta = rsResult.getInt("COUNT");
                        }
                    }
                }
                if (cuenta == 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSLQgetEjercidoCompromisoMeta(int year, String ramo, int meta) throws SQLException {
        double ejercido = 0.0;
        double compromiso = 0.0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetEjercidoCompromisoMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ejercido = rsResult.getDouble("EJERCIDO");
                            compromiso = rsResult.getDouble("COMPROMISO");
                        }
                    }
                }
                if ((compromiso + ejercido) == 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSLQgetEjercidoCompromisoAccion(int year, String ramo, int meta, int accion) throws SQLException {
        double ejercido = 0.0;
        double compromiso = 0.0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetEjercidoCompromisoAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ejercido = rsResult.getDouble("EJERCIDO");
                            compromiso = rsResult.getDouble("COMPROMISO");
                        }
                    }
                }
                if ((compromiso + ejercido) == 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSLQgetSumaAsignadoActualizadoMeta(int year, String ramo, int meta) throws SQLException {
        double asignado = 0.0;
        double actualizado = 0.0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetSumaAsignadoActualizadoMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            asignado = rsResult.getDouble("ASIGNADO");
                            actualizado = rsResult.getDouble("ACTUALIZADO");
                        }
                    }
                }
                if ((actualizado + asignado) == 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSLQgetSumaAsignadoActualizadoAccion(int year, String ramo, int meta, int accion) throws SQLException {
        double asignado = 0.0;
        double actualizado = 0.0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetSumaAsignadoActualizadoAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            asignado = rsResult.getDouble("ASIGNADO");
                            actualizado = rsResult.getDouble("ACTUALIZADO");
                        }
                    }
                }
                if ((actualizado + asignado) == 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSLQgetSumaAsignadoActualizadoMeta(int year, String ramo, int meta, double totalMeta, boolean isUsuario) throws SQLException {
        double asignado = 0.0;
        double actualizado = 0.0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetSumaAsignadoActualizadoMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            asignado = rsResult.getDouble("ASIGNADO");
                            actualizado = rsResult.getDouble("ACTUALIZADO");
                        }
                    }
                }
                if (isUsuario) {
                    if ((actualizado + (asignado + totalMeta)) == 0) {
                        resultado = true;
                    }
                } else {
                    if ((actualizado + asignado) == 0) {
                        resultado = true;
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSLQgetSumaAsignadoActualizadoAccion(int year, String ramo, int meta, int accion, double totalAccion, boolean isUsuario) throws SQLException {
        double asignado = 0.0;
        double actualizado = 0.0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetSumaAsignadoActualizadoAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            asignado = rsResult.getDouble("ASIGNADO");
                            actualizado = rsResult.getDouble("ACTUALIZADO");
                        }
                    }
                }
                if (isUsuario) {
                    if ((actualizado + (asignado + totalAccion)) == 0) {
                        resultado = true;
                    }
                } else {
                    if ((actualizado + asignado) == 0) {
                        resultado = true;
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSQLisAvanceMetaCapturada(int year, String ramo, int meta) throws SQLException {
        int cont = 0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisAvanceCapturadoMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont = rsResult.getInt("AVANCE");
                        }
                    }
                }
                if (cont == 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSQLisAvanceAccionCapturado(int year, String ramo, int meta, int accion) throws SQLException {
        int cont = 0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisAvanceCapturadoAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont = rsResult.getInt("AVANCE");
                        }
                    }
                }
                if (cont == 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSQLcountRecalendarizacion(int folio) throws SQLException {
        int cont = 0;
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountRecalendarizacion());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont = rsResult.getInt("CUENTA");
                        }
                    }
                }
                if (cont > 0) {
                    resultado = true;
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdatePeriodoMeta(int year, String ramo, int meta) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLupdatePeriodoMeta(year, ramo, meta));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdatePeriodoAccion(int year, String ramo, int meta, int accion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLupdatePeriodoAccion(year, ramo, meta, accion));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdatePeriodoAccionByMeta(int year, String ramo, int meta) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                if (this.getResultSQLcountAccionesByMeta(year, ramo, meta) > 0) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLupdatePeriodoAccionByMeta(year, ramo, meta));
                } else {
                    resultado = true;
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public Requerimiento getResultSQLgetRequerimientoByLlave(int year, String ramo, int meta, int accion, int requerimiento) throws SQLException {
        Requerimiento req = new Requerimiento();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetAccionReqByLlave());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    pstmt.setInt(5, requerimiento);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            req.setCantEne(rsResult.getDouble("ENE"));
                            req.setCantFeb(rsResult.getDouble("FEB"));
                            req.setCantMar(rsResult.getDouble("MAR"));
                            req.setCantAbr(rsResult.getDouble("ABR"));
                            req.setCantMay(rsResult.getDouble("MAY"));
                            req.setCantJun(rsResult.getDouble("JUN"));
                            req.setCantJul(rsResult.getDouble("JUL"));
                            req.setCantAgo(rsResult.getDouble("AGO"));
                            req.setCantSep(rsResult.getDouble("SEP"));
                            req.setCantOct(rsResult.getDouble("OCT"));
                            req.setCantNov(rsResult.getDouble("NOV"));
                            req.setCantDic(rsResult.getDouble("DIC"));
                            req.setCostoUnitario(rsResult.getDouble("COSTO_UNITARIO"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return req;
    }

    public int getResultSQLcountAccionesByMeta(int year, String ramo, int meta) throws SQLException {
        int contAccion = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetAccionesByMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        contAccion = rsResult.getInt("CONT");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return contAccion;
    }

    public int getRolNormativo(int rol) throws SQLException {
        int isNormativo = 0;
        int rolPRG = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetRolesPrg());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            rolPRG = rsResult.getInt("ROL");
                            if (rolPRG == rol) {
                                isNormativo = 1;
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return isNormativo;
    }

    public boolean getResultSQLcountBitmovtosByFolio(int folio) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int count = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountBitmovtosByOficio());
                if (pstmt != null) {
                    pstmt.setInt(1, folio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("BITMOVTOS");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }

    public double getResultSQLgetDisponibleParaestatal(int year, int mes, String ramo, String prg, String tipoProy,
            int proyecto, int meta, int accion, String partida, String relLaboral, String fuente, String fondo, String recurso) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double disponible = 0.0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                if (relLaboral.isEmpty() || relLaboral == null) {
                    relLaboral = "0";
                }
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetDisponibleParaestatal());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setInt(2, mes);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, prg);
                    pstmt.setString(5, tipoProy);
                    pstmt.setInt(6, proyecto);
                    pstmt.setInt(7, meta);
                    pstmt.setInt(8, accion);
                    pstmt.setString(9, partida);
                    pstmt.setString(10, relLaboral);
                    pstmt.setString(11, fuente);
                    pstmt.setString(12, fondo);
                    pstmt.setString(13, recurso);

                    pstmt.setInt(14, year);
                    pstmt.setInt(15, mes);
                    pstmt.setString(16, ramo);
                    pstmt.setString(17, prg);
                    pstmt.setString(18, tipoProy);
                    pstmt.setInt(19, proyecto);
                    pstmt.setInt(20, meta);
                    pstmt.setInt(21, accion);
                    pstmt.setString(22, partida);
                    pstmt.setString(23, relLaboral);
                    pstmt.setString(24, fuente);
                    pstmt.setString(25, fondo);
                    pstmt.setString(26, recurso);

                    pstmt.setInt(27, year);
                    pstmt.setInt(28, mes);
                    pstmt.setString(29, ramo);
                    pstmt.setString(30, prg);
                    pstmt.setString(31, tipoProy);
                    pstmt.setInt(32, proyecto);
                    pstmt.setInt(33, meta);
                    pstmt.setInt(34, accion);
                    pstmt.setString(35, partida);
                    pstmt.setString(36, relLaboral);
                    pstmt.setString(37, fuente);
                    pstmt.setString(38, fondo);
                    pstmt.setString(39, recurso);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            disponible = rsResult.getInt("IMPORTE");
                        }
                    }
                }
                disponible = disponible + this.getResultSQLgetAsignadoParaestatal(year, mes, ramo,
                        prg, tipoProy, proyecto, meta, accion, partida, relLaboral,
                        fuente, fondo, recurso);
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return disponible;
    }

    public double getResultSQLgetAsignadoParaestatal(int year, int mes, String ramo, String prg, String tipoProy,
            int proyecto, int meta, int accion, String partida, String relLaboral, String fuente, String fondo, String recurso) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        double asignado = 0.0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                if (relLaboral.isEmpty() || relLaboral == null) {
                    relLaboral = "0";
                }
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetAsignadoParaestatalByMes());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setInt(2, mes);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, prg);
                    pstmt.setString(5, tipoProy);
                    pstmt.setInt(6, proyecto);
                    pstmt.setInt(7, meta);
                    pstmt.setInt(8, accion);
                    pstmt.setString(9, partida);
                    pstmt.setString(10, relLaboral);
                    pstmt.setString(11, fuente);
                    pstmt.setString(12, fondo);
                    pstmt.setString(13, recurso);

                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            asignado = rsResult.getInt("ASIGNADO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return asignado;
    }

    public List<Movimiento> getResultMovimientosByRamo(String ramo, int year) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovimientosByRamo());
                if (pstmt != null) {
                    pstmt.setString(1, ramo);
                    pstmt.setInt(2, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            if (rsResult.getString("TIPO") != null) {
                                movimiento.setTipoOficio(rsResult.getString("TIPO"));
                            } else {
                                movimiento.setTipoOficio("");
                            }
                            if (rsResult.getString("STATUSDESCR") != null) {
                                movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            } else {
                                movimiento.setStatusDescr("");
                            }
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public List<MovoficioLigado> getResultGetMovOficioLigadoPendiente(int year, int oficio, String tipoProceso) throws SQLException, IOException {

        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        MovoficioLigado movoficioLigado = null;
        List<MovoficioLigado> movOficiosLigados = new ArrayList<MovoficioLigado>();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMovOficioLigadoPendiente(year, oficio, tipoProceso));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movoficioLigado = new MovoficioLigado();
                            movoficioLigado.setYear(rsResult.getInt("YEAR"));
                            movoficioLigado.setOficio(rsResult.getInt("OFICIO"));
                            movoficioLigado.setOficioLigado(rsResult.getInt("OFICIO_LIGADO"));
                            movoficioLigado.setLigadoParaestatal(rsResult.getString("LIGADO_PARAESTATAL"));
                            movoficioLigado.setPendiente(rsResult.getBoolean("PENDIENTE"));
                            movOficiosLigados.add(movoficioLigado);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movOficiosLigados;
    }

    public boolean getResultSQLUpdateConsiderarTransAmp(String tabla, String considerar, int oficio, int consec) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateConsiderarTransAmp(tabla, considerar, oficio, consec));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateIngresoPropio(String considerar, int oficio, int consec) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                PreparedStatement pstmt = null;
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLUpdateIngresoPropio());
                if (pstmt != null) {
                    pstmt.setString(1, considerar);
                    pstmt.setInt(2, oficio);
                    pstmt.setInt(3, consec);
                    if (pstmt.executeUpdate() > 0) {
                        resultado = true;
                    }
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public List<AccionReq> getResultSQLgetRequerimientosByFuenteFin(int year, String ramo,
            int meta, int accion, String fuente, String fondo, String recurso) throws SQLException {

        AccionReq requerimiento = null;

        List<AccionReq> requList = new ArrayList<AccionReq>();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetRequerimientosByFuenteFin());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    pstmt.setString(5, fuente);
                    pstmt.setString(6, fondo);
                    pstmt.setString(7, recurso);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            requerimiento = new AccionReq();
                            requerimiento.setRamo(rsResult.getString("RAMO"));
                            requerimiento.setPrg(rsResult.getString("PRG"));
                            requerimiento.setDepto(rsResult.getString("DEPTO"));
                            requerimiento.setMeta(rsResult.getInt("META"));
                            requerimiento.setAccion(rsResult.getInt("ACCION"));
                            requerimiento.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                            requerimiento.setDescr(rsResult.getString("DESCR"));
                            requerimiento.setFuente(rsResult.getInt("FUENTE"));
                            requerimiento.setFondo(rsResult.getInt("FONDO"));
                            requerimiento.setRecurso(rsResult.getInt("RECURSO"));
                            requerimiento.setsFuente(rsResult.getString("FUENTE"));
                            requerimiento.setsFondo(rsResult.getString("FONDO"));
                            requerimiento.setsRecurso(rsResult.getString("RECURSO"));
                            requerimiento.setTipoGasto(rsResult.getInt("TIPO_GASTO"));
                            requerimiento.setPartida(rsResult.getString("PARTIDA"));
                            requerimiento.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            requerimiento.setCant(rsResult.getDouble("CANTIDAD"));
                            requerimiento.setCostoUni(rsResult.getDouble("COSTO_UNITARIO"));
                            requerimiento.setCostoAn(rsResult.getDouble("COSTO_ANUAL"));
                            requerimiento.setEnero(rsResult.getDouble("ENE"));
                            requerimiento.setFebrero(rsResult.getDouble("FEB"));
                            requerimiento.setMarzo(rsResult.getDouble("MAR"));
                            requerimiento.setAbril(rsResult.getDouble("ABR"));
                            requerimiento.setMayo(rsResult.getDouble("MAY"));
                            requerimiento.setJunio(rsResult.getDouble("JUN"));
                            requerimiento.setJulio(rsResult.getDouble("JUL"));
                            requerimiento.setAgosto(rsResult.getDouble("AGO"));
                            requerimiento.setSeptiembre(rsResult.getDouble("SEP"));
                            requerimiento.setOctubre(rsResult.getDouble("OCT"));
                            requerimiento.setNoviembre(rsResult.getDouble("NOV"));
                            requerimiento.setDiciembre(rsResult.getDouble("DIC"));
                            requerimiento.setArchivo(rsResult.getInt("ARCHIVO"));
                            requerimiento.setArticulo(rsResult.getInt("ARTICULO"));
                            requerimiento.setJustificado(rsResult.getString("JUSTIFICACION"));
                            requerimiento.setGpoGasto(rsResult.getString("GPOGTO"));
                            requerimiento.setSubGrupo(rsResult.getString("SUBGPO"));
                            requList.add(requerimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return requList;
    }

    public List<AccionReq> getResultSQLgetRequerimientosByAccion(int year, String ramo,
            int meta, int accion) throws SQLException {

        AccionReq requerimiento = null;

        List<AccionReq> requList = new ArrayList<AccionReq>();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetRequerimientosByAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            requerimiento = new AccionReq();
                            requerimiento.setRamo(rsResult.getString("RAMO"));
                            requerimiento.setPrg(rsResult.getString("PRG"));
                            requerimiento.setDepto(rsResult.getString("DEPTO"));
                            requerimiento.setMeta(rsResult.getInt("META"));
                            requerimiento.setAccion(rsResult.getInt("ACCION"));
                            requerimiento.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                            requerimiento.setDescr(rsResult.getString("DESCR"));
                            requerimiento.setFuente(rsResult.getInt("FUENTE"));
                            requerimiento.setFondo(rsResult.getInt("FONDO"));
                            requerimiento.setRecurso(rsResult.getInt("RECURSO"));
                            requerimiento.setsFuente(rsResult.getString("FUENTE"));
                            requerimiento.setsFondo(rsResult.getString("FONDO"));
                            requerimiento.setsRecurso(rsResult.getString("RECURSO"));
                            requerimiento.setTipoGasto(rsResult.getInt("TIPO_GASTO"));
                            requerimiento.setPartida(rsResult.getString("PARTIDA"));
                            requerimiento.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            requerimiento.setCant(rsResult.getDouble("CANTIDAD"));
                            requerimiento.setCostoUni(rsResult.getDouble("COSTO_UNITARIO"));
                            requerimiento.setCostoAn(rsResult.getDouble("COSTO_ANUAL"));
                            requerimiento.setEnero(rsResult.getDouble("ENE"));
                            requerimiento.setFebrero(rsResult.getDouble("FEB"));
                            requerimiento.setMarzo(rsResult.getDouble("MAR"));
                            requerimiento.setAbril(rsResult.getDouble("ABR"));
                            requerimiento.setMayo(rsResult.getDouble("MAY"));
                            requerimiento.setJunio(rsResult.getDouble("JUN"));
                            requerimiento.setJulio(rsResult.getDouble("JUL"));
                            requerimiento.setAgosto(rsResult.getDouble("AGO"));
                            requerimiento.setSeptiembre(rsResult.getDouble("SEP"));
                            requerimiento.setOctubre(rsResult.getDouble("OCT"));
                            requerimiento.setNoviembre(rsResult.getDouble("NOV"));
                            requerimiento.setDiciembre(rsResult.getDouble("DIC"));
                            requerimiento.setArchivo(rsResult.getInt("ARCHIVO"));
                            requerimiento.setsArchivo(rsResult.getString("ARCHIVO"));
                            requerimiento.setArticulo(rsResult.getInt("ARTICULO"));
                            requerimiento.setJustificado(rsResult.getString("JUSTIFICACION"));
                            requerimiento.setGpoGasto(rsResult.getString("GPOGTO"));
                            requerimiento.setSubGrupo(rsResult.getString("SUBGPO"));
                            requList.add(requerimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return requList;
    }

    public boolean getResultSQLupdateAccionReqFuente(int year, String ramo, int meta,
            int accion, int reqerimiento, String fuente, String fondo, String recurso) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.
                        getSQLupdateAccionReqFuente(year, ramo, meta, accion,
                                reqerimiento, fuente, fondo, recurso));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLupdatePptoFuente(int year, String ramo, int meta,
            int accion, String fuenteN, String fondoN, String recursoN,
            String fuente, String fondo, String recurso) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.
                        getSQLupdatePttoFuente(year, ramo, meta, accion, fuenteN, fondoN, recursoN, fuente, fondo, recurso));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLvalidaDeptoPlantilla(int year, String ramo,
            String prg, String depto, int meta, int accion) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        boolean plantBool = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCuentaPlantillaDepto());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, prg);
                    pstmt.setString(4, depto);
                    pstmt.setInt(5, meta);
                    pstmt.setInt(6, accion);

                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            plantBool = Boolean.parseBoolean(rsResult.getString("PLANTILLA"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return plantBool;
    }

    public int getResultSQLvalidaCapturaCIM(int periodo, int year, String ramos, String prgI, String prgF) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int iContador = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaCapturaCIM(periodo, year, ramos, prgI, prgF));
                if (pstmt != null) {

                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            iContador = rsResult.getInt("CONTADOR");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return iContador;
    }

    public String getResultSQLParamvalidaCIM() throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String svalida = "";
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLParamValidaCIM());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            svalida = rsResult.getString("REPVALIDA_INFOCIM");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return svalida;
    }

    public int getResultValidarPresXCodigoPrgModif(String ramos, int year) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidarPresXCodigoPrgModif(ramos, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            countRow = rsResult.getInt("cuenta");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                if (pstmt == null) {
                    countRow = 0;
                }
                pstmt.close();
                rsResult.close();
            }
        }
        return countRow;
    }

    public Parametros getResultParametros() throws Exception {
        Parametros objParametros = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLParametros());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        objParametros = new Parametros();
                        objParametros.setRepWebImpFirma(rsResult.getString("REPWEB_IMPFIRMA"));
                        objParametros.setValidaTrimestre(rsResult.getString("VALIDA_TRIMESTRE"));
                        objParametros.setReporteCierre(rsResult.getString("REPORTE_CIERRE"));
                        objParametros.setRepValidaInfoCim(rsResult.getString("REPVALIDA_INFOCIM"));
                        objParametros.setValidaTodosTrimestre(rsResult.getString("VALIDA_TODOS_TRIMESTRE"));
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return objParametros;
    }

    public boolean getResultSQLSaveParametros(String repWebImpFirma, String validaTrimestre, String reporteCierre, String repValidaInfoCim, String validaTodosTrimestre) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLSaveParametros(repWebImpFirma, validaTrimestre, reporteCierre, repValidaInfoCim, validaTodosTrimestre));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public ArrayList<Programa> getResultSQLProgramasByYearAppLogin(int year, String usuario, String sRamo, String sClaveIndicador) throws Exception {
        ArrayList<Programa> programaList = new ArrayList<Programa>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Programa programa = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProgramasByYearAppLogin(year, usuario, sRamo, sClaveIndicador));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = new Programa();
                            programa.setProgramaId(rsResult.getString("PRG"));
                            programa.setProgramaDesc(rsResult.getString("DESCR"));
                            programa.setUsuario(rsResult.getString("APP_LOGIN"));
                            programaList.add(programa);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return programaList;
    }

    public List<Meta> getResultgetAllMetasByRamo(String ramo, int year) throws SQLException {
        List<Meta> metaList = new ArrayList<Meta>();
        Meta meta;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLAllMetasByRamo());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            meta = new Meta();
                            meta.setMeta(rsResult.getString("DESCR"));
                            meta.setMetaId(rsResult.getInt("META"));
                            metaList.add(meta);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return metaList;
    }

    public List<Accion> getResultgetAllAccionesByMeta(String ramo, int meta, int year) throws SQLException {
        List<Accion> accionList = new ArrayList<Accion>();
        Accion accion;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLAllAccionesByMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accion = new Accion();
                            accion.setAccion(rsResult.getString("DESCR"));
                            accion.setAccionId(rsResult.getInt("ACCION"));
                            accionList.add(accion);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return accionList;
    }

    public boolean getResultSQLDeletePPTOenCeroByAccion(int year, String ramo, int meta, int accion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query
                        .getSQLDeletePPTOenCeroByAccion(year, ramo, meta, accion));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteCodigosByAccion(int year, String ramo, int meta, int accion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query
                        .getSQLDeleteCodigosByAccion(year, ramo, meta, accion));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultGetPresuPlantillaByAccion(int year, String ramo, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String relLaboral) throws SQLException {
        boolean presPlantilla = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountPresupPlantillaByAccionReq());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    pstmt.setString(5, partida);
                    pstmt.setString(6, tipoGasto);
                    pstmt.setString(7, fuente);
                    pstmt.setString(8, fondo);
                    pstmt.setString(9, recurso);
                    pstmt.setString(10, relLaboral);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            presPlantilla = Boolean
                                    .parseBoolean(rsResult.getString("PRES_PLANTILLA"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return presPlantilla;
    }

    public boolean getResultSQLUpdatePresupPlantillaByAccionReq(CodigoPPTO codigoN, int year,
            String ramo, int meta, int accion, String partida, String tipoGasto, String fuente,
            String fondo, String recurso, String relLaboral) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query
                        .getSQLupdatePresupPlantillaByAccionReq(codigoN,
                                year, ramo, meta, accion, partida,
                                tipoGasto, fuente, fondo, recurso, relLaboral));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdatePresupPlantillaByAccionReqFF(int year,
            String ramo, int meta, int accion, String partida, String tipoGasto, String fuente,
            String fondo, String recurso, String relLaboral, String fuenteN, String fondoN, String recursoN) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query
                        .getSQLupdatePresupPlantillaByAccionReqFF(year, ramo,
                                meta, accion, partida, tipoGasto, fuente, fondo,
                                recurso, relLaboral, fuenteN, fondoN, recursoN));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateMetaAccionPlantilla(CodigoPPTO codigoN, int year,
            String ramo, String prg, String depto, int meta, int accion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                if (!getResultSQLCountMetaAccionPlantilla(year, ramo, codigoN.getPrograma(), codigoN.getDepto())) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query
                            .getSQLUpdateMetaAccionPlantilla(codigoN, year, ramo, prg, depto, meta, accion));
                } else {
                    resultado = true;
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLCountMetaAccionPlantilla(int year,
            String ramo, String prg, String depto) throws SQLException {
        boolean presPlantilla = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountMetaAccionPlantilla());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, prg);
                    pstmt.setString(4, depto);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            presPlantilla = Boolean
                                    .parseBoolean(rsResult.getString("PLANTILLA"));
                        }
                    }
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return presPlantilla;
    }

    public BigDecimal getResultSQLgetAcumuladoCaptura(int year, int oficio, String ramo,
            String partida) throws SQLException {
        BigDecimal acumulado = new BigDecimal(0);
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLAcumuladoCaptura());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, partida);
                    pstmt.setString(3, ramo);
                    pstmt.setInt(4, year);
                    pstmt.setInt(5, oficio);
                    pstmt.setString(6, partida);
                    pstmt.setString(7, ramo);
                    pstmt.setInt(8, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            acumulado = new BigDecimal(rsResult.getDouble("ACUM"));
                        }
                    }
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return acumulado;
    }

    public boolean getResultSQLRebootMovoficiosSequence() throws Exception {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            CallableStatement clstm = null;
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLgetPlainMovoficiosSequenceValue());
                if (clstm != null) {
                    clstm.executeQuery();
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } catch (Exception ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                clstm.close();
            }
        }
        return resultado;
    }

    public boolean getResultSQLIsSequenceRebooted(int year) throws SQLException {
        boolean presPlantilla = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsSequenceRebooted());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            presPlantilla = Boolean
                                    .parseBoolean(rsResult.getString("REG_SEQ"));
                        }
                    }
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return presPlantilla;
    }

    public boolean getResultSQLIsYearReinicioRegistrado(int year) throws SQLException {
        boolean presPlantilla = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsYearReinicioRegistrado());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            presPlantilla = Boolean
                                    .parseBoolean(rsResult.getString("REG_YEAR"));
                        }
                    }
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return presPlantilla;
    }

    public boolean getResultSQLIsPptoCongelado(int year) throws SQLException {
        boolean presPlantilla = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsPPTOCongelado());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            presPlantilla = Boolean
                                    .parseBoolean(rsResult.getString("REG_CONG"));
                        }
                    }
                }
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return presPlantilla;
    }

    public boolean getResultSQLInsertRegistroReinicio(int year, int secuencia, int congelado) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query
                        .getSQLInsertRegistroReinicio(year, secuencia, congelado));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateRegistroReinicio(int year, boolean isSecuencia) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query
                        .getSQLUpdateRegistroReinicio(year, isSecuencia));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public int getResultSQLCountValidaTransferenciasOficons(int oficio) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountValidaTransferenciasOficons(oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt(1);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public int getResultSQLCountValidaAmpliacionesOficons(int oficio) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLCountValidaAmpliacionesOficons(oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt(1);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public List<Accion> getResultSQLValidaAccionesInabilitadasByMeta(int year, String ramo, int meta) throws SQLException, NullPointerException {
        List<Accion> accionList = new ArrayList<Accion>();
        Accion accion = new Accion();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getQueryCountValidaAccionesInhabilitadasByMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accion = new Accion();
                            accion.setRamo(rsResult.getString("RAMO"));
                            accion.setMeta(rsResult.getInt("META"));
                            accion.setAccionId(rsResult.getInt("ACCION"));
                            accionList.add(accion);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return accionList;
    }

    public boolean getResultSQLCountMovoficiosAccion(int oficio, String ramo, int meta) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLcountMovoficiosAccion());
                pstmt.setInt(1, oficio);
                pstmt.setString(2, ramo);
                pstmt.setInt(3, meta);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("ACCION");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        if (cuantos > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<MovoficioLigado> getResultGetMovOficioLigados(int year, int oficio) throws SQLException, IOException {

        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        MovoficioLigado movoficioLigado = null;
        List<MovoficioLigado> movOficiosLigados = new ArrayList<MovoficioLigado>();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMovOficioLigados(year, oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movoficioLigado = new MovoficioLigado();
                            movoficioLigado.setYear(rsResult.getInt("YEAR"));
                            movoficioLigado.setOficio(rsResult.getInt("OFICIO"));
                            movoficioLigado.setOficioLigado(rsResult.getInt("OFICIO_LIGADO"));
                            movoficioLigado.setLigadoParaestatal(rsResult.getString("LIGADO_PARAESTATAL"));
                            movOficiosLigados.add(movoficioLigado);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movOficiosLigados;
    }

    public boolean getResultSQLIsOficioLigado(int year, int oficio, String isParaestatal) throws Exception {
        boolean isOficioLigado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsOficioLigado(year, oficio, isParaestatal));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        isOficioLigado = rsResult.getBoolean("TOTAL");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return isOficioLigado;
    }

    public boolean getResultSQLIsOficioLigadoNormativo(int year, int oficio) throws Exception {
        boolean isOficioLigado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsOficioLigadoNormativo(year, oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        isOficioLigado = rsResult.getBoolean("TOTAL");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return isOficioLigado;
    }

    public int getResultSQLGetOficioLigadoNormativo(int year, int oficioLigado, String isParaestatal) throws Exception {
        int oficioNormativo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetOficioLigadoNormativo(year, oficioLigado, isParaestatal));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        oficioNormativo = rsResult.getInt("OFICIO");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return oficioNormativo;
    }

    public String getResultSQLGetRamoByOficio(int oficio) throws Exception {
        String ramo = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamoByOficio(oficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        ramo = rsResult.getString("RAMO");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return ramo;
    }

    public int getResultSQLValidaOficioAutorizadoMeta(int year, String ramo, int meta, Date fechaCaptura) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaOficioAutorizadoMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setString(4, fechaCaptura.toString());
                    pstmt.setInt(5, year);
                    pstmt.setString(6, ramo);
                    pstmt.setInt(7, meta);
                    pstmt.setString(8, fechaCaptura.toString());
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("OFICIO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public int getResultSQLValidaOficioAutorizadoAccion(int year, String ramo, int meta, int accion, Date fechaCaptura) throws SQLException, NullPointerException {
        int cuantos = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaOficioAutorizadoAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    pstmt.setString(5, fechaCaptura.toString());
                    pstmt.setInt(6, year);
                    pstmt.setString(7, ramo);
                    pstmt.setInt(8, meta);
                    pstmt.setInt(9, accion);
                    pstmt.setString(10, fechaCaptura.toString());
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cuantos = rsResult.getInt("OFICIO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return cuantos;
    }

    public String getSQLValidaModificacionEstimacionAutorizado(int oficio, String ramo, int meta) throws SQLException, NullPointerException {
        String valAutoriza = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaModificacionEstimacionAutorizado());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valAutoriza = rsResult.getString("VAL_AUTORIZADO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return valAutoriza;
    }

    public String getResultSQLValidaModificacionMetaAutorizado(int oficio, String ramo, int meta) throws SQLException, NullPointerException {
        String valAutoriza = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaModificacionMetaAutorizado());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valAutoriza = rsResult.getString("VAL_AUTORIZADO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return valAutoriza;
    }

    public String getSQLValidaModificacionAccionEstimacionAutorizado(int oficio, String ramo, int meta, int accion) throws SQLException, NullPointerException {
        String valAutoriza = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaModificacionAccionEstimacionAutorizado());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valAutoriza = rsResult.getString("VAL_AUTORIZADO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return valAutoriza;
    }

    public String getResultSQLValidaModificacionAccionAutorizado(int oficio, String ramo, int meta, int accion) throws SQLException, NullPointerException {
        String valAutoriza = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaModificacionAccionAutorizado());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valAutoriza = rsResult.getString("VAL_AUTORIZADO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return valAutoriza;
    }

    public boolean getResultSQLUpdateModificacionEstimacion(String valAutorizado, int oficio, String ramo, int meta) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean bError = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                bError = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateValidaMoficacionEstimacion(valAutorizado, oficio, ramo, meta));
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return bError;
    }

    public boolean getResultSQLUpdateModificacionAccionEstimacion(String valAutorizado, int oficio, String ramo, int meta, int accion) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean bError = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                bError = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateValidaMoficacionAccionEstimacion(valAutorizado, oficio, ramo, meta, accion));
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return bError;
    }

    public boolean getResultSQLUpdateModificacionAccion(String valAutorizado, int oficio, String ramo, int meta, int accion) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean bError = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                bError = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateValidaMoficacionAccion(valAutorizado, oficio, ramo, meta, accion));
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return bError;
    }

    public boolean getResultSQLUpdateModificacionMeta(String valAutorizado, int oficio, String ramo, int meta) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean bError = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                bError = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateValidaMoficacionMeta(valAutorizado, oficio, ramo, meta));
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return bError;
    }

    public List<Ramo> getResultSQLgetRamoListByMTipoMovto(String tipoMovto, String estatus, int year) throws SQLException, NullPointerException {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        Ramo ramo = new Ramo();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetRamosListByMovimientos());
                if (pstmt != null) {
                    pstmt.setString(1, tipoMovto);
                    pstmt.setString(2, estatus);
                    pstmt.setInt(3, year);
                    pstmt.setString(4, tipoMovto);
                    pstmt.setString(5, estatus);
                    pstmt.setInt(6, year);
                    pstmt.setString(7, tipoMovto);
                    pstmt.setString(8, estatus);
                    pstmt.setInt(9, year);
                    pstmt.setString(10, tipoMovto);
                    pstmt.setString(11, estatus);
                    pstmt.setInt(12, year);
                    pstmt.setString(13, tipoMovto);
                    pstmt.setString(14, estatus);
                    pstmt.setInt(15, year);
                    pstmt.setInt(16, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ramo = new Ramo();
                            ramo.setRamo(rsResult.getString("RAMO"));
                            ramo.setRamoDescr(rsResult.getString("DESCR"));
                            ramoList.add(ramo);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return ramoList;
    }

    public List<Ramo> getResultSQLgetRamoListByMTipoMovtoAmpTrans(String tipoMovto, String estatus, int year) throws SQLException, NullPointerException {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        Ramo ramo = new Ramo();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetRamosListByMovimientosAmpTrans());
                if (pstmt != null) {
                    pstmt.setString(1, tipoMovto);
                    pstmt.setString(2, estatus);
                    pstmt.setInt(3, year);
                    pstmt.setString(4, tipoMovto);
                    pstmt.setString(5, estatus);
                    pstmt.setInt(6, year);
                    pstmt.setString(7, tipoMovto);
                    pstmt.setString(8, estatus);
                    pstmt.setInt(9, year);
                    pstmt.setString(10, tipoMovto);
                    pstmt.setString(11, estatus);
                    pstmt.setInt(12, year);
                    pstmt.setString(13, tipoMovto);
                    pstmt.setString(14, estatus);
                    pstmt.setInt(15, year);
                    pstmt.setString(16, tipoMovto);
                    pstmt.setString(17, estatus);
                    pstmt.setInt(18, year);
                    pstmt.setInt(19, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ramo = new Ramo();
                            ramo.setRamo(rsResult.getString("RAMO"));
                            ramo.setRamoDescr(rsResult.getString("DESCR"));
                            ramoList.add(ramo);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return ramoList;
    }

    public List<Ramo> getResultSQLgetRamoListByMTipoMovtoAmpTransTipof(String tipoMovto, String estatus, int year) throws SQLException, NullPointerException {
        List<Ramo> ramoList = new ArrayList<Ramo>();
        Ramo ramo;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetRamosListByMovimientosAmpTransTipof());
                if (pstmt != null) {
                    pstmt.setString(1, tipoMovto);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, estatus);
                    pstmt.setString(4, tipoMovto);
                    pstmt.setInt(5, year);
                    pstmt.setString(6, estatus);
                    pstmt.setString(7, tipoMovto);
                    pstmt.setInt(8, year);
                    pstmt.setString(9, estatus);
                    pstmt.setString(10, tipoMovto);
                    pstmt.setInt(11, year);
                    pstmt.setString(12, estatus);
                    pstmt.setString(13, tipoMovto);
                    pstmt.setInt(14, year);
                    pstmt.setString(15, estatus);
                    pstmt.setString(16, tipoMovto);
                    pstmt.setInt(17, year);
                    pstmt.setString(18, estatus);
                    pstmt.setString(19, tipoMovto);
                    pstmt.setInt(20, year);
                    pstmt.setString(21, estatus);
                    pstmt.setInt(22, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ramo = new Ramo();
                            ramo.setRamo(rsResult.getString("RAMO"));
                            ramo.setRamoDescr(rsResult.getString("DESCR"));
                            ramoList.add(ramo);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return ramoList;
    }

    public boolean getResultSQLIsTraeResultadoMeta(int year, String ramo, int meta) throws SQLException {
        boolean resultado = false;
        String strResultado = "";
        //  AccionReq requerimiento = null;

        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsTraeResultadoMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, meta);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            strResultado = rsResult.getString("RESULTADO");
                            if (strResultado.equals("N")) {
                                resultado = false;
                            } else if (strResultado.equals("S")) {
                                resultado = true;
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSQLOnTraeResultadoMeta(int year, String ramo, int meta) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.
                        getSQLOnTraeResultadoMeta(year, ramo, meta));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLOffTraeResultadoMeta(int year, String ramo, int meta) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLOffTraeResultadoMeta(year, ramo, meta));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLIsTraeResultadoAccion(int year, String ramo, int meta, int accion) throws SQLException {
        boolean resultado = false;
        String strResultado = "";
        //  AccionReq requerimiento = null;

        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsTraeResultadoAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, meta);
                    pstmt.setInt(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            strResultado = rsResult.getString("RESULTADO");
                            if (strResultado.equals("N")) {
                                resultado = false;
                            } else if (strResultado.equals("S")) {
                                resultado = true;
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return resultado;
    }

    public boolean getResultSQLOnTraeResultadoAccion(int year, String ramo, int meta, int accion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.
                        getSQLOnTraeResultadoAccion(year, ramo, meta, accion));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public boolean getResultSQLOffTraeResultadoAccion(int year, String ramo, int meta, int accion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLOffTraeResultadoAccion(year, ramo, meta, accion));

            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return resultado;
    }

    public String getResultSQLGetUnidadMedidaMetaAvancePoa(int year, String ramo, int meta) throws SQLException {
        String descr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLAvanceMetasAvancePoaMuestraUnidadMedida());
                if (pstmt != null) {
                    pstmt.setInt(1, meta);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descr = rsResult.getString("META_MEDIDA_DESCR");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return descr;
    }

    public String getResultSQLGetUnidadMedidaAccionAvancePoa(int year, String ramo, int meta, int accion) throws SQLException {
        String descr = new String();
        ResultSet rsResult = null;
        QuerysBD qbdQuery = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qbdQuery.getSQLAvanceAccionesAvancePoaMuestraUnidadMedida());
                if (pstmt != null) {
                    pstmt.setInt(1, accion);
                    pstmt.setInt(2, meta);
                    pstmt.setString(3, ramo);
                    pstmt.setInt(4, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            descr = rsResult.getString("ACCION_MEDIDA_DESCR");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return descr;
    }

    public List<Movimiento> getResultMovimientoListByRamoAfectado(String tipoMovimiento, String estatusBase, int year, String ramoAfectado) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMovimientosListByRamoAfectado());
                if (pstmt != null) {
                    pstmt.setString(1, tipoMovimiento);
                    pstmt.setString(2, estatusBase);
                    pstmt.setInt(3, year);
                    pstmt.setString(4, tipoMovimiento);
                    pstmt.setString(5, estatusBase);
                    pstmt.setInt(6, year);
                    pstmt.setString(7, tipoMovimiento);
                    pstmt.setString(8, estatusBase);
                    pstmt.setInt(9, year);
                    pstmt.setString(10, tipoMovimiento);
                    pstmt.setString(11, estatusBase);
                    pstmt.setInt(12, year);
                    pstmt.setString(13, tipoMovimiento);
                    pstmt.setString(14, estatusBase);
                    pstmt.setInt(15, year);
                    pstmt.setInt(16, year);
                    pstmt.setString(17, ramoAfectado);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            /*if (rsResult.getString("TIPO") != null) {
                             movimiento.setTipoOficio(rsResult.getString("TIPO"));
                             } else {*/
                            movimiento.setTipoOficio("");
                            /*}*/
                            if (rsResult.getString("STATUSDESCR") != null) {
                                movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            } else {
                                movimiento.setStatusDescr("");
                            }
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public List<Movimiento> getResultMovimientoListByRamoAfectadoTransAmp(String tipoMovimiento, String estatusBase, int year, String ramoAfectado) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMovimientosListByRamoAfectadoTransAmp());
                if (pstmt != null) {
                    pstmt.setString(1, tipoMovimiento);
                    pstmt.setString(2, estatusBase);
                    pstmt.setInt(3, year);
                    pstmt.setString(4, tipoMovimiento);
                    pstmt.setString(5, estatusBase);
                    pstmt.setInt(6, year);
                    pstmt.setString(7, tipoMovimiento);
                    pstmt.setString(8, estatusBase);
                    pstmt.setInt(9, year);
                    pstmt.setString(10, tipoMovimiento);
                    pstmt.setString(11, estatusBase);
                    pstmt.setInt(12, year);
                    pstmt.setString(13, tipoMovimiento);
                    pstmt.setString(14, estatusBase);
                    pstmt.setInt(15, year);
                    pstmt.setString(16, tipoMovimiento);
                    pstmt.setString(17, estatusBase);
                    pstmt.setInt(18, year);
                    pstmt.setString(19, tipoMovimiento);
                    pstmt.setString(20, estatusBase);
                    pstmt.setInt(21, year);
                    pstmt.setInt(22, year);
                    pstmt.setString(23, ramoAfectado);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            /*if (rsResult.getString("TIPO") != null) {
                             movimiento.setTipoOficio(rsResult.getString("TIPO"));
                             } else {*/
                            movimiento.setTipoOficio("");
                            /*}*/
                            if (rsResult.getString("STATUSDESCR") != null) {
                                movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            } else {
                                movimiento.setStatusDescr("");
                            }
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public List<Movimiento> getResultMovimientoListByRamoAfectadoTransAmpTipof(String tipoMovimiento,
            String estatusBase, int year, String ramoAfectado) throws SQLException, IOException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        Movimiento movimiento;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMovimientosListByRamoAfectadoTransAmpTipOf());
                if (pstmt != null) {
                    pstmt.setString(1, tipoMovimiento);
                    pstmt.setString(2, estatusBase);
                    pstmt.setInt(3, year);
                    pstmt.setString(4, tipoMovimiento);
                    pstmt.setString(5, estatusBase);
                    pstmt.setInt(6, year);
                    pstmt.setString(7, tipoMovimiento);
                    pstmt.setString(8, estatusBase);
                    pstmt.setInt(9, year);
                    pstmt.setString(10, tipoMovimiento);
                    pstmt.setString(11, estatusBase);
                    pstmt.setInt(12, year);
                    pstmt.setString(13, tipoMovimiento);
                    pstmt.setString(14, estatusBase);
                    pstmt.setInt(15, year);
                    pstmt.setString(16, tipoMovimiento);
                    pstmt.setString(17, estatusBase);
                    pstmt.setInt(18, year);
                    pstmt.setString(19, tipoMovimiento);
                    pstmt.setString(20, estatusBase);
                    pstmt.setInt(21, year);
                    pstmt.setInt(22, year);
                    pstmt.setString(23, ramoAfectado);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimiento = new Movimiento();
                            movimiento.setAppLogin(rsResult.getString("APP_LOGIN"));
                            movimiento.setRamo(rsResult.getString("RAMO_DESCR"));
                            movimiento.setFecPPTO(rsResult.getDate("FECPPTO"));
                            movimiento.setFechaElab(rsResult.getDate("FECHAELAB"));
                            if (rsResult.getString("JUSTIFICACION") == null) {
                                movimiento.setJutificacion(new String());
                            } else {
                                movimiento.setJutificacion(rsResult.getString("JUSTIFICACION"));
                            }
                            if (rsResult.getString("OBS_RECHAZO") != null) {
                                movimiento.setObsRechazo(rsResult.getString("OBS_RECHAZO"));
                            } else {
                                movimiento.setObsRechazo("");
                            }
                            movimiento.setOficio(rsResult.getInt("OFICIO"));
                            movimiento.setStatus(rsResult.getString("STATUS"));
                            movimiento.setTipoMovimiento(rsResult.getString("TIPOMOV"));
                            if (rsResult.getString("TIPO") != null) {
                                movimiento.setTipoOficio(rsResult.getString("TIPO"));
                            } else {
                                movimiento.setTipoOficio("");
                            }
                            if (rsResult.getString("STATUSDESCR") != null) {
                                movimiento.setStatusDescr(rsResult.getString("STATUSDESCR"));
                            } else {
                                movimiento.setStatusDescr("");
                            }
                            movimientoList.add(movimiento);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoList;
    }

    public List<BitMovto> getResultGetComentariosAutorizacion(int oficio) throws SQLException, IOException {
        List<BitMovto> bitacoraList = new ArrayList<BitMovto>();
        BitMovto bitMovto;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetComentariosAutorizacion());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            bitMovto = new BitMovto();
                            bitMovto.setAppLogin(rsResult.getString("APP_LOGIN"));
                            bitMovto.setComentarioAut(rsResult.getString("COMENTARIO"));
                            bitMovto.setFecha(rsResult.getString("FECHA"));
                            bitMovto.setNumComentario(rsResult.getInt("NUM_COMENTARIO"));
                            bitacoraList.add(bitMovto);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return bitacoraList;
    }

    public boolean getInsertComentarioAut(int oficio, String appLogin, String comentario) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean resultado = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.gerSQLInsertComentarioAut());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, appLogin);
                    pstmt.setString(3, comentario);
                    pstmt.setInt(4, oficio);
                    if (pstmt.executeUpdate() > 0) {
                        resultado = true;
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return resultado;
    }

    public boolean getUpdateComentarioAut(int oficio, String appLogin, String comentario, int numComentario) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean resultado = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.gerSQLUpdateComentarioAut());
                if (pstmt != null) {
                    pstmt.setString(1, comentario);
                    pstmt.setInt(2, oficio);
                    pstmt.setString(3, appLogin);
                    pstmt.setInt(4, numComentario);
                    if (pstmt.executeUpdate() > 0) {
                        resultado = true;
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return resultado;
    }

    public boolean getDeleteComentarioAut(int oficio, int numComentario) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean resultado = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.gerSQLDeleteComentario());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setInt(2, numComentario);
                    if (pstmt.executeUpdate() > 0) {
                        resultado = true;
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return resultado;
    }

    public String getComentarioEdicion(int oficio, int numComentario) throws Exception {
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        String comentario = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.gerSQLEdicionComentario());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setInt(2, numComentario);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            comentario = rsResult.getString("COMENTARIO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return comentario;
    }

    public String getUltimoComentario(int oficio) throws Exception {
        QuerysBD query = new QuerysBD();
        ResultSet rsResult;
        String comentario = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getUltimoComentario());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setInt(2, oficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            comentario = rsResult.getString("COMENTARIO");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return comentario;
    }

    public boolean isUsuarioEvaludaor(String appLogin) throws Exception {
        QuerysBD query = new QuerysBD();
        ResultSet rsResult;
        boolean isEvaluadir = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLsiUsuarioEvaluador());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            isEvaluadir = Boolean.parseBoolean(rsResult.getString("EVALUADOR"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return isEvaluadir;
    }

    public boolean isMovimientoEvaludo(int oficio) throws Exception {
        QuerysBD query = new QuerysBD();
        ResultSet rsResult;
        boolean isEvaluadir = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisMovimientoEvaluado());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            isEvaluadir = Boolean.parseBoolean(rsResult.getString("EVALUADO"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return isEvaluadir;
    }

    public boolean isCaratulaEvaluada(int oficio) throws Exception {
        QuerysBD query = new QuerysBD();
        ResultSet rsResult;
        boolean isEvaluadir = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisCaratulaEvaluada());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            isEvaluadir = Boolean.parseBoolean(rsResult.getString("EVALUADO"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return isEvaluadir;
    }

    public boolean getUpdateEvaluacionMovto(int oficio) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean resultado = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLevaluarMovimiento());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    if (pstmt.executeUpdate() > 0) {
                        resultado = true;
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return resultado;
    }

    public boolean getUpdateEvaluacionCaratula(int caratulaId, int year) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean resultado = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLevaluarCaratula());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setInt(2, caratulaId);
                    if (pstmt.executeUpdate() > 0) {
                        resultado = true;
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return resultado;
    }

    public boolean getInsertBitacoraEvaluacion(String appLogin, int identificador, int year) throws Exception {
        QuerysBD query = new QuerysBD();
        boolean resultado = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLinsertBitacoraEvaluacion());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, identificador);
                    pstmt.setInt(3, year);
                    if (pstmt.executeUpdate() > 0) {
                        resultado = true;
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return resultado;
    }

    public ArrayList<TipoModificacion> getTiposModificaciones() {
        ResultSet rsResult = null;
        PreparedStatement pstmt = null;
        QuerysBD qrQuery = new QuerysBD();
        TipoModificacion objTipoModificacion = new TipoModificacion();
        ArrayList<TipoModificacion> arrTiposModificaciones = null;

        if (conectaBD != null) {

            try {
                pstmt = conectaBD.getConConexion().prepareStatement(qrQuery.getTiposModificaciones());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();

                    if (rsResult != null) {
                        arrTiposModificaciones = new ArrayList();
                        while (rsResult.next()) {
                            objTipoModificacion = new TipoModificacion();
                            objTipoModificacion.setIntTipoModificacion(rsResult.getInt(1));
                            objTipoModificacion.setStrDescr(rsResult.getString(2));
                            arrTiposModificaciones.add(objTipoModificacion);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                try {
                    pstmt.close();
                    rsResult.close();
                } catch (Exception ex) {
                }
            }

        }
        return arrTiposModificaciones;
    }

    public List<FolioMonitoreoPresup> getResultSQLconsultaFolioMonitoreoPresup(int op, int year, int mesI, int mesF, String ramos) throws SQLException, NullPointerException {
        List<FolioMonitoreoPresup> folioList = new ArrayList<FolioMonitoreoPresup>();
        FolioMonitoreoPresup folio;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getConsultaFoliosReportesMonitoreoPresup(op, year, mesI, mesF, ramos));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            folio = new FolioMonitoreoPresup();
                            folio.setFolio(rsResult.getInt("OFICIO"));
                            folioList.add(folio);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return folioList;
    }

    public List<Movimiento> getResultGetFoliosByYearRamoCaratulaTipoMovStatusMov(int year, String ramoId, long caratula, String tipoMov, String statusMov, String appLogin, boolean isNormativo) throws SQLException, IOException {
        List<Movimiento> foliosList = new ArrayList<Movimiento>();
        Movimiento folio;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFoliosByYearRamoCaratulaTipoMovStatusMov(year, ramoId, caratula, tipoMov, statusMov, appLogin, isNormativo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            folio = new Movimiento();
                            folio.setOficio(rsResult.getInt("OFICIO"));
                            foliosList.add(folio);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return foliosList;
    }

    public List<Justificacion> getResultGetJustificacionesByFolio(int folio) throws SQLException, IOException {
        List<Justificacion> justificacionList = new ArrayList<Justificacion>();
        Justificacion justificacion;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetJustificacionesByFolio(folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            justificacion = new Justificacion();
                            justificacion.setId_justificacion(rsResult.getInt("ID_JUSTIFICACION"));
                            justificacion.setJustificacion(rsResult.getString("JUSTIFICACION"));
                            justificacionList.add(justificacion);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return justificacionList;
    }

    public List<MovimientoFolio> getResultGetMovimientosByFolioJustificacion(int folio, long justificacion) throws SQLException, IOException {
        List<MovimientoFolio> movimientoFolioList = new ArrayList<MovimientoFolio>();
        MovimientoFolio movimientoFolio;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMovimientosByFolioJustificacion(folio, justificacion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimientoFolio = new MovimientoFolio();
                            movimientoFolio.setMovimiento(rsResult.getInt("MOVIMIENTO"));
                            movimientoFolio.setTabla(rsResult.getInt("TABLA"));
                            movimientoFolio.setFolio(rsResult.getInt("OFICIO"));
                            if (rsResult.getString("ID_JUSTIFICACION") != null) {
                                movimientoFolio.setId_justificacion(Long.parseLong(rsResult.getString("ID_JUSTIFICACION")));
                            }
                            movimientoFolio.setRamo(rsResult.getString("RAMO"));
                            movimientoFolio.setPrg(rsResult.getString("PRG"));
                            movimientoFolio.setProyecto_abr(rsResult.getString("PROYECTO_ABR"));
                            movimientoFolio.setTipoProy(rsResult.getString("TIPO_PROY"));
                            movimientoFolio.setProyecto(rsResult.getInt("PROY"));
                            movimientoFolio.setMeta(rsResult.getInt("META"));
                            movimientoFolio.setAccion(rsResult.getInt("ACCION"));
                            movimientoFolio.setDepto(rsResult.getString("DEPTO"));
                            movimientoFolio.setPartida(rsResult.getString("PARTIDA"));
                            movimientoFolio.setFfr_abr(rsResult.getString("FFR_ABR"));
                            movimientoFolio.setFuente(rsResult.getString("FUENTE"));
                            movimientoFolio.setFondo(rsResult.getString("FONDO"));
                            movimientoFolio.setRecurso(rsResult.getString("RECURSO"));
                            movimientoFolio.setImporte(rsResult.getBigDecimal("IMPORTE"));
                            movimientoFolio.setAsignado(rsResult.getBoolean("ASIGNADO"));
                            movimientoFolio.setDiferenciador(rsResult.getInt("DIFERENCIADOR"));
                            movimientoFolioList.add(movimientoFolio);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoFolioList;
    }

    public boolean getResultSQLActualizaAsignacionMovimientos(int tabla, String asignado, long justificacion, int folio, String ramo, String programa, int proyecto, String tipoProy, int meta, int accion, String depto, String partida, String fuente, String fondo, String recurso, int diferenciador) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLActualizaAsignacionMovimientos(tabla, asignado, justificacion, folio, ramo, programa, proyecto, tipoProy, meta, accion, depto, partida, fuente, fondo, recurso, diferenciador));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    //bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public Justificacion getResultGetJustificacionByFolioJustificacion(int folio, long idJustificacion) throws SQLException, IOException {
        Justificacion justificacion = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetJustificacionByFolioJustificacion(folio, idJustificacion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            justificacion = new Justificacion();
                            justificacion.setId_justificacion(rsResult.getInt("ID_JUSTIFICACION"));
                            justificacion.setJustificacion(rsResult.getString("JUSTIFICACION"));
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return justificacion;
    }

    public boolean getResultSQLSaveJustificacion(int folio, long idJustificacion, String justificacion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLSaveJustificacion(folio, idJustificacion, justificacion));
                conectaBD.transaccionCommit();
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    //bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public List<MovimientoFolio> getResultGetMovimientosByFolio(int folio) throws SQLException, IOException {
        List<MovimientoFolio> movimientoFolioList = new ArrayList<MovimientoFolio>();
        MovimientoFolio movimientoFolio;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMovimientosByFolio(folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            movimientoFolio = new MovimientoFolio();
                            movimientoFolio.setMovimiento(rsResult.getInt("MOVIMIENTO"));
                            if (rsResult.getString("ID_JUSTIFICACION") != null) {
                                movimientoFolio.setId_justificacion(Long.parseLong(rsResult.getString("ID_JUSTIFICACION")));
                            }
                            movimientoFolioList.add(movimientoFolio);
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return movimientoFolioList;
    }

    public boolean getResultSQLIsValidaJustificacionesMovsActivo() throws Exception {
        String validaJustificaciones = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsValidaJustificacionesMovsActivo());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        validaJustificaciones = rsResult.getString("VALIDA_JUSTIFICACIONES");
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        if (validaJustificaciones.equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getResultSQLIsLigadaIngreso(long caratula) throws Exception {
        boolean ligada = false;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlIsLigadaIngreso(caratula));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ligada = rsResult.getBoolean("LIGADA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                try {
                    pstmt.close();
                    rsResult.close();
                } catch (Exception ex) {
                }
            }
        } else {
            throw new Exception("No se puede realizar ninguna operación con la base de datos : Conexión perdida");
        }

        return ligada;
    }

    public boolean getResultSQLIsLigadaPresupuesto(long caratula) throws Exception {
        boolean ligada = false;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlIsLigadaPresupuesto(caratula));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ligada = rsResult.getBoolean("LIGADA");
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                try {
                    pstmt.close();
                    rsResult.close();
                } catch (Exception ex) {
                }
            }
        } else {
            throw new Exception("No se puede realizar ninguna operación con la base de datos : Conexión perdida");
        }

        return ligada;
    }

    public boolean getResultSQLEliminarCaratula(long caratula) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLEliminarCaratula(caratula));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLEliminarEstatusIngresoCaratula(long caratula) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLEliminarEstatusIngresoCaratula(caratula));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getInsertaAvanceAccionesAvancePoa(int meta, String ramo, int year, int accion, int mes) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertaAvanceAccionesAvancePoa(meta, ramo, year, accion, mes));
            } catch (Exception sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    bitacora.setStrUbicacion(getStrUbicacion());
                    bitacora.setStrServer(getStrServer());
                    bitacora.setITipoBitacora(1);
                    bitacora.setStrInformacion(e, new Throwable());
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }
    
    public boolean getResultSQLExistenMetasAccionesRequerimientosNuevaCreacionErroneas(int folio, String tipoOficio) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        boolean existe = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExistenMetasAccionesRequerimientosNuevaCreacionErroneas(folio, tipoOficio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            if (rsResult.getInt(1) == 1) {
                                existe = true;
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return existe;
    }
    
    public boolean getResultSQLExistenAmpliacionesTransferenciasAccionMetaErroneas(int folio, String tipoMov) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        boolean existe = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExistenAmpliacionesTransferenciasAccionMetaErroneas(folio, tipoMov));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            if (rsResult.getInt(1) == 1) {
                                existe = true;
                            }
                        }
                    }
                }
            } catch (SQLException sqle) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sqle, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                pstmt.close();
                rsResult.close();
            }
        }
        return existe;
    }
    
}
