CREATE TABLE IF NOT EXISTS td_users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS td_friendships (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    status ENUM('pending', 'accepted', 'blocked') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES td_users(id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES td_users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS td_channels (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    owner_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted TINYINT DEFAULT 0,
    FOREIGN KEY (owner_id) REFERENCES td_users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS td_channel_memberships (
    id INT PRIMARY KEY AUTO_INCREMENT,
    channel_id INT NOT NULL,
    user_id INT NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'GUEST',
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (channel_id) REFERENCES td_channels(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES td_users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS td_messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    channel_id INT,
    sender_id INT NOT NULL,
    recipient_id INT,
    content TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (channel_id) REFERENCES td_channels(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES td_users(id) ON DELETE CASCADE,
    FOREIGN KEY (recipient_id) REFERENCES td_users(id) ON DELETE CASCADE
);