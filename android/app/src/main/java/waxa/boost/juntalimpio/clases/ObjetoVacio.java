package waxa.boost.juntalimpio.clases;

/**
 * Created by waxa2 on 10/01/15.
 */
public class ObjetoVacio extends ObjetoBase{

    public ObjetoVacio(String name){
        super(name, null, null);
        setEspecial(true);
        setSeparador(false);
    }

    public ObjetoVacio(boolean separador){
        super(null,null,null);
        setEspecial(true);
        setSeparador(true);
    }

}
