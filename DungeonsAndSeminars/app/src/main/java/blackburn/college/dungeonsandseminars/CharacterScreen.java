package blackburn.college.dungeonsandseminars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CharacterScreen extends AppCompatActivity {

    private DatabaseHelper db = null;
    private String name;
    private boolean multiplayer;
    private String playerName;
    private String partyName;
    private String partyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_screen);

        multiplayer = getIntent().getBooleanExtra("Multiplayer", false);
        playerName = getIntent().getStringExtra("playerName");
        partyName = getIntent().getStringExtra("partyName");
        partyID = getIntent().getStringExtra("partyID");

        Button okayButton = (Button)findViewById(R.id.OkJoinCharacterButton);
        if(multiplayer){
            String text = "Join " + partyName;
            okayButton.setText(text);
        } else {
            okayButton.setVisibility(View.GONE);
        }

        setContentView(R.layout.activity_character_screen);

        Intent intent = getIntent();
        name = playerName;

        TextView nameView = (TextView) findViewById(R.id.CharName);
        nameView.setText(name);

        db = new DatabaseHelper(this);

        String[] stats = db.getCharacterStats(name);
        TextView val = null;
        TextView mod = null;

        for (int i = 0; i < stats.length; i++) {
            if (i == 0) { //str
                val = (TextView) findViewById(R.id.str_val);
                mod = (TextView) findViewById(R.id.str_mod);
            } else if (i == 1) { //dex
                val = (TextView) findViewById(R.id.dex_val);
                mod = (TextView) findViewById(R.id.dex_mod);
            } else if (i == 2) { //con
                val = (TextView) findViewById(R.id.con_val);
                mod = (TextView) findViewById(R.id.con_mod);
            } else if (i == 3) { //int
                val = (TextView) findViewById(R.id.int_val);
                mod = (TextView) findViewById(R.id.int_mod);
            } else if (i == 4) { //wis
                val = (TextView) findViewById(R.id.wis_val);
                mod = (TextView) findViewById(R.id.wis_mod);
            } else if (i == 5) { //cha
                val = (TextView) findViewById(R.id.cha_val);
                mod = (TextView) findViewById(R.id.cha_mod);
            }
            int stat = Integer.parseInt(stats[i]);

            val.setText(String.valueOf(stat));

            if (stat >= 10 && stat < 12) {
                mod.setText("0");
            } else if (stat >= 12 && stat < 14) {
                mod.setText("1");
            } else if (stat >= 14 && stat < 16) {
                mod.setText("2");
            } else if (stat >= 16 && stat < 18) {
                mod.setText("3");
            } else if (stat >= 18 && stat < 20) {
                mod.setText("4");
            } else if (stat == 20) {
                mod.setText("5");
            } else if (stat >= 8) {
                mod.setText("-1");
            } else if (stat >= 6) {
                mod.setText("-2");
            } else if (stat >= 4) {
                mod.setText("-3");
            } else if (stat >= 2) {
                mod.setText("-4");
            }
        }


    }

    public void onOkayJoinParty(View v){
        WorkerThread thread = new WorkerThread("Join:" + partyID + "," + playerName);
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = thread.getServerResponse();
        System.out.println("response: " + response);
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(response.toLowerCase().contains("ok")){

            Intent intent = new Intent(v.getContext(), PartyHub.class);
            intent.putExtra("playerORDM", true);
            intent.putExtra("name", playerName);
            intent.putExtra("partyName", partyName);
            intent.putExtra("partyID", partyID);
            intent.putExtra("nameID", response.split(":")[1].split(",")[3]);
            startActivity(intent);
        } else {
            //error
        }
    }

    public void deleteChar(View v) {
        DatabaseHelper db = new DatabaseHelper(this);
        db.removePlayerCharacter(name);

        finish();
    }

    public void openRollDice(View v) {
        Intent intent = new Intent(this, Rolldice.class);
        startActivity(intent);
    }


}
