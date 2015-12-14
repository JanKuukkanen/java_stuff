package Miinaharava;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Janne Kuukkanen
 * 
 */
public class Miinaharava implements ActionListener {
    public static final JFrame paaIkkuna = new JFrame();
   public static ArrayList<Ruutu> ruudut = new ArrayList<>();
   public static int ruudLev = 10, ruudKor = 10, miinoja = 20, numMerkitty = 0; // initalize game variables
   public static ArrayList<Paikka> mineLoc, paljastetutLoc = new ArrayList<>(), paikkaX = new ArrayList<>();
   public static boolean valmiinaMaalaamaan = false, gameOver = false;
   public static Image miina = null, lippu = null;
   
   public static void main(String[] args)
   {
       Miinaharava main = new Miinaharava(); // Luo uuden Main-objektin ja suorittaa main-metodin
       main.start();
    }
    
   public void start()
   {
       InputStream mineIS = getClass().getClassLoader().getResourceAsStream("");
       InputStream flagIS = getClass().getClassLoader().getResourceAsStream("");
       try
       {
        }
       catch (Exception e)
        {
          }
       JMenuBar menuBar = new JMenuBar();
       JMenu gameMenu = new JMenu("Peli");
       JMenu diffMenu = new JMenu("Vaikeustaso");
       JMenu helpMenu = new JMenu("Apua"); // Luo uuden menu bar:in
       final JMenuItem newGame = new JMenuItem("Uusi Peli"); // Luo menu itemit
       final JMenuItem exit = new JMenuItem("Lopeta");
       final JMenuItem help = new JMenuItem("Kuinka Pelataan");
       final JMenuItem about = new JMenuItem("Tietoja Pelistä");
       final JMenuItem easy = new JMenuItem("Helppo");
       final JMenuItem mid = new JMenuItem("Keski");
       final JMenuItem hard = new JMenuItem("Vaikea");
       newGame.addActionListener(this); // lisää actionlistenerit menu itemeille
       exit.addActionListener(this);
       help.addActionListener(this);
       about.addActionListener(this);
       easy.addActionListener(this);
       mid.addActionListener(this);
       hard.addActionListener(this);
       gameMenu.add(newGame);
       gameMenu.add(exit);
       diffMenu.add(easy);
       diffMenu.add(mid);
       diffMenu.add(hard);
       helpMenu.add(help);
       helpMenu.add(about);
       menuBar.add(gameMenu);
       menuBar.add(diffMenu);
       menuBar.add(helpMenu);
       paaIkkuna.setJMenuBar(menuBar);
       
       paaIkkuna.setTitle("Minesweeper");
       paaIkkuna.setSize(500, 500);
       final PeliLauta painter = new PeliLauta();
       paaIkkuna.add(painter); // asettaa ominaisuudet pääikkunalle
       paaIkkuna.setLocationRelativeTo(null);
       paaIkkuna.setIconImage(miina);
       paaIkkuna.setVisible(true);
       paaIkkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       PeliLauta.ikkLev = SwingUtilities.convertPoint(paaIkkuna, new Point(paaIkkuna.getWidth(), paaIkkuna.getHeight()), painter).getX(); // laskee ikkunan korkeuden ja leveyden pikseleinä PeliLaudalle
       PeliLauta.ikkKor = SwingUtilities.convertPoint(paaIkkuna, new Point(paaIkkuna.getWidth(), paaIkkuna.getHeight()), painter).getY();
       
       class WindowListener implements ComponentListener
       {
            @Override
           public void componentHidden(ComponentEvent e) {}

            @Override
           public void componentMoved(ComponentEvent e) {}

            @Override
           public void componentResized(ComponentEvent e) {
               PeliLauta.ikkLev = SwingUtilities.convertPoint(paaIkkuna, new Point(paaIkkuna.getWidth(), paaIkkuna.getHeight()), painter).getX();
               PeliLauta.ikkKor = SwingUtilities.convertPoint(paaIkkuna, new Point(paaIkkuna.getWidth(), paaIkkuna.getHeight()), painter).getY();
               PeliLauta.resetRuudukko(); // Kuuntelee muutoksia ikkunaan, ja käynnistää sitten resetRuudukko metodin
/**               mine = mineOrig.getScaledInstance((int)(.75*(Painter.winWidth/gridWidth)),(int) (.75*(Painter.winHeight/gridHeight)), Image.SCALE_SMOOTH);
               flag = flagOrig.getScaledInstance((int)(.75*(Painter.winWidth/gridWidth)),(int) (.75*(Painter.winHeight/gridHeight)), Image.SCALE_SMOOTH); **/
               paaIkkuna.repaint();
            }

            @Override
            public void componentShown(ComponentEvent e) {}
        }
        
        class mouseListener implements MouseListener
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                MouseEvent event = SwingUtilities.convertMouseEvent(paaIkkuna, e, painter);
                double clickX = event.getX();
                double clickY = event.getY(); // Varastoi hiirenpainalluksen koordinaatit
                Paikka loc = haeHiirenPaikka(event.getX(), event.getY()); // Hakee hiirenpainalluksen ruudukolta
                if (!gameOver) // suoritetaan jos peli vielä jatkuu
                {
                if (e.getButton() == MouseEvent.BUTTON3 && !haeRuutuPaikasta(loc).onMerkitty()) // tarkastaa oliko hiirenpainallus suoritettu hiiren oikealla vai vasemmalla painikkeella
                {
                    if (haeRuutuPaikasta(loc).onPiilossa()) // tarkastaa onko ruutu piilossa
                    {
                    haeRuutuPaikasta(loc).merkitse(); // merkkaa ruudun lipulla
                    numMerkitty++;
                    haeRuutuPaikasta(loc).paljasta();
                }
                }
                else if (e.getButton() == MouseEvent.BUTTON3 && haeRuutuPaikasta(loc).onMerkitty()) // tarkastaa oliko hiirenpainallus suoritettu hiiren oikealla vai vasemmalla painikkeella
                {
                    haeRuutuPaikasta(loc).poistaMerkintä(); // poistaa ruudulta lipun
                    numMerkitty--;
                    haeRuutuPaikasta(loc).piilota();
                }
                else
                {
                haeRuutuPaikasta(loc).paljasta();  // paljastaa ruudun
                if (haeRuutuPaikasta(loc).onMiina()) // tarkastaa onko ruutu miina
                {
                    if (!haeRuutuPaikasta(loc).onMerkitty())
                    {
                    gameOver = true; // lopeta peli, näytä lopetusviesti ja paljasta kaikki ruudut
                                JOptionPane.showMessageDialog(paaIkkuna, "Hävisit pelin.", "Hävisit", JOptionPane.INFORMATION_MESSAGE);
                    for (Ruutu block:ruudut)
                    {
                        block.paljasta();
                        if (block.onMerkitty() && !block.onMiina())
                            paikkaX.add(block.haePaikka());
                    }
                }
                }
                if (haeRuutuPaikasta(loc).haeNumero() == 0) // paljastaa kaikki viereiset ruudut jos numero on nolla
                    paljastaKaikkiViereiset(loc);
                }
                paaIkkuna.repaint(); // repaints
                if (numMerkitty == miinoja && merkitytPaikatOvatMiinoja()) // tarkastaa onko miinoja ja merkittyjä ruutuja sama määrä, ja samalla katsoo myös ovatko merkityt ruudut miinoja
                {
                        JOptionPane.showMessageDialog(paaIkkuna, "Voitit pelin!", "Voitto", JOptionPane.INFORMATION_MESSAGE); // alert the user that they won
                }
            }
            }
            @Override
            public void mouseReleased(MouseEvent event){}
            @Override
            public void mouseClicked(MouseEvent event){}
            @Override
            public void mouseEntered(MouseEvent event){}
            @Override
            public void mouseExited(MouseEvent event){}
        }
        MouseListener listener = new mouseListener();
        paaIkkuna.addMouseListener(listener); // lisää mouseListener
       paaIkkuna.addComponentListener(new WindowListener());
       uusiPeli(); // aloittaa uuden pelin
    }
    
    private void uusiPeli()
    {
        boolean added = false;
        gameOver = false; // palauttaa kaikki muuttujat oikeisiin arvoihin
        numMerkitty = 0;
        mineLoc = teeSatunnaisetMiinat(miinoja); // generoi satunnaisia miinojen paikkoja
        ruudut.clear(); // tyhjentää ruudut
        paljastetutLoc.clear(); // Tyhjentää paljastetut paikat
        paikkaX.clear();
        for (int i = 0; i < ruudKor; i++)
            for (int j = 0; j < ruudLev; j++) 
            {
                added = false;
                for(Paikka loc:mineLoc) 
                    if (loc.equals(new Paikka(i, j)) && !added) // tarkastaa onko nykyinen lisättävä sijainti miina
                    {
                        ruudut.add(new Ruutu(new Paikka(i, j), "mine")); // lisää uuden miinan
                        added = true;
                    }
                
                if (!added) // suoritetaan vain jos miinaa ei ole vielä lisätty
                    ruudut.add(new Ruutu(new Paikka(i, j),haeViereisetMiinat(new Paikka(i, j))+"")); // tekee uuden ruudun jossa numerona koskettavien miinojen määrä
                    
            }
            
         for(Ruutu block:ruudut)
            block.piilota(); // piilota kaikki ruudut
        valmiinaMaalaamaan = true;
        PeliLauta.resetRuudukko(); // palauttaa ruudukon oikeaan kokoonsa
        paaIkkuna.repaint();
    }
    
    private static ArrayList<Paikka> teeSatunnaisetMiinat(int mines)
    {
        ArrayList<Paikka> locs = new ArrayList<>(); // tekee locs nimisen arraylistin
        for (int i = 0; i < mines; i++) // tehdään jokaiselle miinalle
        {
            boolean done = false;
            while (!done)
            {
                done = false;
            Paikka loc = Paikka.satunnainenPaikka(ruudLev - 1, ruudKor - 1); // tekee satunnaisen sijainnin
            boolean contains = false;
            for (Paikka loc1:locs) // tarkastaa onko sijainti jo olemassa
                if (loc1.equals(loc))
                    contains = true;
            if (!contains) // jos se ei ole listassa, lisää se
            {
                locs.add(loc);
                done = true;
            }
            }
        }
        return locs;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand(); // hakee menuItem:in jota klikattiin
        switch (command) {
            case "Uusi Peli":
                uusiPeli();
                break;
            case "Lopeta":
                System.exit(0);
                break;
            case "Helppo":
                miinoja = 15;
                uusiPeli();
                break;
            case "Keski":
                miinoja = 20;
                uusiPeli();
                break;
            case "Vaikea":
                miinoja = 25;
                uusiPeli();
                break;
            case "Kuinka Pelataan":
                JOptionPane.showMessageDialog(paaIkkuna, "Miinaharavan tarkoitus on paikantaa kaikki miinat (mustat ruudut) niin nopeasti kuin mahdollista kuitenkaan paljastamatta niitä.\nJos paljastat miinan häviät pelin. Ruutuja paljastetaan klikkaamalla niitä hiiren vasemmalla korvalla.\nJos ruutuun ilmestyy numero, se näyttää kuinka monta miinaa on sitä ympäröivissä kahdeksassa ruudussa.\nVoit merkitä ruutuja joissa epäilet olevan miinan lipulla (punaiset ruudut) klikkaamalla sitä hiiren oikealla korvalla.\nKun kaikki miinat on merkitty oikein, voitat pelin", "kuinka pelataan", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Tietoja Pelistä":
                JOptionPane.showMessageDialog(paaIkkuna, "Tehty Jamk:n Olio-ohjelmoinnin perusteet kurssin harjoitustyönä keväällä 2012.\nTekijä: Janne Kuukkanen", "tietoja pelistä", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    private static int haeViereisetMiinat(Paikka loc)
    {
        int mines = 0;
        ArrayList<Paikka> adjacentLocs = loc.haeViereisetPaikat(); // varastoi listan viereisistä sijainneista
        for (Paikka loc1:adjacentLocs)
            if (mineLoc.indexOf(loc1) != -1) // tarkistaa onko nykyinen sijainti miina
                mines++;
        return mines; // returns the number of mines
    }
    
    private static Paikka haeHiirenPaikka(int x, int y)
    {
        int x1 = (x/(int)(PeliLauta.ikkLev/ruudLev)); // jakaa x-koordinaatin neliön leveydellä
        int y1 = (y/(int)(PeliLauta.ikkKor/ruudKor)); // jakaa y-koordinaatin neliön korkeudella
        return new Paikka(x1, y1); // palauttaa sijainnin
    }
    
    private static Ruutu haeRuutuPaikasta(Paikka loc)
    {
        for (Ruutu block:ruudut)
            if (block.haePaikka().equals(loc)) // käy läpi listan ja tarkistaa onko ruudulla annettu sijainti
                return block;
        return null; // palauttaa null-arvon jos ruutua ei löydy
    }
    
    private static void paljastaKaikkiViereiset(Paikka loc)
    {
        if (!haeRuutuPaikasta(loc).onMerkitty())
        {
            paljastetutLoc.add(loc);
            if (haeRuutuPaikasta(loc).haeNumero() == 0)
            {
                haeRuutuPaikasta(loc).paljasta();
         for (Paikka loc1:loc.haeViereisetPaikat())
         {   
             haeRuutuPaikasta(loc1).paljasta();
             if (!paljastetutLoc.contains(loc1))  
                 paljastaKaikkiViereiset(loc1);
            }
            }
        }
    }
    
    private static ArrayList<Paikka> haeMerkitytPaikat()
    {
        ArrayList<Paikka> locs = new ArrayList<>();
        for (Ruutu block:ruudut) 
            if (block.onMerkitty()) // käy läpi jokaisen ruudun, tarkistaa onko se merkitty, ja lisää sen listaan
                locs.add(block.haePaikka());
        return locs;
    }

    private static boolean merkitytPaikatOvatMiinoja()
    {
        if (numMerkitty != miinoja)
            return false;
        
       for (Paikka loc:mineLoc)
       {
           if (!haeMerkitytPaikat().contains(loc))
                return false;
            }
            return true;
        }
}
