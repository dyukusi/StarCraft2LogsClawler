package github.dyukusi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import github.dyukusi.model.ProfileLog;
import github.dyukusi.model.Season;
import github.dyukusi.model.League;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Clawler {
    private DB db;
    private Connection con;
    private final String BNET_API_ACCESS_TOKEN;

    Clawler() throws FileNotFoundException {
        Map y = new Yaml().load(new FileInputStream(new File("/etc/sc2logs.yaml")));
        Map dbSetting = (Map) y.get("database");
        this.db = new DB(
                (String) dbSetting.get("hostname"),
                (String) dbSetting.get("database"),
                (String) dbSetting.get("user"),
                (String) dbSetting.get("password")
        );

        Map bnetSetting = (Map) y.get("battle_net");
        this.BNET_API_ACCESS_TOKEN = (String) bnetSetting.get("access_token");
    }

    public DB getDB() {
        return this.db;
    }

    public void exec(Region region) throws SQLException {
        this.con = this.db.connect();

        int currentSeasonId = this.updateCurrentSeason(region);
        this.updateLeagueMaxMinRating(region, currentSeasonId);
        this.updateProfiles(region, currentSeasonId);

        this.con.close();
    }

    private void updateProfiles(Region region, int seasonId) {
        ArrayList<Integer> ladderIds = this.getAllLadderIds(region, seasonId);
        ladderIds.forEach(ladderId -> {
            try {
                Thread.sleep(Constant.WAIT_TIME_PER_LADDER_ANALYZATION);

                List<ProfileLog> profileLogs = this.getProfileLogs(region, ladderId);
                profileLogs.forEach(profileLog -> {
                    profileLog.save(this.con);
                });

                this.con.commit();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("ladder_id: " + ladderId);
            }
        });
    }

    ArrayList<ProfileLog> getProfileLogs(Region region, Integer ladderId) {
        ArrayList<ProfileLog> profileLogs = new ArrayList<>();
        JsonObject ladderJson = BnetAPI.getLadder(region, ladderId, this.BNET_API_ACCESS_TOKEN);

        JsonObject leagueJson = ladderJson.getAsJsonObject("league").getAsJsonObject("league_key");
        int leagueId = leagueJson.get("league_id").getAsInt();
        int seasonId = leagueJson.get("season_id").getAsInt();
        int queueId = leagueJson.get("queue_id").getAsInt();
        int teamType = leagueJson.get("team_type").getAsInt();

        JsonArray teamJsonArray = ladderJson.getAsJsonArray("team");
        teamJsonArray.forEach(team -> {
            try {
                JsonObject teamJson = team.getAsJsonObject();
                int rating = teamJson.get("rating").getAsInt();
                int points = teamJson.get("points").getAsInt();
                int wins = teamJson.get("wins").getAsInt();
                int losses = teamJson.get("losses").getAsInt();
                int ties = teamJson.get("ties").getAsInt();
                int longestWinStreak = teamJson.get("longest_win_streak").getAsInt();
                int currentWinStreak = teamJson.get("current_win_streak").getAsInt();
                int currentRank = teamJson.get("current_rank").getAsInt();
                int highestRank = teamJson.get("highest_rank").getAsInt();
                int previousRank = teamJson.get("previous_rank").getAsInt();
                long joinedAt = teamJson.get("join_time_stamp").getAsLong();
                long lastPlayedAt = teamJson.get("last_played_time_stamp").getAsLong();

                // NOTE: only considering 1v1
                JsonArray memberJsonArray = teamJson.getAsJsonArray("member");
                JsonObject memberJson = memberJsonArray.get(0).getAsJsonObject();
                JsonObject raceJson = memberJson.getAsJsonArray("played_race_count").get(0).getAsJsonObject();

                String raceStr = "Null";
                if (raceJson.has("race") && !raceJson.get("race").isJsonNull()) {
                    raceStr = raceJson.get("race").getAsString();
                }
                Race race = Race.valueOf(raceStr);
                int count = raceJson.get("count").getAsInt();

                JsonObject legacyCharacterJson = memberJson.getAsJsonObject("legacy_link");

                // remove # suffix
                String name = legacyCharacterJson.get("name").getAsString();
                name = name.split("#")[0];

                JsonObject characterJson = memberJson.getAsJsonObject("character_link");
                int profileId = characterJson.get("id").getAsInt();
                String battleTag = characterJson.get("battle_tag").getAsString();

                int clanId = 0;
                String clanTag = null;
                String clanName = null;
                String clanIconURL = null;
                String clanDecalURL = null;
                if (memberJson.has("clan_link")) {
                    JsonObject clanLinkJson = memberJson.getAsJsonObject("clan_link");
                    clanId = clanLinkJson.get("id").getAsInt();
                    clanTag = clanLinkJson.get("clan_tag").getAsString();
                    clanName = clanLinkJson.get("clan_name").getAsString();

                    if (clanLinkJson.has("icon_url")) {
                        clanIconURL = clanLinkJson.get("icon_url").getAsString();
                    }

                    if (clanLinkJson.has("decal_url")) {
                        clanDecalURL = clanLinkJson.get("decal_url").getAsString();
                    }
                }

                ProfileLog profileLog = new ProfileLog(
                        profileId, name, region, battleTag, leagueId,
                        seasonId, ladderId, queueId, teamType, race,
                        count, rating, points, wins, losses,
                        ties,
                        longestWinStreak,
                        currentWinStreak,
                        currentRank,
                        highestRank,
                        previousRank,
                        clanId,
                        clanTag,
                        clanName,
                        clanIconURL,
                        clanDecalURL,
                        joinedAt,
                        lastPlayedAt
                );

                profileLogs.add(profileLog);

            } catch (Exception e) {
                System.err.println(String.format(
                        "Failed to parse JSON. LadderId: %d, LeagueID: %d, SeasonID: %d, QueueID: %d, TeamType: %d",
                        ladderId, leagueId, seasonId, queueId, teamType
                ));
                e.printStackTrace();
            }
        });

        return profileLogs;
    }

    private void updateLeagueMaxMinRating(Region region, int seasonId) {
        int queueId = Constant.LOTV_1V1_QUEUE_ID;
        List<League> leagues = new ArrayList<>();

        for (int leagueId : Constant.LEAGUE_IDS) {
            JsonObject leagueJson = BnetAPI.getLeague(region, seasonId, queueId, leagueId, this.BNET_API_ACCESS_TOKEN);
            JsonArray tierJsonArray = leagueJson.get("tier").getAsJsonArray();

            tierJsonArray.forEach(tierJsonElement -> {
                JsonObject tierJson = tierJsonElement.getAsJsonObject();
                int tier = tierJson.get("id").getAsInt() + 1;
                int minRating = tierJson.has("min_rating") ? tierJson.get("min_rating").getAsInt() : -1;
                int maxRating = tierJson.has("max_rating") ? tierJson.get("max_rating").getAsInt() : -1;

                leagues.add(new League(
                        region,
                        seasonId,
                        queueId,
                        leagueId,
                        tier,
                        minRating,
                        maxRating
                ));
            });
        }

        leagues.forEach(league -> {
            league.save(this.con);
        });

        try {
            this.con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // NOTE: only supporting 1v1 atm
    private ArrayList<Integer> getAllLadderIds(Region region, int seasonId) {
        ArrayList<Integer> ladderIds = new ArrayList<>();

        for (int leagueId : Constant.LEAGUE_IDS) {
            JsonObject leagueJson = BnetAPI.getLeague(region, seasonId, Constant.LOTV_1V1_QUEUE_ID, leagueId, this.BNET_API_ACCESS_TOKEN);
            JsonArray tierJsonArray = leagueJson.get("tier").getAsJsonArray();

            tierJsonArray.forEach(tierJson -> {
                List<Integer> extractedLadderIds = this.extractLadderIdsByTierJson(tierJson.getAsJsonObject());
                ladderIds.addAll(extractedLadderIds);
            });
        }

        return ladderIds;
    }

    private ArrayList<Integer> extractLadderIdsByTierJson(JsonObject tierJson) {
        ArrayList<Integer> ladderIds = new ArrayList<>();

        JsonArray divJsonArray = tierJson.getAsJsonArray("division");
        divJsonArray.forEach(div -> {
            JsonObject divJson = div.getAsJsonObject();
            int ladderId = divJson.get("ladder_id").getAsInt();
            ladderIds.add(ladderId);
        });

        return ladderIds;
    }

    private int updateCurrentSeason(Region region) {
        JsonObject currentSeasonJson = BnetAPI.getCurrentSeason(region, this.BNET_API_ACCESS_TOKEN);
        Season season = new Season(
                currentSeasonJson.get("id").getAsInt(),
                currentSeasonJson.get("year").getAsInt(),
                currentSeasonJson.get("number").getAsInt(),
                region.getId(),
                currentSeasonJson.get("start_timestamp").getAsLong(),
                currentSeasonJson.get("end_timestamp").getAsLong()
        );

        season.save(this.con);

        try {
            this.con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return season.getId();
    }
}
