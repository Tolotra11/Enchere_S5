show dbs
use enchere

db.createCollection('enchere')


db.enchere.insertOne(
   {
         id : "1",
         titre :" Porsche 911  ",
         description :"Une voiture top classe et sportive",
         prixminimal :"20000000",
         duree :"45",
         dateenchere :"01-12-2023",
         categorieid :"1",
         utilisateurid :"1",
         statut:"1"
   }
)


 db.enchere.find().pretty()


 db.enchere.find({ "id" : "1" }).pretty()