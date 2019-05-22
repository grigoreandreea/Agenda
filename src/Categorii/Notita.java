package Categorii;

public class Notita extends Categorie {
    public Notita(String notita) {
        this.descriere = notita;
    }

    public Notita(){}

    public int getIdNotita() {
        return idNotita;
    }

    public void setIdNotita(int idNotita) {
        this.idNotita = idNotita;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    private int idNotita;

    private String descriere;

    public String getNotita() {
        return descriere;
    }

    public void setNotita(String notita) {
        this.descriere = notita;
    }

    @Override
    public void afisare() {
        System.out.println("Notita: {idNotita=" + idNotita + ", descriere='" + descriere + "'}");
    }

}
