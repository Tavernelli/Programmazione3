<?php
 if($_SERVER["REQUEST_METHOD"]=="POST"){
    require 'connection.php';
    prova1();
}


function prova1 ()
{

    global $connect;
    $name = $_POST["name"];
     $age = $_POST["age"];
    $username = $_POST["username"];
    $password = $_POST["password"];
      
    $statement = mysqli_prepare($connect, "INSERT INTO user (name, age, username, password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "siss", $name, $age, $username, $password);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
}
?>