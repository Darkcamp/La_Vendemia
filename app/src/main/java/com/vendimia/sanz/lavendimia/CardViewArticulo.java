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
public class CardViewArticulo extends RecyclerView.Adapter <CardViewArticulo.cardViewAlertasHolder>{
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    private List<cardViewDistribute> listaAlertas;
    public CardViewArticulo(List<cardViewDistribute> alertaInfo) {
        this.listaAlertas = alertaInfo;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return listaAlertas.size();
    }
    @Override
    public void onBindViewHolder(final cardViewAlertasHolder holder, int i) {
        cardViewDistribute ci = listaAlertas.get(i);
        holder.descripcion.setText(ci.descripcionArt);
        holder.clave.setText(ci.clave_articulo);
        holder.hiden.setText(ci.JsonAr);
        holder.editar.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.editar.setBackgroundResource(android.R.drawable.ic_menu_view);
                    }
                },300);
                GSG.setJSONProductos(holder.hiden.getText().toString());
                GSG.setTag2(1);
            }
        });



    }

    @Override
    public cardViewAlertasHolder onCreateViewHolder(ViewGroup viewGroup,int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.viewcard_articulo, viewGroup, false);
        return new cardViewAlertasHolder(itemView);
    }


    public class cardViewAlertasHolder extends RecyclerView.ViewHolder{
        protected TextView clave,descripcion,hiden;
        protected ImageButton editar;


        public cardViewAlertasHolder(View v) {
            super(v);
            //Se cargan los componentes de la cardView
            clave =  (TextView)  v.findViewById(R.id.claveArt);
            descripcion =  (TextView)  v.findViewById(R.id.descrip);
            editar =  (ImageButton)  v.findViewById(R.id.eidt_artuclob);
            hiden =  (TextView)  v.findViewById(R.id.id_oculto);

        }
    }



}
