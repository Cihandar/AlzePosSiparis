package com.example.alzepossiparis.tools;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.alzepossiparis.OptionsPos;

public class ToolProgressBar  {

    Context _context;
    public ToolProgressBar(Context context){
        _context = context;
    }
    public ProgressDialog showProgress(String title,String text,int theme)
    {
        ProgressDialog  progressDialog = new ProgressDialog(_context,theme);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(title);
        progressDialog.setMessage(text);
       return progressDialog;
    }

    public AlertDialog.Builder showAlertDialog(String title,String text)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        builder.setTitle(title);
        builder.setMessage(text);
        builder.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
       return  builder;
    }

    public void showToast(String text){
        Toast.makeText(_context, text, Toast.LENGTH_LONG).show();
    }


    public float setDpFloat(int size)
    {
        DisplayMetrics metrics = _context.getResources().getDisplayMetrics();
        float dp = size;
        float fpixels = metrics.density * dp;
        float pixels = (float) (fpixels + 0.5f);
        return  pixels;
    }

    public int setDpInt(int size)
    {
        DisplayMetrics metrics = _context.getResources().getDisplayMetrics();
        float dp = size;
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return  pixels;
    }




}
