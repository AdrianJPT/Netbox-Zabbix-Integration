package Cibertec.Pages;

import net.thucydides.core.annotations.Step;

public class PanelEvaluador {
    PanelEvaluadorPOM panelEvaluadorPOM;

    @Step
    public void ingresarBandejaEvaluador(){
        panelEvaluadorPOM.ingresarBandejaEvaluador();
    }
    @Step
    public void buscarTramiteID(String id){
        panelEvaluadorPOM.buscarTramiteID(id);
    }

    @Step
    public void abrirTramite(String tramite){
        panelEvaluadorPOM.abrirTramite(tramite);
    }

    @Step
    public void aprobarTramite(String observacion_evaluador){
        panelEvaluadorPOM.aprobarTramite(observacion_evaluador);
    }

    @Step
    public void rechazarTramite(String observacion_evaluador){
        panelEvaluadorPOM.rechazarTramite(observacion_evaluador);
    }
}
