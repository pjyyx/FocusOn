package comp5216.sydney.edu.au.focuson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import android.app.UiModeManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import comp5216.sydney.edu.au.focuson.model.Pet;
import comp5216.sydney.edu.au.focuson.model.User;
import comp5216.sydney.edu.au.focuson.viewmodel.MainActivityViewModel;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mViewModel;
    private ImageView bgImageView;
    private SharedPreferences sharedPreferences;
    private FirebaseUser mFirebaseUser;
    private static final int RC_SIGN_IN = 9001;
    private static final int ST_TIMER = 9999;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View model
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initView();
        initPreference();
        testPreference();
        initFirestore();
    }

    private void initFirestore() {

        //Add mAuth for sign in with email, facebook, google.
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        //Add current user to Firestore
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mFirebaseUser!=null) {
            String email = mFirebaseUser.getEmail();
            assert email != null;
            DocumentReference mUserRef=mFirestore.collection("Users").document(email);
            mUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        assert document != null;
                        ImageView imageview= findViewById(R.id.image_view);
                        if (document.exists()) {
                            Object temp = Objects.requireNonNull(document.getData()).get("pet");
                            Map entry = (Map)temp;
                            String cell = Objects.requireNonNull(entry.get("image")).toString();

                            Glide.with(MainActivity.this).load(cell).into(imageview);
                            Log.e("MainActivity", "DocumentSnapshot data: " +
                                    document.getData());
                        } else {
                            Log.e("MainActivity", "No such document");
                            CollectionReference users = mFirestore.
                                    collection("Users");
                            String name = mFirebaseUser.getDisplayName();
                            String email = mFirebaseUser.getEmail();
                            Uri photoUrl = mFirebaseUser.getPhotoUrl();
                            long point = 0;
                            String petName = "JohnDoe";
                            String image = "android.resource://comp5216.sydney.edu.au.focuson/drawable/d0";
                            Pet tempPet = new Pet(petName, null,  point, image);
                            assert photoUrl != null;
                            User temp = new User(email, name, point, tempPet,null);
                            assert email != null;
                            users.document(email).set(temp);
                        }
                    } else {
                        Log.e("MainActivity", "get failed with ", task.getException());
                    }
                }
            });
        }else{
            startSignIn();
        }
    }


    @Override
    protected void onResume(){
        super.onResume();
    }

    private void initView(){
        // Assign views
        Button btnStart = (Button) findViewById(R.id.start_btn);
        btnStart.setOnClickListener(buttonListener);
        bgImageView=(ImageView)findViewById(R.id.image_view);
    }


    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.start_btn) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivityForResult(intent, ST_TIMER);
            }
        }
    };

    /**
     * On rank click.
     *
     * @param view the view
     */
    public void onRankClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(MainActivity.this, RankingActivity.class);
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
        Intent intent = new Intent(MainActivity.this, AchievementActivity.class);
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
        Intent intent = new Intent(MainActivity.this, MonitorActivity.class);
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
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
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
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        // Brings up the edit activity
        startActivity(intent);

    }

//    public void onSettingClick(View view){
//        startActivity(new Intent(MainActivity.this,SettingsActivity.class));
//    }

    private void initPreference(){
        PreferenceManager.setDefaultValues(this,R.xml.root_preferences,false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Apply settings
        boolean switchPref = sharedPreferences.getBoolean
                (SettingsActivity.KEY_PREF_DARK_SWITCH, false);
        String languagePref = sharedPreferences.getString
                (SettingsActivity.KEY_PREF_LANGUAGE_LIST, "en");

        if(switchPref){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            bgImageView.setBackgroundResource(R.drawable.bg_dark);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        assert languagePref != null;
        if(languagePref.equals("zh")){
            Locale localeZh=new Locale("zh");
            setLocaleOnCreate(localeZh);
        }else {
            Locale localeEn=new Locale("en");
            setLocaleOnCreate(localeEn);
        }
    }

    private void testPreference(){
        Boolean switchPref = sharedPreferences.getBoolean
                (SettingsActivity.KEY_PREF_DARK_SWITCH, false);
        String textNamePref=sharedPreferences.
                getString(SettingsActivity.KEY_PREF_NAME_TEXT, "lang");

        Toast.makeText(this, "Current user: " + textNamePref,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets locale on create.
     *
     * @param locale the locale
     */
    public void setLocaleOnCreate(Locale locale) {
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }

    public void onStart() {
        super.onStart();
        // Start sign in if necessary
        if (shouldStartSignIn()) {
            startSignIn();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            mViewModel.setIsSigningIn(false);

            if (resultCode != RESULT_OK && shouldStartSignIn()) {
                startSignIn();
            }
        }
    }

    private boolean shouldStartSignIn() {
        return (!mViewModel.getIsSigningIn() &&
                FirebaseAuth.getInstance().getCurrentUser() == null);
    }

    //start sign in with 3 ways
    private void startSignIn() {
        // Sign in with FirebaseUI
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.FacebookBuilder().build()))
                .setIsSmartLockEnabled(false)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
        mViewModel.setIsSigningIn(true);
    }

    private void SignOut(){
        AuthUI.getInstance().signOut(this);
    }

}