<?php
    error_reporting(0);
    include ("connection/connection.php");
    
	

    if(!empty($_POST['nome'])){
	$nome =mysqli_real_escape_string($conn, $_POST['nome']); 
    }

    if(!empty($_POST['dataNascimento'])){
	$dataNascimento= mysqli_real_escape_string($conn, $_POST['dataNascimento']);
    }
    
    if(!empty($_POST['email'])){

	$email= mysqli_real_escape_string($conn, $_POST['email']);

    }

    if(!empty($_POST['senha'])){
    	$senha= mysqli_real_escape_string($conn,$_POST['senha']);
    }
  
    if(!empty($_POST['emailCuidador'])){
	$emailCuidador = mysqli_real_escape_string($conn, $_POST['emailCuidador']);
    }

    
 
    $SQL_select = mysqli_query($conn, "SELECT * FROM hrmDB.user WHERE email = '$email' ");
    
    if(mysqli_num_rows($SQL_select)) {
        echo "Cadastro_EmailCadastrado";
    }else {
       $SQL_Insert = mysqli_query($conn,"INSERT INTO hrmDB.user(nome, dataNascimento, email, senha, cuidadorEmail) VALUES ('$nome','$dataNascimento','$email','$senha','$emailCuidador') ");        
    
       if($SQL_Insert){
           echo "Cadastro_ok";
       }else{
           echo "Cadastro_erro";
       }
       
    }
    
    

?>
