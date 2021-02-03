import javax.swing.*;

/**
 * Classe qui permet la gestion des utilisateurs connectés
 * @param fenetre la fenetre dans laquelle on se connecte
 * @param connectes Une zone de texte permettant d'afficher les utilisateurs connectés
 */
public class Connectes{
    
    private JTextField connectes;
    private Fenetre fenetre;

    /**
     * Constructeur
     * @param fenetre la fenetre dans laquelle on se connecte
     */
    public Connectes(Fenetre fenetre){
        this.fenetre = fenetre;
        this.connectes = new JTextField();
        this.connectes.setText("Aucun connecté");
        this.connectes.setSize(10, 50);
        this.connectes.setEditable(false);
    }

    /**
     * Getter des connecté
     * @return JTextField la zone de texte contenant les utilisateurs connectés
     */
    public JTextField getConnectes(){
        return connectes;
    }
}
