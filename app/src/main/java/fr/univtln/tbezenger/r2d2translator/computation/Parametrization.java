package fr.univtln.tbezenger.r2d2translator.computation;

/**
 * Created by tbezenger on 10/01/2018.
 */

/**
 * Singleton permettant le calcul des paramètres du signal
 */
public class Parametrization {
    private static Parametrization ourInstance = new Parametrization();

    public static Parametrization getInstance() {
        return ourInstance;
    }

    private Parametrization() {}
}
