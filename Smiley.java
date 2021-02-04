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
        pane.setLayout(new GridLayout(0,3));
        pane.setPreferredSize(new Dimension(300, 25));

        JButton sourire = new JButton("<html>&#128522</html>");//sourire
        sourire.setBorderPainted(false);
        sourire.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                message.setText(message.getText() + "&#128522");
                miniFenetre.dispatchEvent(new WindowEvent(miniFenetre, WindowEvent.WINDOW_CLOSING));;
            }
        });

        JButton innocent = new JButton("<html>&#128519</html>");//innocent
        innocent.setBorderPainted(false);
        innocent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                message.setText(message.getText() + "&#128519");
                miniFenetre.dispatchEvent(new WindowEvent(miniFenetre, WindowEvent.WINDOW_CLOSING));;
            }
        });

        JButton rire = new JButton("<html>&#128514</html>");//rire
        rire.setBorderPainted(false);
        rire.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                message.setText(message.getText() + "&#128514");
                miniFenetre.dispatchEvent(new WindowEvent(miniFenetre, WindowEvent.WINDOW_CLOSING));;
            }
        });

        pane.add(sourire);
        pane.add(innocent);
        pane.add(rire);

        miniFenetre.add(pane);

        miniFenetre.pack();
        miniFenetre.setVisible(true);
    }
}
