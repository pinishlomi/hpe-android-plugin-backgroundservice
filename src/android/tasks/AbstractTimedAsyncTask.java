package android.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 *
 * Added timeout behavior to standard AsyncTask.
 */
public abstract class AbstractTimedAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    public static final String TAG = AbstractTimedAsyncTask.class.getSimpleName();

    protected long timeout = 60*1000; //in milliseconds. Default is 1 min

    protected final TimeoutWatcher watcher = new TimeoutWatcher();

    private static final List<AbstractTimedAsyncTask> ACTIVE_TASKS = new LinkedList<AbstractTimedAsyncTask>();

    public static void cancelTask(AbstractTimedAsyncTask task) {
         //query active task list
        int taskIndex = ACTIVE_TASKS.indexOf(task);
        if (taskIndex == -1) return;
        task.cancel(true);
        ACTIVE_TASKS.remove(taskIndex);
        Log.d(TAG, "Task removed successfully from a list");
    }

    public static void cancelAll() {
        for (AbstractTimedAsyncTask task : ACTIVE_TASKS) {
            cancelTask(task);
        }
    }

    public void executeTask() {
        ACTIVE_TASKS.add((AbstractTimedAsyncTask) this.execute((Params[])null));
        Log.d(TAG, "New task arrived");
    }

    protected AbstractTimedAsyncTask(long timeout) {
        this.timeout = timeout;
    }

    private class TimeoutWatcher extends Thread {

        public TimeoutWatcher() {
           setName("TimeoutWatcher");
        }

        @Override
        public void run() {
            super.run();
            try {
                    AbstractTimedAsyncTask.this.get(timeout, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e) {
                Log.d(TAG, watcher.getName()+" interrupted");
                watcher.interrupt();
                AbstractTimedAsyncTask.this.cancel(true);
            }
            catch (ExecutionException e) {
                watcher.interrupt();
            }
            catch (TimeoutException e) {
                AbstractTimedAsyncTask.this.cancel(true);
                onTimeout();
                watcher.interrupt();
            }
            catch (CancellationException e) {
               Log.d(TAG, watcher.getName() + " cancelled");
               watcher.interrupt();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        watcher.start();
        Log.d(TAG, watcher.getName()+" started");
    }

    @Override
    protected void onPostExecute(Result result) {
        if (!watcher.isInterrupted()) {
            watcher.interrupt();
        }

        final int size = ACTIVE_TASKS.size();

        if(size > 0) ACTIVE_TASKS.remove(size-1);

    }

    @Override
    protected void onCancelled() {
        Log.d(TAG, "onCancelled() invoked");
    }

    protected abstract void onTimeout();


}
