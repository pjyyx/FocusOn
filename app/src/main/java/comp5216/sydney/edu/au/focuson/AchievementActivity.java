package comp5216.sydney.edu.au.focuson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import comp5216.sydney.edu.au.focuson.adapter.MyRankListAdapter;
import comp5216.sydney.edu.au.focuson.model.User;

/**
 * The type Achievement activity.
 */
public class AchievementActivity extends Activity {


    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);
    }

    @Override
    protected void onStop(){
        super.onStop();
        finish();
    }


    /**
     * On rank click.
     *
     * @param view the view
     */
    public void onRankClick(View view) {
        Intent intent = new Intent(AchievementActivity.this, RankingActivity.class);
        startActivity(intent);
    }

    /**
     * On achieve click.
     *
     * @param view the view
     */
    public void onAchieveClick(View view) {
        Intent intent = new Intent(AchievementActivity.this, AchievementActivity.class);
        startActivity(intent);
    }

    /**
     * On monitor click.
     *
     * @param view the view
     */
    public void onMonitorClick(View view) {
        Intent intent = new Intent(AchievementActivity.this, MonitorActivity.class);
        startActivity(intent);
    }

    /**
     * On setting click.
     *
     * @param view the view
     */
    public void onSettingClick(View view) {
        Intent intent = new Intent(AchievementActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * On home click.
     *
     * @param view the view
     */
    public void onHomeClick(View view) {
        Intent intent = new Intent(AchievementActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * On collection click.
     *
     * @param view the view
     */
    public void onCollectionClick(View view) {
        Intent intent = new Intent(AchievementActivity.this, UserEquipmentActivity.class);
        startActivity(intent);
    }

    /**
     * On record click.
     *
     * @param view the view
     */
    public void onRecordClick(View view) {
        Intent intent = new Intent(AchievementActivity.this, RecordActivity.class);
        startActivity(intent);
    }

}