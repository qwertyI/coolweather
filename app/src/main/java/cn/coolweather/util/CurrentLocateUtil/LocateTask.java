package cn.coolweather.util.CurrentLocateUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by wei on 2015/12/22.
 */
public class LocateTask extends AsyncTask<Void, String, Boolean> {

    private final static String LOCATE_URL = "http://api.map.baidu.com/geocoder/v2/?";
    private static final String AK = "H9VNal4gTO6wKz2XjIQpvSWg";
    private final static String OTHER_PARAM = "output=json&pois=1&location=";

    private Context mContext;
    private ProgressDialog progressDialog;
    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private Location mLocation;
    private TextView mCity;
    private String url;
    private JSONObject jsonObject;

    public LocateTask(Context context, Location location, TextView city){
        this.mContext = context;
        progressDialog = new ProgressDialog(this.mContext, 1);
        mQueue = Volley.newRequestQueue(this.mContext);
        this.mLocation = location;
        this.mCity = city;
        url = LOCATE_URL + OTHER_PARAM + location.getLatitude() + "," + location.getLongitude() + "&ak=" + AK;
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
                Log.i("LOCATETASK", response);
                JSONTokener tokener = new JSONTokener(response);
                try {
                    jsonObject = (JSONObject) tokener.nextValue();
                    publishProgress("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        try {
            mCity.setText(jsonObject.getJSONObject("result").getJSONObject("addressComponent").getString("city"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Boolean result){
        progressDialog.dismiss();
    }

}
