package Categorii;

import java.util.ArrayList;
import java.util.List;

public class WishList extends Categorie{

    private List<String> saCalatoresc;
    private List<String> saCumpar;
    private List<String> saInvat;

    public WishList() {
        saCalatoresc = new ArrayList<>();
        saCumpar = new ArrayList<>();
        saInvat = new ArrayList<>();
    }

    public List<String> getSaCalatoresc() {
        return saCalatoresc;
    }

    public List<String> getSaCumpar() {
        return saCumpar;
    }

    public List<String> getSaInvat() {
        return saInvat;
    }

    @Override
    public void afisare() {
        for (String s2 : saCalatoresc) {
            System.out.print("Lista de locuri de calatorit: ");
            System.out.print(s2 + " ");
        }

        for (String s1 : saCumpar) {
            System.out.print("Lista de lucruri de cumparat: ");
            System.out.print(s1 + " ");
        }

        for (String s : saInvat) {
            System.out.print("Lista de lucruri de invatat: ");
            System.out.print(s + " ");
        }
    }
}
