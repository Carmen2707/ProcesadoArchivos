package org.example;

import java.io.*;
import java.util.ArrayList;

/**
 *  Esta clase genera una carpeta llamada "salida" en la que se crean archivos con una plantilla donde se sustituyen los datos de los clientes.
 */
public class CombinarCorrespondencia {
    /**
     * @param csv Este es el documento data.csv en el encontramos los datos los clientes.
     * @param txt Este es el documento que tiene la plantilla en la que se sustituiran los marcadores por los datos del cliente del documento data.csv.
     */
    public static void combinarCorrespondencia(String csv, String txt) {
        File data = new File(csv);

        try(BufferedReader br = new BufferedReader(new FileReader(data))){

            String leer;
            while ((leer = br.readLine())!=null) {
                String[] datosArchivo = leer.split(",");
                if(datosArchivo.length>=5){
                    ArrayList<String> listaDatos = new ArrayList<>();
                    String id = datosArchivo[0];
                    String nombre = datosArchivo[1];
                    String ciudad = datosArchivo[2];
                    String email = datosArchivo[3];
                    String responsable = datosArchivo[4];

                    try(BufferedReader br2 = new BufferedReader(new FileReader(txt))){

                        String leer2;
                        while((leer2 = br2.readLine())!=null){
                            leer2 = leer2.replace("%%2%%", ciudad);
                            leer2 = leer2.replace("%%3%%", email);
                            leer2 = leer2.replace("%%4%%", nombre);
                            leer2 = leer2.replace("%%5%%", responsable);

                            listaDatos.add(leer2+"\n");
                            System.out.println(leer2);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("Error al leer el archivo de la plantilla " + e);
                    }

                    File salida = new File("salida");
                    salida.mkdir();

                    try(BufferedWriter archivoSalida = new BufferedWriter(new FileWriter("salida/template-" + id+".txt"))){
                        for (String nuevoTxt : listaDatos){
                            archivoSalida.write(nuevoTxt);
                            archivoSalida.flush();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("Error al escribir el archivo de salida " + e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de obtención de datos "+ e);
        }
    }
    }

