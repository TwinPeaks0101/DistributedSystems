Έκανα την κλάση ReadJason.readFile() να επιστρέφει Accommodation
και να παίρνει ορίσματα ένα path και ένα index.
Έχω σχολιάσει όλα τα print και η εκτύπωση γίνεται μέσω AccommodationList
Η main του Client τυπώνει τώρα με τη μορφή που έχουν τα Accommodation objects.

Η κλάση AccommodationList έχει 2 κατασκευαστές, ένας default και ένας που
παίρνει όρισμα path και διαβάζει το json αρχείο για να γεμίσει το accomodationList(παίρνω το size του από τη συνάρτηση
getJsonArray(Path path) της κλάσης ReadJason αλλά διαβάζω 2 φορές το json αρχείο! ???).

Στην κλάση AccommodationList έχω βάλει την addAccommodation η οποία καλεί την createAccomodation μέσα στην
Accommodation.

Έχω βάλει και κάποια σχόλια στην AvailabilityOfAccommodations.

