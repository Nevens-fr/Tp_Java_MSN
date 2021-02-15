package ServeurChat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author Aurélien Tudoret
 * @version 1.0
 */

public class Serveur {

    private static List<Client> listeDesClients;
    private static ServerSocket serveur;
    public static void main(String [] args){

        final int port = 64992;

        listeDesClients = new ArrayList<Client>();

        serveur = null;
        Socket client = null;

        //Création du serveur
        try{
            serveur = new ServerSocket(port);
            System.out.println("Serveur Connecté au port " + port);
        }
        catch(Exception e){       }
        
        //Attente de connection
        try{
            do{
                client = serveur.accept();
                listeDesClients.add(new Client(client));
                System.out.println("Client connecté ! "+ listeDesClients.size() + "connecté(s)");
            }while(true);
        }
        catch(Exception e){       }
    }

    /**
     * Supprime le client passé en paramètre de la liste des sockets connectés
     * @param client
     */
    public static void supprimeClient(Client client){
        listeDesClients.remove(client);
        System.out.println("Client déconnecté ! "+ listeDesClients.size() + "connecté(s) restant(s)");
    }

    /**
     * Getter de la liste des clients
     * @return List la liste des clients
     */
    public static List<Client> getListeDesClients(){
        return listeDesClients;
    }
}