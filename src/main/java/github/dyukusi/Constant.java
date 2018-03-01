package github.dyukusi;

import java.util.HashMap;

public class Constant {
    public static int WAIT_TIME_PER_LADDER_ANALYZATION = 100; // ms

    public static final HashMap<Region, String> BASE_URL_OF_LADDER_API = new HashMap<Region, String>() {
        {
            put(Region.US, "https://us.api.battle.net/data/sc2/ladder/%s?locale=en_US&access_token=%s");
            put(Region.EU, "https://eu.api.battle.net/data/sc2/ladder/%s?locale=en_GB&access_token=%s");
            put(Region.KR, "https://kr.api.battle.net/data/sc2/ladder/%s?locale=ko_KR&access_token=%s");
        }
    };

    public static final HashMap<Region, String> BASE_URL_OF_CURRENT_SEASON_API = new HashMap<Region, String>() {
        {
            put(Region.US, "https://us.api.battle.net/data/sc2/season/current?access_token=%s");
            put(Region.EU, "https://eu.api.battle.net/data/sc2/season/current?access_token=%s");
            put(Region.KR, "https://kr.api.battle.net/data/sc2/season/current?access_token=%s");
        }
    };

    public static final HashMap<Region, String> BASE_URL_OF_LEAGUE_API = new HashMap<Region, String>() {
        {
            put(Region.US, "https://us.api.battle.net/data/sc2/league/%d/%d/0/%d?locale=en_US&access_token=%s");
            put(Region.EU, "https://eu.api.battle.net/data/sc2/league/%d/%d/0/%d?locale=en_GB&access_token=%s");
            put(Region.KR, "https://kr.api.battle.net/data/sc2/league/%d/%d/0/%d?locale=ko_KR&access_token=%s");
        }
    };

    public static int LOTV_1V1_QUEUE_ID = 201;

    public static final int[] LEAGUE_IDS = {
            0, // BRONZE
            1, // SILVER
            2, //  GOLD
            3, // PLATINUM
            4, // DIAMOND
            5, // MASTER
            6  // GRANDMASTER
    };
}
