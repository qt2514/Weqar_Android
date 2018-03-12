
package com.weqar.weqar.JavaClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.weqar.weqar.ProfileInfo;
import com.weqar.weqar.R;

import java.util.List;

import cn.refactor.lib.colordialog.PromptDialog;

/**
 * Created by andriod on 19/2/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView>{
    Context context;

    private List<String> list;
    private List<String> list2;
    private List<String> list3;
    public class MyView extends RecyclerView.ViewHolder {

        public TextView textView,textView2,textView3;

        public Button But_chooseplan;

        public MyView(View view) {
            super(view);

            textView = view.findViewById(R.id.grade_plantype);
                textView2 = view.findViewById(R.id.grade_amount);
                textView3 = view.findViewById(R.id.grade_desc);
            But_chooseplan = view.findViewById(R.id.choose_subs_but);


        }

    }


    public RecyclerViewAdapter(List<String> horizontalList, List<String> horizontalList2, List<String> horizontalList3, Context context) {
        this.list = horizontalList;
        this.list2 = horizontalList2;
        this.list3 = horizontalList3;

        this.context=context;

    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_user_subscription_horizontalpage, parent, false);
        return new MyView(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        //   Picasso.with(context).load(list.get(position)).fit().into(holder.textView);
         context = holder.But_chooseplan.getContext();

        holder.textView.setText(list.get(position));
        holder.textView2.setText("\u20B9 "+list2.get(position));

        String htmlAsString = list3.get(position);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);
        holder.textView3.setText(htmlAsSpanned);
        holder.But_chooseplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        context);
//
//                // set title
//                alertDialogBuilder.setTitle("Your Title");
//
//                // set dialog message
//                alertDialogBuilder
//                        .setMessage("Click yes to exit!")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                dialog.cancel();
//
//                            }
//                        });
//
//
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();

                new PromptDialog(context)
                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                        .setAnimationEnable(true)
                        .setTitleText("Confirm Subscription")
                        .setContentText(" For PlanType - "+list.get(position)+" With Cost of "+"\u20B9"+list2.get(position))
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}



