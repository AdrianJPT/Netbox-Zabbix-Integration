# Utiliza una imagen base con Python instalado
FROM python:3.8

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia los archivos necesarios al contenedor
COPY requirements.txt app.py /app/

# Instala los requisitos
RUN pip install --no-cache-dir -r requirements.txt

# Expone el puerto 5000 para acceder a la aplicación
EXPOSE 5000

# Ejecuta la aplicación Flask
CMD ["python", "app.py"]
