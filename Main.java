import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;

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
        
        //port = 64992;
        socks = null;
        lecture = null;

        stream = 0;
        byte[] b = new byte[4096];

        while(fenetre.getEstConnecte() == false);

        try{
            socks = new Socket(connection.getIpText(), Integer.parseInt(connection.getPortText()));
            System.out.println("Connecté au serveur");
            lecture = new BufferedInputStream(socks.getInputStream());
        }
        catch(Exception e){       }

        //création d'un thread
        Thread t = new Thread(new Runnable(){
            public void run(){
                while(socks.isClosed() == false){
                    //lecture de message du serveur
                    try{
                        stream = lecture.read(b);
                        chat.ecrireMessage(new String(b, 0, stream));
                    }
                    catch(Exception e){ }
                }
            }
        });
        t.start();
    }

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