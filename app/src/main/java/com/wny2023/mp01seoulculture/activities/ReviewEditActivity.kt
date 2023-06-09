package com.wny2023.mp01seoulculture.activities


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wny2023.mp01seoulculture.G
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.adapters.PhotoAdapter
import com.wny2023.mp01seoulculture.databinding.ActivityReviewEditBinding
import com.wny2023.mp01seoulculture.models.Review

class ReviewEditActivity : AppCompatActivity() {

    val binding: ActivityReviewEditBinding by lazy { ActivityReviewEditBinding.inflate(layoutInflater) }
    //review아이템 초기화
    var review= Review(G.member!!.id, mutableListOf(),"","","","")
//    val db = Firebase.firestore
    val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(4)) { uris ->
            // Callback is invoked after the user selects media items or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
//                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                uris.forEach {uri ->
                    Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                    Log.d("PhotoPicker", "${uri}")
                    review.reviewImgs.add(uri)
                }

            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    //사진 선택창 보여주기위한 리사이클러 뷰
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //사진선택버튼
        binding.btnPhotoselect.setOnClickListener {view->selectedPhoto()}

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

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

    }//onCreate()

    //뒤로가기 버튼 작동하게 하기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun selectedPhoto(){
        var imgs:MutableList<Uri> = mutableListOf()
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        imgs=review.reviewImgs
        var adapter4=PhotoAdapter(this,imgs)
        binding.imgRecycler.adapter=adapter4
        recyclerView=binding.imgRecycler
        recyclerView.adapter=adapter4
        Log.d("selectphoto","${imgs}")
    }
}