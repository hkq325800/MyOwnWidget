package com.iue.pocketdoc.visitscheduling.fragment;

import java.util.Calendar;
import java.util.Date;

import com.iue.pocketdoc.utilities.DateUtil;
import com.iue.pocketdoc.utilities.Trace;
import com.iue.pocketdoc.visitscheduling.activity.AddClinicActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	String getWeek;
	Calendar calendar = Calendar.getInstance();
	int getYear, monthB, getDay;// 传进来的日期信息
	int minYear, minMonth, minDay;// 最低要求为最低限度的日期
	DatePickerDialog dialog;
	final static String tip = "请选择明天以后的日期";

	// 2016-03-09 周三
	public DatePickerFragment(Date date) {
		calendar.setTime(date);
		getYear = calendar.get(Calendar.YEAR);
		monthB = calendar.get(Calendar.MONTH);// monthB为真实月份-1
		getDay = calendar.get(Calendar.DAY_OF_MONTH);
		getWeek = DateUtil.getWeekString(calendar.get(Calendar.WEEK_OF_MONTH));
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();// 获取今天的日期信息
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
		minYear = c.get(Calendar.YEAR);
		minMonth = c.get(Calendar.MONTH);
		minDay = c.get(Calendar.DAY_OF_MONTH);
		// todayWeek = DateUtil.getWeekString(c.get(Calendar.DAY_OF_WEEK));
		dialog = new DatePickerDialog(getActivity(), this, getYear, monthB,
				getDay);
		// dialog.getDatePicker().getCalendarView().getDate();
		return dialog;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// 此处为设定日期后的选项
		Calendar c = Calendar.getInstance();
		AddClinicActivity addClinicActivity = (AddClinicActivity) getActivity();
		c.setTimeInMillis(view.getCalendarView().getDate());
		String weekStr = DateUtil.getWeekString(c.get(Calendar.DAY_OF_WEEK));
		int trueMonth = month + 1;
		int trueMonthB = monthB + 1;
		Log.d("OnDateSet", "select year:" + year + ";month:" + trueMonth
				+ ";day:" + day);
		if (year > minYear) {
			addClinicActivity.setDate(year + "-" + trueMonth + "-" + day + " "
					+ weekStr);
			addClinicActivity.setCurrentDate(new Date(view.getCalendarView()
					.getDate()));
		} else if (year == minYear) {
			if (month > minMonth) {
				addClinicActivity.setDate(year + "-" + trueMonth + "-" + day
						+ " " + weekStr);
				addClinicActivity.setCurrentDate(new Date(view
						.getCalendarView().getDate()));
			} else if (month == minMonth) {
				if (day >= minDay) {
					addClinicActivity.setDate(year + "-" + trueMonth + "-"
							+ day + " " + weekStr);
					addClinicActivity.setCurrentDate(new Date(view
							.getCalendarView().getDate()));
				} else if (day < minDay) {
					addClinicActivity.setDate(getYear + "-" + trueMonthB + "-"
							+ getDay + " " + getWeek);
					Trace.show(getActivity(), tip);
				}
			} else {
				addClinicActivity.setDate(getYear + "-" + trueMonthB + "-"
						+ getDay + " " + getWeek);
				Trace.show(getActivity(), tip);
			}
		} else {
			addClinicActivity.setDate(getYear + "-" + trueMonthB + "-" + getDay
					+ " " + getWeek);
			Trace.show(getActivity(), tip);
		}

	}
}
