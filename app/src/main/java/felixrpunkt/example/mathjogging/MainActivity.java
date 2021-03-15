package felixrpunkt.example.mathjogging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    /*Author FelixR
    Starts the game after pressing the start button.
     */
    Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn=findViewById(R.id.startBtn);
    }
    public void startClick(View view){
        Intent i=new Intent(this, PlayActivity.class);
        startActivity(i);
        finish();
    }

}