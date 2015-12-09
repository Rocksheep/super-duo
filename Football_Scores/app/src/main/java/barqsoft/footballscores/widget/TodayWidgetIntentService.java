package barqsoft.footballscores.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.models.Match;

public class TodayWidgetIntentService extends IntentService {

    private static final String LOG_TAG = TodayWidgetIntentService.class.getSimpleName();

    public TodayWidgetIntentService() {
        super(TodayWidgetIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Cursor cursor = createMatchCursor();
        if (cursor == null) {
            Log.d(LOG_TAG, "No cursor was created");
            return;
        }
        if (!cursor.moveToFirst()) {
            Log.d(LOG_TAG, "No results were found");
            cursor.close();
            return;
        }

        Match match = Match.createFromCursor(cursor);
        cursor.close();

        updateWidgets(match);
    }

    private Cursor createMatchCursor() {
        Uri matchWithDateUri = DatabaseContract.scores_table.buildScoreWithDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        Cursor cursor = getContentResolver().query(
                matchWithDateUri,
                null,
                null,
                new String[]{dateFormat.format(currentDate)},
                null
        );

        return cursor;
    }

    private void updateWidgets(Match match) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, TodayWidgetProvider.class));

        for (int appWidgetId : appWidgetIds) {
            int layoutId = getLayoutId();
            RemoteViews views = new RemoteViews(getPackageName(), layoutId);

            views.setTextViewText(R.id.widget_home_team, match.getHomeTeam());
            views.setTextViewText(R.id.widget_away_team, match.getAwayTeam());


            String scoreText = Utilies.getScores(match.getHomeGoals(), match.getAwayGoals());
            views.setTextViewText(R.id.widget_score, match.getTime());

            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private int getLayoutId() {
        return R.layout.widget_today_small;
    }
}
