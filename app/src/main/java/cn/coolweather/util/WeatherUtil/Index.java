package cn.coolweather.util.WeatherUtil;

/**
 * Created by wei on 2015/12/10.
 */
public class Index {

    /*"title": "穿衣",
    "zs": "较冷",
    "tipt": "穿衣指数",
    "des": "建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"*/

    private String title;
    private String zs;
    private String tipt;
    private String des;

    public void setTitle(String title){
        this.title = title;
    }

    public void setZs(String zs){
        this.zs = zs;
    }

    public void setTipt(String tipt){
        this.tipt = tipt;
    }

    public void setDes(String des){
        this.des = des;
    }

    public String getTitle(){
        return title;
    }

    public String getZs(){
        return zs;
    }

    public String getTipt(){
        return tipt;
    }

    public String getDes(){
        return des;
    }

}
