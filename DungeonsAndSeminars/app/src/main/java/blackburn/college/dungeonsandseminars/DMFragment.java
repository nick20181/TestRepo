package blackburn.college.dungeonsandseminars;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class DMFragment extends DialogFragment {
    private EditText dmName;
    private EditText partyName;
    private Button okButton;
    private Button cancelButton;
    private OnCompleteListener onCompleteListen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            onCompleteListen = (OnCompleteListener) getActivity();

        } catch(Exception e){
            throw new ClassCastException("Calling Fragment must implement OnAddFriendListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_party_prompt, container, false);
        dmName = v.findViewById(R.id.InputDMName);
        partyName = v.findViewById(R.id.InputPartyName);
        okButton = v.findViewById(R.id.OkPartyCraation);
        cancelButton = v.findViewById(R.id.CancelPartyCreation);

        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onCompleteListen.onComplete(dmName.getText().toString(), partyName.getText().toString());
            }
        });
        okButton.setText("Okay");

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getDialog().dismiss();
            }
        });
        cancelButton.setText("Cancel");
        return v;
    }



    public static interface OnCompleteListener{
        public abstract void onComplete(String dmName, String partyName);
    }


}
