package edu.utdallas.csdesign.spring17.nutriscope.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by john on 2/21/17.
 */

public final class NutritionPersistenceContract {


    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private NutritionPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "username";
    }

    public static abstract class RecipeConsumed implements BaseColumns {
        public static final String TABLE_NAME = "recipe_consumed";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME_RECIPE_ID = "recipe_id";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
    }

    public static abstract class Recipe implements BaseColumns {
        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_RECIPE_ID = "recipe_id";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class Food implements BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_FOOD_GROUP_ID = "food_group_id";
        public static final String COLUMN_NAME_LONG_DESC = "long_desc";
        public static final String COLUMN_NAME_SHORT_DESC = "short_desc";
        public static final String COLUMN_NAME_COMMON_NAMES = "common_names";
        public static final String COLUMN_NAME_MANUFAC_NAME = "manufac_name";
        public static final String COLUMN_NAME_SURVEY = "survey";
        public static final String COLUMN_NAME_REF_DESC = "ref_desc";
        public static final String COLUMN_NAME_REFUSE = "refuse";
        public static final String COLUMN_NAME_SCI_NAME = "sci_name";
        public static final String COLUMN_NAME_NITROGEN_FACTOR = "nitrogen_factor";
        public static final String COLUMN_NAME_PROTEIN_FACTOR = "protein_factor";
        public static final String COLUMN_NAME_FAT_FACTOR = "fat_factor";
        public static final String COLUMN_NAME_CALORIE_FACTOR = "calorie_factor";
    }

    public static abstract class FoodGroup implements BaseColumns {
        public static final String TABLE_NAME = "food_group";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";

    }

    public static abstract class Nutrient implements BaseColumns {
        public static final String TABLE_NAME = "nutrient";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UNITS = "units";
        public static final String COLUMN_TAGNAME = "tagname";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NUM_DECIMAL_PLACES = "num_decimal_places";
        public static final String COLUMN_SR_ORDER = "sr_order";
    }

    public static abstract class CommonNutrient implements BaseColumns {
        public static final String TABLE_NAME = "common_nutrient";
        public static final String COLUMN_ID = "id";
    }

    public static abstract class Nutrition implements BaseColumns {
        public static final String TABLE_NAME = "nutrition";
        public static final String COLUMN_FOOD_ID = "food_id";
        public static final String COLUMN_NUTRIENT_ID = "nutrient_id";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_NUM_DATA_POINTS = "num_data_points";
        public static final String COLUMN_STD_ERROR = "std_error";
        public static final String COLUMN_SOURCE_CODE = "source_code";
        public static final String COLUMN_DERIVATION_CODE = "derivation_code";
        public static final String COLUMN_REFERENCE_FOOD_ID = "reference_food_id";
        public static final String COLUMN_ADDED_NUTRIENT = "added_nutrient";
        public static final String COLUMN_NUM_STUDIENTS = "num_studients";
        public static final String COLUMN_MIN = "min";
        public static final String COLUMN_MAX = "max";
        public static final String COLUMN_DEGREES_FREEDOM_ = "degrees_freedom";
        public static final String COLUMN_LOWER_ERROR_BOUND = "lower_error_bound";
        public static final String COLUMN_UPPER_ERROR_BOUND = "upper_error_bound";
        public static final String COLUMN_COMMENTS = "comments";
        public static final String COLUMN_MODIFICATION_DATE = "modification_date";
        public static final String COLUMN_CONFIDENCE_CODE = "confidence_code";
    }


}
/*
    DROP TABLE IF EXISTS user;
        CREATE TABLE user (
        id int PRIMARY KEY NOT NULL,
        fname text NOT NULL,
        lname text NOT NULL,
        birthday int,
        activity_level REFERENCES activity_level(id)
        );

        DROP TABLE IF EXISTS user_weight;
        CREATE TABLE user_weight (
        user_id int REFERENCES user(id) NOT NULL,
        timestamp int NOT NULL,
        weight int NOT NULL
        );

        DROP TABLE IF EXISTS activity_level;
        CREATE TABLE activity_level (
        id int PRIMARY KEY NOT NULL,
        name text NOT NULL,
        value int NOT NULL
        );

        DROP TABLE IF EXISTS food_consumed;
        CREATE TABLE food_consumed (
        user_id int REFERENCES user(id) NOT NULL,
        recipe_id int REFERENCES recipe(id) NOT NULL,
        time_stamp int NOT NULL,
        quantity int NOT NULL,
        PRIMARY KEY (user_id, recipe_id, time_stamp)
        );

        DROP TABLE IF EXISTS recipe_consumed;
        CREATE TABLE recipe_consumed (
        user_id int REFERENCES user(id) NOT NULL,
        recipe_id int REFERENCES recipe(id) NOT NULL,
        time_stamp int NOT NULL,
        quantity int NOT NULL,
        PRIMARY KEY (user_id, recipe_id, time_stamp)
        );

        DROP TABLE IF EXISTS recipe;
        CREATE TABLE recipe (
        id int PRIMARY KEY NOT NULL,
        user_id int REFERENCES user(id) NOT NULL,
        name text NOT NULL
        );

        DROP TABLE IF EXISTS ingredient;
        CREATE TABLE ingredient (
        food_id int REFERENCES food(id) NOT NULL,
        recipe_id int REFERENCES recipe(id) NOT NULL,
        quantity int NOT NULL,
        PRIMARY KEY (food_id, recipe_id)
        );

        DROP TABLE IF EXISTS food;
        CREATE TABLE food (
        id int NOT NULL,
        user_id int REFERENCES user(id) NOT NULL,
        PRIMARY KEY (id, user_id),
        food_group_id int REFERENCES food_group(id) NOT NULL,
        long_desc text NOT NULL DEFAULT '',
        short_desc text NOT NULL DEFAULT '',
        common_names text NOT NULL DEFAULT '',
        manufac_name text NOT NULL DEFAULT '',
        survey text NOT NULL DEFAULT '',
        ref_desc text NOT NULL DEFAULT '',
        refuse int NOT NULL,
        sci_name text NOT NULL DEFAULT '',
        nitrogen_factor float NOT NULL,
        protein_factor float NOT  NULL,
        fat_factor float NOT NULL,
        calorie_factor float NOT NULL
        );

        DROP TABLE IF EXISTS food_group;
        CREATE TABLE food_group (
        id int PRIMARY KEY NOT NULL,
        name text NOT NULL
        );

        DROP TABLE IF EXISTS nutrient;
        CREATE TABLE nutrient (
        id int PRIMARY KEY NOT NULL,
        units text NOT NULL,
        tagname text NOT NULL DEFAULT '',
        name text NOT NULL,
        num_decimal_places text NOT NULL,
        sr_order int NOT NULL
        );

        DROP TABLE IF EXISTS common_nutrient;
        CREATE TABLE common_nutrient (
        id int PRIMARY KEY REFERENCES nutrient(id)
        );

        DROP TABLE IF EXISTS nutrition;
        CREATE TABLE nutrition (
        food_id int REFERENCES food(id) NOT NULL,
        nutrient_id int REFERENCES nutrient(id) NOT NULL,
        amount float NOT NULL,
        num_data_points int NOT NULL,
        std_error float,
        source_code text NOT NULL,
        derivation_code text,
        reference_food_id REFERENCES food(id),
        added_nutrient text,
        num_studients int,
        min float,
        max float,
        degrees_freedom int,
        lower_error_bound float,
        upper_error_bound float,
        comments text,
        modification_date text,
        confidence_code text,
        PRIMARY KEY(food_id, nutrient_id)
        );

*/