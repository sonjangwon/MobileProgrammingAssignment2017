package com.example.jaehyukshin.goolgemaps;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListview extends ArrayAdapter<String> {

    private String[] name;
    private String[] desc;
    private Integer[] imgid;
    private Activity context;

    public CustomListview(Activity context,String[] name, String[] desc, Integer[] imgid ){
        super(context,R.layout.listview_layout,name);
        this.context = context;
        this.name = name;
        this.desc = desc;
        this.imgid = imgid;

    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r==null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.listview_layout,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) r.getTag();

        }

        viewHolder.imageView.setImageResource(imgid[position]);
        viewHolder.tv1.setText(name[position]);
        viewHolder.tv1.setText(desc[position]);

        return r;

        //return super.getView(position, convertView,parent);
    }

    class ViewHolder{

        TextView tv1;
        TextView tv2;
        ImageView imageView;
        ViewHolder(View v)
        {
            tv1 = (TextView)v.findViewById(R.id.restaurantName);
            tv2 = (TextView)v.findViewById(R.id.restaurantDescription);
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }
    }
}