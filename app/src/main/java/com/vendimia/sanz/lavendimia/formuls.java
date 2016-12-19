package com.vendimia.sanz.lavendimia;

import java.text.DecimalFormat;

/**
 * Created by sanz on 5/12/16.
 */

//cada una de las forumulas que venia en el pdf, de esta manera simplifico el codigo y
// evito escribir formulas repetidas
public class formuls {
   DecimalFormat decimal = new DecimalFormat("0.00");

     public void formuls(){}

    public double precio (double pArticulo,double tFina,int Pmaximos){
        double resultado;
        resultado = pArticulo *(1+(tFina*Pmaximos)/100);
        return Double.parseDouble(decimal.format(resultado));
    }
    public double importe (double precio,int cantidad){
     double resultado;
        resultado=(precio*cantidad);
       return Double.parseDouble(decimal.format(resultado));

    }
    public double engache(double porcentajeE,double importe){

        double result;
        result= (porcentajeE/100);
        result =result*importe;
        return Double.parseDouble(decimal.format(result));
    }

    public double bonificacionEn(double engache,double tasaF,int Pmaximo){
        double result;
        result = tasaF*Pmaximo;
        result = result/100;
        result = engache*result;

        return Double.parseDouble(decimal.format(result));
    }
    public double totalAdeudo(double importe,double engache,double bonficacionEn){
        double res;
        res= importe-engache-bonficacionEn;
        return Double.parseDouble(decimal.format(res));

    }
    public double preciocontado(double total_adeudo,double tasaF,int Pm){
         double res;
        res = total_adeudo/(1+((tasaF*Pm)/100));
        return Double.parseDouble(decimal.format(res));
    }
    public double total_pagar(double p_contado,double tasaf,int plazo){
        double res;
        res = p_contado*(1+(tasaf*plazo)/100);
        return Double.parseDouble(decimal.format(res));
    }
    public double importeAbono(double total_pagar,int plazo){
        double res;
        res= total_pagar/plazo;
        return Double.parseDouble(decimal.format(res));
    }
    public double importeAhorro(double tadeudo,double tpagar){
        double res ;
        res= tadeudo -tpagar;
        return Double.parseDouble(decimal.format(res));
    }
}

