package waxa.boost.juntalimpio.espaciosnaturales.listaespacios;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import waxa.boost.juntalimpio.clases.Espacio;
import waxa.boost.juntalimpio.R;

public class ListaEspaciosAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private TextView name;
    private TextView dist;

    private ArrayList<Espacio> datos;

    public ListaEspaciosAdapter(Activity activity, ArrayList<Espacio> datos){
        super(activity, R.layout.espacios_lista_espacio_elemento, datos);
        this.datos = datos;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        DecimalFormat format = new DecimalFormat("0.00");

        convertView = inflater.inflate(R.layout.espacios_lista_espacio_elemento, null);
        name = (TextView) convertView.findViewById(R.id.nombre);
        dist = (TextView) convertView.findViewById(R.id.distancia);

        name.setText(datos.get(position).getName());
        if (datos.get(position).getDistancia() > 0)
            dist.setText(format.format(datos.get(position).getDistancia()) +" km");
        else
            dist.setText( "-- -- km");

        return convertView;
    }
}