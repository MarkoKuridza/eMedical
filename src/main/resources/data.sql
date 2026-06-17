-- ============================================================
--  eMedical — inicijalni podaci (seed)
--  Lozinke:
--    admin    → admin123
--    doktori  → doktor123
--    sestre   → sestra123
--
--  Svaki blok se izvrsava samo ako odgovarajuca tabela nema podataka.
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
--  TIMOVI
-- ============================================================
INSERT INTO team (id, name)
VALUES
    (1, 'Tim Alpha'),
    (2, 'Tim Beta'),
    (3, 'Tim Gamma')
ON DUPLICATE KEY UPDATE id = id;

-- ============================================================
--  KORISNICI
-- ============================================================
INSERT INTO user (id, username, password, role)
VALUES
    (1, 'admin', '$2b$10$GKcMHO1sDyIv3pFUdAK.Q.Qj3VZ.7olHnTJsbU6X7gywEC7V5dHGq', 'ADMIN'),
    (2, 'dr.kovac', '$2b$10$eWF2J8HBDctxsrmwKGUaZu7TrUebb.UGl.4GKep9stkGVh3Fw/Dne', 'DOCTOR'),
    (3, 'dr.petrovic', '$2b$10$eWF2J8HBDctxsrmwKGUaZu7TrUebb.UGl.4GKep9stkGVh3Fw/Dne', 'DOCTOR'),
    (4, 'dr.nikolic', '$2b$10$eWF2J8HBDctxsrmwKGUaZu7TrUebb.UGl.4GKep9stkGVh3Fw/Dne', 'DOCTOR'),
    (5, 's.markovic', '$2b$10$ZzFE66VkHqOGe5N6nYnWTOj6BTTP09lXcAFsehlp2.7boyYnFJduW', 'NURSE'),
    (6, 's.jovanovic', '$2b$10$ZzFE66VkHqOGe5N6nYnWTOj6BTTP09lXcAFsehlp2.7boyYnFJduW', 'NURSE'),
    (7, 's.djordjevic', '$2b$10$ZzFE66VkHqOGe5N6nYnWTOj6BTTP09lXcAFsehlp2.7boyYnFJduW', 'NURSE'),
    (8, 's.ilic', '$2b$10$ZzFE66VkHqOGe5N6nYnWTOj6BTTP09lXcAFsehlp2.7boyYnFJduW', 'NURSE'),
    (9, 's.stanisic', '$2b$10$ZzFE66VkHqOGe5N6nYnWTOj6BTTP09lXcAFsehlp2.7boyYnFJduW', 'NURSE'),
    (10, 's.lukic', '$2b$10$ZzFE66VkHqOGe5N6nYnWTOj6BTTP09lXcAFsehlp2.7boyYnFJduW', 'NURSE')
ON DUPLICATE KEY UPDATE id = id;

-- ============================================================
--  DOKTORI
-- ============================================================
INSERT INTO doctor (id, first_name, last_name, specialization, team_id)
VALUES
    (2, 'Marko', 'Kovač', 'Opšta medicina', 1),
    (3, 'Jovana', 'Petrović', 'Kardiologija', 2),
    (4, 'Stefan', 'Nikolić', 'Neurologija', 3)
ON DUPLICATE KEY UPDATE id = id;

-- ============================================================
--  SESTRE
-- ============================================================
INSERT INTO nurse (id, first_name, last_name, team_id)
VALUES
    (5, 'Ana', 'Marković', 1),
    (6, 'Milica', 'Jovanović', 1),
    (7, 'Tijana', 'Đorđević', 2),
    (8, 'Jelena', 'Ilić', 2),
    (9, 'Bojana', 'Stanišić', 3),
    (10, 'Dragana', 'Lukić', 3)
ON DUPLICATE KEY UPDATE id = id;

-- ============================================================
--  PACIJENTI
-- ============================================================
INSERT INTO patient (id, first_name, last_name, team_id)
VALUES
    (1, 'Petar', 'Lazić', 1),
    (2, 'Mila', 'Stojanović', 1),
    (3, 'Dragan', 'Vasić', 1),
    (4, 'Ivana', 'Čović', 2),
    (5, 'Nemanja', 'Bogdanović', 2),
    (6, 'Sanja', 'Ristić', 2),
    (7, 'Aleksandar', 'Simić', 3),
    (8, 'Katarina', 'Đukić', 3),
    (9, 'Vladimir', 'Popović', 3)
ON DUPLICATE KEY UPDATE id = id;

-- ============================================================
--  TERMINI
-- ============================================================
INSERT INTO appointment (id, date, details, status, patient_id, doctor_id, nurse_id, team_id, created_at, updated_at)
VALUES
    (1, '2025-04-10 09:00:00', 'Redovni pregled, kontrola pritiska.', 'COMPLETED', 1, 2, 5, 1, NOW(), NOW()),
    (2, '2025-05-15 10:30:00', 'Upućivanje na laboratorijske analize.', 'COMPLETED', 1, 2, 5, 1, NOW(), NOW()),
    (3, '2025-06-20 08:00:00', 'Kontrola nalaza, EKG.', 'COMPLETED', 1, 2, 6, 1, NOW(), NOW()),
    (4, '2025-09-05 11:00:00', 'Vakcinacija, sezonska prevencija.', 'COMPLETED', 1, 2, 5, 1, NOW(), NOW()),
    (5, '2026-08-01 09:30:00', 'Godišnji sistematski pregled.', 'SCHEDULED', 1, 2, 5, 1, NOW(), NOW()),
    (6, '2025-03-12 08:30:00', 'Tegobe sa sinusima, pregled ORL.', 'COMPLETED', 2, 2, 6, 1, NOW(), NOW()),
    (7, '2025-05-20 09:00:00', 'Kontrolni pregled sinusa.', 'COMPLETED', 2, 2, 6, 1, NOW(), NOW()),
    (8, '2025-07-18 10:00:00', 'Alergijski test, koža.', 'COMPLETED', 2, 2, 5, 1, NOW(), NOW()),
    (9, '2025-10-30 08:30:00', 'Pregled zbog hroničnog umora, krvna slika.', 'CANCELED', 2, 2, 6, 1, NOW(), NOW()),
    (10, '2026-07-20 10:00:00', 'Kontrola alergijskog testa.', 'SCHEDULED', 2, 2, 6, 1, NOW(), NOW()),
    (11, '2025-02-05 11:00:00', 'Bol u leđima, upućivanje na fizikalnu terapiju.', 'COMPLETED', 3, 2, 5, 1, NOW(), NOW()),
    (12, '2025-04-22 09:30:00', 'Kontrola terapije, bol u zglobovima.', 'COMPLETED', 3, 2, 5, 1, NOW(), NOW()),
    (13, '2025-06-10 08:00:00', 'Rendgen kičme.', 'COMPLETED', 3, 2, 6, 1, NOW(), NOW()),
    (14, '2025-08-25 10:30:00', 'Pregled zbog visokog pritiska.', 'COMPLETED', 3, 2, 5, 1, NOW(), NOW()),
    (15, '2026-07-10 09:00:00', 'Polugodišnja kontrola pritiska i srčane funkcije.', 'SCHEDULED', 3, 2, 5, 1, NOW(), NOW()),
    (16, '2025-03-08 09:00:00', 'Kardiološki pregled, EKG i holter.', 'COMPLETED', 4, 3, 7, 2, NOW(), NOW()),
    (17, '2025-05-14 10:00:00', 'Kontrola holtera, srčane aritmije.', 'COMPLETED', 4, 3, 7, 2, NOW(), NOW()),
    (18, '2025-07-22 08:30:00', 'Ehokardiografija.', 'COMPLETED', 4, 3, 8, 2, NOW(), NOW()),
    (19, '2025-10-10 11:00:00', 'Provjera terapije za aritmiju.', 'COMPLETED', 4, 3, 7, 2, NOW(), NOW()),
    (20, '2026-07-15 09:00:00', 'Godišnji kardiološki pregled.', 'SCHEDULED', 4, 3, 7, 2, NOW(), NOW()),
    (21, '2025-04-03 08:00:00', 'Stres test, ergometrija.', 'COMPLETED', 5, 3, 8, 2, NOW(), NOW()),
    (22, '2025-06-17 09:30:00', 'Kontrola nalaza ergometrije.', 'COMPLETED', 5, 3, 8, 2, NOW(), NOW()),
    (23, '2025-08-05 10:00:00', 'Ultrazvuk abdomena.', 'COMPLETED', 5, 3, 7, 2, NOW(), NOW()),
    (24, '2025-11-20 08:00:00', 'Pregled zbog bolova u grudima.', 'CANCELED', 5, 3, 8, 2, NOW(), NOW()),
    (25, '2026-07-25 10:30:00', 'Kontrolni kardiološki pregled.', 'SCHEDULED', 5, 3, 8, 2, NOW(), NOW()),
    (26, '2025-02-18 10:00:00', 'Preventivni kardiološki pregled.', 'COMPLETED', 6, 3, 7, 2, NOW(), NOW()),
    (27, '2025-04-30 08:30:00', 'Mjerenje krvnog pritiska, terapija.', 'COMPLETED', 6, 3, 7, 2, NOW(), NOW()),
    (28, '2025-07-11 09:00:00', 'Kontrola lipidnog statusa.', 'COMPLETED', 6, 3, 8, 2, NOW(), NOW()),
    (29, '2025-09-25 10:00:00', 'EKG i pregled kod kardiologije.', 'COMPLETED', 6, 3, 7, 2, NOW(), NOW()),
    (30, '2026-08-05 08:30:00', 'Godišnja kardiovaskularna procjena.', 'SCHEDULED', 6, 3, 8, 2, NOW(), NOW()),
    (31, '2025-03-20 09:00:00', 'Neurološki pregled, migrene.', 'COMPLETED', 7, 4, 9, 3, NOW(), NOW()),
    (32, '2025-05-08 10:30:00', 'MRI glave, kontrola nalaza.', 'COMPLETED', 7, 4, 9, 3, NOW(), NOW()),
    (33, '2025-07-14 08:00:00', 'Provjera terapije za migrene.', 'COMPLETED', 7, 4, 10, 3, NOW(), NOW()),
    (34, '2025-10-01 09:00:00', 'EEG, epilepsija isključena.', 'COMPLETED', 7, 4, 9, 3, NOW(), NOW()),
    (35, '2026-07-30 10:00:00', 'Polugodišnja neurološka kontrola.', 'SCHEDULED', 7, 4, 9, 3, NOW(), NOW()),
    (36, '2025-02-25 08:00:00', 'Neurološki pregled, utrnulost ekstremiteta.', 'COMPLETED', 8, 4, 10, 3, NOW(), NOW()),
    (37, '2025-04-16 10:00:00', 'EMG testiranje, periferna neuropatija.', 'COMPLETED', 8, 4, 10, 3, NOW(), NOW()),
    (38, '2025-06-28 09:30:00', 'Kontrola neuropatije, promjena terapije.', 'COMPLETED', 8, 4, 9, 3, NOW(), NOW()),
    (39, '2025-09-12 08:30:00', 'Pregled zbog poremećaja ravnoteže.', 'CANCELED', 8, 4, 10, 3, NOW(), NOW()),
    (40, '2026-08-10 09:00:00', 'Godišnji neurološki pregled.', 'SCHEDULED', 8, 4, 10, 3, NOW(), NOW()),
    (41, '2025-03-05 10:00:00', 'Neurološki pregled, tremor ruku.', 'COMPLETED', 9, 4, 9, 3, NOW(), NOW()),
    (42, '2025-05-21 09:00:00', 'DaTscan pregled, provjera Parkinsona.', 'COMPLETED', 9, 4, 9, 3, NOW(), NOW()),
    (43, '2025-07-30 08:30:00', 'Kontrola medikamentne terapije.', 'COMPLETED', 9, 4, 10, 3, NOW(), NOW()),
    (44, '2025-11-05 10:30:00', 'Pregled zbog pogoršanja tremora.', 'COMPLETED', 9, 4, 9, 3, NOW(), NOW()),
    (45, '2026-07-22 09:30:00', 'Polugodišnja kontrola neurološke terapije.', 'SCHEDULED', 9, 4, 9, 3, NOW(), NOW())
ON DUPLICATE KEY UPDATE id = id;

-- ============================================================
--  KARTONI
-- ============================================================
INSERT INTO medical_record (id, diagnosis, prescription, referral, patient_first_name, patient_last_name, created_at, updated_at, emergency, doctor_id, patient_id, appointment_id)
VALUES
    (1, 'Esencijalna hipertenzija', 'Ramipril 5mg/dan', NULL, 'Petar', 'Lazić', NOW(), NOW(), 0, 2, 1, 1),
    (2, 'Povišene masnoće u krvi', 'Atorvastatin 20mg/dan', 'Internista', 'Petar', 'Lazić', NOW(), NOW(), 0, 2, 1, 2),
    (3, 'Normalan EKG nalaz', 'Nastaviti dosadašnju terapiju', NULL, 'Petar', 'Lazić', NOW(), NOW(), 0, 2, 1, 3),
    (4, 'Vakcinisan protiv gripe', 'Bez terapije', NULL, 'Petar', 'Lazić', NOW(), NOW(), 0, 2, 1, 4),
    (5, 'Hronični sinusitis', 'Amoksicilin 500mg, ispiranje sinusa', 'ORL specijalist', 'Mila', 'Stojanović', NOW(), NOW(), 0, 2, 2, 6),
    (6, 'Poboljšanje sinusnog stanja', 'Lokalni kortikosteroid sprej', NULL, 'Mila', 'Stojanović', NOW(), NOW(), 0, 2, 2, 7),
    (7, 'Alergija na pelud i grinje', 'Antihistaminici, desenzibilizacija', 'Alergolog', 'Mila', 'Stojanović', NOW(), NOW(), 0, 2, 2, 8),
    (8, 'Lumbago, degenerativne promjene L4-L5', 'Ibuprofen 400mg, fizikalna terapija', 'Fizijatar', 'Dragan', 'Vasić', NOW(), NOW(), 0, 2, 3, 11),
    (9, 'Osteoartritis koljena', 'Hondroitin sulfat, kontrola za 3mj.', NULL, 'Dragan', 'Vasić', NOW(), NOW(), 0, 2, 3, 12),
    (10, 'Spondiloza lumbalne kičme', 'Fizikalna terapija, vježbe core mišića', NULL, 'Dragan', 'Vasić', NOW(), NOW(), 0, 2, 3, 13),
    (11, 'Hipertenzija II stadij', 'Amlodipin 10mg, dijeta s malo soli', NULL, 'Dragan', 'Vasić', NOW(), NOW(), 0, 2, 3, 14),
    (12, 'Paroksizmalna atrijska fibrilacija', 'Bisoprolol 5mg, antikoagulansi', 'Elektrofiziolog', 'Ivana', 'Čović', NOW(), NOW(), 0, 3, 4, 16),
    (13, 'Kontrolisana AF pod terapijom', 'Nastaviti terapiju, holter za 6mj.', NULL, 'Ivana', 'Čović', NOW(), NOW(), 0, 3, 4, 17),
    (14, 'Blaga disfunkcija lijeve klijetke', 'ACE inhibitori, diuretici', NULL, 'Ivana', 'Čović', NOW(), NOW(), 0, 3, 4, 18),
    (15, 'Stabilno stanje srčane funkcije', 'Nastaviti dosadašnju terapiju', NULL, 'Ivana', 'Čović', NOW(), NOW(), 0, 3, 4, 19),
    (16, 'Ergometrija uredna, dobra kondicija', 'Savjeti o životnom stilu', NULL, 'Nemanja', 'Bogdanović', NOW(), NOW(), 0, 3, 5, 21),
    (17, 'Normalni kardiološki nalaz', 'Bez terapije, redovne kontrole', NULL, 'Nemanja', 'Bogdanović', NOW(), NOW(), 0, 3, 5, 22),
    (18, 'Blaži holecistitis, žučni kamenac', 'Niskokalorijaska dijeta, kontrola', 'Gastroenterolog', 'Nemanja', 'Bogdanović', NOW(), NOW(), 0, 3, 5, 23),
    (19, 'Normalan kardiološki status', 'Bez terapije, redovna fizička aktivnost', NULL, 'Sanja', 'Ristić', NOW(), NOW(), 0, 3, 6, 26),
    (20, 'Granična hipertenzija', 'Promjena životnih navika, kontrola', NULL, 'Sanja', 'Ristić', NOW(), NOW(), 0, 3, 6, 27),
    (21, 'Blago povišen LDL', 'Dijeta, Atorvastatin 10mg ako ne padne', NULL, 'Sanja', 'Ristić', NOW(), NOW(), 0, 3, 6, 28),
    (22, 'Stabilna kardiovaskularna funkcija', 'Nastaviti životne navike', NULL, 'Sanja', 'Ristić', NOW(), NOW(), 0, 3, 6, 29),
    (23, 'Migrena bez aure', 'Sumatriptan 50mg, Propranolol', NULL, 'Aleksandar', 'Simić', NOW(), NOW(), 0, 4, 7, 31),
    (24, 'MRI nalaz uredan, funkcionalna migrena', 'Nastaviti profilaktičku terapiju', NULL, 'Aleksandar', 'Simić', NOW(), NOW(), 0, 4, 7, 32),
    (25, 'Smanjena učestalost migrena', 'Smanjiti Propranolol na 40mg', NULL, 'Aleksandar', 'Simić', NOW(), NOW(), 0, 4, 7, 33),
    (26, 'EEG uredan, bez epileptiformnih izboja', 'Bez antiepileptičke terapije', NULL, 'Aleksandar', 'Simić', NOW(), NOW(), 0, 4, 7, 34),
    (27, 'Periferna polineuropatija', 'Vitamin B kompleks, Alpha-lipoična kis.', 'Neurolog subspecijalist', 'Katarina', 'Đukić', NOW(), NOW(), 0, 4, 8, 36),
    (28, 'Dijabetička neuropatija, EMG potvrđen', 'Metformin optimizacija, Pregabalin 75mg', 'Endokrinolog', 'Katarina', 'Đukić', NOW(), NOW(), 0, 4, 8, 37),
    (29, 'Djelimično poboljšanje neuropatije', 'Nastaviti Pregabalin, fizikalna terapija', NULL, 'Katarina', 'Đukić', NOW(), NOW(), 0, 4, 8, 38),
    (30, 'Esencijalni tremor, isključen Parkinson', 'Propranolol 40mg/dan', NULL, 'Vladimir', 'Popović', NOW(), NOW(), 0, 4, 9, 41),
    (31, 'DaTscan negativan, Parkinson isključen', 'Nastaviti Propranolol', NULL, 'Vladimir', 'Popović', NOW(), NOW(), 0, 4, 9, 42),
    (32, 'Tremor stabilizovan pod terapijom', 'Reducirati dozu na 20mg kontrolno', NULL, 'Vladimir', 'Popović', NOW(), NOW(), 0, 4, 9, 43),
    (33, 'Blago pogoršanje tremora', 'Povećati Propranolol na 80mg', 'Pokretni neurolog', 'Vladimir', 'Popović', NOW(), NOW(), 0, 4, 9, 44)
ON DUPLICATE KEY UPDATE id = id;

SET FOREIGN_KEY_CHECKS = 1;