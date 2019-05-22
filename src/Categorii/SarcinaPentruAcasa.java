package Categorii;

public class SarcinaPentruAcasa extends Sarcina {
    public SarcinaPentruAcasa(String denumireSarcina) {
        this.denumireSarcina = denumireSarcina;
        this.listaCumparaturi = new ShoppingList();
    }

    public SarcinaPentruAcasa(String denumireSarcina, int idShoppingList) {
        this.denumireSarcina = denumireSarcina;
        this.listaCumparaturi = new ShoppingList(idShoppingList);
    }

    private ShoppingList listaCumparaturi;

    public ShoppingList getListaCumparaturi() {
        return listaCumparaturi;
    }

    public void setListaCumparaturi(ShoppingList listaCumparaturi) {
        this.listaCumparaturi = listaCumparaturi;
    }

    @Override
    public void afisare() {
        System.out.println("Denumire sarcina: " + denumireSarcina);
        tipSarcina = getTipSarcina();
        afisareTipSarcina(tipSarcina == TipSarcina.NEFACUTA, tipSarcina == TipSarcina.IN_PROGRES, "Dificultatea sarcinii: " + dificultateSarcina);

        listaCumparaturi.afisare();
    }

    @Override
    public TipSarcina getTipSarcina() {
        int nr = 0;
        for (Produs produs : listaCumparaturi.getProduse()) {
            if (produs.isBifat()) {
                nr++;
            }
        }
        if(nr == 0) {
            return TipSarcina.NEFACUTA;
        }
        if(nr == listaCumparaturi.getProduse().size()) {
            return TipSarcina.FACUTA;
        }
        return TipSarcina.IN_PROGRES;
    }
}
