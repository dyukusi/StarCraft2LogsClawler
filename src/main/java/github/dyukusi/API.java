package github.dyukusi;

import github.dyukusi.model.ProfileLog;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class API {
    static public void updateProfile(int regionId, int ladderId, int profileId) throws FileNotFoundException, SQLException {
        Clawler c = new Clawler();
        List<ProfileLog> profileLogs = c.getProfileLogs(Region.getById(regionId), ladderId);

        Connection con = c.getDB().connect();

        for (ProfileLog p : profileLogs) {
            if (p.getId() == profileId) {
                p.save(con);
                break;
            }
        }

        con.commit();
        con.close();
    }
}
