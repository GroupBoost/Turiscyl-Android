package waxa.boost.juntalimpio.turismorural.informacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.Datos;
import waxa.boost.juntalimpio.mapa.Mapa;

public class Informacion extends ActionBarActivity {

    private android.support.v7.app.ActionBar actionbar;

    private int indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.turismo_info_poblacion);

        Bundle extras = getIntent().getExtras();
        indice = extras.getInt("indice");

        actionbar =  getSupportActionBar();
        String nombre=Datos.LISTAESPACIOS.get(indice).getName();
        actionbar.setTitle(nombre);
        actionbar.setDisplayHomeAsUpEnabled(true);

        ImageButton botonMapa = (ImageButton) findViewById(R.id.pt3mapa);
        ImageButton botonNavigator = (ImageButton) findViewById(R.id.pt3navigator);
        ImageButton botonSendas = (ImageButton) findViewById(R.id.monumentos);
        ImageButton botonAlojamientos = (ImageButton) findViewById(R.id.pt3alojamientos);
        ImageButton botonPuntosInteres = (ImageButton) findViewById(R.id.cafeterias);
;
        Log.d(Datos.TAG, "cual de los 2");

        botonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irmapa();
            }
        });

        botonNavigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irnav();
            }
        });

        botonSendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMonumentos();
            }
        });

        botonAlojamientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAlojamientos();
            }
        });

        botonPuntosInteres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCafeterias();
            }
        });

    }

    private void irmapa(){
        Intent i = new Intent(this, Mapa.class);
        i.putExtra("indice", indice);
        startActivity(i);
    }

    private void irnav(){

        String name = Datos.LISTAESPACIOS.get(indice).getName();
        String[] aux = name.split(" ");
        name = "";

        for(int i = 0; i < aux.length; i++){
            if (i != 0) name += "+";
            name += aux[i];
        }

        Uri uri;

        if (Datos.POSICION_USER != null){
            uri = Uri.parse("http://maps.google.com/maps?saddr="+
                    Datos.POSICION_USER.getLatitude()+","+
                    Datos.POSICION_USER.getLongitude()+
                    "&daddr=" + Datos.LISTAESPACIOS.get(indice).getPosicionCentro().latitude + "," +
                    Datos.LISTAESPACIOS.get(indice).getPosicionCentro().longitude);
        }
        else{
            uri = Uri.parse("geo:0,0?q="+
                    Datos.LISTAESPACIOS.get(indice).getPosicionCentro().latitude + "," +
                    Datos.LISTAESPACIOS.get(indice).getPosicionCentro().longitude +
                    "(" + Datos.LISTAESPACIOS.get(indice).getName() + ")&z=15");
        }
        Intent mInt = new Intent(Intent.ACTION_VIEW, uri);
        mInt.setPackage("com.google.android.apps.maps");
        startActivity(mInt);
    }

    private void irAlojamientos(){

    }

    private void irCafeterias(){
        /*Intent i = new Intent(this, ListaContenido.class);
        i.putExtra("indice",indice);
        startActivity(i);*/
    }

    private void irMonumentos(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id==android.R.id.home) // ID del boton
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(true);
        return true;
    }

}