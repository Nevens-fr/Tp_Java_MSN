package ClientChat;

import java.net.Socket;
import java.util.Random;
import java.io.BufferedInputStream;
import java.io.PrintWriter;

/**
 * @author Aurélien Tudoret
 * @version 1.0
 */


/**
 * La classe Main du programme
 * @param fenetre la fenetre du programme qui contient tous les éléments
 * @param socks le socket pour se conneter au serveur
 * @param stream Un entier pour la lecture de messages du serveur
 * @param lecture Variable qui permet de lire les messages envoyés sur le réseau
 */
public class Main{

    private static Fenetre fenetre;
    private static Socket socks;
    private static int stream;
    private static BufferedInputStream lecture;
    
    public static void main(String[] args) {
        
        fenetre = new Fenetre();

    }

    /**
     * Effectue la connexion au serveur
     * @return boolean retourne true si tout c'est bien passé
     */
    public static boolean connectionServeur(){
        Chat chat = fenetre.getChat();
        Connexion connection = fenetre.getConnexion();
        Connectes connect = fenetre.getConnect();
        
        socks = null;
        lecture = null;

        stream = 0;
        byte[] b = new byte[4096];

        try{
            socks = new Socket(connection.getIpText(), Integer.parseInt(connection.getPortText()));
            System.out.println("Connecté au serveur");
            chat.ecrireMessage("<p><b>SERVEUR : Vous êtes maintenant connecté</b></p>");
            lecture = new BufferedInputStream(socks.getInputStream());
            
            //création d'un thread pour la lecture de message envoyé par le serveur
            Thread t = new Thread(new Runnable(){
                private String message;
                public void run(){
                    message = null;
                    Random duree = new Random();
                    while(socks.isClosed() == false){
                        //lecture de message du serveur
                        try{
                            stream = lecture.read(b);
                            message = new String(b, 0, stream);

                            //création d'un nouveau thread pour traiter chaque message reçu
                            Thread traitement = new Thread(new Runnable(){
                                public void run(){
                                    if(message.indexOf("LOGI") >= 0){
                                        try{
                                            Thread.sleep(duree.nextInt(500)*2);//attends une durée aléatoire pour éviter les concurences sur l'accès aux datas
                                        }
                                        catch(Exception e){
                                            System.out.println("Erreur génération nombre aléatoire thread main");
                                        }
                                        connect.addConnecteUtilisateur(message.substring(4, message.length()));
                                    }
                                    else if(message.indexOf("LOGO") >= 0)
                                        connect.removeUtilisateur(message.substring(4, message.length()));
                                    else if(message.indexOf("MESP") >= 0)
                                        connect.nouveauMessagePrive(message.substring(4, message.length()));
                                    else
                                        connect.nouveauMessage(message.substring(4, message.length()));
                                }
                            });
                            traitement.start();
                        }
                        catch(Exception e){ }
                    }
                }
            });
            t.start();
            return true;
        }
        catch(Exception e){   
            chat.ecrireMessage("<p><b>Connection serveur échouée</b></p>");
            return false;
        }
    }

    /**
     * Envoie la chaine de caractère passée en paramètre au serveur
     * @param e la chaine de caractère à envoyer
     */
    public static void envoiServeur(String e){
        PrintWriter ecrire = null;
        
        try{
            ecrire =  new PrintWriter(socks.getOutputStream());
            ecrire.write(e);
            ecrire.flush();
        }
        catch(Exception exc){   }

        
    }

    /**
     * fermeture du socket vers le serveur puis de l'application
     */
    public static void deconnection(){
        try{
            socks.close();
            System.out.println("Déconnection au serveur");
        }
        catch(Exception e){

        }
        System.exit(0);
    }
}