package com.example.dean.pokemonev;

/**
 * Created by Dean on 28/04/2016.
 */
public class EVLog {
    private String evType;
    private int value;
    private String evType2 = null;
    private int value2 = 0;
    public EVLog(String ev, int val, String ev2, int val2)
    {
        evType = ev;
        value = val;
        evType2=ev2;
        value2 = val2;
    }
    public String ev()
    {
        return evType;
    }
    public int val()
    {
        return value;
    }
    public String ev2()
    {
        return evType2;
    }
    public int val2()
    {
        return value2;
    }
}
