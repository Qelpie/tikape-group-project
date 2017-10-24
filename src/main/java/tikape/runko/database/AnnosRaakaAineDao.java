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
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM AnnosRaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    public void deleteRaakaAine(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM AnnosRaakaAine WHERE raaka_aine_id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    public void deleteAnnos(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
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
        AnnosRaakaAine ar = findByAnnosIdRaakaAineId(object.getAnnosId(), object.getRaakaAineId());

        if (ar != null) {
            try (Connection conn = database.getConnection()) {
                PreparedStatement st = conn.prepareStatement(
                        "SELECT maara FROM AnnosRaakaAine WHERE annos_id = ? AND raaka_aine_id = ?"
                );

                st.setInt(1, object.getAnnosId());
                st.setInt(2, object.getRaakaAineId());

                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    Integer maara_lisays = rs.getInt("maara") + object.getMaara();

                    try (Connection conn2 = database.getConnection()) {
                        PreparedStatement st2 = conn2.prepareStatement(
                                "UPDATE AnnosRaakaAine SET maara = ? WHERE annos_id = ? AND raaka_aine_id = ?");

                        st2.setInt(1, maara_lisays);
                        st2.setInt(2, object.getAnnosId());
                        st2.setInt(3, object.getRaakaAineId());

                        st2.executeUpdate();

                        st2.close();
                        conn2.close();

                    }
                }

                st.close();
                conn.close();
            }
        }

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

        return findByAnnosIdRaakaAineId(object.getAnnosId(), object.getRaakaAineId());
    }

    public AnnosRaakaAine findByAnnosIdRaakaAineId(Integer annosId, Integer raakaAineId) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, jarjestys, maara, ohje, raaka_aine_id, annos_id FROM AnnosRaakaAine WHERE annos_id = ? AND raaka_aine_id = ?");

            stmt.setInt(1, annosId);
            stmt.setInt(1, raakaAineId);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            stmt.close();
            rs.close();
            conn.close();

            return new AnnosRaakaAine(rs.getInt("id"), rs.getInt("annos_id"), rs.getInt("raaka_aine_id"), rs.getInt("jarjestys"), rs.getInt("maara"), rs.getString("ohje"));
        }
    }
}
