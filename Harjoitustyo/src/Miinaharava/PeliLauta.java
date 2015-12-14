package Miinaharava;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Janne Kuukkanen
 * 
 */
public class PeliLauta extends JComponent {
    private static ArrayList<Rectangle2D.Double> ruudukko = new ArrayList<>();
   public static double ikkLev, ikkKor; // alusta muuttujat ruudukon ja JFramen korkeudelle ja leveydelle
   
    @Override
   public void paintComponent (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;  // alusta grafiikat
        
        if (ruudukko.isEmpty()) // täyttää ruudukon jos se on tyhjä
            resetRuudukko(); 
            
        if (Miinaharava.valmiinaMaalaamaan) // varmista että kaikki on valmiina maalaukseen ennen kuin alat maalata
        {
        for (int i = 0; i < ruudukko.size(); i++)
        {
            if (Miinaharava.ruudut.get(i).onPiilossa())
                g2.setColor(Color.LIGHT_GRAY); // maalaa ruudun vaalean harmaaksi jos se on piilossa
            else if (Miinaharava.ruudut.get(i).onMerkitty())
                g2.setColor(Color.RED); // jos ruutu on merkattu lipulla se maalataan punaiseksi
            else if (!Miinaharava.ruudut.get(i).onMiina())
                g2.setColor(Color.WHITE); // muissa tapauksissa paitsi jos ruutu on miina se maalataan valkoiseksi
            g2.fill(ruudukko.get(i));
            g2.setColor(Color.BLACK); // aseta väriksi musta ja maalaa neliöt
            g2.draw(ruudukko.get(i));
        }
        
        for (int i = 0; i < Miinaharava.ruudut.size(); i++)
        {
            if (!Miinaharava.ruudut.get(i).onPiilossa()) // suoritetaan vain jos ruutu ei ole piilossa
            {
            Rectangle2D.Double block = ruudukko.get(i); // varastoi nykyisen ruudukon ruudun muuttujana
            if (Miinaharava.ruudut.get(i).onMerkitty()) // tarkistaa onko ruutu merkitty miinaksi
            {
                g2.setColor(Color.RED);
//                Image image = Miinaharava.lippu;
            }
            else if (Miinaharava.ruudut.get(i).haeNumero() == 1) // suoritetaan jos ruudulla on numero "1"
            {
                g2.setColor(Color.BLUE); // Asettaa värin ja piirtää numeron ruudulle
                g2.drawString("1", (float) ((block.getWidth()/2)+block.getX()), (float) ((block.getHeight()/2)+block.getY()));
            }
            else if (Miinaharava.ruudut.get(i).haeNumero() == 2) // suoritetaan jos ruudulla on numero "2"
            {
                g2.setColor(Color.MAGENTA);
                g2.drawString("2", (float) ((block.getWidth()/2)+block.getX()), (float) ((block.getHeight()/2)+block.getY()));
            }
            else if (Miinaharava.ruudut.get(i).haeNumero() == 3) // suoritetaan jos ruudulla on numero "3"
            {
                g2.setColor(Color.RED);
                g2.drawString("3", (float) ((block.getWidth()/2)+block.getX()), (float) ((block.getHeight()/2)+block.getY()));
            }
            else if (Miinaharava.ruudut.get(i).haeNumero() == 4) // suoritetaan jos ruudulla on numero "4"
            {
                g2.setColor(new Color(0, 0, 128));
                g2.drawString("4", (float) ((block.getWidth()/2)+block.getX()), (float) ((block.getHeight()/2)+block.getY()));
            }
            else if (Miinaharava.ruudut.get(i).haeNumero() == 5) // suoritetaan jos ruudulla on numero "5"
            {
                g2.setColor(new Color(153, 77, 0));
                g2.drawString("5", (float) ((block.getWidth()/2)+block.getX()), (float) ((block.getHeight()/2)+block.getY()));
            }
            else if (Miinaharava.ruudut.get(i).haeNumero() == 6) // suoritetaan jos ruudulla on numero "6"
            {
                g2.setColor(Color.CYAN);
                g2.drawString("6", (float) ((block.getWidth()/2)+block.getX()), (float) ((block.getHeight()/2)+block.getY()));
            }
            else if (Miinaharava.ruudut.get(i).haeNumero() == 7) // suoritetaan jos ruudulla on numero "7"
            {
                g2.setColor(Color.BLACK);
                g2.drawString("7", (float) ((block.getWidth()/2)+block.getX()), (float) ((block.getHeight()/2)+block.getY()));
            }
            else if (Miinaharava.ruudut.get(i).haeNumero() == 8) // suoritetaan jos ruudulla on numero "8"
            {
                g2.setColor(Color.GRAY);
                g2.drawString("8", (float) ((block.getWidth()/2)+block.getX()), (float) ((block.getHeight()/2)+block.getY()));
            }
            else if (Miinaharava.ruudut.get(i).haeNumero() == -1) // suoritetaan jos ruutu on miina
            {
                g2.setColor(Color.BLACK);
//                Image image = Miinaharava.miina;
            }
        }
        
        if (Miinaharava.gameOver)
        {
            for (Paikka loc:Miinaharava.paikkaX)
            {
            }
        }
        }
    }
    }
    
    public static void resetRuudukko()
    {
        ArrayList<Rectangle2D.Double> ruudukko1 = new ArrayList<>(); // tekee uuden arraylistin
        for (int i = 0; i < Miinaharava.ruudKor; i++)
            for (int j = 0; j < Miinaharava.ruudLev; j++) // täyttää arraylistin tyhjillä ruuduilla
                ruudukko1.add(new Rectangle2D.Double((ikkLev/Miinaharava.ruudLev) * i, (ikkKor/Miinaharava.ruudKor) * j, (ikkLev/Miinaharava.ruudLev), (ikkKor/Miinaharava.ruudKor)));
        ruudukko = ruudukko1; // korvaa nykyisen ruudukon
    }
}
