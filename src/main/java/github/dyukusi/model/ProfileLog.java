package github.dyukusi.model;

import github.dyukusi.Race;
import github.dyukusi.Region;

import java.sql.*;

public class ProfileLog {
    private int id;
    private String name;
    private Region region;
    private String battleTag;
    private int leagueId;
    private int seasonId;
    private int ladderId;
    private int queueId;
    private int teamType;
    private Race race;
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

    public ProfileLog(int profileId, String name, Region region, String battleTag, int leagueId,
                      int seasonId, int ladderId, int queueId, int teamType, Race race,
                      int playedCount, int rating, int points, int wins,
                      int losses, int ties, int longestWinStreak, int currentWinStreak,
                      int currentRank, int highestRank, int previousRank, int clanId,
                      String clanTag, String clanName, String clanIconURL, String clanDecalURL,
                      long joinedAt, long lastPlayedAt) {
        this.id = profileId;
        this.name = name;
        this.region = region;
        this.battleTag = battleTag;
        this.leagueId = leagueId;
        this.seasonId = seasonId;
        this.ladderId = ladderId;
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
            PreparedStatement ps = con.prepareStatement("INSERT INTO profile_log VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, FROM_UNIXTIME(?), FROM_UNIXTIME(?), CURRENT_TIMESTAMP)");
            ps.setInt(1, this.region.getId());
            ps.setInt(2, this.id);
            ps.setString(3,this.name);
            ps.setString(4, this.battleTag);
            ps.setInt(5, this.leagueId);
            ps.setInt(6, this.seasonId);
            ps.setInt(7, this.ladderId);
            ps.setInt(8, this.queueId);
            ps.setInt(9, this.teamType);
            ps.setInt(10, this.race.getId());
            ps.setInt(11, this.playedCount);
            ps.setInt(12, this.rating);
            ps.setInt(13, this.points);
            ps.setInt(14, this.wins);
            ps.setInt(15, this.losses);
            ps.setInt(16, this.ties);
            ps.setInt(17, this.longestWinStreak);
            ps.setInt(18, this.currentWinStreak);
            ps.setInt(19, this.currentRank);
            ps.setInt(20, this.highestRank);
            ps.setInt(21, this.previousRank);
            ps.setInt(22, this.clanId);
            ps.setString(23, this.clanTag);
            ps.setString(24, this.clanName);
            ps.setString(25, this.clanIconURL);
            ps.setString(26, this.clanDecalURL);
            ps.setLong(27, this.joinedAt);
            ps.setLong(28, this.lastPlayedAt);

            boolean result = ps.execute();
            if (result) {
                System.err.println("profile_log insertion failed. " + ps.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
