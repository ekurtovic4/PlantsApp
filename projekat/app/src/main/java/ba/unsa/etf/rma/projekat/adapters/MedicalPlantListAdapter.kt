package ba.unsa.etf.rma.projekat.adapters

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.projekat.dataetc.Biljka
import ba.unsa.etf.rma.projekat.R
import ba.unsa.etf.rma.projekat.dataetc.BiljkaDatabase
import ba.unsa.etf.rma.projekat.web.TrefleDAO
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MedicalPlantListAdapter(
    private var biljke: List<Biljka>,
    private val onItemClicked: (biljka: Biljka) -> Unit
) : RecyclerView.Adapter<MedicalPlantListAdapter.MedicalPlantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalPlantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.medical_item, parent, false)
        return MedicalPlantViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: MedicalPlantViewHolder, position: Int) {
        val biljka = biljke[position]
        holder.naziv.text = biljka.naziv
        holder.upozorenje.text = biljka.medicinskoUpozorenje
        holder.korist1.text = if(biljka.medicinskeKoristi.size > 0) biljka.medicinskeKoristi[0].opis else ""
        holder.korist2.text = if(biljka.medicinskeKoristi.size > 1) biljka.medicinskeKoristi[1].opis else ""
        holder.korist3.text = if(biljka.medicinskeKoristi.size > 2) biljka.medicinskeKoristi[2].opis else ""

        val context: Context = holder.slika.context
        val trefle = TrefleDAO()
        trefle.setContext(context)
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val activity = (holder.itemView.context as? Activity)
            if (activity != null && !activity.isFinishing){
                try{
                    val biljkaDao = BiljkaDatabase.getInstance(context).biljkaDao()
                    val dbBitmap = biljka.id?.let { biljkaDao.getBitmapByIdBiljke(it) }
                    val imgBitmap: Bitmap?

                    if(dbBitmap?.isEmpty() == true){
                        imgBitmap = trefle.getImage(biljka)
                        val resizedImgBitmap: Bitmap =
                            Bitmap.createBitmap(imgBitmap, 0, 0, 400, 400)
                        biljka.id.let { biljkaDao.addImage(it, resizedImgBitmap) }
                    }
                    else{
                        imgBitmap = dbBitmap?.get(0)?.bitmap
                    }

                    Glide.with(context)
                        .load(imgBitmap)
                        .centerCrop()
                        .placeholder(R.drawable.picture1)
                        .into(holder.slika)
                }
                catch(e: Exception){
                    Glide.with(context)
                        .load(R.drawable.picture1)
                        .centerCrop()
                        .placeholder(R.drawable.picture1)
                        .into(holder.slika)
                }
            }
        }

        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]) }
    }

    fun updatePlants(biljke: List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }

    inner class MedicalPlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val slika: ImageView = itemView.findViewById(R.id.slikaItem)
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val upozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val korist1: TextView = itemView.findViewById(R.id.korist1Item)
        val korist2: TextView = itemView.findViewById(R.id.korist2Item)
        val korist3: TextView = itemView.findViewById(R.id.korist3Item)
    }
}