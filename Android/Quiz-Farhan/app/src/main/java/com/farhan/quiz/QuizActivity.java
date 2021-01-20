package com.farhan.quiz;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farhan.quiz.database.Questions;
import com.farhan.quiz.network.DataServiceGenerator;
import com.farhan.quiz.network.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
	List<Questions> quesList;
    ProgressDialog progressDialog;
	int score=0;
	int qid=0;
	int jumlah_soal=0;
	ImageView btnkeluar;
	Questions currentQ;
	TextView txtQuestion,nm1,nm2,time;
	RadioButton rda, rdb, rdc ,rdd ,rde;
	Button butNext,btnBefore,btnselesai;
	private QuestionsViewModel questionsViewModel;
	private RelativeLayout relativeLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		btnkeluar = (ImageView) findViewById(R.id.btn_keluar);
		btnkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(QuizActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Farhan Quiz"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog// ;
        progressDialog.setCancelable(false);

		fetchQuestions();



        questionsViewModel = ViewModelProviders.of(this).get(QuestionsViewModel.class);

        questionsViewModel.getAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(@Nullable final List<Questions> words) {
                // Update the cached copy of the words in the adapter.
                quesList = words;
                Log.e("Semua Soal",words.toString());
                Collections.shuffle(quesList);
            }
        });

        btnselesai = (Button) findViewById(R.id.btn_selesai);

        btnselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                Bundle b = new Bundle();
                b.putInt("score", score); //Your score
                intent.putExtras(b); //Put your score to your next Intent
                startActivity(intent);
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Beranda");

        }

	}

	private void setQuestionView()
	{
		txtQuestion.setText(currentQ.getQuestion());
		rda.setText("a. "+currentQ.getOptA());
		rdb.setText("b. "+currentQ.getOptB());
		rdc.setText("c. "+currentQ.getOptC());
        rdd.setText("d. "+currentQ.getOptD());
        rde.setText("e. "+currentQ.getOptE());
		qid++;

		if(qid==1){
		    nm1.setTextColor(getResources().getColor(R.color.colorWhite));
		    nm1.setBackground(ContextCompat.getDrawable(this, R.drawable.lingkaran));
            nm2.setTextColor(getResources().getColor(R.color.colorBlack));
            nm2.setBackground(ContextCompat.getDrawable(this, R.drawable.putih));
        }else if (qid==2){
            nm1.setTextColor(getResources().getColor(R.color.colorBlack));
            nm1.setBackground(ContextCompat.getDrawable(this, R.drawable.putih));
            nm2.setTextColor(getResources().getColor(R.color.colorWhite));
            nm2.setBackground(ContextCompat.getDrawable(this, R.drawable.lingkaran));
        }

        if(qid!=1){

            btnBefore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    score = score - 1;
                    qid = qid - 2;
                    currentQ = quesList.get(qid);
                    Log.e("ID ", String.valueOf(qid));
                    setQuestionView();
                }

            });

        }
	}

    private void fetchQuestions(){

        DataServiceGenerator DataServiceGenerator = new DataServiceGenerator();
        Service service = DataServiceGenerator.createService(Service.class);
        Call<List<QuestionsModel>> call = service.getQuestions();

        call.enqueue(new Callback<List<QuestionsModel>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<List<QuestionsModel>> call, Response <List<QuestionsModel>> response) {
                if (response.isSuccessful()){
                    if (response != null){
                        List<QuestionsModel> questionsModelList = response.body();

                        for (int i = 0; i < questionsModelList.size(); i++){

                            String question = questionsModelList.get(i).getSoal();
                           String answer = questionsModelList.get(i).getJawabanBenar();
                           String opta = questionsModelList.get(i).getJawabanA();
                           String optb = questionsModelList.get(i).getJawabanB();
                           String optc = questionsModelList.get(i).getJawabanC();
                           String optd = questionsModelList.get(i).getJawabanD();
                           String opte = questionsModelList.get(i).getJawabanE();

                           Questions questions = new Questions(question, opta,
                                    optb, optc, optd , opte, answer);

                           Log.e("Soal : ",questions.toString());
                           questionsViewModel.insert(questions);

                        }

                        Log.e("List Soal : ",questionsViewModel.toString());


                        jumlah_soal = questionsModelList.size();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                takeAction();

                            }
                        }, 3000);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<QuestionsModel>> call, Throwable t) {

            }
        });
    }

    public void takeAction(){
        progressDialog.dismiss();

        time=(TextView)findViewById(R.id.time);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("00:00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                Bundle b = new Bundle();
                b.putInt("score", score); //Your score
                intent.putExtras(b); //Put your score to your next Intent
                startActivity(intent);
                finish();
            }

        }.start();

        currentQ=quesList.get(qid);
        Log.e("Soals :",quesList.get(qid).getQuestion());
        txtQuestion=(TextView)findViewById(R.id.textView1);
        nm1=(TextView)findViewById(R.id.nm1);
        nm2=(TextView)findViewById(R.id.nm2);

        rda=(RadioButton)findViewById(R.id.radio0);
        rdb=(RadioButton)findViewById(R.id.radio1);
        rdc=(RadioButton)findViewById(R.id.radio2);
        rdd=(RadioButton)findViewById(R.id.radio3);
        rde=(RadioButton)findViewById(R.id.radio4);
        butNext=(Button)findViewById(R.id.button1);
        btnBefore=(Button)findViewById(R.id.button2);
        setQuestionView();
        Log.e("qid :", String.valueOf(qid));
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroup1);

                Log.e("Isian :", String.valueOf(grp.getCheckedRadioButtonId()));
                if (grp.getCheckedRadioButtonId() == -1) {

                    Log.e("Err :", "Kamu Belum Mengisi Jawaban");
                    return;

                } else {

                    RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());

                    grp.clearCheck();
                    String[] parts = answer.getText().toString().split(" ");
                    Log.d("yourans", parts[0]);
                    Log.d("jawaban bener", currentQ.getAnswer());

                    String jawabanbener = currentQ.getAnswer()+".";

                    if (jawabanbener.equals(parts[0])) {
                        score++;
                        Log.e("score", "Your score" + score);
                    }



                    Log.i("Jumlah Soal :", String.valueOf(jumlah_soal));

                    if (qid < jumlah_soal) {
                        currentQ = quesList.get(qid);
                        setQuestionView();
                    } else {
                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("score", score); //Your score
                        intent.putExtras(b); //Put your score to your next Intent
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });





    }

}
