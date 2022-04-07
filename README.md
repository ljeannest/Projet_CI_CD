# Projet_CI_CD

### Ajouter un menu :

curl -H "Content-Type: application/json" --data-raw '{"name": "Nom du menu", "dishes": [{"name": "Plat 1"},{"name": "Plat 2"},{"name":"Plat 3"}]}' https://menu-server-jmz.herokuapp.com/menus

### Lister les menus :

java -jar target/Menucli-1.0-jar-with-dependencies.jar list-menus

### Supprimer un menu grâce à son ID :

java -jar target/Menucli-1.0-jar-with-dependencies.jar -i idMenu delete-menu