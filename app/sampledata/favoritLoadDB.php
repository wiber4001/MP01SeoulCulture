<?php
    header('Content-Type:application/json; charset=utf-8');

    $id=$_POST['id'];
    $pass=$_POST['pass'];

    $db=mysqli_connect('localhost','wny2023','thdek543!','wny2023');
    mysqli_query($db,"set names utf8");

    $sql="SELECT TITLE,GUNAME,PLACE,DATE,USE_FEE,PROGRAM,ORG_LINK,MAIN_IMG FROM favoritMP where id='$id' and pass='$pass'";
    $result=mysqli_query($db,$sql);
    // $TITLE=stripslashes($TITLE);
    // $GUNAME=stripslashes($GUNAME);
    // $PLACE=stripslashes($PLACE);
    // $DATE=stripslashes($DATE);
    // $USE_FEE=stripslashes($USE_FEE);
    // $PROGRAM=stripslashes($PROGRAM);
    // $ORG_LINK=stripslashes($ORG_LINK);
    // $MAIN_IMG=stripslashes($MAIN_IMG);
  
    //결과표로부터 총 레코드 수 알아내기
    $rowNum=mysqli_num_rows($result);

    $rows=array();
    for($i=0;$i<$rowNum;$i++){
        $row=mysqli_fetch_array($result, MYSQLI_ASSOC);//연관배열로 설정

       
        $rows[$i]=$row;
        // array_push($rows[$i],$row);
    }
    
    //2차원 배열을 json으로 받아옴
    $new_testArr = str_replace('\\/', '/', json_encode($rows));
     echo $new_testArr;
     mysqli_close($db);
    
    // for($i=0;$i<$rowNum;$i++){
    //     $row=mysqli_fetch_array($result, MYSQLI_ASSOC);
    //     echo $row['TITLE'] . ",";
    // }

?>