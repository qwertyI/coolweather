package cn.coolweather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.coolweather.R;
import cn.coolweather.adapter.WeatherAdapter;
import cn.coolweather.util.HttpTask;

public class CoolActivity extends AppCompatActivity implements OnClickListener{

    private final static int RESULT_OK = 0;

    private Button get_data;
    private ListView show_simple_weather;
    private WeatherAdapter weatherAdapter;
    private List<SimpleWeather> weathers = new ArrayList<>();
    private TextView select_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        get_data = (Button) findViewById(R.id.get_data_btn);
        select_city = (TextView) findViewById(R.id.city_name_tv);
        show_simple_weather = (ListView) findViewById(R.id.weather_lv);
        get_data.setOnClickListener(this);
        select_city.setOnClickListener(this);
        show_simple_weather.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                weathers.get(position).getDate();
            }
        });
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.get_data_btn:
                weathers.clear();
                new HttpTask(this, show_simple_weather, weatherAdapter, weathers).execute();
                break;
            case R.id.city_name_tv:
                Intent intent = new Intent(this, CityActivity.class);
                intent.putExtra("current_city", "深圳");
                startActivityForResult(intent, 0);
            default:break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (resultCode){
            case RESULT_OK:
                select_city.setText(data.getStringExtra("city_name"));
                break;
            default:break;
        }
    }

    public static class SimpleWeather{
        private String dayPictureUrl;
        private String temperature;
        private String wind;
        private String date;

        public SimpleWeather(String  dayPictureUrl, String temperature, String wind, String date){
            this.dayPictureUrl = dayPictureUrl;
            this.temperature = temperature;
            this.wind = wind;
            this.date = date;
        }

        public String getDayPictureUrl(){
            return dayPictureUrl;
        }

        public String getTemperature(){
            return temperature;
        }

        public String getWind(){
            return wind;
        }

        public String getDate(){
            return date;
        }

    }

}
