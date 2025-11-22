-- Add location fields for filtering
ALTER TABLE events ADD COLUMN city VARCHAR(100);
ALTER TABLE events ADD COLUMN zipcode VARCHAR(20);
ALTER TABLE events ADD COLUMN area VARCHAR(100);

-- Update existing events with sample location data
UPDATE events SET city = 'Portland', zipcode = '97201', area = 'Downtown' WHERE id = 1;
UPDATE events SET city = 'Portland', zipcode = '97210', area = 'Central' WHERE id = 2;
UPDATE events SET city = 'Portland', zipcode = '97215', area = 'Southeast' WHERE id = 3;
UPDATE events SET city = 'Portland', zipcode = '97201', area = 'Downtown' WHERE id = 4;
UPDATE events SET city = 'Portland', zipcode = '97202', area = 'Southwest' WHERE id = 5;
UPDATE events SET city = 'Portland', zipcode = '97210', area = 'Central' WHERE id = 6;

-- Add more sample events with different locations
INSERT INTO events (title, category, date, location, icon, attendees, max_attendees, city, zipcode, area, description) VALUES
('Beach Volleyball Tournament', 'Sports', 'Saturday, 2:00 PM', 'Ocean Beach', '🏐', 14, 18, 'Beaverton', '97006', 'West Side', 'Fun beach volleyball game for all skill levels'),
('Sunset Cycling Group', 'Fitness', 'Friday, 5:30 PM', 'Burnside Bridge', '🚴', 9, 15, 'Southeast', '97214', 'Southeast', 'Evening bike ride through scenic routes'),
('Art Workshop - Painting', 'Gathering', 'Saturday, 10:00 AM', 'Artist Studio', '🎨', 5, 12, 'Portland', '97214', 'Southeast', 'Learn landscape painting techniques'),
('Brunch Social Meetup', 'Social', 'Sunday, 10:30 AM', 'Cafe Pearl', '🥐', 11, 16, 'Lake Oswego', '97034', 'South', 'Casual brunch gathering with new friends');
