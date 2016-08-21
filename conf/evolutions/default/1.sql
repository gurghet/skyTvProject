# --- !Ups

create table "product" (
    "uuid" VARCHAR(254) NOT NULL PRIMARY KEY,
    "tiny_description" VARCHAR(254) NOT NULL,
    "availability_acc" VARCHAR(254) NOT NULL,
    "category" VARCHAR(254) NOT NULL);

comment on column "product"."tiny_description" is 'This would be equivalent to the name of the product, called description to be uniform with possible short_description, long_description...';
comment on column "product"."availability_acc" is 'This could be an accumulator/hash of zones (for example a bloom filter). You would check if the locationID is inside the accumulator. For this example is just the concatenation of locationIDs';
comment on table "product" is 'In a real implementation a table like this would be possible only in a serving layer of a lambda architecture, otherwise you are bound to have a lot of joins with other table and a lot of meaningless ids in each record';

insert into "product" values
    ('Arsenal TV', 'Arsenal TV', 'LONDON', 'Sports'),
    ('Chelsea TV', 'Chelsea TV', 'LONDON', 'Sports'),
    ('Liverpool TV', 'Liverpool TV', 'LIVERPOOL', 'Sports'),
    ('Sky News', 'Sky News', 'WORLD-LONDON-LIVERPOOL', 'News'),
    ('Sky Sport News', 'Sky Sport News', 'WORLD-LONDON-LIVERPOOL', 'News');

# --- !Downs

drop table "product";