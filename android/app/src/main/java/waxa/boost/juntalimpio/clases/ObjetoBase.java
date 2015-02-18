package waxa.boost.juntalimpio.clases;

import com.google.android.gms.maps.model.LatLng;

public abstract class ObjetoBase implements Comparable<ObjetoBase> {

    private String name;
    private String descripcion;
    private LatLng posicionCentro;
    private float distancia;
    private boolean especial;
    private boolean separador;
    private int tipoAlojamiento;
    private int tipoPuntoInteres;
    private int id;

    public ObjetoBase (String name, String descripcion, LatLng posicionCentro){
        this.name = name;
        this.descripcion = descripcion;
        this.posicionCentro = posicionCentro;
        this.tipoAlojamiento = 0;
        this.tipoPuntoInteres = 0;
        this.distancia = -1;
        especial = false;
        this.id = 0;
        separador = false;
    }

    @Override
    public int compareTo(ObjetoBase obj){
        return (int)(this.distancia - obj.getDistancia());
    }

    public void setDistancia(float distancia){
        this.distancia = distancia;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public double getDistancia(){
        return distancia;
    }

    public boolean isEspecial(){
        return especial;
    }

    public void setEspecial(boolean especial){
        this.especial = especial;
    }

    public void setSeparador(boolean separador){
        this.separador = separador;
    }

    public String getName(){
        return name;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public LatLng getPosicionCentro(){
        return posicionCentro;
    }

    public int getTipoAlojamiento(){
        return tipoAlojamiento;
    }

    public int getTipoPuntoInteres() {
        return tipoPuntoInteres;
    }

    public void setTipoPuntoInteres(int tipoPuntoInteres) {
        this.tipoPuntoInteres = tipoPuntoInteres;
    }

    public void setTipoAlojamiento(int tipoAlojamiento){
        this.tipoAlojamiento = tipoAlojamiento;
    }

    public boolean isSeparador(){
        return separador;
    }

    public void setPosicionCentro(LatLng posicionCentro) {
        this.posicionCentro = posicionCentro;
    }
}