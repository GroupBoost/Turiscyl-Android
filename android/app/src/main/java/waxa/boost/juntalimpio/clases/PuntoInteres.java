package waxa.boost.juntalimpio.clases;

/**
 * Created by waxa2 on 10/01/15.
 */
public class PuntoInteres extends ObjetoBase {

    // 1 = arboles, 2 = casa parque, 3 = centro visitas, 4 = kiosko,
    // 5 = miradores, 6 = observatorios, 7 = zonas recreativas, 8 = otros
    public PuntoInteres(String name, int tipoPuntoInteres){
        super(name, name, null);
        setTipoPuntoInteres(tipoPuntoInteres);
    }
}
