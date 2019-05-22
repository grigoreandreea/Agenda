package Categorii;

public class IntalnireProfesionala extends Intalnire {

    private String persoana;
    private Scop scop;

    public Scop getScop() {
        return scop;
    }

    public void setScop(Scop scop) {
        this.scop = scop;
    }

    public String getPersoana() {
        return persoana;
    }

    public void setPersoana(String persoana) {
        this.persoana = persoana;
    }

    @Override
    public String toString() {
        return "IntalnireProfesionala{" +
                "persoana='" + persoana + '\'' +
                ", scop=" + scop +
                '}';
    }

    @Override
    public void afisare() {
        System.out.println("Intalnire profesionala{" +
                "data=" + data +
                ", locatie='" + locatie + '\'' +
                "persoana='" + persoana + '\'' +
                ", scop=" + scop +
                '}');
    }

}
