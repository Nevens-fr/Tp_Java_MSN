package ClientChat;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * @author Aurélien Tudoret
 * @version 1.0
 */

/**
 * La classe représente la zone de chat
 * @param discussion le champs de texte contenant tous les messages
 * @param message la zone de texte pour la saisie des messages de l'utilisateur
 * @param bouton le bouton pour envoyer le message de l'utilisateur
 * @param fenetre la fenetre dans laquelle on se connecte
 * @param scroll un layout pour scroller la discussion
 * @param smiley une fenetre pour choisir des smiley
 */
public class Chat implements ActionListener {
    
    private JTextField message;
    private JEditorPane discussion;
    private Bouton bouton;
    private Fenetre fenetre;
    private JScrollPane scroll;
    private Smiley smiley;

    /**
     * Constructeur
     * @param fenetre la fenetre dans laquelle on se connecte
     */
    public Chat(Fenetre fenetre){

        this.fenetre = fenetre;

        bouton = new Bouton("Envoyer");
        bouton.getBouton().setEnabled(false);

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
        message.setPreferredSize(new Dimension(300, 50));
        message.setText(null);

        //permet de griser le bouton d'envoi si la zone de message est vide
        message.getDocument().addDocumentListener(new DocumentListener(){
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
                if(message.getDocument().getLength()> 0  && fenetre.getEstConnecte()){
                    bouton.getBouton().setEnabled(true);
                }
                else{
                    bouton.getBouton().setEnabled(false);
                }
            }
        });

        message.addKeyListener(new KeyListener(){
            /**
             * Permet d'envoyer le message quand l'utilisateur presse la touche Entrée
             */
            @Override
            public void keyTyped(KeyEvent e1){
                if(e1.getKeyChar() == '\n'){
                    String e = new String("<font color=\"red\">"+fenetre.getConnexion().getNomTexte()+"</font> : "+message.getText());
                    String envoi = new String("MESS"+fenetre.getConnexion().getNomTexte()+" "+message.getText());
                    Main.envoiServeur(envoi);
                    ecrireMessage(e);
                    message.setText(null);
                }
            }

            /**
             * Override obligatoire mais non utilisé
             */
            @Override
            public void keyReleased(KeyEvent e1){
            }

            /**
             * Override obligatoire mais non utilisé
             */
            @Override
            public void keyPressed(KeyEvent e1){
            }
        });

        smiley = new Smiley(message);
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
     * Getter du bouton envoyer
     * @return JButton le bouton envoyer
     */
    public JButton getBouton(){
        return bouton.getBouton();
    }

    /**
     * Getter du bouton des smiley
     * @return JButton le bouton envoyer
     */
    public JButton getSmileyBouton(){
        return smiley.getBouton();
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
            e = "<br/><p style=\"font-size:16\">" + e + "</p>";
            ((HTMLEditorKit)discussion.getEditorKit()).insertHTML((HTMLDocument)discussion.getDocument(), discussion.getDocument().getLength(), e, 0, 0, null);
        }
        catch(Exception exc){

        }
    }
}
