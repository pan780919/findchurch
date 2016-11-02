package com.jackpan.taiwamrain.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;

/**
 * Created by redjack on 15/10/1.
 */
public class ImageHelper {


    public static Bitmap convertToRoundCornerBitmap(Context context, Bitmap bitmap, float radiusInDp)
    {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int color = 0xffffffff;
        int radiusInPx = (int) convertDpToPixel(context, radiusInDp);
        Paint paint = new Paint();

        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, radiusInPx, radiusInPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static float convertDpToPixel(Context context, float dp)
    {
        return dp * getDensity(context);
    }

    public static float convertPixelToDp(Context context, float pixel)
    {
        return pixel / getDensity(context);
    }

    public static float getDensity(Context context)
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }
}
