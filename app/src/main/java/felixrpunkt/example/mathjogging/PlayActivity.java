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
    TextView exerciseView;                                          //shows the current math problem
    int number1, number2;                                           //the numbers will be generated in rngNumber()
    int number;                                                     //auxiliary-variable to generate the numbers
    int result;                                                     //shows the result
    int fakeResult;                                                 //the results will be generated in rngResult
    int rightCounter;                                               //Counts the right answers
    int counterTotal;                                               //counts the total exercises
    int lifeCounter =5;                                             //counts the lifes of the player
    TextView counterTxt;                                            //shows the value of the rightCounter and counterTotal
    TextView countDownTxt;                                          //shows the seconds that are left
    TextView resultView;                                            //shows the result if the game is over
    ImageView heart1, heart2,heart3,heart4,heart5;                  //shows the lifes of the player
    Button button1, button2, button3, button4,playAgainBtn;         //shows the possible results
    Integer[] resultArray =new Integer[4];                          //used to generate and shuffle the results from line 99 to 102
    CountDownTimer timer;                                           //used for the CountDownTimer in setCountDownTimer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        /*
        initialization of the graphic components
         */
        exerciseView =findViewById(R.id.exerciseView);
        counterTxt=findViewById(R.id.counterTxt);
        countDownTxt=findViewById(R.id.countDownTxt);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        playAgainBtn=findViewById(R.id.playAgainBtn);
        playAgainBtn.setVisibility(View.INVISIBLE);
        resultView =findViewById(R.id.resultView);
        heart1=findViewById(R.id.heart1);
        heart2=findViewById(R.id.heart2);
        heart3=findViewById(R.id.heart3);
        heart4=findViewById(R.id.heart4);
        heart5=findViewById(R.id.heart5);

        setCounter(rightCounter =0, counterTotal =0);

        //startAgain() starts the game
        startAgain();

    }

    //adds the two numbers and return the result
    public int exercise(int number1, int number2){
        result =number1+number2;
        return result;
    }

    //creating a number beteen 1 and 40
    public int rngNumber(){
        int min=1;
        int max=40;
        number=(min+ (int)(Math.random()*((max-min)+1)));
        Log.i(String.valueOf(number), "Number");
        return number;
    }
    //sets the exerciseView to the current exercise
    public void setExerciseView(int number1, int number2){
        exerciseView.setText(number1 +" + "+ number2 +" =? ");
    }

    //creats 3 fakeResults by adding or substracting 1 to 10 depending on the value of the "operand" wich is also generate by a rngGenerator
    //returns the fakeResults
    public int rngResult(int ergebnis){
        int min=1;
        int max=100;
        int minErgebnis=1;
        int maxErgebnis=10;
        int operand=2;
        operand=(min+ (int)(Math.random()*((max-min)+1)));
        if(operand>=50){
            fakeResult = ergebnis-(minErgebnis+ (int)(Math.random()*((maxErgebnis-minErgebnis)+1)));
        }
        else {
            fakeResult = ergebnis + (minErgebnis + (int) (Math.random() * ((maxErgebnis - minErgebnis) + 1)));
        }
        return fakeResult;
    }

    public void startAgain( ){
        //sets the visibility of the playAgainBtn to invisible
        playAgainBtn.setVisibility(View.INVISIBLE);
        //generats the 2 numbers
        number1 =rngNumber();
        number2 =rngNumber();
        //calculates the math-problem and returns the result
        exercise(number1, number2);
        //setting the exerciseView to the current exercise
        setExerciseView(number1, number2);
        //wright the fakeResults into an Array
        for(int i=0; i<=2;i++) {
            rngResult(result);
            resultArray[i]= fakeResult;
        }
        resultArray[3]= result;
        //converts the array to a list an shuffles this
        List<Integer> resultList = Arrays.asList(resultArray);
        Collections.shuffle(resultList);
        //setting the text of the buttons to one of the results
        button1.setText(String.valueOf(resultList.get(0)));
        button2.setText(String.valueOf(resultList.get(1)));
        button3.setText(String.valueOf(resultList.get(2)));
        button4.setText(String.valueOf(resultList.get(3)));

        //sets the time of the countdown depending on counterTotal
        if(counterTotal >30){
            setCountDown(8000);
        }
        else if(counterTotal >20){
            setCountDown(10000);
        }
        else if(counterTotal >15){
            setCountDown(15000);
        }
        else if(counterTotal >10){
            setCountDown(25000);
        }
        else if(counterTotal <=10){
            setCountDown(30000);
        }

    }
    //checks the results by getting the text of the button an compares it with the result
    //sets the 2 counters
    //if the answer is wrong the lifecounter will get 1 down
    public void checkResult(View view){
        Button button=(Button)view;
        button.getText();
        counterTotal = counterTotal +1;
        Log.i("Tag", String.valueOf(view.getTag()));
        Log.i("Text", String.valueOf(button.getText()));
        if(result == Integer.valueOf(String.valueOf(button.getText()))){
            rightCounter = rightCounter +1;
        }
        else{
            lifeCounter = lifeCounter -1;
            if(lifeCounter ==4){

                heart5.animate().alpha(0).setDuration(1000);
            }
            if(lifeCounter ==3){
                heart4.animate().alpha(0).setDuration(1000);
            }
            if(lifeCounter ==2){
                heart3.animate().alpha(0).setDuration(1000);
            }
            if(lifeCounter ==1){
                heart2.animate().alpha(0).setDuration(1000);
            }

            if(lifeCounter ==0){

                heart1.animate().alpha(0).setDuration(1000);
            }
        }
        /*if there is no more life for the player, the buttons will be disabled and the timer will be canceled,
        *playAgainBtn will be set on visible,resultView will be set on visible and the text of resultView will be set on result, by running the method gameOver()
        */
        if(lifeCounter <=0){
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            gameOver();
        }
        /*if the lifeCounter is greater than 0 the next mathproblem will be shown by the method startAgain()
          the will be canceled
          the countertext will be updated
         */
        else if(lifeCounter >0) {
            setCounter(rightCounter, counterTotal);
            timer.cancel();
            startAgain();
        }
    }
    //sets the counter
    public void setCounter(int rightCounter, int counterTotal){
        counterTxt.setText(rightCounter +" / "+ counterTotal);
    }
    //the setCountDown method gets the millisInFuture by the checkResult method, depending on the counterTotal value
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
            //if the time runs out the button will be disabled, playAgainBtn will be visible, the hearts invisible and toastmessage will be shown
            public void onFinish() {
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                playAgainBtn.setVisibility(View.VISIBLE);
                resultView.setVisibility(View.VISIBLE);
                resultView.setText(String.valueOf(result));
                heart1.animate().alpha(0).setDuration(0);
                heart2.animate().alpha(0).setDuration(0);
                heart3.animate().alpha(0).setDuration(0);
                heart4.animate().alpha(0).setDuration(0);
                heart5.animate().alpha(0).setDuration(0);
                Toast.makeText(getApplicationContext(),"Time's up ! ", LENGTH_SHORT).show();
            }
        }.start();
    }
    //presets the game
    public void playAgainClick(View view){
        rightCounter =0;
        counterTotal =0;
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        resultView.setVisibility(View.INVISIBLE);
        setCounter(rightCounter, counterTotal);
        countDownTxt.setTextColor(Color.BLACK);
        lifeCounter =5;
        heart1.animate().alpha(1).setDuration(0);
        heart2.animate().alpha(1).setDuration(0);
        heart3.animate().alpha(1).setDuration(0);
        heart4.animate().alpha(1).setDuration(0);
        heart5.animate().alpha(1).setDuration(0);
        startAgain();
    }
    public void gameOver(){
        timer.cancel();
        playAgainBtn.setVisibility(View.VISIBLE);
        resultView.setVisibility(View.VISIBLE);
        resultView.setText(String.valueOf(result));
    }

}