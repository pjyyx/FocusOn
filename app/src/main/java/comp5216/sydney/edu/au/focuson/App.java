package comp5216.sydney.edu.au.focuson;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * The type App.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}