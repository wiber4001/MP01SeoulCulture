<?php
    header('Content-Type:text/plain; charset=utf-8');

    $id=$_POST['id'];
    $pass=$_POST['pass'];

    $db=mysqli_connect('localhost','wny2023','thdek543!','wny2023');
    mysqli_query($db,"set names utf8");

    $sql="SELECT * FROM membersMP where id='$id' and pass='$pass'";
    $result=mysqli_query($db,$sql);

    if($result){
        $row=mysqli_fetch_array($result, MYSQLI_ASSOC);//연관배열로 설정
        echo json_encode($row);
    }else {
        $row=array('no'=> 0,'id'=>'','pass'=>'','email'=>'','imgUrl'=>'','path'=>'','date'=>'');
        echo json_encode($row);
    }

    // $row=mysqli_fetch_array($result, MYSQLI_ASSOC);

    // echo json_encode($row);

    // if($result){
    //     $row_num=mysqli_num_rows($result);
    // }else echo "fail";

    // if($row_num>0){
    //     echo "true";
    // }else echo "false";

    mysqli_close($db);

?>