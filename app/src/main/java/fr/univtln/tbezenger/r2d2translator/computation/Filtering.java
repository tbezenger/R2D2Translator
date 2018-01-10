package fr.univtln.tbezenger.r2d2translator.computation;

/**
 * Created by tbezenger on 10/01/2018.
 */

/**
 * Singleton permettant le filtrage bruit/son du signal capturé
 */
public class Filtering {
    private static Filtering ourInstance = new Filtering();

    public static Filtering getInstance() {
        return ourInstance;
    }

    private Filtering() {}
}
