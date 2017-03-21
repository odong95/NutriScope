package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.lang.ref.WeakReference;

import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFoodFirebaseRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFoodRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFoodRepositoryModule;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.DaggerConsumedFoodRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.DaggerFoodRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.FoodRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.FoodRepositoryModule;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.DaggerHistoryRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepositoryModule;
import edu.utdallas.csdesign.spring17.nutriscope.util.FirebaseLogger;


public class NutriscopeApplication extends Application {

    final private static String TAG = "NutriscopeApplication";
    private static WeakReference<Context> context;

    private HistoryRepositoryComponent historyRepositoryComponent;
    private ConsumedFoodRepositoryComponent consumedFoodRepositoryComponent;
    private FoodRepositoryComponent foodRepositoryComponent;
    private NetComponent netComponent;

    public static Context getContext() {

        return context.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<Context>(this);
        AndroidThreeTen.init(this);
    //    FacebookSdk.sdkInitialize(getApplicationContext());
    //    AppEventsLogger.activateApp(this);

        new FirebaseLogger();

        netComponent = DaggerNetComponent.builder().build();

        historyRepositoryComponent = DaggerHistoryRepositoryComponent.builder()
                .historyRepositoryModule(new HistoryRepositoryModule(new HistoryRepository()))
                .build();

        consumedFoodRepositoryComponent = DaggerConsumedFoodRepositoryComponent.builder()
                .consumedFoodRepositoryModule(new ConsumedFoodRepositoryModule(
                        new ConsumedFoodRepository(
                                historyRepositoryComponent.getHistoryRepository(),
                                new ConsumedFoodFirebaseRepository())))
                .build();

        foodRepositoryComponent = DaggerFoodRepositoryComponent.builder()
                .foodRepositoryModule(new FoodRepositoryModule()).build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public HistoryRepositoryComponent getHistoryRepositoryComponent() {
        return historyRepositoryComponent;
    }

    public ConsumedFoodRepositoryComponent getConsumedFoodRepositoryComponent() {
        return consumedFoodRepositoryComponent;
    }

    public FoodRepositoryComponent getFoodRepositoryComponent() {
        return foodRepositoryComponent;
    }



}

