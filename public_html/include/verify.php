<?php

$conn;

require_once 'DB_Connect.php';
$db = new Db_Connect();
$conn = $db->connect();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['student_id'])) {
 
    $student_id=$_POST['student_id'];
    $active = "true";
    
    $sql = "UPDATE users SET active = '$active' WHERE id = '$student_id'";
    $result = $conn->query($sql);

        
        if ($result) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["status"] = "OK";
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (student_id) is missing!";
    echo json_encode($response);
}
?>