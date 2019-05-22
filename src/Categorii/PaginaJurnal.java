package Categorii;

import java.util.Date;

public class PaginaJurnal{

    private int idPagina;
    private Date data;
    private String descriere;

    public PaginaJurnal(Date data, String descriere) {
        this.idPagina = idPagina;
        this.data = data;
        this.descriere = "\n     Draga Jurnalule, \n\n     " + descriere + "\n\n       A/Al ta/tau, \n               " + Agenda.getInstance().posesor + "\n";
    }

    public PaginaJurnal(int idPagina, Date data, String descriere) {
        this.idPagina = idPagina;
        this.data = data;
        this.descriere = "\n     Draga Jurnalule, \n\n     " + descriere + "\n\n       A/Al ta/tau, \n               " + Agenda.getInstance().posesor + "\n";
    }

    public String getPosesor() {
        return Agenda.getInstance().posesor;
    }

    public void setPosesor(String posesor) {
        Agenda.getInstance().posesor = posesor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriereCuPattern(String descriere) {
        this.descriere = "\n     Draga Jurnalule, \n     " + descriere + "\n\n    , A/Al ta/tau, \n               " + Agenda.getInstance().posesor + "\n";
    }

    public void setDescriereFaraPattern(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public String toString() {
        return "PaginaJurnal\n" +
                "idPagina: " + idPagina +
                ",\ndata: " + data +
                ",\ndescriere:\n" + descriere;
    }

    public int getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(int idPagina) {
        this.idPagina = idPagina;
    }
}
