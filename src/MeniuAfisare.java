import Categorii.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MeniuAfisare extends JFrame {
    JFrame frame;

    MeniuAfisare() {
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

        ArrayList<ShoppingList> listeCumparaturi = Agenda.getListeCumparaturi();

        ArrayList<JButton> jLabels = new ArrayList<>();

        int height = 24;

        for(int i=0; i<listeCumparaturi.size(); i++) {
            JButton titlu = new JButton();
            titlu.setText(listeCumparaturi.get(i).getTitlu() + " [id = " + listeCumparaturi.get(i).getIdShoppingList() + "]");
            titlu.setBounds(64, height, 200, 40);
            titlu.addActionListener(new GoToMeniuEditareLista(frame, listeCumparaturi.get(i)));

            jLabels.add(titlu);
            height += 64;
        }

        for (int i=0; i<jLabels.size(); i++) {
            frame.add(jLabels.get(i));
        }

        frame.add(backButton, BorderLayout.EAST);
        frame.setSize(508, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}

class GoToMeniuEditareLista implements ActionListener {
    private ShoppingList shoppingList;
    private JFrame frame;

    public GoToMeniuEditareLista(JFrame frame, ShoppingList shoppingList) {
        this.frame = frame;
        this.shoppingList = shoppingList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MeniuEditareLista meniuEditareLista = new MeniuEditareLista(shoppingList);
        frame.setVisible(false);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.dispose();
    }
}