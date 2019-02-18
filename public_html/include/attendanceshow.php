<?php
$conn;

require_once 'DB_Connect.php';
$db = new Db_Connect();
$conn = $db->connect();

$sql = "SELECT * FROM attendance";
$result = $conn->query($sql);
 
$userinfo = array();

while ($row_user = mysqli_fetch_assoc($result))
    $userinfo[] = $row_user;

// foreach ($userinfo as $user) {
//     echo "Id: {$user['id']}<br />"
//       . "Name: {$user['name']}<br />"
//       . "Email: {$user['email']}<br /><br />";
// }

echo json_encode($userinfo);

?>