# JavaUniLab8
This application allows users to store MusicBand objects in a database. Client provides a GUI where all objects are visualized. Their location on the panel and size depend on coordinates and albums count. Users can login and register. When an objects is deleted/edited/added the changes automatically appear on screens of all users.
The server stores objects in a database and works with them in Vector. Passwords are encrypted with sha-256 algorithm.

It is also possible to execute scripts and work with collection with the console using these commands:

help : info about commands
info : print info about the collection
show : print list of all objects in the collection to the console
add {element} : add new object to the collection
remove_by_id id : remove object by id
update id {element} : update element with a certain id
clear : clear the collection
execute_script file_name : execute script in the file
exit : stop the program
insert_at index {element} : add new element to a position
add_if_max {element} : add new object if it is the max object
add_if_min {element} : add new object if it is the min object
count_less_than_genre genre : print number of object with genre less than the one given
filter_starts_with_description description : print elements with description that starts with given string
print_descending : print objects in descending order

Demonstration: https://youtu.be/koLPT2Z3C34
