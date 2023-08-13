package uz.ikhtidev.diskret.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.ikhtidev.diskret.databinding.ActivityPdfViewerBinding
import uz.ikhtidev.diskret.utils.Constants.Companion.DIALOG_ITEM
import uz.ikhtidev.diskret.utils.Constants.Companion.FILE_NUMBER
import uz.ikhtidev.diskret.utils.Constants.Companion.FILE_TYPE
import uz.ikhtidev.diskret.utils.TYPE

class PdfViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfViewerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fileName: String =
            if (FILE_NUMBER == -1)
                "$DIALOG_ITEM.pdf"
            else
                "${(FILE_NUMBER + 1)}.${FILE_TYPE}.pdf"

        binding.apply {
            toolbarName.text =
                if (FILE_NUMBER == -1)
                    DIALOG_ITEM
                else
                    TYPE.from(FILE_TYPE)?.typeName
            btnBack.setOnClickListener {
                finish()
            }
            pdfView.fromAsset(fileName)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(false)
                .defaultPage(0)
                .load()
        }

    }
}