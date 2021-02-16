package ClientChat;

import javax.swing.JButton;

/**
 * Cette classe représente un bouton de l'interface graphique ainsi que des méthodes de traitement
 * 
 * @param bouton un JButton de la classe
 */
public class Bouton {
    private JButton bouton;

    /**
     * Constructeur de la classe
     * @param textBouton le texte à insérer dans le bouton
     */
    public Bouton(String textBouton){
        bouton = new JButton(textBouton);
    }

    /**
     * Getter du boutton
     * @return JButton le bouton
     */
    public JButton getBouton(){
        return bouton;
    }
    
}
