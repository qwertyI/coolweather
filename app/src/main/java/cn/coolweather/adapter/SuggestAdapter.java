package cn.coolweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.coolweather.R;
import cn.coolweather.activity.InfoActivity.Suggest;

/**
 * Created by wei on 2015/12/21.
 */
public class SuggestAdapter extends ArrayAdapter<Suggest> {

    private int resourceId;
    private List<Suggest> objects;

    public SuggestAdapter(Context context, int resource, List<Suggest> objects){
        super(context, resource, objects);
        resourceId = resource;
        this.objects = objects;
    }

    class ViewHolder{
        TextView title;
        TextView zs;
        TextView des;
    }

    @Override
    public View getView(int positon, View convertView, ViewGroup group){
        Suggest suggest = getItem(positon);
        final ViewHolder viewHolder;
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title_suggest_tv);
            viewHolder.zs = (TextView) view.findViewById(R.id.zs_suggest_tv);
            viewHolder.des = (TextView) view.findViewById(R.id.des_suggest_tv);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(suggest.getTitle());
        viewHolder.zs.setText(suggest.getZs());
        viewHolder.des.setText(suggest.getDes());
        return view;
    }

}
