<?php

   error_reporting(0);

   // include ("connection/connection.php"); 
   require "connection/connection.php";
    
    $email= mysqli_real_escape_string($conn, $_POST["email"]);
    $senha= mysqli_real_escape_string($conn, $_POST["senha"]);
    
  
    $SQL_select = mysqli_query($conn, "SELECT * FROM hrmDB.user WHERE email like '$email' AND senha like '$senha'  ");
  
    if(mysqli_num_rows($SQL_select) > 0) {
        echo "login_ok";
    }else {
        echo "login_erro";
        
    }
  


?>
