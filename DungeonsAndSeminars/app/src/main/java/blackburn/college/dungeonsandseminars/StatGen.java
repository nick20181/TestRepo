package blackburn.college.dungeonsandseminars;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class StatGen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_gen);
    }



    public void generateStats(View v) {
        Random generator = new Random();

        TextView val = null;
        TextView mod = null;
        for (int i = 0; i < 6; i++) {
            //roll 3d6 [3-6], sum them - TO BE CHANGED
            int first = generator.nextInt(4) + 3;
            int second = generator.nextInt(4) + 3;
            int third = generator.nextInt(4) + 3;
            int stat = first + second + third;

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

    public void addChar(View v) {
        DatabaseHelper db = new DatabaseHelper(this);
        TextView name = findViewById(R.id.nameField);
        TextView str = findViewById(R.id.str_val);
        TextView dexterity = findViewById(R.id.dex_val);
        TextView constitution = findViewById(R.id.con_val);
        TextView intelligence = findViewById(R.id.int_val);
        TextView wisdom = findViewById(R.id.wis_val);
        TextView charisma = findViewById(R.id.cha_val);

        System.out.println(name.getEditableText().toString());
        System.out.println(Integer.parseInt(str.getText().toString()));
        System.out.println(Integer.parseInt(dexterity.getText().toString()));
        System.out.println(Integer.parseInt(constitution.getText().toString()));
        System.out.println(Integer.parseInt(intelligence.getText().toString()));
        System.out.println(Integer.parseInt(wisdom.getText().toString()));
        System.out.println(Integer.parseInt(charisma.getText().toString()));


        db.createPlayerCharacter(name.getText().toString(), Integer.parseInt(str.getText().toString()),
                Integer.parseInt(dexterity.getText().toString()), Integer.parseInt(constitution.getText().toString()), Integer.parseInt(intelligence.getText().toString()),
                Integer.parseInt(wisdom.getText().toString()), Integer.parseInt(charisma.getText().toString()));

        finish();
    }
}
