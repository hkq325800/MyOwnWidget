# DatePickerDialog StringPickerDialog DatePickerFragment
`StringPickerDialog用于Stirng的选择`
```java
dialog = new StringPickerDialog(ThisActivity.this,
        R.layout.single_dialog, "title", true, stringArr) {
    @Override
    public void callback(String data) {
        // do sth. with data
    }
};
dialog.setDefault("默认值", "");
```
`DatePickerFragment用于选择年月日`
下一步的改进需要参考[文章](http://www.jianshu.com/p/af6499abd5c2)


`SinglePickDialog用于选择小时（0-23时），分钟（00、15、30、45）`
```java
dialog = new SinglePickDialog(ThisActivity.this,
		R.layout.single_dialog, "开始时间", true,
		SinglePickDialog.type_single_start) {
	@Override
	public void callback(String data, int valueFirst,
			int valueSecond) {
		mClinicStartTxt.setText(data);
		hourStart = valueFirst;
		minuteStart = valueSecond;
	}
};
String str = mClinicStartTxt.getText().toString();
if (isFirstSetStart) {
	dialog.setDefault(9, 0);
	isFirstSetStart = false;
} else {
	hourStart = Integer.valueOf(str.substring(0,
			str.indexOf(":")));
	minuteStart = Integer.valueOf(str.substring(str
			.indexOf(":") + 1));
	dialog.setDefault(hourStart, minuteStart);
}
```