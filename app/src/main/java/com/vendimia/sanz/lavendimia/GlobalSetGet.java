package com.vendimia.sanz.lavendimia;

/**
 * Created by sanz on 4/12/16.
 */

public class GlobalSetGet {
    public static GlobalSetGet instance;
    //clientes
    private static String clientes,JSONClientes;
    //articulos
    private static String productos,existencia,JSONProductos,articulos,cantexistente;
    //configuracion y otras
    private static String URL,tasaF,enganche,pMaximo,fecha,FinalFolio,FinalCliente,finalProducto;
    private static double cantidad;
    private static int tc,tc2;

    //constructor vacio para manejar lo datos independientes
    private GlobalSetGet(){}

    public void setClientes(String clientes){
        GlobalSetGet.clientes = clientes;
    }
    public String getClientes(){

        return GlobalSetGet.clientes;

    }

    public void setArticulos(String articulos){
        GlobalSetGet.articulos = articulos;
    }
    public String getArticulos(){
        return GlobalSetGet.articulos;    }
    public String getCantexistente(){
        return cantexistente;
    }
    public void setCantexistente(String cantexistente){
        this.cantexistente = cantexistente;
    }

    public void setURL(String url){
        GlobalSetGet.URL = url;
    }
    public String getURL(){

        return GlobalSetGet.URL;

    }

    public void setTasaF(String tasaF){
        GlobalSetGet.tasaF = tasaF;
    }
    public String getTasaF(){

        return GlobalSetGet.tasaF;

    }

    public void setEnganche(String enganche){
        GlobalSetGet.enganche = enganche;
    }
    public String getEnganche(){

        return GlobalSetGet.enganche;

    }

    public void setpMaximo(String pMaximo){
        GlobalSetGet.pMaximo = pMaximo;
    }
    public String getpMaximo(){
        return GlobalSetGet.pMaximo;

    }

    public void setCantidad(double cantidad){
        GlobalSetGet.cantidad = cantidad;
    }
    public double getCantidad(){

        return GlobalSetGet.cantidad;

    }

    public void setJSONClientes(String jsonClientes){
        GlobalSetGet.JSONClientes = jsonClientes;
    }
    public String getJSONClientes(){

        return GlobalSetGet.JSONClientes;

    }

    public void setJSONProductos(String JSONProductos){
        GlobalSetGet.JSONProductos = JSONProductos;
    }
    public String getJSONProductos(){

        return GlobalSetGet.JSONProductos;

    }

    public void setFecha(String fecha){
        GlobalSetGet.fecha = fecha;
    }
    public String getFecha(){

        return GlobalSetGet.fecha;

    }

    public void setExistencia(String existencia){
        GlobalSetGet.existencia = existencia;
    }
    public String getExistencia(){

        return GlobalSetGet.existencia;

    }

    public void setProductos(String productos){
        GlobalSetGet.productos = productos;
    }
    public String getProductos(){

        return GlobalSetGet.productos;

    }
    public static synchronized GlobalSetGet getInstance() {

        if (instance == null) {

            instance = new GlobalSetGet();

        }

        return instance;

    }


    public int getTagC() {
        return tc;
    }

    public void setTagC(int tagC) {
        this.tc = tagC;
    }


    public void setTag2(int i) {
        this.tc2 =i;
    }
    public void setFinalFolio(String FinalFolio){
        this.FinalFolio = FinalFolio;
    }
    public String getFinalFolio(){
        return FinalFolio;
    }
    public void setFinalCliente(String FinalCliente){
        this.FinalCliente = FinalCliente;
    }
    public String getFinalCliente(){
        return FinalCliente;
    }

    public void setFinalProducto(String finalProducto) {
        this.finalProducto = finalProducto;
    }

    public String getFinalProduct() {
        return finalProducto;
    }
}
