<?php
 
require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['address']) && isset($_POST['roll']) && isset($_POST['faculty'])   && isset($_POST['password']) && isset($_POST['phone_number']) && isset($_POST['user_type'])) {
 
    // receiving the post params
    $name = $_POST['name'];
    $email = $_POST['email'];
    $address=$_POST['address'];
    $roll=$_POST['roll'];
    $faculty=$_POST['faculty'];
    $password = $_POST['password'];
    $phone_number = $_POST['phone_number'];
     $user_type = $_POST['user_type'];
 
    // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($name, $email,$address,$roll, $faculty,$password, $phone_number, $user_type);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["uid"] = $user["unique_id"];
            $response["user"]["name"] = $user["name"];
            $response["user"]["email"] = $user["email"];
            $response["user"]["address"]=$user["address"];
            $response["user"]["roll"]=$user["roll"];
             $response["user"]["faculty"]=$user["faculty"];
            $response["user"]["phone_number"] = $user["phone_number"];
            $response["user"]["user_type"] = $user["user_type"];
            $response["user"]["created_at"] = $user["created_at"];
            $response["user"]["updated_at"] = $user["updated_at"];
            $response["user"]["active"] = $user["active"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email, number or password) is missing!";
    echo json_encode($response);
}
?>