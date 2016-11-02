<?php
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    require 'connection.php';    
    send_response();
}

    function send_response ()
{
  
  $connect = mysqli_connect(HOST,USER,PASS,DB);
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$sql="SELECT materia FROM subject ";
$result=mysqli_query($connect,$sql);
$response = array();

// Numeric array

while ($row=mysqli_fetch_array($result,MYSQLI_NUM))
{
$response[] =  $row;  


}


echo json_encode($response);
}
?> 