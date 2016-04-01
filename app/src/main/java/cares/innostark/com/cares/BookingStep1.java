package cares.innostark.com.cares;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cares.innostark.com.cares.Models.ModelStep1;
import cares.innostark.com.cares.Models.OperationWorkPlaces;

public class BookingStep1 extends AppCompatActivity {

    ImageButton loc_icon1,loc_icon2,info_button1,info_button2,calendar1,calendar2,pickup_clock,drop_clock;
    EditText pickup_loc,pickup_date,pickup_time,drop_loc,drop_date,drop_time;
    Button submit_step1;
    Calendar c;
    Bundle siteProperties;
    ArrayList<OperationWorkPlaces> list;
    String cityId,opWorkPlId;
    Bundle car_api_params;
    ModelStep1 model=new ModelStep1();
    String message,location;
    OperationWorkPlaces obj1,obj2;                // obj1 for pickup info and obj2 for dropoff info

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_step1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_tool_bar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //getting the bundle from mainactivity
        siteProperties=getIntent().getExtras();

        car_api_params=new Bundle();

        Window window = this.getWindow();                // setting the color of the status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(android.R.color.black));

        //calendar instance
        c = Calendar.getInstance();

        // setting pickup information

        pickup_loc=(EditText) findViewById(R.id.pickup_loc_field);
        pickup_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //selectLocation();
                Intent i=new Intent(BookingStep1.this,SelectLocation.class);
                String value="pickup";               //passing this extra value so that activity can guess where to set the location
                i.putExtra("val",value);             // i.e pickup field or drop field
                i.putExtras(siteProperties);
                startActivityForResult(i,0);
            }
        });

        loc_icon1=(ImageButton) findViewById(R.id.loc_icon1);             // location image to show pickup location on map
        //loc_icon1.setEnabled(false);
        loc_icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pickup_loc.getText().toString().isEmpty())
                {
                    Toast.makeText(BookingStep1.this,"Select Pickup Location first",Toast.LENGTH_SHORT).show();
                }
                else if(obj1.getLatitude() == null && obj1.getLongitude() == null)
                {
                    message="Can't find this location on map.";
                    Toast.makeText(BookingStep1.this,message,Toast.LENGTH_SHORT).show();
                }

                else {
                    Intent i = new Intent(BookingStep1.this, MapsActivity.class);
                    String callingAct = "BookingStep1";
                    i.putExtra("loc_info", obj1);
                    i.putExtra("callingActivity", callingAct);
                    startActivity(i);
                }
            }
        });

        pickup_date=(EditText) findViewById(R.id.pickup_date);
        calendar1=(ImageButton) findViewById(R.id.calendar1);            // calendar image for pickup date
        calendar1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(BookingStep1.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        pickup_date.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Pick-up Date");
                mDatePicker.show();
            }
        });


        int mYear = c.get(Calendar.YEAR);                   //setting current date to the pickup date field
        int mMonth = c.get(Calendar.MONTH)+1 ;
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String date = mDay + "/"+mMonth +"/" + mYear;
        pickup_date.setText(date);
        pickup_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(BookingStep1.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        pickup_date.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Pick-up Date");
                mDatePicker.show();
            }
        });
        pickup_time=(EditText) findViewById(R.id.pickup_time);
        pickup_time.setOnClickListener(new View.OnClickListener() {                // to show time picker for pickup time

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookingStep1.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute < 10)
                            pickup_time.setText( selectedHour + ":0" + selectedMinute);
                        else
                            pickup_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true); //Yes 24 hour time
                mTimePicker.setTitle("Select Pick-up Time");
                mTimePicker.show();

            }
        });

        pickup_clock=(ImageButton) findViewById(R.id.pickup_clock);              // clock dialog to get pickup time
        pickup_clock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookingStep1.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute < 10)
                            pickup_time.setText( selectedHour + ":0" + selectedMinute);
                        else
                            pickup_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true); //Yes 24 hour time
                mTimePicker.setTitle("Select Pick-up Time");
                mTimePicker.show();

            }
        });


        // pickup info ends here

        // setting drop info

        drop_loc=(EditText) findViewById(R.id.drop_loc_field);
        drop_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectLocation();
                Intent i=new Intent(BookingStep1.this,SelectLocation.class);
                String value="drop";                 //passing this extra value so that activity can guess where to set the location
                i.putExtra("val",value);             // i.e pickup field or drop field
                i.putExtras(siteProperties);
                startActivityForResult(i,0);
            }
        });

        loc_icon2=(ImageButton) findViewById(R.id.loc_icon2);                  // location image to set the drop location
        loc_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drop_loc.getText().toString().isEmpty())
                {
                    Toast.makeText(BookingStep1.this,"Select Drop-off Location first",Toast.LENGTH_SHORT).show();
                }
                else if(obj2.getLatitude() == null && obj2.getLongitude() == null)
                {
                    message="Can't find this location on map.";
                    Toast.makeText(BookingStep1.this,message,Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(BookingStep1.this, MapsActivity.class);
                    String callingAct = "BookingStep1";
                    intent.putExtra("loc_info", obj2);
                    intent.putExtra("callingActivity", callingAct);
                    startActivity(intent);
                }
            }
        });

        drop_date=(EditText) findViewById(R.id.drop_date);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        int dYear = cal.get(Calendar.YEAR);                   //setting current date to the drop date field
        int dMonth = cal.get(Calendar.MONTH)+1 ;
        int dDay = cal.get(Calendar.DAY_OF_MONTH);
        String d_date = dDay + "/"+dMonth +"/" + dYear;
        drop_date.setText(d_date);
                            // drop date field
        drop_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 2);
                int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(BookingStep1.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        drop_date.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Drop-off Date");
                mDatePicker.show();
            }
        });
        calendar2=(ImageButton) findViewById(R.id.calendar2);                  //calendar image for drop date
        calendar2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 2);
                int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(BookingStep1.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        drop_date.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Drop-off Date");
                mDatePicker.show();
            }
        });

        drop_time=(EditText) findViewById(R.id.drop_time);
        drop_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookingStep1.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute < 10)
                            drop_time.setText( selectedHour + ":0" + selectedMinute);
                        else
                            drop_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Drop-off Time");
                mTimePicker.show();

            }
        });

        drop_clock=(ImageButton) findViewById(R.id.drop_clock);                //clock image to set drop time
        drop_clock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookingStep1.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute < 10)
                            drop_time.setText( selectedHour + ":0" + selectedMinute);
                        else
                            drop_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Drop-off Time");
                mTimePicker.show();

            }
        });
        // drop info ends here

        // Initializing the submit button
        submit_step1=(Button) findViewById(R.id.show_offers_button);
        submit_step1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()) {
                    Intent i = new Intent(BookingStep1.this, BookingStep2.class);
                    car_api_params.putString("key",siteProperties.getString("SiteContentId"));
                    car_api_params.putString("URL",Constants.url);
                    car_api_params.putString("DomainKey",siteProperties.getString("UserDomainKey"));

                    String end_date_month=model.getDropDate().split("/")[1];
                    String end_date_day=model.getDropDate().split("/")[0];
                    String end_date_year=model.getDropDate().split("/")[2];
                    String EndDate=end_date_month+ "/"+ end_date_day+ "/" +end_date_year;
                    car_api_params.putString("EndDateTime",EndDate+" " +model.getDropTime());

                    String start_date_month=model.getPickUpDate().split("/")[1];
                    String start_date_day=model.getPickUpDate().split("/")[0];
                    String start_date_year=model.getPickUpDate().split("/")[2];
                    String StartDate=start_date_month+ "/"+ start_date_day+ "/" +start_date_year;
                    car_api_params.putString("StartDateTime",StartDate+" " +model.getPickUpTime());
                    i.putExtras(car_api_params);            // sending the params needed to call the API to get vehicles in car_api_params
                    i.putExtra("ModelStep1Obj",model);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //  if the result code is 1 then data will contain pickup location
        if(resultCode==1)
        {
            obj1=data.getParcelableExtra("loc_info");        //getting the selected pickup location object

            location=obj1.getLocationName();
            cityId=obj1.getCityId();
            opWorkPlId=obj1.getOperationWorkplaceId();
//            lat=obj1.getLatitude();                             // getting the lat/lng of the pickup location
//            lng=obj1.getLongitude();
            car_api_params.putString("PickUpCityId1",opWorkPlId);           // sending the api params to get vehicles
            car_api_params.putString("OutLocationId1",opWorkPlId);
            car_api_params.putString("OperationId",obj1.getOperationId());
            pickup_loc.setText(location);

        }
        //  if the result code is 2 then data will contain drop location
        else if(resultCode==2)
        {
            obj2=data.getParcelableExtra("loc_info");    //getting the selected drop location object

            location=obj2.getLocationName();
            //cityId=data.getStringExtra("cityId");                        // getting the city id of the drop location
            cityId=obj2.getCityId();
            //opWorkPlId=data.getStringExtra("opWorkPlId");                //getting the operation work place id of drop location
            opWorkPlId=obj2.getOperationWorkplaceId();
//            lat=obj2.getLatitude();                              // getting the lat/lng of the dropoff location
//            lng=obj2.getLongitude();
            car_api_params.putString("DropOffCityId2",opWorkPlId);           // sending the api params to get vehicles
            car_api_params.putString("ReturnLocationId2",opWorkPlId);
            drop_loc.setText(location);
        }
    }

    public Boolean validateForm()                      // validation of the step 1 form
    {

        Boolean flag=true;
        String errorString = "";
        if(pickup_loc.getText().toString().isEmpty())
        {
            flag=false;
            pickup_loc.setError("Required");
        }
        else {
            pickup_loc.setError(null);
            model.setPickUpLocation(pickup_loc.getText().toString());
        }
        if(drop_loc.getText().toString().isEmpty())
        {
            flag=false;
            drop_loc.setError("Required");
        }
        else {
            drop_loc.setError(null);
            model.setDropLocation(drop_loc.getText().toString());
        }
        if(pickup_time.getText().toString().isEmpty())
        {
            flag=false;
            pickup_time.setError("Required");
        }
        else {
            pickup_time.setError(null);
            model.setPickUpTime(pickup_time.getText().toString());
        }
        if(drop_time.getText().toString().isEmpty())
        {
            flag=false;
            drop_time.setError("Required");
        }
        else {
            drop_time.setError(null);
            model.setDropTime(drop_time.getText().toString());
        }
        if(pickup_date.getText().toString().isEmpty())
        {
            flag=false;
            pickup_date.setError("Required");
        }
        else {
            pickup_date.setError(null);
            model.setPickUpDate(pickup_date.getText().toString());
        }
        if(drop_date.getText().toString().isEmpty())
        {
            flag=false;
            drop_date.setError("Required");
        }
        else {
            drop_date.setError(null);
            model.setDropDate(drop_date.getText().toString());
        }
        if(!(drop_date.getText().toString().isEmpty()) && !(pickup_date.getText().toString().isEmpty()))
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            String datePick = pickup_date.getText().toString();
            String dateDrop=drop_date.getText().toString();

            int cur_Year = c.get(Calendar.YEAR);                   //setting current date to the pickup date field
            int cur_Month = c.get(Calendar.MONTH)+1 ;
            int cur_Day = c.get(Calendar.DAY_OF_MONTH);
            String currentDate = cur_Day + "/"+cur_Month +"/" + cur_Year;
            try {

                Date date = formatter.parse(datePick);
                Date date2 = formatter2.parse(dateDrop);
                Date cur_date=formatter2.parse(currentDate);

                if (date2.before(date)){
                    flag=false;
                    errorString+="- Drop-off Date Should Be Greater Than Pick-up Date.\n";
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder
                            .setTitle("Please Correct following error")
                            .setMessage(errorString)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                if(date.before(cur_date))
                {
                    flag=false;
                    errorString+="- Pick-up Date Should Be Greater Than Current Date.\n";
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder
                            .setTitle("Please Correct following error")
                            .setMessage(errorString)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                long diff = date2.getTime() - date.getTime();
                int dif= (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                //Toast.makeText(getApplicationContext(),dif,Toast.LENGTH_SHORT).show();
                car_api_params.putInt("NoOfDays",dif);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
