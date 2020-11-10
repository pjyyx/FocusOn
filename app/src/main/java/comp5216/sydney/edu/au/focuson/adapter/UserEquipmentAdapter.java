package comp5216.sydney.edu.au.focuson.adapter;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import comp5216.sydney.edu.au.focuson.R;
import comp5216.sydney.edu.au.focuson.model.Equipment;

/**
 * The type User equipment adapter.
 */
public class UserEquipmentAdapter extends BaseQuickAdapter<Equipment, BaseViewHolder> {

    /**
     * Instantiates a new User equipment adapter.
     */
    public UserEquipmentAdapter() {
        super(R.layout.userequipment_item);
    }

    @SuppressLint({"CheckResult", "UseCompatLoadingForDrawables"})
    @Override
    protected void convert(BaseViewHolder helper, Equipment item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_basepower, "basepower:" + item.getBasePower());
        Glide.with(helper.itemView.getContext())
                .load(helper.itemView.getContext()
                        .getResources()
                        .getIdentifier(item.getImage(),
                                "drawable",
                                helper.itemView.getContext()
                                        .getPackageName()))
                .into((ImageView) helper.getView(R.id.iv_image));
        ImageView imageView = helper.getView(R.id.checkbox);
        imageView.setBackground(item.isCheck() ? helper.itemView.getContext().getResources().getDrawable(R.drawable.select_state_act) : helper.itemView.getContext().getResources().getDrawable(R.drawable.select_state_nor));
        helper.addOnClickListener(R.id.checkbox);
    }
}
