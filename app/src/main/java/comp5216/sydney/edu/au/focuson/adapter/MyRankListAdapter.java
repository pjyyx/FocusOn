package comp5216.sydney.edu.au.focuson.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import comp5216.sydney.edu.au.focuson.R;
import comp5216.sydney.edu.au.focuson.model.User;

/**
 * The type My rank list adapter.
 */
public class MyRankListAdapter  extends ArrayAdapter<User>  {


    /**
     * Instantiates a new My rank list adapter.
     *
     * @param context  the context
     * @param itemList the item list
     */
    public MyRankListAdapter(Context context, ArrayList<User> itemList) {
        super(context, 0, itemList);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Set the item to the data in given position
        User user = getItem(position);
        // Check if the view exist, if not, inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rank_items, parent, false);
        }
        // Find the view position for the data
        TextView userName = (TextView) convertView.findViewById(R.id.name_text);
        TextView userScore = (TextView) convertView.findViewById(R.id.score_text);
        ImageView userAvatar = (ImageView) convertView.findViewById(R.id.avatar_image);
        //Check if the data in given position is null, If not, set the data item to view
        if(user != null) {
            userName.setText(user.getNickName());
            userScore.setText(Long.toString(user.getPoint()));
            Glide.with(getContext()).load(user.getAvatar()).into(userAvatar);
            //userAvatar.setImageURI(Uri.parse((String) user.getAvatar()));
        }
        else{
            Log.i("MyRankListAdapter", "getView->" +
                    "item get NullPointerException on position:" + position);
        }

        // Return the view to render on phone
        return convertView;
    }
}
