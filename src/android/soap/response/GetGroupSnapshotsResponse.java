package com.hpe.hybridsitescope.soap.response;


import android.util.Log;

import com.hpe.hybridsitescope.data.EntityHandler;
import com.hpe.hybridsitescope.data.Group;
import com.hpe.hybridsitescope.soap.request.SoapCallException;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 21.04.11
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
public class GetGroupSnapshotsResponse extends AbstractSoapResponse<List<Group>> {
    public static final String TAG = GetGroupSnapshotsResponse.class.getSimpleName();

    @Override
    public List<Group> getResult() throws SoapCallException {
        super.getResult();

        SoapObject groups = (SoapObject)soapResponse;
        Log.d(TAG, "Got a response: " + groups.toString());

        final List<Group> groupList = new ArrayList<Group>();

        for (int i = 0; i < groups.getPropertyCount(); i++) {
            Group group = new Group(siteScopeServer);
            EntityHandler entityHandler = new EntityHandler(group);
            group = (Group) entityHandler.getGroup(groups, i);
            groupList.add(group);
        }
        return groupList;
    }
}
