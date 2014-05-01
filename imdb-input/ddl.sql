drop table movie_collection;
CREATE TABLE movie_collection (
  id int identity,
  title varchar(128) DEFAULT NULL,
  year varchar(128) DEFAULT NULL,
  rated varchar(128) DEFAULT NULL,
  released varchar(128) DEFAULT NULL,
  genre varchar(128) DEFAULT NULL,
  director varchar(128) DEFAULT NULL,
  writer varchar(128) DEFAULT NULL,
  actors varchar(128) DEFAULT NULL,
  plot varchar(500) DEFAULT NULL,
  poster varchar(500) DEFAULT NULL,
  runtime varchar(128) DEFAULT NULL,
  rating varchar(128) DEFAULT NULL,
  votes varchar(128) DEFAULT NULL,
  imdb varchar(128) DEFAULT NULL,
  tstamp varchar(128) DEFAULT NULL
);