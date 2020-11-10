package comp5216.sydney.edu.au.focuson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * The type Monitor activity.
 */
public class MonitorActivity extends Activity {
    private static final int ST_SEARCH = 9000;

    /**
     * The User name text.
     */
    EditText user_name_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        user_name_text = findViewById(R.id.user_name_text);

    }

    /**
     * Search.
     *
     * @param view the view
     */
    public void search(View view) {
        Intent i=new Intent(MonitorActivity.this,MonitoringActivity.class);
        i.putExtra("email",user_name_text.getText().toString());
        startActivity(i);
    }


    /**
     * Cancel.
     *
     * @param view the view
     */
    public void cancel(View view) {
        finish();
    }

    /**
     * On rank click.
     *
     * @param view the view
     */
    public void onRankClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(MonitorActivity.this, RankingActivity.class);
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
        Intent intent = new Intent(MonitorActivity.this, AchievementActivity.class);
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
        Intent intent = new Intent(MonitorActivity.this, MonitorActivity.class);
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
        Intent intent = new Intent(MonitorActivity.this, SettingsActivity.class);
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
        Intent intent = new Intent(MonitorActivity.this, MainActivity.class);
        // Brings up the edit activity
        finish();
        startActivity(intent);
    }

}
