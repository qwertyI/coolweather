package cn.coolweather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.coolweather.R;
import cn.coolweather.util.CityUtil.GetCityTask;

public class CityActivity extends Activity {

    private Context mContext;
    private TextView current_city;
    private ListView province;
    private ListView state;
    private ListView county;
    private List<city_name> province_names = new ArrayList<>();
    private List<city_name> state_names = new ArrayList<>();
    private String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        mContext = this;
        current_city = (TextView) findViewById(R.id.current_city_tv);
        province = (ListView) findViewById(R.id.province_lv);
        state = (ListView) findViewById(R.id.state_lv);
        county = (ListView) findViewById(R.id.county_lv);
        current = getIntent().getStringExtra("current_city");
        current_city.setText("当前城市：" + getIntent().getStringExtra("current_city"));
        province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state_names.clear();
                new GetCityTask(mContext, state_names, state, position).execute();
            }
        });

        state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("select_city", state_names.get(position).getName()+"市");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        new GetCityTask(this, province_names, province, -1).execute();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if(keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            Intent intent = new Intent();
            intent.putExtra("select_city", current);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    public static class city_name{
        private String name;

        public city_name(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
    }

}
