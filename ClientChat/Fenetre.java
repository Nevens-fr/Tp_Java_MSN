package ClientChat;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author Aurélien Tudoret
 * @version 1.0
 */


/**
 * Une classe Fenetre, classe fille de JFrame qui permet de créer une fenetre personnalisée
 * 
 * @param WIN_WIDTH la largeur de la fenetre
 * @param WIN_HEIGHT la hauteur de la fenetre
 * @param principalPanel le gestionnaire de placement dans la fenetre
 * @param serialVersionUID numéro de version
 * @param estConnecte Défini si l'utilisateur est connecté ou non
 * @param connexion La classe contenant le necessaire pour se connecter au serveur
 * @param chat la classe permettant la gestion du système de message
 * @param connect la classe permettant d'afficher les personnes connectées
*/
public class Fenetre extends JFrame{
    
    private final int WIN_WIDTH = 980, WIN_HEIGHT = 680;
    private static final long serialVersionUID = -4939544011287453046L;
    private JPanel principalPanel;
    private Connexion connexion;
    private boolean estConnecte;
    private Chat chat;
    private Connectes connect;

    /**
     * Constructeur de la fenetre
     */
    public Fenetre(){

        super("Msn_like");

        estConnecte = false;

        this.setSize(WIN_WIDTH, WIN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        this.setLocationRelativeTo(null);

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){

        }

        principalPanel = new JPanel();
        principalPanel.setLayout(new BorderLayout(20 , 20));

        this.setContentPane(principalPanel);

        principalPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //placement de la zone de connexion

        Box panelNord1 = Box.createHorizontalBox();
        Box panelNord2 = Box.createVerticalBox();
        Box panelNord3 = Box.createVerticalBox();
        Box panelNord4 = Box.createHorizontalBox();
        Box panelNord5 = Box.createHorizontalBox();
        Box panelNord6 = Box.createHorizontalBox();
        connexion = new Connexion(new JTextField(), new JTextField(), new JTextField(), this);
        
        principalPanel.add(panelNord1, BorderLayout.NORTH);
        panelNord1.add(panelNord2);
        panelNord1.add(Box.createRigidArea(new Dimension(50, 0)));
        panelNord1.add(panelNord3);

        panelNord2.add(panelNord4);
        panelNord4.add(new JLabel("Nom"));
        panelNord4.add(Box.createRigidArea(new Dimension(10, 0)));
        panelNord4.add(connexion.getNom());
        panelNord3.add(connexion.getBouton());
        panelNord3.add(Box.createRigidArea(new Dimension(0, 10)));
        panelNord2.add(Box.createRigidArea(new Dimension(0, 5)));

        panelNord2.add(panelNord5);
        panelNord3.add(panelNord6);
        panelNord5.add(new JLabel("IP"));
        panelNord5.add(Box.createRigidArea(new Dimension(28, 0)));
        panelNord5.add(connexion.getIp());
        panelNord6.add(new JLabel("Port"));
        panelNord6.add(Box.createRigidArea(new Dimension(10, 0)));
        panelNord6.add(connexion.getPort());

        //Placement de la zone des utilisateurs connectés
        connect = new Connectes(this);

        Box panelOuest = Box.createVerticalBox();

        principalPanel.add(panelOuest, BorderLayout.WEST);

        panelOuest.add(new JLabel("Connectés"));
        panelOuest.add(connect.getConnectes());

        //Placement de la zone de discussion
        chat = new Chat(this);

        Box panelEst = Box.createVerticalBox();

        principalPanel.add(panelEst, BorderLayout.CENTER);
        Box panelEst2 = Box.createHorizontalBox();
        Box panelEst3 = Box.createHorizontalBox();

        panelEst.add(new JLabel("Discussion"));
        panelEst.add(chat.getScroll());
        panelEst.add(new JLabel("Message"));
        panelEst.add(panelEst3);
        panelEst3.add(connect.getComboBox());
        panelEst3.add(chat.getMessage());
        panelEst.add(panelEst2);
        panelEst2.add(chat.getBouton());
        panelEst2.add(chat.getSmileyBouton());


        //on rend la feentre visible
        this.setVisible(true);
    }

    /**
     * Change la valeur du boolean
     * @param bool la nouvelle valeur de la variable d'instance
     */
    public void setEstConnecte(boolean bool){
        estConnecte = bool;
    }

    /**
     * Getter du boolean de connection
     * @return boolean la valeur de la variable
     */
    public boolean getEstConnecte(){
        return estConnecte;
    }

    /**
     * Getter de la partie de l'interface affichants les membres connectés
     * @return Connectes la classe gérant l'afichages des gens connectés
     */
    public Connectes getConnect(){
        return connect;
    }

    /**
     * Getter de la partie de l'interface permettant la connection et déconnection au serveur
     * @return Connexion la connection et déconnection au serveur
     */
    public Connexion getConnexion(){
        return connexion;
    }

    /**
     * Getter de la zone de discussion
     * @return Chat la zone de discussion
     */
    public Chat getChat(){
        return chat;
    }
}
