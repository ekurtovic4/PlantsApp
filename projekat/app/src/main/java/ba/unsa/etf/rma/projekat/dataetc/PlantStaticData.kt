package ba.unsa.etf.rma.projekat.dataetc

fun getBiljke(): List<Biljka>{
    return listOf(
        Biljka(
            naziv = "Bosiljak (Ocimum basilicum)",
            porodica = "Lamiaceae (usnate)",
            medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salata od paradajza", "Punjene tikvice"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
        ),
        Biljka(
            naziv = "Nana (Mentha spicata)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
            profilOkusa = ProfilOkusaBiljke.MENTA,
            jela = listOf("Jogurt sa voćem", "Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
        ),
        Biljka(
            naziv = "Kamilica (Matricaria chamomilla)",
            porodica = "Asteraceae (glavočike)",
            medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Čaj od kamilice"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
        ),
        Biljka(
            naziv = "Ružmarin (Rosmarinus officinalis)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Pečeno pile", "Grah","Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.SLJUNOVITO, Zemljiste.KRECNJACKO)
        ),
        Biljka(
            naziv = "Lavanda (Lavandula angustifolia)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Jogurt sa voćem"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
        ),
        Biljka(
            naziv = "Limun (Citrus limon)",
            porodica = "Rutaceae (rutvice)",
            medicinskoUpozorenje = "Nije pogodan za osobe s gastritisom i drugim bolestima probavnog trakta.",
            medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.CITRUSNI,
            jela = listOf("Riba", "Slatka pita"),
            klimatskiTipovi = listOf(KlimatskiTip.SUBTROPSKA, KlimatskiTip.SREDOZEMNA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA)
        ),
        Biljka(
            naziv = "Kopar (Anethum graveolens)",
            porodica = "Apiaceae (štitarke)",
            medicinskoUpozorenje = "Nije preporučljiv za trudnice.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTIVBOLOVA, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.CITRUSNI,
            jela = listOf("Kisela salata"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA)
        ),
        Biljka(
            naziv = "Ljuta paprika (Capsicum anuum)",
            porodica = "Solanaceae (noćurke)",
            medicinskoUpozorenje = "Prevelika konzumacija može dovesti do probavnih smetnji.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.LJUTO,
            jela = listOf("Čili", "Pileća krilca", "Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.TROPSKA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA)
        ),
        Biljka(
            naziv = "Kičica (Centaurium erythraea)",
            porodica = "Gentianaceae (gencijane)",
            medicinskoUpozorenje = "Nije preporučljiva za trudnice i dojilje.",
            medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("Salata", "Supa"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.CRNICA)
        ),
        Biljka(
            naziv = "Majčina dušica (Thymus serpyllum)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Neke osobe mogu iskusiti sniženje krvnog pritiska nakon konzumacije čaja od majčine dušice.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Rižoto", "Pečeni krompir"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.SLJUNOVITO)
        ),
        Biljka(
            naziv = "Kadulja (Salvia officinalis)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Kaduljino ulje je vrlo toksično te se treba primjenjivati s oprezom. Nije preporučljiva za osobe koje boluju od epilepsije ili hipertenzije.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
            profilOkusa = ProfilOkusaBiljke.MENTA,
            jela = listOf("Piletina", "Pasta"),
            klimatskiTipovi = listOf(KlimatskiTip.SUHA, KlimatskiTip.SREDOZEMNA),
            zemljisniTipovi = listOf(Zemljiste.CRNICA)
        ),
        Biljka(
            naziv = "Stevija (Stevia rebaudiana)",
            porodica = "Asteraceae (glavočike)",
            medicinskoUpozorenje = "Može uzrokovati probavne smetnje.",
            medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa = ProfilOkusaBiljke.SLATKI,
            jela = listOf("Zobene pahuljice", "Kolači"),
            klimatskiTipovi = listOf(KlimatskiTip.TROPSKA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA)
        )
    )
}


