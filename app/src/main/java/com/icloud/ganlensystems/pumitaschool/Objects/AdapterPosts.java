package com.icloud.ganlensystems.pumitaschool.Objects;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icloud.ganlensystems.pumitaschool.R;

import java.util.List;


/**
 * Created by drewermerc on 30/12/17.
 */

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.ViewHolderPosts> {

    List<Posts> ListPosts;

    public AdapterPosts(List<Posts> listPosts) {
        ListPosts = listPosts;
    }

    @Override
    public ViewHolderPosts onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolderPosts(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPosts holder, int position) {
        holder.postDescription.setText(ListPosts.get(position).getMessage());
        if(ListPosts.get(position).getFull_picture() != null) {
            new DownloadImageTask(holder.postImage).execute(ListPosts.get(position).getFull_picture());
        }
    }

    @Override
    public int getItemCount() {
        return ListPosts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void clear() {
        int size = this.ListPosts.size();
        this.ListPosts.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolderPosts extends RecyclerView.ViewHolder{
        TextView postDescription;
        ImageView postImage;
        CardView post;
        public ViewHolderPosts(View itemView) {
            super(itemView);
            post = (CardView)itemView.findViewById(R.id.cardview_posts);
            postDescription = (TextView) itemView.findViewById(R.id.post_description);
            postImage = (ImageView) itemView.findViewById(R.id.post_image);
        }

    }
}
