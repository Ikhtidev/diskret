package uz.ikhtidev.diskret.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import uz.ikhtidev.diskret.R
import uz.ikhtidev.diskret.adapters.ThemeAdapter
import uz.ikhtidev.diskret.database.ThemeDatabase
import uz.ikhtidev.diskret.databinding.ActivityMainBinding
import uz.ikhtidev.diskret.databinding.LayoutMenuDialogBinding
import uz.ikhtidev.diskret.utils.Constants.Companion.DIALOG_ITEM
import uz.ikhtidev.diskret.utils.Constants.Companion.FILE_NUMBER

class MainActivity : AppCompatActivity(), ThemeAdapter.ThemeClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog: LayoutMenuDialogBinding

    private val themeDatabase: ThemeDatabase by lazy {
        ThemeDatabase.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val themes = ArrayList(themeDatabase.themeDao().getAllThemes())
        val themeAdapter = ThemeAdapter(themes)
        themeAdapter.setListener(this)
        binding.apply {

            rvTheme.adapter = themeAdapter
            btnMenu.setOnClickListener {
                showMenuDialogBox()
            }
            lottiMenu.setAnimation(R.raw.menu)
        }
    }

    private fun showMenuDialogBox() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        bindingDialog = LayoutMenuDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.TOP or Gravity.END)
        val layoutParams:ViewGroup.MarginLayoutParams = bindingDialog.root.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(0,150,70,0)
        bindingDialog.root.requestLayout()

        bindingDialog.apply {

            uquvMeyoriy.setOnClickListener {
                if (bindingDialog.subMenuItems.visibility == View.VISIBLE)
                    bindingDialog.subMenuItems.visibility = View.GONE
                else bindingDialog.subMenuItems.visibility = View.VISIBLE
            }
            kidt.setOnClickListener {
                openPdfViewer(getString(R.string.kidt))
            }
            att.setOnClickListener {
                openPdfViewer(getString(R.string.att))
            }
            namunaviyDastur.setOnClickListener {
                openPdfViewer(getString(R.string.namunaviy_dastur))
            }
            ishchiDastur.setOnClickListener {
                openPdfViewer(getString(R.string.ishchi_dastur))
            }
            fanHaqida.setOnClickListener {
                openPdfViewer(getString(R.string.fan_haqida_ma_lumot))
            }
            muallifHaqida.setOnClickListener {
                openPdfViewer(getString(R.string.muallif_haqida))
            }
            crossword.setOnClickListener {
                openPdfViewer(getString(R.string.crossword))
            }
            mustaqilTaLim.setOnClickListener {
                openPdfViewer(getString(R.string.mustaqil_ta_lim))
            }
            adabiyotlarRuyxati.setOnClickListener {
                openPdfViewer(getString(R.string.adabiyotlar_ro_yxati))
            }
            natijalarim.setOnClickListener {
                startActivity(Intent(this@MainActivity, ResultsActivity::class.java))
            }

        }

        dialog.show()
    }

    private fun openPdfViewer(dialogItem: String) {
        FILE_NUMBER = -1
        DIALOG_ITEM = dialogItem
        startActivity(Intent(this@MainActivity, PdfViewerActivity::class.java))
    }

    override fun onThemeClick(position: Int) {
        FILE_NUMBER = position
        startActivity(Intent(this@MainActivity, ThemeDetailActivity::class.java))
    }

}
