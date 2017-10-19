/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;

/**
 *
 * @author haii
 */
public class SmoothieMain {
    
    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothie.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
    }
}
