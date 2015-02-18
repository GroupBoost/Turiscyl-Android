package waxa.boost.juntalimpio.mapa;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

import waxa.boost.juntalimpio.clases.Alojamiento;
import waxa.boost.juntalimpio.clases.Datos;
import waxa.boost.juntalimpio.R;
import waxa.boost.juntalimpio.clases.ObjetoBase;
import waxa.boost.juntalimpio.clases.ObjetoEnvoltorio;
import waxa.boost.juntalimpio.espaciosnaturales.listaalojamientos.ListaAlojamientosAdapter;

public class Mapa extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private PolygonOptions po;
    private int indice;

    private ObjetoEnvoltorio[] elementosDrawer;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private ListaAlojamientosAdapter adapter;

    private String nombreEspacio;
    private int tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);

        Bundle extras = getIntent().getExtras();
        indice = extras.getInt("indice");
        tipo = extras.getInt("tipo");

        if (tipo == 0) espacioLimpio();

        getSupportActionBar().setTitle(nombreEspacio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setUpMapIfNeeded();
    }

    private void espacioLimpio(){

        nombreEspacio = Datos.LISTAESPACIOS.get(indice).getName();
        cargarDatos();

        adapter = new ListaAlojamientosAdapter(this, elementosDrawer);

        mDrawerList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.icon_drawer,
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */) {

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                Log.d(Datos.TAG,"desde el togle");
                return super.onOptionsItemSelected(item);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // TODO Auto-generated method stub
                super.onDrawerSlide(drawerView, slideOffset);
            }

            /** Called when a drawer has settled in a completely closed state. */

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(nombreEspacio);
            }


            /** Called when a drawer has settled in a completely open state. */

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Que puedo ver");
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void cargarDatos(){
        elementosDrawer = new ObjetoEnvoltorio[16];
        elementosDrawer[0] = generarEnvoltorio("Hoteles",1);
        elementosDrawer[1] = generarEnvoltorio("Camping",2);
        elementosDrawer[2] = generarEnvoltorio("Albergue",3);
        elementosDrawer[3] = generarEnvoltorio("Refugio",4);
        elementosDrawer[4] = generarEnvoltorio("Zona de Acampada",5);
        elementosDrawer[5] = generarEnvoltorio("Arboles",6);
        elementosDrawer[6] = generarEnvoltorio("Casa del Parque",7);
        elementosDrawer[7] = generarEnvoltorio("Centro de visitantes",8);
        elementosDrawer[8] = generarEnvoltorio("Kiosko",9);
        elementosDrawer[9] = generarEnvoltorio("Miradores",10);
        elementosDrawer[10] = generarEnvoltorio("Observatorios",11);
        elementosDrawer[11] = generarEnvoltorio("zonas Recreativas",12);
        elementosDrawer[12] = generarEnvoltorio("Otros",13);
        elementosDrawer[13] = generarEnvoltorio("Sendas nivel facil",14);
        elementosDrawer[14] = generarEnvoltorio("Sendas nivel medio",15);
        elementosDrawer[15] = generarEnvoltorio("Sendas nivel dificil",16);
    }

    private ObjetoEnvoltorio generarEnvoltorio(String nombre, int tipo){
        ObjetoEnvoltorio aux = new ObjetoEnvoltorio(nombre,generarDato(tipo));
        return aux;
    }

    private ArrayList<ObjetoBase> generarDato(int tipo){
        ArrayList<ObjetoBase> aux = new ArrayList<>();

        for(int i=0; i< Datos.LISTAESPACIOS.size() && i < 3; i++){
            aux.add(new Alojamiento(Datos.LISTAESPACIOS.get(((int)(Math.random()*10001))%Datos.LISTAESPACIOS.size()).getName(), tipo));
        }
        return aux;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            //findViewById(R.id.action_settings).setVisibility(View.INVISIBLE);
            return true;
        }
        // Handle your other action bar items...
        switch (item.getItemId()) {
            /*case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_LONG).show();
                break;

            */default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.clear();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        po = new PolygonOptions();

        ArrayList<ArrayList<LatLng>> aPintar = Datos.LISTAESPACIOS.get(indice).getPoligono();

        for (int i=0; i< aPintar.size(); i++) {
            for (int j = 0; j < aPintar.get(i).size(); j++) {
                po.add(aPintar.get(i).get(j));
            }
        }

        po.strokeWidth(10);
        po.strokeColor(Color.YELLOW);
        mMap.addPolygon(po);

        Marker marker = mMap.addMarker(new MarkerOptions().position(Datos.LISTAESPACIOS.get(indice).getPosicionCentro())
                .title(Datos.LISTAESPACIOS.get(indice).getName()));
        marker.showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Datos.LISTAESPACIOS.get(indice).getPosicionCentro(), 10));
    }
}