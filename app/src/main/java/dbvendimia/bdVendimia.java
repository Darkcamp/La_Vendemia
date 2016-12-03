package dbvendimia;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sanz on 3/12/16.
 */

public class bdVendimia extends SQLiteOpenHelper {
    private static String name = "vendimia";
    private static int version = 1;
    private static SQLiteDatabase.CursorFactory cursorFactory = null;
    //creacion de las tablas
    protected static String TableClientes = "Clientes";
    protected static String TableVentas = "Ventas";
    protected static String TableLSettings = "Configuracion";
    protected static String TableArticulos = "Articulos";
    private String SQLCreateClientes = "CREATE TABLE " + TableClientes +  " (id_client INTEGER PRIMARY KEY AUTOINCREMENT," +
            " nombre VARCHAR(100) NOT NULL," +
            " apellidoP VARCHAR(100) NOT NULL," +
            "apellidoM VARCHAR(100) NOT NULL," +
            "RFC VARCHAR(100) NOT NULL" +
            "UNIQUE (id_client)) ";
    private String SQLCreateVentas = "CREATE TABLE " + TableVentas +  " (id_vent INTEGER PRIMARY KEY AUTOINCREMENT," +
            " claveC INT NOT NULL, " +
            "nombre VARCHAR(100) NOT NULL," +
            " total FLOAT NOT NULL," +
            "fecha DATETIME DEFAULT CURRENT_TIMESTAMP " +
            "UNIQUE (id_vent))";
    private String SQLCreateSettings = "CREATE TABLE " + TableLSettings +  " (tasaF FLOAT," +
            " engache FLOAT," +
            "plazoM INT) ";
    private String SQLCreateArticulos = "CREATE TABLE " + TableArticulos +  " (id_arti INTEGER PRIMARY KEY AUTOINCREMENT," +
            " Descripcion VARCHAR(100) NOT NULL," +
            " modelo VARCHAR(100) NOT NULL," +
            "precio FLOAT,inpote FLOAT,existencia INT " +
            "UNIQUE(id_arti))";

    public bdVendimia(Context context ){
        super(context,name,cursorFactory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCreateClientes);
        db.execSQL(SQLCreateVentas);
        db.execSQL(SQLCreateSettings);
        db.execSQL(SQLCreateArticulos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertClient(String nombre,String apellidop,String apellidom,String RFC){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            db.execSQL("INSERT INTO " + TableClientes +
                    " (nombre, apellidoP,apellidoM,RFC) " +
                    " VALUES('" + nombre + "', '" + apellidop +"', '" + apellidom + "' , '" + RFC + "') ");
            db.close();
        }
    }
    public void insertVent(int Ccliente,String nombre,Float total,String fecha){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            db.execSQL("INSERT INTO " + TableVentas +
                    " (claveC,nombre,total,fecha) " +
                    " VALUES('" + Ccliente + "', '" + nombre +"', '" + total + "' , '" + fecha + "') ");
            db.close();
        }
    }
    public void insertSettings(Float tasa,float engache,int plazos){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            db.execSQL("INSERT INTO " + TableLSettings +
                    " (tasaF, engache, plazoM) " +
                    " VALUES('" + tasa + "', '" + engache +"', '" + plazos + "') ");
            db.close();
        }
    }
    public void insertProduct(String dsc,String modelo,Float precio,int exist){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            db.execSQL("INSERT INTO " + TableArticulos +
                    " (Descripcion, modelo,precio,existencia) " +
                    " VALUES('" + dsc + "', '" + modelo +"', '" + precio + "' , '" + exist + "') ");
            db.close();
        }
    }

    public Cursor getCursorBuscador(String textSearch){
        textSearch = textSearch.replace("'", "''");
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT id_client AS _id, nombre AS item" +
                " FROM "+TableClientes +
                " WHERE nombre LIKE '%" +textSearch+ "%' ", null);
    }

}
