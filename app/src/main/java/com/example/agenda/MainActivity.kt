package com.example.agenda

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.agenda.ui.theme.AgendaTheme
import com.example.agenda.viewmodel.EventViewModel
import com.example.agenda.database.EventDatabase
import com.example.agenda.models.Event
import com.example.agenda.repository.EventRepository
import com.example.agenda.viewmodel.EventViewModelFactory
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eventDao = EventDatabase.getDatabase(this, lifecycleScope).eventDao()
        val repository = EventRepository(eventDao)
        val viewModelFactory = EventViewModelFactory(repository)

        eventViewModel = ViewModelProvider(this, viewModelFactory).get(EventViewModel::class.java)
        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val title = "Mon événement"
            val description = "Ceci est une description d'événement"
            val date = Date() // la date actuelle
            val startTime = Date() // l'heure de début actuelle
            val endTime = Date() // l'heure de fin actuelle
            val category = "Travail"
            val newEvent = Event(0, title, description, date, startTime, endTime, category)
            eventViewModel.insert(newEvent)
        }


    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        AgendaTheme {
            Greeting("Android")
        }
    }
}