# spark-mongo
 ensure good connectivity between Apache Spark and MongoDB "creation dataframe which contains docs / write data / read and validate the written data"

#	Application Interaction Spark/MongoDB

Cette application a pour but tester les interactions entre Apache Spark et MongoDB, elle permet :

-	La création d’une base de données. 
-	La création d’une collection dans la base de données créer.
-	Le remplissage de la collection.
-	La lecture des données écrit.
-	La vérification des données écrit. 
-	Générer un fichier résultat en format JSON, qui contient les informations du test et son résultat. 

#	Composants concernés


Composant	   	Version
- Spark		2.3.2
- MongoDB		3.6.11
- DC-OS		1.12
- Scala		2.11.8
- Assembly		0.14.9
- Gitlab		11.3.0

# Prérequis 
Avant de lancer l’application vous devez la configurer, cela se fait au niveau du fichier de configuration de l’application, qui est dans le chemin (/src/main/resources/mongodb.conf).


# Traitements 
-	Lancer l’application sur le dcos bootstrap avec la commande 
dcos spark --name="spark-2-3" run --submit-args="--conf spark.mesos.containerizer=docker --conf spark.driver.memory=4G --conf spark.cores.max=3 --conf spark.executor.cores=1 --conf spark.executor.memory=4G --conf spark.mesos.executor.docker.forcePullImage=false --conf spark.eventLog.enabled=true --conf spark.eventLog.dir=hdfs:///spark_history  --class sparkMongodb hdfs:///jars/Spark-MongoDB -assembly-0.1.jar"
 
# Validation du test 
Vérifier le statut du test dans le fichier résultat. 
