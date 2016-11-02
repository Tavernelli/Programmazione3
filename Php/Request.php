<?php

if($_SERVER["REQUEST_METHOD"]=="POST")
{
    require 'connection.php';    
    send_response();
}

function send_response ()
{
    global $connect;
    $materie = json_decode($_POST["materie"]);
    $response = array();
    
	foreach ($materie as $materia) {
      $statement = mysqli_prepare($connect, "SELECT * FROM subject WHERE materia = ?");
      mysqli_stmt_bind_param($statement, "s", $materia);
      mysqli_stmt_execute($statement);
      mysqli_stmt_store_result($statement);
      mysqli_stmt_bind_result($statement, $user_id, $materia, $docente, $info);

      if(mysqli_stmt_fetch($statement))
      {
          $this_response = array();
          $this_response["user_id"] = $user_id;
          $this_response["materia"] = $materia;
          $this_response["docente"] = $docente;
          $this_response["info"] = $info;
		  array_push($response,$this_response);
      }
    }
    
    
    echo json_encode($response);
}


?>
