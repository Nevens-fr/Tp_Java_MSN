import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.*;

/**
 * La classe représente la zone de chat
 * @param discussion le champs de texte contenant tous les messages
 * @param message la zone de texte pour la saisie des messages de l'utilisateur
 * @param bouton le bouton pour envoyer le message de l'utilisateur
 * @param fenetre la fenetre dans laquelle on se connecte
 * @param scroll un layout pour scroller la discussion
 */
public class Chat implements ActionListener {
    
    private JTextField message;
    private JEditorPane discussion;
    private Bouton bouton;
    private Fenetre fenetre;
    private JScrollPane scroll;

    /**
     * Constructeur
     * @param fenetre la fenetre dans laquelle on se connecte
     */
    public Chat(Fenetre fenetre){

        this.fenetre = fenetre;

        bouton = new Bouton("Envoyer");

        bouton.getBouton().addActionListener(this);

        discussion = new JEditorPane();
        discussion.setEditable(false);
        discussion.setEditorKit(new HTMLEditorKit());
        discussion.setDocument(new HTMLDocument());
        discussion.setContentType("text/html");
        discussion.setText("<p>Début de la conversation</p>");
        discussion.setPreferredSize(new Dimension(300, 450));

        scroll = new JScrollPane(discussion);

        message = new JTextField();
        message.setSize(20, 10);
        message.setText(null);
    }

    /**
     * Getter du fil de discussion
     * @return JTextPane la zone de texte de la discussion
     */
    public JEditorPane getDiscussion(){
        return discussion;
    }

    /**
     * Getter de la scroll bar
     * @return JScrollPane le scrollpane de la discussion
     */
    public JScrollPane getScroll(){
        return scroll;
    }

    /**
     * Getter de message
     * @return JTextField la zone de texte des messages écrits par l'user
     */
    public JTextField getMessage(){
        return message;
    }

    /**
     * Getter du bouton
     * @return JButton le bouton envoyer
     */
    public JButton getBouton(){
        return bouton.getBouton();
    }

    /**
     * L'action à effectuer quand le bouton est pressé, envoyer du texte
     * @param arg0 Paramètre inutilisé
    */
    @Override
    public void actionPerformed(ActionEvent arg0){
        if((message.getText() != "" || message.getText() != null) && fenetre.getEstConnecte()){
            String e = new String("<font color=\"red\">"+fenetre.getConnexion().getNomTexte()+"</font> : "+message.getText());
            String envoi = new String("MESS"+fenetre.getConnexion().getNomTexte()+" "+message.getText());
            Main.envoiServeur(envoi);
            ecrireMessage(e);
            message.setText(null);
        }
    }

    /**
     * Ecris la chaine de caractère passée en paramètre dans le chat
     * @param e la chaine à écrire dans le chat
     */
    public void ecrireMessage(String e){
        try{
            e = "<br/><p>" + e + "</p>";
            ((HTMLEditorKit)discussion.getEditorKit()).insertHTML((HTMLDocument)discussion.getDocument(), discussion.getDocument().getLength(), e, 0, 0, null);
        }
        catch(Exception exc){

        }
    }
}