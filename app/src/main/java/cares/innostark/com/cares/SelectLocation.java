package cares.innostark.com.cares;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cares.innostark.com.cares.Models.OperationWorkPlaces;

public class SelectLocation extends AppCompatActivity {

    ListView list;
    ArrayAdapter<String> listAdapter;
    TextView loc_identifier;
    String location;
    Bundle siteProperties;
    ArrayList<String> locsList=new ArrayList<>();
    ImageButton showMap;
    AutoCompleteTextView search;
    EditText loc_search;
    ArrayList<String> appsListSort = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_tool_bar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        search= (AutoCompleteTextView) findViewById(R.id.searchEditText);
        //loc_search=(EditText) findViewById(R.id.searchEditText);
        loc_identifier=(TextView) findViewById(R.id.loc_identifier);

        final Intent i=getIntent();
        final String val=i.getStringExtra("val");

        if (val.equals("pickup")) {
            loc_identifier.setText("Pickup Location");
        }
        else {
            loc_identifier.setText("Dropoff Location");
        }

        siteProperties=getIntent().getExtras();
        final ArrayList<OperationWorkPlaces> workLocs = siteProperties.getParcelableArrayList("opWorkPlaces");

        showMap= (ImageButton)findViewById(R.id.show_map);                // map button to show all locations on the map
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(SelectLocation.this,MapsActivity.class);
                String callingActivity="SelectLocation";
                in.putExtra("all_locations_list",workLocs);
                in.putExtra("callingActivity",callingActivity);
                startActivity(in);
            }
        });

        Window window = this.getWindow();                // setting the color of the status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(android.R.color.black));

        for(int count=0; count < workLocs.size();count++)
        {
            OperationWorkPlaces obj=workLocs.get(count);
            String loc=obj.getLocationName();
            locsList.add(loc);
        }

        // setting the adapter for autocomplete edit text to search for locations
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, locsList);
        search.setAdapter(adapter);
        
//        loc_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //searchList();
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        list=(ListView) findViewById(R.id.cities_list);
        listAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, locsList);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location= list.getItemAtPosition(position).toString();
                //location= locsList.get(position);
                OperationWorkPlaces item = (OperationWorkPlaces) workLocs.get(position);


                //val=i.getStringExtra("val");
                if (val.equals("pickup")) {                                // to decide whether call came from pickup loc or dropoff loc
                    i.putExtra("loc_info",item);           // sending back the pickup location obnject
                    setResult(1, i);
                }
                else if(val.equals("drop"))                                // to decide whether call came from pickup loc or dropoff loc
                {
                    i.putExtra("loc_info",item);      // sending back the dropoff location object
                    setResult(2, i);
                }
                finish();
            }
        });
    }

    private void searchList() {
//        String s = loc_search.getText().toString();
//        int textlength = s.length();
//        String sApp;
//        ArrayList<String> appsListSort = new ArrayList<String>();
//        int appSize = locsList.size();
//        for (int i = 0; i < appSize; i++) {
//            sApp = locsList.get(i);
//            if (textlength <= sApp.length()) {
//                if (s.equalsIgnoreCase((String) sApp.subSequence(0, textlength))) {
//                    appsListSort.add(locsList.get(i));
//                }
//            }
//        }
//        locsList.clear();
//        for (int j = 0; j < appsListSort.size(); j++) {
//            locsList.add(appsListSort.get(j));
//        }
//        listAdapter.notifyDataSetChanged();

    }
}
