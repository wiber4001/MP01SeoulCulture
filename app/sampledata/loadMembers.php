<?php
    header('Content-Type:text/plain; charset=utf-8');

    $id=$_POST['id'];
    $db=mysqli_connect('localhost','wny2023','thdek543!','wny2023');
    mysqli_query($db,"set names utf8");

    // id존재 하면 값이 result에 받아지게 함
    $sql="SELECT * FROM membersMP where id='$id'";
    $result=mysqli_query($db,$sql);

    $row_num=mysqli_num_rows($result);

    if($row_num>0){
        echo "false";
    }else echo "true";  
    
    mysqli_close($db);

?>