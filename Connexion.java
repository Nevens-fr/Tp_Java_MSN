import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;


/**
 * Une classe qui gère la connexion de l'utilisateur
 * @param bouton le bouton de connexion
 * @param nom la zone de texte pour que l'utilisateur son nom
 * @param ip la zone de texte pour que l'utilisateur son ip
 * @param port la zone de texte pour que l'utilisateur son port
 * @param nomText le nom de l'utilisateur
 * @param ipText l'adresse ip a utiliser
 * @param portText le port a utiliser
 * @param fenetre la fenetre dans laquelle on se connecte
 */
public class Connexion implements ActionListener{

    private Bouton bouton;
    private JTextField nom, ip, port;

    private String nomText, ipText, portText;

    private Fenetre fenetre;

    /**
     * Constructeur de la classe
     * @param nom la zone de texte pour que l'utilisateur saississe son nom
     * @param ip la zone de texte pour que l'utilisateur saississe son ip
     * @param port la zone de texte pour que l'utilisateur saississe son port
     * @param fenetre la fenetre dans laquelle on se connecte
     */
    public Connexion(JTextField nom, JTextField ip, JTextField port, Fenetre fenetre){
        this.fenetre = fenetre;

        bouton = new Bouton("Connection");
        bouton.getBouton().addActionListener(this);
        this.nom = nom;
        this.nom.setText(null);
        this.ip = ip;
        this.ip.setText(null);
        this.port = port;
        this.port.setText(null);

        nomText = null;
        ipText = null;
        portText = null;
    }

    /**
     * Getter du nom d'user
     * @return String le nom d'user a utiliser
     */
    public String getNomTexte(){
        return nomText;
    }

    /**
     * Getter de l'ip
     * @return String l'ip  a utiliser
     */
    public String getIpText(){
        return ipText;
    }

    /**
     * Getter du port
     * @return String le port a utiliser
     */
    public String getPortText(){
        return portText;
    }

    /**
     * Getter JTextField nom
     * @return JTextField le nom de l'utilisateur
     */
    public JTextField getNom(){
        return nom;
    }

    /**
     * Getter JTextField port
     * @return JTextField le port a utiliser
     */
    public JTextField getPort(){
        return port;
    }

    /**
     * Getter JTextField ip
     * @return JTextField l'ip a utiliser
     */
    public JTextField getIp(){
        return ip;
    }

    /**
     * getter du bouton de connexion
     * @return JButton le bouton de connexion
     */
    public JButton getBouton(){
        return bouton.getBouton();
    }

    /**
     * L'action à effectuer quand le bouton est pressé
     * @param arg0 Paramètre inutilisé
    */
    @Override
    public void actionPerformed(ActionEvent arg0){
        if(fenetre.getEstConnecte() == false){
            if(!champsSontValides()){
                JFrame dial = new JFrame();
                dial.setTitle("Nom ou IP ou port invalide");
                dial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dial.add(new JLabel("Nom ou IP ou port invalide"), BorderLayout.CENTER);
                dial.setSize(300, 100);
                dial.setLocationRelativeTo(null);
                dial.setVisible(true);
            }
            else{
                nomText = nom.getText();
                ipText = ip.getText();
                portText = port.getText();

                fenetre.setEstConnecte(true);
                fenetre.getConnect().getConnectes().setText("<br/><p> <font color=\"red\">"+nomText+"</font>");

                nom.setEditable(false);
                ip.setEditable(false);
                port.setEditable(false);
                bouton.getBouton().setText("Déconnection");

                Main.connectionServeur();
                Main.envoiServeur("LOGI"+nomText);
            }
        }
        else{
            Main.envoiServeur("LOGO"+nomText);
            Main.deconnection();
        }
    }

    /**
     * Teste les valeurs dans les champs pour voir si elles sont valides
     * @return boolean true si les champs sont valides, false sinon
     */
    private boolean champsSontValides(){
        if(nom.getText() == "" || port.getText() == "" || ip.getText() == "")
            return false;
        else{
            try{
                Integer.parseInt(port.getText());
                return true;
            }
            catch(Exception e){
                return false;
            }
        }
    }

}
