package com.iue.pocketdoc.common.fragment;

import com.iue.pocketdoc.android.R;
import com.iue.pocketdoc.common.widget.ProgressRelativeLayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseFragment extends Fragment {
	private static final long listViewShowDuration = 1000;
	/**
	 * 视图是否创建
	 */
	private boolean isInitView;
	/**
	 * 是否visable
	 */
	private boolean refresh;
	/**
	 * 数据有没有加载过
	 */
	private boolean isInitData;

	public boolean power;
	public ProgressRelativeLayout mProgcess;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		isInitView = true;
		isFechdata();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setRetainInstance(false);
	}

	public abstract void fechdata();

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {

		if (isVisibleToUser) {
			refresh = true;
			isFechdata();
		} else {
			refresh = false;
		}

	}

	public void initProcess(View view, View...views) {
		mProgcess = (ProgressRelativeLayout) view.findViewById(R.id.mProgcess);
		if (views != null) {
			mProgcess.hideAlphaView(views);
		}
		dismissProg();

	}

	public boolean isFechdata() {
		if (isInitView && refresh && (!isInitData)
				|| (isInitView && refresh && power)) {
			fechdata();
			isInitData = true;
			power = false;
			return true;
		}
		return false;
	}

	public void dismissProg() {
		// stopProgressDialog();
		if (mProgcess != null) {
			mProgcess.closeloadimg();
			mProgcess.dismiss();
		}
	}

	public void startProg() {

		if (mProgcess != null) {
			mProgcess.openloadimg();
		}
	}

	/**
	 * @deprecated
	 * @param text
	 */
	public void showNodata(String text) {
		mProgcess.showNoData(text);
	}

	public void showRefresh(String text, String err,
			OnClickListener noDataInterface) {
		mProgcess.showRefresh(text, err, noDataInterface);
	}

	public void dismiss() {
		mProgcess.dismiss();
	}

	/**
	 * @param id
	 *            NoDataImg的资源id
	 * @param text
	 *            NoDataImg下的说明文字
	 */
	public void showNodataImg(int id, String text) {
		mProgcess.setNoDataImgRes(id, text);
		mProgcess.showNoDataImg();
	}

	public void showListView(View... views) {
		for (View view : views) {
			view.setVisibility(View.VISIBLE);
			view.animate().alpha(1).setDuration(listViewShowDuration).start();
		}
	}
}
