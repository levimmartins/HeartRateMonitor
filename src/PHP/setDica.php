<?php

   error_reporting(0);

   // include ("connection/connection.php"); 
   require "connection/connection.php";
    
    $response = array();
    $email= mysqli_real_escape_string($conn, $_POST["email"]);
    
  
    $SQL_select = mysqli_query($conn, "SELECT COUNT(user_has_dicas.dicas_idDicas) as total FROM hrmDB.user_has_dicas WHERE user_idUser IN (SELECT idUser FROM user WHERE email ='$email') ");
  

    while($row = mysqli_fetch_assoc($SQL_select)){
		
		$total = $row["total"];		
		
    }

	$response["total"] = $total;

        echo json_encode($response);	

   mysqli_free_result($SQL_select);
   mysqli_close($conn);

?>
