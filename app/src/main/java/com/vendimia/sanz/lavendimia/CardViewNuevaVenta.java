package com.vendimia.sanz.lavendimia;
/**
 *  Created by sanz on 5/12/16.
 */
import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

@SuppressLint("NewApi")
public class CardViewNuevaVenta extends RecyclerView.Adapter <CardViewNuevaVenta.cardViewAlertasHolder>{
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    private List<cardViewDistribute> listaAlertas;
    public CardViewNuevaVenta(List<cardViewDistribute> alertaInfo) {
        this.listaAlertas = alertaInfo;
    }
    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return listaAlertas.size();
    }
    @Override
    public void onBindViewHolder(final cardViewAlertasHolder holder, int i) {

        cardViewDistribute cvd = listaAlertas.get(i);
        holder.descripcion.setText(cvd.descripcion);
        holder.Modelo.setText(cvd.modelo);
        holder.precio.setText(cvd.precio);
        holder.importe.setText(cvd.importe);
        holder.cantidad.setText(cvd.cantidad+"");
       // GSG.setCatidadpuesta(holder.cantidad.getText().toString());
        final int index=i;

        holder.btn.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaAlertas.remove(index);
                notifyItemRemoved(index);
                notifyItemRangeChanged(index, listaAlertas.size());
                // GSG.setCatidadpuesta(holder.cantidad.getText().toString());
            }
        });

      holder.cantidad.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {

              try {
                  int cantid = Integer.parseInt(s.toString());
                  //Log.d("msg", cantid + "");
                  double imp = Double.parseDouble(holder.precio.getText().toString());
                  double result = imp * cantid;
                  holder.importe.setText(""+result);
                  GSG.setCantidad(result);
                  GSG.setExistencia(cantid+"");
              }catch(Exception e){
                  //Log.d("msg",e.getMessage());
               holder.importe.setText("");
                  GSG.setCantidad(0);
              }

          }
      });


    }

    @Override
    public cardViewAlertasHolder onCreateViewHolder(ViewGroup viewGroup,int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.viewcard_venta, viewGroup, false);
        return new cardViewAlertasHolder(itemView);
    }

    public class cardViewAlertasHolder extends RecyclerView.ViewHolder{
        protected TextView descripcion,Modelo,precio,importe;
        protected EditText cantidad;
        ImageButton btn;



        public cardViewAlertasHolder(View v) {
            super(v);
            //Se cargan los componentes de la cardView
            descripcion =  (TextView)  v.findViewById(R.id.VC_dsArticulo);
            Modelo =  (TextView)  v.findViewById(R.id.VC_Modelo);
            precio =  (TextView)  v.findViewById(R.id.VC_Precio);
            importe =  (TextView)  v.findViewById(R.id.VC_importe);
            btn =  (ImageButton)  v.findViewById(R.id.cv_delte);
            cantidad = (EditText) v.findViewById(R.id.VC_cantidad);

        }
    }



}
