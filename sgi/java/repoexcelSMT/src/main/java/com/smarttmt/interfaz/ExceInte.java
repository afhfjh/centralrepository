
package com.smarttmt.interfaz;

/**
 * Esta interfaz tiene un metodo que se encarga de escribir un archivo excel 
 * 
 * @author adalbertdavidaroca
 */
public interface ExceInte {
    /**
     *  
     * Se crea este metodo para realizar la escritura de un archivo en excel, 
     * apartir de un formato o plantilla existente en la hoja de EXCEL
     * @param pathPlan ruta de la plantilla
     * @param pathDeex ruta destino donde se va almacenar la plantilla. 
     * @param pathFida ruta del archivo de datos
     * @param separato separador que se usa para identificar las columnas en el
     *                 archivo de datos
     * 
     * @throws Exception indica que el metodo genera excepciones que deben
     * ser capturadas para identificar cuando la misma no funcione bien.
     */
    public void writExce(String pathPlan,String pathDeex,String pathFida,String separato) throws Exception;
    
}
