/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Linea;
import gob.gbc.entidades.Programa;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.EnumProcesoEspecial;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ugarcia
 */
public class ProgramaBean extends ResultSQL {

    Bitacora bitacora;

    public ProgramaBean() throws Exception {
        super();

        bitacora = new Bitacora();
    }

    public ProgramaBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
    }

    public List<Programa> getProgramasByRamo(String ramoId, int year, String usuario) {
        List<Programa> programaList = new ArrayList<Programa>();
        try {
            programaList = super.getResultSQLProgramasByRamo(ramoId, year, usuario);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
            programaList = null;
        }
        return programaList;
    }

    public Programa getProgramaById(String programaId, String ramoId, int year) {
        Programa programa = new Programa();
        try {
            programa = super.getResultProgramaById(year, ramoId, programaId);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return programa;
    }

    public boolean updatePrograma(String programaId, String ramoId, String rfc, String homoclave,
            int fin, String proposito, int ponderado, int depto, int year, String[] lineas) {
        boolean resultado = false;
        resultado = super.getResultSQLActualizaPrograma(programaId, ramoId, year, rfc, homoclave,
                fin, proposito, ponderado, depto);
        if (resultado) {
            resultado = this.actualizaLineaAccion(year, ramoId, programaId, lineas);
        }
        return resultado;
    }

    public boolean validaRamo(String ramoId, int year, String rol) {
        boolean resultado = false;
        String rolV = new String();
        resultado = super.getResultSQLValidaRamo(ramoId, year);
        if (resultado) {
            rolV = this.getRoles();
            if (rol.equals(rolV)) {
                resultado = true;
            } else {
                resultado = false;
            }
        } else {
            resultado = true;
        }
        return resultado;
    }

    public boolean validaRamo(String ramoId, int year, String rol, String usuario) {
        boolean resultado = false;
        String rolV = new String();
        try {
            resultado = super.getResultSQLValidaRamoAvancePoa(ramoId, year);
            if (resultado) {
                rolV = this.getRoles();
                if (usuario.isEmpty()) {
                    if (!rol.equals(rolV)) {
                        resultado = false;
                    }
                } else {
                    resultado = super.getResultSQLhasProcesoEspecual(usuario, EnumProcesoEspecial.NORMATIVO_AVANCE.getProceso());
                }
            } else {
                resultado = true;
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean validaRamoDefinicionMetas(String ramoId, int year, String rol, String usuario) {
        boolean resultado = false;
        String rolV = new String();
        try {
            resultado = super.getResultSQLValidaRamo(ramoId, year);
            if (resultado) {
                rolV = this.getRoles();
                if (usuario.isEmpty()) {
                    if (!rol.equals(rolV)) {
                        resultado = false;
                    }
                } else {
                    resultado = super.getResultSQLhasProcesoEspecual(usuario, EnumProcesoEspecial.NORMATIVO_AVANCE.getProceso());
                }
            } else {
                resultado = true;
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public String getRoles() {
        String rol = new String();
        try {
            rol = super.getResultSQLGetRolesPrg();
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return rol;
    }

    public String formateaPrograma(String programa) {
        if (programa.length() == 3) {
            return programa;
        }
        if (programa.length() == 2) {
            programa = "0" + programa;
            return programa;
        }
        if (programa.length() == 1) {
            programa = "00" + programa;
            return programa;
        }
        return "000";
    }

    public List<Linea> getLineaRamoPrg(int year, String ramo, String programa) {
        List<Linea> lineaList = new ArrayList<Linea>();
        try {
            lineaList = super.getResultGetLineaRamoPrograma(year, ramo, programa);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<Linea> getAllLineaRamoPrg(int year, String ramo) {
        List<Linea> lineaList = new ArrayList<Linea>();
        try {
            lineaList = super.getResultGetAllLineaRamoPrograma(year, ramo);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public boolean actualizaLineaAccion(int year, String ramo, String programa, String[] lineas) {
        boolean resultado = false;
        int cont = 0;
        cont = this.countLineasAccion(year, ramo, programa);
        if (cont > 0) {
            resultado = super.getResultSQLDeleteLineaAccion(year, ramo, programa);
        }
        if (lineas.length > 0) {
            if (resultado || cont == 0) {
                resultado = super.getResultSQLInsertLineaAccion(year, ramo, programa, lineas);
            }
        }
        if (resultado) {
            super.transaccionCommit();
        }
        return resultado;
    }

    public int getCountLineaAccionMeta(int year, String ramo, String programa, String linea) {
        int contL = 0;
        try {
            contL = super.getResultSQLgetLineaAccionMeta(year, ramo, programa, linea);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return contL;
    }

    public int getCountLineaAccionAccion(int year, String ramo, String programa, String linea) {
        int contL = 0;
        try {
            contL = super.getResultSQLgetLineaAccionAccion(year, ramo, programa, linea);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return contL;
    }

    public int countLineasAccion(int year, String ramo, String programa) {
        int cont = 0;
        cont = super.getResultSQLContLineasACcion(year, ramo, programa);
        return cont;
    }

    public List<Programa> getProgramasByRamoInList(String ramoInList, int year, String usuario) {
        List<Programa> programaList = new ArrayList<Programa>();
        try {
            programaList = super.getResultSQLProgramasByRamoInList(ramoInList, year, usuario);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
            programaList = null;
        }
        return programaList;
    }

    public boolean validaRamoAvancePoa(String ramoId, int year, String rol) {
        boolean resultado = false;
        String rolV = new String();
        resultado = super.getResultSQLValidaRamoAvancePoa(ramoId, year);
        if (resultado) {
            rolV = this.getRoles();
            if (rol.equals(rolV)) {
                resultado = true;
            } else {
                resultado = false;
            }
        } else {
            resultado = true;
        }
        return resultado;
    }

    public ArrayList<Programa> getProgramasByYearAppLogin(int year, String usuario, String sRamo, String sClaveIndicador) {
        ArrayList<Programa> programaList = new ArrayList<Programa>();
        try {
            programaList = super.getResultSQLProgramasByYearAppLogin(year, usuario, sRamo, sClaveIndicador);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
            programaList = null;
        }
        return programaList;
    }

}
