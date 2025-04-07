<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="card shadow mb-4">
    <div class="card-header py-3 bg-second-primary" style="background-color: var(--purple);">
        <h6 class="m-0 font-weight-bold text-white">Lista de Picks</h6>
    </div>
    <div class="card-body">
        <div class="row">
            <div class="col-sm-5">
                <button class="btn btn-warning" id="btnNuevoPickNBA"><i class="fas fa-plus-circle"></i> Registrar NBA</button>
                <button class="btn btn-primary" id="btnNuevoPickMLB"><i class="fas fa-plus-circle"></i> Registrar MLB</button>
                <button class="btn btn-success" id="btnNuevoPickFutbol"><i class="fas fa-plus-circle"></i> Registrar Futbol</button>
            </div>
        </div>
        <hr />
        <div class="row">
            <div class="col-sm-12">
                <div id="gridPicks" style="height: 60vh;"></div>
            </div>
        </div>
    </div>
</div>

<!--Modal-->
<div class="modal fade" id="modalData" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h6>Detalle Pick</h6>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close" id="btnCloseModal">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-row">
                                <div class="form-group col-sm-3">
                                    <input type="text" hidden class="form-control form-control-sm" id="txtId" name="id">
                                    <label for="txtFecha">Fecha</label>
                                    <input type="date" class="form-control form-control-sm input-validar" id="txtFecha" name="fecha">
                                </div>
                                <div class="form-group col-sm-3">
                                    <label for="cboCasino">Casa de Apuesta</label>
                                    <select class="form-control form-control-sm" id="cboCasino" name="casino">
                                    </select>
                                </div>
                                <div class="form-group col-sm-3">
                                    <label for="cboSport">Deporte</label>
                                    <select class="form-control form-control-sm" id="cboSport" name="sport">
                                    </select>
                                </div>
                                <div class="form-group col-sm-3">
                                    <label for="cboCountry">País</label>
                                    <select class="form-control form-control-sm" id="cboCountry" name="country">
                                    </select>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-sm-4">
                                    <label for="cboLinea">Linea</label>
                                    <select class="form-control form-control-sm" id="cboLinea" name="linea">
                                    </select>
                                </div>
                                <div class="form-group col-sm-4">
                                    <label for="cboDescripcion">Detalle del Pick</label>
                                    <select class="form-control form-control-sm" id="cboDescripcion" name="descripcion">
                                    </select>
                                </div>
                                <div class="form-group col-sm-4">
                                    <label for="txtJugador">Jugador</label>
                                    <input type="text" class="form-control form-control-sm input-validar" id="txtJugador" name="jugador" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-sm-4">
                                    <label for="txtEquipoLocal">Equipo Local</label>
                                    <input type="text" class="form-control form-control-sm input-validar" id="txtEquipoLocal" name="equipoLocal" autocomplete="off">
                                </div>
                                <div class="form-group col-sm-4">
                                    <label for="txtEquipoVisitante">Equipo Visitante</label>
                                    <input type="text" class="form-control form-control-sm input-validar" id="txtEquipoVisitante" name="equipoVisitante" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-sm-4">
                                    <label for="txtValor">Valor Apostado</label>
                                    <input type="number" class="form-control form-control-sm input-validar" id="txtValor" name="valor" autocomplete="off">
                                </div>
                                <div class="form-group col-sm-4">
                                    <label for="txtCuota">Cuota</label>
                                    <input type="text" oninput="this.value = this.value.replace(/[^0-9.]/g, '')" class="form-control form-control-sm input-validar" id="txtCuota" name="cuota" autocomplete="off">
                                </div>
                                <div class="form-group col-sm-4">
                                    <label for="cboResultado">Resultado</label>
                                    <select class="form-control form-control-sm" id="cboResultado" name="resultado">
                                        <option value="Pendiente">Pendiente</option>
                                        <option value="Acierto">Acierto</option>
                                        <option value="Perdido">Perdido</option>
                                        <option value="Nulo">Nulo</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary btn-sm" type="button" id="btnGuardarPick">Guardar</button>
            </div>
        </div>
    </div>
</div>

