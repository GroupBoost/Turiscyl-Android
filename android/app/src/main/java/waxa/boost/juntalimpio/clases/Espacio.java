package waxa.boost.juntalimpio.clases;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by waxa2 on 28/12/14.
 */
public class Espacio extends ObjetoBase{

    public static String NOMBRE = "Espacios Naturales";

    private ArrayList<ArrayList<LatLng>> poligono;

    public Espacio(String name, ArrayList<ArrayList<LatLng>> poligono){
        super(name, name,poligono.get(0).get(0));
        this.poligono = poligono;
        calcularCentro();

    }

    private void calcularCentro(){
        double[] latMinMax = new double[2];
        double[] lonMinMax = new double[2];
        latMinMax[0] = 100;
        latMinMax[1] = -100;
        lonMinMax[0] = 100;
        lonMinMax[1] = -100;

        for(int i=0; i< poligono.size(); i++){
            for (int j=0; j<poligono.get(i).size(); j++){
                if(poligono.get(i).get(j).latitude < latMinMax[0] ) latMinMax[0] = poligono.get(i).get(j).latitude;
                if(poligono.get(i).get(j).latitude > latMinMax[1] ) latMinMax[1] = poligono.get(i).get(j).latitude;
                if(poligono.get(i).get(j).longitude < lonMinMax[0] ) lonMinMax[0] = poligono.get(i).get(j).longitude;
                if(poligono.get(i).get(j).longitude > lonMinMax[1] ) lonMinMax[1] = poligono.get(i).get(j).longitude;
            }
        }

        setPosicionCentro(new LatLng(((latMinMax[0] + latMinMax[1])/2), ((lonMinMax[0] + lonMinMax[1])/2)));

    }

    public ArrayList<ArrayList<LatLng>> getPoligono(){
        return poligono;
    }

}
