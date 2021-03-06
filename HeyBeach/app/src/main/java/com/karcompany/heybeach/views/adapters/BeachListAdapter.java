package com.karcompany.heybeach.views.adapters;

/**
 * Created by pvkarthik on 2017-02-22.
 *
 * Recycler view adapter which displays beach list data.
 */

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karcompany.heybeach.R;
import com.karcompany.heybeach.cache.ImageFetcher;
import com.karcompany.heybeach.cache.ImageWorker;
import com.karcompany.heybeach.models.BeachMetaData;
import com.karcompany.heybeach.networking.ApiRepo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BeachListAdapter extends RecyclerView.Adapter<BeachListItemViewHolder> {

	private Map<String, BeachMetaData> mBeachDataMap;
	private List<String> mBeachIdList;

	private boolean mIsDataLoadFinished;

	private int VIEW_TYPE_ITEM = 1;
	private int VIEW_TYPE_PROGRESS = 2;

	private ImageFetcher mImageFetcher;

	private int mColorIndex = 0;

	private List<Integer> mColourList;

	public BeachListAdapter(ImageFetcher imageFetcher) {
		mBeachDataMap = new LinkedHashMap<>();
		mBeachIdList = new ArrayList<>(4);
		mImageFetcher = imageFetcher;
		mColourList = new ArrayList<>(4);
		mColourList.add(R.color.accent_brand);
		mColourList.add(R.color.neutral_medium_soft_dark);
		mColourList.add(R.color.light_brand);
		mColourList.add(R.color.medium_brand);
	}

	@Override
	public BeachListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view;
		if(viewType == VIEW_TYPE_ITEM) {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_beach_row_item, parent, false);
		} else {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_progress, parent, false);
		}
		return new BeachListItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final BeachListItemViewHolder holder, int position) {
		if(getItemViewType(position) == VIEW_TYPE_ITEM) {
			holder.imageTitleTxtView.setText("");
			if (position < mBeachIdList.size()) {
				BeachMetaData beachItem = mBeachDataMap.get(mBeachIdList.get(position));
				if (beachItem != null) {
					if (!TextUtils.isEmpty(beachItem.getName())) {
						holder.imageTitleTxtView.setText(beachItem.getName());
					}
					if (!TextUtils.isEmpty(beachItem.getUrl())) {
						ViewCompat.setBackground(holder.imageImgView, new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), getColorIndex())));
						// Finally load the image asynchronously into the ImageView, this also takes care of
						// setting a placeholder image while the background thread runs
						mImageFetcher.loadImage(ApiRepo.getBaseUrl(beachItem.getUrl()), holder.imageImgView, new ImageWorker.OnImageLoadedListener() {
							@Override
							public void onImageLoaded(boolean success) {
								ViewCompat.setBackground(holder.imageImgView, null);
							}
						});
					}
				}
			}
		} else {
			StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
			layoutParams.setFullSpan(true);
		}
	}

	@Override
	public void onViewRecycled(BeachListItemViewHolder holder) {
		if(holder.imageImgView != null) {

		}
		super.onViewRecycled(holder);
	}

	@Override
	public int getItemCount() {
		if(mIsDataLoadFinished) {
			return mBeachIdList.size();
		} else {
			return mBeachIdList.size() + 1;
		}
	}

	public void clearData() {
		mBeachIdList.clear();
		mBeachDataMap.clear();
	}

	@Override
	public int getItemViewType(int position) {
		if(mIsDataLoadFinished)
			return VIEW_TYPE_ITEM;
		if(position < mBeachIdList.size()){
			return VIEW_TYPE_ITEM;
		}
		return VIEW_TYPE_PROGRESS;
	}

	public boolean isLoadingPos(int position) {
		return getItemViewType(position) == VIEW_TYPE_PROGRESS;
	}

	public boolean isDataLoadFinished() {
		return mIsDataLoadFinished;
	}

	public void setDataFinished() {
		mIsDataLoadFinished = true;
		notifyItemRemoved(mBeachIdList.size());
	}

	public void addData(List<BeachMetaData> beachList) {
		if (beachList != null) {
			for (BeachMetaData beachItem : beachList) {
				mBeachDataMap.put(beachItem.getId(), beachItem);
			}
			int oldSize = mBeachIdList.size();
			mBeachIdList.clear();
			mBeachIdList.addAll(mBeachDataMap.keySet());
			int newSize = mBeachIdList.size();
			if (oldSize > 0) {
				notifyItemRangeInserted(oldSize, newSize - oldSize);
			} else {
				notifyDataSetChanged();
			}
		}
	}

	private synchronized int getColorIndex() {
		int colour = mColourList.get(mColorIndex);
		mColorIndex++;
		if (mColorIndex == mColourList.size()) mColorIndex = 0;
		return colour;
	}

	public ArrayList<BeachMetaData> getBeachList() {
		ArrayList<BeachMetaData> beachList = new ArrayList<>();
		Iterator<Map.Entry<String, BeachMetaData>> iterator = mBeachDataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, BeachMetaData> entry = iterator.next();
			beachList.add(entry.getValue());
		}
		return beachList;
	}
}
