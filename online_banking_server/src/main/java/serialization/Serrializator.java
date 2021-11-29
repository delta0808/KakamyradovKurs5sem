package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Model;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Serrializator {
    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gson = builder.create();

    public static String ObjectToJSON(Model model) {
        return gson.toJson(model);
    }

    public static Model JSONToObject(String string, Class<? extends Model> modelType) {
        return gson.fromJson(string, modelType);
    }

    public static String ObjectsToJSON(ArrayList<? extends Model> models) {
        return gson.toJson(models);
    }

    public static String ObjectsToJSON(ArrayList<? extends String> models, boolean isString) {
        return gson.toJson(models);
    }

    public static ArrayList<Model> JSONToObjects(String string, Type type) {
        return gson.fromJson(string, type);
    }

    public static ArrayList<String> JSONToObjects(String string, Type type, boolean isString) {
        return gson.fromJson(string, type);
    }
}
