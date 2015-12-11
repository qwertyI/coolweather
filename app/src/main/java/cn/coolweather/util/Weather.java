package cn.coolweather.util;

/**
 * Created by wei on 2015/12/10.
 */
public class Weather {
    /*"date": "周日",
    "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/yin.png",
    "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/xiaoxue.png",
    "weather": "阴转小雪",
    "wind": "微风",
    "temperature": "4 ~ -2℃"*/

    private String date;
    private String weather;
    private String wind;
    private String temperature;
    private String dayPictureUrl;
    private String nightPictureUrl;

    public void setDate(String date){
        this.date = date;
    }

    public void setWeather(String weather){
        this.weather = weather;
    }

    public void setWind(String wind){
        this.wind = wind;
    }

    public void setTemperature(String temperature){
        this.temperature = temperature;
    }

    public void setDayPictureUrl(String dayPictureUrl){
        this.dayPictureUrl = dayPictureUrl;
    }

    public void setNightPictureUrl(String nightPictureUrl){
        this.nightPictureUrl = nightPictureUrl;
    }

    public String getDate(){
        return date;
    }

    public String getWeather(){
        return weather;
    }

    public String getWind(){
        return wind;
    }

    public String getTemperature(){
        return temperature;
    }

    public String getDayPictureUrl(){
        return dayPictureUrl;
    }

    public String getNightPictureUrl(){
        return nightPictureUrl;
    }

}
