package cn.coolweather.util.WeatherUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

import cn.coolweather.R;
import cn.coolweather.activity.CoolActivity.SimpleWeather;
import cn.coolweather.adapter.WeatherAdapter;

/**
 * Created by wei on 2015/12/10.
 */
public class HttpTask extends AsyncTask<Void, String, Boolean> {

    private final static String URL = "http://api.map.baidu.com/telematics/v3/weather?location=";
    private final static String AK = "H9VNal4gTO6wKz2XjIQpvSWg";

    private Context mContext;
    private ProgressDialog progressDialog;
    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private Gson gson;
    private ListView listView;
    private WeatherAdapter weatherAdapter;
    private List<SimpleWeather> mWeathers;
    private String City;
    private String url;
    public static MStatus status;

    public HttpTask(Context context, ListView listView,
                    List<SimpleWeather> weathers,
                    String city){
        this.mContext = context;
        this.listView = listView;
        this.mWeathers = weathers;
        progressDialog = new ProgressDialog(mContext);
        mQueue = Volley.newRequestQueue(mContext);
        gson = new Gson();
        this.weatherAdapter = new WeatherAdapter(this.mContext, R.layout.weather_item, this.mWeathers);
        this.listView.setAdapter(this.weatherAdapter);
        this.listView.setDividerHeight(0);
        this.City = URLEncoder.encode(city);
        url = URL + this.City + "&output=json&ak=" + AK;
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
                if (!mWeathers.isEmpty()){
                    mWeathers.clear();
                }
                Log.i("TAG", response);
                status = gson.fromJson(response, MStatus.class);
                List<Result> results = status.getResults();
                for (Result result : results) {
                    List<Weather> weathers = result.getWeathers();
                    for (Weather weather : weathers) {
                        SimpleWeather weather1;
                        Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        if (hour <= 6 || hour >=18){
                            weather1 = new SimpleWeather(weather.getNightPictureUrl(), weather.getTemperature(), weather.getWind(), weather.getDate());
                        }else {
                            weather1 = new SimpleWeather(weather.getDayPictureUrl(), weather.getTemperature(), weather.getWind(), weather.getDate());
                        }
                        mWeathers.add(weather1);
                        publishProgress(weather.getDayPictureUrl());
                    }
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
        weatherAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Boolean result){
        progressDialog.dismiss();
    }
}
