package waxa.boost.juntalimpio.espaciosnaturales.seleccioncomunidades;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.Datos;
import waxa.boost.juntalimpio.clases.Espacio;
import waxa.boost.juntalimpio.espaciosnaturales.listaespacios.ListaEspacios;

public class SeleccionComunidades extends ActionBarActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        AdvertenciaFragment.Clikao{

    private Button leon;
    private Button zamora;
    private Button salamanca;
    private Button palencia;
    private Button burgos;
    private Button soria;
    private Button segovia;
    private Button avila;
    private Button valladolid;

    private String provincia;
    private PedirListaEspacios tarea;
    private boolean cargando;

    private Context contexto;
    private GoogleApiClient mGoogleApiClient;
    //private LocationRequest mLocationRequest;
    private boolean gps;
    private boolean cargaCompleta;

    private android.support.v7.app.ActionBar actionbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.espacios_seleccion_comunidades);

        cargando = false;
        contexto = getApplicationContext();

        cargaCompleta = false;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        gps = false;

        actionbar =  getSupportActionBar();
        actionbar.setTitle("Elige la provincia");
        actionbar.setDisplayHomeAsUpEnabled(true);

        leon=(Button)findViewById(R.id.leon);
        zamora=(Button)findViewById(R.id.zamora);
        salamanca=(Button)findViewById(R.id.salamanca);
        palencia=(Button)findViewById(R.id.palencia);
        burgos=(Button)findViewById(R.id.burgos);
        soria=(Button)findViewById(R.id.soria);
        segovia=(Button)findViewById(R.id.segovia);
        avila=(Button)findViewById(R.id.avila);
        valladolid=(Button)findViewById(R.id.valladolid);

        leon.setOnClickListener(this);
        zamora.setOnClickListener(this);
        salamanca.setOnClickListener(this);
        palencia.setOnClickListener(this);
        burgos.setOnClickListener(this);
        soria.setOnClickListener(this);
        segovia.setOnClickListener(this);
        avila.setOnClickListener(this);
        valladolid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!cargando) {
            provincia = ((Button) v).getText().toString();
            cargando = true;

            DialogFragment dialog = new AdvertenciaFragment();
            dialog.show(getFragmentManager(), "AdvertenciaFragment");
            //TODO tarea;
        }
    }

    @Override
    public void onClikao() {
        tarea = new PedirListaEspacios();
        tarea.execute(provincia);
    }

    private void lanzarPeticion( String id){
        Intent i = new Intent(this, ListaEspacios.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    private void malaConex(){

        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SinConexionFragment();
        dialog.show(getFragmentManager(), "SinConexionFragment");
        Log.d(Datos.TAG, "NO SE HIZO BIEN EL LOG");
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
            Toast.makeText(this, "El GPS esta desconectado", Toast.LENGTH_SHORT).show();
        }
        try {
            Datos.POSICION_USER = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }catch (Exception e){
            Log.d(Datos.TAG,"aki peto");
        }

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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: // ID del boton
                onBackPressed();


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            Datos.wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            Datos.mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(Datos.wifiConnected) {
                Log.i(Datos.TAG, getString(R.string.wifi_connection));
            } else if (Datos.mobileConnected){
                Log.i(Datos.TAG, getString(R.string.mobile_connection));
            }
            return true;
        } else {
            Log.i(Datos.TAG, getString(R.string.no_wifi_or_mobile));
            return false;
        }
    }

    private JSONObject uploadUrl(String datosPost) throws IOException, JSONException, Exception {
        //Log.d(Datos.TAG,"urlString = "+urlString+", datosPost = "+datosPost);
        URL url = new URL(Datos.URL_ESPACIOS);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000 /* milliseconds */);
        conn.setConnectTimeout(17000 /* milliseconds */);
        conn.setRequestMethod("POST");

        conn.setDoOutput(true);
        conn.setDoInput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        JSONObject obj = new JSONObject();
        obj.put("provincia",datosPost);
        Log.d(Datos.TAG,obj.toString(4));
        wr.write(obj.toString());
        wr.flush();
        Log.d(Datos.TAG, "llego hasta aki?");

        Log.d(Datos.TAG, "llego hasta aki?2");
        // Start the query
        conn.connect();

        InputStreamReader stream = new InputStreamReader(conn.getInputStream());
        String line;
        String resp="";
        BufferedReader rd = new BufferedReader(stream);
        while((line = rd.readLine()) != null){
            Log.d(Datos.TAG,line);

            resp += line;
        }

        Log.d(Datos.TAG, "llego hasta aki?4");

        conn.disconnect();


        return new JSONObject(resp);
    }

    private JSONObject downloadUrl() throws IOException, JSONException {
        URL url = new URL(Datos.URL_ESPACIOS);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000 /* milliseconds */);
        conn.setConnectTimeout(17000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();

        InputStreamReader stream = new InputStreamReader(conn.getInputStream());
        String line;
        String resp="";
        BufferedReader rd = new BufferedReader(stream);
        while((line = rd.readLine()) != null){
            resp += line;
        }
        Log.d(Datos.TAG,resp);

        return new JSONObject(resp);
    }

    private void tratarJsonListaEspacios(JSONObject jobj) throws  JSONException{

        ArrayList<ArrayList<LatLng>> poligono;
        Espacio espacio;
        JSONObject jsonObject;
        Datos.PRUEBACONEX = new ArrayList<>();

        JSONArray jsonArray = jobj.getJSONArray("espacios");

        for (int i = 0; i< jsonArray.length(); i++){
            Log.d(Datos.TAG,"autentificacion2 correcta :" + i);
            jsonObject = jsonArray.getJSONObject(i);
            poligono = buscarPoligono(jsonObject.getString("nombre"));
            if (poligono == null) poligono = Datos.SHAPES.get(i);
            espacio = new Espacio(jsonObject.getString("nombre"), poligono);
            espacio.setPosicionCentro(new LatLng(
                    jsonObject.getDouble("lat"),
                    jsonObject.getDouble("long")));
            espacio.setId(jsonObject.getInt("id"));
            Datos.PRUEBACONEX.add(espacio);
        }
    }

    private ArrayList<ArrayList<LatLng>> buscarPoligono(String name){

        for (int i= 0; i< Datos.PRUEBACONEX.size(); i++)
            if (Datos.PRUEBACONEX.get(i).getName().equals(name)) return Datos.PRUEBACONEX.get(i).getPoligono();
        Log.d(Datos.TAG,"paso por el busca");
        return null;
    }

    private class PedirListaEspacios extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... urls) {
            try{
                if (checkNetworkConnection()){
                    //JSONObject jobj = downloadUrl();
                    JSONObject jobj = uploadUrl(urls[0]);
                    Log.d(Datos.TAG,jobj.toString(4));
                    Log.d(Datos.TAG, jobj.toString(2));
                    if (jobj.getInt("status") == 200){
                        Log.d(Datos.TAG,"autentificacion2 correcta :");
                        Log.d(Datos.TAG,jobj.toString(4));
                        tratarJsonListaEspacios(jobj);
                        return true;
                    }
                    else{
                        Log.d(Datos.TAG,"autentificacion2 incorrecta");
                        return false;
                    }
                }else{
                    Log.d(Datos.TAG, "no hay conexion2 a internet");
                    return false;
                }
            } catch (IOException e) {
                Log.d(Datos.TAG, "ERROR2 DE CONEXION "+ e.getMessage());
                return false;
            } catch (JSONException e){
                Log.d(Datos.TAG, "ERROR2 DE JSON " + e.getMessage());
                return false;
            }catch (Exception e){
                Log.d(Datos.TAG, "NO SE QUE COÑO PASA " + e.getMessage());
                return false;
            }
        }

        /**
         * Uses the logging framework to display the output of the fetch
         * operation in the log fragment.
         */
        @Override
        protected void onPostExecute(Boolean res){
            Log.d(Datos.TAG, "Conexion2 acabada");

            cargando = false;
            if (res) {
                if (gps) calcularDistancia();
                else gps = true;
                lanzarPeticion(provincia);
            }
            else malaConex();

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
                for (int i=0; i < Datos.PRUEBACONEX.size(); i++) {
                    Location.distanceBetween(
                            Datos.POSICION_USER.getLatitude(),
                            Datos.POSICION_USER.getLongitude(),
                            Datos.PRUEBACONEX.get(i).getPosicionCentro().latitude,
                            Datos.PRUEBACONEX.get(i).getPosicionCentro().longitude,
                            res
                    );
                    Log.d(Datos.TAG, "en el "+i+" con " +res[0]);
                    Datos.PRUEBACONEX.get(i).setDistancia(res[0] / 1000);
                }
                Collections.sort(Datos.PRUEBACONEX);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            cargaCompleta = true;
        }
    }
}