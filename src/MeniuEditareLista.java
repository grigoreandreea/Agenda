import Categorii.Agenda;
import Categorii.Categorie;
import Categorii.Produs;
import Categorii.ShoppingList;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class MeniuEditareLista extends JFrame {
    JFrame frame;

    MeniuEditareLista(ShoppingList shoppingList) {
        frame = new JFrame();
        BasicArrowButton backButton = new BasicArrowButton(BasicArrowButton.WEST);
        backButton.setBounds(0, 0, 36, 24);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuAfisare meniuAfisare= new MeniuAfisare();
                frame.setVisible(false);
                frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
                frame.dispose();
            }
        });

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        int height = 0;
        JLabel titlu = new JLabel();
        titlu.setText(shoppingList.getTitlu());
        titlu.setBounds(64, height, 200, 40);
        frame.add(titlu);


        for(int j=0; j<shoppingList.getProduse().size(); j++ ) {
            height += 44;
            JCheckBox produs = new JCheckBox(shoppingList.getProduse().get(j).getNume(), shoppingList.getProduse().get(j).isBifat());
            produs.setBounds(64, height, 200, 60);
            produs.addActionListener(new PassParameter(frame, shoppingList, shoppingList.getProduse().get(j)));
            checkBoxes.add(produs);
        }

        for (int i=0; i<checkBoxes.size(); i++) {
            frame.add(checkBoxes.get(i));
        }


        frame.add(backButton, BorderLayout.EAST);
        frame.add(backButton, BorderLayout.EAST);
        frame.setSize(508, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

}

class PassParameter implements ActionListener {
    private Produs produs;
    private ShoppingList shoppingList;
    private JFrame frame;

    public PassParameter(JFrame frame, ShoppingList shoppingList, Produs produs) {
        this.frame = frame;
        this.shoppingList = shoppingList;
        this.produs = produs;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Categorie categorie : Agenda.getInstance().categorii) {
            if(categorie instanceof ShoppingList && ((ShoppingList)categorie).getIdShoppingList() == shoppingList.getIdShoppingList()) {
                try {
                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "fds633jkAndreea");
                    PreparedStatement myStm = myConn.prepareStatement("update produse set bifat = ? where id = ?");
                    boolean x = ((ShoppingList) categorie).bifeazaProdusDupaId(produs.getIdProdus());
                    myStm.setBoolean(1, ((ShoppingList) categorie).getProdus(produs.getIdProdus()).isBifat());
                    myStm.setInt(2, produs.getIdProdus());
                    myStm.execute();
                    frame.revalidate();
                    frame.repaint();
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}