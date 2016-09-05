package ir.adicom.app.puzzleslider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * Created by adicom on 9/5/16.
 */
public class CustomImageView extends ImageView {
    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        shuffleArray(array);
    }

    public int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, -1};
    float lastX,lastY;
    boolean touch;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                touch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if ( !touch) {
                    return true;
                }
                float dx = (event.getX() - lastX);
                float dy = (event.getY() - lastY);
                if (Math.abs(dx) > 20 && Math.abs(dx) > 2 * Math.abs(dy)) {
                    if (dx > 0) {
                        // move To Right
                        int _x = (int) (lastX/len);
                        int _y= (int) (lastY/len);
                        if (_x<3 && _y<4) {
                            Log.e("TAG", "" + array[_y*4+_x]);
                            if(array[_y*4+(_x+1)]==-1) {
                                int t = array[_y*4+(_x+1)];
                                array[_y*4+(_x+1)] = array[_y*4+(_x)];
                                array[_y*4+(_x)] = t;
                            }
                        }
                    } else {
                        // move To Left
                        int _x = (int) (lastX/len);
                        int _y= (int) (lastY/len);
                        if (_x<4 && _y<4 && _x>0) {
                            Log.e("TAG", "" + array[_y*4+_x]);
                            if(array[_y*4+(_x-1)]==-1) {
                                int t = array[_y*4+(_x-1)];
                                array[_y*4+(_x-1)] = array[_y*4+(_x)];
                                array[_y*4+(_x)] = t;
                            }
                        }
                    }
                    touch = false;
                    invalidate();
                }
                if (Math.abs(dy) > 20 && Math.abs(dy) > 2 * Math.abs(dx)) {
                    if (dy > 0) {
                        // move To Down
                        int _x = (int) (lastX/len);
                        int _y= (int) (lastY/len);
                        if (_x<4 && _y<3) {
                            Log.e("TAG", "" + array[_y*4+_x]);
                            if(array[(_y+1)*4+_x]==-1) {
                                int t = array[(_y+1)*4+_x];
                                array[(_y+1)*4+_x] = array[_y*4+_x];
                                array[_y*4+_x] = t;
                            }
                        }
                    } else {
                        // move To Up
                        int _x = (int) (lastX/len);
                        int _y= (int) (lastY/len);
                        if (_x<4 && _y<4 && _y>0) {
                            Log.e("TAG", "" + array[_y*4+_x]);
                            if(array[(_y-1)*4+_x]==-1) {
                                int t = array[(_y-1)*4+_x];
                                array[(_y-1)*4+_x] = array[_y*4+_x];
                                array[_y*4+_x] = t;
                            }
                        }
                    }
                    touch = false;
                    invalidate();
                }
                break;
        }
        return true;
    }

    int len;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        len = width / 4;
        int offset = 5;

        Paint p1 = new Paint();
        p1.setColor(Color.rgb(200,200,180));
        p1.setTextSize(50);
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                String title = "" + array[(i * 4 + j)];
                if (title.equals("-1") == false) {
                    // the display area.
                    Rect areaRect = new Rect(j * len + offset, i * len + offset, j * len + len - offset, i * len + len - offset);
                    canvas.drawRect(areaRect, paint);
                    RectF bounds = new RectF(areaRect);
                    // measure text width
                    bounds.right = p1.measureText(title, 0, title.length());
                    // measure text height
                    bounds.bottom = p1.descent() - p1.ascent();
                    bounds.left += (areaRect.width() - bounds.right) / 2.0f;
                    bounds.top += (areaRect.height() - bounds.bottom) / 2.0f;
                    canvas.drawText(title, bounds.left, bounds.top - p1.ascent(), p1);
                }
            }
    }

    private void shuffleArray(int[] array) {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
