package com.iue.pocketdoc.visitscheduling.widget;

import com.iue.pocketdoc.android.R;
import com.iue.pocketdoc.common.widget.PickDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * @author HKQ
 * @category 新增排班界面中开始时间与持续时间的选择
 * @date 2015年12月
 */
public abstract class SinglePickDialog extends PickDialog {
	public static final int type_symbol = 0;
	public static final int type_word = 1;
	private final static int layout = R.layout.single_dialog;
	private int type;
	private NumberPicker mFirstPicker, mSecondPicker;
	private TextView mSingleDialogFirstTxt, mSingleDialogSecondTxt;
	String[] leftArr;
	String[] rightArr;

	public SinglePickDialog(Context context, String[] leftArr,
			String[] rightArr, String title, int type) {
		super(context, layout, title, true);// hasBtn默认true
		this.leftArr = leftArr;
		this.rightArr = rightArr;
		this.type = type;
	}

	public void setDefault(int leftValue, int rightValue) {
		mFirstPicker.setDisplayedValues(leftArr);
		// 设置最小值为arr[0]
		mFirstPicker.setMinValue(0);
		// 设置最小值为arr[arr.length-1]
		mFirstPicker.setMaxValue(leftArr.length - 1);
		mFirstPicker.setValue(leftValue);
		mSecondPicker.setDisplayedValues(rightArr);
		mSecondPicker.setMinValue(0);
		mSecondPicker.setMaxValue(rightArr.length - 1);
		mSecondPicker.setValue(getTrueRightIndex(rightValue));
		switch (type) {
		case type_symbol:
			mSingleDialogFirstTxt.setText(":");
			break;
		case type_word:
			mSingleDialogFirstTxt.setText("小时");
			mSingleDialogSecondTxt.setText("分钟");
			break;
		}
	}

	private int getTrueRightIndex(int rightValue) {
		switch (type) {
		case type_symbol:
			for (int i = 0; i < rightArr.length; i++) {
				if (rightValue == Integer.valueOf(rightArr[i]))
					return i;
			}
			break;
		case type_word:
			for (int i = 0; i < rightArr.length; i++) {
				if (rightValue == Integer.valueOf(rightArr[i]))
					return i;
			}
			break;
		}
		return 0;
	}

	private String getTrueRightValue(int rightValue) {
		switch (type) {
		case type_symbol:
			for (int i = 0; i < rightArr.length; i++) {
				if (rightValue == i)
					return rightArr[i];
			}
			break;
		case type_word:
			for (int i = 0; i < rightArr.length; i++) {
				if (rightValue == i)
					return rightArr[i];
			}
			break;
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
			switch (type) {
			case type_symbol:
				sb.append(mFirstPicker.getValue());
				sb.append(":");
				sb.append(getTrueRightValue(mSecondPicker.getValue()));
				callback(sb.toString(), mFirstPicker.getValue(),
						Integer.valueOf(getTrueRightValue(mSecondPicker
								.getValue())));
				break;
			case type_word:
				if (mFirstPicker.getValue() != 0) {
					sb.append(mFirstPicker.getValue());
					sb.append("小时");
				}
				if (mSecondPicker.getValue() != 0) {
					sb.append(getTrueRightValue(mSecondPicker.getValue()));
					sb.append("分钟");
				}
				callback(sb.toString(), mFirstPicker.getValue(),
						Integer.valueOf(getTrueRightValue(mSecondPicker
								.getValue())));
				break;
			}
		}
	}

	public double getduration() {

		return 0;
	}

	public abstract void callback(String data, int valueFirst, int valueSecond);
}
