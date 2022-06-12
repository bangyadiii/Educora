package com.example.coursera.ui.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.coursera.R;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }
    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.loading_progess, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

    }
    public void dissmisDialog(){
        alertDialog.dismiss();
    }

}
