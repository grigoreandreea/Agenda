package Categorii;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SarcinaPentruMunca extends Sarcina{

    private List<Bug> bugs;

    public List<Bug> getBugs() {
        return bugs;
    }

    public void sortBugs(){
        Collections.sort(bugs);
    }

    public SarcinaPentruMunca(String denumireSarcina) {
        this.denumireSarcina = denumireSarcina;
        this.bugs = new ArrayList<>();
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    @Override
    public void afisare() {
        System.out.println("Denumire sarcina: " + denumireSarcina);
        afisareTipSarcina(tipSarcina == TipSarcina.NEFACUTA, tipSarcina == TipSarcina.IN_PROGRES, "Dificultatea sarcinii: " + dificultateSarcina);

        for(Bug bug : bugs){
            System.out.println(bug + ";");
        }
    }

    @Override
    public TipSarcina getTipSarcina() {
        return null;
    }
}
