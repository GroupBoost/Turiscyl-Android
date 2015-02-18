package waxa.boost.juntalimpio.espaciosnaturales.listasendas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.ObjetoBase;

public class ListaSendasAdapter extends ArrayAdapter{

    private Activity act;
    private LayoutInflater inflater;
    private ArrayList<ObjetoBase> sendas;

    public ListaSendasAdapter(Activity act, ArrayList<ObjetoBase> sendas){
        super(act, R.layout.espacios_lista_sendas_elemento, sendas);
        this.sendas = sendas;
        this.act = act;
        this.inflater = act.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (!sendas.get(position).isEspecial()) {

            convertView = inflater.inflate(R.layout.espacios_lista_sendas_elemento, null);

            TextView nombre = (TextView) convertView.findViewById(R.id.nombre_senda);
            TextView descripcion = (TextView) convertView.findViewById(R.id.desc_senda);

            nombre.setText(sendas.get(position).getName());
            descripcion.setText(sendas.get(position).getDescripcion());
        }
        else{
            if (sendas.get(position).isSeparador()){

                convertView = inflater.inflate(R.layout.espacios_lista_sendas_separador, null);
            }
            else{

                convertView = inflater.inflate(R.layout.espacios_lista_sendas_categoria, null);

                TextView categoria = (TextView) convertView.findViewById(R.id.titulo);

                categoria.setText(sendas.get(position).getName());
            }
        }
        return convertView;
    }


}
