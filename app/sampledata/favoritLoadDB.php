<?php
    header('content-type:text/plain; charset=utf-8');

    $id=$_POST['id'];
    $pass=$_POST['pass'];

    $pass=addslashes($pass);

    $db=mysqli_connect('localhost','wny2023','thdek543!','wny2023');
    mysqli_query($db,"set names utf8");

    $sql="SELECT TITLE FROM favoritMP where id='$id' and pass='$pass'";
    $result=mysqli_query($db,$sql);
  
    //결과표로부터 총 레코드 수 알아내기
    $rowNum=mysqli_num_rows($result);
    
    for($i=0;$i<$rowNum;$i++){
        $row=mysqli_fetch_array($result, MYSQLI_ASSOC);
        echo $row['TITLE'] . ",";
    }
    
    mysqli_close($db);

?>