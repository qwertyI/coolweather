package cn.coolweather.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.coolweather.R;
import cn.coolweather.adapter.WeatherAdapter;
import cn.coolweather.util.HttpTask;

public class CoolActivity extends AppCompatActivity implements OnClickListener{

    private Button get_data;
    private ListView show_simple_weather;
    private WeatherAdapter weatherAdapter;
    private List<SimpleWeather> weathers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        get_data = (Button) findViewById(R.id.get_data_btn);
        show_simple_weather = (ListView) findViewById(R.id.weather_lv);
        get_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.get_data_btn:
                weathers.clear();
                new HttpTask(this, show_simple_weather, weatherAdapter, weathers).execute();
                break;
            default:break;
        }
    }

    public static class SimpleWeather{
        private Bitmap dayPictureUrl;
        private String temperature;
        private String wind;

        public SimpleWeather(Bitmap dayPictureUrl, String temperature, String wind){
            this.dayPictureUrl = dayPictureUrl;
            this.temperature = temperature;
            this.wind = wind;
        }

        public Bitmap getDayPictureUrl(){
            return dayPictureUrl;
        }

        public String getTemperature(){
            return temperature;
        }

        public String getWind(){
            return wind;
        }

    }

}
