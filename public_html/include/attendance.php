<?php

$conn;

require_once 'DB_Connect.php';
$db = new Db_Connect();
$conn = $db->connect();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['teacher_id']) && isset($_POST['att_date']) && isset($_POST['student_id']) && isset($_POST['subject']) && isset($_POST['present'])) {
 
    // receiving the post params
    $teacher_id = $_POST['teacher_id'];
    $att_date = $_POST['att_date'];
    $student_id=$_POST['student_id'];
    $subject=$_POST['subject'];
    $present = $_POST['present'];
    
    $sql = "INSERT INTO attendance(teacher_id, att_date, student_id,subject,present) VALUES('$teacher_id', '$att_date', '$student_id', '$subject', '$present')";
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
    $response["error_msg"] = "Required parameters (teacher_id, att_date, student_id or subject) is missing!";
    echo json_encode($response);
}
?>