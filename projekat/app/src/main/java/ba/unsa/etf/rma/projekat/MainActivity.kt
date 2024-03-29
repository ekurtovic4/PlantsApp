package ba.unsa.etf.rma.projekat

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.projekat.adapters.BotanicalPlantListAdapter
import ba.unsa.etf.rma.projekat.adapters.CulinaryPlantListAdapter
import ba.unsa.etf.rma.projekat.adapters.MedicalPlantListAdapter
import ba.unsa.etf.rma.projekat.dataetc.Biljka
import ba.unsa.etf.rma.projekat.dataetc.getBiljke

class MainActivity : AppCompatActivity() {
    private lateinit var plants: RecyclerView
    private lateinit var medicalAdapter: MedicalPlantListAdapter
    private lateinit var culinaryAdapter: CulinaryPlantListAdapter
    private lateinit var botanicalAdapter: BotanicalPlantListAdapter
    private var plantsList = getBiljke()
    private lateinit var mod: Spinner
    private lateinit var resetBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        plants = findViewById(R.id.biljkeRV)
        plants.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        medicalAdapter = MedicalPlantListAdapter(listOf()) { biljka -> filterMedical(biljka) }
        culinaryAdapter = CulinaryPlantListAdapter(listOf()) { biljka -> filterCulinary(biljka) }
        botanicalAdapter = BotanicalPlantListAdapter(listOf()) { biljka -> filterBotanical(biljka) }

        plants.adapter = medicalAdapter
        medicalAdapter.updatePlants(plantsList)

        mod = findViewById(R.id.modSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_elements,
            android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mod.adapter = adapter
        }

        mod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val text: String = mod.selectedItem.toString()
                if(text == "Medicinski")
                    showMedical()
                else if(text == "Kuharski")
                    showCulinary()
                else if(text == "Botanički")
                    showBotanical()
            }
        }

        resetBtn = findViewById(R.id.resetBtn)
        resetBtn.setOnClickListener {
            plantsList = getBiljke()
            medicalAdapter.updatePlants(plantsList)
            culinaryAdapter.updatePlants(plantsList)
            botanicalAdapter.updatePlants(plantsList)
        }
    }

    private fun showMedical(){
        plants.adapter = medicalAdapter
        medicalAdapter.updatePlants(plantsList)
    }

    private fun showCulinary(){
        plants.adapter = culinaryAdapter
        culinaryAdapter.updatePlants(plantsList)
    }

    private fun showBotanical(){
        plants.adapter = botanicalAdapter
        botanicalAdapter.updatePlants(plantsList)
    }

    private fun filterMedical(model: Biljka) {
        var newPlantsList: MutableList<Biljka> = mutableListOf()
        for(plant in plantsList){
            if(plant.medicinskeKoristi.any{it in model.medicinskeKoristi})
                newPlantsList.add(plant)
        }
        plantsList = newPlantsList
        medicalAdapter.updatePlants(plantsList)
    }

    private fun filterCulinary(model: Biljka) {
        var newPlantsList: MutableList<Biljka> = mutableListOf()
        for(plant in plantsList){
            if(plant.profilOkusa == model.profilOkusa || plant.jela.any{it in model.jela})
                newPlantsList.add(plant)
        }
        plantsList = newPlantsList
        culinaryAdapter.updatePlants(plantsList)
    }

    private fun filterBotanical(model: Biljka) {
        var newPlantsList: MutableList<Biljka> = mutableListOf()
        for(plant in plantsList){
            if(plant.porodica == model.porodica && plant.zemljisniTipovi.any{it in model.zemljisniTipovi} && plant.klimatskiTipovi.any{it in model.klimatskiTipovi})
                newPlantsList.add(plant)
        }
        plantsList = newPlantsList
        botanicalAdapter.updatePlants(plantsList)
    }
}
