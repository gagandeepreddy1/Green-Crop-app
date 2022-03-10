package com.greencrop.activities.HomeScreen.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.greencrop.marketlist.MarketActivity
import com.greencrop.R
import com.greencrop.machine_learning.GetNameWiseMLActivity
import com.greencrop.databinding.FragmentHomeBinding
import com.greencrop.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    lateinit var bitmap: Bitmap

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var veeraColor: String? = null;
    lateinit var img_view: ImageView
    var veeraTimeFormat: kotlin.String? = "HH:mm:ss"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textClimate: TextView = binding.textClimate
        val textFruits: TextView = binding.textFruits
        val textVegetables: TextView = binding.textVegetables
        val select_image_button: Button = binding.btnCamera
        val camerabtn: Button = binding.btnGallary
        val make_prediction: Button = binding.classify
        img_view = binding.image
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textClimate.text = it
            textFruits.text = "FRUITS"
            textVegetables.text = "VEGETABLES"
        })

        textClimate.setOnClickListener() {
            it.findNavController().navigate(R.id.nav_weather)
        }
        textFruits.setOnClickListener() {
            var i = Intent(activity, MarketActivity::class.java)
            i.putExtra("cat", "Fruits")
            startActivity(i)
        }
        textVegetables.setOnClickListener() {
            var i = Intent(activity, MarketActivity::class.java)
            i.putExtra("cat", "Vegetables")
            startActivity(i)
        }
        setSettings()

        val labels =
            requireActivity().assets.open("labels.txt").bufferedReader().use { it.readText() }
                .split("\n")

        select_image_button.setOnClickListener {
            Log.d("mssg", "button pressed")
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 250)
        }

        make_prediction.setOnClickListener{
            try {
                var resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
                val model = Model.newInstance(requireContext())
                var tbuffer = TensorImage.fromBitmap(resized)
                var byteBuffer = tbuffer.buffer
                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
                inputFeature0.loadBuffer(byteBuffer)
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                var max = getMax(outputFeature0.floatArray)
                //  make_prediction.setText(labels[max])
                var i = Intent(activity, GetNameWiseMLActivity::class.java)
                i.putExtra("name", labels[max])
                i.putExtra("BitmapImage", resized)
                startActivity(i)
                model.close()
            }catch (e:Exception){
                Toast.makeText(it.context,"No Image Selected",Toast.LENGTH_LONG).show()
            }
        }

        camerabtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                )
                == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.CAMERA
                    ), 1
                );
            }else{
                var camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(camera, 200)
            }
        }



        return root
    }

    fun setSettings() {
        val date = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Date())
        binding.veeraTVDate.setText(date)
        clock()
        val prefs = requireActivity().getSharedPreferences("SharedData", Context.MODE_PRIVATE)
        veeraTimeFormat =
            prefs.getString("TimeFormat", "HH:mm:ss") //"No name defined" is the default value.

        veeraColor = prefs.getString("color", "")
        if (veeraColor.equals("Red", ignoreCase = true)) {
            binding.veeraBackgroundLayout.setBackgroundColor(Color.parseColor("#CDF97171"))
        } else if (veeraColor.equals("Blue", ignoreCase = true)) {
            binding.veeraBackgroundLayout.setBackgroundColor(Color.parseColor("#5172E7"))
        } else if (veeraColor.equals("Green", ignoreCase = true)) {
            binding.veeraBackgroundLayout.setBackgroundColor(Color.parseColor("#5CB559"))
        }
    }

    private fun clock() {
        val hander = Handler()
        Thread {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            hander.post {
                getTime()
                clock()
            }
        }.start()
    }

    fun getTime() {
        try {
            val dateFormat = SimpleDateFormat(veeraTimeFormat, Locale.getDefault())
            binding.veeraTVTime.text = dateFormat.format(Calendar.getInstance().time)
        } catch (e: Exception) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try{
            if (requestCode == 250) {
                img_view.setImageURI(data?.data)

                var uri: Uri? = data?.data
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            } else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
                bitmap = data?.extras?.get("data") as Bitmap
                img_view.setImageBitmap(bitmap)
            }


        }catch (e:Exception){}

    }

    fun getMax(arr: FloatArray): Int {
        var ind = 0;
        var min = 0.0f;

        for (i in 0..6) {
            if (arr[i] > min) {
                min = arr[i]
                ind = i;
            }
        }
        return ind
    }

}