package com.example.dean.pokemonev;

import java.io.Serializable;

/**
 * Created by Dean on 27/04/2016.
 */
public class Pkmn {
    private int uid = 0;
    private String nickname = "";
    private String owner = "";
    private String id = "";
    private int totalEVs = 0;


    public Pkmn(int x1, String x2, String x3, String x4, int x5) {
        this.uid = x1;
        this.nickname = x2;
        this.owner = x3;
        this.id = x4;
        this.totalEVs = x5;

    }

    public int uniqueid() { return uid; }

    public String nname() {
        return nickname;
    }

    public String own() {
        return owner;
    }

    public String pid() {
        return id;
    }

    public int ev() { return totalEVs; }


}