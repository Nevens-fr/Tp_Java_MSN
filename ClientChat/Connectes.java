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
 */
public class Connectes{
    
    private JEditorPane connectes;
    private Fenetre fenetre;
    private ArrayList<String> listeCo;
    private ArrayList<String> listeCouleur;
    private JComboBox<String> comboBox;

    /**
     * Constructeur
     * @param fenetre la fenetre dans laquelle on se connecte
     */
    public Connectes(Fenetre fenetre){
        this.fenetre = fenetre;
        this.connectes = new JEditorPane();

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
                comboBox.addItem(e);
                Main.envoiServeur("LOGI"+fenetre.getConnexion().getNomTexte());
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
     * @param e Le message reçu depuis le serveur
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
     * @param message le message privé recu
     */
    public void nouveauMessagePrive(String message){
        String nomDest = new String("");
        int i;

        for(i = 0; i < message.length() && message.charAt(i) != ':'; i++){
            nomDest += message.charAt(i);
        }

        if (stringCompare(nomDest,fenetre.getConnexion().getNomTexte())){
            message = message.substring(i + 1, message.length());
            String e = "<font color=\"#71463c\"> DE " + message + "</font>";
            fenetre.getChat().ecrireMessage(e);
        }
    }

    /**
     * Compare deux chaine de caractères
     * @param s1 première chaine
     * @param s2 seconde chaine
     * @return boolean true si elles sont égales
     */
    private boolean stringCompare(String s1, String s2){
        if(s1.length() != s2.length())
            return false;
        
        for(int i = 0; i < s1.length(); i++){
            if(s1.charAt(i) != s2.charAt(i))
                return false;
        }
        return true;
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
                    comboBox.removeItem(e);
                    this.connectes.setDocument(new HTMLDocument());
                    this.connectes.setContentType("text/html");
                    connectes.setText("<br/><p> <font color=\"red\">"+fenetre.getConnexion().getNomTexte()+"</font>");
                }
                catch(Exception exc){   System.out.println("Marche po " + exc.getMessage());}
            }
        }
        Main.envoiServeur("LOGI"+fenetre.getConnexion().getNomTexte());
    }

    /**
     * Retourne l'utilisateur actuellement sélectionné dans la combobox
     * @return String le nom de l'utilisateur sélectionné
     */
    public String userSelectionne(){
        return (String)comboBox.getSelectedItem();
    }
}