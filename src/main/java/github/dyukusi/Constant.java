package github.dyukusi;

public class Constant {
    public static int WAIT_TIME_PER_LADDER_ANALYZATION = 100; // ms

    public static class BNET_API {
        public static class LOCALE {
            public static final String US = "en_US";
        }

        public static class SC2 {
            public static final String BASE_URL_OF_LADDER_API = "https://us.api.battle.net/data/sc2/ladder/%s?locale=%s&access_token=%s";
            public static final String BASE_URL_OF_PROFILE_API = "https://us.api.battle.net/sc2/profile/%s/1/%s/?locale=%s&apikey=%s";
            public static final String BASE_URL_OF_SPECIFIC_PERSON_LADDER_API = "https://us.api.battle.net/sc2/profile/%s/1/%s/ladders?locale=%s&apikey=%s";
            public static final String BASE_URL_OF_CURRENT_SEASON_API = "https://us.api.battle.net/data/sc2/season/current?access_token=%s";
            public static final String BASE_URL_OF_LEAGUE_API = "https://us.api.battle.net/data/sc2/league/%d/%d/0/%d?locale=%s&access_token=%s";
        }
    }

    public static int LOTV_1V1_QUEUE_ID = 201;

    public static final int[] LEAGUE_IDS = {
            0, // BRONZE
            1, // SILVER
            2, // GOLD
            3, // PLATINUM
            4, // DIAMOND
            5, // MASTER
            6  // GRANDMASTER
    };
}
