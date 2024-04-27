package ba.unsa.etf.rma.projekat.activities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AbsListView.CHOICE_MODE_MULTIPLE
import android.widget.AbsListView.CHOICE_MODE_SINGLE
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import ba.unsa.etf.rma.projekat.R
import ba.unsa.etf.rma.projekat.dataetc.Biljka
import ba.unsa.etf.rma.projekat.dataetc.KlimatskiTip
import ba.unsa.etf.rma.projekat.dataetc.MedicinskaKorist
import ba.unsa.etf.rma.projekat.dataetc.ProfilOkusaBiljke
import ba.unsa.etf.rma.projekat.dataetc.Zemljiste

class NovaBiljkaActivity : AppCompatActivity() {
    private lateinit var medicinskaKoristLV: ListView
    private lateinit var klimatskiTipLV: ListView
    private lateinit var zemljisniTipLV: ListView
    private lateinit var profilOkusaLV: ListView
    private lateinit var jelaLV: ListView

    private lateinit var medicinskaKoristAdapter: ArrayAdapter<String>
    private lateinit var klimatskiTipAdapter: ArrayAdapter<String>
    private lateinit var zemljisniTipAdapter: ArrayAdapter<String>
    private lateinit var profilOkusaAdapter: ArrayAdapter<String>
    private lateinit var jelaAdapter: ArrayAdapter<String>

    private lateinit var jela: ArrayList<String>
    var izmjena = false //false za dodaj jelo, true za izmijeni jelo
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var slikaIV: ImageView
    private lateinit var nestedScrollView: NestedScrollView

    private lateinit var dodajJeloBtn: Button
    private lateinit var dodajBiljkuBtn: Button
    private lateinit var uslikajBiljkuBtn: Button

    private lateinit var nazivET: EditText
    private lateinit var porodicaET: EditText
    private lateinit var medicinskoUpozorenjeET: EditText
    private lateinit var jeloET: EditText

    private lateinit var medicinskaKoristTxt: TextView
    private lateinit var klimatskiTipTxt: TextView
    private lateinit var zemljisniTipTxt: TextView
    private lateinit var profilOkusaTxt: TextView
    private lateinit var jelaTxt: TextView

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)

        //medicinska korist
        medicinskaKoristLV = findViewById(R.id.medicinskaKoristLV)
        medicinskaKoristLV.choiceMode = CHOICE_MODE_MULTIPLE
        val medicinskaKoristEntries = MedicinskaKorist.entries.map { it.opis }
        medicinskaKoristAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, medicinskaKoristEntries.toTypedArray())
        medicinskaKoristLV.adapter = medicinskaKoristAdapter
        medicinskaKoristAdapter.notifyDataSetChanged()

        //klimatski tip
        klimatskiTipLV = findViewById(R.id.klimatskiTipLV)
        klimatskiTipLV.choiceMode = CHOICE_MODE_MULTIPLE
        val klimatskiTipEntries = KlimatskiTip.entries.map { it.opis }
        klimatskiTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, klimatskiTipEntries.toTypedArray())
        klimatskiTipLV.adapter = klimatskiTipAdapter
        klimatskiTipAdapter.notifyDataSetChanged()

        //zemljisni tip
        zemljisniTipLV = findViewById(R.id.zemljisniTipLV)
        zemljisniTipLV.choiceMode = CHOICE_MODE_MULTIPLE
        val zemljisniTipEntries = Zemljiste.entries.map { it.naziv }
        zemljisniTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, zemljisniTipEntries.toTypedArray())
        zemljisniTipLV.adapter = zemljisniTipAdapter
        zemljisniTipAdapter.notifyDataSetChanged()

        //profil okusa
        profilOkusaLV = findViewById(R.id.profilOkusaLV)
        profilOkusaLV.choiceMode = CHOICE_MODE_SINGLE
        val profilOkusaEntries = ProfilOkusaBiljke.entries.map { it.opis }
        profilOkusaAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, profilOkusaEntries.toTypedArray())
        profilOkusaLV.adapter = profilOkusaAdapter
        profilOkusaAdapter.notifyDataSetChanged()

        //jela
        jelaLV = findViewById(R.id.jelaLV)
        jela = ArrayList()
        jela.add("probno jelo")
        jelaAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jela)
        jelaLV.adapter = jelaAdapter

        val item = jelaAdapter.getView(0, null, jelaLV)
        item.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val singleHeight = item.measuredHeight
        var totalHeight = singleHeight

        val parameters = jelaLV.layoutParams
        parameters.height = totalHeight
        jelaLV.layoutParams = parameters
        jelaLV.requestLayout()

        jela.remove("probno jelo")

        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)
        jeloET = findViewById(R.id.jeloET)

        var clicked = ""
        nestedScrollView = findViewById(R.id.nestedScrollView)

        dodajJeloBtn.setOnClickListener {
            dodajIzmijeniJelo(totalHeight, singleHeight, clicked)
        }

        jelaLV.setOnItemClickListener { parent, view, position, id ->
            clicked = jelaAdapter.getItem(position).toString()
            jeloET.setText(clicked)
            dodajJeloBtn.text = "Izmijeni jelo"
            izmjena = true
        }

        //uslikaj biljku
        slikaIV = findViewById(R.id.slikaIV)
        uslikajBiljkuBtn = findViewById(R.id.uslikajBiljkuBtn)
        uslikajBiljkuBtn.setOnClickListener {
            uslikaj()
        }

        //dodaj biljku
        nazivET = findViewById(R.id.nazivET)
        porodicaET = findViewById(R.id.porodicaET)
        medicinskoUpozorenjeET = findViewById(R.id.medicinskoUpozorenjeET)
        dodajBiljkuBtn = findViewById(R.id.dodajBiljkuBtn)

        medicinskaKoristTxt = findViewById(R.id.medicinskaKoristTxt)
        klimatskiTipTxt = findViewById(R.id.klimatskiTipTxt)
        zemljisniTipTxt = findViewById(R.id.zemljisniTipTxt)
        profilOkusaTxt = findViewById(R.id.profilOkusaTxt)
        jelaTxt = findViewById(R.id.jelaTxt)

        dodajBiljkuBtn.setOnClickListener {
            if(validate()){
                val biljka = novaBiljka()
                val returnIntent = Intent()
                returnIntent.putExtra("EXTRA_OBJECT", biljka)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }

    private fun dodajIzmijeniJelo(total: Int, singleHeight: Int, clicked: String){
        val parameters = jelaLV.layoutParams
        val txt = jeloET.text.toString()
        var totalHeight = total
        var error = false

        if(txt.isNotEmpty()){
            if(jela.any { it.equals(txt, ignoreCase = true) } && (!izmjena || txt != clicked)){
                error = true
                jeloET.setError("Jelo već postoji")
                nestedScrollView.smoothScrollTo(0, jeloET.top)
            }
            else if(txt.length !in 2..20){
                error = true
                jeloET.setError("Dužina mora biti između 2 i 20 karaktera")
                nestedScrollView.smoothScrollTo(0, jeloET.top)
            }
            else if(izmjena){
                val index = jela.indexOf(clicked)
                jela[index] = txt
                jelaAdapter.notifyDataSetChanged()
                jeloET.setText("")
                dodajJeloBtn.text = "Dodaj jelo"
                izmjena = false
            }
            else{
                jela.add(txt)
                jelaAdapter.notifyDataSetChanged()
                jeloET.setText("")
                if(jeloET.error != null)
                    jeloET.setError(null)
                totalHeight += singleHeight
            }
        }
        else if(izmjena){
            jela.remove(clicked)
            totalHeight -= singleHeight
            jelaAdapter.notifyDataSetChanged()
            jeloET.setText("")
            dodajJeloBtn.text = "Dodaj jelo"
            izmjena = false
        }

        if(!error){
            parameters.height = totalHeight + (jelaLV.dividerHeight * (jelaAdapter.count - 1))
            jelaLV.layoutParams = parameters
            jelaLV.requestLayout()

            nestedScrollView.post{
                nestedScrollView.smoothScrollTo(0, jelaLV.bottom)
            }
        }
    }

    private fun uslikaj(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Greška s kamerom", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            slikaIV.setImageBitmap(imageBitmap)
        }
    }

    private fun validate() : Boolean{
        var returnValue = true

        //EditTexts
        if(nazivET.text.length !in 2..20){
            nazivET.setError("Dužina mora biti između 2 i 20 karaktera")
            nestedScrollView.smoothScrollTo(0, nestedScrollView.top)
            returnValue = false
        }
        if(porodicaET.text.length !in 2..20){
            porodicaET.setError("Dužina mora biti između 2 i 20 karaktera")
            if(returnValue)
                nestedScrollView.smoothScrollTo(0, porodicaET.top)
            returnValue = false
        }
        if(medicinskoUpozorenjeET.text.length !in 2..20){
            medicinskoUpozorenjeET.setError("Dužina mora biti između 2 i 20 karaktera")
            if(returnValue)
                nestedScrollView.smoothScrollTo(0, medicinskoUpozorenjeET.top)
            returnValue = false
        }
        if(jela.isEmpty()){
            jeloET.setError("Nije dodano nijedno jelo u listu")
            if(returnValue)
                nestedScrollView.smoothScrollTo(0, jeloET.top)
            returnValue = false
        }

        //ListViews
        if((0 until medicinskaKoristLV.count).count { medicinskaKoristLV.isItemChecked(it) } < 1 ||
            (0 until klimatskiTipLV.count).count { klimatskiTipLV.isItemChecked(it) } < 1 ||
            (0 until zemljisniTipLV.count).count { zemljisniTipLV.isItemChecked(it) } < 1 ||
            profilOkusaLV.checkedItemPosition == ListView.INVALID_POSITION){
            dodajBiljkuBtn.setError("Nije selektovan item u listi/ama")
            Toast.makeText(this, "Nije selektovan item u listi/ama", Toast.LENGTH_SHORT).show()
            returnValue = false
        }

        return returnValue
    }

    private fun novaBiljka() : Biljka{
        val medicinskeKoristi: MutableList<MedicinskaKorist> = mutableListOf()
        for (i in 0 until medicinskaKoristLV.count) {
            if (medicinskaKoristLV.isItemChecked(i)) {
                MedicinskaKorist.fromString(medicinskaKoristLV.getItemAtPosition(i).toString())
                    ?.let { medicinskeKoristi.add(it) }
            }
        }
        val klimatskiTipovi: MutableList<KlimatskiTip> = mutableListOf()
        for (i in 0 until klimatskiTipLV.count) {
            if (klimatskiTipLV.isItemChecked(i)) {
                KlimatskiTip.fromString(klimatskiTipLV.getItemAtPosition(i).toString())
                    ?.let { klimatskiTipovi.add(it) }
            }
        }
        val zemljisniTipovi: MutableList<Zemljiste> = mutableListOf()
        for (i in 0 until zemljisniTipLV.count) {
            if (zemljisniTipLV.isItemChecked(i)) {
                Zemljiste.fromString(zemljisniTipLV.getItemAtPosition(i).toString())
                    ?.let { zemljisniTipovi.add(it) }
            }
        }

        return Biljka(
            naziv = nazivET.text.toString(),
            porodica = porodicaET.text.toString(),
            medicinskoUpozorenje = medicinskoUpozorenjeET.text.toString(),
            medicinskeKoristi = medicinskeKoristi,
            profilOkusa = ProfilOkusaBiljke.fromString(profilOkusaLV.getItemAtPosition(profilOkusaLV.checkedItemPosition).toString()),
            jela = jela.toList(),
            klimatskiTipovi = klimatskiTipovi,
            zemljisniTipovi = zemljisniTipovi
        )
    }
}