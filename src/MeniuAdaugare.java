import Categorii.Agenda;
import Categorii.Produs;
import Categorii.ShoppingList;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class MeniuAdaugare extends JFrame {
    JFrame frame;

    MeniuAdaugare() {
        frame = new JFrame();
        BasicArrowButton backButton = new BasicArrowButton(BasicArrowButton.WEST);
        backButton.setBounds(0, 0, 36, 24);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuPrincipal meniuPrincipal= new MeniuPrincipal();
                frame.setVisible(false);
                frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
                frame.dispose();
            }
        });


        JLabel titluShoppingListLabel = new JLabel("Titlul listei de cumparaturi:");
        titluShoppingListLabel.setBounds(24, 24, 154, 40);
        JTextField titluShoppingListTextField = new JTextField();
        titluShoppingListTextField.setBounds(224, 24, 154, 40);


        JLabel numarProduseLabel = new JLabel("Numar de produse:");
        numarProduseLabel.setBounds(24, 88, 154, 40);
        JTextField numarProduseTextField = new JTextField();
        numarProduseTextField.setBounds(224, 88, 154, 40);

        JButton submitNumarProduse = new JButton("Set");
        submitNumarProduse.setBounds(402, 88, 64, 40);


        ArrayList<JLabel> jLabels = new ArrayList<>();
        ArrayList<JTextField> jTextFields = new ArrayList<>();

        JButton saveButton = new JButton("Save");
        JLabel successInfo = new JLabel("Succes!");


        submitNumarProduse.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                numarProduseTextField.setEnabled(false);

                int numarProduse = parseInt(numarProduseTextField.getText());

                for(int i=0; i<numarProduse; i++) {
                    JLabel jLabel = new JLabel("Numele produsului:");
                    JTextField jTextField = new JTextField();

                    jLabels.add(jLabel);
                    frame.add(jLabels.get(i));
                    jTextFields.add(jTextField);
                    frame.add(jTextFields.get(i));

                    jLabels.get(i).setBounds(24, 154 + i*(24 + 40), 154, 40);
                    jTextFields.get(i).setBounds(224, 154 + i*(24 + 40), 154, 40);
                }

                saveButton.setBounds(222, 154 + numarProduse*(24+40), 64, 40);

            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShoppingList listaCumparaturi = new ShoppingList();
                String titlu = titluShoppingListTextField.getText();
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
                catch(Exception ex) {
                    ex.printStackTrace();
                }

                int numarProduse = parseInt(numarProduseTextField.getText());
                int nr = 0;
                int[] id_produse = new int[numarProduse];
                for (int i = 0; i < numarProduse; i += 1) {
                    String numeProdus = jTextFields.get(i).getText();

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
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }
                    listaCumparaturi.getProduse().add(new Produs(numeProdus, id_produse[i]));
                }

                listaCumparaturi.setIdShoppingList(id_lista_cumparaturi);

                Agenda.getInstance().categorii.add(listaCumparaturi);

                Agenda.audit("adaugare_lista_cumparaturi");

                titluShoppingListTextField.setEnabled(false);
                submitNumarProduse.setEnabled(false);
                for(int i=0; i<numarProduse; i++) {
                    jTextFields.get(i).setEnabled(false);
                }
                saveButton.setEnabled(false);
                successInfo.setBounds(230, 154 + (numarProduse + 1)*(24+40), 64, 40);
            }
        });

        frame.add(backButton, BorderLayout.EAST);
        frame.add(titluShoppingListLabel);
        frame.add(titluShoppingListTextField);
        frame.add(numarProduseLabel);
        frame.add(numarProduseTextField);
        frame.add(submitNumarProduse);//adding button on frame
        frame.add(saveButton);
        frame.add(successInfo);
        frame.setSize(508, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}
