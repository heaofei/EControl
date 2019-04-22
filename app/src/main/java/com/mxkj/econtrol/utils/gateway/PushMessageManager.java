package com.mxkj.econtrol.utils.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lidongxing on 2018/3/1 0001.
 */

public class PushMessageManager {
    private static final PushMessageManager ourInstance = new PushMessageManager();

    public static PushMessageManager getInstance() {
        return ourInstance;
    }

    private Map<String, List<IPassThroughMessageListener>> passThroughMessageListeners;

    private PushMessageManager() {
        passThroughMessageListeners = new HashMap<>();
    }

    public void addPassThroughMessageListener(String title, IPassThroughMessageListener listener) {
        synchronized (ourInstance) {
            if(passThroughMessageListeners.containsKey(title)) {
                List<IPassThroughMessageListener> listeners = passThroughMessageListeners.get(title);

                if(listeners==null) {
                    listeners = new ArrayList<>();
                }

                listeners.add(listener);
            } else {
                List<IPassThroughMessageListener> listeners = new ArrayList<>();

                listeners.add(listener);

                passThroughMessageListeners.put(title, listeners);
            }
        }
    }

    public void removePassThroughMessageListener(String title, IPassThroughMessageListener listener) {
        synchronized (ourInstance) {
            if(passThroughMessageListeners.containsKey(title)) {
                List<IPassThroughMessageListener> listeners = passThroughMessageListeners.get(title);

                if(listeners!=null) {
                    listeners.remove(listener);
                }
            }
        }
    }

    public void onReceivePassThroughMessage(String title, String message) {
        synchronized (ourInstance) {
            if(passThroughMessageListeners.containsKey(title)) {
                List<IPassThroughMessageListener> listeners = passThroughMessageListeners.get(title);

                if(listeners!=null && listeners.size()>0) {
                    for (IPassThroughMessageListener listener : listeners) {
                        listener.onReceiveMessage(message);
                    }
                }
            }
        }
    }
}
