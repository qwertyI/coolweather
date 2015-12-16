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

    private final static String URL = "http://api.map.baidu.com/telematics/v3/weather?location=%E6%B7%B1%E5%9C%B3&output=json&ak=H9VNal4gTO6wKz2XjIQpvSWg";

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
    private Bitmap weatherPicture;

    public HttpTask(Context context, ListView listView, WeatherAdapter weatherAdapter, List<SimpleWeather> weathers){
        this.mContext = context;
        this.listView = listView;
        this.weatherAdapter = weatherAdapter;
        this.mWeathers = weathers;
        progressDialog = new ProgressDialog(mContext);
        mQueue = Volley.newRequestQueue(mContext);
        gson = new Gson();
        this.weatherAdapter = new WeatherAdapter(this.mContext, R.layout.weather_item, this.mWeathers);
        this.listView.setAdapter(this.weatherAdapter);
        this.listView.setDividerHeight(0);
        Log.i("TAG", URLEncoder.encode("深圳"));
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
                    List<Index> indexes = result.getIndexes();
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
                Log.i("TAG", volleyError.getMessage());
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

    public Bitmap getImageBitmap(String url){
        final Bitmap[] wPicture = new Bitmap[1];
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        wPicture[0] = bitmap;
                    }
                }, 50, 70, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, "cuocuocuo", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(imageRequest);
        return wPicture[0];
    }

}
