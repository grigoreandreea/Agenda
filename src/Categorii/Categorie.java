package Categorii;

public abstract class Categorie{

    protected String titlu;

    public Categorie() {
    }

    public Categorie(String titlu) {
        this.titlu = titlu;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public abstract void afisare();

}
