package ml.oscarmorton.simongame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

// TODO CREATE HIGH SCORE INTENT AND SOUNDS
public class MainActivity extends AppCompatActivity {

    private ArrayList < Integer > generatedNumbers;
    private Random randomNumber;
    private PlaySequence playSequence;

    private AppCompatButton bGreen, bRed, bYellow, bBlue, bStart, bShowHighScore;
    private TextView tvCurrentScore;



    private int currentPosition;
    private int generatedNumbersPosition;



    @SuppressLint("WrongViewCast") //?????
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // inicialse
        generatedNumbers = new ArrayList < > ();
        randomNumber = new Random();

        //Buttons

        bGreen = findViewById(R.id.bGreen);
        bRed = findViewById(R.id.bRed);
        bYellow = findViewById(R.id.bYellow);
        bBlue = findViewById(R.id.bBlue);

        bStart = findViewById(R.id.bStart);
        bShowHighScore = findViewById(R.id.bShowHighScore);

        tvCurrentScore = findViewById(R.id.tvCurrentScore);


        currentPosition = 0;
        generatedNumbersPosition = 0;


        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int id = v.getId();

                switch (id) {
                    case R.id.bGreen:
                        validation(0);

                        break;
                    case R.id.bRed:

                        validation(1);

                        break;
                    case R.id.bYellow:
                        validation(2);
                        break;
                    case R.id.bBlue:
                        validation(3);
                        break;

                    default:
                        break;
                }
            }
        };
        setButtons(false);



        bGreen.setOnClickListener(listener);
        bRed.setOnClickListener(listener);
        bYellow.setOnClickListener(listener);
        bBlue.setOnClickListener(listener);


        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtons(true);
                bStart.setEnabled(false);
                play();


            }
        });

        bShowHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent intent = new Intent(MainActivity.this,HighScoreActivity.class);
               // startActivity(intent);
            }
        });


    }


    /**
     * Adds a random number to the arraylist
     */
    public void addNumebers() {
        generatedNumbers.add(randomNumber.nextInt(4));
    }


    /**
     * Starts the game
     */
    public void play() {
        addNumebers();
        playSequence = new PlaySequence(generatedNumbers);
        playSequence.start();
        setButtons(true);
    }


    /**
     * Validates the users decision.
     * @param colorNumber
     */
    public void validation(int colorNumber) {
        int score;

        if (colorNumber == generatedNumbers.get(generatedNumbersPosition)) {

            currentPosition++;
            generatedNumbersPosition++;

            if (currentPosition == generatedNumbers.size()) {


                //End of the list
                //next round
                // Setting score
                score = Integer.parseInt(tvCurrentScore.getText().toString());
                score += 1;
                tvCurrentScore.setText(String.valueOf(score));

                // resetting the pointer
                currentPosition = 0;
                generatedNumbersPosition = 0;
                play();


            }

        } else {
            // LOST
            Log.d("Lost", "LOST ");
            Toast.makeText(MainActivity.this, "GAME OVER", Toast.LENGTH_SHORT).show();
            generatedNumbers = new ArrayList < > ();

            // resetting the pointer
            currentPosition = 0;
            generatedNumbersPosition = 0;

            // Disabling the buttons
            setButtons(false);
            tvCurrentScore.setText(String.valueOf(0));

            //reactivatin the start button.
            bStart.setEnabled(true);


        }

    }


    /**
     * Set the button enable state
     * @param buttonState the button state
     */
    public void setButtons(Boolean buttonState) {



        bGreen.setEnabled(buttonState);
        bRed.setEnabled(buttonState);
        bYellow.setEnabled(buttonState);
        bBlue.setEnabled(buttonState);

    }





    public class PlaySequence extends Thread {
        private ArrayList < Integer > generatedNumbers;

        // Basic bounce animation
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);



        public PlaySequence(ArrayList < Integer > generatedNumbers) {
            this.generatedNumbers = generatedNumbers;
        }

        @Override
        public void run() {

            // Depending on the random number, we run the bounce animation
            for (int i = 0; i < generatedNumbers.size(); i++) {
                switch (generatedNumbers.get(i)) {
                    case 0:
                        bGreen.post(new Runnable() {
                            @Override
                            public void run() {
                                bGreen.startAnimation(animation);
                            }
                        });
                        break;
                    case 1:
                        bRed.post(new Runnable() {
                            @Override
                            public void run() {
                                bRed.startAnimation(animation);
                            }
                        });
                        break;
                    case 2:

                        bYellow.post(new Runnable() {
                            @Override
                            public void run() {
                                bYellow.startAnimation(animation);
                            }
                        });
                        break;
                    case 3:
                        bBlue.post(new Runnable() {
                            @Override
                            public void run() {
                                bBlue.startAnimation(animation);
                            }
                        });
                        break;
                    default:
                        Log.d("GENERATE NUMBERS", "playSecuence: ERROR ON SWITCH ");
                        break;

                }
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}