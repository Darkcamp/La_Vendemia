package autocomplet;

/**
 * Created by sanz on 4/12/16.
 */

public class clientGetSet {
    String id_clientes,nombre,apellidoP,apellidoM,rfc;

    public clientGetSet(String id_clientes,String nombre,String apellidoP,String apellidoM,String rfc){
        this.setId_clientes(id_clientes);
        this.setNombre(nombre);
        this.setApellidoP(apellidoP);
        this.setApellidoM(apellidoM);
        this.setRfc(rfc);
    }
    public String getId_clientes(){
        return id_clientes;
    }
    public void setId_clientes(String id_clientes){
        this.id_clientes = id_clientes;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getApellidoP(){
        return apellidoP;
    }
    public void setApellidoP(String apellidoP){
        this.apellidoP = apellidoP;
    }
    public String getApellidoM(){
        return apellidoM;
    }
    public void setApellidoM(String apellidoM){
        this.apellidoM = apellidoM;
    }
    public String getRfc()
    {
        return rfc;
    }
    public void setRfc(String rfc){
        this.rfc = rfc;
    }
}
