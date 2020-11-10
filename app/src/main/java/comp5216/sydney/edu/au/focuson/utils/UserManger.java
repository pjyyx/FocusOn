package comp5216.sydney.edu.au.focuson.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.focuson.model.Equipment;

import static android.content.Context.MODE_PRIVATE;

/**
 * The type User manger.
 */
public class UserManger {
    /**
     * Save user.
     *
     * @param user the user
     */
//save equipment
    public static void saveUser(Equipment user) {
        List<Equipment> list = new ArrayList<>();
        if (getUser() == null) {
            list.add(user);
        } else {
            List<Equipment> array = getUser();
            array.add(user);
            list.addAll(array);
        }
        SharedPreferences.Editor editor = ActivityUtils.getTopActivity().getSharedPreferences("AlterSamplesList", MODE_PRIVATE).edit();
        String s = GsonUtils.toJson(list);
        editor.putString("alterSampleJson", s);
        editor.apply();
    }


    /**
     * Gets user.
     *
     * @return the user
     */
// gain equipments
    public static List<Equipment> getUser() {
        SharedPreferences preferences = ActivityUtils.getTopActivity().getSharedPreferences("AlterSamplesList", MODE_PRIVATE);
        String json = preferences.getString("alterSampleJson", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Equipment>>() {
            }.getType();
            return gson.fromJson(json, type);
        } else {
            return null;
        }
    }

    /**
     * Clear.
     */
//clear
    public static void clear() {
        SharedPreferences preferences = ActivityUtils.getTopActivity().getSharedPreferences("AlterSamplesList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
