package cares.innostark.com.cares;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import cares.innostark.com.cares.Models.BookingModel;
import cares.innostark.com.cares.Models.ModelStep1;
import cares.innostark.com.cares.Models.SubHireGroups;
import cares.innostark.com.cares.Models.UserInfo;

public class BookingStep3 extends AppCompatActivity {

    EditText fname,lname,address,email,phone,dob;
    Button submit;
    ImageButton show_booking_info;
    private boolean isLoaded;
    private ProgressDialog dialog;
    Bundle car_api_params;
    Intent i;
    public ModelStep1 m;
    public BookingModel model;
    SubHireGroups s;
    UserInfo ui=new UserInfo();
    SharedPreferences prefs;
    SharedPreference sharedPreference;
    public ArrayList<BookingModel> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booking_step3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_tool_bar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreference = new SharedPreference();       // initializing SharedPreference class
        model= new BookingModel();                //initializing booking model object

        prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        i=getIntent();
        car_api_params=i.getExtras();
        m=car_api_params.getParcelable("ModelStep1Obj");        // getting the ModelStep1 object from the bundle
        s=car_api_params.getParcelable("SubHireGroup");        // getting the subhiregroup object from the bundle
        show_booking_info=(ImageButton) findViewById(R.id.show_order_info);
        show_booking_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookingDialog();
            }
        });

        Window window = this.getWindow();                  // setting the color of the status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(android.R.color.black));

        fname=(EditText) findViewById(R.id.first_name);
        fname.setBackground(null);               // to remove the underlining of the edittexts
        fname.setText(prefs.getString("firstName",fname.getText().toString()));
        lname=(EditText) findViewById(R.id.last_name);
        lname.setBackground(null);               // to remove the underlining of the edittexts
        lname.setText(prefs.getString("lastName",lname.getText().toString()));
        address=(EditText) findViewById(R.id.contact_address);
        address.setBackground(null);               // to remove the underlining of the edittexts
        address.setText(prefs.getString("address",address.getText().toString()));
        email=(EditText) findViewById(R.id.contact_email);
        email.setBackground(null);               // to remove the underlining of the edittexts
        email.setText(prefs.getString("email",email.getText().toString()));
        phone=(EditText) findViewById(R.id.contact_num);
        phone.setBackground(null);               // to remove the underlining of the edittexts
        phone.setText(prefs.getString("phone",phone.getText().toString()));
        dob=(EditText) findViewById(R.id.date_of_birth);
        dob.setBackground(null);               // to remove the underlining of the edittexts
        dob.setText(prefs.getString("birthDate",dob.getText().toString()));

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, -20);
                int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(BookingStep3.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        dob.setText("" + selectedmonth + "/" + selectedday + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date Of Birth:");
                mDatePicker.show();
            }
        });

        submit=(Button) findViewById(R.id.enter_payment_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()) {
                    initializeBookingModel();
                    saveBookingInfo();
                }
            }
        });

    }

    private void initializeBookingModel() {
        model.setPickUpLocation(m.getPickUpLocation());
        model.setDropLocation(m.getDropLocation());
        model.setPickUpDateTime(car_api_params.getString("StartDateTime"));
        model.setDropDateTime(car_api_params.getString("EndDateTime"));
        model.setTotalCharge(s.getTotalStandardCharge());
        model.setVehicleMake(s.getVehicleMake());
        model.setVehicleModel(s.getVehicleModel());
        model.setVehicleYear(s.getModelYear());
    }


    private void showBookingDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //dialogBuilder.setTitle("Booking Details");
        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Booking Details");
        title.setAllCaps(true);
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        dialogBuilder.setCustomTitle(title);

        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.booking_details_dialog, null);
        dialogBuilder.setView(dialogView);
        //dialogView.setBackgroundResource(R.color.wallet_hint_foreground_holo_dark);

        TextView pickup_loc = (TextView) dialogView.findViewById(R.id.pickup_loc_value);
        pickup_loc.setText(m.getPickUpLocation());

        TextView drop_loc = (TextView) dialogView.findViewById(R.id.drop_loc_value);
        drop_loc.setText(m.getDropLocation());

        TextView pickup_dtTime = (TextView) dialogView.findViewById(R.id.pickup_datetime_value);
        pickup_dtTime.setText(car_api_params.getString("StartDateTime"));

        TextView drop_dtTime = (TextView) dialogView.findViewById(R.id.drop_datetime_value);
        drop_dtTime.setText(car_api_params.getString("EndDateTime"));

        TextView vehicleMake = (TextView) dialogView.findViewById(R.id.vehicle_make);
        vehicleMake.setText(s.getVehicleMake());
        TextView vehicleModel = (TextView) dialogView.findViewById(R.id.vehicle_model);
        vehicleModel.setText(s.getVehicleModel());
        TextView vehicleYear = (TextView) dialogView.findViewById(R.id.vehicle_year);
        vehicleYear.setText(s.getModelYear());
        TextView totalCharge = (TextView) dialogView.findViewById(R.id.total_charge);
        totalCharge.setText(s.getTotalStandardCharge());
        ImageView vehicleImage = (ImageView) dialogView.findViewById(R.id.vehicle_image);
        Picasso.with(getApplicationContext()).load(s.getImageUrl()).into(vehicleImage);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    public Boolean validateForm()
    {
        SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
        Boolean flag=true;
        if(fname.getText().toString().isEmpty())
        {
            flag=false;
            fname.setError("Required");
        }

        else {
            fname.setError(null);
            ui.setFirstName(fname.getText().toString());
            editor.putString("firstName",fname.getText().toString() );
        }
        if(lname.getText().toString().isEmpty())
        {
            flag=false;
            lname.setError("Required");
        }
        else
        {
            lname.setError(null);
            ui.setLastName(lname.getText().toString());
            editor.putString("lastName",lname.getText().toString() );
        }

        if(address.getText().toString().isEmpty())
        {
            flag=false;
            address.setError("Required");
        }
        else{
            address.setError(null);
            ui.setAddress(address.getText().toString());
            editor.putString("address",address.getText().toString() );
        }

        if(!isEmailValid(email.getText().toString()))
        {
            flag=false;
            email.setError("Invalid Email");
        }
        else{
            email.setError(null);
            ui.setEmail(email.getText().toString());
            editor.putString("email",email.getText().toString() );
        }

        if(phone.getText().toString().isEmpty())
        {
            flag=false;
            phone.setError("Required");
        }
        else{
            phone.setError(null);
            ui.setPhoneNo(phone.getText().toString());
            editor.putString("phone",phone.getText().toString() );
        }

        if(dob.getText().toString().isEmpty())
        {
            flag=false;
            dob.setError("Required");
        }
        else{
            dob.setError(null);
            ui.setDateOfBirth(dob.getText().toString());
            editor.putString("birthDate",dob.getText().toString() );
        }
        ui.setCustomerType(0);
        ui.setInsurancesTotal(0.0);
        ui.setServicesItemsTotal(0.0);
        editor.putString("CheckVal","Ok");
        editor.commit();
        return flag;
    }

    public Boolean isEmailValid(String target)
    {
        return !(target == null || target.isEmpty()) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void saveBookingInfo() {
        new SaveBooking().execute();
    }

    private class SaveBooking extends AsyncTask<String, String, String> {
        String url = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            if (!isLoaded) {
                dialog = new ProgressDialog(BookingStep3.this);
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
                dialog.show();

            }

        }

        @Override
        protected String doInBackground(String... params) {

            url += Constants.saveBooking;
            String content = HttpURLConnect.saveBookingData(url,Double.valueOf(car_api_params.getString("PickUpCityId1")),Double.valueOf(car_api_params.getString("DropOffCityId2")),Double.valueOf(car_api_params.getString("OperationId")),car_api_params.getString("StartDateTime"),car_api_params.getString("EndDateTime"),Long.valueOf(car_api_params.getString("DomainKey")),Double.valueOf(s.getHireGroupDetailId()),Double.valueOf(s.getStandardRt()),s.getTariffType(),ui);

            return content;
        }
        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();

            if(s == null)
            {
                showErrorDialog();
            }
            if(s != null)
            {
                showBookingId(s);
            }
        }
    }

    private void showErrorDialog() {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);

        dialog.setMessage("Dear Mr/Mrs. "+ ui.getFirstName() + ",\nAn error has occurred. Please try again!");
        dialog.setCancelable(false);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showBookingId(String s) {
        s = s.replace("\"", "");

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.booking_response, null);
        dialogBuilder.setView(dialogView);
        //dialogView.setBackgroundResource(R.color.wallet_hint_foreground_holo_dark);

        TextView text1 = (TextView) dialogView.findViewById(R.id.textView1);
        text1.setText("Dear Mr/Mrs. " + ui.getFirstName());
        TextView text2= (TextView) dialogView.findViewById(R.id.textView2);
        TextView text3 = (TextView) dialogView.findViewById(R.id.textView3);
        text3.setText(s);

        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        dialogBuilder.setNegativeButton("Ok1", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                sharedPreference.addBooking(BookingStep3.this, model);
                Intent i = new Intent(BookingStep3.this,BookingListActivity.class);
                startActivity(i);
                finish();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
