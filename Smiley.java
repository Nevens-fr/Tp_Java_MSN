import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * @author Aurélien Tudoret
 * @version 1.0
 */


/**
 * Classe permettant la sélection de smiley par l'utilisateur et l'insertion de ceux-ci dans la zone de texte
 * @param bouton le bouton permettant l'accès aux smileys
 * @param message la zone de texte dans laquelle insérer les smileys choisis
 */
public class Smiley implements ActionListener {
    private Bouton bouton;
    private JTextField message;
    
    /**
     * Constructeur
     * @param message la zone de texte dans laquelle insérer les smileys choisis
     */
    public Smiley(JTextField message){

        this.message = message;
        bouton = new Bouton("Smiley");

        bouton.getBouton().addActionListener(this);
    }

    /**
     * Getter du bouton
     * @return JButton le bouton pour ouvrir la fenetre de smiley
     */
    public JButton getBouton(){
        return bouton.getBouton();
    }

    /**
     * L'action à effectuer quand le bouton est pressé, ouvrir la zone de smiley
     * @param arg0 Paramètre inutilisé
    */
    @Override
    public void actionPerformed(ActionEvent arg0){
        JFrame miniFenetre = new JFrame();
        miniFenetre.setTitle("Smiley");
        miniFenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        miniFenetre.setLocationRelativeTo(null);

        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(2,3));
        pane.setPreferredSize(new Dimension(300, 75));

        JButton sourire = new JButton("<html><h1>&#128522</h1></html>");//sourire
        sourire.setBorderPainted(false);
        sourire.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                message.setText(message.getText() + "&#128522");
                //miniFenetre.dispatchEvent(new WindowEvent(miniFenetre, WindowEvent.WINDOW_CLOSING));; close window
            }
        });

        JButton innocent = new JButton("<html><h1>&#128519</h1></html>");//innocent
        innocent.setBorderPainted(false);
        innocent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                message.setText(message.getText() + "&#128519");
            }
        });

        JButton rire = new JButton("<html><h1>&#128514</h1></html>");//rire
        rire.setBorderPainted(false);
        rire.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                message.setText(message.getText() + "&#128514");
            }
        });

        JButton clinDOeil = new JButton("<html><h1>&#128521</h1></html>");//clinDOeil
        clinDOeil.setBorderPainted(false);
        clinDOeil.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                message.setText(message.getText() + "&#128521");
            }
        });

        JButton tongue = new JButton("<html><h1>&#128539</h1></html>");//tongue
        tongue.setBorderPainted(false);
        tongue.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                message.setText(message.getText() + "&#128539");
            }
        });

        JButton noMouth = new JButton("<html><h1>&#128566</h1></html>");//noMouth
        noMouth.setBorderPainted(false);
        noMouth.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                message.setText(message.getText() + "&#128566");
            }
        });

        pane.add(sourire);
        pane.add(innocent);
        pane.add(rire);
        pane.add(clinDOeil);
        pane.add(tongue);
        pane.add(noMouth);

        miniFenetre.add(pane);

        miniFenetre.pack();
        miniFenetre.setVisible(true);
    }
}
