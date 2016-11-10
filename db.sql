DROP TABLE IF EXISTS resume;

CREATE TABLE IF NOT EXISTS resume(
  uuid CHAR(36) NOT NULL,
  full_name TEXT NOT NULL,
  location TEXT,
  home_page TEXT,
  CONSTRAINT pkUuid PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS contact;

CREATE TABLE IF NOT EXISTS contact(
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  resume_uuid CHAR(36) NOT NULL,
  type TEXT NOT NULL,
  value TEXT NOT NULL,
  CONSTRAINT pkID PRIMARY KEY (id),
  CONSTRAINT fkResume_uuid FOREIGN KEY (resume_uuid) REFERENCES resume (uuid)
  ON DELETE CASCADE
  ON UPDATE NO ACTION
);