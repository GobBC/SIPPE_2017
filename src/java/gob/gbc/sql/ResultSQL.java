/*
 * ResultSQL.java
 *
 * Created on 18 de mayo de 2009
 *
 */
package gob.gbc.sql;

/**
 *
 * @author ugarcia
 */
import gob.gbc.aplicacion.Mes;
import gob.gbc.entidades.Estimacion;
import gob.gbc.bd.ConectaBD;
import gob.gbc.entidades.Accion;
import gob.gbc.entidades.AccionFuenteFinanciamiento;
import gob.gbc.entidades.AccionReq;
import gob.gbc.entidades.Articulo;
import gob.gbc.entidades.CentroCosto;
import gob.gbc.entidades.ClasificacionFuncional;
import gob.gbc.entidades.CodProg;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.ConceptoIngreso;
import gob.gbc.entidades.Dependencia;
import gob.gbc.entidades.Evaluacion;
import gob.gbc.entidades.Fin;
import gob.gbc.entidades.Finalidad;
import gob.gbc.entidades.Fondo;
import gob.gbc.entidades.Fuente;
import gob.gbc.entidades.FuenteFinanciamiento;
import gob.gbc.entidades.FuenteRecurso;
import gob.gbc.entidades.Funcion;
import gob.gbc.entidades.GrupoPoblacional;
import gob.gbc.entidades.Grupos;
import gob.gbc.entidades.Linea;
import gob.gbc.entidades.LineaSectorial;
import gob.gbc.entidades.Localidad;
import gob.gbc.entidades.LongitudCodigo;
import gob.gbc.entidades.Meta;
import gob.gbc.entidades.Municipio;
import gob.gbc.entidades.OpcionMenu;
import gob.gbc.entidades.Partida;
import gob.gbc.entidades.Persona;
import gob.gbc.entidades.Ponderado;
import gob.gbc.entidades.Programa;
import gob.gbc.entidades.Proyecto;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.Requerimiento;
import gob.gbc.entidades.TipoCompromiso;
import gob.gbc.entidades.TipoGasto;
import gob.gbc.entidades.UnidadMedida;
import gob.gbc.entidades.ProgramaConac;
import gob.gbc.entidades.ParametroPrg;
import gob.gbc.entidades.RelacionLaboral;
import gob.gbc.entidades.TipoAccion;
import gob.gbc.entidades.TipoCalculo;
import gob.gbc.entidades.Transversalidad;
import gob.gbc.entidades.Plantilla;
import gob.gbc.entidades.PlantillaPersonal;
import gob.gbc.entidades.Ppto;
import gob.gbc.entidades.PresupuestoIngreso;
import gob.gbc.entidades.Proyeccion;
import gob.gbc.entidades.ProyectoFuncional;
import gob.gbc.entidades.RamoPrograma;
import gob.gbc.entidades.Recurso;
import gob.gbc.entidades.SubGrupos;
import gob.gbc.entidades.SubSubGpo;
import gob.gbc.entidades.Subfuncion;
import gob.gbc.util.Bitacora;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import gob.gbc.entidades.AvancePoaMeta;
import gob.gbc.entidades.AvancePoaMetaObservaciones;
import gob.gbc.entidades.AccionesAvancePoa;

import gob.gbc.entidades.AvancePoaAcciones;
import gob.gbc.entidades.Caratula;
import gob.gbc.entidades.CapturaPresupuestoIngreso;
import gob.gbc.entidades.RevisionCaratula;
import gob.gbc.util.Utilerias;
import gob.gbc.util.Archivo;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;
import oracle.jdbc.OracleTypes;
import org.apache.commons.lang.StringUtils;
import gob.gbc.entidades.EstatusMovReporte;
import gob.gbc.entidades.IndicadorGeneralSei;
import gob.gbc.entidades.IndicadorSectorRamo;
import gob.gbc.entidades.SectorRamo;
import gob.gbc.entidades.TipoSesion;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ResultSQL extends ResultSQLMedio {

    private String strServer = "";
    private String strUbicacion = "";
    public Bitacora bitacora;
    //private ConectaBD conectaBD;
    public String errorBD = new String();

    /**
     * Creates a new instance of ResultSQL
     *
     * @throws java.io.IOException
     */
    public ResultSQL() throws Exception {

        conectaBD = new ConectaBD();
        bitacora = new Bitacora();
    }

    public ResultSQL(String tipoDependencia) throws Exception {

        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public Vector getResultSQLConsultaEstatusUsuario(String strUsuario, String strContrasena) {
        Vector vecDatos = new Vector();
        Vector vecResultado = new Vector();
        Vector vecInterno = new Vector();
        QuerysBD query = new QuerysBD();
        int iValorRegresa = 0;
        String res = new String();
        String appPwd = new String();
        String salt = new String();
        String pwdGenerado = new String();
        String hashAppPwd = new String();
        boolean bEncriptarContrasena = false;

        try {
            vecDatos = conectaBD.ejecutaSQLConsulta(query.getSQLConsultaEstatusUsuario(strUsuario));
            if (vecDatos.size() == 0) {
                iValorRegresa = 2;
            } else {
                vecInterno = (Vector) vecDatos.get(0);
                String strEstatus = (String) vecInterno.get(0);
                appPwd = (String) vecInterno.get(1);
                salt = (String) vecInterno.get(2);
                pwdGenerado = (String) vecInterno.get(3);
                if (!strEstatus.equals("1")) {
                    iValorRegresa = 3;
                } else {
                    vecDatos = conectaBD.ejecutaSQLConsulta(query.getSQLConsultaComparativoHash(strContrasena, salt));
                    if (vecDatos.size() == 0) {
                        iValorRegresa = 5;
                    } else {
                        bEncriptarContrasena = getResultEncriptarContrasenia();
                        if (bEncriptarContrasena) {
                            vecInterno = (Vector) vecDatos.get(0);
                            hashAppPwd = (String) vecInterno.get(0);
                            appPwd = hashAppPwd;
                        } else {
                            appPwd = strContrasena;
                        }
                        vecDatos = conectaBD.ejecutaSQLConsulta(query.getSQLConsultaDatosUsuario(strUsuario, appPwd));
                        if (vecDatos.size() == 0) {
                            iValorRegresa = 4;
                        } else {
                            iValorRegresa = 1;
                            vecInterno = (Vector) vecDatos.get(0);
                            vecResultado.add(vecInterno.get(0));
                            vecResultado.add(vecInterno.get(1));
                            vecResultado.add(vecInterno.get(2));
                            vecResultado.add(vecInterno.get(3));
                            vecResultado.add(vecInterno.get(4));
                            vecResultado.add(vecInterno.get(5));
                            vecResultado.add(vecInterno.get(6));

                            if (bEncriptarContrasena && pwdGenerado.equals("S")) {
                                vecDatos = conectaBD.ejecutaSQLConsulta(query.getSQLConsultaDiasRestantes(strUsuario));
                                if (vecDatos.size() == 0) {
                                    iValorRegresa = 6;
                                } else {
                                    vecInterno = (Vector) vecDatos.get(0);

                                    iValorRegresa = 100 + Integer.parseInt((String) vecInterno.get(0));
                                    conectaBD.ejecutaSQLActualizacion(query.getSQLInsertaBitAcceso(strUsuario));
                                }
                            }
                        }
                    }
                }
            }

            // conectaBD.transaccionCommit();revisado
        } catch (Exception sqle) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sqle, new Throwable());
            bitacora.grabaBitacora();
        }
        vecResultado.add(0, Integer.toString(iValorRegresa));
        return vecResultado;
    }

    public List<Proyecto> getResultSQLProyectosByPrograma(String ramoId, String programaId, int year) throws SQLException {
        List<Proyecto> proyectoList = new ArrayList<Proyecto>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Proyecto proyecto = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProyectosByPrograma(ramoId, programaId, year));
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

    public Proyecto getResultSQLProyectoById(String ramoId, String programaId, int proyectoId, int year, String tipoProy) throws SQLException {
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        Proyecto proyecto = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProyectoById(ramoId, programaId, proyectoId, year, tipoProy));
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
                pstmt.close();
                rsResult.close();
            }
        }
        return proyecto;
    }

    public List<Programa> getResultSQLProgramasByRamo(String ramoId, int year, String usuario) throws Exception {
        List<Programa> programaList = new ArrayList<Programa>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Programa programa = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProgramasByRamo(ramoId, year, usuario));
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

    public Vector getResultSQLConsultaDatosUsuario(String strUsuario) {
        Vector vecResultado = new Vector();
        QuerysBD query = new QuerysBD();

        try {
            vecResultado = conectaBD.ejecutaSQLConsulta(query.getSQLConsultaDatosUsuario(strUsuario));
//conectaBD.transaccionCommit();//revisado        
        } catch (Exception sqle) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sqle, new Throwable());
            bitacora.grabaBitacora();
        }
        return vecResultado;
    }

    public List<Meta> getResultGetMetas(String ramoId, String programaId, int proyectoId, int year, String tipoProy) throws SQLException {
        List<Meta> metaList = new ArrayList<Meta>();
        Meta meta;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMetas(ramoId, programaId, proyectoId, year, tipoProy));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            meta = new Meta();
                            meta.setMetaId(rsResult.getInt("META"));
                            meta.setMeta(rsResult.getString("DESCR"));
                            meta.setRamo(rsResult.getString("RAMO"));
                            meta.setPrograma(rsResult.getString("PRG"));
                            meta.setProyecto(rsResult.getInt("PROY"));
                            meta.setLineaPED(rsResult.getString("LINEA"));
                            meta.setCalculo(rsResult.getString("CALCULO"));
                            meta.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            meta.setAutorizacion(rsResult.getString("PROCESO_AUTORIZACION"));
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

    public LongitudCodigo getRestultGetLongitudCodigo(int year) throws SQLException {
        LongitudCodigo longitud = new LongitudCodigo();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLongitudCodigo(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            longitud = new LongitudCodigo();
                            longitud.setRamo(rsResult.getInt("RAMO"));
                            longitud.setPrg(rsResult.getInt("PRG"));
                            longitud.setUejecutora(rsResult.getInt("UEJECUTORA"));
                            longitud.setPartida(rsResult.getInt("PARTIDA"));
                            longitud.setFinalidad(rsResult.getInt("FINALIDAD"));
                            longitud.setFuncion(rsResult.getInt("FUNCION"));
                            longitud.setSubfuncion(rsResult.getInt("SUNBFUNCION"));
                            longitud.setPrgConac(rsResult.getInt("PRG_CONAC"));
                            longitud.setTipoProy(rsResult.getInt("TIPO_PROY"));
                            longitud.setProyecto(rsResult.getInt("PROYECTO"));
                            longitud.setMeta(rsResult.getInt(rsResult.getInt("META")));
                            longitud.setAccion(rsResult.getInt("ACCION"));
                            longitud.setTipoGasto(rsResult.getInt("TIPO_GASTO"));
                            longitud.setFuente(rsResult.getInt("FUENTE"));
                            longitud.setFondo(rsResult.getInt("FONDO"));
                            longitud.setRecurso(rsResult.getInt("RECURSO"));
                            longitud.setMunicipio(rsResult.getInt("MUNICIPIO"));
                            longitud.setDelegacion(rsResult.getInt("DELEGACION"));
                            longitud.setRelLaboral(rsResult.getInt("REL_LABORAL"));
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
        return longitud;
    }

    public List<Ramo> getResultRamoByYear(int year, String usuario) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<Ramo> ramoList = new ArrayList<Ramo>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Ramo ramo;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamoByUsuario(year, usuario));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        ramo = new Ramo();
                        ramo.setRamo(rsResultado.getString("RAMO"));
                        ramo.setRamoDescr(rsResultado.getString("DESCR"));
                        ramo.setUsaurio(rsResultado.getString("APP_LOGIN"));
                        //ramo.setYear(rsResultado.getString("YEAR"));
                        //ramo.setRfc(rsResultado.getString("RFC"));
                        //ramo.setHomoclave(rsResultado.getString("HOMOCLAVE"));
                        ramoList.add(ramo);
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
        return ramoList;
    }

    public List<Persona> getResultadoEmpleadoByNombre(String nombre, String apPaterno, String apMaterno) throws SQLException {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<Persona> personaList = new ArrayList<Persona>();
        Persona persona;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().
                        prepareStatement(query.getSQLBuscarPersona(nombre, apPaterno, apMaterno));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            persona = new Persona();
                            persona.setNombre(rsResultado.getString("NOMBRE"));
                            persona.setApPaterno(rsResultado.getString("PATERNO"));
                            persona.setApMaterno(rsResultado.getString("MATERNO"));
                            persona.setRfc(rsResultado.getString("RFC"));
                            persona.setHomoclave(rsResultado.getString("HOMOCLAVE"));
                            persona.setNombreComleto(rsResultado.getString("C_NOMBRE"));
                            personaList.add(persona);
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
        }
        return personaList;
    }

    public Ramo getRamoByIdAndYear(int ramoId, int year) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        Ramo ramo = new Ramo();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLRamoByIdAndYear(ramoId, year));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            ramo.setRamo(rsResultado.getString("RAMO"));
                            ramo.setRamoDescr(rsResultado.getString("RAMO_DESC"));
                            ramo.setYear(rsResultado.getString("YEAR"));
                            ramo.setRfc(rsResultado.getString("RFC"));
                            ramo.setHomoclave(rsResultado.getString("HOMOCLAVE"));
                            ramo.setNombreResponsable(rsResultado.getString("NOMBRE_C"));
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
        return ramo;
    }

    public Linea getResultGetLineaEstatal(int year, String lineaId) throws SQLException {
        Linea linea = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaEstatalByID(year, lineaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        linea = new Linea();
                        while (rsResult.next()) {
                            linea.setTipoLinea(rsResult.getString("TIPO"));
                            linea.setLineaId(rsResult.getString("LINEA"));
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
                rsResult.close();
            }
        }
        return linea;
    }

    public boolean getResultIsLineaGuardada(int ramoId, int programaId, int proyectoId, int year, String lineaId) throws SQLException {
        boolean lineaGuardada = false;
        int resultado = -1;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineasGuardadas(ramoId, programaId, proyectoId, lineaId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            resultado = rsResult.getInt("LINEA");
                        }
                    }
                }
                if (resultado > 0) {
                    lineaGuardada = true;
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
        return lineaGuardada;
    }

    public List<Linea> getResultGetLineaRamoPrograma(int year, String Ramo, String programa) throws SQLException {
        List<Linea> lineaList = new ArrayList<Linea>();
        Linea linea;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineasRamoPrg(year, Ramo, programa));
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

    public List<Linea> getResultGetLineaSectorialByLineaAccion(String estrategia, int year) throws SQLException {
        List<Linea> lineaList = new ArrayList<Linea>();
        Linea linea;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaSectorialByLineaAccion(estrategia, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            linea = new Linea();
                            linea.setLineaId(rsResult.getString("LINEA_SECTORIAL"));
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

    public List<Linea> getResultGetAllLineaRamoPrograma(int year, String Ramo) throws SQLException {
        List<Linea> lineaList = new ArrayList<Linea>();
        Linea linea;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAllLineasRamoPrg(year, Ramo));
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

    public List<LineaSectorial> getRestulLineaSectorialByProyecto(String ramoId, String programaId, int proyectoId, int year) throws SQLException {
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        LineaSectorial lineaSectorial;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaSectorialProyecto(ramoId, programaId, proyectoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            lineaSectorial = new LineaSectorial();
                            lineaSectorial.setLineaId(rsResult.getString("LINEA_SECTORIAL"));
                            lineaSectorial.setLineaSectorial(rsResult.getString("DESCR"));
                            lineaSectorial.setEstrategia(rsResult.getString("ESTRATEGIA"));
                            lineaSectorialList.add(lineaSectorial);
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
        return lineaSectorialList;
    }

    public List<LineaSectorial> getRestulLineaSectorialByRamo(int year, String ramoId) throws SQLException {
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        LineaSectorial lineaSectorial;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaSectorialRamo(year, ramoId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            lineaSectorial = new LineaSectorial();
                            lineaSectorial.setLineaId(rsResult.getString("LINEA_SECTORIAL"));
                            lineaSectorial.setLineaSectorial(rsResult.getString("DESCR"));
                            lineaSectorialList.add(lineaSectorial);
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
        return lineaSectorialList;
    }

    public boolean getResultSQLDeleteRequerimiento(int year, String ramo, String programa, int meta, int accion, int requerimiento) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteRequerimiento(year, ramo, programa, meta, accion, requerimiento));
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

    public boolean getResultSQLDeleteEstimacionMeta(int year, String ramo, int meta) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteEstimacionMeta(year, ramo, meta));
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

    public boolean getResultSQLDeleteEstimacionAccion(int year, String ramo, int meta, int accion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteEstimacionAccion(year, ramo, meta, accion));
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

    public String getRestulSQLCallActualizarCodPPTO(int year, String ramo, String depto, String finalidad, String funcion, String subFuncion, String progConac, String programa, String tipoProyecto, int proyecto, int meta, int accion, String partida, String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral, double cantXcostoUn,
            int mes, int existeCod, String usuario, int requerimiento) throws SQLException {
        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            CallableStatement clstm = null;
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLGetProcedureActualizaCodPPTO(year, ramo,
                        depto, finalidad, funcion, subFuncion, progConac, programa, tipoProyecto, proyecto, meta, accion,
                        partida, tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLaboral, cantXcostoUn, mes,
                        existeCod, usuario, requerimiento));
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

    public String getRestulSQLCallUpdatePresupuesto(int year, String ramo, String depto,
            String finalidad, String funcion, String subFuncion, String progConac,
            String programa, String tipoProyecto, int proyecto, int meta, int accion,
            String partida, String tipoGasto, String fuente, String fondo, String recurso,
            String municipio, int delegacion, String relLaboral, int idRequ, double ene,
            double feb, double mar, double abr, double may, double jun, double jul, double ago,
            double sep, double oct, double nov, double dic, double cantidad, double costoUnitario,
            String nuevo, String borrado, String reqDesc, String articulo, String justificacion,
            String usuario, String gpoto, String subgpo) throws SQLException {
        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            CallableStatement clstm = null;
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLUpdatePresupuesto());
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
                    clstm.setInt(21, idRequ);
                    clstm.setDouble(22, ene);
                    clstm.setDouble(23, feb);
                    clstm.setDouble(24, mar);
                    clstm.setDouble(25, abr);
                    clstm.setDouble(26, may);
                    clstm.setDouble(27, jun);
                    clstm.setDouble(28, jul);
                    clstm.setDouble(29, ago);
                    clstm.setDouble(30, sep);
                    clstm.setDouble(31, oct);
                    clstm.setDouble(32, nov);
                    clstm.setDouble(33, dic);
                    clstm.setDouble(34, cantidad);
                    clstm.setDouble(35, costoUnitario);
                    clstm.setString(36, nuevo);
                    clstm.setString(37, borrado);
                    clstm.setString(38, reqDesc);
                    clstm.setString(39, articulo);
                    clstm.setString(40, justificacion);
                    clstm.setString(41, usuario);
                    clstm.setString(42, gpoto);
                    clstm.setString(43, subgpo);
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

    public String getRestulSQLCallUpdatePresupuesto(int year, String ramo,
            String programa, String tipoProyecto, int proyecto, int meta, int accion,
            String partida, String fuente, String fondo, String recurso, String tipoGasto, String relLaboral,
            int idRequ, double ene, double feb, double mar, double abr, double may, double jun,
            double jul, double ago, double sep, double oct, double nov, double dic, double cantidad,
            double costoUnitario, String nuevo, String borrado, String reqDesc, String articulo,
            String justificacion, String usuario, String gpoto, String subgpo) throws SQLException {
        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            CallableStatement clstm = null;
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLUpdatePresupuestoAv());
                if (clstm != null) {
                    clstm.setString(1, String.valueOf(year));
                    clstm.setString(2, ramo);
                    clstm.setString(3, programa);
                    clstm.setString(4, tipoProyecto);
                    clstm.setInt(5, proyecto);
                    clstm.setInt(6, meta);
                    clstm.setInt(7, accion);
                    clstm.setString(8, partida);
                    clstm.setString(9, fuente);
                    clstm.setString(10, fondo);
                    clstm.setString(11, recurso);
                    clstm.setString(12, tipoGasto);
                    clstm.setString(13, relLaboral);
                    clstm.setInt(14, idRequ);
                    clstm.setDouble(15, ene);
                    clstm.setDouble(16, feb);
                    clstm.setDouble(17, mar);
                    clstm.setDouble(18, abr);
                    clstm.setDouble(19, may);
                    clstm.setDouble(20, jun);
                    clstm.setDouble(21, jul);
                    clstm.setDouble(22, ago);
                    clstm.setDouble(23, sep);
                    clstm.setDouble(24, oct);
                    clstm.setDouble(25, nov);
                    clstm.setDouble(26, dic);
                    clstm.setDouble(27, cantidad);
                    clstm.setDouble(28, costoUnitario);
                    clstm.setString(29, nuevo);
                    clstm.setString(30, borrado);
                    clstm.setString(31, reqDesc);
                    clstm.setString(32, articulo);
                    clstm.setString(33, justificacion);
                    clstm.setString(34, usuario);
                    clstm.setString(35, gpoto);
                    clstm.setString(36, subgpo);
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

    public String getRestulSQLCallActualizarCodPPTO(int year, String ramo, String depto, String finalidad, String funcion, String subFuncion, String progConac, String programa, String tipoProyecto, int proyecto, int meta, int accion, String partida, String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral, double cantXcostoUn,
            int mes, int existeCod, String usuario, int requerimiento, int validaBitacora) throws SQLException {
        boolean resultado = true;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            CallableStatement clstm = null;
            try {
                clstm = conectaBD.getConConexion().prepareCall(query.getSQLGetProcedureActualizaCodPPTO(year, ramo,
                        depto, finalidad, funcion, subFuncion, progConac, programa, tipoProyecto, proyecto, meta, accion,
                        partida, tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLaboral, cantXcostoUn, mes,
                        existeCod, usuario, requerimiento, validaBitacora));
                if (clstm != null) {
                    clstm.executeQuery();
                }
                if (mes == 12) {
                    // conectaBD.transaccionCommit(); revisado
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

    public List<LineaSectorial> getResultLineaSectorialByPED(String est, int year, String ramoId, String programa, int proyecto) throws SQLException {
        List<LineaSectorial> sectorialList = new ArrayList<LineaSectorial>();
        LineaSectorial linea;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaSectorialByPED(est, year, ramoId, programa, proyecto));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            linea = new LineaSectorial();
                            linea.setLineaId(rsResult.getString("LINEA_SECTORIAL"));
                            linea.setLineaSectorial(rsResult.getString("DESCR"));
                            sectorialList.add(linea);
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
        return sectorialList;
    }

    public List<Linea> getResultLineaByProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProy) throws SQLException {
        List<Linea> lineaList = new ArrayList<Linea>();
        Linea linea;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineasProyecto(ramoId, programaId, proyectoId, year, tipoProy));
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

    public boolean getResultIsRelacionLaboral(int year, String partida) throws SQLException {
        String relLaboral = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisRelacionLaboral(year, partida));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            relLaboral = new String();
                            relLaboral = rsResult.getString("RELACION_LABORAL");
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
        if (relLaboral != null) {
            if (relLaboral.equalsIgnoreCase("N")) {
                return false;
            } else if (relLaboral.equals("S")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int getResultSQLMaxRequerimiento(int year, String ramoId, int metaId, int accion) throws SQLException, NullPointerException {
        int accionId = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMaxRequerimiento(year, ramoId, metaId, accion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accionId = rsResult.getInt("MAX_REQ");
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
        return accionId;
    }

    public int getResultSQLGetCountAcciones(int year, String ramoId, String programaId, int metaId) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCountAccionesByMeta(year, ramoId, programaId, metaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("ACC");
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
        return count;
    }

    public int getResultSQLGetcalidaRelLaboral(String relLaboral) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLvalidaRelacionLabora(relLaboral));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("REL");
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
        return count;
    }

    public int getSQLGetTipoRequerimientoPlantilla(int year, String ramo, String prg, int meta,
            int accion, String partida, String fuente, String fondo, String recurso, int requerimiento) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoRequerimientoPlantilla(year, ramo,
                        prg, meta, accion, partida, fuente, fondo, recurso, requerimiento));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("ARCHIVO");
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
        return count;
    }

    public String getResultSQLGetValidaParametros(Sheet hojaXLS, int year) throws SQLException {
        int countRamo = 0;
        int countPartida = 0;
        int countRelLaboral = 0;

        String errorValidacion = new String();

        Row fila;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int rows;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                rows = hojaXLS.getPhysicalNumberOfRows();
                for (int ite = 0; ite < rows; ite++) {
                    fila = hojaXLS.getRow(ite);
                    if (fila != null && fila.getCell(0) != null) {
                        pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetCountRamoAnterior());
                        if (pstmt != null) {
                            pstmt.setInt(1, year);
                            pstmt.setString(2, Utilerias.getStringValueProyeccion(fila.getCell(0), false));
                            rsResult = pstmt.executeQuery();
                            if (rsResult != null) {
                                while (rsResult.next()) {
                                    countRamo = rsResult.getInt("RAMO_ANT");
                                }
                            }
                        }
                        pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetCountPartidaAnterior());
                        if (pstmt != null) {
                            pstmt.setInt(1, year);
                            pstmt.setString(2, Utilerias.getStringValueProyeccion(fila.getCell(2), false));
                            rsResult = pstmt.executeQuery();
                            if (rsResult != null) {
                                while (rsResult.next()) {
                                    countPartida = rsResult.getInt("PARTIDA_ANT");
                                }
                            }
                        }
                        pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetCountRelLaboralAnterior());
                        if (pstmt != null) {
                            pstmt.setInt(1, year);
                            pstmt.setString(2, Utilerias.getStringValueProyeccion(fila.getCell(3), false));
                            rsResult = pstmt.executeQuery();
                            if (rsResult != null) {
                                while (rsResult.next()) {
                                    countRelLaboral = rsResult.getInt("REL_LABORAL");
                                }
                            }
                        }
                    }
                    if (countRamo == 0) {
                        errorValidacion = "ramo," + Utilerias.getStringValueProyeccion(fila.getCell(0), false);
                    }
                    if (countPartida == 0) {
                        errorValidacion = "partida," + Utilerias.getStringValueProyeccion(fila.getCell(2), false);
                    }
                    if (countRelLaboral == 0) {
                        errorValidacion = "relLaboral," + Utilerias.getStringValueProyeccion(fila.getCell(3), false);
                    }
                    if (!errorValidacion.isEmpty()) {
                        break;
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
        return errorValidacion;
    }

    public int getResultSQLGetCountPartidaAnteriores(String partida, int year) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetCountPartidaAnterior());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, partida);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("PARTIDA_ANT");
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
        return count;
    }

    public int getResultSQLGetCountRelLaboralAnterior(String relLaboral, int year) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetCountRelLaboralAnterior());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, relLaboral);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("REL_LABORAL");
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
        return count;
    }

    public boolean getResultSQLGetCountPartidaPlantilla(String partida, int year) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetCountPartidaPlantilla(partida, year));
                pstmt.setInt(1, year);
                pstmt.setString(2, partida);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("PLANTILLA");
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

    public int getResultSQLGetCountPresupPlantilla(int year, String ramo, String origen) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCountPresupPlantilla(year, ramo, origen));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("PRESUP");
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
        return count;
    }

    public int getResultSQLGetCountPresupPlantilla(int year, String ramo) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCountPresupPlantilla(year, ramo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("PRESUP");
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
        return count;
    }

    public int getResultSQLGetCountProyeccion(int year, String ramo) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCountProyeccion(year, ramo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("PRESUP");
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
        return count;
    }

    public int getResultSQLGetCountRequerimientos(int year, String ramoId, String programaId, int metaId, int accion) throws SQLException {
        int count = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCountRequerimientosByAccion(year, ramoId, programaId, metaId, accion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            count = rsResult.getInt("REQ");
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
        return count;
    }

    public int getResultSQLMaxAccion(int year, String ramoId, int metaId) throws SQLException {
        int accionId = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMaxAccion(year, ramoId, metaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accionId = rsResult.getInt("MAX_ACC");
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
        return accionId;
    }

    public int getMaxMeta(int year, String ramoId) throws SQLException {
        int metaId = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMaxMeta(year, ramoId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            metaId = rsResult.getInt("METAID");
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
        return metaId;
    }

    public List<Partida> getResultSQLGetPartidas(int year, String tipoDependencia) throws SQLException {
        List<Partida> partidaList = new ArrayList<Partida>();
        Partida partida;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                if (tipoDependencia.equals("SPPD")) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPartidas(year));
                } else {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPartidasPara(year));
                }
                if (pstmt != null) {
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

    public List<Partida> getResultSQLGetPartidasParaestatal(int year) throws SQLException {
        List<Partida> partidaList = new ArrayList<Partida>();
        Partida partida;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPartidasParaestatal(year));
                if (pstmt != null) {
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

    public List<Partida> getResultSQLGetPartidasGeneral(int year) throws SQLException {
        List<Partida> partidaList = new ArrayList<Partida>();
        Partida partida;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetPartidasGeneral(year));
                if (pstmt != null) {
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

    public List<Linea> getResultLineaByPrograma(String ramoId, String programaId, int year) throws SQLException {
        List<Linea> lineaList = new ArrayList<Linea>();
        Linea linea;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineasPrograma(ramoId, programaId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            linea = new Linea();
                            linea.setEst(rsResult.getString("EST"));
                            linea.setObj(rsResult.getString("OBJ"));
                            linea.setLineaId(rsResult.getString("LINEA"));
                            linea.setLinea(rsResult.getString("DESCR"));
                            linea.setTipoLinea(rsResult.getString("TIPO"));
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

    public Dependencia getResultSQLGetDependenciaById(String ramoId, String programaId, int year, String depto) throws SQLException {
        Dependencia dependencia = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetDeptoById(ramoId, programaId, year, depto));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            dependencia = new Dependencia();
                            dependencia.setDeptoId(rsResult.getString("DEPTO"));
                            dependencia.setDepartamento(rsResult.getString("DESCR"));
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
        return dependencia;
    }

    public List<Dependencia> getResultDependenciasByRamoPrograma(String ramoId, String programaId, int year) throws SQLException {
        List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
        Dependencia dependencia = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetEmpleadoByRamoPrograma(ramoId, programaId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            dependencia = new Dependencia();
                            dependencia.setRamo(rsResult.getString("RAMO"));
                            dependencia.setDeptoId(rsResult.getString("DEPTO"));
                            dependencia.setDepartamento(rsResult.getString("DESCR"));
                            dependencia.setMpo(rsResult.getString("MPO"));
                            dependencia.setYear(rsResult.getInt("YEAR"));
                            dependencia.setRfc(rsResult.getString("RFC"));
                            dependencia.setHomoclave(rsResult.getString("HOMOCLAVE"));
                            dependenciaList.add(dependencia);
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
        return dependenciaList;
    }

    public List<Dependencia> getResultDependenciasByRamo(String ramoId, int year, String programaId) throws SQLException {
        List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
        Dependencia dependencia = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetDeptoByRamo(year, ramoId, programaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            dependencia = new Dependencia();
                            dependencia.setDeptoId(rsResult.getString("DEPTO"));
                            dependencia.setDepartamento(rsResult.getString("DESCR"));
                            dependenciaList.add(dependencia);
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
        return dependenciaList;
    }

    public List<UnidadMedida> getResultGetUnidadMedidaList(int year) throws SQLException {
        UnidadMedida unidadMedida;
        List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetUnidadMedida(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        unidadMedidaList = new ArrayList<UnidadMedida>();
                        while (rsResult.next()) {
                            unidadMedida = new UnidadMedida();
                            unidadMedida.setUnidadMedidaId(rsResult.getInt("MEDIDA"));
                            unidadMedida.setUnidadMedida(rsResult.getString("DESCR"));
                            unidadMedidaList.add(unidadMedida);
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
        return unidadMedidaList;
    }

    public List<UnidadMedida> getResultGetUnidadAccionMedidaList(int year) throws SQLException {
        UnidadMedida unidadMedida;
        List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetUnidadMedidaAccion(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        unidadMedidaList = new ArrayList<UnidadMedida>();
                        while (rsResult.next()) {
                            unidadMedida = new UnidadMedida();
                            unidadMedida.setUnidadMedidaId(rsResult.getInt("MEDIDA"));
                            unidadMedida.setUnidadMedida(rsResult.getString("DESCR"));
                            unidadMedidaList.add(unidadMedida);
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
        return unidadMedidaList;
    }

    public List<TipoCompromiso> getResultGetTipoCompromiso() throws SQLException {
        List<TipoCompromiso> tipoCompromisoList = new ArrayList<TipoCompromiso>();
        TipoCompromiso tipoCompromiso = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoCompromiso());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        tipoCompromisoList = new ArrayList<TipoCompromiso>();
                        while (rsResult.next()) {
                            tipoCompromiso = new TipoCompromiso();
                            tipoCompromiso.setTipoCompromisoId(rsResult.getInt("TIPO_COMPROMISO"));
                            tipoCompromiso.setTipoCompromisoDes(rsResult.getString("DESCR"));
                            tipoCompromisoList.add(tipoCompromiso);
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
        return tipoCompromisoList;
    }

    public List<Municipio> getResultGetMunicipio() throws SQLException {
        List<Municipio> municipioList = new ArrayList<Municipio>();
        Municipio municipio = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMunicipio());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        municipioList = new ArrayList<Municipio>();
                        while (rsResult.next()) {
                            municipio = new Municipio();
                            municipio.setMunicipioId(rsResult.getInt("MPO"));
                            municipio.setMunicipio(rsResult.getString("NOMBREMPO"));
                            municipioList.add(municipio);
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
        return municipioList;
    }

    public List<Localidad> getResultSQLGetLocalidad(int mpo) throws SQLException {
        List<Localidad> localidadList = new ArrayList<Localidad>();
        Localidad localidad = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLLocalidades(mpo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        localidadList = new ArrayList<Localidad>();
                        while (rsResult.next()) {
                            localidad = new Localidad();
                            localidad.setLocalidadId(rsResult.getInt("LOCALIDAD"));
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

    public List<Linea> getResultSQLGetLineaPEDByRamo(String ramoId, int year) throws SQLException {
        List<Linea> lineaList = new ArrayList<Linea>();
        Linea linea = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaPED(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        lineaList = new ArrayList<Linea>();
                        while (rsResult.next()) {
                            linea = new Linea();
                            linea.setLineaId(rsResult.getString("LINEA"));
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
                pstmt.close();
                rsResult.close();
            }
        }
        return lineaList;
    }

    public List<GrupoPoblacional> getResultGetGrupoPoblacional() throws SQLException {
        List<GrupoPoblacional> grupoPoblacionalList = new ArrayList<GrupoPoblacional>();
        GrupoPoblacional grupoPoblacional = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetGrupoPoblacional());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        grupoPoblacionalList = new ArrayList<GrupoPoblacional>();
                        while (rsResult.next()) {
                            grupoPoblacional = new GrupoPoblacional();
                            grupoPoblacional.setGrupoPobId(rsResult.getInt("GRUPO_POBLACION"));
                            grupoPoblacional.setGripoPoblacional(rsResult.getString("DESCR"));
                            grupoPoblacionalList.add(grupoPoblacional);
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
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return grupoPoblacionalList;
    }

    public Ramo getResultGetRamoById(String ramoId, int year, String usuario) throws SQLException {
        Ramo ramo = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamoById(ramoId, year, usuario));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ramo = new Ramo();
                            ramo.setRamo(rsResult.getString("RAMO"));
                            ramo.setRamoDescr(rsResult.getString("DESCR"));
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
        return ramo;
    }

    public Programa getResultGetProgramaById(String ramoId, int year, String programaId, String usuario) throws SQLException {
        Programa programa = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProgramaById(ramoId, programaId, year, usuario));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = new Programa();
                            programa.setProgramaId(rsResult.getString("PRG"));
                            programa.setProgramaDesc(rsResult.getString("DESCR"));
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
        return programa;
    }

    public Proyecto getResultGetProyectoById(String ramoId, String programaId, int proyectoId, int year, String tipoProy) throws SQLException {
        Proyecto proyecto = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProyectoById(ramoId, programaId, proyectoId, year, tipoProy));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            proyecto = new Proyecto();
                            proyecto.setProyectoId(rsResult.getInt("PROY"));
                            proyecto.setProyecto(rsResult.getString("DESCR"));

                            proyecto.setTipoProyecto(rsResult.getString("TIPO_PROY"));
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
        return proyecto;
    }

    public Accion getResultGetAccionById(int year, String ramoId, int metaId, int accionId) throws SQLException {
        Accion accion = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAccionById());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramoId);
                    pstmt.setInt(3, metaId);
                    pstmt.setInt(4, accionId);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accion = new Accion();
                            accion.setRamo(rsResult.getString("RAMO"));
                            accion.setPrg(rsResult.getString("PRG"));
                            accion.setAccionId(rsResult.getInt("ACCION"));
                            accion.setMeta(rsResult.getInt("META"));
                            accion.setAccion(rsResult.getString("DESCR"));
                            accion.setDeptoId(rsResult.getString("DEPTO"));
                            accion.setMedidaId(rsResult.getInt("CVE_MEDIDA"));
                            accion.setGrupoPob(rsResult.getInt("GRUPO_POBLACION"));
                            accion.setLineaPed(rsResult.getString("LINEA"));
                            accion.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                            accion.setCalculo(rsResult.getString("CALCULO"));
                            accion.setMedida(rsResult.getString("DMEDIDA"));
                            accion.setMunicipio(rsResult.getInt("MPO"));
                            accion.setLocalidad(rsResult.getInt("LOCALIDAD"));
                            accion.setBenefHom(rsResult.getInt("BENEF_HOMBRE"));
                            accion.setBenefMuj(rsResult.getInt("BENEF_MUJER"));
                            accion.setTipoAccion(rsResult.getInt("TIPO_ACCION"));
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

    public List<Requerimiento> getResultGeRequerimientoByAccion(int year, String ramoId, String programaId,
            int metaId, int accionId) throws SQLException {
        Requerimiento requerimiento = null;
        List<Requerimiento> requerimientoList = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRequerimientosByAccion(year, ramoId, programaId, metaId, accionId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        requerimientoList = new ArrayList<Requerimiento>();
                        while (rsResult.next()) {
                            requerimiento = new Requerimiento();
                            requerimiento.setReqId(rsResult.getInt("REQUERIMIENTO"));
                            requerimiento.setReq(rsResult.getString("DESCR"));
                            requerimiento.setPartida(rsResult.getString("PARTIDA"));
                            requerimiento.setCostoAnual(rsResult.getDouble("COSTO_ANUAL"));
                            requerimiento.setFuenteFin(rsResult.getString("FUENTE"));
                            requerimiento.setFuenteDescr(rsResult.getString("F_DESC"));
                            requerimientoList.add(requerimiento);
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
        return requerimientoList;
    }

    public Requerimiento getResultGetRequerimientoById(int year, String ramoId, String programaId,
            int metaId, int accionId, int requerimientoId) throws SQLException {
        Requerimiento requerimiento = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRequerimientoById(year, ramoId, programaId, metaId, accionId, requerimientoId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            requerimiento = new Requerimiento();
                            requerimiento.setReqId(rsResult.getInt("REQUERIMIENTO"));
                            requerimiento.setReq(rsResult.getString("DESCR"));
                            requerimiento.setPartida(rsResult.getString("PARTIDA"));
                            requerimiento.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            requerimiento.setArticulo(rsResult.getInt("ARTICULO"));
                            requerimiento.setGpogto(rsResult.getInt("GPOGTO"));
                            requerimiento.setSubgpo(rsResult.getInt("SUBGPO"));
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

    public List<String> getYears() throws SQLException {
        List<String> yearList = new ArrayList<String>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetYears());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            yearList.add(rsResult.getString("ANTERIOR"));
                            yearList.add(rsResult.getString("ACTUAL"));
                            yearList.add(rsResult.getString("NUEVO"));
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
        return yearList;
    }

    public String getResultSQLGetAvisoInicial() throws SQLException {
        String avisoInicial = new String();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAvisoInicial());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            avisoInicial = rsResult.getString("AVISO_ENTRADA");
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
        return avisoInicial;
    }

    public String getResultSQLGetObraProyectoActividad(int year, int proyecto, String tipoProyecto) throws SQLException {
        String obra = new String();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetObraProyectoActividad(year, proyecto, tipoProyecto));
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

    public String getResultSQLGetNumObraAccion(int year, String ramo, int meta, int accion) throws SQLException {
        String obra = new String();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetNumObraAccion(year, ramo, meta, accion));
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

    public String getResultSQLGetArticuloDescr(int year, String partida, int articulo) throws SQLException {
        String strDescr = new String();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetArticuloDescr(year, partida, articulo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            strDescr = rsResult.getString("DESCR");
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
        return strDescr;
    }

    public List<TipoCalculo> getResultGeTipoCalculo() throws SQLException {
        TipoCalculo tipoCalculo = null;
        List<TipoCalculo> tipoCalculoList = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoCalculo());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        tipoCalculoList = new ArrayList<TipoCalculo>();
                        while (rsResult.next()) {
                            tipoCalculo = new TipoCalculo();
                            tipoCalculo.setAbrev(rsResult.getString("SIGLA"));
                            tipoCalculo.setTipoCalculo(rsResult.getString("DESCR"));
                            tipoCalculo.setTipoCalculoId(rsResult.getInt("TIPO_CALCULO"));
                            tipoCalculoList.add(tipoCalculo);
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
        return tipoCalculoList;
    }

    public double getResultSQLGetCostoArticulo(int articulo, String partida, int year) throws SQLException {
        Double costo = 0.0;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCostoArticulo(articulo, partida, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            costo = 0.0;
                            costo = rsResult.getDouble("COSTO");
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
        return costo;
    }

    public PresupuestoIngreso getResultSQLGetPresupuestoIngresoById(int year, String ramo, String concepto, int pptoId) throws SQLException {
        QuerysBD query = new QuerysBD();
        PresupuestoIngreso presupuesto = null;
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPresupuestoIngresoById(year, ramo, concepto, pptoId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            presupuesto = new PresupuestoIngreso();
                            presupuesto.setPresupuestoIngreso(rsResult.getString("DESCR"));
                            presupuesto.setPresupuestoIngresoId(rsResult.getString("CONSEC"));
                            presupuesto.setEne(rsResult.getDouble("ENE"));
                            presupuesto.setFeb(rsResult.getDouble("FEB"));
                            presupuesto.setMar(rsResult.getDouble("MZO"));
                            presupuesto.setAbr(rsResult.getDouble("ABR"));
                            presupuesto.setMay(rsResult.getDouble("MAY"));
                            presupuesto.setJun(rsResult.getDouble("JUN"));
                            presupuesto.setJul(rsResult.getDouble("JUL"));
                            presupuesto.setAgo(rsResult.getDouble("AGO"));
                            presupuesto.setSep(rsResult.getDouble("SEP"));
                            presupuesto.setOct(rsResult.getDouble("OCT"));
                            presupuesto.setNov(rsResult.getDouble("NOV"));
                            presupuesto.setDic(rsResult.getDouble("DIC"));
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
        return presupuesto;
    }

    public Double getResultSQLGetCostoAccion(int year, String ramo, int meta, int accion) throws SQLException {
        Double costo = 0.0;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCostoAccion(year, ramo, meta, accion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            costo += rsResult.getInt("ENE");
                            costo += rsResult.getInt("FEB");
                            costo += rsResult.getInt("MAR");
                            costo += rsResult.getInt("ABR");
                            costo += rsResult.getInt("MAY");
                            costo += rsResult.getInt("JUN");
                            costo += rsResult.getInt("JUL");
                            costo += rsResult.getInt("AGO");
                            costo += rsResult.getInt("SEP");
                            costo += rsResult.getInt("OCT");
                            costo += rsResult.getInt("NOV");
                            costo += rsResult.getInt("DIC");
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
        return costo;
    }

    public double getResultSQLGetTechoRamo(int year, String ramo) throws SQLException {
        double costo = 0.0;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTechoRamo(year, ramo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            costo += rsResult.getDouble("TECHO_PRESUP");
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
        return costo;
    }

    public Meta getResultGetMetaById(String ramoId, String programaId, int proyectoId, int metaId, int year, String tipoProy) throws SQLException {
        Meta meta = new Meta();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMetaById(ramoId, programaId, proyectoId, year, metaId, tipoProy));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            meta = new Meta();
                            meta.setRamo(rsResult.getString("RAMO"));
                            meta.setPrograma(rsResult.getString("PRG"));
                            meta.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            meta.setProyecto(rsResult.getInt("PROY"));
                            meta.setMetaId(rsResult.getInt("META"));
                            meta.setMeta(rsResult.getString("DESCR"));
                            meta.setCalculo(rsResult.getString("CALCULO"));
                            meta.setFinalidad(rsResult.getString("FINALIDAD"));
                            meta.setFuncion(rsResult.getString("FUNCION"));
                            meta.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            meta.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                            meta.setClasificacion(rsResult.getString("FINALIDAD") + "."
                                    + rsResult.getString("FUNCION") + "." + rsResult.getString("SUBFUNCION"));
                            meta.setCompromiso(rsResult.getInt("TIPO_COMPROMISO"));
                            meta.setLineaPED(rsResult.getString("LINEA"));
                            meta.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                            //meta.setGrupoPoblacional(rsResult.getInt("GRUPO_POBLACIONAL"));
                            meta.setBenefH(rsResult.getInt("BENEF_HOMBRE"));
                            meta.setBenefM(rsResult.getInt("BENEF_MUJER"));
                            meta.setConvenio(rsResult.getString("CONVENIO"));
                            meta.setPrincipal(rsResult.getString("PRINCIPAL"));
                            meta.setGenero(rsResult.getString("GENERO"));
                            meta.setPonderado(rsResult.getInt("PONDERADO"));
                            meta.setAutorizacion(rsResult.getString("PROCESO_AUTORIZACION"));
                            meta.setCriterio(rsResult.getInt("CRITERIO"));
                            meta.setObra(rsResult.getString("OBRA"));
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

    public Meta getResultGetMetaById(int year, String ramo, int meta) throws SQLException {
        Meta metaObj = new Meta();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMetaById());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            metaObj = new Meta();
                            metaObj.setRamo(rsResult.getString("RAMO"));
                            metaObj.setPrograma(rsResult.getString("PRG"));
                            metaObj.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            metaObj.setProyecto(rsResult.getInt("PROY"));
                            metaObj.setMetaId(rsResult.getInt("META"));
                            metaObj.setMeta(rsResult.getString("DESCR"));
                            metaObj.setCalculo(rsResult.getString("CALCULO"));
                            metaObj.setFinalidad(rsResult.getString("FINALIDAD"));
                            metaObj.setFuncion(rsResult.getString("FUNCION"));
                            metaObj.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            metaObj.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                            metaObj.setMedidaDescr(rsResult.getString("DMEDIDA"));
                            metaObj.setClasificacion(rsResult.getString("FINALIDAD") + "."
                                    + rsResult.getString("FUNCION") + "." + rsResult.getString("SUBFUNCION"));
                            metaObj.setCompromiso(rsResult.getInt("TIPO_COMPROMISO"));
                            metaObj.setLineaPED(rsResult.getString("LINEA"));
                            metaObj.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                            //meta.setGrupoPoblacional(rsResult.getInt("GRUPO_POBLACIONAL"));
                            metaObj.setBenefH(rsResult.getInt("BENEF_HOMBRE"));
                            metaObj.setBenefM(rsResult.getInt("BENEF_MUJER"));
                            metaObj.setConvenio(rsResult.getString("CONVENIO"));
                            metaObj.setPrincipal(rsResult.getString("PRINCIPAL"));
                            metaObj.setGenero(rsResult.getString("GENERO"));
                            metaObj.setPonderado(rsResult.getInt("PONDERADO"));
                            metaObj.setAutorizacion(rsResult.getString("PROCESO_AUTORIZACION"));
                            metaObj.setCriterio(rsResult.getInt("CRITERIO"));
                            metaObj.setObra(rsResult.getString("OBRA"));
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
        return metaObj;
    }

    public Meta getResultGetMovoficiosMeta(int oficio, String ramo, int meta) throws SQLException {
        Meta metaObj = new Meta();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getMovoficioMeta());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            metaObj = new Meta();
                            metaObj.setRamo(rsResult.getString("RAMO"));
                            metaObj.setPrograma(rsResult.getString("PRG"));
                            metaObj.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            metaObj.setProyecto(rsResult.getInt("PROY"));
                            metaObj.setMetaId(rsResult.getInt("META"));
                            metaObj.setMeta(rsResult.getString("DESCR"));
                            metaObj.setCalculo(rsResult.getString("CALCULO"));
                            metaObj.setFinalidad(rsResult.getString("FINALIDAD"));
                            metaObj.setFuncion(rsResult.getString("FUNCION"));
                            metaObj.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            metaObj.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
                            metaObj.setMedidaDescr(rsResult.getString("DMEDIDA"));
                            metaObj.setClasificacion(rsResult.getString("FINALIDAD") + "."
                                    + rsResult.getString("FUNCION") + "." + rsResult.getString("SUBFUNCION"));
                            metaObj.setCompromiso(rsResult.getInt("TIPO_COMPROMISO"));
                            metaObj.setLineaPED(rsResult.getString("LINEA"));
                            metaObj.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                            //meta.setGrupoPoblacional(rsResult.getInt("GRUPO_POBLACIONAL"));
                            metaObj.setBenefH(rsResult.getInt("BENEF_HOMBRE"));
                            metaObj.setBenefM(rsResult.getInt("BENEF_MUJER"));
                            metaObj.setConvenio(rsResult.getString("CONVENIO"));
                            metaObj.setPrincipal(rsResult.getString("PRINCIPAL"));
                            metaObj.setGenero(rsResult.getString("GENERO"));
                            metaObj.setPonderado(rsResult.getInt("PONDERADO"));
                            metaObj.setAutorizacion(rsResult.getString("PROCESO_AUTORIZACION"));
                            metaObj.setCriterio(rsResult.getInt("CRITERIO"));
                            metaObj.setObra(rsResult.getString("OBRA"));
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
        return metaObj;
    }

    public List<Estimacion> getResultSQLGetMovoficioEstimacion(int oficio, String ramo, int meta) throws Exception {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        ResultSet rsResult = null;
        Estimacion estimacion;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getMovoficioEstimacion());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        estimacion = new Estimacion();
                        estimacion.setMeta(rsResult.getInt("META"));
                        estimacion.setRamo(rsResult.getString("RAMO"));
                        estimacion.setPeriodo(rsResult.getInt("PERIODO"));
                        estimacion.setValor(rsResult.getDouble("VALOR"));
                        estimacionList.add(estimacion);
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
        return estimacionList;
    }

    public Accion getResultGetMovoficioAccion(int oficio, String ramoId, int metaId, int accionId) throws SQLException {
        Accion accion = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getMovoficioAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramoId);
                    pstmt.setInt(3, metaId);
                    pstmt.setInt(4, accionId);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accion = new Accion();
                            accion.setRamo(rsResult.getString("RAMO"));
                            accion.setPrg(rsResult.getString("PRG"));
                            accion.setAccionId(rsResult.getInt("ACCION"));
                            accion.setMeta(rsResult.getInt("META"));
                            accion.setAccion(rsResult.getString("DESCR"));
                            accion.setDeptoId(rsResult.getString("DEPTO"));
                            accion.setMedidaId(rsResult.getInt("CVE_MEDIDA"));
                            accion.setGrupoPob(rsResult.getInt("GRUPO_POBLACION"));
                            accion.setLineaPed(rsResult.getString("LINEA"));
                            accion.setLineaSectorial(rsResult.getString("LINEA_SECTORIAL"));
                            accion.setCalculo(rsResult.getString("CALCULO"));
                            accion.setMedida(rsResult.getString("DMEDIDA"));
                            accion.setMunicipio(rsResult.getInt("MPO"));
                            accion.setLocalidad(rsResult.getInt("LOCALIDAD"));
                            accion.setBenefHom(rsResult.getInt("BENEF_HOMBRE"));
                            accion.setBenefMuj(rsResult.getInt("BENEF_MUJER"));
                            accion.setTipoAccion(rsResult.getInt("TIPO_ACCION"));
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

    public List<Estimacion> getResultSQLGetMovoficiosAccionEstimacion(int oficio, String ramo, int meta, int accion) throws Exception {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        ResultSet rsResult = null;
        Estimacion estimacion;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetMovoficiosEstimacionAccion());
                if (pstmt != null) {
                    pstmt.setInt(1, oficio);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        estimacion = new Estimacion();
                        estimacion.setPeriodo(rsResult.getInt("PERIODO"));
                        estimacion.setValor(rsResult.getDouble("VALOR"));
                        estimacionList.add(estimacion);
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
        return estimacionList;
    }

    public Persona getResultPersonaByRFC(String rfc, String homoclave) throws SQLException {
        Persona persona = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetEmpleadoByRFC(rfc, homoclave));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            persona = new Persona();
                            persona.setRfc(rsResult.getString("RFC"));
                            persona.setHomoclave(rsResult.getString("HOMOCLAVE"));
                            persona.setNombre(rsResult.getString("NOMBRE"));
                            persona.setApPaterno(rsResult.getString("PATERNO"));
                            persona.setApMaterno(rsResult.getString("MATERNO"));
                            persona.setCategoria(rsResult.getString("CATEGORIA"));
                            persona.setEmplDescr(rsResult.getString("DESCR"));
                            persona.setNivel(rsResult.getString("NIVEL"));
                            persona.setRamo(rsResult.getString("RAMO"));
                            persona.setDepartamento(rsResult.getString("DEPTO"));
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
        return persona;
    }

    public Programa getResultProgramaById(int year, String ramoId, String programaId) throws Exception {
        Programa programa = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProgramaById(ramoId, programaId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = new Programa();
                            programa.setProgramaId(rsResult.getString("PROG_ID"));
                            programa.setProgramaDesc(rsResult.getString("PROG"));
                            programa.setYear(rsResult.getInt("YEAR"));
                            programa.setObjetivo(rsResult.getString("OBJETIVO"));
                            programa.setResultado(rsResult.getString("RESULTADO"));
                            programa.setAspectos(rsResult.getString("ASPECTOS"));
                            programa.setRfc(rsResult.getString("RFC"));
                            programa.setHomoclave(rsResult.getString("HOMOCLAVE"));
                            programa.setRfcCoord(rsResult.getString("COORD_RFC"));
                            programa.setHomoCoord(rsResult.getString("COORD_HOMO"));
                            programa.setFin(rsResult.getInt("FIN"));
                            programa.setProposito(rsResult.getString("PROPOSITO"));
                            programa.setTipoPrograma(rsResult.getInt("TIPO_PROGRAMA"));
                            programa.setPonderado(rsResult.getInt("PONDERADO"));
                            programa.setTipoObjeto(rsResult.getInt("TIPO_OBJ"));
                            programa.setUnidadResponsable(rsResult.getString("DEPTO"));
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
        return programa;
    }

    public List<Accion> getResultSQLGetAcciones(int year, String ramoId, int metaId) throws Exception {
        List<Accion> accionList = new ArrayList<Accion>();
        ResultSet rsResult = null;
        Accion accion;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAcciones(year, ramoId, metaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        accion = new Accion();
                        accion.setYear(rsResult.getInt("YEAR"));
                        accion.setRamo(rsResult.getString("RAMO"));
                        accion.setPrg(rsResult.getString("PRG"));
                        accion.setDeptoId(rsResult.getString("DEPTO"));
                        accion.setMeta(rsResult.getInt("META"));
                        accion.setAccionId(rsResult.getInt("ACCION"));
                        accion.setAccion(rsResult.getString("DESCR"));
                        accion.setObra(rsResult.getLong("OBRA"));
                        accionList.add(accion);
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
        return accionList;
    }

    public List<Accion> getResultSQLGetAccionesAvance(int year, String ramoId, int metaId) throws Exception {
        List<Accion> accionList = new ArrayList<Accion>();
        ResultSet rsResult = null;
        Accion accion;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAccionesAvance(year, ramoId, metaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        accion = new Accion();
                        accion.setAccionId(rsResult.getInt("ACCION"));
                        accion.setAccion(rsResult.getString("DESCR"));
                        accion.setObra(rsResult.getLong("OBRA"));
                        accionList.add(accion);
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
        return accionList;
    }

    public List<Estimacion> getResultSQLGetEstimacion(int year, String ramoId, int metaId) throws Exception {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        ResultSet rsResult = null;
        Estimacion estimacion;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetEstimacionByMeta(year, ramoId, metaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        estimacion = new Estimacion();
                        estimacion.setMeta(rsResult.getInt("META"));
                        estimacion.setRamo(rsResult.getString("RAMO"));
                        estimacion.setPeriodo(rsResult.getInt("PERIODO"));
                        estimacion.setValor(rsResult.getDouble("VALOR"));
                        estimacionList.add(estimacion);
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
        return estimacionList;
    }

    public List<Estimacion> getResultSQLGetEstimacionByAccion(int year, String ramoId, int metaId, int accionId) throws Exception {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        ResultSet rsResult = null;
        Estimacion estimacion;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetEstimacionByAccion(year, ramoId, metaId, accionId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        estimacion = new Estimacion();
                        estimacion.setPeriodo(rsResult.getInt("PERIODO"));
                        estimacion.setValor(rsResult.getDouble("VALOR"));
                        estimacionList.add(estimacion);
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
        return estimacionList;
    }

    public String getResultSQLGetTipoCalculoAccion(int year, String ramoId, int metaId, int accionId) throws Exception {
        ResultSet rsResult = null;
        String calculo = new String();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCalculoEstimacionAccion(year, ramoId, metaId, accionId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        calculo = rsResult.getString("CALCULO");
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
        return calculo;
    }

    public List<Fin> getResultFinRamo(int ramoId, int year) throws Exception {
        List<Fin> finList = new ArrayList<Fin>();
        ResultSet rsResult = null;
        Fin fin;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFinRamo(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        fin = new Fin();
                        fin.setFinId(rsResult.getInt("FIN"));
                        fin.setFin(rsResult.getString("NOMBRE"));
                        fin.setDescripcion(rsResult.getString("DESCR"));
                        finList.add(fin);
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
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return finList;
    }

    public List<OpcionMenu> getResultOpcionMenu(String usuario) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<OpcionMenu> menuList = new ArrayList<OpcionMenu>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            OpcionMenu menu;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLOpcionesMenuByUsuario(usuario));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            menu = new OpcionMenu();
                            menu.setLoginUsuario(rsResultado.getString("APP_LOGIN"));
                            menu.setRol(rsResultado.getInt("ROL"));
                            menu.setProceso(rsResultado.getInt("PROCESO"));
                            menu.setMenuId(rsResultado.getInt("MENU"));
                            menu.setDescripcion(rsResultado.getString("DESCR"));
                            menu.setNombreMenu(rsResultado.getString("SYSMENUNAME"));
                            menu.setUrl(rsResultado.getString("URL"));
                            menu.setSubMenu(rsResultado.getString("SUBMENU"));
                            menuList.add(menu);
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
        return menuList;
    }

    public Vector getResultSQLConsultaUsuario(String strUsuario) {
        Vector vecResultado = new Vector();
        QuerysBD query = new QuerysBD();

        try {
            vecResultado = conectaBD.ejecutaSQLConsulta(query.getSQLConsultaUsuario(strUsuario));
            //conectaBD.transaccionCommit();
        } catch (Exception sqle) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sqle, new Throwable());
            bitacora.grabaBitacora();
        }
        return vecResultado;
    }

    public boolean getResultSQLUpdateEstimacion(int year, String ramoId, int metaId, int periodo, Double valor) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstimacion(year, ramoId, metaId, periodo, valor));
                //conectaBD.transaccionCommit();
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

    public boolean getResultSQLUpdateDeptoPlantilla(int year, String ramo, String prg, int meta, int accion, String depto) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        PreparedStatement pstmt = null;
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateDeptoPlantilla(year, ramo, prg, meta, accion, depto));
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

    public boolean getResultSQLUpdateAccionEstimacion(int year, String ramoId, int metaId, int accionId, int periodo, Double valor) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateAccionEstimacion(year, ramoId, metaId, accionId, valor, periodo));
                //conectaBD.transaccionCommit();
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

    public boolean getResultSQLUpdateAccion(int year, String ramoId, int metaId, int accionId, String accDesc, String depto,
            int medida, String calculo, String linea, int grupoPob, int mpo, int localidad, String lineaSect, int benefMuj, int benefHom) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateAccion(year, ramoId,
                        metaId, accionId, accDesc, depto, medida, calculo, linea, grupoPob, mpo, localidad, lineaSect, benefMuj, benefHom));

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

    public boolean getResultSQLUpdateMeta(int year, String ramoId, String programaId, int proyectoId, int metaId, int medida, String calculo, String linea, int compromiso,
            int benefH, int benefM, String principal, String objMeta, int grupoPob, int ponderado, String lineaSect,
            String finalidad, String funcion, String subfuncion, String autorizacion, int criterio, String obra) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateMeta(year, ramoId, programaId, proyectoId,
                        metaId, medida, calculo, linea, compromiso, benefH, benefM, principal, objMeta, grupoPob, ponderado,
                        lineaSect, finalidad, funcion, subfuncion, autorizacion, criterio, obra));
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
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLUpdateBeneficiariosMeta(int year, String ramoId, String programaId, int metaId, int[] sumBenef) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateBeneficiatiosMeta(year, ramoId, programaId, metaId, sumBenef));
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

    public Evaluacion getResultSQLGetEvaluacionMeta(int year) throws SQLException {
        Evaluacion evaluacion = new Evaluacion();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetEvaluacionMeta(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            evaluacion.setNumMeses(rsResult.getInt("NUMEVAL"));
                            evaluacion.setTipoEvaluacion(rsResult.getString("DESCREVAL"));
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
        return evaluacion;
    }

    public List<TipoAccion> getResultSQLGetTipoAccion() throws SQLException {
        TipoAccion tipoAccion;
        List<TipoAccion> tipoAccionList = new ArrayList<TipoAccion>();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoAccion());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoAccion = new TipoAccion();
                            tipoAccion.setTipoAccionId(rsResult.getInt("TIPO_ACCION"));
                            tipoAccion.setTipoAccion(rsResult.getString("DESCR"));
                            tipoAccionList.add(tipoAccion);
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
        return tipoAccionList;
    }

    public List<Transversalidad> getResultSQLGetCriterioTrans() throws SQLException {
        Transversalidad transversalidad;
        List<Transversalidad> transverList = new ArrayList<Transversalidad>();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetCriterioTransversalidad());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            transversalidad = new Transversalidad();
                            transversalidad.setTransversalidadId(rsResult.getInt("CRITERIO"));
                            transversalidad.setTransvesalidad(rsResult.getString("DESCR"));
                            transverList.add(transversalidad);
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
        return transverList;
    }

    public double getResultSQLGetContEstimacionMeta(int year, String ramoId, int meta) throws SQLException {
        double contEst = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCountEstimacionMeta(year, ramoId, meta));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            contEst = rsResult.getDouble("VALOR");
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
        return contEst;
    }

    @SuppressWarnings("null")
    public int getResultSQLGetValidaCountEstimacionMeta(int year, String ramoId, int meta) throws SQLException {
        int contEst = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaCountEstimacionMeta(year, ramoId, meta));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            contEst = rsResult.getInt("VALOR");
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
        return contEst;
    }

    public double getResultSQLGetContEstimacionAccion(int year, String ramo, int meta, int accion) throws SQLException {
        double contEst = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetContEstimacionAccion(year, ramo, meta, accion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            contEst = rsResult.getDouble("CONT_EST");
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
        return contEst;
    }

    public int getResultSQLGetDeptoRamoPrograma(int year, String ramoId, String programaId) throws SQLException {
        int depto = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLDeptpRamoPrograma(year, ramoId, programaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            depto = rsResult.getInt("DEPTO");
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
        return depto;
    }

    public String getResultSQLGetDeptoAccion(int year, String ramoId, int meta, int accion) throws SQLException {
        String depto = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLDeptoAccion(year, ramoId, meta, accion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            depto = rsResult.getString("DEPTO");
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
        return depto;
    }

    public List<ClasificacionFuncional> getResultSQLGetClasificacionFuncional(String ramoId, String programaId, int proyectoId, int year, String tipoProy) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        ClasificacionFuncional clasificacion = new ClasificacionFuncional();
        List<ClasificacionFuncional> clasificacionList = new ArrayList<ClasificacionFuncional>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetClasificacionFuncional(ramoId, programaId, proyectoId, year, tipoProy));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            clasificacion = new ClasificacionFuncional();
                            clasificacion.setClasificacion(rsResult.getString("CLASIF"));
                            clasificacion.setClasificacionDescr(rsResult.getString("DESCR"));
                            clasificacionList.add(clasificacion);
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
        return clasificacionList;
    }

    public String getResultSQLGetTipoCalculo(int year, String ramoId, String programaId, int proyectoId, int metaId) throws SQLException {
        String tipoC = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoCalculo(year, ramoId, programaId, proyectoId, metaId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoC = rsResult.getString("CALCULO");
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
        return tipoC;
    }

    public List<FuenteFinanciamiento> getResultSQLGetAccionFuente(int year, String ramoId, int metaId, int accionId) throws SQLException {
        ResultSet rsResult = null;
        List<FuenteFinanciamiento> fuenteList = new ArrayList<FuenteFinanciamiento>();
        FuenteFinanciamiento fuenteFin;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAccionFuente(year, ramoId, metaId, accionId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fuenteFin = new FuenteFinanciamiento();
                            fuenteFin.setFuenteId(rsResult.getInt("FUENTE"));
                            fuenteFin.setFuenteFinanciamiento(rsResult.getString("DESCR"));
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
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return fuenteList;
    }

    public boolean getResultSQLInsertFuenteFinanciamiento(int year, String ramo, int meta, int accion, int fuente, String programa) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertAccionFuente(year, ramo, meta, accion, fuente, programa));
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
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteMovtoAccionReq(int folio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteMovtoAccionReq(folio));
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

    public boolean getResultSQLDeleteMovtoAccionReqByTipoOficio(int folio, String tipoOficio) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLdeleteMovtoAccionReqByTipoOficio(folio, tipoOficio));
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

    public boolean getResultSQLInsertProyeccion(List<Proyeccion> proyeccionList, int year) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                for (Proyeccion proyeccion : proyeccionList) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertProyeccion(proyeccion, year));
                }
                conectaBD.transaccionCommit();
            } catch (Exception sqle) {
                resultado = false;
                errorBD = sqle.getMessage();
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

    public boolean getResultSQLUpdateEstatusPresupPlantilla() {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateEstatusPresupPlantilla());
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

    public boolean getResultSQLDeletePresupPlantilla() {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeletePresupPlantillaMal());
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
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertCodigo(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertCodigoProgramatico(year, ramo, depto, finalidad,
                        funcion, subfuncion, prgConac, programa, tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente,
                        fondo, recurso, municipio, delegacion, relLab));
                //conectaBD.transaccionCommit(); revisado
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

    public boolean getResultSQLInsertRequerimiento(int year, String ramoId, String programaId, String depto, int metaId,
            int accionId, String fuete, String tipoGasto, int requerimientoId, String requerimiento, String partida,
            String reqLaboral, double cantidad, Double costoUnit, Double costoAnual, double ene, double feb, double marzo, double abril,
            double mayo, double junio, double julio, double agosto, double sept, double oct, double nov, double dic, int articulo, String justificacion,
            String fondo, String recurso, String usuario, int gpogto, int subgpo, String archivo) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertRequerimiento(year,
                        ramoId, programaId, depto, metaId, accionId, fuete, tipoGasto, requerimientoId,
                        requerimiento, partida, reqLaboral, cantidad, costoUnit, costoAnual,
                        ene, feb, marzo, abril, mayo, junio, julio, agosto, sept, oct, nov, dic,
                        articulo, justificacion, fondo, recurso, usuario, gpogto, subgpo, archivo));
                //conectaBD.transaccionCommit(); revisado
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

    public boolean getResultSQLInsertRequerimientoPresupPlantilla(Ppto presPpto, int reqId) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertRequerimientoPresupPlantilla(presPpto, reqId));
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

    public boolean getResultUpdateRequerimiento(int year, String ramoId, String programaId, int meta, int accion, int req,
            String justificacion, double cantidad, double costoUnitario, double costoAnual, String relLab,
            double ene, double feb, double mar, double abr, double may, double jun, double jul, double ago, double sep,
            double oct, double nov, double dic, String partidDesc, String usuario, int articulo, int gpogto, int subgpo) {
        boolean result = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                result = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateRequerimiento(year, ramoId, programaId,
                        meta, accion, req, justificacion, cantidad, costoUnitario, costoAnual,
                        relLab, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic, partidDesc, usuario, articulo, gpogto, subgpo));
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
        return result;
    }

    public boolean getResultSQLDeleteFuenteFinanciamiento(int year, String ramo, int meta, int accion) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteFuenteFinanciamiento(year, ramo, meta, accion));
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
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLDeleteTipoGasto(int year, String ramo, int meta, int accion, int fuente) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteAccionTipoGasto(year, ramo, meta, accion, fuente));
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
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public boolean getResultSQLInsertTipoGasto(int year, String ramo, int meta, int accion, int fuente, int tipoGasto, String programa) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertAccionTipoGasto(year, ramo, meta, accion, fuente, tipoGasto, programa));
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
                    bitacora.grabaBitacora();
                }
            }
        }
        return resultado;
    }

    public CodigoPPTO getRestulSQLGetCodigoPPTO(int year, String ramo, String programa, int proyecto, String requ, String partida, int meta, int accion, String tipoProy) throws SQLException {
        ResultSet rsResult = null;
        CodigoPPTO codigo = null;
        int cont = 0;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCodigoProgramatico(year, ramo, programa, proyecto, requ, partida, meta, accion, tipoProy));
                if (pstmt != null) {
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
                        }
                        if (cont > 1) {
                            bitacora.setStrUbicacion(getStrUbicacion());
                            bitacora.setStrServer(getStrServer());
                            bitacora.setITipoBitacora(1);
                            bitacora.setStrInformacion("Mas de 1 registro codigo GetCodigoPPTO");
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

    public List<FuenteFinanciamiento> getResultSQLGetFuenteFinanciamiento(int year) throws SQLException {
        ResultSet rsResult = null;
        List<FuenteFinanciamiento> fuenteList = new ArrayList<FuenteFinanciamiento>();
        FuenteFinanciamiento fuenteFin;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFuenteFinanciamiento(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fuenteFin = new FuenteFinanciamiento();
                            fuenteFin.setFuenteId(rsResult.getInt("FUENTE"));
                            fuenteFin.setFuenteFinanciamiento(rsResult.getString("DESCR"));
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
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return fuenteList;
    }

    public List<RelacionLaboral> getResultSQLGetRelacionLaboral(int year) throws SQLException {
        ResultSet rsResult = null;
        List<RelacionLaboral> relacionLaboralList = new ArrayList<RelacionLaboral>();
        RelacionLaboral relacionLab;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRelLaboral(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            relacionLab = new RelacionLaboral();
                            relacionLab.setRelacionLabId(rsResult.getString("REL_LABORAL"));
                            relacionLab.setRelacionLab(rsResult.getString("DESCR"));
                            relacionLaboralList.add(relacionLab);
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
        return relacionLaboralList;
    }

    public boolean getResultSQLInsertarMeta(int metaId, String ramoId, String programaId, int year,
            int cvMedida, int proyectoId, String calculo, String tipoProy, String depto, String clave,
            String lineaPed, int tipoCompr, int benefM, int benefH, String principal, String objComp,
            int grupoPoblacion, int ponderado, String lineaSectorial, int finalidad, int funcion,
            int subfuncion, String autorizacion, int criterio, String obra) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertMeta(metaId, ramoId, programaId,
                        year, cvMedida, proyectoId, calculo, tipoProy, depto, clave, lineaPed, tipoCompr, benefM,
                        benefH, principal, objComp, grupoPoblacion, ponderado, lineaSectorial, finalidad, funcion,
                        subfuncion, autorizacion, criterio, obra));
                conectaBD.transaccionCommit(); //Revisado
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

    public boolean getResultSQLinsertAccion(int metaId, String ramoId, int year, int accionId, String accionDesc,
            String depto, int medida, String tipoCalculo, String lineaPed, int grupoPob, int mpo, int localidad,
            String lineaSectorial, int benefMuj, int benefHom, int tipoAccion, String programaId) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertAccion(metaId, ramoId, year,
                        accionId, accionDesc, depto, medida, tipoCalculo, lineaPed, grupoPob, mpo, localidad,
                        lineaSectorial, benefMuj, benefHom, programaId));
                conectaBD.transaccionCommit(); //revisado
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

    public boolean getResultSQLInsertEstimacion(int year, String ramoId, int metaId, int periodo, Double valor) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertEstimacion(year, ramoId, metaId, periodo, valor));
                //conectaBD.transaccionCommit(); //Revisado
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

    public boolean getResultSQLInsertPresupPlantilla(String origen, int year, String ramo, String depto, String finalidad, String funcion,
            String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab,
            int mes, double cantidad) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertPresupPlantilla(origen, year,
                        ramo, depto, finalidad, funcion, subfuncion, prgConac, programa, tipoProy, proyecto, meta,
                        accion, partida, tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLab,
                        mes, cantidad));
                conectaBD.transaccionCommit(); //NO SE USA
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

    public boolean getResultSQLInsertPPTOPresupPlantilla(List<Ppto> pptoList) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertPptoPresupPlantilla(pptoList));
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

    public boolean getResultSQLDeletePPTOPresupPlantilla(List<Ppto> pptoList) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertPptoPresupPlantilla(pptoList));
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

    public boolean getResultSQLInsertAccionEstimacion(int year, String ramoId, int metaId, int accionId, int periodo, Double valor) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertAccionEstimacion(year, ramoId, metaId, accionId, periodo, valor));
                //conectaBD.transaccionCommit(); Revisado
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

    public boolean getResultSQLActualizaResponsableRamo(String rfc, String homoclave, int year, int ramoId) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        try {
            resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLActualizarResponsableRamo(rfc, homoclave, year, ramoId));
            conectaBD.transaccionCommit(); //Revisado
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
        return resultado;
    }

    public boolean getResultSQLActualizaProyecto(String rfc, String homoclave, int depto, int ramoId, int programaId,
            int proyectoId, int year) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateProyecto(rfc, homoclave, depto, ramoId, programaId, proyectoId, year));
                conectaBD.transaccionCommit(); //No se usa
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

    public boolean getResultSQLActualizaPrograma(String programaId, String ramoId, int year, String rfc, String homoclave,
            int fin, String proposito, int ponderado, int depto) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdatePrograma(programaId, ramoId, year, rfc, homoclave,
                        fin, proposito, ponderado, depto));
                //conectaBD.transaccionCommit(); REVISADO
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

    public boolean getResultSQLActualizaDatosUsuario(String strUsuario, String strCorreo,
            String strEstatus, String strNombre) {
        boolean blResultado = false;
        QuerysBD query = new QuerysBD();

        try {
            blResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLActualizaDatosUsuario(strUsuario, strCorreo, strEstatus, strNombre));
            conectaBD.transaccionCommit(); //Revisado
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

        return blResultado;
    }

    public Vector getResultSQLConsultaUsuarios() {
        Vector vecResultado = new Vector();
        QuerysBD query = new QuerysBD();

        try {
            vecResultado = conectaBD.ejecutaSQLConsulta(query.getSQLConsultaUsuarios());
            conectaBD.transaccionCommit(); //Revisado
        } catch (Exception sqle) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sqle, new Throwable());
            bitacora.grabaBitacora();
        }
        return vecResultado;
    }

    public List<TipoGasto> getResultSQLAccionTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente) throws SQLException {
        List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
        TipoGasto tipoGasto;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAccionTipoGasto(year, ramoId, metaId, accionId, fuente));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoGasto = new TipoGasto();
                            tipoGasto.setTipoGastoId(rsResult.getInt("TIPO_GASTO"));
                            tipoGasto.setTipoGasto(rsResult.getString("DESCR"));
                            tipoGastoList.add(tipoGasto);
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
        return tipoGastoList;
    }

    public List<TipoGasto> getResultSQLTipoGasto(int year) throws SQLException {
        List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
        TipoGasto tipoGasto;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTipoGasto(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoGasto = new TipoGasto();
                            tipoGasto.setTipoGastoId(rsResult.getInt("TIPO_GASTO"));
                            tipoGasto.setTipoGasto(rsResult.getString("DESCR"));
                            tipoGastoList.add(tipoGasto);
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
        return tipoGastoList;
    }

    public int[] getResultSQLSumaBeneficiarios(int year, String ramoId, int meta) throws SQLException {
        int sumaBenef[] = new int[2];
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetSumaBeneficiarios(year, ramoId, meta));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            sumaBenef[0] = rsResult.getInt("MUJ");
                            sumaBenef[1] = rsResult.getInt("HOM");
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
        return sumaBenef;
    }

    public List<LineaSectorial> getResultSQLLineaSectorial(int year) throws SQLException {
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        LineaSectorial lineaSectorial;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaSectorial(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            lineaSectorial = new LineaSectorial();
                            lineaSectorial.setLineaId(rsResult.getString("LINEA_SECTORIAL"));
                            lineaSectorial.setLineaSectorial(rsResult.getString("DESCR"));
                            lineaSectorialList.add(lineaSectorial);
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
        return lineaSectorialList;
    }

    public List<FuenteRecurso> getResultSQLGetFuenteRecurso(int year, int fuente) throws SQLException {
        List<FuenteRecurso> fuenteRecursioList = new ArrayList<FuenteRecurso>();
        FuenteRecurso fuenteRecurso;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFuenteRecurso(year, fuente));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fuenteRecurso = new FuenteRecurso();
                            fuenteRecurso.setFuente(rsResult.getString("FUENTE"));
                            fuenteRecurso.setFondo(rsResult.getString("FONDO"));
                            fuenteRecurso.setRecurso(rsResult.getString("RECURSO"));
                            fuenteRecurso.setFuenteRecursoDescr(rsResult.getString("DESCR"));
                            fuenteRecurso.setFuenteRecurso(fuenteRecurso.getFuente() + "."
                                    + fuenteRecurso.getFondo() + "." + fuenteRecurso.getRecurso());
                            fuenteRecursioList.add(fuenteRecurso);
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
        return fuenteRecursioList;
    }

    public List<FuenteRecurso> getResultSQLGetFuenteRecursoCompleto(int year) throws SQLException {
        List<FuenteRecurso> fuenteRecursioList = new ArrayList<FuenteRecurso>();
        FuenteRecurso fuenteRecurso;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFuenteRecursoCompleta(year));
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fuenteRecurso = new FuenteRecurso();
                            fuenteRecurso.setFuente(rsResult.getString("FUENTE"));
                            fuenteRecurso.setFondo(rsResult.getString("FONDO"));
                            fuenteRecurso.setRecurso(rsResult.getString("RECURSO"));
                            fuenteRecurso.setFuenteRecursoDescr(rsResult.getString("DESCR"));
                            fuenteRecurso.setFuenteRecurso(fuenteRecurso.getFuente() + "."
                                    + fuenteRecurso.getFondo() + "." + fuenteRecurso.getRecurso());
                            fuenteRecursioList.add(fuenteRecurso);
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
        return fuenteRecursioList;
    }

    public List<FuenteRecurso> getResultSQLGetFuenteRecursoFiltrado(int year, int fuente) throws SQLException {
        List<FuenteRecurso> fuenteRecursioList = new ArrayList<FuenteRecurso>();
        FuenteRecurso fuenteRecurso;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFuenteRecursoFiltrado(year, fuente));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fuenteRecurso = new FuenteRecurso();
                            fuenteRecurso.setFuente(rsResult.getString("FUENTE"));
                            fuenteRecurso.setFondo(rsResult.getString("FONDO"));
                            fuenteRecurso.setRecurso(rsResult.getString("RECURSO"));
                            fuenteRecurso.setFuenteRecursoDescr(rsResult.getString("DESCR"));
                            fuenteRecurso.setFuenteRecurso(fuenteRecurso.getFuente() + "."
                                    + fuenteRecurso.getFondo() + "." + fuenteRecurso.getRecurso());
                            fuenteRecursioList.add(fuenteRecurso);
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
        return fuenteRecursioList;
    }

    public boolean getResultDeleteLineas(String ramoId, String programaid, int proyectoId, int year, String tipoProy, String LineaPed) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        try {
            resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteLineasViejas(ramoId, programaid, proyectoId, year, tipoProy, LineaPed));
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
        return resultado;
    }

    public String getResultDeleteAccion(int year, String ramo, String programa, int meta, int accion) {
        boolean resultado = false;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();
        try {
            resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteAccion(year, ramo, programa, meta, accion));
            conectaBD.transaccionCommit(); //revisado
        } catch (Exception sqle) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sqle, new Throwable());
            bitacora.grabaBitacora();
            mensaje = sqle.getMessage();
            try {
                conectaBD.transaccionRollback();
            } catch (Exception e) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(e, new Throwable());
                bitacora.grabaBitacora();
            }
            if (resultado) {
                mensaje = "exito";
            }
        }
        return mensaje;
    }

    public boolean getResultDeleteLineasSectoriales(String ramoId, String programaid, int proyectoId, int year, String lineaSectorial, String tipoProy, String estrategia) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        try {
            resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteLineaSectorial(ramoId, programaid, proyectoId, year, lineaSectorial, tipoProy, estrategia));
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
        return resultado;
    }

    public boolean getResultDeleteMeta(int year, String ramoId, String programaId, int proyectoId, int metaId, String tipoProyecto) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        try {
            resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteMeta(year, ramoId, programaId, proyectoId, metaId, tipoProyecto));
            conectaBD.transaccionCommit(); //Revisado
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
        return resultado;
    }

    public boolean getResultInsertaLinea(int year, String ramoId, String programaId, String tipoP, int proyectoId, String lineaId) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        try {
            resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLSaveLinea(year, ramoId, programaId, tipoP, proyectoId, lineaId));
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
        return resultado;
    }

    public boolean getResultInsertaLineaSectorial(String ramoId, String programaId, int proyectoId, int year, String tipoP, String lineaId, String estrategia) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        try {
            resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertLineaSectorial(ramoId, programaId, proyectoId, year, tipoP, lineaId, estrategia));
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
        return resultado;
    }

    public int getResultSQLInsertaUsuario(String strUsuario, String strContrasena, String strCorreo,
            String strEstatus, String strNombre) {
        Vector vecDatos = new Vector();
        QuerysBD query = new QuerysBD();
        int iValorRegresa = 0;
        boolean blResultado = false;

        try {
            vecDatos = conectaBD.ejecutaSQLConsulta(query.getSQLValidaInsertaUsuario(strUsuario));
            if (vecDatos.size() > 0) {
                iValorRegresa = 2;
            } else {
                blResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertaUsuario(strUsuario, strContrasena, strCorreo, strEstatus, strNombre));
                if (blResultado) {
                    iValorRegresa = 1;
                }
            }
            conectaBD.transaccionCommit(); //Revisado
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
        return iValorRegresa;
    }

    public int getResultSQLCambiaContrasena(String strUsuario, String strContrasena, String strContrasenaAnterior) {
        Vector vecDatos = new Vector();
        QuerysBD query = new QuerysBD();
        int iValorRegresa = 0;
        boolean blResultado = false;

        try {
            vecDatos = conectaBD.ejecutaSQLConsulta(query.getSQLValidaCambiaContrasena(strUsuario, strContrasenaAnterior));
            if (vecDatos.size() == 0) {
                iValorRegresa = 2;
            } else {
                blResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLCambiaContrasena(strUsuario, strContrasena));
                if (blResultado) {
                    iValorRegresa = 1;
                }
            }
            conectaBD.transaccionCommit(); //Revisado
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
        return iValorRegresa;
    }

    public boolean getResultSQLValidaRamo(String ramo, int year) {
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int valida = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidacionRamo(ramo, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valida = rsResult.getInt("VALIDA");
                        }
                    }
                    if (valida == 1) {
                        resultado = true;
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            }
        }
        return resultado;
    }

    public int getResultSQLNumeroLineas(int year, String ramoId, String programaId, int proyectoId) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int valida = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTotalLineasPED(year, ramoId, programaId, proyectoId));
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

    public int getResultSQLNumeroSectorial(int year, String ramoId, String programaId, int proyectoId) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int valida = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTotalLineasSectorial(year, ramoId, programaId, proyectoId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valida = rsResult.getInt("SECTORIAL");
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

    public int getResultSQLTotalFuentes(int year, String ramoId, int metaId, int accionId) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int valida = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLTotalAccionFIN(year, ramoId, metaId, accionId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valida = rsResult.getInt("TOT_ACC");
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

    public int getResultSQLTotalTipoGasto(int year, String ramoId, int metaId, int accionId, int fuente) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int valida = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetTotalTipoGasto(year, ramoId, metaId, accionId, fuente));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valida = rsResult.getInt("TIPO_GASTO");
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

    public Vector getResultSQLSolicitaContrasena(String strUsuario, String strContrasena) {
        Vector vecDatos = new Vector();
        Vector vecResultado = new Vector();
        Vector vecInterno = new Vector();
        QuerysBD query = new QuerysBD();
        int iValorRegresa = 0;
        boolean blResultado = false;
        try {
            vecDatos = conectaBD.ejecutaSQLConsulta(query.getSQLValidaSolicitaContrasena(strUsuario));
            if (vecDatos.size() == 0) {
                iValorRegresa = 2;
            } else {
                vecInterno = (Vector) vecDatos.get(0);
                String strEstatus = (String) vecInterno.get(0);
                vecResultado.add(vecInterno.get(1));
                if (!strEstatus.equals("A")) {
                    iValorRegresa = 3;
                } else {
                    blResultado = conectaBD.ejecutaSQLActualizacion(query.getSQLSolicitaContrasena(strUsuario, strContrasena));
                    if (blResultado) {
                        iValorRegresa = 1;
                    }
                }
            }
            conectaBD.transaccionCommit();//Revisado
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
        vecResultado.add(0, Integer.toString(iValorRegresa));
        vecResultado.add(1, vecInterno.get(2));
        return vecResultado;
    }

    private void grabaBitacora(Exception ex, Throwable thThrowable) {
        try {
            Bitacora log;
            log = new Bitacora();
            log.setStrUbicacion(getStrUbicacion());
            log.setStrServer(getStrServer());
            log.setITipoBitacora(1);
            log.setStrInformacion(ex, thThrowable);
            log.grabaBitacora();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
    }

    public void commit() {
        try {
            conectaBD.transaccionCommit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void rollback() {
        try {
            conectaBD.transaccionRollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<ProgramaConac> getResultProgramaConac(String programaId, int year) throws Exception {
        List<ProgramaConac> programaConacList = new ArrayList<ProgramaConac>();
        ResultSet rsResult = null;
        ProgramaConac programaConac;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProgramaConac(programaId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        programaConac = new ProgramaConac();
                        programaConac.setAnio(rsResult.getString("YEAR"));
                        programaConac.setProgramaConac(rsResult.getString("PRG_CONAC"));
                        programaConac.setDescripcion(rsResult.getString("DESCR"));
                        programaConacList.add(programaConac);
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
        return programaConacList;
    }

    public ProgramaConac getResultProgramaConacSimple(String programaId, int year) throws Exception {
        ResultSet rsResult = null;
        ProgramaConac programaConac = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetProgramaConac(programaId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        programaConac = new ProgramaConac();
                        programaConac.setAnio(rsResult.getString("YEAR"));
                        programaConac.setProgramaConac(rsResult.getString("PRG_CONAC"));
                        programaConac.setDescripcion(rsResult.getString("DESCR"));
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
        return programaConac;
    }

    public List<ParametroPrg> getResultParametroPrgByRol(int rol) throws Exception {
        List<ParametroPrg> parametroPrgList = new ArrayList<ParametroPrg>();
        ResultSet rsResult = null;
        ParametroPrg parametroPrg;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetParametroPrgByRol(rol));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        parametroPrg = new ParametroPrg();
                        parametroPrg.setRol(rsResult.getInt("ROL"));
                        parametroPrgList.add(parametroPrg);
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
        return parametroPrgList;
    }

    @Override
    public ConectaBD getConectaBD() {
        return conectaBD;
    }

    public List<Articulo> getResultSQLArticuloPartida(int year, String partida) throws Exception {
        List<Articulo> articuloList = new ArrayList<Articulo>();
        Articulo articulo;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetArticuloPartida(year, partida));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        articulo = new Articulo();
                        articulo.setArticulo(rsResult.getString("DESCR"));
                        articulo.setArticuloId(rsResult.getString("ARTICULO"));
                        articulo.setCosto(rsResult.getDouble("COSTO"));
                        articuloList.add(articulo);
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
        return articuloList;
    }

    public List<Fin> getResultFinOnRamoPrograma(String ramoId, int year) throws Exception {

        List<Fin> finList = new ArrayList<Fin>();
        ResultSet rsResult = null;
        Fin fin;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFinOnRamoPrograma(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        fin = new Fin();
                        fin.setYear(rsResult.getInt("YEAR"));
                        fin.setFinId(rsResult.getInt("FIN"));
                        fin.setFin(rsResult.getString("NOMBRE"));
                        fin.setDescripcion(rsResult.getString("DESCR"));
                        finList.add(fin);
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
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null");
        }
        return finList;
    }

    public int getResultSQLisArticuloPartida(int year, String partida) throws Exception {
        int countArticulo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsArticuloPartida(year, partida));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        countArticulo = rsResult.getInt("CONT");
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
        return countArticulo;
    }

    public List<Fin> getResultFinOnRamoPrograma() throws Exception {
        List<Fin> finList = new ArrayList<Fin>();
        ResultSet rsResult = null;
        Fin fin;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetFinOnRamoPrograma());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        fin = new Fin();
                        fin.setYear(rsResult.getInt("YEAR"));
                        fin.setFinId(rsResult.getInt("FIN"));
                        fin.setFin(rsResult.getString("NOMBRE"));
                        fin.setDescripcion(rsResult.getString("DESCR"));
                        finList.add(fin);
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
        return finList;
    }

    public boolean getResultSQLisRequerimientoPPTOCerrado(int year, String ramoIs) throws Exception {
        String cierre = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCierreRequerimientoPPTO(ramoIs, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        cierre = rsResult.getString("CIERRE_ACCION_REQ_PPTO");
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
        if (cierre != null && cierre.equals("S")) {
            return true;
        } else {
            return false;
        }
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

    public boolean getResultSQLisCodigoCapturadoCarga(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion) throws Exception {
        int repetido = -1;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisCodigoCapturadoCarga(year, ramo, depto, finalidad,
                        funcion, subfuncion, prgConac, programa, tipoProy, proyecto, meta, accion, tipoGasto, fuente, fondo,
                        recurso, municipio, delegacion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        repetido = rsResult.getInt("CODIGO");
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
        if (repetido > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getResultSQLisCodigoRepetido(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab) throws Exception {
        int repetido = -1;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisCodigoRepetido(year, ramo, depto,
                        finalidad, funcion, subfuncion, prgConac, programa, tipoProy, proyecto, meta, accion, partida,
                        tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLab));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        repetido = rsResult.getInt("CODREP");
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
        if (repetido == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getResultSQLisAccionRegistrada(int year, String ramo, String origen, boolean isParaestatal) throws Exception {
        int repetido = -1;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        boolean bError = true;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLisAccionRegistrado(year, ramo, origen, isParaestatal));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        repetido = rsResult.getInt(1);
                        if (repetido == 0) {
                            bError = false;
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
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return bError;
    }

    public boolean getResultSQLisRequerimientoDescrCerrado(int year, String ramoIs) throws Exception {
        String cierre = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsReqDescrCerrado(ramoIs, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        cierre = rsResult.getString("CIERRE_REQ_DESCR");
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
        if (cierre != null && cierre.equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    public Date getResultSQLgetServerDate() throws Exception {
        Date date = new Date();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetServerDate());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        date = rsResult.getDate("SYSDATE");
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
        return date;
    }

    public boolean getResultSQLisAccionPPTOCerrado(int year, String ramoIs) throws Exception {
        String cierre = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCierreAccionPPTO(ramoIs, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        cierre = rsResult.getString("CIERRE_ACCION_PPTO");
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
        if (cierre != null && cierre.equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getResultSQLisAccionCerrado(int year, String ramoIs) throws Exception {
        String cierre = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCierreAccionPPTO(ramoIs, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        cierre = rsResult.getString("CIERRE_ACCION_PPTO");
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
        if (cierre != null && cierre.equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getResultSQLisPOACerrado(String ramoId, int year) throws Exception {
        int cierre = -1;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCierrePOA(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        cierre = rsResult.getInt("CIERRE_POA");
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
        if (cierre == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getResultSQLisPOAPresupueatable(String ramoId, String programa, int meta, int year) throws Exception {
        String cierre = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsMetaPresupuesta(ramoId, programa, meta, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        cierre = rsResult.getString("PRESUPUESTAR");
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
        if (cierre.equals("N")) {
            return true;
        } else {
            return false;
        }
    }

    public List<Dependencia> getResultDepartamentosByRamoProgramaYear(String ramoId, String programaId, int year) throws SQLException {
        List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
        Dependencia dependencia = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetDepartamentosByRamoProgramaYear(ramoId, programaId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            dependencia = new Dependencia();
                            dependencia.setRamo(rsResult.getString("RAMO"));
                            dependencia.setDeptoId(rsResult.getString("DEPTO"));
                            dependencia.setDepartamento(rsResult.getString("DESCR"));
                            dependencia.setMpo(rsResult.getString("MPO"));
                            dependencia.setYear(rsResult.getInt("YEAR"));
                            dependencia.setRfc(rsResult.getString("RFC"));
                            dependencia.setHomoclave(rsResult.getString("HOMOCLAVE"));
                            dependenciaList.add(dependencia);
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
        return dependenciaList;
    }

    public List<Plantilla> getResultPlantillasByRamoProgramaYear(String ramoId, String programaId, String departamentoId, int year) throws SQLException {
        List<Plantilla> plantillaList = new ArrayList<Plantilla>();
        Plantilla plantilla = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPlantillasByRamoProgramaDepartamentoYear(ramoId, programaId, departamentoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            plantilla = new Plantilla();
                            plantilla.setMeta(rsResult.getInt("META"));
                            plantilla.setDescrMeta(rsResult.getString("DESCR_META"));
                            plantilla.setAccion(rsResult.getInt("ACCION"));
                            plantilla.setDescrAccion(rsResult.getString("DESCR_ACCION"));
                            plantilla.setFuente(rsResult.getString("FUENTE"));
                            plantilla.setFondo(rsResult.getString("FONDO"));
                            plantilla.setRecurso(rsResult.getString("RECURSO"));
                            plantilla.setSeleccionado(rsResult.getInt("SELECCIONADO"));
                            plantilla.setTipoGasto(rsResult.getString("TIPO_GASTO"));
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

    public List<Plantilla> getResultPlantillasByRamoYear(String ramoId, int year) throws SQLException {
        List<Plantilla> plantillaList = new ArrayList<Plantilla>();
        Plantilla plantilla = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPlantillasByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            plantilla = new Plantilla();
                            plantilla.setPrgId(rsResult.getString("PRG"));
                            plantilla.setDescePrg(rsResult.getString("DESCR_PROGRAMA"));
                            plantilla.setDepto(rsResult.getString("DEPTO"));
                            plantilla.setDescrDepto(rsResult.getString("DESCR_DEPENDENCIA"));
                            plantilla.setMeta(rsResult.getInt("META"));
                            plantilla.setDescrMeta(rsResult.getString("DESCR_META"));
                            plantilla.setAccion(rsResult.getInt("ACCION"));
                            plantilla.setDescrAccion(rsResult.getString("DESCR_ACCION"));
                            plantilla.setSeleccionado(rsResult.getInt("SELECCIONADO"));
                            plantilla.setTipoGasto(rsResult.getString("TIPO_GASTO"));
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

    public boolean getResultSQLUpdateAccionAcctivarPlantilla(String ramoId, String programaId, String departamentoId, int metaId, int accionId, String plantilla, String year) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateAccionActivarPlantilla(ramoId, programaId, departamentoId, metaId, accionId, plantilla, year));
                conectaBD.transaccionCommit(); //No se usa
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

    public List<Ramo> getResultSQLRamosByRamoYear(String ramoId, int year) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<Ramo> ramoList = new ArrayList<Ramo>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Ramo ramo;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamosByRamoYearUsuario(ramoId, year));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        ramo = new Ramo();
                        ramo.setRamo(rsResultado.getString("RAMO"));
                        ramo.setRamoDescr(rsResultado.getString("DESCR"));
                        ramo.setTransitorio(rsResultado.getString("TRANSITORIO"));
                        ramo.setYear(rsResultado.getString("YEAR"));
                        ramoList.add(ramo);
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
        return ramoList;
    }

    public List<PresupuestoIngreso> getResultSQLGetPresupuestoIngresoByRamoConcepto(int year, String ramo, String concepto) throws Exception {
        ResultSet rsResultado = null;
        List<PresupuestoIngreso> presupuestoList = new ArrayList<PresupuestoIngreso>();
        PresupuestoIngreso presupuesto = new PresupuestoIngreso();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPresupuestoIngresoByRamoConcepto(year, ramo, concepto));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        presupuesto = new PresupuestoIngreso();
                        presupuesto.setPresupuestoIngresoId(rsResultado.getString("CONSEC"));
                        presupuesto.setPresupuestoIngreso(rsResultado.getString("DESCR"));
                        presupuestoList.add(presupuesto);
                        presupuesto.setEne(rsResultado.getDouble("ENE"));
                        presupuesto.setFeb(rsResultado.getDouble("FEB"));
                        presupuesto.setMar(rsResultado.getDouble("MZO"));
                        presupuesto.setAbr(rsResultado.getDouble("ABR"));
                        presupuesto.setMay(rsResultado.getDouble("MAY"));
                        presupuesto.setJun(rsResultado.getDouble("JUN"));
                        presupuesto.setJul(rsResultado.getDouble("JUL"));
                        presupuesto.setAgo(rsResultado.getDouble("AGO"));
                        presupuesto.setSep(rsResultado.getDouble("SEP"));
                        presupuesto.setOct(rsResultado.getDouble("OCT"));
                        presupuesto.setNov(rsResultado.getDouble("NOV"));
                        presupuesto.setDic(rsResultado.getDouble("DIC"));
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
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return presupuestoList;
    }

    public List<Ramo> getResultSQLRamoByUsuarioPara(String usuario, int year) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<Ramo> ramoList = new ArrayList<Ramo>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Ramo ramo;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamoByUsuarioPara(year, usuario));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        ramo = new Ramo();
                        ramo.setRamo(rsResultado.getString("RAMO"));
                        ramo.setRamoDescr(rsResultado.getString("DESCR"));
                        ramoList.add(ramo);
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
        return ramoList;
    }

    public List<PlantillaPersonal> getResultSQLGetPlantillaPersonal(int year, String ramo) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<PlantillaPersonal> plantillaList = new ArrayList<PlantillaPersonal>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            PlantillaPersonal plantilla;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPlantillaEmpleado(year, ramo));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        plantilla = new PlantillaPersonal();
                        plantilla.setRelLaboral(rsResultado.getString("REL_LABORAL"));
                        plantilla.setRelLaboralDescr(rsResultado.getString("DESCR"));
                        plantilla.setCantidad(rsResultado.getDouble("CANTIDAD"));
                        plantillaList.add(plantilla);
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
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return plantillaList;
    }

    public List<Ramo> getResultSQLRamoPlantillaEmple(String usuario, int year) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<Ramo> ramoList = new ArrayList<Ramo>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Ramo ramo;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamosPlantillaEmp(year, usuario));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        ramo = new Ramo();
                        ramo.setRamo(rsResultado.getString("RAMO"));
                        ramo.setRamoDescr(rsResultado.getString("DESCR"));
                        ramoList.add(ramo);
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
        return ramoList;
    }

    public List<ConceptoIngreso> getResultSQLGetConceptoIngreso(int year) {
        List<ConceptoIngreso> conceptoList = new ArrayList<ConceptoIngreso>();
        ConceptoIngreso conceptoIngreso;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetConceptoIngreso(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            conceptoIngreso = new ConceptoIngreso();
                            conceptoIngreso.setConceptoPresupuestoId(rsResult.getString("CONCEPTO"));
                            conceptoIngreso.setConceptoPresupuesto(rsResult.getString("CONCEPTO_DESCR"));
                            conceptoList.add(conceptoIngreso);
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            } finally {
                try {
                    this.desconectaDblink("ING");
                    pstmt.close();
                    rsResult.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ResultSQL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return conceptoList;
    }

    public boolean getResultSQLValidaRamoCierre(String ramoId, int year) {

        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int valida = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidacionRamoCierre(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valida = rsResult.getInt("VALIDA");
                        }
                    }
                    if (valida == 1) {
                        resultado = true;
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            } finally {
                try {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                    rsResult.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ResultSQL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultado;
    }

    public String getResultSQLgetParametroIndicadores() {
        boolean resultado = false;
        ResultSet rsResult = null;
        String parametro = new String();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetParametroIndicadores());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            parametro = rsResult.getString("MUESTRA_INDICADORES");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            } finally {
                try {
                    pstmt.close();
                    rsResult.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ResultSQL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return parametro;
    }

    public List<Programa> getResultSQLProgramaByYear(int year) throws Exception {
        List<Programa> programaList = new ArrayList<Programa>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Programa programa = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoProgramaByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            programa = new Programa();
                            programa.setYear(rsResult.getInt("YEAR"));
                            programa.setProgramaId(rsResult.getString("PRG"));
                            programa.setProgramaDesc(rsResult.getString("DESCR"));
                            programa.setProgramaConac(rsResult.getString("PRG_CONAC"));
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
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return programaList;
    }

    public List<Dependencia> getResultSQLCatalogoDependenciaByRamoYear(String ramoId, int year) throws SQLException {
        List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
        Dependencia dependencia = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoDependenciaByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            dependencia = new Dependencia();
                            dependencia.setRamo(rsResult.getString("RAMO"));
                            dependencia.setDeptoId(rsResult.getString("DEPTO"));
                            dependencia.setDepartamento(rsResult.getString("DESCR"));
                            dependencia.setMpo(rsResult.getString("MPO"));
                            dependencia.setYear(rsResult.getInt("YEAR"));
                            dependenciaList.add(dependencia);
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
        return dependenciaList;
    }

    public List<CodProg> getResultSQLCatalogoCodProgByRamoYear(String ramoId, int year) throws SQLException {
        List<CodProg> codProgList = new ArrayList<CodProg>();
        CodProg codProg = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoCodProgByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            codProg = new CodProg();
                            codProg.setRamo(rsResult.getString("RAMO"));
                            codProg.setPrg(rsResult.getString("PRG"));
                            codProg.setDepto(rsResult.getString("DEPTO"));
                            codProg.setYear(rsResult.getInt("YEAR"));
                            codProgList.add(codProg);
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
        return codProgList;
    }

    public List<Grupos> getResultSQLCatalogoGruposByYear(int year) throws SQLException {
        List<Grupos> gruposList = new ArrayList<Grupos>();
        Grupos grupos = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoGruposByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            grupos = new Grupos();
                            grupos.setGrupo(rsResult.getString("GRUPO"));
                            grupos.setDescr(rsResult.getString("DESCR"));
                            grupos.setYear(rsResult.getInt("YEAR"));
                            grupos.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            gruposList.add(grupos);
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
        return gruposList;
    }

    public List<SubGrupos> getResultSQLCatalogoSubGruposByYear(int year) throws SQLException {
        List<SubGrupos> subgruposList = new ArrayList<SubGrupos>();
        SubGrupos subgrupos = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoSubGruposByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            subgrupos = new SubGrupos();
                            subgrupos.setGrupo(rsResult.getString("GRUPO"));
                            subgrupos.setSubgrupo(rsResult.getString("SUBGRUPO"));
                            subgrupos.setDescr(rsResult.getString("DESCR"));
                            subgrupos.setYear(rsResult.getInt("YEAR"));
                            subgruposList.add(subgrupos);
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
        return subgruposList;
    }

    public List<SubSubGpo> getResultSQLCatalogoSubSubGpoByYear(int year) throws SQLException {
        List<SubSubGpo> subsubgpoList = new ArrayList<SubSubGpo>();
        SubSubGpo subsubgpo = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoSubSubGpoByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            subsubgpo = new SubSubGpo();
                            subsubgpo.setGrupo(rsResult.getString("GRUPO"));
                            subsubgpo.setSubgrupo(rsResult.getString("SUBGRUPO"));
                            subsubgpo.setSubsubgrupo(rsResult.getString("SUBSUBGRUPO"));
                            subsubgpo.setDescr(rsResult.getString("DESCR"));
                            subsubgpo.setYear(rsResult.getInt("YEAR"));
                            subsubgpoList.add(subsubgpo);
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
        return subsubgpoList;
    }

    public List<Partida> getResultSQLCatalogoPartidaByYear(int year) throws SQLException {
        List<Partida> partidaList = new ArrayList<Partida>();
        Partida partida = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoPartidaByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            partida = new Partida();
                            partida.setPartidaId(rsResult.getString("PARTIDA"));
                            partida.setPartida(rsResult.getString("DESCR"));
                            partida.setPlantilla(rsResult.getString("PLANTILLA"));
                            partida.setYear(rsResult.getInt("YEAR"));
                            partida.setSubsubgrupo(rsResult.getString("SUBSUBGPO"));
                            partida.setCtaAprobado(rsResult.getString("CTA_APROBADO"));
                            partida.setCtaModificado(rsResult.getString("CTA_MODIFICADO"));
                            partida.setCtaComprometido(rsResult.getString("CTA_COMPROMETIDO"));
                            partida.setCtaDevengado(rsResult.getString("CTA_DEVENGADO"));
                            partida.setCtaEjercido(rsResult.getString("CTA_EJERCIDO"));
                            partida.setCtaPagado(rsResult.getString("CTA_PAGADO"));
                            partida.setCtaPptoXEjer(rsResult.getString("CTA_PPTOXEJER"));
                            partida.setRelacionLaboral(rsResult.getString("RELACION_LABORAL"));
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

    public List<CodigoPPTO> getResultSQLCatalogoCodigosByRamoYear(String ramoId, int year) throws SQLException {
        List<CodigoPPTO> codigosList = new ArrayList<CodigoPPTO>();
        CodigoPPTO codigo = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoCodigosByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            codigo = new CodigoPPTO();
                            codigo.setYear(rsResult.getInt("YEAR"));
                            codigo.setRamoId(rsResult.getString("RAMO"));
                            codigo.setDepto(rsResult.getString("DEPTO"));
                            codigo.setFinalidad(rsResult.getString("FINALIDAD"));
                            codigo.setFuncion(rsResult.getString("FUNCION"));
                            codigo.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            codigo.setProgCONAC(rsResult.getString("PRG_CONAC"));
                            codigo.setPrograma(rsResult.getString("PRG"));
                            codigo.setTipoProy(rsResult.getString("TIPO_PROY"));
                            codigo.setProyecto(rsResult.getString("PROYECTO"));
                            codigo.setMetaId(rsResult.getString("META"));
                            codigo.setAccionId(rsResult.getString("ACCION"));
                            codigo.setPartida(rsResult.getString("PARTIDA"));
                            codigo.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            codigo.setFuente(rsResult.getString("FUENTE"));
                            codigo.setFondo(rsResult.getString("FONDO"));
                            codigo.setRecurso(rsResult.getString("RECURSO"));
                            codigo.setMunicipio(rsResult.getString("MUNICIPIO"));
                            codigo.setDelegacionId(rsResult.getString("DELEGACION"));
                            codigo.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            codigosList.add(codigo);
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
        return codigosList;
    }

    public List<CodigoPPTO> getResultSQLExisteCodigoPPTO(String ramoId, int year, String prg, String finalidad, String funcion, String subfuncion, String prg_conac, String depto, String tipo_proy, int proyecto, int meta, int accion, String Partida, String tipo_gasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String rel_laboral) throws SQLException {
        List<CodigoPPTO> codigosList = new ArrayList<CodigoPPTO>();
        CodigoPPTO codigo = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExisteCodigoPPTO(ramoId, year, prg, finalidad, funcion, subfuncion, prg_conac, depto, tipo_proy, proyecto, meta, accion, Partida, tipo_gasto, fuente, fondo, recurso, municipio, delegacion, rel_laboral));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            codigo = new CodigoPPTO();
                            codigo.setYear(rsResult.getInt("YEAR"));
                            codigo.setRamoId(rsResult.getString("RAMO"));
                            codigo.setDepto(rsResult.getString("DEPTO"));
                            codigo.setFinalidad(rsResult.getString("FINALIDAD"));
                            codigo.setFuncion(rsResult.getString("FUNCION"));
                            codigo.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            codigo.setProgCONAC(rsResult.getString("PRG_CONAC"));
                            codigo.setPrograma(rsResult.getString("PRG"));
                            codigo.setTipoProy(rsResult.getString("TIPO_PROY"));
                            codigo.setProyecto(rsResult.getString("PROYECTO"));
                            codigo.setMetaId(rsResult.getString("META"));
                            codigo.setAccionId(rsResult.getString("ACCION"));
                            codigo.setPartida(rsResult.getString("PARTIDA"));
                            codigo.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            codigo.setFuente(rsResult.getString("FUENTE"));
                            codigo.setFondo(rsResult.getString("FONDO"));
                            codigo.setRecurso(rsResult.getString("RECURSO"));
                            codigo.setMunicipio(rsResult.getString("MUNICIPIO"));
                            codigo.setDelegacionId(rsResult.getString("DELEGACION"));
                            codigo.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            codigosList.add(codigo);
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
        return codigosList;
    }

    private void escribeLinea(String ubicacion, String strCadena) throws Exception {
        PrintWriter outs = null;
        outs = new PrintWriter(new BufferedWriter(new FileWriter(ubicacion, true)));
        outs.println(strCadena);
        outs.close();
        outs.flush();
        outs = null;//libero out
    }

    public void getResultSQLCatalogoPPTOByRamoYear(String ramoId, int year, String ubicacion) throws SQLException, Exception {
        //List<Ppto> pptoList = new ArrayList<Ppto>();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String contenidoArch = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoPPTOByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        contenidoArch += "year\t";
                        contenidoArch += "ramo\t";
                        contenidoArch += "depto\t";
                        contenidoArch += "finalidad\t";
                        contenidoArch += "funcion\t";
                        contenidoArch += "subfuncion\t";
                        contenidoArch += "prg_conac\t";
                        contenidoArch += "prg\t";
                        contenidoArch += "tipo_proy\t";
                        contenidoArch += "proyecto\t";
                        contenidoArch += "meta\t";
                        contenidoArch += "accion\t";
                        contenidoArch += "partida\t";
                        contenidoArch += "tipo_gasto\t";
                        contenidoArch += "fuente\t";
                        contenidoArch += "fondo\t";
                        contenidoArch += "recurso\t";
                        contenidoArch += "municipio\t";
                        contenidoArch += "delegacion\t";
                        contenidoArch += "rel_laboral\t";
                        contenidoArch += "mes\t";
                        contenidoArch += "asignado";
                        this.escribeLinea(ubicacion, contenidoArch);
                        while (rsResult.next()) {

                            contenidoArch = rsResult.getInt("YEAR") + "\t";
                            contenidoArch += rsResult.getString("RAMO") + "\t";
                            contenidoArch += rsResult.getString("DEPTO") + "\t";
                            contenidoArch += rsResult.getString("FINALIDAD") + "\t";
                            contenidoArch += rsResult.getString("FUNCION") + "\t";
                            contenidoArch += rsResult.getString("SUBFUNCION") + "\t";
                            contenidoArch += rsResult.getString("PRG_CONAC") + "\t";
                            contenidoArch += rsResult.getString("PRG") + "\t";
                            contenidoArch += rsResult.getString("TIPO_PROY") + "\t";
                            contenidoArch += rsResult.getString("PROYECTO") + "\t";
                            contenidoArch += rsResult.getString("META") + "\t";
                            contenidoArch += rsResult.getString("ACCION") + "\t";
                            contenidoArch += rsResult.getString("PARTIDA") + "\t";
                            contenidoArch += rsResult.getString("TIPO_GASTO") + "\t";
                            contenidoArch += rsResult.getString("FUENTE") + "\t";
                            contenidoArch += rsResult.getString("FONDO") + "\t";
                            contenidoArch += rsResult.getString("RECURSO") + "\t";
                            contenidoArch += rsResult.getString("MUNICIPIO") + "\t";
                            contenidoArch += rsResult.getString("DELEGACION") + "\t";
                            contenidoArch += rsResult.getString("REL_LABORAL") + "\t";
                            contenidoArch += rsResult.getInt("MES") + "\t";
                            contenidoArch += rsResult.getDouble("ASIGNADO");
                            contenidoArch = contenidoArch.replace("null", "");
                            escribeLinea(ubicacion, contenidoArch);
                            //pptoList.add(ppto);
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
    }

    public List<RamoPrograma> getResultSQLCatalogoRamoProgramaByRamoYear(String ramoId, int year) throws SQLException {
        List<RamoPrograma> ramoProgramaList = new ArrayList<RamoPrograma>();
        RamoPrograma ramoPrograma = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoRamoProgramaByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ramoPrograma = new RamoPrograma();
                            ramoPrograma.setYear(rsResult.getInt("YEAR"));
                            ramoPrograma.setRamo(rsResult.getString("RAMO"));
                            ramoPrograma.setPrg(rsResult.getString("PRG"));
                            ramoProgramaList.add(ramoPrograma);
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
        return ramoProgramaList;
    }

    public List<Finalidad> getResultSQLCatalogoFinalidadByYear(int year) throws SQLException {
        List<Finalidad> finalidadList = new ArrayList<Finalidad>();
        Finalidad finalidad = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoFinalidadByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            finalidad = new Finalidad();
                            finalidad.setYear(rsResult.getInt("YEAR"));
                            finalidad.setFinalidad(rsResult.getString("FINALIDAD"));
                            finalidad.setDescr(rsResult.getString("DESCR"));
                            finalidadList.add(finalidad);
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
        return finalidadList;
    }

    public List<Funcion> getResultSQLCatalogoFuncionByYear(int year) throws SQLException {
        List<Funcion> funcionList = new ArrayList<Funcion>();
        Funcion funcion = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoFuncionByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            funcion = new Funcion();
                            funcion.setYear(rsResult.getInt("YEAR"));
                            funcion.setFinalidad(rsResult.getString("FINALIDAD"));
                            funcion.setFuncion(rsResult.getString("FUNCION"));
                            funcion.setDescr(rsResult.getString("DESCR"));
                            funcionList.add(funcion);
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
        return funcionList;
    }

    public List<Subfuncion> getResultSQLCatalogoSubfuncionByYear(int year) throws SQLException {
        List<Subfuncion> subfuncionList = new ArrayList<Subfuncion>();
        Subfuncion subfuncion = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoSubfuncionByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            subfuncion = new Subfuncion();
                            subfuncion.setYear(rsResult.getInt("YEAR"));
                            subfuncion.setFinalidad(rsResult.getString("FINALIDAD"));
                            subfuncion.setFuncion(rsResult.getString("FUNCION"));
                            subfuncion.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            subfuncion.setDescr(rsResult.getString("DESCR"));
                            subfuncionList.add(subfuncion);
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
        return subfuncionList;
    }

    public List<Fin> getResultSQLCatalogoFinByYear(int year) throws SQLException {
        List<Fin> finList = new ArrayList<Fin>();
        Fin fin = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoFinByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fin = new Fin();
                            fin.setYear(rsResult.getInt("YEAR"));
                            fin.setFinId(rsResult.getInt("FIN"));
                            fin.setFin(rsResult.getString("NOMBRE"));
                            fin.setDescripcion(rsResult.getString("DESCR"));
                            finList.add(fin);
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
        return finList;
    }

    public List<Fuente> getResultSQLCatalogoFuenteByYear(int year) throws SQLException {
        List<Fuente> fuenteList = new ArrayList<Fuente>();
        Fuente fuente = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoFuenteByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fuente = new Fuente();
                            fuente.setYear(rsResult.getInt("YEAR"));
                            fuente.setFuente(rsResult.getString("FUENTE"));
                            fuente.setDescr(rsResult.getString("DESCR"));
                            fuenteList.add(fuente);
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
        return fuenteList;
    }

    public List<Fondo> getResultSQLCatalogoFondoByYear(int year) throws SQLException {
        List<Fondo> fondoList = new ArrayList<Fondo>();
        Fondo fondo = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoFondoByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            fondo = new Fondo();
                            fondo.setYear(rsResult.getInt("YEAR"));
                            fondo.setFuente(rsResult.getString("FUENTE"));
                            fondo.setFondo(rsResult.getString("FONDO"));
                            fondo.setDescr(rsResult.getString("DESCR"));
                            fondoList.add(fondo);
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
        return fondoList;
    }

    public List<Recurso> getResultSQLCatalogoRecursoByYear(int year) throws SQLException {
        List<Recurso> recursoList = new ArrayList<Recurso>();
        Recurso recurso = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoRecursoByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            recurso = new Recurso();
                            recurso.setYear(rsResult.getInt("YEAR"));
                            recurso.setFuente(rsResult.getString("FUENTE"));
                            recurso.setFondo(rsResult.getString("FONDO"));
                            recurso.setRecurso(rsResult.getString("RECURSO"));
                            recurso.setDescr(rsResult.getString("DESCR"));
                            recursoList.add(recurso);
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
        return recursoList;
    }

    public List<ProgramaConac> getResultSQLCatalogoPrgConacByYear(int year) throws SQLException {
        List<ProgramaConac> prgConacList = new ArrayList<ProgramaConac>();
        ProgramaConac prgConac = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoPrgConacByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            prgConac = new ProgramaConac();
                            prgConac.setAnio(rsResult.getString("YEAR"));
                            prgConac.setProgramaConac(rsResult.getString("PRG_CONAC"));
                            prgConac.setDescripcion(rsResult.getString("DESCR"));
                            prgConacList.add(prgConac);
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
        return prgConacList;
    }

    public List<TipoAccion> getResultSQLCatalogoTipoAccion() throws SQLException {
        List<TipoAccion> tipoAccionList = new ArrayList<TipoAccion>();
        TipoAccion tipoAccion = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoTipoAccion());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoAccion = new TipoAccion();
                            tipoAccion.setTipoAccionId(rsResult.getInt("TIPO_ACCION"));
                            tipoAccion.setTipoAccion(rsResult.getString("DESCR"));
                            tipoAccion.setDetalleReq(rsResult.getString("REQ_DETALLE"));
                            tipoAccionList.add(tipoAccion);
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
        return tipoAccionList;
    }

    public List<TipoGasto> getResultSQLCatalogoTipoGastoByYear(int year) throws SQLException {
        List<TipoGasto> tipoGastoList = new ArrayList<TipoGasto>();
        TipoGasto tipoGasto = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoTipoGastoByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            tipoGasto = new TipoGasto();
                            tipoGasto.setYear(rsResult.getInt("YEAR"));
                            tipoGasto.setTipoGastoId(rsResult.getInt("TIPO_GASTO"));
                            tipoGasto.setTipoGasto(rsResult.getString("DESCR"));
                            tipoGastoList.add(tipoGasto);
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
        return tipoGastoList;
    }

    public List<RelacionLaboral> getResultSQLCatalogoRelLaboralByYear(int year) throws SQLException {
        List<RelacionLaboral> relLaboralList = new ArrayList<RelacionLaboral>();
        RelacionLaboral relLaboral = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoRelLaboralByYear(year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            relLaboral = new RelacionLaboral();
                            relLaboral.setYear(rsResult.getInt("YEAR"));
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
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return relLaboralList;
    }

    public List<CentroCosto> getResultSQLCatalogoCentroCosto() throws SQLException {
        List<CentroCosto> centroCostoList = new ArrayList<CentroCosto>();
        CentroCosto centroCosto = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoCentroCosto());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            centroCosto = new CentroCosto();
                            centroCosto.setCentroCosto(rsResult.getInt("CENTRO_COSTO"));
                            centroCosto.setDescr(rsResult.getString("DESCR"));

                            centroCostoList.add(centroCosto);
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
        return centroCostoList;
    }

    public List<Municipio> getResultSQLCatalogoMunicipio() throws SQLException {
        List<Municipio> municipioList = new ArrayList<Municipio>();
        Municipio municipio = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoMunicipio());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            municipio = new Municipio();
                            municipio.setMunicipioId(rsResult.getInt("MPO"));
                            municipio.setMunicipio(rsResult.getString("NOMBREMPO"));
                            municipioList.add(municipio);
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
        return municipioList;
    }

    public List<Proyecto> getResultSQLCatalogoProyectoByRamoYear(String ramoId, int year) throws SQLException {
        List<Proyecto> proyectoList = new ArrayList<Proyecto>();
        Proyecto proyecto = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoProyectoByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            proyecto = new Proyecto();
                            proyecto.setYear(rsResult.getInt("YEAR"));
                            proyecto.setRamo(rsResult.getString("RAMO"));
                            proyecto.setPrg(rsResult.getString("PRG"));
                            proyecto.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            proyecto.setProyId(rsResult.getString("PROY"));
                            //proyecto.setProyecto(rsResult.getString("DESCR"));
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
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return proyectoList;
    }

    public List<ProyectoFuncional> getResultSQLCatalogoProyectoFuncionalByRamoYear(String ramoId, int year) throws SQLException {
        List<ProyectoFuncional> proyectoFuncionalList = new ArrayList<ProyectoFuncional>();
        ProyectoFuncional proyectoFuncional = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoProyectoFuncionalByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            proyectoFuncional = new ProyectoFuncional();
                            proyectoFuncional.setYear(rsResult.getInt("YEAR"));
                            proyectoFuncional.setRamo(rsResult.getString("RAMO"));
                            proyectoFuncional.setPrg(rsResult.getString("PRG"));
                            proyectoFuncional.setTipoProy(rsResult.getString("TIPO_PROY"));
                            proyectoFuncional.setProy(rsResult.getInt("PROY"));
                            proyectoFuncional.setDepto(rsResult.getString("DEPTO"));
                            proyectoFuncional.setTipo(rsResult.getString("TIPO"));
                            proyectoFuncional.setFinalidad(rsResult.getString("FINALIDAD"));
                            proyectoFuncional.setFuncion(rsResult.getString("FUNCION"));
                            proyectoFuncional.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            proyectoFuncionalList.add(proyectoFuncional);
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
        return proyectoFuncionalList;
    }

    public boolean getResultSQLDeleteMetaAccionPlantilla(String ramo, String prg, String depto, int meta, int accion, String fuente, String fondo, String recurso, int year) {
        boolean resultado = false;

        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteMetaAccionPlantilla(year, ramo, prg, depto, meta, accion, fuente, fondo, recurso));
                conectaBD.transaccionCommit(); //REVISADO
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

    public boolean getResultSQLDeleteAccionRequPlantilla(String origen, String ramo, int year, boolean isParaestatal) {
        boolean resultado = false;

        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                if (isParaestatal) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteAccionRePlantillaParaestatal(ramo, origen, year));
                } else if (!isParaestatal) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteAccionRePlantillaCentral(origen, year));
                }
                //conectaBD.transaccionCommit(); REVISADO
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

    public boolean getResultSQLInsertMetaAccionPlantilla(String ramo, String prg, String depto, int meta, int accion, String fuente, String fondo, String recurso, String tipoGasto, int year) {
        boolean resultado = false;

        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertMetaAccionPlantilla(year, ramo, prg, depto, meta, accion, fuente, fondo, recurso, tipoGasto));
                conectaBD.transaccionCommit(); // Revisado
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

    public boolean getResultSQLInsertPlantillaPersonal(int year, String ramo, String relLab, double cantidad) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertPlantillaEmpleado(year, ramo, relLab, cantidad));
                conectaBD.transaccionCommit(); //Revisado
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

    public boolean getResultSQLCountCodigoPlantilla(int year, String ramo, String programa,
            String depto) throws SQLException {
        int codigo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetContCodigoPlantilla(year, ramo, programa, depto));
                pstmt.setInt(1, year);
                pstmt.setString(2, ramo);
                pstmt.setString(3, programa);
                pstmt.setString(4, depto);
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            codigo = rsResult.getInt("CONT_COD");
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
        if (codigo > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Ppto getResultSQLCodigoPlantillaCompleto(int year, String ramo, String programa, String depto) throws SQLException {
        //int codigo = 0;
        Ppto codigo = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCodigoCompleto(year, ramo, programa, depto));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            codigo = new Ppto();
                            codigo.setYear(rsResult.getInt("YEAR"));
                            codigo.setRamo(rsResult.getString("RAMO"));
                            codigo.setPrg(rsResult.getString("PRG"));
                            codigo.setPrgConac(rsResult.getString("PRG_CONAC"));
                            codigo.setDepto(rsResult.getString("DEPTO"));
                            codigo.setMetaId(rsResult.getString("META"));
                            codigo.setProyectoId(rsResult.getString("PROYECTO"));
                            codigo.setTipoProy(rsResult.getString("TIPO_PROY"));
                            codigo.setFinalidad(rsResult.getString("FINALIDAD"));
                            codigo.setFuncion(rsResult.getString("FUNCION"));
                            codigo.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            codigo.setAccionId(rsResult.getString("ACCION"));
                            codigo.setDelegacionId(rsResult.getString("DELEGACION"));
                            codigo.setMunicipio(rsResult.getString("MUNICIPIO"));
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

    public List<Ppto> getResultSQLCodigoPlantillaParaBorrar(String ramo, String origen, int year) throws SQLException, Exception {
        List<Ppto> pptoList = new ArrayList<Ppto>();
        Ppto codigo = new Ppto();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                if (this.getResultSQLisParaestatal()) {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCodigoByRamoOrigen(ramo, origen, year));
                } else {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCodigoByRamoOrigenCntral(origen, year));
                }
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            codigo = new Ppto();
                            codigo.setYear(rsResult.getInt("YEAR"));
                            codigo.setRamo(rsResult.getString("RAMO"));
                            codigo.setPrg(rsResult.getString("PRG"));
                            codigo.setPrgConac(rsResult.getString("PRG_CONAC"));
                            codigo.setDepto(rsResult.getString("DEPTO"));
                            codigo.setMetaId(rsResult.getString("META"));
                            codigo.setProyectoId(rsResult.getString("PROY"));
                            codigo.setTipoProy(rsResult.getString("TIPO_PROY"));
                            codigo.setFinalidad(rsResult.getString("FINALIDAD"));
                            codigo.setFuncion(rsResult.getString("FUNCION"));
                            codigo.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            codigo.setAccionId(rsResult.getString("ACCION"));
                            codigo.setPartida(rsResult.getString("PARTIDA"));
                            codigo.setFuente(rsResult.getString("FUENTE"));
                            codigo.setFondo(rsResult.getString("FONDO"));
                            codigo.setRecurso(rsResult.getString("RECURSO"));
                            codigo.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            codigo.setDelegacionId(rsResult.getString("DELEGACION"));
                            codigo.setMunicipio(rsResult.getString("MUNICIPIO"));
                            codigo.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            codigo.setMes(rsResult.getInt("MES"));
                            codigo.setAsignado(rsResult.getDouble("CANTIDAD"));
                            pptoList.add(codigo);
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
        return pptoList;
    }

    public boolean deletePresupPlantilla(String origen, String ramo, int year) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                if (this.getResultSQLisParaestatal()) {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLGetDeletePresupPlantillaParas(origen, ramo, year));
                } else {
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLGetDeletePresupPlantillaCentral(origen, year));
                }
                //conectaBD.transaccionCommit(); //revisado
            } catch (Exception sql) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sql, new Throwable());
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

    public String getResultSQLInsertPresupPlantillaMasivo(List<Ppto> codigo) {
        boolean resultado = false;
        String mensaje = new String();
        QuerysBD query = new QuerysBD();
        Ppto codTem = null;
        if (conectaBD != null) {
            try {
                for (Ppto cod : codigo) {
                    codTem = cod;
                    resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertMasivoPresupPlantilla(cod.getOrigen(),
                            cod.getYear(), cod.getRamo(), cod.getDepto(), cod.getFinalidad(), cod.getFuncion(), cod.getSubfuncion(),
                            cod.getPrgConac(), cod.getPrg(), cod.getTipoProy(), cod.getProyectoId(), cod.getMetaId(), cod.getAccionId(),
                            cod.getPartida(), cod.getTipoGasto(), cod.getFuente(), cod.getFondo(), cod.getRecurso(), cod.getMunicipio(),
                            cod.getDelegacionId(), cod.getRelLaboral(), cod.getMes(), cod.getAsignado()));
                }
                if (resultado) {
                    mensaje = new String();
                } else {
                    mensaje = "No se cargaron el archivo en la tabla PESSUP_PLANTILLA";
                }
            } catch (Exception sql) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(sql, new Throwable());
                bitacora.grabaBitacora();
                mensaje = sql.getMessage();
                mensaje += "|" + codTem.getPrg() + "-" + codTem.getDepto() + "-" + codTem.getMetaId() + "-" + codTem.getPartida() + "-" + codTem.getFuente() + "-" + codTem.getFondo() + "-" + codTem.getRecurso() + "-" + codTem.getRelLaboral();
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

    public boolean getResultSQLInsertPresupuestoIngreso(int year, String ramo, String concepto, int pptoId, String descr, double ene,
            double feb, double mar, double abr, double may, double jun, double jul,
            double ago, double sep, double oct, double nov, double dic) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertPresupuestoIngreso(year, ramo,
                        concepto, pptoId, descr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic));
                conectaBD.transaccionCommit(); //Revisado
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

    public boolean getResultSQLUpdatePresupuestoIngreso(int year, String ramo, String concepto, int pptoId, String descr, double ene,
            double feb, double mar, double abr, double may, double jun, double jul,
            double ago, double sep, double oct, double nov, double dic) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdatePresupuestoIngreso(year, ramo,
                        concepto, pptoId, descr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic));
                conectaBD.transaccionCommit(); //revisado
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

    public boolean getResultSQLUpdatePlantillaPersonal(int year, String ramo, String relLaboral, double cantidad, String relLaboralAnt) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdatePlantillaPersonal(year, ramo, relLaboral, cantidad, relLaboralAnt));
                conectaBD.transaccionCommit();//revisado
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

    public boolean getResultSQLDeletePresupuestoIngreso(int year, String ramo, String concepto, int pptoId) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSLQDeletePresupuestoIngreso(year, ramo, concepto, pptoId));
                conectaBD.transaccionCommit();//revisado
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

    public boolean getResultSQLDeleteLineaAccion(int year, String ramo, String programa) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteLineaAccion(year, ramo, programa));
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

    public boolean getResultSQLInsertLineaAccion(int year, String ramo, String programa, String[] lineas) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertLineaAccion(year, ramo, programa, lineas));
                conectaBD.transaccionCommit(); //revisado
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

    public boolean getResultSQLDeletePlantillaPersonal(int year, String ramo, String relLaboral) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeletePlantillaPersonal(year, ramo, relLaboral));
                conectaBD.transaccionCommit();//revisado
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

    public boolean getResultSQLDeleteProyeccion(String ramo, int year) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDeleteProyeccion(ramo, year));
                conectaBD.transaccionCommit();//revisado
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

    public List<Meta> getResultSQLCatalogoMetaByRamoYear(String ramoId, int year) throws SQLException {
        List<Meta> metaList = new ArrayList<Meta>();
        Meta meta = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoMetaByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            meta = new Meta();
                            meta.setYear(rsResult.getInt("YEAR"));
                            meta.setRamo(rsResult.getString("RAMO"));
                            meta.setPrograma(rsResult.getString("PRG"));
                            meta.setMetId(rsResult.getString("META"));
                            meta.setMeta(rsResult.getString("DESCR"));
                            meta.setLineaPED(rsResult.getString("LINEA"));
                            meta.setFinalidad(rsResult.getString("FINALIDAD"));
                            meta.setFuncion(rsResult.getString("FUNCION"));
                            meta.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            meta.setPresupuestar(rsResult.getString("PRESUPUESTAR"));
                            meta.setAprobCongreso(rsResult.getString("APROB_CONGRESO"));
                            meta.setConvenio(rsResult.getString("CONVENIO"));
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
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return metaList;
    }

    public List<Ppto> getResultSQLGetGruposPresupPlantilla(String origen) throws SQLException {
        List<Ppto> presupList = new ArrayList<Ppto>();
        Ppto presup = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        Ppto ppant = null;
        boolean bNuevo = false;
        ArrayList arImportes = new ArrayList<Mes>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGruposPresupPlantilla(origen));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {

                            bNuevo = false;
                            presup = new Ppto();
                            presup.setYear(rsResult.getInt("YEAR"));
                            presup.setRamo(rsResult.getString("RAMO"));
                            presup.setDepto(rsResult.getString("DEPTO"));
                            presup.setFinalidad(rsResult.getString("FINALIDAD"));
                            presup.setFuncion(rsResult.getString("FUNCION"));
                            presup.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            presup.setPrgConac(rsResult.getString("PRG_CONAC"));
                            presup.setPrg(rsResult.getString("PRG"));
                            presup.setTipoProy(rsResult.getString("TIPO_PROY"));
                            presup.setProyecto(rsResult.getInt("PROY"));
                            presup.setMeta(rsResult.getInt("META"));
                            presup.setAccion(rsResult.getInt("ACCION"));
                            presup.setPartida(rsResult.getString("PARTIDA"));
                            presup.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            presup.setFondo(rsResult.getString("FONDO"));
                            presup.setFuente(rsResult.getString("FUENTE"));
                            presup.setRecurso(rsResult.getString("RECURSO"));
                            presup.setMunicipio(rsResult.getString("MUNICIPIO"));
                            presup.setDelegacion(rsResult.getInt("DELEGACION"));
                            presup.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            if (ppant == null) {
                                bNuevo = true;
                            } else if (ppant.getAccion() != presup.getAccion()
                                    || ppant.getDelegacion() != presup.getDelegacion()
                                    || !ppant.getDepto().equals(presup.getDepto())
                                    || !ppant.getFinalidad().equals(presup.getFinalidad())
                                    || !ppant.getFondo().equals(presup.getFondo())
                                    || !ppant.getFuente().equals(presup.getFuente())
                                    || !ppant.getFuncion().equals(presup.getFuncion())
                                    //  || ppant.getMes() != presup.getMes()
                                    || ppant.getMeta() != presup.getMeta()
                                    || !ppant.getMunicipio().equals(presup.getMunicipio())
                                    || !ppant.getPartida().equals(presup.getPartida())
                                    || !ppant.getPrg().equals(presup.getPrg())
                                    || !ppant.getPrgConac().equals(presup.getPrgConac())
                                    || ppant.getProyecto() != presup.getProyecto()
                                    || !ppant.getRamo().equals(presup.getRamo())
                                    || !ppant.getRecurso().equals(presup.getRecurso())
                                    || !ppant.getRelLaboral().equals(presup.getRelLaboral())
                                    || !ppant.getSubfuncion().equals(presup.getSubfuncion())
                                    || !ppant.getTipoGasto().equals(presup.getTipoGasto())
                                    || !ppant.getTipoProy().equals(presup.getTipoProy())) {
                                bNuevo = true;
                            }

                            if (bNuevo) {
                                if (ppant != null) {
                                    ppant.setdMeses(arImportes);
                                    presupList.add(ppant);
                                    arImportes = new ArrayList<Mes>();
                                }

                            }
                            Mes mDato = new Mes();
                            mDato.setiMes(rsResult.getInt("MES"));
                            mDato.setdImporte(rsResult.getDouble("CANTIDAD"));
                            arImportes.add(mDato);
                            ppant = new Ppto();
                            ppant.setYear(rsResult.getInt("YEAR"));
                            ppant.setRamo(rsResult.getString("RAMO"));
                            ppant.setDepto(rsResult.getString("DEPTO"));
                            ppant.setFinalidad(rsResult.getString("FINALIDAD"));
                            ppant.setFuncion(rsResult.getString("FUNCION"));
                            ppant.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            ppant.setPrgConac(rsResult.getString("PRG_CONAC"));
                            ppant.setPrg(rsResult.getString("PRG"));
                            ppant.setTipoProy(rsResult.getString("TIPO_PROY"));
                            ppant.setProyecto(rsResult.getInt("PROY"));
                            ppant.setMeta(rsResult.getInt("META"));
                            ppant.setAccion(rsResult.getInt("ACCION"));
                            ppant.setPartida(rsResult.getString("PARTIDA"));
                            ppant.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            ppant.setFondo(rsResult.getString("FONDO"));
                            ppant.setFuente(rsResult.getString("FUENTE"));
                            ppant.setRecurso(rsResult.getString("RECURSO"));
                            ppant.setMunicipio(rsResult.getString("MUNICIPIO"));
                            ppant.setDelegacion(rsResult.getInt("DELEGACION"));
                            ppant.setRelLaboral(rsResult.getString("REL_LABORAL"));
                        }
                        presup.setdMeses(arImportes);
                        presupList.add(presup);
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
        return presupList;
    }

    public String getResultSQLGetRolesPrg() throws SQLException {
        String rol = new String();
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
                            rol = rsResult.getString("ROL");
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
        return rol;
    }

    public List<Ppto> getResultSQLGetRequerimientoPresupPlantilla(Ppto pres) throws SQLException {
        List<Ppto> presupList = new ArrayList<Ppto>();
        Ppto presup = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRequerimientoPresupPlantilla(pres));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            presup = new Ppto();
                            presup.setYear(rsResult.getInt("YEAR"));
                            presup.setRamo(rsResult.getString("RAMO"));
                            presup.setDepto(rsResult.getString("DEPTO"));
                            presup.setFinalidad(rsResult.getString("FINALIDAD"));
                            presup.setFuncion(rsResult.getString("FUNCION"));
                            presup.setSubfuncion(rsResult.getString("SUBFUNCION"));
                            presup.setPrgConac(rsResult.getString("PRG_CONAC"));
                            presup.setPrg(rsResult.getString("PRG"));
                            presup.setTipoProy(rsResult.getString("TIPO_PROY"));
                            presup.setProyecto(rsResult.getInt("PROY"));
                            presup.setMeta(rsResult.getInt("META"));
                            presup.setAccion(rsResult.getInt("ACCION"));
                            presup.setPartida(rsResult.getString("PARTIDA"));
                            presup.setTipoGasto(rsResult.getString("TIPO_GASTO"));
                            presup.setFondo(rsResult.getString("FONDO"));
                            presup.setFuente(rsResult.getString("FUENTE"));
                            presup.setRecurso(rsResult.getString("RECURSO"));
                            presup.setMunicipio(rsResult.getString("MUNICIPIO"));
                            presup.setDelegacion(rsResult.getInt("DELEGACION"));
                            presup.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            presup.setMes(rsResult.getInt("MES"));
                            presup.setAsignado(rsResult.getDouble("CANTIDAD"));
                            presup.setOrigen(rsResult.getString("ORIGEN"));
                            presupList.add(presup);
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
        return presupList;
    }

    public List<Accion> getResultSQLCatalogoAccionByRamoYear(String ramoId, int year) throws SQLException {
        List<Accion> accionList = new ArrayList<Accion>();
        Accion accion = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoAccionByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accion = new Accion();
                            accion.setYear(rsResult.getInt("YEAR"));
                            accion.setRamo(rsResult.getString("RAMO"));
                            accion.setPrg(rsResult.getString("PRG"));
                            accion.setDeptoId(rsResult.getString("DEPTO"));
                            accion.setMetaId(rsResult.getString("META"));
                            accion.setAccId(rsResult.getString("ACCION"));
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
                this.desconectaDblink("SPP");
                pstmt.close();
                rsResult.close();
            }
        }
        return accionList;
    }

    public PlantillaPersonal getResultSQLGetPlatnillaPersonalById(int year, String ramo, String relLaboral) throws SQLException {
        PlantillaPersonal plantilla = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPlantillaPersonalById(year, ramo, relLaboral));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            plantilla = new PlantillaPersonal();
                            plantilla.setRelLaboral(rsResult.getString("REL_LABORAL"));
                            plantilla.setRelLaboralDescr(rsResult.getString("DESCR"));
                            plantilla.setCantidad(rsResult.getDouble("CANTIDAD"));
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
        return plantilla;
    }

    public List<AccionReq> getResultSQLCatalogoAccionReqByRamoYear(String ramoId, int year) throws SQLException {
        List<AccionReq> accionReqList = new ArrayList<AccionReq>();
        AccionReq accionReq = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCatalogoAccionReqByRamoYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accionReq = new AccionReq();
                            accionReq.setYear(rsResult.getInt("YEAR"));
                            accionReq.setRamo(rsResult.getString("RAMO"));
                            accionReq.setPrg(rsResult.getString("PRG"));
                            accionReq.setDepto(rsResult.getString("DEPTO"));
                            accionReq.setMeta(rsResult.getInt("META"));
                            accionReq.setAccion(rsResult.getInt("ACCION"));
                            accionReq.setRequerimiento(rsResult.getInt("REQUERIMIENTO"));
                            accionReq.setDescr(rsResult.getString("DESCR"));
                            accionReq.setFuente(rsResult.getInt("FUENTE"));
                            accionReq.setTipoGasto(rsResult.getInt("TIPO_GASTO"));
                            accionReq.setPartida(rsResult.getString("PARTIDA"));
                            accionReq.setRelacionLaboral(rsResult.getInt("REL_LABORAL"));
                            accionReq.setCantidad(rsResult.getInt("CANTIDAD"));
                            accionReq.setCostoUnitario(rsResult.getInt("COSTO_UNITARIO"));
                            accionReq.setCostoAnual(rsResult.getInt("COSTO_ANUAL"));
                            accionReq.setEne(rsResult.getInt("ENE"));
                            accionReq.setFeb(rsResult.getInt("FEB"));
                            accionReq.setMar(rsResult.getInt("MAR"));
                            accionReq.setAbr(rsResult.getInt("ABR"));
                            accionReq.setMay(rsResult.getInt("MAY"));
                            accionReq.setJun(rsResult.getInt("JUN"));
                            accionReq.setJul(rsResult.getInt("JUL"));
                            accionReq.setAgo(rsResult.getInt("AGO"));
                            accionReq.setSep(rsResult.getInt("SEP"));
                            accionReq.setOct(rsResult.getInt("OCT"));
                            accionReq.setNov(rsResult.getInt("NOV"));
                            accionReq.setDic(rsResult.getInt("DIC"));
                            accionReq.setArticulo(rsResult.getInt("ARTICULO"));
                            accionReq.setCantidadOrg(rsResult.getInt("CANTIDAD_ORG"));
                            accionReq.setCostoUnitarioOrg(rsResult.getInt("COSTO_UNITARIO_ORG"));
                            accionReq.setArchivo(rsResult.getInt("ARCHIVO"));
                            accionReq.setJustificado(rsResult.getString("JUSTIFICACION"));
                            accionReq.setFondo(rsResult.getInt("FONDO"));
                            accionReq.setRecurso(rsResult.getInt("RECURSO"));
                            accionReqList.add(accionReq);
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
        return accionReqList;
    }

    public List<LineaSectorial> getRestulLineaSectorialByEstrategia(String ramoId, String programaId, int proyectoId, int year, String estrategia, String tipoProy) throws SQLException {
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        LineaSectorial lineaSectorial;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaSectorialEstrategia(ramoId, programaId, proyectoId, year, estrategia, tipoProy));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            lineaSectorial = new LineaSectorial();
                            lineaSectorial.setLineaId(rsResult.getString("LINEA_SECTORIAL"));
                            lineaSectorial.setLineaSectorial(rsResult.getString("DESCR"));
                            lineaSectorial.setEstrategia(rsResult.getString("ESTRATEGIA"));
                            lineaSectorialList.add(lineaSectorial);
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
        return lineaSectorialList;
    }

    public List<LineaSectorial> getResultSQLLineaSectorialByYearEstrategia(int year, String estrategia) throws SQLException {
        List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
        LineaSectorial lineaSectorial;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaSectorialByYearEstrategia(year, estrategia));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            lineaSectorial = new LineaSectorial();
                            lineaSectorial.setLineaId(rsResult.getString("LINEA_SECTORIAL"));
                            lineaSectorial.setLineaSectorial(rsResult.getString("DESCR"));
                            lineaSectorialList.add(lineaSectorial);
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
        return lineaSectorialList;
    }

    public int getResultSQLContLineasPed(int year, String ramoId, String programaId, int proyectoId, String linea, String tipoProy) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int valida = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetContLineasPED(year, ramoId, programaId, proyectoId, linea, tipoProy));
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

    /*cambios para avance poa*/
    public List<Meta> getResultGetMetasAvancePoa(String ramoId, String programaId, int proyectoId, int year, String tipoProy) throws
            SQLException {
        List<Meta> metaList = new ArrayList<Meta>();
        Meta meta;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetMetasAvance(ramoId, programaId, proyectoId, year,
                        tipoProy));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            meta = new Meta();
                            meta.setMetaId(rsResult.getInt("META"));
                            meta.setMeta(rsResult.getString("DESCR"));
                            meta.setRamo(rsResult.getString("RAMO"));
                            meta.setPrograma(rsResult.getString("PRG"));
                            meta.setProyecto(rsResult.getInt("PROY"));
                            meta.setLineaPED(rsResult.getString("LINEA"));
                            meta.setCalculo(rsResult.getString("CALCULO"));
                            meta.setTipoProyecto(rsResult.getString("TIPO_PROY"));
                            meta.setAutorizacion(rsResult.getString("PROCESO_AUTORIZACION"));
                            meta.setClaveMedida(rsResult.getInt("CVE_MEDIDA"));
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

    public String getResultSQLgetValidaPeriodoDefinido(int year, String ramo) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String cadena = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaPeriodoDefinido());
                if (pstmt != null) {
                    pstmt.setString(1, ramo);
                    pstmt.setInt(2, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cadena += rsResult.getString("PRG_PERIODO");
                            cadena += ",";
                            cadena += rsResult.getString("CIERRE_PRG_AVA");
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
        return cadena;
    }

    public boolean getSQLValidaAccesoRolAvancePoa(int rol) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        boolean acceso = false;
        int rol_a = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaAccesoRolAvancePoa());
                if (pstmt != null) {
                    pstmt.setInt(1, rol);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            rol_a = rsResult.getInt("ROL");
                        }
                    }

                    if (rol_a > 0) {
                        acceso = true;
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
        return acceso;
    }

    public List<AvancePoaMeta> getProgramacionAvancePoa(String ramo, int meta, int year) {
        List<AvancePoaMeta> avancePoaMetaList = new ArrayList<AvancePoaMeta>();
        AvancePoaMeta avancePoaMeta;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        String cadena = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLProgramacionAvancePoa());
                if (pstmt != null) {
                    pstmt.setString(1, ramo);
                    pstmt.setInt(2, meta);
                    pstmt.setInt(3, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            avancePoaMeta = new AvancePoaMeta();
                            avancePoaMeta.setAvance_periodo(rsResult.getInt("AVANCE_PERIODO"));
                            avancePoaMeta.setEstimacion_periodo(rsResult.getInt("ESTIMACION_PERIODO"));
                            avancePoaMeta.setAvance_valor(rsResult.getString("AVANCE_VALOR"));
                            avancePoaMeta.setEstimacion_valor(rsResult.getString("ESTIMACION_VALOR"));
                            avancePoaMeta.setAvance_meta(rsResult.getInt("AVANCE_META"));
                            avancePoaMeta.setAvance_ramo(rsResult.getString("AVANCE_RAMO"));
                            avancePoaMeta.setAvance_year(rsResult.getInt("AVANCE_YEAR"));
                            avancePoaMeta.setCalculo(rsResult.getString("CALCULO"));
                            avancePoaMeta.setActivo(rsResult.getString("ACTIVO"));
                            avancePoaMeta.setParametro_prg_periodo(rsResult.getInt("PARAMETRO_PRG_PERIODO"));
                            avancePoaMeta.setAvance_observa(rsResult.getString("OBSERVA"));
                            avancePoaMeta.setAvance_observa_anual(rsResult.getString("OBSERVA_ANUAL"));
                            avancePoaMetaList.add(avancePoaMeta);
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
        return avancePoaMetaList;
    }

    public List<Evaluacion> getEvaluacionAvancePoa(int year) {
        List<Evaluacion> evaluacionList = new ArrayList<Evaluacion>();
        Evaluacion evaluacion;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLEvaluacionAvancePoa());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            evaluacion = new Evaluacion();
                            evaluacion.setNumMeses(rsResult.getInt("NUMEVAL"));
                            evaluacion.setTipoEvaluacion(rsResult.getString("DESCREVAL"));
                            evaluacionList.add(evaluacion);
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
        return evaluacionList;
    }

    public List<AvancePoaMetaObservaciones> getObtieneObservacionMetaAvancePoa(String ramo, int meta, int periodo, int year) {
        List<AvancePoaMetaObservaciones> MetaObservList = new ArrayList<AvancePoaMetaObservaciones>();
        AvancePoaMetaObservaciones metaObserv;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLObtieneObservacionMetaAvancePoa());
                if (pstmt != null) {
                    pstmt.setString(1, ramo);
                    pstmt.setInt(2, meta);
                    pstmt.setInt(3, periodo);
                    pstmt.setInt(4, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            metaObserv = new AvancePoaMetaObservaciones();
                            metaObserv.setMeta(rsResult.getInt("META"));
                            metaObserv.setRamo(rsResult.getString("RAMO"));
                            metaObserv.setYear(rsResult.getInt("YEAR"));
                            metaObserv.setPerido(rsResult.getInt("PERIODO"));
                            metaObserv.setObserva(rsResult.getString("OBSERVA"));
                            metaObserv.setObserva_anual(rsResult.getString("OBSERVA_ANUAL"));
                            MetaObservList.add(metaObserv);
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
        return MetaObservList;
    }

    public int getPeriodoParametroPrgAvancePoa() {
        int periodo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLPeriodoParametroPrgAvancePoa());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            periodo = rsResult.getInt("PERIODO");
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
        return periodo;
    }

    public int getSQLEvaluacionEstimacionAvancePoa(int periodo) {
        int evaluacion = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLEvaluacionEstimacionAvancePoa());
                if (pstmt != null) {
                    pstmt.setInt(1, periodo);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            evaluacion = rsResult.getInt("EVALUACION");
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
        return evaluacion;
    }

    public int getMaxPeriodoEstimacionAvancePoa(int eval) {
        int maxPeriodo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMaxPeriodoEstimacionAvancePoa());
                if (pstmt != null) {
                    pstmt.setInt(1, eval);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            maxPeriodo = rsResult.getInt("PERIODO");
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
        return maxPeriodo;
    }

    public String getIsParaestatalAvancePoa() {
        String paraestatal = "";
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsParaestatalAvancePoa());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            paraestatal = rsResult.getString("PARAESTATAL");
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
        return paraestatal;
    }

    public int getExisteMetaAvancePoa(int meta, String ramo, int year, int periodo) {
        int existe = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExisteMetaAvancePoa());
                if (pstmt != null) {
                    pstmt.setInt(1, meta);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, year);
                    pstmt.setInt(4, periodo);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            existe = rsResult.getInt("EXISTE");
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
        return existe;
    }

    public int getInsertaMetaAvancePoa(int meta, String ramo, int year, int periodo, Double valor, String observacion, String observacionAnual) {
        int realizado = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLInsertaMetaAvancePoa());
                if (pstmt != null) {
                    pstmt.setInt(1, meta);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, year);
                    pstmt.setInt(4, periodo);
                    pstmt.setDouble(5, valor);
                    pstmt.setString(6, observacion);
                    pstmt.setString(7, observacionAnual);
                    realizado = pstmt.executeUpdate();
                    /* if (rsResult != null) {
                     while(rsResult.next()) {
                     realizado=rsResult.getInt("EXISTE");
                     }
                     }*/
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return realizado;
    }

    public int getActualizaMetaAvancePoa(int meta, String ramo, int year, int periodo, Double valor, String observacion, String observacionAnual) {
        int realizado = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLActualizaMetaAvancePoa());
                if (pstmt != null) {
                    pstmt.setDouble(1, valor);
                    pstmt.setString(2, observacion);
                    pstmt.setString(3, observacionAnual);
                    pstmt.setInt(4, meta);
                    pstmt.setString(5, ramo);
                    pstmt.setInt(6, year);
                    pstmt.setInt(7, periodo);
                    realizado = pstmt.executeUpdate();
                    /* if (rsResult != null) {
                     while(rsResult.next()) {
                     realizado=rsResult.getInt("EXISTE");
                     }
                     }*/
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }
        return realizado;
    }

    public String getValidaCierreAccionAvancePoa(String ramo, int year) {
        String cierre = "";
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaCierreAccionAvancePoa());
                if (pstmt != null) {
                    pstmt.setString(1, ramo);
                    pstmt.setInt(2, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cierre = rsResult.getString("CIERRE_ACCION");
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
        return cierre;
    }

    public List<AccionesAvancePoa> getObtieneAccionesAvancePoa(int meta, String ramo, int year) {
        String cierre = "";
        ResultSet rsResult = null;
        List<AccionesAvancePoa> accionesAvanceList = new ArrayList<AccionesAvancePoa>();
        AccionesAvancePoa accionesAvancePoa;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLobtieneListadoAccionesAvancePoa());
                if (pstmt != null) {
                    pstmt.setInt(1, meta);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, year);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accionesAvancePoa = new AccionesAvancePoa();
                            accionesAvancePoa.setMETA(rsResult.getInt("META"));
                            accionesAvancePoa.setRAMO(rsResult.getString("RAMO"));
                            accionesAvancePoa.setYEAR(rsResult.getInt("YEAR"));
                            accionesAvancePoa.setACCION(rsResult.getInt("ACCION"));
                            accionesAvancePoa.setDESCR(rsResult.getString("DESCR"));
                            accionesAvancePoa.setUE(rsResult.getString("UE"));
                            accionesAvancePoa.setUE_DESCR(rsResult.getString("UE_DESCR"));
                            accionesAvancePoa.setIMPORTE(rsResult.getFloat("IMPORTE"));
                            accionesAvancePoa.setEJERCIDO(rsResult.getFloat("EJERCIDO"));
                            accionesAvancePoa.setASIGNADO(rsResult.getFloat("ASIGNADO"));
                            accionesAvancePoa.setUNIDAD_MEDIDA(rsResult.getString("UNIDAD_MEDIDA"));
                            accionesAvancePoa.setCALCULO(rsResult.getString("CALCULO"));
                            accionesAvanceList.add(accionesAvancePoa);
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
        return accionesAvanceList;
    }

    public List<AvancePoaAcciones> getAvanceAccionesAvancePoa(int meta, String ramo, int year, int accion) {
        String cierre = "";
        ResultSet rsResult = null;
        List<AvancePoaAcciones> avancePoaAccionesList = new ArrayList<AvancePoaAcciones>();
        AvancePoaAcciones avancePoaAcciones;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLAvanceAccionesAvancePoa());
                if (pstmt != null) {
                    pstmt.setInt(1, meta);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, year);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            avancePoaAcciones = new AvancePoaAcciones();
                            avancePoaAcciones.setMETA(rsResult.getInt("META"));
                            avancePoaAcciones.setRAMO(rsResult.getString("RAMO"));
                            avancePoaAcciones.setYEAR(rsResult.getInt("YEAR"));
                            avancePoaAcciones.setACCION(rsResult.getInt("ACCION"));
                            avancePoaAcciones.setMES(rsResult.getInt("MES"));
                            avancePoaAcciones.setDESCR_MES(rsResult.getString("DESCR_MES"));
                            avancePoaAcciones.setREALIZADO(rsResult.getString("REALIZADO"));
                            avancePoaAcciones.setPROGRAMADO(rsResult.getString("PROGRAMADO"));
                            avancePoaAcciones.setIMPT_ASIG_XMES(rsResult.getFloat("IMPT_ASIG_XMES"));
                            avancePoaAcciones.setIMPT_EJER_XMES(rsResult.getFloat("IMPT_EJER_XMES"));
                            avancePoaAcciones.setOBSERVACIONES(rsResult.getString("OBSERVACIONES"));
                            avancePoaAcciones.setPERIODO(rsResult.getInt("PERIODO"));
                            avancePoaAccionesList.add(avancePoaAcciones);
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
        return avancePoaAccionesList;
    }

    public int getExisteAvanceAccionesAvancePoa(int meta, String ramo, int year, int accion) {
        int existe = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExisteAvanceAccionesAvancePoa());
                if (pstmt != null) {
                    pstmt.setInt(1, meta);
                    pstmt.setString(2, ramo);
                    pstmt.setInt(3, year);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            existe = Integer.parseInt(rsResult.getString("EXISTE"));
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
        return existe;
    }

    public int getUpdateAvanceAccionesAvancePoa(int meta, String ramo, int year, int accion, int mes, String realizado, float ampRed, float ejercido, String observ) {
        int res = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLUpdateAvanceAccionesAvancePoa());
                if (pstmt != null) {
                    pstmt.setString(1, realizado);
                    pstmt.setString(2, observ);
                    pstmt.setString(3, ramo);
                    pstmt.setInt(4, year);
                    pstmt.setInt(5, mes);
                    pstmt.setInt(6, meta);
                    pstmt.setInt(7, accion);

                    res = pstmt.executeUpdate();

                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
            }
        }

        return res;
    }

    public List<Linea> getResultGetLineaSectorialByLineaMeta(String estrategia, int year) throws SQLException {
        List<Linea> lineaList = new ArrayList<Linea>();
        Linea linea;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetLineaSectorialByLineaMeta(estrategia, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            linea = new Linea();
                            linea.setLineaId(rsResult.getString("LINEA_SECTORIAL"));
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

    public boolean getResultSQLActualizaCaratula(Caratula caratula) throws SQLException, Exception {

        boolean bResultado = false;
        ResultSet rs = null;
        boolean bExiste = false;
        if (getConectaBD().getConConexion() != null) {
            PreparedStatement pstmt = null;
            try {
                String strSql = "";
                QuerysBD query = new QuerysBD();
                Caratula carTemp = this.getResultSQLExisteCaratula(caratula);
                if (carTemp != null) {
                    caratula.setsIdCaratula(carTemp.getsIdCaratula());
                    if (caratula.getsIdCaratula() > 0) {
                        bExiste = true;
                    }
                }
                int iRes = 0;
                if (bExiste) {
                    strSql = query.getUpdateCaratula();
                    pstmt = getConectaBD().getConConexion().prepareStatement(strSql);
                    if (pstmt != null) {
                        pstmt.setString(1, caratula.getsNumModPres());
                        pstmt.setString(2, caratula.getsNumodProg());
                        pstmt.setInt(3, caratula.getiTipoSesion());
                        pstmt.setString(4, caratula.getsFechaRevision());
                        pstmt.setString(5, caratula.getsNumeroSesion());
                        pstmt.setString(6, String.valueOf(caratula.getiStatus()));
                        pstmt.setInt(7, caratula.getIntTipoModificacion());
                        pstmt.setInt(8, caratula.getiYear());
                        pstmt.setString(9, caratula.getsRamo());
                        pstmt.setString(10, String.valueOf(caratula.getsIdCaratula()));
                        iRes = pstmt.executeUpdate();
                    }
                } else {
                    strSql = query.getInsertaCaratula();
                    pstmt = getConectaBD().getConConexion().prepareStatement(strSql);
                    if (pstmt != null) {
                        pstmt.setInt(1, caratula.getiYear());
                        pstmt.setString(2, caratula.getsRamo());
                        caratula.setsIdCaratula(this.getSecuenciaMovimientosCaratulas(false, caratula.getiYear()));
                        pstmt.setString(3, String.valueOf(caratula.getsIdCaratula()));
                        pstmt.setString(4, caratula.getsFechaRevision());
                        pstmt.setString(5, caratula.getsNumeroSesion());
                        pstmt.setInt(6, caratula.getiTipoSesion());
                        pstmt.setString(7, caratula.getsNumodProg());
                        pstmt.setString(8, caratula.getsNumModPres());
                        pstmt.setString(9, caratula.getsSysClave());
                        pstmt.setString(10, caratula.getsAppLogin());
                        pstmt.setString(11, caratula.getiStatus());
                        pstmt.setInt(12, caratula.getIntTipoModificacion());
                        iRes = pstmt.executeUpdate();
                    }
                }
                if (iRes > 0) {
                    this.commit();
                    return true;
                } else {
                    this.rollback();
                    return false;
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

    public Caratula getResultSQLExisteCaratula(Caratula caratula) throws Exception {
        String strSql = "";
        PreparedStatement pstmt;
        QuerysBD query = new QuerysBD();
        ResultSet rs = null;
        Boolean bExiste = false;
        String sCaratula = "";
        Caratula car = null;
        strSql = query.existeCaratula();
        pstmt = getConectaBD().getConConexion().prepareStatement(strSql);
        if (pstmt != null) {
            pstmt.setLong(1, caratula.getsIdCaratula());
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    car = new Caratula();
                    car.setsIdCaratula(Long.parseLong(rs.getString(1)));
                    car.setiStatus(rs.getString(2));
                    if (car.getsIdCaratula() > 0) {
                        bExiste = true;
                    }
                }
                rs.close();
                pstmt.close();
            }
        }
        return car;
    }

    public long getSecuenciaMovimientosCaratulas(boolean isActual, int year) {
        String movimientos = new String();
        String strYear = new String();
        int folio = 0;
        QuerysBD bd = new QuerysBD();
        PreparedStatement pstmt = null;
        try {
            strYear = String.valueOf(year);
            movimientos = bd.getSQLgetSequenceMovimientoOficioCaratula(isActual);
            pstmt = getConectaBD().getConConexion().prepareStatement(movimientos);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    folio = Integer.parseInt(rs.getString(1));
                }
            }
            rs.close();
            pstmt.close();

        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return folio;
    }

    public Caratula getResultSQLCargaCaratula(Caratula caratula) throws Exception {
        String strSql = "";
        PreparedStatement pstmt;
        QuerysBD query = new QuerysBD();
        ResultSet rs = null;
        Boolean bExiste = false;
        String sCaratula = "";
        Caratula car = null;
        strSql = query.cargaCaratula();
        pstmt = getConectaBD().getConConexion().prepareStatement(strSql);
        if (pstmt != null) {
            pstmt.setInt(1, caratula.getiYear());
            pstmt.setString(2, caratula.getsRamo());
            pstmt.setString(3, caratula.getsFechaRevision());
            pstmt.setString(4, caratula.getsNumeroSesion());
            pstmt.setInt(5, caratula.getiTipoSesion());
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    car = new Caratula();
                    car.setiYear(rs.getInt(1));
                    car.setsRamo(rs.getString(2));
                    car.setsIdCaratula(Long.parseLong(rs.getString(3)));
                    car.setsFechaRegistro(rs.getString(4));
                    car.setsNumeroSesion(rs.getString(5));
                    car.setiTipoSesion(rs.getInt(6));
                    car.setsNumModPres(rs.getString(7));
                    car.setsNumodProg(rs.getString(8));
                    car.setsSysClave(rs.getString(9));
                    car.setsAppLogin(rs.getString(10));
                    car.setiStatus(rs.getString(11));
                }
                rs.close();
                pstmt.close();
            }
        }
        return car;
    }

    public ArrayList<Caratula> getResultSQLObtieneCaratulas(String sYear, String sRamos, boolean bFiltraEstatusAbiertas, int tipoCaratula, boolean isNormativo) throws Exception {
        String strSql = "";
        PreparedStatement pstmt;
        QuerysBD query = new QuerysBD();
        ResultSet rs = null;
        Boolean bExiste = false;
        String sCaratula = "";
        Caratula car = null;
        strSql = query.obtieneCaratulas(bFiltraEstatusAbiertas, tipoCaratula, isNormativo);
        ArrayList<Caratula> arCaratulas = null;
        pstmt = getConectaBD().getConConexion().prepareStatement(strSql);
        if (pstmt != null) {
            pstmt.setInt(1, Integer.parseInt(sYear));
            pstmt.setInt(2, Integer.parseInt(sYear));
            if (!isNormativo) {
                pstmt.setString(3, sRamos);
            }
            rs = pstmt.executeQuery();
            if (rs != null) {
                arCaratulas = new ArrayList();
                while (rs.next()) {
                    car = new Caratula();
                    car.setiYear(rs.getInt(1));
                    car.setsRamo(rs.getString(2));
                    car.setsIdCaratula(Long.parseLong(rs.getString(3)));
                    car.setsFechaRevision(rs.getString(4));
                    car.setsNumeroSesion(rs.getString(5));
                    car.setiTipoSesion(rs.getInt(6));
                    car.setsNumModPres(rs.getString(7));
                    car.setsNumodProg(rs.getString(8));
                    car.setsSysClave(rs.getString(9));
                    car.setsAppLogin(rs.getString(10));
                    car.setiStatus(rs.getString(11));
                    car.setsFechaRegistro(rs.getString(12));
                    car.setsTipoSesionDescr(rs.getString(13));
                    car.setsNumSesionDescr(rs.getString(14));
                    if (rs.getString(7) == null) {
                        car.setsModPresupDescr("No Aplica");
                    } else {
                        car.setsModPresupDescr(rs.getString(15));
                    }
                    if (rs.getString(8) == null) {
                        car.setsModProgDescr("No Aplica");
                    } else {
                        car.setsModProgDescr(rs.getString(16));
                    }
                    car.setsDescr(rs.getString(17));
                    car.setIntTipoModificacion(rs.getInt(18));
                    car.setStrTipoModificacionDescr(rs.getString(19));
                    car.setiYearSesion(rs.getInt(20));
                    arCaratulas.add(car);
                }
                rs.close();
                pstmt.close();
            }
        }
        return arCaratulas;
    }

    public List<Caratula> getResultSQLObtieneCaratulasEvaluacion(int year) throws Exception {
        String strSql = "";
        PreparedStatement pstmt;
        QuerysBD query = new QuerysBD();
        ResultSet rs = null;
        Boolean bExiste = false;
        String sCaratula = "";
        Caratula car = null;
        strSql = query.getCaratulasEvaluacion();
        List<Caratula> caratulaList = null;
        try {
            pstmt = getConectaBD().getConConexion().prepareStatement(strSql);
            if (pstmt != null) {
                pstmt.setInt(1, year);
                rs = pstmt.executeQuery();
                if (rs != null) {
                    caratulaList = new ArrayList();
                    while (rs.next()) {
                        car = new Caratula();
                        car.setiYear(rs.getInt("YEAR"));
                        car.setsIdCaratula(Long.parseLong(rs.getString("ID_CARATULA")));
                        car.setsFechaRevision(rs.getString("FECHA_SESION"));
                        car.setsNumeroSesion(rs.getString("NUMERO_SESION"));
                        car.setsTipoSesionDescr(rs.getString("TIPOSESIONDESCR"));
                        car.setsNumSesionDescr(rs.getString("NUMSESIONDESCR"));
                        car.setEvaluado(rs.getString("EVALUACION"));
                        car.setsRamo(rs.getString("RDESCR"));
                        if (rs.getString("MODPROGDESCR").trim().isEmpty()) {
                            car.setsModPresupDescr("No Aplica");
                        } else {
                            car.setsModPresupDescr(rs.getString("MODPROGDESCR"));
                        }
                        if (rs.getString("MODPROGDESCR").trim().isEmpty()) {
                            car.setsModProgDescr("No Aplica");
                        } else {
                            car.setsModProgDescr(rs.getString("MODPROGDESCR"));
                        }
                        caratulaList.add(car);
                    }
                    rs.close();
                    pstmt.close();
                }
            }
        } catch (SQLException sqle) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sqle, new Throwable());
            bitacora.grabaBitacora();
        }
        return caratulaList;
    }

    public boolean insertaTraspaso(String sYear, String sRamo, long lOficio, String sTipoOficio, String sIdTraspaso, String sUsuario) throws SQLException, Exception {

        boolean bResultado = false;
        ResultSet rs = null;
        boolean bExiste = false;
        String strSql = "";
        int iRes = 0;
        QuerysBD quer = new QuerysBD();
        if (getConectaBD().getConConexion() != null) {
            PreparedStatement pstmt = null;
            try {

                strSql = quer.insertaTraspaso();
                pstmt = getConectaBD().getConConexion().prepareStatement(strSql);
                if (pstmt != null) {
                    pstmt.setString(1, sYear);
                    pstmt.setString(2, sRamo);
                    pstmt.setString(3, sIdTraspaso);
                    pstmt.setLong(4, lOficio);
                    pstmt.setString(5, sTipoOficio);
                    pstmt.setString(6, sUsuario);
                    iRes = pstmt.executeUpdate();
                }

                if (iRes > 0) {
                    this.commit();
                    return true;
                } else {
                    this.rollback();
                    return false;
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

    public int equivalenciaOficio(String sTipoOficio) {
        if (sTipoOficio.equals("A")) {
            return Utilerias.AMPLIACIONES;
        }
        if (sTipoOficio.equals("T")) {
            return Utilerias.TRANSFERENCIAS;
        }
        if (sTipoOficio.equals("R")) {
            return Utilerias.REPROGRAMACION;
        }
        if (sTipoOficio.equals("C")) {
            return Utilerias.RECALENDARIZACION;
        }
        return 0;
    }

    public boolean extraeTablas(String strRuta, String sYear, String sRamo, String sIdTraspaso) throws SQLException, Exception {

        boolean bResultado = false;
        ResultSet rs = null;
        boolean bExiste = false;
        boolean bInformacion;
        String strSql = "";
        int iRes = 0;
        int cont = 0;
        QuerysBD quer = new QuerysBD();
        ArrayList<String> arGenerados = new ArrayList();
        ArrayList<String> arDatos = new ArrayList();
        String sArchivos[] = {"25 AMPLIACIONES", "26 AMPCAL", "27 TRANSFERENCIAS", "28 TRANSFREC", "16 ACCION", /*"ACCION_ESTIMACION",*/
            "29 TRANSFRECCAL", "23 TIPOFICIO", "24 OFICONS", "15 META", "22 MOVOFICIOS", "31 BITMOVTOS", "10 RAMO", "11 PRG", "13 DEPTO",
            "04 FINALIDAD", "05 FUNCION", "06 SUBFUNCION", "32 MEDIDA", "07 PRG_CONAC", "14 PROYECTO", "17 PARTIDA", "18 TIPO GASTO",
            "19 FUENTE", "20 FONDO", "21 RECURSO",
            "01 MUNICIPIO", "02 DELEGACION", "03 REL LABORAL", "09 ACTIVIDAD", "08 CATALOGO_PROYECTO", "12 RAMO_PROGRAMA", "30 RECALENDARIZACION"};
        int iRowParam = 0;
        String sMovoficios = "";
        String sQueryUltimo = "";
        String sEtapas = "";
        if (getConectaBD().getConConexion() != null) {
            PreparedStatement pstmt = null;
            try {
                int iOcurrencias = 0;
                arDatos = quer.obtieneInformacionParaestatal();
                sEtapas = "GENERA ARCHIVOS";
                for (int iRow = 0; iRow < arDatos.size(); iRow++) {
                    //System.out.println(iRow);
                    bInformacion = false;
                    strSql = arDatos.get(iRow);
                    sQueryUltimo = strSql;
                    iRowParam = 1;
                    iOcurrencias = StringUtils.countMatches(strSql, "?");
                    pstmt = getConectaBD().getConConexion().prepareStatement(strSql);
                    for (int iVuelta = 0; iVuelta < iOcurrencias / 3; iVuelta++) {//una vuelta ya esta afuera                        
                        pstmt.setInt(iRowParam, Integer.parseInt(sYear));
                        iRowParam++;
                        pstmt.setString(iRowParam, sRamo);
                        iRowParam++;
                        pstmt.setString(iRowParam, sIdTraspaso);
                        iRowParam++;
                    }
                    rs = pstmt.executeQuery();
                    cont++;
                    if (rs != null) {

                        while (rs.next()) {
                            if (!bInformacion) {
                                arGenerados.add(sArchivos[iRow]);
                            }
                            bInformacion = true;
                            if (sArchivos[iRow].equals("22 MOVOFICIOS")) {
                                sMovoficios = "";
                                sMovoficios += rs.getLong(1) + "|";
                                if (rs.getString(2) != null)//fecha elab
                                {
                                    sMovoficios += rs.getString(2);
                                }
                                sMovoficios += "|";
                                if (rs.getString(3) != null)//applogin
                                {
                                    sMovoficios += rs.getString(3);
                                }
                                sMovoficios += "|";
                                if (rs.getString(4) != null)//tipomov
                                {
                                    sMovoficios += rs.getString(4);
                                }
                                sMovoficios += "|";
                                if (rs.getString(5) != null)//status
                                {
                                    sMovoficios += rs.getString(5);
                                }
                                sMovoficios += "|";
                                if (rs.getString(6) != null)//fecppto
                                {
                                    sMovoficios += rs.getString(6);
                                }
                                sMovoficios += "|";
                                if (rs.getString(7) != null)//justificacion
                                {
                                    sMovoficios += rs.getString(7);
                                }
                                sMovoficios += "|";
                                if (rs.getString(8) != null)//OBS_RECHAZO
                                {
                                    sMovoficios += rs.getString(8);
                                }
                                sMovoficios += "|";
                                if (rs.getString(9) != null)//CAPTURA_ESPECIAL
                                {
                                    sMovoficios += rs.getString(9);
                                }

                                Utilerias.escribeLinea2(strRuta + "/", sArchivos[iRow] + sIdTraspaso, "txt", sMovoficios);
                            } else {
                                Utilerias.escribeLinea2(strRuta + "/", sArchivos[iRow] + sIdTraspaso, "txt", rs.getString(1));
                            }

                        }
                    }
                    rs.close();
                    pstmt.close();

                }
                sEtapas = "GENERA GENERA ZIP";

                FileOutputStream fos = new FileOutputStream(strRuta + "/EstructuraPresupuestal" + sIdTraspaso + ".zip");
                ZipOutputStream zos = new ZipOutputStream(fos);
                Archivo arFiles = new Archivo();
                sEtapas = "AGREGA ZIP";
                for (int iRow = 0; iRow < arGenerados.size(); iRow++) {

                    arFiles.addToZipFile(strRuta + "/", arGenerados.get(iRow) + sIdTraspaso + ".txt", zos);
                    File fTemp = new File(strRuta + "/" + arGenerados.get(iRow) + sIdTraspaso + ".txt");
                    fTemp.delete();
                }
                sEtapas = "TERMINA ZIP";
                zos.close();
                fos.close();

            } catch (Exception ex) {
                grabaBitacoraError(ex, new Throwable());
                throw new SQLException("OCURRIOR UN ERROR AL GENERAR EL ARCHIVO : " + sEtapas + " - " + sQueryUltimo + ex.getMessage());
            } finally {
                pstmt.close();
            }

        }

        return bResultado;
    }

    public Caratula getResultSQLCaratulaByYearIdRamoIdCaratula(String year, String idRamo, long idCaratula) throws Exception {
        String strSql = "";
        PreparedStatement pstmt;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        Boolean bExiste = false;
        String sCaratula = "";
        Caratula objMovCaratula = null;
        strSql = query.getQueryBDCaratulaByYearIdRamoIdCaratula();
        pstmt = getConectaBD().getConConexion().prepareStatement(strSql);
        if (pstmt != null) {
            pstmt.setString(1, year);
            pstmt.setString(2, idRamo);
            pstmt.setLong(3, idCaratula);
            rsResult = pstmt.executeQuery();
            if (rsResult != null) {
                while (rsResult.next()) {
                    objMovCaratula = new Caratula();
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
                    objMovCaratula.setIntTipoModificacion(rsResult.getInt(18));
                    objMovCaratula.setStrTipoModificacionDescr(rsResult.getString(19));
                    objMovCaratula.setiYearSesion(rsResult.getInt(20));
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
                rsResult.close();
            }
            pstmt.close();
        }

        return objMovCaratula;
    }

    public boolean getResultEncriptarContrasenia() throws SQLException {
        boolean encriptada = false;
        String resultado = "";
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLEncriptarContrasenia());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            resultado = rsResult.getString("ENCRIPTAR_CONTRASENIAS");
                        }
                    }
                }
                if (resultado.equals("S")) {
                    encriptada = true;
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
        return encriptada;
    }

    public int getContEstimacionMetaAvancePoa(String ramo, int meta, int year) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLEstimacionMetaAvancePoa());
                if (pstmt != null) {
                    pstmt.setString(1, ramo);
                    pstmt.setInt(2, year);
                    pstmt.setInt(3, meta);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont = rsResult.getInt("CONT");
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
        return cont;
    }

    public int getContEstimacionAccionAvancePoa(String ramo, int meta, int accion, int year) {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int cont = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLEstimacionAccionAvancePoa());
                if (pstmt != null) {
                    pstmt.setString(1, ramo);
                    pstmt.setInt(2, year);
                    pstmt.setInt(3, meta);
                    pstmt.setInt(4, accion);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont = rsResult.getInt("CONT");
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
        return cont;
    }

    public boolean getResultSQLValidarContrasenaAct(String strUsuario, String strContrasenaAct) {

        QuerysBD qryQuery = new QuerysBD();
        Vector vecEstatusUsuario = new Vector();
        Vector vecDatosHashTemp = new Vector();
        Vector vecDatosUsuario = new Vector();
        Vector vecEstatusTemp = new Vector();
        Vector vecDatosHash = new Vector();
        String strPassGenerado = new String();
        String strKeyEncriptBD = new String();
        String strPassBD = new String();
        boolean bEncriptarContrasena = false;
        boolean bContrasenaCorrecta = false;

        try {

            bEncriptarContrasena = getResultEncriptarContrasenia();
            vecEstatusUsuario = conectaBD.ejecutaSQLConsulta(qryQuery.getSQLConsultaEstatusUsuario(strUsuario));
            if (vecEstatusUsuario.size() > 0) {
                vecEstatusTemp = (Vector) vecEstatusUsuario.get(0);
                strPassBD = ((String) vecEstatusTemp.get(1)).trim();
                strKeyEncriptBD = (String) vecEstatusTemp.get(2);
                if (bEncriptarContrasena) {
                    vecDatosHash = conectaBD.ejecutaSQLConsulta(qryQuery.getSQLConsultaComparativoHash(strContrasenaAct, strKeyEncriptBD));
                    if (vecDatosHash.size() > 0) {
                        vecDatosHashTemp = (Vector) vecDatosHash.get(0);
                        strPassGenerado = (String) vecDatosHashTemp.get(0);
                        if (strPassBD.trim().equals(strPassGenerado.trim())) {
                            bContrasenaCorrecta = true;
                        }
                    }
                } else {
                    if (strPassBD.trim().equals(strContrasenaAct)) {
                        bContrasenaCorrecta = true;
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

        return bContrasenaCorrecta;
    }

    public boolean getResultSQLCambiarContrasena(String strUsuario, String strContrasenaAct, String strContrasenaNva) {

        QuerysBD qryQuery = new QuerysBD();
        CallableStatement cstmt = null;
        Vector vecEstatusUsuario = new Vector();
        Vector vecEstatusTemp = new Vector();
        String strKeyEncriptGenerado = new String();
        String strPassGenerado = new String();
        String strKeyEncriptBD = new String();
        String strPassBD = new String();
        boolean bEncriptarContrasena = false;
        boolean bContrasenaCambiada = false;

        try {

            bEncriptarContrasena = getResultEncriptarContrasenia();

            if (bEncriptarContrasena) {
                cstmt = getConectaBD().prepareCall(qryQuery.getQuerysBDGeneraNuevoHash());
                cstmt.registerOutParameter(1, OracleTypes.VARCHAR);
                cstmt.registerOutParameter(3, OracleTypes.VARCHAR);
                cstmt.setString(2, strContrasenaNva);
                cstmt.setString(3, strKeyEncriptGenerado);

                if (cstmt != null) {
                    cstmt.execute();
                    strPassGenerado = cstmt.getString(1);
                    strKeyEncriptGenerado = cstmt.getString(3);
                    vecEstatusUsuario = conectaBD.ejecutaSQLConsulta(qryQuery.getSQLConsultaEstatusUsuario(strUsuario));

                    if (vecEstatusUsuario.size() > 0) {
                        vecEstatusTemp = (Vector) vecEstatusUsuario.get(0);
                        strPassBD = (String) vecEstatusTemp.get(1);
                        strKeyEncriptBD = (String) vecEstatusTemp.get(2);
                        bContrasenaCambiada = conectaBD.ejecutaSQLActualizacion(qryQuery.getQuerysBDCambiaContrasena(strUsuario, strPassBD, strPassGenerado, strKeyEncriptGenerado));
                    }
                }
            } else {
                bContrasenaCambiada = conectaBD.ejecutaSQLActualizacion(qryQuery.getQuerysBDCambiaContrasena(strUsuario, strContrasenaAct, strContrasenaNva, ""));
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

        return bContrasenaCambiada;
    }

    public String getSQLMunicipioUsuario(String strUsuario) {
        boolean resultado = false;
        ResultSet rsResult = null;
        String usuario = new String();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLMunicipioUsuario(strUsuario));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            usuario = rsResult.getString("MPO");
                        }
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            } finally {
                try {
                    pstmt.close();
                    rsResult.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ResultSQL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return usuario;
    }

    public boolean getResultSQLValidaRamoAvancePoa(String year, int ramoId) {
        boolean resultado = false;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        int valida = 0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidacionRamoAvancePoa(year, ramoId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            valida = rsResult.getInt("VALIDA");
                        }
                    }
                    if (valida == 1) {
                        resultado = true;
                    }
                }
            } catch (SQLException ex) {
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, new Throwable());
                bitacora.grabaBitacora();
                resultado = false;
            }
        }
        return resultado;
    }

    public List<CapturaPresupuestoIngreso> getResultSQLGetCaratulaPresupIngByCaratulaPresupRamConcep(int year, String ramo, String concepto, long caratula) throws Exception {
        ResultSet rsResultado = null;
        List<CapturaPresupuestoIngreso> caratulaPresupuestoIngresoList = new ArrayList<CapturaPresupuestoIngreso>();
        CapturaPresupuestoIngreso caratulaPresupuestoIngreso = new CapturaPresupuestoIngreso();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCaratulaPresupuestoIngresoByCaratulaRamoConcepto(year, ramo, concepto, caratula));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        caratulaPresupuestoIngreso = new CapturaPresupuestoIngreso();
                        caratulaPresupuestoIngreso.setIdCaratula(rsResultado.getLong("ID_CARATULA"));
                        caratulaPresupuestoIngreso.setYear(rsResultado.getInt("YEAR"));
                        caratulaPresupuestoIngreso.setRamo(rsResultado.getString("RAMO"));
                        caratulaPresupuestoIngreso.setConcepto(rsResultado.getString("CONCEPTO"));
                        caratulaPresupuestoIngreso.setConsec(rsResultado.getInt("CONSEC"));
                        caratulaPresupuestoIngreso.setDescr(rsResultado.getString("DESCR"));
                        caratulaPresupuestoIngreso.setTipoMov(rsResultado.getString("TIPOMOV"));
                        caratulaPresupuestoIngreso.setEne(rsResultado.getDouble("ENE"));
                        caratulaPresupuestoIngreso.setFeb(rsResultado.getDouble("FEB"));
                        caratulaPresupuestoIngreso.setMar(rsResultado.getDouble("MZO"));
                        caratulaPresupuestoIngreso.setAbr(rsResultado.getDouble("ABR"));
                        caratulaPresupuestoIngreso.setMay(rsResultado.getDouble("MAY"));
                        caratulaPresupuestoIngreso.setJun(rsResultado.getDouble("JUN"));
                        caratulaPresupuestoIngreso.setJul(rsResultado.getDouble("JUL"));
                        caratulaPresupuestoIngreso.setAgo(rsResultado.getDouble("AGO"));
                        caratulaPresupuestoIngreso.setSep(rsResultado.getDouble("SEP"));
                        caratulaPresupuestoIngreso.setOct(rsResultado.getDouble("OCT"));
                        caratulaPresupuestoIngreso.setNov(rsResultado.getDouble("NOV"));
                        caratulaPresupuestoIngreso.setDic(rsResultado.getDouble("DIC"));
                        caratulaPresupuestoIngresoList.add(caratulaPresupuestoIngreso);
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
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return caratulaPresupuestoIngresoList;
    }

    public CapturaPresupuestoIngreso getResultSQLGetCapturaPresupuestoIngresoById(int year, String ramo, String concepto, int subConcepto, long caratula) throws SQLException {
        QuerysBD query = new QuerysBD();
        CapturaPresupuestoIngreso caratulaPresupuestoIngreso = new CapturaPresupuestoIngreso();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCapturaPresupuestoIngresoById(year, ramo, concepto, subConcepto, caratula));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            caratulaPresupuestoIngreso = new CapturaPresupuestoIngreso();
                            caratulaPresupuestoIngreso.setIdCaratula(rsResult.getLong("ID_CARATULA"));
                            caratulaPresupuestoIngreso.setYear(rsResult.getInt("YEAR"));
                            caratulaPresupuestoIngreso.setRamo(rsResult.getString("RAMO"));
                            caratulaPresupuestoIngreso.setConcepto(rsResult.getString("CONCEPTO"));
                            caratulaPresupuestoIngreso.setConsec(rsResult.getInt("CONSEC"));
                            caratulaPresupuestoIngreso.setDescr(rsResult.getString("DESCR"));
                            caratulaPresupuestoIngreso.setTipoMov(rsResult.getString("TIPOMOV"));
                            caratulaPresupuestoIngreso.setEne(rsResult.getDouble("ENE"));
                            caratulaPresupuestoIngreso.setFeb(rsResult.getDouble("FEB"));
                            caratulaPresupuestoIngreso.setMar(rsResult.getDouble("MZO"));
                            caratulaPresupuestoIngreso.setAbr(rsResult.getDouble("ABR"));
                            caratulaPresupuestoIngreso.setMay(rsResult.getDouble("MAY"));
                            caratulaPresupuestoIngreso.setJun(rsResult.getDouble("JUN"));
                            caratulaPresupuestoIngreso.setJul(rsResult.getDouble("JUL"));
                            caratulaPresupuestoIngreso.setAgo(rsResult.getDouble("AGO"));
                            caratulaPresupuestoIngreso.setSep(rsResult.getDouble("SEP"));
                            caratulaPresupuestoIngreso.setOct(rsResult.getDouble("OCT"));
                            caratulaPresupuestoIngreso.setNov(rsResult.getDouble("NOV"));
                            caratulaPresupuestoIngreso.setDic(rsResult.getDouble("DIC"));
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
        return caratulaPresupuestoIngreso;
    }

    public boolean getResultSQLInsertSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto, int subConcepto, String subConceptoDescr, double ene, double feb, double mar, double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic, String tipoMov) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertSubConceptoCapturaIngreso(caratula, year, ramo, concepto, subConcepto, subConceptoDescr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic, tipoMov));
                conectaBD.transaccionCommit(); //Revisado
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

    public boolean getResultSQLUpdateSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto, int subConcepto, String subConceptoDescr, double ene, double feb, double mar, double abr, double may, double jun, double jul, double ago, double sep, double oct, double nov, double dic, String tipoMov) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateSubConceptoCapturaIngreso(caratula, year, ramo, concepto, subConcepto, subConceptoDescr, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic, tipoMov));
                conectaBD.transaccionCommit(); //revisado
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

    public boolean getResultSQLDeleteSubConceptoCapturaIngreso(long caratula, int year, String ramo, String concepto, int subConcepto) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSLQDeleteSubConceptoCapturaIngreso(caratula, year, ramo, concepto, subConcepto));
                conectaBD.transaccionCommit();//revisado
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

    public List<Estimacion> getResultSQLGetHistEstimacion(int year, String ramoId, int metaId, int folio) throws Exception {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        ResultSet rsResult = null;
        Estimacion estimacion;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetHistEstimacion(year, ramoId, metaId, folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        estimacion = new Estimacion();
                        estimacion.setMeta(rsResult.getInt("META"));
                        estimacion.setRamo(rsResult.getString("RAMO"));
                        estimacion.setPeriodo(rsResult.getInt("PERIODO"));
                        estimacion.setValor(rsResult.getDouble("VALOR"));
                        estimacionList.add(estimacion);
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
        return estimacionList;
    }

    public List<Estimacion> getResultSQLGetHistEstimacion(int year, String ramoId, int metaId, int accionId, int folio) throws Exception {
        List<Estimacion> estimacionList = new ArrayList<Estimacion>();
        ResultSet rsResult = null;
        Estimacion estimacion;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetHistEstimacion(year, ramoId, metaId, accionId, folio));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        estimacion = new Estimacion();
                        estimacion.setPeriodo(rsResult.getInt("PERIODO"));
                        estimacion.setValor(rsResult.getDouble("VALOR"));
                        estimacionList.add(estimacion);
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
        return estimacionList;
    }

    public List<RevisionCaratula> getResultSQLGetListRevisionesCaratulaByRamoCaratulaYear(String ramo, long caratula, int year, int yearSesion, int tipoModificacion, int tipoSesion) throws Exception {
        List<RevisionCaratula> revisionesCaratulaList = new ArrayList<RevisionCaratula>();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            RevisionCaratula revisionCaratula = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetListRevisionesCaratulaByRamoCaratulaYear(ramo, caratula, year, yearSesion, tipoModificacion, tipoSesion));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            revisionCaratula = new RevisionCaratula();
                            revisionCaratula.setRevision(rsResult.getString("REVISION"));
                            revisionCaratula.setDescr_Corta(rsResult.getString("DESCR_CORTA"));
                            revisionCaratula.setDescr(rsResult.getString("DESCR"));
                            revisionCaratula.setLibre_Num_Session(rsResult.getBoolean("LIBRE_NUM_SESION"));
                            revisionCaratula.setLibre_Mod_Presup(rsResult.getBoolean("LIBRE_MOD_PRESUP"));
                            revisionCaratula.setLibre_Mod_Prog(rsResult.getBoolean("LIBRE_MOD_PROG"));
                            revisionCaratula.setSelected_Num_Session(rsResult.getBoolean("SELECTED_NUM_SESION"));
                            revisionCaratula.setSelected_Mod_Presup(rsResult.getBoolean("SELECTED_MOD_PRESUP"));
                            revisionCaratula.setSelected_Mod_Prog(rsResult.getBoolean("SELECTED_MOD_PROG"));
                            revisionesCaratulaList.add(revisionCaratula);
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
        return revisionesCaratulaList;
    }

    public Partida getResultSQLGetPartidaByIdPartidaYear(String idPartida, int year) throws SQLException {
        Partida partida = null;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();

        if (conectaBD != null) {

            PreparedStatement pstmt = null;

            try {

                pstmt = conectaBD.getConConexion().prepareStatement(query.getQuerysBDGetPartidaByIdPartidaYear(idPartida, year));

                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            partida = new Partida();
                            partida.setPartidaId(rsResult.getString("PARTIDA"));
                            partida.setPartida(rsResult.getString("DESCR"));
                            partida.setArticuloPoa(rsResult.getString("ARTICULO_POA"));
                            partida.setArticuloSip(rsResult.getString("ARTICULO_SIP"));
                            partida.setJustifReq(rsResult.getInt("JUSTIF_REQ"));
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
        return partida;
    }

    public List<Articulo> getResultSQLArticulosSipByYearPartida(int year, String partida) throws Exception {
        List<Articulo> articuloList = new ArrayList<Articulo>();
        Articulo articulo;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetArticulosSipByYearPartida(year, partida));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        articulo = new Articulo();
                        articulo.setArticuloId(rsResult.getString("ARTICULO"));
                        articulo.setGpogto(rsResult.getInt("GPOGTO"));
                        articulo.setSubgpo(rsResult.getInt("SUBGPO"));
                        articulo.setArticulo(rsResult.getString("DESCR"));
                        articulo.setCosto(rsResult.getDouble("COSTO"));
                        articuloList.add(articulo);
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
        return articuloList;
    }

    public int getResultSQLisArticuloSipPartida(int year, String partida) throws Exception {
        int countArticulo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsArticuloSipPartida(year, partida));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        countArticulo = rsResult.getInt("CONT");
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
        return countArticulo;
    }

    public String getResultSQLGetArticuloSipDescr(String partida, int articulo, int gpogto, int subgpo) throws SQLException {
        String strDescr = new String();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetArticuloSipDescr(partida, articulo, gpogto, subgpo));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            strDescr = rsResult.getString("DESCR");
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
        return strDescr;
    }

    public boolean getResultIsPlantillaByRamoProgramaDeptoMetaAccionYear(String ramoId, String programaId, String deptoId, int metaId, int accionId, int year) throws SQLException {
        boolean bResultado = false;
        ResultSet rsResult = null;
        int cont = 0;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsPlantillaByRamoProgramaDeptoMetaAccionYear(ramoId, programaId, deptoId, metaId, accionId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont = rsResult.getInt("CONT");
                        }
                    }
                }

                if (cont > 0) {
                    bResultado = true;
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
        return bResultado;
    }

    public boolean getResultExistPlantillaByRamoProgramaDeptoMetaAccionYear(String ramoId, String programaId, String deptoId, int metaId, int accionId, int year) throws SQLException {
        boolean bResultado = false;
        ResultSet rsResult = null;
        int cont = 0;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExistPlantillaByRamoProgramaDeptoMetaAccionYear(ramoId, programaId, deptoId, metaId, accionId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            cont = rsResult.getInt("CONT");
                        }
                    }
                }

                if (cont > 0) {
                    bResultado = true;
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
        return bResultado;
    }

    public CapturaPresupuestoIngreso getResultSQLPresupuestoIngresoById(int year, String ramo, String concepto, int subConcepto) throws SQLException {
        QuerysBD query = new QuerysBD();
        CapturaPresupuestoIngreso caratulaPresupuestoIngreso = new CapturaPresupuestoIngreso();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLPresupuestoIngresoById(year, ramo, concepto, subConcepto));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            caratulaPresupuestoIngreso = new CapturaPresupuestoIngreso();
                            caratulaPresupuestoIngreso.setYear(rsResult.getInt("YEAR"));
                            caratulaPresupuestoIngreso.setRamo(rsResult.getString("RAMO"));
                            caratulaPresupuestoIngreso.setConcepto(rsResult.getString("CONCEPTO"));
                            caratulaPresupuestoIngreso.setConsec(rsResult.getInt("CONSEC"));
                            caratulaPresupuestoIngreso.setDescr(rsResult.getString("DESCR"));
                            caratulaPresupuestoIngreso.setTipoMov(rsResult.getString("TIPOMOV"));
                            caratulaPresupuestoIngreso.setEne(rsResult.getDouble("ENE"));
                            caratulaPresupuestoIngreso.setFeb(rsResult.getDouble("FEB"));
                            caratulaPresupuestoIngreso.setMar(rsResult.getDouble("MZO"));
                            caratulaPresupuestoIngreso.setAbr(rsResult.getDouble("ABR"));
                            caratulaPresupuestoIngreso.setMay(rsResult.getDouble("MAY"));
                            caratulaPresupuestoIngreso.setJun(rsResult.getDouble("JUN"));
                            caratulaPresupuestoIngreso.setJul(rsResult.getDouble("JUL"));
                            caratulaPresupuestoIngreso.setAgo(rsResult.getDouble("AGO"));
                            caratulaPresupuestoIngreso.setSep(rsResult.getDouble("SEP"));
                            caratulaPresupuestoIngreso.setOct(rsResult.getDouble("OCT"));
                            caratulaPresupuestoIngreso.setNov(rsResult.getDouble("NOV"));
                            caratulaPresupuestoIngreso.setDic(rsResult.getDouble("DIC"));
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
        return caratulaPresupuestoIngreso;
    }

    public List<PresupuestoIngreso> getResultSQLGetSubconceptosPresupuestoIngreso(int year, String ramo, String concepto, long caratula) throws Exception {
        ResultSet rsResultado = null;
        List<PresupuestoIngreso> presupuestoList = new ArrayList<PresupuestoIngreso>();
        PresupuestoIngreso presupuesto = new PresupuestoIngreso();
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetSubconceptosPresupuestoIngresoByRamoConcepto(year, ramo, concepto, caratula));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        presupuesto = new PresupuestoIngreso();
                        presupuesto.setPresupuestoIngresoId(rsResultado.getString("CONSEC"));
                        presupuesto.setPresupuestoIngreso(rsResultado.getString("DESCR"));
                        presupuesto.setEne(rsResultado.getDouble("ENE"));
                        presupuesto.setFeb(rsResultado.getDouble("FEB"));
                        presupuesto.setMar(rsResultado.getDouble("MZO"));
                        presupuesto.setAbr(rsResultado.getDouble("ABR"));
                        presupuesto.setMay(rsResultado.getDouble("MAY"));
                        presupuesto.setJun(rsResultado.getDouble("JUN"));
                        presupuesto.setJul(rsResultado.getDouble("JUL"));
                        presupuesto.setAgo(rsResultado.getDouble("AGO"));
                        presupuesto.setSep(rsResultado.getDouble("SEP"));
                        presupuesto.setOct(rsResultado.getDouble("OCT"));
                        presupuesto.setNov(rsResultado.getDouble("NOV"));
                        presupuesto.setDic(rsResultado.getDouble("DIC"));
                        presupuestoList.add(presupuesto);
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
                rsResultado.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return presupuestoList;
    }

    public Ramo getResultGetRamoByRamoIdYear(String ramoId, int year) throws SQLException {
        Ramo ramo = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamoByRamoIdYear(ramoId, year));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            ramo = new Ramo();
                            ramo.setRamo(rsResult.getString("RAMO"));
                            ramo.setRamoDescr(rsResult.getString("DESCR"));
                            if (rsResult.getString("ABREVIATURA") != null) {
                                ramo.setAbreviatura(rsResult.getString("ABREVIATURA"));
                            } else {
                                ramo.setAbreviatura(rsResult.getString("DESCR"));
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
        return ramo;
    }

    public boolean getResultgetParametroReporteCierre() throws SQLException {
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        String parametro = new String();
        boolean resultado = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetParametroReporteCierre());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            parametro = rsResult.getString("REPORTE_CIERRE");
                        }
                    }
                }
                if (parametro.equals("S")) {
                    resultado = true;
                } else {
                    resultado = false;
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

    public List<EstatusMovReporte> getResultSQLGetEstatusMovReporte() throws SQLException {
        List<EstatusMovReporte> estatusList = new ArrayList<EstatusMovReporte>();
        EstatusMovReporte estatus;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLgetEstatusMovReporte());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            estatus = new EstatusMovReporte();
                            estatus.setEstatusMovId(rsResult.getString("ESTATUS"));
                            estatus.setEstatusMov(rsResult.getString("DESCR"));
                            estatus.setOrden(rsResult.getInt("ORDEN"));
                            estatusList.add(estatus);
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
        return estatusList;
    }

    public CapturaPresupuestoIngreso getResultSQLGetCalendarizacionIngresoPlusMod(int year, String ramo, String concepto, int subConcepto, long caratula) throws SQLException {
        QuerysBD query = new QuerysBD();
        CapturaPresupuestoIngreso caratulaPresupuestoIngreso = new CapturaPresupuestoIngreso();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetCalendarizacionIngresoPlusMod(year, ramo, concepto, subConcepto, caratula));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            caratulaPresupuestoIngreso = new CapturaPresupuestoIngreso();
                            caratulaPresupuestoIngreso.setYear(rsResult.getInt("YEAR"));
                            caratulaPresupuestoIngreso.setRamo(rsResult.getString("RAMO"));
                            caratulaPresupuestoIngreso.setConcepto(rsResult.getString("CONCEPTO"));
                            caratulaPresupuestoIngreso.setConsec(rsResult.getInt("CONSEC"));
                            caratulaPresupuestoIngreso.setEne(rsResult.getDouble("ENE"));
                            caratulaPresupuestoIngreso.setFeb(rsResult.getDouble("FEB"));
                            caratulaPresupuestoIngreso.setMar(rsResult.getDouble("MZO"));
                            caratulaPresupuestoIngreso.setAbr(rsResult.getDouble("ABR"));
                            caratulaPresupuestoIngreso.setMay(rsResult.getDouble("MAY"));
                            caratulaPresupuestoIngreso.setJun(rsResult.getDouble("JUN"));
                            caratulaPresupuestoIngreso.setJul(rsResult.getDouble("JUL"));
                            caratulaPresupuestoIngreso.setAgo(rsResult.getDouble("AGO"));
                            caratulaPresupuestoIngreso.setSep(rsResult.getDouble("SEP"));
                            caratulaPresupuestoIngreso.setOct(rsResult.getDouble("OCT"));
                            caratulaPresupuestoIngreso.setNov(rsResult.getDouble("NOV"));
                            caratulaPresupuestoIngreso.setDic(rsResult.getDouble("DIC"));
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
        return caratulaPresupuestoIngreso;
    }

    public boolean getResultSQLInsertStatusIngresoModificado(long caratula, int year, String ramo, String concepto, String status) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLInsertStatusIngresoModificado(caratula, year, ramo, concepto, status));
                conectaBD.transaccionCommit(); //Revisado
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

    public int getResultExisteStatusModificacionIngreso(long caratula, int year, String ramo, String concepto) throws SQLException {
        int countRow = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLExisteStatusModificacionIngreso(caratula, year, ramo, concepto));
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

    public boolean getResultSQLUpdateStatusModificacionIngreso(long caratula, int year, String ramo, String concepto, String status) {
        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLUpdateStatusIngresoModificado(caratula, year, ramo, concepto, status));
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

    public String getResultGetStatusModificacionIngreso(long caratula, int year, String ramo, String concepto) throws SQLException {
        String status = new String();
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetStatusModificacionIngreso(caratula, year, ramo, concepto));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            status = rsResult.getString("STATUS");
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
        return status;
    }

    public ArrayList<TipoSesion> getCaratulasTipoSesiones() {
        ResultSet rsResult = null;
        CodigoPPTO codigo = null;
        int cont = 0;
        TipoSesion sesiones = new TipoSesion();
        QuerysBD query = new QuerysBD();
        ArrayList<TipoSesion> lSesiones = null;
        PreparedStatement pstmt = null;
        if (conectaBD != null) {

            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlCaratulasTipoSesiones());
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

    public boolean getResultSQLDisponibleTipoSesion(int year, String ramo, String numSesion, String tipoSesion, long caratula) throws Exception {
        boolean disponible = false;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlDisponibleTipoSesion(year, ramo, numSesion, tipoSesion, caratula));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            disponible = rsResult.getBoolean("DISPONIBLE");
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
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return disponible;
    }

    public List<Articulo> getResultSQLGetArticulosByPartida(int year, String partida) throws Exception {
        List<Articulo> articuloList = new ArrayList<Articulo>();
        Articulo articulo;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetArticulosByPartida(year, partida));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        articulo = new Articulo();
                        articulo.setArticuloId(rsResult.getString("ARTICULO"));
                        articulo.setArticulo(rsResult.getString("DESCR"));
                        articulo.setPartida(rsResult.getInt("PARTIDA"));
                        articulo.setSistema(rsResult.getString("SIST"));
                        articuloList.add(articulo);
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
        return articuloList;
    }

    public int getResultSQLisArticuloPartidas(int year, String partida) throws Exception {
        int countArticulo = 0;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLIsArticuloPartidas(year, partida));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    while (rsResult.next()) {
                        countArticulo = rsResult.getInt("CONT");
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
        return countArticulo;
    }

    public String getResultSQLGetArticuloDescrByArticulo(int year, String partida, String articuloId) throws SQLException {
        String strDescr = new String();
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetArticuloDescrByArticulo(year, partida, articuloId));
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            strDescr = rsResult.getString("DESCR");
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
        return strDescr;
    }

    // modificar query
    public List<AccionFuenteFinanciamiento> getResultSQLGetAccionesByFuenteFinanciamiento(int year, String ramo, String fuente, String fondo, String recurso) throws SQLException {
        List<AccionFuenteFinanciamiento> accionFFList = new ArrayList<AccionFuenteFinanciamiento>();
        AccionFuenteFinanciamiento accionFF;
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetAccionesByFuenteFinanciamiento());
                if (pstmt != null) {
                    pstmt.setInt(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, fuente);
                    pstmt.setString(4, fondo);
                    pstmt.setString(5, recurso);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            accionFF = new AccionFuenteFinanciamiento();
                            accionFF.setMeta(rsResult.getInt("META"));
                            accionFF.setDescrMeta(rsResult.getString("M_DESCR"));
                            accionFF.setAccion(rsResult.getInt("ACCION"));
                            accionFF.setDescrAccion(rsResult.getString("A_DESCR"));
                            accionFF.setTotal(rsResult.getDouble("COSTO"));
                            accionFFList.add(accionFF);
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
        return accionFFList;
    }

    public ArrayList<IndicadorGeneralSei> getResultSQLObtieneIndicadoresGeneralesSei(String sYear) throws Exception {

        ResultSet rs = null;
        PreparedStatement pstmt;
        QuerysBD query = new QuerysBD();
        IndicadorGeneralSei indicadorGeneralSei = null;
        ArrayList<IndicadorGeneralSei> arrIndicadoresGeneralesSei = null;
        String strSql = "";
        strSql = query.getQueryBDObtieneIndicadoresGeneralesSei(sYear);
        pstmt = getConectaBD().getConConexion().prepareStatement(strSql);

        if (pstmt != null) {
            rs = pstmt.executeQuery();
            if (rs != null) {
                arrIndicadoresGeneralesSei = new ArrayList();
                while (rs.next()) {
                    indicadorGeneralSei = new IndicadorGeneralSei();
                    indicadorGeneralSei.setClaveIndicador(rs.getString(1));
                    indicadorGeneralSei.setNombreIndicador(rs.getString(2));
                    indicadorGeneralSei.setYear(rs.getString(3));
                    arrIndicadoresGeneralesSei.add(indicadorGeneralSei);
                }
                rs.close();
                pstmt.close();
            }
        }

        return arrIndicadoresGeneralesSei;

    }

    public ArrayList<SectorRamo> getResultSQLObtieneSectorRamos(String sYear, String sClaveIndicador) throws Exception {

        ResultSet rs = null;
        PreparedStatement pstmt;
        SectorRamo sectorRamo = null;
        QuerysBD query = new QuerysBD();
        ArrayList<SectorRamo> arrSectoresRamos = null;
        String strSql = "";
        strSql = query.getQueryBDObtieneSectorRamos(sYear, sClaveIndicador);
        pstmt = getConectaBD().getConConexion().prepareStatement(strSql);

        if (pstmt != null) {
            rs = pstmt.executeQuery();
            if (rs != null) {
                arrSectoresRamos = new ArrayList();
                while (rs.next()) {
                    sectorRamo = new SectorRamo();
                    sectorRamo.setYear(rs.getString(1));
                    sectorRamo.setRamo(rs.getString(2));
                    sectorRamo.setRamoDescr(rs.getString(3));
                    arrSectoresRamos.add(sectorRamo);
                }
                rs.close();
                pstmt.close();
            }
        }

        return arrSectoresRamos;

    }

    public List<IndicadorSectorRamo> getResultSQLObtieneIndicadorSectorRamos(String sYear, String sClaveIndicador, String sRamo) throws Exception {

        ResultSet rs = null;
        PreparedStatement pstmt;
        QuerysBD query = new QuerysBD();
        IndicadorSectorRamo indicadorSectorRamo = null;
        List<IndicadorSectorRamo> arrIndicadorSectorRamos = null;
        String strSql = "";
        strSql = query.getQueryBDObtieneIndicadorSectorRamos(sYear, sClaveIndicador, sRamo);
        pstmt = getConectaBD().getConConexion().prepareStatement(strSql);

        if (pstmt != null) {
            rs = pstmt.executeQuery();
            if (rs != null) {
                arrIndicadorSectorRamos = new ArrayList();
                while (rs.next()) {
                    indicadorSectorRamo = new IndicadorSectorRamo();
                    indicadorSectorRamo.setYear(rs.getString(1));
                    indicadorSectorRamo.setClaveIndicador(rs.getString(2));
                    indicadorSectorRamo.setNombreIndicador(rs.getString(3));
                    indicadorSectorRamo.setRamo(rs.getString(4));
                    indicadorSectorRamo.setRamoDescr(rs.getString(5));
                    indicadorSectorRamo.setPrg(rs.getString(6));
                    indicadorSectorRamo.setPrgDescr(rs.getString(7));
                    arrIndicadorSectorRamos.add(indicadorSectorRamo);
                }
                rs.close();
                pstmt.close();
            }
        }

        return arrIndicadorSectorRamos;

    }

    public boolean getResultSQLDesligarIndicadorRamoSector(String sYear, String sClaveIndicador, String sRamo, String sPrograma) throws Exception {

        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLDesligarIndicadorRamoSector(sYear, sClaveIndicador, sRamo, sPrograma));
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

    public boolean getResultSQLLigarIndicadorRamoSector(String sYear, String sClaveIndicador, String sRamo, String sPrograma) throws Exception {

        boolean resultado = false;
        QuerysBD query = new QuerysBD();
        if (conectaBD != null) {
            try {
                resultado = conectaBD.ejecutaSQLActualizacion(query.getSQLLigarIndicadorRamoSector(sYear, sClaveIndicador, sRamo, sPrograma));
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

    public List<Ramo> getResultRamosAsociadosOficioByOficio(int oficio, int year) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        List<Ramo> ramoList = new ArrayList<Ramo>();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Ramo ramo;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetRamosAsociadosOficioByOficio(oficio, year));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        ramo = new Ramo();
                        ramo.setRamo(rsResultado.getString("RAMO"));
                        ramo.setRamoDescr(rsResultado.getString("DESCR"));
                        ramoList.add(ramo);
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
        return ramoList;
    }

    public double getResultSQLGetImporteMovto(int oficio, String tipoMov) throws Exception {
        ResultSet rsResultado = null;
        QuerysBD query = new QuerysBD();
        double importe = 0.0;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            Ramo ramo;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetImporteMovto(oficio, tipoMov));
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    while (rsResultado.next()) {
                        importe = rsResultado.getDouble("IMPORTE");
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
        return importe;
    }

    public List<Ponderado> getResultGetPonderado() throws SQLException {
        GrupoPoblacional grupoPoblacional = null;

        List<Ponderado> ponderadoList = new ArrayList<Ponderado>();
        Ponderado ponderado = null;
        QuerysBD query = new QuerysBD();
        ResultSet rsResult = null;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetPonderado());
                if (pstmt != null) {
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        ponderadoList = new ArrayList<Ponderado>();
                        while (rsResult.next()) {
                            ponderado = new Ponderado();
                            ponderado.setPonderadoId(rsResult.getString("PONDERADO"));
                            ponderado.setPonderadoDescr(rsResult.getString("DESCR"));
                            ponderadoList.add(ponderado);
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
        return ponderadoList;
    }

    public boolean getResultSQLhasProcesoEspecual(String appLogin, int procesoEspecial) throws SQLException {
        ResultSet rsResult = null;
        QuerysBD query = new QuerysBD();
        boolean hasProcesoEspecial = false;
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLtieneProcesoEspecial());
                if (pstmt != null) {
                    pstmt.setString(1, appLogin);
                    pstmt.setInt(2, procesoEspecial);

                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        while (rsResult.next()) {
                            hasProcesoEspecial = Boolean.parseBoolean(rsResult.getString("ESPECIAL"));
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
        return hasProcesoEspecial;
    }

}
