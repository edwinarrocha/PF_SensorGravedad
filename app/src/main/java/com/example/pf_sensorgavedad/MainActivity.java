package com.example.pf_sensorgavedad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;
    private Sensor acelerometer;
    private SensorEventListener evento;
    private Button preguntas;
    private TextView respuesta;

    private int mov = 0,
            vector = 0,
            answer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Animacion del fondo
        ConstraintLayout constraintLayout= findViewById(androidx.constraintlayout.widget.R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        //Delcaracion de elementos a usar
        respuesta = (TextView) findViewById(R.id.txt_respuesta);
        preguntas = (Button) findViewById(R.id.btn_pregunta);

        //Acceso a los sensores
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Representa al acelerometro
        acelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Declaracion de Random
        Random rand = new Random();

        //Declaracion de Repsuetas
        /* respuestas afirmativas*/
        String[] resPos = {"Todo apunta a que si",
                "SI",
                "Si,definitivamente",
                "Deberias confiar en ello",
                "Probablemnete si",
                "Es posible",
                "Obviamente si",
                "Si crees todo es posible",
                "Claro, porque no?",
                "Mis fuentes me dicen que probablemente si"};
        /* respuestas negativas*/
        String[] resNeg = {"No",
                "No cuentes con ello",
                "No puedo predecir eso en este momento",
                "No lo veo posible en ningun lugar del multiverso",
                "Ni 12 millones de universos disintos es eso posible",
                "Ni lo suenes",
                "Ni con la brisa de la Rosa de Guadalupe",
                "Me gustaria decirte que si, pero te mentiria",
                "Mis fuentes me dicen que no",
                "Eso es imposible"};
        /* respuestas indefinidas*/
        String[] resIndf = {"Talvez es mejor esperar a las respuesta del universo",
                "No te puedo ofrecer una respuesta clara",
                "Y si mejor le consultas a Walter Mercado",
                "No estoy seguro, pregunta otra vez",
                "Concentrate, y vuelve a preguntar",
                "No entendi la pregunta, hazlo nuevamente",
                "Creo que no estoy apto para responder",
                "Tranquilo velocista, calmate y repite la pregunta",
                "No estoy seguro, mejor sigue tu corazon",
                "No puedo responder a eso "};

        preguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respuesta.setText(" ");
                vector = 0;
                answer = 0;
            }
        });

        if (acelerometer == null){
            finish();
        }

        evento = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //Codigo que se genera por el evento del acelerometer
                if (event.values[0] < 4 && mov == 0) {
                    mov++;
                } else {
                    if (event.values[0] > 4 && mov == 1) {
                        mov++;
                    }
                }

                if (mov == 2) {
                    mov = 0;
                    vector = rand.nextInt(3);
                    answer = rand.nextInt(10);
                    sonido();

                    if (vector == 0) {
                        respuesta.setText(resPos[answer]);

                    } else if (vector == 1) {
                        respuesta.setText(resNeg[answer]);

                    } else {
                        respuesta.setText(resIndf[answer]);
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //
            }
        };

        sm.registerListener(evento, acelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void sonido(){
        MediaPlayer playMagic = MediaPlayer.create(this, R.raw.magic);
        playMagic.start();
    }
}