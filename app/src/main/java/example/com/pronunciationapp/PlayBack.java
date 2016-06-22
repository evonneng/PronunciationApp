package example.com.pronunciationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class PlayBack extends AppCompatActivity {
    TextToSpeech player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //show the text typed in by the user
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView usrTxt = new TextView(this);
        usrTxt.setText(message);
        usrTxt.setTextSize(40);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.usrTxt);
        layout.addView(usrTxt);

        //initializing the player
        player=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    player.setLanguage(Locale.UK);
                }
            }
        });
        //play the string message passed to the player
        player.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }

}
