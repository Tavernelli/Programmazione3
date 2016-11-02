<?php

if($_SERVER["REQUEST_METHOD"]=="POST")
{
    require 'connection.php';    
    send_response();
}

function send_response ()
{
    global $connect;
    $username = $_POST["username"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $username, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $name, $username, $age, $password);
    
    $response = array();
    $response["success"] = false;  
    $_SESSION["login"] = false;
    if(mysqli_stmt_fetch($statement))
    {
        $response["success"] = true;  
        $response["name"] = $name;
        $response["username"] = $username;
        $response["age"] = $age;
        $response["password"] = $password;

        $_SESSION["login"] = true;
        $_SESSION["username"] = $username;
        $_SESSION["password"] = $password;
    }
    
    echo json_encode($response);
}

?>