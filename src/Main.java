import Categorii.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class Main {

    private static void afisareMeniuPrincipal(){
        System.out.println("Alege, te rog, o optiune:");
        System.out.println("1. Adauga categorii in agenda;");
        System.out.println("2. Modifica categorii in agenda;");
        System.out.println("3. Sterge categorii din agenda;");
        System.out.println("4. Afisare agenda;");
        System.out.println("0. Parasiti meniul;");

        System.out.print("Optiune: ");
    }

    private static void afiseazaMeniuAdaugare() {
        System.out.println("Alege, te rog, o categorie in care vrei sa scrii:");

        afisareMeniuGeneral();
        System.out.println("8. Bifeaza produs dintr-o lista");
        System.out.println("0. Parasiti meniul;");
        System.out.print("Categoria: ");

    }

    private static void afiseazaMeniuModificare() {
        System.out.println("Alege, te rog, o categorie pe care vrei sa o modifici:");
        System.out.println("1. Modifica o intalnire");
        System.out.println("2. Modifica o lista de cumparaturi");
        System.out.println("3. Modifica o notita");
        System.out.println("4. Modifica o pagina in jurnal");
        System.out.println("0. Parasiti meniul;");
        System.out.print("Categoria: ");
    }

    private static void afiseazaMeniuStergere(){
        System.out.println("Alege, te rog, o categorie din care vrei sa stergi:");

        afisareMeniuGeneral();
        System.out.println("8. Stergerea totala a agendei.");
        System.out.println("0. Parasiti meniul;");
        System.out.print("Categoria: ");

    }

    private static void afisareMeniuGeneral() {
        System.out.println("1. Sarcina;");
        System.out.println("2. Intalnire;");
        System.out.println("3. Intalnire profesionala;");
        System.out.println("4. Lista de dorinte;");
        System.out.println("5. Lista de cumpaturi;");
        System.out.println("6. Notita;");
        System.out.println("7. Pagina in jurnal;");
    }

    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int idShoppingList = 1;
        int idProdus = 1;
        Jurnal jurnal = new Jurnal();
        Agenda.getInstance().categorii.add(jurnal);

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");

            Statement myStm = myConn.createStatement();
            Statement myStmProduse = myConn.createStatement();

            ResultSet notiteRS = myStm.executeQuery("select * from notite");
            while (notiteRS.next()) {
                Notita notita = new Notita();
                String titluNotita = notiteRS.getString("titlu");
                notita.setIdNotita(parseInt(notiteRS.getString("id")));
                notita.setTitlu(titluNotita);
                notita.setNotita(notiteRS.getString("descriere"));
                Agenda.getInstance().categorii.add(notita);
            }

            ResultSet intalniriRS = myStm.executeQuery("select * from intalniri");
            while (intalniriRS.next()) {
                String titlu = intalniriRS.getString("titlu");
                String dataString = intalniriRS.getString("data");
                Date data = new Date();
                try {
                    data = formatter.parse(dataString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String locatie =  intalniriRS.getString("locatie");
                Intalnire intalnire = new Intalnire(data, locatie);
                intalnire.setTitlu(titlu);
                Agenda.getInstance().categorii.add(intalnire);
            }

            ResultSet liste_cumparaturiRS = myStm.executeQuery("select * from liste_cumparaturi");
            while (liste_cumparaturiRS.next()) {
                String titlu = liste_cumparaturiRS.getString("titlu");
                ShoppingList listaCumparaturi = new ShoppingList();
                listaCumparaturi.setTitlu(titlu);
                listaCumparaturi.setIdShoppingList(parseInt(liste_cumparaturiRS.getString("id")));
                ResultSet produseRS = myStmProduse.executeQuery("select * from produse where id_lista_cumparaturi = " + liste_cumparaturiRS.getString("id"));
                while (produseRS.next()){
                    String numeProdus = produseRS.getString("nume");
                    boolean isBifat = produseRS.getString("bifat").equals("1");
                    listaCumparaturi.getProduse().add(new Produs(numeProdus, parseInt(produseRS.getString("id")), isBifat));
                }
                produseRS.close();
                Agenda.getInstance().categorii.add(listaCumparaturi);
            }

        } catch(Exception e) {
	    System.out.println("Hello from here");
            e.printStackTrace();
        }

        MeniuPrincipal meniuPrincipal = new MeniuPrincipal();

        System.out.print("Introdu numele posesorului agendei: ");
        Scanner sca = new Scanner(System.in);
        Agenda.getInstance().posesor = sca.nextLine();
        Agenda.getInstance().s = "Buna ziua, " + Agenda.getInstance().posesor + "! ";
        System.out.println(Agenda.getInstance().s);

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");

            Statement myStm = myConn.createStatement();
            Statement myStmProduse = myConn.createStatement();

            ResultSet pagini_jurnalRS = myStm.executeQuery("select * from pagini_jurnal");
            while (pagini_jurnalRS.next()) {
                String dataString = pagini_jurnalRS.getString("data");
                Date data = new Date();
                try {
                    data = formatter.parse(dataString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int idPagina = parseInt(pagini_jurnalRS.getString("id"));
                String text = pagini_jurnalRS.getString("text");
                ((Jurnal)Agenda.getInstance().categorii.get(0)).adaugaPagina(new PaginaJurnal(idPagina, data,text));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        int optiune = 1;
        while(optiune != 0){
            afisareMeniuPrincipal();
            Scanner in = new Scanner(System.in);
            optiune = in.nextInt();
            switch (optiune){
                case 1:{
                    afiseazaMeniuAdaugare();
                    optiune = in.nextInt();
                    switch (optiune){

                        case 1: {
                            System.out.println("Introdu tipul sarcinii:\nPentru acasa = 1\nPentru la munca = 2");
                            System.out.print("Sarcina ");
                            int tipSarcina = in.nextInt();
                            if (tipSarcina == 1) {
                                System.out.print("Introdu numele listei de sarcini pentru acasa: ");
                                String denumireLista = in.next();
                                SarcinaPentruAcasa sarcinaPentruAcasa = new SarcinaPentruAcasa(denumireLista, idShoppingList++);
                                System.out.print("Introdu dificultatea sarcinii:\nUSOR = 1\nMEDIU = 2\nDIFICIL = 3\nDificultatea: ");
                                int dificultateSarcina = in.nextInt();
                                while (dificultateSarcina < 1 || dificultateSarcina > 3) {
                                    System.out.println("Dificultatea introdusa nu este valida!");
                                    System.out.print("Introdu dificultatea sarcinii:\nUSOR = 1\nMEDIU = 2\nDIFICIL = 3\nDificultatea: ");
                                    dificultateSarcina = in.nextInt();
                                }
                                sarcinaPentruAcasa.setDificultateSarcina(
                                        dificultateSarcina == 3
                                                ? DificultateSarcina.DIFICIL
                                                : dificultateSarcina == 2
                                                ? DificultateSarcina.MEDIU
                                                : DificultateSarcina.USOR
                                );
                                System.out.print("Introdu numarul de produse pe care vrei sa le introduci in lista de cumparaturi: ");
                                int numarProduse = in.nextInt();
                                for (int i = 0; i < numarProduse; i += 1) {
                                    System.out.print("Introdu numele produsului: ");
                                    String numeProdus = in.next();
                                    sarcinaPentruAcasa.getListaCumparaturi().setProdus(new Produs(numeProdus, idProdus++));
                                }
                                Agenda.getInstance().categorii.add(sarcinaPentruAcasa);

                            } else if (tipSarcina == 2) {
                                System.out.print("Introdu numele listei de sarcini pentru la munca: ");
                                String denumireLista = in.next();
                                SarcinaPentruMunca sarcinaPentruMunca = new SarcinaPentruMunca(denumireLista);
                                System.out.print("Introdu dificultatea sarcinii:\nUSOR = 1\nMEDIU = 2\nDIFICIL = 3\nDificultatea: ");
                                int dificultateSarcina = in.nextInt();
                                while (dificultateSarcina < 1 || dificultateSarcina > 3) {
                                    System.out.println("Dificultatea introdusa nu este valida!");
                                    System.out.print("Introdu dificultatea sarcinii:\nUSOR = 1\nMEDIU = 2\nDIFICIL = 3\nDificultatea: ");
                                    dificultateSarcina = in.nextInt();
                                }
                                sarcinaPentruMunca.setDificultateSarcina(
                                        dificultateSarcina == 3
                                                ? DificultateSarcina.DIFICIL
                                                : dificultateSarcina == 2
                                                ? DificultateSarcina.MEDIU
                                                : DificultateSarcina.USOR
                                );
                                System.out.print("Introdu numarul de buguri pe care vrei sa le introduci in lista de buguri: ");
                                int numarBuguri = in.nextInt();
                                for (int i = 0; i < numarBuguri; i += 1) {
                                    System.out.print("Introdu numele bugului: ");
                                    String numeBug = in.next();
                                    System.out.print("Introdu gradul de dificultate al bugului: ");
                                    int gradDificultate = in.nextInt();
                                    Bug bug = new Bug(numeBug, gradDificultate);
                                    sarcinaPentruMunca.getBugs().add(bug);
                                }
                                Agenda.getInstance().categorii.add(sarcinaPentruMunca);
                            }
                            Agenda.audit("adaugare_sarcina_pentru_acasa");
                            break;
                        }

                        case 2:{
                            System.out.print("Introdu titlul intalnirii: ");
                            String titlu = in.next();
                            System.out.print("Introdu data la care are loc intalnirea sub formatul yyyy-mm-dd: ");
                            String dataString = in.next();
                            String parsedData = "";
                            Date data = new Date();
                            try {
                                data = formatter.parse(dataString);
                                parsedData = formatter.format(data);
                            } catch (ParseException e) {
                                parsedData = formatter.format(new Date());
                                e.printStackTrace();
                            }
                            System.out.print("Introdu locatia unde are loc intalnirea: ");
                            String locatie = in.next();
                            Intalnire intalnire = new Intalnire(data, locatie);
                            intalnire.setTitlu(titlu);
                            Agenda.getInstance().categorii.add(intalnire);

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("insert into intalniri(titlu, data, locatie) values (?, ?, ?)");
                                myStm.setString(1, titlu);
                                myStm.setString(2, parsedData);
                                myStm.setString(3, locatie);
                                myStm.execute();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            Agenda.audit("adaugare_intalnire");
                            break;
                        }

                        case 3:{
                            System.out.print("Introdu titlul intalnirii profesionale: ");
                            String titlu = in.next();
                            IntalnireProfesionala intalnireProfesionala = new IntalnireProfesionala();
                            intalnireProfesionala.setTitlu(titlu);
                            System.out.print("Introdu data la care are loc intalnirea profesionala sub formatul dd/mm/yyyy: ");
                            String dataString = in.next();
                            Date data = new Date();
                            try {
                                data = formatter.parse(dataString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            intalnireProfesionala.setData(data);
                            System.out.print("Introdu locatia unde are loc intalnirea profesionala: ");
                            String locatie = in.next();
                            intalnireProfesionala.setLocatie(locatie);
                            System.out.print("Introdu persoana cu care are loc intalnirea profesionala: ");
                            Scanner sc = new Scanner(System.in);
                            String persoana = sc.nextLine();
                            intalnireProfesionala.setPersoana(persoana);
                            System.out.print("Introdu scopul pentru care are loc intalnirea profesionala:\n1.Demisie;\n2.Informativa;\n3.Organizare;\n4.Promovare;\n5.Recrutare;\nScop: ");
                            int scop = in.nextInt();

                            switch (scop){
                                case 1:{ intalnireProfesionala.setScop(Scop.DEMISIE); break;}
                                case 2:{ intalnireProfesionala.setScop(Scop.INFORMATIVA); break;}
                                case 3:{ intalnireProfesionala.setScop(Scop.ORGANIZARE); break;}
                                case 4:{ intalnireProfesionala.setScop(Scop.PROMOVARE); break;}
                                case 5:{ intalnireProfesionala.setScop(Scop.RECRUTARE); break;}
                                default:{break;}
                            }
                            Agenda.getInstance().categorii.add(intalnireProfesionala);
                            Agenda.audit("adaugare_intalnire_profesionala");
                            break;

                        }

                        case 4:{
                            WishList listaDorinte = new WishList();
                            System.out.println("Introdu ce fel de dorinta ai dori:\nCalatorie = 1\nCumparaturi: 2\nSa invat ceva: 3\nTip de dorinta: ");
                            int tipDorinta = in.nextInt();
                            if(tipDorinta == 1){
                                System.out.print("Introdu unde ai vrea sa calatoresti: ");
                                System.out.print("Introdu numarul de calatorii: ");
                                int nrCalatorii = in.nextInt();
                                for(int i = 0; i< nrCalatorii; i++){
                                    System.out.print("Introdu numele calatoriei: ");
                                    String numeCalatorie = in.next();
                                    listaDorinte.getSaCalatoresc().add(numeCalatorie);
                                }

                            }

                            if(tipDorinta == 2){
                                System.out.print("Introdu numarul de obiecte pe care ai vrea sa le cumperi: ");
                                int nrObiecte = in.nextInt();
                                for(int i = 0; i< nrObiecte; i++){
                                    System.out.print("Introdu numele obiectului: ");
                                    String numeObiect = in.next();
                                    listaDorinte.getSaCumpar().add(numeObiect);
                                }
                            }

                            if(tipDorinta == 3){
                                System.out.print("Introdu numarul de lucruri pe care ai vrea sa le inveti: ");
                                int nrLucruri = in.nextInt();
                                for(int i = 0; i< nrLucruri; i++){
                                    System.out.print("Introdu numele lucrului: ");
                                    String numeLucru = in.next();
                                    listaDorinte.getSaInvat().add(numeLucru);
                                }
                            }
                            Agenda.getInstance().categorii.add(listaDorinte);
                            Agenda.audit("adaugare_lista_dorinte");
                            break;

                        }

                        case 5:{
                            ShoppingList listaCumparaturi = new ShoppingList();
                            System.out.print("Introdu titlul listei de cumparaturi: ");
                            String titlu = in.next();
                            listaCumparaturi.setTitlu(titlu);
                            int id_lista_cumparaturi = 0;
                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("insert into liste_cumparaturi(titlu) values (?)", Statement.RETURN_GENERATED_KEYS);
                                myStm.setString(1, titlu);
                                myStm.execute();
                                ResultSet rs = myStm.getGeneratedKeys();
                                if(rs.next()){
                                    id_lista_cumparaturi = rs.getInt(1);
                                }
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            System.out.print("Introdu numarul de produse pe care vrei sa le introduci in lista de cumparaturi: ");
                            int numarProduse = in.nextInt();
                            int nr = 0;
                            int[] id_produse = new int[numarProduse];
                            for (int i = 0; i < numarProduse; i += 1) {
                                System.out.print("Introdu numele produsului: ");
                                String numeProdus = in.next();

                                try {
                                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                    PreparedStatement myStmProduse = myConn.prepareStatement(
                                            "insert into produse(nume, id_lista_cumparaturi) values (?, ?)",
                                            Statement.RETURN_GENERATED_KEYS
                                    );
                                    myStmProduse.setString(1, numeProdus);
                                    myStmProduse.setInt(2, id_lista_cumparaturi);
                                    myStmProduse.execute();
                                    ResultSet rsProduse = myStmProduse.getGeneratedKeys();
                                    if(rsProduse.next()){
                                        id_produse[nr++] = rsProduse.getInt(1);
                                    }
                                }
                                catch(Exception e) {
                                    e.printStackTrace();
                                }
                                listaCumparaturi.getProduse().add(new Produs(numeProdus, id_produse[i]));
                            }

                            listaCumparaturi.setIdShoppingList(id_lista_cumparaturi);

                            Agenda.getInstance().categorii.add(listaCumparaturi);

                            Agenda.audit("adaugare_lista_cumparaturi");
                            break;
                        }

                        case 6:{
                            System.out.print("Introdu titlul notitei: ");
                            Notita notita = new Notita();
                            String titluNotita = in.next();
                            notita.setTitlu(titluNotita);
                            System.out.println("Introdu notita: ");
                            Scanner sc = new Scanner(System.in);
                            notita.setNotita(sc.nextLine());
                            int idNotita = 0;

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("insert into notite(titlu, descriere) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                                myStm.setString(1, titluNotita);
                                myStm.setString(2, notita.getNotita());
                                myStm.execute();
                                ResultSet rs = myStm.getGeneratedKeys();
                                if(rs.next()) {
                                    idNotita = rs.getInt(1);
                                }
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                            notita.setIdNotita(idNotita);
                            Agenda.getInstance().categorii.add(notita);

                            Agenda.audit("adaugare_notita");
                            break;
                        }

                        case 7:{
                            System.out.print("Introdu data la care scrii in jurnal sub formatul yyyy-mm-dd: ");
                            String dataString = in.next();
                            String parsedData = "";
                            Date data = new Date();
                            try {
                                data = formatter.parse(dataString);
                                parsedData = formatter.format(data);
                            } catch (ParseException e) {
                                parsedData = formatter.format(new Date());
                                e.printStackTrace();
                            }
                            System.out.println("Introdu textul pentru jurnal:");
                            Scanner sc = new Scanner(System.in);
                            String text = sc.nextLine();
                            PaginaJurnal paginaJurnal = new PaginaJurnal(data,text);

                            int idPagina = 0;
                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("insert into pagini_jurnal(data, text) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                                myStm.setString(1, parsedData);
                                myStm.setString(2, paginaJurnal.getDescriere());
                                myStm.execute();
                                ResultSet rs = myStm.getGeneratedKeys();
                                if(rs.next()) {
                                    idPagina = rs.getInt(1);
                                }
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            paginaJurnal.setIdPagina(idPagina);
                            ((Jurnal)Agenda.getInstance().categorii.get(0)).adaugaPagina(paginaJurnal);
                            Agenda.audit("adaugare_pagina_in_jurnal");
                            break;
                        }

                        case 8: {
                            Agenda.afiseazaToateCategoriile();
                            System.out.println("Introdu tipul sarcinii:\nPentru acasa = 1\nShopping List = 2 ");
                            int tipSarcina = in.nextInt();
                            if(tipSarcina == 1) {
                                System.out.println("Introdu id-ul listei de cumparaturi din sarcina pentru acasa");
                                int idListCumapraturi  = in.nextInt();
                                System.out.println("Introdu id-ul produsului de bifat");
                                int idProd = in.nextInt();
                                Agenda.bifeazaProdusDinShoppingList(idListCumapraturi, idProd);
                            } else if(tipSarcina == 2) {
                                System.out.println("Introdu id-ul listei de cumparaturi");
                                int idListCumapraturi = in.nextInt();
                                System.out.println("Introdu id-ul produsului de bifat");
                                int idProd = in.nextInt();
                                Agenda.bifeazaProdusDinShoppingList(idListCumapraturi, idProd);
                            }
                            Agenda.audit("afisare_toate_categoriile");
                            break;
                        }

                        default: {
                            System.out.println("Ai parasit meniul de adaugare!");
                            Agenda.audit("parasire_meniu_adaugare");
                            break;
                        }
                    }
                    break;
                }

                case 2:{
                    afiseazaMeniuModificare();
                    optiune = in.nextInt();
                    System.out.println("Afisam toate categoriile adaugate in agenda:");
                    Agenda.afiseazaToateCategoriile();
                    switch (optiune){
                        case 1:{
                            System.out.print("Introdu id-ul intalnirii: ");
                            String id_intalnire = in.next();
                            System.out.print("Introdu noul titlu al intalnirii: ");
                            String titlu = in.next();
                            System.out.print("Introdu noua data la care are loc intalnirea sub formatul yyyy-mm-dd: ");
                            String dataString = in.next();
                            String parsedData = "";
                            Date data = new Date();
                            try {
                                data = formatter.parse(dataString);
                                parsedData = formatter.format(data);
                            } catch (ParseException e) {
                                parsedData = formatter.format(new Date());
                                e.printStackTrace();
                            }
                            System.out.print("Introdu noua locatie unde are loc intalnirea: ");
                            String locatie = in.next();
                            Intalnire intalnire = new Intalnire(data, locatie);
                            intalnire.setTitlu(titlu);

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("update intalniri set titlu = ?, data = ?, locatie = ? where id = ?");
                                myStm.setString(1, titlu);
                                myStm.setString(2, parsedData);
                                myStm.setString(3, locatie);
                                myStm.setInt(4, parseInt(id_intalnire));
                                myStm.execute();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            Agenda.audit("adaugare_intalnire");
                            break;
                        }

                        case 2:{

                            System.out.println("Introdu id-ul listei de cumparaturi");
                            int idListCumapraturi  = in.nextInt();

                            System.out.println("Introdu id-ul produsului de bifat");
                            int idProd = in.nextInt();

                            for(Categorie categorie : Agenda.getInstance().categorii) {
                                if(categorie instanceof ShoppingList && ((ShoppingList)categorie).getIdShoppingList() == idListCumapraturi) {
                                    try {
                                        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                        PreparedStatement myStm = myConn.prepareStatement("update produse set bifat = ? where id = ?");
                                        boolean x = ((ShoppingList) categorie).bifeazaProdusDupaId(idProd);
                                        myStm.setBoolean(1, ((ShoppingList) categorie).getProdus(idProd).isBifat());
                                        myStm.setInt(2, idProd);
                                        myStm.execute();
                                    }
                                    catch(Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            break;
                        }

                        case 3:{
                            System.out.print("Introdu id-ul notitei: ");
                            String id_notita = in.next();
                            System.out.print("Introdu titlul notitei: ");
                            String titluNotita = in.next();
                            System.out.println("Introdu notita: ");
                            Scanner sc = new Scanner(System.in);
                            String descriere = sc.nextLine();
                            Agenda.modificaNotita(parseInt(id_notita), titluNotita, descriere);

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("update notite set titlu = ?, descriere = ? where id = ?");
                                myStm.setString(1, titluNotita);
                                myStm.setString(2, descriere);
                                myStm.setInt(3, parseInt(id_notita));
                                myStm.execute();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            break;
                        }

                        case 4:{
                            System.out.print("Introdu id-ul paginii de jurnal: ");
                            String id_pagina_jurnal = in.next();
                            System.out.print("Introdu data la care scrii in jurnal sub formatul yyyy-mm-dd: ");
                            String dataString = in.next();
                            String parsedData = "";
                            Date data = new Date();
                            try {
                                data = formatter.parse(dataString);
                                parsedData = formatter.format(data);
                            } catch (ParseException e) {
                                parsedData = formatter.format(new Date());
                                e.printStackTrace();
                            }
                            System.out.println("Introdu textul pentru jurnal:");
                            Scanner sc = new Scanner(System.in);
                            String text = sc.nextLine();
                            PaginaJurnal paginaJurnal = new PaginaJurnal(data,text);
                            ((Jurnal)Agenda.getInstance().categorii.get(0)).updatePagina(parseInt(id_pagina_jurnal), data, text);

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("update pagini_jurnal set data = ?, text = ? where id = ?");
                                myStm.setString(1, parsedData);
                                myStm.setString(2, paginaJurnal.getDescriere());
                                myStm.setInt(3, parseInt(id_pagina_jurnal));
                                myStm.execute();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }


                            break;
                        }

                        default:{
                            break;
                        }
                    }
                    break;
                }

                case 3:{
                    afiseazaMeniuStergere();
                    optiune = in.nextInt();
                    switch (optiune){

                        case 1: {
                            System.out.println("Introdu tipul sarcinii pe care doresti sa o stergi:\nPentru acasa = 1\nPentru la munca = 2");
                            int tipSarcina = in.nextInt();
                            if(tipSarcina == 1){
                                Agenda.stergeSarcinilePentruAcasa();
                                System.out.println("Sarcinile pentru acasa au fost sterse cu succes!");
                            }
                            if(tipSarcina == 2){
                                Agenda.stergeSarcinilePentruMunca();
                                System.out.println("Sarcinile pentru la munca au fost sterse cu succes!");
                            }
                            Agenda.audit("stergere_sarcina");
                            break;
                        }

                        case 2: {
                            Agenda.stergeIntalnirile();
                            System.out.println("Intalnirile au fost sterse cu succes!");

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("delete from intalniri");
                                myStm.execute();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            Agenda.audit("stergere_intalniri");
                            break;
                        }

                        case 3: {
                            Agenda.stergeIntalnirileProfesionale();
                            System.out.println("Intalnirile profesionale au fost sterse cu succes!");
                            Agenda.audit("stergere_intalniri_profesionale");
                            break;
                        }

                        case 4: {
                            Agenda.stergeListaDeDorinte();
                            System.out.println("Listele de dorinte au fost sterse cu succes!");
                            Agenda.audit("stergere_liste_dorinte");
                            break;
                        }

                        case 5: {
                            Agenda.stergeListaDeCumparaturi();
                            System.out.println("Listele de cumparaturi au fost sterse cu succes!");
                            Agenda.audit("stergere_liste_cumparaturi");

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("delete from liste_cumparaturi");
                                myStm.execute();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            break;
                        }

                        case 6: {
                            Agenda.stergeNotitele();
                            System.out.println("Notitele au fost sterse cu succes!");

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("delete from notite");
                                myStm.execute();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            Agenda.audit("stergere_notite");
                            break;
                        }

                        case 7: {
                            Agenda.stergePaginileDinJurnal();
                            System.out.println("Paginile din jurnal au fost sterse cu succes!");

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("delete from pagini_jurnal");
                                myStm.execute();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            Agenda.audit("stergere_pagini_jurnal");
                            break;
                        }

                        case 8: {
                            Agenda.stergeCategoriile();
                            System.out.println("Toate categoriile din agenda au fost sterse cu succes!");

                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                                PreparedStatement myStm = myConn.prepareStatement("delete from intalniri");
                                myStm.execute();
                                myStm = myConn.prepareStatement("delete from notite");
                                myStm.execute();
                                myStm = myConn.prepareStatement("delete from pagini_jurnal");
                                myStm.execute();
                                myStm = myConn.prepareStatement("delete from liste_cumparaturi");
                                myStm.execute();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }

                            Agenda.audit("stergere_totala_categorii");
                            break;
                        }

                        default:{
                            System.out.println("Ati parasit meniul de stergere!");
                            Agenda.audit("parasire_meniu_stergere");
                            break;}
                    }
                    break;
                }

                case 4:{
                    System.out.println();
                    System.out.println("Afisam toate categoriile adaugate in agenda:");
                    Agenda.afiseazaToateCategoriile();
                    Agenda.audit("afisare_categorii");
                    break;
                }

                default:{
                    System.out.println("Ai parasit meniul!\nO zi frumoasa!");
                    break;
                }
            }
        }
    }

}
