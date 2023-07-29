package mdj.app.assessment.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import mdj.app.assessment.databinding.ActivityMainBinding
import mdj.app.assessment.utils.Helper.Companion.progressDialog

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // view binding
    private lateinit var binding: ActivityMainBinding

    // loader
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoader()
    }

    private fun initLoader() {
        dialog = progressDialog(this)!!
    }

}