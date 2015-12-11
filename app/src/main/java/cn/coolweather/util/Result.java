package cn.coolweather.util;

import java.util.List;

/**
 * Created by wei on 2015/12/10.
 */
public class Result {

    private String currentCity;
    private String pm25;
    private List<Index> index;
    private List<Weather> weather_data;

    public void setCurrentCity(String currentCity){
        this.currentCity = currentCity;
    }

    public void setPm25(String pm25){
        this.pm25 = pm25;
    }

    public void setIndexes(List<Index> indexes){
        this.index = indexes;
    }

    public void setWeathers(List<Weather> weathers){
        this.weather_data = weathers;
    }

    public String getCurrentCity(){
        return currentCity;
    }

    public String getPm25(){
        return pm25;
    }

    public List<Index> getIndexes(){
        return index;
    }

    public List<Weather> getWeathers(){
        return weather_data;
    }

}
