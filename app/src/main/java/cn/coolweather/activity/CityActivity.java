package cn.coolweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import cn.coolweather.R;

public class CityActivity extends Activity {

    private TextView current_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        current_city = (TextView) findViewById(R.id.current_city_tv);
        current_city.setText("当前城市："+getIntent().getStringExtra("current_city"));



    }

}
