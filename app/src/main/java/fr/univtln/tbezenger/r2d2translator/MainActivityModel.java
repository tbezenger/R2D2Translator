package fr.univtln.tbezenger.r2d2translator;


import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import fr.univtln.tbezenger.r2d2translator.Utils.Config;
import fr.univtln.tbezenger.r2d2translator.Utils.JsonDecoder;
import fr.univtln.tbezenger.r2d2translator.Utils.Requete;


public class MainActivityModel extends Observable{

    String texte_compris;

    private static final MainActivityModel ourInstance = new MainActivityModel();

    public static MainActivityModel getInstance() {
        return ourInstance;
    }

    private MainActivityModel() {
    }

    public String getTexte_compris() {
        return texte_compris;
    }

    public void requete(String s){

        try {
            String url = Config.URL;
            new Requete().execute(url, "POST", s).get();
            String JSON = new Requete().execute(url,"GET").get();
            System.out.println(JSON);
            JsonDecoder<String> decoder = new JsonDecoder<>();
            texte_compris = decoder.Decoder(JSON,String.class);

            setChanged();
            notifyObservers();

        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
