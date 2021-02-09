package ClientChat;

import javax.swing.JButton;

/**
 * Cette classe représente un bouton de l'interface graphique ainsi que des méthodes de traitement
 */
public class Bouton {
    private JButton bouton;

    /**
     * Constructeur de la classe
     */
    public Bouton(String textBouton){
        bouton = new JButton(textBouton);
    }

    /**
     * Getter du boutton
     */
    public JButton getBouton(){
        return bouton;
    }
    
}
