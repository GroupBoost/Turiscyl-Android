package waxa.boost.juntalimpio.espaciosnaturales.listaalojamientos;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.Alojamiento;
import waxa.boost.juntalimpio.clases.Datos;
import waxa.boost.juntalimpio.clases.ObjetoBase;
import waxa.boost.juntalimpio.clases.ObjetoEnvoltorio;

/**
 * Created by waxa2 on 10/01/15.
 */
public class ListaAlojamientos extends ActionBarActivity implements SearchView.OnQueryTextListener{

    private ExpandableListView listaAlojamientos;
    private ListaAlojamientosAdapter adapter;
    private ObjetoEnvoltorio[] alojamientos;

    private android.support.v7.app.ActionBar actionbar;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.espacios_lista_alojamientos_base);

        actionbar =  getSupportActionBar();
        actionbar.setTitle("Alojamientos");
        actionbar.setDisplayHomeAsUpEnabled(true);

        listaAlojamientos = (ExpandableListView)findViewById(R.id.listaAlojamientos);

        cargarDatos();

        adapter = new ListaAlojamientosAdapter(this, alojamientos);

        listaAlojamientos.setAdapter(adapter);
    }

    private void cargarDatos(){
        alojamientos = new ObjetoEnvoltorio[5];
        alojamientos[0] = generarEnvoltorio("Hoteles",1);
        alojamientos[1] = generarEnvoltorio("Camping",2);
        alojamientos[2] = generarEnvoltorio("Albergue",3);
        alojamientos[3] = generarEnvoltorio("Refugio",4);
        alojamientos[4] = generarEnvoltorio("Zona de Acampada",5);
    }

    private ObjetoEnvoltorio generarEnvoltorio(String nombre, int tipo){
        ObjetoEnvoltorio aux = new ObjetoEnvoltorio(nombre,generarDato(tipo));
        return aux;
    }

    private ArrayList<ObjetoBase> generarDato(int tipo){
        ArrayList<ObjetoBase> aux = new ArrayList<>();

        for(int i=0; i< Datos.LISTAESPACIOS.size(); i++){
            aux.add(new Alojamiento(Datos.LISTAESPACIOS.get(i).getName(), tipo));
        }
        return aux;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                mSearchView.setIconified(false);
                return true;

            case android.R.id.home: // ID del boton
                onBackPressed(); // con finish terminamos el activity actual, con lo que volvemos
                // al activity anterior (si el anterior no ha sido cerrado)


            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
