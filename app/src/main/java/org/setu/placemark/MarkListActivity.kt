package org.setu.placemark

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.setu.placemark.models.OnMarkListener
import org.setu.placemark.models.PlacedMark
import timber.log.Timber


class MarkListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlacedMarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mark_list)

        recyclerView =
            findViewById(R.id.marksRecyclerView)

        adapter = PlacedMarkAdapter(
            marks = AppData.placedMarks.findAll(),

            listener = object : OnMarkListener {

                override fun onMarkClick(mark: PlacedMark) {
                    editMark(mark)
                }

                override fun onMarkLongClick(mark: PlacedMark): Boolean {
                    showDeleteConfirmationDialog(mark)
                    return true
                }
            }

        )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        val returnButton =
            findViewById<Button>(R.id.returnButton)

        returnButton.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {
            adapter.updateMarks(
                AppData.placedMarks.findAll()
            )
        }
    }

    private fun editMark(mark: PlacedMark) {

        val intent =
            Intent(this, AddEditActivity::class.java)

        intent.putExtra("id", mark.id)
        startActivity(intent)
    }

    private fun showDeleteConfirmationDialog(mark: PlacedMark) {

        AlertDialog.Builder(this)
            .setTitle("Delete this mark?")
            .setMessage("Are you sure you want to delete ${mark.title}?")
            .setPositiveButton("Delete") { _, _ ->
                Timber.i("Deleted mark id=${mark.id}, title=${mark.title}")


        AppData.placedMarks.delete(mark.id)

        adapter.updateMarks(
            AppData.placedMarks.findAll()
        )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}