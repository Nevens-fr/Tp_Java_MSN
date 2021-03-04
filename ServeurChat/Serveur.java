package ServeurChat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author Aurélien Tudoret
 * @version 1.0
 */

/**
 * Le serveur du système de messagerie, qui assure une connexion entre tous les utilisateurs
 * 
 * @param listeDesClients une liste contenant des instances de la classe Client représentant des utilisateurs connectés
 * @param serveur Le serverSocket permettant de connecter tous les clients
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
     * @param client le client a retiré de la liste
     */
    public static void supprimeClient(Client client){
        listeDesClients.remove(client);
        System.out.println("Client déconnecté ! "+ listeDesClients.size() + " connecté(s) restant(s)");
    }

    /**
     * Getter de la liste des clients
     * @return List la liste des clients
     */
    public static List<Client> getListeDesClients(){
        return listeDesClients;
    }
}
