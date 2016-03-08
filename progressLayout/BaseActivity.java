package com.iue.pocketdoc.common.activity;

import com.iue.pocketdoc.android.R;
import com.iue.pocketdoc.common.widget.LoadingDialog;
import com.iue.pocketdoc.common.widget.ProgressRelativeLayout;
import com.iue.pocketdoc.global.IUETheme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author SYC
 *
 * @date 2015年4月20日
 */
public abstract class BaseActivity extends Activity {
	private static final long listViewShowDuration = 1000;
	public ImageView mTitleBack, mBackImg;
	public TextView mRightTxt, mTitle;
	public View mTitleLiner;
	public boolean isExisTitle = true;
	public boolean isprogcess = false;
	private LoadingDialog mloadingdialog;
	public ProgressRelativeLayout mProgcess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();
		initTitleView();
		onTitleClick();
		initializeView();
		initializeData();
	}

	protected abstract void setContentView();

	protected abstract void onTitleClick();

	protected abstract void initializeView();

	protected abstract void initializeData();

	@Override
	protected void onResume() {
		super.onResume();
		// TODO umeng
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void startProgcess() {
		if (mloadingdialog == null) {
			mloadingdialog = LoadingDialog.createDialog(this);
			// mloadingdialog.setMessage("正在加载中...");
		}
		mloadingdialog.show();
	}

	public void stopProgressDialog() {
		if (mloadingdialog != null) {
			mloadingdialog.dismiss();
		}
	}

	public void dismissProg() {
		stopProgressDialog();
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

	private void initTitleView() {
		if (isExisTitle) {
			mRightTxt = (TextView) findViewById(R.id.mRightTxt);
			mTitleBack = (ImageView) findViewById(R.id.mTitleBack);
			mTitle = (TextView) findViewById(R.id.mTitle);
			mTitleLiner = findViewById(R.id.mTitleLiner);
			mRightTxt.setTextColor(IUETheme.getThemeColorID());
			mTitleBack.setImageResource(IUETheme
					.getThemeImageID("ic_arrow_left"));
			mTitleBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					onBackPressed();
				}
			});
			if (isprogcess) {
				mProgcess = (ProgressRelativeLayout) findViewById(R.id.mProgcess);
				dismissProg();
			}
		}
	}

	public void initProcess(View... views) {
		if (views != null) {
			mProgcess.hideAlphaView(views);
		}
	}
}
