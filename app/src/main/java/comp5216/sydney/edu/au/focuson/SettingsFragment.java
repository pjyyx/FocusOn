package comp5216.sydney.edu.au.focuson;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static comp5216.sydney.edu.au.focuson.SettingsActivity.KEY_PREF_NAME_TEXT;

/**
 * The type Settings fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private static final int MY_PERMISSIONS_REQUEST_OPEN_CAMERA = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    public String photoFileName = "photo.jpg";
    /**
     * The Avatar pref.
     */
    Preference avatarPref, /**
     * The Logout pref.
     */
    logoutPref;
    /**
     * The Name edit text pref.
     */
    EditTextPreference nameEditTextPref;
    /**
     * The User name.
     */
    String userName;
    /**
     * The Tag.
     */
    String TAG = "FRAGMENT DB";
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private CollectionReference mUserRef;
    private FirebaseFirestore mFirestore;
    private DocumentReference docRef;
    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged
                        (SharedPreferences sharedPreferences, String s) {
                    String newUserName = sharedPreferences.getString
                            (KEY_PREF_NAME_TEXT, "en");

                    switch (s) {
                        case "name":
                            updateFirestoreUsers("nickName", nameEditTextPref.getText());
                            Toast.makeText(getActivity(), "UPDATED",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };
    private File file;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        avatarPref = (Preference) findPreference("avatar");
        avatarPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                onTakePhotoClick(getView());
                return true;
            }
        });

        logoutPref = (Preference) findPreference("logout");
        logoutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                FirebaseAuth.getInstance().signOut();
                //LoginManager.getInstance().logOut();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                return true;
            }
        });

        nameEditTextPref = (EditTextPreference) findPreference("name");

        initFirestore();
        initPreference();
    }

    /**
     * Init preference.
     */
    public void initPreference() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
                        userName = Objects.requireNonNull(document.get("nickName")).toString();
                        nameEditTextPref.setText(userName);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void updateFirestoreUsers(String key, String value) {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        //Add current user to Firestore
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserRef = mFirestore.collection("Users");
        Map<String, Object> user = new HashMap<>();
        user.put(key, value);
        mUserRef.document(mFirebaseUser.getEmail())
                .update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void onTakePhotoClick(View v) {
        // Check permissions
        if (ActivityCompat.checkSelfPermission
                (getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CAMERA_PERMISSION);
            onTakePhotoClick(getView());
        } else {
            // create Intent to take a picture and return control to the calling application
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // set file name
            photoFileName = "avatar_" + userName + ".jpg";
            // Create a photo file reference
            Uri file_uri = getFileUri(photoFileName, 0);
            // Add extended data to the intent
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
            startActivityForResult(intent, MY_PERMISSIONS_REQUEST_OPEN_CAMERA);
        }
    }

    public Uri getFileUri(String fileName, int type) {
        Uri fileUri = null;
        try {
            String typestr = "/images/"; //default to images type
            if (type == 1) {
                typestr = "/videos/";
            } else if (type != 0) {
                typestr = "/audios/";
            }
            // Get safe storage directory depending on type
            File mediaStorageDir = new
                    File(getActivity().getExternalFilesDir(null).getAbsolutePath(),
                    typestr + fileName);
            // Create the storage directory if it does not exist
            if (!mediaStorageDir.getParentFile().exists() &&
                    !mediaStorageDir.getParentFile().mkdirs()) {
                Log.d(TAG, "failed to create directory");
            }
            // Create the file target for the media based on filename
            file = new File(mediaStorageDir.getParentFile().getPath() + File.separator +
                    fileName);
            // Wrap File object into a content provider, required for API >= 24
            // See https://guides.codepath.com/android/Sharing-Content-withIntents#sharing-files
            // -with-api-24-or-higher
            if (Build.VERSION.SDK_INT >= 24) {
                fileUri = FileProvider.getUriForFile(
                        getActivity().getApplicationContext(),
                        "comp5216.sydney.edu.au.focuson.fileProvider", file);
            } else {
                fileUri = Uri.fromFile(mediaStorageDir);
            }
        } catch (Exception ex) {
            Log.d("getFileUri", ex.getStackTrace().toString());
        }
        return fileUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_PERMISSIONS_REQUEST_OPEN_CAMERA) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(file.getAbsolutePath());

                Toast.makeText(getActivity(), file.getAbsolutePath(),
                        Toast.LENGTH_LONG).show();
                String fileAbsolutePath = file.getAbsolutePath();
                updateFirestoreUsers("avatar", fileAbsolutePath);
                Toast.makeText(getActivity(), "New avatar saved",
                        Toast.LENGTH_SHORT).show();

            } else { // Result was a failure
                Toast.makeText(getActivity(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}