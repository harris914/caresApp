package cares.innostark.com.cares;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cares.innostark.com.cares.Mappings.OperationWorkPlaceMapper;
import cares.innostark.com.cares.Models.OperationWorkPlaces;

public class MainActivity extends Activity {

    ImageButton booking,available_cars,contact_us,location,special_offer;
    private ProgressDialog pDialog;
    private boolean isLoaded;
    Bundle siteProperties;
    ArrayList<String> siteContentList;
    ArrayList<OperationWorkPlaces> opWorkPlList;
    ConnectionDetector cd;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
        siteProperties=new Bundle();
        opWorkPlList=new ArrayList<OperationWorkPlaces>();

        siteContentList=new ArrayList<String>();

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();                  // setting the color of the status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(android.R.color.black));
        }
        // Initializing the connection detector
        cd = new ConnectionDetector(this);
        if (!(cd.isConnectingToInternet())) {
            internetStatus();

        }
        else
            getDataFromApi();         // getting the app data from the API

        booking=(ImageButton) findViewById(R.id.booking);
        available_cars=(ImageButton) findViewById(R.id.available_cars);
        contact_us=(ImageButton) findViewById(R.id.contact_us);
        location=(ImageButton) findViewById(R.id.location);
        special_offer=(ImageButton) findViewById(R.id.special_offer);

        booking.bringToFront();
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Booking clicked",Toast.LENGTH_SHORT).show();
                i=new Intent(MainActivity.this,BookingStep1.class);
                i.putExtras(siteProperties);
                startActivity(i);
            }
        });

        available_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Available Cars clicked",Toast.LENGTH_SHORT).show();
                i=new Intent(MainActivity.this,BookingStep1.class);
                i.putExtras(siteProperties);
                startActivity(i);
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage();
            }
        });

        // showing all the locations on the map, just like SelectLocation
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(MainActivity.this,MapsActivity.class);
                ArrayList<OperationWorkPlaces> workLocs = siteProperties.getParcelableArrayList("opWorkPlaces");
                i.putExtra("all_locations_list",workLocs);
                // setting it to 'SelectLocation' because showing all locations on the map just like in SelectLocation activity
                String callingActivity="SelectLocation";
                i.putExtra("callingActivity",callingActivity);
                i.putExtras(siteProperties);
                startActivity(i);
            }
        });
        special_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(MainActivity.this,BookingStep1.class);
                i.putExtras(siteProperties);
                startActivity(i);
            }
        });
    }

    private void openWebPage() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tajeercare.com/bookingdemo"));
        startActivity(browserIntent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //So here we are calling the api again "most inmportantly when we are coming after turning the wifi ON "
        getDataFromApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void internetStatus() {
        // Internet connection is not present
        // Ask user to connect to Internet
        final CharSequence[] options = {"Connect to WIFI", "Connect to Mobile Data", "Quit Application"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                } else if (options[item].equals("Quit Application")) {
                    dialog.dismiss();
                    finish();
                }
            }
        });
        builder.show();

    }

    private void getDataFromApi() {
        isLoaded=false;
        String baseUrl="";
        baseUrl+=Constants.baseUrl+Constants.getSiteContents;
        new GetBaseData().execute(baseUrl);
    }

    private class GetBaseData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            if (!isLoaded) {
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();

            }

        }
        @Override
        protected String doInBackground(String... params) {
            String baseUrl="";
            baseUrl+=Constants.baseUrl+Constants.getSiteContents;
            String content =HttpURLConnect.getData(baseUrl);
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            if(siteProperties!=null)
                siteProperties.clear();

            //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            JSONArray OpWorkPlaces;
            JSONObject siteContent;

            if(s != null) {
                try {
                    JSONObject reader = new JSONObject(s);
                    siteContent = reader.getJSONObject("SiteContent");
                    OpWorkPlaces= reader.getJSONArray("OperationsWorkPlaces");
                    opWorkPlList = OperationWorkPlaceMapper.MapOperationWorkPlaces(OpWorkPlaces);
                    // Putting Site Content Properties in Bundle
                    siteProperties.putString("SiteContentId",siteContent.getString("SiteContentId"));
                    siteProperties.putString("CompanyLogo",siteContent.getString("CompanyLogo"));
                    siteProperties.putString("CompanyDisplayName",siteContent.getString("CompanyDisplayName"));
                    siteProperties.putString("Slogan",siteContent.getString("Slogan"));
                    siteProperties.putString("Banner1",siteContent.getString("Banner1"));
                    siteProperties.putString("Banner2",siteContent.getString("Banner2"));
                    siteProperties.putString("Banner3",siteContent.getString("Banner3"));
                    siteProperties.putString("ServiceContents",siteContent.getString("ServiceContents"));
                    siteProperties.putString("AboutusContents",siteContent.getString("AboutusContents"));
                    siteProperties.putString("Address",siteContent.getString("Address"));
                    siteProperties.putString("Telephone",siteContent.getString("Telephone"));
                    siteProperties.putString("FBLink",siteContent.getString("FBLink"));
                    siteProperties.putString("TwiterLink",siteContent.getString("TwiterLink"));
                    siteProperties.putString("WebsiteClickURL",siteContent.getString("WebsiteClickURL"));
                    siteProperties.putString("BodyBGColor",siteContent.getString("BodyBGColor"));
                    siteProperties.putString("Email",siteContent.getString("Email"));
                    siteProperties.putString("CompanyShortName",siteContent.getString("CompanyShortName"));
                    siteProperties.putString("ExtraField1",siteContent.getString("ExtraField1"));
                    siteProperties.putString("ExtraField2",siteContent.getString("ExtraField2"));
                    siteProperties.putString("ExtraField3",siteContent.getString("ExtraField3"));
                    siteProperties.putString("ExtraField4",siteContent.getString("ExtraField4"));
                    siteProperties.putString("UserDomainKey",siteContent.getString("UserDomainKey"));
                    siteProperties.putString("TitleIcon",siteContent.getString("TitleIcon"));
                    siteProperties.putString("CompanyLogoBytes",siteContent.getString("CompanyLogoBytes"));
                    siteProperties.putString("CompanyLogoSource",siteContent.getString("CompanyLogoSource"));
                    siteProperties.putString("Banner1Bytes",siteContent.getString("Banner1Bytes"));
                    siteProperties.putString("Banner1Source",siteContent.getString("Banner1Source"));
                    siteProperties.putString("Banner2Bytes",siteContent.getString("Banner2Bytes"));
                    siteProperties.putString("Banner2Source",siteContent.getString("Banner2Source"));
                    siteProperties.putString("Banner3Bytes",siteContent.getString("Banner3Bytes"));
                    siteProperties.putString("Banner3Source",siteContent.getString("Banner3Source"));
                    siteProperties.putString("CompanyDisplayNameAr",siteContent.getString("CompanyDisplayNameAr"));
                    siteProperties.putString("SloganAr",siteContent.getString("SloganAr"));
                    siteProperties.putString("ServiceContentsAr",siteContent.getString("ServiceContentsAr"));
                    siteProperties.putString("AboutusContentsAr",siteContent.getString("AboutusContentsAr"));
                    siteProperties.putString("AddressAr",siteContent.getString("AddressAr"));

                    siteProperties.putParcelableArrayList("opWorkPlaces", opWorkPlList);

                }
            catch (JSONException e){
                e.printStackTrace();
                }
            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");

            }

            //Toast.makeText(MainActivity.this, siteProperties.toString(), Toast.LENGTH_LONG).show();

        }

    }
}
