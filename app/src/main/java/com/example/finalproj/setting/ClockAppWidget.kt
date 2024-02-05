package com.example.finalproj.setting

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.TextClock
import com.example.finalproj.R
import com.example.finalproj.firstActivity


/**
 * Implementation of App Widget functionality.
 */
class ClockAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.clock_app_widget)
    var digital_clock = TextClock(context)
    digital_clock.format12Hour = "HH:mm:ss a"
    var time = digital_clock.toString()
    views.setTextViewText(R.id.clock_widget, time)


    var intent = Intent(context, firstActivity::class.java)
    var pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
    views.setOnClickPendingIntent(R.id.clock_widget, pendingIntent)

    appWidgetManager.updateAppWidget(appWidgetId, views)


}