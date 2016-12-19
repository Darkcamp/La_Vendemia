package com.vendimia.sanz.lavendimia;

/**
 *  Created by sanz on 5/12/16.
 */
import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

@SuppressLint("NewApi")
public class CardViewVentasActivas extends RecyclerView.Adapter <CardViewVentasActivas.cardViewAlertasHolder>{

    private List<cardViewDistribute> listaAlertas;
    public CardViewVentasActivas(List<cardViewDistribute> alertaInfo) {
        this.listaAlertas = alertaInfo;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return listaAlertas.size();
    }
    @Override
    public void onBindViewHolder(cardViewAlertasHolder holder, int i) {
        cardViewDistribute ci = listaAlertas.get(i);
        holder.folio.setText(ci.folio);
        holder.clave.setText(ci.clave);
        holder.nombre.setText(ci.nombre);
        holder.total.setText(ci.total);
        holder.fecha.setText(ci.fecha);

    }

    public cardViewAlertasHolder onCreateViewHolder(ViewGroup viewGroup,int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.viewcard_alertas, viewGroup, false);
        return new cardViewAlertasHolder(itemView);
    }

    public class cardViewAlertasHolder extends RecyclerView.ViewHolder{
        protected TextView folio,clave,nombre,total,fecha;


        public cardViewAlertasHolder(View v) {
            super(v);
            //Se cargan los componentes de la cardView
            folio =  (TextView)  v.findViewById(R.id.FolioVentas);
            clave =  (TextView)  v.findViewById(R.id.ClaveCliente);
            nombre =  (TextView)  v.findViewById(R.id.Nombre);
            total =  (TextView)  v.findViewById(R.id.Total);
            fecha =  (TextView)  v.findViewById(R.id.Fecha);

        }
    }



}
