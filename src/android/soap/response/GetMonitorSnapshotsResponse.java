package com.hpe.hybridsitescope.soap.response;


import android.util.Log;
import com.hpe.hybridsitescope.data.EntityHandler;
import com.hpe.hybridsitescope.data.Monitor;
import com.hpe.hybridsitescope.soap.request.SoapCallException;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 19.04.11
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class GetMonitorSnapshotsResponse extends AbstractSoapResponse <List<Monitor>>{
    public static final String TAG = GetMonitorSnapshotsResponse.class.getSimpleName();

    @Override
    public List<Monitor> getResult() throws SoapCallException {
        super.getResult();

        SoapObject monitors = (SoapObject)soapResponse;
        Log.d(TAG, "Got a response: " + monitors.toString());

        final List<Monitor> monitorList = new ArrayList<Monitor>();

        for (int i = 0; i < monitors.getPropertyCount(); i++) {
            Monitor monitor = new Monitor(siteScopeServer);
            EntityHandler entityHandler = new EntityHandler(monitor);
            monitor = (Monitor) entityHandler.getMonitor(monitors, i);
            monitorList.add(monitor);
        }

        return monitorList;
    }
}
