package cares.innostark.com.cares;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cares.innostark.com.cares.Models.SubHireGroups;

public class HireGroupDetailActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView vehicleList;
    ArrayList<SubHireGroups> list;
    VehicleAdapter adapter;
    String hiregrpname,tarifftypecode;
    Bundle car_api_params;
    int pos_in_list;
    Double total_standard_charge;
    TextView totalCharges,perDayCharges;
    private boolean isLoaded;
    private ProgressDialog dialog;
    ConnectionDetector cd;
    SubHireGroups shg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_group_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_tool_bar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cd=new ConnectionDetector(this);

        Intent i=getIntent();             //getting intent from the previous activity
        car_api_params=i.getExtras();        //getting bundle from previous activity

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();                  // setting the color of the status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(android.R.color.black));
        }

        vehicleList=(ListView) findViewById(R.id.vehicle_list);
        list=i.getExtras().getParcelableArrayList("SubHireGroup");
        hiregrpname=i.getExtras().getString("hiregrpname");
        adapter= new VehicleAdapter(HireGroupDetailActivity.this,list,hiregrpname);
        vehicleList.setOnItemClickListener(HireGroupDetailActivity.this);
        vehicleList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed()
    {
        this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long viewId = view.getId();

        if (viewId == R.id.getCharge) {             //getting the id of the imagebutton which is clicked in hire_vehicles_detail inflated in vehcileAdapter
            if(!(cd.isConnectingToInternet()))
            {
                Toast.makeText(getApplicationContext(),"No Internet Access",Toast.LENGTH_SHORT).show();
            }
            else
                getVehicleCharges(position);
        }
        else if(viewId == R.id.checkout) {
            //Toast.makeText(getApplicationContext(), "here" + id, Toast.LENGTH_SHORT).show();
            if(!(cd.isConnectingToInternet()))
            {
                Toast.makeText(getApplicationContext(),"No Internet Access",Toast.LENGTH_SHORT).show();
            }
            else
                getSelectedCarInfoAndMoveToStep3(position);

        }

    }

    private void getSelectedCarInfoAndMoveToStep3(int position) {
        SubHireGroups s= list.get(position);
        car_api_params.putParcelable("SubHireGroup",s);
        Intent in=new Intent(this,BookingStep3.class);
        in.putExtras(car_api_params);
        this.finish();
        startActivity(in);
    }

    private void getVehicleCharges(int position) {

        new GetVehicleCharge().execute(String.valueOf(position));
    }

    private class GetVehicleCharge extends AsyncTask<String, String, String>
    {
        String url="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            if (!isLoaded) {
                dialog = new ProgressDialog(HireGroupDetailActivity.this);
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
                dialog.show();

            }

        }

        @Override
        protected String doInBackground(String... params) {
            pos_in_list=Integer.parseInt(params[0]);
            shg= list.get(pos_in_list);
            String sh_id=shg.getHireGroupDetailId();
            url+=Constants.getVehicleCharge;
            String content= HttpURLConnect.getVehicleCharge(url,sh_id,car_api_params.getString("OperationId"),car_api_params.getString("EndDateTime"),car_api_params.getString("StartDateTime"),car_api_params.getString("DomainKey"));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();

            JSONObject rateObj;

            if(s != null)
            {
                try
                {
                    rateObj= new JSONObject(s);
                    shg.setAllowedMileage(rateObj.getString("AllowedMileage"));
                    shg.setExcessMileage(rateObj.getString("ExcessMileage"));
                    shg.setExcessMileageRate(rateObj.getString("ExcessMileageRate"));
                    shg.setTariffType(rateObj.getString("TariffTypeCode"));
                    shg.setStandardRt(rateObj.getString("StandardRate"));
                    Double totalCharges= Double.valueOf(rateObj.getString("TotalStandardCharge"));
                    DecimalFormat newFormat = new DecimalFormat("#");
                    Double formatted_total_charge =  Double.valueOf(newFormat.format(totalCharges));
                    shg.setTotalStandardCharge(String.valueOf(formatted_total_charge));
                    total_standard_charge= formatted_total_charge;
                }
                catch(Exception e)
                {

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            View v=getViewByPosition(pos_in_list,vehicleList);           // getting the view so that standard rates could be set from where the call came to get the charges
            final LinearLayout layout=(LinearLayout) v.findViewById(R.id.linearLayout2);
            final Button getCharge=(Button) v.findViewById(R.id.getCharge);
            final Button checkout=(Button) v.findViewById(R.id.checkout);

            totalCharges=(TextView) v.findViewById(R.id.total_vehicle_charge);
            Double per_day_charge=0.0;
            if(!shg.getTariffType().equals("H")) {
                per_day_charge = (total_standard_charge) / car_api_params.getInt("NoOfDays");
            }
            else
            {
                per_day_charge = total_standard_charge;
            }
            int i = per_day_charge.intValue();
            if(!(total_standard_charge.toString().equals("")))
            {
                totalCharges.setText("SAR " + total_standard_charge.intValue()+ " ("+i+"/day)");              //setting the total charges of the selected vehicle
                checkout.setVisibility(View.VISIBLE);
                getCharge.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
            }
            else
            {
                getCharge.setVisibility(View.VISIBLE);
            }

        }

    }

    //this function returns a listview item at position= pos from the given listView when an item in it is clicked
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
