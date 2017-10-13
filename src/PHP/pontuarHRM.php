<?php

   error_reporting(0);


   require "connection/connection.php";
    
    $email= mysqli_real_escape_string($conn, $_POST["email"]);
    $bpm= mysqli_real_escape_string($conn, $_POST["bpm"]);

    $SQL_select = mysqli_query($conn, "SELECT idUser FROM user WHERE email ='$email' ;");
  

    while($row = mysqli_fetch_assoc($SQL_select)){
   	$id = $row["idUser"];
    }

  
    $SQL_insert = mysqli_query($conn, "INSERT INTO pontos(ponto, descAtividade, user_idUser) VALUES (30,'Heart Rate Monitoring', '$id') ");


   

   mysqli_free_result($SQL_select);
   mysqli_free_result($SQL_insert);


   $SQL_insert2 = mysqli_query($conn, "INSERT INTO hrmDB.hrm(taxaHRM, dataRegistro, user_idUser) VALUES('$bpm',  CURDATE(), '$id' ) ");

   mysqli_close($conn);

   echo "pontuado";
?>
