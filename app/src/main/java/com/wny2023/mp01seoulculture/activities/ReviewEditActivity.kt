package com.wny2023.mp01seoulculture.activities


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.wny2023.mp01seoulculture.G
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.adapters.PhotoAdapter
import com.wny2023.mp01seoulculture.databinding.ActivityReviewEditBinding
import com.wny2023.mp01seoulculture.models.Photos
import com.wny2023.mp01seoulculture.models.Review
import java.io.File
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class ReviewEditActivity : AppCompatActivity() {

    val binding: ActivityReviewEditBinding by lazy { ActivityReviewEditBinding.inflate(layoutInflater) }
    //review아이템 초기화
    var review= Review(G.member!!.id, mutableListOf(),"","","","","")
    var photos= Photos(mutableListOf())

    val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(4)) { uris ->
            // Callback is invoked after the user selects media items or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
//                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                uris.forEach {uri ->
                    Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                    Log.d("PhotoPicker", "${uri}")
                    photos.imgUris.add(uri)
                }

            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    //사진 선택창 보여주기위한 리사이클러 뷰
    lateinit var recyclerView: RecyclerView
    var imgs:MutableList<Uri> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //사진선택버튼, 누르고 여러장 선택하여 미리보기
        binding.btnPhotoselect.setOnClickListener { view->
            selectedPhoto()
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //제목은 intent에서 넘겨받기
        review.reviewTitle=intent?.getStringExtra("title").toString()
        binding.tvTitle.setText(review.reviewTitle!!)

        //긴 글쓰기
        review.reviewLong = binding.etLongreview.text.toString()

        //스피너버튼 대체 textinputlayout
        var evalPlace: Array<String> = resources.getStringArray(R.array.evaluation)
        var adapter1 = ArrayAdapter(this,R.layout.list_simple,evalPlace)
        val autoCompleteTextView1:AutoCompleteTextView = binding.dropdownPlace
        autoCompleteTextView1.setAdapter(adapter1)
        autoCompleteTextView1.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var item = parent.getItemAtPosition(position).toString()
            review.reviewPlace= item
            Toast.makeText(this, "장소평가:${review.reviewPlace}", Toast.LENGTH_SHORT).show()
        })

        var evalEquip: Array<String> = resources.getStringArray(R.array.evaluation)
        var adapter2 = ArrayAdapter(this,R.layout.list_simple,evalEquip)
        val autoCompleteTextView2:AutoCompleteTextView = binding.dropdownInstrument
        autoCompleteTextView2.setAdapter(adapter2)
        autoCompleteTextView2.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var item =parent.getItemAtPosition(position).toString()
            review.reviewEquip =item
            Toast.makeText(this, "시설평가:${review.reviewEquip}", Toast.LENGTH_SHORT).show()
        })

        var evalContent: Array<String> = resources.getStringArray(R.array.evaluation)
        var adapter3 = ArrayAdapter(this,R.layout.list_simple,evalContent)
        val autoCompleteTextView3:AutoCompleteTextView = binding.dropdownContent
        autoCompleteTextView3.setAdapter(adapter3)
        autoCompleteTextView3.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var item =parent.getItemAtPosition(position).toString()
            review.reviewContent =item
            Toast.makeText(this, "내용평가:${review.reviewContent}", Toast.LENGTH_SHORT).show()
        })

        //작성버튼 작동
        binding.btnReviewComplete.setOnClickListener { savedReview() }


    }//onCreate()

    override fun onResume() {
        super.onResume()
        var adapter4=PhotoAdapter(this,imgs)
        binding.imgRecycler.adapter=adapter4
        recyclerView=binding.imgRecycler
        recyclerView.adapter=adapter4
    }

    //뒤로가기 버튼 작동하게 하기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    //사진 업로드 버튼 작동 기능
    fun selectedPhoto() {
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        imgs=photos.imgUris

    }

    fun savedReview(){
        // 저장소 세팅
        val storage:FirebaseStorage = Firebase.storage
        var sdf= System.currentTimeMillis()
        var vnum=photos.imgUris.size

        if(vnum==0){
            savedInfoReview()
            return
        }else{
            for(i:Int in photos.imgUris.indices){
                var fileUrl =photos.imgUris[i].toString()
                var filename=fileUrl.substring(fileUrl.lastIndexOf("/")+1,fileUrl.length)
                var name="IMG_"+sdf+filename +".png"
                var imgRef=storage.getReference().child("review").child(name)
                imgRef.putFile(photos.imgUris[i]).addOnSuccessListener {
                    imgRef.downloadUrl.addOnSuccessListener {
                        review.reviewImgs.add(it.toString())
                    }
                    Log.d("uploadphoto","${it.toString()}")
                    vnum=vnum-1
                    Log.d("uploadphoto","${vnum}")
                    if (vnum==0){
                        savedInfoReview()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "파일업로드 오류:"+it, Toast.LENGTH_SHORT).show()
                    Log.d("uploadphoto","${it}")
                }
            }//멀티 이미지 업로드 for문
        }

    }//savedReview()

    fun savedInfoReview(){
        var firestore = FirebaseFirestore.getInstance()
        var ref=firestore.collection("reviews")
        var sdf2=SimpleDateFormat("yyyyMMddHHmmss")
        var docTime= "DOC_"+sdf2.format(Date())
        ref.document(docTime).set(review).addOnSuccessListener {
            Log.d("TAG", "DocumentSnapshot successfully written!")
            Toast.makeText(this, "리뷰업로드 성공", Toast.LENGTH_SHORT).show()
            //리뷰화면(메인의 fragment) 으로 넘어가기
            var intent=Intent(this,MainActivity::class.java)
            intent.putExtra("fragment", "2")
            startActivity(intent)
        }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
    }
}