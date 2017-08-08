/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.sql;
/**
 *
 * @author ealonso
 */
import gob.gbc.Framework.DataSourceRequest;
import gob.gbc.bd.ConectaBD;
import gob.gbc.entidades.Actividad;
import gob.gbc.entidades.Componente;
import gob.gbc.entidades.GraficaMir;
import gob.gbc.entidades.Indicador;
import gob.gbc.entidades.IndicadorGeneralSei;
import gob.gbc.entidades.MIR;
import gob.gbc.entidades.Programa;
import gob.gbc.entidades.Ramo;
import gob.gbc.entidades.RamoPrograma;
import gob.gbc.entidades.UsuarioSession;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.EnumEstatusMIR;
import gob.gbc.util.Helper;
import gob.gbc.util.Resultado;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSQLCatalogos extends ResultSQLMedio {
    // <editor-fold defaultstate="collapsed" desc="GLOBALES">
   
    // private String strServer = "";
    //private String strUbicacion = "";
   // public Bitacora bitacora;
    private String strError = "";
   // public String errorBD = new String();
    
    /**
     * Creates a new instance of ResultSQLMedio
     *
     * @throws java.io.IOException
     */
    public ResultSQLCatalogos() throws SQLException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException, Exception{

       // conectaBD = new ConectaBD();
      //  bitacora = new Bitacora();
    }
    
    public ResultSQLCatalogos(String tipoDependencia) 
            throws SQLException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException, Exception {
        super(tipoDependencia);
     //   bitacora = new Bitacora();
    }   
    
    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        this.strError = strError;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CONSULTAS PARA CONTROLES">
    
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
    
    public String getValorParametros(String strOpcion) throws SQLException, Exception{
        String valor = new String();
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            try {               
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlObtenerValorParametros());
                if (pstmt != null) {
                    pstmt.setString(1, strOpcion);
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        if (rsResultado.next()) {
                            valor = rsResultado.getString(1);   
                        }
                    }
                }
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return valor;
    }
    
    public List<Indicador> consultaFinProposito(MIR mir, boolean normativo) throws Exception{
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        Indicador indicador;
        //MIR mir = ;
        mir = getMIREstatusEtapa(mir);
        List<Indicador> indicadorList = new ArrayList<Indicador>();
        if(conectaBD != null){
            PreparedStatement pstmt = null;
            try{
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaFinProposito());
                if(pstmt != null){
                    pstmt.setString(1, mir.getStrYear());
                    pstmt.setString(2, mir.getRamo());
                    pstmt.setString(3, mir.getPrg());
                    pstmt.setString(4, mir.getStrYear());
                    pstmt.setString(5, mir.getRamo());
                    pstmt.setString(6, mir.getPrg());
                    
                    rsResultado = pstmt.executeQuery();
                    if(rsResultado != null){
                        while(rsResultado.next()){
                            indicador = new Indicador();
                            indicador.setYear(mir.getStrYear());
                            indicador.setRamo(mir.getRamo());
                            indicador.setPrograma(mir.getPrg());
                            indicador.setFinProposito(rsResultado
                                    .getString("FIN_PROPOSITO") == null 
                                    ? new String() : rsResultado.getString("FIN_PROPOSITO"));
                            indicador.setIndicadorId(rsResultado
                                    .getString("INDICADOR_SEI") == null 
                                    ? new String() : rsResultado.getString("INDICADOR_SEI"));
                            indicador.setIndicadorSEI(rsResultado
                                    .getString("INDICADOR_SEI") == null 
                                    ? new String() : rsResultado.getString("INDICADOR_SEI"));
                            indicador.setIndicadores(rsResultado
                                    .getString("INDESCR") == null 
                                    ? new String() : rsResultado.getString("INDESCR"));
                            indicador.setMedios(rsResultado
                                    .getString("MEDIOS_VERIFICACION") == null 
                                    ? new String() : rsResultado.getString("MEDIOS_VERIFICACION"));
                            indicador.setSupuestos(rsResultado
                                    .getString("SUPUESTO") == null 
                                    ? new String() : rsResultado.getString("SUPUESTO"));
                            indicador.setEstatus(mir.getEstatus());
                            indicador.setEtapa(mir.getEtapa());
                            indicador.setPoliticaPublica(mir.isPoliticaPublica());
                            indicador.setNormativo(normativo);
                            indicadorList.add(indicador);
                            indicador.setTipoRegistro(rsResultado.getString("DIMENSION").equals("1") ? "FIN" : "PROPÓSITO");
                            indicador.setDimension(rsResultado.getInt("DIMENSION"));
                        }
                    }
                }
            }catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        }
        return indicadorList;
    }
    
    public int getRamoListCount(UsuarioSession session, String ramo, String programa, DataSourceRequest options, boolean count) throws Exception {
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        int obResultado = 0;
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            try {               
                //Posibles filtros para edicion de catalogo o en cascada
                ramo = ramo == null? "" : ramo; 
                programa = programa == null ? "" : programa;       

                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaRamoListFiltros(session, ramo, programa, options, count));

                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            obResultado = rsResultado.getInt(1);   
                        }
                    }
                }

            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }

    public List<Ramo> getRamoListFiltros(UsuarioSession session, String strramo, String programa, DataSourceRequest options, boolean count) throws Exception {
        ArrayList alEntidad  = null;
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        List<Ramo> obResultado = null;
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            Ramo entidad;
            try {                
                //Posibles filtros para edicion de catalogo o en cascada
                strramo = strramo == null? "" : strramo; 
                programa = programa == null ? "" : programa;

                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaRamoListFiltros(session, strramo, programa, options, count));

                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        alEntidad = new ArrayList();
                        while (rsResultado.next()) {
                            entidad = new Ramo();
                            entidad.setRamo(rsResultado.getString(1));
                            entidad.setRamoDescr(rsResultado.getString(2));
                            alEntidad.add(entidad);
                        }
                        obResultado = alEntidad;
                    }
                }
           } catch (SQLException sqle) {
                throw new Exception(sqle.getMessage());
            } catch (Exception ex) {
                bitacora.grabaBitacora();
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }
    
    public int getProgramaListCount(UsuarioSession session, String ramo, String programa, DataSourceRequest options, boolean count) throws Exception {
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        int obResultado = 0;
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            try {               
                //Posibles filtros para edicion de catalogo o en cascada
                ramo = ramo == null? "" : ramo; 
                programa = programa == null ? "" : programa;       

                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaProgramaListFiltros(session, ramo, programa, options, count));

                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            obResultado = rsResultado.getInt(1);   
                        }
                    }
                }

            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }

    public List<Programa> getProgramaListFiltros(UsuarioSession session, String strramo, String programa, DataSourceRequest options, boolean count) throws Exception {
        ArrayList alEntidad  = null;
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        List<Programa> obResultado = null;
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            Programa entidad;
            try {
                String year = session.getYear();
                String usuario = session.getStrUsuario();
                String tipoDependencia = session.getTipoDependencia();
                
                //Posibles filtros para edicion de catalogo o en cascada
                strramo = strramo == null? "" : strramo; 
                programa = programa == null ? "" : programa;

                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaProgramaListFiltros(session, strramo, programa, options, count));

                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        alEntidad = new ArrayList();
                        while (rsResultado.next()) {
                            entidad = new Programa();
                            entidad.setProgramaId(rsResultado.getString(1));
                            entidad.setProgramaDesc(rsResultado.getString(2));
                            alEntidad.add(entidad);
                        }
                        obResultado = alEntidad;
                    }
                }
           } catch (SQLException sqle) {
                throw new Exception(sqle.getMessage());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }
    
    public GraficaMir ConsultaGraficaMir(UsuarioSession usuario, String ramo, String programa) throws SQLException{
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        GraficaMir grafica = null;
        if(conectaBD != null){
            PreparedStatement pstmt = null;
            try{
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaGraficaMir(ramo,programa));
                if(pstmt != null){
                    pstmt.setString(1, usuario.getStrUsuario());
                    pstmt.setString(2, usuario.getYear());
                    rsResultado = pstmt.executeQuery();
                    if(rsResultado != null && rsResultado.next()){
                        grafica = new GraficaMir();
                        grafica.setCancelada(rsResultado.getInt("CANCELADA"));
                        grafica.setBorrador(rsResultado.getInt("BORRADOR"));
                        grafica.setEnviada(rsResultado.getInt("ENVIADA"));
                        grafica.setRechazada(rsResultado.getInt("RECHAZADA"));
                        grafica.setValidada(rsResultado.getInt("VALIDADA"));
                        grafica.setEnviadaPosterior(rsResultado.getInt("ENVIADA_P"));
                        grafica.setRechazadaPosterior(rsResultado.getInt("RECHAZADA_P"));
                        grafica.setValidadaPosterior(rsResultado.getInt("VALIDADA_P"));
                    }
                }
            }catch(SQLException e){
                bitacora.setStrUbicacion(getStrUbicacion());
                bitacora.setStrServer(getStrServer());
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(e, new Throwable());
                bitacora.grabaBitacora();
            }finally {
                rsResultado.close();
                pstmt.close();
            }
        }
        return grafica;
    }
    
    public int getMIRCount(UsuarioSession session, String ramo, String programa, DataSourceRequest options, boolean count) throws Exception {
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        int obResultado = 0;
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            try {               
                //Posibles filtros para edicion de catalogo o en cascada
                ramo = ramo == null? "" : ramo; 
                programa = programa == null ? "" : programa;       

                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaMIRFiltros(session, ramo, programa, options, count));

                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            obResultado = rsResultado.getInt(1);   
                        }
                    }
                }

            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }
    
    public String getEstatusMir(MIR mir) throws Exception{
        String estatusMir = new String();
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if(conectaBD != null){
            PreparedStatement pstmt = null;
            try{
                pstmt= conectaBD.getConConexion().prepareStatement(query.getSQLgetStatusMIR());
                if(pstmt != null){
                    pstmt.setString(1, mir.getStrYear());
                    pstmt.setString(2, mir.getRamo());
                    pstmt.setString(3, mir.getPrg());
                    
                    rsResultado = pstmt.executeQuery();
                    if(rsResultado != null && rsResultado.next()){
                        estatusMir = rsResultado.getString("STATUS");
                    }
                }
            }catch (SQLException sqle) {
                throw new Exception(sqle.getMessage());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        }
        
        return estatusMir;
    }

    public MIR getMIREstatusEtapa(MIR mir)throws Exception {
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if(conectaBD != null){
            PreparedStatement pstmt = null;
            try{
                pstmt= conectaBD.getConConexion().prepareStatement(query.getSqlEstatusEtapaMIR());
                if(pstmt != null){
                    pstmt.setString(1, mir.getStrYear());
                    pstmt.setString(2, mir.getRamo());
                    pstmt.setString(3, mir.getPrg());
                    
                    rsResultado = pstmt.executeQuery();
                    if(rsResultado != null && rsResultado.next()){
                        mir.setEstatus(rsResultado.getInt("STATUS"));
                        mir.setEtapa(rsResultado.getInt("ETAPA"));
                        mir.setPoliticaPublica(rsResultado.getBoolean("politicaPublica"));
                    }
                }
            }catch (SQLException sqle) {
                throw new Exception(sqle.getMessage());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        }
        
        return mir;
    }
    
    public List<MIR> getMIRFiltros(UsuarioSession session, String strramo, String programa, DataSourceRequest options, boolean count) throws Exception {
        ArrayList alEntidad  = null;
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        List<MIR> obResultado = null;
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            MIR entidad;
            try {
                String year = session.getYear();
                String usuario = session.getStrUsuario();
                String tipoDependencia = session.getTipoDependencia();
                
                //Posibles filtros para edicion de catalogo o en cascada
                strramo = strramo == null? "" : strramo; 
                programa = programa == null ? "" : programa;

                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaMIRFiltros(session, strramo, programa, options, count));
                        //pstmt.setString(1, year);
                        //pstmt.setString(2, ramo);
                       // pstmt.setString(3, programa);                       

                if (pstmt != null) {
                    
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        alEntidad = new ArrayList();
                        while (rsResultado.next()) {
                            entidad = new MIR();
                            entidad.setMir(rsResultado.getInt(1));
                            entidad.setStrYear(rsResultado.getString(2));
                            entidad.setRamo(rsResultado.getString(3));
                            entidad.setRamoDescr(rsResultado.getString(4));
                            entidad.setPrg(rsResultado.getString(5));
                            entidad.setPrgDescr(rsResultado.getString(6));
                            entidad.setFechaRegistro(rsResultado.getDate(7));
                            entidad.setStatus(rsResultado.getString(8));
                            entidad.setStatusP(rsResultado.getString(9));
                            entidad.setPoliticaPublica(rsResultado.getBoolean(10));
                            entidad.setStatusIniDescr(Helper.ObtieneDescrEstatus(rsResultado.getString(8) != null ? Integer.parseInt(rsResultado.getString(8)) : -1));
                            entidad.setStatusPosDescr(Helper.ObtieneDescrEstatus(rsResultado.getString(9) != null ? Integer.parseInt(rsResultado.getString(9)) : -1));
                            entidad.setColorIni(Helper.ObtieneColorEstatus(rsResultado.getString(8) != null ? Integer.parseInt(rsResultado.getString(8)) : -1));
                            entidad.setColorPos(Helper.ObtieneColorEstatus(rsResultado.getString(9) != null ? Integer.parseInt(rsResultado.getString(9)) : -1));
                            entidad.setNormativo(session.isNormativo());

                            alEntidad.add(entidad);
                        }
                        obResultado = alEntidad;
                    }
                }
           } catch (SQLException sqle) {
                throw new Exception(sqle.getMessage());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }
    
    public int getComponenteCount(UsuarioSession session, String ramo, String programa, DataSourceRequest options, boolean count) throws Exception {
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        int obResultado = 0;
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            try {               
                //Posibles filtros para edicion de catalogo o en cascada
                ramo = ramo == null? "" : ramo; 
                programa = programa == null ? "" : programa;       
               if(!ramo.isEmpty() || !programa.isEmpty())
               {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaComponenteFiltros(session, ramo, programa, options, count));

                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next()) {
                            obResultado = rsResultado.getInt(1);   
                        }
                    }
                }
               }
            } catch (Exception ex) {
                bitacora.grabaBitacora();
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }

    public List<Componente> getComponenteFiltros(UsuarioSession session, String strramo, String programa, DataSourceRequest options, boolean count) throws Exception {
        ArrayList alEntidad  = null;
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        List<Componente> obResultado = null;
        MIR mir = getMIREstatusEtapa(new gob.gbc.entidades.MIR(session.getYear(), strramo, programa));
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            Componente entidad;
            try {
               // String year = session.getYear();
               // String usuario = session.getStrUsuario();
               // String tipoDependencia = session.getTipoDependencia();
                
               //Posibles filtros para edicion de catalogo o en cascada
               // strramo = strramo == null? "" : strramo; 
               // programa = programa == null ? "" : programa;
               //if(!strramo.isEmpty() || !programa.isEmpty())
               //{
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaComponenteFiltros(session, strramo, programa, options, count));

                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        alEntidad = new ArrayList();
                        while (rsResultado.next()) {
                            entidad = new Componente();
                            entidad.setStrYear(rsResultado.getString(1));
                            entidad.setRamo(rsResultado.getString(2));
                            entidad.setPrg(rsResultado.getString(3));
                            entidad.setRenglon(rsResultado.getString(4));
                            entidad.setComponente(rsResultado.getInt(5));
                            entidad.setDescr(rsResultado.getString(6));
                            entidad.setMeta(rsResultado.getInt(7));
                            entidad.setIndicadorSEI(rsResultado.getString(8));
                            entidad.setIndicadores(rsResultado.getString(9));
                            entidad.setMedios(rsResultado.getString(10));
                            entidad.setSupuestos(rsResultado.getString(11));
                            entidad.setNormativo(session.isNormativo());
                            entidad.setEtapa(mir.getEtapa());
                            entidad.setEstatus(mir.getEstatus());
                            entidad.setPoliticaPublica(mir.isPoliticaPublica());

                            alEntidad.add(entidad);
                        }
                        obResultado = alEntidad;
                    }
                }
               //} else { 
               //    entidad = new Componente();
               //}
           } catch (SQLException sqle) {
                throw new Exception(sqle.getMessage());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }
    
    public int getActividadCount(UsuarioSession session, String ramo, String programa, int componente, DataSourceRequest options, boolean count) throws Exception {  
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        int obResultado = 0;
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            try {               
                //Posibles filtros para edicion de catalogo o en cascada
                ramo = ramo == null? "" : ramo; 
                programa = programa == null ? "" : programa;       
                                        
               if(!ramo.isEmpty() || !programa.isEmpty())
               {
                   pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaActividadFiltros(session, ramo, programa, componente, options, count));                  

                    if (pstmt != null) {
                        rsResultado = pstmt.executeQuery();
                        if (rsResultado != null) {
                            while (rsResultado.next()) {
                                obResultado = rsResultado.getInt(1);   
                            }
                        }
                    }
               }
               
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }

    public List<Actividad> getActividadFiltros(UsuarioSession session, String strramo, String programa, int componente, DataSourceRequest options, boolean count) throws Exception {
        ArrayList alEntidad  = null;
        ResultSet rsResultado = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        List<Actividad> obResultado = null;
        MIR mir = getMIREstatusEtapa(new gob.gbc.entidades.MIR(session.getYear(), strramo, programa));
        if (conectaBD != null) {       
            PreparedStatement pstmt = null;
            Actividad entidad;
            try {
                String year = session.getYear();
                String usuario = session.getStrUsuario();
                String tipoDependencia = session.getTipoDependencia();
                
                //Posibles filtros para edicion de catalogo o en cascada
                strramo = strramo == null? "" : strramo; 
                programa = programa == null ? "" : programa;
               if(!strramo.isEmpty() || !programa.isEmpty())
               {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLConsultaActividadFiltros(session, strramo, programa,componente, options, count));
               
                if (pstmt != null) {
                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        alEntidad = new ArrayList();
                        while (rsResultado.next()) {
                            entidad = new Actividad();
                            entidad.setStrYear(rsResultado.getString(1));
                            entidad.setRamo(rsResultado.getString(2));
                            entidad.setPrg(rsResultado.getString(3));                            
                            entidad.setRenglon(rsResultado.getString(4));
                            entidad.setComponente(rsResultado.getInt(5));
                            entidad.setActividad(rsResultado.getInt(6));
                            entidad.setDescr(rsResultado.getString(7));
                            entidad.setMeta(rsResultado.getInt(8));
                            entidad.setAccion(rsResultado.getInt(9));
                            entidad.setIndicadorSEI(rsResultado.getString(10));
                            entidad.setIndicadores(rsResultado.getString(11));
                            entidad.setMedios(rsResultado.getString(12));
                            entidad.setSupuestos(rsResultado.getString(13));
                            entidad.setNormativo(session.isNormativo());
                            entidad.setEtapa(mir.getEtapa());
                            entidad.setEstatus(mir.getEstatus());
                            entidad.setPoliticaPublica(mir.isPoliticaPublica());

                            alEntidad.add(entidad);
                        }
                        obResultado = alEntidad;
                    }
                }
               } else { 
                   entidad = new Actividad();
               }
           } catch (SQLException sqle) {
                throw new Exception(sqle.getMessage());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            } finally {
                rsResultado.close();
                pstmt.close();
            }
        } else {
            throw new Exception("No se puede realizar ninguna operacion con la base de datos : ConectaBD is null ");
        }
        return obResultado;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="MANTENIMIENTOS">       
    public boolean guardar() throws IOException, Exception {
        boolean blActualizo = false;
        try {
            
            conectaBD.transaccionCommit();
            
            blActualizo = true;
            
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return blActualizo;
    }    
        
    // <editor-fold defaultstate="collapsed" desc="MIR">     
    public boolean permiteEditarMIR(UsuarioSession session, MIR entidad) throws IOException, Exception {       
        boolean permitir = false;
        try{
            int estatusActual = obtenerEstatusMIR(entidad.getStrYear(), entidad.getRamo(), entidad.getPrg());
            //Valida si la edicion esta abierta por estatus y Rol
            permitir = editarMIR(estatusActual, session.getRol(), session.getRolNormativo());   
        } catch (Exception e) {
                throw new Exception(e.getMessage());
        }
        return permitir;
    }
    
    public boolean ValidaDuplicadoMIR(String year, String ramo, String programa) throws IOException, Exception {
        boolean validaDuplicado = true;
        String existe = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if (conectaBD != null) {       
            ResultSet rsResultado = null;
            PreparedStatement pstmt = null;
            try {   
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlExisteMIR());
                if (pstmt != null) {
                    pstmt.setString(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);

                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {

                        while (rsResultado.next()) {
                            existe = rsResultado.getString(1);
                        }

                        if (existe.equals("0")) {
                            validaDuplicado = false;
                        }
                    }
                }
            } catch (Exception sqle) {
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                throw new Exception(sqle.getMessage());
            }  finally {
                pstmt.close(); 
                rsResultado.close();
            }
        }
        return validaDuplicado;
    }
        
    public boolean InsertarMIR(String year, String ramo, String programa) throws IOException, Exception {
        boolean inserto = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if (!ValidaDuplicadoMIR(year,ramo,programa)) {
            if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlInsertMIR());
                    if (pstmt != null) {
                        pstmt.setString(1, year);
                        pstmt.setString(2, ramo);
                        pstmt.setString(3, programa);                       

                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible insertar el elemento");
                        } else {
                            inserto = true;
                        }
                     }
                    else{
                        this.setStrError("No fue posible insertar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        }
        return inserto;
    }
    
    public boolean actualizarMIR(String year, String ramo, String programa, String programaAnterior) throws IOException, Exception {
        boolean actualizo = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if (!ValidaDuplicadoMIR(year, ramo, programa)) {
            if (conectaBD != null) {   
                PreparedStatement pstmt = null;
                try {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlActualizarMIR());
                    if (pstmt != null) {
                        pstmt.setString(1, programa);
                        pstmt.setString(2, year);
                        pstmt.setString(3, ramo); 
                        pstmt.setString(4, programaAnterior);

                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible actualizar el elemento");
                        } else {
                            actualizo = true;
                        }                        
                    } 
                    else{
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    pstmt.close(); 
                }
            }
        }
        return actualizo;
    }
    
    public boolean borrarMIR(String year, String ramo, String programa) throws IOException, Exception {
        boolean borro = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if (conectaBD != null) { 
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlBorrarMIR());
                if (pstmt != null) {
                    pstmt.setString(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);

                    if (pstmt.executeUpdate() == 0) {
                        this.setStrError("No fue posible borrar el elemento");
                    } else {
                        borro = true;
                    }
                }
                else{
                    this.setStrError("No fue posible borrar el elemento");
                }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    pstmt.close(); 
                }
        }
        return borro;
    }
    
    public boolean validaPropositoMIR(String year, String ramo, String programa) throws IOException, Exception {
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        boolean bEnvia = false; 
        if (conectaBD != null) {       
            ResultSet rsResultado = null;
            PreparedStatement pstmt = null;
            try {   
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaPropositoMIR());
                if (pstmt != null) {
                    pstmt.setString(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);

                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        if (rsResultado.next()) {
                            if(rsResultado.getString("PROPOSITO") != null && !rsResultado.getString("PROPOSITO").trim().equals("")){
                                bEnvia = true;
                            }
                        }
                    }
                }
            } catch (Exception sqle) {
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    throw new Exception(sqle.getMessage());
                }
                throw new Exception(sqle.getMessage());
            }  finally {
                pstmt.close();
                rsResultado.close();
                pstmt.close(); 
            }
        }
        return bEnvia;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="COMPONENTE">  
    public boolean permiteEditarComponente(UsuarioSession session, Componente entidad) throws IOException, Exception {
        boolean permitir = false;
        try{
            MIR mir = new MIR();
            mir.setStrYear(entidad.getStrYear());
            mir.setRamo(entidad.getRamo());
            mir.setPrg(entidad.getPrg());
            //Valida si la edicion esta abierta por estatus y Rol
            if(permiteEditarMIR(session, mir))
                permitir = true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return permitir;
    }
    
    public boolean permiteBorrarComponente(UsuarioSession session, Componente entidad) throws IOException, Exception {
        boolean permitir = false;
        try{
            MIR mir = new MIR();
            mir.setStrYear(entidad.getStrYear());
            mir.setRamo(entidad.getRamo());
            mir.setPrg(entidad.getPrg());
            //Valida si la edicion esta abierta por estatus y Rol
            if(permiteEditarMIR(session, mir)){
                if(!componenteTieneHijos(entidad) && !metaTieneHijos(entidad)){
                    permitir = true;
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return permitir;
    }
    
    public boolean componenteTieneHijos(Componente entidad) throws IOException, Exception {
        boolean tieneHijos = true;
        String existe = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if (conectaBD != null) {       
            ResultSet rsResultado = null;
            PreparedStatement pstmt = null;
            try {   
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlTieneHijosComponente());
                if (pstmt != null) {
                    pstmt.setString(1, entidad.getStrYear());
                    pstmt.setString(2, entidad.getRamo());
                    pstmt.setString(3, entidad.getPrg());
                    pstmt.setInt(4, entidad.getComponente());

                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {

                        while (rsResultado.next()) {
                            existe = rsResultado.getString(1);
                        }

                        if (existe.equals("0")) {
                            tieneHijos = false;
                        }
                    }
                }
            } catch (Exception sqle) {
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                throw new Exception(sqle.getMessage());
            }  finally {
                rsResultado.close();
                pstmt.close(); 
            }
        }
        return tieneHijos;
    }
    
    public boolean metaTieneHijos(Componente entidad) throws Exception{
        boolean bolRespuesta = false;
        int intCuantos = 0;
        ResultSQL rsSQL = new ResultSQL(super.tipoDependencia);
        try {
            rsSQL.setStrServer(super.getStrServer());
            rsSQL.setStrUbicacion(super.getStrUbicacion());
            rsSQL.resultSQLConecta(super.tipoDependencia); 
            intCuantos = rsSQL.getResultSQLGetCountAcciones(Integer.parseInt(entidad.getStrYear()), entidad.getRamo(), entidad.getPrg(), entidad.getMeta());
            if(intCuantos > 0){
                bolRespuesta = true;
            }
        } catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }
        finally{
            rsSQL.resultSQLDesconecta();
        }
        return bolRespuesta;
    }
    
    public boolean insertaComponente(Componente entidad) throws IOException, Exception {
        boolean inserto = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        int iRes = 0;
       // if (!ValidaDuplicadoMIR(year,ramo,programa)) {
            if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlInsertComponente());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getStrYear());
                        pstmt.setString(2, entidad.getRamo());
                        pstmt.setString(3, entidad.getPrg());
                        pstmt.setString(4, entidad.getDescr().toUpperCase());

                        iRes = pstmt.executeUpdate();
                        if (iRes <= 0) {
                            this.setStrError("No fue posible insertar el elemento");
                        } else {
                            inserto = true;
                        }                        
                     }
                    else{
                        this.setStrError("No fue posible insertar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        //}
        return inserto;
    }
    
    public boolean actualizaComponente(Componente entidad) throws IOException, Exception {
        boolean actualizo = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        //if (!ValidaDuplicadoMIR(year, ramo, programa)) {
            if (conectaBD != null) {   
                PreparedStatement pstmt = null;
                try {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlActualizarComponente());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getDescr().toUpperCase());
                        pstmt.setString(2, entidad.getStrYear());
                        pstmt.setString(3, entidad.getRamo());
                        pstmt.setString(4, entidad.getPrg());
                        pstmt.setInt(5, entidad.getComponente());

                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible actualizar el elemento");
                        } else {
                            pstmt.close();
                            if(entidad.getMeta() > 0){
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlActualizarDescrMeta());
                                if (pstmt != null) {
                                    pstmt.setString(1, entidad.getDescr().toUpperCase());
                                    pstmt.setString(2, entidad.getStrYear());
                                    pstmt.setString(3, entidad.getRamo());
                                    pstmt.setInt(4, entidad.getMeta());

                                    if (pstmt.executeUpdate() <= 0) {
                                        this.setStrError("No fue posible actualizar el elemento relacionado");
                                    } else {
                                        actualizo = true;
                                    }
                                } 
                            } else {
                                actualizo = true;
                            }
                            if(entidad.getIndicadorSEI() != null || entidad.getIndicadores() != null || entidad.getMedios() != null || entidad.getSupuestos() != null ) {  
                                //GUARDAR INDICADOR                        
                                if(existeIndicadorComponente(entidad)){
                                    actualizo = updateIndicadorComponente(entidad);
                                } else {
                                    actualizo = insertMIRIndicadorComponente(entidad);                        
                                }
                            }
                        }
                    } else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }

                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    pstmt.close(); 
                }
            }
       // }
        return actualizo;
    }
    
    public boolean borraComponente(Componente entidad) throws IOException, Exception {
        boolean borro = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if (conectaBD != null) { 
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlBorrarComponente());
                if (pstmt != null) {
                    pstmt.setString(1, entidad.getStrYear());
                    pstmt.setString(2, entidad.getRamo());
                    pstmt.setString(3, entidad.getPrg());
                    pstmt.setInt(4, entidad.getComponente());
                    
                    if (pstmt.executeUpdate() <= 0) {
                        this.setStrError("No fue posible borrar el elemento");
                    } else {
                        borro = true;
                    }
                }
                else {
                    this.setStrError("No fue posible borrar el elemento");
                }
            } catch (Exception sqle) {
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception ex) {
                    throw new Exception(ex.getMessage());
                }
                throw new Exception(sqle.getMessage());
            } finally {
                pstmt.close(); 
            }
        }
        return borro;
    }
        // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="ACTIVIDAD">      
    public boolean permiteEditarActividad(UsuarioSession session, Actividad entidad) throws IOException, Exception {
        boolean permitir = false;
        try{
            MIR mir = new MIR();
            mir.setStrYear(entidad.getStrYear());
            mir.setRamo(entidad.getRamo());
            mir.setPrg(entidad.getPrg());
            //Valida si la edicion esta abierta por estatus y Rol
            if(permiteEditarMIR(session, mir)){
                permitir = true;
            }
        } catch (Exception e) {
                throw new Exception(e.getMessage());
        }
        return permitir;
    }
        
    public boolean permiteBorrarActividad(UsuarioSession session, Actividad entidad) throws IOException, Exception {
        boolean permitir = false;
        try{
            MIR mir = new MIR();
            mir.setStrYear(entidad.getStrYear());
            mir.setRamo(entidad.getRamo());
            mir.setPrg(entidad.getPrg());
            //Valida si la edicion esta abierta por estatus y Rol
            if(permiteEditarMIR(session, mir)){
                if(!accionTieneHijos(entidad)){
                    permitir = true;
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return permitir;
    }
    
    public boolean accionTieneHijos(Actividad entidad) throws Exception{
        boolean bolRespuesta = false;
        int intCuantos = 0;
        ResultSQL rsSQL = new ResultSQL(super.tipoDependencia);
        try {
            rsSQL.setStrServer(super.getStrServer());
            rsSQL.setStrUbicacion(super.getStrUbicacion());
            rsSQL.resultSQLConecta(super.tipoDependencia); 
            intCuantos = rsSQL.getResultSQLGetCountRequerimientos(Integer.parseInt(entidad.getStrYear()), entidad.getRamo(), entidad.getPrg(), entidad.getMeta(), entidad.getAccion());
            if(intCuantos > 0){
                bolRespuesta = true;
            }
        } catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }
        finally{
            rsSQL.resultSQLDesconecta();
        }
        return bolRespuesta;
    }
        
    public boolean insertaActividad(Actividad entidad) throws IOException, Exception {
        boolean inserto = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlInsertActividad());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getStrYear());
                        pstmt.setString(2, entidad.getRamo());
                        pstmt.setString(3, entidad.getPrg());
                        pstmt.setInt(4, entidad.getComponente());
                        pstmt.setString(5, entidad.getDescr().toUpperCase());

                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible insertar el elemento");
                        } else {
                            inserto = true;
                        }
                     }
                    else {
                        this.setStrError("No fue posible insertar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return inserto;
    }
    
    public boolean actualizaActividad(Actividad entidad) throws IOException, Exception {
        boolean actualizo = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
            if (conectaBD != null) {   
                PreparedStatement pstmt = null;
                try {
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlActualizarActividad());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getDescr().toUpperCase());
                        pstmt.setString(2, entidad.getStrYear());
                        pstmt.setString(3, entidad.getRamo());
                        pstmt.setString(4, entidad.getPrg());
                        pstmt.setInt(5, entidad.getComponente());
                        pstmt.setInt(6, entidad.getActividad());

                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible actualizar el elemento");
                        } else {
                            if(entidad.getAccion() > 0){
                                pstmt.close();
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlActualizarDescrAccion());
                                if (pstmt != null) {
                                    pstmt.setString(1, entidad.getDescr().toUpperCase());
                                    pstmt.setString(2, entidad.getStrYear());
                                    pstmt.setString(3, entidad.getRamo());
                                    pstmt.setInt(4, entidad.getMeta());
                                    pstmt.setInt(5, entidad.getAccion());

                                    if (pstmt.executeUpdate() <= 0) {
                                        this.setStrError("No fue posible actualizar el elemento relacionado");
                                    } else {
                                        actualizo = true;
                                    }
                                } 
                            } else {
                                actualizo = true;
                            }
                            if(entidad.getIndicadorSEI() != null || entidad.getIndicadores() != null || entidad.getMedios() != null || entidad.getSupuestos() != null ) {  
                                //GUARDAR INDICADOR                        
                                if(existeIndicadorActividad(entidad)){
                                    actualizo = updateIndicadorActividad(entidad);
                                } else {
                                    actualizo = insertMIRIndicadorActividad(entidad);                        
                                }
                            }
                        }
                    } 
                    else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    pstmt.close(); 
                }
            }
        return actualizo;
    }
    
    public boolean borraActividad(Actividad entidad) throws IOException, Exception {
        boolean borro = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if (conectaBD != null) { 
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlBorrarActividad());
                if (pstmt != null) {
                    pstmt.setString(1, entidad.getStrYear());
                    pstmt.setString(2, entidad.getRamo());
                    pstmt.setString(3, entidad.getPrg());
                    pstmt.setInt(4, entidad.getComponente());
                    pstmt.setInt(5, entidad.getActividad());

                    if (pstmt.executeUpdate() <= 0) {
                        this.setStrError("No fue posible borrar el elemento");
                    } else {
                        borro = true;
                    }
                }
                else {
                    this.setStrError("No fue posible borrar el elemento");
                }
            } catch (Exception sqle) {
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception ex) {
                    throw new Exception(ex.getMessage());
                }
                throw new Exception(sqle.getMessage());
            } finally {
                pstmt.close(); 
            }
        }
        return borro;
    }
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="INDICADOR"> 
    
    public boolean updateIndicadorFinProposito(Indicador indicador) throws IOException, Exception {
        boolean actualizo = false;
        int cont = 1;
        
        QuerysBDCatalogos query = new QuerysBDCatalogos();
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.updateIndicadorFinProposito());
                    if (pstmt != null) {
                        pstmt.setString(1, indicador.getIndicadores().toUpperCase());
                        pstmt.setString(2, indicador.getMedios().toUpperCase());
                        pstmt.setString(3, indicador.getSupuestos().toUpperCase());
                        //pstmt.setString(4, indicador.getIndicadorId() == null ? new String() : indicador.getIndicadorId());
                        pstmt.setString(4, indicador.getIndicadorSEI() == null ? new String() : indicador.getIndicadorSEI());
                        pstmt.setString(5, indicador.getYear());
                        pstmt.setString(6, indicador.getRamo());
                        pstmt.setString(7, indicador.getPrograma());
                        pstmt.setInt(8, indicador.getDimension());

                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible actualizar el elemento");
                        } else {
                            actualizo = true;
                        }
                     }
                    else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return actualizo;
    }
    
    public boolean updateIndicadorComponente(Componente entidad) throws IOException, Exception {
        boolean actualizo = false;
        int cont = 1;
        
        QuerysBDCatalogos query = new QuerysBDCatalogos();
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.updateIndicadorComponente());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getIndicadores() == null ? new String() : entidad.getIndicadores().toUpperCase());
                        pstmt.setString(2, entidad.getMedios() == null ? new String() : entidad.getMedios().toUpperCase());
                        pstmt.setString(3, entidad.getSupuestos() == null ? new String() : entidad.getSupuestos().toUpperCase());
                        //pstmt.setString(4, indicador.getIndicadorId() == null ? new String() : indicador.getIndicadorId());
                        pstmt.setString(4, entidad.getIndicadorSEI() == null ? new String() : entidad.getIndicadorSEI());
                        pstmt.setString(5, entidad.getStrYear());
                        pstmt.setString(6, entidad.getRamo());
                        pstmt.setString(7, entidad.getPrg());
                        pstmt.setInt(8, 3);
                        pstmt.setInt(9, entidad.getComponente());               

                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible actualizar el elemento");
                        } else {
                            actualizo = true;
                        }
                     }
                    else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return actualizo;
    }
        
    public boolean updateIndicadorActividad(Actividad entidad) throws IOException, Exception {
        boolean actualizo = false;
        int cont = 1;
        
        QuerysBDCatalogos query = new QuerysBDCatalogos();
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.updateIndicadorActividad());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getIndicadores() == null ? new String() : entidad.getIndicadores().toUpperCase());
                        pstmt.setString(2, entidad.getMedios() == null ? new String() : entidad.getMedios().toUpperCase());
                        pstmt.setString(3, entidad.getSupuestos() == null ? new String() : entidad.getSupuestos().toUpperCase());
                        //pstmt.setString(4, indicador.getIndicadorId() == null ? new String() : indicador.getIndicadorId());
                        pstmt.setString(4, entidad.getIndicadorSEI() == null ? new String() : entidad.getIndicadorSEI());
                        pstmt.setString(5, entidad.getStrYear());
                        pstmt.setString(6, entidad.getRamo());
                        pstmt.setString(7, entidad.getPrg());
                        pstmt.setInt(8, 4);
                        pstmt.setInt(9, entidad.getComponente());
                        pstmt.setInt(10, entidad.getActividad());
                                                                
                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible actualizar el elemento");
                        } else {
                            actualizo = true;
                        }
                     }
                    else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return actualizo;
    }
        
    public boolean insertMIRIndicadorFinProposito(Indicador indicador) throws IOException, Exception {
        boolean actualizo = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.insertMIRIndicadorFinProposito());
                    if (pstmt != null) {
                        pstmt.setString(1, indicador.getYear());
                        pstmt.setString(2, indicador.getRamo());
                        pstmt.setString(3, indicador.getPrograma());
                        pstmt.setString(4, indicador.getIndicadores().toUpperCase());
                        pstmt.setInt(5, indicador.getDimension());
                        pstmt.setString(6, indicador.getSupuestos().toUpperCase());
                        pstmt.setString(7, indicador.getMedios().toUpperCase());
                        //pstmt.setString(8, indicador.getIndicadorId());
                        pstmt.setString(8, indicador.getIndicadorSEI());

                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible actualizar el elemento");
                        } else {
                            actualizo = true;
                        }
                     }
                    else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return actualizo;
    }
    
    public boolean insertMIRIndicadorComponente(Componente entidad) throws IOException, Exception {
        boolean actualizo = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
       
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.insertMIRIndicadorComponente());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getStrYear());
                        pstmt.setString(2, entidad.getRamo());
                        pstmt.setString(3, entidad.getPrg());
                        pstmt.setString(4, entidad.getIndicadores() == null ? new String() : entidad.getIndicadores().toUpperCase());
                        pstmt.setInt(5, 3);
                        pstmt.setString(6, entidad.getSupuestos() == null ? new String() : entidad.getSupuestos().toUpperCase());
                        pstmt.setString(7, entidad.getMedios() == null ? new String() : entidad.getMedios().toUpperCase());
                        //pstmt.setString(8, indicador.getIndicadorId());
                        pstmt.setString(8, entidad.getIndicadorSEI() == null ? new String() : entidad.getIndicadorSEI());
                        pstmt.setInt(9, entidad.getComponente());
                       
                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible actualizar el elemento");
                        } else {
                            actualizo = true;
                        }
                     }
                    else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return actualizo;
    }
    
    public boolean insertMIRIndicadorActividad(Actividad entidad) throws IOException, Exception {
        boolean actualizo = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.insertMIRIndicadorActividad());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getStrYear());
                        pstmt.setString(2, entidad.getRamo());
                        pstmt.setString(3, entidad.getPrg());
                        pstmt.setString(4, entidad.getIndicadores() == null ? new String() : entidad.getIndicadores().toUpperCase());
                        pstmt.setInt(5, 4);
                        pstmt.setString(6, entidad.getSupuestos() == null ? new String() : entidad.getSupuestos().toUpperCase());
                        pstmt.setString(7, entidad.getMedios() == null ? new String() : entidad.getMedios().toUpperCase());
                        pstmt.setString(8, entidad.getIndicadorSEI() == null ? new String() : entidad.getIndicadorSEI());
                        pstmt.setInt(9, entidad.getComponente());
                        pstmt.setInt(10, entidad.getActividad());
                        
                        if (pstmt.executeUpdate() <= 0) {
                            this.setStrError("No fue posible actualizar el elemento");
                        } else {
                            actualizo = true;
                        }
                     }
                    else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return actualizo;
    }
    
    public boolean existeIndicadorFinProposito(Indicador indicador) throws IOException, Exception {
        boolean existeIndicador = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        ResultSet rs = null;
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.existeIndicadorFinProposito());
                    if (pstmt != null) {
                        pstmt.setString(1, indicador.getYear());
                        pstmt.setString(2, indicador.getRamo());
                        pstmt.setString(3, indicador.getPrograma());
                        pstmt.setInt(4, indicador.getDimension());

                        rs = pstmt.executeQuery();
                        if(rs != null && rs.next()){
                            existeIndicador = rs.getString("INDICADOR").equals("1");
                        }                        
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return existeIndicador;
    }
    
    public boolean existeIndicadorComponente(Componente entidad) throws IOException, Exception {
        boolean existeIndicador = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        ResultSet rs = null;
        
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.existeIndicadorComponente());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getStrYear());
                        pstmt.setString(2, entidad.getRamo());
                        pstmt.setString(3, entidad.getPrg());
                        pstmt.setInt(4, 3);
                        pstmt.setInt(5, entidad.getComponente());

                        rs = pstmt.executeQuery();
                        if(rs != null && rs.next()){
                            existeIndicador = rs.getString("INDICADOR").equals("1");
                        }                        
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return existeIndicador;
    }
    
    public boolean existeIndicadorActividad(Actividad entidad) throws IOException, Exception {
        boolean existeIndicador = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        ResultSet rs = null;
        
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.existeIndicadorActividad());
                    if (pstmt != null) {
                        pstmt.setString(1, entidad.getStrYear());
                        pstmt.setString(2, entidad.getRamo());
                        pstmt.setString(3, entidad.getPrg());
                        pstmt.setInt(4, 4);
                        pstmt.setInt(5, entidad.getComponente());
                        pstmt.setInt(6, entidad.getActividad());
                        rs = pstmt.executeQuery();
                        if(rs != null && rs.next()){
                            existeIndicador = rs.getString("INDICADOR").equals("1");
                        }                        
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return existeIndicador;
    }
    
    public List<Indicador> getIndicadorList(Indicador mir) throws IOException, Exception {
        List<Indicador> indicadorList = new ArrayList<Indicador>();
        Indicador indicador;
        ResultSet rsResult;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getIndicadoresList());
                    if (pstmt != null) {
                        pstmt.setString(1, mir.getYear());
                        pstmt.setString(2, mir.getRamo());
                        pstmt.setString(3, mir.getPrograma());
                        pstmt.setInt(4, mir.getDimension());

                        rsResult = pstmt.executeQuery();
                        
                        if(rsResult != null){
                            while(rsResult.next()){
                                indicador = new Indicador();
                                //indicador.setIndicadorId(rsResult.getString("CLAVE_INDICADOR"));
                                indicador.setIndicadorSEI(rsResult.getString("CLAVE_INDICADOR"));
                                indicador.setIndicadores(rsResult.getString("NOMBRE_INDICADOR"));
                                indicadorList.add(indicador);
                            }
                        }
                        
                     }
                    else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return indicadorList;
    }
    
    public Indicador getInformacionIndicador(Indicador indicador) throws IOException, Exception {
        ResultSet rsResult;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
             if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getInformacionIndicador());
                    if (pstmt != null) {
                        pstmt.setString(1, indicador.getYear());
                        pstmt.setString(2, indicador.getRamo());
                        pstmt.setString(3, indicador.getPrograma());
                        //pstmt.setString(4, indicador.getIndicadorId());
                        pstmt.setString(4, indicador.getIndicadorSEI());

                        rsResult = pstmt.executeQuery();
                        
                        if(rsResult != null){
                            while(rsResult.next()){
                                indicador = new Indicador();
                                indicador.setMedios(rsResult.getString("FUENTE_DATOS"));
                                indicador.setIndicadores(rsResult.getString("NOMBRE_INDICADOR"));
                            }
                        }
                        
                     }
                    else {
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return indicador;
    }
    
    // </editor-fold>
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="AUTORIZACION MIR">
    
    public int obtenerEstatusMIR(String year, String ramo, String programa) throws IOException, Exception {
        int intEstatus = -1;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        if (conectaBD != null) {       
            ResultSet rsResultado = null;
            PreparedStatement pstmt = null;
            try {   
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlEstatusMIR());
                if (pstmt != null) {
                    pstmt.setString(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);

                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {

                        while (rsResultado.next()) {
                            intEstatus = rsResultado.getInt(1);
                        }

                    }
                }
            } catch (Exception sqle) {
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                throw new Exception(sqle.getMessage());
            }  finally {
                rsResultado.close();
                pstmt.close();
            }
        }
        return intEstatus;
    }
    
    public boolean muestraBotonEnviarMIR(int estatusActual){
        boolean bMuestra = false;
        
        if(estatusActual == EnumEstatusMIR.BORRADOR.ordinal() ||
           estatusActual == EnumEstatusMIR.RECHAZADA.ordinal() ||
           estatusActual == EnumEstatusMIR.VALIDADA.ordinal() ||
           estatusActual == EnumEstatusMIR.RECHAZADA_POS.ordinal())
                bMuestra = true;
        
        return bMuestra;
    }
    
    public boolean muestraBotonValidarRechazarMIR(int estatusActual){
        boolean bMuestra = false;
        
        if(estatusActual == EnumEstatusMIR.ENVIADA.ordinal() ||
           estatusActual == EnumEstatusMIR.ENVIADA_POS.ordinal())
                bMuestra = true;
        
        return bMuestra;
    }
    
    public boolean editarMIR(int estatusActual, String rol, String rolNormativo){
        boolean bPermite = true;
                
        if((estatusActual == EnumEstatusMIR.ENVIADA.ordinal() ||
            estatusActual == EnumEstatusMIR.ENVIADA_POS.ordinal()  ||
            estatusActual == EnumEstatusMIR.VALIDADA_POS.ordinal() ) &&
            !rol.equals(rolNormativo)){
                bPermite = false;
        }
        
        return bPermite;
    }
    
    public boolean validaComponenteActividadMIR(String year, String ramo, String programa) throws IOException, Exception {
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        boolean bEnvia = false; 
        boolean bActividad = true; 
        int intCantMinComponente = 0;
        int intCantMinActividad = 0;
        int intComponente = 0;
        int intActividad = 0;
        if (conectaBD != null) {       
            ResultSet rsResultado = null;
            ResultSet rsResultadoAct = null;
            PreparedStatement pstmt = null;
            try {   
                
                intCantMinComponente = Integer.parseInt(getValorParametros("CANT_MIN_COMPONENTES"));
                intCantMinActividad = Integer.parseInt(getValorParametros("CANT_MIN_ACTIVIDADES"));
                        
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlComponentes());
                if (pstmt != null) {
                    pstmt.setString(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);

                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        while (rsResultado.next() && bActividad) {
                            bActividad = false;
                            intActividad = 0;
                            intComponente++;
                            pstmt = null;
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlActividades());
                            if (pstmt != null) {
                                pstmt.setString(1, year);
                                pstmt.setString(2, ramo);
                                pstmt.setString(3, programa);
                                pstmt.setInt(4, rsResultado.getInt("COMPONENTE"));

                                rsResultadoAct = pstmt.executeQuery();
                                if (rsResultadoAct != null) {
                                    if (rsResultadoAct.next()) {
                                        intActividad = rsResultadoAct.getInt(1);
                                        if(intActividad >= intCantMinActividad){
                                            bActividad = true;
                                        }
                                    }
                                }
                            }
                            rsResultadoAct.close();
                            rsResultadoAct = null;
                        }
                    }
                }
                
                if(intComponente >= intCantMinComponente && bActividad){
                    bEnvia = true;
                }
            } catch (Exception sqle) {
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                throw new Exception(sqle.getMessage());
            }  finally {
                rsResultado.close();
                pstmt.close();
            }
        }
        return bEnvia;
    }
    
    public boolean validaPropositoFinProblemaMIR(String year, String ramo, String programa) throws IOException, Exception {
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        boolean bEnvia = false; 
        if (conectaBD != null) {       
            ResultSet rsResultado = null;
            PreparedStatement pstmt = null;
            try {   
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaPropositoFinProblemaMIR());
                if (pstmt != null) {
                    pstmt.setString(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);

                    rsResultado = pstmt.executeQuery();
                    if (rsResultado != null) {
                        if (rsResultado.next()) {
                            if(rsResultado.getString("PROPOSITO") != null && !rsResultado.getString("PROPOSITO").trim().equals("") &&
                                    rsResultado.getString("FIN") != null && !rsResultado.getString("FIN").trim().equals("") &&
                                    rsResultado.getString("PROBLEMA_FOCAL") != null && !rsResultado.getString("PROBLEMA_FOCAL").trim().equals("")){
                                bEnvia = true;
                            }
                        }
                    }
                }
            } catch (Exception sqle) {
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                throw new Exception(sqle.getMessage());
            }  finally {
                pstmt.close();
                rsResultado.close();
            }
        }
        return bEnvia;
    }
    
    public boolean actualizarEstatusMIR(String year, String ramo, String programa, int estatus) throws IOException, Exception {
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        boolean res = false;
        if (conectaBD != null) {   
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSqlActualizarEstatusMIR());
                if (pstmt != null) {
                    pstmt.setInt(1, estatus);
                    pstmt.setString(2, year);
                    pstmt.setString(3, ramo);
                    pstmt.setString(4, programa);

                    if (pstmt.executeUpdate() > 0) {
                        res = true;
                    }
                    else{
                        this.setStrError("No fue posible actualizar el elemento");
                    }
                }  
                else{
                    this.setStrError("No fue posible actualizar el elemento");
                }
            } catch (Exception sqle) {
                try {
                    conectaBD.transaccionRollback();
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                throw new Exception(sqle.getMessage());
            } finally {
                pstmt.close();
            }
        }
        return res;
    }
   
    public boolean registraObsRechazoMIR(String year, String ramo, String programa, String observacion) throws IOException, Exception {
        boolean res = false;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
            if (conectaBD != null) {       
                PreparedStatement pstmt = null;
                try {   
                    pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLRegistraObsRechazoMIR());
                    if (pstmt != null) {
                        pstmt.setString(1, year);
                        pstmt.setString(2, ramo);
                        pstmt.setString(3, programa);
                        pstmt.setString(4, observacion);

                        if (pstmt.executeUpdate() > 0) {
                            res = true;
                        }else{
                            this.setStrError("No fue posible insertar el elemento");
                        }
                     }
                    else{
                        this.setStrError("No fue posible insertar el elemento");
                    }
                } catch (Exception sqle) {
                    try {
                        conectaBD.transaccionRollback();
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    throw new Exception(sqle.getMessage());
                } finally {
                    this.desconectaDblink("SPP");
                    pstmt.close();
                }
            }
        return res;
    }
   
    public String getObsRechazoMIR(String year, String ramo, String programa) throws Exception {
        ResultSet rsResult = null;
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        String observacion = new String();
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLGetObsRechazoMIR());
                if (pstmt != null) {
                    pstmt.setString(1, year);
                    pstmt.setString(2, ramo);
                    pstmt.setString(3, programa);
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null) {
                        if (rsResult.next()) {
                            observacion = rsResult.getString("OBSERVACION");
                        }
                    }
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
            finally{
                rsResult.close();
                pstmt.close();
            }
        }
        return observacion;
    }
    
    public Resultado validaDimensionesMIR(MIR mir) throws Exception{
        ResultSet rsResult = null;
        Resultado resultado = new Resultado("Ocurrió un error al validar dimensiones de MIR", false);
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        
        boolean valido = false;
        
        if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaDimensionFinProposito());
                if (pstmt != null) {
                    pstmt.setString(1, mir.getStrYear());
                    pstmt.setString(2, mir.getRamo());
                    pstmt.setString(3, mir.getPrg());
                    rsResult = pstmt.executeQuery();
                    if (rsResult != null && rsResult.next()) {
                        valido = rsResult.getString("INDICADOR").equals("1");
                    }
                    if(valido){           
                        pstmt.close();
                        rsResult.close();
                        pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaDimensionComponente());
                        if(pstmt != null){
                            //Parametros para MIR_COMPONENTE
                            pstmt.setString(1, mir.getStrYear());
                            pstmt.setString(2, mir.getRamo());
                            pstmt.setString(3, mir.getPrg());
                            //Parametros para MIR_INDICADOR
                            pstmt.setString(4, mir.getStrYear());
                            pstmt.setString(5, mir.getRamo());
                            pstmt.setString(6, mir.getPrg());
                            rsResult = pstmt.executeQuery();
                            if (rsResult != null && rsResult.next()) {
                                valido = rsResult.getString("COMPONENTE").equals("1");
                            }
                            if(valido){
                                pstmt.close();
                                rsResult.close();
                                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaDimensionActividad());
                                if(pstmt != null){
                                    //Parametros para MIR_ACTIVIDAD
                                    pstmt.setString(1, mir.getStrYear());
                                    pstmt.setString(2, mir.getRamo());
                                    pstmt.setString(3, mir.getPrg());
                                    //Parametros para MIR_INDICADOR
                                    pstmt.setString(4, mir.getStrYear());
                                    pstmt.setString(5, mir.getRamo());
                                    pstmt.setString(6, mir.getPrg());
                                    rsResult = pstmt.executeQuery();
                                    if (rsResult != null && rsResult.next()) {
                                        valido = rsResult.getString("ACTIVIDAD").equals("1");
                                    }
                                    if(valido){
                                        resultado = validaMIRIndicadorSEI(mir);
                                    }else
                                       resultado = new Resultado("Todas las actividades deden de estar relacionadas a un indicador",valido); 
                                }
                            }else
                               resultado = new Resultado("Todos los componentes deden de estar relacionados a un indicador",valido); 
                        }
                    }else{
                        resultado = new Resultado("El Fin y Propósito deden de estar relacionados a un indicador",valido);
                    }
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
            finally{
                rsResult.close();
                pstmt.close();
            }
        }
        return resultado;
    }
    
    public Resultado validaMIRIndicadorSEI(MIR mir) throws Exception{
        IndicadorGeneralSei indicador;
        List<IndicadorGeneralSei> indicadorList = new ArrayList<IndicadorGeneralSei>();
        ResultSet rsResult = null;
        Resultado resultado = new Resultado("Ocurrió un error al validar dimensiones de MIR", false);
        QuerysBDCatalogos query = new QuerysBDCatalogos();
        
        boolean valido = false;
         if (conectaBD != null) {
            PreparedStatement pstmt = null;
            try {
                pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaDatosIndicadorMIR());
                if(pstmt != null){
                    pstmt.setString(1, mir.getStrYear());
                    pstmt.setString(2, mir.getRamo());
                    pstmt.setString(3, mir.getPrg());
                    rsResult = pstmt.executeQuery();
                    if(rsResult != null){
                        while(rsResult.next()){
                            indicador = new IndicadorGeneralSei();
                            indicador.setYear(rsResult.getString("YEAR"));
                            indicador.setRamo(rsResult.getString("RAMO"));
                            indicador.setProg(rsResult.getString("PRG"));
                            indicador.setClaveIndicador(rsResult.getString("INDICADOR"));
                            indicadorList.add(indicador);
                        }
                    }
                    if(indicadorList.isEmpty()){
                        resultado = new Resultado("", true);
                    }else{
                        pstmt.close();
                        rsResult.close();
                        for(IndicadorGeneralSei indic : indicadorList){
                            pstmt = conectaBD.getConConexion().prepareStatement(query.getSQLValidaDatosIndicadorSEIMIR());
                            if(pstmt != null){
                                pstmt.setString(1, indic.getYear());
                                pstmt.setString(2, indic.getRamo());
                                pstmt.setString(3, indic.getProg());
                                pstmt.setString(4, indic.getClaveIndicador());
                                rsResult = pstmt.executeQuery();
                                if(rsResult != null && rsResult.next()){
                                    valido = rsResult.getString("INDICADOR").equals("1");
                                }
                            }
                            if(!valido){
                                resultado = new Resultado("Todos los indicadores relacionados a la MIR deben de contener Descripción, Medio de verificación y supuesto; o estar relaciondo a un indicador SEI", valido);
                                break;
                            }
                        }
                        
                    }
                }
                if(valido)
                    resultado = new Resultado("", valido);
            }catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
            finally{
                rsResult.close();
                pstmt.close();
            }
         }
        return resultado;
    }
    
    // </editor-fold>
}
