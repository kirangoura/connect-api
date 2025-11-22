CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    date VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    icon VARCHAR(10),
    attendees INTEGER DEFAULT 0,
    max_attendees INTEGER DEFAULT 20,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample events
INSERT INTO events (title, category, date, location, icon, attendees, max_attendees) VALUES
('Weekend Hiking Adventure', 'Sports', 'Saturday, 9:00 AM', 'Mountain Trail Park', '🥾', 12, 15),
('Morning Yoga in the Park', 'Fitness', 'Sunday, 7:00 AM', 'Central Park', '🧘', 8, 20),
('Coffee & Conversations', 'Social', 'Friday, 5:00 PM', 'Downtown Cafe', '☕', 6, 10),
('Basketball Pickup Game', 'Sports', 'Wednesday, 6:00 PM', 'Community Center', '🏀', 10, 12),
('Running Club Meetup', 'Fitness', 'Tuesday, 6:30 AM', 'Riverside Trail', '🏃', 15, 25),
('Book Club Discussion', 'Gathering', 'Thursday, 7:00 PM', 'Local Library', '📚', 7, 12);
