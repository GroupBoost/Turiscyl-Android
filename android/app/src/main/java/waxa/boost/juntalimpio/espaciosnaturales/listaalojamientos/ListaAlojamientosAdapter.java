package waxa.boost.juntalimpio.espaciosnaturales.listaalojamientos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.ObjetoBase;
import waxa.boost.juntalimpio.clases.ObjetoEnvoltorio;

/**
 * Created by waxa2 on 10/01/15.
 */
public class ListaAlojamientosAdapter extends BaseExpandableListAdapter{

    private ObjetoEnvoltorio[] datos;
    private Activity activity;
    private LayoutInflater inflater;

    public ListaAlojamientosAdapter(Activity activity, ObjetoEnvoltorio[] datos){
        this.datos = datos;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getGroupCount() {
        return datos.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datos[groupPosition].getContenido().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return datos[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return datos[groupPosition].getContenido().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.espacios_lista_alojamientos_padre,null);
        TextView titulo = (TextView)convertView.findViewById(R.id.titulo);
        titulo.setText(datos[groupPosition].getTitulo());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ObjetoBase aux = datos[groupPosition].getContenido().get(childPosition);

        TextView nombre;
        TextView descripcion;

        switch (aux.getTipoAlojamiento()) {
            case 1:
                convertView = inflater.inflate(R.layout.espacios_lista_alojamientos_hijo_hotel, null);
                break;
            case 2:
                convertView = inflater.inflate(R.layout.espacios_lista_alojamientos_hijo_camping, null);
                break;
            case 3:
                convertView = inflater.inflate(R.layout.espacios_lista_alojamientos_hijo_albergue, null);
                break;
            case 4:
                convertView = inflater.inflate(R.layout.espacios_lista_alojamientos_hijo_refugio, null);
                break;
            case 5:
                convertView = inflater.inflate(R.layout.espacios_lista_alojamientos_hijo_zona_acampada, null);
                break;
            default:
                convertView = inflater.inflate(R.layout.espacios_lista_alojamientos_hijo_hotel, null);
                break;
        }

        nombre = (TextView)convertView.findViewById(R.id.nombre);
        descripcion = (TextView)convertView.findViewById(R.id.descripcion);

        nombre.setText(aux.getName());
        descripcion.setText(aux.getDescripcion());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
