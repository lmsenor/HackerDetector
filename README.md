# Hacker-Detector
Servicio REST que detecta logins incorrectos guardando la IP del usuario.

Al realizar una llamada al servicio REST, se registra la IP del usuario, la fecha actual, un código de acceso válido o inválido y el nombre de usuario.

Como requisito para que la llamada sea correcta debe de aparecer un ".", en caso contrario se registra como acceso denegado.

Según el estado de la llamada se registran los accesos en una carpeta temp en los siguientes logs:

  - "datos.log"         -> Registra todos los accesos, correctos e incorrectos.
  - "IPsospechosos.log" -> Registra los accesos denegados.
  - "IPbloqueados.log"  -> Registra los accesos bloqueados si un usuario con la misma IP accede de forma incorrecta 5 veces o más en los últimos 5 minutos.

El dominio del servicio REST debe de tener la siguiente dirección:
  - http://localhost:8080/Will.Smith

Este servicio está desplegado en un docker, para instanciar la imagen acceder a:
  - lmsenor/imagenesdocker:hackerdetector
