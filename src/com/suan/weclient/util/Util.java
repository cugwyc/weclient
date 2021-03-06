package com.suan.weclient.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suan.weclient.R;

public class Util {
	public static Bitmap roundCorner(Bitmap src, float round) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();

		// create bitmap output
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		// set canvas for painting
		Canvas canvas = new Canvas(result);
		canvas.drawARGB(0, 0, 0, 0);

		// config paint
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);

		// config rectangle for embedding
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);

		// draw rect to canvas
		canvas.drawRoundRect(rectF, round, round, paint);

		// create Xfer mode
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// draw source image to canvas
		canvas.drawBitmap(src, rect, rect, paint);

		// return final image
		return result;
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Dialog createLoadingDialog(Context context,
			String loadingText, boolean cancelable) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView outerImg = (ImageView) v.findViewById(R.id.loading_img_outer);
		ImageView innerImg = (ImageView) v.findViewById(R.id.loading_img_inner);
		// 加载动画

		Animation outerRotateAnimation = new RotateAnimation(0, 360,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		outerRotateAnimation.setRepeatCount(-1);
		outerRotateAnimation.setDuration(1000);
		outerRotateAnimation.setInterpolator(new LinearInterpolator());
		Animation innerRotateAnimation = new RotateAnimation(360, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		innerRotateAnimation.setRepeatCount(-1);
		innerRotateAnimation.setDuration(1300);
		innerRotateAnimation.setInterpolator(new LinearInterpolator());
		// 使用ImageView显示动画
		outerImg.startAnimation(outerRotateAnimation);
		innerImg.startAnimation(innerRotateAnimation);

		TextView loadingTextView = (TextView) v.findViewById(R.id.loading_text);
		loadingTextView.setText("" + loadingText);

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(cancelable);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;

	}

	@SuppressWarnings("deprecation")
	public static Dialog createEnsureDialog(
			OnClickListener sureOnClickListener, boolean cancelVisible,
			Context context, String titleText, boolean cancelable) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_ensure_layout, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		ImageButton sureButton = (ImageButton) v
				.findViewById(R.id.dialog_ensure_button_sure);

		RelativeLayout cancelLayout = (RelativeLayout) v
				.findViewById(R.id.dialog_ensure_layout_cancel);

		sureButton.setOnClickListener(sureOnClickListener);

		TextView titleTextView = (TextView) v
				.findViewById(R.id.dialog_ensure_text_title);
		titleTextView.setText("" + titleText);

		final Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		if (cancelVisible) {

			ImageButton cancelButton = (ImageButton) v
					.findViewById(R.id.dialog_ensure_button_cancel);
			cancelButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					loadingDialog.dismiss();
					
				}
			});
		} else {
			cancelLayout.setVisibility(View.GONE);

		}
		loadingDialog.setCancelable(cancelable);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;

	}

}
