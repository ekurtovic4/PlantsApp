package ba.unsa.etf.rma.projekat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.projekat.dataetc.Biljka
import ba.unsa.etf.rma.projekat.R

class MedicalPlantListAdapter(
    private var biljke: List<Biljka>
) : RecyclerView.Adapter<MedicalPlantListAdapter.MedicalPlantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalPlantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.medical_item, parent, false)
        return MedicalPlantViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: MedicalPlantViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv
        holder.upozorenje.text = biljke[position].medicinskoUpozorenje
        holder.korist1.text = if(biljke[position].medicinskeKoristi.size > 0) biljke[position].medicinskeKoristi[0].opis else ""
        holder.korist2.text = if(biljke[position].medicinskeKoristi.size > 1) biljke[position].medicinskeKoristi[1].opis else ""
        holder.korist3.text = if(biljke[position].medicinskeKoristi.size > 2) biljke[position].medicinskeKoristi[2].opis else ""

        val context: Context = holder.slika.context
        val id: Int = context.resources.getIdentifier("picture1", "drawable", context.packageName)
        holder.slika.setImageResource(id)
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