package Categorii;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Agenda {

    private static Agenda instance = null;

    public String s;
    public String posesor;

    public List<Categorie> categorii = new ArrayList<>();

    private Agenda() {}

    public static Agenda getInstance(){
        if (instance == null)
            instance = new Agenda();
        return instance;
    }

    public String getPosesor() {
        return posesor;
    }

    public void setPosesor(String posesor) {
        this.posesor = posesor;
    }

    public static void afiseazaCategorie(int index){
        instance.categorii.get(index).afisare();
    }

    public static void stergeCategorie(int index){
        instance.categorii.remove(index);
    }

    public static void afiseazaToateCategoriile(){
        for(Categorie categorie : instance.categorii){
            categorie.afisare();
            System.out.println();
        }
    }

    public static void stergeCategoriile(){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof Jurnal){
                ((Jurnal)instance.categorii.get(i)).stergerePagini();
            }
            else
            {
                instance.categorii.remove(instance.categorii.get(i));
                i--;
            }
        }
    }

    public static void stergeSarcinilePentruAcasa(){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof SarcinaPentruAcasa){
                instance.categorii.remove(instance.categorii.get(i));
                i--;
            }
        }
    }

    public static void stergeSarcinilePentruMunca(){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof SarcinaPentruMunca){
                instance.categorii.remove(instance.categorii.get(i));
                i--;
            }
        }
    }

    public static void stergeIntalnirile(){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof Intalnire){
                instance.categorii.remove(instance.categorii.get(i));
                i--;
            }
        }
    }

    public static void stergeIntalnirileProfesionale(){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof IntalnireProfesionala){
                instance.categorii.remove(instance.categorii.get(i));
                i--;
            }
        }
    }

    public static void stergeListaDeDorinte(){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof WishList){
                instance.categorii.remove(instance.categorii.get(i));
                i--;
            }
        }
    }

    public static void stergeListaDeCumparaturi(){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof ShoppingList){
                instance.categorii.remove(instance.categorii.get(i));
                i--;
            }
        }
    }

    public static void stergeListaDeCumparaturi(int idListaCumparaturi){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof ShoppingList
            && ((ShoppingList) instance.categorii.get(i)).getIdShoppingList() == idListaCumparaturi){
                instance.categorii.remove(instance.categorii.get(i));
                i--;
            }
        }
    }

    public static void stergeNotitele(){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof Notita){
                instance.categorii.remove(instance.categorii.get(i));
                i--;
            }
        }
    }

    public static void stergePaginileDinJurnal(){
        for(int i = 0; i< instance.categorii.size(); i++){
            if(instance.categorii.get(i) instanceof Jurnal){
                ((Jurnal)instance.categorii.get(i)).stergerePagini();
            }
        }
    }

    public static void bifeazaProdusDinShoppingList(int idShoppingList, int idProdus) {
        for(Categorie categorie : instance.categorii) {
            if(categorie instanceof ShoppingList && ((ShoppingList)categorie).getIdShoppingList() == idShoppingList) {
                ((ShoppingList)categorie).bifeazaProdusDupaId(idProdus);
            } else if(categorie instanceof SarcinaPentruAcasa && ((SarcinaPentruAcasa)categorie).getListaCumparaturi().getIdShoppingList() == idShoppingList){
                ((SarcinaPentruAcasa)categorie).getListaCumparaturi().bifeazaProdusDupaId(idProdus);
            }
        }
    }

    public static void modificaNotita(int idNotita, String titlu, String descriere) {
        for(Categorie categorie : instance.categorii) {
            if (categorie instanceof Notita && ((Notita) categorie).getIdNotita() == idNotita) {
                ((Notita) categorie).setTitlu(titlu);
                ((Notita) categorie).setDescriere(descriere);
            }
        }
    }

    public static void audit(String tipActiune){

        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("audit.csv", true), "UTF-8"));
            StringBuilder str = new StringBuilder()
                    .append("nume_actiune: ")
                    .append(tipActiune)
                    .append("; timestamp: ")
                    .append(new Date().toString())
                    .append("; thread_name: ")
                    .append(Thread.currentThread().getName());
            bw.write(str.toString());
            bw.newLine();
            bw.flush();
            bw.close();

            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
            PreparedStatement myStm = myConn.prepareStatement("insert into audit(nume_actiune, timestamp, thread_name) values (?, ?, ?)");
            myStm.setString(1, tipActiune);

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);

            myStm.setString(2, currentTime);
            myStm.setString(3, Thread.currentThread().getName());
            myStm.execute();

        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ShoppingList> getListeCumparaturi() {
        ArrayList<ShoppingList> listeCumparaturi = new ArrayList<>();
        for(int i=0; i<instance.categorii.size(); i++) {
            if(instance.categorii.get(i) instanceof ShoppingList) {
                listeCumparaturi.add((ShoppingList)instance.categorii.get(i));
            }
        }
        return listeCumparaturi;
    }
}
