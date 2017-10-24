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
import tikape.runko.database.AnnosRaakaAineDao;

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
        AnnosRaakaAineDao annosRaakaAine = new AnnosRaakaAineDao(database);
        
        // Create index-page:
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/smoothiet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", raakaAineDao.findAll());

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", raakaAineDao.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        // Create spesific Annos:
        Spark.get("/smoothiet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            
            // Define Annos and its' id:
            Integer annosId = Integer.parseInt(req.params(":id"));
            Annos annos = annosDao.findOne(annosId);
            
            // Find raakaAine list:
            List<RaakaAine> raakaAineet = raakaAineDao.findAnnokseenLiittyvat(annosId);
            
            map.put("annosId", annosId);
            map.put("annos", annos);
            map.put("raakaAineet", raakaAineet);
            map.put("annosRaakaAineet", annosRaakaAine.findAnnokseenLiittyvat(annosId));
            
            return new ModelAndView(map, "annos");
        },  new ThymeleafTemplateEngine());
        
        Spark.post("/ainekset", (req, res) -> {
            RaakaAine r = new RaakaAine(-1, req.queryParams("nimi"));
            raakaAineDao.saveOrUpdate(r);
            res.redirect("/ainekset");
            return "";
        });
    }
}
