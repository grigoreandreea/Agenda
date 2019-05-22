package Categorii;

public abstract class Sarcina extends Categorie {
    TipSarcina tipSarcina;
    DificultateSarcina dificultateSarcina;
    String denumireSarcina;

    public String getDenumireSarcina() {
        return denumireSarcina;
    }

    public void setDenumireSarcina(String denumireSarcina) {
        this.denumireSarcina = denumireSarcina;
    }


    public DificultateSarcina getDificultateSarcina() {
        return dificultateSarcina;
    }

    public void setDificultateSarcina(DificultateSarcina dificultateSarcina) {
        this.dificultateSarcina = dificultateSarcina;
    }

    public abstract TipSarcina getTipSarcina();

    public void setTipSarcina(TipSarcina tipSarcina) {
        this.tipSarcina = tipSarcina;
    }

    void afisareTipSarcina(boolean b, boolean b2, String s) {
        String tipSarcinaAfisat;
        if (b) {
            tipSarcinaAfisat = "Nefacuta";
        } else if (b2) {
            tipSarcinaAfisat = "In progres";
        } else {
            tipSarcinaAfisat = "Facuta";
        }

        System.out.println("Tipul sarcinii: " + tipSarcinaAfisat);
        System.out.println(s);
    }
}
