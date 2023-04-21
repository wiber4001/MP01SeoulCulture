<?php
    header('Content-Type:plain/text; charset=utf-8');

    $id=$_POST['id'];
    $pass=$_POST['pass'];
    $TITLE=$_POST['TITLE'];

    $pass=addslashes($pass);
    $TITLE=addslashes($TITLE);

    $db=mysqli_connect('localhost','wny2023','thdek543!','wny2023');
    mysqli_query($db,"set names utf8");

    $sql="SELECT TITLE,GUNAME,PLACE,DATE,USE_FEE,PROGRAM,ORG_LINK,MAIN_IMG FROM favoritMP where id='$id' and pass='$pass' and TITLE='$TITLE'";
    $result=mysqli_query($db,$sql);

    if($result){
        $row=mysqli_fetch_array($result, MYSQLI_ASSOC);//연관배열로 설정
        $new_testArr = str_replace('\\/', '/', json_encode($row));
        echo $new_testArr;
    }else {
        $row=array();
        echo $row;
    }

?>