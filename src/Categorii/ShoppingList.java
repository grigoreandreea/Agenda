package Categorii;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends Categorie {

    private int idShoppingList;

    public int getIdShoppingList() {
        return idShoppingList;
    }

    public void setIdShoppingList(int idShoppingList) {
        this.idShoppingList = idShoppingList;
    }

    private List<Produs> produse;

    public ShoppingList() {
        this.produse = new ArrayList<>();
    }

    public ShoppingList(int idShoppingList) {
        this.produse = new ArrayList<>();
        this.idShoppingList = idShoppingList;
    }

    public List<Produs> getProduse() {
        return produse;
    }

    public void setProdus(Produs produs) {
        this.produse.add(produs);
    }

    public boolean bifeazaProdusDupaId(int idProdus){
        for(Produs produs : produse) {
            if(produs.getIdProdus() == idProdus) {
                return produs.setBifat();
            }
        }

        return false;
    }

    public Produs getProdus(int idProdus){
        for(Produs produs : produse) {
            if(produs.getIdProdus() == idProdus) {
                return produs;
            }
        }
        return null;
    }

    @Override
    public void afisare() {
        System.out.println("Id shopping list: " + idShoppingList);
       for(Produs produs : produse)
        {
            System.out.println(produs);
        }
    }

}
