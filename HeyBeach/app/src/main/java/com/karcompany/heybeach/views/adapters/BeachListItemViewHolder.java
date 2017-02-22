package com.karcompany.heybeach.views.adapters;

/**
 * Created by pvkarthik on 2017-02-22.
 *
 * View holder.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karcompany.heybeach.R;

public class BeachListItemViewHolder extends RecyclerView.ViewHolder {

	public TextView imageTitleTxtView;
	public ImageView imageImgView;

	public BeachListItemViewHolder(View itemView) {
		super(itemView);
		imageTitleTxtView = (TextView) itemView.findViewById(R.id.name);
		imageImgView = (ImageView) itemView.findViewById(R.id.image);
	}

}
