package comp5216.sydney.edu.au.focuson;

import android.annotation.SuppressLint;
import org.apache.http.util.EncodingUtils;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;

import comp5216.sydney.edu.au.focuson.dialog.EquipDialog;
import comp5216.sydney.edu.au.focuson.model.Equipment;
import comp5216.sydney.edu.au.focuson.model.Pet;
import comp5216.sydney.edu.au.focuson.model.User;

/**
 * The type Timer activity.
 */
public class TimerActivity extends BaseActivity {
    private Button mStart;
    private java.util.Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private timeHandler mtimeHandler;
    private SensorManager sensorManager;
    private PowerManager pm;
    private PowerManager.WakeLock mWakelock;
    private long shakeTime;//抬手时间
    private long showTime;//平放手机时间
    private int total=0;
    private Sensor accelerometer;
    private EquipDialog equipDialog;

    private int totalSec;
    private int FocusHour;
    private int FocusMin;
    private int numOfChange=0;
    private int last = 0;
    private int numOfPause = 0;
    private static TextView mHours_Tv, mMinutes_Tv, mSeconds_Tv;
    /**
     * The Hour.
     */
    int hour = 0;
    /**
     * The Min.
     */
    int min = 0;
    /**
     * The Score.
     */
    int score = 0;
    private String content;
    private String pet_file;

    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private CollectionReference mUserRef;
    private FirebaseFirestore mFirestore;

    private ImageView bgImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        pm = (PowerManager)getSystemService(POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "myapp:mywakelocktag");

        mStart = (Button) findViewById(R.id.start);
        mtimeHandler = new timeHandler(this);
        mHours_Tv = (TextView) findViewById(R.id.hours_tv);
        mMinutes_Tv = (TextView) findViewById(R.id.minutes_tv);
        mSeconds_Tv = (TextView) findViewById(R.id.seconds_tv);

        mStart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String str = mStart.getText().toString();
                if(str.equals("Start")){
                    if (mHours_Tv.getText().toString().equals("00")
                            &&mMinutes_Tv.getText().toString().equals("00")
                            &&mSeconds_Tv.getText().toString().equals("00")){
                        String text="Please set the time first!";
                        Toast.makeText( TimerActivity.this, text, Toast.LENGTH_SHORT ).show();
                    }
                    else {
                        mStart.setText("Pause");
                        totalSec = (hour*60 + min)*60;
                        FocusHour = hour;
                        FocusMin = min;
                        last =1;
                        startRun();
                    }

                }
                else if(str.equals("Pause")){
                    mStart.setText("Restart");
                    endRun();
                    numOfPause++;
                }
                else if(str.equals("Restart")){
                    mStart.setText("Pause");
                    last =1;
                    startRun();
                }
            }
        });
        checkDark();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer == null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    //重力感应监听
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            int medumValue = 14;
            //判断是否抬手
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                shakeTime = System.currentTimeMillis();
            }

            //判断是否平放手机
            if(9 < z  && -2 < x && x < 2 && -2 < y && y < 2){
                showTime = System.currentTimeMillis();
                if(0 < showTime - shakeTime && showTime - shakeTime < 500){
                    total++;
                    shakeTime = 0;
                    mWakelock.acquire();
                    mWakelock.release();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }
    };

    @Override
    protected void onDestroy() {
        if(equipDialog != null) {
            equipDialog.dismiss();
        }
        super.onDestroy();
        sensorManager.unregisterListener(sensorEventListener);
    }

    /**
     * Time.
     *
     * @param v the v
     */
    public void Time(View v){
//        Calendar calendar=Calendar.getInstance();
        new TimePickerDialog( this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                mHours_Tv.setText(getTv(hour));
                mMinutes_Tv.setText(getTv(min));
            }
        }
                ,0
                ,0
                , true).show();
    }


    private void startRun(){
        mTimer = new java.util.Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                count();
                totalSec--;
                Message message = mtimeHandler.obtainMessage();
                message.obj = totalSec;
                message.what = 88888;
                mtimeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,1000);
    }

    /**
     * Count.
     */
    public void count(){
        if(isAppOnForeground()){
            last =1;
        }
        else if(!isAppOnForeground()){
            if(last==1) numOfChange++;
            last=2;
        }
    }

    private void endRun(){
        mTimer.cancel();
    }

    private static String getTv(int l){
        if(l>=10){
            return l+"";
        }else{
            return "0"+l;//If it's less than 10, we have a zero in front of it.
        }
    }

    /**
     * Back.
     *
     * @param v the v
     */
    public void back(View v){
        if(mTimer!=null) {
            mTimer.cancel();
        }
        if(mtimeHandler!=null){
            mtimeHandler.removeCallbacksAndMessages(null);
        }
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    /**
     * Exit.
     */
    public void exit(){

        if(mTimer!=null) {
            mTimer.cancel();
        }
        if(mtimeHandler!=null){
            mtimeHandler.removeCallbacksAndMessages(null);
        }
        scoring();
        String[] getEq =getEquipment();
        Equipment get = new Equipment(getEq[0],Integer.parseInt(getEq[1]),
                Integer.parseInt(getEq[2]),
                Integer.parseInt(getEq[3]),
                getEq[4]);
        if (equipDialog == null) {
            equipDialog = new EquipDialog(TimerActivity.this);
        }
        equipDialog.upData(get);
        equipDialog.show();
        initFirestore(get);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        }, 5000);//3秒后执行Runnable中的run方法

    }


    private void scoring(){
        if(FocusMin>3){
            score = 100;
        }
        else if(FocusHour==0&&FocusMin>2){
            score = 75;
        }
        else if(FocusHour==0&&FocusMin<=2&&FocusMin>1){
            score = 50;
        }
        else {
            score = 25;
        }

        score = score - numOfPause - total * 5 - numOfChange * 10;
        if(score<0) score =0;
    }

    private String[] getEquipment(){
        String fileName;
        if(score<=25){
            fileName = "level1.json";
            pet_file = "android.resource://comp5216.sydney.edu.au.focuson/drawable/d0";
        }
        else if(score>25&&score<=50){
            fileName = "level2.json";
            pet_file = "android.resource://comp5216.sydney.edu.au.focuson/drawable/d1";
        }
        else if(score>50&&score<=75){
            fileName = "level3.json";
            pet_file = "android.resource://comp5216.sydney.edu.au.focuson/drawable/d2";
        }
        else{
            fileName = "level4.json";
            pet_file = "android.resource://comp5216.sydney.edu.au.focuson/drawable/d3";
        }
        HashMap<Integer,String[]> Equipment = doParseJson(fileName);
        Random random = new Random();
        int a = random.nextInt(Equipment.size());
        String[] e = Equipment.get(a);
        return e;
    }

    private HashMap<Integer,String[]> doParseJson(String fileName){
        HashMap<Integer,String[]> equip = new HashMap<Integer,String[]>();
        try {
            // 读取assets目录里的test.json文件，获取字节输入流
            InputStream is = getResources().getAssets().open(fileName);
            // 获取字节输入流长度
            int length = is.available();
            // 定义字节缓冲区
            byte[] buffer = new byte[length];
            // 读取字节输入流，存放到字节缓冲区里
            is.read(buffer);
            // 将字节缓冲区里的数据转换成utf-8字符串
            content = EncodingUtils.getString(buffer, "utf-8");
            // 关闭输入流
            is.close();
            // 基于content字符串创建Json数组
            JSONArray jsonArray = new JSONArray(content);


            // 遍历Json数组
            for (int i = 0; i < jsonArray.length(); i++) {
                // 通过下标获取json数组元素——Json对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // 对Json对象按键取值
                String[] eq = {jsonObject.getString("name"),
                        jsonObject.getString("basepower"),
                        jsonObject.getString("level"),
                        jsonObject.getString("type"),
                        jsonObject.getString("image")};
                equip.put(i,eq);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return equip;
    }

    private void initFirestore(Equipment Equip) {
        //Add mAuth for sign in with email, facebook, google.
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        //Add current user to Firestore
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mUserRef = mFirestore.collection("Record");
        Map<String, Object> user = new HashMap<>();
        user.put("email", mFirebaseUser.getEmail());
        user.put("nickName", mFirebaseUser.getDisplayName());
        user.put("timestamp",System.currentTimeMillis());
        user.put("FocusTime", getTv(FocusHour)+":"+getTv(FocusMin));
        user.put("pause", numOfPause);
        user.put("PickUpMobile",total);
        user.put("ScreenChange",numOfChange);
        user.put("point", score);
        user.put("Equipment",Equip);
        mUserRef.add(user);

        DocumentReference washingtonRef = mFirestore.
                collection("Users").
                document(mFirebaseUser.getEmail());
        washingtonRef.update("point", FieldValue.increment(score));
        washingtonRef.update("pet.image", pet_file);
    }


    /**
     * The type Time handler.
     */
    public static class timeHandler extends Handler {

        /**
         * The Main activity weak reference.
         */
        public final WeakReference<TimerActivity> mainActivityWeakReference;

        /**
         * Instantiates a new Time handler.
         *
         * @param activity the activity
         */
        public timeHandler(TimerActivity activity) {
            mainActivityWeakReference = new WeakReference<TimerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TimerActivity timerActivity = mainActivityWeakReference.get();
            switch (msg.what) {
                case 88888:
                    int value = (int)msg.obj;
                    mHours_Tv.setText(getTv((value/60)/60));
                    mMinutes_Tv.setText(getTv((value/60)%60));
                    mSeconds_Tv.setText(getTv((value)%60));
                    if (value==0) {
                        timerActivity.exit();
                    }
                    else{
                        timerActivity.startRun();
                    }
            }
        }
    }

    private void checkDark(){
        bgImageView=(ImageView)findViewById(R.id.timer_bg);
        PreferenceManager.setDefaultValues(this,R.xml.root_preferences,false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPreferences.getBoolean
                (SettingsActivity.KEY_PREF_DARK_SWITCH, false);
        if(switchPref){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            bgImageView.setBackgroundResource(R.drawable.bg_dark);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}