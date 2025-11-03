# API-JavaSpring-Clima

Esta API consume la API de openweathermap para consultar un pronostico a 5 dias
y mostrar con un solo metodo en el endpoint la siguiente información:
 - Un pronostico para las próximas 24 horasincluyendo la Temperatura Promedio, la hora de referencia y una descripción general.
 - Un resumen para el pronostico de los
proximos 3 dias.

Funcionamiento del único metodo en el ENDPONT

Método	GET
Ruta Base	/api/clima/resumen
Parámetro Requerido	ciudad (Nombre de la ciudad, ej: Bogota, Quito)
Tipo de Contenido	text/html (Devuelve un texto pre-formateado)