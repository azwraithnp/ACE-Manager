<?php

    $conn;
    require_once 'Config.php';
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
    if($conn)
    {
	echo "Connected";
    }

	
    

 
?>