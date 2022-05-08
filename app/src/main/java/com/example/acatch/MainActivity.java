package com.example.acatch;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.TimedText;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView timeText;
    TextView scoreText;
    TextView bestText;
    int score;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timeText = (TextView) findViewById(R.id.timeText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        bestText = (TextView) findViewById(R.id.bestText);
        sharedPreferences = this.getSharedPreferences("com.example.acatch", Context.MODE_PRIVATE);

        int storedScore = sharedPreferences.getInt("storedScore", 0);

        if (storedScore != 0) {
            bestText.setText("Best: " + storedScore);
        }


        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        imageArray = new ImageView[]{imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};

        hideImages();

        score = 0;




        new CountDownTimer(10000, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: " + millisUntilFinished / 1000);
            }


            @Override
            public void onFinish() {

                timeText.setText("Full Time");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }


                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Restart");
                alert.setMessage("Do you want play again?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
            }
        }.start();

    }
    public void save (View view){

        if (!scoreText.getText().toString().matches("")) {
            int userScore = Integer.parseInt(scoreText.getText().toString());
            bestText.setText("Best: " + userScore);


            sharedPreferences.edit().putInt("storedScore", userScore).apply();
        }
    }





        public void increaseScore (View view){

            score++;

            scoreText.setText("Score: " + score);

        }

        private void hideImages () {

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {

                    for (ImageView image : imageArray) {
                        image.setVisibility(View.INVISIBLE);
                    }
                    ;

                    Random random = new Random();
                    int i = random.nextInt(9);
                    imageArray[i].setVisibility(View.VISIBLE);

                    handler.postDelayed(this, 375);
                }

            };

            handler.post(runnable);
        }

    }
