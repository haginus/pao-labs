# Changelog
### Checkpoint 3
 - Șters servicii citire/ștergere în CSV
 - Adăugat baza de date
 - Adăugat clasă serviciu pentru configurarea inițială bază de date
 - Adăugat servicii 

### Checkpoint 2
- Organizat în pachete
- Adăugat servicii de scriere/citire în CSV

# Detalii despre baza de date
 - Programul se conectează la baza de date MySQL ```register``` folosind user-ul ```register``` și parola ```register``` ce rulează pe ```localhost:3306```.
 - Rulați:
 
```mysql
CREATE DATABASE register;
CREATE USER 'register'@'localhost' IDENTIFIED BY 'register';
GRANT ALL PRIVILEGES ON * . * TO 'register'@'localhost';
```

# Instrucțiuni sintaxă comandă
 - ```+ <barcode> <quantity>```: Adaugă produsul corespunzător codului de bare în cantitatea specificată la comanda curentă;
 - ```- <barcode>```: Șterge produsul corespunzător codului de bare (în toată cantitatea) din comanda curentă;
 - ```?```: Afișează totalul comenzii;
 - - ```P Cash <handled_cash>```: Plătirea comenzii în numerar, cu suma specificată;
   - ```P Card <card_number> <card_type>```: Plătirea comenzii în numerar, cu un anume card (cele două câmpuri sunt date de umplutură);
 - ```D```: Anulează comanda curentă.
