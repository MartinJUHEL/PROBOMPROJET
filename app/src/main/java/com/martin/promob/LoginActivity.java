package com.martin.promob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.martin.promob.model.User;

public class LoginActivity extends Activity {


    EditText editTextj1;
    EditText editTextj2;
    private Button send;

    public static FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         editTextj1 =  findViewById(R.id.edit_user1);
         editTextj2 = findViewById(R.id.edit_user2);
         send = findViewById(R.id.sendButton);
         send.getBackground().setAlpha(MainActivity.getOpacity());

        View view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.drawable.background_login);

        if(!MainActivity.isMulti()) {
            editTextj2.setActivated(false);
            editTextj2.setVisibility(View.INVISIBLE);

        }
    }



    public void sendMessage(View view) {
        Intent intent = new Intent(this, TypeActivity.class);


        //editTextj2.setActivated(false);
        //editTextj2.setVisibility(View.INVISIBLE);

        String user1 = editTextj1.getText().toString();

        if(MainActivity.getmUser().containsKey(user1)){
            MainActivity.setCurrentUser(MainActivity.getmUser().get(user1));
        }else{
            MainActivity.getmUser().put(user1,new User(user1));
            MainActivity.setCurrentUser(MainActivity.getmUser().get(user1));
        }

        if(MainActivity.isMulti()) {
            String user2 = editTextj2.getText().toString();

            if (MainActivity.getmUser().containsKey(user2)) {
                MainActivity.setCurrentUser2(MainActivity.getmUser().get(user2));
            } else {
                MainActivity.getmUser().put(user2, new User(user2));
                MainActivity.setCurrentUser2(MainActivity.getmUser().get(user2));
            }

        }
        startActivity(intent);
    }

}
