package ClientChat;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import java.awt.event.*;

/**
 * @author Aurélien Tudoret
 * @version 1.0
 */


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
        bouton.getBouton().setEnabled(false);

        this.nom = nom;
        this.ip = ip;
        this.port = port;

        setChamps(true, "Connection");

        //Ajout d'un listener sur le champs nom afin de vérifier s'il est rempli
        nom.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent event){
                updated(event);
            }
            @Override
            public void insertUpdate(DocumentEvent event){
                updated(event);
            }
            @Override
            public void removeUpdate(DocumentEvent event){
                updated(event);
            }

            /**
             * Active ou désactive le bouton d'envoi si la zone de text est vide
             * @param event L'évenement reçu
             */
            public void updated(DocumentEvent event){
                if(nom.getText().length() > 0 && ip.getText().length() > 0 && port.getText().length() > 0){
                    bouton.getBouton().setEnabled(true);
                }
                else{
                    bouton.getBouton().setEnabled(false);
                }
            }
        });

        //Ajout d'un listener sur le champs ip afin de vérifier s'il est rempli
        ip.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent event){
                updated(event);
            }
            @Override
            public void insertUpdate(DocumentEvent event){
                updated(event);
            }
            @Override
            public void removeUpdate(DocumentEvent event){
                updated(event);
            }

            /**
             * Active ou désactive le bouton d'envoi si la zone de text est vide
             * @param event L'évenement reçu
             */
            public void updated(DocumentEvent event){
                if(nom.getText().length() > 0 && ip.getText().length() > 0 && port.getText().length() > 0){
                    bouton.getBouton().setEnabled(true);
                }
                else{
                    bouton.getBouton().setEnabled(false);
                }
            }
        });

        //Ajout d'un listener sur le champs port afin de vérifier s'il est rempli
        port.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent event){
                updated(event);
            }
            @Override
            public void insertUpdate(DocumentEvent event){
                updated(event);
            }
            @Override
            public void removeUpdate(DocumentEvent event){
                updated(event);
            }

            /**
             * Active ou désactive le bouton d'envoi si la zone de text est vide
             * @param event L'évenement reçu
             */
            public void updated(DocumentEvent event){
                if(nom.getText().length() > 0 && ip.getText().length() > 0 && port.getText().length() > 0){
                    bouton.getBouton().setEnabled(true);
                }
                else{
                    bouton.getBouton().setEnabled(false);
                }
            }
        });
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
            fenetre.setEstConnecte(true);

            setChamps(false, "Déconnection");

            if(Main.connectionServeur()){
                Main.envoiServeur("LOGI"+nomText);
                fenetre.getConnect().getConnectes().setText("<br/><p> <font color=\"red\">"+nomText+"</font>");
            }
            else{
                fenetre.setEstConnecte(false);
                setChamps(true, "Connection");
            }
        }
        else{
            Main.envoiServeur("LOGO"+nomText);
            Main.deconnection();
        }
    }

    /**
     * Remet les champs dans un état d'attente de connection
     * @param etat l'état dans lequel on souhaite mettre les champs
     * @param String le text a mettre dans le bouton
     */
    public void setChamps(boolean etat, String text){
        nomText = nom.getText();
        ipText = ip.getText();
        portText = port.getText();

        nom.setEditable(etat);
        ip.setEditable(etat);
        port.setEditable(etat);
        bouton.getBouton().setText(text);
    }
}
