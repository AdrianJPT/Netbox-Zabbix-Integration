Feature: Tramite Cambio de Carrera

  Scenario Outline: Rechazar trámite Cambio de Carrera

    Given Me he logeado correctamente con <user> y <password>
    When Yo click en Solicitud de Tramite
    And Elijo un Tipo de Modalidad <modalidad>
    And Elijo un Programa
    And Elijo un Tramite <tramite>
    And Elijo Modalidad de Pago
    And Escribo Motivo <tramite>
    And Adjunto Archivo

    Then Se debe mostrar los avisos de Cambio de carrera

    When Click en el boton Enviar Solicitud
    And Realizo click en mi <tramite> creado
    Then El estatus deberia estas en PENDIENTE DE PAGO
    And El tracking debe estar en la etapa REGISTRO DE SOLICITUD
    And Deberia ver el precio <precio>


    When Alumno <user> realiza el pago del <tramite> con codigo <cod_tramite>
    Then El tracking debe estar en la etapa PAGO DE SOLICITUD


    When El evaluador con contrasena <password> desaprueba el <tramite> con el motivo <tramite>
    Then El trackin de <user> con contraseña <password> su <tramite> debe estar en la etapa de notificacion

    Examples:

      | user        | password    | modalidad                | tramite                        | precio  | cod_tramite          |
      | "201410685" | "123456789" | "AC - CARRERAS TECNICAS" | "Solicitud cambio de carrera" | "32.00" | "SOTRAGE_CAMBIOCARRERA" |

