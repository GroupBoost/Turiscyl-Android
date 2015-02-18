package waxa.boost.juntalimpio.inicio;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.Datos;
import waxa.boost.juntalimpio.clases.Espacio;
import waxa.boost.juntalimpio.espaciosnaturales.seleccioncomunidades.SeleccionComunidades;
import waxa.boost.juntalimpio.espaciosnaturales.seleccioncomunidades.SinConexionFragment;


public class Inicio extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    //LocationListener {

    private Button espaciosNaturales;
    private Button turismo;
    private Button eventos;

    private TextView cargando;

    private GoogleApiClient mGoogleApiClient;
    //private LocationRequest mLocationRequest;
    private boolean gps;
    private boolean cargaCompleta;

    private Context contexto;

    private android.support.v7.app.ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        actionbar =  getSupportActionBar();
        actionbar.setTitle("TurisCyL");

        espaciosNaturales = (Button)findViewById(R.id.pt1Espacios);
        turismo = (Button)findViewById(R.id.pt1Turismo);
        eventos = (Button)findViewById(R.id.pt1Eventos);
        cargando = (TextView)findViewById(R.id.carga);

        cargando.setVisibility(View.VISIBLE);

        eventos.setVisibility(View.GONE);
        turismo.setText("Poblaciones");

        espaciosNaturales.setVisibility(View.INVISIBLE);
        turismo.setVisibility(View.INVISIBLE);

        contexto = getApplicationContext();

        cargaCompleta = false;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        gps = false;

        espaciosNaturales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarComunidadesEspacios();
            }
        });

        turismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarFragmentNotImplemented();
                //lanzarComunidadesTurismo();
            }
        });

        Tarea tarea = new Tarea();
        tarea.execute();
    }

    private void lanzarFragmentNotImplemented(){
        DialogFragment dialog = new NotImplementedFragment();
        dialog.show(getFragmentManager(), "NotImplementedFragment");
    }

    private void lanzarComunidadesEspacios(){
        Log.d(Datos.TAG,"inicio LanzarComunidades");
        while(!cargaCompleta){
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                Log.d(Datos.TAG,"en el sleep con " + e.getMessage());
            }
        }
        Intent i = new Intent(this, SeleccionComunidades.class);
        Log.d(Datos.TAG,"antes LanzarComunidades");
        startActivity(i);
    }


    private void lanzarComunidadesTurismo(){
        Log.d(Datos.TAG,"inicio LanzarTurismo");
        while(!cargaCompleta){
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                Log.d(Datos.TAG,"en el sleep con " + e.getMessage());
            }
        }
        Intent i = new Intent(this, waxa.boost.juntalimpio.turismorural.seleccioncomunidades.SeleccionComunidades.class);
        Log.d(Datos.TAG,"antes LanzarTurismo");
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {

        LocationManager manager = (LocationManager) getSystemService(contexto.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this,"El GPS esta desconectado, no podrá " +
                    "\ncalcularse la distancia a las localizaciones",Toast.LENGTH_LONG).show();
        }
        try {
            Datos.POSICION_USER = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }catch (Exception e){
            Log.d(Datos.TAG,"aki peto");
        }
/*
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest,new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Datos.POSICION_USER = location;
                        Log.d(Datos.TAG,"LA POSICION HA CAMBIADO");
                    }
                });
*/
        if (gps) calcularDistancia();
        else gps = true;

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(Datos.TAG, "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(Datos.TAG, "GoogleApiClient connection has failed");
    }
/*
    @Override
    public void onLocationChanged(Location location) {
        Log.d(Datos.TAG, "Location received: " + location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(Datos.TAG, "onStatusChanged: "+provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(Datos.TAG, "onProviderEnabled: "+provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(Datos.TAG, "onProviderDisabled: "+provider);
    }*/

    private class Tarea extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Datos.LISTAESPACIOS = new ArrayList<>();
            Datos.SHAPES = new ArrayList<>();
            String aux;
            String aux2;

            Log.d(Datos.TAG, "MIERDA DE LINEA 1");
            try {
                Log.d(Datos.TAG, "MIERDA DE LINEA 2");
                BufferedReader BR = new BufferedReader(new InputStreamReader(
                        contexto.getResources().openRawResource(R.raw.todas)));
                BufferedReader BR2 = new BufferedReader(new InputStreamReader(
                        contexto.getResources().openRawResource(R.raw.nombres)));
                Log.d(Datos.TAG, "MIERDA DE LINEA 3");

                while(true){
                    //Log.d(Datos.TAG, "MIERDA DE LINEA 4");
                    aux = BR.readLine();
                    aux2 = BR2.readLine();
                    if (aux != null && !aux.isEmpty()) {
                        Datos.LISTAESPACIOS.add(new Espacio(aux2, cosasDelDirecto(aux)));
                        Datos.SHAPES.add(cosasDelDirecto(aux));
                    }
                    else break;
                }
            }catch (Exception e) {
                Log.d(Datos.TAG, "MIERDA DE LINEA");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (gps) calcularDistancia();
            else gps = true;

            espaciosNaturales.setVisibility(View.VISIBLE);
            //TODO todo turismo
            turismo.setVisibility(View.VISIBLE);
            cargando.setVisibility(View.GONE);

            //Log.d(Datos.TAG, "EN EL onPostExecute, loc = " +
            //       LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).toString());
            Log.d(Datos.TAG,"en datos.posicion = " + Datos.POSICION_USER);

        }
    }

    private void calcularDistancia(){
        TareaDistancia tarea = new TareaDistancia();
        tarea.execute();
    }

    private class TareaDistancia extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            float[] res = new float[1];

            LocationManager manager = (LocationManager) getSystemService(contexto.LOCATION_SERVICE);
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                for (int i=0; i < Datos.LISTAESPACIOS.size(); i++) {
                    Location.distanceBetween(
                            Datos.POSICION_USER.getLatitude(),
                            Datos.POSICION_USER.getLongitude(),
                            Datos.LISTAESPACIOS.get(i).getPosicionCentro().latitude,
                            Datos.LISTAESPACIOS.get(i).getPosicionCentro().longitude,
                            res
                    );
                    Log.d(Datos.TAG, "en el "+i+" con " +res[0]);
                    Datos.LISTAESPACIOS.get(i).setDistancia(res[0] / 1000);
                }
                Collections.sort(Datos.LISTAESPACIOS);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            cargaCompleta = true;
        }
    }

    private ArrayList<ArrayList<LatLng>> cosasDelDirecto(String lista){

        String[] list;
        ArrayList < ArrayList <LatLng>> res = new ArrayList<>();
        ArrayList <LatLng> parcial;
        if (lista.contains("; ")) list = lista.split("; ");
        else {
            list = new String[1];
            list[0] = lista;
        }

        String[] strCoords;

        for (String aList : list) {
            String[] aux = aList.split(", ");
            parcial = new ArrayList<>();
            for (String anAux : aux) {
                strCoords = anAux.split(" ");
                parcial.add(new LatLng(Double.parseDouble(strCoords[0]), Double.parseDouble(strCoords[1])));
            }
            res.add(parcial);
        }

        return res;
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }
}