Ejempor de Generar una Archiov en Excel Para Ipro
Codigo PLSSQL

DECLARE
  CURSOR cuBase (P_sbTian VARCHAR2)
  IS
  SELECT *
    FROM OW_VALPROD.PROYECCI
   WHERE ProyTian = P_sbTian
   ORDER BY ProySecu;
   
   v_inFila NUMBER; 
BEGIN
   v_inFila := 0;
  --pkgutfile.proCerrar;   
   FOR rg IN cuBase ('RECA') LOOP
     IF (v_inFila = 0) THEN
       pkgutfile.proAbrir (P_sbDire => 'IDU_MIGRA',
                           P_sbArch => 'reca001.txt'
                          );
      v_inFila := rg.ProySecu;
      END IF;
      v_inFila := v_inFila + 1;
      pkgutfile.proCelda(v_inFila,1,rg.ProyCodi) ; 
      pkgutfile.proCelda(v_inFila,2,rg.ProyDesc) ;
      pkgutfile.proCelda(v_inFila,3,rg.ProyVa01) ;
      pkgutfile.proCelda(v_inFila,4,rg.ProyVa02) ;
      pkgutfile.proCelda(v_inFila,5,rg.ProyVa03) ;
      pkgutfile.proCelda(v_inFila,6,rg.ProyVa04) ;
      pkgutfile.proCelda(v_inFila,7,rg.ProyVa05) ;
      pkgutfile.proCelda(v_inFila,8,rg.ProyVa06) ;
      pkgutfile.proCelda(v_inFila,9,rg.ProyVa07) ;
      pkgutfile.proCelda(v_inFila,10,rg.ProyVa08) ;
      pkgutfile.proCelda(v_inFila,11,rg.ProyVa09) ;
      pkgutfile.proCelda(v_inFila,12,rg.ProyVa10) ;
      pkgutfile.proCelda(v_inFila,13,rg.ProyVa11) ;
      pkgutfile.proCelda(v_inFila,14,rg.ProyVa12) ;
    END LOOP;    
    pkgutfile.proCerrar;
END;

El codigo anterio genera el archivo indicado en el directorio de base de datos

Se debe realizar una llamada al sistema Operativo mediante java a la aplicacion  repoexcelSMT.jar, la cual debe estar ubicada en el directorio de Generacion asi: 
## La version de java debe ser minimo 1.7
## Parametros
##  jar a ejecutar
##  Plantilla  de Excel que debe ser 2007
##  Archivo de Salida
##  Archivo de Datos
##  Separador de Campos

java -jar repoexcelSMT.jar /ss03cc01/admi1.xls /ss03cc01/admi1_sali.xls /ss03cc01/admi001.txt "|"

