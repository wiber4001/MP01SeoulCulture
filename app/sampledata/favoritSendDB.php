<?php
    header('Content-Type:text/plain; charset=utf-8');
    
    //@PartMap으로 전달된 POST방식의 데이터들
    $id=$_POST['id'];
    $pass=$_POST['pass'];
    $TITLE=$_POST['TITLE'];
    $GUNAME=$_POST['GUNAME'];
    $PLACE=$_POST['PLACE'];
    $DATE=$_POST['DATE'];
    $USE_FEE=$_POST['USE_FEE'];
    $PROGRAM=$_POST['PROGRAM'];
    $ORG_LINK=$_POST['ORG_LINK'];
    $MAIN_IMG=$_POST['MAIN_IMG'];
  
    //타이틀, 메시지 중에 특수문자(줄바꿈포함) 사용 가능성 있음 -> SQL에서 쿼리문이 잘못 해석될 수 있음
    //특수문자 앞에는 자동으로 슬래시문자를 추가해줌 -> 원본은 안바뀜-> 다시 메시지에 넣어서 원본바꿈
    $TITLE=addslashes($TITLE);
    $pass=addslashes($pass);

    //MySQL DB에 데이터를 저장[테이블명: favoritMP]
    $db=mysqli_connect('localhost','wny2023','thdek543!','wny2023');
    mysqli_query($db,"set names utf8");

    $sql="INSERT INTO favoritMP(id,pass,TITLE,GUNAME,PLACE,DATE,USE_FEE,PROGRAM,ORG_LINK,MAIN_IMG) VALUES ('$id','$pass','$TITLE','$GUNAME','$PLACE','$DATE','$USE_FEE','$PROGRAM','$ORG_LINK','$MAIN_IMG')";
    $result=mysqli_query($db,$sql);

    //$result로 확인 echo
    if($result) echo "true";
    else echo "false";

    mysqli_close($db);

?>