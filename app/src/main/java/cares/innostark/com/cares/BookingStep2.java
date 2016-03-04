package cares.innostark.com.cares;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cares.innostark.com.cares.Mappings.HireGroupsMapper;
import cares.innostark.com.cares.Models.HireGroups;
import cares.innostark.com.cares.Models.SubHireGroups;

public class BookingStep2 extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView hireGroupList;
    String url="";
    Bundle car_api_params;
    private boolean isLoaded;
    private ProgressDialog dialog;
    CustomAdapter adapter;
    ConnectionDetector cd;
    public ArrayList<HireGroups> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_step2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_tool_bar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //getting the bundle from BookingStep1 activity
        //siteProperties=getIntent().getExtras();
        car_api_params=getIntent().getExtras();
        //Toast.makeText(BookingStep2.this,car_api_params.getString("PickUpCityId1")+"\n"+car_api_params.getString("PickUpCityId2"),Toast.LENGTH_LONG).show();
        //url+= Constants.baseUrl + Constants.getParentHireGroups;

        cd = new ConnectionDetector(this);
        if (!(cd.isConnectingToInternet())) {
            internetStatus();

        }
        else
            getCarsData();

        Window window = this.getWindow();                // setting the color of the status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(android.R.color.black));

        hireGroupList=(ListView) findViewById(R.id.car_list);

    }

//    @Override
//    public void onRestart() {
//        super.onRestart();
//        //When BACK BUTTON is pressed, the activity on the stack is restarted
//        //So here we are calling the api again "most inmportantly when we are coming after turning the wifi ON "
//        getCarsData();
//    }

    private void getCarsData() {
        isLoaded=false;
        //url+=Constants.baseUrl+Constants.getSiteContents;
        new GetHireGroupData().execute();
    }

    public void internetStatus() {
        // Internet connection is not present
        // Ask user to connect to Internet
        final CharSequence[] options = {"Connect to WIFI", "Connect to Mobile Data"};

        AlertDialog.Builder builder = new AlertDialog.Builder(BookingStep2.this);
        builder.setTitle("No Internet Connection");
        builder.setIcon(R.drawable.fail);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Connect to WIFI")) {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);

                } else if (options[item].equals("Connect to Mobile Data")) {
                    Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                    startActivity(intent);
                }
            }
        });
        builder.show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final HireGroups i = items.get(position);
        String hiregrpname=i.getHireGroupName();
        final ArrayList<SubHireGroups> sh=i.getSubHireGroup();
        //Toast.makeText(BookingStep2.this, "Hello", Toast.LENGTH_SHORT).show();
        Intent in= new Intent(this,HireGroupDetailActivity.class);
        in.putExtra("SubHireGroup",sh);
        in.putExtra("hiregrpname",hiregrpname);
        in.putExtras(car_api_params);
        startActivity(in);
    }

    private class GetHireGroupData extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            if (!isLoaded) {
                dialog = new ProgressDialog(BookingStep2.this);
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
                dialog.show();

            }

        }
        @Override
        protected String doInBackground(String... params) {
            url+=Constants.baseUrl+Constants.getParentHireGroups;
            String key=car_api_params.getString("key");
            String domainkey=car_api_params.getString("DomainKey");
            String dropoffcityid= car_api_params.getString("DropOffCityId2");
            String returnlocid= car_api_params.getString("ReturnLocationId2");
            String pickupcityid= car_api_params.getString("PickUpCityId1");
            String outlocid= car_api_params.getString("OutLocationId1");
            String enddatetime=car_api_params.getString("EndDateTime");
            String startdatetime= car_api_params.getString("StartDateTime");
            String content = HttpURLConnect.getHireGroupsData(url,key,domainkey,dropoffcityid,returnlocid,pickupcityid,outlocid,enddatetime,startdatetime);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();

            JSONArray hireGroups;

            if(s != null)
            {
                try
                {
                    hireGroups= new JSONArray(s);
                    items= HireGroupsMapper.MapHireGroups(hireGroups);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
            //Toast.makeText(BookingStep2.this, items.toString() , Toast.LENGTH_LONG).show();
            adapter= new CustomAdapter(BookingStep2.this,items);
            hireGroupList.setOnItemClickListener(BookingStep2.this);
            hireGroupList.setAdapter(adapter);

        }
    }

}
