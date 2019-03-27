package blackburn.college.dungeonsandseminars;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;

import java.util.Date;

public class PartyHub extends AppCompatActivity {
    private Date calendar = Calendar.getInstance().getTime();

    //true for player, false for dm
    private boolean playerORDM;
    private String name;
    private String partyName;
    private String partyID;
    private String nameID;

    private Button closeButton;
    private TextView showName;
    private TextView showPartyName;
    private boolean isConnected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_hub);
        this.name = getIntent().getStringExtra("name");
        this.partyName = getIntent().getStringExtra("partyName");
        this.partyID = getIntent().getStringExtra("partyID");
        this.nameID = getIntent().getStringExtra("nameID");
        this.playerORDM = getIntent().getBooleanExtra("playerORDM", false);

        showName = findViewById(R.id.ShowName);
        showName.setText(name);
        showPartyName = findViewById(R.id.ShowPartyName);
        showPartyName.setText("Party: " + partyName);
        closeButton = findViewById(R.id.ClosePartyHub);
        closeButton.setText("Exit Party");


    }

    public void closeConnection(View v){
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        WorkerThread thread = new WorkerThread("Close:" + nameID + "," + partyID);
        thread.start();
        Intent intent = new Intent(this, IntroScreen.class);
        startActivity(intent);
    }







}
