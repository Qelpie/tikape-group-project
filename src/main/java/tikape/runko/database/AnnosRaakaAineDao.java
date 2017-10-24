/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.AnnosRaakaAine;


public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {
    private Database database;

    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE id = ?");
        // key is AnnosRaakaAine key!!
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer jarjestys = rs.getInt("jarjestys");
        Integer maara = rs.getInt("maara");
        String ohje = rs.getString("ohje");
        
        Integer raakaAineId = rs.getInt("raaka_aine_id");
        Integer annosId = rs.getInt("annos_id");

        AnnosRaakaAine r = new AnnosRaakaAine(id, annosId, raakaAineId, jarjestys, maara, ohje);

        rs.close();
        stmt.close();
        connection.close();

        return r;
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addOne(String s) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // Etsi annos_id:tä vastaavat annosRaakaAineet:
    public List<AnnosRaakaAine> findAnnokseenLiittyvat(Integer annosId) throws SQLException {
        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
        Connection connection = database.getConnection();
        
        // Find all annosRaakaAine where annos_id == annosId:
        PreparedStatement stmt = connection.prepareStatement("SELECT id, jarjestys, maara, ohje, raaka_aine_id FROM AnnosRaakaAine WHERE annos_id = ?");
        stmt.setInt(1, annosId);
        ResultSet rs = stmt.executeQuery();
        
        // Add annosRaakaAineet to a list:
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer raakaAineId = rs.getInt("raaka_aine_id");
            Integer jarjestys = rs.getInt("jarjestys");
            Integer maara = rs.getInt("maara");
            String ohje = rs.getString("ohje");
            
            annosRaakaAineet.add(new AnnosRaakaAine(id, annosId, raakaAineId, jarjestys, maara, ohje));
        }
        
        rs.close();
        stmt.close();
        connection.close();
            
        return annosRaakaAineet;
    }
    
    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine object) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO AnnosRaakaAine (jarjestys, maara, ohje, raaka_aine_id, annos_id) "
                            + " VALUES (?, ?, ?, ?, ?)"
            );
            st.setInt(1, object.getJarjestys());
            st.setInt(2, object.getMaara());
            st.setString(3, object.getOhje());
            st.setInt(4, object.getRaakaAineId());
            st.setInt(5, object.getAnnosId());
            
            st.executeUpdate();
            st.close();
            conn.close();
        }
        
        
        return null;
    }
}
