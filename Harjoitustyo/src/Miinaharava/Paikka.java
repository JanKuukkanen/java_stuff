package Miinaharava;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Janne Kuukkanen
 * 
 */
public class Paikka {
    private int x, y; // luo x ja y muuttujat
    
    public Paikka(int x, int y)
    {
        this.x = x; // antaa muuttujille alkuarvot
        this.y = y;
    }
    
    public int haeX()
    {
        return x; // palauttaa x-koordinaatin (sarake)
    }
    
    public int haeY()
    {
        return y; // palauttaa y-koordinaatin (rivi)
    }
    
    public boolean onVoimassa()
    {
        return (haeX() >= 0 && haeY() >= 0 && haeX() < Miinaharava.ruudLev && haeY() < Miinaharava.ruudKor); 
        // tarkastaa onko tämä sijainti voimassa (ruudukossa) Miinaharava-luokan ruudukon koordinaateissa
    }
    
    @Override
    public boolean equals(Object loc1)
    {
        Paikka loc = (Paikka) loc1;
        return (loc.haeY() == y && loc.haeX() == x);
        // tarkastaa onko tämä sijainti sama kuin jokin toinen (eli niillä olisi samat x- ja y-koordinaatit)
    }
    
    @Override
    public String toString()
    {
        return "("+x+", "+y+")";
        //palauttaa tästä sijainnista String-muotoisen kuvauksen
    }
    
    public static Paikka satunnainenPaikka(int maxX, int maxY)
    {
        Random generator = new Random();
        Paikka loc = new Paikka(generator.nextInt(maxX + 1), generator.nextInt(maxY + 1));
        return loc;
        //palauttaa satunnaisen, voimassa olevan sijainnin
    }
    
    public ArrayList<Paikka> haeViereisetPaikat()
    {
        Paikka loc = this;
        ArrayList<Paikka> locs = new ArrayList<>();
        locs.add(new Paikka(loc.haeX()-1, loc.haeY()-1));
        locs.add(new Paikka(loc.haeX(), loc.haeY()-1));
        locs.add(new Paikka(loc.haeX()+1, loc.haeY()-1));
        locs.add(new Paikka(loc.haeX()-1, loc.haeY()));
        locs.add(new Paikka(loc.haeX()+1, loc.haeY())); // palauttaa kaikki tämän sijainnin vieressä voimassa olevat sijainnit
        locs.add(new Paikka(loc.haeX()-1, loc.haeY()+1));
        locs.add(new Paikka(loc.haeX(), loc.haeY()+1));
        locs.add(new Paikka(loc.haeX()+1, loc.haeY()+1));
        
        return Paikka.poistaVirheelliset(locs);
    }
    
    public static ArrayList<Paikka> poistaVirheelliset(ArrayList<Paikka> locs)
    {
        ArrayList<Paikka> newLocs = new ArrayList<>();
        for (Paikka loc:locs) 
            if (loc.onVoimassa()) // poistaa kaikki mitättömät sijainnit annetulta arraylistiltä ja palauttaa tuloksen
                newLocs.add(loc);
        return newLocs;
    }
}