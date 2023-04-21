<?php
    header('Content-Type:text/plain; charset=utf-8');
    
    //@PartMap으로 전달된 POST방식의 데이터들
    $id=$_POST['id'];
    $pass=$_POST['pass'];
    $email=$_POST['email'];
    $path=$_POST['path'];

    //@Part로 전달된 이미지파일
    $file=$_FILES['img'];
    
    $srcName=$file['name']; //원본파일명
    $tmpName=$file['tmp_name']; //임시저장소 경로/파일명

    //이미지 파일을 영구적으로 저장하기 위해 임시저장소에서 서버저장소로 이동
    $dstName="./image/".date('YmdHis').$srcName;
    move_uploaded_file($tmpName,$dstName);

    //타이틀, 메시지 중에 특수문자(줄바꿈포함) 사용 가능성 있음 -> SQL에서 쿼리문이 잘못 해석될 수 있음
    //특수문자 앞에는 자동으로 슬래시문자를 추가해줌 -> 원본은 안바뀜-> 다시 메시지에 넣어서 원본바꿈
    $email=addslashes($email);
    $pass=addslashes($pass);

    //데이터가 저장되는 시간
    $now=date('Y-m-d H:i:s');

    //MySQL DB에 데이터를 저장[테이블명: membersMP]
    $db=mysqli_connect('localhost','wny2023','thdek543!','wny2023');
    mysqli_query($db,"set names utf8");

    //저장할 데이터($id, $pass, $email, $file, $dstName, $now)들을 삽입하는 쿼리문
    $sql="INSERT INTO membersMP(id,pass,email,imgUrl,path,now) VALUES ('$id','$pass','$email','$dstName','$path','$now')";
    $result=mysqli_query($db,$sql);

    //$result로 확인 echo
    if($result) echo "true";
    else echo "false";

    mysqli_close($db);

?>