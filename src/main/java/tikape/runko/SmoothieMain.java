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
import tikape.runko.domain.AnnosRaakaAine;

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
        AnnosRaakaAineDao annosRaakaAineDao = new AnnosRaakaAineDao(database);
        
        // Create index-page:
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/smoothiet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());
            map.put("ainekset", raakaAineDao.findAll());

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", raakaAineDao.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/ainekset/:id/poista", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer id = Integer.parseInt(req.params(":id"));
            RaakaAine r = raakaAineDao.findOne(id);
            
            // Delete raakaAine and its relations:
            raakaAineDao.delete(id);
            annosRaakaAineDao.deleteRaakaAine(id);
            
            map.put("id", id);
            map.put("poistettu", r.getNimi());
                    
            return new ModelAndView(map, "poista");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/smoothiet/:id/poista", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer id = Integer.parseInt(req.params(":id"));
            Annos a = annosDao.findOne(id);
            
            // Delete Annos and its relations.
            annosDao.delete(id);
            annosRaakaAineDao.deleteAnnos(id);
            
            map.put("id", id);
            map.put("poistettu", a.getNimi());
                    
            return new ModelAndView(map, "poista");
        }, new ThymeleafTemplateEngine());
        
        
        // Create spesific Annos:
        Spark.get("/smoothiet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            
            // Define Annos and its id:
            Integer annosId = Integer.parseInt(req.params(":id"));
            Annos annos = annosDao.findOne(annosId);
            
            // Find raakaAine list:
            List<RaakaAine> raakaAineet = raakaAineDao.findAnnokseenLiittyvat(annosId);
            
            map.put("annosId", annosId);
            map.put("annos", annos);
            map.put("raakaAineet", raakaAineet);
            map.put("annosRaakaAineet", annosRaakaAineDao.findAnnokseenLiittyvat(annosId));
            
            return new ModelAndView(map, "annos");
        },  new ThymeleafTemplateEngine());
        
        Spark.post("/ainekset", (req, res) -> {
            RaakaAine r = new RaakaAine(-1, req.queryParams("nimi"));
            raakaAineDao.saveOrUpdate(r);
            res.redirect("/ainekset");
            return "";
        });
        
        // Add new Smoothie: Annos
        Spark.post("/smoothiet", (req, res) -> {
            Annos a = new Annos(-1, req.queryParams("nimi"));
            annosDao.saveOrUpdate(a);
            
            res.redirect("/smoothiet");
            return "";
        });
        
        //Add new Smoothie: relation between Annos and RaakaAineet -> AnnosRaakaAine
        Spark.post("/smoothiet", (req, res) -> {
            Integer annosId = Integer.parseInt(req.queryParams("smoothie"));
            Integer raakaAineId = Integer.parseInt(req.queryParams("raakaAine"));
            
            Integer maara = Integer.parseInt(req.queryParams("maara"));
            Integer jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            String ohje = req.queryParams("ohje");
            
            AnnosRaakaAine ara = new AnnosRaakaAine(-2 ,annosId, raakaAineId, jarjestys, maara, ohje);
            annosRaakaAineDao.saveOrUpdate(ara);
           
            res.redirect("/smoothiet");
            return "";
        });
        
        
    }
}
