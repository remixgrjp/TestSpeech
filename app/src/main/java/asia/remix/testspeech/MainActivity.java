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

public class MainActivity extends AppCompatActivity{
	private TextToSpeech textToSpeech;

	class TextToSpeechListener implements TextToSpeech.OnInitListener{
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
		}
	}

	public void onClickSpeek( View v ){
		if( textToSpeech.isSpeaking() ){
			textToSpeech.stop();
		}
		textToSpeech.speak( "pen pineapple apple pen", TextToSpeech.QUEUE_FLUSH, null );
	}

	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );

		textToSpeech = new TextToSpeech( this, new TextToSpeechListener() );
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		if( null != textToSpeech ){
			textToSpeech.shutdown();
		}
	}
}
