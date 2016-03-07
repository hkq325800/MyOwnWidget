package com.iue.pocketdoc.common.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.iue.pocketdoc.android.R;

/**
 * @author HKQ
 * @category 新增排班界面中开始时间与持续时间的选择
 * @date 2015年12月
 */
public abstract class StringPickerDialog extends PickDialog {
	private NumberPicker mFirstPicker, mSecondPicker;
	private TextView mSingleDialogFirstTxt, mSingleDialogSecondTxt;
	private String[] arrFirst, arrSecond;

	public StringPickerDialog(Context context, int layout, String title,
			boolean hasbtn, String[] arrFirst) {
		super(context, layout, title, hasbtn);
		this.arrFirst = arrFirst;
	}

	public StringPickerDialog(Context context, int layout, String title,
			boolean hasbtn, String[] arrFirst, String[] arrSecond) {
		super(context, layout, title, hasbtn);
		this.arrFirst = arrFirst;
		this.arrSecond = arrSecond;
	}

	public void setDefault(String leftValue, String rightValue) {

		mSingleDialogFirstTxt.setText(":");
		mFirstPicker.setDisplayedValues(arrFirst);
		mFirstPicker.setMinValue(0);
		mFirstPicker.setMaxValue(arrFirst.length - 1);
		mFirstPicker.setValue(getTrueIndex(leftValue, arrFirst));// 0-23

		if (rightValue.equals("")) {
			mSingleDialogFirstTxt.setVisibility(View.GONE);
			mSingleDialogSecondTxt.setVisibility(View.GONE);
			mSecondPicker.setVisibility(View.GONE);
		}
	}

	private int getTrueIndex(String value, String[] arr) {

		for (int i = 0; i < arr.length; i++) {
			if (value.equals(arr[i]))
				return i;
		}

		return 0;
	}

	private String getTrueValue(int index, String[] arr) {

		for (int i = 0; i < arr.length; i++) {
			if (index == i)
				return arr[i];
		}

		return "";
	}

	@Override
	public void findViewByView(View view) {
		mSingleDialogFirstTxt = (TextView) view
				.findViewById(R.id.mSingleDialogFirstTxt);
		mSingleDialogSecondTxt = (TextView) view
				.findViewById(R.id.mSingleDialogSecondTxt);
		mFirstPicker = (NumberPicker) view.findViewById(R.id.mFirstPicker);
		mSecondPicker = (NumberPicker) view.findViewById(R.id.mSecondPicker);
		EditText e1 = (EditText) mFirstPicker.getChildAt(0);
		EditText e2 = (EditText) mSecondPicker.getChildAt(0);
		e1.setFocusable(false);
		e2.setFocusable(false);
	}

	@Override
	public void okClicked(DialogInterface dialog, boolean sure) {
		if (sure) {
			StringBuffer sb = new StringBuffer();
			if (mFirstPicker.getDisplayedValues() != null)
				sb.append(getTrueValue(mFirstPicker.getValue(), arrFirst));
			sb.append("");// mSingleDialogFirstTxt
			if (mSecondPicker.getDisplayedValues() != null)
				sb.append(getTrueValue(mSecondPicker.getValue(), arrFirst));
			sb.append("");// mSingleDialogSecondTxt
			callback(sb.toString());
		}
	}

	public double getduration() {

		return 0;
	}

	public abstract void callback(String data);
}
