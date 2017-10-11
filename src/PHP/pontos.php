<?php

   error_reporting(0);

   // include ("connection/connection.php"); 
   require "connection/connection.php";
    
    $email= mysqli_real_escape_string($conn, $_POST["email"]);
    
  
    $SQL_select = mysqli_query($conn, "SELECT SUM(pontos.ponto) as total FROM hrmDB.pontos WHERE user_idUser =(SELECT idUser FROM user WHERE email ='$email') ");
  

    while($row = mysqli_fetch_assoc($SQL_select)){
   	echo "Pontos:"+$row["total"];
    }

   mysqli_free_result($SQL_select);
   mysqli_close($conn);

?>
