# Enterprise Identity & Access Management
## Overview
Enterprise IAM is a secure Identity and Access Management system built using Spring Boot and PostgreSQL. 
The application provides authentication, authorization, role-based access control (RBAC), user management, 
permission management, audit logging, session management, and JWT-based security.
## Features
### Authentication
- User Registration
- User Login
- JWT Access Token
- Refresh Token
- Logout
- Forgot Password
- OTP Verification
- Reset Password
### User Management
- Create User
- View Users
- Update User
- Delete User
### Role Management
- Create Role
- View Roles
- Update Role
- Delete Role
### Permission Management
- Create Permission
- View Permissions
- Update Permission
- Delete Permission
### Role Assignment
- Assign Roles To Users
### Security
- Spring Security
- JWT Authentication
- JWT Authorization
- Role-Based Access Control (RBAC)
- Password Encryption using BCrypt
### Logging
- Login History Tracking
- Audit Logging
### Session Management
- Refresh Token Support
- Token Revocation
## Security Implementation
### JWT Authentication
- Access Token Generation
- Refresh Token Generation
- Token Validation
- Stateless Authentication
## Database schema
### users
CREATE TABLE users (
id BIGSERIAL PRIMARY KEY,
username VARCHAR(100) NOT NULL UNIQUE,
email VARCHAR(150) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
phone_number VARCHAR(20),
is_active BOOLEAN NOT NULL DEFAULT TRUE,
last_login_time TIMESTAMP,
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP NOT NULL
);
### roles
CREATE TABLE roles (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE,
description VARCHAR(500),
is_active BOOLEAN NOT NULL DEFAULT TRUE,
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP NOT NULL
);
### permissions 
CREATE TABLE permissions (
id BIGSERIAL PRIMARY KEY,
code VARCHAR(100) NOT NULL UNIQUE,
name VARCHAR(100) NOT NULL,
description VARCHAR(500),
is_active BOOLEAN NOT NULL DEFAULT TRUE,
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP NOT NULL
);
### user_roles
CREATE TABLE user_roles (
user_id BIGINT NOT NULL,
role_id BIGINT NOT NULL,
CONSTRAINT pk_user_roles
PRIMARY KEY (user_id, role_id),
CONSTRAINT fk_user_roles_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE,
CONSTRAINT fk_user_roles_role
FOREIGN KEY (role_id)
REFERENCES roles(id)
ON DELETE CASCADE
);
### role_permissions
CREATE TABLE role_permissions (
role_id BIGINT NOT NULL,
permission_id BIGINT NOT NULL,
CONSTRAINT pk_role_permissions
PRIMARY KEY (role_id, permission_id),
CONSTRAINT fk_role_permissions_role
FOREIGN KEY (role_id)
REFERENCES roles(id)
ON DELETE CASCADE,
CONSTRAINT fk_role_permissions_permission
FOREIGN KEY (permission_id)
REFERENCES permissions(id)
ON DELETE CASCADE
);
### refresh_tokens
CREATE TABLE refresh_tokens (
id BIGSERIAL PRIMARY KEY,
token TEXT NOT NULL UNIQUE,
expiry_date TIMESTAMP NOT NULL,
is_revoked BOOLEAN NOT NULL DEFAULT FALSE,
created_at TIMESTAMP NOT NULL,
user_id BIGINT NOT NULL,
CONSTRAINT fk_refresh_tokens_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE
);
### otp
CREATE TABLE otp (
id BIGSERIAL PRIMARY KEY,
code VARCHAR(10) NOT NULL,
expiry_date TIMESTAMP NOT NULL,
is_used BOOLEAN NOT NULL DEFAULT FALSE,
used_at TIMESTAMP,
created_at TIMESTAMP NOT NULL,
user_id BIGINT NOT NULL,
CONSTRAINT fk_otp_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE
);
### login_history
CREATE TABLE login_history (
id BIGSERIAL PRIMARY KEY,
login_timestamp TIMESTAMP NOT NULL,
logout_timestamp TIMESTAMP,
ip_address VARCHAR(50),
user_agent TEXT,
login_status VARCHAR(20) NOT NULL,
user_id BIGINT NOT NULL,
CONSTRAINT fk_login_history_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE
);
### audit_logs
CREATE TABLE audit_logs (
id BIGSERIAL PRIMARY KEY,
action_type VARCHAR(100) NOT NULL,
entity_type VARCHAR(100) NOT NULL,
entity_id BIGINT,
old_value TEXT,
new_value TEXT,
ip_address VARCHAR(50),
user_agent TEXT,
audit_timestamp TIMESTAMP NOT NULL,
status VARCHAR(20) NOT NULL,
user_id BIGINT,
CONSTRAINT fk_audit_logs_user
FOREIGN KEY (user_id)
REFERENCES users(id)
);
