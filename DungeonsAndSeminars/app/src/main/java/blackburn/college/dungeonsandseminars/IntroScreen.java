package blackburn.college.dungeonsandseminars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

public class IntroScreen extends AppCompatActivity {

    private Socket client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void openCharSelect(View v) {
        Intent intent = new Intent(this, CharSelect.class);
        intent.putExtra("Multiplayer", false);
        startActivity(intent);
    }

    public void openPartySelect(View v) {
        Intent intent = new Intent(this, PartyScreen.class);
        startActivity(intent);
    }

}
