package github.dyukusi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BnetAPI {
    static JsonObject getJsonByURL(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            return root.getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    static JsonObject getLadder(Region region, int ladderId, String accessToken) {
        String urlStr = String.format(Constant.BASE_URL_OF_LADDER_API.get(region), Integer.toString(ladderId), accessToken);
        return getJsonByURL(urlStr);
    }

    static JsonObject getCurrentSeason(Region region, String accessToken) {
        String urlStr = String.format(Constant.BASE_URL_OF_CURRENT_SEASON_API.get(region), accessToken);
        return getJsonByURL(urlStr);
    }

    static JsonObject getLeague(Region region, int seasonId, int queueId, int leagueId, String accessToken) {
        String urlStr = String.format(Constant.BASE_URL_OF_LEAGUE_API.get(region), seasonId, queueId, leagueId, accessToken);
        return getJsonByURL(urlStr);
    }
}
