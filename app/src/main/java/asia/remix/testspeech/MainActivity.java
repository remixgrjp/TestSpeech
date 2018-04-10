/*
*	BASIC of TextToSpeech
*/
package asia.remix.testspeech;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Locale;
import android.widget.Toast;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
	private TextToSpeech textToSpeech;
	private HashMap<String, String> hashMap = new HashMap<String, String>();
	private final static String ID = "KEY_PARAM_UTTERANCE_ID";

	class Text2SpeechListener implements TextToSpeech.OnInitListener{
		@Override
		public void onInit( int status ){
			Context context = getApplicationContext();
			if( TextToSpeech.SUCCESS==status ){
				Locale locale = Locale.getDefault();
				if( textToSpeech.isLanguageAvailable( locale ) >= TextToSpeech.LANG_AVAILABLE ){
					textToSpeech.setLanguage( locale );
				}else{
					Toast.makeText( context, "need speech data " + locale.getDisplayName(), Toast.LENGTH_SHORT ).show();
				}
			}else{
				Toast.makeText( context, "need TextToSpeech Engine", Toast.LENGTH_SHORT ).show();
			}
			Toast.makeText( context, "Ready TextToSpeech Engine", Toast.LENGTH_SHORT ).show();
			//Must in onInit() set Listener !
			textToSpeech.setOnUtteranceCompletedListener( new Text2SpeechOnUtteranceCompletedListener() );
			hashMap.put( TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, ID );
		}
	}

	class Text2SpeechOnUtteranceCompletedListener implements TextToSpeech.OnUtteranceCompletedListener{
		@Override
		public void onUtteranceCompleted( final String utteranceId ){
			runOnUiThread( new Runnable(){
				@Override
				public void run(){
					Toast.makeText( getApplicationContext(), "finish speech " + utteranceId , Toast.LENGTH_SHORT ).show();
				}
			});
		}
	}

	public void onClickSpeek( View v ){
		if( textToSpeech.isSpeaking() ){
			textToSpeech.stop();
		}
		textToSpeech.speak( "pen pineapple apple pen", TextToSpeech.QUEUE_FLUSH, hashMap );
	}

	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );

		textToSpeech = new TextToSpeech( this, new Text2SpeechListener() );
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		if( null != textToSpeech ){
			textToSpeech.shutdown();
		}
	}
}
