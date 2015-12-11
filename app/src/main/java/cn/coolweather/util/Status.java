package cn.coolweather.util;

import java.util.List;

/**
 * Created by wei on 2015/12/10.
 */
public class Status {

    private int error;
    private String status;
    private String date;
    private List<Result> results;

    public void setError(int error){
        this.error = error;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setResults(List<Result> results){
        this.results = results;
    }

    public int getError(){
        return error;
    }

    public String getStatus(){
        return status;
    }

    public String getDate(){
        return date;
    }

    public List<Result> getResults(){
        return results;
    }

}
