package com.example.pr31_glazirinng;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.View;

public class Draw2D extends View {

    private Paint mPaint = new Paint();
    private Rect mRect = new Rect();
    private Bitmap mBitmap;

    public Draw2D(Context context) {
        super(context);
        Resources res = this.getResources();
        mBitmap = BitmapFactory.decodeResource(res, R.drawable.planeoff);

        // Уменьшение размера фотографии
        int newWidth = 1000;
        int newHeight = 1000;
        mBitmap = Bitmap.createScaledBitmap(mBitmap, newWidth, newHeight, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boolean isHorizontal = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(Color.BLACK);
        canvas.drawPaint(mPaint);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.YELLOW);

        int circleX;
        int circleY;
        int rectY;

        if (isHorizontal) {
            circleX = (width - 100);

            rectY = height - 100;
        } else {
            circleX = (width - 100);

            rectY = height - 100;
        }

        canvas.drawCircle(circleX, 200, 120, mPaint);

        mPaint.setColor(Color.parseColor("#006400"));
        canvas.drawRect(0, rectY, width, height, mPaint);

        int bitmapX = (width - mBitmap.getWidth()) / 2;
        int bitmapY = (height - mBitmap.getHeight())-300;
        canvas.drawBitmap(mBitmap, bitmapX, bitmapY, mPaint);

        // Определение размеров экрана
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        // Расчет координат центра экрана и смещений для ромба
        int offsetX;
        int offsetY;

        if (isHorizontal) {
            offsetX = screenWidth / 2;
            offsetY = (int) (height * 0.70);
        } else {
            offsetX = screenWidth / 2;
            offsetY = screenHeight - 300;
        }

        int offsetxy = 200;

        // Рисование ромба
        Path diamondPath = new Path();
        diamondPath.moveTo(offsetX, offsetY + offsetxy);
        diamondPath.lineTo(offsetX - offsetxy, offsetY);
        diamondPath.lineTo(offsetX, offsetY - offsetxy);
        diamondPath.lineTo(offsetX + offsetxy, offsetY);
        diamondPath.close();

        // Закраска ромба красным цветом
        mPaint.setColor(Color.RED);
        canvas.drawPath(diamondPath, mPaint);

        // Наклоненная надпись на одной стороне ромба
        Paint mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);

        int textSize;
        if (isHorizontal) {
            textSize = 50;
        } else {
            textSize = 100;
        }
        mTextPaint.setTextSize(textSize);

        String text = "Hello!";
        float textWidth = mTextPaint.measureText(text);

        // Вычисление координат для наклоненной надписи
        float centerX = offsetX + 160;
        float centerY = offsetY + offsetxy - 160;
        float angle = -45; // Угол поворота в градусах
        float offset = 120; // Смещение текста относительно точки на ребре ромба

        // Координаты надписи
        float x = centerX - (float) (offset * Math.cos(Math.toRadians(angle))) - (textWidth / 2);
        float y = centerY - (float) (offset * Math.sin(Math.toRadians(angle)));

        // Поворот канвы
        canvas.rotate(angle, centerX, centerY);
        canvas.drawText(text, x, y, mTextPaint);
    }
}