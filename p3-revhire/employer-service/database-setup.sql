-- RevHire Employer Service - Database Setup Script
-- This script creates the database and provides sample data for testing

-- Create database
CREATE DATABASE IF NOT EXISTS revhire_employer_db;

-- Use the database
USE revhire_employer_db;

-- The employer_profiles table will be auto-created by Hibernate
-- This script is for reference and manual setup if needed

-- Manual table creation (if not using Hibernate auto-generation)
CREATE TABLE IF NOT EXISTS employer_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    company_name VARCHAR(200) NOT NULL,
    industry VARCHAR(100),
    company_size VARCHAR(50),
    company_description VARCHAR(2000),
    website VARCHAR(255),
    company_location VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample data for testing (optional)
-- Note: These user IDs should correspond to actual users in the auth-service database

INSERT INTO employer_profiles (user_id, company_name, industry, company_size, company_description, website, company_location)
VALUES
(101, 'Tech Innovations Inc', 'Information Technology', '100-500', 'Leading provider of cloud-based solutions and enterprise software', 'https://techinnovations.com', 'San Francisco, CA'),
(102, 'Global Finance Corp', 'Financial Services', '500-1000', 'Premier financial services company specializing in investment banking', 'https://globalfinance.com', 'New York, NY'),
(103, 'Healthcare Plus', 'Healthcare', '1000-5000', 'Comprehensive healthcare services provider with nationwide presence', 'https://healthcareplus.com', 'Boston, MA'),
(104, 'EduTech Solutions', 'Education Technology', '50-100', 'Innovative educational technology platform for K-12 and higher education', 'https://edutechsolutions.com', 'Austin, TX'),
(105, 'Green Energy Systems', 'Renewable Energy', '100-500', 'Sustainable energy solutions and solar panel manufacturing', 'https://greenenergysystems.com', 'Seattle, WA');

-- Verify data
SELECT * FROM employer_profiles;

-- Useful queries for testing

-- Find employer profile by user ID
-- SELECT * FROM employer_profiles WHERE user_id = 101;

-- Count total employer profiles
-- SELECT COUNT(*) as total_profiles FROM employer_profiles;

-- Get employers by industry
-- SELECT * FROM employer_profiles WHERE industry = 'Information Technology';

-- Get employers by company size
-- SELECT * FROM employer_profiles WHERE company_size = '100-500';

-- Get recently updated profiles
-- SELECT * FROM employer_profiles ORDER BY updated_at DESC LIMIT 10;

-- Clean up (use with caution)
-- DELETE FROM employer_profiles WHERE user_id = 101;
-- TRUNCATE TABLE employer_profiles;
-- DROP TABLE employer_profiles;
-- DROP DATABASE revhire_employer_db;
