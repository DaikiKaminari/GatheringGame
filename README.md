# Projet Middleware : The Gathering Game

## But du jeu

Dans ce jeu, 4 joueurs s'affrontent en deux équipes distinctes. L'équipe rouge et l'équipe bleue doivent récupérer des
ressources éparpillées et les amener dans une usine centrale. Il faut pour cela suivre la demande correspondant à
l'équipe. Lorsqu'une demande de trois ressources est satisfaite par une équipe, elle gagne un point. L'équipe qui
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

- un *main* thread qui permet de générer des matchs grâce à la classe Matchmaking
- Un *thread* compte à rebours qui permet de gérer un compte à rebours commun à tous les joueurs dans le match
- Un *thread* qui génère des ressources à des positions aléatoires dans le jeu.

Dans le thread principal du serveur, une instance de la classe MatchmakingImpl est utilisé pour réaliser un binding RMI
avec les clients. Cette classe permet de récupérer une partie puis de la rejoindre. Si le joueur n'arrive pas à rejoindre
car il n'y a plus de place accessible, alors il réessaie plusieurs fois jusqu'à ce qu'il arrive à rejoindre une partie.

La création de partie est gérée dynamiquement par le serveur, il se charge d'instancier à chaque partie de nouveaux
threads *compte à rebours* et *générateur de ressources*. Cela permet au serveur de gérer plusieurs parties en parallèles.

Le joueur une fois qu'il a reçu une instance de partie et l'instance de son joueur généré par l'instance de Jeu, il peut
afficher son jeu en récupérant les données via des getters et interagir avec le jeu en faisant des appels sur son
instance de joueur.

Le *Client*, par le biais du *Matchmaking*, reçoit une instance de *Jeu* lui permettant d'afficher l'état actuel du jeu.
Via cette instance de *Jeu* il fait un *join*, et reçoit une instance de *Joueur* lui permettant d'interagir avec le
jeu.

Pour gérer les entrées du clavier du joueur, nous utilisons un contrôleur joueur qui se trouve côté client. Il
fonctionne grâce à deux maps. Un map qui permet de binder une entrée clavier à une action, et une autre map qui associe
une action à un état vrai ou faux lorsqu'il est actif ou non. À partir des états d'action, des méthodes peuvent être
appelées en RMI, ce qui défini les interactions entre le joueur et le jeu.

## Synchronisation

Les ressources sont stockées par le jeu qui met à disposition des méthodes permettant de modifier cette liste en
écriture (tout en prenant un lock à chaque modification) grâce aux méthodes *ajouterResource(...)* et
*retirerResource(...)*).

Ces ressources sont en concurrence d'accès en lecture par :
- les joueurs qui peuvent prendre une ressource à tout moment
- le *générateur de ressources* qui dès qu'il est réveillé va vérifier s'il y a assez de ressources de disponible
- l'affichage des ressources

Elles sont aussi en concurrence d'accès en écriture par :
- les joueurs qui peuvent déposer la ressource qui est dans leur inventaire à tout moment
- le *générateur de ressources* qui génère la ressource la moins présente dans le jeu à une place aléatoire

Le générateur de ressources fonctionne de la manière suivante : il est tout d'abord créé puis démarré par le jeu, à ce
moment il va commencer à générer des ressources jusqu'à ce qu'il y en ait une certaine quantité maximale. Pour se faire
il prend un lock (*synchronized block*) sur la liste des ressources, il vérifie s'il y en a assez, si ce n'est pas le
cas alors il en génère une nouvelle de manière pseudo random (il tentera toujours d'en générer celle qu'il manque le
plus sur le jeu), si ce n'est pas le cas alors il s'endort (*wait*). Il n'est alors réveillé que par le jeu, qui quand
une ressource est retirée, va réveiller le générateur.