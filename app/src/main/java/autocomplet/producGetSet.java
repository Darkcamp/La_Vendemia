package autocomplet;

/**
 * Created by sanz on 4/12/16.
 */

public class producGetSet {

    String id_productos,descripcion,modelo,precio,existencia;
    public producGetSet(String id_productos,String descripcion,String modelo,String precio,String existencia){
        this.setId_productos(id_productos);
        this.setDescripcion(descripcion);
        this.setExistencia(existencia);
        this.setModelo(modelo);
        this.setPrecio(precio);
    }
    public String getId_productos(){
        return id_productos;
    }
    public void setId_productos(String id_productos){
        this.id_productos = id_productos;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    public String getExistencia(){
        return existencia;
    }
    public void setExistencia(String existencia){
        this.existencia = existencia;
    }
    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    public String getPrecio(){
        return precio;
    }
    public void setPrecio(String precio){
        this.precio = precio;
    }
}
