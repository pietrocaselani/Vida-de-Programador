package com.pc.programmerslife.views;

import com.pc.programmerslife.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class PreviewView extends View {
	private String text;
	
	public PreviewView(Context context) {
		super(context);
	}

	public PreviewView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vdp_preview);
		canvas.drawBitmap(bitmap, 0, 0, null);
		
		int halfY = getHeight() / 2;
		int y = halfY + halfY / 2;
		
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setTextSize(19);
		p.setTypeface(Typeface.DEFAULT_BOLD);
		int x = (getWidth() - (int) p.measureText(text)) / 2;
		
		canvas.drawText(text, x, y, p);
	}
	
	public void drawText(String text) {
		this.text = "#" + text;
	}
}