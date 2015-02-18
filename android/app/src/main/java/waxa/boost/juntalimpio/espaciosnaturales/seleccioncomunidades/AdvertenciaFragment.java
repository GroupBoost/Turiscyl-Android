package waxa.boost.juntalimpio.espaciosnaturales.seleccioncomunidades;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import waxa.boost.juntalimpio.R;

/**
 * Created by waxa2 on 4/01/15.
 */
public class AdvertenciaFragment extends DialogFragment {

    private Clikao listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Los datos que se mostraran a continuacion pueden ser erroneos," +
                "sin embargo son capaces de ilustrar el funcionamiento de la aplicacion").setTitle("Aviso")
                .setNeutralButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onClikao();
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface Clikao {
        public void onClikao();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (Clikao) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}