package com.weqar.weqar.JavaClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by andriod on 16/3/18.
 */

public class RecyclerViewAdapter_DiscountDetails_User extends RecyclerView.Adapter<RecyclerViewAdapter_DiscountDetails_User.MyView>{
    Context context;


    private List<String> u_list_id;
    private List<String> u_list_desc;
    private List<String> u_list_img;
    private List<String> u_list_per;
    private List<String> u_list_title;
    private List<String> u_list_logo;
    private List<String> u_list_enddate;
    private List<String> u_list_type;

    public class MyView extends RecyclerView.ViewHolder {

        TextView TV_descdet_title,TV_disc_desc,TV_title_two;
        RatingBar RB_rating_userisc;
        ImageView IV_image;
        CircleImageView CV_logo;

        public MyView(View view) {
            super(view);

            TV_descdet_title = view.findViewById(R.id.disc_desc_title);
            TV_title_two = view.findViewById(R.id.disc_desc_titletwo);
            TV_disc_desc = view.findViewById(R.id.disc_dec_det);
            RB_rating_userisc = view.findViewById(R.id.disc_desc_rating);
            IV_image = view.findViewById(R.id.disc_det_image);
            CV_logo = view.findViewById(R.id.disc_det_logo);


        }

    }


    public RecyclerViewAdapter_DiscountDetails_User(List<String> horizontalList_id,
                                                    List<String> horizontalList_desc,
                                                    List<String> horizontalList_img,
                                                    List<String> horizontalList_per,
                                                    List<String> horizontalList_title,
                                                    List<String> horizontalList_logo,
                                                    List<String> horizontalList_enddate,
                                                    List<String> horizontalList_type,


                                                    Context context) {
        this.u_list_id = horizontalList_id;
        this.u_list_desc = horizontalList_desc;
        this.u_list_img = horizontalList_img;
        this.u_list_per = horizontalList_per;
        this.u_list_title = horizontalList_title;
        this.u_list_logo = horizontalList_logo;
        this.u_list_enddate = horizontalList_enddate;
        this.u_list_type = horizontalList_type;


        this.context=context;

    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_discountdesc_user, parent, false);
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        //   Picasso.with(context).load(list.get(position)).fit().into(holder.textView);
     context = holder.TV_descdet_title.getContext();
        String getdiscount_type= u_list_type.get(position);
        if(getdiscount_type.equals("1"))
        {
            holder.TV_descdet_title.setText(u_list_per.get(position)+"% "+u_list_title.get(position));

        }
        else
        {
            holder.TV_descdet_title.setText(u_list_title.get(position));

        }
        String second=u_list_enddate.get(position).substring(0,10);
        holder.TV_title_two.setText("End Date: "+second);

        holder.TV_disc_desc.setText(u_list_desc.get(position));
        String gg=u_list_per.get(position);
        Integer k=Integer.parseInt(gg);
        Integer kk=k/10;
        Float g=(float) kk;
        holder.RB_rating_userisc.setRating(g);
        try {

            Picasso.with(context).load(Global_URL.Image_url_load+u_list_img.get(position)).error(R.color.colorHints).fit().centerCrop().into(holder.IV_image);
            Picasso.with(context).load(Global_URL.Image_url_load+u_list_logo.get(position)).error(R.drawable.rounded).fit().centerCrop().into(holder.CV_logo);

        } catch (Exception e) {

            e.printStackTrace();
        }





    }

    @Override
    public int getItemCount() {
        return u_list_id.size();
    }

}



