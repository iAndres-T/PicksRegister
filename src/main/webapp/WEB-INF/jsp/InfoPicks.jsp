<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div class="card shadow mb-4">
      <div class="card-header py-3 bg-second-primary" style="background-color: var(--purple);">
        <h6 class="m-0 font-weight-bold text-white">Información de Picks</h6>
      </div>
      <div class="card-body">
        <!-- Filtros -->
        <div class="row mb-3">
          <div class="col-sm-12">
            <label class="pl-2" for="cboFiltroDeporte">Deporte:</label>
            <select class="form-control col-sm-2" id="cboFiltroDeporte" style="display: inline;"></select>

            <label class="pl-2" for="cboFiltroMes">Mes:</label>
            <select class="form-control col-sm-2" id="cboFiltroMes" style="display: inline;"></select>

            <label class="pl-2" for="cboFiltroAnio">Año:</label>
            <select class="form-control col-sm-2" id="cboFiltroAnio" style="display: inline;"></select>

            <button class="btn btn-secondary" id="btnFiltrar" style="display: inline;"><i
                class="fas fa-filter"></i></button>
            <button class="btn btn-secondary" id="btnLimpiar" style="display: inline;"><i
                class="fas fa-eraser"></i></button>
          </div>
        </div>

        <!-- Tarjetas de Estadísticas -->
        <div class="row">
          <!-- Jugador Mas Rentable -->
          <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
              <div class="card-body">
                <div class="row no-gutters align-items-center">
                  <div class="col mr-2">
                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                      Jugador + Rentable</div>
                    <div class="h5 mb-0 font-weight-bold text-gray-800" id="txtPlayerMax">-</div>
                  </div>
                  <div class="col-auto">
                    <i class="fas fa-user-plus fa-2x text-gray-300"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Jugador Menos Rentable -->
          <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-danger shadow h-100 py-2">
              <div class="card-body">
                <div class="row no-gutters align-items-center">
                  <div class="col mr-2">
                    <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">
                      Jugador - Rentable</div>
                    <div class="h5 mb-0 font-weight-bold text-gray-800" id="txtPlayerMin">-</div>
                  </div>
                  <div class="col-auto">
                    <i class="fas fa-user-minus fa-2x text-gray-300"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Mercado Mas Rentable -->
          <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
              <div class="card-body">
                <div class="row no-gutters align-items-center">
                  <div class="col mr-2">
                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                      Mercado + Rentable</div>
                    <div class="h5 mb-0 font-weight-bold text-gray-800" id="txtMarketMax">-</div>
                  </div>
                  <div class="col-auto">
                    <i class="fas fa-chart-line fa-2x text-gray-300"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Mercado Menos Rentable -->
          <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-danger shadow h-100 py-2">
              <div class="card-body">
                <div class="row no-gutters align-items-center">
                  <div class="col mr-2">
                    <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">
                      Mercado - Rentable</div>
                    <div class="h5 mb-0 font-weight-bold text-gray-800" id="txtMarketMin">-</div>
                  </div>
                  <div class="col-auto">
                    <i class="fas fa-chart-area fa-2x text-gray-300"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <hr />
        <div class="row">
          <div class="col-sm-12">
            <div id="gridPicksInfo" style="height: 60vh;"></div>
          </div>
        </div>
      </div>
    </div>