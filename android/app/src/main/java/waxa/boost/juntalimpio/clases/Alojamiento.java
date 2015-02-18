package waxa.boost.juntalimpio.clases;

/**
 * Created by waxa2 on 30/12/14.
 */
public class Alojamiento extends ObjetoBase {

    public static String NOMBRE = "Albergues";

    // 1 = hotel, 2 = camping, 3 = albergue, 4 = refugio, 5 = campamento, 6 = zona acampada
    public Alojamiento(String name, int tipoAlojamiento){
        super(name, name, null);
        setTipoAlojamiento(tipoAlojamiento);
    }
}
