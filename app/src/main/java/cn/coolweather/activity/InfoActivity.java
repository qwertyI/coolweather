package cn.coolweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.coolweather.R;
import cn.coolweather.adapter.SuggestAdapter;
import cn.coolweather.util.WeatherUtil.HttpTask;
import cn.coolweather.util.WeatherUtil.Index;
import cn.coolweather.util.WeatherUtil.MStatus;
import cn.coolweather.util.WeatherUtil.Result;

public class InfoActivity extends Activity {

    private ListView suggest_lv;
    private SuggestAdapter suggestAdapter;
    private MStatus status;
    private List<Suggest> suggests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        suggest_lv = (ListView) findViewById(R.id.more_suggest_lv);
        status = HttpTask.status;
        for (Result result : status.getResults()){
            for (Index index : result.getIndexes()){
                suggests.add(new Suggest(index.getTitle(), index.getZs(), index.getDes()));
            }
        }
        suggestAdapter = new SuggestAdapter(this, R.layout.more_suggest_item, suggests);
        suggest_lv.setAdapter(suggestAdapter);

    }

    public class Suggest{

        private String title;
        private String zs;
        private String des;

        public Suggest(String title, String zs, String des){
            this.title = title;
            this.zs = zs;
            this.des = des;
        }

        public String getTitle(){
            return this.title;
        }

        public String getZs(){
            return this.zs;
        }

        public String getDes(){
            return this.des;
        }

    }
}
