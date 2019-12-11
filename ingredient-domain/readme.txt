<<<<<<< HEAD
Adding the ojdbc6.jar to your local repo.
========================================

Ref:  https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html

1. Install a local copy of maven.

   C:\opt\maven\apache-maven-3.3.9

2. Go to the maven folder.
   
3. Run the following command.

mvn install:install-file -Dfile=C:\opt\tomcat\apache-tomcat-6.0.53\lib\ojdbc6.jar -DgroupId=ojdbc6 -DartifactId=ojdbc6 -Dversion=6.0.0 -Dpackaging=jar

4. You can now reference the above jar file in your maven pom.xml

Note: The above step is not required when the jar is stored in artifactory and that your project references artifactory.

=======
Adding the ojdbc6.jar to your local repo.
========================================

Ref:  https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html

1. Install a local copy of maven.

   C:\opt\maven\apache-maven-3.3.9

2. Go to the maven folder.
   
3. Run the following command.

mvn install:install-file -Dfile=C:\opt\tomcat\apache-tomcat-6.0.53\lib\ojdbc6.jar -DgroupId=ojdbc6 -DartifactId=ojdbc6 -Dversion=6.0.0 -Dpackaging=jar

4. You can now reference the above jar file in your maven pom.xml

Note: The above step is not required when the jar is stored in artifactory and that your project references artifactory.

>>>>>>> refs/remotes/origin/master
