package comp5216.sydney.edu.au.focuson;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import comp5216.sydney.edu.au.focuson.adapter.MyRecordListAdapter;
import comp5216.sydney.edu.au.focuson.model.Record;

/**
 * The type Monitoring activity.
 */
public class MonitoringActivity extends Activity {
    /**
     * The M list view.
     */
    ListView mListView;
    /**
     * The Items.
     */
    ArrayList<Record> items;
    /**
     * The Email.
     */
    String email;
    /**
     * The My adapter.
     */
    MyRecordListAdapter MyAdapter;
    private FirebaseFirestore mFirestore;

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve_record);
        // Reference the "listView" variable to the id "lstView" in the layout
        mListView = findViewById(R.id.achieve_ist);
        email = getIntent().getStringExtra("email");
        //Read from the database
        items = new ArrayList<>();
        getList(this);

    }

    /**
     * Gets list.
     *
     * @param context the context
     */
    public void getList(final Context context) {
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Record").whereEqualTo("email",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document :
                                    Objects.requireNonNull(task.getResult())) {
                                Map<String, Object> temp = document.getData();
                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat simpleDateFormat =
                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                //如果它本来就是long类型的,则不用写这一步
                                long lt = Long.valueOf(temp.get("timestamp").toString());
                                Date date = new Date(lt);
                                String time = simpleDateFormat.format(date);
                                String FocusTime = Objects.
                                        requireNonNull(temp.get("FocusTime")).toString();
                                int Pause = Integer.parseInt(Objects.
                                        requireNonNull(temp.get("pause")).toString());
                                int ScreenChange = Integer.parseInt(Objects.
                                        requireNonNull(temp.get("ScreenChange")).toString());
                                int PickUp = Integer.parseInt(Objects.
                                        requireNonNull(temp.get("PickUpMobile")).toString());
                                int score = Integer.parseInt(Objects.
                                        requireNonNull(temp.get("point")).toString());
                                Record tempRecord = new Record(time, FocusTime,
                                        Pause, ScreenChange, PickUp, score);
                                items.add(tempRecord);
                                Log.d("TAG", document.getId() + " => " +
                                        items.toString());


                            }
                            //Setup the view with customise adapter
                            MyAdapter = new MyRecordListAdapter(context, items);
                            mListView.setAdapter(MyAdapter);
                        }
                        else {
                            Log.d("TAG", "Error getting documents: ",
                                    task.getException());
                        }
                    }
                });
    }


    /**
     * On rank click.
     *
     * @param view the view
     */
    public void onRankClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(MonitoringActivity.this, RankingActivity.class);
        // Brings up the edit activity
        finish();
        startActivity(intent);
    }

    /**
     * On achieve click.
     *
     * @param view the view
     */
    public void onAchieveClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(MonitoringActivity.this,
                AchievementActivity.class);
        // Brings up the edit activity
        finish();
        startActivity(intent);
    }

    /**
     * On monitor click.
     *
     * @param view the view
     */
    public void onMonitorClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(MonitoringActivity.this, MonitorActivity.class);
        // Brings up the edit activity
        finish();
        startActivity(intent);
    }

    /**
     * On setting click.
     *
     * @param view the view
     */
    public void onSettingClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(MonitoringActivity.this, SettingsActivity.class);
        // Brings up the edit activity
        finish();
        startActivity(intent);
    }

    /**
     * On home click.
     *
     * @param view the view
     */
    public void onHomeClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(MonitoringActivity.this, MainActivity.class);
        // Brings up the edit activity
        finish();
        startActivity(intent);
    }


}