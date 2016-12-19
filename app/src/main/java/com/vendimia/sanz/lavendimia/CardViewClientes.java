package com.vendimia.sanz.lavendimia;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
/**
 *  Created by sanz on 5/12/16.
 */

@SuppressLint("NewApi")
public class CardViewClientes extends RecyclerView.Adapter <CardViewClientes.cardViewAlertasHolder>{
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    private List<cardViewDistribute> listaAlertas;
    public CardViewClientes(List<cardViewDistribute> alertaInfo) {
        this.listaAlertas = alertaInfo;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return listaAlertas.size();
    }

    public void onBindViewHolder(final cardViewAlertasHolder holder, int i) {
        cardViewDistribute ci = listaAlertas.get(i);
        holder.nombre.setText(ci.nombre_cliente);
        holder.clave.setText(ci.clave_cliente);
        holder.hiden.setText(ci.JsonCl);

        holder.edit.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.edit.setBackgroundResource(android.R.drawable.ic_menu_view);
                    }
                },300);
                GSG.setJSONClientes(holder.hiden.getText().toString());
                GSG.setTagC(1);
            }
        });

    }

    @Override
    public cardViewAlertasHolder onCreateViewHolder(ViewGroup viewGroup,int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.viewcard_clientes, viewGroup, false);
        return new cardViewAlertasHolder(itemView);
    }


    public class cardViewAlertasHolder extends RecyclerView.ViewHolder{
        protected TextView clave,nombre,hiden;
        protected ImageButton edit;


        public cardViewAlertasHolder(View v) {
            super(v);
            //Se cargan los componentes de la cardView
            nombre =  (TextView)  v.findViewById(R.id.C_nombre);
            clave =  (TextView)  v.findViewById(R.id.C_clave);
            edit =  (ImageButton)  v.findViewById(R.id.C_edit);//editar
            hiden =  (TextView)  v.findViewById(R.id.idclienteoculto);






        }
    }
    }






