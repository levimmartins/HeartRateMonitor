<?php

   error_reporting(0);

   // include ("connection/connection.php"); 
   require "connection/connection.php";
    
    $response = array();
    $email= mysqli_real_escape_string($conn, $_POST["email"]);
    $idDica= mysqli_real_escape_string($conn, $_POST["idDicas"]);
    
  
    $SQL_select = mysqli_query($conn, "SELECT dica FROM hrmDB.dicas WHERE idDicas= '$idDica' ");
  

    while($row = mysqli_fetch_assoc($SQL_select)){
   	$dica = $row["dica"];
    }
	$response["dica"] = $dica;
		
      // array_push($response["dica"], $dica);

   // if(mysqli_num_rows($SQL_select) > 0) {
        echo json_encode($response);
   // }

?>
