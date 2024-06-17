package ba.unsa.etf.rma.projekat.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.projekat.R
import ba.unsa.etf.rma.projekat.adapters.BotanicalPlantListAdapter
import ba.unsa.etf.rma.projekat.adapters.CulinaryPlantListAdapter
import ba.unsa.etf.rma.projekat.adapters.MedicalPlantListAdapter
import ba.unsa.etf.rma.projekat.dataetc.Biljka
import ba.unsa.etf.rma.projekat.dataetc.BiljkaDatabase
import ba.unsa.etf.rma.projekat.dataetc.getBiljke
import ba.unsa.etf.rma.projekat.web.TrefleDAO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var plants: RecyclerView
    private lateinit var medicalAdapter: MedicalPlantListAdapter
    private lateinit var culinaryAdapter: CulinaryPlantListAdapter
    private lateinit var botanicalAdapter: BotanicalPlantListAdapter
    private var plantsList = mutableListOf<Biljka>()
    private lateinit var mod: Spinner
    private lateinit var resetBtn: Button
    private lateinit var novaBiljkaBtn: Button
    private lateinit var pretragaET: EditText
    private lateinit var bojaSPIN: Spinner
    private lateinit var brzaPretraga: Button

    private var trefleDAO = TrefleDAO()
    private var brzaPretragaMod = false

    private lateinit var biljkaDao: BiljkaDatabase.BiljkaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //database
        biljkaDao = BiljkaDatabase.getInstance(this).biljkaDao()

        //trefleDAO
        trefleDAO.setContext(this)

        //RV i adapteri
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

        lifecycleScope.launch{
            plantsList = biljkaDao.getAllBiljkas().toMutableList()
            if(plantsList.isEmpty()){
                biljkaDao.insertAll(getBiljke())
                plantsList = biljkaDao.getAllBiljkas().toMutableList()
            }
            medicalAdapter.updatePlants(plantsList)
        }

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

        val height = plants.layoutParams.height
        mod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val text: String = mod.selectedItem.toString()
                when (text) {
                    "Medicinski" -> {
                        val parameters = plants.layoutParams
                        parameters.height = height
                        plants.requestLayout()

                        pretragaET.visibility = View.GONE
                        bojaSPIN.visibility = View.GONE
                        brzaPretraga.visibility = View.GONE

                        if(brzaPretragaMod)
                            lifecycleScope.launch{
                                plantsList = biljkaDao.getAllBiljkas().toMutableList()
                                showMedical()
                            }
                        else{
                            showMedical()
                        }
                    }
                    "Kuharski" -> {
                        val parameters = plants.layoutParams
                        parameters.height = height
                        plants.requestLayout()

                        pretragaET.visibility = View.GONE
                        bojaSPIN.visibility = View.GONE
                        brzaPretraga.visibility = View.GONE

                        if(brzaPretragaMod)
                            lifecycleScope.launch{
                                plantsList = biljkaDao.getAllBiljkas().toMutableList()
                                showCulinary()
                            }
                        else{
                            showCulinary()
                        }
                    }
                    "Botanički" -> {
                        val parameters = plants.layoutParams
                        parameters.height = height - (pretragaET.layoutParams.height + bojaSPIN.layoutParams.height) * 2
                        plants.requestLayout()

                        pretragaET.visibility = View.VISIBLE
                        bojaSPIN.visibility = View.VISIBLE
                        brzaPretraga.visibility = View.VISIBLE
                        showBotanical()
                    }
                }
            }
        }

        //reset button
        resetBtn = findViewById(R.id.resetBtn)
        resetBtn.setOnClickListener {
            lifecycleScope.launch{
                plantsList = biljkaDao.getAllBiljkas().toMutableList()
                medicalAdapter.updatePlants(plantsList)
                culinaryAdapter.updatePlants(plantsList)
                botanicalAdapter.updatePlants(plantsList)
                pretragaET.setText("")
                bojaSPIN.setSelection(0)
            }
        }

        //nova biljka button
        novaBiljkaBtn = findViewById(R.id.novaBiljkaBtn)
        novaBiljkaBtn.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        //elementi za pretragu
        pretragaET = findViewById(R.id.pretragaET)
        bojaSPIN = findViewById(R.id.bojaSPIN)
        brzaPretraga = findViewById(R.id.brzaPretraga)
        pretragaET.visibility = View.GONE
        bojaSPIN.visibility = View.GONE
        brzaPretraga.visibility = View.GONE

        //boja spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.color_spinner_elements,
            android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bojaSPIN.adapter = adapter
        }

        //brza pretraga button
        brzaPretraga.setOnClickListener {
            if(pretragaET.text.isNotEmpty()){
                lifecycleScope.launch{
                    try{
                        brzaPretragaMod = true
                        plantsList = trefleDAO.getPlantsWithFlowerColor(bojaSPIN.selectedItem.toString(), pretragaET.text.toString()).toMutableList()
                        botanicalAdapter.updatePlants(plantsList)
                    }
                    catch(e: Exception){
                        Toast.makeText(this@MainActivity, "Nije moguce dohvatiti biljke s web servisa", Toast.LENGTH_LONG).show()
                        brzaPretragaMod = false
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val receivedObject = data?.getParcelableExtra<Biljka>("EXTRA_OBJECT")

            if (receivedObject != null) {
                lifecycleScope.launch{
                    biljkaDao.saveBiljka(receivedObject)
                    plantsList = biljkaDao.getAllBiljkas().toMutableList()
                    mod.setSelection(0)
                    showMedical()
                }
            }
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
        pretragaET.setText("")
        bojaSPIN.setSelection(0)
    }

    private fun filterMedical(model: Biljka) {
        val newPlantsList: MutableList<Biljka> = mutableListOf()
        for(plant in plantsList){
            if(plant.medicinskeKoristi.any{ model.medicinskeKoristi.contains(it) })
                newPlantsList.add(plant)
        }
        plantsList = newPlantsList
        medicalAdapter.updatePlants(plantsList)
    }

    private fun filterCulinary(model: Biljka) {
        val newPlantsList: MutableList<Biljka> = mutableListOf()
        for(plant in plantsList){
            if(plant.profilOkusa == model.profilOkusa || plant.jela.any{it in model.jela})
                newPlantsList.add(plant)
        }
        plantsList = newPlantsList
        culinaryAdapter.updatePlants(plantsList)
    }

    private fun filterBotanical(model: Biljka) {
        if(brzaPretragaMod) return

        val newPlantsList: MutableList<Biljka> = mutableListOf()
        for(plant in plantsList){
            if(plant.porodica == model.porodica && plant.zemljisniTipovi.any{it in model.zemljisniTipovi} && plant.klimatskiTipovi.any{it in model.klimatskiTipovi})
                newPlantsList.add(plant)
        }
        plantsList = newPlantsList
        botanicalAdapter.updatePlants(plantsList)
    }

    companion object {
        const val REQUEST_CODE = 123
    }
}
