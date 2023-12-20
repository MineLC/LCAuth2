package lc.auth2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MYSQL {
    public static Boolean playerexits(String name) {
        Connection thiscon = Auth2.get().getConnection();
        try {
            PreparedStatement statement = thiscon.prepareStatement("SELECT * FROM `" + Auth2.get().authtable + "` WHERE `Player` = ?;");
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(thiscon != null)
                try {
                    thiscon.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return false;
    }
}
