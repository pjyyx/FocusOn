package comp5216.sydney.edu.au.focuson.dialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import comp5216.sydney.edu.au.focuson.R;
import comp5216.sydney.edu.au.focuson.model.Equipment;
import comp5216.sydney.edu.au.focuson.utils.UserManger;

/**
 * The type Equip dialog.
 */
public class EquipDialog extends Dialog {
    private final Context context;
    private TextView tvName;
    private ImageView ivImage;
    private TextView tvBasepower;

    /**
     * Instantiates a new Equip dialog.
     *
     * @param context the context
     */
    public EquipDialog(Context context) {
        super(context, R.style.DialogStyle);
        this.context = context;
        initView();
    }

    private void initView() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.commodity_dialog, null);
        setContentView(inflate);
        tvName = inflate.findViewById(R.id.tv_name);
        ivImage = inflate.findViewById(R.id.iv_image);
        Window window = getWindow();
        if (null != window) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
        }
    }

    /**
     * Up data.
     *
     * @param equipment the equipment
     */
    public void upData(Equipment equipment) {
        if (tvName != null && equipment != null) {
            tvName.setText(equipment.getName());
            //tvBasepower.setText("basepower:"+equipment.getBasePower());
            Glide.with(context).load(equipment.getImage()).into(ivImage);
            UserManger.saveUser(equipment);
        }
    }
}