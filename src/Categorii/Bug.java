package Categorii;

public class Bug implements Comparable<Bug> {
    private String descriere;
    private int gradDificultate;

    private int gasesteGradDificultate(int nrAproximat, int a,  int b){
        if(a + b > nrAproximat){
            System.out.println("Gradul de dificultate al unui bug este calculat ca fiind un numar din sirul lui Fibonacci." +
                    " Astfel, dificultatea bugului: '"+ descriere + "' a fost estimata ca fiind " + (a+b) );
            return a+b;
        } else
            if (a + b == nrAproximat) {
                return a + b;
        }
        return gasesteGradDificultate(nrAproximat,b,a+b);
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getGradDificultate() {
        return gradDificultate;
    }

    public void setGradDificultate(int gradDificultate) {
        this.gradDificultate = this.gasesteGradDificultate(gradDificultate, 1, 1);
    }

    public Bug(String descriere, int gradDificultate) {
        this.descriere = descriere;
        this.gradDificultate = gasesteGradDificultate(gradDificultate,1,1);
    }

    @Override
    public String toString() {
        return "Bug{" +
                "descriere='" + descriere + '\'' +
                ", gradDificultate=" + gradDificultate +
                '}';
    }

    @Override
    public int compareTo(Bug o) {
        if(this.gradDificultate > o.gradDificultate){
            return 1;
        }
        else
            if(this.gradDificultate == o.gradDificultate){
                return 0;
            }
        return -1;
    }
}
