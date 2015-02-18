package waxa.boost.juntalimpio.clases;

import java.util.ArrayList;

/**
 * Created by waxa2 on 10/01/15.
 */
public class ObjetoEnvoltorio {

    private String titulo;
    private ArrayList<ObjetoBase> contenido;

    public ObjetoEnvoltorio(String titulo, ArrayList<ObjetoBase> contenido){
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public ArrayList<ObjetoBase> getContenido() {
        return contenido;
    }
}
