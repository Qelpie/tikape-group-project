# tikape-group-project

This repository is a fork from Helsinki University database course's basic repository. Group project is based on the forked repository's structure and given problem.

Our small task was to make a database for Smoothie archive and a small and simple user interface with Java (and: JDBC, Spark), Thymeleaf and Html.

Terms are in Finnish. Smoothie database has Smoothies (Smoothie Annos) and ingredients (RaakaAine). One ingredient can be included in many different smoothies. Their relation in the database is called AnnosRaakaAine.

User interface has a list of smoothie names as links. Clicking a smoothie name you can see its' ingredients. In the front page there are also links to add an ingredient to the database and to form a new smoothie with ingredients.

# Database:
- db:
  - smoothiet.db

# Java source packages included:
With JDBC and Java Spark dependencies, under src/main/java/:
- tikape.runko: 
  - SmoothieMain.java
  
- tikape.runko.database:
  - AnnosDao.java
  - AnnosRaakaAineDao.java
  - Dao.java
  - Database.java
  - RaakaAineDao.java
  
- tikape.runko.domain:
  - Annos.java
  - AnnosRaakaAine.java
  - RaakaAine.java
  
# Other source packages:
- src/main/resources/templates:
  - ainekset.html
  - annos.html
  - index.html
  - poista.html
  - smoothiet.html
  
# Documents:
- README.md
- Tikape_ryhmatyo_smoothiet.pdf (In Finnish)
- UML-diagram for Java Classes: tietokantakaavio.png

