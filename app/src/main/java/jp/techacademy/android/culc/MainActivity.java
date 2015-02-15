package jp.techacademy.android.culc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    private String inputStr ="";  //入力値
    private String aNum = "";     //数値a
    private String bNum = "";     //数値b
    private String op = "";       //演算子
    private String result = "";   //演算結果
    private int state = 0;        //ステータス(0:a入力中,1:演算子選択,2:b入力中,3:結果表示)

    TextView text1;
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonMethod(View b){ //全てのonClickをbuttonMethodにしておく。この中に処理を書く。

        Button button = (Button)b;
        String s = button.getText().toString();      //入力値を取得する。sとして取得。ここよくわかんない！

        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);  //テキストビューの取得

        //状態分岐。A入力中
        if(state == 0){
            //イベント分岐。数値ボタンが押された時。
            if(chkEvent(s)==0){
                inputStr += s;                      //ここがよくわからない！！
                text1.setText(inputStr);
                state=0;
            }
            //イベント分岐。演算子ボタンが押された時。
            else if(chkEvent(s)==1){
                if(inputStr.length()!=0) {
                    aNum = inputStr;                    //aNumに入力値を保存する
                    op = s;                               //入力された演算子の保存
                    text1.setText(op);                //入力値の出力
                    state = 1;
                }                                                        //状態を演算子入力中へ移行させる
            }
            //イベント分岐。＝ボタンが押された時。
            else if(chkEvent(s)==2){
                if(inputStr.length()==0){           //もし入力値が入力されていなかったら０を表示させる
                    text1.setText("0");
                    text2.setText("0");
                }else{
                    text2.setText(inputStr);
                }
                state =3;                           //状態を結果表示中に移行させる
            }
        }

        //演算子入力中
        else if(state == 1){
            if(chkEvent(s)==0){
                inputStr = s;
                text1.setText(inputStr);
                state = 2;
            }else if(chkEvent(s)==1){
                op = s;
                text1.setText(op);
                state = 1;
            }else if(chkEvent(s)==2){
                result = calculation(aNum,aNum,op);
                text2.setText(result);
                state = 3;
            }
        }
        //B入力中
        else if(state == 2){
            if(chkEvent(s)==0){
                inputStr += s;
                text1.setText(inputStr);
                state = 2;
            }else if(chkEvent(s)==1){
                bNum = inputStr;
                result = calculation(aNum,bNum,op);
                text1.setText(op);
                text2.setText(result);
                aNum = result;
                op = s;
                state = 2;
            }else if(chkEvent(s)==2){
                bNum = inputStr;
                result = calculation(aNum,bNum,op);
                text2.setText(result);
                aNum = result;
                state = 3;
            }
        }
        //＝が入力されたとき
        else if(state == 3){
            if(chkEvent(s)==0){
                inputStr = s;
                text1.setText(inputStr);
                state=0;
            }else if(chkEvent(s)==1){
                aNum = result;
                op=s;
                text1.setText(op);
                text2.setText(result);
                state=1;
            }else if(chkEvent(s)==2){
                if(aNum.length()==0||bNum.length()==0){
                    text1.setText(0);
                }else{
                    result = calculation(aNum,bNum,op);
                    text2.setText(result);
                    aNum = result;
                }
                state =3;
            }
        }

    }

    private int chkEvent(String s){                                     //起きたイベントのチェックする。
        if(s.equals("+")||s.equals("-")||s.equals("×")||s.equals("÷")){ //演算子なら状態1。stringなのでequals
            return 1;
        }else if (s.equals("=")){                                       // =なら状態2.
            return 2;
        }else{                                                          //数値なら状態0
            return 0;
        }
    }

    private String calculation(String a,String b,String ope){           // 演算をしてString型を返すところ。calculationの関数の定義をする
        float anum = Float.parseFloat(a);                               // aをanumとして。
        float bnum = Float.parseFloat(b);                               // bをbnumとして。
        float res = 0;

        if(ope.equals("+")){
            res = anum + bnum;
        }else if(ope.equals("-")){
            res = anum - bnum;
        }else if(ope.equals("×")){
            res = anum * bnum;
        }else if(ope.equals("÷")){
            res = anum / bnum;
        }
        return Float.toString(res);                                     //Float型からStringにしてresを返す
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
}
