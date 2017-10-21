/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author katri
 */
public class AnnosRaakaAine {
    
    private Integer id;
    private Integer annos_Id;
    private Integer raaka_aine_Id;
    private Integer jarjestys;
    private Integer maara;
    private String ohje;
    
    public AnnosRaakaAine(Integer id, Integer annosId, Integer raakaAineId, Integer jarjestys, Integer maara, String ohje) {
        this.id = id;
        this.annos_Id = annosId;
        this.raaka_aine_Id = raakaAineId;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;  
    }

    public Integer getId() {
        return id;
    }

    public Integer getAnnos_Id() {
        return annos_Id;
    }

    public Integer getRaaka_aine_Id() {
        return raaka_aine_Id;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public Integer getMaara() {
        return maara;
    }

    public String getOhje() {
        return ohje;
    }
    
    
    
    
}
