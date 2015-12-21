package cn.coolweather.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.coolweather.R;
import cn.coolweather.util.LocationTask;
import cn.coolweather.util.WeatherUtil.HttpTask;

public class CoolActivity extends Activity implements OnClickListener {

    private final static String CURRENT = "current_city";

    private LocationManager locationManager;
    private Location location;
    private Context mContext;
    private Button get_data;
    private ListView show_simple_weather;
    private List<SimpleWeather> weathers = new ArrayList<>();
    private TextView select_city;
    private SharedPreferences.Editor editor;
    private SharedPreferences spf;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        location = getLocalAddress();
        new LocationTask (this, location).execute();
        mContext = this;
        calendar = Calendar.getInstance();
        get_data = (Button) findViewById(R.id.get_data_btn);
        select_city = (TextView) findViewById(R.id.city_name_tv);
        show_simple_weather = (ListView) findViewById(R.id.weather_lv);
        get_data.setOnClickListener(this);
        select_city.setOnClickListener(this);
        editor = getSharedPreferences(CURRENT, MODE_PRIVATE).edit();
        spf = getSharedPreferences(CURRENT, MODE_PRIVATE);
        if (spf.getString(CURRENT, "") != "") {
            select_city.setText(spf.getString(CURRENT, ""));
            new HttpTask(this, show_simple_weather, weathers, select_city.getText().toString()).execute();
        }
        show_simple_weather.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (position == 0) {
                    Intent intent = new Intent(mContext, InfoActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(mContext, "啊哦，无法显示其他日期的详细情况", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public static MStatus getStatus(){
//        return status;
//    }

    private Location getLocalAddress() {
        LocationManager locationManager;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return null;
            }
        }
        Location location = locationManager.getLastKnownLocation(provider);
        Log.i("LOCATION", location.getAltitude() + "");
        Log.i("LOCATION", location.getLatitude() + "");
        return location;
        //updateWithNewLocation(location);
//        locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
//        updateWithNewLocation(location);
    }


    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };


    private void updateWithNewLocation(Location location) {
        String latLongString;
        TextView myLocationText;
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "纬度:" + lat + "\n经度:" + lng;
        } else {
            latLongString = "无法获取地理信息";
        }
        Toast.makeText(mContext, latLongString,
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.get_data_btn:
//                weathers.clear();
                if (select_city.getText().equals("请选择")){
                    Toast.makeText(mContext, "请选择所在城市", Toast.LENGTH_SHORT).show();
                }else {
                    new HttpTask(this, show_simple_weather, weathers, select_city.getText().toString()).execute();
                }
                break;
            case R.id.city_name_tv:
                Intent intent = new Intent(this, CityActivity.class);
                intent.putExtra(CURRENT, select_city.getText());
                startActivityForResult(intent, 1);
            default:break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                Log.i("TAG", data.getStringExtra("select_city"));
                select_city.setText(data.getStringExtra("select_city"));
                editor.putString(CURRENT, data.getStringExtra("select_city"));
                editor.commit();
                new HttpTask(this, show_simple_weather, weathers, select_city.getText().toString()).execute();
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