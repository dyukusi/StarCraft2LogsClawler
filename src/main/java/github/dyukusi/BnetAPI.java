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

    static JsonObject getLadder(int ladderId, String locale, String accessToken) {
        String urlStr = String.format(Constant.BNET_API.SC2.BASE_URL_OF_LADDER_API, Integer.toString(ladderId), locale, accessToken);
        return getJsonByURL(urlStr);
    }

    static JsonObject getProfile(int userId, String userName, String locale, String apiKey) {
        String urlStr = String.format(Constant.BNET_API.SC2.BASE_URL_OF_PROFILE_API, Integer.toString(userId), userName, locale, apiKey);
        return getJsonByURL(urlStr);
    }

    static JsonObject getPersonLadder(int userId, String userName, String locale, String apiKey) {
        String urlStr = String.format(Constant.BNET_API.SC2.BASE_URL_OF_SPECIFIC_PERSON_LADDER_API, Integer.toString(userId), userName, locale, apiKey);
        return getJsonByURL(urlStr);
    }

    static JsonObject getCurrentSeason(String accessToken) {
        String urlStr = String.format(Constant.BNET_API.SC2.BASE_URL_OF_CURRENT_SEASON_API, accessToken);
        return getJsonByURL(urlStr);
    }

    static JsonObject getLeague(int seasonId, int queueId, int leagueId, String accessToken) {
        String urlStr = String.format(Constant.BNET_API.SC2.BASE_URL_OF_LEAGUE_API, seasonId, queueId, leagueId, Constant.BNET_API.LOCALE.US, accessToken);
        return getJsonByURL(urlStr);
    }
}
