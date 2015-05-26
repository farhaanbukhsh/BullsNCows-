package com.example.farhaanbukhsh.bullsncows;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends ActionBarActivity {
    int bulls =0;
    int cows=0;
    int randomNumber[]= new int[4];
    int guessedNumber[]= new int[4];
    EditText et;
    Button guess;
    LinearLayout container;
    MediaPlayer mp,mp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this, R.raw.cowmoo);
        mp2 = MediaPlayer.create(this,R.raw.tigerroar);
        Random crazy = new Random();
        int guessNum =crazy.nextInt(10000);
        convertToArray(guessNum, randomNumber);
        et = (EditText) findViewById(R.id.et);
        guess= (Button) findViewById(R.id.guess);
        container = (LinearLayout)findViewById(R.id.container);
        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int getNum;

                getNum = (et.getText().toString().isEmpty())?0:Integer.valueOf(et.getText().toString());

                if(isFourDigit(getNum)){
                    mp.start();
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.result, null);
                    convertToArray(getNum,guessedNumber);
                    checkBulls(guessedNumber);
                    checkCows(guessedNumber);
                    TextView userNum = (TextView) addView.findViewById(R.id.tv1);
                    TextView bullTv = (TextView) addView.findViewById(R.id.tv2);
                    TextView cowTv = (TextView) addView.findViewById(R.id.tv3);
                    userNum.setText(et.getText().toString());
                    String bullText = bulls +" BULL/S";
                    String cowText = cows+" COW/S";
                    bullTv.setText(bullText);
                    cowTv.setText(cowText);
                    et.setText("");
                    container.addView(addView);
                    if(bulls==4){
                        Toast.makeText(getApplicationContext(),"BINGO!",Toast.LENGTH_LONG);
                        guess.setEnabled(false);
                    }
                    bulls=0;cows=0;
                }
                else{
                    LayoutInflater lf = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popView = lf.inflate(R.layout.popwidow,null);
                    final PopupWindow popUp = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    Button dismiss = (Button) popView.findViewById(R.id.popBt);
                    mp2.start();
                    dismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popUp.dismiss();
                        }
                    });
                    popUp.showAsDropDown(guess, 50, -30);
                    //Toast.makeText(getApplicationContext(),"Give a four digit number",Toast.LENGTH_SHORT).show();
                }

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean isFourDigit(int num){
        int count=0;
        while(num>0){
            count++;
            num /=10;
        }
        if(count==4)
            return true;
        else
            return false;
    }
    public void convertToArray(int num, int a[]){
        int index =0;
        int r;
        while(num>0){
            r = num%10;
            a[index]=r;
            num/=10;
            index++;
        }
    }
    public void checkBulls(int guessedNum[]){
        int i;
        for(i=0; i<4; i++){
            if (guessedNum[i]==randomNumber[i])
                bulls++;
        }
    }
    public void checkCows(int guessedNum[]){
        int i,j;
        for (i=0; i<4; i++){
            for (j=0; j<4; j++){
                if(guessedNum[i]== randomNumber[j])
                    cows++;
            }
        }
        cows=cows-bulls;
    }
}
