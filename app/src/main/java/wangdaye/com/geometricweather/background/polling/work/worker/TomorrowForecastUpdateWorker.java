package wangdaye.com.geometricweather.background.polling.work.worker;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.hilt.Assisted;
import androidx.hilt.work.WorkerInject;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.futures.SettableFuture;

import java.util.List;

import wangdaye.com.geometricweather.background.polling.PollingManager;
import wangdaye.com.geometricweather.common.basic.models.Location;
import wangdaye.com.geometricweather.location.LocationHelper;
import wangdaye.com.geometricweather.remoteviews.presenters.notification.ForecastNotificationIMP;
import wangdaye.com.geometricweather.weather.WeatherHelper;

public class TomorrowForecastUpdateWorker extends AsyncUpdateWorker {

    @WorkerInject
    public TomorrowForecastUpdateWorker(@Assisted @NonNull Context context,
                                        @Assisted @NonNull WorkerParameters workerParams,
                                        LocationHelper locationHelper,
                                        WeatherHelper weatherHelper) {
        super(context, workerParams, locationHelper, weatherHelper);
    }

    @Override
    public void updateView(Context context, Location location) {
        if (ForecastNotificationIMP.isEnable(context, false)) {
            ForecastNotificationIMP.buildForecastAndSendIt(context, location, false);
        }
    }

    @Override
    public void updateView(Context context, List<Location> locationList) {
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void handleUpdateResult(SettableFuture<Result> future, boolean failed) {
        future.set(failed ? Result.failure() : Result.success());
        PollingManager.resetTomorrowForecastBackgroundTask(
                getApplicationContext(), false, true);
    }
}
