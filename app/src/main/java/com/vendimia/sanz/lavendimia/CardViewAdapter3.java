package com.vendimia.sanz.lavendimia;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

@SuppressLint("NewApi")
public class CardViewAdapter3 extends RecyclerView.Adapter <CardViewAdapter3.cardViewAlertasHolder>{
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    private List<cardViewDistribute> listaAlertas;
    public CardViewAdapter3(List<cardViewDistribute> alertaInfo) {
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


        final int index=i;

        holder.boton.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                holder.boton.setBackgroundResource(android.R.color.darker_gray);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.boton.setBackgroundResource(android.R.drawable.ic_menu_view);
                        //ic_menu_edit
                    }
                },300);

                GSG.setJSONClientes(holder.hiden.getText().toString());
                Log.d("json",GSG.getJSONClientes());
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
        protected TextView clave,nombre,boton,hiden;
        protected Button cantidad;


        public cardViewAlertasHolder(View v) {
            super(v);
            //Se cargan los componentes de la cardView
            nombre =  (TextView)  v.findViewById(R.id.name);
            clave =  (TextView)  v.findViewById(R.id.clave);
            boton =  (TextView)  v.findViewById(R.id.edit);//editar
            hiden =  (TextView)  v.findViewById(R.id.hiden);






        }
    }
    }






