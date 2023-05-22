package mg.akim.megan;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNb;
    private Button buttonGo;
    private TextView textViewLog;
    private TextView textResult;
    private ProgressBar preogressBarScore;

    private int nbToFind;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("KILALAO", "App started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNb = (EditText) findViewById(R.id.editTextNb);
        buttonGo = (Button) findViewById(R.id.buttonGo);
        textViewLog = (TextView) findViewById(R.id.textViewLog);
        textResult = (TextView) findViewById(R.id.textResult);
        preogressBarScore = (ProgressBar) findViewById(R.id.preogressBarScore);

        buttonGo.setOnClickListener(buttonGoListener);

        init();
    }

    private void init(){
        score = 0;
        nbToFind = 1 + (int) (Math.random() * 100); //1-100

        Log.i("KILALAO", "Number to find : "+nbToFind);

        preogressBarScore.setProgress(score);

        textViewLog.setText("");
        textResult.setText("");
        editTextNb.setText("");

        editTextNb.requestFocus();
    }

    private void feliciation(){
        textResult.setText(R.string.strEqual);

        AlertDialog retryAlert = new AlertDialog.Builder(this).create();
        retryAlert.setTitle(R.string.app_name);
        retryAlert.setMessage(getString(R.string.strMessage, score));
        retryAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.strYes), new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
            }
        });

        retryAlert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.strNo), new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Log.i("KILALAO", "App exited");
            }
        });

        retryAlert.show();
    }

    private View.OnClickListener buttonGoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("KILALAO", "Button Go clicked");

            String txtNumber = editTextNb.getText().toString();

            if (txtNumber.equals("") || Integer.parseInt(txtNumber) <= 0 || Integer.parseInt(txtNumber) > 100 ){
                Toast.makeText(MainActivity.this, getString(R.string.strValid), Toast.LENGTH_SHORT).show();
                return;
            };

            textViewLog.append(txtNumber + "\r\n");
            preogressBarScore.incrementProgressBy(1);
            score++;

            int myNumber = Integer.parseInt(txtNumber);

            if (myNumber == nbToFind) {
                feliciation();
            } else if (myNumber < nbToFind) {
                textResult.setText(R.string.strGreater);
            } else {
                textResult.setText(R.string.strSmaller);
            }

            editTextNb.setText("");
            editTextNb.requestFocus();

          if(score < 10){
              textResult.append(getString(R.string.strTry, 10 - score));
          }else{
              init();
          }
        }
    };
}
