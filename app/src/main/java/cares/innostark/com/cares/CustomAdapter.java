package cares.innostark.com.cares;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        return 3 * result.size();       // to repeat the list 3 times
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        final HireGroups h = result.get(position % 3);           // repeated the list 3 times
        if(h.getSubHireGroup().size() == 0) {
            return false;
        }
        return super.isEnabled(position % 3);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;


        final HireGroups i = result.get(position % 3);          // to get right model as the list is repeated 3 times

        if(i != null) {
            v = inflater.inflate(R.layout.single_hiregroup_item, null);
            if (position % 2 == 1) {
                v.setBackgroundColor(Color.parseColor("#EEEEEE"));
            } else {
                v.setBackgroundColor(Color.parseColor("#DCDCDC"));
            }
            final TextView hireGroupName = (TextView) v.findViewById(R.id.hire_group_name);
            final TextView subtitle = (TextView) v.findViewById(R.id.subtitle);
            //final TextView desc = (TextView) v.findViewById(R.id.hire_group_desc);
            final ImageView desc = (ImageView) v.findViewById(R.id.hire_group_desc);
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
                //desc.setText(i.getDescription());
                desc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),i.getDescription(),Toast.LENGTH_SHORT).show();
                    }
                });
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
                    vehicleCount.setText("  "+count+" Available ");
                }
                else
                    vehicleCount.setText("  "+count+" Available ");
            }

        }
        return v;
    }
}
