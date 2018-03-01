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
            String sql = String.format(
                    "INSERT INTO season (region_id, id, year, number, start_at, end_at) VALUES (%d, %d, %d, %d, FROM_UNIXTIME(%d), FROM_UNIXTIME(%d))",
                    this.region_id,
                    this.id,
                    this.year,
                    this.number,
                    this.start_at,
                    this.end_at
            );
            Statement st = con.createStatement();
            st.execute(sql);
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
