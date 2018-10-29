package com.example.baking.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by diegocotta on 29/10/2018.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this);
    }
}