package edu.utdallas.csdesign.spring17.nutriscope.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by john on 2/21/17.
 */

public class NutritionDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Tasks.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String INTEGER_TYPE = " INTEGER";

    private static String FLOAT_TYPE = " FLOAT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
                    "    DROP TABLE IF EXISTS user;\n" +
                    "        CREATE TABLE user (\n" +
                    "        id int PRIMARY KEY NOT NULL,\n" +
                    "        fname text NOT NULL,\n" +
                    "        lname text NOT NULL,\n" +
                    "        birthday int,\n" +
                    "        activity_level REFERENCES activity_level(id)\n" +
                    "        );\n" +
                    "        DROP TABLE IF EXISTS user_weight;\n" +
                    "        CREATE TABLE user_weight (\n" +
                    "        user_id int REFERENCES user(id) NOT NULL,\n" +
                    "        timestamp int NOT NULL,\n" +
                    "        weight int NOT NULL\n" +
                    "        );\n" +
                    "        DROP TABLE IF EXISTS activity_level;\n" +
                    "        CREATE TABLE activity_level (\n" +
                    "        id int PRIMARY KEY NOT NULL,\n" +
                    "        name text NOT NULL,\n" +
                    "        value int NOT NULL\n" +
                    "        );\n" +
                    "        DROP TABLE IF EXISTS food_consumed;\n" +
                    "        CREATE TABLE food_consumed (\n" +
                    "        user_id int REFERENCES user(id) NOT NULL,\n" +
                    "        recipe_id int REFERENCES recipe(id) NOT NULL,\n" +
                    "        time_stamp int NOT NULL,\n" +
                    "        quantity int NOT NULL,\n" +
                    "        PRIMARY KEY (user_id, recipe_id, time_stamp)\n" +
                    "        );\n" +
                    "\n" +
                    "        DROP TABLE IF EXISTS recipe_consumed;\n" +
                    "        CREATE TABLE recipe_consumed (\n" +
                    "        user_id int REFERENCES user(id) NOT NULL,\n" +
                    "        recipe_id int REFERENCES recipe(id) NOT NULL,\n" +
                    "        time_stamp int NOT NULL,\n" +
                    "        quantity int NOT NULL,\n" +
                    "        PRIMARY KEY (user_id, recipe_id, time_stamp)\n" +
                    "        );\n" +
                    "\n" +
                    "        DROP TABLE IF EXISTS recipe;\n" +
                    "        CREATE TABLE recipe (\n" +
                    "        id int PRIMARY KEY NOT NULL,\n" +
                    "        user_id int REFERENCES user(id) NOT NULL,\n" +
                    "        name text NOT NULL\n" +
                    "        );\n" +
                    "\n" +
                    "        DROP TABLE IF EXISTS ingredient;\n" +
                    "        CREATE TABLE ingredient (\n" +
                    "        food_id int REFERENCES food(id) NOT NULL,\n" +
                    "        recipe_id int REFERENCES recipe(id) NOT NULL,\n" +
                    "        quantity int NOT NULL,\n" +
                    "        PRIMARY KEY (food_id, recipe_id)\n" +
                    "        );\n" +
                    "\n" +
                    "        DROP TABLE IF EXISTS food;\n" +
                    "        CREATE TABLE food (\n" +
                    "        id int NOT NULL,\n" +
                    "        user_id int REFERENCES user(id) NOT NULL,\n" +
                    "        PRIMARY KEY (id, user_id),\n" +
                    "        food_group_id int REFERENCES food_group(id) NOT NULL,\n" +
                    "        long_desc text NOT NULL DEFAULT '',\n" +
                    "        short_desc text NOT NULL DEFAULT '',\n" +
                    "        common_names text NOT NULL DEFAULT '',\n" +
                    "        manufac_name text NOT NULL DEFAULT '',\n" +
                    "        survey text NOT NULL DEFAULT '',\n" +
                    "        ref_desc text NOT NULL DEFAULT '',\n" +
                    "        refuse int NOT NULL,\n" +
                    "        sci_name text NOT NULL DEFAULT '',\n" +
                    "        nitrogen_factor float NOT NULL,\n" +
                    "        protein_factor float NOT  NULL,\n" +
                    "        fat_factor float NOT NULL,\n" +
                    "        calorie_factor float NOT NULL\n" +
                    "        );\n" +
                    "\n" +
                    "        DROP TABLE IF EXISTS food_group;\n" +
                    "        CREATE TABLE food_group (\n" +
                    "        id int PRIMARY KEY NOT NULL,\n" +
                    "        name text NOT NULL\n" +
                    "        );\n" +
                    "\n" +
                    "        DROP TABLE IF EXISTS nutrient;\n" +
                    "        CREATE TABLE nutrient (\n" +
                    "        id int PRIMARY KEY NOT NULL,\n" +
                    "        units text NOT NULL,\n" +
                    "        tagname text NOT NULL DEFAULT '',\n" +
                    "        name text NOT NULL,\n" +
                    "        num_decimal_places text NOT NULL,\n" +
                    "        sr_order int NOT NULL\n" +
                    "        );\n" +
                    "\n" +
                    "        DROP TABLE IF EXISTS common_nutrient;\n" +
                    "        CREATE TABLE common_nutrient (\n" +
                    "        id int PRIMARY KEY REFERENCES nutrient(id)\n" +
                    "        );\n" +
                    "\n" +
                    "        DROP TABLE IF EXISTS nutrition;\n" +
                    "        CREATE TABLE nutrition (\n" +
                    "        food_id int REFERENCES food(id) NOT NULL,\n" +
                    "        nutrient_id int REFERENCES nutrient(id) NOT NULL,\n" +
                    "        amount float NOT NULL,\n" +
                    "        num_data_points int NOT NULL,\n" +
                    "        std_error float,\n" +
                    "        source_code text NOT NULL,\n" +
                    "        derivation_code text,\n" +
                    "        reference_food_id REFERENCES food(id),\n" +
                    "        added_nutrient text,\n" +
                    "        num_studients int,\n" +
                    "        min float,\n" +
                    "        max float,\n" +
                    "        degrees_freedom int,\n" +
                    "        lower_error_bound float,\n" +
                    "        upper_error_bound float,\n" +
                    "        comments text,\n" +
                    "        modification_date text,\n" +
                    "        confidence_code text,\n" +
                    "        PRIMARY KEY(food_id, nutrient_id)\n" +
                    "        );"



            ;

    public NutritionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }


}


/*
            "CREATE TABLE " + NutritionPersistenceContract.User.TABLE_NAME + " (" +
                    NutritionPersistenceContract.User._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    NutritionPersistenceContract.User.COLUMN_NAME_USERNAME + TEXT_TYPE +
                    " );" +
            "CREATE TABLE " + NutritionPersistenceContract.RecipeConsumed.TABLE_NAME + " (" +
                    NutritionPersistenceContract.RecipeConsumed._ID + INTEGER_TYPE + " PRIMARY KEY NOT NULL," +
                    NutritionPersistenceContract.RecipeConsumed.COLUMN_USER_ID + " "  + INTEGER_TYPE + " REFERENCES " + NutritionPersistenceContract.User.TABLE_NAME + "(" + NutritionPersistenceContract.User._ID + ")," +
                    NutritionPersistenceContract.RecipeConsumed.COL
 */