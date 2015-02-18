package waxa.boost.juntalimpio.clases;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by waxa2 on 30/12/14.
 */
public class Senda extends ObjetoBase {

    public static String NOMBRE = "Sendas";

    private ArrayList<LatLng> ruta;

    public Senda(String name, String descripcion, ArrayList<LatLng> ruta){
        super(name, descripcion, ruta.get(0));
        this.ruta = ruta;
    }

    public ArrayList<LatLng> getRuta(){
        return ruta;
    }

}
