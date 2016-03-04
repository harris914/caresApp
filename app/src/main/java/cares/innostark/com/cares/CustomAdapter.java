package cares.innostark.com.cares;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cares.innostark.com.cares.Models.HireGroups;

/**
 * Created by bcm on 2/8/2016.
 */
public class CustomAdapter extends ArrayAdapter<HireGroups> {

    ArrayList<HireGroups> result;
    Context c;
    LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<HireGroups> list) {
        super(context,0, list);
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
    public boolean isEnabled(int position) {
        final HireGroups h = result.get(position);
        if(h.getSubHireGroup().size() == 0) {
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;

        final HireGroups i = result.get(position);
        if(i != null) {
            v = inflater.inflate(R.layout.single_hiregroup_item, null);
            final TextView hireGroupName = (TextView) v.findViewById(R.id.hire_group_name);
            final TextView subtitle = (TextView) v.findViewById(R.id.subtitle);
            final TextView desc = (TextView) v.findViewById(R.id.hire_group_desc);
            final ImageView hiregroupImage = (ImageView) v.findViewById(R.id.hire_group_image);
            final TextView vehicleCount = (TextView) v.findViewById(R.id.no_of_vehicles);

            if(hireGroupName != null) {
                hireGroupName.setText(i.getHireGroupName());
            }
            if(subtitle != null) {
                subtitle.setText(i.getSubTitle());
            }
            if(hiregroupImage != null) {
                Picasso.with(c).load(i.getPhotoUrl()).into(hiregroupImage);
            }
            if(desc != null) {
                desc.setText(i.getDescription());
            }
            if(vehicleCount != null)
            {
                int count=i.getSubHireGroup().size();
                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(200); //You can manage the blinking time with this parameter
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.INFINITE);
                if(count > 0)
                {
                    vehicleCount.startAnimation(anim);
                    vehicleCount.setText("  "+count+" Vehicle(s) ");
                }
                else
                    vehicleCount.setText("  "+count+" Vehicle(s) ");
            }

        }
        return v;
    }
}
