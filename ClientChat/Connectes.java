package ClientChat;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Aurélien Tudoret
 * @version 1.0
 */

/**
 * Classe qui permet la gestion des utilisateurs connectés
 * @param fenetre la fenetre dans laquelle on se connecte
 * @param connectes Une zone de texte permettant d'afficher les utilisateurs connectés
 * @param listeCo Liste des utilisateurs connectés avec formattage HTML
 * @param listeCouleur liste des couleurs disponibles pour les utilisateurs
 * @param comboBox  Permet de définir le destinataire du message
 * @param userConnectes tableau des utilisateurs connectés sans formattage HTML
 */
public class Connectes{
    
    private JEditorPane connectes;
    private Fenetre fenetre;
    private ArrayList<String> listeCo;
    private ArrayList<String> listeCouleur;
    private JComboBox<String> comboBox;
    private ArrayList<String> userConnectes;

    /**
     * Constructeur
     * @param fenetre la fenetre dans laquelle on se connecte
     */
    public Connectes(Fenetre fenetre){
        this.fenetre = fenetre;
        this.connectes = new JEditorPane();

        userConnectes = new ArrayList<String>();
        userConnectes.add("Général");

        this.connectes.setEditorKit(new HTMLEditorKit());
        this.connectes.setDocument(new HTMLDocument());
        this.connectes.setContentType("text/html");
        this.connectes.setText("<p>Aucun connecté</p>");
        this.connectes.setPreferredSize(new Dimension(100, 50));

        comboBox = new JComboBox<String>();
        comboBox.addItem("Général");

        listeCo = new ArrayList<String>();
        listeCouleur = new ArrayList<String>();

        listeCouleur.add("#f4780a");//orange
        listeCouleur.add("green");
        listeCouleur.add("#1C9B80");//turquoise
        listeCouleur.add("blue");
        listeCouleur.add("#0d4acd");//bleu foncé
        listeCouleur.add("#be0dcd");//magenta
        listeCouleur.add("#ff00d1");//rose
        listeCouleur.add("#8b00ff");//violet
    }

    /**
     * Getter des connectés
     * @return JEditorPane la zone de texte contenant les utilisateurs connectés
     */
    public JEditorPane getConnectes(){
        return connectes;
    }

    /**
     * Ajoute un nouvel utilisateur à la liste des connectés
     * @param e la chaine de caractère contenant le nom du nouvel utilisateur
     */
    public void addConnecteUtilisateur(String e){
        boolean contient = false;
        try{
            String e1 = "<font color=\""+listeCouleur.get(listeCouleur.size() - 1)+"\">"+e+"</font>";

            for(String e2 : listeCo){
                if(e2.indexOf(e) >= 0){
                    contient = true;
                }
            }
            if(contient == false){
                listeCouleur.remove(listeCouleur.size() - 1);
                listeCo.add(e1);
                ((HTMLEditorKit)connectes.getEditorKit()).insertHTML((HTMLDocument)connectes.getDocument(), connectes.getDocument().getLength(), e1, 0, 0, null);
                Main.envoiServeur("LOGI"+fenetre.getConnexion().getNomTexte());
                comboBox.addItem(e);
                userConnectes.add(e);
            }
        }
        catch(Exception exc){

        }
    }

    /**
     * Getter de la combobox contenant l'utilisateur a qui le message s'adresse
     * @return JComboBox le conteneur contenant un bouton de choix
     */
    public JComboBox<String> getComboBox(){
        return comboBox;
    }

    /**
     * Un nouveau message est reçu, on l'envoie pour affichage formatté avec la couleur
     * @param e
     */
    public void nouveauMessage(String e){
        int tailleNom = e.indexOf(" ");
        String nom = e.substring(0, tailleNom);

        for(String elem : listeCo){
            if(elem.indexOf(nom) >= 0){
                String send = elem + " : " +e.substring(tailleNom, e.length());
                fenetre.getChat().ecrireMessage(send);
            }
        }
    }

    /**
     * Réception  d'un message privé
     * @param message le message recu
     */
    public void nouveauMessagePrive(String message){
        String nomDest = new String("");
        int i;

        for(i = 0; i < message.length() && message.charAt(i) != ':'; i++){
            nomDest += message.charAt(i);
        }

        if (nomDest == fenetre.getConnexion().getNomTexte()){
            message = message.substring(i + 1, message.length());
            System.out.println("MESP" + message);
        }
    }

    /**
     * Supprime l'utilisateur de la liste des personnes connectées
     * @param e le nom de l'utilisateur
     */
    public void removeUtilisateur(String e){

        for(String elem : listeCo){
            if(elem.indexOf(e) >= 0){
                try{
                    listeCo.remove(elem);
                    userConnectes.remove(e);
                    comboBox.removeItem(e);
                    this.connectes.setDocument(new HTMLDocument());
                    this.connectes.setContentType("text/html");
                    connectes.setText("<br/><p> <font color=\"red\">"+fenetre.getConnexion().getNomTexte()+"</font>");
                    for(String elem2 : listeCo){
                        try{
                            String send = elem2;
                            ((HTMLEditorKit)connectes.getEditorKit()).insertHTML((HTMLDocument)connectes.getDocument(), 0, send, 0, 0, null);
                        }
                        catch(Exception exc){ }
                    }
                }
                catch(Exception exc){   System.out.println("Marche po " + exc.getMessage());}
            }
        }
        
    }

    public String userSelectionne(){
        return (String)comboBox.getSelectedItem();
    }
}