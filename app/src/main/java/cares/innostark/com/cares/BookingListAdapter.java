package cares.innostark.com.cares;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
            v = inflater.inflate(R.layout.booking_list_item, null);
            if (position % 2 == 1) {
                v.setBackgroundColor(Color.parseColor("#EEEEEE"));
            } else {
                v.setBackgroundColor(Color.parseColor("#DCDCDC"));
            }
            final TextView date_time = (TextView) v.findViewById(R.id.date_time);
            final TextView charge = (TextView) v.findViewById(R.id.charge);
            final TextView vehicle_info= (TextView) v.findViewById(R.id.vehicle_info);
            final TextView booking_id= (TextView) v.findViewById(R.id.booking_id);
            final ImageView vehicle_image= (ImageView) v.findViewById(R.id.vehicle_image);
            final ImageButton reminder= (ImageButton) v.findViewById(R.id.reminder);

            if (date_time != null) {
                date_time.setText(booking.getPickUpDateTime() + " - " + booking.getDropDateTime());
            }
            if (charge != null) {
                String currency_label = getContext().getString(R.string.currency_label);
                charge.setText(currency_label + " " + booking.getTotalCharge());
            }if (booking_id != null) {
                booking_id.setText(booking.getBookingId());
            }
            if (vehicle_info != null) {
                vehicle_info.setText(booking.getVehicleMake() + " " + booking.getVehicleModel() + " " + booking.getVehicleYear());
            }
            if(vehicle_image != null) {
                Picasso.with(c).load(booking.getImageUrl()).into(vehicle_image);
            }

            reminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {        //setting on click listener on getcharge button to get vehicle charge
                    ((ListView) parent).performItemClick(v, position , 0);
                    //this sends a callback to the previous Activity's OnItemClick on which the listView is being shown
                }
            });
        }
        return v;
    }

}
