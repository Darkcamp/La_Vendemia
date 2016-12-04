package autocomplet;

import android.content.Context;
import android.util.Log;

import com.example.sanzlibrary1_0_1.AsynResponse;
import com.example.sanzlibrary1_0_1.ServerRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sanz on 4/12/16.
 */

public class autocomplet  implements AsynResponse {
  Context context;
    double current_latitude, current_longitude;
    JSONObject jsob = null ;
    public autocomplet() {
    }

    public autocomplet(double current_latitude, double current_longitude) {
        this.current_latitude = current_latitude;
        this.current_longitude = current_longitude;
    }

    public List<clientGetSet> getParseJsonWCF(String sName) {
        List<clientGetSet> ListData = new ArrayList<clientGetSet>();

        try {
            final HashMap<String, String> postData = new HashMap<>();
            postData.put("action", "clientes");
            final ServerRequest sr = new ServerRequest(context, (AsynResponse) context);
            sr.setSendData(postData);

            sr.execute("http://chali23.000webhostapp.com/Vendemia/autocomplete.php");
            /// send(ip+"receptor/settings.php",postData);

            String temp = sName.replace(" ", "%20");

            JSONArray jsonArray = jsob.getJSONArray("jsonclient");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject r = jsonArray.getJSONObject(i);
                ListData.add(new clientGetSet(r.getString("id_clientes"), r.getString("name"),r.getString("apellidoP"),
                        r.getString("apellidoM"),r.getString("rfc")));

            }
        } catch(Exception e){
            Log.d("errro", e.getMessage());
        }
         return ListData;

    }

    @Override
    public void postInternetCheck(Boolean aBoolean) {

    }

    @Override
    public void postServeRequest(String s) {

        try {
           jsob= new JSONObject(s);


    }catch (Exception e){
      Log.d("error s:",e.getMessage());
    }

    }


}




