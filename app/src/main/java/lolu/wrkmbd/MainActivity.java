package lolu.wrkmbd;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity /*implements View.OnClickListener*/ {



    MySQLiteDatabase mydb;
    private TextView logview;
    private EditText sendtext;
    private LinearLayout grap;


    private BtRaspberry bt = null;

    private long lastTime = 0;
    float f;
    ArrayList<Float> dati = new ArrayList<Float>();
    Timer timer;
    TimerTask timerTask;
    final Handler handlera = new Handler();


    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            MbedModel mbdTmp=new MbedModel();
            String data = msg.getData().getString("receivedData");
            int g=Integer.valueOf(data);
            mbdTmp.setValueMbed(g);
            float f= (float) Integer.valueOf(data);



            // Affichage de donnée
            long t = System.currentTimeMillis();
            if(t-lastTime > 100) {// Pour éviter que les messages soit coupés
               // logview.append("\n");
               // dati.add(f);
                lastTime = System.currentTimeMillis();
            }
            dati.add(f);
            mydb.addValueMbed(mbdTmp);

           // logview.append(data);
        }
    };

    final Handler handlerStatus = new Handler() {
        public void handleMessage(Message msg) {
            int co = msg.arg1;
            if(co == 1) {
                //logview.append("Connected\n");
            } else if(co == 2) {
               // logview.append("Disconnected\n");
            }
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb= new MySQLiteDatabase(this);
       final Graph graphView = (Graph) findViewById(R.id.barchart);


        bt = new BtRaspberry(handlerStatus, handler);
        bt.connect();
        findViewById(R.id.idStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.sendData("1");

            }
        });
        findViewById(R.id.idStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.sendData("0");

            }
        });


        startTimer(graphView);






    }

   public void startTimer(final Graph graphView) {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask(graphView);

        timer.schedule(timerTask, 0, 5000);
    }

    public void initializeTimerTask(final Graph graphView) {
        timerTask = new TimerTask() {
            public void run() {
                handlera.post(new Runnable() {
                    public void run() {
                        ArrayList<Float> data = new ArrayList<Float>();
                       data=dati;
                        graphView.setData(data);
                    }
                });
            }
        };
    }

}
