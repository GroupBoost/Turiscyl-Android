package waxa.boost.juntalimpio.turismorural.seleccioncomunidades;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.Datos;
import waxa.boost.juntalimpio.clases.Espacio;
import waxa.boost.juntalimpio.espaciosnaturales.seleccioncomunidades.SinConexionFragment;
import waxa.boost.juntalimpio.turismorural.listapoblaciones.ListaPoblaciones;

public class SeleccionComunidades extends ActionBarActivity implements View.OnClickListener {

    private Button leon;
    private Button zamora;
    private Button salamanca;
    private Button palencia;
    private Button burgos;
    private Button soria;
    private Button segovia;
    private Button avila;
    private Button valladolid;

    private boolean cargando;
    private PedirListaEspacios tarea;
    private String comunidad;


    private android.support.v7.app.ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turismo_seleccion_comunidades);

        cargando = false;

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
        comunidad = ((Button) v).getText().toString();
        /*if (!cargando) {

            cargando = true;
            tarea = new PedirListaEspacios();
            tarea.execute();
            //TODO tarea;
        }*/
        lanzarPeticion(comunidad);
    }

    private void lanzarPeticion( String id){
        Intent i = new Intent(this, ListaPoblaciones.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    private void malaConex(){
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SinConexionFragment();
        dialog.show(getFragmentManager(), "SinConexionFragment");
        Log.d(Datos.TAG, "NO SE HIZO BIEN EL LOG");
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

        for (int i= 0; i< Datos.LISTAESPACIOS.size(); i++)
            if (Datos.LISTAESPACIOS.get(i).getName().equals(name)) return Datos.LISTAESPACIOS.get(i).getPoligono();
        Log.d(Datos.TAG,"paso por el busca");
        return null;
    }

    private class PedirListaEspacios extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... urls) {
            try{
                if (checkNetworkConnection()){
                    JSONObject jobj = downloadUrl();
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
            if (res) lanzarPeticion(comunidad);
            else malaConex();

        }
    }

}