package cn.coolweather.util.CityUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.coolweather.R;
import cn.coolweather.activity.CityActivity.city_name;
import cn.coolweather.adapter.CityNameAdapter;

/**
 * Created by wei on 2015/12/18.
 */
public class GetCityTask extends AsyncTask<Void, String, Boolean> {

    private ProgressDialog progressDialog;
    private Context mContext;
    private Gson gson;
    private List<city_name> cityNames = new ArrayList<>();
    private ListView listview;
    private CityNameAdapter cityAdapter;
    private int preCity;

    public GetCityTask(Context context, List<city_name> cityNames, ListView listview, int preCity){
        this.mContext = context;
        this.cityNames = cityNames;
        this.listview = listview;
        this.preCity = preCity;
        this.cityAdapter = new CityNameAdapter(this.mContext, R.layout.city_name_item, this.cityNames);
        Log.i("TAG", this.cityAdapter.getCount()+"");
        this.listview.setAdapter(this.cityAdapter);
        this.listview.setDividerHeight(0);
        gson = new Gson();
        progressDialog = new ProgressDialog(context, 0);
    }

    @Override
    protected void onPreExecute(){
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params){
        CityCode cityCode = gson.fromJson(new CityData().getCityData(), CityCode.class);
        if (preCity == -1){
            for (Province province : cityCode.getCityCode()){
                city_name city = new city_name(province.getProvince());
                cityNames.add(city);
//            List<State> states = province.getStates();
//            Log.i("TAG", province.getProvince());
//            for (State state : states){
//                Log.i("TAG", state.getCounty());
//                Log.i("TAG", state.getCode());
//            }
            }
        }else {
            for (State state : cityCode.getCityCode().get(preCity).getStates()){
                city_name city = new city_name(state.getCounty());
                cityNames.add(city);
                publishProgress("");
            }
            Log.i("zhebukexue", cityNames.size() + "");
        }

        return true;
    }

    @Override
    protected void onProgressUpdate(String... progress){
        progressDialog.setMessage("Loading....");
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Boolean result){
        progressDialog.dismiss();
    }
}
