package booking;

import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadJson {

    public static JSONArray getJsonArray(Path path){
        String data = "";
        try{
            data = new String(Files.readAllBytes(Paths.get(path.toUri())));
        }catch (IOException e){
            e.getStackTrace();
        }
        JSONArray jsonArray = new JSONArray(data);
        int size = jsonArray.length();

        return jsonArray;
    }

    public static Accommodation readFile(Path path, int index){
        JSONArray jsonArray = getJsonArray(path);

        JSONObject jsonObject = jsonArray.getJSONObject(index);
        String accType = jsonObject.getString("accType");
        String roomName = jsonObject.getString("roomName");
        String numOfPersons = jsonObject.getString("numOfPersons");
        String area = String.valueOf(jsonObject.getJSONObject("area"));
        String stars = jsonObject.getString("stars");
        String numOfReviews = jsonObject.getString("numOfReviews");
        String roomImage = jsonObject.getString("roomImage");
        String pricePerNight = jsonObject.getString("pricePerNight");

        Accommodation accommodation = new Accommodation(jsonObject);

        return accommodation;
    }
}


