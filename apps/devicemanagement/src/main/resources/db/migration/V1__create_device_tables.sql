-- V1__create_device_tables.sql

-- Create device_type table
CREATE TABLE IF NOT EXISTS device_type (
                                           id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    protocol_type VARCHAR(50) NOT NULL,
    config_schema JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Create device table
CREATE TABLE IF NOT EXISTS device (
                                      id VARCHAR(36) PRIMARY KEY,
    device_type_id VARCHAR(36) NOT NULL,
    serial_number VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    status VARCHAR(20) DEFAULT 'OFFLINE',
    last_seen TIMESTAMP,
    ip_address VARCHAR(45),
    metadata JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Create device_credentials table
CREATE TABLE IF NOT EXISTS device_credentials (
                                                  id VARCHAR(36) PRIMARY KEY,
    device_id VARCHAR(36) UNIQUE NOT NULL,
    auth_token VARCHAR(500) NOT NULL,
    public_key TEXT,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Add foreign key constraints if they don't exist

ALTER TABLE device ADD CONSTRAINT device_device_type_id_fk
    FOREIGN KEY (device_type_id) REFERENCES device_type(id);


ALTER TABLE device_credentials ADD CONSTRAINT device_credentials_device_id_fk
    FOREIGN KEY (device_id) REFERENCES device(id);


CREATE INDEX idx_device_status ON device(status);


CREATE INDEX idx_device_last_seen ON device(last_seen);


CREATE INDEX idx_device_type ON device(device_type_id);

COMMIT;
