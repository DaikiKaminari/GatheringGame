# Projet Middleware : The Gathering Game

## But du jeu

Dans ce jeu, 4 joueurs s'affrontent en deux équipes distinctes. L'équipe rouge et l'équipe bleu doivent récupérer des
ressources éparpillées et les amener dans une usine centrale. Il faut pour cela suivre la demande correspondant à
l'équipe. Lorsqu'une demande de trois ressources est satsifaite par une équipe, elle gagne un point. L'équipe qui
réussit à satisfaire le plus de demande complète avant la fin du temps impartie gagne.

## Comment jouer ?

Pour se déplacer, il faut utiliser les touches directionnelles HAUT, BAS, GAUCHE et DROITE. Pour ramasser une ressource,
il faut utiliser la barre espace. Une ressource ramassée peut être posé sur le sol en utilisant à nouveau la barre
espace.

Pour envoyer une ressource à l'usine, il faut se situer sur l'usine, et appuyer sur la barre espace avec la bonne
ressource. S'il ne s'agit pas de la bonne ressource, la demande sera refusée.

## Implémentation

Le projet est implémenté à l'aide de Java RMI.

Côté serveur, il y a 3 threads :

- un main thread qui permet de générer des match grâce à la classe Matchmaking
- Un thread compte à rebours qui permet de gérer un compte à rebour commun à tous les joueurs dans le match
- Un thread qui génère des ressources à des positions aléatoires dans le jeu.

Dans le thread principal du serveur, une instance de la classe MatchmakingImpl est utilisé pour réaliser un binding RMI
avec les clients. Cette classe permet de récupérer une partie puis de la rejoindre. Si lme joueur n'arrive pas à joindre
car plus de place sont accessible, alors il réessaie plusieurs fois jusqu'à ce qu'il arrive à rejoindre une partie.

La création de partie est gérée dynamiquement par le serveur, il se charge d'instancier à chaque partie de nouveaux
threads compte à rebours et générateur de ressources. Cela permet au serveur de gérer plusieurs parties en parallèles.

Le joueur une fois qu'il a reçu une instance de partie et l'instance de son joueur généré par l'instance de Jeu, il peut
afficher son jeu en récupérant les données via des getters, et intéragir avec le jeu en faisant des appels sur son
instance de joueur.

Pour gérer les entrées du clavier du joueur, nous utilisons un contrôleur joueur qui se trouve côté client. Il
fonctionne grâce à deux maps. Un map qui permet de binder une entrée clavier à une action, et une autre map qui associe
une action à un état vrai ou faux lorsqu'il est actif ou non. À partir des états d'action, des méthodes peuvent être
appelées en RMI, ce qui défini les interactions entre le joueur et le jeu.

## Synchronisation

