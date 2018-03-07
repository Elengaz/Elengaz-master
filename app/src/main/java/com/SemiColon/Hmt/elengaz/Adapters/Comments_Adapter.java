package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.Model.Comments;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;

/**
 * Created by Delta on 04/03/2018.
 */

public class Comments_Adapter extends RecyclerView.Adapter<Comments_Adapter.mViewHolder> {
    Context context;
    List<Comments> commentsList;

    public Comments_Adapter(Context context, List<Comments> commentsList) {
        this.context = context;
        this.commentsList = commentsList;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comments_item_row,parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        Comments comments = commentsList.get(position);
        holder.BindData(comments);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        private TextView m_name,m_date,m_comment,service_name;
        private RatingBar ratingBar;
        public mViewHolder(View itemView) {
            super(itemView);
            m_name = itemView.findViewById(R.id.d_name);
            m_date = itemView.findViewById(R.id.d_date);
            m_comment = itemView.findViewById(R.id.d_comment);
            service_name = itemView.findViewById(R.id.service_name);
            ratingBar = itemView.findViewById(R.id.d_rate);
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(ContextCompat.getColor(context, R.color.ratebar), PorterDuff.Mode.SRC_ATOP);
            //stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.starPartiallySelected), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(ContextCompat.getColor(context, R.color.gr), PorterDuff.Mode.SRC_ATOP);
        }

        public void BindData(Comments comments)
        {

            service_name.setText(comments.getClient_service_name());
            m_name.setText(comments.getClient_user_name());
            m_date.setText(comments.getClient_service_date());
            m_comment.setText(comments.getClient_evaluation_comment());
            ratingBar.setRating((float) Integer.parseInt(comments.getClient_evaluation()));
        }
    }

}
