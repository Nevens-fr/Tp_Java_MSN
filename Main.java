import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.PrintWriter;


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
     */
    public static void connectionServeur(){
        Chat chat = fenetre.getChat();
        Connexion connection = fenetre.getConnexion();
        Connectes connect = fenetre.getConnect();
        
        //port = 64992;
        socks = null;
        lecture = null;

        stream = 0;
        byte[] b = new byte[4096];

        while(fenetre.getEstConnecte() == false);

        try{
            socks = new Socket(connection.getIpText(), Integer.parseInt(connection.getPortText()));
            System.out.println("Connecté au serveur");
            chat.ecrireMessage("<p><b>SERVEUR : Vous êtes maintenant connecté</b></p>");
            lecture = new BufferedInputStream(socks.getInputStream());
        }
        catch(Exception e){   
            chat.ecrireMessage("<p><b>Connection serveur échouée</b></p>");
        }

        //création d'un thread
        Thread t = new Thread(new Runnable(){
            public void run(){
                String message = null;
                while(socks.isClosed() == false){
                    //lecture de message du serveur
                    try{
                        stream = lecture.read(b);
                        message = new String(b, 0, stream);

                        if(message.indexOf("LOGI") >= 0)
                            connect.addConnecteUtilisateur(message.substring(4, message.length()));
                        else if(message.indexOf("LOGO") >= 0)
                            connect.removeUtilisateur(message.substring(4, message.length()));
                        else
                            connect.nouveauMessage(message.substring(4, message.length()));
                    }
                    catch(Exception e){ }
                }
            }
        });
        t.start();
    }

    /**
     * Envoie la chaine de caractère passée en paramètre au serveur
     * @param e la chaine de caractère à envoyer
     */
    public static void envoiServeur(String e){
        PrintWriter ecrire = null;
        
        try{
            ecrire =  new PrintWriter(socks.getOutputStream());
        }
        catch(Exception exc){   }

        ecrire.write(e);
        ecrire.flush();
    }

    /**
     * fermeture du socket vers le serveur
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