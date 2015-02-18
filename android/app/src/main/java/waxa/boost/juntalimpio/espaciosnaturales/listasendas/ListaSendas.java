package waxa.boost.juntalimpio.espaciosnaturales.listasendas;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.Datos;
import waxa.boost.juntalimpio.clases.ObjetoBase;
import waxa.boost.juntalimpio.clases.ObjetoVacio;

public class ListaSendas extends ActionBarActivity implements SearchView.OnQueryTextListener{

    private ListView lista;
    private ListaSendasAdapter adapter;
    private ArrayList<ObjetoBase> sendas;
    private android.support.v7.app.ActionBar actionbar;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.espacios_lista_sendas_base);

        actionbar =  getSupportActionBar();
        actionbar.setTitle("Sendas");
        actionbar.setDisplayHomeAsUpEnabled(true);

        lista = (ListView)findViewById(R.id.listasendas);

        generarDatos();

        adapter = new ListaSendasAdapter(this, sendas);

        lista.setAdapter(adapter);
    }

    private void carga(){
        for(int i = 0; i < Datos.LISTAESPACIOS.size(); i++){
            sendas.add(Datos.LISTAESPACIOS.get(i));
        }
    }

    private void generarDatos(){

        sendas = new ArrayList<>();

        ObjetoVacio facil = new ObjetoVacio("facil");
        ObjetoVacio medio = new ObjetoVacio("medio");
        ObjetoVacio dificil = new ObjetoVacio("dificil");

        ObjetoVacio separador = new ObjetoVacio(true);

        sendas.add(facil);
        carga();

        sendas.add(separador);
        sendas.add(medio);
        carga();

        sendas.add(separador);
        sendas.add(dificil);
        carga();
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
                onBackPressed();// con finish terminamos el activity actual, con lo que volvemos
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