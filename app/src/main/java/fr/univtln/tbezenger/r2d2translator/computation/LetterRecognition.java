package fr.univtln.tbezenger.r2d2translator.computation;

/**
 * Created by tbezenger on 10/01/2018.
 */

/**
 * Singleton permettant de déterminer la lettre prononcée par R2D2
 */
public class LetterRecognition {
    private static LetterRecognition ourInstance = new LetterRecognition();

    public static LetterRecognition getInstance() {
        return ourInstance;
    }

    private LetterRecognition() {
    }
}
