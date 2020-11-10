package comp5216.sydney.edu.au.focuson.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import comp5216.sydney.edu.au.focuson.R;
import comp5216.sydney.edu.au.focuson.model.Record;

/**
 * The type My record list adapter.
 */
public class MyRecordListAdapter extends ArrayAdapter<Record> {
    /**
     * Instantiates a new My record list adapter.
     *
     * @param context  the context
     * @param itemList the item list
     */
    public MyRecordListAdapter(Context context, ArrayList<Record> itemList) {
        super(context, 0, itemList);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Set the item to the data in given position
        Record record = getItem(position);
        // Check if the view exist, if not, inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_items, parent, false);
        }
        // Find the view position for the data
        TextView datetime_text = (TextView) convertView.findViewById(R.id.datetime_text);
        TextView FocusTime = (TextView) convertView.findViewById(R.id.FocusTime);
        TextView pickup = (TextView) convertView.findViewById(R.id.pickup);
        TextView screen_change = (TextView) convertView.findViewById(R.id.screen_change);
        TextView pause = (TextView) convertView.findViewById(R.id.pause);
        TextView score = (TextView) convertView.findViewById(R.id.score);

        //Check if the data in given position is null, If not, set the data item to view
        if(record != null) {
            datetime_text.setText(record.getTime());
            FocusTime.setText(record.getFocusTime());
            pickup.setText(String.valueOf(record.getPickUp()));
            screen_change.setText(String.valueOf(record.getScreenChange()));
            pause.setText(String.valueOf(record.getPause()));
            score.setText(String.valueOf(record.getscore()));
            //userAvatar.setImageURI(Uri.parse((String) user.getAvatar()));
        }
        else{
            Log.i("MyRecordListAdapter", "getView->" +
                    "item get NullPointerException on position:" + position);
        }

        // Return the view to render on phone
        return convertView;
    }
}
