package Miinaharava;

/**
 *
 * @author Janne Kuukkanen
 * 
 */
public class Ruutu {
    private String text;
    private Paikka loc;  // luo String muuttujan nimeltä text, Paikka tyyppisen muuttujan nimeltä loc, ja boolean tyyppiset muuttujat sille onko ruutu piilossa ja onko se merkitty
    private boolean hidden = true, marked = false;
    
    public Ruutu(Paikka loc, String text)
    {
        this.text = text; // asettaa text ja loc muuttujat
        this.loc = loc;
    }
    
    /**
     * Palauttaa ruudun teksti Stringin.
     */
    public String haeTeksti()
    {
        return text; // palauttaa ruudun tekstin
    }
    
    /**
     * Palauttaa ruutua koskettavien miinojen lukumäärän. jos ruutu on miina, palautetaan -1.
     */
    public int haeNumero()
    {
        if (!onMiina())
            return Integer.parseInt(text); // jos ruutu ei ole miina palauta sen teksti
        return -1; // jos se on miina palauta -1
    }
    
    /**
     * Palauttaa ruudun sijainnin (x, y) ruudukossa.
     */
    public Paikka haePaikka()
    {
        return loc; // palauttaa paikan
    }
    
    /**
     * Palauttaa tiedon onko ruutu piilossa vai ei.
     */
    public boolean onPiilossa()
    {
        return hidden; // palauttaa tiedon siitä onko ruutu piilossa vai ei
    }
    
    /**
     * Paljastaa ruudun.
     */
    public void paljasta()
    {
        hidden = false; // paljastaa ruudun
    }
    
    /**
     * piilottaa ruudun.
     */
    public void piilota()
    {
        hidden = true; // piilottaa ruudun
    }
    
    /**
     * Palauttaa tiedon onko tämä ruutu miina. 
     */
    public boolean onMiina()
    {
        return text.equalsIgnoreCase("mine"); // palauttaa tiedon siitä onko ruutu miina vai ei
    }
    
    /**
     * Palauttaa tästä ruudusta String-muotoisen kuvauksen
     */
    public String stringMuoto()
    {
        return "Loc: "+loc+" Text: "+text+" Hidden: "+hidden; // palauttaa ruudusta String muotoisen kuvauksen, joka sisältää sen paikan, tekstin, ja tiedon onko se piilossa vai ei
    }
    
    /**
     * Palauttaa tiedon onko tämä ruutu merkitty miinaksi.
     */
    public boolean onMerkitty()
    {
        return marked; // palauttaa tiedon onko ruutu merkitty vai ei
    }
    
    /**
     * Merkkaa tämän ruudun miinaksi.
     */
    public void merkitse()
    {
        marked = true; // merkkaa ruudun lipulla
    }
    
    /**
     * Poistaa tästä ruudusta merkinnän.
     */
    
    public void poistaMerkintä()
    {
        marked = false; // poistaa merkinnän
    }
}
