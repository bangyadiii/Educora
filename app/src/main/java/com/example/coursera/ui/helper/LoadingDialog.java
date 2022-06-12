package com.example.coursera.ui.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.coursera.R;

public class LoadingDialog {
    static Activity activity1;
    private AlertDialog alertDialog;
    private static LoadingDialog newInstance;

    private LoadingDialog(){}
    public static LoadingDialog getInstance(Activity activity){
        activity1 = activity;
        if(newInstance == null){
            newInstance = new LoadingDialog();
        }
        return newInstance;



    }
    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity1);

        LayoutInflater inflater = activity1.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.loading_progess, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

    }
    public void dissmisDialog(){
        alertDialog.dismiss();
    }

}
