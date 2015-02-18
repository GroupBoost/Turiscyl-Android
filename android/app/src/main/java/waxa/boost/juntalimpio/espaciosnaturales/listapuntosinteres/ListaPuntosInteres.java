package waxa.boost.juntalimpio.espaciosnaturales.listapuntosinteres;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.Datos;
import waxa.boost.juntalimpio.clases.ObjetoBase;
import waxa.boost.juntalimpio.clases.ObjetoEnvoltorio;
import waxa.boost.juntalimpio.clases.PuntoInteres;

/**
 * Created by waxa2 on 10/01/15.
 */
public class ListaPuntosInteres extends ActionBarActivity implements SearchView.OnQueryTextListener{

    private ExpandableListView listaPuntosInteres;
    private ListaPuntosInteresAdapter adapter;
    private ObjetoEnvoltorio[] puntosInteres;

    private android.support.v7.app.ActionBar actionbar;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.espacios_lista_puntos_interes_base);

        actionbar =  getSupportActionBar();
        actionbar.setTitle("Puntos de interes");
        actionbar.setDisplayHomeAsUpEnabled(true);

        listaPuntosInteres = (ExpandableListView)findViewById(R.id.listaPuntosInteres);

        cargarDatos();

        adapter = new ListaPuntosInteresAdapter(this, puntosInteres);

        listaPuntosInteres.setAdapter(adapter);
    }

    private void cargarDatos(){
        puntosInteres = new ObjetoEnvoltorio[8];
        puntosInteres[0] = generarEnvoltorio("Arboles",1);
        puntosInteres[1] = generarEnvoltorio("Casa del Parque",2);
        puntosInteres[2] = generarEnvoltorio("Centro de visitantes",3);
        puntosInteres[3] = generarEnvoltorio("Kiosko",4);
        puntosInteres[4] = generarEnvoltorio("Miradores",5);
        puntosInteres[5] = generarEnvoltorio("Observatorios",6);
        puntosInteres[6] = generarEnvoltorio("zonas Recreativas",7);
        puntosInteres[7] = generarEnvoltorio("Otros",8);
    }

    private ObjetoEnvoltorio generarEnvoltorio(String nombre, int tipo){
        ObjetoEnvoltorio aux = new ObjetoEnvoltorio(nombre,generarDato(tipo));
        return aux;
    }

    private ArrayList<ObjetoBase> generarDato(int tipo){
        ArrayList<ObjetoBase> aux = new ArrayList<>();

        for(int i=0; i< Datos.LISTAESPACIOS.size(); i++){
            aux.add(new PuntoInteres(Datos.LISTAESPACIOS.get(i).getName(), tipo));
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

            case android.R.id.home:
                onBackPressed();


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
