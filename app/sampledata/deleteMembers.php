<?php
    header('Content-Type:text/plain; charset=utf-8');

    //Delete할 아이템의 정보를 받음
    $id=$_POST['id'];
    $pass=$_POST['pass'];
    $pass=addslashes($pass);

    $db=mysqli_connect('localhost','wny2023','thdek543!','wny2023');
    mysqli_query($db,"set names utf8");
    
    // 지울대상의 폴더 위치를 알아낼 sql문이 하나 더 필요함
    $sql="SELECT imgUrl FROM membersMP WHERE id='$id' and pass='$pass'";
    $result=mysqli_query($db,$sql);
    $image=" ";
    if($result){
        $row=mysqli_fetch_array($result,MYSQLI_ASSOC);
        $image=$row['imgUrl'];
    }
    
    $sql="DELETE FROM membersMP WHERE id='$id' and pass='$pass'";
    $result=mysqli_query($db,$sql);
    if($result) {
        echo "회원정보가 안전하게 삭제되었습니다.";
        unlink($image);
    } 
    else echo "가입정보가 없습니다.";

    mysqli_close($db);

?>