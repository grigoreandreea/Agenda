import Categorii.Agenda;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static java.lang.Integer.parseInt;

public class MeniuStergereLista extends JFrame {

    JFrame frame;

    public MeniuStergereLista(){
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

        JLabel label = new JLabel("Introdu id-ul listei: ");
        label.setBounds(24, 48, 130, 40);

        JTextField textField = new JTextField();
        textField.setBounds(178, 48, 100, 40);

        JButton submit = new JButton("Sterge!");
        submit.setBounds(302, 48, 100, 40);

        JLabel successInfo = new JLabel("Succes!");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idListaCumparaturi = parseInt(textField.getText());

                Agenda.stergeListaDeCumparaturi(idListaCumparaturi);
                Agenda.audit("stergere_lista_cumparaturi, id = " + idListaCumparaturi);

                try {
                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                    PreparedStatement myStm = myConn.prepareStatement("delete from liste_cumparaturi where id= ?");
                    myStm.setInt(1, idListaCumparaturi);
                    myStm.execute();
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }

                textField.setEnabled(false);
                submit.setEnabled(false);
                successInfo.setBounds(426, 48, 64, 40);
            }
        });

        frame.add(backButton, BorderLayout.EAST);
        frame.add(label);
        frame.add(textField);
        frame.add(submit);
        frame.add(successInfo);
        frame.setSize(508, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

}
