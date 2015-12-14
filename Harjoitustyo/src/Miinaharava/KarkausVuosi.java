/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;

/**
 *
 * @author Omistaja
 * (Tehtävä 4)
 */
public class KarkausVuosi {
    
    public static void main(String args[]) {
        //Ohjelma kysyy käyttäjältä Vuoden
        int vuosi = 0;
        Scanner nappis = new Scanner(System.in);
        System.out.print("Syota vuosi: ");
        vuosi = nappis.nextInt();
        System.out.println("Annoit vuoden: "+vuosi);
        
        //Ohjelma tarkistaa onko vuosi karkausvuosi ja tulostaa vastauksen
        if (vuosi % 4 == 0) 
        {
            if (vuosi % 100 == 0) 
            {
                if (vuosi % 400 == 0) 
                {
                System.out.println("Vuosi on karkausvuosi.");
                } 
                else 
                {
                System.out.println("Vuosi ei ole karkausvuosi.");
                }
            } 
            else 
            {
            System.out.println("Vuosi on karkausvuosi.");
            }
        } 
        else {
             System.out.println("Vuosi ei ole karkausvuosi.");
             }
    }
}
