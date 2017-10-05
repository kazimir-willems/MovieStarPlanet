package luca.com.moviestartplanet;

import android.app.Application;
import android.content.Context;

import com.onesignal.OneSignal;

/**
 * Created by Arthur on 9/26/2017.
 */

public class MovieStarApplication extends Application{
    private static MovieStarApplication instance;

    public static String ADMOB_BANNER_UNIT_ID = "ca-app-pub-1588855366653236/2696278707";
    public static String ADMOB_INTERSTITIAL_UNIT_ID = "ca-app-pub-1588855366653236/4173011907";

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        instance = this;

    }

    public static synchronized MovieStarApplication getInstance() {
        return instance;
    }
}
