package github.dyukusi.model;

import java.sql.*;

public class ProfileLog {
    private int id;
    private String name;
    private String battleTag;
    private int leagueId;
    private int seasonId;
    private int queueId;
    private int teamType;
    private String race;
    private int playedCount;
    private int rating;
    private int points;
    private int wins;
    private int losses;
    private int ties;
    private int longestWinStreak;
    private int currentWinStreak;
    private int currentRank;
    private int highestRank;
    private int previousRank;
    private int clanId;
    private String clanTag;
    private String clanName;
    private String clanIconURL;
    private String clanDecalURL;
    private long joinedAt;
    private long lastPlayedAt;

    public ProfileLog(int profileId, String name, String battleTag, int leagueId,
                      int seasonId, int queueId, int teamType, String race,
                      int playedCount, int rating, int points, int wins,
                      int losses, int ties, int longestWinStreak, int currentWinStreak,
                      int currentRank, int highestRank, int previousRank, int clanId,
                      String clanTag, String clanName, String clanIconURL, String clanDecalURL,
                      long joinedAt, long lastPlayedAt) {
        this.id = profileId;
        this.name = name;
        this.battleTag = battleTag;
        this.leagueId = leagueId;
        this.seasonId = seasonId;
        this.queueId = queueId;
        this.teamType = teamType;
        this.race = race;
        this.playedCount = playedCount;
        this.rating = rating;
        this.points = points;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.longestWinStreak = longestWinStreak;
        this.currentWinStreak = currentWinStreak;
        this.currentRank = currentRank;
        this.highestRank = highestRank;
        this.previousRank = previousRank;
        this.clanId = clanId;
        this.clanTag = clanTag;
        this.clanName = clanName;
        this.clanIconURL = clanIconURL;
        this.clanDecalURL = clanDecalURL;
        this.joinedAt = joinedAt;
        this.lastPlayedAt = lastPlayedAt;
    }

    public int getId() {
        return this.id;
    }

    public void save(Connection con) {
        try {
            String sql = String.format(
                    "INSERT INTO profile_log VALUES " +
                            "(%d, '%s', '%s', %d, %d, %d, %d, \"%s\", %d, %d, %d, %d, %d, %d, %d, %d, %d, %d, %d, %d, \"%s\", \"%s\", \"%s\", \"%s\", FROM_UNIXTIME(%d), FROM_UNIXTIME(%d), CURRENT_TIMESTAMP);",
                    this.id,
                    this.name,
                    this.battleTag,
                    this.leagueId,
                    this.seasonId,
                    this.queueId,
                    this.teamType,
                    this.race,
                    this.playedCount,
                    this.rating,
                    this.points,
                    this.wins,
                    this.losses,
                    this.ties,
                    this.longestWinStreak,
                    this.currentWinStreak,
                    this.currentRank,
                    this.highestRank,
                    this.previousRank,
                    this.clanId,
                    this.clanTag,
                    this.clanName,
                    this.clanIconURL,
                    this.clanDecalURL,
                    this.joinedAt,
                    this.lastPlayedAt
            );

            Statement st = con.createStatement();
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
