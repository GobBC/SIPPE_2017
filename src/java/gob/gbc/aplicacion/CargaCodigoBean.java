/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.LongitudCodigo;
import gob.gbc.entidades.Ppto;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

/**
 *
 * @author ugarcia
 */
public class CargaCodigoBean extends ResultSQL {

    public String bError = new String();
    Bitacora bitacora;
    public int renglonError = 0;

    public CargaCodigoBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);

        bitacora = new Bitacora();
    }

    public int getValidaRelLaboral(String relLaboral) {
        int rel = 0;
        try {
            rel = super.getResultSQLGetcalidaRelLaboral(relLaboral);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return rel;
    }

    public String cargaDatosArchivo(List<Ppto> codigo) {
        String resultado = new String();
        if (codigo != null) {
            resultado = this.insertPresupPlantillaMasivo(codigo);
        }
        return resultado;
    }

    public boolean isAccionRegistrada(int year, String ramo, String origen) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisAccionRegistrada(year, ramo, origen, this.isParaestatal());
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean insertaCodigosFaltantes(int year, String ramo, String origen) {
        boolean resultado = false;
        try {
            resultado = super.getInsertaCodigosFaltantes(year, ramo, origen, this.isParaestatal());
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean isCodigoRegistrado(int year, String ramo, String depto, String finalidad, String funcion,
            String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisCodigoCapturadoCarga(year, ramo, depto, finalidad, funcion,
                    subfuncion, prgConac, programa, tipoProy, proyecto, meta, accion, tipoGasto, fuente,
                    fondo, recurso, municipio, delegacion);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean isCodigoRepetido(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLisCodigoRepetido(year, ramo, depto, finalidad, funcion, subfuncion, prgConac,
                    programa, tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio,
                    delegacion, relLab);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean insertPresupPlantilla(String origen, int year, String ramo, String depto, String finalidad, String funcion,
            String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab,
            int mes, double cantidad) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertPresupPlantilla(origen, year, ramo, depto, finalidad,
                funcion, subfuncion, prgConac, programa, tipoProy, proyecto, meta, accion, partida,
                tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLab, mes, cantidad);
        return resultado;
    }

    public String callProcedureActualizaCodigoPPTO(int year, String ramo, String depto, String finalidad, String funcion, String subFuncion, String progConac, String programa, String tipoProyecto, int proyecto, int meta, int accion, String partida, String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLaboral, double cantXcostoUn,
            int mes, int existeCod, String usuario) {
        String mensaje = new String();
        try {
            mensaje = super.getRestulSQLCallActualizarCodPPTO(year, ramo, depto, finalidad, funcion, subFuncion, progConac,
                    programa, tipoProyecto, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio,
                    delegacion, relLaboral, cantXcostoUn, mes, existeCod, usuario, 0);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public boolean insertPptoPresupPlantilla(List<Ppto> pptoList) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertPPTOPresupPlantilla(pptoList);
        return resultado;
    }

    public boolean deletePresupPlabtilla(String origen, String ramo, int year) {
        boolean resultado = false;
        resultado = super.deletePresupPlantilla(origen, ramo, year);
        return resultado;
    }

    public boolean isParaestatal() {
        boolean isParaestatal = false;
        try {
            isParaestatal = super.getResultSQLisParaestatal();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isParaestatal;
    }

    public boolean getCountPartidaPlantilla(String partida, int year) {
        boolean plantilla = false;
        try {
            plantilla = super.getResultSQLGetCountPartidaPlantilla(partida, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return plantilla;
    }

    public String insertPresupPlantillaMasivo(List<Ppto> codigo) {
        String resultado = new String();
        resultado = super.getResultSQLInsertPresupPlantillaMasivo(codigo);
        return resultado;
    }

    public boolean validaRamoParaestatal(String ramo, Sheet hojaXLS, int it) {
        boolean valida = false;
        int cols = 0;
        int temp;
        int row = 0;
        //Sheet hojaXLS;
        Row rows;
       // hojaXLS = archivo.getSheetAt(0);
        //row = hojaXLS.getPhysicalNumberOfRows();
        /*for (int it = 0; it < 10 || it > row; it++) {
         rows = hojaXLS.getRow(it);
         if (rows != null) {
         temp = hojaXLS.getRow(it).getPhysicalNumberOfCells();
         if (temp > cols) {
         cols = temp;
         }
         }
         }*/
        //for (int it = 0; it < row; it++) {
        rows = hojaXLS.getRow(it);
        if (rows != null && rows.getCell(1) != null) {
            if (rows.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                if (String.valueOf(new Double(rows.getCell(2).getNumericCellValue()).intValue()).trim().equals(ramo)) {
                    valida = true;
                } else {
                    valida = false;
                    //    break;
                }
            } else if (rows.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                if (String.valueOf(rows.getCell(2).getStringCellValue()).trim().equals(ramo)) {
                    valida = true;
                } else {
                    valida = false;
                    //    break;
                }
            }
        } else {
            //  break;
        }
        //}
        return valida;
    }

    public boolean validaRamoCentral(Sheet hojaXLS, int it) {
        boolean valida = false;
        int cols = 0;
        int temp;
        int row = 0;
        //Sheet hojaXLS;
        Row rows;
        //hojaXLS = archivo.getSheetAt(0);
        //row = hojaXLS.getPhysicalNumberOfRows();
        /*for (int it = 0; it < 10 || it > row; it++) {
         rows = hojaXLS.getRow(it);
         if (rows != null) {
         temp = hojaXLS.getRow(it).getPhysicalNumberOfCells();
         if (temp > cols) {
         cols = temp;
         }
         }
         }*/
        //for (int it = 0; it < row; it++) {
        rows = hojaXLS.getRow(it);
        if (rows != null && rows.getCell(0) != null) {
            if (!rows.getCell(0).getStringCellValue().equalsIgnoreCase("SIRHB")) {
                if (rows.getCell(0).getStringCellValue().equals("14")) {
                    valida = true;
                } else {
                    valida = false;
                    // break;
                }
            } else {
                valida = true;
                //break;
            }
        }
        //}
        return valida;
    }

    public List<Ppto> validaPartidaPlantilla(XSSFWorkbook archivo, int year, String ramo) {
        boolean valida = false;
        this.bError = new String();
        int fuenteFin = 0;
        int row = 0;
        boolean isParaestatal = false;
        String partida = new String();
        Sheet hojaXLS;
        Row rows;
        hojaXLS = archivo.getSheetAt(0);
        row = hojaXLS.getPhysicalNumberOfRows();
        List<Ppto> codigoPpto = new ArrayList<Ppto>();
        List<Ppto> codigoPptoT = new ArrayList<Ppto>();
        /*for (int it = 0; it < 10 || it > row; it++) {
         rows = hojaXLS.getRow(it);
         if (rows != null) {
         temp = hojaXLS.getRow(it).getPhysicalNumberOfCells();
         if (temp > cols) { 
         cols = temp;
         }
         }
         }*/
        ArrayList<String> arPartida = new ArrayList<String>();
        isParaestatal = this.isParaestatal();
        for (int it = 0; it < row; it++) {
            rows = hojaXLS.getRow(it);            
            renglonError = it;
            if (rows != null && rows.getCell(0) != null && !rows.getCell(0).getStringCellValue().isEmpty()) {
                if (arPartida.indexOf(rows.getCell(5).getStringCellValue().trim()) >= 0) {
                    partida = rows.getCell(5).getStringCellValue();
                } else {
                    if (this.getCountPartidaPlantilla(rows.getCell(5).getStringCellValue().trim(), year)) {
                        partida = rows.getCell(5).getStringCellValue().trim();
                    } else {
                        this.bError = "La partida " + rows.getCell(5).getStringCellValue().trim() + " en el archivo no se encuentra marcada como plantilla, no puede continuar el proceso";
                        break;
                    }
                    arPartida.add(rows.getCell(5).getStringCellValue().trim());
                }
                if (!partida.isEmpty()) {
                    fuenteFin = this.countFuenteFinanciamiento(year, rows.getCell(6).getStringCellValue().trim(), rows.getCell(7).getStringCellValue().trim(), rows.getCell(8).getStringCellValue().trim());
                    if (fuenteFin > 0) {
                        if (isParaestatal) {
                            valida = validaRamoParaestatal(ramo, hojaXLS, it);
                            if (valida) {
                                try {
                                    this.bError = new String();
                                    codigoPpto.addAll(verificaCodigoPlantillaParaestatal(year, hojaXLS, it));
                                    if(!this.bError.isEmpty()){
                                        break;
                                    }
                                } catch (Exception ex) {
                                    this.bError = "El codigo del renglon " + (it + 1) + " no tiene una acción asignada para la plantilla para el departamento";
                                    break;
                                }
                            } else {
                                this.bError = "Revisar que el layout de las columnas corresponda";
                                break;
                            }
                        } else {
                            valida = validaRamoCentral(hojaXLS, it);
                            if (valida) {
                                try {
                                    codigoPpto.addAll(verificarCodigoPlantilla(hojaXLS, it));
                                } catch (Exception ex) {
                                    this.bError = "El codigo del renglon " + (it + 1) + " no tiene una meta asignada para la plantilla";
                                    break;
                                }
                            } else {
                                this.bError = "Revisar que el layout de las columnas corresponda";
                                break;
                            }
                        }
                    } else {
                        this.bError = "La fuente de financiamiento:  " + rows.getCell(6).getStringCellValue().trim() + "-" + rows.getCell(7).getStringCellValue().trim() + "-" + rows.getCell(8).getStringCellValue().trim() + " no existe para el ejercicio " + year + ". La nomenclatura que debe de seguir para la fuente de financiamineto es la siguiente: X-XX-XX ";
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (!this.bError.equals("")) {
            return codigoPptoT;
        }
        return codigoPpto;
    }

    public boolean borrarCargaPlantilla(XSSFWorkbook archivo, String ramo, int year, String usuario) {
        boolean borrado = false;
        String mensaje = new String();
        List<Ppto> codigoList = new ArrayList<Ppto>();
        int cols = 0;
        int temp;
        int row = 0;
        int contRow = 0;
        String origen = new String();
        Sheet hojaXLS;
        Row rows;
        hojaXLS = archivo.getSheetAt(0);
        row = hojaXLS.getPhysicalNumberOfRows();
        /*for (int it = 0; it < 10 || it > row; it++) {
         rows = hojaXLS.getRow(it);
         if (rows != null) {
         temp = hojaXLS.getRow(it).getPhysicalNumberOfCells();
         if (temp > cols) {
         cols = temp;
         }
         }
         }*/
        rows = hojaXLS.getRow(0);
        origen = rows.getCell(0).getStringCellValue().trim();
        contRow = this.getCountPresupPlantill(year, ramo);
        if (contRow > 0) {
            /*codigoList = this.getCodigosParaBorrar(ramo, origen, year);
             if (codigoList.size() > 0) {
             for (Ppto codigo : codigoList) {
             mensaje = this.callProcedureActualizaCodigoPPTO(codigo.getYear(), codigo.getRamo(), codigo.getDepto(),
             codigo.getFinalidad(), codigo.getFuncion(), codigo.getSubfuncion(), codigo.getPrgConac(),
             codigo.getPrg(), codigo.getTipoProy(), Integer.parseInt(codigo.getProyectoId()), Integer.parseInt(codigo.getMetaId()), Integer.parseInt(codigo.getAccionId()),
             codigo.getPartida(), codigo.getTipoGasto(), codigo.getFuente(), codigo.getFondo(), codigo.getRecurso(),
             codigo.getMunicipio(), Integer.parseInt(codigo.getDelegacionId()), codigo.getRelLaboral(), codigo.getAsignado(), codigo.getMes(), -1, usuario);
             }
             }*/
            mensaje = this.actualizaPPTO(year, ramo, origen, false);
            if (mensaje.equals("exito")) {
                borrado = this.deleteAccionReqplantilla(origen, ramo, this.isParaestatal(), year);
                if (borrado) {
                    if (this.deletePresupPlabtilla(origen, ramo, year)) {
                        this.transaccionCommit();
                    } else {
                        this.transaccionRollback();
                    }
                } else {
                    this.transaccionRollback();
                }
            } else {
                this.transaccionRollback();//revisado
            }
        } else {
            borrado = true;
        }
        return borrado;
    }

    public List<Ppto> getCodigosParaBorrar(String ramo, String origen, int year) {
        List<Ppto> codigoList = new ArrayList<Ppto>();
        try {
            codigoList = super.getResultSQLCodigoPlantillaParaBorrar(ramo, origen, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return codigoList;
    }

    public String getDescrPartidaPlantilla(String ramo, String relLaboral, int year) {
        String descrPartida = new String();
        try {
            descrPartida = super.getResultSQLgetDescrPartidaPlantilla(ramo, relLaboral, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return descrPartida;
    }

    public boolean deleteAccionReqplantilla(String origen, String ramo, boolean isParaestatal, int year) {
        boolean resultado = false;
        resultado = super.getResultSQLDeleteAccionRequPlantilla(origen, ramo, year, isParaestatal);
        return resultado;
    }

    public List<Ppto> verificaCodigoPlantillaParaestatal(int yearAct, Sheet hojaXLS, int it) {
        int year = 0;
        String ramo = new String();
        String programa = new String();
        String depto = new String();
        String fuente = new String();
        String fondo = new String();
        String recurso = new String();
        List<Ppto> codigoList = new ArrayList<Ppto>();
        Ppto codigo;
        Ppto codTemp;
        int cols = 0;
        int temp;
        int row = 0;
        boolean bandera = true;
        //Sheet hojaXLS;
        Row rows;
        //longitud = this.getLongitudCodigo(yearAct);
        //hojaXLS = archivo.getSheetAt(0);
        //row = hojaXLS.getPhysicalNumberOfRows();
        /*for (int it = 0; it < 10 || it > row; it++) {
         rows = hojaXLS.getRow(it);
         if (rows != null) {
         temp = hojaXLS.getRow(it).getPhysicalNumberOfCells();
         if (temp > cols) {
         cols = temp;
         }
         }
         }*/
        try {
            /*for (int it = 0; it < row; it++) {*/
            rows = hojaXLS.getRow(it);
            if (!rows.getCell(0).getStringCellValue().isEmpty()) {
                codigo = new Ppto();
                if (rows != null) {
                    if (rows.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        year = new Double(rows.getCell(1).getNumericCellValue()).intValue();
                    } else if (rows.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
                        year = Integer.parseInt(rows.getCell(1).getStringCellValue());
                    }
                    if (rows.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        ramo = String.valueOf(new Double(rows.getCell(2).getNumericCellValue()).intValue()).trim();
                    } else if (rows.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                        ramo = rows.getCell(2).getStringCellValue().trim();
                    }
                    if (rows.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        programa = String.valueOf(new Double(rows.getCell(3).getNumericCellValue()).intValue()).trim();
                    } else if (rows.getCell(3).getCellType() == Cell.CELL_TYPE_STRING) {
                        programa = rows.getCell(3).getStringCellValue().trim();
                    }
                    if (rows.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        depto = String.valueOf(new Double(rows.getCell(4).getNumericCellValue()).intValue()).trim();
                    } else if (rows.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {
                        depto = rows.getCell(4).getStringCellValue().trim();
                    }
                    if (rows.getCell(6).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        fuente = String.valueOf(new Double(rows.getCell(6).getNumericCellValue()).intValue()).trim();
                    } else if (rows.getCell(6).getCellType() == Cell.CELL_TYPE_STRING) {
                        fuente = rows.getCell(6).getStringCellValue().trim();
                    }
                    if (rows.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        fondo = String.valueOf(new Double(rows.getCell(7).getNumericCellValue()).intValue()).trim();
                    } else if (rows.getCell(7).getCellType() == Cell.CELL_TYPE_STRING) {
                        fondo = rows.getCell(7).getStringCellValue().trim();
                    }
                    if (rows.getCell(8).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        recurso = String.valueOf(new Double(rows.getCell(8).getNumericCellValue()).intValue()).trim();
                    } else if (rows.getCell(8).getCellType() == Cell.CELL_TYPE_STRING) {
                        recurso = rows.getCell(8).getStringCellValue().trim();
                    }
                    codigo = this.completaCodigoPlantilla(year, ramo, programa, depto);
                    if (codigo != null) {
                        for (int i = 0; i < 12; i++) {
                            codTemp = new Ppto();
                            codTemp.setFuente(fuente);
                            codTemp.setFondo(fondo);
                            codTemp.setRecurso(recurso);
                            codTemp.setYear(codigo.getYear());
                            codTemp.setRamo(codigo.getRamo());
                            codTemp.setPrg(codigo.getPrg());
                            codTemp.setPrgConac(codigo.getPrgConac());
                            codTemp.setDepto(codigo.getDepto());
                            codTemp.setMetaId(codigo.getMetaId());
                            codTemp.setProyectoId(codigo.getProyectoId());
                            codTemp.setTipoProy(codigo.getTipoProy());
                            codTemp.setFinalidad(codigo.getFinalidad());
                            codTemp.setFuncion(codigo.getFuncion());
                            codTemp.setSubfuncion(codigo.getSubfuncion());
                            codTemp.setAccionId(codigo.getAccionId());
                            codTemp.setDelegacionId(codigo.getDelegacionId());
                            codTemp.setMunicipio(codigo.getMunicipio());
                            if (rows.getCell(i + 10).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                if(rows.getCell(i + 10).getNumericCellValue() >= 0)
                                    codTemp.setAsignado(rows.getCell(i + 10).getNumericCellValue());
                                else{
                                    this.bError = "El asignado no puede ser menor a cero";
                                    bandera = false;
                                    break;
                                }
                            } else if (rows.getCell(i + 10).getCellType() == Cell.CELL_TYPE_STRING) {
                                if(Double.parseDouble(rows.getCell(i + 10).getStringCellValue().trim()) > 0)
                                    codTemp.setAsignado(Double.parseDouble(rows.getCell(i + 10).getStringCellValue().trim()));
                                else{
                                    this.bError = "El asignado no puede ser menor a cero";
                                    bandera = false;
                                    break;
                                }
                            }
                            codTemp.setOrigen(rows.getCell(0).getStringCellValue());
                            if (rows.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                codTemp.setPartida(String.valueOf(rows.getCell(5).getNumericCellValue()).split("\\.")[0].trim());
                            } else if (rows.getCell(5).getCellType() == Cell.CELL_TYPE_STRING) {
                                codTemp.setPartida(rows.getCell(5).getStringCellValue().trim());
                            }
                            if (rows.getCell(9).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                codTemp.setRelLaboral(String.valueOf(rows.getCell(9).getNumericCellValue()).split("\\.")[0].trim().toUpperCase());
                            } else if (rows.getCell(9).getCellType() == Cell.CELL_TYPE_STRING) {
                                codTemp.setRelLaboral(rows.getCell(9).getStringCellValue().trim().toUpperCase());
                            }
                            codTemp.setTipoGasto(this.getTipoGastoPlantilla(codTemp.getPartida(), year));
                            codTemp.setMes(i + 1);
                            codigoList.add(codTemp);
                        }
                    } else {
                        codigoList = null;
                        //  break;
                    }
                }
            } else {
                //   break;
            }
            if(!bandera){
                codigoList = null;
            }
            //  }
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return codigoList;
    }

    public String getTipoGastoPlantilla(String partida, int year) {
        String tipoGasto = new String();
        try {
            tipoGasto = super.getResultSQLgetTipoDeGastoByPartidaPlantilla(partida, year);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return tipoGasto;
    }

    public List<Ppto> verificarCodigoPlantilla(Sheet hojaXLS, int it) {
        bError = "";
        List<Ppto> codigoList = new ArrayList<Ppto>();
        int year = 0;
        String ramo = new String();
        String programa = new String();
        String depto = new String();
        String fuente = new String();
        String fondo = new String();
        String recurso = new String();
        Ppto codigo;
        int cols = 0;
        int relLab = 0;
        int row = 0;
        //Sheet hojaXLS;
        Row rows;
        //hojaXLS = archivo.getSheetAt(0);
        //row = hojaXLS.getPhysicalNumberOfRows();
        /*for (int it = 0; it < 10 || it > row; it++) {
         rows = hojaXLS.getRow(it);
         if (rows != null) {
         temp = hojaXLS.getRow(it).getPhysicalNumberOfCells();
         if (temp > cols) {
         cols = temp;
         }
         }
         }*/
        //for (int it = 0; it < row; it++) {
        rows = hojaXLS.getRow(it);
        codigo = new Ppto();
        if (!rows.getCell(0).getStringCellValue().isEmpty()) {
            if (rows != null && (rows.getCell(0) != null || rows.getCell(2) != null || rows.getCell(3) != null
                    || rows.getCell(4) != null || rows.getCell(5) != null || rows.getCell(6) != null)) {
                if (rows.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    year = new Double(rows.getCell(1).getNumericCellValue()).intValue();
                } else if (rows.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
                    year = Integer.parseInt(rows.getCell(1).getStringCellValue());
                }
                if (rows.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    ramo = String.valueOf(new Double(rows.getCell(2).getNumericCellValue()).intValue());
                } else if (rows.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                    ramo = rows.getCell(2).getStringCellValue();
                }
                if (rows.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    programa = String.valueOf(new Double(rows.getCell(3).getNumericCellValue()).intValue());
                } else if (rows.getCell(3).getCellType() == Cell.CELL_TYPE_STRING) {
                    programa = rows.getCell(3).getStringCellValue();
                }
                if (rows.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    depto = String.valueOf(new Double(rows.getCell(4).getNumericCellValue()).intValue());
                } else if (rows.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {
                    depto = rows.getCell(4).getStringCellValue();
                }
                if (rows.getCell(6).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    fuente = String.valueOf(new Double(rows.getCell(6).getNumericCellValue()).intValue());
                } else if (rows.getCell(6).getCellType() == Cell.CELL_TYPE_STRING) {
                    fuente = rows.getCell(6).getStringCellValue();
                }
                if (rows.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    fondo = String.valueOf(new Double(rows.getCell(7).getNumericCellValue()).intValue());
                } else if (rows.getCell(7).getCellType() == Cell.CELL_TYPE_STRING) {
                    fondo = rows.getCell(7).getStringCellValue();
                }
                if (rows.getCell(8).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    recurso = String.valueOf(new Double(rows.getCell(8).getNumericCellValue()).intValue());
                } else if (rows.getCell(8).getCellType() == Cell.CELL_TYPE_STRING) {
                    recurso = rows.getCell(8).getStringCellValue();
                }
                codigo = this.completaCodigoPlantilla(year, ramo, programa, depto);
                if (codigo != null) {
                    try {
                        codigo.setFuente(fuente);
                        codigo.setFondo(fondo);
                        codigo.setRecurso(recurso);
                        codigo.setOrigen(rows.getCell(0).getStringCellValue());
                        if (rows.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            codigo.setPartida(String.valueOf(rows.getCell(5).getNumericCellValue()).split("\\.")[0]);
                        } else if (rows.getCell(5).getCellType() == Cell.CELL_TYPE_STRING) {
                            codigo.setPartida(rows.getCell(5).getStringCellValue());
                        }
                        if (rows.getCell(9).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            codigo.setRelLaboral(String.valueOf(rows.getCell(9).getNumericCellValue()).split("\\.")[0]);
                        } else if (rows.getCell(9).getCellType() == Cell.CELL_TYPE_STRING) {
                            codigo.setRelLaboral(rows.getCell(9).getStringCellValue());
                        }
                        if (rows.getCell(10).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            codigo.setMes(new Double(rows.getCell(10).getNumericCellValue()).intValue());
                        } else if (rows.getCell(10).getCellType() == Cell.CELL_TYPE_STRING) {
                            codigo.setMes(Integer.parseInt(rows.getCell(10).getStringCellValue()));
                        }
                        if (rows.getCell(11).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            codigo.setAsignado(rows.getCell(11).getNumericCellValue());
                        } else if (rows.getCell(11).getCellType() == Cell.CELL_TYPE_STRING) {
                            codigo.setAsignado(Double.parseDouble(rows.getCell(11).getStringCellValue()));
                        }
                        relLab = getValidaRelLaboral(codigo.getRelLaboral());
                        if (relLab < 1) {
                            bError = "Existe una relación laboral en el archivo Excel que no existe: " + codigo.getRelLaboral();
                        }
                        codigo.setTipoGasto(this.getTipoGastoPlantilla(codigo.getPartida(), year));
                        //codigo.setAsignado(rows.getCell(11).getNumericCellValue());
                        codigoList.add(codigo);
                    } catch (IllegalStateException ex) {
                        bitacora.setStrUbicacion(getStrUbicacion());
                        bitacora.setStrServer(getStrServer());
                        bitacora.setITipoBitacora(1);
                        bitacora.setStrInformacion(ex, new Throwable());
                        bitacora.grabaBitacora();
                    }
                } else {
                    codigoList = null;
                    // break;
                }
            }
        }
        //}
        return codigoList;
    }

    public Ppto completaCodigoPlantilla(int year, String ramo, String programa, String depto) {
        Ppto codigoList = new Ppto();
        boolean res = false;
        try {
            //res = super.getResultSQLCountCodigoPlantilla(year, ramo, programa, depto);
            //if (res) {
            codigoList = super.getResultSQLCodigoPlantillaCompleto(year, ramo, programa, depto);
            /*} else {
             codigoList = null;
             }*/
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return codigoList;
    }

    public List<Ppto> getGruposPresupPlantilla(String origen) {
        List<Ppto> presupList = new ArrayList<Ppto>();
        try {
            presupList = super.getResultSQLGetGruposPresupPlantilla(origen);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return presupList;
    }
    /*public List<Ppto> getGruposPresupPlantillaSinLista(String origen) {

        List<Ppto> presupList = new ArrayList<Ppto>();
        try {
            presupList = super.getResultSQLGetGruposPresupPlantillasinLista(origen);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return presupList;
    }*/

    public boolean insertarCodigosPPTO(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion, String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab) {
        boolean resultado = false;
        if (!this.isCodigoRepetido(year, ramo, depto, finalidad, funcion, subfuncion, prgConac, programa,
                tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio,
                delegacion, relLab)) {
            resultado = super.getResultSQLInsertCodigo(year, ramo, depto, finalidad, funcion, subfuncion, prgConac, programa,
                    tipoProy, proyecto, meta, accion, partida, tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLab);
        } else {
            resultado = false;
        }
        return resultado;
    }

    public List<Ppto> getRequerimientoPresupPlantilla(Ppto pres) {
        List<Ppto> presupList = new ArrayList<Ppto>();
        try {
            presupList = super.getResultSQLGetRequerimientoPresupPlantilla(pres);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return presupList;
    }

    public boolean insertRequerimientoPresupPlantilla(Ppto reqPres) {
        boolean resultado = false;
        int reqId = 0;
        try {
            reqId = super.getResultSQLMaxRequerimiento(reqPres.getYear(),
                    reqPres.getRamo(), reqPres.getMeta(), reqPres.getAccion());
            reqPres.setDescrPlantilla(this.getDescrPartidaPlantilla(
                    reqPres.getPartida(), reqPres.getRelLaboral(), reqPres.getYear()));
            resultado = super.getResultSQLInsertRequerimientoPresupPlantilla(reqPres, reqId);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean updateEstatusPresupPlantilla() {
        boolean resultado = false;
        resultado = super.getResultSQLUpdateEstatusPresupPlantilla();
        return resultado;
    }

    public boolean deletePresupPlantilla() {
        boolean resultado = false;
        resultado = super.getResultSQLDeletePresupPlantilla();
        return resultado;
    }

    public boolean insertCodigo(int year, String ramo, String depto, String finalidad, String funcion, String subfuncion,
            String prgConac, String programa, String tipoProy, int proyecto, int meta, int accion, String partida,
            String tipoGasto, String fuente, String fondo, String recurso, String municipio, int delegacion, String relLab) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertCodigo(year, ramo, depto, finalidad,
                funcion, subfuncion, prgConac, programa, tipoProy, proyecto, meta,
                accion, partida, tipoGasto, fuente, fondo, recurso, municipio, delegacion, relLab);
        return resultado;
    }

    public LongitudCodigo getLongitudCodigo(int year) {
        LongitudCodigo longitud = new LongitudCodigo();
        try {
            longitud = super.getRestultGetLongitudCodigo(year);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return longitud;
    }

    public int getCountPresupPlantill(int year, String ramo) {
        int presup = 0;
        try {
            presup = super.getResultSQLGetCountPresupPlantilla(year, ramo);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return presup;
    }

    public int getCountPresupPlantill(int year, String ramo, String origen) {
        int presup = 0;
        try {
            presup = super.getResultSQLGetCountPresupPlantilla(year, ramo, origen);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return presup;
    }

    public int getCountProyeccion(int year, String ramo) {
        int presup = 0;
        try {
            presup = super.getResultSQLGetCountProyeccion(year, ramo);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return presup;
    }

    public boolean insertoPPTOFaltantes(int iYear, String sRamo, String sOrigen) {
        boolean resultado = false;
        resultado = super.getResultSQLInsertPPTOFaltantes(iYear, sRamo, sOrigen, this.isParaestatal());
        return resultado;
    }

    public String actualizaPPTO(int year, String ramo, String sOrigen, boolean bSuma) {
        String mensaje = new String();
        try {
            mensaje = super.getUpdatePPTO(year, ramo, sOrigen, this.isParaestatal(), bSuma);
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public String callValidaArchivo(int year, String ramo, String sOrigen) {
        String mensaje = new String();
        try {
            mensaje = super.getRestulSQLValidaCarga(year, ramo, sOrigen, this.isParaestatal());
        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return mensaje;
    }

    public int countFuenteFinanciamiento(int year, String fuente, String fondo, String recurso) {
        int fuenteFin = 0;
        try {
            fuenteFin = super.getResultSQLcountFuenteFinanciamianto(year, fuente, fondo, recurso);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return fuenteFin;
    }

    public int countPresupPlantillaCentral(int year, String origen) {
        int fuenteFin = 0;
        try {
            fuenteFin = super.getResultSQLcountPresupPlantillaCentral(year, origen);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return fuenteFin;
    }

    public boolean ejecutaPPTO(List<Ppto> presupList,int year,String ramo,String origen)throws Exception {
        int iRow=0;
        List<Mes> mesAsignad = null;
        String pptoTemp = "",strResultado="";
        double total=0;
        boolean resultado,bandera=true;        
        String badCode="";
        for (Ppto pres : presupList) {
            iRow++;
            /*
            if (iRow % 500 == 0) {
                System.out.println("CICLO " + new Date().toString() + "- " + iRow);
            }
            */
            if (pres != null) {
                mesAsignad = new ArrayList<Mes>();
                mesAsignad = pres.getdMeses();
                pptoTemp = pres.getPrg() + "-" + pres.getDepto() + "-" + pres.getMetaId() + "-" + pres.getPartida() + "-" + pres.getFuente() + "-" + pres.getFondo() + "-" + pres.getRecurso() + "-" + pres.getRelLaboral();
                total = 0;
                for (Mes mes : mesAsignad) {
                    total += mes.getdImporte();
                }
                if (total > 0) {
                    resultado = this.insertRequerimientoPresupPlantilla(pres);
                    if (resultado) {
                        resultado = true;
                        int iCont = 0;
                        //Mes pptoList;                                                                            
                    } else {
                        this.transaccionRollback();
                        bandera = false;
                        strResultado += "0 | Ocurrió un error al cargar la información en ACCION_REQ";
                    }
                } else {
                    this.transaccionRollback();
                    bandera = false;
                    strResultado += "0 | Existe un registro sin dinero asignado para el código: " + pptoTemp;
                    break;
                }

                /*} else {
                 badCode = pres.getYear() + "-" + pres.getRamo() + "-" + pres.getDepto() + "-"
                 + pres.getFinalidad() + "-" + pres.getFuncion() + "-" + pres.getSubfuncion() + "-" + pres.getPrgConac() + "-" + pres.getPrg() + "-"
                 + pres.getTipoProy() + "-" + pres.getProyecto() + "-" + pres.getMeta() + "-" + pres.getAccion() + "-" + pres.getPartida() + "-"
                 + pres.getTipoGasto() + "-" + pres.getFuente() + "-" + pres.getFondo() + "-" + pres.getRecurso() + "-" + pres.getMunicipio() + "-"
                 + pres.getDelegacion() + "-" + pres.getRelLaboral();
                 System.out.println(badCode + "**");
                 }*/
            } else {
                this.getConectaBD().transaccionRollback();
                bandera = false;
                badCode = pres.getYear() + "-" + pres.getRamo() + "-" + pres.getPrgConac() + "-" + pres.getPrg() + "-" + pres.getDepto() + "-"
                        + pres.getFinalidad() + "-" + pres.getFuncion() + "-" + pres.getSubfuncion() + "-" + pres.getPrgConac() + "-" + pres.getPrg() + "-"
                        + pres.getTipoProy() + "-" + pres.getProyecto() + "-" + pres.getMeta() + "-" + pres.getAccion() + "-" + pres.getPartida() + "-"
                        + pres.getTipoGasto() + "-" + pres.getFuente() + "-" + pres.getFondo() + "-" + pres.getRecurso() + "-" + pres.getMunicipio() + "-"
                        + pres.getDelegacion() + "-" + pres.getRelLaboral();
                strResultado += "0 | Hay un código no existente en el archivo | " + badCode;
                break;
            }
        }
        if (bandera) {
            String sError = "";
            sError = this.actualizaPPTO(year, ramo, origen, true);
            if (sError.equals("exito")) {
                bandera = true;
                System.out.println("TERMINO " + new Date().toString());
            } else {
                bandera = false;
                strResultado += "0 | No se logro actualizar PPTO | " + badCode;
            }
        }
        sMensajeAdic = strResultado;
        return bandera;
        /* if (bandera)
         cargaBean.transaccionCommit();//REVISADO*/
    }
    public String sMensajeAdic = "";
}
