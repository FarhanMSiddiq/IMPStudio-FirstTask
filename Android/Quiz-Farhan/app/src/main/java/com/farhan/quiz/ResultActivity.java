package com.farhan.quiz;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.farhan.quiz.R;

public class ResultActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		//get rating bar object
		RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1); 
		bar.setNumStars(5);
		bar.setStepSize(0.5f);
		//get text view
		TextView t=(TextView)findViewById(R.id.textResult);
		//get score
		Bundle b = getIntent().getExtras();
		int score= b.getInt("score");
		//display score
		if (score == 1){
			bar.setRating((float) 2.5);
		}else if (score == 2){
			bar.setRating(5);
		}else if (score == 0){
			bar.setRating(0);
		}

		switch (score)
		{
			case 0: t.setText("Skor Kamu 0%, Tetap Terus Belajar ya");
				break;
		case 1: t.setText("Skor Kamu 50%, Belajar Lagi Dengan Giat");
			break;
		case 2: t.setText("Skor Kamu 100%, Kamu Mendapatkan Nilai Sempurna");
		break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent settingsIntent = new Intent(this, QuizActivity.class);
			startActivity(settingsIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
