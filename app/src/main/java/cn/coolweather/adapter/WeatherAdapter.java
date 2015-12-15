package cn.coolweather.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import cn.coolweather.R;
import cn.coolweather.activity.CoolActivity.SimpleWeather;

/**
 * Created by wei on 2015/12/11.
 */
public class WeatherAdapter extends ArrayAdapter<SimpleWeather>{

    private int resourceId;
    private RequestQueue mQueue;
    private ImageLoader imageLoader;

    public WeatherAdapter(Context context, int resource, List<SimpleWeather> objects){
        super(context, resource, objects);
        resourceId = resource;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group){
        SimpleWeather simpleWeather = getItem(position);
        View view = convertView;
        final ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.picture = (ImageView) view.findViewById(R.id.weather_iv);
            viewHolder.temperature = (TextView) view.findViewById(R.id.weather_temperature_tv);
            viewHolder.wind = (TextView) view.findViewById(R.id.weather_wind_tv);
            viewHolder.date = (TextView) view.findViewById(R.id.date_tv);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageRequest imageRequest = new ImageRequest(simpleWeather.getDayPictureUrl(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        viewHolder.picture.setImageBitmap(bitmap);
                    }
                }, 50, 70, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                });
        mQueue.add(imageRequest);
        viewHolder.temperature.setText(simpleWeather.getTemperature());
        viewHolder.wind.setText(simpleWeather.getWind());
        viewHolder.date.setText(simpleWeather.getDate());
        return view;
    }

    class ViewHolder{
        ImageView picture;
        TextView temperature;
        TextView wind;
        TextView date;
    }
}
