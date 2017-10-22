/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.get;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.Database;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Annos;

/**
 *
 * @author haii
 */
public class SmoothieMain {
    
    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:db/smoothie.db");
        database.init();

        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        AnnosDao annosDao = new AnnosDao(database);
        
        List<RaakaAineDao> raakaAineet = new ArrayList<>();
        List<Annos> annokset = annosDao.findAll();
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annos", annokset);

            return new ModelAndView(map, "smoothieindeksi");
        }, new ThymeleafTemplateEngine());
        
//        get("/smoothiet", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("smoothiet", AnnosDao.findAll());
//
//            return new ModelAndView(map, "smoothiet");
//        }, new ThymeleafTemplateEngine());
//        
//        get("/ainekset", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("ainekset", RaakaAineDao.findAll());
//
//            return new ModelAndView(map, "ainekset");
//        }, new ThymeleafTemplateEngine());
    }
}
