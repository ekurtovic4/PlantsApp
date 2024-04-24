package ba.unsa.etf.rma.projekat.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.projekat.R
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
    private var plantsList: MutableList<Biljka> = getBiljke().toMutableList()
    private lateinit var tempPlantsList: MutableList<Biljka>
    private lateinit var mod: Spinner
    private lateinit var resetBtn: Button
    private lateinit var novaBiljkaBtn: Button

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

        tempPlantsList = plantsList
        plants.adapter = medicalAdapter
        medicalAdapter.updatePlants(tempPlantsList)

        //mod spinner
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
                when (text) {
                    "Medicinski" -> showMedical()
                    "Kuharski" -> showCulinary()
                    "Botanički" -> showBotanical()
                }
            }
        }

        //reset button
        resetBtn = findViewById(R.id.resetBtn)
        resetBtn.setOnClickListener {
            tempPlantsList = plantsList
            medicalAdapter.updatePlants(tempPlantsList)
            culinaryAdapter.updatePlants(tempPlantsList)
            botanicalAdapter.updatePlants(tempPlantsList)
        }

        //nova biljka button
        novaBiljkaBtn = findViewById(R.id.novaBiljkaBtn)
        novaBiljkaBtn.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val receivedObject = data?.getParcelableExtra<Biljka>("EXTRA_OBJECT")

            if (receivedObject != null) {
                plantsList.add(receivedObject)
                tempPlantsList = plantsList
                showMedical()
            }
        }
    }

    private fun showMedical(){
        plants.adapter = medicalAdapter
        medicalAdapter.updatePlants(tempPlantsList)
    }

    private fun showCulinary(){
        plants.adapter = culinaryAdapter
        culinaryAdapter.updatePlants(tempPlantsList)
    }

    private fun showBotanical(){
        plants.adapter = botanicalAdapter
        botanicalAdapter.updatePlants(tempPlantsList)
    }

    private fun filterMedical(model: Biljka) {
        val newPlantsList: MutableList<Biljka> = mutableListOf()
        for(plant in tempPlantsList){
            if(plant.medicinskeKoristi.any{ model.medicinskeKoristi.contains(it) })
                newPlantsList.add(plant)
        }
        tempPlantsList = newPlantsList
        medicalAdapter.updatePlants(tempPlantsList)
    }

    private fun filterCulinary(model: Biljka) {
        val newPlantsList: MutableList<Biljka> = mutableListOf()
        for(plant in tempPlantsList){
            if(plant.profilOkusa == model.profilOkusa || plant.jela.any{it in model.jela})
                newPlantsList.add(plant)
        }
        tempPlantsList = newPlantsList
        culinaryAdapter.updatePlants(tempPlantsList)
    }

    private fun filterBotanical(model: Biljka) {
        val newPlantsList: MutableList<Biljka> = mutableListOf()
        for(plant in tempPlantsList){
            if(plant.porodica == model.porodica && plant.zemljisniTipovi.any{it in model.zemljisniTipovi} && plant.klimatskiTipovi.any{it in model.klimatskiTipovi})
                newPlantsList.add(plant)
        }
        tempPlantsList = newPlantsList
        botanicalAdapter.updatePlants(tempPlantsList)
    }

    companion object {
        const val REQUEST_CODE = 123
    }
}
