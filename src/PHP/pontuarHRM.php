<?php

   error_reporting(0);


   require "connection/connection.php";
    
    $email= mysqli_real_escape_string($conn, $_POST["email"]);
    

    $SQL_select = mysqli_query($conn, "SELECT idUser FROM user WHERE email ='$email' ;");
  

    while($row = mysqli_fetch_assoc($SQL_select)){
   	$id = $row["idUser"];
    }

  
    $SQL_insert = mysqli_query($conn, "INSERT INTO pontos(ponto, user_idUser) VALUES (30, '$id') ");




   mysqli_free_result($SQL_select);
   mysqli_free_result($SQL_insert);

   mysqli_close($conn);

?>
