package com.evan.bazar.ui.auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.evan.bazar.BuildConfig
import com.evan.bazar.R
import com.evan.bazar.interfaces.DialogActionListener
import com.evan.bazar.ui.fragments.StepOneFragment
import com.evan.bazar.ui.fragments.StepTwoFragment
import com.evan.bazar.ui.home.HomeActivity
import com.evan.bazar.ui.interfaces.SignUpInterface
import com.evan.bazar.util.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CreateAccountActivity : AppCompatActivity(),KodeinAware, SignUpInterface {
    override val kodein by kodein()
    private lateinit var viewModel: AuthViewModel
    private val factory: AuthViewModelFactory by instance()
    var progress_bar: ProgressBar? = null
    var mFragManager: FragmentManager? = null
    var fragTransaction: FragmentTransaction? = null
    var mCurrentFrag: Fragment? = null
    private val FRAG_STEP_ONE: Int = 1
    private val FRAG_STEP_TWO: Int = 2
    var btn_step_1: AppCompatButton ?=null
    var btn_step_2: AppCompatButton ?=null
    var step: Boolean?=null
    var name: String=""
    var mobile: String=""
    var email: String=""
    var password: String=""
    var address: String=""
    var image: String=""
    var shopId: String=""
    var shopAddress: String=""
    var agreementDate: String=""
    var shopName: String=""
    var license: String=""
    var root_layout: RelativeLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        viewModel.signUpInterface=this
        root_layout=findViewById(R.id.root_layout)
        progress_bar=findViewById(R.id.progress_bar)
        btn_step_1=findViewById(R.id.btn_step_1)
        btn_step_2=findViewById(R.id.btn_step_2)
        addFragment(FRAG_STEP_ONE,false,null)
        btn_step_1?.setOnClickListener{


            val f = getVisibleFragment()
            if (f != null) {
                if (f is StepOneFragment) {

                    f.value()
                  //  f.showImage(updated_image_url)
                }
                else if (f is StepTwoFragment) {

                    f.value()
                    Log.e("data","data"+name)
                    Log.e("data","data"+mobile)
                    Log.e("data","data"+email)
                    Log.e("data","data"+password)
                    Log.e("data","data"+address)
                    Log.e("data","data"+shopId)
                    Log.e("data","data"+shopAddress)
                    Log.e("data","data"+agreementDate)
                    Log.e("data","data"+shopName)
                    Log.e("data","data"+license)
                    Log.e("data","data"+image)
                    //  f.showImage(updated_image_url)
                    val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    val currentDate = sdf.format(Date())
                    Log.e("currentDate","currentDate"+currentDate)
                    viewModel.signUp(email,password,mobile,name,agreementDate,address,"0",shopId,image,currentDate,shopName,shopAddress,license)
                }
            }




        }
        btn_step_2?.setOnClickListener{
            goToStepOneFragment(name,mobile,email,password,address,image!!)
            val f = getVisibleFragment()
            if (f != null) {
                if (f is StepTwoFragment) {

                    f.value()
                    //  f.showImage(updated_image_url)
                }
            }
            btn_step_2?.visibility=View.GONE

        }
    }
    fun stepOneValue(names:String,mobiles:String,emails:String,passwords:String,addresss:String,images :String){
        name=names
        mobile=mobiles
        email=emails
        password=passwords
        address=addresss
        image=images
        goToStepTwoFragment(shopId!!,agreementDate,shopName,shopAddress,license)
        btn_step_2?.visibility=View.VISIBLE
        btn_step_1?.text="Finish"

    }
    fun stepTwoValue(ids:String,dates:String,name:String,address:String,licenses:String){
        shopId=ids
        agreementDate=dates
        shopName=name
        shopAddress=address
        license=licenses
        btn_step_1?.text="Next"
    }
    fun addFragment(fragId: Int, isHasAnimation: Boolean, obj: Any?) {
        // init fragment manager
        mFragManager = supportFragmentManager
        // create transaction
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }

        var newFrag: Fragment? = null
        // identify which fragment will be called
        when (fragId) {
            FRAG_STEP_ONE -> {
                newFrag = StepOneFragment()
            }
            FRAG_STEP_TWO -> {
                newFrag = StepTwoFragment()
            }

        }
        mCurrentFrag = newFrag
        // init argument
        if (obj != null) {
            val args = Bundle()
        }
        // set animation
        if (isHasAnimation) {
            fragTransaction!!.setCustomAnimations(
                R.anim.view_transition_in_left,
                R.anim.view_transition_out_left,
                R.anim.view_transition_in_right,
                R.anim.view_transition_out_right
            )
        }
        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.add(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()
    }

    fun goToStepOneFragment(name:String,mobile:String,email:String,password:String,address:String,image :String){
        mFragManager = supportFragmentManager
        fragTransaction = mFragManager?.beginTransaction()
        var fragId: Int? = 0
        fragId = FRAG_STEP_ONE
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
        }
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }

        var newFrag: Fragment? = null
        // identify which fragment will be called
        newFrag = StepOneFragment()
        mCurrentFrag = newFrag
        // init argument
        val b = Bundle()
        b.putString("name", name)
        b.putString("mobile", mobile)
        b.putString("email", email)
        b.putString("password", password)
        b.putString("address", address)
        b.putString("image", image)
        newFrag.setArguments(b)
        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )


        fragTransaction?.add(R.id.main_container, newFrag!!, fragId.toString())
        fragTransaction?.addToBackStack(fragId.toString())
        fragTransaction!!.commit()

    }
    fun goToStepTwoFragment(id:String,date:String,name:String,address:String,license:String){
        mFragManager = supportFragmentManager
        fragTransaction = mFragManager?.beginTransaction()
        var fragId: Int? = 0
        fragId = FRAG_STEP_TWO
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
        }
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }

        var newFrag: Fragment? = null
        // identify which fragment will be called
        newFrag = StepTwoFragment()
        mCurrentFrag = newFrag
        // init argument
        val b = Bundle()
        b.putString("id", id)
        b.putString("date", date)
        b.putString("name", name)
        b.putString("address", address)
        b.putString("license", license)
        newFrag.setArguments(b)
        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )


        fragTransaction?.add(R.id.main_container, newFrag!!, fragId.toString())
        fragTransaction?.addToBackStack(fragId.toString())
        fragTransaction!!.commit()
    }
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001
    private val RESULT_TAKE_PHOTO = 10
    private val RESULT_LOAD_IMG = 101
    private val REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE = 1002
    private val RESULT_UPDATE_IMAGE = 11

    fun openImageChooser() {
        showImagePickerDialog(this, object :
            DialogActionListener {
            override fun onPositiveClick() {
                openCamera()
            }

            override fun onNegativeClick() {
                checkGalleryPermission()
            }
        })
    }
    private fun checkGalleryPermission() {
        if (isCameraePermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf<String?>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE
                    )
                } else {
                    //start your camera

                    getImageFromGallery()
                }
            } else {
                getImageFromGallery()
            }
        } else {
            //required permission

            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE
            )
        }
    }
    fun openCamera() {
        if (isCameraePermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED&&checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf<String?>(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE),
                        CAMERA_PERMISSION_REQUEST_CODE
                    )
                } else {
                    //start your camera

                    takePhoto()
                }
            } else {
                takePhoto()
            }
        } else {
            //required permission

            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_CAMERA,
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    // Receive the permissions request result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here

                    takePhoto()
                }
            }
            REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                    getImageFromGallery()
                }
            }
        }
    }

    private var mTakeUri: Uri? = null
    private var mFile: File? = null
    private var mCurrentPhotoPath: String? = null
    private fun takePhoto() {
        val intent =
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            mFile = null
            try {
                mFile = createImageFile(this)
                mCurrentPhotoPath = mFile?.getAbsolutePath()
            } catch (ex: IOException) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created


            if (mFile != null) {
                mTakeUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID, mFile!!
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                } else {
                    val packageManager: PackageManager =
                        this.getPackageManager()
                    val activities: List<ResolveInfo> =
                        packageManager.queryIntentActivities(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        )
                    for (resolvedIntentInfo in activities) {
                        val packageName: String? =
                            resolvedIntentInfo.activityInfo.packageName
                        this.grantUriPermission(
                            packageName,
                            mTakeUri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mTakeUri)
                startActivityForResult(intent, RESULT_TAKE_PHOTO)
            }
        }
    }

    private fun getImageFromGallery() {
        val photoPickerIntent =
            Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        Log.e("requestCode", resultCode.toString() + " requestCode" + requestCode)
        Log.e("RESULT_OK", "RESULT_OK" + RESULT_OK)


        when (requestCode) {
            RESULT_LOAD_IMG -> {
                try {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        val file = File(getRealPathFromURI(imageUri, this))
                        goImagePreviewPage(imageUri, file)
                    }
                } catch (e: Exception) {
                    Log.e("exc", "" + e.message)
                    Toast.makeText(this,"Can not found this image", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            RESULT_TAKE_PHOTO -> {
                //return if photopath is null
                if(mCurrentPhotoPath == null)
                    return
                mCurrentPhotoPath = getRightAngleImage(mCurrentPhotoPath!!)
                try {
                    val imgFile = File(mCurrentPhotoPath)
                    if (imgFile.exists()) {
                        goImagePreviewPage(mTakeUri, imgFile)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            RESULT_UPDATE_IMAGE -> {
                if (data != null && data?.hasExtra("updated_image_url")) {
                    val updated_image_url: String? = data?.getStringExtra("updated_image_url")
                    Log.e("updated_image_url", "--$updated_image_url")
                    if (updated_image_url != null) {
                        if (updated_image_url == "") {

                        } else {
                            //update in
                            val f = getVisibleFragment()
                            if (f != null) {
                                if (f is StepOneFragment) {

                                    f.showImage(updated_image_url)
                                }
                                }



                        }
                    }
                }
            }





        }

    }
    fun getVisibleFragment(): Fragment? {
        val fragmentManager = mFragManager
        val fragments = fragmentManager?.fragments
        Collections.reverse(fragments)
        for (fragment in fragments!!) {
            if (fragment != null && fragment.isVisible) {
                return fragment
            }
        }
        return null
    }
    fun goImagePreviewPage(uri: Uri?, imageFile: File) {
        val fileSize = imageFile.length().toInt()
        if (fileSize <= SERVER_SEND_FILE_SIZE_MAX_LIMIT) {
            val i = Intent(
                this,
                ImageUpdateActivity::class.java
            )
            i.putExtra("from", FRAG_CREATE_NEW_DELIVERY)
            temporary_profile_picture = imageFile
            temporary_profile_picture_uri = uri
            startActivityForResult(i, RESULT_UPDATE_IMAGE)
            overridePendingTransition(R.anim.right_to_left, R.anim.stand_by)
        } else {
            showDialogSuccessMessage(
                this,
                resources.getString(R.string.image_size_is_too_large),
                resources.getString(R.string.txt_close),


                null
            )
        }
    }

    override fun onStartProgress() {
        progress_bar?.show()

    }

    override fun onEndProgress() {
        progress_bar?.hide()

    }

    override fun onSignUpSuccess(message: String) {
        progress_bar?.hide()
        root_layout?.snackbar(message)
        Toast.makeText(this,"Successfully Register and Please wait for admin verify",Toast.LENGTH_SHORT).show()
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onSignUpFailed(message: String) {
        progress_bar?.hide()
        root_layout?.snackbar(message)
    }
}
