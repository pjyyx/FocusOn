package comp5216.sydney.edu.au.focuson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import comp5216.sydney.edu.au.focuson.model.Pet;
import comp5216.sydney.edu.au.focuson.model.User;
import comp5216.sydney.edu.au.focuson.adapter.MyRankListAdapter;

/**
 * The type Ranking activity.
 */
public class RankingActivity extends Activity {
    private ListView mListView;
    private TextView mTextView;
    private ArrayList<User> items;
    private MyRankListAdapter MyAdapter;

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        // Reference the "listView" variable to the id "lstView" in the layout
        mListView = findViewById(R.id.rank_list);
        mTextView = findViewById(R.id.ur_score_text);
        //Read from the database
        mTextView.setText("Sorry, you are not top 10.");
        items = new ArrayList<>();
        getList(this);

    }

    @Override
    protected void onStop(){
        super.onStop();
        finish();
    }

    /**
     * Gets list.
     *
     * @param context the context
     */
    public void getList(final Context context) {
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Users").orderBy("point",
                Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser mFirebaseUser = FirebaseAuth.
                                    getInstance().
                                    getCurrentUser();
                            assert mFirebaseUser != null;
                            String myEmail = mFirebaseUser.getEmail();
                            for (QueryDocumentSnapshot document : Objects.
                                    requireNonNull(task.getResult())) {
                                Map<String, Object> temp = document.getData();
                                String name = Objects.
                                        requireNonNull(temp.get("nickName")).toString();
                                String email = Objects.
                                        requireNonNull(temp.get("email")).toString();
                                String point = Objects.
                                        requireNonNull(temp.get("point")).toString();
                                assert myEmail != null;
                                if(myEmail.equals(email)){
                                    mTextView.setText("Congratulations, you are in top 10.");
                                }
                                User tempUser = new User(email, name, Long.parseLong(point),
                                        null, null);
                                items.add(tempUser);
                                Log.d("TAG", document.getId() + " => " +
                                        items.toString());
                            }

                        } else {
                            Log.d("TAG", "Error getting documents: ",
                                    task.getException());
                        }
                        //Setup the view with customise adapter
                        MyAdapter = new MyRankListAdapter(context, items);
                        mListView.setAdapter(MyAdapter);
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
        Intent intent = new Intent(RankingActivity.this, RankingActivity.class);
        // Brings up the edit activity
        startActivity(intent);
    }

    /**
     * On achieve click.
     *
     * @param view the view
     */
    public void onAchieveClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(RankingActivity.this, AchievementActivity.class);
        // Brings up the edit activity
        startActivity(intent);
    }

    /**
     * On monitor click.
     *
     * @param view the view
     */
    public void onMonitorClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(RankingActivity.this, MonitorActivity.class);
        // Brings up the edit activity
        startActivity(intent);
    }

    /**
     * On setting click.
     *
     * @param view the view
     */
    public void onSettingClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(RankingActivity.this, SettingsActivity.class);
        // Brings up the edit activity
        startActivity(intent);
    }

    /**
     * On home click.
     *
     * @param view the view
     */
    public void onHomeClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(RankingActivity.this, MainActivity.class);
        // Brings up the edit activity
        startActivity(intent);
    }




}