-- Update all events to use only 3 categories: Events, Sports, Fitness
UPDATE events SET category = 'Events' WHERE category = 'Social';
UPDATE events SET category = 'Events' WHERE category = 'Gathering';
