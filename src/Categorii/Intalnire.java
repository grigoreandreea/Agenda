package Categorii;
import java.util.Date;

public class Intalnire extends Categorie{

    protected Date data;
    protected String locatie;

    public Intalnire() {
    }

    public Intalnire(Date data, String locatie){
        this.data = data;
        this.locatie = locatie;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    @Override
    public void afisare() {
        System.out.println("Intalnire{" +
                "data=" + data +
                ", locatie='" + locatie + '\'' +
                '}');
    }
}
