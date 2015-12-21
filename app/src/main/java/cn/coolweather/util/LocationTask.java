package cn.coolweather.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by wei on 2015/12/21.
 */
public class LocationTask extends AsyncTask<Void, String, Boolean> {
    private final static String URL = "http://api.map.baidu.com/telematics/v3/reverseGeocoding?location=";
    private final static String AK = "H9VNal4gTO6wKz2XjIQpvSWg";

    private Context mContext;
    private ProgressDialog progressDialog;
    private Location location;
    private String url;
    private RequestQueue mQueue;
    private StringRequest stringRequest;

    public LocationTask(Context context, Location location){
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext);
        this.location = location;
        mQueue = Volley.newRequestQueue(mContext);
        url = URL + this.location.getAltitude() + "," + this.location.getLatitude() + "&output=json&ak=" + AK;
        Log.i("LOCATION", url);
    }

    @Override
    protected void onPreExecute(){
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params){
        stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOCATION", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, "当前城市错误", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);
        return true;
    }

    @Override
    protected void onProgressUpdate(String... progress){
        progressDialog.setMessage(progress[0]);
    }

    @Override
    protected void onPostExecute(Boolean result){
        progressDialog.dismiss();
    }
}
