package waxa.boost.juntalimpio.turismorural.listapoblaciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.Datos;
import waxa.boost.juntalimpio.clases.Espacio;
import waxa.boost.juntalimpio.espaciosnaturales.listaespacios.ListaEspaciosAdapter;

public class ListaPoblaciones extends ActionBarActivity implements SearchView.OnQueryTextListener{

    private int actual;
    private String localidad;
    private String busqueda;
    private SearchView mSearchView;
    private boolean cargando;
    private boolean original;

    private ListView lista;

    private ListaPoblacionesAdapter adaptador;

    private android.support.v7.app.ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        busqueda = "";

        Bundle extras = getIntent().getExtras();
        localidad = extras.getString("id");
        actual=0;


        actionbar =  getSupportActionBar();
        actionbar.setTitle(localidad);
        actionbar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.turismo_lista_poblaciones_base);

        lista = (ListView) findViewById(android.R.id.list);

        adaptador = new ListaPoblacionesAdapter(this, new ArrayList<Espacio>());

        //addCosas();

        for(int i=0; i< Datos.LISTAESPACIOS.size(); i++){
            adaptador.add(Datos.LISTAESPACIOS.get(i));
        }

        lista.setAdapter(adaptador);
        //contador.setText("Mostrando " + actual + " de " + Datos.LISTAESPACIOS.size());
/*

        lista.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean lastItem = (firstVisibleItem + visibleItemCount == totalItemCount);
                boolean moreRows = adaptador.getCount() < Datos.LISTAESPACIOS.size();

                if (!cargando && lastItem && moreRows) {
                    contador.setText("Cargando nuevos");
                    cargando = true;
                    (new CargarSiguientes()).execute();
                }
            }
        });
*/
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lanzarLocalidadesInfo(position);
            }
        });

        cargando = false;

        Log.d(Datos.TAG, "En el final del onCreate de lista");
    }

  /*  private void addCosas(){
        int cont =0;

        for(int i=actual; i < actual + 10 && i < Datos.LISTAESPACIOS.size(); i++){
            adaptador.add(Datos.LISTAESPACIOS.get(i));
            cont++;
        }
        actual += cont;
    }
*/
    private void lanzarLocalidadesInfo(int position) {
        Intent i = new Intent(this, waxa.boost.juntalimpio.turismorural.informacion.Informacion.class);
        i.putExtra("indice",position);
        startActivity(i);

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
                onBackPressed();


            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        busqueda=s;
        Log.d(Datos.TAG, "lanzando log sumit");

        adaptador = new ListaPoblacionesAdapter(this, new ArrayList<Espacio>());
        for(int i=0; i< Datos.LISTAESPACIOS.size(); i++)
            if(Datos.limpiar(Datos.LISTAESPACIOS.get(i).getName()).toLowerCase()
                    .contains(Datos.limpiar(s).toLowerCase())) {
                adaptador.add(Datos.LISTAESPACIOS.get(i));
                Log.d(Datos.TAG, Datos.LISTAESPACIOS.get(i).getName() + " = " + s);
            }

        lista.setAdapter(adaptador);
        original = false;

        return true;
    }

    private void cargarOriginal(){
        adaptador = new ListaPoblacionesAdapter(this, new ArrayList<Espacio>());
        for(int i=0; i< Datos.LISTAESPACIOS.size(); i++)
            adaptador.add(Datos.LISTAESPACIOS.get(i));

        lista.setAdapter(adaptador);
        original = true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //Log.d(Datos.TAG, "lanzando log en change");
        if ( s == null) {
            Log.d(Datos.TAG, "String nulo");
            cargarOriginal();
        }
        else if (s.isEmpty()){
            Log.d(Datos.TAG, "string vacio");
            cargarOriginal();
        }
        else {
            Log.d(Datos.TAG, "lanzando log en change2 con ");
            adaptador = new ListaPoblacionesAdapter(this, new ArrayList<Espacio>());
            for (int i = 0; i < Datos.LISTAESPACIOS.size(); i++)
                if(Datos.limpiar(Datos.LISTAESPACIOS.get(i).getName()).toLowerCase()
                        .contains(Datos.limpiar(s).toLowerCase())) {
                    adaptador.add(Datos.LISTAESPACIOS.get(i));
                    Log.d(Datos.TAG, Datos.LISTAESPACIOS.get(i).getName() + " = " + s);
                }

            lista.setAdapter(adaptador);
            original = false;
        }

        return false;
    }
/*
    private class CargarSiguientes extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Thread.sleep(1200);
            }
            catch (InterruptedException e) {
                Log.d(Datos.TAG, "sleep = " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            int cont = 0;

            Log.d(Datos.TAG, "valor de actual = " + actual);
            Log.d(Datos.TAG, "tama de lista = " + Datos.LISTAESPACIOS.size());

            for(int i=actual; i < actual + 10 && i < Datos.LISTAESPACIOS.size(); i++){
                adaptador.add(Datos.LISTAESPACIOS.get(i));
                Log.d(Datos.TAG, "carga el " + i);
                cont++;
            }
            actual += cont;

            adaptador.notifyDataSetChanged();

            Log.d(Datos.TAG, "ha cargado mas cosas");

            contador.setText("Mostrando " + actual + " de " + Datos.LISTAESPACIOS.size());
            cargando = false;
        }
    }*/
}