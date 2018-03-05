package github.dyukusi.model;

import github.dyukusi.Region;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class League {
    private Region region;
    private int seasonId;
    private int queueId;
    private int leagueId;
    private int tier;
    private int minRating;
    private int maxRating;

    public League(Region region, int seasonId, int queueId, int leagueId, int tier, int minRating, int maxRating) {
        this.region = region;
        this.seasonId = seasonId;
        this.queueId = queueId;
        this.leagueId = leagueId;
        this.tier = tier;
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    public void save(Connection con) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO league VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)");
            ps.setInt(1, this.region.getId());
            ps.setInt(2, this.seasonId);
            ps.setInt(3, this.queueId);
            ps.setInt(4, this.leagueId);
            ps.setInt(5, this.tier);
            ps.setInt(6, this.minRating);
            ps.setInt(7, this.maxRating);

            boolean result = ps.execute();
            if (result) {
                System.err.println("league insertion failed. " + ps.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int getMinRating() {
        return this.getMinRating();
    }
}
