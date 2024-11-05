- Lage NPC's hvis mulig

- Må klare å velge en random vann-tile og en høyt gress tile der det er en pokémon som man skal slåss mot.
  - Når man går over denne tilen skal man havne i en battle

- Når man går over vannet skal det poppe opp en egen ny pokemon,
    Hvis man taper mot denne er straffen at man må stille til ett bad med ESC Badekameratene.

En idé for programmet. Hva skal programmet ditt være og hvilken funksjonalitet skal det ha?
    En GUI der jeg kan bevege spriten min, i dette tilfellet Ashe fra pokemon. Ashe skal bevege seg på et map 
    som skal etterligne nygårdsparke. 
    I vann tilesene skal det være forhåndsplassert en karakter som man 
    kommer i battle med, her skal jeg prøve å bruke rand.int for å plassere dette randomly.
    NB!!! Husk å sentrer skjermen rundt player spriten min
    

Valg av kodebase (Tetris, Othello/BlobWars/Connect4/TicTacToe, fra scratch)
    Fra scratch.    

En plan/oversikt av viktigste klassene. Skriv opp hva de viktigste klassene for programmet ditt vil være og si hvilke variabler/metoder de burde inneholde.
    GamePanel - 
    CharacterController - Har som ansvar å ta hånd om bevegelsen av Player Spriten min
    Characters - Superklassen til alle mulige "entities" vi lager. Lager en type enemy senere i spillet som arver fra denne klassen.
    Player - Player specific class
    Tile - 
    TileManager - Henter tiles fra MapTiles mappen. Tegner tiles med drawTile metoden, og loader mappet vi har lagd.