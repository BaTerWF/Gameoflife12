package com.example.gameoflife2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;



public class GameOfLifeView extends SurfaceView implements Runnable {

    // Размер ячейки по умолчанию
    public static  int DEFAULT_SIZE = 10;
    // Цвет по умолчанию живого цвета (в нашем случае белый)
    public static final int DEFAULT_ALIVE_COLOR = Color.WHITE;
    // Цвет по умолчанию мертвого цвета (в нашем случае черный)
    public static final int DEFAULT_DEAD_COLOR = Color.BLACK;
    // Поток, который будет отвечать за управление эволюцией Мира
    private  Thread thread;
    // Логическое значение, указывающее, развивается мир или нет.
    public  boolean isRunning = false;
    private int columnWidth = 1;
    private int rowHeight = 1;
    private int nbColumns = 1;
    private int nbRows = 1;
    private World world;




    // Вспомогательные объекты: экземпляр Rectangle и экземпляр Paint, используемые для рисования элементов.
    private Rect r = new Rect();
    private Paint p = new Paint();



    public GameOfLifeView(Context context) {
        super(context);
        initWorld();
    }

    public GameOfLifeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWorld();
    }

    @Override
    public void run() {
        // пока мир развивается
        while (isRunning) {
            if (!getHolder().getSurface().isValid())
                continue;

            // Пауза 300 мс для лучшей визуализации эволюции мира
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }

            Canvas canvas = getHolder().lockCanvas();
            world.nextGeneration();
            drawCells(canvas);
            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    public void start() {
        // Мир развивается
        isRunning = true;
        thread = new Thread(this);
        // мы начинаем Нить для эволюции Мира
        thread.start();
    }

    public  void stop() {
        isRunning = false;

        while (true) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
            break;
        }

    }



    private void initWorld() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        // вычисляем количество столбцов и строк для нашего Мира
        nbColumns = point.x / DEFAULT_SIZE;
        nbRows = point.y / DEFAULT_SIZE;
        // вычисляем ширину столбца и высоту строки
        columnWidth = point.x / nbColumns;
        rowHeight = point.y / nbRows;
        world = new World(nbColumns, nbRows);
    }

    // Метод рисования каждой клетки мира на холсте
    private void drawCells(Canvas canvas) {
        for (int i = 0; i < nbColumns; i++) {
            for (int j = 0; j < nbRows; j++) {
                Cell cell = world.get(i, j);
                r.set((cell.x * columnWidth) - 1, (cell.y * rowHeight) - 1,
                        (cell.x * columnWidth + columnWidth) - 1,
                        (cell.y * rowHeight + rowHeight) - 1);
                // we change the color according the alive status of the cell
                p.setColor(cell.alive ? DEFAULT_ALIVE_COLOR : DEFAULT_DEAD_COLOR);
                canvas.drawRect(r, p);
            }
        }
    }


    // Мы позволяем пользователю взаимодействовать с Ячейками Мира
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //мы получаем координаты касания и преобразуем их в координаты для доски
            int i = (int) (event.getX() / columnWidth);
            int j = (int) (event.getY() / rowHeight);
            // мы получаем ячейку, связанную с этими позициями
            Cell cell = world.get(i, j);
            // мы вызываем метод инвертирования ячейки, чтобы изменить ее состояние
            cell.invert();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}

