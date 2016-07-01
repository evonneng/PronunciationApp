package example.com.pronunciationapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class PlayBack extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener{
    //TTS object
    private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    //create the Activity
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);

        String message = getString();
        TextView usrTxt = new TextView(this);
        usrTxt.setTextSize(40);
        usrTxt.setText(message);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.playback);
        layout.addView(usrTxt);

        //get a reference to the button element listed in the XML layout
        Button speakButton = (Button)findViewById(R.id.play_button);
        //listen for clicks
        speakButton.setOnClickListener(this);

        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    //respond to button clicks
    public void onClick(View v) {
        String message = getString();
        speakWords(message);
    }

    //speak the user text
    private void speakWords(String speech) {
        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    private String getString() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        return message;
    }

    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
            }
            else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    //setup TTS
    public void onInit(int initStatus) {
        String lang = MainActivity.get_lang();
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            switch (lang) {
                case "English (US)":
                    if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                        myTTS.setLanguage(Locale.US);
                    break;
                case "English (UK)":
                    if (myTTS.isLanguageAvailable(Locale.UK) == TextToSpeech.LANG_AVAILABLE)
                        myTTS.setLanguage(Locale.UK);
                    break;
                case "Chinese":
                    if (myTTS.isLanguageAvailable(Locale.CHINESE) == TextToSpeech.LANG_AVAILABLE)
                        myTTS.setLanguage(Locale.CHINESE);
                    break;
                case "French":
                    if (myTTS.isLanguageAvailable(Locale.FRENCH) == TextToSpeech.LANG_AVAILABLE)
                        myTTS.setLanguage(Locale.FRENCH);
                    break;
                case "German":
                    if (myTTS.isLanguageAvailable(Locale.GERMAN) == TextToSpeech.LANG_AVAILABLE)
                        myTTS.setLanguage(Locale.GERMAN);
                    break;
                case "Italian":
                    if (myTTS.isLanguageAvailable(Locale.ITALIAN) == TextToSpeech.LANG_AVAILABLE)
                        myTTS.setLanguage(Locale.ITALIAN);
                    break;
                case "Japanese":
                    if (myTTS.isLanguageAvailable(Locale.JAPANESE) == TextToSpeech.LANG_AVAILABLE)
                        myTTS.setLanguage(Locale.JAPANESE);
                    break;
                case "Spanish":
                    Locale locSpanish = new Locale("spa", "MEX");
                    if (myTTS.isLanguageAvailable(locSpanish) == TextToSpeech.LANG_AVAILABLE)
                        myTTS.setLanguage(locSpanish);
                    break;
                case "Korean":
                    if (myTTS.isLanguageAvailable(Locale.KOREAN) == TextToSpeech.LANG_AVAILABLE)
                        myTTS.setLanguage(Locale.KOREAN);
                    break;
            }
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Unable to read input...", Toast.LENGTH_LONG).show();
        }
    }
}
