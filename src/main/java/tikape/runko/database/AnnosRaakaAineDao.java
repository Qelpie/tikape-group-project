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

/**
 *
 * @author katri
 */
public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer>{
    
    private Database database;

    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }
    
    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
       return null; 
    }
    
    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        return null;
    }
    
    @Override
    public void delete(String nimi) throws SQLException {
        //tätä ei varmaan tarvitse tehdä
    }
    
    @Override
    public void addOne(String nimi) throws SQLException {
        //tätäkään ei varmaan tarvitse tässä kohtaa
    }
    
    public List<AnnosRaakaAine> findAnnokseenLiittyvat() throws SQLException {
        //etsii tiettyyn annokseen liittyvät
        return null;
    }
    
}
