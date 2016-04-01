package cares.innostark.com.cares;

import android.net.Uri;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cares.innostark.com.cares.Models.UserInfo;

/**
 * Created by bcm on 2/12/2016.
 */
public class HttpURLConnect {



    public static String getData(String uri){
        //String authorization="";
        BufferedReader reader = null;
        HttpURLConnection con=null;
        try {
            //String urlParameters  = "url=bookingdemo";
            String urlParameters  = "url="+ Constants.url;
            byte[] postData       = urlParameters.getBytes( "UTF-8" );
            int    postDataLength = postData.length;
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            String authorization = Constants.username+":"+Constants.password;
            //String basicAuth = "Basic "+ Base64().encode(authorization.getBytes(),Base64.DEFAULT);
            byte[] encodedBytes = Base64.encode(authorization.getBytes(), 0);
            authorization = "Basic " + new String(encodedBytes);
            con.setRequestProperty ("Authorization", authorization);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestMethod("POST");
            con.setRequestProperty( "Content-Length", "" + Integer.toString( postDataLength ));
            con.setUseCaches( false );

            OutputStream os = con.getOutputStream();
            os.write(urlParameters.getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }
        finally {

            if(con != null) {
                con.disconnect();
            }
        }
    }

    public static String getVehicleCharge(String uri, String sh_id, String op_id,String endDateTime,String startDateTime,String domkey)
    {
        Calendar c;
        c = Calendar.getInstance();
        int dYear = c.get(Calendar.YEAR);                   //setting current date to the drop date field
        int dMonth = c.get(Calendar.MONTH)+1 ;
        int dDay = c.get(Calendar.DAY_OF_MONTH);
        String date = dMonth + "/"+dDay +"/" + dYear;
        Date dt = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:00"));
        String time = sdf.format(dt);
        String dateTime=date+ " " + time;

        String authorization="";
        BufferedReader reader = null;
        HttpURLConnection con=null;
        try {
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            authorization = Constants.username + ":" + Constants.password;
            byte[] encodedBytes = Base64.encode(authorization.getBytes(), 0);
            authorization = "Basic " + new String(encodedBytes);
            con.setRequestProperty("Authorization", authorization);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestMethod("POST");
            //con.setRequestProperty( "Content-Length", "" + Integer.toString( postDataLength ));
            con.setUseCaches(false);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("HireGroupDetailId", sh_id)
                    .appendQueryParameter("OperationId", op_id)
                    .appendQueryParameter("StartDtTime", startDateTime)
                    .appendQueryParameter("EndDtTime", endDateTime)
                    .appendQueryParameter("RaCreatedDate", dateTime)
                    .appendQueryParameter("UserDomainKey", domkey);

            String query = builder.build().getEncodedQuery();

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            //writer.flush();
            writer.close();
            os.close();
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally {

            if(con != null) {
                con.disconnect();
            }
        }
    }
    public static String getHireGroupsData(String uri,String key, String domkey,String dropoffcityid,String returnlocid, String pickupcityid,String outlocid, String enddatetime, String startdatetime){
        String authorization;
        HttpURLConnection con=null;
        try {
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            authorization = Constants.username+":"+Constants.password;
            byte[] encodedBytes = Base64.encode(authorization.getBytes(), 0);
            authorization = "Basic " + new String(encodedBytes);
            con.setRequestProperty ("Authorization", authorization);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestMethod("POST");
            con.setUseCaches( false );

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("key", key)
                    .appendQueryParameter("url", Constants.url)
                    .appendQueryParameter("DomainKey", domkey)
                    .appendQueryParameter("DropOffCityId", dropoffcityid)
                    .appendQueryParameter("EndDateTime", enddatetime)
                    .appendQueryParameter("OutLocationId", outlocid)
                    .appendQueryParameter("PickUpCityId", pickupcityid)
                    .appendQueryParameter("ReturnLocationId", returnlocid)
                    .appendQueryParameter("StartDateTime", startdatetime);
            String query = builder.build().getEncodedQuery();

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            //writer.flush();
            writer.close();
            os.close();

//            OutputStream os = con.getOutputStream();
//            os.write(urlParameters.getBytes("UTF-8"));
//            os.close();

            // read the response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }
        finally {

            if(con != null) {
                con.disconnect();
            }
        }
    }
    public static String saveBookingData(String uri, Double pickUpCityId1, Double dropOffCityId2, Double operationId, String startDateTime, String endDateTime, Long domainKey, Double hireGroupDetailId,  Double standardRt,  String tariffType, UserInfo ui){
        String authorization;
        HttpURLConnection con=null;
        try {
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            authorization = Constants.username+":"+Constants.password;
            byte[] encodedBytes = Base64.encode(authorization.getBytes(), 0);
            authorization = "Basic " + new String(encodedBytes);
            con.setRequestProperty ("Authorization", authorization);
            con.setRequestProperty("Content-Type", "application/json");
            //con.setRequestProperty("charset", "utf-8");
            con.setRequestMethod("POST");
            con.setUseCaches( false );


            JSONObject jsonMainObj = new JSONObject();
            JSONObject userInfo=new JSONObject();
            jsonMainObj.put("PickUpLocationId", pickUpCityId1);
            jsonMainObj.put("DropOffLocationId", dropOffCityId2);
            jsonMainObj.put("PickUpOperationId", operationId);
            jsonMainObj.put("PickUpDateTime", startDateTime);
            jsonMainObj.put("DropOffDateTime", endDateTime);
            jsonMainObj.put("UserDomainKey", domainKey);
            jsonMainObj.put("HireGroupDetailId", hireGroupDetailId);
            jsonMainObj.put("DropOffCharges", 0);
            jsonMainObj.put("StandardRate", standardRt);
            jsonMainObj.put("ServiceItems", new JSONArray());
            jsonMainObj.put("InsuranceTypes", new JSONArray());
            jsonMainObj.put("TariffType", "D-Daily");

            userInfo.put("BillingAddress",ui.getAddress());
            //userInfo.put("CustomerType",0);
            userInfo.put("DOB",ui.getDateOfBirth()+ " 12:00:00 AM");
            userInfo.put("Email",ui.getEmail());
            userInfo.put("FName",ui.getFirstName());
            //userInfo.put("InsurancesTotal",0.0);
            userInfo.put("LName",ui.getLastName());
            userInfo.put("PhoneNumber",ui.getPhoneNo());
            //userInfo.put("ServiceItemsTotal",0.0);

            jsonMainObj.put("UserInfo",userInfo);

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonMainObj.toString());
            //writer.flush();
            writer.close();
            os.close();

            // read the response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }
        finally {

            if(con != null) {
                con.disconnect();
            }
        }
    }
}
