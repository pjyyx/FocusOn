package comp5216.sydney.edu.au.focuson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.focuson.adapter.UserEquipmentAdapter;
import comp5216.sydney.edu.au.focuson.dialog.EquipDialog;
import comp5216.sydney.edu.au.focuson.model.Equipment;
import comp5216.sydney.edu.au.focuson.utils.UserManger;

/**
 * user equipment
 */
public class UserEquipmentActivity extends Activity implements View.OnClickListener,
        BaseQuickAdapter.OnItemChildClickListener {
    private ArrayList<Equipment> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserEquipmentAdapter userEquipmentAdapter;
    private EquipDialog commodityDialog;
    private Equipment datum1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_equipment);
        recyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.bt_synthetic_equipment).setOnClickListener(this);
        TextView tvTitle = findViewById(R.id.tv_title);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        userEquipmentAdapter = new UserEquipmentAdapter();
        recyclerView.setAdapter(userEquipmentAdapter);
        userEquipmentAdapter.setNewData(getData());
        userEquipmentAdapter.setOnItemChildClickListener(this);
    }

    private List<Equipment> getData() {
        return UserManger.getUser();
    }

    @Override
    public void onClick(View v) {
        list.clear();
        for (Equipment datum : userEquipmentAdapter.getData()) {
            if (datum.isCheck()) {
                list.add(datum);
            }
        }
        if (ObjectUtils.isNotEmpty(list)) {
            startActivity(new Intent(UserEquipmentActivity.this,
                    SyntheticEquipmentActivity.class));
        } else {
            ToastUtils.showShort("No wearing equipment yet");
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean isHas = false;
        list.clear();
        Equipment item = userEquipmentAdapter.getItem(position);
        for (Equipment datum : userEquipmentAdapter.getData()) {
            if (datum.isCheck()) {
                list.add(datum);
            }
        }
        for (Equipment datum :list) {
            if (datum.getType() == item.getType()) {
                isHas = true;
                datum1 = datum;
            }
        }
        if (isHas) {
            if (datum1.getName().equals(item.getName())){
                item.setCheck(false);
            }else {
                ToastUtils.showShort("The same type of equipment has been selected");
            }
        } else {
            item.setCheck(true);
        }
        userEquipmentAdapter.setData(position, item);
    }

    /**
     * On rank click.
     *
     * @param view the view
     */
    public void onRankClick(View view) {
        //Create intent for EditToDoItemActivity. Ready to transfer data
        Intent intent = new Intent(UserEquipmentActivity.this,
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
        Intent intent = new Intent(UserEquipmentActivity.this,
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
        Intent intent = new Intent(UserEquipmentActivity.this,
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
        Intent intent = new Intent(UserEquipmentActivity.this,
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
        Intent intent = new Intent(UserEquipmentActivity.this,
                MainActivity.class);
        // Brings up the edit activity
        finish();
        startActivity(intent);
    }
}