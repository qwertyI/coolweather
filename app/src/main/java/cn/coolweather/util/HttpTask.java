package cn.coolweather.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;

import cn.coolweather.R;
import cn.coolweather.activity.CoolActivity.SimpleWeather;
import cn.coolweather.adapter.WeatherAdapter;

/**
 * Created by wei on 2015/12/10.
 */
public class HttpTask extends AsyncTask<Void, String, Boolean> {

    private final static String URL = "http://api.map.baidu.com/telematics/v3/weather?location=%E6%8A%9A%E5%B7%9E&output=json&ak=H9VNal4gTO6wKz2XjIQpvSWg";

    private Context mContext;
    private ProgressDialog progressDialog;
    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private TextView mTextView;
    private Gson gson;
    private ListView listView;
    private WeatherAdapter weatherAdapter;
    private List<SimpleWeather> mWeathers;
    private List<Result> mResult;
    private ImageLoader imageLoader;
    private ImageLoader.ImageListener listener;

    public HttpTask(Context context, ListView listView, WeatherAdapter weatherAdapter, List<SimpleWeather> weathers){
        this.mContext = context;
        this.listView = listView;
        this.weatherAdapter = weatherAdapter;
        this.mWeathers = weathers;
//        this.mTextView = textView;
        progressDialog = new ProgressDialog(mContext, 0);
        mQueue = Volley.newRequestQueue(mContext);
        gson = new Gson();
        this.weatherAdapter = new WeatherAdapter(this.mContext, R.layout.weather_item, this.mWeathers);
        this.listView.setAdapter(this.weatherAdapter);
    }

    @Override
    protected void onPreExecute(){
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params){
        stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TAG", response);
                cn.coolweather.util.Status status = gson.fromJson(response, cn.coolweather.util.Status.class);
                List<Result> results = status.getResults();
                for (Result result : results) {
                    Log.i("TAG", result.getCurrentCity());
                    Log.i("TAG", result.getPm25());
                    List<Index> indexes = result.getIndexes();
                    List<Weather> weathers = result.getWeathers();
                    for (Index index : indexes) {
                        Log.i("TAG", index.getDes());
                        Log.i("TAG", index.getTipt());
                        Log.i("TAG", index.getTitle());
                        Log.i("TAG", index.getZs());
                    }
                    for (Weather weather : weathers) {
                        getImageBitmap(weather.getDayPictureUrl());
                        SimpleWeather weather1 = new SimpleWeather(weatherPicture, weather.getTemperature(), weather.getWind());
                        mWeathers.add(weather1);
                        publishProgress(weather.getDate());
                        Log.i("TAG", weather.getDate());
                        Log.i("TAG", weather.getTemperature());
                        Log.i("TAG", weather.getWeather());
                        Log.i("TAG", weather.getWind());
                        Log.i("TAG", weather.getDayPictureUrl());
                        Log.i("TAG", weather.getNightPictureUrl());
                    }
                }
                Log.i("TAG", status.getDate());
                Log.i("TAG", gson.toJson(status.getResults().get(0)));
                Log.i("TAG", status.getStatus());
//                try{
//                    JSONObject Status = new JSONObject(response);
//                    Log.i("TAG", Status.optString("status"));
//                    Log.i("TAG", Status.optString("date"));
//                    JSONArray Results = new JSONArray(String.valueOf(Status.getJSONArray("results")));
//                    for (int i = 0; i < Results.length(); i++){
//                        JSONObject subResults = Results.optJSONObject(i);
//                        Log.i("TAG", subResults.optString("weather_data"));
//                        JSONArray Weather = new JSONArray(subResults.getJSONArray("weather_data").toString());
//                        for (int j = 0; j < Weather.length(); j++){
//                            JSONObject subWeather = Weather.optJSONObject(j);
//                            Log.i("TAG", subWeather.optString("date"));
//                            Log.i("TAG", subWeather.optString("weather"));
//                            Log.i("TAG", subWeather.optString("wind"));
//                            Log.i("TAG", subWeather.optString("temperature"));
//                            Log.i("TAG", subWeather.optString("dayPictureUrl"));
//                            Log.i("TAG", subWeather.optString("nightPictureUrl"));
//                        }
//                        Log.i("TAG", subResults.optString("index"));
//                        JSONArray Index = new JSONArray(subResults.getJSONArray("index").toString());
//                        for (int j = 0; j < Index.length(); j++){
//                            JSONObject subIndex = Index.optJSONObject(j);
//                            Log.i("TAG", subIndex.optString("title"));
//                            Log.i("TAG", subIndex.optString("zs"));
//                            Log.i("TAG", subIndex.optString("tipt"));
//                            Log.i("TAG", subIndex.optString("des"));
//                        }
//                        Log.i("TAG", subResults.optString("currentCity"));
//                        Log.i("TAG", subResults.optString("pm25"));
//
//
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("TAG", volleyError.getMessage());
            }
        });
        mQueue.add(stringRequest);

        return true;
    }

    @Override
    protected void onProgressUpdate(String... progress){
        weatherAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Boolean result){
        progressDialog.dismiss();
    }

    public void getImageBitmap(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        weatherPicture = bitmap;
                    }
                }, 50, 70, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, "cuocuocuo", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(imageRequest);
    }

}
