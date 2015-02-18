package waxa.boost.juntalimpio.clases;

import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by waxa2 on 26/12/14.
 */
public class Datos {


    public static String URL_ESPACIOS = "http://hera.j5web.net/espacios";

    public static ArrayList <Espacio> PRUEBACONEX;
    public static ArrayList<ArrayList<ArrayList<LatLng>>> SHAPES;

    public static boolean wifiConnected = false;
    public static boolean mobileConnected = false;

    public static String TAG = "WAXATAG";
    public static ArrayList <Espacio> LISTAESPACIOS;

    public static Location POSICION_USER;

    public static String SERVICIO_ACTUAL;

    public static ArrayList < ArrayList<ObjetoBase>> LISTASERVICIOS;

    public static String[] NOMBRESSERVICIOS = {Senda.NOMBRE, Alojamiento.NOMBRE};

    public static String[] NIVELES = {"facil","medio","dificil"};

    public static int[] COLORES = { Color.GREEN, Color.YELLOW, Color.RED,Color.BLUE, Color.DKGRAY, Color.BLACK,  Color.WHITE, Color.CYAN, Color.GRAY};
    public static String[] COMUNIDADES = {};
    //public static ListaServiciosFragment.SwipeTouchListener2 LISTENER;

    //public static ListaServiciosFragment.CambiarPestaña LISTENER;

    //public static AdaptadorListaServicios.Servicio LISTENERSERVICIO;

    public static String limpiar(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }//remove1




}
