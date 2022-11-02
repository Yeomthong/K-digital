import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.tabell.MainActivity
import com.example.tabell.R
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.fragment_qr.*
import kotlinx.android.synthetic.main.fragment_qr.view.*
import java.io.File

// 카메라(사진 찍기)
class QrFragment : Fragment() {
    // Permisisons
//    val PERMISSIONS = arrayOf(
//        Manifest.permission.CAMERA,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//        Manifest.permission.READ_EXTERNAL_STORAGE    )
//    val PERMISSIONS_REQUEST = 100
    // Request Code
//    private val BUTTON1 = 100
    // 어디에 어떤 이름으로 저장? -> url로 표현
    var uri : Uri? = null
    // MainActivity의 context(가 저장 위한 파일 주소 가지고 있어서 필요)
    lateinit var mContext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qr, container, false)

        // 사진 찍기 버튼 누르면 사진찍기
        view.btnCapture.setOnClickListener {
            takePhoto()
        }

        return view
    }

    fun checkPermission() {

    }
    // 사진 찍기
    fun takePhoto() {
        // 찍은 사진 자동 저장
        val cameraPermission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
        // externalCacheDir=액티비티는 외부 데이터 저장 가능한 공간
        val capturedFile = File(requireActivity().externalCacheDir, "daptured.jpg")
        // 캡쳐 이미지가 이미 있으면 지우고
        if (capturedFile.exists()) capturedFile.delete()
        // 지금 찍은 사진 저장
        capturedFile.createNewFile()
        // 어디에 어떤 이름으로 저장? -> url로 표현
        // 버전이 24보다 큰 경우
        uri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(mContext, "com.example.mycamera.fileprovider", capturedFile)
        }
        // 그렇지 않은 겅우
        else {
            Uri.fromFile(capturedFile)
        }

        // 사진 저장
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        // 저장할 파일 주소 같이 저장
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
        if (Build.VERSION.SDK_INT == 30) {
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                // 카메라 권한이 거절된 상태일 경우

//                Toast.makeText(requireContext(),"1", Toast.LENGTH_LONG).show()
            } else {
//                Toast.makeText(requireContext(),"2", Toast.LENGTH_LONG).show()
                requireContext().startActivity(intent)
            }
        }
        else{
//            Toast.makeText(requireContext(),Build.VERSION.SDK_INT.toString(), Toast.LENGTH_LONG).show()
              requireContext().startActivity(intent)
        }

//     101 말고 다른 숫자 가능

//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if(takePictureIntent.resolveActivity(requireActivity().packageManager)!=null) {
//            startActivityForResult(takePictureIntent, BUTTON1)    }
    }

    // 프렉먼트가 메인 액티비티에 붙을 때 자동으로 호출
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        // context가 MainActivity면 MainActivity의 context를 사용
        if (context is MainActivity) {
            mContext = context as MainActivity
        }
    }

    // 이미지 저장 잘 되고 응답도 잘 오면
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 요청한 requestCode가 101이 맞다면
        when (requestCode) {
            101 -> {
                // 올바르게 저장됐다면
                if (resultCode == Activity.RESULT_OK) {
                    // uri로 해당 이미지 불러오기
                    val bitmap = BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(uri!!))
                    // 화면에 보이기
                    imgCapture.setImageBitmap(bitmap)
                }
            }
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        for(result in grantResults){
//            if(result != PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(requireContext(), "권한 승인 부탁드립니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    companion object {
    }
}