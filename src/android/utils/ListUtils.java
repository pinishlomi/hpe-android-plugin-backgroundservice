package com.hpe.hybridsitescope.utils;

import com.hpe.hybridsitescope.data.Entity;
import com.hpe.hybridsitescope.data.SiteScopeServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * To change this template use File | Settings | File Templates.
 */
public final class ListUtils {
    /**
     * Transforms given entity list into the map in the following format:
     * {sis_server=>{entity_type(monitor OR group)=>list_of_entity_paths}}
     * Further this map is used to issue
     * @return
     */
    public static final Map<SiteScopeServer, Map<String, List<String>>> transformEntityListToRequestMap(final List<? extends Entity> entityList, boolean checkIsFavorite) {

        final Map<SiteScopeServer, Map<String, List<String>>> requestMap = new HashMap<SiteScopeServer, Map<String, List<String>>>();
        Map<String, List<String>> entityPathMap;
        List<String> paths;

        int size = entityList.size();
        for (int i = 0; i < size; i++) {
            Entity entity = entityList.get(i);
            if (checkIsFavorite && !entity.isFavorite()) continue;
            String entityType = entity.getEntityType();    //Monitor or Group
            String fullPath = entity.getFullPath();
            SiteScopeServer siteScopeServer = entity.getSiteScopeServer();

            if (requestMap.containsKey(siteScopeServer)) {
               entityPathMap = requestMap.get(siteScopeServer);
               if (entityPathMap.containsKey(entityType)) {
                  paths = entityPathMap.get(entityType);
                  if (!paths.contains(fullPath)) paths.add(fullPath);
               }
               else {
                   paths = new ArrayList<String>();
                   paths.add(fullPath);
                   entityPathMap.put(entityType, paths);
               }
            } else {
                  entityPathMap = new HashMap<String, List<String>>();
                  paths = new ArrayList<String>();
                  paths.add(fullPath);
                  entityPathMap.put(entityType, paths);
                  requestMap.put(siteScopeServer, entityPathMap);
            }
        }

        return requestMap;
    }
}
