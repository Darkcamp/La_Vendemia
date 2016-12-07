package com.vendimia.sanz.lavendimia;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sanz on 5/12/16.
 */

public class DatoJson {
    private String[] tipo = null;
    private Context ctx = null;

    public DatoJson(Context ctx){
        this.ctx = ctx;
    }

    public DatoJson setArray(String tipo){
        switch (tipo)
        {
            case "ventas":
                this.tipo = ctx.getResources().getStringArray(R.array.nventa);
                break;
            case "usuario":
                this.tipo = ctx.getResources().getStringArray(R.array.usuario);
                break;
            case "articulo":
                this.tipo = ctx.getResources().getStringArray(R.array.articulo);
                break;

        }

        return this;
    }

    public String Convert(String... data){
        JSONObject datos = new JSONObject();

        for(int i = 0; i < tipo.length && i < data.length; i++){
            try {
                datos.put(tipo[i], data[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return datos.toString();
    }



}