package blackburn.college.dungeonsandseminars;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class PartyScreen extends AppCompatActivity implements DMFragment.OnCompleteListener{

    private ArrayList<String> partyList = new ArrayList<String>();
    private FragmentManager fm = getSupportFragmentManager();
    final private Intent toButton = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_screen);
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshParties(this.getCurrentFocus());
        LinearLayout layout = findViewById(R.id.partyLayout);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(30);
        layout.setBaselineAligned(true);
        layout.removeAllViews();
        if (partyList == null) {
            System.out.println("partyList null");
        } else if (partyList.isEmpty()) {
            System.out.println("Party List Empty");
            TextView msg = new TextView(this);
            msg.setText("No Parties Yet! Try refreshing!");
            layout.addView(msg);
        } else {
            System.out.println("Parties Found");
            for (int i = 0; i < partyList.size(); i++) {
                Button toadd = new Button(this);
                System.out.println(partyList.get(i));
                toadd.setText(partyList.get(i).split(",")[1]);
                toButton.putExtra(partyList.get(i).split(",")[1], partyList.get(i).split(",")[0]);
                toadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), CharSelect.class);
                        intent.putExtra("Multiplayer", true);
                        intent.putExtra("partyName", ((Button) v).getText().toString());
                        System.out.println("PartyID" + toButton.getStringExtra(((Button) v).getText().toString()));
                        intent.putExtra("partyID", toButton.getStringExtra(((Button) v).getText().toString()));
                        startActivity(intent);
                    }
                });
                layout.addView(toadd);
            }
        }
    }

    public void refreshParties(View v) {

        WorkerThread thread = new WorkerThread("PartyList");
        thread.start();
        //wait for worker to get info
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = thread.getServerResponse();
        String[] parties = response.split(";");
        for (int i = 0; i < parties.length; i++) {
            System.out.println(parties[1]);
        }
        if (partyList != null) {
            partyList.clear();
            for (int i = 1; i < parties.length; i++) {
                partyList.add(parties[i]);
            }
        }

    }

    public void createNewParty(View v){
        DMFragment frag = new DMFragment();
        frag.show(fm, "Dialog");

    }


    @Override
    public void onComplete(String dmName, String partyName) {
        System.out.println("PartyCreated: " + dmName + " : " + partyName);
        WorkerThread thread = new WorkerThread("CreateParty:" + dmName + "," + partyName);
        thread.start();

        //wait for worker to get info
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = thread.getServerResponse();

        if(response.split(":")[0].contains("partycreated")){
            //continue to partyhub
            Intent intent = new Intent(this, PartyHub.class);
            String ids = response.split(":")[1];
            intent.putExtra("playerORDM", false);
            intent.putExtra("name", dmName);
            intent.putExtra("partyName", partyName);
            intent.putExtra("nameID", ids.split(",")[1]);
            intent.putExtra("partyID", ids.split(",")[0]);
            startActivity(intent);
        } else {
            //error go back to party select screen
        }

    }
}
