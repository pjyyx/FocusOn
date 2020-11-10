package comp5216.sydney.edu.au.focuson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.Objects;

/**
 * The type Settings activity.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * The constant KEY_PREF_DARK_SWITCH.
     */
    public static final String
            KEY_PREF_DARK_SWITCH = "dark",
    /**
     * The Key pref deep switch.
     */
    KEY_PREF_DEEP_SWITCH = "deep",
    /**
     * The Key pref name text.
     */
    KEY_PREF_NAME_TEXT = "name",
    /**
     * The Key pref language list.
     */
    KEY_PREF_LANGUAGE_LIST = "language",
    /**
     * The Tag.
     */
    TAG = "DB";
    private SharedPreferences sharedPreferences;
    private String userName;

    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private CollectionReference mUserRef;
    private FirebaseFirestore mFirestore;
    private DocumentReference docRef;

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    boolean darkPref = sharedPreferences.getBoolean
                            (KEY_PREF_DARK_SWITCH, false);
                    String languagePref = sharedPreferences.getString
                            (KEY_PREF_LANGUAGE_LIST, "en");

                    switch (s) {
                        case "dark":
                            if (darkPref) {
                                AppCompatDelegate.
                                        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            } else {
                                AppCompatDelegate.
                                        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            }
                            break;
                        case "deep":
                            // TODO: Deep focus mode!
                            break;
                        case "language":
                            assert languagePref != null;
                            if (languagePref.equals("zh")) {
                                Locale localeZh = new Locale("zh");
                                setLocaleOnCreate(localeZh);
                                restartActivity();
                            } else {
                                Locale localeEn = new Locale("en");
                                setLocaleOnCreate(localeEn);
                                restartActivity();
                            }
//                            Toast.makeText(SettingsActivity.this,
//                                    "Restart application to make effect",
//                                    Toast.LENGTH_SHORT).show();
//                            finish();
                            break;
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initPreference();
        initFirestore();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }


    /**
     * Init preference.
     */
    public void initPreference() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.
                registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    private void initFirestore() {
        //Add mAuth for sign in with email, facebook, google.
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        //Add current user to Firestore
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserRef = mFirestore.collection("Users");
        docRef = mUserRef.document(Objects.requireNonNull(mFirebaseUser.getEmail()));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "DocumentSnapshot nick name data: " +
                                document.get("nickName"));
                        userName = document.get("nickName").toString();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void setLocaleOnCreate(Locale locale) {
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }


}