-- Insert sample events with complete data
INSERT INTO events (title, category, date, location, icon, attendees, max_attendees, city, zipcode, area, description, created_at) VALUES
('Weekend Hiking Adventure', 'Sports', 'Saturday, 9:00 AM', 'Mountain Trail Park', '🥾', 12, 15, 'Portland', '97201', 'Downtown', NULL, CURRENT_TIMESTAMP),
('Morning Yoga in the Park', 'Fitness', 'Sunday, 7:00 AM', 'Central Park', '🧘', 8, 20, 'Portland', '97210', 'Central', NULL, CURRENT_TIMESTAMP),
('Coffee & Conversations', 'Events', 'Friday, 5:00 PM', 'Downtown Cafe', '☕', 6, 10, 'Portland', '97215', 'Southeast', NULL, CURRENT_TIMESTAMP),
('Basketball Pickup Game', 'Sports', 'Wednesday, 6:00 PM', 'Community Center', '🏀', 10, 12, 'Portland', '97201', 'Downtown', NULL, CURRENT_TIMESTAMP),
('Running Club Meetup', 'Fitness', 'Tuesday, 6:30 AM', 'Riverside Trail', '🏃', 15, 25, 'Portland', '97202', 'Southwest', NULL, CURRENT_TIMESTAMP),
('Book Club Discussion', 'Events', 'Thursday, 7:00 PM', 'Local Library', '📚', 7, 12, 'Portland', '97210', 'Central', NULL, CURRENT_TIMESTAMP),
('Beach Volleyball Tournament', 'Sports', 'Saturday, 2:00 PM', 'Ocean Beach', '🏐', 14, 18, 'Beaverton', '97006', 'West Side', 'Fun beach volleyball game for all skill levels', CURRENT_TIMESTAMP),
('Sunset Cycling Group', 'Fitness', 'Friday, 5:30 PM', 'Burnside Bridge', '🚴', 9, 15, 'Southeast', '97214', 'Southeast', 'Evening bike ride through scenic routes', CURRENT_TIMESTAMP),
('Art Workshop - Painting', 'Events', 'Saturday, 10:00 AM', 'Artist Studio', '🎨', 5, 12, 'Portland', '97214', 'Southeast', 'Learn landscape painting techniques', CURRENT_TIMESTAMP),
('Brunch Social Meetup', 'Events', 'Sunday, 10:30 AM', 'Cafe Pearl', '🥐', 11, 16, 'Lake Oswego', '97034', 'South', 'Casual brunch gathering with new friends', CURRENT_TIMESTAMP);
