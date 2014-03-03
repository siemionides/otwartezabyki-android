package pl.siemionczyk.otwartezabytki.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import pl.siemionczyk.otwartezabytki.BuildConfig;

/**
 * Created by majkeliusz on 7/7/13.
 */
public class HelperToolkit {

    public static AlertDialog.Builder createAlertDialog(Context cx,
                                                        String title, String message ){
        return new AlertDialog.Builder(cx)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });
    }

    public static void makeToast(Context appContext, String valueToShow, boolean isProductionToast){
        if ( BuildConfig.DEBUG || isProductionToast ){
            Toast.makeText(appContext,
                    valueToShow,
                    Toast.LENGTH_LONG).show();
        }


    }

    public static int returnFive (){
        return 5;
    }
}