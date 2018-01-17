package fr.univtln.tbezenger.r2d2translator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream inputStream = getResources().openRawResource(R.raw.a);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream,5000);
        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

        List<Short> sound = new ArrayList<>();

        try {
            while (dataInputStream.available()>0){
                sound.add(dataInputStream.readShort());

            }
            System.out.println(sound.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}