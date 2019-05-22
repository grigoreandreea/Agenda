import Categorii.Agenda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MeniuPrincipal extends JFrame {
    JFrame frame;

    MeniuPrincipal() {
        frame = new JFrame();
        JButton adaugareShoppingList = new JButton("Adaugare lista cumparaturi");
        adaugareShoppingList.setBounds(64, 24, 300, 40);
        adaugareShoppingList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuAdaugare meniuAdaugare = new MeniuAdaugare();
                frame.setVisible(false);
                frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
                frame.dispose();
            }
        });

        JButton afisare = new JButton("Accesare liste cumparaturi");
        afisare.setBounds(64, 88, 300, 40);
        afisare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuAfisare meniuAfisare = new MeniuAfisare();
                frame.setVisible(false);
                frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
                frame.dispose();
            }
        });

        JButton stergereListe = new JButton("Sterge toate listele de cumparaturi");
        stergereListe.setBounds(64, 216, 300, 40);

        JButton stergereLista = new JButton("Sterge o lista de cumparaturi");
        stergereLista.setBounds(64, 152, 300, 40);
        stergereLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuStergereLista meniuStergereLista = new MeniuStergereLista();
                frame.setVisible(false);
                frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
                frame.dispose();
            }
        });


        JLabel successInfo = new JLabel("Succes!");
        stergereListe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Agenda.stergeListaDeCumparaturi();
                Agenda.audit("stergere_liste_cumparaturi");

                try {
                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                    PreparedStatement myStm = myConn.prepareStatement("delete from liste_cumparaturi");
                    myStm.execute();
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
                successInfo.setBounds(412, 152, 64, 40);
            }
        });

        frame.add(adaugareShoppingList);
        frame.add(afisare);
        frame.add(stergereListe);
        frame.add(stergereLista);
        frame.add(successInfo);
        frame.setSize(508, 600);
        frame.setLayout(null);
        frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}
