package fr.univtln.tbezenger.r2d2translator;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer{

    TextView textResult;
    MainActivityModel model;
    CanvasView canvasView;
    int SAMPLE_RATE = 22050;
    boolean shouldContinue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = MainActivityModel.getInstance();
        model.addObserver(this);

        textResult = (TextView)findViewById(R.id.textResult);
        canvasView = (CanvasView)findViewById(R.id.canvas);

        while (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},0);
        }



        //////////////////////    TESTS    ////////////////////////
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            model.requete(mapper.writeValueAsString(sound));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////////////////////////


    }

    public void ecouter(View view){
        shouldContinue = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

                int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);

                if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
                    bufferSize = SAMPLE_RATE * 2;
                }

                final short[] audioBuffer = new short[bufferSize / 2];

                AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.DEFAULT,
                        SAMPLE_RATE,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        bufferSize);

                if (record.getState() != AudioRecord.STATE_INITIALIZED) {
                    Log.e("TAG", "Audio Record can't initialize!");
                    return;
                }
                record.startRecording();

                Log.v("TAG", "Start recording");

                long shortsRead = 0;
                while (shouldContinue) {
                    int numberOfShort = record.read(audioBuffer, 0, audioBuffer.length);
                    shortsRead += numberOfShort;

                    // Do something with the audioBuffer
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            canvasView.setSpectre(audioBuffer);
                        }
                    });
                }

                record.stop();
                record.release();

                // evoyer en rest
                Log.v("TAG", String.format("Recording stopped. Samples read: %d", shortsRead));
            }
        }).start();
    }

    public void traduire(View view){
        shouldContinue = false;
        textResult.setText("A");
    }



    @Override
    public void update(Observable o, Object arg) {
        textResult.setText(model.getTexte_compris());
    }
}