package blackburn.college.dungeonsandseminars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class Rolldice extends AppCompatActivity {

    private Random generator = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolldice);
    }

    public void rollD4(View v){
        setTextToResult("" + (generator.nextInt(4) + 1));
    }

    public void rollD6(View v){
        setTextToResult("" + (generator.nextInt(6) + 1));
    }

    public void rollD8(View v){
        setTextToResult("" + (generator.nextInt(8) + 1));
    }

    public void rollD10(View v){
        setTextToResult("" + (generator.nextInt(10) + 1));
    }

    public void rollD12(View v){
        setTextToResult("" + (generator.nextInt(12) + 1));
    }

    public void rollD20(View v){
        setTextToResult("" + (generator.nextInt(20) + 1));
    }
    public void rollpercentile(View v){
        int tens = (generator.nextInt(10));
        int ones = (generator.nextInt(10));
        if(tens == 0 && ones == 0){
            setTextToResult("100");
        } else {
            setTextToResult("" + tens + ones);
        }
    }

    public void setTextToResult(String s){
        TextView result = findViewById(R.id.ShowRollResults);
        result.setText(s);
    }
}
