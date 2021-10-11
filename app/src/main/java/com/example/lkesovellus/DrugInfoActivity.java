package com.example.lkesovellus;

import static android.app.AlarmManager.RTC_WAKEUP;
import static com.example.lkesovellus.MainActivity.EXTRA;
import static java.lang.Math.round;
import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

public class DrugInfoActivity extends AppCompatActivity {

    private TextView drugName;
    private TextView drugAmount;
    private TextView drugPrice;
    private int taken;
    int infoAmountOfDrug;
    double infoPriceOfDrug;
    private ProgressBar amountProgress;
    private int i;
    private Button addButton;
    private EditText number;
    public static final String SHARED_PREFS = "sharedprefs";
    public static final String VALUE = "Amount taken";
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_info);

        drugName = findViewById(R.id.drugNameTextView);
        drugAmount = findViewById(R.id.drugAmountTextView);
        drugPrice = findViewById(R.id.drugPriceTextView);
        amountProgress = findViewById(R.id.amountProgressBar);

        Bundle b = getIntent().getExtras();
        int i = b.getInt(EXTRA, 0);

        final int[] drugAmountEditable = {Global.getInstance().getDrugs().get(i).getDrugAmount()};
        int drugAmountStatic = Global.getInstance().getDrugs().get(i).getDrugAmount();
        double infoPriceOfDrug = Global.getInstance().getDrugs().get(i).getDrugPrice();

        DecimalFormat moneyFormat = new DecimalFormat("0.00");

        drugName.setText(Global.getInstance().getDrugs().get(i).getDrugName());
        drugAmount.setText("Annoksia: " + ((drugAmountEditable[0]) - taken) + "/" + drugAmountStatic);
        drugPrice.setText(infoPriceOfDrug + "€, Hinta/kpl: " + moneyFormat.format(infoPriceOfDrug / drugAmountStatic) + "€");
        amountProgress.setMax(drugAmountStatic);
        amountProgress.setProgress(drugAmountEditable[0] - taken);

        Button addButton = (Button) findViewById(R.id.takeDrugButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    if (drugAmountEditable[0] > 0) {
                        drugAmountEditable[0]--;
                        taken++;
                        drugAmount.setText("Annoksia: " + ((drugAmountEditable[0]) + "/" + drugAmountStatic));
                        amountProgress.setProgress(drugAmountEditable[0]);
                        Toast.makeText(getApplicationContext(), Global.getInstance().getDrugs().get(i).getDrugName() +
                                " otettu", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Lääkkeet loppu!", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        Button btSetAlarm2 = findViewById(R.id.btSetAlarm2);

        btSetAlarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Muistutus asetettu", Toast.LENGTH_SHORT).show();
                number = findViewById(R.id.inputHour);
                int hours = Integer.parseInt(number.getText().toString());

                Intent intent = new Intent(DrugInfoActivity.this, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(DrugInfoActivity.this, 0, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                long buttonClickTime = System.currentTimeMillis();
                long dayInMillisecond = 1000 * hours;

                alarmManager.set(RTC_WAKEUP, buttonClickTime + dayInMillisecond, pendingIntent);
            }
        });

        Button btCancelAlarm2 = findViewById(R.id.btCancelAlarm2);

        btCancelAlarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Muistutus poistettu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DrugInfoActivity.this, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(DrugInfoActivity.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }
        });
    }
}
