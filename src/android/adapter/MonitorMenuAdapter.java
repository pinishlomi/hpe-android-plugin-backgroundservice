package com.hpe.hybridsitescope.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 */
public class MonitorMenuAdapter extends ArrayAdapter<String> {
    public static final String TAG = MonitorMenuAdapter.class.getSimpleName();
    private boolean isMonitorRunEnabled;
    private final Context context;
    private final int textViewResourceId;
    private final String[] objects;


    public MonitorMenuAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.objects = objects;
    }

    public void setMonitorRunEnabled(boolean monitorRunEnabled) {
        isMonitorRunEnabled = monitorRunEnabled;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Button view = (Button)LayoutInflater.from(context).inflate(textViewResourceId, parent, false);

        if (view == null) return super.getView(position, convertView, parent);

        view.setText(objects[position]);
        if (position == 0 ) {
                Log.d(TAG, "Run monitor button state = " + isMonitorRunEnabled);
                view.setEnabled(isMonitorRunEnabled);
        }
        return view;
    }
}
