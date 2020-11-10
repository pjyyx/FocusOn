package comp5216.sydney.edu.au.focuson;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.focuson.model.Equipment;
import comp5216.sydney.edu.au.focuson.utils.FileAssetsUtils;

/**
 * Worn equipment
 */
public class SyntheticEquipmentActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.won_equipment);


    }
    /**
     * On rank click.
     *
     * @param view the view
     */
    public void onRankClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(SyntheticEquipmentActivity.this,
                RankingActivity.class);
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
        Intent intent = new Intent(SyntheticEquipmentActivity.this,
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
        Intent intent = new Intent(SyntheticEquipmentActivity.this,
                MonitorActivity.class);
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
        Intent intent = new Intent(SyntheticEquipmentActivity.this,
                SettingsActivity.class);
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
        Intent intent = new Intent(SyntheticEquipmentActivity.this,
                MainActivity.class);
        // Brings up the edit activity
        finish();
        startActivity(intent);
    }
}