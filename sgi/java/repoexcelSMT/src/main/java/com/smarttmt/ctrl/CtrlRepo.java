/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarttmt.ctrl;

import com.smarttmt.interfaz.ExceInte;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author administrador
 */
public class CtrlRepo implements ExceInte, Serializable {

    private static final Logger LOGGER = LogManager.getLogger(CtrlRepo.class);

    @Override
    public void writExce(String pathPlan, String pathDeex, String pathFida, String separato) throws Exception {

        LOGGER.info("Iniciando escritura del archivo en excel");
        LOGGER.debug("Ruta de la plantilla {}", pathPlan);
        LOGGER.debug("Ruta donde se va a escribir la plantilla {} ", pathDeex);
        LOGGER.debug("Ruta de donde se van a obtener los datos {} ", pathFida);
        LOGGER.debug("Separador para identificar columnas por cada registro del archivo de datos {} ", separato);
        //Archivo Origen
        File archOrig = null;
        //Archivo  Destino
        File archDest = null;
        //ruta completa de la plantilla
        String pathDefi = pathDeex.substring(0, pathDeex.lastIndexOf('/'));
        //Registra del archivo de excel
        Row row = null;
        //Celda en el archivo de excel
        Cell cell;
        //Hoja de excel
        Sheet sheet = null;
        //Numero de hojas en el libro de excel
        int numberOfSheets;
        // Fila  
        int fila = 0;
        // Columna
        int columna = 0;
        //valor celda
        String valor;
        //lineas del archivo
        List<String> lines = null;
        //cambio del set de caracteres por defecto
        boolean chanseca = false;
        //set de caracteres
        Charset charset = null;

        try {
            archOrig = new File(pathPlan);

            if (!archOrig.exists()) {
                LOGGER.debug("Plantilla no existe en la ruta {} ", pathPlan);
                throw new IOException("La plantilla no existe en la ruta " + pathPlan);
            }

            archDest = new File(pathDefi);

            if (!archDest.exists()) {
                LOGGER.debug("Ruta no existe donde se va a depositar el excel {} , se va a crear", pathDefi);
                archDest.mkdirs();
            }

            LOGGER.info("Ruta del archivo a crear {}", pathDeex);
            archDest = new File(pathDeex);

            if (!archDest.exists()) {
                LOGGER.info("No existe el archivo en la ruta  {}, se procede a la creacion ", pathDeex);
                archDest.createNewFile();
            } else {

                LOGGER.info("el archivo que se requiere crear, ya existe {} se va a recrear", pathDeex);
                archDest.delete();
                LOGGER.info("archivo en la ruta {}, borrado", pathDeex);
                archDest.createNewFile();
                LOGGER.info("archivo en la ruta {}, se vuelve a crear", pathDeex);

            }

            LOGGER.info("Se inicia con la copia de la plantilla de la ruta {} a la ruta {} ", pathPlan, pathDeex);
            try (FileChannel archTror = new FileInputStream(archOrig).getChannel();
                    FileChannel archTrDe = new FileOutputStream(archDest).getChannel();) {

                archTrDe.transferFrom(archTror, 0, archTror.size());

                LOGGER.info("Termina la copia del archivo");

            } catch (Exception e) {
                LOGGER.info("Se genera un error con la transferencia {} ", e.getMessage());
                throw new Exception("Error [" + e.getMessage() + "]");
            }

            LOGGER.info("Se inicia con el diligenciamiento del formato ");

            LOGGER.info("Nombre Archivo {}", archDest.getName());
            if (!archDest.getName().toLowerCase().endsWith("xls")) {
                throw new Exception("La plantilla debe tener extension xls");
            }

            try (FileInputStream fis = new FileInputStream(archDest);
                    Workbook workbook = new HSSFWorkbook(fis);
                    FileOutputStream fos = new FileOutputStream(archDest);) {

                if (workbook != null) {
                    numberOfSheets = workbook.getNumberOfSheets();
                    LOGGER.debug("Numero de hojas {}", numberOfSheets);

                    sheet = workbook.getSheetAt(0);
                    LOGGER.info("Hoja seleccionada:{}", 0);

                    try {


                        /* try (BufferedReader inputStream = new BufferedReader(new FileReader(pathFida))
                        //PrintWriter outputStream = null;
                        ) { //outputStream = new PrintWriter(new FileWriter("characteroutput.txt"));
                                                        //outputStream = new PrintWriter(new FileWriter("characteroutput.txt"));

                            String l = null;
                            while ((l = inputStream.readLine()) != null) {
                                System.out.println(" "+l);
                            }
                        }*/
 /* 
                        LOGGER.debug("Se prepara para recorrer lineas {} ",charset);*/
                        try {
                            lines = Files.readAllLines(Paths.get(pathFida));
                        } catch (Exception e) {
                            LOGGER.warn("El set de caracteres por defecto no funciono {}", e.getMessage());
                            chanseca = true;
                        }
                        
                        if(chanseca){
                            charset = Charset.forName("ISO-8859-1");
                            lines = Files.readAllLines(Paths.get(pathFida),charset);
                        }

                        LOGGER.debug("Empieza a recorrer lineas");
                        for (String line : lines) {
                            LOGGER.debug("Linea {}", line);
                            String[] parts = line.split("[" + separato + "]");

                            if (parts.length > 0) {

                                fila = Integer.parseInt(parts[1].trim()) - 1;
                                LOGGER.debug("fila {}", fila);
                                columna = Integer.parseInt(parts[2].trim()) - 1;
                                LOGGER.debug("columna {}", columna);
                                valor = parts[3].trim() != null ? parts[3].trim() : "";
                                LOGGER.debug("valor {}", valor);
                                row = sheet.getRow(fila);
                                cell = row.getCell(columna);
                                if (cell != null) {
                                    cell.setCellValue(valor);
                                } else {
                                    cell = row.createCell(columna);
                                    cell.setCellValue(valor);
                                }
                            }

                            LOGGER.debug("Termino linea");

                        }
                        /*Charset charset = Charset.forName("ISO-8859-1");
                        LOGGER.debug("Se prepara para recorrer lineas {} ",charset);
                        List<String> lines = Files.readAllLines(Paths.get(pathFida), charset);
                        LOGGER.debug("Empieza a recorrer lineas");
                        for (String line : lines) {
                        LOGGER.debug("Linea {}", line);
                        String[] parts = line.split("[" + separato + "]");
                        if (parts.length > 0) {
                        fila = Integer.parseInt(parts[1].trim())-1;
                        LOGGER.debug("fila {}", fila);
                        columna = Integer.parseInt(parts[2].trim())-1;
                        LOGGER.debug("columna {}", columna);
                        valor = parts[3].trim() != null ? parts[3].trim() : "";
                        LOGGER.debug("valor {}", valor);
                        row = sheet.getRow(fila);
                        cell = row.getCell(columna);
                        if (cell != null) {
                        cell.setCellValue(valor);
                        } else {
                        cell = row.createCell(columna);
                        cell.setCellValue(valor);
                        }
                        }
                        LOGGER.debug("Termino linea");
                        }*/                    } catch (IOException | NumberFormatException e) {
                        throw new Exception("Error Leyendo archivo de datos y/o escribiendo en el excel: " + e.getMessage() + " ");
                    }
                    workbook.write(fos);
                    LOGGER.debug("Termino el proceso de escritura de la plantilla");
                } else {
                    throw new Exception("No se cargo de manera adecuada el archivo ");
                }

            } catch (Exception e) {
                throw new Exception("Error en el proceso de copiado de plantilla [" + e.getMessage() + "]");
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } 
    }

    public static void main(String[] args) {

        try {
            if (args.length == 4) {
                ExceInte exceInte = new CtrlRepo();

                exceInte.writExce(args[0], args[1], args[2], args[3]);
            } else {
                System.out.println("Debe enviar los paramertros de manera correcta");
                System.out.println("1. Parametro Path plantilla");
                System.out.println("2. Parametro Path destino");
                System.out.println("3. Parametro Path archivo de datos");
                System.out.println("4. Parametro separador del archivo de datos");
            }

        } catch (Exception ex) {
            LOGGER.error("Error {}", ex.getMessage());
        }
    }

}
