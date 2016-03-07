package com.iue.pocketdoc.common.widget;

import java.lang.reflect.Field;

import com.iue.pocketdoc.utilities.TextUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public abstract class PickDialog {
	// private EditText editText;
	private Context context;
	private int layout;
	protected AlertDialog.Builder builder;
	private String title;
	private boolean hasbtn;

	public PickDialog(Context context, int layout, String title, boolean hasbtn) {
		this.context = context;
		this.layout = layout;
		this.title = title;
		this.hasbtn = hasbtn;
		init();
	}

	public void init() {
		builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, layout, null);
		if (TextUtil.isValidate(title)) {
			builder.setTitle(title);
		}
		builder.setView(view);
		if (hasbtn) {
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							closeDialog(dialog, true);
							okClicked(dialog, true);
						}
					}).setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							closeDialog(dialog, true);
							okClicked(dialog, false);
						}
					});
		}
		findViewByView(view);
		Dialog dialog = builder.create();
		dialog.show();

	}

	/**
	 * 初始化各控件
	 * 
	 * @param view
	 *            父控件
	 */
	public abstract void findViewByView(View view);

	/**
	 * 系统自带的确定按钮被点击
	 */
	public abstract void okClicked(DialogInterface dialog, boolean sure);

	/**
	 * 利用反射确定是否关闭对话框
	 * 
	 * @param dialog
	 * @param close
	 *            false:不关闭；true：关闭
	 */
	public void closeDialog(final DialogInterface dialog, boolean close) {
		// 利用反射使点击按钮时，对话框不会关闭
		try {
			// 得到AlertDialog的父类属性mShowing
			field = dialog.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			// 将mShowing变量设为false，表示对话框已关闭
			field.set(dialog, close);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	Field field;
}
