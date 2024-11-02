-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS eventoslab8;
USE eventoslab8;

-- Crear tabla de eventos
CREATE TABLE eventos (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         nombre VARCHAR(255) NOT NULL,
                         fecha DATE NOT NULL,
                         categoria VARCHAR(100) NOT NULL,
                         capacidad_maxima INT NOT NULL,
                         reservas_actuales INT DEFAULT 0
);

-- Crear tabla de reservas
CREATE TABLE reservas (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          id_evento INT NOT NULL,
                          nombre VARCHAR(255) NOT NULL,
                          correo VARCHAR(255) NOT NULL,
                          cupos INT NOT NULL,
                          FOREIGN KEY (id_evento) REFERENCES eventos(id) ON DELETE CASCADE
);

-- Insertar datos en la tabla eventos
INSERT INTO eventos (nombre, fecha, categoria, capacidad_maxima, reservas_actuales) VALUES
                                                                                        ('Conferencia de Tecnología', '2024-11-15', 'Conferencia', 100, 25),
                                                                                        ('Exposición de Arte Moderno', '2024-12-01', 'Exposición', 50, 30),
                                                                                        ('Taller de Fotografía', '2024-11-20', 'Taller', 20, 15),
                                                                                        ('Concierto de Jazz', '2024-11-22', 'Concierto', 80, 50),
                                                                                        ('Seminario de Historia', '2024-11-30', 'Conferencia', 60, 10);

-- Insertar datos en la tabla reservas
INSERT INTO reservas (id_evento, nombre, correo, cupos) VALUES
                                                            (1, 'Juan Pérez', 'juan.perez@example.com', 3),
                                                            (1, 'Ana Gómez', 'ana.gomez@example.com', 2),
                                                            (2, 'Carlos Ramírez', 'carlos.ramirez@example.com', 4),
                                                            (3, 'María López', 'maria.lopez@example.com', 1),
                                                            (4, 'Luis Fernández', 'luis.fernandez@example.com', 5),
                                                            (4, 'Paula Torres', 'paula.torres@example.com', 3),
                                                            (2, 'Sofía Castro', 'sofia.castro@example.com', 2),
                                                            (5, 'Diego Sánchez', 'diego.sanchez@example.com', 1),
                                                            (1, 'Laura Gil', 'laura.gil@example.com', 2),
                                                            (3, 'Elena Martín', 'elena.martin@example.com', 2);
