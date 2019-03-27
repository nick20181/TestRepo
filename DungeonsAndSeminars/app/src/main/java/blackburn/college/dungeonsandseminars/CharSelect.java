package blackburn.college.dungeonsandseminars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class CharSelect extends AppCompatActivity {

    private DatabaseHelper db = null;
    private boolean multiplayer;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_select);
        intent = new Intent(this, CharacterScreen.class);
        multiplayer = getIntent().getBooleanExtra("Multiplayer", false);
        if(multiplayer){
            intent.putExtra("Multiplayer", multiplayer);
            intent.putExtra("partyName", getIntent().getStringExtra("partyName"));
            intent.putExtra("partyID", getIntent().getStringExtra("partyID"));
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        db = new DatabaseHelper(this);

        final ArrayList<String> charList = db.getPCList();

        LinearLayout layout = findViewById(R.id.CharScroll);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(30);
        layout.setBaselineAligned(true);
        layout.removeAllViews();


        if (charList.isEmpty()) {
            TextView msg = new TextView(this);
            msg.setText("No Characters Yet!");
            layout.addView(msg);
        } else {
            for(int i = 0; i < charList.size(); i++){
                Button toadd = new Button(this);
                toadd.setText(charList.get(i));
                toadd.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("playerName", ((Button) v).getText().toString());
                        startActivity(intent);
                    }
                });
                layout.addView(toadd);

            }
        }
    }





    public void openStatGen(View v) {
        Intent intent = new Intent(this, StatGen.class);
        startActivity(intent);
    }
}
