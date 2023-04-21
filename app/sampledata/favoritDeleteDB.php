<?php
    header('Content-Type:text/plain; charset=utf-8');

    //Delete할 아이템의 정보를 받음
    $id=$_POST['id'];
    $pass=$_POST['pass'];
    $TITLE=$_POST['TITLE'];

    $TITLE=addslashes($TITLE);
    $pass=addslashes($pass);

    $db=mysqli_connect('localhost','wny2023','thdek543!','wny2023');
    mysqli_query($db,"set names utf8");

    $sql="DELETE FROM favoritMP WHERE id='$id' and pass='$pass' and TITLE='$TITLE'";
    $result=mysqli_query($db,$sql);
    if($result) {
        echo "좋아요를 취소하였습니다.";
    }
    else echo "삭제과정에서 오류가 발생하였습니다.";

    mysqli_close($db);

?>