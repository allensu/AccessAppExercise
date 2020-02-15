package com.example.accessappexercise.utility;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.accessappexercise.R;


public class DialogUtils {


    public interface DialogCallbackInterface {
        void onDialogOkClick(DialogInterface dialogInterface, int i);
        void onDialogCancelClick(DialogInterface dialogInterface, int i);
    }

    public static void showMessageDalaog(Activity activity, String message, final DialogCallbackInterface callback) {
        AlertDialog.Builder alertDg = new AlertDialog.Builder(activity);

        alertDg.setMessage(message);
        alertDg.setPositiveButton(activity.getString(R.string.api_error_dialog_confirm_ok_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(callback != null) {
                    callback.onDialogOkClick(dialog, which);
                } else {
                    dialog.dismiss();
                }
            }
        });

        alertDg.create().show();
    }

    public static void showToastMessage(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

}
