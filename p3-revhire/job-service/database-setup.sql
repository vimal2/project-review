-- Job Service Database Setup Script
-- RevHire Recruitment Platform

-- Create database
CREATE DATABASE IF NOT EXISTS revhire_job_db;

-- Use the database
USE revhire_job_db;

-- The job_postings table will be automatically created by JPA/Hibernate
-- with ddl-auto: update in application.yml

-- However, if you want to create it manually, use this schema:
/*
CREATE TABLE IF NOT EXISTS job_postings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employer_id BIGINT NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    skills TEXT,
    education VARCHAR(200),
    max_experience_years INT,
    location VARCHAR(255) NOT NULL,
    min_salary DECIMAL(10, 2),
    max_salary DECIMAL(10, 2),
    job_type VARCHAR(50),
    openings INT NOT NULL DEFAULT 1,
    application_deadline DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_employer_id (employer_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    INDEX idx_location (location),
    INDEX idx_job_type (job_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
*/

-- Sample data for testing (optional)
/*
INSERT INTO job_postings (
    employer_id, company_name, title, description, skills, education,
    max_experience_years, location, min_salary, max_salary, job_type,
    openings, application_deadline, status
) VALUES
(1, 'TechCorp Solutions', 'Senior Java Developer',
 'We are looking for an experienced Java developer to join our team. The ideal candidate will have strong experience with Spring Boot, microservices, and cloud technologies.',
 'Java, Spring Boot, Microservices, MySQL, Docker, Kubernetes',
 'Bachelor''s degree in Computer Science',
 5, 'New York, NY', 100000.00, 150000.00, 'Full-time', 2, '2024-12-31', 'OPEN'),

(1, 'TechCorp Solutions', 'Frontend Developer',
 'Join our frontend team to build amazing user experiences using React and modern web technologies.',
 'React, JavaScript, TypeScript, HTML, CSS, Redux',
 'Bachelor''s degree in Computer Science or related field',
 3, 'Remote', 80000.00, 120000.00, 'Full-time', 3, '2024-12-31', 'OPEN'),

(2, 'Innovative Systems', 'DevOps Engineer',
 'We need a DevOps engineer to help us build and maintain our cloud infrastructure.',
 'AWS, Docker, Kubernetes, Jenkins, Terraform, Python',
 'Bachelor''s degree in Computer Science',
 4, 'San Francisco, CA', 110000.00, 160000.00, 'Full-time', 1, '2024-11-30', 'OPEN'),

(2, 'Innovative Systems', 'Data Scientist',
 'Looking for a data scientist to analyze large datasets and build ML models.',
 'Python, Machine Learning, SQL, TensorFlow, PyTorch, Statistics',
 'Master''s degree in Data Science or related field',
 3, 'Boston, MA', 95000.00, 140000.00, 'Full-time', 2, '2024-12-15', 'OPEN');
*/
