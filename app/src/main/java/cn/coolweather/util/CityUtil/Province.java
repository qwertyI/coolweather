package cn.coolweather.util.CityUtil;

import java.util.List;

/**
 * Created by wei on 2015/12/18.
 */
public class Province {
    
    private String province;
    private List<State> state;

    public void setProvince(String province) {
        this.province = province;
    }

    public void setStates(List<State> state){
        this.state = state;
    }

    public String getProvince(){
        return province;
    }

    public List<State> getStates(){
        return this.state;
    }

}
