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
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.DaggerNutritionRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionFirebaseRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionRepositoryModule;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.DaggerUserManagerComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManager;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManagerComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManagerModule;
import edu.utdallas.csdesign.spring17.nutriscope.util.FirebaseLogger;


public class NutriscopeApplication extends Application {

    final private static String TAG = "NutriscopeApplication";
    private static WeakReference<Context> context;

    private HistoryRepositoryComponent historyRepositoryComponent;
    private ConsumedFoodRepositoryComponent consumedFoodRepositoryComponent;
    private FoodRepositoryComponent foodRepositoryComponent;
    private NutritionRepositoryComponent nutritionRepositoryComponent;
    private NetComponent netComponent;
    private UserManagerComponent userManagerComponent;

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

        userManagerComponent = DaggerUserManagerComponent.builder()
                .userManagerModule(new UserManagerModule(new UserManager())).build();

        historyRepositoryComponent = DaggerHistoryRepositoryComponent.builder()
                .historyRepositoryModule(new HistoryRepositoryModule(new HistoryRepository(userManagerComponent.getUserManager())))
                .build();

        foodRepositoryComponent = DaggerFoodRepositoryComponent.builder()
                .foodRepositoryModule(new FoodRepositoryModule()).build();

        nutritionRepositoryComponent = DaggerNutritionRepositoryComponent.builder()
                .nutritionRepositoryModule(new NutritionRepositoryModule(
                        new NutritionRepository(new NutritionFirebaseRepository())))
                .build();





        consumedFoodRepositoryComponent = DaggerConsumedFoodRepositoryComponent.builder()
                .consumedFoodRepositoryModule(new ConsumedFoodRepositoryModule(
                        new ConsumedFoodRepository(
                                foodRepositoryComponent.getFoodRepository(),
                                historyRepositoryComponent.getHistoryRepository(),
                                new ConsumedFoodFirebaseRepository(userManagerComponent.getUserManager()),
                                nutritionRepositoryComponent.getNutritionRepository())))
                .build();


       // SQLiteDatabase db = new CategoryDbHelper(getContext()).getReadableDatabase();
       // Cursor cursor = db.rawQuery("select * from category", null);
        //Log.d(TAG," count " + cursor.getCount());
       // cursor.close();

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

    public NutritionRepositoryComponent getNutritionRepositoryComponent() {
        return nutritionRepositoryComponent;
    }

    public UserManagerComponent getUserManagerComponent() {
        return userManagerComponent;
    }
}

