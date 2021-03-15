package felixrpunkt.example.mathjogging;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class PlayActivity extends AppCompatActivity {
    TextView aufgabeView;
    int nummer1, nummer2;
    int number;
    int ergebnis,ergebnis1, ergebnis2, ergebnis3;
    int fakeErgebnis;
    int richtigCounter;
    int counterGesamt;
    int lebenCounter=5;
    TextView counterTxt;
    TextView countDownTxt;
    TextView ergebnisView;
    ImageView heart1, heart2,heart3,heart4,heart5;
    Button button1, button2, button3, button4,playAgainBtn;
    Integer[] ergebnisArray =new Integer[4];
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        aufgabeView=findViewById(R.id.aufgabeView);
        counterTxt=findViewById(R.id.counterTxt);
        countDownTxt=findViewById(R.id.countDownTxt);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        playAgainBtn=findViewById(R.id.playAgainBtn);
        playAgainBtn.setVisibility(View.INVISIBLE);
        ergebnisView=findViewById(R.id.ergebnisView);
        heart1=findViewById(R.id.heart1);
        heart2=findViewById(R.id.heart2);
        heart3=findViewById(R.id.heart3);
        heart4=findViewById(R.id.heart4);
        heart5=findViewById(R.id.heart5);
        setCounter(richtigCounter=0,counterGesamt=0);
        startAgain();

    }
    public int aufgabe(int nummer1, int nummer2){
        ergebnis=nummer1+nummer2;
        return ergebnis;
    }
    public int rngNumber(){
        int min=1;
        int max=40;
        number=(min+ (int)(Math.random()*((max-min)+1)));
        Log.i(String.valueOf(number), "Number");
        return number;
    }
    public void setAufgabeView(int nummer1, int nummer2){
        aufgabeView.setText(nummer1+" + "+nummer2+" =? ");
    }
    public int rngErgebnis(int ergebnis){
        int min=1;
        int max=100;
        int minErgebnis=1;
        int maxErgebnis=10;
        int operand=2;
        operand=(min+ (int)(Math.random()*((max-min)+1)));
        if(operand>=50){
            fakeErgebnis= ergebnis-(minErgebnis+ (int)(Math.random()*((maxErgebnis-minErgebnis)+1)));
        }
        else {
            fakeErgebnis = ergebnis + (minErgebnis + (int) (Math.random() * ((maxErgebnis - minErgebnis) + 1)));
        }
        return fakeErgebnis;
    }
    public void startAgain( ){
        playAgainBtn.setVisibility(View.INVISIBLE);
        nummer1=rngNumber();
        nummer2=rngNumber();
        aufgabe(nummer1,nummer2);
        setAufgabeView(nummer1,nummer2);
        for(int i=0; i<=2;i++) {
            rngErgebnis(ergebnis);
            ergebnisArray[i]=fakeErgebnis;
        }
        ergebnisArray[3]=ergebnis;
        List<Integer> ergebnisList= Arrays.asList(ergebnisArray);
        Collections.shuffle(ergebnisList);
        button1.setText(String.valueOf(ergebnisList.get(0)));
        button2.setText(String.valueOf(ergebnisList.get(1)));
        button3.setText(String.valueOf(ergebnisList.get(2)));
        button4.setText(String.valueOf(ergebnisList.get(3)));
        if(counterGesamt>30){
            setCountDown(8000);
        }
        else if(counterGesamt>20){
            setCountDown(10000);
        }
        else if(counterGesamt>15){
            setCountDown(15000);
        }
        else if(counterGesamt>10){
            setCountDown(25000);
        }
        else if(counterGesamt<=10){
            setCountDown(30000);
        }

    }
    public void checkResult(View view){
        Button button=(Button)view;
        button.getText();
        counterGesamt=counterGesamt+1;
        Log.i("Tag", String.valueOf(view.getTag()));
        Log.i("Text", String.valueOf(button.getText()));
        if(ergebnis == Integer.valueOf(String.valueOf(button.getText()))){
            richtigCounter=richtigCounter+1;
        }
        else{
            lebenCounter=lebenCounter-1;
            if(lebenCounter==4){
                heart5.setVisibility(View.INVISIBLE);
            }
            if(lebenCounter==3){
                heart4.setVisibility(View.INVISIBLE);
            }
            if(lebenCounter==2){
                heart3.setVisibility(View.INVISIBLE);
            }
            if(lebenCounter==1){
                heart2.setVisibility(View.INVISIBLE);
            }

            if(lebenCounter==0){
                heart1.setVisibility(View.INVISIBLE);
            }
        }
        if(lebenCounter<=0){
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            gameOver();
        }
        else if(lebenCounter>0) {
            setCounter(richtigCounter, counterGesamt);
            timer.cancel();
            startAgain();
        }
    }
    public void setCounter(int richtigCounter, int counterGesamt){
        counterTxt.setText(richtigCounter+" / "+counterGesamt);
    }
    public void setCountDown(int millisInFuture){
        timer= new CountDownTimer(millisInFuture,500) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownTxt.setText(String.valueOf(millisUntilFinished/1000));
                if(millisUntilFinished/1000<=10){
                    countDownTxt.setTextColor(Color.RED);
                }
            }

            @Override
            public void onFinish() {
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                playAgainBtn.setVisibility(View.VISIBLE);
                ergebnisView.setVisibility(View.VISIBLE);
                ergebnisView.setText(String.valueOf(ergebnis));
                heart1.setVisibility(View.INVISIBLE);
                heart2.setVisibility(View.INVISIBLE);
                heart3.setVisibility(View.INVISIBLE);
                heart4.setVisibility(View.INVISIBLE);
                heart5.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Zeit abgelaufen! ", LENGTH_SHORT).show();
            }
        }.start();
    }
    public void playAgainClick(View view){
        richtigCounter=0;
        counterGesamt=0;
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        ergebnisView.setVisibility(View.INVISIBLE);
        setCounter(richtigCounter,counterGesamt);
        countDownTxt.setTextColor(Color.BLACK);
        lebenCounter=5;
        heart1.setVisibility(View.VISIBLE);
        heart2.setVisibility(View.VISIBLE);
        heart3.setVisibility(View.VISIBLE);
        heart4.setVisibility(View.VISIBLE);
        heart5.setVisibility(View.VISIBLE);
        startAgain();
    }
    public void gameOver(){
        timer.cancel();
        playAgainBtn.setVisibility(View.VISIBLE);
        ergebnisView.setVisibility(View.VISIBLE);
        ergebnisView.setText(String.valueOf(ergebnis));
    }

}