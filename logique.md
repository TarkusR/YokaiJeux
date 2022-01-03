Logique du jeu

potentielle structure :
abstract class Card
attribut de classe : verso
en héritent : Oni, Kitsune...
attribut de classe : recto

class Hint
attribut : booléen prepared (face cachée -> face visible)

organisation d'une partie : 
nombre de joueurs, niveau

initialisation d'une partie : 
instantation de 4 cartes de chaque classe fille
mélange de celles-ci (dans une liste ou autre)
disposition en 4x4 sur grille
(manque : niveaux et variantes)

déroulement : 
chaque joueur réalise un tour, on boucle

tour : 
prompt le joueur pour déclare les yokai apaisés OU réaliser les actions suivantes dans l'ordre : 
- regarder 2 cartes
- déplacer une carte
- préparer (retourner) un indice OU utiliser un indice
les cartes yokai munies d'une carte indice ne peuvent plus être regardées ou déplacées

lorsque les cartes indice sont épuisées (placées sur des cartes yokai) OU lorsqu'un joueur déclare les yokai apaisés (ceci ne représente pas la condition en pratique), la partie s'arrête

on vérifie si les yokais sont apaisés (les 4 familles sont regroupées (tous leurs membres sont adjacents à au moins un autre membre de leur famille)
si non : perdu
si oui : on comptabilise les points des joueurs (qui sont ensemble) selon les cartes indice disposées, si elles sont correctement placées ou non
ainsi que selon les cartes indices non utilisées / non préparées.
type de victoire selon l'encadrement des points (varie selon nombre de joueurs) (jingle visuel / audio ?)

cartes indice :
bon nombre d'indices des bons types (selon le nombre de joueur) ; mélangées, pile face cachée
leur "préparation" consiste en retourner une face cachée (sommet de la pile ?), ce qui la rend disponible pour utilisation
leur "utilisation" consiste en la placer sur une carte yokai.
