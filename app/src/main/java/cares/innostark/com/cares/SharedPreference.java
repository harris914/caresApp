package cares.innostark.com.cares;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cares.innostark.com.cares.Models.BookingModel;

/**
 * Created by bcm on 7/18/2016.
 */
public class SharedPreference {
    public static final String PREFS_NAME = "BookingListPrefs";
    public static final String FAVORITES = "BookingObject";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveBookings(Context context, List<BookingModel> bookings) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(bookings);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addBooking(Context context, BookingModel booking) {
        List<BookingModel> bookings = getBookings(context);
        if (bookings == null)
            bookings = new ArrayList<BookingModel>();
        bookings.add(booking);
        saveBookings(context, bookings);
    }

//    public void removeFavorite(Context context, Product product) {
//        ArrayList<Product> favorites = getFavorites(context);
//        if (favorites != null) {
//            favorites.remove(product);
//            saveFavorites(context, favorites);
//        }
//    }

    public ArrayList<BookingModel> getBookings(Context context) {
        SharedPreferences settings;
        List<BookingModel> bookings;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            BookingModel[] favoriteItems = gson.fromJson(jsonFavorites,
                    BookingModel[].class);

            bookings = Arrays.asList(favoriteItems);
            bookings = new ArrayList<BookingModel>(bookings);
        } else
            return null;

        return (ArrayList<BookingModel>) bookings;
    }
}
