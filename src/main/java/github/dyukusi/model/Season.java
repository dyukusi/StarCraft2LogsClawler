package github.dyukusi.model;

import java.sql.*;

public class Season {
    private int id;
    private int year;
    private int number;
    private int region_id;
    private long start_at;
    private long end_at;

    public Season(int id, int year, int number, int region_id, long start_at, long end_at) {
        this.id        = id;
        this.year      = year;
        this.number    = number;
        this.region_id = region_id;
        this.start_at  = start_at;
        this.end_at    = end_at;
    }

    public int getId() {
        return this.id;
    }

    public void save(Connection con) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO season VALUES (?, ?, ?, ?, FROM_UNIXTIME(?), FROM_UNIXTIME(?))");
            ps.setInt(1, this.region_id);
            ps.setInt(2, this.id);
            ps.setInt(3, this.year);
            ps.setInt(4, this.number);
            ps.setLong(5, this.start_at);
            ps.setLong(6, this.end_at);

            System.out.println(ps.toString());

            boolean result = ps.execute();
            if (result) {
                System.err.println("season insertion failed. " + ps.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --------------
    // static methods
    // --------------

    static public boolean has(Connection con, int seasonId) {
        String query = String.format("SELECT * FROM season WHERE id = %d;", seasonId);

        try {
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery(query);
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
