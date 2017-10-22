/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.Database;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Annos;
import tikape.runko.domain.RaakaAine;
import org.thymeleaf.context.Context;

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
        
        List<RaakaAine> raakaAineet = raakaAineDao.findAll();
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "smoothieindex");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/smoothiet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer annosId = Integer.parseInt(req.params(":id"));
            Annos annos = annosDao.findOne(annosId);
            
            map.put("annosId", annosId);
            map.put("annos", annos);
            
//            map.put("annokset", annosDao.findAll());
//            map.put("raakaAineet", raakaAineDao.findAll());
            
            return new ModelAndView(map, "annos");
        },  new ThymeleafTemplateEngine());
        
//        get("/smoothiet", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("Annostesti", raakaAineDao.findAll());
//
//            return new ModelAndView(map, "Annos");
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
