import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.util.*;


/**
 * @author Aurélien Tudoret
 * @version 1.0
 */


/**
 * Une classe Client étendant les threads, permet de gèrer toutes les interactions réseaux avec un client
 * @param sock le socket relié au client
 * @param ecrire Afin d'écrire des messages vers le client
 * @param lecture Afin de lire les messages du client
 * @param message Stockage d'un message en attente d'envoi
 */
public class Client extends Thread{
    
    private Socket sock;
    private PrintWriter ecrire;
    private BufferedInputStream lecture;
    private String message;

    /**
     * Constructeur
     * @param sock le socket du client
     */
    public Client(Socket sock){

        ecrire = null;
        lecture = null;
        this.sock = sock;
        try{
            ecrire = new PrintWriter(sock.getOutputStream());
            lecture = new BufferedInputStream(sock.getInputStream());
        }
        catch(Exception e){       }

        this.start();
    }

    /**
     * Redéfinition de la méthode de l'interface Runnable
     */
    @Override
    public void run(){
        boolean  deco = false;
        while(!sock.isClosed() && deco == false){
            if(lit()){
                if(message.indexOf("LOGO") >= 0){
                    deco = true;
                }    
                ecrit(message);
            }
        }

        Serveur.supprimeClient(this);
    }

    /**
     * Regarde si un message à été reçu depuis le client, l'extrait et le range
     * @return
     */
    public boolean lit(){
        int stream;
        byte[] b = new byte[4096];

        try{
            lecture = new BufferedInputStream(sock.getInputStream());
            stream = lecture.read(b);
            message = new String(b, 0, stream);
            return true;
        }
        catch(Exception e){       }

        return false;
    }
    
    /**
     * Envoie le paramètre au client
     * @param e Une chaine de caractère à envoyer au client
     */
    public void ecrit(String e){
        //écriture au client
        List<Client> liste = Serveur.getListeDesClients();

        for(Client c : liste){
            if(c != this){
                c.ecrire.write(e);
                c.ecrire.flush(); 
            }  
        }
    }
}
