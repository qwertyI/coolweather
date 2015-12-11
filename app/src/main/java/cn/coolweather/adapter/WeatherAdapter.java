package cn.coolweather.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.coolweather.R;
import cn.coolweather.activity.CoolActivity.SimpleWeather;

/**
 * Created by wei on 2015/12/11.
 */
public class WeatherAdapter extends ArrayAdapter<SimpleWeather>{

    private int resourceId;

    public WeatherAdapter(Context context, int resource, List<SimpleWeather> objects){
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group){
        SimpleWeather simpleWeather = getItem(position);
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.picture = (ImageView) view.findViewById(R.id.weather_iv);
            viewHolder.temperature = (TextView) view.findViewById(R.id.weather_temperature_tv);
            viewHolder.wind = (TextView) view.findViewById(R.id.weather_wind_tv);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Log.i("TAG", simpleWeather.getDayPictureUrl()+"");
        viewHolder.picture.setImageBitmap(simpleWeather.getDayPictureUrl());
        viewHolder.temperature.setText(simpleWeather.getTemperature());
        viewHolder.wind.setText(simpleWeather.getWind());
        return view;
    }

    class ViewHolder{
        ImageView picture;
        TextView temperature;
        TextView wind;
    }

    public Bitmap getUrlImage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

}
