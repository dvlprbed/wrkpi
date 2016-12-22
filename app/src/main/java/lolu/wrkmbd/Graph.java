package lolu.wrkmbd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.ArrayList;


public class Graph extends View {
    private int maxSize;
    private Paint paint;
    private ArrayList<Integer> graphPoints;
    public Graph(Context context, int maxSize) {
        super(context);
        this.maxSize = maxSize;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        graphPoints = new ArrayList<>();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int maxValue = graphPoints.get(0);
        for (int i = 1; i < graphPoints.size(); ++i) {
            if (graphPoints.get(i) > maxValue) maxValue = graphPoints.get(i);
        }

        int width = 0;
        int height = graphPoints.get(0) * canvas.getHeight() / maxValue;
        Path path = new Path();
        path.moveTo(width, canvas.getHeight() - height);
        for (int i = 1; i < graphPoints.size(); ++i) {
            width = i * (canvas.getWidth() / (graphPoints.size() - 1));
            height = graphPoints.get(i) * canvas.getHeight() / maxValue;
            path.lineTo(width, canvas.getHeight() - height);
        }

        canvas.drawPath(path, paint);
    }

    void addGraphPoint(int pt) {
        if (graphPoints.size() == maxSize) {
            graphPoints.remove(0);
        }
        graphPoints.add(pt);
    }

    void clear() {
        graphPoints.clear();
    }
}
