package Categorii;

public class Produs{

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    private int idProdus;
    private String nume;
    private boolean bifat;

    public Produs(String nume, int idProdus) {
        this.nume = nume;
        this.bifat = false;
        this.idProdus = idProdus;
    }

    public Produs(String nume, int idProdus, boolean bifat) {
        this.nume = nume;
        this.bifat = bifat;
        this.idProdus = idProdus;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public boolean isBifat() {
        return bifat;
    }

    public boolean setBifat() {
        this.bifat = !this.bifat;
        return this.bifat;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "idProdus=" + idProdus +
                ", nume='" + nume + '\'' +
                ", bifat=" + bifat +
                '}';
    }
}
