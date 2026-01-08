// Graphs.js: carga datos y renderiza gráficas con Chart.js
$(document).ready(function () {
  // insertar canvas
  $('#graphsContainer').html(`
        <div class="card shadow mb-4">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-3">
                        <select id="selCasino" class="form-control"><option value="">Todas las casas</option></select>
                    </div>
                    <div class="col-md-3">
                        <select id="selSport" class="form-control"><option value="">Todos los deportes</option></select>
                    </div>
                    <div class="col-md-3">
                        <select id="selMes" class="form-control">
                          <option value="">Todos los meses</option>
                          <option value="Enero">Enero</option>
                          <option value="Febrero">Febrero</option>
                          <option value="Marzo">Marzo</option>
                          <option value="Abril">Abril</option>
                          <option value="Mayo">Mayo</option>
                          <option value="Junio">Junio</option>
                          <option value="Julio">Julio</option>
                          <option value="Agosto">Agosto</option>
                          <option value="Septiembre">Septiembre</option>
                          <option value="Octubre">Octubre</option>
                          <option value="Noviembre">Noviembre</option>
                          <option value="Diciembre">Diciembre</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <button id="btnLoad" class="btn btn-primary w-100">Cargar</button>
                    </div>
                </div>
                <div class="row mb-3">
                  <div class="col-md-12">
                    <span class="badge pr-1" id="graphGanancia"></span>
                    <span class="badge pr-1" id="graphRentabilidad"></span>
                  </div>
                </div>
                <div style="position: relative; height: 70vh; width: 100%">
                    <canvas id="chartLine"></canvas>
                </div>
            </div>
        </div>
    `);

  // poblar selects desde el servidor que ya inyectó listas en JSP
  function populateSelects() {
    if (window.Casinos) {
      window.Casinos.forEach(c => $('#selCasino').append(`<option value="${c.id}">${c.nombre || c.name || c.id}</option>`));
    }
    if (window.Sports) {
      window.Sports.forEach(s => $('#selSport').append(`<option value="${s.id}">${s.nombre || s.name || s.id}</option>`));
    }
  }

  populateSelects();

  let chart;
  function renderChart(labels, data) {
    const ctx = document.getElementById('chartLine').getContext('2d');
    if (chart) chart.destroy();
    chart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: labels,
        datasets: [{
          label: 'Rendimiento acumulado',
          data: data,
          borderColor: '#4e73df',
          backgroundColor: 'rgba(78,115,223,0.05)',
          fill: true,
        }]
      },
      options: {
        maintainAspectRatio: false,
        scales: {
          x: { display: true },
          y: { display: true }
        }
      }
    });
  }

  $('#btnLoad').click(function () {
    const casinoId = $('#selCasino').val();
    const sportId = $('#selSport').val();
    const mes = $('#selMes').val();
    const base = window.basePath || '';
    let url = base + '/api/graphs/cumulative?';
    if (casinoId) url += `casinoId=${casinoId}&`;
    if (sportId) url += `sportId=${sportId}&`;
    if (mes) url += `mes=${encodeURIComponent(mes)}&`;

    $.get(url).done(function (resp) {
      const labels = Object.keys(resp);
      const data = Object.values(resp).map(v => parseFloat(v.toFixed ? v : v));
      renderChart(labels, data);
      mostrarDatosRentabilidadGraph(data[data.length - 1]);
    }).fail(function () {
      toastr.error('Error al cargar datos');
    });
  });

  // Cargar inicial
  $('#btnLoad').trigger('click');
});

function mostrarDatosRentabilidadGraph(dato) {

  $("#graphGanancia").text('Ganancia: ' + dato.toFixed(0));
  if (dato < 0) {
    $("#graphGanancia").removeClass('text-bg-success').addClass('text-bg-danger');
  } else {
    $("#graphGanancia").removeClass('text-bg-danger').addClass('text-bg-success');
  }

  const saldoInicial = parseFloat($("#txtSaldoInicial").text().replace(/[^0-9.-]+/g, ""));
  const rentabilidad = saldoInicial ? (dato / saldoInicial * 100).toFixed(2) : 0;
  $("#graphRentabilidad").text('Rentabilidad: ' + rentabilidad + '%');
  if (rentabilidad < 0) {
    $("#graphRentabilidad").removeClass('text-bg-success').addClass('text-bg-danger');
  } else {
    $("#graphRentabilidad").removeClass('text-bg-danger').addClass('text-bg-success');
  }
}