# recipe
1. double click /Recipe/build/libs/startup.cmd to start runing application. 
2. you can change content of startup.cmd e.g. java -jar Recipe.jar then the log will be printed to the console.
3. the /Recipe/build/libs/database/ is default h2 db files. 
   you can change /ROOT-INF/classes/application.properties in Recipe.jar to change the server port, db file, db console, hibernate ddl policy...
4. for rest repository. root url: http://localhost:8080/profile
   each repository has the same api. please refer to JpaRepository. 
   e.g. http://localhost:8080/dish
