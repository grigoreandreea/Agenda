package Categorii;

import java.util.*;

public class Jurnal extends Categorie{

    private int nrPagini;
    private Map<Integer, PaginaJurnal> pagini;

    public Jurnal() {
        this.pagini = new HashMap<>();
        this.nrPagini = 0;
    }

    public int getNrPagini() {
        return nrPagini;
    }

    public Map<Integer, PaginaJurnal> getPagini() {
        return pagini;
    }

    public PaginaJurnal getPagina(int nrPagina){
        return pagini.get(nrPagina);
    }

    public void adaugaPagina(PaginaJurnal pagina) {
        pagina.setPosesor(Agenda.getInstance().posesor);
        this.pagini.put(pagina.getIdPagina(), pagina);
    }

    public void updatePagina(int idPaginaJurnal, Date data, String text) {
        this.pagini.get(idPaginaJurnal).setData(data);
        this.pagini.get(idPaginaJurnal).setDescriereCuPattern(text);
    }

    @Override
    public void afisare() {
        System.out.println(pagini);
    }

    public void stergerePagini(){
        this.pagini.clear();
        this.nrPagini=0;
    }

}
