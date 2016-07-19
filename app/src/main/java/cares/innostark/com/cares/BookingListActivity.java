package cares.innostark.com.cares;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import cares.innostark.com.cares.Models.BookingModel;

public class BookingListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView bookingList;
    ArrayList<BookingModel> list= new ArrayList<>();
    SharedPreference sharedPreference;
    BookingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();                  // setting the color of the status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(android.R.color.black));
        }

        sharedPreference = new SharedPreference();         // initializing SharedPreference class

        bookingList = (ListView) findViewById(R.id.booking_list);

        list = sharedPreference.getBookings(getApplicationContext());
        if(list != null && list.size() > 0)
        {
            Collections.reverse(list);
            adapter = new BookingListAdapter(BookingListActivity.this,list);
            bookingList.setOnItemClickListener(BookingListActivity.this);
            bookingList.setAdapter(adapter);
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long viewId = view.getId();
        BookingModel model = list.get(position);

        if (viewId == R.id.reminder) {             //getting the id of the imagebutton which is clicked in booking_list_item inflated in bookingListAdapter

            Calendar beginTime = Calendar.getInstance();
            try {
                beginTime.setTime(new SimpleDateFormat("MM/dd/yyyy hh:mm").parse(model.getPickUpDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar endTime = Calendar.getInstance();
            try {
                endTime.setTime(new SimpleDateFormat("MM/dd/yyyy hh:mm").parse(model.getDropDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Intent calIntent = new Intent(Intent.ACTION_INSERT);
            calIntent.setType("vnd.android.cursor.item/event");
            calIntent.putExtra(CalendarContract.Events.TITLE, "Car Coming to Pickup");
            calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, model.getPickUpLocation());
            //calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "A Chicken Roast on the Beach");

            calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
            calIntent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    beginTime.getTimeInMillis());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                    endTime.getTimeInMillis());

            startActivity(calIntent);
        }
    }
}
