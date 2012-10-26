package org.gitmad.wreckroll.canvas;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CanvasThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private ControllerBoard board;
    private boolean run = false;

    public CanvasThread(SurfaceHolder surfaceHolder, ControllerBoard panel) {
        this.surfaceHolder = surfaceHolder;
        this.board = panel;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        Canvas c;
        while (run) {
            c = null;
            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    board.onDraw(c);
                }
            } catch (Exception e) {
                Log.w("SHIT", "WTF");
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}