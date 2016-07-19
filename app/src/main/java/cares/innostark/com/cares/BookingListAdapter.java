package cares.innostark.com.cares;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cares.innostark.com.cares.Models.BookingModel;

/**
 * Created by bcm on 7/18/2016.
 */
public class BookingListAdapter extends ArrayAdapter{
    ArrayList<BookingModel> result;
    Context c;
    LayoutInflater inflater;
    BookingModel booking;

    public BookingListAdapter(Context context, ArrayList<BookingModel> list) {
        super(context, 0,list);
        c=context;
        result=list;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v=convertView;

        booking = result.get(position);
        if(booking != null) {
            v = inflater.inflate(R.layout.user_list_detail_item, null);
            if (position % 2 == 1) {
                v.setBackgroundColor(Color.parseColor("#EEEEEE"));
            } else {
                v.setBackgroundColor(Color.parseColor("#DCDCDC"));
            }
            final TextView name = (TextView) v.findViewById(R.id.name);
            final TextView city = (TextView) v.findViewById(R.id.city);
            final CheckBox select = (CheckBox) v.findViewById(R.id.checkbox);
            //final TextView age = (TextView) v.findViewById(R.id.age);

            if (name != null) {
                name.setText(user.getName());
            }
            if (city != null) {
                city.setText(user.getCity());
            }
            if (select != null) {
                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(),"Hello",Toast.LENGTH_SHORT).show();
                        if(select.isChecked()) {
                            ((ListView) parent).performItemClick(v, position, 0);
                        }
                    }
                });
            }
        }
        return v;
    }

}
