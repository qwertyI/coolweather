package cn.coolweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.coolweather.R;
import cn.coolweather.activity.CityActivity.city_name;

/**
 * Created by wei on 2015/12/18.
 */
public class CityNameAdapter extends ArrayAdapter<city_name> {

    private int resourceId;
    private List<city_name> objects;

    public CityNameAdapter(Context context, int resource, List<city_name> objects){
        super(context, resource, objects);
        resourceId = resource;
        this.objects = objects;
    }

    class ViewHolder{
        TextView city_name;
    }

    @Override
    public View getView(int positon, View convertView, ViewGroup group){
        city_name cityName = getItem(positon);
        final ViewHolder viewHolder;
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.city_name = (TextView) view.findViewById(R.id.city_name_tv);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.city_name.setText(cityName.getName());
        return view;
    }

}
