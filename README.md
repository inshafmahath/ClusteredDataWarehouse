# ClusteredDataWarehouse

<p>Check out from GI repository</p>

<p>To create the DB schema, run the below script</p>

     https://github.com/inshafmahath/ClusteredDataWarehouse/blob/master/src/main/resources/schema/db_schema.sql
     

<p>Once the script is executed change the below properties on </p>

     https://github.com/inshafmahath/ClusteredDataWarehouse/blob/master/src/main/resources/META-INF/persistence.xml
     

     Provide the relevant connection params:        

               <property name="hibernate.connection.url" value="jdbc:mysql://localhost:3306/test" />
               <property name="hibernate.connection.username" value="root" />
               <property name="hibernate.connection.password" value="password" />


<p>To run the project execute:</p>

    mvn clean install tomcat7:run

<p>Once server started browse to:</p>

    http://localhost:9090/fileUpload
    
   
<p>Sample CSV files are available in:</p>

    https://github.com/inshafmahath/ClusteredDataWarehouse/tree/master/src/main/resources/Samples
    
  
