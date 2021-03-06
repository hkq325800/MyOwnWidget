package com.iue.pocketdoc.common.widget;

import com.iue.pocketdoc.android.R;
import com.iue.pocketdoc.global.IUETheme;
import com.iue.pocketdoc.utilities.DesityUtil;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 作为导航点使用时
 * mLoadingAnim.setType(1);
 * mLoadingAnim.setRadius(3);
 * mLoadingAnim.focusPoint(0, R.color.circle_white, R.color.circle_gray);
 * mLoadingAnim.setDistance(27);
 * @author Kerchin
 *
 */
public class IueLoadingAnimView extends View {
	static final double trans = 180 / Math.PI;// value()/trans = PI/2、PI、3PI/2
	Context context;
	Point[] point;
	ValueAnimator[] valueAnimator;
	int type;// view的类型 0-loading 1-guide
	int size = 3;// 小点的数量
	int distance = 80;// 每两点间的距离
	int radius = 18;// 小点半径
	int limitTimes = 30;// 最多重复次数
	Paint paint;// loading画笔，聚焦画笔
	boolean isInit = false;// 用于初始化centreX和centreY
	double centreX, centreY;// 控件中心距离

	long duration = 800;// 完成一次波浪的时间 用于loading
	int repeatTimes;// 重复次数 用于loading
	boolean isStart = false;// 防止多次启动动画 用于loading
	int baseOffset = 80;// 基础上升高度 总的一半 用于loading

	Paint paintUnFocus;// 未聚焦画笔 用于guide
	int focusPosition = 0;// 聚焦位置用于guide

	public IueLoadingAnimView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initPaint();
	}

	public IueLoadingAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initPaint();
	}

	public IueLoadingAnimView(Context context) {
		super(context);
		this.context = context;
		initPaint();
	}

	private void initPaint() {
		valueAnimator = new ValueAnimator[size];
		// 未聚焦画笔初始化
		paintUnFocus = new Paint();
		paintUnFocus.setColor(getResources().getColor(R.color.white)); // 设置画笔的颜色
		paintUnFocus.setStyle(Paint.Style.FILL); // 设置空心
		paintUnFocus.setAntiAlias(true); // 消除锯齿
		// 聚焦画笔初始化
		paint = new Paint();
		paint.setColor(IUETheme.getThemeColorID()); // 设置画笔的颜色
		paint.setStyle(Paint.Style.FILL); // 设置空心
		paint.setAntiAlias(true); // 消除锯齿
	}

	private void init() {
		repeatTimes = 1;
		point = new Point[size];
		// 初始化点的半径
		for (int i = 0; i < size; i++) {
			point[i] = new Point();
			point[i].r = radius;
		}
		centreX = getWidth() / 2;
		centreY = getHeight() / 2;
		initPoint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (!isInit && getHeight() != 0) {
			init();
			paint.setStrokeWidth(2);
			isInit = true;
		}
		if (type == 0)
			for (int i = 0; i < size; i++) {
				// if (i == 0)
				// Log.d("point[0].y", point[0].y + "");
				canvas.drawCircle(Float.parseFloat(point[i].x + ""),
						Float.parseFloat(point[i].y + ""), point[i].r, paint);
			}
		else if (type == 1) {
			for (int i = 0; i < size; i++) {
				canvas.drawCircle(Float.parseFloat(point[i].x + ""),
						Float.parseFloat(point[i].y + ""), point[i].r,
						i == focusPosition ? paint : paintUnFocus);
			}
		}
		super.onDraw(canvas);
	}

	private void initPoint() {
		for (int i = 0; i < size; i++) {
			point[i].y = centreY;
		}
		if (size % 2 == 0) {
			final int zhongxinA = size / 2;// 中心后面那个数
			final int zhongxinB = size / 2 - 1;// 中心前面那个数
			for (int i = 0; i < size; i++) {
				if (i == zhongxinA) {
					point[i].x = centreX + distance / 2;
				} else if (i == zhongxinB) {
					point[i].x = centreX - distance / 2;
				} else {
					int juli = i - zhongxinA;
					point[i].x = centreX + distance / 2 + distance * juli;
				}
			}
		} else {
			final int zhongxin = (size - 1) / 2;
			for (int i = 0; i < size; i++) {
				if (i != zhongxin) {
					int juli = i - zhongxin;
					point[i].x = centreX + juli * distance;
				} else {
					point[zhongxin].x = centreX;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			point[i].offset = 0;
		}
	}

	/**
	 * 停止loading动画
	 */
	public void stopRotateAnimation() {
		for (int i = 0; i < size; i++) {
			if (valueAnimator[i] != null)
				valueAnimator[i].end();
		}
	}

	/**
	 * 开始loading动画
	 */
	public void startRotateAnimation() {
		// 补齐不足的数量
		if (size > valueAnimator.length) {
			valueAnimator = new ValueAnimator[size];
		}
		repeatTimes = 1;
		if (!isStart) {
			isStart = true;
			for (int i = 0; i < size; i++) {
				final int j = i;
				valueAnimator[i] = ValueAnimator.ofInt(0, 360);
				valueAnimator[i].setInterpolator(new LinearInterpolator());
				// valueAnimator[i].setRepeatMode(ValueAnimator.RESTART);
				// valueAnimator[i].setRepeatCount(50);
				valueAnimator[i].setDuration(duration);
				ValueAnimator.setFrameDelay(50);
				valueAnimator[i]
						.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(
									ValueAnimator animation) {
								if (isInit) {
									int value = (Integer) animation
											.getAnimatedValue();
									double piValue = value / trans;
									point[j].offset = baseOffset
											* Math.sin(piValue);
									point[j].y = centreY - point[j].offset;
									invalidate();
								}
							}
						});
			}
			valueAnimator[size - 1].addListener(new AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {
				}

				@Override
				public void onAnimationRepeat(Animator animation) {
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					CountDownTimer cdt = new CountDownTimer(500, 500) {
						@Override
						public void onTick(long millisUntilFinished) {
						}

						@Override
						public void onFinish() {
							// Log.d("repeat", repeatTimes + "");
							// if (repeatTimes < limitTimes)
							for (int i = 0; i < size; i++) {
								valueAnimator[i].start();
							}
							// else
							// isStart = false;
							repeatTimes++;
						}
					};
					cdt.start();
				}

				@Override
				public void onAnimationCancel(Animator animation) {
				}
			});
			for (int i = 1; i < size; i++) {
				valueAnimator[i].setStartDelay(125 * i);
			}
			for (int i = 0; i < size; i++) {
				valueAnimator[i].start();
			}
		}
	}

	/**
	 * guide类型入口
	 * 
	 * @param 焦点位置focusPosition
	 * @param 焦点颜色focusColor
	 * @param 非焦点颜色unfocusColor
	 */
	public void focusPoint(int focusPosition, int focusColor, int unfocusColor) {
		paintUnFocus.setColor(getResources().getColor(unfocusColor));
		paint.setColor(getResources().getColor(focusColor));
		this.focusPosition = focusPosition;
		invalidate();
	}

	/**
	 * 设置点的个数
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		if (size < 1) {
			this.size = 3;
		} else {
			this.size = size;
		}
	}

	/**
	 * 设置每两点间的距离
	 * 
	 * @param distance
	 */
	public void setDistance(int distance) {
		int f = DesityUtil.dip2px(context, distance);
		this.distance = f;
	}

	/**
	 * 设置半径
	 * 
	 * @param radius
	 */
	public void setRadius(int radius) {
		int r = DesityUtil.dip2px(context, radius);
		this.radius = r;
		// for (int i = 0; i < size; i++) {
		// point[i].r = radius;
		// }
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            0-loading 1-guide
	 */
	public void setType(int type) {
		this.type = type;
	}

	final class Point {
		double x;
		int r;
		double y;
		/**
		 * 点距离水平线的偏移量
		 */
		double offset;

	}
}
