drop database if exists music_store_recommendations;
create database music_store_recommendations;
use music_store_recommendations;

create table label_recommendation (
    label_recommendation_id int primary key auto_increment,
    label_id int not null,
    user_id int not null,
    liked bool not null
);

create table artist_recommendation (
    artist_recommendation_id int primary key auto_increment,
    artist_id int not null,
    user_id int not null,
    liked bool not null
);

create table album_recommendation (
    album_recommendation_id int primary key auto_increment,
    album_id int not null,
    user_id int not null,
    liked bool not null
);

create table track_recommendation (
    track_recommendation_id int primary key auto_increment,
    track_id int not null,
    user_id int not null,
    liked bool not null
);
insert into track_recommendation(track_id, user_id, liked)
	values (1, 1, true);
    
insert into artist_recommendation(artist_id, user_id, liked)
	values (1, 1, false);
    
insert into label_recommendation(label_id, user_id, liked)
	values (1, 1, true);
    
insert into album_recommendation(album_id, user_id, liked)
	values (1, 1, false);